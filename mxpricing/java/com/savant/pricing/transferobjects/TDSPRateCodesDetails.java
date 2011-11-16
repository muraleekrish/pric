/*
 * Created on Apr 9, 2007
 *
 * ClassName	:  	TDSPRateCodesDetails.java
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

import com.savant.pricing.valueobjects.MeterCategoryVO;
import com.savant.pricing.valueobjects.ServiceVoltageVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TDSPRateCodesDetails implements Serializable
{

    public TDSPRateCodesDetails()
    {
    }
    private String rateCode;
    private String rateClass;
    private String description;
    private float scudPercentage;
    private MeterCategoryVO meterCategory;
    private ServiceVoltageVO serviceVoltage;
    

    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public MeterCategoryVO getMeterCategory()
    {
        return meterCategory;
    }
    public void setMeterCategory(MeterCategoryVO meterCategory)
    {
        this.meterCategory = meterCategory;
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
}

/*
*$Log: TDSPRateCodesDetails.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/09 14:14:29  kduraisamy
*initial commit.
*
*
*/