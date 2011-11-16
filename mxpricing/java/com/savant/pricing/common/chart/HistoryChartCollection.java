/*
 * 
 * HistoryChartCollection.java    Aug 8, 2007
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

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 * 
 */
public class HistoryChartCollection
{
    public String getBarChart(HttpSession session, List lstPrice, boolean far, String type, PrintWriter pw)
    {
        SummaryChart objSummaryChart = new SummaryChart();
        String fileName = "";
        try
        {
            fileName = objSummaryChart.priceChart(session, lstPrice, far, type, pw);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return fileName;
    }
    public String getLineChart(HttpSession session, List lstPrice, boolean far, String type, PrintWriter pw)
    {

        SummaryLineChart objSummaryLineChart = new SummaryLineChart();
        String fileName = "";
        try
        {
            fileName = objSummaryLineChart.priceLineChart(session, lstPrice, far, type, pw);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return fileName;
    
    }
}
/*
 *$Log: HistoryChartCollection.java,v $
 *Revision 1.2  2008/11/21 09:46:10  jvediyappan
 *Trieagle changed as MXEnergy.
 *
 *Revision 1.1  2007/12/07 06:18:35  jvediyappan
 *initial commit.
 *
 *Revision 1.1  2007/10/30 05:51:54  jnadesan
 *Initial commit.
 *
 *Revision 1.1  2007/10/26 15:19:23  jnadesan
 *initail MXEP commit
 *
 *Revision 1.1  2007/08/10 10:21:57  jnadesan
 *line chart added
 *
 *
 */