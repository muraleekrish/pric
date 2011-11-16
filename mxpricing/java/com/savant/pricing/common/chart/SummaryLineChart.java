/*
 * 
 * SummaryLineChart.java    Aug 7, 2007
 * 
 * Copyright (c) Savant Technologies Pvt Ltd.
 * 'Savant House' 127 Chamiers Road, Nandanam, Chennai-600 035. INDIA 
 * All rights reserved.

 * @author: jnadesan
 * @company: Savant Technologies
 * @client: MX Energy
 * @version: 
 * @Description: 
 * 
 */
package com.savant.pricing.common.chart;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
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
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import com.savant.pricing.transferobjects.PriceRunResultDetails;

/**
 * 
 */
public class SummaryLineChart
{ 
    double maxvalue = 0;
    public String priceLineChart(HttpSession session, List lstPrice, boolean far, String type, PrintWriter pw)
    {
        final JFreeChart chart = createChart(createDataset(lstPrice,far,type));
        String filename = "";
        try
        {
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            filename = ServletUtilities.saveChartAsJPEG(chart, 600, 350, info, session);
            ChartUtilities.writeImageMap(pw, filename, info, new StandardToolTipTagFragmentGenerator(), null);
            pw.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return filename; 
    } 

    private JFreeChart createChart(XYDataset xydataset)
    {
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("", "Date", "$/MWh", xydataset, false, true, false);
        jfreechart.setBackgroundPaint(Color.white);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);
        TextTitle chartTitle = new TextTitle();
        chartTitle.setText("Price Summary");
        chartTitle.setFont(new Font("Arial", Font.BOLD, 12));
        jfreechart.setTitle(chartTitle);
        org.jfree.chart.renderer.xy.XYItemRenderer xyitemrenderer = xyplot.getRenderer();
        if(xyitemrenderer instanceof XYLineAndShapeRenderer)
        {
            XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyitemrenderer;
            xylineandshaperenderer.setDefaultShapesVisible(true);
            xylineandshaperenderer.setDefaultShapesFilled(true);
            xylineandshaperenderer.setToolTipGenerator(new StandardXYToolTipGenerator("{1} : {2} $/MWh",new SimpleDateFormat("MM-dd-yy"),NumberFormat.getInstance()));
            
        }
        xyplot.setRangeZeroBaselineVisible(true);
        Paint objpaint = new GradientPaint(0.0f, 0.0f, new Color(51, 153, 51), 0.0f, 0.0f, new Color(160, 245, 160));
        xyitemrenderer.setSeriesPaint(0,objpaint);
        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
        dateaxis.setDateFormatOverride(new SimpleDateFormat("MM-dd-yy"));
        dateaxis.setTickLabelsVisible(true);
        dateaxis.setAxisLineVisible(true); 
        Paint pBack = new GradientPaint(0.0f, 0.0f, new Color(249, 254, 249), 0.0f, 0.0f, new Color(249, 254, 249));
        jfreechart.setBackgroundPaint(pBack);
        ValueAxis valuAxis = (ValueAxis)xyplot.getRangeAxis();
        valuAxis.setLowerBound(0);
        valuAxis.setUpperBound(maxvalue+maxvalue/4);
        valuAxis.setAutoRange(false);
        return jfreechart;
    }

    private XYDataset createDataset(List lstPrice,boolean far,String type)
    {
        TimeSeries timeseries = new TimeSeries("Price History", Day.class);
        Iterator it = lstPrice.iterator();
        while (it.hasNext())
        {
            try
            {
                PriceRunResultDetails objPriceRunResultDetails = (PriceRunResultDetails) it.next();
                double price = 0;
                if(!far)
                    price = objPriceRunResultDetails.getFixedPrice();
                else if(type.equalsIgnoreCase("base"))
                {
                    price = objPriceRunResultDetails.getBaseRate();
                }
                else if(type.equalsIgnoreCase("ff"))
                {
                    price = objPriceRunResultDetails.getFuelFactor();
                }
                else if(type.equalsIgnoreCase("total"))
                {
                    price = objPriceRunResultDetails.getFuelFactor()+objPriceRunResultDetails.getBaseRate();
                }
                if(price>maxvalue)
                    maxvalue = price;
                timeseries.addOrUpdate(new Day(objPriceRunResultDetails.getRunDateTime()), new Double(price));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
        timeseriescollection.addSeries(timeseries);
        timeseriescollection.setDomainIsPointsInTime(true);
        return timeseriescollection;
    }
}  
/*
 *$Log: SummaryLineChart.java,v $
 *Revision 1.2  2008/11/21 09:46:10  jvediyappan
 *Trieagle changed as MXEnergy.
 *
 *Revision 1.1  2007/12/07 06:18:35  jvediyappan
 *initial commit.
 *
 *Revision 1.1  2007/10/30 05:51:54  jnadesan
 *Initial commit.
 *
 *Revision 1.1  2007/10/26 15:19:23  jnadesan
 *initail MXEP commit
 *
 *Revision 1.4  2007/08/10 12:11:47  jnadesan
 *chart size changed
 *
 *Revision 1.3  2007/08/10 12:06:00  jnadesan
 *tool tip message changed
 *
 *Revision 1.2  2007/08/10 11:49:41  jnadesan
 *chart design changed
 *
 *Revision 1.1  2007/08/10 10:21:57  jnadesan
 *line chart added
 *
 *
 */