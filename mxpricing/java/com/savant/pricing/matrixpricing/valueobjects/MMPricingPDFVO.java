/*
 * Created on Aug 28, 2007
 *
 * ClassName	:  	MMPricingPDFVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.matrixpricing.valueobjects;

import java.io.Serializable;
import java.util.Date;

import com.savant.pricing.securityadmin.valueobject.UsersVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MMPricingPDFVO implements Serializable
{

    private String refNo;
    private MMPriceRunHeaderVO priceRunRefNo;
    private UsersVO salesRep;
    private UsersVO salesManager;
    private boolean valid;
    private Date createdDate;
    private String custName;
    private String createdBy;
    
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
    public String getCustName()
    {
        return custName;
    }
    public void setCustName(String custName)
    {
        this.custName = custName;
    }
    public MMPriceRunHeaderVO getPriceRunRefNo()
    {
        return priceRunRefNo;
    }
    public void setPriceRunRefNo(MMPriceRunHeaderVO priceRunRefNo)
    {
        this.priceRunRefNo = priceRunRefNo;
    }
    public String getRefNo()
    {
        return refNo;
    }
    public void setRefNo(String refNo)
    {
        this.refNo = refNo;
    }
    public UsersVO getSalesManager()
    {
        return salesManager;
    }
    public void setSalesManager(UsersVO salesManager)
    {
        this.salesManager = salesManager;
    }
    public UsersVO getSalesRep()
    {
        return salesRep;
    }
    public void setSalesRep(UsersVO salesRep)
    {
        this.salesRep = salesRep;
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
*$Log: MMPricingPDFVO.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:24  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/08/28 11:18:07  jnadesan
*file property removed
*
*Revision 1.1  2007/08/28 05:05:23  kduraisamy
*initial commit
*
*
*/