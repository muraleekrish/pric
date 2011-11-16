/*
 * Created on Nov 29, 2007
 *
 * ClassName	:  	MrktCmmntryServlet.java
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.dao.MarketCommentryDAO;


/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MrktCmmntryServlet extends HttpServlet
{

    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)throws ServletException, IOException 
    {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
       
        String retResult = "0";
        MarketCommentryDAO commentryDAO = new MarketCommentryDAO();
        try
        {
            
            String mrktDate =  request.getParameter("marketDate");
            System.out.println("************** market date :"+mrktDate);
            String arr[] = mrktDate.split("-");
            String passDate = arr[2]+"-"+arr[0]+"-"+arr[1];
            int result = 0;
           
            if(!mrktDate.equals(""))
            {
                try
                {      
                    System.out.println("************** passDate :"+passDate);
                    result = commentryDAO.checkDate(passDate);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                retResult = String.valueOf(result);
            }   
            System.out.println("************ result :" + result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        if(BuildConfig.DMODE)
            System.out.println("select:"+retResult);
        response.setContentType("*/*");
        response.getWriter().write(retResult);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        System.gc();
    }


}

/*
*$Log: MrktCmmntryServlet.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/11/29 10:19:55  tannamalai
*add validation done
*
*
*/