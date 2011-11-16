/*
 * Created on Apr 5, 2007
 *
 * ClassName	:  	UserTypeListAction.java
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
import org.hibernate.HibernateException;

import com.savant.pricing.securityadmin.dao.UserTypeDAO;
import com.savant.pricing.securityadmin.valueobject.UserTypesVO;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserTypeListAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String action="failure";		
		ActionErrors actionErrors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        boolean result=false;
        
        if(form instanceof UserTypeListForm)
        {
            UserTypeListForm frm = (UserTypeListForm)form;
            UserTypeDAO userTypeDAO = new UserTypeDAO();  
            UserTypesVO userTypesVO = null;
			if(frm.getFormAction().trim().equalsIgnoreCase("delete"))
	        {
			    try
                {
		            String temp[] = request.getParameter("userTypeid").split(","); 
		            for (int i = 0; i < temp.length; i++) 
		            {
		               int  userTypeid = Integer.parseInt(temp[i]);
		               userTypesVO = userTypeDAO.getUserType(userTypeid);
		               result = userTypeDAO.deleteUserType(userTypesVO);    
		               if(result)
		               {
		                   messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.delete.message","User Type",userTypesVO.getUserType()));
		                   saveMessages(request,messages);
		                   request.setAttribute("message","message");
		               }
		           }
                }
			    catch(HibernateException e)
                {
                    if(e.toString().indexOf("hibernate.exception.ConstraintViolationException")!= -1)
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.usertypecannotbedelete","Parent User Type",userTypesVO.getUserType()));
                        if(!actionErrors.isEmpty())
                        {
                            saveErrors(request,actionErrors);
                            request.setAttribute("message","error");
                        }
                    }
                    else
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.generalerror"));
                        if(!actionErrors.isEmpty())
                        {
                            saveErrors(request,actionErrors);
                            request.setAttribute("message","error");
                        }
                    }
                }   		
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
*$Log: UserTypeListAction.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.6  2007/05/22 13:19:16  spandiyarajan
*set the message attribute for set the div height in list page
*
*Revision 1.5  2007/05/02 09:11:39  spandiyarajan
*removed unwanted s.o.p
*
*Revision 1.4  2007/04/13 05:01:03  kduraisamy
*unwanted println commented.
*
*Revision 1.3  2007/04/11 10:31:28  spandiyarajan
*throw error msg when parent usertype delete(if have constraints)
*
*Revision 1.2  2007/04/05 14:04:37  spandiyarajan
*fixed the bug in user type
*
*Revision 1.1  2007/04/05 13:38:29  spandiyarajan
*fixed the bug in user type
*
*
*/