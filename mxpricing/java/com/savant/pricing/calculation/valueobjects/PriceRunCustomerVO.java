/*
 * Created on Feb 6, 2007
 *
 * ClassName	:  	PriceRunHeaderVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.calculation.valueobjects;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PriceRunCustomerVO implements Serializable
{
    private int priceRunCustomerRefId;
    private PriceRunHeaderVO priceRunRef;
    private ProspectiveCustomerVO prospectiveCustomer;
    private boolean runStatus;
    private String reason;
    private Date startDate;
    private boolean taxExempt;
    private Set customerContracts = new HashSet();
    
    public PriceRunCustomerVO()
    {
    }
    public boolean isTaxExempt() {
        return taxExempt;
    }
    public void setTaxExempt(boolean taxExempt) {
        this.taxExempt = taxExempt;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public String getReason()
    {
        return reason;
    }
    public void setReason(String reason)
    {
        this.reason = reason;
    }
    public boolean isRunStatus()
    {
        return runStatus;
    }
    public void setRunStatus(boolean runStatus)
    {
        this.runStatus = runStatus;
    }
    public int getPriceRunCustomerRefId()
    {
        return priceRunCustomerRefId;
    }
    public void setPriceRunCustomerRefId(int priceRunCustomerRefId)
    {
        this.priceRunCustomerRefId = priceRunCustomerRefId;
    }
    public PriceRunHeaderVO getPriceRunRef()
    {
        return priceRunRef;
    }
    public void setPriceRunRef(PriceRunHeaderVO priceRunRef)
    {
        this.priceRunRef = priceRunRef;
    }
    public ProspectiveCustomerVO getProspectiveCustomer()
    {
        return prospectiveCustomer;
    }
    public void setProspectiveCustomer(ProspectiveCustomerVO prospectiveCustomer)
    {
        this.prospectiveCustomer = prospectiveCustomer;
    }
    
    public Set getCustomerContracts()
    {
        return customerContracts;
    }
    public void setCustomerContracts(Set customerContracts)
    {
        this.customerContracts = customerContracts;
    }
}

/*
*$Log: PriceRunCustomerVO.java,v $
*Revision 1.2  2008/02/14 05:43:22  tannamalai
*pagination done for price quote page
*
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/07/18 04:54:37  jnadesan
*tax exception added
*
*Revision 1.3  2007/07/05 13:26:30  jnadesan
*startdate maintained run wise
*
*Revision 1.2  2007/04/21 12:29:56  kduraisamy
*status and reason added.
*
*Revision 1.1  2007/02/09 11:56:55  kduraisamy
*pricing core algorithm almost finished.
*
*
*/