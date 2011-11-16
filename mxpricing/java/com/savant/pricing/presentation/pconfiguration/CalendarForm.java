/*
 * Created on Apr 18, 2007
 *
 * ClassName	:  	CalendarForm.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pconfiguration;

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
public class CalendarForm extends ActionForm
{
    private String txtDate = "";
    private String txtReason = "";
    private String formActions="";
    
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
    /**
     * @return Returns the txtReason.
     */
    public String getTxtReason()
    {
        return txtReason;
    }
    /**
     * @param txtReason The txtReason to set.
     */
    public void setTxtReason(String txtReason)
    {
        this.txtReason = txtReason;
    }
    /**
     * @return Returns the txtDate.
     */
    public String getTxtDate()
    {
        return txtDate;
    }
    /**
     * @param txtDate The txtDate to set.
     */
    public void setTxtDate(String txtDate)
    {
        this.txtDate = txtDate;
    }
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors actionErrors = new ActionErrors();        
        
        if(formActions.equalsIgnoreCase("add") || formActions.equalsIgnoreCase("modify"))
        {
	        if(txtDate==null || txtDate.trim().length()<1 || txtDate.trim().equals(""))
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Date"));
	        }
	        if(txtReason.trim().length()>200)
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.exceed.length"));
	        }
        } 
        return actionErrors;
    }
}

/*
*$Log: CalendarForm.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/09/10 11:26:42  jnadesan
*longer string validated
*
*Revision 1.1  2007/04/19 04:13:18  spandiyarajan
*pccaledndar(holiday) add/modify/delete functionality initially added
*
*
*/