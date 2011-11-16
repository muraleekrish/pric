/*
 * Created on May 7, 2007
 *
 * ClassName	:  	AggregatedLoadProfileDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.transferobjects;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AggregatedLoadProfileDetails
{

    public AggregatedLoadProfileDetails()
    {
    }
     private float onPeakMin;
     private float onPeakMax;
     private float onPeakAvg;
     private float offPeakMin;
     private float offPeakMax;
     private float offPeakAvg;
     
    
    public float getOffPeakAvg()
    {
        return offPeakAvg;
    }
    public void setOffPeakAvg(float offPeakAvg)
    {
        this.offPeakAvg = offPeakAvg;
    }
    public float getOffPeakMax()
    {
        return offPeakMax;
    }
    public void setOffPeakMax(float offPeakMax)
    {
        this.offPeakMax = offPeakMax;
    }
    public float getOffPeakMin()
    {
        return offPeakMin;
    }
    public void setOffPeakMin(float offPeakMin)
    {
        this.offPeakMin = offPeakMin;
    }
    public float getOnPeakAvg()
    {
        return onPeakAvg;
    }
    public void setOnPeakAvg(float onPeakAvg)
    {
        this.onPeakAvg = onPeakAvg;
    }
    public float getOnPeakMax()
    {
        return onPeakMax;
    }
    public void setOnPeakMax(float onPeakMax)
    {
        this.onPeakMax = onPeakMax;
    }
    public float getOnPeakMin()
    {
        return onPeakMin;
    }
    public void setOnPeakMin(float onPeakMin)
    {
        this.onPeakMin = onPeakMin;
    }
}

/*
*$Log: AggregatedLoadProfileDetails.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/05/07 13:07:44  kduraisamy
*initial commit.
*
*
*/