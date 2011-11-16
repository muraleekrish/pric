/*
 * 
 * PriceMatrixChart.java    Sep 3, 2007
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
import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.savant.pricing.matrixpricing.valueobjects.MatrixRunResultVO;

/**
 * 
 */
public class PriceMatrixChart
{
    public  String pricematrixChart(HttpSession session,List lstResults, String TDSPName) {
        
        final CategoryDataset dataset = createDataset(lstResults);
        final JFreeChart chart = createChart(dataset);
        String filename="";
        
        try{
            
            ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            
            File ff= new File("E:/pricingdata/jasper/"+TDSPName+".jpeg");
            if(ff.exists())
                ff.delete();
            ChartUtilities.saveChartAsJPEG(new File("D:/J2EEProjects/pricingdata/jasper/"+TDSPName+".jpeg"),chart,200,84);
            //Write the image map to the PrintWriter
            PrintWriter pw= new PrintWriter(System.out);
            ChartUtilities.writeImageMap(pw, filename, info,true);
            pw.flush();
            
        }catch (Exception e) {
            
            e.printStackTrace();
        }
        return filename;
    }
    
    private CategoryDataset createDataset(List lstResults) {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        MatrixRunResultVO objMatrixRunResultVO = null;
        Iterator iteResult = lstResults.iterator();
        while(iteResult.hasNext())
        {
            objMatrixRunResultVO = (MatrixRunResultVO)iteResult.next();
            result.addValue(objMatrixRunResultVO.getEnergyOnlyPrice(),objMatrixRunResultVO.getLoadProfile().getProfileType(),new Integer(objMatrixRunResultVO.getTerm()));
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
        
        final JFreeChart chart = ChartFactory.createBarChart(
                null,  // chart title
                null,                  // domain axis label
                null,                     // range axis label
                dataset,                     // data
                PlotOrientation.VERTICAL,    // the plot orientation
                false,                        // legend
                true,                        // tooltips
                false                        // urls
        );
        CategoryItemRenderer renderer = chart.getCategoryPlot().getRenderer();
        Paint p1 = new GradientPaint(
                0.0f, 0.0f,new Color(51, 153, 51), 0.0f, 0.0f,   new Color(160, 255, 160)
        );
        Paint p2 = new GradientPaint(
                0.0f, 0.0f,new Color(153, 51, 51), 0.0f, 0.0f,   new Color(255,140, 160)
        );
        Paint p3 = new GradientPaint(
                0.0f, 0.0f,new Color(51, 51, 153), 0.0f, 0.0f,   new Color(160, 140, 255)
        );
        renderer.setSeriesPaint(0, p1);
        renderer.setSeriesPaint(1, p2);
        renderer.setSeriesPaint(2, p3);
        Font f1 = new Font("Arial",Font.PLAIN,8);
        Font f2 = new Font("Arial Narrow",Font.PLAIN,9);
        
        CategoryPlot plot = chart.getCategoryPlot();
       // chart.getCategoryPlot().getRangeAxis().setLowerBound(0.0);
       // chart.getCategoryPlot().getRangeAxis().setLabelFont(f1);
        plot.getRangeAxis().setTickLabelFont(f1);
        //chart.getCategoryPlot().getDomainAxis().setLabelFont(f1);
        plot.getDomainAxis().setTickLabelFont(f1);
        plot.getRangeAxis().setLowerBound(0.045);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.WHITE);
        chart.setBackgroundPaint(new Color(0xFF, 0xFF, 0xFF));
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.WHITE);
        plot.setNoDataMessage(" No data available");
       // plot.setOutlinePaint(p1);
       // plot.setWeight(100);
       // plot.setDomainGridlinePaint(p1);
        return chart;
    }
    
    
}


/*
*$Log: PriceMatrixChart.java,v $
*Revision 1.3  2009/01/27 05:47:57  tannamalai
*changes - according to MX server
*
*Revision 1.2  2008/11/21 09:46:10  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.4  2007/11/29 12:41:50  tannamalai
*grid color changed to white
*
*Revision 1.3  2007/11/29 04:49:23  jnadesan
*chart design chaged
*
*Revision 1.2  2007/11/28 13:03:55  jnadesan
*Chart for Matrix Pdf
*
*Revision 1.1  2007/10/30 05:51:54  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:23  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/09/04 12:01:16  jnadesan
*style applied
*
*Revision 1.3  2007/09/04 05:07:50  spandiyarajan
*removed unwanted imports
*
*Revision 1.2  2007/09/04 04:51:26  jnadesan
*chart size changed
*
*Revision 1.1  2007/09/03 15:04:54  jnadesan
*chart added
*
*
*/