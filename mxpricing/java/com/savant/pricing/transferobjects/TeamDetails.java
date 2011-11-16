/*
 * Created on Jun 6, 2007
 *
 * ClassName	:  	TeamDetails.java
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

import com.savant.pricing.securityadmin.valueobject.UsersVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TeamDetails implements Serializable
{
    private UsersVO salesRep;
    private UsersVO salesManager;
    private UsersVO pricingAnalyst;
    public TeamDetails()
    {
    }
    public UsersVO getPricingAnalyst()
    {
        return pricingAnalyst;
    }
    public void setPricingAnalyst(UsersVO pricingAnalyst)
    {
        this.pricingAnalyst = pricingAnalyst;
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
}

/*
*$Log: TeamDetails.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/06/07 09:33:04  kduraisamy
*multiple mail for a run added.
*
*
*/