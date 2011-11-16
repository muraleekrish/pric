/*
 * Created on Apr 7, 2007
 *
 * ClassName	:  	UpdateOverride.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.pconfig.deallevers;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.calculation.dao.DealLeversDAO;
import com.savant.pricing.common.BuildConfig;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UpdateOverride extends HttpServlet
{
    private ServletContext context;
    public void init(ServletConfig config) throws ServletException 
    {
        this.context= config.getServletContext();
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DealLeversDAO objDealLeversDAO  = new DealLeversDAO();
        boolean updateResult = false;
        boolean isOverride = false;
        if(request.getParameter("override").equalsIgnoreCase("true"))
            isOverride = true;
        String resp = request.getParameter("override"); 
        try 
        {
            updateResult = objDealLeversDAO.overRide(isOverride);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }        
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        if(BuildConfig.DMODE)
            System.out.println("The updateResult : "+updateResult);
        response.getWriter().write(resp);
    }
}

/*
*$Log: UpdateOverride.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:30  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/06/12 12:57:53  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.2  2007/04/23 05:52:20  kduraisamy
*unwanted println removed.
*
*Revision 1.1  2007/04/07 09:39:24  spandiyarajan
*deal levers committed after the override operation
*
*
*/