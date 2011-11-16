/*
 * Created on Jun 14, 2007
 *
 * ClassName	:  	UserSettingsForm.java
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
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserSettingsForm  extends ActionForm
{
    private String userId;
    private String userName;
    private String firstName ;
    private String lastName;
    private String password;
    private String confirmPwd;
    private String email;
    private String formAction="";
    private String formActions="";
    
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
   
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFormAction() {
        return formAction;
    }
    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * @return Returns the confirmPwd.
     */
    public String getConfirmPwd()
    {
        return confirmPwd;
    }
    /**
     * @param confirmPwd The confirmPwd to set.
     */
    public void setConfirmPwd(String confirmPwd)
    {
        this.confirmPwd = confirmPwd;
    }
    /**
     * @return Returns the formActions.
     */
    public String getFormActions()
    {
        return formActions;
    }
    /**
     * @param formActions The formActions to set.
     */
    public void setFormActions(String formActions)
    {
        this.formActions = formActions;
    }
    
    /* (non-Javadoc)
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors actionErrors = new ActionErrors();
        if(formAction.equalsIgnoreCase("add")||formAction.equalsIgnoreCase("update"))
        {
            
            if(firstName==null || firstName.trim().length()<1 || firstName.trim().equals(""))
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","FirstName"));
            
            if(password==null || password.trim().length()<1 || password.trim().equals(""))
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Password"));
            
            if(confirmPwd==null || confirmPwd.trim().length()<1 || confirmPwd.trim().equals(""))
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","ConfirmPassword"));
            
            if(email==null || email.trim().length()<1 || email.trim().equals(""))
                actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","EmailId"));
            else
	        {
				boolean invalidMail = false;
	            String at="@";
				String dot=".";
				int lat=email.indexOf(at);
				int lstr=email.length();
				if (email.indexOf(at)==-1){
				    invalidMail = true;
				}

				if (email.indexOf(at)==-1 || email.indexOf(at)==0 || email.indexOf(at)==lstr){
				    invalidMail = true;
				}

				else if (email.indexOf(dot)==-1 || email.indexOf(dot)==0 || email.indexOf(dot)==lstr){
				    invalidMail = true;
				}

				else if (email.indexOf(at,(lat+1))!=-1){
				    invalidMail = true;
				 }

				else if (email.substring(lat-1,lat)==dot || email.substring(lat+1,lat+2)==dot){
				    invalidMail = true;
				 }

				else if (email.indexOf(dot,(lat+2))==-1){
				    invalidMail = true;
				 }
				
				 else if (email.indexOf(" ")!=-1){
				     invalidMail = true;
				 }		
	            
				if(invalidMail)
				    actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.email.invalidEmailId"));
	        }
            
            if(!password.trim().equals("") && !confirmPwd.trim().equals(""))
            {
	            if(!password.equals(confirmPwd))
		            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.pwddoesnotmatch"));
            }
        }
        return actionErrors;
    }
}

/*
*$Log: UserSettingsForm.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/06/14 09:53:35  spandiyarajan
*altered the settings page
*
*Revision 1.1  2007/06/14 09:03:00  spandiyarajan
*initially commit settings functionality
*
*
*/