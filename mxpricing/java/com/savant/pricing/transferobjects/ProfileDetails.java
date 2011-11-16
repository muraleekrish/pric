/*
 * Created on Feb 9, 2007
 *
 * ClassName	:  	ProfileDetails.java
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
public class ProfileDetails implements Serializable
{
    private String profileString;
    private String profileType;
    private int profileTypeId;
    private String loadProfile;
    private int congestionZoneId;
    private int weatherZoneId;
    private String weatherZoneCode;
    private String meterType;
    public ProfileDetails()
    {
    }
    
    public int getProfileTypeId()
    {
        return profileTypeId;
    }
    public void setProfileTypeId(int profileTypeId)
    {
        this.profileTypeId = profileTypeId;
    }
    public String getWeatherZoneCode()
    {
        return weatherZoneCode;
    }
    public void setWeatherZoneCode(String weatherZoneCode)
    {
        this.weatherZoneCode = weatherZoneCode;
    }
    public int getCongestionZoneId()
    {
        return congestionZoneId;
    }
    public void setCongestionZoneId(int congestionZoneId)
    {
        this.congestionZoneId = congestionZoneId;
    }
    
    public String getProfileType()
    {
        return profileType;
    }
    public void setProfileType(String profileType)
    {
        this.profileType = profileType;
    }
    public String getLoadProfile()
    {
        return loadProfile;
    }
    public void setLoadProfile(String loadProfile)
    {
        this.loadProfile = loadProfile;
    }
    public String getProfileString()
    {
        return profileString;
    }
    public void setProfileString(String profileString)
    {
        this.profileString = profileString;
    }
    public int getWeatherZoneId()
    {
        return weatherZoneId;
    }
    public void setWeatherZoneId(int weatherZoneId)
    {
        this.weatherZoneId = weatherZoneId;
    }
    
    public String getMeterType()
    {
        return meterType;
    }
    public void setMeterType(String meterType)
    {
        this.meterType = meterType;
    }
}

/*
*$Log: ProfileDetails.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/02/13 14:05:23  kduraisamy
*BillingDemand related mapping completed.
*
*Revision 1.1  2007/02/09 11:58:04  kduraisamy
*pricing core algorithm almost finished.
*
*
*/