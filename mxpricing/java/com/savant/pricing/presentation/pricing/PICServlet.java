/*
 * Created on Mar 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.calculation.dao.LoadExtrapolationDAO;
import com.savant.pricing.calculation.valueobjects.PICVO;
import com.savant.pricing.common.NumberUtil;
import com.savant.pricing.common.chart.PICChart;
import com.savant.pricing.transferobjects.AnnualkWhDetails;
import com.savant.pricing.transferobjects.MonthDetails;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PICServlet extends HttpServlet{
    
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)throws ServletException, IOException 
    {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String esiId =  request.getParameter("Esiid");
       String strEsiId = "";
        String prsCustId = request.getParameter("prsCustId");
        String message = request.getParameter("message");
        try
        {
            HashMap hmessiid = new HashMap();
            LoadExtrapolationDAO objLoadExtrapolationDAO = new LoadExtrapolationDAO();
            PICVO objCustomerDetails=null;
            PICChart objchart = new PICChart();
            List picUsage= new ArrayList();
            NumberFormat num = NumberFormat.getIntegerInstance();
            NumberFormat df = NumberUtil.doubleFraction();
            AnnualkWhDetails objAnnualkWhDetails = null;
            if(esiId.equalsIgnoreCase("0"))
            {
                strEsiId = "All";
            }
            else
            {
                strEsiId = esiId;
            }
            List lstannual = (List)new com.savant.pricing.dao.PICDAO().getTotalkWh(Integer.parseInt(prsCustId),strEsiId);
            objAnnualkWhDetails = (AnnualkWhDetails)lstannual.get(0);
            List noOfDays = new ArrayList();
            List readDayOfMonth=new ArrayList();
            NumberFormat nf = new DecimalFormat("0.00##");
            List kW = new ArrayList();
            List kWh = new ArrayList();
            List kVA = new ArrayList();
            int totalEsiidCount = 0;
            String strmeterread = "";
            String strnoofDays = "";
            String strkWh = "";
            String strkVA = "";
            String strkW = "";
            String strLoadFactor = "";
            String previousimage="&nbsp;";
            String nextimage = "&nbsp;";
            int count = 0; 
            int j = 0;
            int i=0;
            MonthDetails objMonthDetails=null;
            try
            { 
                List custDetails = objLoadExtrapolationDAO.getAllESIID(Integer.parseInt(prsCustId));
                if(esiId.equalsIgnoreCase("0"))
                {
                    picUsage = objLoadExtrapolationDAO.getMonthDetails(Integer.parseInt(prsCustId));
                    count = custDetails.size();
                    totalEsiidCount = count;
                }
                else
                {
                    
                    for(i=0;i<custDetails.size();i++)
                    {
                        objCustomerDetails =(PICVO) custDetails.get(i);
                        if(objCustomerDetails.getEsiId().equalsIgnoreCase(esiId)&& message.equalsIgnoreCase("combo"))
                        {
                            count = i+1;
                            j=i;
                        }
                        else if(objCustomerDetails.getEsiId().equalsIgnoreCase(esiId)&& message.equalsIgnoreCase("inc"))
                        {
                            count = i+2;
                            j=i+1;
                        }
                        else if(objCustomerDetails.getEsiId().equalsIgnoreCase(esiId)&& message.equalsIgnoreCase("dec"))
                        {
                            count = i;
                            j=i-1;
                        }
                        hmessiid.put(objCustomerDetails.getEsiId(),objCustomerDetails.getEsiId());
                    }
                    totalEsiidCount = hmessiid.size();
                    objCustomerDetails =(PICVO) custDetails.get(j);
                    picUsage = objLoadExtrapolationDAO.getMonthDetailsByESIID(objCustomerDetails.getEsiId(),Integer.parseInt(prsCustId));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
            for(i=0;i< picUsage.size();i++)
            { 
                noOfDays.add(new Integer(((MonthDetails)picUsage.get(i)).getNoOfDays()));
                if(((MonthDetails)picUsage.get(i)).getMeterReadDate()==null)
                    readDayOfMonth.add(null);
                else
                    readDayOfMonth.add(new Date(((MonthDetails)picUsage.get(i)).getMeterReadDate()));
                kW.add(new Float(((MonthDetails)picUsage.get(i)).getHistoricalDemand()));
                kVA.add(new Float(((MonthDetails)picUsage.get(i)).getHistoricalApparentPower()));
                kWh.add(new Float((((MonthDetails)picUsage.get(i)).getPicUsage())));
            }
            HashMap hmfilenameChart = objchart.chart(request.getSession(),kW,kWh,kVA,noOfDays,readDayOfMonth,response.getWriter());
            String chartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + (String)hmfilenameChart.get("chartdays");  
            String kWkVAchartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + (String)hmfilenameChart.get("chartkW");  
            String kwhchartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + (String)hmfilenameChart.get("chartkWh");  
            
            float floadfactor = 0;
            for(i=0;i< picUsage.size();i++)
            {  
                objMonthDetails = (MonthDetails)picUsage.get(i); 
                strmeterread += "<td width='50' class='list_data'>" + (objMonthDetails.getMeterReadDate()==null?"&nbsp;":objMonthDetails.getMeterReadDate())+"</td>";
                strnoofDays += "<td width='50' class='list_data'>" + objMonthDetails.getNoOfDays()+"</td>";
                strkVA +="<td width='50' class='list_data'>" + objMonthDetails.getHistoricalApparentPower()+"</td>";
                strkW +="<td width='50' class='list_data'>" + objMonthDetails.getHistoricalDemand()+"</td>"; 
                strkWh +="<td width='50' class='list_data'>" + objMonthDetails.getPicUsage()+"</td>";
                
                if(objMonthDetails.getPicUsage() == 0.0 || objMonthDetails.getHistoricalDemand() == 0.0 || objMonthDetails.getNoOfDays() == 0 )
                    strLoadFactor +="<td width='50' class='list_data'>" + 0.00+"</td>";
                else
                {
                    floadfactor = ((objMonthDetails.getPicUsage()*100)/(objMonthDetails.getHistoricalDemand()*objMonthDetails.getNoOfDays()*24));
                    strLoadFactor +="<td width='50' class='list_data'>" + nf.format(floadfactor)+"</td>";
                }
            }  
            
            if(count>1&&!esiId.equalsIgnoreCase("0"))
            {
            previousimage = "<a href='#'onclick="+"servletaccess('dec')"+">"+
            "<img  src='"+request.getContextPath()+"/images/prev.gif' alt='Previous' width='6' height='12' border='0' >"+
            "</a>";
            }
            if(count<hmessiid.size()&&!esiId.equalsIgnoreCase("0"))
            {
            nextimage = "<a href='#'onclick="+"servletaccess('inc')"+">"+
            "<img  src='"+request.getContextPath()+"/images/nxt.gif' alt='Next' width='6' height='12' border='0'>"+
            "</a>";
            }
            
            String show = "<table width='100%' height='136'  border='0' cellpadding='0' cellspacing='0'>"+ 
            "<tr class='tblheader'>"+
            "<td width='11%'> &nbsp; Month </td>"+
            " <td width='50'>Jan</td> "+
            "<td width='50'>Feb</td>"+ 
            "<td width='50'>Mar</td> "+
            "<td width='50'>Apr</td>"+ 
            "<td width='50'>May</td>"+ 
            "<td width='50'>Jun</td>"+ 
            "<td width='50'>Jul</td>"+ 
            "<td width='50'>Aug</td>"+ 
            " <td width='50'>Sep</td>"+ 
            "<td width='50'>Oct</td>"+ 
            "<td width='50'>Nov</td>"+ 
            "<td width='50'>Dec</td></tr><tr ><td class='list_data'><strong>Read Date </strong></td> " +strmeterread+
            " </tr><tr ><td class='list_data'><strong>No of Days </strong></td>"+strnoofDays+
            "</tr><tr ><td class='list_data'  ><strong>kWh</strong></td>" +strkWh+
            "</tr><tr ><td class='list_data'><strong>kVA</strong></td>" +strkVA +
            "</tr><tr ><td class='list_data'><strong>kW</strong></td>"+strkW+
            "</tr><tr ><td class='list_data'><strong>Load Factor</strong></td>"+strLoadFactor+"</tr></table>" +"@@@"+
            "<table width='100%' border='0' cellspacing='0' cellpadding='0'><tr id = 'chart'>"+
             "<td width='10'>&nbsp;</td><td width='309'>"+
             "<img src='"+ chartURL +"' border='0' usemap='#"+(String)hmfilenameChart.get("chartdays")+"'></td> "+
             "<td width='288'><img src='" + kWkVAchartURL + "' border='0' usemap='#"+(String)hmfilenameChart.get("chartkW")+"'></td>"+ 
             "<td width='288'><img src='" + kwhchartURL +"' border='0' usemap='#"+(String)hmfilenameChart.get("chartkWh")+"'></td> "+
             "<td width='13'></td></tr></table>&nbsp;</td>"+"@@@"+(esiId.equalsIgnoreCase("0")?"All ESIID":" ESIID : "+objCustomerDetails.getEsiId())+"@@@"+" ESIID "+count+" of "+totalEsiidCount+"@@@"+
             "<table width='100%'  border='0' cellspacing='0' cellpadding='0'><tr><td width='189' class='fieldtitle'>Customer </td>" +
             "<td width='10' class='fieldtitle'>:</td>" +
             "<td class='fieldata' width='245' id='customername'>"+ (esiId.equalsIgnoreCase("0")?"":objCustomerDetails.getCustomerName())+"</td>" +
             "<td width='191' class='fieldtitle'>Service Address</td>" +
             "<td width='10' class='fieldtitle'>:</td><td width='347' class='fieldata'>"+(esiId.equalsIgnoreCase("0")?"":objCustomerDetails.getAddress1())+"</td></tr><tr><td width='189' class='fieldtitle'>Rate Class </td>" +
             "<td width='10' class='fieldtitle'>:</td><td class='fieldata' width='245'>" +
             (esiId.equalsIgnoreCase("0")?"":new Integer(objCustomerDetails.getRateCode().getRateCodeIdentifier()).toString())+"</td><td width='191' class='fieldtitle'>Read Cycle </td><td width='10' class='fieldtitle'>:</td>" +
             "<td width='347' class='fieldata'>"+(esiId.equalsIgnoreCase("0")?"":objCustomerDetails.getMeterReadCycle())+"</td>" +
             "</tr><tr><td width='189' class='fieldtitle'>Service Zip </td><td width='10' class='fieldtitle'>:</td>" +
             "<td class='fieldata' width='245'>"+ (esiId.equalsIgnoreCase("0")?"":new Integer(objCustomerDetails.getZipCode().getZipCode()).toString())+"</td>" +
             "<td width='191' class='fieldtitle'>Load Profile </td><td width='10' class='fieldtitle'>:</td>" +
             "<td width='347' class='fieldata'>"+(esiId.equalsIgnoreCase("0")?"":objCustomerDetails.getProfileDetails().getLoadProfile())+"</td>" +
             "</tr></table>"+"@@@"+
             previousimage+"@@@"+nextimage+"@@@"+" Annual kWh: "+num.format(objAnnualkWhDetails.getKWh())+" ("+ df.format(objAnnualkWhDetails.getKWhPercentage())+" %)";
                       
            response.setContentType("*/*");
            response.getWriter().write(show);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0L);
            System.gc();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    
}
