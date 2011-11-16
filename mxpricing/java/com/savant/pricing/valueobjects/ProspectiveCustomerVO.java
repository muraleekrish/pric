/*
 * Created on Jan 23, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.valueobjects;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.savant.pricing.securityadmin.valueobject.UsersVO;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProspectiveCustomerVO implements Serializable
{
    private int prospectiveCustomerId;
    private String customerName = "";
    private Integer customerId ;
    private String pocFirstName = "";
    private String pocLastName = "";
    private String title = "";
    private String address = "";
    private String city = "";
    private String state = "";
    private String zipCode = "";
    private String phone = "";
    private String fax = "";
    private String mobile = "";
    private String email = "";
    private String currentProvider = "";
    private String businessType = "";
    private Date contractOpenDate ;
    private Date importedPICOn ;
    private CustomerStatusVO customerStatus;
    private Date statusDate;
    private	CDRStatusVO cdrStatus;
    private boolean valid;
    private boolean mmCust;
    private UsersVO salesRep;
    private String customerDBA;
    private Date contractStartDate;
    private boolean outOfCycleSwitch;
    private boolean taxExempt;
    private String censusTract;
    private float estimatedUsage;
    private float commission;
    private float commissionIncome;
    private String competitor;
    private float competitorPrice;
    private int locationTypeId;
    private int addressTypeId;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private Set customerComments = new HashSet();
    private Set customerPreference = new HashSet();
    private Set customerPreferenceTerms = new HashSet();
    private Set customerPreferenceProducts = new HashSet();
    private Set picVOs = new HashSet();
    
    private CustomerCommentsVO comments;
    
    public ProspectiveCustomerVO()
    {
    }
    
    public boolean isMmCust()
    {
        return mmCust;
    }
    public void setMmCust(boolean mmCust)
    {
        this.mmCust = mmCust;
    }
    public Set getCustomerPreference()
    {
        return customerPreference;
    }
    public void setCustomerPreference(Set customerPreference)
    {
        this.customerPreference = customerPreference;
    }
    public Set getCustomerComments()
    {
        return customerComments;
    }
    public void setCustomerComments(Set customerComments)
    {
        this.customerComments = customerComments;
    }
    public CustomerCommentsVO getComments()
    {
        return comments;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public String getModifiedBy() {
        return modifiedBy;
    }
    public Date getModifiedDate() {
        return modifiedDate;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    public void setComments(CustomerCommentsVO comments)
    {
        this.comments = comments;
    }
    public int getAddressTypeId()
    {
        return addressTypeId;
    }
    public void setAddressTypeId(int addressTypeId)
    {
        this.addressTypeId = addressTypeId;
    }
    public int getLocationTypeId()
    {
        return locationTypeId;
    }
    public void setLocationTypeId(int locationTypeId)
    {
        this.locationTypeId = locationTypeId;
    }
    public String getPocFirstName()
    {
        return pocFirstName;
    }
    public void setPocFirstName(String pocFirstName)
    {
        this.pocFirstName = pocFirstName;
    }
    public String getPocLastName()
    {
        return pocLastName;
    }
    public void setPocLastName(String pocLastName)
    {
        this.pocLastName = pocLastName;
    }
    public String getCompetitor()
    {
        return competitor;
    }
    public void setCompetitor(String competitor)
    {
        this.competitor = competitor;
    }
    public float getCompetitorPrice()
    {
        return competitorPrice;
    }
    public void setCompetitorPrice(float competitorPrice)
    {
        this.competitorPrice = competitorPrice;
    }
    public String getCensusTract()
    {
        return censusTract;
    }
    public void setCensusTract(String censusTract)
    {
        this.censusTract = censusTract;
    }
   
    public float getCommission()
    {
        return commission;
    }
    public void setCommission(float commission)
    {
        this.commission = commission;
    }
    public float getCommissionIncome()
    {
        return commissionIncome;
    }
    public void setCommissionIncome(float commissionIncome)
    {
        this.commissionIncome = commissionIncome;
    }
    public Date getContractStartDate()
    {
        return contractStartDate;
    }
    public void setContractStartDate(Date contractStartDate)
    {
        this.contractStartDate = contractStartDate;
    }
    public String getCustomerDBA()
    {
        return customerDBA;
    }
    public void setCustomerDBA(String customerDBA)
    {
        this.customerDBA = customerDBA;
    }
    public float getEstimatedUsage()
    {
        return estimatedUsage;
    }
    public void setEstimatedUsage(float estimatedUsage)
    {
        this.estimatedUsage = estimatedUsage;
    }
    public boolean isOutOfCycleSwitch()
    {
        return outOfCycleSwitch;
    }
    public void setOutOfCycleSwitch(boolean outOfCycleSwitch)
    {
        this.outOfCycleSwitch = outOfCycleSwitch;
    }
    public boolean isTaxExempt()
    {
        return taxExempt;
    }
    public void setTaxExempt(boolean taxExempt)
    {
        this.taxExempt = taxExempt;
    }
    public CDRStatusVO getCdrStatus()
    {
        return cdrStatus;
    }
    public void setCdrStatus(CDRStatusVO cdrStatus)
    {
        this.cdrStatus = cdrStatus;
    }
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public String getBusinessType()
    {
        return businessType;
    }
    public void setBusinessType(String businessType)
    {
        this.businessType = businessType;
    }
    
    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public Date getContractOpenDate()
    {
        return contractOpenDate;
    }
    public void setContractOpenDate(Date contractOpenDate)
    {
        this.contractOpenDate = contractOpenDate;
    }
    public String getCurrentProvider()
    {
        return currentProvider;
    }
    public void setCurrentProvider(String currentProvider)
    {
        this.currentProvider = currentProvider;
    }
    
    /**
     * @return Returns the customerId.
     */
    public Integer getCustomerId()
    {
        return customerId;
    }
    /**
     * @param customerId The customerId to set.
     */
    public void setCustomerId(Integer customerId)
    {
        this.customerId = customerId;
    }
    public String getCustomerName()
    {
        return customerName;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getFax()
    {
        return fax;
    }
    public void setFax(String fax)
    {
        this.fax = fax;
    }
    public boolean isValid()
    {
        return valid;
    }
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
    public String getMobile()
    {
        return mobile;
    }
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
    public int getProspectiveCustomerId()
    {
        return prospectiveCustomerId;
    }
    public void setProspectiveCustomerId(int prospectiveCustomerId)
    {
        this.prospectiveCustomerId = prospectiveCustomerId;
    }
    public String getState()
    {
        return state;
    }
    public void setState(String state)
    {
        this.state = state;
    }
    public Date getStatusDate()
    {
        return statusDate;
    }
    public void setStatusDate(Date statusDate)
    {
        this.statusDate = statusDate;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public CustomerStatusVO getCustomerStatus()
    {
        return customerStatus;
    }
    public void setCustomerStatus(CustomerStatusVO customerStatus)
    {
        this.customerStatus = customerStatus;
    }
    public UsersVO getSalesRep()
    {
        return salesRep;
    }
    public void setSalesRep(UsersVO salesRep)
    {
        this.salesRep = salesRep;
    }
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public Date getImportedPICOn()
    {
        return importedPICOn;
    }
    public void setImportedPICOn(Date importedPICOn)
    {
        this.importedPICOn = importedPICOn;
    }
    public Set getCustomerPreferenceProducts()
    {
        return customerPreferenceProducts;
    }
    public void setCustomerPreferenceProducts(Set customerPreferenceProducts)
    {
        this.customerPreferenceProducts = customerPreferenceProducts;
    }
    public Set getCustomerPreferenceTerms()
    {
        return customerPreferenceTerms;
    }
    public void setCustomerPreferenceTerms(Set customerPreferenceTerms)
    {
        this.customerPreferenceTerms = customerPreferenceTerms;
    }
    public Set getPicVOs()
    {
        return picVOs;
    }
    public void setPicVOs(Set picVOs)
    {
        this.picVOs = picVOs;
    }
}
