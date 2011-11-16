/*
 * Created on Jun 21, 2007
 * 
 * Class Name ContractExpireDateServlet.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pdf;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.dao.ContractsDAO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractExpireDateServlet extends HttpServlet{
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
    throws ServletException, IOException {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Date cpeExp = null;
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        
        try
        {
        String pricerunId = request.getParameter("pricerunId");
        String cntrStartdate = request.getParameter("StartDate");
        String expDate = request.getParameter("ExpDate");
        //int productId = Integer.parseInt(request.getParameter("productId"));
        String[] strDate = cntrStartdate.split("-");
        String str = strDate[2]+"/"+strDate[0]+"/"+strDate[1];
        Date cntrStart = new Date(str);
        strDate = expDate.split("-");
        str = strDate[2]+"/"+strDate[0]+"/"+strDate[1];
        cpeExp = new Date(str);
        String minutes = request.getParameter("ExpTime");
        StringTokenizer st = new StringTokenizer(minutes,":");
        if(st.hasMoreTokens())
            cpeExp.setHours(Integer.parseInt(st.nextToken()));
        if(st.hasMoreTokens())
            cpeExp.setMinutes(Integer.parseInt(st.nextToken()));
        if(request.getParameter("foramt").equalsIgnoreCase("1"))
            cpeExp.setHours(cpeExp.getHours()+12); 
        ContractsDAO objContractsDAO = new ContractsDAO();
        objContractsDAO.updateContractsExpAndStartsDates(Integer.parseInt(pricerunId),cntrStart,cpeExp);   
        
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        response.setContentType("*/*");
        response.getWriter().write("Exp Date: "+df.format(cpeExp));
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        System.gc();
      
    }
    
    
}

   



/*
*$Log: ContractExpireDateServlet.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/07/02 12:41:31  jnadesan
*expire date set as per pricerunCustomer wise
*
*Revision 1.2  2007/06/25 05:33:14  jnadesan
*proposal strat date and exp modification enabled only for analyst
*
*Revision 1.1  2007/06/23 08:02:27  jnadesan
*updating startDate and ExpDate
*
*
*/