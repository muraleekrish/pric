/*
 * 
 * XPressPricingViewResultForm.java    Aug 24, 2007
 * 
 * Copyright (c) Savant Technologies Pvt Ltd.
 * 'Savant House' 127 Chamiers Road, Nandanam, Chennai-600 035. INDIA 
 * All rights reserved.

 * @author: jnadesan
 * @company: Savant Technologies
 * @client: MX Energy
 * @version: 
 * @Description: 
 * 
*/

package com.savant.pricing.presentation.matrix;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * 
 */
public class XPressPricingViewResultForm extends ActionForm
{
    private String formAction ="";
    private String customerName = "";
    private String comboTdsp="1";
    private String comboTerm = "0";
    private String comboProfile = "1";
    private String congestionZone = "1";
    private String billMonth="0";
    private String monthlyDemand="0";
    private String monthlyEnergy="2000";
    private String estimatedEnergy="";
    private String customerCharge="0";
    private String loadFactor="";
    private String cmbSalesRep="";
    private String salesManager="";
    private String analyst="";
    private String depositAmount="0";
    private String energyOnly="bundle";
    private String referenceNo="";
    private String paymenthistory="";
    private String compPrice = "";
    
    /**
     * 
     */
    
    public String getPaymenthistory()
    {
        return paymenthistory;
    }
    public void setPaymenthistory(String paymenthistory)
    {
        this.paymenthistory = paymenthistory;
    }
    public String getComboProfile()
    {
        return comboProfile;
    }
    public void setComboProfile(String comboProfile)
    {
        this.comboProfile = comboProfile;
    }
    public String getEstimatedEnergy()
    {
        return estimatedEnergy;
    }
    public void setEstimatedEnergy(String estimatedEnergy)
    {
        this.estimatedEnergy = estimatedEnergy;
    }
    public String getReferenceNo()
    {
        return referenceNo;
    }
    public void setReferenceNo(String referenceNo)
    {
        this.referenceNo = referenceNo;
    }
    public String getAnalyst()
    {
        return analyst;
    }
    public String getBillMonth()
    {
        return billMonth;
    }
    public String getComboTdsp()
    {
        return comboTdsp;
    }
    public String getCongestionZone()
    {
        return congestionZone;
    }
    public String getCustomerCharge()
    {
        return customerCharge;
    }
    public String getCustomerName()
    {
        return customerName;
    }
    public String getDepositAmount()
    {
        return depositAmount;
    }
    
    public String getFormAction()
    {
        return formAction;
    }
    public String getLoadFactor()
    {
        return loadFactor;
    }
    public String getMonthlyDemand()
    {
        return monthlyDemand;
    }
    public String getMonthlyEnergy()
    {
        return monthlyEnergy;
    }
    public String getSalesManager()
    {
        return salesManager;
    }
    public void setAnalyst(String analyst)
    {
        this.analyst = analyst;
    }
    public void setBillMonth(String billMonth)
    {
        this.billMonth = billMonth;
    }
    public void setComboTdsp(String comboTdsp)
    {
        this.comboTdsp = comboTdsp;
    }
    public void setCongestionZone(String congestionZone)
    {
        this.congestionZone = congestionZone;
    }
    public void setCustomerCharge(String customerCharge)
    {
        this.customerCharge = customerCharge;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    public void setDepositAmount(String depositAmount)
    {
        this.depositAmount = depositAmount;
    }
    public String getEnergyOnly()
    {
        return energyOnly;
    }
    public void setEnergyOnly(String energyOnly)
    {
        this.energyOnly = energyOnly;
    }
    public void setFormAction(String formAction)
    {
        this.formAction = formAction;
    }
    public void setLoadFactor(String loadFactor)
    {
        this.loadFactor = loadFactor;
    }
    public void setMonthlyDemand(String monthlyDemand)
    {
        this.monthlyDemand = monthlyDemand;
    }
    public void setMonthlyEnergy(String monthlyEnergy)
    {
        this.monthlyEnergy = monthlyEnergy;
    }
    public void setSalesManager(String salesManager)
    {
        this.salesManager = salesManager;
    }
    public String getComboTerm()
    {
        return comboTerm;
    }
    public void setComboTerm(String comboTerm)
    {
        this.comboTerm = comboTerm;
    }
    public String getCmbSalesRep()
    {
        return cmbSalesRep;
    }
    public void setCmbSalesRep(String cmbSalesRep)
    {
        this.cmbSalesRep = cmbSalesRep;
    }
    public String getCompPrice()
    {
        return compPrice;
    }
    public void setCompPrice(String compPrice)
    {
        this.compPrice = compPrice;
    }
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors actionErrors = new ActionErrors();
        if(formAction.equalsIgnoreCase("makepdf"))
        {
            if(customerName==null || customerName.trim().length()<1 || customerName.trim().equals(""))
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Customer Name"));
            
            if(monthlyEnergy==null || monthlyEnergy.trim().length()<1 || monthlyEnergy.trim().equals(""))
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Monthly kWh"));
            
            if(monthlyDemand==null || monthlyDemand.trim().length()<1 || monthlyDemand.trim().equals(""))
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Monthly kVA"));
        }
        
        return actionErrors;
    }
}


/*
*$Log: XPressPricingViewResultForm.java,v $
*Revision 1.2  2008/11/21 09:47:23  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.6  2007/09/06 12:37:29  jnadesan
*default is budle
*
*Revision 1.5  2007/08/31 07:46:59  spandiyarajan
**** empty log message ***
*
*Revision 1.4  2007/08/30 12:37:40  spandiyarajan
*makepdf changes
*
*Revision 1.3  2007/08/29 07:23:55  spandiyarajan
*makepdf initially commited
*
*Revision 1.2  2007/08/28 11:18:22  jnadesan
*porperty added
*
*Revision 1.1  2007/08/27 04:43:02  jnadesan
*computaion for mAtrixPricer page
*
*
*/