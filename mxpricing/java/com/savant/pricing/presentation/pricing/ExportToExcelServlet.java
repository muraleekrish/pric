/*
 * Created on Jun 7, 2007
 * 
 * Class Name ExportToExcelServlet.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.common.Filter;
import com.savant.pricing.common.FilterHandler;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
 
/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExportToExcelServlet  extends HttpServlet{
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
        try
        { 
            byte[] b = null;
            
            ExportCustomersintoExcel objExportCustomersintoExcel = new ExportCustomersintoExcel();
            if(request.getParameter("User").equalsIgnoreCase("Analyst"))
                b = objExportCustomersintoExcel.getCustomers("Analyst",this.getCustomersByAnalyst(request));
            else if(request.getParameter("User").equalsIgnoreCase("Manager"))
                b = objExportCustomersintoExcel.getCustomers("Manager",this.getCustomersByManager(request));
            else if((request.getParameter("User").equalsIgnoreCase("Rep")))
                b = objExportCustomersintoExcel.getCustomers("Rep",this.getRepCustomers(request));
            
            response.setHeader("Content-Disposition", "attachment;filename=\""
                    + "Customers.xls" + "\";");
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
    private HashMap getRepCustomers(HttpServletRequest request)
    {
        HashMap hmCustomers = null;
        FilterHandler objFilterHandler = new FilterHandler();
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        String userName = String.valueOf(request.getSession().getAttribute("userName"));
        int custId = 0; 
        int custStatus = 0;
        int cdrStatus = 0;
        boolean order=true;
        int autoRun = Integer.parseInt(request.getParameter("autoRun"));
        if(!request.getParameter("sort").equals("0"))
            order = false;
        
        if(!request.getParameter("cmsId").equals("") && ! (request.getParameter("cmsId")==null))
        {
            custId = Integer.parseInt(request.getParameter("cmsId"));
        }
        if(!request.getParameter("status").equals("") && ! (request.getParameter("status")==null))
        {
            custStatus = Integer.parseInt(request.getParameter("status"));
        }
        if(!request.getParameter("CDRstatus").equals("") && ! (request.getParameter("CDRstatus")==null))
        {
            cdrStatus = Integer.parseInt(request.getParameter("CDRstatus"));
        }
        
        Filter fil[] =null;
        Filter newfilter  = null;
        
        Vector filter = new Vector();
        if (!request.getParameter("Cust").trim().equalsIgnoreCase("")&& !(request.getParameter("Cust")==null))
        {
            filter = new Vector();
            filter = objFilterHandler.setFormDetails("customerName",request.getParameter("Cust").trim(),request.getParameter("custSrch"),filter);
            fil = new Filter[filter.size()];
            filter.copyInto(fil);
            newfilter = fil[0];
        }
        hmCustomers = objProspectiveCustomerDAO.getAllProspectiveCustomerBySalesRep(userName,newfilter,custId,custStatus,cdrStatus,"customerName",order,-1,-1,autoRun);
        
        return hmCustomers;
    }
    private HashMap getCustomersByAnalyst(HttpServletRequest request)
    {
        HashMap hmCustomers = null;
        FilterHandler objFilterHandler = new FilterHandler();
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        int custId = 0; 
        int custStatus = 0;
        boolean order=true;
        if(!request.getParameter("sort").equals("0"))
            order = false;
        
        if(!request.getParameter("cmsId").equals("") && ! (request.getParameter("cmsId")==null))
        {
            custId = Integer.parseInt(request.getParameter("cmsId"));
        }
        if(!request.getParameter("status").equals("") && ! (request.getParameter("status")==null))
        {
            custStatus = Integer.parseInt(request.getParameter("status"));
        }
        
        int autoRun = Integer.parseInt(request.getParameter("autoRun"));
        Filter fil[] =null;
        Filter newfilter  = null;
        Filter Repfilter  = null;
        Filter Mngrfilter  = null;
        
        Vector filter = new Vector();
        if (!request.getParameter("Cust").trim().equalsIgnoreCase("")&& !(request.getParameter("Cust")==null))
        {
            filter = new Vector();
            filter = objFilterHandler.setFormDetails("customerName",request.getParameter("Cust").trim(),request.getParameter("custSrch"),filter);
            fil = new Filter[filter.size()];
            filter.copyInto(fil);
            newfilter = fil[0];
        }
        if (!request.getParameter("Rep").trim().equalsIgnoreCase("") && (request.getParameter("Rep") != null))
        {
            filter = new Vector();
            filter = objFilterHandler.setFormDetails("",request.getParameter("Rep"),request.getParameter("repSrch"),filter);
            fil = new Filter[filter.size()];
            filter.copyInto(fil);
            Repfilter = fil[0];
        }
        if (!request.getParameter("mang").trim().equalsIgnoreCase("")&& !(request.getParameter("mang")==null))
        {
            filter = new Vector();
            filter = objFilterHandler.setFormDetails("",request.getParameter("mang"),request.getParameter("mangSrch"),filter);
            fil = new Filter[filter.size()];
            filter.copyInto(fil);
            Mngrfilter = fil[0];
        }
        
        String strCrFromDt =  request.getParameter("crFromDt")==null?"":request.getParameter("crFromDt");
        String strCrToDt =  request.getParameter("crToDt")==null?"":request.getParameter("crToDt");
        String strMdFromDt =  request.getParameter("mdFromDt")==null?"":request.getParameter("mdFromDt");
        String strMdToDt =  request.getParameter("mdToDt")==null?"":request.getParameter("mdToDt");
        hmCustomers = objProspectiveCustomerDAO.getAllProspectiveCustomer(newfilter,Repfilter,Mngrfilter,custId,custStatus,0, strCrFromDt, strCrToDt, strMdFromDt, strMdToDt, "customerName",order,-1,-1,autoRun,request.getParameter("MMCust"));
        
        return hmCustomers;
    }
    
    private HashMap getCustomersByManager(HttpServletRequest request)
    {
        HashMap hmCustomers = null;
        FilterHandler objFilterHandler = new FilterHandler();
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        int custId = 0; 
        int custStatus = 0;
        int cdrStatus = 0;
        String userName = String.valueOf(request.getSession().getAttribute("userName"));
        
        if(!request.getParameter("cmsId").equals("") && ! (request.getParameter("cmsId")==null))
        {
            custId = Integer.parseInt(request.getParameter("cmsId"));
        }
        if(!request.getParameter("status").equals("") && ! (request.getParameter("status")==null))
        {
            custStatus = Integer.parseInt(request.getParameter("status"));
        }
        if(!request.getParameter("CDRstatus").equals("") && ! (request.getParameter("CDRstatus")==null))
        {
            cdrStatus = Integer.parseInt(request.getParameter("CDRstatus"));
        }
        int autoRun = Integer.parseInt(request.getParameter("autoRun"));
        
        Filter fil[] =null;
        Filter newfilter  = null;
        Filter Repfilter  = null;
        Vector filter = new Vector();
        if (!request.getParameter("Cust").trim().equalsIgnoreCase("")&& !(request.getParameter("Cust")==null))
        {
            filter = new Vector();
            filter = objFilterHandler.setFormDetails("customerName",request.getParameter("Cust").trim(),request.getParameter("custSrch"),filter);
            fil = new Filter[filter.size()];
            filter.copyInto(fil);
            newfilter = fil[0];
        }
        if (!request.getParameter("Rep").trim().equalsIgnoreCase("") && (request.getParameter("Rep")!= null))
        {
            filter = new Vector();
            filter = objFilterHandler.setFormDetails("",request.getParameter("Rep"),request.getParameter("repSrch"),filter);
            fil = new Filter[filter.size()];
            filter.copyInto(fil);
            Repfilter = fil[0];
        }
        //(String.valueOf(session.getAttribute("userName")),Repfilter,newFilter,Integer.parseInt(frm.getTxtCustomerId()),Integer.parseInt(frm.getCmbCustomerStatus()),Integer.parseInt(frm.getCmbCDRStatus()),((pageCount-1)*maxItems),maxItems);
        hmCustomers = objProspectiveCustomerDAO.getAllProspectiveCustomerBySalesManager(userName,Repfilter,newfilter,custId,custStatus,cdrStatus,-1,-1,autoRun);
        
        return hmCustomers;
    }
    
}

/*
*$Log: ExportToExcelServlet.java,v $
*Revision 1.2  2009/05/07 12:18:20  tannamalai
*Final Commit
*
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:49  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.6  2007/09/07 05:33:00  jnadesan
*MM customers also imported into excel
*
*Revision 1.5  2007/08/23 14:34:52  jnadesan
*restricted viewing both MM customers with normal customers
*
*Revision 1.4  2007/07/24 14:04:27  kduraisamy
*Auto run filter added
*
*Revision 1.3  2007/07/23 11:01:11  spandiyarajan
*added 4 more parameters. createdfromdate, createdtodate, modifiedfromdate and modifiedtodate.
*
*Revision 1.2  2007/07/05 07:30:17  jnadesan
*filter by sales rep problem solved
*
*Revision 1.1  2007/06/11 05:39:15  jnadesan
*excel option added for all users
*
*
*/