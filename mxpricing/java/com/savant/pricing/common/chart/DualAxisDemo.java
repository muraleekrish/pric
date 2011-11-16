/*
 * Created on Jan 23, 2008
 *
 * ClassName	:  	DualAxisDemo.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.common.chart;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;

import com.savant.pricing.calculation.valueobjects.ForwardCurveBlockVO;
import com.savant.pricing.calculation.valueobjects.GasPriceVO;
import com.savant.pricing.common.BuildConfig;

/**
 * A simple demonstration application showing how to create a dual axis chart based on data
 * from two {@link CategoryDataset} instances.
 *
 */
public class DualAxisDemo extends ApplicationFrame {

    
    public DualAxisDemo(String arg0)
    {
        super(arg0);
    }
    
    private Date maximumDate = null;
    public  String getchart(List gasPrice, List lstForwardPrice) {
        
        final JFreeChart chart = createChart(gasPrice,lstForwardPrice);
        String filename="";
        try{
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            
            File ff = null;
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
            {
                ff = new File("D:/J2EEProjects/pricingdata/jasper/MMPrice.jpeg");
            }
            else
            {
                ff = new File("D:/J2EEProjects/pricingdata/jasper/MMPrice.jpeg");
            }
            if(ff.exists())
                ff.delete();
            
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
            { 
                ChartUtilities.saveChartAsJPEG(new File("D:/J2EEProjects/pricingdata/jasper/MMPrice.jpeg"), chart, 595,196);
            }
            else
            {
                ChartUtilities.saveChartAsJPEG(new File("D:/J2EEProjects/pricingdata/jasper/MMPrice.jpeg"), chart, 595,196);
            }
            //Write the image map to the PrintWriter
            PrintWriter pw = new PrintWriter(System.out);
            ChartUtilities.writeImageMap(pw, filename, info, true);
            pw.flush();
        }catch (Exception e) {
            
            e.printStackTrace();
        }
        return filename;
    }
    
    private JFreeChart createChart(List gasPrice, List lstForwardPrice) {

        final DateAxis domainAxis = new DateAxis("Month");
        SimpleDateFormat format = new SimpleDateFormat("MMM-yy");
        domainAxis.setDateFormatOverride(format);
        domainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
        final ValueAxis rangeAxis = new NumberAxis("$/MWh");
        final IntervalXYDataset data1 = createForwardDataset(lstForwardPrice);
        final XYItemRenderer renderer1 = new XYBarRenderer(0.20);
        final XYPlot plot = new XYPlot(data1, domainAxis, rangeAxis, renderer1);
        final XYItemRenderer renderer2A = new StandardXYItemRenderer();
        renderer2A.setToolTipGenerator(
            new StandardXYToolTipGenerator(
                StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
                new SimpleDateFormat("MMM-yy"), new DecimalFormat("0.00")
            )
        );
        final ValueAxis rangeAxis2 = new NumberAxis("$/MMBtu");
        plot.setRangeAxis(1, rangeAxis2);
        final XYDataset data2B = createGasDataset(gasPrice);
        plot.setDataset(1, data2B);
        plot.setRenderer(1, new StandardXYItemRenderer());
        plot.mapDatasetToRangeAxis(1, 1);
        rangeAxis2.setFixedDimension(2);   
       // rangeAxis2.setLabelAngle(-30);  
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinesVisible(false);
        
        plot.getRangeAxis().setLowerBound(0.045);
        plot.setWeight(100);
        plot.setNoDataMessage("No Data Available");
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        plot.setOrientation(PlotOrientation.VERTICAL);
        
        return new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);

    } 

    
    /**
     * creating category dataset using gas price
     */

    private IntervalXYDataset  createGasDataset(List listgaspricedetails) {
        
        final TimeSeries series = new TimeSeries("NG Price", Month.class);
        Iterator ite = listgaspricedetails.iterator();
        while(ite.hasNext())
        {
            GasPriceVO objGasPriceVO = (GasPriceVO)ite.next();
            series.addOrUpdate(new Month(objGasPriceVO.getMonthYear()),objGasPriceVO.getPrice());
            this.maximumDate = objGasPriceVO.getMonthYear();
        }
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        dataset.setDomainIsPointsInTime(true);
        
        return dataset;
    }
    
    /**
     * creating category dataset using forward price
     */
    
    private IntervalXYDataset  createForwardDataset(List lstForwardPrice) {
        
        final TimeSeries series = new TimeSeries("7x24 Houston", Month.class);
        Iterator ite = lstForwardPrice.iterator();
        while(ite.hasNext())
        {
            ForwardCurveBlockVO objForwardCurveBlockVO = (ForwardCurveBlockVO)ite.next();
            series.addOrUpdate(new Month(objForwardCurveBlockVO.getMonthYear()),objForwardCurveBlockVO.getPrice());
            //this.maximumDate = objForwardCurveBlockVO.getMonthYear();
        }
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        dataset.setDomainIsPointsInTime(true);
        
        return dataset;
    }
    
    public static void main(final String[] args) {
     /*   List gas = null;	
        List forward = null;
        final DualAxisDemo demo = new DualAxisDemo("Dual Axis Demo",gas,forward);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);*/

    }

}


/*
*$Log: DualAxisDemo.java,v $
*Revision 1.3  2008/12/03 10:55:10  tannamalai
*URL Updated Based on Remote Environment.
*
*Revision 1.2  2008/01/29 07:02:13  tannamalai
*chart class modified
*
*Revision 1.1  2008/01/24 08:34:52  tannamalai
*new chart class added for combined chart
*
*
*/