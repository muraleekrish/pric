/*
 * Created on Feb 7, 2007
 *
 */
package com.savant.pricing.tdp.dao;


import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.LoadPrerequisites;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.ui.VerticalAlignment;
import org.jfree.util.SortOrder;

import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.PricingException;

/**
 * 
 * @author Thiyagarajan
 *
 */

public class IDRProfilerManger 
{

    public Hashtable getDayProfileForAnYear(String esiid) throws PricingException
    {
        Hashtable htResult = new Hashtable();
        Vector vecperiod = new Vector();
        Vector vecKwh = new Vector();
        NumberFormat kwh=NumberUtil.getkWhFormatter();
        Session objSession = null;
        CallableStatement cstmnt = null;
        ResultSet rs = null;
        try
        {
            if(esiid == null)
            {
                throw new PricingException("ESIID Number cannot be null");
            }
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            cstmnt = objSession.connection().prepareCall("{call spTypicalDayProfileforanYear(?)}");
            cstmnt.setString(1,esiid);
            rs = cstmnt.executeQuery();
           
          /*  objDBConnection = DBConnectionFactory.getConnection();
            Hashtable htQuery = new Hashtable();
            htQuery.put("esiid",esiid);
            ResultSet rs = objDBConnection.executeStatement("{call spTypicalDayProfileforanYear(#esiid#)} ",htQuery);*/
            String mon[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
            while(rs.next())
            {
                String timeStamp = mon[(rs.getInt("month")-1)]+" "+ rs.getString("year");
                vecperiod.addElement(timeStamp);
                vecKwh.addElement(kwh.format(new Double(rs.getString("sumkwh")).doubleValue()));
            }
            htResult.put("timeStamp",vecperiod);
            htResult.put("kwh",vecKwh);
            objSession.getTransaction().commit();
            cstmnt.close();
            rs.close();
        }
        catch(PricingException e)
        {
            objSession.getTransaction().rollback();
            throw new PricingException(e.toString());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            objSession.close();
        }
        return htResult;
    }
    public Vector getDayProfileByIntervalSummary(String dataFor, String esiid, String fromDate, String toDate) throws PricingException
    {
        Vector result = new Vector();
        Session objSession = null;
        CallableStatement cstmnt = null;
        ResultSet rs = null;
        //DBConnection objDBConnection = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try 
        {
            if((dataFor.trim().length() == 0) || (dataFor == null))
                throw new IDRProfilerException("Data for cannot be Null or Empty");
            if(esiid == null)
                throw new IDRProfilerException("ESIID Number cannot be Null");
            if(fromDate == null)
                throw new IDRProfilerException("From Date cannot be Null");
            if(toDate == null)
                throw new IDRProfilerException("To Date cannot be Null");

            SimpleDateFormat sdfUI = new SimpleDateFormat("MMM-dd-yyyy");
            if((esiid.trim().length() != 0) && (fromDate.trim().length() == 0))
            {
            	Hashtable ht = this.getFlowDate(esiid);
            	toDate = ht.get("maxflowDate").toString();
            	if(toDate.trim().length() !=0)
            	{
            	   
            		GregorianCalendar gc = new GregorianCalendar();
            		gc.setTime(new Date(toDate));
            		gc.add(Calendar.DATE,-6);
            		fromDate = sdfUI.format(gc.getTime());
            		
            	}
            }
           /* objDBConnection = DBConnectionFactory.getConnection();
            Hashtable htQuery = new Hashtable();
            htQuery.put("esiid",esiid);
            htQuery.put("startDate",sdf.format(new Date(fromDate)));
            htQuery.put("endDate",sdf.format(new Date(toDate)));
            htQuery.put("datafor",dataFor);
            NumberFormat kwh=NumberUtil.getkWhFormatter();
            ResultSet rs = objDBConnection.executeStatement("{call sp_getTypicalDayProfileData(#esiid#, #startDate#,#endDate#,#datafor#)} ",htQuery);*/
            NumberFormat kwh=NumberUtil.getkWhFormatter();
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            cstmnt = objSession.connection().prepareCall("{call sp_getTypicalDayProfileData(?,?,?,?)}");
            cstmnt.setString(1,esiid);
            cstmnt.setString(2,sdf.format(new Date(fromDate)));
            cstmnt.setString(3,sdf.format(new Date(toDate)));
            cstmnt.setString(4,dataFor);
            rs = cstmnt.executeQuery();
            
            if(dataFor.equalsIgnoreCase("interval"))
            {
                while(rs.next())
                {
                    ImportDataDetails objDetails = new ImportDataDetails();
                    String str = ""; 
                    objDetails.setMrktDate((str = rs.getString("flowDate")) == null ? str = "" : str.trim());
                    objDetails.setHour((str = rs.getString("interval")) == null ? str = "" : str.trim());
                    objDetails.setKwh(kwh.format(new Double((str = rs.getString("sumkwh")) == null ? str = "" : str.trim())));
                    result.addElement(objDetails);
                }
            }
            else
            {
                while(rs.next())
                {
                    ImportDataDetails objDetails = new ImportDataDetails();
                    String str = ""; 
                    objDetails.setMrktDate((str = rs.getString("flowDate")) == null ? str = "" : str.trim());
                    objDetails.setKwh(kwh.format(new Double((str = rs.getString("sumkwh")) == null ? str = "" : str.trim()).doubleValue()));
                    result.addElement(objDetails);
                }
            }
            objSession.getTransaction().commit();

            cstmnt.close();
            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            objSession.getTransaction().rollback();
            throw new PricingException(e.toString());
        }
        
        finally
        {
           
            objSession.close();
        }
        return result;        
    }
    
    public String getChartForDayProfile(Vector vecParam, String esiid, String fromDate, String toDate, HttpSession session,String charttitle)
    {
        String fileName="";
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = null;
        try 
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
            for(int i=0;i<vecParam.size();i++)
            {
                ImportDataDetails objDetails = (ImportDataDetails)vecParam.get(i);
                dataset.addValue(Double.parseDouble(objDetails.getKwh().replaceAll(",","")),sdf.format(new Date(objDetails.getMrktDate())),objDetails.getHour()+"");
            }
            
            SimpleDateFormat sdfUI = new SimpleDateFormat();
            if(fromDate.equals(""))
            {
            	fromDate = sdfUI.format(new Date());
            }
            if(toDate.equals(""))
            {
            	toDate = sdfUI.format(new Date());
            }

            SimpleDateFormat sdfTitle = new SimpleDateFormat("EEE, MMM d, yyyy");
            String title = charttitle+esiid+" from "+sdfTitle.format(new Date(fromDate))+" to "+sdfTitle.format(new Date(toDate));
            chart = createChart(dataset,title,true,"Hour","kWh");
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            fileName = ServletUtilities.saveChartAsJPEG(chart, 700, 400,info,  session);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
       
        return fileName;
    }
    
    public String getChartForProfileByDate(Vector vecParam, String esiid, String fromDate, String toDate, HttpSession session,String charttitle)
    {
        String fileName="";
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = null;
        try 
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
            
            for(int i=0;i<vecParam.size();i++)
            {
                ImportDataDetails objDetails = (ImportDataDetails)vecParam.get(i);
                dataset.addValue(Double.parseDouble(objDetails.getKwh().replaceAll(",","")),"",sdf.format(new Date(objDetails.getMrktDate())));
            }
            SimpleDateFormat sdfUI = new SimpleDateFormat();
            if(fromDate.equals(""))
            {
            	fromDate = sdfUI.format(new Date());
            }
            if(toDate.equals(""))
            {
            	toDate = sdfUI.format(new Date());
            }
            SimpleDateFormat sdfTitle = new SimpleDateFormat("EEE, MMM d, yyyy");
            String title = charttitle+esiid+" from "+sdfTitle.format(new Date(fromDate))+" to "+sdfTitle.format(new Date(toDate));
            chart = createChart(dataset,title, false,"Date","kWh");
            
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            fileName = ServletUtilities.saveChartAsJPEG(chart, 700, 400,info,  session);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
       
        return fileName;
    }
    public String getBarChartForProfileByDate(Vector vecParam, String esiid, String fromDate, String toDate, HttpSession session,String charttitle)
    {
        String fileName="";
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = null;
        try 
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
            
            for(int i=0;i<vecParam.size();i++)
            {
                ImportDataDetails objDetails = (ImportDataDetails)vecParam.get(i);
                dataset.addValue(Double.parseDouble(objDetails.getKwh().replaceAll(",","")),"",sdf.format(new Date(objDetails.getMrktDate())));
                
            }
            SimpleDateFormat sdfUI = new SimpleDateFormat();
            if(fromDate.equals(""))
            {
            	fromDate = sdfUI.format(new Date());
            }
            if(toDate.equals(""))
            {
            	toDate = sdfUI.format(new Date());
            }
            SimpleDateFormat sdfTitle = new SimpleDateFormat("EEE, MMM d, yyyy");
            String title = charttitle+esiid+" from "+sdfTitle.format(new Date(fromDate))+" to "+sdfTitle.format(new Date(toDate));

             chart = ChartFactory.createStackedBarChart3D
        	(
        		charttitle+esiid,  // Contract Average Supply Cost-$/MWH chart title
                "Date ",                   // domain axis label
                "kWh",                      // range axis label
                dataset,                      // data
                PlotOrientation.VERTICAL,   // the plot orientation
                false,                         // include legend
                true,                         // tooltips
                false                         // urls
             );
             chart.setBackgroundPaint (new Color(0xFF, 0xFF, 0xFF));
             
                  final CategoryPlot plot = chart.getCategoryPlot();
                
                  plot.getRenderer().setSeriesPaint(0, new Color(204,224, 65));
                  plot.getRenderer().setSeriesPaint(1, new Color(204,224, 65));
                  plot.getRenderer().setSeriesPaint(2, new Color(204,224, 65));
                  plot.getRenderer().setSeriesPaint(3, new Color(204,224, 65));
                  plot.getRenderer().setSeriesPaint(4, new Color(204,224, 65));
                  plot.getRenderer().setSeriesPaint(5, new Color(204,224, 65));
                  final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
                 
                  rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
                  
                  final CategoryItemRenderer renderer = plot.getRenderer();
                //  renderer.setLabelGenerator(new StandardCategoryLabelGenerator());
                  renderer.setItemLabelsVisible(false);
                  
                  final CategoryAxis domainAxis = plot.getDomainAxis();
                  domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
                  
                  renderer.setPositiveItemLabelPosition(
                          new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER)
                  );
                  renderer.setNegativeItemLabelPosition(
                          new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER)
                  );
                  ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
                   fileName = ServletUtilities.saveChartAsJPEG( chart, 700, 400 , info, session);
                      
          }catch (Exception e) 
          {
                      // TODO: handle exception
          }
                  return fileName;
    }
    public String getChartForProfileByYear(Hashtable htResult, String esiid, HttpSession session)
    {
        String fileName="";
        
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = null;
        try 
        {
            if(htResult == null)
                throw new IDRProfilerException("IDR Profile Manager Chart Hashtable is null");

            Vector vecperiod = new Vector();
            Vector vecKwh = new Vector();

            vecperiod = (Vector)htResult.get("timeStamp");
            vecKwh = (Vector)htResult.get("kwh");

            for(int i=0;i<vecperiod.size();i++)
            {
                dataset.addValue(Double.parseDouble(vecKwh.get(i).toString().replaceAll(",","")),"",vecperiod.get(i).toString());
            }
            
            chart = createChart(dataset,"Monthly Profile for ESI-ID : "+esiid, false,"Time Period","kWh"); 
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            fileName = ServletUtilities.saveChartAsJPEG(chart, 700, 400,info,  session);
          
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
       
        return fileName;
    }
    //barchart
    public String getBarChartForProfileByYear(Hashtable htResult, String esiid, HttpSession session,String charttitle)
    {    	

    	DefaultCategoryDataset dataset =new DefaultCategoryDataset();
    	String filename=null;
    	Vector vecperiod = new Vector();
        Vector vecKwh = new Vector();

            vecperiod = (Vector)htResult.get("timeStamp");
            vecKwh = (Vector)htResult.get("kwh");

            for(int i=0;i<vecperiod.size();i++)
            {
                dataset.addValue(Double.parseDouble(vecKwh.get(i).toString().replaceAll(",","")),"",vecperiod.get(i).toString());
            }
    	
        final JFreeChart chart = ChartFactory.createStackedBarChart3D
    	(
    		charttitle+esiid,  // Contract Average Supply Cost-$/MWH chart title
            "Time Period ",                   // domain axis label
            "kWh",                      // range axis label
            dataset,                      // data
            PlotOrientation.VERTICAL,   // the plot orientation
            false,                         // include legend
            true,                         // tooltips
            false                         // urls
         );
        chart.setBackgroundPaint (new Color(0xFF, 0xFF, 0xFF));
   
        final CategoryPlot plot = chart.getCategoryPlot();
      
        plot.getRenderer().setSeriesPaint(0, new Color(204,224, 65));
        plot.getRenderer().setSeriesPaint(1, new Color(204,224, 65));
        plot.getRenderer().setSeriesPaint(2, new Color(204,224, 65));
        plot.getRenderer().setSeriesPaint(3, new Color(204,224, 65));
        plot.getRenderer().setSeriesPaint(4, new Color(204,224, 65));
        plot.getRenderer().setSeriesPaint(5, new Color(204,224, 65));
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
       
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        final CategoryItemRenderer renderer = plot.getRenderer();
    //    renderer.setLabelGenerator(new StandardCategoryLabelGenerator());
        renderer.setItemLabelsVisible(false);
        
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        
        renderer.setPositiveItemLabelPosition(
                new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER)
        );
        renderer.setNegativeItemLabelPosition(
                new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER)
        );
        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
        try
        {
            filename = ServletUtilities.saveChartAsJPEG( chart, 700, 400 , info, session);
            
            //  Write the image map to the PrintWriter
         /*   PrintWriter pw= new PrintWriter(System.out);
            ChartUtilities.writeImageMap(pw, filename, info);
            pw.flush();*/
        }catch (Exception e) 
        {
            // TODO: handle exception
        }
        return filename;
}
    
    
    
    public String[] getChartForTypicalDayHourlyProfileDataByESIID(Vector vec, String esiidNumber, HttpSession session)
    {
        String[] fileName = {"",""};
        final DefaultCategoryDataset datasetWeekDays = new DefaultCategoryDataset();
        final DefaultCategoryDataset datasetWeekEnds = new DefaultCategoryDataset();
        JFreeChart chartWeekDays = null;
        JFreeChart chartWeekEnds = null;
        try 
        {
            if(vec == null)
            {
                throw new IDRProfilerException("Weekly Profile Data Vector cannot be null");
            }
            
            String mon[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"}; 
            for(int i=0;i<vec.size();i++)
            {
                TypicalDayProfileWeeklyDetails objDetails = (TypicalDayProfileWeeklyDetails) vec.get(i);
                datasetWeekDays.addValue(Double.parseDouble(objDetails.getWeekDaykWh().replaceAll(",","")),"",objDetails.getMonth());
                datasetWeekEnds.addValue(Double.parseDouble(objDetails.getWeekEndkWh().replaceAll(",","")),"",objDetails.getMonth());
            }
            chartWeekDays = createChart(datasetWeekDays,"Week Days", false,"Time Period","kWh");
            chartWeekEnds = createChart(datasetWeekEnds,"Week Ends", false,"Time Period","kWh");
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            fileName[0] = ServletUtilities.saveChartAsJPEG(chartWeekDays, 700, 400,info,  session);
            fileName[1] = ServletUtilities.saveChartAsJPEG(chartWeekEnds, 700, 400,info,  session);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
       
        return fileName;
    }
    /**
     * Creates a sample chart.
     * 
     * @param dataset  a dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(final CategoryDataset dataset, String title, boolean legend, String xAxisTitle, String yAxisTitle) 
    {
        // create the chart...
        JFreeChart chart = null;
        try
        {
            chart = ChartFactory.createLineChart(
                title,      			   // chart title
                xAxisTitle,                // domain axis label
                yAxisTitle,                // range axis label
                dataset,                   // data
                PlotOrientation.VERTICAL,  // orientation
                legend,                    // include legend
                true,                      // tooltips
                false                      // urls
        );
        
        //title = "Daily Graph for ESIID 1008901000153480017100 for Thursday Mar 30, 2006"; 
        
        chart.setBackgroundPaint(Color.white);
        chart.setBorderVisible(false);
        final TextTitle subtitle = new TextTitle(title);
        subtitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        subtitle.setPosition(RectangleEdge.TOP);
        //subtitle.setPadding(new RectangleInsets(0.05, 0.05, 0.05, 0.05));
        subtitle.setVerticalAlignment(VerticalAlignment.CENTER);
        subtitle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        chart.setTitle(subtitle);
                
        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.lightGray);
        
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        
        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
                
        renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        renderer.setDefaultShapesFilled(false);
        renderer.setSeriesOutlinePaint(0,new Color(229,61,0));
        
        plot.setRenderer(1, renderer);
        plot.setAnchorValue(0.8);
        plot.setRowRenderingOrder(SortOrder.ASCENDING);
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return chart;
    }

    
    public Vector getTypicalDayHourlyProfileDataByESIID(String esiid, String month)
    {
        Vector resultVec = new Vector();
        Session objSession = null;
        CallableStatement cstmnt = null;
        ResultSet rs = null;
        NumberFormat kwh=NumberUtil.getkWhFormatter();
        try
        {
            if(esiid == null)
                throw new IDRProfilerException("ESIID Number cannot be Null");
            
            if((month == null) || (month.trim().length() == 0))
                throw new IDRProfilerException("Month cannot be Null or Empty");

            
            try
            {
                objSession = HibernateUtil.getSession();
                objSession.beginTransaction();
                cstmnt = objSession.connection().prepareCall("{call sp_GetTypicalDayHourlyProfileDataByESIID (?,?)}");
                cstmnt.setString(1,esiid);
                cstmnt.setString(2,month);
              /*  Hashtable htQuery = new Hashtable();
                htQuery.put("ESIIDNumber",esiid);
                htQuery.put("month",month);
                ResultSet rs = objDBConnection.executeStatement("{call dbo.sp_GetTypicalDayHourlyProfileDataByESIID (#ESIIDNumber#,#month#)}",htQuery );*/
                rs = cstmnt.executeQuery(); 
                while (rs.next())
                {
                    TypicalDayProfileWeeklyDetails objDetails = new TypicalDayProfileWeeklyDetails();
                    objDetails.setMonth(rs.getString("month"));
                    objDetails.setInterval(rs.getString("month"));
                    objDetails.setWeekDaykWh(kwh.format(rs.getDouble("weekdaykwh")));
                    objDetails.setWeekEndkWh(kwh.format(rs.getDouble("weekendkwh")));
                    resultVec.addElement(objDetails);
                }
                cstmnt.close();
                rs.close();
            }
            catch (Exception e) 
            {
                e.printStackTrace();
            }
            finally
            {
                objSession.close();
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return resultVec;
    }
    
    
    public Vector getTypicalDayProfileDataByESIID(String esiid) throws PricingException
    {
        Vector resultVec = new Vector();
     //   DBConnection objDBConnection = null;
        Session objSession = null;
        CallableStatement cstmnt = null;
        ResultSet rs = null;
        try
        {
            
            if(esiid == null)
                throw new IDRProfilerException("ESIID Number cannot be Null");
            
            try
            {
                /*objDBConnection = DBConnectionFactory.getConnection();
                Hashtable htQuery = new Hashtable();
                htQuery.put("ESIIDNumber",esiid);
                ResultSet rs = objDBConnection.executeStatement("{call dbo.sp_GetTypicalDayProfileDataByESIID (#ESIIDNumber#)}",htQuery );*/
                objSession = HibernateUtil.getSession();
                objSession.beginTransaction();
                cstmnt = objSession.connection().prepareCall("{call dbo.sp_GetTypicalDayProfileDataByESIID (?)}");
                cstmnt.setString(1,esiid);
                rs = cstmnt.executeQuery();
                SimpleDateFormat sdf = new SimpleDateFormat("MM");
                NumberFormat kwh=NumberUtil.getkWhFormatter();
                while (rs.next())
                {
                    String str = "";
                    TypicalDayProfileDetails objDetails = new TypicalDayProfileDetails();
                    objDetails.setMonth((str = rs.getString("mnth")) == null ?str = "" : str.trim());
                    objDetails.setYear((str = rs.getString("yr")) == null ?str = "" : str.trim());
                    objDetails.setTotalOnPeakkWh(kwh.format(new Double((str = rs.getString("TotalOnPeakkWh")) == null ?str = "" : str.trim()).doubleValue()));
                    objDetails.setTotalOffPeakkWh(kwh.format(new Double((str = rs.getString("TotalOffPeakkWh")) == null ?str = "" : str.trim()).doubleValue()));
                    objDetails.setTotalkWh(kwh.format(new Double((str = rs.getString("TotalkWh")) == null ?str = "" : str.trim()).doubleValue()));
                    objDetails.setMinHourlykWh((str = rs.getString("MinHourlykWh")) == null ?str = "" : str.trim());
                    objDetails.setMaxHourlykWh((str = rs.getString("MaxHourlykWh")) == null ?str = "" : str.trim());
                    objDetails.setMin15MinIntkWh((str = rs.getString("Min15MinIntkWh")) == null ?str = "" : str.trim());
                    objDetails.setMax15MinIntkWh((str = rs.getString("Max15MinIntkWh")) == null ?str = "" : str.trim());
                    objDetails.setNoOfWeekDay((str = rs.getString("NoOfWeekDay")) == null ?str = "" : str.trim());
                    objDetails.setNoOfWeekEnd((str = rs.getString("NoOfWeekEnd")) == null ?str = "" : str.trim());
                    objDetails.setNoOfDays((str = rs.getString("NoOfDays")) == null ?str = "" : str.trim());
                    objDetails.setLoadFactor((str = rs.getString("loadfactor")) == null ? str = "" : str.trim());
                    resultVec.addElement(objDetails);
                }
                objSession.getTransaction().commit();
                cstmnt.close();
                rs.close();
            }
            catch(Exception e)
            {
                objSession.getTransaction().rollback();
                throw new PricingException(e.toString());
            }
            finally
            {
                objSession.close();
            }
        }
        catch (IDRProfilerException e)
        {
            e.printStackTrace();
        }

        return resultVec;
    }

    public String importFile(String esiidno,String fName, String tdspType,String userType,String sheetName) throws PricingException  
    {
        Session objSession = null;
        CallableStatement cstmnt = null;
        ResultSet rs = null;
        boolean result = false;
        POIFSFileSystem fs;
        HSSFWorkbook wb = null ;
        String sName = sheetName;
        String results=null;
        try
        {
            File f=new File(fName);   
            if((tdspType == null) || (tdspType.trim().length() == 0))
                throw new IDRProfilerException("TDSP Type can not be Null or Empty");
            
            if((fName == null) || (fName.trim().length() == 0))
                throw new IDRProfilerException("File Name can not be Null or Empty");

            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            cstmnt = objSession.connection().prepareCall("{ CALL sp_ReadTypicalDayUsageData(?,?,?,?,?)}");
            cstmnt.setString(1,esiidno);
            cstmnt.setString(2,tdspType);
            cstmnt.setString(3,fName);
            cstmnt.setString(4,sName);
            cstmnt.setString(5,userType);
            rs= cstmnt.executeQuery();
            if(rs.next())
            {
            	results=rs.getString(1);
            }
            objSession.getTransaction().commit();
            cstmnt.close();
            rs.close();
        }
        catch(Exception e)
        {
            objSession.getTransaction().rollback();
            e.printStackTrace();
            throw new PricingException("Invalid File Format");
           
        }
        finally
        {
            objSession.close();
        }
        if(!results.trim().equalsIgnoreCase("Import successfully completed."))
        {
            System.out.println(" Error Page :" + results);
            results="Import Failed Invaild File Format";
            System.out.println(" Error Page :" + results);
        }
        return results;
    }

    public boolean moveTypicalDayDataToSrcTbl(String tdspType) throws PricingException
    {
        boolean result = false;
        Session objSession = null;
        CallableStatement cstmnt = null;
        
        //DBConnection objDBConnection =null;
        try
        {
          //  objDBConnection = DBConnectionFactory.getConnection();
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            cstmnt = objSession.connection().prepareCall("{call sp_MoveTypicalDayDataToSrcTbl (?)}");
            //PreparedStatement cs = objDBConnection.getPreparedStatement("{CALL sp_MoveTypicalDayDataToSrcTbl(?)}");
            cstmnt.setString(1,tdspType);
            int i = cstmnt.executeUpdate();
            
            if(i == 0)
            	result = true;
            objSession.getTransaction().commit();
            cstmnt.close();
        }
        catch(Exception e)
        {
            objSession.getTransaction().rollback();
            throw new PricingException(e.toString());
        }
        finally
        {
          
            objSession.close();
        }
        return result;
    }
    
    public Vector getImportedData(String tdspType)
    {
        Vector resultVec = new Vector();
     //   DBConnection objDBConnection = null;
        Session objSession = null;
        CallableStatement cstmnt = null;
        List objlist = null;
        try
        {
            //objDBConnection = DBConnectionFactory.getConnection();
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            String query = "";
            if(tdspType.equalsIgnoreCase("CenterPoint"))
            {
                query = "Select * from tbl_TypicalDayProfile_cp order by recorddate, endinterval";
                Query objQuery = objSession.createSQLQuery(query);
                Iterator itr = objQuery.list().iterator();
                while(itr.hasNext())
                {
                    ImportedDataDetails objDetails = new ImportedDataDetails();
                    String str = "";
                    Object[] innerRow = (Object[]) itr.next();
                    objDetails.setEsiid((str = String.valueOf(innerRow[0]) )== null ? str = "" : str.trim());
                    objDetails.setFlowdate((str = String.valueOf(innerRow[1]))== null ? str = "" : str.trim());
                    objDetails.setInterval((str = String.valueOf(innerRow[2]))== null ? str = "" : str.trim());
                    objDetails.setKwh((str = String.valueOf(innerRow[3]))== null ? str = "" : str.trim());
                    resultVec.addElement(objDetails);
                }
                objSession.getTransaction().commit();
                objSession.close();
              /*  while(rs.next())
                {
                    objDetails.setEsiid((str = rs.getString("esiid"))== null ? str = "" : str.trim());
                    objDetails.setFlowdate((str = rs.getString("recordDate"))== null ? str = "" : str.trim());
                    objDetails.setInterval((str = rs.getString("endInterval"))== null ? str = "" : str.trim());
                    objDetails.setKwh((str = rs.getString("qty"))== null ? str = "" : str.trim());
                    resultVec.addElement(objDetails);
                }*/
            }
            else if(tdspType.equalsIgnoreCase("Oncor"))
            {
                query = "Select * from tbl_TypicalDayProfile_Oncor order by recorddate, endinterval";
                Query objQuery = objSession.createSQLQuery(query);
                Iterator itr = objQuery.list().iterator();
                while(itr.hasNext())
                {
                    ImportedDataDetails objDetails = new ImportedDataDetails();
                    String str = "";
                    Object[] innerRow = (Object[]) itr.next();
                    objDetails.setEsiid((str = String.valueOf(innerRow[0]))== null ? str = "" : str.trim());
                    objDetails.setMonth((str = String.valueOf(innerRow[1]))== null ? str = "" : str.trim());
                    objDetails.setFlowdate((str = String.valueOf(innerRow[2]))== null ? str = "" : str.trim());
                    objDetails.setInterval((str = String.valueOf(innerRow[3]))== null ? str = "" : str.trim());
                    objDetails.setKwh((str = String.valueOf(innerRow[4]))== null ? str = "" : str.trim());
                    objDetails.setKVARh((str = String.valueOf(innerRow[5]))== null ? str = "" : str.trim());
                    resultVec.addElement(objDetails);
                }
                objSession.getTransaction().commit();
                objSession.close();
              /*  query = "Select * from tbl_TypicalDayProfile_Oncor order by flowdate, interval";
                ResultSet rs = objDBConnection.executeStatement(query,new Hashtable());
                while(rs.next())
                {
                    ImportedDataDetails objDetails = new ImportedDataDetails();
                    String str = "";
                    objDetails.setEsiid((str = rs.getString("esiid"))== null ? str = "" : str.trim());
                    objDetails.setMonth((str = rs.getString("month"))== null ? str = "" : str.trim());
                    objDetails.setFlowdate((str = rs.getString("flowdate"))== null ? str = "" : str.trim());
                    objDetails.setInterval((str = rs.getString("Interval"))== null ? str = "" : str.trim());
                    objDetails.setKwh((str = rs.getString("kwh"))== null ? str = "" : str.trim());
                    objDetails.setKVARh((str = rs.getString("kvarh"))== null ? str = "" : str.trim());
                    resultVec.addElement(objDetails);
                }*/
            }
            else
            {
                query = "Select * from tbl_TypicalDayProfile_AEP order by interval";
                Query objQuery = objSession.createSQLQuery(query);
                Iterator itr = objQuery.list().iterator();
                while(itr.hasNext())
                {
                    ImportedDataDetails objDetails = new ImportedDataDetails();
                    String str = "";
                    Object[] innerRow = (Object[]) itr.next();
                    objDetails.setEsiid((str =String.valueOf(innerRow[0]))== null ? str = "" : str.trim());
                    objDetails.setInterval((str = String.valueOf(innerRow[1]))== null ? str = "" : str.trim());
                    objDetails.setKwh((str = String.valueOf(innerRow[2]))== null ? str = "" : str.trim());
                    resultVec.addElement(objDetails);
                }
                objSession.getTransaction().commit();
                objSession.close();
              /*  query = "Select * from tbl_TypicalDayProfile_AEP order by interval";
                ResultSet rs = objDBConnection.executeStatement(query,new Hashtable());
                while(rs.next())
                {
                    ImportedDataDetails objDetails = new ImportedDataDetails();
                    String str = "";
                    objDetails.setEsiid((str = rs.getString("esiid"))== null ? str = "" : str.trim());
                    objDetails.setInterval((str = rs.getString("Interval"))== null ? str = "" : str.trim());
                    objDetails.setKwh((str = rs.getString("usage"))== null ? str = "" : str.trim());
                    resultVec.addElement(objDetails);
                }*/
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return resultVec;
    }
    public String getEsiid(String tdspType) throws PricingException
    {
        String esiid = "";
        Session objSession = null;
        CallableStatement cstmnt = null;
        ResultSet rs = null;
        List objList = null;
        try
        {
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            String query = "";
            if(tdspType.equalsIgnoreCase("CenterPoint"))
            {
                query = "Select esiid from tbl_TypicalDayProfile_cp";
            }
            else if(tdspType.equalsIgnoreCase("Oncor"))
            {
                query = "Select esiid from tbl_TypicalDayProfile_Oncor";
            }
            else
            {
                query = "Select esiid from tbl_TypicalDayProfile_AEP";
            }
            Query objQuery = objSession.createSQLQuery(query);
            Iterator itr = objQuery.list().iterator();
            while(itr.hasNext())
            {
               // Object[] innerRow = (Object[]) itr.next();
                esiid = String.valueOf(itr.next());
            }
            objSession.getTransaction().commit();
        }
        catch(Exception e)
        {
            objSession.getTransaction().rollback();
            e.printStackTrace();
            throw new PricingException(e.toString());
        }
        finally
        {
            objSession.close();
        }
        return esiid;
    }
    
    public Hashtable getFlowDate(String esiid) throws PricingException
    {
        String minflowDate = "";
        String maxflowDate = "";
        Hashtable htResult = new Hashtable();
        //DBConnection objDBConnection = null;
        Session objSession = null;
        CallableStatement cstmnt = null;
        ResultSet rs = null;
        try
        {
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            cstmnt = objSession.connection().prepareCall("{call sp_IntervalDetailAvailableDateRange (?)}");
            cstmnt.setString(1, esiid);
           /* objDBConnection = DBConnectionFactory.getConnection();
            Hashtable ht = new Hashtable();
            ht.put("esiid",esiid);
            String getEsiid="{call sp_IntervalDetailAvailableDateRange(#esiid#)}";
            ResultSet rs=objDBConnection.executeStatement(getEsiid,ht);
            //ResultSet rs = objDBConnection.executeStatement("select isnull(min(flowdate), getdate()) minflowDate, isnull(max(flowdate), getdate()) maxflowDate from tbl_typicalDayprofile where esiid = #esiid# and insertdate = (select max(insertdate) from tbl_typicalDayprofile where esiid = #esiid#)",ht);*/
            rs = cstmnt.executeQuery();
           
            SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
            if(rs.next())
            {
            	if((rs.getDate("minflowDate") != null) && (rs.getDate("maxflowDate")) != null)
            	{
            		minflowDate = sdf.format(rs.getDate("minflowDate"));
            		maxflowDate = sdf.format(rs.getDate("maxflowDate"));
            	}
            }   
            objSession.getTransaction().commit();

            cstmnt.close();
            rs.close();
        }
        catch(Exception e)
        {
            objSession.getTransaction().rollback();
            throw new PricingException(e.toString());
        }
        finally
        {
           
            objSession.close();
        }
        htResult.put("minflowDate",minflowDate);
        htResult.put("maxflowDate",maxflowDate);

        return htResult;
    }
    
    public static void main(String args[])
    {
        
        IDRProfilerManger obj = new IDRProfilerManger();
       // int option = 3;
       // Vector v = obj.getImportedData("centerpoint");
        Vector vec = new Vector();
        /*Hashtable ht = new Hashtable();
         
         
         // System.out.println("*********** :"+v.size());
          */        try
          {
             // Vector vecIntervalProfileSummary = obj.getDayProfileByIntervalSummary("Interval","BUSLOLF_COAST","Apr-29-2007","May-05-2007");
             // String result = obj.getChartForDayProfile(vecIntervalProfileSummary,"BUSLOLF_COAST","Apr-29-2007","May-05-2007",null,"Hourly Profile Graph for : ");
              
              
              /* switch(option)
               {
               case 1:
               Vector vec = obj.getDayProfileByIntervalSummary("interval","1008901000153480017100","","");
               break;
               case 2:
               vec = obj.getDayProfileByIntervalSummary("date","1008901000153480017100","Dec-12-2005","Dec-13-2005");
               break;
               case 3:
               */
               vec = obj.getTypicalDayHourlyProfileDataByESIID("1008901010186262413100","All");
               String s[] =  obj.getChartForTypicalDayHourlyProfileDataByESIID(vec, "1008901010186262413100",null);
               System.out.println("************* "+ s[0]);
               
              /* 
               * break;
               * case 4:
               ht = obj.getDayProfileForAnYear("1008901000153480017100"); 
               break;
               
               case 5: 
               vec = obj.getTypicalDayProfileDataByESIID("1008901000153480017100");
               break;
               case 6:
               obj.importFile("10443720006320693","//win2001/win-d/DataAutomation/EC0DE23C54041E756AD80385712DA73Foncur.txt","oncur","admin");
               break;
               
               case 7:
               obj.getFlowDate("10443720006320693");
               break;
               case 8:
               ht=obj.getDayProfileForAnYear("BUSIDRRQ_COAST");
               obj.getBarChartForProfileByYear(ht,"BUSIDRRQ_COAST",null,"Monthly Profile for : ");
               break;
               
               }*/
          }
          catch (Exception e)
          {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
          
          
    }

}

/***
$Log: IDRProfilerManger.java,v $
Revision 1.2  2007/12/12 10:00:32  tannamalai
interval removed from weekly graph

Revision 1.1  2007/12/07 06:18:35  jvediyappan
initial commit.

Revision 1.9  2007/12/03 06:50:49  tannamalai
aep sheet name problm handled

Revision 1.8  2007/11/29 11:59:10  tannamalai
aep import changed from csv to xl

Revision 1.7  2007/11/29 10:20:28  tannamalai
new column(yr) added

Revision 1.6  2007/11/28 11:53:25  tannamalai
tdp error handled

Revision 1.5  2007/11/28 09:58:40  tannamalai
*** empty log message ***

Revision 1.4  2007/11/27 11:54:32  tannamalai
*** empty log message ***

Revision 1.3  2007/11/27 07:27:04  tannamalai
null pointer excption handled

Revision 1.2  2007/11/26 10:45:33  tannamalai
*** empty log message ***

Revision 1.1  2007/11/26 05:33:55  tannamalai
latest changes for tdp

Revision 1.8  2007/10/26 09:06:50  amahesh
removed system.out.println statements

Revision 1.7  2007/07/24 05:05:33  amahesh
Added BuildConfig.DMODE

Revision 1.6  2007/07/17 08:32:09  amahesh
added BuildConfig.DMODE

Revision 1.5  2007/07/11 10:37:10  amahesh
Added one else statement for if condition for 'AEP'

Revision 1.4  2007/06/05 11:09:39  amahesh
changed Kwh to kWh

Revision 1.3  2007/05/25 09:10:38  amahesh
Add 2 Methods for Bar Chart

Revision 1.1  2007/05/25 15:17:39  Aswini Mahesh
Created 2 methods for BarChart 


***/