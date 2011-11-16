/*
 * Created on Apr 12, 2007
 *
 * ClassName	:  	TDSPChargesListForm.java
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
public class TDSPChargesListForm extends ActionForm
{
    private String formActions = "list";
    private String searchTDSP = "1";
    private String searchRateCode = "111";
    
    /**
     * @return Returns the searchRateCode.
     */
    public String getSearchRateCode()
    {
        return searchRateCode;
    }
    /**
     * @param searchRateCode The searchRateCode to set.
     */
    public void setSearchRateCode(String searchRateCode)
    {
        this.searchRateCode = searchRateCode;
    }
    /**
     * @return Returns the searchTDSP.
     */
    public String getSearchTDSP()
    {
        return searchTDSP;
    }
    /**
     * @param searchTDSP The searchTDSP to set.
     */
    public void setSearchTDSP(String searchTDSP)
    {
        this.searchTDSP = searchTDSP;
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
}

/*
*$Log: TDSPChargesListForm.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/05/07 07:31:26  spandiyarajan
*tdsp charges changed
*
*Revision 1.1  2007/04/12 11:57:54  spandiyarajan
**** empty log message ***
*
*
*/