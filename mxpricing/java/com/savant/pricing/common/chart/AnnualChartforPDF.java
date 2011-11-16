/*
 * Created on Apr 4, 2007
 * 
 * Class Name AnnualChartforPDF.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.common.chart;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.transferobjects.AnnualEnergyDetails;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AnnualChartforPDF {
    
    float maxvalue = 0;
    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public  String annualchart(HttpSession session,Vector vecAnnualDetails) {
        
        final CategoryDataset dataset = createDataset(vecAnnualDetails);
        final JFreeChart chart = createChart(dataset);
        
        String filename="";
        
        try{
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            
            File ff= null;
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
                ff = new File("E:/pricingdata/jasper/annualChart.jpeg");
            else
                ff = new File("E:/pricingdata/jasper/annualChart.jpeg");
            if(ff.exists())
                ff.delete();
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
                ChartUtilities.saveChartAsJPEG(new File("E:/pricingdata/jasper/annualChart.jpeg"),chart,400,170);
            else
                ChartUtilities.saveChartAsJPEG(new File("E:/pricingdata/jasper/annualChart.jpeg"),chart,400,170);
            //Write the image map to the PrintWriter
            PrintWriter pw= new PrintWriter(System.out);
            ChartUtilities.writeImageMap(pw, filename, info,true);
            pw.flush();
        }catch (Exception e) {
            
            e.printStackTrace();
        }
        return filename;
    }
    public  String annualMultipleCpechart(HttpSession session,Vector vecAnnualDetails) {
        
        final CategoryDataset dataset = createDataset(vecAnnualDetails);
        final JFreeChart chart = createChart(dataset);
        String filename="";
        
        try{
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            
            File ff= null;
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
                ff = new File("E:/pricingdata/jasper/annualChart.jpeg");
            else
                ff = new File("E:/pricingdata/jasper/annualChart.jpeg");
            if(ff.exists())
                ff.delete();
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
                ChartUtilities.saveChartAsJPEG(new File("E:/pricingdata/jasper/annualChart.jpeg"),chart,410,192);
            else
                ChartUtilities.saveChartAsJPEG(new File("E:/pricingdata/jasper/annualChart.jpeg"),chart,410,192);
            //Write the image map to the PrintWriter
            PrintWriter pw= new PrintWriter(System.out);
            ChartUtilities.writeImageMap(pw, filename, info,true);
            pw.flush();
        }catch (Exception e) {
            
            e.printStackTrace();
        }
        return filename;
    }
    public  String comparisionChart(HttpSession session,LinkedHashMap hmTermDetails) {
        final CategoryDataset dataset = createComparisonDataset(hmTermDetails);
        final JFreeChart chart = createComparisionChart(dataset);
        String filename="";
        try{
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            
            File ff= null;
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
                ff = new File("E:/pricingdata/jasper/TermComparison.jpeg");
            else
                ff = new File("E:/pricingdata/jasper/TermComparison.jpeg");
            if(ff.exists())
                ff.delete();
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
                ChartUtilities.saveChartAsJPEG(new File("E:/pricingdata/jasper/TermComparison.jpeg"),chart,300,160);
            else
                ChartUtilities.saveChartAsJPEG(new File("E:/pricingdata/jasper/TermComparison.jpeg"),chart,300,160);
            //Write the image map to the PrintWriter
            PrintWriter pw= new PrintWriter(System.out);
            ChartUtilities.writeImageMap(pw, filename, info,true);
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
        final CategoryAxis categoryAxis = new CategoryAxis();
        final ValueAxis valueAxis = new NumberAxis("kWh");
        valueAxis.setLabelFont(new Font("SansSerif",10,10));
        valueAxis.setTickLabelFont(new Font("SansSerif",1,8));
        
        final CategoryPlot plot = new CategoryPlot(dataset,
                categoryAxis, 
                valueAxis, 
                new LayeredBarRenderer());
        plot.setOrientation(PlotOrientation.VERTICAL);
        final JFreeChart chart = new JFreeChart(
                "Monthly kWh", 
                new Font("SansSerif",10,10), 
                plot, 
                false
        );
        //chart.setBorderVisible(true);
        chart.setBorderPaint(Color.gray);
        chart.setBackgroundPaint(Color.WHITE);
           
        final LayeredBarRenderer renderer = (LayeredBarRenderer) plot.getRenderer();
        renderer.setSeriesBarWidth(0,0.5);
        
        renderer.setSeriesPaint(0,  new Color(153, 153, 255));
        renderer.setSeriesOutlinePaint(0,new Color(0, 0, 0));
        //plot.setBackgroundPaint(new Color(248, 248, 255 )); 
        chart.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.WHITE);
        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        plot.mapDatasetToRangeAxis(1, 0);
        
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        plot.setNoDataMessage(" No data available");
        
        return chart;
       
    }
    
    
    public CategoryDataset createComparisonDataset(HashMap comparison) {
        
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        Set setTerm = comparison.keySet();
        for (Iterator it = setTerm.iterator(); it.hasNext();) {
            int term = ((Integer)it.next()).intValue();  
            result.addValue(((Float)comparison.get(new Integer(term))).floatValue(), "",new Integer(term).toString());
            if(this.maxvalue<(((Float)comparison.get(new Integer(term))).floatValue()))
            {
                maxvalue = (((Float)comparison.get(new Integer(term))).floatValue());
            }
        }
        return result;
    }
    
    private JFreeChart createComparisionChart(final CategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createBarChart(
                null,
                null,
                "$/kWh",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );
         
        //chart.setBackgroundPaint(new Color(204,255,204));
        chart.setBackgroundPaint(Color.WHITE);
        TextTitle tx = new TextTitle("Price Comparison");
        tx.setFont(new Font("SansSerif",0,9));
        chart.setTitle(tx);
        CategoryPlot plot = chart.getCategoryPlot();
        //plot.setBackgroundPaint(new Color(248, 248, 255 ));
        plot.setBackgroundPaint(Color.WHITE);
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setNumberFormatOverride(new DecimalFormat("0.0###"));
        rangeAxis.setLabelFont(new Font("SansSerif",0,9));
        rangeAxis.setAutoTickUnitSelection(true);
        rangeAxis.setLowerBound(0);
        rangeAxis.setUpperBound(maxvalue*130/100);
        rangeAxis.setTickLabelFont(new Font("SansSerif",1,7));
        rangeAxis.setTickUnit(new NumberTickUnit((rangeAxis.getUpperBound()-rangeAxis.getLowerBound())/6));

        
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setMaxBarWidth(0.6);
        renderer.setSeriesPaint(0,  new Color(153, 153, 255)); 
        renderer.setSeriesOutlinePaint(0,new Color(0, 0, 0));
        DecimalFormat decimalformat1 = new DecimalFormat("##,##0.0000");
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat1)); //i added your line here.
        renderer.setItemLabelFont(new Font("SansSerif",1,7));
        //renderer.setToolTipGenerator(new StandardCategoryToolTipGenerator("{2}",decimalformat1));
        renderer.setItemLabelsVisible(true);
        //renderer.setBaseItemLabelsVisible(true);
        chart.getCategoryPlot().setRenderer(renderer);
        
        plot.setNoDataMessage(" No data available");
        
        return chart;
        
    }
    
    
}


/*
*$Log: AnnualChartforPDF.java,v $
*Revision 1.3  2008/12/03 10:55:10  tannamalai
*URL Updated Based on Remote Environment.
*
*Revision 1.2  2008/02/06 06:41:45  tannamalai
*mcpe calculations corrected
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.5  2007/11/29 12:41:50  tannamalai
*grid color changed to white
*
*Revision 1.4  2007/11/27 05:02:46  jnadesan
*chart size changed
*
*Revision 1.3  2007/11/26 14:44:40  srajan
*bar width changed
*
*Revision 1.2  2007/11/22 11:53:24  jnadesan
*chart color and size changed
*
*Revision 1.1  2007/10/30 05:51:54  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:23  jnadesan
*initail MXEP commit
*
*Revision 1.11  2007/08/23 07:26:54  jnadesan
*imports organized
*
*Revision 1.10  2007/08/01 04:57:31  jnadesan
*Chart changed
*
*Revision 1.9  2007/07/13 06:25:57  jnadesan
*initial value for chart chaged to .05
*
*Revision 1.8  2007/07/02 11:41:27  jnadesan
*max value changed
*
*Revision 1.7  2007/05/14 10:20:31  jnadesan
*null value checked
*
*Revision 1.6  2007/04/18 03:55:54  kduraisamy
*imports organized.
*
*Revision 1.5  2007/04/16 13:21:24  jnadesan
*chart aligned modified
*
*Revision 1.4  2007/04/13 12:41:24  jnadesan
*chart bar lebel showed
*
*Revision 1.3  2007/04/12 13:09:11  jnadesan
*label angel changed
*
*Revision 1.2  2007/04/12 09:02:52  jnadesan
*sensitivity chart added
*
*Revision 1.1  2007/04/06 03:49:32  jnadesan
*chart for pdf
*
*
*/