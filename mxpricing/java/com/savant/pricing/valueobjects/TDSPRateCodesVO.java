/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	TDSPRateCodesVO.java
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

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TDSPRateCodesVO implements Serializable
{
    private int rateCodeIdentifier;
    private boolean scud;
    private float scudPercentage;
    private MeterCategoryVO meterCategory;
    private ServiceVoltageVO serviceVoltage;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private boolean valid;
    private RateCodesVO rateCode;
    
    public TDSPRateCodesVO()
    {
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
    public MeterCategoryVO getMeterCategory()
    {
        return meterCategory;
    }
    public void setMeterCategory(MeterCategoryVO meterCategory)
    {
        this.meterCategory = meterCategory;
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
    public RateCodesVO getRateCode()
    {
        return rateCode;
    }
    public void setRateCode(RateCodesVO rateCode)
    {
        this.rateCode = rateCode;
    }
    public boolean isScud()
    {
        return scud;
    }
    public void setScud(boolean scud)
    {
        this.scud = scud;
    }
    public float getScudPercentage()
    {
        return scudPercentage;
    }
    public void setScudPercentage(float scudPercentage)
    {
        this.scudPercentage = scudPercentage;
    }
    public ServiceVoltageVO getServiceVoltage()
    {
        return serviceVoltage;
    }
    public void setServiceVoltage(ServiceVoltageVO serviceVoltage)
    {
        this.serviceVoltage = serviceVoltage;
    }
    
    public int getRateCodeIdentifier()
    {
        return rateCodeIdentifier;
    }
    public void setRateCodeIdentifier(int rateCodeIdentifier)
    {
        this.rateCodeIdentifier = rateCodeIdentifier;
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
*$Log: TDSPRateCodesVO.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:45  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/03/08 16:34:26  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.2  2007/02/13 14:05:29  kduraisamy
*BillingDemand related mapping completed.
*
*Revision 1.1  2007/02/02 15:15:27  kduraisamy
*initial commit.
*
*
*/