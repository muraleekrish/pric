/*
 * Created on Mar 16, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.common.chart;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import com.savant.pricing.calculation.valueobjects.GasPriceVO;
import com.savant.pricing.common.NumberUtil;
import com.savant.pricing.dao.GasPriceDAO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GasPriceCurve {
    private Date maximumDate = null;
    public  String getchart(HttpSession session,PrintWriter pw) {
        
        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        String filename="";
        try{
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            
            filename = ServletUtilities.saveChartAsJPEG(chart, 520, 350 , info, session);
            //Write the image map to the PrintWriter
            ChartUtilities.writeImageMap(pw, filename, info,new StandardToolTipTagFragmentGenerator(),null);
            pw.flush();
        }catch (Exception e) {
            
            e.printStackTrace();
        }
        return filename;
    }
    private XYDataset createDataset() {
        
        final TimeSeries series = new TimeSeries("Gas Price", Month.class);
        GasPriceDAO objGasPriceDAO = new GasPriceDAO();
        List listgaspricedetails = objGasPriceDAO.getAllGasPrices();
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
     * Creates a sample chart.
     *
     * @param dataset  the dataset for the chart.
     *
     * @return A sample chart.
     */
    
    private JFreeChart createChart(final XYDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "",
                "Month-Year", "Price($/MMBtu)",
                dataset,
                false,
                true,
                false
        ); 
        
        chart.setBackgroundPaint(Color.white);
        TextTitle title = new TextTitle("Gas Price");
        title.setPaint(Color.BLUE);
        chart.setTitle(title);
        
        DateFormat dateformat = new SimpleDateFormat("MMM-yy");
        StandardXYToolTipGenerator tooltip = new StandardXYToolTipGenerator("{1}, {2}",dateformat,NumberUtil.doubleFraction());
       
        final XYPlot plot = chart.getXYPlot();
        Paint pBack = new GradientPaint(0.0f, 0.0f, new Color(249, 254, 249), 0.0f, 0.0f, new Color(249, 254, 249));
        chart.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.setNoDataMessage("No Data Available");
        plot.setWeight(2);
      
        
        final XYItemRenderer renderer = plot.getRenderer();
        if (renderer instanceof StandardXYItemRenderer) {
            final StandardXYItemRenderer rr = (StandardXYItemRenderer) renderer;
            rr.setPlotShapes(true);
            rr.setShapesFilled(true);
            rr.setItemLabelsVisible(true);
        }
        renderer.setToolTipGenerator(tooltip);
        final ValueAxis axis2 = plot.getRangeAxis();
      
        final DateAxis axis = (DateAxis) plot.getDomainAxis();
        GradientPaint gp2 = new GradientPaint(
                0.0f, 0.0f, new Color(196, 0, 0), 
                0.0f, 0.0f, new Color( 196, 0, 0)
            );
        axis.setLabelPaint(gp2);
        axis2.setLabelPaint(gp2);
        axis.setDateFormatOverride(new SimpleDateFormat("MMM-yy"));
        if(this.maximumDate!=null)
        axis.setMaximumDate(this.maximumDate);
        return chart;
    }
}
