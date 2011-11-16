/*
 * Created on Jun 23, 2006
 *
 * ClassName	:  	NewRoleAddAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.security;

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
import org.hibernate.HibernateException;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.securityadmin.dao.MenuItemDao;
import com.savant.pricing.securityadmin.dao.RolesDAO;
import com.savant.pricing.securityadmin.valueobject.RolesVO;


/**
 * @author kduraisamy
 *
 */
public class NewRoleAddAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
    {
        String action = "failure";
        ActionErrors errors = new ActionErrors();
        boolean insertUserRole = false;
        if(form instanceof NewRoleAddForm)
        {
            NewRoleAddForm frm = (NewRoleAddForm)form;
            String formAction = frm.getFormAction();
            try
            {
                RolesDAO  rolesDAO = new RolesDAO();
                
                if(formAction.equals("add"))
                {
                    RolesVO objRolesVO = new RolesVO();
                    objRolesVO.setGroupName(frm.getRoleName().trim().toUpperCase());
                    objRolesVO.setDescription(frm.getUserRoleDesc());
                    objRolesVO.setCreatedBy("");
                    objRolesVO.setValid(true);
                    String ids[]=frm.getResourceIds();
                    String resourceIds = "";
                    for (int i = 0; i < ids.length; i++) 
                    {
                        if(resourceIds.length()<=0)
                            resourceIds = ids[i];
                        else
                            resourceIds += ","+ids[i];
                    }
                   
                    MenuItemDao menuItemDao = new MenuItemDao();
                    List objMenuItemList = (List)menuItemDao.getMenuItem(resourceIds);
                    insertUserRole = rolesDAO.addRoles(objRolesVO, objMenuItemList);
                    if(insertUserRole == true )
                    {
                        ActionMessages messages = new ActionMessages();
                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.added","Role",frm.getRoleName().toUpperCase()));
                        saveMessages(request,messages);
                        request.setAttribute("message","message");
                        action = "success";
                    }
                    else
                    {
                        action = "failure";
                    }
                }
            }
            catch(HibernateException e)
            {
                if(BuildConfig.DMODE)
                    System.out.println("e.toString():"+e.toString());
                if(e.toString().indexOf("Role Already Exists") >= 0)
                    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.rolealreadyexists"));
                else
                    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.generalerror"));
                if(!errors.isEmpty())
                {
                    saveErrors(request,errors);
                    request.setAttribute("message","error");
                }
            }  
        }
        if(!errors.isEmpty())
        {
            action = "failure";
            saveErrors(request, errors);
            request.setAttribute("message","error");
        }
        if(BuildConfig.DMODE)
            System.out.println("Action:"+action);
        return mapping.findForward(action);
}
}

/*
*$Log: NewRoleAddAction.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.10  2007/08/24 13:34:20  kduraisamy
*calling menu item in all pages problem solved.
*
*Revision 1.9  2007/05/22 13:19:16  spandiyarajan
*set the message attribute for set the div height in list page
*
*Revision 1.8  2007/05/02 09:11:39  spandiyarajan
*removed unwanted s.o.p
*
*Revision 1.7  2007/04/23 05:52:20  kduraisamy
*unwanted println removed.
*
*Revision 1.6  2007/04/08 07:59:14  rraman
*altered
*
*Revision 1.5  2007/04/07 11:28:46  spandiyarajan
*committed for role
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
*Revision 1.9  2006/10/09 10:15:38  srajappan
*import organized
*
*Revision 1.8  2006/10/09 09:47:35  srajappan
*import organized
*
*Revision 1.7  2006/10/05 07:40:03  jnadesan
*TMODE option added
*
*Revision 1.6  2006/09/09 07:13:38  srajappan
*error msg properly thrown
*
*Revision 1.5  2006/07/21 07:32:10  kduraisamy
*validation included
*
*Revision 1.4  2006/07/13 07:17:45  kduraisamy
*indentation
*
*Revision 1.3  2006/07/11 01:42:14  kduraisamy
*string.trim() included to avoid empty spaces
*
*Revision 1.2  2006/06/30 07:13:24  kduraisamy
*role delete fiinished
*
*Revision 1.1  2006/06/24 08:20:09  kduraisamy
*role add finished
*
*
*/