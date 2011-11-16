/*
 * Created on Feb 26, 2007
 *
 * ClassName	:  	IndividualEPP.java
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
public class IndividualEPP implements Serializable
{
    private int contractMonth;
    private Date termMonth;
    private float indexPrice;
    private float equiWholeSale$perMWH;
    private float totKwh;
    public IndividualEPP()
    {
    }
    
    public int getContractMonth()
    {
        return contractMonth;
    }
    public void setContractMonth(int contractMonth)
    {
        this.contractMonth = contractMonth;
    }
    public float getEquiWholeSale$perMWH()
    {
        return equiWholeSale$perMWH;
    }
    public void setEquiWholeSale$perMWH(float equiWholeSale$perMWH)
    {
        this.equiWholeSale$perMWH = equiWholeSale$perMWH;
    }
    public float getIndexPrice()
    {
        return indexPrice;
    }
    public void setIndexPrice(float indexPrice)
    {
        this.indexPrice = indexPrice;
    }
    public Date getTermMonth()
    {
        return termMonth;
    }
    public void setTermMonth(Date termMonth)
    {
        this.termMonth = termMonth;
    }
    public float getTotKwh()
    {
        return totKwh;
    }
    public void setTotKwh(float totKwh)
    {
        this.totKwh = totKwh;
    }
}

/*
*$Log: IndividualEPP.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/02/26 13:27:02  kduraisamy
*HeatRate and energy Partner Plan added.
*
*
*/