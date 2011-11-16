/*
 * Created on Jun 27, 2006
 *
 * ClassName	:  	RoleEditAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.security;

import java.util.Iterator;
import java.util.List;

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

import com.savant.pricing.securityadmin.dao.MenuItemDao;
import com.savant.pricing.securityadmin.dao.RolesDAO;
import com.savant.pricing.securityadmin.valueobject.RoleMenuItemVO;
import com.savant.pricing.securityadmin.valueobject.RolesVO;


/**
 * @author kduraisamy
 *
 */
public class RoleEditAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String action="failure";
        if (form instanceof NewRoleAddForm)
        {
            NewRoleAddForm frm = (NewRoleAddForm)form;
            ActionErrors errors = new ActionErrors();
            boolean defaultRole = false;
            String loginUserName = "";
            String loginUserRole = "";
            
            RolesDAO  objRolesDAO = new RolesDAO();
            MenuItemDao objMenuItemDao = new MenuItemDao();
            try
            {
                if (frm.getFormAction().equalsIgnoreCase("view")) {
                    action = "view";
                }
                else if (!defaultRole)
                {
                    if (frm.getFormAction().equalsIgnoreCase("view"))
                    {
                        action = "view";
                    }
                    else if((frm.getFormAction()).equalsIgnoreCase("edit"))
                    { 
                        RolesVO objRoleDetails = new RolesVO();
                        objRoleDetails = objRolesDAO.getRoles(request.getParameter("roleid"));
                        
                        loginUserName = (String) request.getSession().getAttribute("userName");
                        loginUserRole = objRolesDAO.getRoleName(loginUserName).trim();
                        
                        System.out.println("loginUserRole: " + loginUserRole);
                        System.out.println("objRoleDetails.getGroupName() : " + objRoleDetails.getGroupName());
                        
                        if(loginUserRole.equalsIgnoreCase(objRoleDetails.getGroupName()))
                        {
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.rolevalidation"));
                            saveErrors(request, errors);
        	                request.setAttribute("message", "error");
                            action="success";
                        }
                        else
                        {
	                        frm.setRoleName(objRoleDetails.getGroupName());
	                        frm.setUserRoleDesc(objRoleDetails.getDescription());
	                        frm.setRoleId(Integer.parseInt(request.getParameter("roleid")));
	                        List objList = objMenuItemDao.getMenuItems(objRoleDetails.getGroupId());
	                        String[] resourceId =  new String[objList.size()];
	                        Iterator itr = objList.iterator();
	                        int i=0;
	                        while(itr.hasNext())
	                        {
	                            RoleMenuItemVO objRoleMenuItemVO = (RoleMenuItemVO)itr.next();
	                            resourceId[i] = String.valueOf(objRoleMenuItemVO.getMenuItem().getMenuItemID());
	                            i++;
	                        }
	                        frm.setResourceIds(resourceId);
	                        action="failure";
                        }
                    }
                    else if(frm.getFormAction().equals("update"))
                    {
                        
                        boolean noFunction=false;
                        RolesVO objRoleDetails = new RolesVO();
                        objRoleDetails.setGroupName(frm.getRoleName());
                        objRoleDetails.setGroupId(frm.getRoleId());
                        objRoleDetails.setDescription(frm.getUserRoleDesc());
                       
                        String ids[]=frm.getResourceIds();
                        String resourceIds ="";
                        for (int i = 0; i < ids.length; i++) 
                        {
                            if(resourceIds.length()<=0)
                                resourceIds = ids[i];
                            else
                                resourceIds += ","+ids[i];
                            
                        }
                        MenuItemDao menuItemDao = new MenuItemDao();
                        List objMenuItemList = (List)menuItemDao.getMenuItem(resourceIds);
                       if(ids.length > 0 )
                       {
                           noFunction = true;
                       }
                      
                        boolean updated =false;
                        if(noFunction)
                        updated =objRolesDAO.updateRole(objRoleDetails, objMenuItemList);
                        
                        if(updated)
                        {
                            ActionMessages messages = new ActionMessages();
                            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.modified","Role",frm.getRoleName()));
                            saveMessages(request,messages);
                            request.setAttribute("message","message");
                            action="success";
                        }
                        else
                        {
                            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.role.nofunction","Role ",frm.getRoleName()));
                            saveErrors(request,errors);
                            request.setAttribute("message","error");
                            action="failure";
                        }
                    }
                }
            }
            catch(Exception se)
            {
                se.printStackTrace();
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("Role.error",se.getMessage()));
                if(!errors.isEmpty())
                {
                    saveErrors(request,errors);
                    request.setAttribute("message","error");
                }
                action = "failure";
            }
        }
        return mapping.findForward(action);
    }
}

/*
*$Log: RoleEditAction.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.13  2007/08/24 13:34:20  kduraisamy
*calling menu item in all pages problem solved.
*
*Revision 1.12  2007/08/23 07:39:47  jnadesan
*imports organized
*
*Revision 1.11  2007/07/31 11:55:33  kduraisamy
*unwanted imports removed.
*
*Revision 1.10  2007/07/17 14:02:32  sramasamy
*error fixed in user add modify update
*
*Revision 1.9  2007/05/22 13:19:16  spandiyarajan
*set the message attribute for set the div height in list page
*
*Revision 1.8  2007/05/02 09:11:39  spandiyarajan
*removed unwanted s.o.p
*
*Revision 1.7  2007/04/13 05:01:03  kduraisamy
*unwanted println commented.
*
*Revision 1.6  2007/04/07 11:28:46  spandiyarajan
*committed for role
*
*Revision 1.5  2007/03/22 12:17:36  kduraisamy
*role modify Error corrected.
*
*Revision 1.4  2007/03/20 13:14:35  kduraisamy
*Roles page simplified.
*
*Revision 1.3  2007/03/10 12:21:42  srajappan
*security admin menu changed
*
*Revision 1.2  2007/03/09 14:10:53  srajappan
*security admin actions added
*
*Revision 1.1  2007/03/08 04:54:21  srajappan
*role base menu added
*
*Revision 1.12  2006/10/09 09:42:15  jnadesan
*alignment corrected
*
*Revision 1.11  2006/10/05 07:40:03  jnadesan
*TMODE option added
*
*Revision 1.10  2006/10/04 07:33:46  jnadesan
*roleid property handled
*
*Revision 1.9  2006/09/28 05:16:39  jnadesan
*default role cant be deleted operation added
*
*Revision 1.8  2006/07/31 14:00:59  srajappan
*unwanted print removed
*
*Revision 1.7  2006/07/21 10:40:50  kduraisamy
*validation included
*
*Revision 1.6  2006/07/21 07:32:10  kduraisamy
*validation included
*
*Revision 1.5  2006/07/04 01:05:22  kduraisamy
*error included
*
*Revision 1.4  2006/07/02 17:00:17  kduraisamy
*role view completed
*
*Revision 1.3  2006/06/30 07:42:28  kduraisamy
*message class added
*
*Revision 1.2  2006/06/30 07:13:24  kduraisamy
*role delete fiinished
*
*Revision 1.1  2006/06/27 10:24:36  kduraisamy
*role edit added
*
*
*/