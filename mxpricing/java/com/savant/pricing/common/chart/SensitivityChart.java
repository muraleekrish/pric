/*
 * Created on Feb 28, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.common.chart;

import java.awt.Color;
import java.awt.Font;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.imagemap.StandardToolTipTagFragmentGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SensitivityChart {

    public  String chart(HttpSession session,List lstsenValues,PrintWriter pw) {

        
        List values = lstsenValues;
        final XYSeriesCollection dataset = createDataset(values);
        final JFreeChart chart = createChart(dataset);
        String filename="";
        try{
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());

            filename = ServletUtilities.saveChartAsJPEG(chart, 400, 220 , info, session);
            //Write the image map to the PrintWriter
            ChartUtilities.writeImageMap(pw, filename, info,new StandardToolTipTagFragmentGenerator(),null);
            pw.flush();
        }catch (Exception e) {

            e.printStackTrace();
        }
        return filename;
    }
  
    private XYSeriesCollection createDataset(List lstDetails) {
        
        final XYSeriesCollection dataset = new XYSeriesCollection();
        final XYSeries series2 = new XYSeries("(Term,$/MWh)");
        Iterator ite = lstDetails.iterator();       
        int i = 1;
        while(ite.hasNext())
        {
            double price = ((Double)ite.next()).doubleValue();
            if(price!=0)
            series2.add(new Double(i++),new Double((price*1000)));
        }
        
        dataset.addSeries(series2);
        
        return dataset;
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset for the chart.
     *
     * @return A sample chart.
     */
  
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "",      // chart title
                "Terms",                      // x axis label
                "$/MWh",                      // y axis label
                dataset,                  // data
                PlotOrientation.VERTICAL,
                false,                     // include legend
                true,                     // tooltips
                false                      // urls
        );
        
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.WHITE);
        
        TextTitle chartTitle = new TextTitle();
        chartTitle.setText("SENSITIVITY OF PRICE TO TERM");
        chartTitle.setFont(new Font("Arial", Font.BOLD,11));
        chart.setTitle(chartTitle);
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setNoDataMessage("No Data Available");
         
        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRange(true);
        rangeAxis.setLowerBound(10);
        // OPTIONAL CUSTOMISATION COMPLETED.
        final ValueAxis objValueAxis =  plot.getDomainAxis();
        objValueAxis.setLowerBound(1);
        objValueAxis.setUpperBound(60);
        return chart;
        
    }

}
