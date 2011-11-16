/*
 * Created on Apr 6, 2007
 *
 * ClassName	:  	UserListAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.savant.pricing.securityadmin.dao.UserDAO;
import com.savant.pricing.securityadmin.valueobject.UserGroupVO;
import com.savant.pricing.securityadmin.valueobject.UsersVO;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserListAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{    
        String action="failure";		
		ActionErrors actionErrors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        boolean result=false;
	    
	    if(form instanceof UserListForm)
        {
	        UserListForm frm = (UserListForm)form;
	        UserDAO usersDAO = new UserDAO();
	        
	        String userName = (String) request.getSession().getAttribute("userName");
            String userId   = request.getParameter("userid");
            
	        if(!userName.equalsIgnoreCase(userId))
	        {
	            if(frm.getFormAction().trim().equalsIgnoreCase("delete"))        
	            {	            
	                UsersVO usersVO = null;
	                UserGroupVO usrGrpVO = null;
	                String temp[] = request.getParameter("userid").split(",");
	                boolean deleteResult=false;
	                for (int i = 0; i < temp.length; i++) 
	                { 
	                    try 
	                    { 
	                        usersVO = usersDAO.getUsers(temp[i].trim());
	                        deleteResult = usersDAO.deleteUser(usersVO);   
	                        if(deleteResult)
	                        {
	                            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.delete.message","User",usersVO.getUserId()));
	                            saveMessages(request,messages);
	                            request.setAttribute("message","message");
	                        }
	                    } 
	                    catch (Exception e) 
	                    {
	                        actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Common.error.inuse","User",usersVO.getUserId()));
	                        saveErrors(request,actionErrors);
	                        request.setAttribute("message","error");
	                        frm.setFormAction("list");
	                    }
	                }
	            }
	        }
	        else
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.loginuser","User",userId, "deleted"));
                saveErrors(request,actionErrors);
                request.setAttribute("message","error");
                frm.setFormAction("list");
	        }
        }
	    if(result)
        {
            action = "success";
        }
		return mapping.findForward(action);		
	}
}

/*
*$Log: UserListAction.java,v $
*Revision 1.2  2009/01/27 05:47:57  tannamalai
*changes - according to MX server
*
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/07/30 06:21:48  spandiyarajan
*throw exception
*
*Revision 1.4  2007/07/17 14:02:32  sramasamy
*error fixed in user add modify update
*
*Revision 1.3  2007/05/22 13:19:16  spandiyarajan
*set the message attribute for set the div height in list page
*
*Revision 1.2  2007/04/24 13:58:03  jnadesan
*errors are saved to show in UI
*
*Revision 1.1  2007/04/06 13:33:26  spandiyarajan
*fix the  bug in user
*
*
*/