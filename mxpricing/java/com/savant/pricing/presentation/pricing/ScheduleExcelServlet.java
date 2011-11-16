/*
 * Created on Aug 22, 2007
 * 
 * Class Name ScheduleExcelServlet.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.calculation.dao.DealLeversDAO;
 
/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ScheduleExcelServlet extends HttpServlet{
    
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)throws ServletException, IOException
    {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        { 
            byte[] b = null;
            
            ExportScheduleintoExcel objExportScheduleintoExcel = new ExportScheduleintoExcel();
            //if(request.getParameter("User").equalsIgnoreCase("Analyst"))
                b = objExportScheduleintoExcel.getSchedule("Analyst",this.getScheduleDetails(request));

            response.setHeader("Content-Disposition", "attachment;filename=\""
                    + "Schedule.xls" + "\";");
            response.setContentType("application");
            response.setContentLength(b.length);
            ServletOutputStream servletoutputstream = response.getOutputStream();
            servletoutputstream.write(b, 0, b.length);
            servletoutputstream.flush();
            servletoutputstream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private LinkedHashMap getScheduleDetails(HttpServletRequest request)
    {
        LinkedHashMap lhmSchedule = null;
        DealLeversDAO objDealLeversDAO = new DealLeversDAO();
        int custId = 0; 
        int custStatus = 0;
        
        if(!request.getParameter("CMSId").equals("") && ! (request.getParameter("CMSId")==null))
        {
            custId = Integer.parseInt(request.getParameter("CMSId"));
        }
        if(!request.getParameter("CDRStatus").equals("") && ! (request.getParameter("CDRStatus")==null))
        {
            custStatus = Integer.parseInt(request.getParameter("CDRStatus"));
        }
        int autoRun = Integer.parseInt(request.getParameter("MXAutoRun"));
        
        lhmSchedule = objDealLeversDAO.getDealLeverDetails(custId,custStatus,autoRun);
        
        return lhmSchedule;
    }
}

/*
*
*
*/