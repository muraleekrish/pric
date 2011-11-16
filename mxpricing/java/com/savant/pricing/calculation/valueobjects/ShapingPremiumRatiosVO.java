/*
 * Created on Feb 12, 2007
 *
 * ClassName	:  	ShapingPremiumRatiosVO.java
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

import com.savant.pricing.valueobjects.CongestionZonesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ShapingPremiumRatiosVO implements Serializable
{
    private String loadProfile;
    private CongestionZonesVO congestionZone;
    private int month;
    private PriceBlockHeaderVO priceBlock;
    private float ratio;
    public ShapingPremiumRatiosVO()
    {
    }
    public CongestionZonesVO getCongestionZone()
    {
        return congestionZone;
    }
    public void setCongestionZone(CongestionZonesVO congestionZone)
    {
        this.congestionZone = congestionZone;
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
    public PriceBlockHeaderVO getPriceBlock()
    {
        return priceBlock;
    }
    public void setPriceBlock(PriceBlockHeaderVO priceBlock)
    {
        this.priceBlock = priceBlock;
    }
    public float getRatio()
    {
        return ratio;
    }
    public void setRatio(float ratio)
    {
        this.ratio = ratio;
    }
}

/*
*$Log: ShapingPremiumRatiosVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/02/12 06:55:10  kduraisamy
*initial commit.
*
*
*/