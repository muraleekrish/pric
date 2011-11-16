/*
 * 
 * MatrixMonthlyWeightsVO.java    Aug 24, 2007
 * 
 * Copyright (c) Savant Technologies Pvt Ltd.
 * 'Savant House' 127 Chamiers Road, Nandanam, Chennai-600 035. INDIA 
 * All rights reserved.

 * @author: jnadesan
 * @company: Savant Technologies
 * @client: MX Energy
 * @version: 
 * @Description: 
 * 
*/

package com.savant.pricing.matrixpricing.valueobjects;

import java.io.Serializable;
import com.savant.pricing.valueobjects.CongestionZonesVO;
import com.savant.pricing.valueobjects.LoadProfileTypesVO;
import com.savant.pricing.valueobjects.WeatherZonesVO;

/**
 * 
 */
public class MatrixMonthlyWeightsVO implements Serializable
{
    private LoadProfileTypesVO loadProfile;
    private CongestionZonesVO congestionZone;
    private WeatherZonesVO weatherZone;
    private int month;
    private float value;
    
    public CongestionZonesVO getCongestionZone()
    {
        return congestionZone;
    }
    
    public int getMonth()
    {
        return month;
    }
    public float getValue()
    {
        return value;
    }
    public WeatherZonesVO getWeatherZone()
    {
        return weatherZone;
    }
    public void setWeatherZone(WeatherZonesVO weatherZone)
    {
        this.weatherZone = weatherZone;
    }
    public void setCongestionZone(CongestionZonesVO congestionZone)
    {
        this.congestionZone = congestionZone;
    }
    public LoadProfileTypesVO getLoadProfile()
    {
        return loadProfile;
    }
    public void setLoadProfile(LoadProfileTypesVO loadProfile)
    {
        this.loadProfile = loadProfile;
    }
    public void setMonth(int month)
    {
        this.month = month;
    }
    public void setValue(float value)
    {
        this.value = value;
    }
}


/*
*$Log: MatrixMonthlyWeightsVO.java,v $
*Revision 1.2  2008/11/21 09:47:03  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:24  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/08/27 15:13:49  kduraisamy
*weatherzone id added into condition
*
*Revision 1.1  2007/08/27 04:42:37  jnadesan
*initial commit for MonthlyWeights
*
*
*/