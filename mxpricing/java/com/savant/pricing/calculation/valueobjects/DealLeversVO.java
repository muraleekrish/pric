/*
 * Created on Feb 28, 2007
 *
 * ClassName	:  	DealLeversVO.java
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

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DealLeversVO implements Serializable
{
    private int dealLeverIdentifier;
    private String dealLever;
    private UOMVO unit;
    private float value;
    private boolean overRide;
    
    public DealLeversVO()
    {
    }
    
    public boolean isOverRide()
    {
        return overRide;
    }
    public void setOverRide(boolean overRide)
    {
        this.overRide = overRide;
    }
    public String getDealLever()
    {
        return dealLever;
    }
    public void setDealLever(String dealLever)
    {
        this.dealLever = dealLever;
    }
    public int getDealLeverIdentifier()
    {
        return dealLeverIdentifier;
    }
    public void setDealLeverIdentifier(int dealLeverIdentifier)
    {
        this.dealLeverIdentifier = dealLeverIdentifier;
    }
    public UOMVO getUnit()
    {
        return unit;
    }
    public void setUnit(UOMVO unit)
    {
        this.unit = unit;
    }
    public float getValue()
    {
        return value;
    }
    public void setValue(float value)
    {
        this.value = value;
    }
}

/*
*$Log: DealLeversVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/04/07 05:51:27  kduraisamy
*overRide added for deal Levers
*
*Revision 1.1  2007/02/28 09:15:29  kduraisamy
*system dealLevers and customerwise dealLevers mapping completed.
*
*
*/