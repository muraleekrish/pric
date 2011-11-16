/*
 * Created on Apr 17, 2007
 *
 * ClassName	:  	HourValueDetails.java
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
public class HourValueDetails implements Serializable
{
    public HourValueDetails()
    {
    }
    private int hour;
    private float value;
    
    public int getHour()
    {
        return hour;
    }
    public void setHour(int hour)
    {
        this.hour = hour;
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
*$Log: HourValueDetails.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/17 13:49:12  kduraisamy
*price run performance took place.
*
*
*/