/*
 * Created on Feb 7, 2007
 *
 * ClassName	:  	UsageVO.java
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

import com.savant.pricing.valueobjects.DayTypesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UsageVO implements Serializable
{
    private PricingUsageHeaderVO usageRef;
    private Date termMonth;
    private DayTypesVO dayType;
    private UOMVO unit;
    private int hour;
    private Date usageStartDate;
    private Date usageEndDate;
    private float forecastedUsage;
    private float forecastedUsageWL;
    
    public UsageVO()
    {
    }
    public float getForecastedUsageWL()
    {
        return forecastedUsageWL;
    }
    public void setForecastedUsageWL(float forecastedUsageWL)
    {
        this.forecastedUsageWL = forecastedUsageWL;
    }
    public DayTypesVO getDayType()
    {
        return dayType;
    }
    public void setDayType(DayTypesVO dayType)
    {
        this.dayType = dayType;
    }
    
    public UOMVO getUnit()
    {
        return unit;
    }
    public void setUnit(UOMVO unit)
    {
        this.unit = unit;
    }
    public float getForecastedUsage()
    {
        return forecastedUsage;
    }
    public void setForecastedUsage(float forecastedUsage)
    {
        this.forecastedUsage = forecastedUsage;
    }
    public int getHour()
    {
        return hour;
    }
    public void setHour(int hour)
    {
        this.hour = hour;
    }
    public Date getTermMonth()
    {
        return termMonth;
    }
    public void setTermMonth(Date termMonth)
    {
        this.termMonth = termMonth;
    }
    public Date getUsageEndDate()
    {
        return usageEndDate;
    }
    public void setUsageEndDate(Date usageEndDate)
    {
        this.usageEndDate = usageEndDate;
    }
    public PricingUsageHeaderVO getUsageRef()
    {
        return usageRef;
    }
    public void setUsageRef(PricingUsageHeaderVO usageRef)
    {
        this.usageRef = usageRef;
    }
    public Date getUsageStartDate()
    {
        return usageStartDate;
    }
    public void setUsageStartDate(Date usageStartDate)
    {
        this.usageStartDate = usageStartDate;
    }
}

/*
*$Log: UsageVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/04/06 12:32:19  kduraisamy
*TLF AND DLF COMPLETED.
*
*Revision 1.1  2007/02/09 11:56:55  kduraisamy
*pricing core algorithm almost finished.
*
*
*/