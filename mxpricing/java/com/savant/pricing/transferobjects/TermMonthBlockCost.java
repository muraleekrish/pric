/*
 * Created on Feb 9, 2007
 *
 * ClassName	:  	TermMonthBlockUsage.java
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
public class TermMonthBlockCost implements Serializable
{
    private Date termMonth;
    private float cost5x16;
    private float cost2x16;
    private float cost7x8;
    
    public TermMonthBlockCost()
    {

    }
    public Date getTermMonth()
    {
        return termMonth;
    }
    public void setTermMonth(Date termMonth)
    {
        this.termMonth = termMonth;
    }
    
    public float getCost2x16()
    {
        return cost2x16;
    }
    public void setCost2x16(float cost2x16)
    {
        this.cost2x16 = cost2x16;
    }
    public float getCost5x16()
    {
        return cost5x16;
    }
    public void setCost5x16(float cost5x16)
    {
        this.cost5x16 = cost5x16;
    }
    public float getCost7x8()
    {
        return cost7x8;
    }
    public void setCost7x8(float cost7x8)
    {
        this.cost7x8 = cost7x8;
    }
}

/*
*$Log: TermMonthBlockCost.java,v $
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