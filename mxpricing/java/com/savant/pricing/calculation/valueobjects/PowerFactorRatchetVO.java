/*
 * Created on Feb 13, 2007
 *
 * ClassName	:  	PowerFactorRatchetVO.java
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

import com.savant.pricing.valueobjects.TDSPRateCodesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PowerFactorRatchetVO implements Serializable
{
    private float powerFactor;
    private TDSPRateCodesVO tdspRateCode;
    private float demandRatchetPercent;
    public PowerFactorRatchetVO()
    {
    }

    public float getDemandRatchetPercent()
    {
        return demandRatchetPercent;
    }
    public void setDemandRatchetPercent(float demandRatchetPercent)
    {
        this.demandRatchetPercent = demandRatchetPercent;
    }
    public float getPowerFactor()
    {
        return powerFactor;
    }
    public void setPowerFactor(float powerFactor)
    {
        this.powerFactor = powerFactor;
    }
    public TDSPRateCodesVO getTdspRateCode()
    {
        return tdspRateCode;
    }
    public void setTdspRateCode(TDSPRateCodesVO tdspRateCode)
    {
        this.tdspRateCode = tdspRateCode;
    }
}

/*
*$Log: PowerFactorRatchetVO.java,v $
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