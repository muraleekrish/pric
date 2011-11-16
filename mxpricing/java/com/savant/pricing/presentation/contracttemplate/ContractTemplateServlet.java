/*
 * Created on Apr 8, 2007
 * 
 * Class Name ContractTemplateServlet.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.contracttemplate;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.calculation.dao.ReportsDAO;
import com.savant.pricing.calculation.valueobjects.ReportsParamVO;
import com.savant.pricing.calculation.valueobjects.ReportsParamValuesVO;
import com.savant.pricing.common.BuildConfig;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractTemplateServlet extends HttpServlet
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
        if(BuildConfig.DMODE)
            System.out.println("report :"+request.getParameter("Message"));
        String message = request.getParameter("Message");
        ReportsDAO objReportsDAO = new ReportsDAO();
        ReportsParamVO objReportsParamVO = new ReportsParamVO();
        ReportsParamValuesVO objReportsParamValuesVO = null;
        String select = message+"@@@";
        
        if(message.equalsIgnoreCase("report"))
        {
            String report =  request.getParameter("Report");
            select += "<select name=\"reportParam\" onchange=\"viewparam()\">"+"<option value=\""+0+"\">Select one</option>";
            try
            {
                List lstParam = objReportsDAO.getAllReportParams(Integer.parseInt(report));
                Iterator it = lstParam.iterator();
                while(it.hasNext())
                {
                    objReportsParamVO  = (ReportsParamVO)it.next();
                    select += "<option value=\""+objReportsParamVO.getReportParamIdentifier() +"\">"+objReportsParamVO.getReportParamName()+"</option>";
                }
                select +="</select>";
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(message.equalsIgnoreCase("param"))
        {
            String param =  request.getParameter("Param");
            objReportsParamValuesVO = objReportsDAO.getReportParamValue(Integer.parseInt(param));
            if(BuildConfig.DMODE)
                System.out.println(objReportsParamValuesVO.getReportParamValue());
            select += objReportsParamValuesVO.getReportParamValue();
        }
        if(BuildConfig.DMODE)
            System.out.println("select:"+select);
        response.setContentType("*/*");
        response.getWriter().write(select);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        System.gc();
    }
}


/*
*$Log: ContractTemplateServlet.java,v $
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:30  jnadesan
*initail MXEP commit
*
*Revision 1.6  2007/06/12 12:57:53  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.5  2007/05/07 07:30:50  spandiyarajan
*choose option properly chaged to 'select one'
*
*Revision 1.4  2007/04/24 07:35:15  jnadesan
*search option and other bugs solved
*
*Revision 1.3  2007/04/23 05:52:20  kduraisamy
*unwanted println removed.
*
*Revision 1.2  2007/04/18 03:56:32  kduraisamy
*imports organized.
*
*Revision 1.1  2007/04/08 15:10:18  jnadesan
*initial commit for contract template
*
*
*/