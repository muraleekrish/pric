/*
 * Created on Jul 6, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.dao.ProspectiveCustomerDAO;

/**
 * @author sramasamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UpdateAutoRun extends HttpServlet
{
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)throws ServletException, IOException 
    {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean updateResult = false;
        boolean autoRun = false;
        if(request.getParameter("autoRun").equalsIgnoreCase("yes"))
            autoRun = true;
        int custId = Integer.parseInt(request.getParameter("cusId"));
        try 
        {
            ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
            updateResult = objProspectiveCustomerDAO.updatecustPreference(autoRun,custId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }        
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        if(BuildConfig.DMODE)
            System.out.println("The updateResult : "+updateResult);
        response.getWriter().write("");
    }
}
