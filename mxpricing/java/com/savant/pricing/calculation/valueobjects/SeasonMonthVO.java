/*
 * Created on Mar 30, 2007
 *
 * ClassName	:  	SeasonMonthVO.java
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

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SeasonMonthVO implements Serializable
{
    private int seasonMonthId;
    private int month;
    private SeasonsVO season;
    public SeasonMonthVO()
    {
    }
    
    public int getMonth()
    {
        return month;
    }
    public void setMonth(int month)
    {
        this.month = month;
    }
    public SeasonsVO getSeason()
    {
        return season;
    }
    public void setSeason(SeasonsVO season)
    {
        this.season = season;
    }
    public int getSeasonMonthId()
    {
        return seasonMonthId;
    }
    public void setSeasonMonthId(int seasonMonthId)
    {
        this.seasonMonthId = seasonMonthId;
    }
}

/*
*$Log: SeasonMonthVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/03/31 06:22:23  kduraisamy
*initial commit.
*
*
*/