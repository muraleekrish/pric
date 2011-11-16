/*
 * 
 * SummaryChart.java    Aug 6, 2007
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import com.savant.pricing.transferobjects.PriceRunResultDetails;

/**
 * 
 */
public class SummaryChart
{
    public String priceChart(HttpSession session, List lstPrice, boolean far, String type,PrintWriter pw)
    {
        final CategoryDataset dataset = createDataset(lstPrice, far, type);
        final JFreeChart chart = createChart(dataset);
        String filename = "";
        try
        {
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            filename = ServletUtilities.saveChartAsJPEG(chart, 600, 345, info, session);
            ChartUtilities.writeImageMap(pw, filename, info, new StandardToolTipTagFragmentGenerator(), null);
            pw.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return filename;
    }

    public CategoryDataset createDataset(List lstPrice, boolean far,String type)
    {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
        Iterator it = lstPrice.iterator();
        while (it.hasNext())
        {
            PriceRunResultDetails objPriceRunResultDetails = (PriceRunResultDetails) it.next();
            Date runDate = objPriceRunResultDetails.getRunDateTime();
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
                result.addValue(new Double(price), "", sdf.format(runDate));
        }
        return result;
    } 

    private JFreeChart createChart(final CategoryDataset dataset)
    {
        // create the chart...
        final CategoryAxis categoryAxis = new CategoryAxis("RunDate");
        final ValueAxis valueAxis = new NumberAxis("$/MWh");
        final CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis, new LayeredBarRenderer());
        plot.setOrientation(PlotOrientation.VERTICAL);
        final JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        chart.setBackgroundPaint(Color.white);
        final LayeredBarRenderer renderer = (LayeredBarRenderer) plot.getRenderer();
        renderer.setSeriesBarWidth(0, 0.5);
        Paint p1 = new GradientPaint(0.0f, 0.0f, new Color(51, 153, 51), 0.0f, 0.0f, new Color(160, 245, 160));
        renderer.setSeriesPaint(0, p1);
        renderer.setSeriesPaint(4, p1);
        renderer.setSeriesPaint(8, p1);
        TextTitle chartTitle = new TextTitle();
        chartTitle.setText("Price Summary");
        chartTitle.setFont(new Font("Arial", Font.BOLD, 12));
        chart.setTitle(chartTitle);
        Paint pBack = new GradientPaint(0.0f, 0.0f, new Color(249, 254, 249), 0.0f, 0.0f, new Color(249, 254, 249));
        chart.setBackgroundPaint(pBack);
        //chart.setBackgroundPaint(new Color(0xFF, 0xFF, 0xFF));
        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        plot.setNoDataMessage(" No data available");
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator("{1} : {2} $/MWh", NumberFormat.getInstance()));
        return chart;
    }
}
/*
 *$Log: SummaryChart.java,v $
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
 *Revision 1.7  2007/08/13 07:22:03  spandiyarajan
 **** empty log message ***
 *
 *Revision 1.6  2007/08/10 12:11:47  jnadesan
 *chart size changed
 *
 *Revision 1.5  2007/08/10 12:06:00  jnadesan
 *tool tip message changed
 *
 *Revision 1.4  2007/08/10 11:49:41  jnadesan
 *chart design changed
 *
 *Revision 1.3  2007/08/10 10:21:57  jnadesan
 *line chart added
 *
 *Revision 1.2  2007/08/07 05:24:53  jnadesan
 *date alignment changed
 *
 *Revision 1.1  2007/08/06 15:55:36  jnadesan
 *initial commit for history page
 *
 *
 */