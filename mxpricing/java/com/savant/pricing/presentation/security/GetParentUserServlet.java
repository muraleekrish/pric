/*
 * Created on Apr 25, 2007
 *
 * ClassName	:  	GetParentUserServlet.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.security;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.securityadmin.dao.UserDAO;
import com.savant.pricing.securityadmin.valueobject.BOSSUsersVO;
import com.savant.pricing.securityadmin.valueobject.UsersVO;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GetParentUserServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)throws ServletException, IOException 
    {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(BuildConfig.DMODE)
        {
            System.out.println("userTypeId :"+request.getParameter("userTypeId"));
            System.out.println("Message :"+request.getParameter("formAction"));
        }
		String message = request.getParameter("formAction");
        UserDAO objUserDAO = new UserDAO();
        String select = message+"@#$";
        try
        {
            if(message.equalsIgnoreCase("userTypeChange"))
            {
                String usrTypId =  request.getParameter("userTypeId");
                if(Integer.parseInt(usrTypId)>=0)
                {
                    select += "<select name=\"parentUserid\">"+"<option value=\""+0+"\">Select one</option>";
                    try
                    {                    
                        List objLst = null;
                        objLst = (List)objUserDAO.getParentTypeUsers(Integer.parseInt(usrTypId));
                        Iterator itr = objLst.iterator();
                        while(itr.hasNext())
                        {
                            UsersVO objUsersVO = (UsersVO)itr.next();
                            String lName = String.valueOf(objUsersVO.getLastName());
                            lName = lName.equals("null")?" ":lName;
                            String parentName = objUsersVO.getFirstName()+" "+lName;
                            select += "<option value=\""+objUsersVO.getUserId()+"\">"+parentName+"</option>";
                        }
                        select +="</select>";
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }   
            }
            else if(message.equalsIgnoreCase("userIdChange") && !request.getParameter("userId").equalsIgnoreCase(""))
            {
                String usrId =  request.getParameter("userId");
                if(!usrId.equals(""))
                {
                    try
                    {                    
                        BOSSUsersVO objBOSSUsersVO = null;
                        objBOSSUsersVO = objUserDAO.getBOSSUser(usrId);
                        String usrFirstName = objBOSSUsersVO.getUserFirstName().trim()==null?"":objBOSSUsersVO.getUserFirstName().trim();
                        String usrLastName = objBOSSUsersVO.getUserLastName().trim()==null?"":objBOSSUsersVO.getUserLastName().trim();
                        String usrEmail = objBOSSUsersVO.getEmail().trim()==null?"":objBOSSUsersVO.getEmail().trim();
                        select += usrFirstName+"@#$"+usrLastName+"@#$"+usrEmail;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }   
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
*$Log: GetParentUserServlet.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/23 05:29:12  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.8  2007/07/31 11:55:33  kduraisamy
*unwanted imports removed.
*
*Revision 1.7  2007/07/17 14:02:32  sramasamy
*error fixed in user add modify update
*
*Revision 1.6  2007/06/14 04:12:17  spandiyarajan
*code aligned properly
*
*Revision 1.5  2007/06/13 13:44:42  spandiyarajan
*trim added
*
*Revision 1.4  2007/06/12 07:31:13  spandiyarajan
*alteration in user add & modify page
*
*Revision 1.3  2007/05/25 10:06:10  jnadesan
*unwanted lines removed
*
*Revision 1.2  2007/05/07 07:32:27  spandiyarajan
*choose option properly chaged to 'select one'
*
*Revision 1.1  2007/04/30 05:42:11  spandiyarajan
*added getparentuser servlet - initial commit
*
*
*/