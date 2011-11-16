/*
 * 
 * MxContrcatACustomerDetails.java    Nov 23, 2007
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
public class MxContrcatACustomerDetails
{
    private String customerName;
    private String serviceAddress;
    private String billingAddress;
    private String noticeContactName;
    private String phoneNumber;
    private String fax;
    private String zipCodeState;
    private String emailAddress;
    private String term;
    private String dollarperkWh;
    private Date   contractStartDate;
    private String monthlySerChrge;

    /**
     * 
     */
    public MxContrcatACustomerDetails(String customerName, String serviceAddress, String billingAddress, String noticeContactName, String phoneNumber, String fax, String zipCodeState, String emailAddress, String term, String dollarperkWh, Date contractStartDate,String monthlySerChrge)
    {
        this.customerName = customerName;
        this.serviceAddress = serviceAddress;
        this.billingAddress = billingAddress;
        this.noticeContactName = noticeContactName;
        this.phoneNumber = phoneNumber;
        this.fax = fax;
        this.zipCodeState = zipCodeState;
        this.emailAddress = emailAddress;
        this.term = term;
        this.dollarperkWh = dollarperkWh;
        this.contractStartDate = contractStartDate;
        this.monthlySerChrge = monthlySerChrge;
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
    public String getDollarperkWh()
    {
        return dollarperkWh;
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
   
    public String getServiceAddress()
    {
        return serviceAddress;
    }
    public String getTerm()
    {
        return term;
    }
    public String getZipCodeState()
    {
        return zipCodeState;
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
    public void setDollarperkWh(String dollarperkWh)
    {
        this.dollarperkWh = dollarperkWh;
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
    public void setServiceAddress(String serviceAddress)
    {
        this.serviceAddress = serviceAddress;
    }
    public void setTerm(String term)
    {
        this.term = term;
    }
    public void setZipCodeState(String zipCodeState)
    {
        this.zipCodeState = zipCodeState;
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
*$Log: MxContrcatACustomerDetails.java,v $
*Revision 1.3  2008/11/21 09:47:11  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.2  2008/02/27 10:32:10  tannamalai
*enerygy diff added to mcpe
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/11/23 14:32:36  jnadesan
*initial commit
*
*
*/