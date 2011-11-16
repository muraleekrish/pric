/*
 * Created on Mar 30, 2007
 *
 * ClassName	:  	TLFVO.java
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
public class TLFVO implements Serializable
{

    public TLFVO()
    {
    }
    private SeasonsVO season;
    private float onPeakLoss;
    private float offPeakLoss;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private boolean valid;
    
    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    public Date getCreatedDate()
    {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }
    public String getModifiedBy()
    {
        return modifiedBy;
    }
    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }
    public Date getModifiedDate()
    {
        return modifiedDate;
    }
    public void setModifiedDate(Date modifiedDate)
    {
        this.modifiedDate = modifiedDate;
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
   
    public SeasonsVO getSeason()
    {
        return season;
    }
    public void setSeason(SeasonsVO season)
    {
        this.season = season;
    }
    public boolean isValid()
    {
        return valid;
    }
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
}

/*
*$Log: TLFVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/03/31 06:22:23  kduraisamy
*initial commit.
*
*
*/