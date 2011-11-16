/*
 * Created on May 5, 2007
 * 
 * Class Name ContractBundleDetails.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pdf;

import java.util.ArrayList;
import java.util.Collection;

import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractBundleDetails {
    
    private String mxenergyAddress = "";
    private String pricing = "";
    private String term = "";
    private String billingFees = "";
    private String switchAuthorization = "";
    private int contractTerm = 0;
    private String custName = "";
    private String billingAdress = "";
    private String city = "";
    private String state = "";
    private String zipCode = "";
    private String billingContact = "";
    private String federalTax = "";
    private String entityType = "";
    private String emailAddress = "";
    private String customerDBA = "";
    private String phoneNumber = "";
    private String faxNumber = "";
    
    private float price = 0;
    private Collection subreport = new ArrayList();
    private Collection esiidreport = new ArrayList();

    /**
     * 
     */
    public ContractBundleDetails(String address,String pricing,String term, String billingFees,String switchAuthorization,ProspectiveCustomerVO prsCustVo,int contrctTerm,float price,Object obj,Collection coll) 
    {
        this.mxenergyAddress = address;
        this.pricing = pricing;
        this.term = term;
        this.billingFees = billingFees;
        this.switchAuthorization = switchAuthorization;
        this.subreport.add(obj);
        this.price = price*100; 
        this.custName = prsCustVo.getCustomerName();
        this.contractTerm = contrctTerm;
        this.billingAdress = prsCustVo.getAddress()==null?"":prsCustVo.getAddress();
        this.city = prsCustVo.getCity()==null?"":prsCustVo.getCity();
        this.state = prsCustVo.getState()==null?"":prsCustVo.getState();
        this.zipCode = prsCustVo.getZipCode(); 
        this.esiidreport = coll;
        this.billingContact = prsCustVo.getPocFirstName()+" "+prsCustVo.getPocLastName();
        this.federalTax = "";
        this.entityType = "";
        this.emailAddress = prsCustVo.getEmail()==null?"":prsCustVo.getEmail();
        this.customerDBA = prsCustVo.getCustomerDBA();
        this.phoneNumber = prsCustVo.getPhone()==null?"":prsCustVo.getPhone();
        this.faxNumber = prsCustVo.getFax()==null?"":prsCustVo.getFax();
    }
    
    public String getBillingAdress() {
        return billingAdress;
    }
    public String getBillingContact() {
        return billingContact;
    }
    public String getBillingFees() {
        return billingFees;
    }
    public String getCity() {
        return city;
    }
    public int getContractTerm() {
        return contractTerm;
    }
    public String getCustName() {
        return custName;
    }
    public String getCustomerDBA() {
        return customerDBA;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
   
    public Collection getEsiidreport() {
        return esiidreport;
    }
    public String getFaxNumber() {
        return faxNumber;
    }
    public String getFederalTax() {
        return federalTax;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public float getPrice() {
        return price;
    }
    public String getPricing() {
        return pricing;
    }
    public String getState() {
        return state;
    }
    public Collection getSubreport() {
        return subreport;
    }
    public String getSwitchAuthorization() {
        return switchAuthorization;
    }
    public String getTerm() {
        return term;
    }
    public String getMXEnergyAddress() {
        return mxenergyAddress;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setBillingAdress(String billingAdress) {
        this.billingAdress = billingAdress;
    }
    public void setBillingContact(String billingContact) {
        this.billingContact = billingContact;
    }
    public void setBillingFees(String billingFees) {
        this.billingFees = billingFees;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setContractTerm(int contractTerm) {
        this.contractTerm = contractTerm;
    }
    public void setCustName(String custName) {
        this.custName = custName;
    }
    public void setCustomerDBA(String customerDBA) {
        this.customerDBA = customerDBA;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public void setEsiidreport(Collection esiidreport) {
        this.esiidreport = esiidreport;
    }
    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }
    public void setFederalTax(String federalTax) {
        this.federalTax = federalTax;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public void setPricing(String pricing) {
        this.pricing = pricing;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setSubreport(Collection subreport) {
        this.subreport = subreport;
    }
    public void setSwitchAuthorization(String switchAuthorization) {
        this.switchAuthorization = switchAuthorization;
    }
    public void setTerm(String term) {
        this.term = term;
    }
    public void setMXEnergyAddress(String mxenergyAddress) {
        this.mxenergyAddress = mxenergyAddress;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getEntityType() {
        return entityType;
    }
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
}


/*
*$Log: ContractBundleDetails.java,v $
*Revision 1.2  2008/11/21 09:47:11  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/07/05 12:42:05  jnadesan
*zipcode validation removed
*
*Revision 1.1  2007/05/05 11:05:07  jnadesan
*bundle and unbundle contracts are finished
*
*
*/