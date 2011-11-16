/*
 * Created on Jun 23, 2006
 *
 * ClassName	:  	RoleListAction.java
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

import com.savant.pricing.securityadmin.dao.RolesDAO;
import com.savant.pricing.securityadmin.valueobject.RolesVO;

/**
 * @author kduraisamy
 *
 */
public class RoleListAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{    
        String action="failure";		
		ActionErrors actionErrors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        boolean deleteResult=false;
	    
	    if(form instanceof RoleListForm)
        {
	        RoleListForm frm = (RoleListForm)form;
	        RolesDAO rolesDAO = new RolesDAO();
	        if(frm.getFormAction().trim().equalsIgnoreCase("delete"))        
	        {	            
	            RolesVO rolesVO = new RolesVO();
	            String ids = request.getParameter("roleid").trim();	            
	            String roleName = request.getParameter("roleName").trim();
                rolesVO.setGroupId(Integer.parseInt(ids));	            
	            try 
	            {
	                deleteResult = rolesDAO.deleteRole(rolesVO);   
	                if(deleteResult)
	                {
	                    messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.delete.message","Role",roleName));
	                    saveMessages(request,messages);   
	                    request.setAttribute("message","message");
	                }
	            } 
	            catch (Exception e) 
	            {
	                actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Common.error.inuse","Role",roleName));
	                frm.setFormAction("list");
	                saveErrors(request,actionErrors);
	                request.setAttribute("message","error");
	            }	           
	        }
        }
	    if(deleteResult)
        {
            action = "success";
        }
		return mapping.findForward(action);		
	}
}

/*
*$Log: RoleListAction.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.7  2007/07/17 14:02:32  sramasamy
*error fixed in user add modify update
*
*Revision 1.6  2007/05/22 13:19:16  spandiyarajan
*set the message attribute for set the div height in list page
*
*Revision 1.5  2007/05/02 09:11:39  spandiyarajan
*removed unwanted s.o.p
*
*Revision 1.4  2007/04/24 13:58:03  jnadesan
*errors are saved to show in UI
*
*Revision 1.3  2007/04/07 11:54:25  spandiyarajan
*committed for role
*
*Revision 1.2  2007/04/07 11:28:46  spandiyarajan
*committed for role
*
*Revision 1.1  2007/03/08 04:54:21  srajappan
*role base menu added
*
*Revision 1.1  2006/06/23 12:05:05  kduraisamy
*Role List Completed
*
*
*/