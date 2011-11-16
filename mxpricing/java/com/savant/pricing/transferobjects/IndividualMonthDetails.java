/*
 * Created on Feb 5, 2007
 *
 * ClassName	:  	IndividualMonthDetails.java
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
import java.util.Date;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IndividualMonthDetails implements Serializable
{
    private int term;
    private int month;
    private Date startDate;
    private Date endDate;
    private int noOfWD;
    private int noOfWE;
    
    private float totalProfileUsageWD;
    private float totalProfileUsageWE;
    
    
    
    public IndividualMonthDetails()
    {
    }
    

    public int getTerm()
    {
        return term;
    }
    public void setTerm(int term)
    {
        this.term = term;
    }
    public Date getEndDate()
    {
        return endDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }
    public int getMonth()
    {
        return month;
    }
    public void setMonth(int month)
    {
        this.month = month;
    }
    public int getNoOfWD()
    {
        return noOfWD;
    }
    public void setNoOfWD(int noOfWD)
    {
        this.noOfWD = noOfWD;
    }
    public int getNoOfWE()
    {
        return noOfWE;
    }
    public void setNoOfWE(int noOfWE)
    {
        this.noOfWE = noOfWE;
    }
    public Date getStartDate()
    {
        return startDate;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }
    
    
    public float getTotalProfileUsageWD()
    {
        return totalProfileUsageWD;
    }
    public void setTotalProfileUsageWD(float totalProfileUsageWD)
    {
        this.totalProfileUsageWD = totalProfileUsageWD;
    }
    public float getTotalProfileUsageWE()
    {
        return totalProfileUsageWE;
    }
    public void setTotalProfileUsageWE(float totalProfileUsageWE)
    {
        this.totalProfileUsageWE = totalProfileUsageWE;
    }
}

/*
*$Log: IndividualMonthDetails.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/02/09 11:58:04  kduraisamy
*pricing core algorithm almost finished.
*
*
*/