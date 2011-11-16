/*
 * Created on Feb 22, 2007
 *  
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.tdp.dao;

/**
 * @author Karthikeyan Chellamuthu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


public class TypicalDayProfileWeeklyDetails
{
    private String month = "";
    private String interval = "";
    private String weekDaykWh = "";
    private String weekEndkWh = "";
    
    
    /**
     * @return Returns the interval.
     */
    public String getInterval()
    {
        return interval;
    }
    /**
     * @param interval The interval to set.
     */
    public void setInterval(String interval)
    {
        this.interval = interval;
    }
    /**
     * @return Returns the month.
     */
    public String getMonth()
    {
        return month;
    }
    /**
     * @param month The month to set.
     */
    public void setMonth(String month)
    {
        this.month = month;
    }
    /**
     * @return Returns the weekDaykWh.
     */
    public String getWeekDaykWh()
    {
        return weekDaykWh;
    }
    /**
     * @param weekDaykWh The weekDaykWh to set.
     */
    public void setWeekDaykWh(String weekDaykWh)
    {
        this.weekDaykWh = weekDaykWh;
    }
    /**
     * @return Returns the weekEndkWh.
     */
    public String getWeekEndkWh()
    {
        return weekEndkWh;
    }
    /**
     * @param weekEndkWh The weekEndkWh to set.
     */
    public void setWeekEndkWh(String weekEndkWh)
    {
        this.weekEndkWh = weekEndkWh;
    }
}

/***
$Log: TypicalDayProfileWeeklyDetails.java,v $
Revision 1.1  2007/12/07 06:18:35  jvediyappan
initial commit.

Revision 1.1  2007/11/26 05:33:55  tannamalai
latest changes for tdp

Revision 1.2  2007/05/25 09:11:16  amahesh
Changes Double datatype to String datatype

Revision 1.1  2007/05/25 15:17:39  Aswini Mahesh
Changed Double to String


***/