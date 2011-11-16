/*
 * Created on Aug 10, 2007
 *
 * ClassName	:  	PriceRunResultDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.transferobjects;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PriceRunResultDetails implements Serializable
{
    private Date runDateTime;
    private float fixedPrice;
    private float fuelFactor;
    private float baseRate;
    
    public float getFixedPrice()
    {
        return fixedPrice;
    }
    public void setFixedPrice(float fixedPrice)
    {
        this.fixedPrice = fixedPrice;
    }
    public float getFuelFactor()
    {
        return fuelFactor;
    }
    public void setFuelFactor(float fuelFactor)
    {
        this.fuelFactor = fuelFactor;
    }
    public Date getRunDateTime()
    {
        return runDateTime;
    }
    public void setRunDateTime(Date runDateTime)
    {
        this.runDateTime = runDateTime;
    }
   
    public float getBaseRate()
    {
        return baseRate;
    }
    public void setBaseRate(float baseRate)
    {
        this.baseRate = baseRate;
    }
}

/*
*$Log: PriceRunResultDetails.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/08/10 10:06:22  jnadesan
*baserate also selected from query
*
*Revision 1.1  2007/08/10 07:16:18  kduraisamy
*i9nitial commit for summary page
*
*
*/