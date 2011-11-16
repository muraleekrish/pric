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
public class UserForm extends ActionForm
{
    private String userId;
    private String userName;
    private String firstName ;
    private String lastName;
    private String password;
    private String email;
    private String comment;
    private String parentUserid;
    private String userType;
    private String role;
    private String oldPassword;
    private String newPassword;
    private String conPassword;
    private String changePasswordStatus;
    private String formAction="list";
    
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getParentUserid() {
        return parentUserid;
    }
    public void setParentUserid(String parentUserid) {
        this.parentUserid = parentUserid;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
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
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /* (non-Javadoc)
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors actionErrors = new ActionErrors();
        if(formAction.equalsIgnoreCase("add") || formAction.equalsIgnoreCase("update"))
        {
            if(userName==null || userName.trim().length()<1 || userName.trim().equals("0"))
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","UserId"));
            
            if(firstName==null || firstName.trim().length()<1 || firstName.trim().equals(""))
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","FirstName"));
            
            if(password==null || password.length()<1 || password.equals(""))
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Password"));
            
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
            
            if(userType.equals("0"))
                actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","UserType"));
            
            if(parentUserid.equals("0"))
                actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","ParentUser"));
        }
        return actionErrors;
    }
    
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    /**
     * @return Returns the conPassword.
     */
    public String getConPassword() {
        return conPassword;
    }
    /**
     * @param conPassword The conPassword to set.
     */
    public void setConPassword(String conPassword) {
        this.conPassword = conPassword;
    }
    /**
     * @return Returns the newPassword.
     */
    public String getNewPassword() {
        return newPassword;
    }
    /**
     * @param newPassword The newPassword to set.
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    /**
     * @return Returns the oldPassword.
     */
    public String getOldPassword() {
        return oldPassword;
    }
    /**
     * @param oldPassword The oldPassword to set.
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    /**
     * @return Returns the changePasswordStatus.
     */
    public String getChangePasswordStatus() {
        return changePasswordStatus;
    }
    /**
     * @param changePasswordStatus The changePasswordStatus to set.
     */
    public void setChangePasswordStatus(String changePasswordStatus) {
        this.changePasswordStatus = changePasswordStatus;
    }
}
