/*
 * Created on Apr 8, 2007
 *
 * ClassName	:  	HolidayVO.java
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

import com.savant.pricing.valueobjects.DayTypesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HolidayVO implements Serializable
{
    private Date date;
    private String reason;
    private DayTypesVO dayType;
    
    public HolidayVO()
    {
    }

    /**
     * @return Returns the dayType.
     */
    public DayTypesVO getDayType()
    {
        return dayType;
    }
    /**
     * @param dayType The dayType to set.
     */
    public void setDayType(DayTypesVO dayType)
    {
        this.dayType = dayType;
    }
    public Date getDate()
    {
        return date;
    }
    public void setDate(Date date)
    {
        this.date = date;
    }
    public String getReason()
    {
        return reason;
    }
    public void setReason(String reason)
    {
        this.reason = reason;
    }
}

/*
*$Log: HolidayVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/04/19 04:13:18  spandiyarajan
*pccaledndar(holiday) add/modify/delete functionality initially added
*
*Revision 1.1  2007/04/08 14:34:14  kduraisamy
*initial commit.
*
*
*/