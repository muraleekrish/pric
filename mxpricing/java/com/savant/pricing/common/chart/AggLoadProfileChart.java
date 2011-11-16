/*
 * Created on May 7, 2007
 * 
 * Class Name AggLoadProfileChart.java
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
import java.util.HashMap;

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
import com.savant.pricing.dao.LoadProfileTypesDAO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AggLoadProfileChart {
    
    public  HashMap getchart(HttpSession session,int custId,String esiid,PrintWriter pw) {
        LoadProfileTypesDAO objLoadProfileTypesDAO = new LoadProfileTypesDAO();
        HashMap objAll = objLoadProfileTypesDAO.getAggregatedLoadProfile(custId,esiid);
        HashMap hmFileName = new HashMap();
        String filename="";
        String filename1="";
        try{
            
            final XYDataset dataset = createDataset((HashMap)objAll.get(new Integer(2)));
            final JFreeChart chart = createChart(dataset,"Aggregated Load Profile Details for WeekDay");
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            filename = ServletUtilities.saveChartAsJPEG(chart, 600, 360 , info, session);
            ChartUtilities.writeImageMap(pw, filename, info,new StandardToolTipTagFragmentGenerator(),null);
            hmFileName.put("weekday",filename);
            
            final XYDataset dataset1 = createDataset((HashMap)objAll.get(new Integer(3)));
            final JFreeChart chart1 = createChart(dataset1,"Aggregated Load Profile Details for WeekEnd");
            ChartRenderingInfo info1 = new ChartRenderingInfo(new StandardEntityCollection());
            filename1 = ServletUtilities.saveChartAsJPEG(chart1, 600, 360 , info1, session);
            ChartUtilities.writeImageMap(pw, filename1, info1,new StandardToolTipTagFragmentGenerator(),null);
            hmFileName.put("weekend",filename1);
            pw.flush();
            
        }catch (Exception e) {
            
            e.printStackTrace();
        }
        return hmFileName;
    }
    private XYDataset createDataset(HashMap profileDetails) {
        final XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = null;
        String[] month = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        for(int i=1;i<=12;i++)
        {
            series1 = new XYSeries(month[i-1]);
            if(null!=profileDetails)
            {
                HashMap hmDetails =(HashMap)profileDetails.get(new Integer(i));
                for(int hourCount=1;hourCount<=hmDetails.size();hourCount++)
                {
                    series1.addOrUpdate(new Integer(hourCount),(Float)hmDetails.get(new Integer(hourCount)));
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
                "kW",                      // y axis label
                dataset,                  // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );
        
        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
       // Paint pBack = new GradientPaint(0.0f, 0.0f, new Color(249, 254, 249), 0.0f, 0.0f, new Color(249, 254, 249));
        chart.setBackgroundPaint(Color.WHITE);
        TextTitle chartTitle = new TextTitle();
        chartTitle.setText(message);
        chartTitle.setFont(new Font("Arial", Font.BOLD,11));
        chart.setTitle(chartTitle);
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        final ValueAxis valueAxis = plot.getDomainAxis();
        valueAxis.setLowerBound(1);
        valueAxis.setUpperBound(24);
        StandardXYToolTipGenerator tooltip = new StandardXYToolTipGenerator("{0}: {2} kW",NumberFormat.getIntegerInstance(),NumberUtil.doubleFraction());
        plot.getRenderer().setToolTipGenerator(tooltip);
        
       
        plot.setNoDataMessage("No Data available");
        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRange(true);
        rangeAxis.setLowerBound(0);
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
        
    }
}


/*
*$Log: AggLoadProfileChart.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/29 12:41:50  tannamalai
*grid color changed to white
*
*Revision 1.1  2007/10/30 05:51:54  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:23  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/08/23 07:26:54  jnadesan
*imports organized
*
*Revision 1.4  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.3  2007/05/08 13:46:28  jnadesan
*load profile chart plotted by esiid wise
*
*Revision 1.2  2007/05/08 05:32:49  jnadesan
*unit changed
*
*Revision 1.1  2007/05/07 15:53:10  jnadesan
*chart for aggregated loadprofile
*
*
*/