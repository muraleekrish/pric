/*
 * Created on Apr 17, 2007
 *
 * ClassName	:  	OnPkOffPkDetails.java
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
public class OnPkOffPkDetails implements Serializable
{
    private float onPeakLoss;
    private float offPeakLoss;
    public OnPkOffPkDetails()
    {
    }
    public float getOffPeakLoss()
    {
        return offPeakLoss;
    }
    public void setOffPeakLoss(float offPeakLoss)
    {
        this.offPeakLoss = offPeakLoss;
    }
    public float getOnPeakLoss()
    {
        return onPeakLoss;
    }
    public void setOnPeakLoss(float onPeakLoss)
    {
        this.onPeakLoss = onPeakLoss;
    }
}

/*
*$Log: OnPkOffPkDetails.java,v $
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