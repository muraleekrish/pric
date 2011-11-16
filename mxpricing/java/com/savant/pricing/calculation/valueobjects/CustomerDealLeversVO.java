/*
 * Created on Feb 28, 2007
 *
 * ClassName	:  	DealLeversVO.java
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

import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CustomerDealLeversVO implements Serializable
{
    private ProspectiveCustomerVO prospectiveCustomer;
    private DealLeversVO dealLever;
    private int term;
    private float value;
    private Date modifiedDate = new Date();
    public CustomerDealLeversVO()
    {
    }
    
    public Date getModifiedDate()
    {
        return modifiedDate;
    }
    public void setModifiedDate(Date modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }
    public ProspectiveCustomerVO getProspectiveCustomer()
    {
        return prospectiveCustomer;
    }
    public void setProspectiveCustomer(ProspectiveCustomerVO prospectiveCustomer)
    {
        this.prospectiveCustomer = prospectiveCustomer;
    }
    public DealLeversVO getDealLever()
    {
        return dealLever;
    }
    public void setDealLever(DealLeversVO dealLever)
    {
        this.dealLever = dealLever;
    }
    public int getTerm()
    {
        return term;
    }
    public void setTerm(int term)
    {
        this.term = term;
    }
    public float getValue()
    {
        return value;
    }
    public void setValue(float value)
    {
        this.value = value;
    }
}

/*
*$Log: CustomerDealLeversVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/03/23 07:14:40  kduraisamy
*deal Levers stored by customer and term wise.
*
*Revision 1.1  2007/02/28 09:15:29  kduraisamy
*system dealLevers and customerwise dealLevers mapping completed.
*
*
*/