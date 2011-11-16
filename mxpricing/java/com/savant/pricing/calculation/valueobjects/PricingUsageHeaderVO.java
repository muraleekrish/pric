/*
 * Created on Feb 6, 2007
 *
 * ClassName	:  	UsageHeaderVO.java
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

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates 
 */
public class PricingUsageHeaderVO implements Serializable
{
    private int pricingUsageRefId;
    private PriceRunCustomerVO priceRunCustomer;
    private String esiId;
    private int term;
    public PricingUsageHeaderVO()
    {
    }
    public String getEsiId()
    {
        return esiId;
    }
    public void setEsiId(String esiId)
    {
        this.esiId = esiId;
    }
    public PriceRunCustomerVO getPriceRunCustomer()
    {
        return priceRunCustomer;
    }
    public void setPriceRunCustomer(PriceRunCustomerVO priceRunCustomer)
    {
        this.priceRunCustomer = priceRunCustomer;
    }
    public int getPricingUsageRefId()
    {
        return pricingUsageRefId;
    }
    public void setPricingUsageRefId(int pricingUsageRefId)
    {
        this.pricingUsageRefId = pricingUsageRefId;
    }
    public int getTerm()
    {
        return term;
    }
    public void setTerm(int term)
    {
        this.term = term;
    }
}

/*
*$Log: PricingUsageHeaderVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/02/09 11:56:55  kduraisamy
*pricing core algorithm almost finished.
*
*
*/