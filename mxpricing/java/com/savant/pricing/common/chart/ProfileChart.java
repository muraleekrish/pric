/*
 * Created on May 31, 2007
 * 
 * Class Name ProfileChart.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */ 
package com.savant.pricing.common.chart;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.imagemap.StandardToolTipTagFragmentGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.savant.pricing.common.NumberUtil;
import com.savant.pricing.transferobjects.HourValueDetails;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProfileChart {
    
    public  HashMap getchart(HttpSession session,HashMap hmProfileDetails,PrintWriter pw)
    {
        HashMap hmFileName = new HashMap();
        String filename="";
        String filename1="";
        try{
            if(hmProfileDetails!=null)
            {
                final XYDataset dataset = createDataset((HashMap) hmProfileDetails.get(new Integer(2)));
                final JFreeChart chart = createChart(dataset,"Profile Details for WeekDay");
                ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
                filename = ServletUtilities.saveChartAsJPEG(chart, 600, 360 , info, session);
                ChartUtilities.writeImageMap(pw, filename, info,new StandardToolTipTagFragmentGenerator(),null);
                hmFileName.put("weekday",filename);
                
                final XYDataset dataset1 = createDataset((HashMap) hmProfileDetails.get(new Integer(3)));
                final JFreeChart chart1 = createChart(dataset1,"Profile Details for WeekEnd");
                ChartRenderingInfo info1 = new ChartRenderingInfo(new StandardEntityCollection());
                filename1 = ServletUtilities.saveChartAsJPEG(chart1, 600, 360 , info1, session);
                ChartUtilities.writeImageMap(pw, filename1, info1,new StandardToolTipTagFragmentGenerator(),null);
                hmFileName.put("weekend",filename1);
            }
            pw.flush();
            
        }catch (Exception e) {
            
            e.printStackTrace();
        }
        return hmFileName;
    }
    private XYDataset createDataset(HashMap hmIDRWeekDayDetails) {
        final XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = null;
        String[] month = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        
        Set keySet = hmIDRWeekDayDetails.keySet();
        Iterator iteMonth = keySet.iterator();
        while(iteMonth.hasNext())
        {
            int monthCount = ((Integer)iteMonth.next()).intValue();
            series1 = new XYSeries(month[monthCount-1]);
            List lsthourDetails = (List)hmIDRWeekDayDetails.get(new Integer(monthCount));
            int hourCount = 0;
            if(lsthourDetails!=null)
            {
                while(hourCount<lsthourDetails.size())
                {
                    HourValueDetails objHourValueDetails = (HourValueDetails)lsthourDetails.get(hourCount);
                    series1.addOrUpdate(new Integer(objHourValueDetails.getHour()),new Double(objHourValueDetails.getValue()));
                    hourCount++;
                }
                dataset.addSeries(series1);
            }
        }
        return dataset; 
    }
    private JFreeChart createChart(final XYDataset dataset,String message) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "",      // chart title
                "Hour",                      // x axis label
                "kWh",                      // y axis label
                dataset,                  // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );
        
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        //chart.setBackgroundPaint(new Color(249, 254, 249));
        chart.setBackgroundPaint(Color.WHITE);
        TextTitle chartTitle = new TextTitle();
        chartTitle.setText(message);
        chartTitle.setPaint(Color.BLUE); 
        chartTitle.setFont(new Font("Arial", Font.BOLD,12));
        chart.setTitle(chartTitle);
        GradientPaint gp2 = new GradientPaint(
                0.0f, 0.0f, new Color(196, 0, 0), 
                0.0f, 0.0f, new Color( 196, 0, 0)
        );
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        final ValueAxis valueAxis = plot.getDomainAxis();
        valueAxis.setLowerBound(1);
        valueAxis.setUpperBound(24);
        StandardXYToolTipGenerator tooltip = new StandardXYToolTipGenerator("{0}: {2} kWh",NumberFormat.getIntegerInstance(),NumberUtil.doubleFraction());
        plot.getRenderer().setToolTipGenerator(tooltip);
        
        plot.setNoDataMessage("No Data available");
        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRange(true);
        rangeAxis.setLabelPaint(gp2);
        valueAxis.setLabelPaint(gp2);
        rangeAxis.setLowerBound(0);
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
        
    }
}


/*
*$Log: ProfileChart.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:54  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:23  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/09/10 11:25:36  jnadesan
*chart title color changed
*
*Revision 1.4  2007/08/23 07:26:54  jnadesan
*imports organized
*
*Revision 1.3  2007/06/01 10:01:39  kduraisamy
*null pointer error recitified.
*
*Revision 1.2  2007/06/01 07:25:01  jnadesan
*taking profile details logic chaged
*
*Revision 1.1  2007/06/01 06:02:47  jnadesan
*chart for profile details
*
*
*/