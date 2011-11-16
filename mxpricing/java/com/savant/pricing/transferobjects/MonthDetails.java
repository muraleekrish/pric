/*
 * Created on Feb 5, 2007
 *
 * ClassName	:  	Term.java
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
import java.util.Vector;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MonthDetails implements Serializable
{
    
    private int month;
    private Date meterReadfromDate;
    private Date meterReadEndDate;
    private Vector individualMonthDetails;
    private float totalProfileUsageWD;
    private float totalProfileUsageWE;
    private float picUsage;
    private float usageRatio;
    private int noOfDays;
    private float historicalApparentPower;
    private float historicalDemand;
    private String meterReadDate;
    public MonthDetails()
    {
    }
    
    public float getHistoricalDemand() {
        return historicalDemand;
    }
   
    public String getMeterReadDate() {
        return meterReadDate;
    }
    public int getNoOfDays() {
        return noOfDays;
    }
    public void setHistoricalDemand(float historicalDemand) {
        this.historicalDemand = historicalDemand;
    }
   
    public float getHistoricalApparentPower() {
        return historicalApparentPower;
    }
    public void setHistoricalApparentPower(float historicalApparentPower) {
        this.historicalApparentPower = historicalApparentPower;
    }
    public void setMeterReadDate(String meterReadDate) {
        this.meterReadDate = meterReadDate;
    }
    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }
   
    
    public Date getMeterReadEndDate()
    {
        return meterReadEndDate;
    }
    public void setMeterReadEndDate(Date meterReadEndDate)
    {
        this.meterReadEndDate = meterReadEndDate;
    }
    public Date getMeterReadfromDate()
    {
        return meterReadfromDate;
    }
    public void setMeterReadfromDate(Date meterReadfromDate)
    {
        this.meterReadfromDate = meterReadfromDate;
    }
   
    public int getMonth()
    {
        return month;
    }
    public void setMonth(int month)
    {
        this.month = month;
    }
   
    public Vector getIndividualMonthDetails()
    {
        return individualMonthDetails;
    }
    public void setIndividualMonthDetails(Vector individualMonthDetails)
    {
        this.individualMonthDetails = individualMonthDetails;
    }
    
    public float getTotalProfileUsageWD()
    {
        return totalProfileUsageWD;
    }
    public void setTotalProfileUsageWD(float totalProfileUsageWD)
    {
        this.totalProfileUsageWD = totalProfileUsageWD;
    }
    public float getTotalProfileUsageWE()
    {
        return totalProfileUsageWE;
    }
    public void setTotalProfileUsageWE(float totalProfileUsageWE)
    {
        this.totalProfileUsageWE = totalProfileUsageWE;
    }
    
    public float getPicUsage()
    {
        return picUsage;
    }
    public void setPicUsage(float picUsage)
    {
        this.picUsage = picUsage;
    }
    
    public float getUsageRatio()
    {
        return usageRatio;
    }
    public void setUsageRatio(float usageRatio)
    {
        this.usageRatio = usageRatio;
    }
}

/*
*$Log: MonthDetails.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/03/24 06:28:13  kduraisamy
*load factor taken removed.
*
*Revision 1.3  2007/03/23 09:24:49  kduraisamy
*loadFactor and usage for all the EsiId methods added.
*
*Revision 1.2  2007/03/06 03:35:04  jnadesan
*form fields added for import view
*
*Revision 1.1  2007/02/09 11:58:04  kduraisamy
*pricing core algorithm almost finished.
*
*
*/