/*
 * Created on Feb 13, 2006
 *
 * ClassName	:  	ProfileSelectionServlet.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India
 *
 */
package com.savant.pricing.presentation.pricing;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.calculation.dao.PricingDAO;
import com.savant.pricing.common.BuildConfig;


/**
 * @author srajappan
 */
public class DashBoardComboESIIDServlet extends HttpServlet
{
/* (non-Javadoc)
 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 */
protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)throws ServletException, IOException 
{
    super.doPost(arg0, arg1);
    this.doGet(arg0,arg1);
}
public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
    String congestionZones =  request.getParameter("congestionZones");
    if(BuildConfig.DMODE)
        System.out.println("congestionZones Is:"+congestionZones);
    HashMap hmEsiid =null;
    String select = "<div id ='comboEsiid' style='overflow:auto; position:absolute; top:"+request.getParameter("divTopCurPos")+"px; left:"+request.getParameter("divLeftCurPos")+"px; display:none;'><select name=\"esiids\" onchange=\"servletaccess("+"'false'"+")\" multiple  size = '10' >"+"<option value=\""+0+"\">All</option>";
    
    hmEsiid =  getEsiid(request);
    Set set = hmEsiid.keySet();
    Iterator it = set.iterator();
    while(it.hasNext())
    {
        Object key = it.next();
        select += "<option value=\""+key +"\">"+hmEsiid.get(key)+"</option>";
    }
    select +="</select></div>@@@"+getEsiidstr(hmEsiid);      
    response.setContentType("*/*");
    response.getWriter().write(select);
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0L);
    System.gc();
}
private HashMap getEsiid(HttpServletRequest request)
{
    PricingDAO objPricingDAO = new PricingDAO();
    String tdsp = request.getParameter("TDSP");
    String zone = request.getParameter("Zone");
    String custRefId = request.getParameter("priceRunId");
    if(BuildConfig.DMODE)
        System.out.println(" TDSP "+tdsp+" Zone "+zone);
    HashMap mapEsiid = new HashMap();
     
            if(zone.equalsIgnoreCase("0"))
            {
                if(tdsp.equalsIgnoreCase("0"))
                {
                    mapEsiid = objPricingDAO.getAllEsiIds(Integer.parseInt(custRefId));
                }
                else
                {
                    mapEsiid = objPricingDAO.getAllEsiIds(Integer.parseInt(custRefId),Integer.parseInt(tdsp));
                }
            }
            else
            {
                if(tdsp.equalsIgnoreCase("0"))
                {
                    mapEsiid = objPricingDAO.getAllEsiIdsByCongestionZone(Integer.parseInt(custRefId),Integer.parseInt(zone));
                }
                else
                {
                    mapEsiid = objPricingDAO.getAllEsiIdsByTDSPCongestionZone(Integer.parseInt(custRefId),Integer.parseInt(tdsp),Integer.parseInt(zone));
                }
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
