/*
 * Created on Apr 12, 2007
 *
 * ClassName	:  	TDSPChargesServlet.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pconfiguration;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.calculation.valueobjects.TDSPChargeRatesVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.NumberUtil;
import com.savant.pricing.dao.RateCodesDAO;
import com.savant.pricing.dao.TDSPChargeRatesDAO;
import com.savant.pricing.dao.TDSPDAO;
import com.savant.pricing.valueobjects.RateCodesVO;
import com.savant.pricing.valueobjects.TDSPVO;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TDSPChargesServlet  extends HttpServlet
{
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)throws ServletException, IOException 
    {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(BuildConfig.DMODE)
        {
            System.out.println("tdspId :"+request.getParameter("tdspId"));
            System.out.println("Message :"+request.getParameter("Message"));
        }
        String message = request.getParameter("Message");
        TDSPDAO objTDSPDAO = new TDSPDAO();
        RateCodesDAO objRateCodesDAO = new RateCodesDAO();
    	TDSPVO objTDSPVO = null;
    	NumberFormat tnf = NumberUtil.tetraFraction();
        String select = message+"@#$";
        try
        {
            String frstTDSPId =  request.getParameter("tdspId");
            if(message.equalsIgnoreCase("ratecode"))
	        {
	            select += "<select name=\"searchRateCode\" onchange='CallLoadRateCode(\"value\")'>";
	            if(Integer.parseInt(frstTDSPId)>0)
	            {
	                try
	                {                    
	            	 	if(Integer.parseInt(frstTDSPId)>0)
	            	 	{
		                    RateCodesVO objRateCodesVO = null;
		            	 	objTDSPVO = objTDSPDAO.getTDSP(Integer.parseInt(frstTDSPId));
		               		Iterator itr = objRateCodesDAO.getAllRateCodes(objTDSPVO.getTdspIdentifier()).iterator();
		            	 	while(itr.hasNext())
		            	   	{
		            	     	objRateCodesVO = (RateCodesVO)itr.next();
		            	     	select += "<option value=\""+objRateCodesVO.getRateCode() +"\">"+objRateCodesVO.getRateCode()+"</option>";
		            	   	}
	            	 	}
	                }
	                catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	            else
	            {
	                select += "<option value=\"-1\">Select one</option>";
	            }
	            select +="</select>";
	        }
	        else if(message.equalsIgnoreCase("value"))
	        {
	            List listTDSPChargeRates = null;
	            TDSPChargeRatesDAO objTDSPChargeRatesDAO = new TDSPChargeRatesDAO();
	            if(request.getParameter("rateCodes")!="null" && Integer.parseInt(frstTDSPId)>0 && !request.getParameter("rateCodes").equals("-1") )
	                listTDSPChargeRates = (List)objTDSPChargeRatesDAO.getAllTDSPChargeRates(Integer.parseInt(request.getParameter("tdspId")),request.getParameter("rateCodes"));
	            if(BuildConfig.DMODE)
	                System.out.println(listTDSPChargeRates +frstTDSPId+request.getParameter("rateCodes"));
				if(listTDSPChargeRates!= null && listTDSPChargeRates.size()>0)
				{
				    select +="<table width='100%' border='0' cellspacing='0' cellpadding='0'>" +
				    			"<tr><td width='50%' valign='top'>" +
				    				"<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
				    Iterator itrTDSPChargeRates = listTDSPChargeRates.iterator();
				    int count = 1;
					while(itrTDSPChargeRates.hasNext())
					{			
					    TDSPChargeRatesVO objTDSPChargeRatesVO = new TDSPChargeRatesVO();
						objTDSPChargeRatesVO = (TDSPChargeRatesVO)itrTDSPChargeRates.next();
						if(BuildConfig.DMODE)
						{
						    System.out.println("ChargeName:"+objTDSPChargeRatesVO.getTdspChargeName().getTdspChargeName()+"<===>"+"ChargeTypeName:"+objTDSPChargeRatesVO.getTdspChargeName().getChargeType().getChargeType());
						    System.out.println("ChargeValue:"+objTDSPChargeRatesVO.getCharge()+"<===>"+"ChargeTypeName:"+objTDSPChargeRatesVO.getTdspChargeName().getChargeType().getChargeType());
						}
						
						if(count==1) // if(objTDSPChargeRatesVO.getTdspChargeName().getChargeType().getChargeType().equalsIgnoreCase("Delivery Charges"))
						{
						    select +="<tr><td class='tbltitlebold' colspan='2'>Delivery Charges</td></tr>";
						}
						else if(count==6) // if(objTDSPChargeRatesVO.getTdspChargeName().getChargeType().getChargeType().equalsIgnoreCase("Distribution Charges"))
						{
						    select +="<tr><td class='tbltitlebold' colspan='2'>Distribution Charges</td></tr>";
						}
						else if(count==10) // if(objTDSPChargeRatesVO.getTdspChargeName().getChargeType().getChargeType().equalsIgnoreCase("Transmission Charges"))
						{
						    select +="<tr><td class='fieldtitle' colspan='2'>Transmission Charges</td></tr>";
						}
						else if(count==14) // if(objTDSPChargeRatesVO.getTdspChargeName().getChargeType().getChargeType().equalsIgnoreCase("Delivery Non-bypassable Charges"))
						{
						    select +="<tr><td class='tbltitlebold' colspan='2'>Delivery Non-bypassable Charges</td></tr>";
						}
						else if(count==17) // if(objTDSPChargeRatesVO.getTdspChargeName().getChargeType().getChargeType().equalsIgnoreCase("TC Charges"))
						{
						    select +="</table></td><td valign='top' width='50%'><table width='100%' border='0' cellspacing='0' cellpadding='0'>"+
						    		"<tr><td class='tbltitlebold' colspan='2'>TC Charges</td></tr>";
						}
						else if(count==19) // if(objTDSPChargeRatesVO.getTdspChargeName().getChargeType().getChargeType().equalsIgnoreCase("Excess Earning Credits"))
						{
						    select +="<tr><td class='tbltitlebold' colspan='2'>Excess Earning Credits</td></tr>";
						}
						else if(count==21) // if(objTDSPChargeRatesVO.getTdspChargeName().getChargeType().getChargeType().equalsIgnoreCase("Gross Receipts Fee"))
						{
						    select +="<tr><td class='tbltitlebold' colspan='2'>Gross Receipts Fee</td></tr>";
						}
						else if(count==22) // if(objTDSPChargeRatesVO.getTdspChargeName().getChargeType().getChargeType().equalsIgnoreCase("NMS and RR Credits"))
						{
						    select +="<tr><td class='tbltitlebold' colspan='2'>NMS and RR Credits</td></tr>";
						}
						else if(count==24) // if(objTDSPChargeRatesVO.getTdspChargeName().getChargeType().getChargeType().equalsIgnoreCase("Trans Cost Recovery Factor"))
						{
						    select +="<tr><td class='tbltitlebold' colspan='2'>Power Factor for kVA to kW %</td></tr>";
						    select +="<tr><td class='tbltitlebold' colspan='2'>Billing Demand Ratchet, %</td></tr>";
						    select +="<tr><td class='tbltitlebold' colspan='2'>Trans Cost Recovery Factor</td></tr>";
						}
											
						select +="<tr>"+
						"<td class='tbltitle'>"+objTDSPChargeRatesVO.getTdspChargeName().getTdspChargeName()+"</td>"+
						"<td width='1%' class='tbltitle'>:</td>"+
						"<td width='50%' class='fieldata'>"+tnf.format(objTDSPChargeRatesVO.getCharge())+"</td>"+
						"</tr>";
						count++;
					}
					select +="</table></td></tr></table>";
				}
				else
				{
				    select += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>" +
				    		"<tr><td>&nbsp;</td></tr>" +
				    		"</table>";
				}
	        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if(BuildConfig.DMODE)
            System.out.println("select:"+select);
        response.setContentType("*/*");
        response.getWriter().write(select);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        System.gc();
    }
}

/*
*$Log: TDSPChargesServlet.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.12  2007/06/12 10:44:57  spandiyarajan
*align double side
*
*Revision 1.11  2007/06/07 10:21:08  jnadesan
*selection changed
*
*Revision 1.10  2007/05/25 13:53:45  spandiyarajan
*tdsp bug fixed
*
*Revision 1.9  2007/05/16 11:31:16  spandiyarajan
*tdsp charges partially finished
*
*Revision 1.8  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.7  2007/05/07 07:31:26  spandiyarajan
*tdsp charges changed
*
*Revision 1.6  2007/04/23 05:52:20  kduraisamy
*unwanted println removed.
*
*Revision 1.5  2007/04/19 11:13:48  kduraisamy
*Set is changed to List.
*
*Revision 1.4  2007/04/17 13:52:16  spandiyarajan
*changed the tdsp charges
*
*Revision 1.3  2007/04/17 04:49:39  kduraisamy
*rateCodes set removed from TDSP.
*
*Revision 1.2  2007/04/16 05:17:52  jnadesan
*TDSP Chargesservlet partial work commit
*
*Revision 1.1  2007/04/12 13:47:59  spandiyarajan
*initial commit
*
*
*/