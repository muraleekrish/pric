/*
 * Created on Feb 5, 2007
 *
 * ClassName	:  	PICUsageVO.java
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


/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PICUsageVO implements Serializable
{
    private long id;
    private PICVO picRef;
    private Date meterReadDate;
    private int month;
    private int noOfDays;
    private float historicalUsage;
    private float historicalDemand;
    private float historicalApparentPower;
    private UOMVO historicalUsageUnit;
    private UOMVO historicalDemandUnit;
    private UOMVO historicalApparentPowerUnit;
    private float perDayHistoricalUsage;
    public PICUsageVO()
    {
    }
    
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }
    public float getHistoricalApparentPower()
    {
        return historicalApparentPower;
    }
    public void setHistoricalApparentPower(float historicalApparentPower)
    {
        this.historicalApparentPower = historicalApparentPower;
    }
    public UOMVO getHistoricalApparentPowerUnit()
    {
        return historicalApparentPowerUnit;
    }
    public void setHistoricalApparentPowerUnit(UOMVO historicalApparentPowerUnit)
    {
        this.historicalApparentPowerUnit = historicalApparentPowerUnit;
    }
    public float getHistoricalDemand()
    {
        return historicalDemand;
    }
    public void setHistoricalDemand(float historicalDemand)
    {
        this.historicalDemand = historicalDemand;
    }
    public UOMVO getHistoricalDemandUnit()
    {
        return historicalDemandUnit;
    }
    public void setHistoricalDemandUnit(UOMVO historicalDemandUnit)
    {
        this.historicalDemandUnit = historicalDemandUnit;
    }
    public float getHistoricalUsage()
    {
        return historicalUsage;
    }
    public void setHistoricalUsage(float historicalUsage)
    {
        this.historicalUsage = historicalUsage;
    }
    public UOMVO getHistoricalUsageUnit()
    {
        return historicalUsageUnit;
    }
    public void setHistoricalUsageUnit(UOMVO historicalUsageUnit)
    {
        this.historicalUsageUnit = historicalUsageUnit;
    }
    public Date getMeterReadDate()
    {
        return meterReadDate;
    }
    public void setMeterReadDate(Date meterReadDate)
    {
        this.meterReadDate = meterReadDate;
    }
    public int getMonth()
    {
        return month;
    }
    public void setMonth(int month)
    {
        this.month = month;
    }
    public int getNoOfDays()
    {
        return noOfDays;
    }
    public void setNoOfDays(int noOfDays)
    {
        this.noOfDays = noOfDays;
    }
    public float getPerDayHistoricalUsage()
    {
        return perDayHistoricalUsage;
    }
    public void setPerDayHistoricalUsage(float perDayHistoricalUsage)
    {
        this.perDayHistoricalUsage = perDayHistoricalUsage;
    }
    public PICVO getPicRef()
    {
        return picRef;
    }
    public void setPicRef(PICVO picRef)
    {
        this.picRef = picRef;
    }
}

/*
*$Log: PICUsageVO.java,v $
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