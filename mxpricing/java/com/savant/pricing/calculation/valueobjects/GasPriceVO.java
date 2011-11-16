/*
 * Created on Feb 19, 2007
 *
 * ClassName	:  	GasPriceVO.java
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

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GasPriceVO implements Serializable
{
    private Date marketDate;
    private Date monthYear;
    private ForwardCurvesSourceVO dataSource;
    private float price;
    

    public GasPriceVO()
    {
    }
    

    public ForwardCurvesSourceVO getDataSource()
    {
        return dataSource;
    }
    public void setDataSource(ForwardCurvesSourceVO dataSource)
    {
        this.dataSource = dataSource;
    }
    public Date getMarketDate()
    {
        return marketDate;
    }
    public void setMarketDate(Date marketDate)
    {
        this.marketDate = marketDate;
    }
    public Date getMonthYear()
    {
        return monthYear;
    }
    public void setMonthYear(Date monthYear)
    {
        this.monthYear = monthYear;
    }
    public float getPrice()
    {
        return price;
    }
    public void setPrice(float price)
    {
        this.price = price;
    }
}

/*
*$Log: GasPriceVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/02/26 13:26:51  kduraisamy
*HeatRate and energy Partner Plan added.
*
*
*/