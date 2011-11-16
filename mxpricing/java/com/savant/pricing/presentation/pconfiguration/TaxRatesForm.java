/*
 * Created on Apr 11, 2007
 *
 * ClassName	:  	TaxRatesForm.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pconfiguration;

import org.apache.struts.action.ActionForm;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxRatesForm extends ActionForm
{
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
    private String formActions = "list";
}

/*
*$Log: TaxRatesForm.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/11 14:55:06  spandiyarajan
*tax page initially commited
*
*
*/