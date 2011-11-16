/*
 * Created on Feb 5, 2007
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

import com.savant.pricing.valueobjects.DayTypesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TempProfileUsageVO implements Serializable
{
    private String esiId;
    private String loadProfile;
    private int term;
    private int month;
    private int hour;
    private DayTypesVO dayType;
    private float value;
    private int noOfWD;
    private int noOfWE;
    
    

    public TempProfileUsageVO()
    {
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
    
    public int getTerm()
    {
        return term;
    }
    public void setTerm(int term)
    {
        this.term = term;
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
}

/*
*$Log: TempProfileUsageVO.java,v $
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