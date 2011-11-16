/*
 * 
 * MatrixPricePDFChart.java    Nov 28, 2007
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
import java.awt.GradientPaint;
import java.awt.Paint;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import com.savant.pricing.calculation.valueobjects.ForwardCurveBlockVO;
import com.savant.pricing.calculation.valueobjects.GasPriceVO;

/**
 * 
 */
public class MatrixPricePDFChart
{
    private Date maximumDate = null;
    public  String getchart(List gasPrice, List lstForwardPrice) {
        
        final JFreeChart chart = createChart(gasPrice,lstForwardPrice);
        String filename="";
        try{
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            File ff = new File("E:/pricingdata/jasper/MMPrice.jpeg");
            if(ff.exists())
                ff.delete();
            ChartUtilities.saveChartAsJPEG(new File("E:/pricingdata/jasper/MMPrice.jpeg"), chart, 595,196);
            //Write the image map to the PrintWriter
            PrintWriter pw = new PrintWriter(System.out);
            ChartUtilities.writeImageMap(pw, filename, info, true);
            pw.flush();
        }catch (Exception e) {
            
            e.printStackTrace();
        }
        return filename;
    }
    private XYDataset createDataset(List listgaspricedetails) {
        
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
    private XYDataset createForwardDataset(List listgaspricedetails) {
        
        final TimeSeries series = new TimeSeries("7x24 Houston", Month.class);
        Iterator ite = listgaspricedetails.iterator();
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
    
    /**
     * Creates a sample chart.
     *
     * @param dataset  the dataset for the chart.
     *
     * @return A sample chart.
     */
    
    private JFreeChart createChart(List gasPrice, List lstForwardPrice) {
        final XYDataset gasdataset = createDataset(gasPrice);
        final XYDataset forwardDataset = createForwardDataset(lstForwardPrice);
        final DateAxis valueAxis = new DateAxis("Month");
        valueAxis.setDateFormatOverride(new SimpleDateFormat("MMM-yy"));
        final ValueAxis valueAxis2 = new NumberAxis("$/MMBtu");
        
       final JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "", 
                "NG Price", 
                "Price Per Unit",
                gasdataset,
                false,
                false,
                false
            );

        final XYPlot plot = chart.getXYPlot();
        plot.setDataset(0,gasdataset);
        plot.setDomainAxis(valueAxis);
        plot.setRangeAxis(0,valueAxis2);
        chart.setBackgroundPaint(Color.white);
        
        plot.mapDatasetToRangeAxis(1, 1);
        plot.setDataset(1,forwardDataset);
        final ValueAxis axis2 = new NumberAxis("$/MWh");
        axis2.setLabelAngle(9.4);
        axis2.setLabelPaint(Color.BLUE);
        axis2.setTickLabelPaint(Color.BLUE);
        axis2.setTickMarkPaint(Color.BLUE);
        plot.setRangeAxis(1, axis2);
        
        Paint pBack = new GradientPaint(0.0f, 0.0f, new Color(249, 254, 249), 0.0f, 0.0f, new Color(249, 254, 249));
        chart.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.WHITE);
       
        plot.setNoDataMessage("No Data Available");
        plot.setWeight(2);
        
        final XYItemRenderer renderer = plot.getRenderer();
        renderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
        renderer.setSeriesPaint(0,Color.red);
        if (renderer instanceof StandardXYItemRenderer) {
            final StandardXYItemRenderer rr = (StandardXYItemRenderer) renderer;
            rr.setPlotShapes(false);
            rr.setShapesFilled(true);
        }
        final StandardXYItemRenderer renderer2 = new StandardXYItemRenderer();
        renderer2.setSeriesPaint(0, Color.BLUE);
        renderer2.setPlotShapes(false);
        renderer2.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
        plot.setRenderer(1, renderer2);

        final DateAxis axis = (DateAxis) plot.getDomainAxis();
        chart.setBackgroundPaint(java.awt.Color.white);
        
        return chart;
    }
}


/*
*$Log: MatrixPricePDFChart.java,v $
*Revision 1.4  2009/01/27 05:47:57  tannamalai
*changes - according to MX server
*
*Revision 1.3  2008/11/21 09:46:10  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.2  2008/01/24 08:34:52  tannamalai
*new chart class added for combined chart
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/11/28 13:03:55  jnadesan
*Chart for Matrix Pdf
*
*
*/