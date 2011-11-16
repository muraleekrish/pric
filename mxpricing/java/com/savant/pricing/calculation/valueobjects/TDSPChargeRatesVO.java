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

import com.savant.pricing.valueobjects.TDSPRateCodesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TDSPChargeRatesVO implements Serializable
{
    private TDSPChargeNamesVO tdspChargeName;
    private TDSPRateCodesVO tdspRateCode;
    private UOMVO unit;
    private float charge;
    public TDSPChargeRatesVO()
    {
    }
    
    public TDSPChargeNamesVO getTdspChargeName()
    {
        return tdspChargeName;
    }
    public void setTdspChargeName(TDSPChargeNamesVO tdspChargeName)
    {
        this.tdspChargeName = tdspChargeName;
    }
    public TDSPRateCodesVO getTdspRateCode()
    {
        return tdspRateCode;
    }
    public void setTdspRateCode(TDSPRateCodesVO tdspRateCode)
    {
        this.tdspRateCode = tdspRateCode;
    }
    public float getCharge()
    {
        return charge;
    }
    public void setCharge(float charge)
    {
        this.charge = charge;
    }
  
    public UOMVO getUnit()
    {
        return unit;
    }
    public void setUnit(UOMVO unit)
    {
        this.unit = unit;
    }
   
}

/*
*$Log: TDSPChargeRatesVO.java,v $
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