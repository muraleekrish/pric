/*
 * Created on Mar 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.common.chart;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.PrintWriter;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.imagemap.StandardToolTipTagFragmentGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;

import com.savant.pricing.common.NumberUtil;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PICChart {
    
    public  HashMap chart(HttpSession session,List kw,List kWh,List kVA,List noOfdays,List readDayofmonth,PrintWriter pw) {
        
        final JFreeChart chartdays = createChartdays(noOfdays,readDayofmonth);
        final JFreeChart chartkw = createChartkwkVA(kw,kVA,noOfdays,kWh);
        final JFreeChart chartkwh = createChartkwkVA(kWh);
        HashMap hmchart = new HashMap();
        
        String filenamedays="";
        String filenamekW="";
        String filenamekWh="";
        try{
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            filenamedays = ServletUtilities.saveChartAsJPEG(chartdays, 310, 210 , info, session);
            //Write the image map to the PrintWriter
            ChartUtilities.writeImageMap(pw, filenamedays, info,new StandardToolTipTagFragmentGenerator(),null);
            pw.flush();
            hmchart.put("chartdays",filenamedays);
            
          info = new ChartRenderingInfo(new StandardEntityCollection());
            filenamekW = ServletUtilities.saveChartAsJPEG(chartkw, 310, 210 , info, session);
            //Write the image map to the PrintWriter
            ChartUtilities.writeImageMap(pw, filenamekW, info,new StandardToolTipTagFragmentGenerator(),null);
            pw.flush();
            hmchart.put("chartkW",filenamekW);
            
            info = new ChartRenderingInfo(new StandardEntityCollection());
            filenamekWh = ServletUtilities.saveChartAsJPEG(chartkwh, 310, 210 , info, session);
            //Write the image map to the PrintWriter
            ChartUtilities.writeImageMap(pw, filenamekWh, info,new StandardToolTipTagFragmentGenerator(),null);
            pw.flush();
            hmchart.put("chartkWh",filenamekWh);
        }catch (Exception e) {
            
            e.printStackTrace();
        }
        return hmchart;
    }
    private CategoryDataset createDataset1(List noOfDays,List dayInMonth) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Format formatter = new SimpleDateFormat("MMM");
        String strdate = "";
        Date date = new Date("1/1/2007");
        int i = 0;
        if(noOfDays.size()>0)
        while( i < 12)
        {
            date.setMonth(i);
            strdate = formatter.format(date);
            dataset.addValue(((Integer)noOfDays.get(i)).intValue(),"Days in Billing period",strdate);
            if(dayInMonth.get(i)!=null)
            dataset.addValue(0,"Read Day of Month",strdate);
            i++;
        }
        return dataset;
    }
    private CategoryDataset createDataset(List kw,String strval) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Iterator ite = kw.iterator();
        Format formatter = new SimpleDateFormat("MMM");
        String strdate = "";
        String str = strval;
        Date date = new Date("1/1/2007");
        int i =0;
        while(ite.hasNext())
        {
            float value = ((Float)ite.next()).floatValue();
            date.setMonth(i++);
            strdate = formatter.format(date).toString();
            if(value!=0)
            dataset.addValue(value,str,strdate);
        }
        
        return dataset;
    }
    private CategoryDataset createDatasetkw(List kVA,List kw ) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Format formatter = new SimpleDateFormat("MMM");
        String strdate = "";
        Date date = new Date("1/1/2007");
        int i = 0;
        while( i < 12)
        {
            date.setMonth(i);
            strdate = formatter.format(date).toString();
            if(((Float)kVA.get(i)).floatValue()!=0)
            dataset.addValue(((Float)kVA.get(i)).floatValue(),"peak kVA",strdate);
            if(((Float)kw.get(i)).floatValue()!=0)
            dataset.addValue(((Float)kw.get(i)).floatValue(),"peak kW",strdate);
            i++;
        }
        return dataset;
    }
    private CategoryDataset createDatasetloadfactor(List noOfDays,List kwh,List kw) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Format formatter = new SimpleDateFormat("MMM");
        String strdate = "";
        Date date = new Date("1/1/2007");
        for(int i =0;i<12;i++)
        {
            date.setMonth(i);
            strdate = formatter.format(date).toString();
            float floatkwh =((Float)kwh.get(i)).floatValue();
            float floatkw =((Float)kw.get(i)).floatValue();
            int intnoOfDays =((Integer)noOfDays.get(i)).intValue();
            float loadfactor = ((floatkwh*100)/(floatkw*intnoOfDays*24));
            if(floatkwh == 0.0 || floatkw == 0.0 || intnoOfDays == 0 )
            {
             //   dataset.addValue(0.0,"Load Factor",strdate);
            }
            else
                dataset.addValue(loadfactor,"Load Factor",strdate);
            
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
    
    private JFreeChart createChartdays(List noofDays,List dayInMonth) {
        final CategoryDataset dataset1 = createDataset1(noofDays,dayInMonth);
        
        final CategoryAxis categoryAxis = new CategoryAxis(" Month");
        final ValueAxis valueAxis = new NumberAxis( "Billing Days & Read Day");
        valueAxis.setLabelFont(new Font("", Font.PLAIN, 10));
        
        final CategoryPlot plot = new CategoryPlot(dataset1,
                categoryAxis, 
                valueAxis, 
                new LayeredBarRenderer());
        plot.setOrientation(PlotOrientation.VERTICAL);
        final JFreeChart chart = new JFreeChart(
                "", 
                JFreeChart.DEFAULT_TITLE_FONT, 
                plot, 
                true
        );
        chart.setBackgroundPaint(Color.white);
        
        final LayeredBarRenderer renderer = (LayeredBarRenderer) plot.getRenderer();
        renderer.setSeriesBarWidth(0,0.8);
        renderer.setSeriesBarWidth(1,0.5);
        
        //plot.setBackgroundPaint(new Color(249, 254, 249));
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        plot.mapDatasetToRangeAxis(1, 0);
        
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        plot.setNoDataMessage(" No data available");
        
        
        final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
        plot.setRenderer(1, renderer2,false);
        plot.getRenderer().setToolTipGenerator(new StandardCategoryToolTipGenerator("{0}={2}",NumberFormat.getIntegerInstance()));
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        
        return chart;
        
    }
    private JFreeChart createChartkwkVA(List kw,List kVA,List noOfDays,List kWh) {
        final CategoryDataset datasetkVA = createDatasetkw(kVA,kw);
        final CategoryAxis categoryAxis = new CategoryAxis(" Month");
        final ValueAxis valueAxis = new NumberAxis("peak kVA & peak kW");
        
        final CategoryPlot plot = new CategoryPlot(datasetkVA,
                categoryAxis, 
                valueAxis, 
                new LayeredBarRenderer());
        plot.setOrientation(PlotOrientation.VERTICAL);
        final JFreeChart chart = new JFreeChart(
                "", 
                JFreeChart.DEFAULT_TITLE_FONT, 
                plot, 
                true
        );
        chart.setBackgroundPaint(Color.white);
        
        final LayeredBarRenderer renderer = (LayeredBarRenderer) plot.getRenderer();
        renderer.setSeriesBarWidth(0,1.0);
        renderer.setSeriesBarWidth(1,.5);
        renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator("{0}={2}",NumberUtil.doubleFraction()));
        
        plot.setBackgroundPaint(new Color(249, 254, 249)); 
        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        
        final CategoryDataset datasetkw = createDatasetloadfactor(noOfDays,kWh,kw);
        plot.setDataset(1, datasetkw);
        plot.setAnchorValue(0.1);
        plot.mapDatasetToRangeAxis(1, 1);
        plot.setNoDataMessage(" No data available");
        
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        final ValueAxis axis2 = new NumberAxis("Load Factor");
        //axis2.setLabelAngle();
        axis2.setLabelAngle(9.4);
        axis2.setLabelInsets(new RectangleInsets(0, 5, 0, 5));
        plot.setRangeAxis(1, axis2);
        plot.setWeight(2);
        axis2.setAutoTickUnitSelection(true);
        axis2.setLowerMargin(0.0);
        final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
        //renderer2.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        GradientPaint gp2 = new GradientPaint(
                0.0f, 0.0f, new Color(0, 196, 00), 
                0.0f, 0.0f, new Color(0, 196, 00)
            );
        renderer2.setSeriesPaint(1, gp2);
        renderer2.setToolTipGenerator(new StandardCategoryToolTipGenerator("{2}%",NumberUtil.doubleFraction()));
        plot.setRenderer(1, renderer2,true);
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        
        return chart;
        
    }
    private JFreeChart createChartkwkVA(List kwh) {
        final CategoryDataset datasetkWh = createDataset(kwh,"kWh");
        
        // create the chart...
        final CategoryAxis categoryAxis = new CategoryAxis(" Month");
        final ValueAxis valueAxis = new NumberAxis("kWh");
        
        
        final CategoryPlot plot = new CategoryPlot(datasetkWh,
                categoryAxis, 
                valueAxis, 
                new LayeredBarRenderer());
        plot.setOrientation(PlotOrientation.VERTICAL);
        final JFreeChart chart = new JFreeChart(
                "", 
                JFreeChart.DEFAULT_TITLE_FONT, 
                plot, 
                true
        );
        chart.setBackgroundPaint(new Color(249, 254, 249));
        
        final LayeredBarRenderer renderer = (LayeredBarRenderer) plot.getRenderer();
        renderer.setSeriesBarWidth(0,0.5);
        GradientPaint gp2 = new GradientPaint(
                0.0f, 0.0f, new Color(196, 0, 196), 
                0.0f, 0.0f, new Color(196, 0, 196)
            );
        renderer.setSeriesFillPaint(0,gp2);
        renderer.setBaseFillPaint(gp2);
        //  final CategoryPlot plot = chart.getCategoryPlot();
        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        
        plot.setNoDataMessage(" No data available");
        
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        
         renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator("{2} kWh",NumberFormat.getIntegerInstance()));
        
        
        
        return chart;
        
    }
}
