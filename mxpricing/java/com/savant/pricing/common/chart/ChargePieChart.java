/*
 * Created on Feb 28, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.common.chart;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.imagemap.StandardToolTipTagFragmentGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/** 
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ChargePieChart {

    public  String chart(HttpSession session,HashMap pievalues,PrintWriter pw) {

        final PieDataset dataset =createDataset(pievalues);
        final JFreeChart chart = createChart(dataset);
        String filename="";
        try{
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());

            filename = ServletUtilities.saveChartAsJPEG(chart, 270, 237 , info, session);
            //Write the image map to the PrintWriter
            ChartUtilities.writeImageMap(pw, filename, info,new StandardToolTipTagFragmentGenerator(),null);
            pw.flush();
        }catch (Exception e) {

            e.printStackTrace();
        }
        return filename;
    }
    private PieDataset createDataset(HashMap pievalue) {
        final DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Cost", (Double)pievalue.get("Fixed"));
        dataset.setValue("TDSP", (Double)pievalue.get("TDSPCharge"));
        dataset.setValue("Sales Fee", (Double)pievalue.get("sales"));
        dataset.setValue("Margin",(Double)pievalue.get("margin"));
        return dataset;        
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset for the chart.
     *
     * @return A sample chart.
     */
    private JFreeChart createChart(final PieDataset  dataset) {

        JFreeChart chart = ChartFactory.createPieChart("",dataset,false,true,false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("Arial", Font.PLAIN, 9));
        plot.setNoDataMessage("No data available");
        plot.setCircular(true);
        plot.setLabelGap(0.00000001);
        plot.setLabelLinkMargin(0.000000001);
        
        TextTitle chartTitle = new TextTitle();
        chartTitle.setText("Distribution of Cost Elements Before Taxes");
        chartTitle.setFont(new Font("Arial", Font.BOLD,11));
        chart.setTitle(chartTitle);
        //Paint pBack = new GradientPaint(0.0f, 0.0f, new Color(249, 254, 249), 0.0f, 0.0f, new Color(249, 254, 249));
        chart.setBackgroundPaint(Color.WHITE);
        chart.setBackgroundPaint(new Color(0xFF, 0xFF, 0xFF));
        
        return chart;
    }
}
