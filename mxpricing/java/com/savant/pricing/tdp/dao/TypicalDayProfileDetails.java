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
public class TypicalDayProfileDetails
{
    private String month = "";
    private String totalOnPeakkWh = "";
    private String totalOffPeakkWh = "";
    private String totalkWh = "";
    private String minHourlykWh = "";
    private String maxHourlykWh = "";
    private String min15MinIntkWh = "";
    private String max15MinIntkWh = "";
    private String noOfWeekDay = "";
    private String noOfWeekEnd = "";
    private String noOfDays = "";
    private String loadFactor = "";
    private String year = "";
    
    

    
    public String getYear()
    {
        return year;
    }
    public void setYear(String year)
    {
        this.year = year;
    }
    /**
     * @return Returns the loadFactor.
     */
    public String getLoadFactor()
    {
        return loadFactor;
    }
    /**
     * @param loadFactor The loadFactor to set.
     */
    public void setLoadFactor(String loadFactor)
    {
        this.loadFactor = loadFactor;
    }
    /**
     * @return Returns the max15MinIntkWh.
     */
    public String getMax15MinIntkWh()
    {
        return max15MinIntkWh;
    }
    /**
     * @param max15MinIntkWh The max15MinIntkWh to set.
     */
    public void setMax15MinIntkWh(String max15MinIntkWh)
    {
        this.max15MinIntkWh = max15MinIntkWh;
    }
    /**
     * @return Returns the maxHourlykWh.
     */
    public String getMaxHourlykWh()
    {
        return maxHourlykWh;
    }
    /**
     * @param maxHourlykWh The maxHourlykWh to set.
     */
    public void setMaxHourlykWh(String maxHourlykWh)
    {
        this.maxHourlykWh = maxHourlykWh;
    }
    /**
     * @return Returns the min15MinIntkWh.
     */
    public String getMin15MinIntkWh()
    {
        return min15MinIntkWh;
    }
    /**
     * @param min15MinIntkWh The min15MinIntkWh to set.
     */
    public void setMin15MinIntkWh(String min15MinIntkWh)
    {
        this.min15MinIntkWh = min15MinIntkWh;
    }
    /**
     * @return Returns the minHourlykWh.
     */
    public String getMinHourlykWh()
    {
        return minHourlykWh;
    }
    /**
     * @param minHourlykWh The minHourlykWh to set.
     */
    public void setMinHourlykWh(String minHourlykWh)
    {
        this.minHourlykWh = minHourlykWh;
    }
    /**
     * @return Returns the monthAndYear.
     */
    public String getMonth()
    {
        return month;
    }
    /**
     * @param monthAndYear The monthAndYear to set.
     */
    public void setMonth(String month)
    {
        this.month = month;
    }
    /**
     * @return Returns the noOfDays.
     */
    public String getNoOfDays()
    {
        return noOfDays;
    }
    /**
     * @param noOfDays The noOfDays to set.
     */
    public void setNoOfDays(String noOfDays)
    {
        this.noOfDays = noOfDays;
    }
    /**
     * @return Returns the noOfWeekDay.
     */
    public String getNoOfWeekDay()
    {
        return noOfWeekDay;
    }
    /**
     * @param noOfWeekDay The noOfWeekDay to set.
     */
    public void setNoOfWeekDay(String noOfWeekDay)
    {
        this.noOfWeekDay = noOfWeekDay;
    }
    /**
     * @return Returns the noOfWeekEnd.
     */
    public String getNoOfWeekEnd()
    {
        return noOfWeekEnd;
    }
    /**
     * @param noOfWeekEnd The noOfWeekEnd to set.
     */
    public void setNoOfWeekEnd(String noOfWeekEnd)
    {
        this.noOfWeekEnd = noOfWeekEnd;
    }
    /**
     * @return Returns the totalkWh.
     */
    public String getTotalkWh()
    {
        return totalkWh;
    }
    /**
     * @param totalkWh The totalkWh to set.
     */
    public void setTotalkWh(String totalkWh)
    {
        this.totalkWh = totalkWh;
    }
    /**
     * @return Returns the totalOffPeakkWh.
     */
    public String getTotalOffPeakkWh()
    {
        return totalOffPeakkWh;
    }
    /**
     * @param totalOffPeakkWh The totalOffPeakkWh to set.
     */
    public void setTotalOffPeakkWh(String totalOffPeakkWh)
    {
        this.totalOffPeakkWh = totalOffPeakkWh;
    }
    /**
     * @return Returns the totalOnPeakkWh.
     */
    public String getTotalOnPeakkWh()
    {
        return totalOnPeakkWh;
    }
    /**
     * @param totalOnPeakkWh The totalOnPeakkWh to set.
     */
    public void setTotalOnPeakkWh(String totalOnPeakkWh)
    {
        this.totalOnPeakkWh = totalOnPeakkWh;
    }
}
