/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	RateCodesVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.valueobjects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RateCodesVO implements Serializable
{
    private int rateCodeIdentifier;
    private TDSPVO tdsp;
    private String rateCode;
    private String rateClass;
    private String description;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private boolean valid;
    private List tdspChargeRates = null;
    private List tdspRateCodesVo = null;
    private List powerFactorRatchet = null;
    public RateCodesVO()
    {
    }
    public List getPowerFactorRatchet()
    {
        return powerFactorRatchet;
    }
    public void setPowerFactorRatchet(List powerFactorRatchet)
    {
        this.powerFactorRatchet = powerFactorRatchet;
    }
    public List getTdspRateCodesVo()
    {
        return tdspRateCodesVo;
    }
    public void setTdspRateCodesVo(List tdspRateCodesVo)
    {
        this.tdspRateCodesVo = tdspRateCodesVo;
    }
    public int getRateCodeIdentifier()
    {
        return rateCodeIdentifier;
    }
    public void setRateCodeIdentifier(int rateCodeIdentifier)
    {
        this.rateCodeIdentifier = rateCodeIdentifier;
    }
    public TDSPVO getTdsp()
    {
        return tdsp;
    }
    public void setTdsp(TDSPVO tdsp)
    {
        this.tdsp = tdsp;
    }
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
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
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
    public String getRateClass()
    {
        return rateClass;
    }
    public void setRateClass(String rateClass)
    {
        this.rateClass = rateClass;
    }
    public String getRateCode()
    {
        return rateCode;
    }
    public void setRateCode(String rateCode)
    {
        this.rateCode = rateCode;
    }
    public boolean isValid()
    {
        return valid;
    }
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
    public List getTdspChargeRates()
    {
        return tdspChargeRates;
    }
    public void setTdspChargeRates(List tdspChargeRates)
    {
        this.tdspChargeRates = tdspChargeRates;
    }
}

/*
*$Log: RateCodesVO.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:45  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.8  2007/04/19 11:14:08  kduraisamy
*Set is changed to List.
*
*Revision 1.7  2007/03/13 08:31:47  kduraisamy
*Ratchet and demand power directly taken from PICVO.
*
*Revision 1.6  2007/03/09 08:52:46  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.5  2007/03/08 16:34:26  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.4  2007/02/13 14:05:29  kduraisamy
*BillingDemand related mapping completed.
*
*Revision 1.3  2007/02/12 06:04:04  kduraisamy
*unwanted set Removed.
*
*Revision 1.2  2007/02/02 15:15:27  kduraisamy
*initial commit.
*
*Revision 1.1  2007/02/02 12:20:13  kduraisamy
*initial commit.
*
*
*/