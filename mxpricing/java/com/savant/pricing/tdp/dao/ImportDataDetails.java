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
public class ImportDataDetails 
{
    private String mrktDate = "";
    private String hour = "";
    private String kwh = "";
    
    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
    public String getKwh() {
        return kwh;
    }
    public void setKwh(String kwh) {
        this.kwh = kwh;
    }
    public String getMrktDate() {
        return mrktDate;
    }
    public void setMrktDate(String mrktDate) {
        this.mrktDate = mrktDate;
    }
}
