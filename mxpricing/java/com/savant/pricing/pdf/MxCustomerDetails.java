/*
 * 
 * MxCustomerDetails.java    Nov 22, 2007
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
package com.savant.pricing.pdf;

import java.util.Date;

/**
 * 
 */
public class MxCustomerDetails
{
    private String customerName;
    private String physicalAddress;
    private String billingAddress;
    private String noticeContactName;
    private String phoneNumber;
    private String fax;
    private String emailAddress;
    private String securityNo;
    private String term;
    private String dollarperkWh;
    private Date   contractStartDate;
    private String monthlyPrice;
    private String productName;
    private String monthlySerChrge;

    /**
     *  
     */
    public MxCustomerDetails(String customerName, String physicalAddress,
            String billingAddress, String noticeContactName,
            String phoneNumber, String fax, String emailAddress,
            String securityNo, String term, String dollarperkWh,
            Date contractStartDate, String monthlyPrice, String productName,String monthlySerChrge)
    {
        this.customerName = customerName;
        this.physicalAddress = physicalAddress;
        this.billingAddress = billingAddress;
        this.noticeContactName = noticeContactName;
        this.phoneNumber = phoneNumber;
        this.fax = fax;
        this.emailAddress = emailAddress;
        this.securityNo = securityNo;
        this.term = term;
        this.dollarperkWh = dollarperkWh;
        this.contractStartDate = contractStartDate;
        this.monthlyPrice = monthlyPrice;
        this.productName = productName;
        this.monthlySerChrge = monthlySerChrge;
    }

    public String getMonthlyPrice()
    {
        return monthlyPrice;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setMonthlyPrice(String monthlyPrice)
    {
        this.monthlyPrice = monthlyPrice;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getBillingAddress()
    {
        return billingAddress;
    }

    public Date getContractStartDate()
    {
        return contractStartDate;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public String getFax()
    {
        return fax;
    }

    public String getNoticeContactName()
    {
        return noticeContactName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getPhysicalAddress()
    {
        return physicalAddress;
    }

    public String getSecurityNo()
    {
        return securityNo;
    }

    public String getTerm()
    {
        return term;
    }

    public String getDollarperkWh()
    {
        return dollarperkWh;
    }

    public void setDollarperkWh(String dollarperkWh)
    {
        this.dollarperkWh = dollarperkWh;
    }

    public void setBillingAddress(String billingAddress)
    {
        this.billingAddress = billingAddress;
    }

    public void setContractStartDate(Date contractStartDate)
    {
        this.contractStartDate = contractStartDate;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public void setFax(String fax)
    {
        this.fax = fax;
    }

    public void setNoticeContactName(String noticeContactName)
    {
        this.noticeContactName = noticeContactName;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setPhysicalAddress(String physicalAddress)
    {
        this.physicalAddress = physicalAddress;
    }

    public void setSecurityNo(String securityNo)
    {
        this.securityNo = securityNo;
    }

    public void setTerm(String term)
    {
        this.term = term;
    }
    
  
    public String getMonthlySerChrge()
    {
        return monthlySerChrge;
    }
    public void setMonthlySerChrge(String monthlySerChrge)
    {
        this.monthlySerChrge = monthlySerChrge;
    }
}
/*
 *$Log: MxCustomerDetails.java,v $
 *Revision 1.3  2008/11/21 09:47:11  jvediyappan
 *Trieagle changed as MXEnergy.
 *
 *Revision 1.2  2008/02/27 10:32:10  tannamalai
 *enerygy diff added to mcpe
 *
 *Revision 1.1  2007/12/07 06:18:35  jvediyappan
 *initial commit.
 *
 *Revision 1.1  2007/11/23 08:44:06  jnadesan
 *initial commit
 *
 *
 */