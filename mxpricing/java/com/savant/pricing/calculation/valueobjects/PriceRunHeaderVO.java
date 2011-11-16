/*
 * Created on Feb 6, 2007
 *
 * ClassName	:  	PriceRunHeaderVO.java
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

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PriceRunHeaderVO implements Serializable
{
    private String priceRunRefNo;
    private Date priceRunTime;
    private Date fwdCurveDate;
    private float gasPrice;
    private String runBy;
    private String runType;
    private String comments;
    private Date gasPriceDate;
    
    
    public Date getGasPriceDate()
    {
        return gasPriceDate;
    }
    public void setGasPriceDate(Date gasPriceDate)
    {
        this.gasPriceDate = gasPriceDate;
    }
    public PriceRunHeaderVO()
    {
    }
    public Date getFwdCurveDate()
    {
        return fwdCurveDate;
    }
    public void setFwdCurveDate(Date fwdCurveDate)
    {
        this.fwdCurveDate = fwdCurveDate;
    }
    
    public float getGasPrice()
    {
        return gasPrice;
    }
    public void setGasPrice(float gasPrice)
    {
        this.gasPrice = gasPrice;
    }
    public String getRunType()
    {
        return runType;
    }
    public void setRunType(String runType)
    {
        this.runType = runType;
    }
    public String getComments()
    {
        return comments;
    }
    public void setComments(String comments)
    {
        this.comments = comments;
    }
    public String getPriceRunRefNo()
    {
        return priceRunRefNo;
    }
    public void setPriceRunRefNo(String priceRunRefNo)
    {
        this.priceRunRefNo = priceRunRefNo;
    }
    public Date getPriceRunTime()
    {
        return priceRunTime;
    }
    public void setPriceRunTime(Date priceRunTime)
    {
        this.priceRunTime = priceRunTime;
    }
    public String getRunBy()
    {
        return runBy;
    }
    public void setRunBy(String runBy)
    {
        this.runBy = runBy;
    }
}

/*
*$Log: PriceRunHeaderVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/30 10:44:57  tannamalai
*new column added in pricerunheader
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/05/04 06:34:50  kduraisamy
*gas price added in runHeaderVO.
*
*Revision 1.3  2007/04/30 09:05:11  kduraisamy
*forward Curve Date included.
*
*Revision 1.2  2007/04/26 12:28:11  kduraisamy
*runType Added.
*
*Revision 1.1  2007/02/09 11:56:55  kduraisamy
*pricing core algorithm almost finished.
*
*
*/