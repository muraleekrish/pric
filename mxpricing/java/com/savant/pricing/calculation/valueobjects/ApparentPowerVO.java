/*
 * Created on Feb 13, 2007
 *
 * ClassName	:  	ApparentPowerVO.java
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
public class ApparentPowerVO implements Serializable
{
    private PricingUsageHeaderVO usageRef;
    private UOMVO unit;
    private float apparentPower;

    public ApparentPowerVO()
    {
    }
    

    public float getApparentPower()
    {
        return apparentPower;
    }
    public void setApparentPower(float apparentPower)
    {
        this.apparentPower = apparentPower;
    }
    public UOMVO getUnit()
    {
        return unit;
    }
    public void setUnit(UOMVO unit)
    {
        this.unit = unit;
    }
    public PricingUsageHeaderVO getUsageRef()
    {
        return usageRef;
    }
    public void setUsageRef(PricingUsageHeaderVO usageRef)
    {
        this.usageRef = usageRef;
    }
}

/*
*$Log: ApparentPowerVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/02/13 14:05:04  kduraisamy
*BillingDemand related mapping completed.
*
*
*/