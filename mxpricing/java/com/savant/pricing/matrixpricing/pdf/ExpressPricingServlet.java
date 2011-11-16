/*
 * @ ExpressPricingServlet.java	Aug 28, 2007
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. 
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use it 
 * only in accordance with the terms of the license agreement you entered into with Savant Technologies Pvt Ltd.
 * 
 */
 
package com.savant.pricing.matrixpricing.pdf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.matrixpricing.dao.MMCustomersPDFDAO;
import com.savant.pricing.pdf.ValidateString;

/*
 * Class description goes here.
 * 
 * @author	spandiyarajan
 * 
 */

public class ExpressPricingServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
    throws ServletException, IOException {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        MMCustomersPDFDAO objMMCustomersPDFDAO = new MMCustomersPDFDAO();
        String referNo = request.getParameter("referNo");
		byte[] bytes = objMMCustomersPDFDAO.getFile(referNo);
		
		response.setHeader("Content-Disposition", "attachment;filename=\"" +ValidateString.checkMetaCharacters(referNo)+"_Xpress_Pricing.pdf" + "\";");
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);
        ServletOutputStream servletoutputstream = response.getOutputStream();
        servletoutputstream.write(bytes, 0, bytes.length);
        servletoutputstream.flush();
        servletoutputstream.close();
    }
}


/*
*$Log: ExpressPricingServlet.java,v $
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/08/30 12:37:17  spandiyarajan
*makepdf changes
*
*
*/