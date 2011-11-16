/*
 * Created on Feb 7, 2007
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
public class ImportedDataDetails 
{
    private String esiid = "";
    private String flowdate = "";
    private String month = "";    
    private String Interval = "";
    private String kwh = "";
    private String kVARh = "";
    
    
    /**
     * @return Returns the esiid.
     */
    public String getEsiid()
    {
        return esiid;
    }
    /**
     * @param esiid The esiid to set.
     */
    public void setEsiid(String esiid)
    {
        this.esiid = esiid;
    }
    /**
     * @return Returns the flowdate.
     */
    public String getFlowdate()
    {
        return flowdate;
    }
    /**
     * @param flowdate The flowdate to set.
     */
    public void setFlowdate(String flowdate)
    {
        this.flowdate = flowdate;
    }
    /**
     * @return Returns the interval.
     */
    public String getInterval()
    {
        return Interval;
    }
    /**
     * @param interval The interval to set.
     */
    public void setInterval(String interval)
    {
        Interval = interval;
    }
    /**
     * @return Returns the kVARh.
     */
    public String getKVARh()
    {
        return kVARh;
    }
    /**
     * @param rh The kVARh to set.
     */
    public void setKVARh(String rh)
    {
        kVARh = rh;
    }
    /**
     * @return Returns the kwh.
     */
    public String getKwh()
    {
        return kwh;
    }
    /**
     * @param kwh The kwh to set.
     */
    public void setKwh(String kwh)
    {
        this.kwh = kwh;
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
}
