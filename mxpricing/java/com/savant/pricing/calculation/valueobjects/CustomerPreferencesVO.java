/*
 * Created on Feb 7, 2007
 *
 * ClassName	:  	PricingCustomerPreferencesVO.java
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

import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CustomerPreferencesVO implements Serializable
{
    private ProspectiveCustomerVO prospectiveCustomer;
    private Date contractStartDate;
    private Date contractEndDate;
    private boolean autoRun;
    private boolean unitary;
    private Date priceRunEligibleDate;
    private boolean priceReRun;
    public CustomerPreferencesVO()
    {
    }
    
    public boolean isUnitary()
    {
        return unitary;
    }
    public void setUnitary(boolean unitary)
    {
        this.unitary = unitary;
    }
    public boolean isAutoRun()
    {
        return autoRun;
    }
    public void setAutoRun(boolean autoRun)
    {
        this.autoRun = autoRun;
    }
    public Date getContractEndDate()
    {
        return contractEndDate;
    }
    public void setContractEndDate(Date contractEndDate)
    {
        this.contractEndDate = contractEndDate;
    }
    public Date getContractStartDate()
    {
        return contractStartDate;
    }
    public void setContractStartDate(Date contractStartDate)
    {
        this.contractStartDate = contractStartDate;
    }
    public boolean isPriceReRun()
    {
        return priceReRun;
    }
    public void setPriceReRun(boolean priceReRun)
    {
        this.priceReRun = priceReRun;
    }
    public Date getPriceRunEligibleDate()
    {
        return priceRunEligibleDate;
    }
    public void setPriceRunEligibleDate(Date priceRunEligibleDate)
    {
        this.priceRunEligibleDate = priceRunEligibleDate;
    }
   
    public ProspectiveCustomerVO getProspectiveCustomer() {
        return prospectiveCustomer;
    }
    public void setProspectiveCustomer(ProspectiveCustomerVO prospectiveCustomer) {
        this.prospectiveCustomer = prospectiveCustomer;
    }
}

/*
*$Log: CustomerPreferencesVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.6  2007/06/14 10:14:19  kduraisamy
*unitary added for shaping premium.
*
*Revision 1.5  2007/03/14 08:25:33  srajappan
*prospectiveCustomerId changed to an prospectiveCustomer object.
*
*Revision 1.4  2007/02/28 10:03:30  kduraisamy
*preference terms taken out.
*
*Revision 1.3  2007/02/28 09:48:56  kduraisamy
*preference terms taken out.
*
*Revision 1.2  2007/02/10 06:00:42  kduraisamy
*alignment.
*
*Revision 1.1  2007/02/09 11:56:55  kduraisamy
*pricing core algorithm almost finished.
*
*
*/