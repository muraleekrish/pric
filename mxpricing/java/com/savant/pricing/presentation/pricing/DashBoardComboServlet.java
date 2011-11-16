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


/**
 * @author srajappan
 */
public class DashBoardComboServlet extends HttpServlet
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
    String tdsp =  request.getParameter("TDSP");
    PricingDAO pricingDAO = new PricingDAO();
    HashMap hmCongestionZones =null;
    String custRefId = request.getParameter("priceRunId");
    
    String select = "<select name=\"congestionZones\" onchange=\"callEsiIdCombo();servletaccess("+"'Zone'"+")\">"+"<option value=\""+0+"\">Select one</option>";
    if(tdsp.equalsIgnoreCase("0"))
        hmCongestionZones =  pricingDAO.getAllCongestionZones(Integer.parseInt(custRefId));
    else
        hmCongestionZones =  pricingDAO.getAllCongestionZones(Integer.parseInt(custRefId),Integer.parseInt(tdsp));
    Set set = hmCongestionZones.keySet();
    Iterator it = set.iterator();
    while(it.hasNext())
    {
        Object key = it.next();
        select += "<option value=\""+key +"\">"+hmCongestionZones.get(key)+"</option>";
    }
    select +="</select>";
    String selectEsiid = "<div id ='comboEsiid' style='overflow:auto; position:absolute; top:"+request.getParameter("divTopCurPos")+"px; left:"+request.getParameter("divLeftCurPos")+"px; display:none;'><select name=\"esiids\" multiple  size = '10' onchange=\"servletaccess("+"'false'"+")\">"+"<option value=\""+0 +"\">All</option>";
    HashMap hmEsiid =  getEsiid(request);
    set = hmEsiid.keySet();
    it = set.iterator();
    while(it.hasNext())
    {
        Object key = it.next();
        selectEsiid += "<option value=\""+key +"\">"+hmEsiid.get(key)+"</option>";
    }
    selectEsiid +="</select></div>";
    
    response.setContentType("*/*");
    response.setHeader("Cache-Control", "no-cache");
    response.getWriter().write(select+"@#@#"+selectEsiid+"@#@#"+getEsiidstr(hmEsiid));
}
private HashMap getEsiid(HttpServletRequest request)
{
    PricingDAO objPricingDAO = new PricingDAO();
    String tdsp = request.getParameter("TDSP");
    String custRefId = request.getParameter("priceRunId");
    HashMap mapEsiid = new HashMap();
    
    if(tdsp.equalsIgnoreCase("0"))
    {
        mapEsiid = objPricingDAO.getAllEsiIds(Integer.parseInt(custRefId));
    }
    else
    {
        mapEsiid = objPricingDAO.getAllEsiIds(Integer.parseInt(custRefId),Integer.parseInt(tdsp));
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
