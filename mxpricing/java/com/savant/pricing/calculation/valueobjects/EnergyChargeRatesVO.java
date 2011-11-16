/*
 * Created on Feb 8, 2007
 *
 * ClassName	:  	EnergyChargeRatesVO.java
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

import com.savant.pricing.valueobjects.CongestionZonesVO;
import com.savant.pricing.valueobjects.LoadProfileTypesVO;
import com.savant.pricing.valueobjects.WeatherZonesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EnergyChargeRatesVO implements Serializable
{
    private int engId;
    private Date monthYear;
    private EnergyChargeNamesVO energyChargeName;
    private UOMVO unit;
    private float charge;
    private WeatherZonesVO weatherZone;
    private LoadProfileTypesVO profileType;
    private CongestionZonesVO congestion;
    public EnergyChargeRatesVO()
    {
    }
    
    public float getCharge()
    {
        return charge;
    }
    public void setCharge(float charge)
    {
        this.charge = charge;
    }
    public CongestionZonesVO getCongestion()
    {
        return congestion;
    }
    public void setCongestion(CongestionZonesVO congestion)
    {
        this.congestion = congestion;
    }
    public int getEngId() {
        return engId;
    }
    public void setEngId(int engId) {
        this.engId = engId;
    }
    public EnergyChargeNamesVO getEnergyChargeName()
    {
        return energyChargeName;
    }
    public void setEnergyChargeName(EnergyChargeNamesVO energyChargeName)
    {
        this.energyChargeName = energyChargeName;
    }
    public Date getMonthYear()
    {
        return monthYear;
    }
    public void setMonthYear(Date monthYear)
    {
        this.monthYear = monthYear;
    }
    public LoadProfileTypesVO getProfileType()
    {
        return profileType;
    }
    public void setProfileType(LoadProfileTypesVO profileType)
    {
        this.profileType = profileType;
    }
    public UOMVO getUnit()
    {
        return unit;
    }
    public void setUnit(UOMVO unit)
    {
        this.unit = unit;
    }
    public WeatherZonesVO getWeatherZone()
    {
        return weatherZone;
    }
    public void setWeatherZone(WeatherZonesVO weatherZone)
    {
        this.weatherZone = weatherZone;
    }
}

/*
*$Log: EnergyChargeRatesVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/07/11 13:20:09  jnadesan
*Energy chargerates primary added
*
*Revision 1.1  2007/02/09 11:56:55  kduraisamy
*pricing core algorithm almost finished.
*
*
*/