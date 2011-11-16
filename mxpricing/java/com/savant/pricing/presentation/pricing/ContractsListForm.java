/*
 * Created on May 2, 2007
 *
 * ClassName	:  	ContractsListForm.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pricing;

import org.apache.struts.action.ActionForm;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractsListForm extends ActionForm
{
    private String formActions = "list";
    private String maxItems="10";
    private String page="1";
    private String sortOrder="desending";
    private String sortField="contractTrackingIdentifier";  
    
    private String txtCustName="";
    private String cmbCustName="0";
    private String txtContractId="";
    private String cmbContractId="0";
    private String cmbContractStatus="0";
    private String contTrackId="";
    private String txtCMSId = "";
    
    /**
     * @return Returns the contTrackId.
     */
    public String getContTrackId()
    {
        return contTrackId;
    }
    /**
     * @param contTrackId The contTrackId to set.
     */
    public void setContTrackId(String contTrackId)
    {
        this.contTrackId = contTrackId;
    }
    /**
     * @return Returns the cmbContractId.
     */
    public String getCmbContractId()
    {
        return cmbContractId;
    }
    /**
     * @param cmbContractId The cmbContractId to set.
     */
    public void setCmbContractId(String cmbContractId)
    {
        this.cmbContractId = cmbContractId;
    }
    /**
     * @return Returns the cmbContractStatus.
     */
    public String getCmbContractStatus()
    {
        return cmbContractStatus;
    }
    /**
     * @param cmbContractStatus The cmbContractStatus to set.
     */
    public void setCmbContractStatus(String cmbContractStatus)
    {
        this.cmbContractStatus = cmbContractStatus;
    }
    /**
     * @return Returns the cmbCustName.
     */
    public String getCmbCustName()
    {
        return cmbCustName;
    }
    /**
     * @param cmbCustName The cmbCustName to set.
     */
    public void setCmbCustName(String cmbCustName)
    {
        this.cmbCustName = cmbCustName;
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
    /**
     * @return Returns the maxItems.
     */
    public String getMaxItems()
    {
        return maxItems;
    }
    /**
     * @param maxItems The maxItems to set.
     */
    public void setMaxItems(String maxItems)
    {
        this.maxItems = maxItems;
    }
    /**
     * @return Returns the page.
     */
    public String getPage()
    {
        return page;
    }
    /**
     * @param page The page to set.
     */
    public void setPage(String page)
    {
        this.page = page;
    }
    /**
     * @return Returns the sortField.
     */
    public String getSortField()
    {
        return sortField;
    }
    /**
     * @param sortField The sortField to set.
     */
    public void setSortField(String sortField)
    {
        this.sortField = sortField;
    }
    /**
     * @return Returns the sortOrder.
     */
    public String getSortOrder()
    {
        return sortOrder;
    }
    /**
     * @param sortOrder The sortOrder to set.
     */
    public void setSortOrder(String sortOrder)
    {
        this.sortOrder = sortOrder;
    }
    /**
     * @return Returns the txtContractId.
     */
    public String getTxtContractId()
    {
        return txtContractId;
    }
    /**
     * @param txtContractId The txtContractId to set.
     */
    public void setTxtContractId(String txtContractId)
    {
        this.txtContractId = txtContractId;
    }
    /**
     * @return Returns the txtCustName.
     */
    public String getTxtCustName()
    {
        return txtCustName;
    }
    /**
     * @param txtCustName The txtCustName to set.
     */
    public void setTxtCustName(String txtCustName)
    {
        this.txtCustName = txtCustName;
    }
    public String getTxtCMSId() {
        return txtCMSId;
    }
    public void setTxtCMSId(String txtCMSId) {
        this.txtCMSId = txtCMSId;
    }
}

/*
*$Log: ContractsListForm.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:49  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/07/30 12:42:34  jnadesan
*CMS ID filter added
*
*Revision 1.4  2007/05/04 06:45:57  spandiyarajan
*in contract page default loding list value is chaged from asc to desc
*
*Revision 1.3  2007/05/03 12:45:05  spandiyarajan
*initilally commit the update part of contractstracking
*
*Revision 1.2  2007/05/03 07:47:39  spandiyarajan
*search filter functionality finished
*
*Revision 1.1  2007/05/03 05:03:48  spandiyarajan
*initially committed the contract list page
*
*
*/