/*
 * Created on Feb 21, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UsertypeForm extends ActionForm
{
    private String userType="";
    private String parentType="";
    private String comment="";
    private String formAction="";
    private int userTypeId=0;
   
    
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getFormAction() {
        return formAction;
    }
    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }
    public String getParentType() {
        return parentType;
    }
    public void setParentType(String parentType) {
        this.parentType = parentType;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public int getUserTypeId() {
        return userTypeId;
    }
    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }
    
    /* (non-Javadoc)
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {        
        ActionErrors actionErrors = new ActionErrors();        
        /* if(request.getParameter("userTypeid")!= null)
        {
	        String temp[] = request.getParameter("userTypeid").split(",");
	        if(temp.length>1)
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Prospective.customer.customerId.numeric"));
	        } */
        if(formAction.equalsIgnoreCase("add"))
        {         
	        if(userType==null || userType.trim().length()<1 || userType.trim().equals(""))
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","UserType"));
        }
       // }
       return actionErrors;               
    }
    
}
