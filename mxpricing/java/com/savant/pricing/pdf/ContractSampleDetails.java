/*
 * Created on Apr 9, 2007
 * 
 * Class Name ContractSampleDetails.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pdf;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractSampleDetails {
    
    private String mxenergyAddress = "";
    private String pricing = "";
    private String pricingDetails = "";
    private String term = "";
    private String termDetails = "";
    private String billingFees = "";
    private String billingAgreement = "";
    private String switchAuthorization = "";
    private int contractTerm = 0;
    private String custName = "";
    private String billingAdress = "";
    private String city = "";
    private String state = "";
    private String zipCode = "";
    private float price = 0;
    private float gasPrice = 0;
    private float fuelFactor = 0;
    private Collection subreport = new ArrayList();
    private Collection esiidreport = new ArrayList();

    /**
     * 
     */
    public ContractSampleDetails(String address,String pricing,String pricingDetails,String term,String termDetails,String billingFees,String billingAgreement,String switchAuthorization,String custName,String custAdress,String city,String state,String zipcCode,int contrctTerm,float price,float gasPrice,float fuelFactor,Object obj,Collection coll) 
    {
        this.mxenergyAddress = address;
        this.pricing = pricing;
        this.termDetails = termDetails;
        this.pricingDetails =pricingDetails;
        this.term = term;
        this.billingFees = billingFees;
        this.billingAgreement = billingAgreement;
        this.switchAuthorization = switchAuthorization;
        this.subreport.add(obj);
        this.price = price*100; 
        this.gasPrice = gasPrice;
        this.fuelFactor = fuelFactor;
        this.custName = custName;
        this.contractTerm = contrctTerm;
        this.billingAdress = custAdress;
        this.city = city;
        this.state = state;
        this.zipCode = zipcCode; 
        this.esiidreport = coll;
    }
    
    public float getFuelFactor() {
        return fuelFactor;
    }
    public void setFuelFactor(float fuelFactor) {
        this.fuelFactor = fuelFactor;
    }
    public String getTermDetails() {
        return termDetails;
    }
    public void setTermDetails(String termDetails) {
        this.termDetails = termDetails;
    }
    public String getPricingDetails() {
        return pricingDetails;
    }
    public void setPricingDetails(String pricingDetails) {
        this.pricingDetails = pricingDetails;
    }
    public String getCustName() {
        return custName;
    }
    public void setCustName(String custName) {
        this.custName = custName;
    }
    public Collection getEsiidreport() {
        return esiidreport;
    }
    public void setEsiidreport(Collection esiidreport) {
        this.esiidreport = esiidreport;
    }
    public int getContractTerm() {
        return contractTerm;
    }
    
    public float getPrice() {
        return price;
    }
    public void setContractTerm(int contractTerm) {
        this.contractTerm = contractTerm;
    }
    
    public void setPrice(float price) {
        this.price = price;
    }
    public Collection getSubreport() {
        return subreport;
    }
    public void setSubreport(Collection subreport) {
        this.subreport = subreport;
    }
   
    public String getBillingAgreement() {
        return billingAgreement;
    }
    public String getBillingFees() {
        return billingFees;
    }
    public String getPricing() {
        return pricing;
    }
    public String getSwitchAuthorization() {
        return switchAuthorization;
    }
    public String getTerm() {
        return term;
    }
    public String getBillingAdress() {
        return billingAdress;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
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
    public void setCity(String city) {
        this.city = city;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setMXEnergyAddress(String mxenergyAddress) {
        this.mxenergyAddress = mxenergyAddress;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public void setBillingAgreement(String billingAgreement) {
        this.billingAgreement = billingAgreement;
    }
    public void setBillingFees(String billingFees) {
        this.billingFees = billingFees;
    }
    public void setPricing(String pricing) {
        this.pricing = pricing;
    }
    public void setSwitchAuthorization(String switchAuthorization) {
        this.switchAuthorization = switchAuthorization;
    }
    public void setTerm(String term) {
        this.term = term;
    }
    public float getGasPrice() {
        return gasPrice;
    }
    public void setGasPrice(float gasPrice) {
        this.gasPrice = gasPrice;
    }
}


/*
*$Log: ContractSampleDetails.java,v $
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
*Revision 1.4  2007/07/05 12:42:05  jnadesan
*zipcode validation removed
*
*Revision 1.3  2007/04/17 15:16:36  jnadesan
*entry for contract FAR
*
*Revision 1.2  2007/04/16 13:22:52  jnadesan
*cent per kWh multiplication factor changed
*
*Revision 1.1  2007/04/10 09:53:43  jnadesan
*DataSource for fixed Contract
*
*
*/