/*
 * Created on Mar 13, 2007
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India
 *
 */
package com.savant.pricing.tdp.dao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 * @author kchellamuthu
 *
 */
public class ImportDataServlet extends HttpServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request,response);		
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IDRProfilerManger objManager = new IDRProfilerManger();
		String output = "failed";
		String tdspType = "";
		try
		{
			if(request.getParameter("tdspType")!=null)
				tdspType = request.getParameter("tdspType");
		 
			
			boolean result = objManager.moveTypicalDayDataToSrcTbl(tdspType);
			if(result)
				output = "success";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		response.setContentType("*/*");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(output);
	}
	
}
