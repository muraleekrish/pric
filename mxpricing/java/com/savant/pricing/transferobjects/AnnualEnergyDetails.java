/*
 * Created on Feb 19, 2007
 *
 * ClassName	:  	AnnualEnergyDetails.java
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

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AnnualEnergyDetails implements Serializable
{
    private int month;
    private float usagekWh;
    private float energyDemandkW;
    private float loadFactor;
    
    public AnnualEnergyDetails()
    {
    }
    
    public float getEnergyDemandkW()
    {
        return energyDemandkW;
    }
    public void setEnergyDemandkW(float energyDemandkW)
    {
        this.energyDemandkW = energyDemandkW;
    }
    public float getLoadFactor()
    {
        return loadFactor;
    }
    public void setLoadFactor(float loadFactor)
    {
        this.loadFactor = loadFactor;
    }
    public int getMonth()
    {
        return month;
    }
    public void setMonth(int month)
    {
        this.month = month;
    }
    public float getUsagekWh()
    {
        return usagekWh;
    }
    public void setUsagekWh(float usagekWh)
    {
        this.usagekWh = usagekWh;
    }
}

/*
*$Log: AnnualEnergyDetails.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/02/19 07:24:36  kduraisamy
*graph details object
*
*
*/