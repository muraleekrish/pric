/*
 * Created on Feb 3, 2007
 *
 * ClassName	:  	TaxTypesVO.java
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
public class TaxRatesVO implements Serializable
{
    private int taxRateIdentifier;
    private TaxTypesVO taxType;
    private float taxRate;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private boolean valid;
    
    public TaxRatesVO()
    {
    }
    
    public float getTaxRate()
    {
        return taxRate;
    }
    public void setTaxRate(float taxRate)
    {
        this.taxRate = taxRate;
    }
    public int getTaxRateIdentifier()
    {
        return taxRateIdentifier;
    }
    public void setTaxRateIdentifier(int taxRateIdentifier)
    {
        this.taxRateIdentifier = taxRateIdentifier;
    }
    public TaxTypesVO getTaxType()
    {
        return taxType;
    }
    public void setTaxType(TaxTypesVO taxType)
    {
        this.taxType = taxType;
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
*$Log: TaxRatesVO.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:45  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/03/14 10:07:29  kduraisamy
*initial commit.
*
*Revision 1.1  2007/02/03 06:10:40  kduraisamy
*Tax Types mapping included.
*
*
*/