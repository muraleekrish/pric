/*
 * Created on Mar 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.common.chart;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.imagemap.StandardToolTipTagFragmentGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import com.savant.pricing.calculation.valueobjects.ForwardCurveBlockVO;
import com.savant.pricing.calculation.valueobjects.ForwardCurveProfileVO;
import com.savant.pricing.dao.ForwardCurveBlockDAO;
import com.savant.pricing.dao.ForwardCurveProfileDAO;
import com.savant.pricing.dao.LoadProfileTypesDAO;
import com.savant.pricing.dao.PriceBlockDAO;
import com.savant.pricing.dao.WeatherZonesDAO;
import com.savant.pricing.valueobjects.LoadProfileTypesVO;
import com.savant.pricing.valueobjects.WeatherZonesVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FulcrumCurve {
     
    private Date maximumDate = null;
    public  String getchart(HttpSession session,int zoneid,String zoneName,PrintWriter pw) {
        
        final XYDataset dataset = createDataset(zoneid);
        final JFreeChart chart = createChart(dataset,zoneName);
        String filename="";
        try{
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            filename = ServletUtilities.saveChartAsJPEG(chart, 550, 330 , info, session);
            //Write the image map to the PrintWriter
            ChartUtilities.writeImageMap(pw, filename, info,new StandardToolTipTagFragmentGenerator(),null);
            pw.flush();
        }catch (Exception e) {
            
            e.printStackTrace();
        }
        return filename;
    }
    public String getProfileChart(HttpSession session,int zoneId,int profileId,PrintWriter pw)
    {
        String zoneName = "";
        String profileName = "";
        
        WeatherZonesDAO objWeatherZonesDAO = new WeatherZonesDAO();
        List lstZone = objWeatherZonesDAO.getAllWeatherZones();
        Iterator iteZone = lstZone.iterator();
        while(iteZone.hasNext())
        {
            WeatherZonesVO objWeatherZonesVO = (WeatherZonesVO)iteZone.next();
            if(objWeatherZonesVO.getWeatherZoneId()==(zoneId))
            {
                zoneName = objWeatherZonesVO.getWeatherZone();
            }
        }
        LoadProfileTypesDAO objLoadProfileTypesDAO = new LoadProfileTypesDAO();
        List lstProfile = objLoadProfileTypesDAO.getAllProfileTypes();
        Iterator iteProfile = lstProfile.iterator();
        while(iteProfile.hasNext())
        {
            LoadProfileTypesVO objLoadProfileTypesVO = (LoadProfileTypesVO)iteProfile.next();
            if(objLoadProfileTypesVO.getProfileIdentifier()==(profileId))
            {
                profileName = objLoadProfileTypesVO.getProfileType();
            }
        }
        
        final XYDataset dataset = createProfileDataset(zoneId,profileId);
        final JFreeChart chart = createProfileChart(dataset,zoneName,profileName);
        String filename="";
        try{
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            filename = ServletUtilities.saveChartAsJPEG(chart, 520, 330 , info, session);
            //Write the image map to the PrintWriter
            ChartUtilities.writeImageMap(pw, filename, info, new StandardToolTipTagFragmentGenerator(), null);
            pw.flush();
        }catch (Exception e) {
            
            e.printStackTrace();
        }
        return filename;
        
    }
    
    private XYDataset createProfileDataset(int zoneid,int profileId) {
        
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        String blckName = "";
        TimeSeries series1 = null;
        
        ForwardCurveProfileDAO objForwardCurveProfileDAO = new ForwardCurveProfileDAO();
        List lstProfile = (List)objForwardCurveProfileDAO.getAllForwardCurveProfiles(zoneid,profileId,1);
        Iterator itr = lstProfile.iterator();
        series1 = new TimeSeries(blckName, Month.class);
        while(itr.hasNext())
        {
            ForwardCurveProfileVO objForwardCurveProfileVO = (ForwardCurveProfileVO)itr.next();
            series1.addOrUpdate(new Month(objForwardCurveProfileVO.getMonthYear()), objForwardCurveProfileVO.getPrice());
            this.maximumDate = objForwardCurveProfileVO.getMonthYear();
        }
        dataset.addSeries(series1);
        dataset.setDomainIsPointsInTime(true);
        return dataset;
    }
     
    
    private XYDataset createDataset(int zoneid) {
        
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries series1 = null;
        
        ForwardCurveBlockDAO objForwardCurveBlockDAO = new ForwardCurveBlockDAO();
        HashMap hmforEnergyPrice = objForwardCurveBlockDAO.getAllForwardCurveBlocks(zoneid,1);
        PriceBlockDAO objPriceBlockDAO = new PriceBlockDAO();
        
        Set keySet = hmforEnergyPrice.keySet();
        Iterator iteBlck = keySet.iterator();
        while(iteBlck.hasNext())
        {
            int prcId = ((Integer)iteBlck.next()).intValue();
            List lstprice = (List)hmforEnergyPrice.get(new Integer(prcId));
            if(null != lstprice)
            {
                Iterator itr = lstprice.iterator();
                series1 = new TimeSeries(objPriceBlockDAO.getpriceBlck(prcId).getPriceBlock(), Month.class);
                while(itr.hasNext())
                {
                    ForwardCurveBlockVO objForwardCurveBlockVO = (ForwardCurveBlockVO)itr.next();
                    System.out.println(" objForwardCurveBlockVO.getMonthYear() "+objForwardCurveBlockVO.getMonthYear()+" Prod Id: "+objForwardCurveBlockVO.getPrice());
                    series1.addOrUpdate(new Month(objForwardCurveBlockVO.getMonthYear()), objForwardCurveBlockVO.getPrice());
                    this.maximumDate = objForwardCurveBlockVO.getMonthYear();
                }
                dataset.addSeries(series1);
                dataset.setDomainIsPointsInTime(true);
            }
        }
        return dataset;
    }
     
    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset for the chart.
     *
     * @return A sample chart.
     */
    private JFreeChart createProfileChart(final XYDataset dataset,String zoneName,String profileName) {
        
        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "",
                "Month-Year", "Price($/MWh)",
                dataset,
                false,
                true,
                false
        );
        
        chart.setBackgroundPaint(Color.white);
        TextTitle title = new TextTitle(zoneName+" energy prices for "+profileName );
        title.setPaint(Color.BLUE);
        chart.setTitle(title);
        
        DateFormat dateformat = new SimpleDateFormat("MMM-yy");
        StandardXYToolTipGenerator tooltip = new StandardXYToolTipGenerator("{1}, {2}",dateformat,NumberFormat.getInstance());
       
        final XYPlot plot = chart.getXYPlot();
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setDomainCrosshairVisible(false);
        plot.setRangeCrosshairVisible(false);
        plot.setNoDataMessage("No Data Available");
        plot.setWeight(200);
        XYItemRenderer render = plot.getRenderer();
        render.setToolTipGenerator(tooltip);
        Paint pBack = new GradientPaint(0.0f, 0.0f, new Color(249, 254, 249), 0.0f, 0.0f, new Color(249, 254, 249));
        chart.setBackgroundPaint(Color.WHITE);
        final ValueAxis axis2 = plot.getRangeAxis();
        
        final DateAxis axis = (DateAxis) plot.getDomainAxis();
        GradientPaint gp2 = new GradientPaint(
                0.0f, 0.0f, new Color(196, 0, 0), 
                0.0f, 0.0f, new Color( 196, 0, 0)
        );
        axis.setLabelPaint(gp2);
        axis2.setLabelPaint(gp2);
        axis.setDateFormatOverride(new SimpleDateFormat("MMM-yy"));
        if(this.maximumDate!=null)
        axis.setMaximumDate(this.maximumDate);
        return chart;
    }
    private JFreeChart createChart(final XYDataset dataset,String message) {
        
        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "",
                "Month-Year", "Price($/MWh)",
                dataset,
                true,
                true,
                false
        );
        
        chart.setBackgroundPaint(Color.white);
        TextTitle title = new TextTitle(message+" energy prices for all price blocks" );
        title.setPaint(Color.BLUE);
        chart.setTitle(title);
        DateFormat dateformat = new SimpleDateFormat("MMM-yy");
        StandardXYToolTipGenerator tooltip = new StandardXYToolTipGenerator(StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,dateformat,NumberFormat.getInstance());
        final XYPlot plot = chart.getXYPlot();
        Paint pBack = new GradientPaint(0.0f, 0.0f, new Color(249, 254, 249), 0.0f, 0.0f, new Color(249, 254, 249));
        chart.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setDomainCrosshairVisible(false);
        plot.setRangeCrosshairVisible(false);
        plot.setNoDataMessage("No Data Available");
        plot.setWeight(200);
        XYItemRenderer render = plot.getRenderer();
        render.setToolTipGenerator(tooltip);
        
        final ValueAxis axis2 = plot.getRangeAxis();
        
        final DateAxis axis = (DateAxis) plot.getDomainAxis();
       
        GradientPaint gp0 = new GradientPaint(
                0.0f, 0.0f, Color.CYAN, 
                0.0f, 0.0f, new Color(0, 0, 64)
            );
            GradientPaint gp1 = new GradientPaint(
                0.0f, 0.0f, Color.green, 
                0.0f, 0.0f, new Color(0, 64, 0)
            );
            GradientPaint gp2 = new GradientPaint(
                    0.0f, 0.0f, Color.MAGENTA, 
                    0.0f, 0.0f, new Color(64, 0, 0)
            );
            GradientPaint gp3 = new GradientPaint(
                0.0f, 0.0f, Color.red, 
                0.0f, 0.0f, new Color(64, 0, 0)
            );
            GradientPaint gp4 = new GradientPaint(
                    0.0f, 0.0f, Color.blue,
                    0.0f, 0.0f, new Color(64, 0, 0)
                );
            render.setSeriesPaint(0, gp0);
            render.setSeriesPaint(1, gp1);
            render.setSeriesPaint(2, gp2);
            render.setSeriesPaint(3, gp3);
            render.setSeriesPaint(4, gp4);
               
        axis.setLabelPaint(gp3);
        axis2.setLabelPaint(gp3);
        axis.setDateFormatOverride(new SimpleDateFormat("MMM-yy"));
        if(maximumDate!=null)
        axis.setMaximumDate(this.maximumDate);
        return chart;
    }
    public static void main(String[] args) {
        FulcrumCurve curve = new FulcrumCurve();
        curve.createDataset(1);
    }
}
