/*
 * Created on May 8, 2007
 * 
 * Class Name LoadProfileServlet.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.aggregatorlp;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.calculation.dao.PricingDAO;
import com.savant.pricing.common.chart.AggLoadProfileChart;
import com.savant.pricing.dao.LoadProfileTypesDAO;
import com.savant.pricing.transferobjects.AggregatedLoadProfileDetails;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoadProfileServlet extends HttpServlet{
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
    throws ServletException, IOException {
        
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String select = "";
        String priceRunId = request.getParameter("priceRunId");
        String esiId =request.getParameter("ESIID");
        String message =request.getParameter("Message");
        NumberFormat df = NumberFormat.getIntegerInstance();
        PricingDAO objPricingDAO = new PricingDAO();
        int esiidcount = 0;
        if(priceRunId != null && esiId!=null)
        {
            if(esiId.trim().equalsIgnoreCase(""))
            {
                esiId = getEsiidstr(getEsiidHashMap(request));
            }
        }
        try
        {
            if(!message.equalsIgnoreCase("Excel"))
            {
                AggLoadProfileChart objAggLoadProfileChart = new AggLoadProfileChart();
                AggregatedLoadProfileDetails objAggregatedLoadProfileDetails = null;
                StringTokenizer st = new StringTokenizer(esiId,",");
                esiidcount = st.countTokens();
                HashMap objfilename = objAggLoadProfileChart.getchart(request.getSession(),Integer.parseInt(priceRunId),esiId,new PrintWriter(response.getWriter()));
                String[] month = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
                
                LoadProfileTypesDAO objLoadProfileTypesDAO = new LoadProfileTypesDAO();
                HashMap objAll = objLoadProfileTypesDAO.getAggregatedLoadProfileDetails(Integer.parseInt(priceRunId),esiId);
                for(int i=2;i<=3;i++)
                {
                    HashMap hmprofileDetails = (HashMap)objAll.get(new Integer(i));
                    select +="<table width='100%' border='0' cellspacing='0' cellpadding='0'><tr><td width='20%' class='tblheader' rowspan='2'>Month</td>" +
                    "<td colspan='3' class='tblheader'>On Peak(kW)</td><td colspan='3' class='tblheader'>Off Peak(kW)</td></tr>" +
                    "<tr > <td width='12%' class='tblheader'>Min</td><td width='12%' class='tblheader'>Max</td><td width='12%' class='tblheader'>Avg</td>" +
                    "<td width='12%' class='tblheader'>Min</td><td width='12%' class='tblheader'>Max</td><td width='12%' class='tblheader'>Avg</td>" +
                    "</tr>";
                    for(int monthCount = 1;monthCount<=12;monthCount++)
                    { 
                        if(hmprofileDetails!=null)
                        {
                            objAggregatedLoadProfileDetails = (AggregatedLoadProfileDetails)hmprofileDetails.get(new Integer(monthCount));
                            if(objAggregatedLoadProfileDetails!=null)
                            {
                                select+="<tr><td class='tbldata' align='left'>"+month[monthCount-1]+"</td>" +
                                "<td class='tbldata' align='right'>"+df.format(objAggregatedLoadProfileDetails.getOnPeakMin())+"</td>" +
                                "<td class='tbldata' align='right'>"+df.format(objAggregatedLoadProfileDetails.getOnPeakMax())+"</td>" +
                                "<td class='tbldata' align='right'>"+df.format(objAggregatedLoadProfileDetails.getOnPeakAvg())+"</td>" +
                                "<td class='tbldata' align='right'>"+df.format(objAggregatedLoadProfileDetails.getOffPeakMin())+"</td>" +
                                "<td class='tbldata' align='right'>"+df.format(objAggregatedLoadProfileDetails.getOffPeakMax())+"</td>" +
                                "<td class='tbldata' align='right'>"+df.format(objAggregatedLoadProfileDetails.getOffPeakAvg())+"</td></tr>";
                            }
                        }
                    }
                    if(hmprofileDetails==null||objAggregatedLoadProfileDetails==null)
                    {
                        select+="<tr valign='center'><td class='tbldata' colspan='7' align='center'>No Data available</td></tr>";
                    }
                    select += "</table>***@@";
                }
                String weekdayChartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + objfilename.get("weekday");
                String weekEndChartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + objfilename.get("weekend");
                select+="<img src="+ weekdayChartURL+"  border=0 usemap=#"+objfilename.get("weekday")+">***@@"+"<img src="+ weekEndChartURL+"  border=0 usemap=#"+objfilename.get("weekend")+">***@@";
                select+=esiidcount+" of "+objPricingDAO.getAllEsiIds(Integer.parseInt(priceRunId)).size();
                response.setContentType("*/*");
                response.getWriter().write(select);
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0L);
                System.gc();
            }
            else
            { 
                try
                {
                    ExportToExcel objExportToExcel = new ExportToExcel();
                    String urlStr[] = request.getRequestURL().toString().split(request.getContextPath());
                    String filePath = urlStr[0]+ request.getContextPath();
                    byte[] b = objExportToExcel.getLoadProfileexcelData(Integer.parseInt(priceRunId),esiId,filePath);
                    response.setHeader("Content-Disposition", "attachment;filename=\""
                            + "AggregatedLoadProfile.xls" + "\";");
                    response.setContentType("application");
                    response.setContentLength(b.length);
                    
                    ServletOutputStream servletoutputstream = response.getOutputStream();
                    
                    servletoutputstream.write(b, 0, b.length);
                    servletoutputstream.flush();
                    servletoutputstream.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    private HashMap getEsiidHashMap(HttpServletRequest request)
    {
        PricingDAO objPricingDAO = new PricingDAO();
        String tdsp = request.getParameter("TDSP");
        String zone = request.getParameter("Zone");
        String message = request.getParameter("Message");
        String custRefId = request.getParameter("priceRunId");
        HashMap mapEsiid = new HashMap();
        if(message.equalsIgnoreCase("Zone")||message.equalsIgnoreCase("Excel")||message.equalsIgnoreCase("month")||message.equalsIgnoreCase("false"))
        {
            if(zone.equalsIgnoreCase("0"))
            {
                mapEsiid = tdsp.equalsIgnoreCase("0")?objPricingDAO.getAllEsiIds(Integer.parseInt(custRefId)):objPricingDAO.getAllEsiIds(Integer.parseInt(custRefId),Integer.parseInt(tdsp));
            }
            else
            {
                mapEsiid = tdsp.equalsIgnoreCase("0")?objPricingDAO.getAllEsiIdsByCongestionZone(Integer.parseInt(custRefId),Integer.parseInt(zone)):objPricingDAO.getAllEsiIdsByTDSPCongestionZone(Integer.parseInt(custRefId),Integer.parseInt(tdsp),Integer.parseInt(zone));
            }
        }
        else if(message.equalsIgnoreCase("TDSP"))
        {
            mapEsiid = tdsp.equalsIgnoreCase("0")?objPricingDAO.getAllEsiIds(Integer.parseInt(custRefId)):objPricingDAO.getAllEsiIds(Integer.parseInt(custRefId),Integer.parseInt(tdsp));
        }
        return mapEsiid;
    }
    private String getEsiidstr(HashMap hmEsiid)
    {
        String esiId = "";
        Set set = hmEsiid.keySet();
        Iterator it = set.iterator();
        while(it.hasNext()) 
        {
            Object key = it.next();
            if(esiId.length()>1)
                esiId+=",";
            esiId += (String)hmEsiid.get(key);
        }
        return esiId;
    }
}



/*
*$Log: LoadProfileServlet.java,v $
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:30  jnadesan
*initail MXEP commit
*
*Revision 1.6  2007/05/11 10:55:33  jnadesan
*name of the file changed
*
*Revision 1.5  2007/05/10 07:09:07  jnadesan
*esiid count show changed
*
*Revision 1.4  2007/05/09 14:22:47  jnadesan
*export to excel wormk finished
*
*Revision 1.3  2007/05/09 11:31:13  jnadesan
*all values will be taken frm request
*
*Revision 1.2  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.1  2007/05/08 13:47:03  jnadesan
*load profile chart plotted by esiid wise and export excel option added
*
*
*/