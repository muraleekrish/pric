/*
 * 
 * MMPriceRunHeaderVO.java    Aug 23, 2007
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
import java.util.Date;

/**
 * 
 */
public class MMPriceRunHeaderVO implements Serializable
{
    private String priceRunRefNo;
    private Date priceRunTime;
    private Date offerDate;
    private Date expiredate;
    private boolean status;
    private String runBy;
    private String comments;
    private float gasValue;
    private Date forwardCurveDate;
    private Date gasPriceDate;
    
    
    public boolean isStatus()
    {
        return status;
    }
    public void setStatus(boolean status)
    {
        this.status = status;
    }
    public String getComments()
    {
        return comments;
    }
    public Date getExpiredate()
    {
        return expiredate;
    }
    public Date getOfferDate()
    {
        return offerDate;
    }
    public String getPriceRunRefNo()
    {
        return priceRunRefNo;
    }
    public Date getPriceRunTime()
    {
        return priceRunTime;
    }
    public String getRunBy()
    {
        return runBy;
    }
    public Date getForwardCurveDate()
    {
        return forwardCurveDate;
    }
    public Date getGasPriceDate()
    {
        return gasPriceDate;
    }
    public float getGasValue()
    {
        return gasValue;
    }
    public void setForwardCurveDate(Date forwardCurveDate)
    {
        this.forwardCurveDate = forwardCurveDate;
    }
    public void setGasPriceDate(Date gasPriceDate)
    {
        this.gasPriceDate = gasPriceDate;
    }
    public void setGasValue(float gasValue)
    {
        this.gasValue = gasValue;
    }
    public void setComments(String comments)
    {
        this.comments = comments;
    }
    public void setExpiredate(Date expiredate)
    {
        this.expiredate = expiredate;
    }
    public void setOfferDate(Date offerDate)
    {
        this.offerDate = offerDate;
    }
    public void setPriceRunRefNo(String priceRunRefNo)
    {
        this.priceRunRefNo = priceRunRefNo;
    }
    public void setPriceRunTime(Date priceRunTime)
    {
        this.priceRunTime = priceRunTime;
    }
    public void setRunBy(String runBy)
    {
        this.runBy = runBy;
    }
}


/*
*$Log: MMPriceRunHeaderVO.java,v $
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
*Revision 1.2  2007/09/07 09:30:31  jnadesan
*GasPrice Value Stored by run wise
*
*Revision 1.1  2007/08/23 07:36:34  jnadesan
*initial commit MMPrice entry
*
*
*/