/*
 * Created on Jun 23, 2006
 *
 * ClassName	:  	NewRoleAddForm.java
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

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author kduraisamy
 *
 */
public class NewRoleAddForm extends ActionForm
{
    private String formAction = "";
	private String roleName = "";
	private int roleId;
    private String userRoleDesc = "";
	private String[] resourceIds = new String[0];
	
    
    public String[] getResourceIds()
    {
        return resourceIds;
    }
    public void setResourceIds(String[] resourceIds)
    {
        this.resourceIds = resourceIds;
    }
	public String getFormAction()
    {
        return formAction;
    }
    public void setFormAction(String formAction)
    {
        this.formAction = formAction;
    }
    public String getRoleName()
    {
        return roleName;
    }
    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }
    
    public String getUserRoleDesc()
    {
        return userRoleDesc;
    }
    public void setUserRoleDesc(String userRoleDesc)
    {
        this.userRoleDesc = userRoleDesc;
    }
   
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    /* (non-Javadoc)
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors actionErrors = new ActionErrors();        
        
        if(formAction.equalsIgnoreCase("add")||formAction.equalsIgnoreCase("update"))
        {
	        if(roleName==null || roleName.trim().length()<1 || roleName.trim().equals(""))
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","RoleName"));
	        }
	        
	        if(resourceIds.length <= 0)
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Resources"));
	        }
        }
        return actionErrors;
    }
   }

/*
*$Log: NewRoleAddForm.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.7  2007/04/16 12:50:03  spandiyarajan
*removed unwanted lines
*
*Revision 1.6  2007/04/13 05:01:03  kduraisamy
*unwanted println commented.
*
*Revision 1.5  2007/04/08 07:59:14  rraman
*altered
*
*Revision 1.4  2007/04/07 11:28:46  spandiyarajan
*committed for role
*
*Revision 1.3  2007/03/20 13:14:35  kduraisamy
*Roles page simplified.
*
*Revision 1.2  2007/03/09 14:10:53  srajappan
*security admin actions added
*
*Revision 1.1  2007/03/08 04:54:21  srajappan
*role base menu added
*
*Revision 1.6  2006/10/05 07:40:03  jnadesan
*TMODE option added
*
*Revision 1.5  2006/10/04 07:32:52  jnadesan
*new property roleid added
*
*Revision 1.4  2006/09/28 05:15:19  jnadesan
*roleIds value initialized with string array
*
*Revision 1.3  2006/07/04 01:05:22  kduraisamy
*error included
*
*Revision 1.2  2006/06/30 07:13:24  kduraisamy
*role delete fiinished
*
*Revision 1.1  2006/06/24 08:20:09  kduraisamy
*role add finished
*
*
*/