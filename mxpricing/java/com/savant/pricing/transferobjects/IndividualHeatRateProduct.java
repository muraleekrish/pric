/*
 * Created on Feb 26, 2007
 *
 * ClassName	:  	IndividualHeatRateProduct.java
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
public class IndividualHeatRateProduct implements Serializable
{ 
    private int contractMonth;
    private Date termMonth;
    private float indexPrice;
    private float equiWholeSale$PerMWH;
    private float totKwh;
    
    public IndividualHeatRateProduct()
    {
    }
    
    public float getEquiWholeSale$PerMWH()
    {
        return equiWholeSale$PerMWH;
    }
    public void setEquiWholeSale$PerMWH(float equiWholeSale$PerMWH)
    {
        this.equiWholeSale$PerMWH = equiWholeSale$PerMWH;
    }
    public int getContractMonth()
    {
        return contractMonth;
    }
    public void setContractMonth(int contractMonth)
    {
        this.contractMonth = contractMonth;
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
*$Log: IndividualHeatRateProduct.java,v $
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