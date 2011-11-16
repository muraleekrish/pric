/*
 * Created on Feb 6, 2007
 *
 * ClassName	:  	TempActualUsageVO.java
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
import com.savant.pricing.valueobjects.DayTypesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ActualUsageAggregationVO implements Serializable
{
    private String esiId;
    private String loadProfile;
    private int month;
    private int hour;
    private DayTypesVO dayType;
    private float value;
    
    
    private int noOfWD;
    private int noOfWE;
    
    private float perDayUsage;
    private float perDayUsageWL;
    
    
    public ActualUsageAggregationVO()
    {
    }
    
    public float getPerDayUsageWL()
    {
        return perDayUsageWL;
    }
    public void setPerDayUsageWL(float perDayUsageWL)
    {
        this.perDayUsageWL = perDayUsageWL;
    }
    public DayTypesVO getDayType()
    {
        return dayType;
    }
    public void setDayType(DayTypesVO dayType)
    {
        this.dayType = dayType;
    }
    public String getEsiId()
    {
        return esiId;
    }
    public void setEsiId(String esiId)
    {
        this.esiId = esiId;
    }
    public int getHour()
    {
        return hour;
    }
    public void setHour(int hour)
    {
        this.hour = hour;
    }
    public String getLoadProfile()
    {
        return loadProfile;
    }
    public void setLoadProfile(String loadProfile)
    {
        this.loadProfile = loadProfile;
    }
    
    public int getMonth()
    {
        return month;
    }
    public void setMonth(int month)
    {
        this.month = month;
    }
    public float getValue()
    {
        return value;
    }
    public void setValue(float value)
    {
        this.value = value;
    }
    
    public int getNoOfWD()
    {
        return noOfWD;
    }
    public void setNoOfWD(int noOfWD)
    {
        this.noOfWD = noOfWD;
    }
    public int getNoOfWE()
    {
        return noOfWE;
    }
    public void setNoOfWE(int noOfWE)
    {
        this.noOfWE = noOfWE;
    }
    public float getPerDayUsage()
    {
        return perDayUsage;
    }
    public void setPerDayUsage(float perDayUsage)
    {
        this.perDayUsage = perDayUsage;
    }
}

/*
*$Log: ActualUsageAggregationVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.6  2007/08/23 07:26:29  jnadesan
*imports organized
*
*Revision 1.5  2007/04/06 12:32:19  kduraisamy
*TLF AND DLF COMPLETED.
*
*Revision 1.4  2007/04/02 12:25:31  kduraisamy
*loss factor computation removed.
*
*Revision 1.3  2007/03/31 06:41:02  kduraisamy
*losses included.
*
*Revision 1.2  2007/03/16 10:35:12  kduraisamy
*dividedByZero Error Corrected.
*
*Revision 1.1  2007/03/08 16:32:23  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.1  2007/02/09 11:56:55  kduraisamy
*pricing core algorithm almost finished.
*
*
*/