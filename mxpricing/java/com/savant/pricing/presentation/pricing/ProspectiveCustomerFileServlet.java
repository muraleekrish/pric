/*
 * Created on Aug 9, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.savant.pricing.dao.CustomerFileDAO;

/**
 * @author rraman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProspectiveCustomerFileServlet extends HttpServlet 
{
	CustomerFileDAO objCustomerFileDAO = new CustomerFileDAO();
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
    throws ServletException, IOException {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{
		String fileName = request.getParameter("fileName");
		int fileId = Integer.parseInt(request.getParameter("fileId"));
		byte[] fileContent = null;
        try 
        {
            fileContent = objCustomerFileDAO.getFileAlt(fileId, fileName);
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\";");
        response.setContentType("application");
        ServletOutputStream servletoutputstream = response.getOutputStream();
        servletoutputstream.write(fileContent,0,fileContent.length);
        servletoutputstream.flush();
        servletoutputstream.close();
	}
}