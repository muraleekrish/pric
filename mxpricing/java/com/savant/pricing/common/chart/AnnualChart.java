/*
 * Created on Feb 19, 2007
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.imagemap.StandardToolTipTagFragmentGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.savant.pricing.transferobjects.AnnualEnergyDetails;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
 
public class AnnualChart   {
    
    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public  String annualchart(HttpSession session,Vector vecAnnualDetails,PrintWriter pw) {
        final CategoryDataset dataset = createDataset(vecAnnualDetails);
        final JFreeChart chart = createChart(dataset);
        String filename="";
        
        try{
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            
            filename = ServletUtilities.saveChartAsJPEG(chart, 400, 224 , info, session);
            ChartUtilities.writeImageMap(pw, filename, info,new StandardToolTipTagFragmentGenerator(),null);
            pw.flush();
        }catch (Exception e) {
            
            e.printStackTrace();
        }
        return filename;
    }
    
    public CategoryDataset createDataset(Vector vecAnnualDetails) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        Calendar objCalendar = Calendar.getInstance();
        for (Iterator it = vecAnnualDetails.iterator(); it.hasNext();) {
            AnnualEnergyDetails element = (AnnualEnergyDetails) it.next();
            objCalendar.clear();
            objCalendar.set(Calendar.MONTH,element.getMonth()-1);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM");
            
            if(element.getUsagekWh()!=0)
            {
                result.addValue(Double.parseDouble(element.getUsagekWh()+""), "",sdf.format(objCalendar.getTime()) );
            }
        }
        return result;
    }
    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset for the chart.
     *
     * @return A sample chart.
     */
    private JFreeChart createChart(final CategoryDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createStackedBarChart(
                "",  // chart title
                "",                  // domain axis label
                "kWh",                     // range axis label
                dataset,                     // data
                PlotOrientation.VERTICAL,    // the plot orientation
                false,                        // legend
                true,                        // tooltips
                false                        // urls
        );
        GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
        KeyToGroupMap map = new KeyToGroupMap("G1");
        map.mapKeyToGroup("Annual Load", "G1");
        renderer.setSeriesToGroupMap(map);
        Paint p1 = new GradientPaint(
                0.0f, 0.0f,new Color(51, 153, 51), 0.0f, 0.0f,   new Color(160, 245, 160)
        );
        renderer.setSeriesPaint(0, p1);
        renderer.setSeriesPaint(4, p1);
        renderer.setSeriesPaint(8, p1);
        renderer.setBase(1);
        chart.getCategoryPlot().getRangeAxis().setLowerBound(0.0);
        StandardCategoryToolTipGenerator tooltip =new StandardCategoryToolTipGenerator("{2} kWh",NumberFormat.getIntegerInstance());
        renderer.setToolTipGenerator(tooltip);
        TextTitle chartTitle = new TextTitle();
        chartTitle.setText("AVERAGE ANNUAL LOAD PROFILE FOR ESI ID(s)");
        
        chartTitle.setFont(new Font("Arial", Font.BOLD,11));
        chart.setTitle(chartTitle);
        Paint pBack = new GradientPaint(0.0f, 0.0f, new Color(249, 254, 249), 0.0f, 0.0f, new Color(249, 254, 249));
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setRenderer(renderer);
        plot.setNoDataMessage(" No data available");
        return chart;
    }
    
}

/*
*$Log:
*
*
*/