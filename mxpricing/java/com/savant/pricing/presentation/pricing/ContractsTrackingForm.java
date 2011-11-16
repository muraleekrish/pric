/*
 * Created on May 3, 2007
 *
 * ClassName	:  	ContractsTrackingForm.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pricing;

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
public class ContractsTrackingForm extends ActionForm
{
    private String contractTrackingIdentifier;
    private String customerName;
    private int customerId;
    private String productName ;
    private String term;
    private String price;
    private String contractStatus;
    private String txtcomments = "";
    private String txtcommentsGeneral = "";
    private String contractType;
    private String contractCMSStatus;
    private String rateClass;
    private String formActions="list";
    private String contractStartDate = "";
    private String contractEndDate = "";
    
    public String getContractEndDate() {
        return contractEndDate;
    }
    public String getContractStartDate() {
        return contractStartDate;
    }
    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }
    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate;
    }
    public String getContractCMSStatus() {
        return contractCMSStatus;
    }
    public String getContractType() {
        return contractType;
    }
    public void setContractCMSStatus(String contractCMSStatus) {
        this.contractCMSStatus = contractCMSStatus;
    }
    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
    
    public String getTxtcomments() {
        return txtcomments;
    }
    public String getTxtcommentsGeneral() {
        return txtcommentsGeneral;
    }
    public void setTxtcomments(String txtcomments) {
        this.txtcomments = txtcomments;
    }
    public void setTxtcommentsGeneral(String txtcommentsGeneral) {
        this.txtcommentsGeneral = txtcommentsGeneral;
    }
    public String getContractStatus()
    {
        return contractStatus;
    }
    /**
     * @param contractStatus The contractStatus to set.
     */
    public void setContractStatus(String contractStatus)
    {
        this.contractStatus = contractStatus;
    }
    /**
     * @return Returns the contractTrackingIdentifier.
     */
    public String getContractTrackingIdentifier()
    {
        return contractTrackingIdentifier;
    }
    /**
     * @param contractTrackingIdentifier The contractTrackingIdentifier to set.
     */
    public void setContractTrackingIdentifier(String contractTrackingIdentifier)
    {
        this.contractTrackingIdentifier = contractTrackingIdentifier;
    }
    /**
     * @return Returns the customerName.
     */
    public String getCustomerName()
    {
        return customerName;
    }
    /**
     * @param customerName The customerName to set.
     */
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
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
     * @return Returns the price.
     */
    public String getPrice()
    {
        return price;
    }
    /**
     * @param price The price to set.
     */
    public void setPrice(String price)
    {
        this.price = price;
    }
    /**
     * @return Returns the productName.
     */
    public String getProductName()
    {
        return productName;
    }
    /**
     * @param productName The productName to set.
     */
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    /**
     * @return Returns the term.
     */
    public String getTerm()
    {
        return term;
    }
    /**
     * @param term The term to set.
     */
    public void setTerm(String term)
    {
        this.term = term;
    }
    /**
     * @return Returns the rateClass.
     */
    public String getRateClass()
    {
        return rateClass;
    }
    /**
     * @param rateClass The rateClass to set.
     */
    public void setRateClass(String rateClass)
    {
        this.rateClass = rateClass;
    }
    /**
     * @return Returns the customerId.
     */
    public int getCustomerId()
    {
        return customerId;
    }
    /**
     * @param customerId The customerId to set.
     */
    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors actionErrors = new ActionErrors();
        if(formActions.equalsIgnoreCase("update"))
        {
            if(contractStatus.equals("0"))
                actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Status"));
            
        }
        return actionErrors;
    }
   
}

/*
*$Log: ContractsTrackingForm.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/23 11:44:43  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/10/30 05:51:49  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.8  2007/09/24 13:14:49  jnadesan
*Rate class Contract type set as mandatory
*
*Revision 1.7  2007/09/24 12:08:51  jnadesan
*Entered into CMS status added.
*
*Revision 1.6  2007/06/21 11:24:14  jnadesan
*viewing and editing comments in contract tracking page has finished
*
*Revision 1.5  2007/06/20 08:08:11  spandiyarajan
*comments updated
*
*Revision 1.4  2007/06/06 16:17:24  jnadesan
*Contract strartmoth will be selected while contrcat
*status is changed into signed
*
*Revision 1.3  2007/06/04 12:42:32  spandiyarajan
*added one more field
*
*Revision 1.2  2007/05/28 15:10:27  jnadesan
*field to get cms customer status and cotract type
*
*Revision 1.1  2007/05/03 12:45:05  spandiyarajan
*initilally commit the update part of contractstracking
*
*
*/