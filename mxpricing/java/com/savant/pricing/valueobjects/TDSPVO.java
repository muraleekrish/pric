/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	TDSPVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.valueobjects;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TDSPVO implements Serializable
{
    private int tdspIdentifier;
    private String tdspName;
    private String esiIdPrefix;
    private String description;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private boolean valid;
    private WeatherZonesVO weatherZone;
    private CongestionZonesVO congestionZone;
    private List meterReadCycles;
    public TDSPVO()
    {
    }
    public CongestionZonesVO getCongestionZone()
    {
        return congestionZone;
    }
    public void setCongestionZone(CongestionZonesVO congestionZone)
    {
        this.congestionZone = congestionZone;
    }
    public WeatherZonesVO getWeatherZone()
    {
        return weatherZone;
    }
    public void setWeatherZone(WeatherZonesVO weatherZone)
    {
        this.weatherZone = weatherZone;
    }
    public List getMeterReadCycles()
    {
        return meterReadCycles;
    }
    public void setMeterReadCycles(List meterReadCycles)
    {
        this.meterReadCycles = meterReadCycles;
    }
    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    public Date getCreatedDate()
    {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getEsiIdPrefix()
    {
        return esiIdPrefix;
    }
    public void setEsiIdPrefix(String esiIdPrefix)
    {
        this.esiIdPrefix = esiIdPrefix;
    }
    public String getModifiedBy()
    {
        return modifiedBy;
    }
    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }
    public Date getModifiedDate()
    {
        return modifiedDate;
    }
    public void setModifiedDate(Date modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }
    public int getTdspIdentifier()
    {
        return tdspIdentifier;
    }
    public void setTdspIdentifier(int tdspIdentifier)
    {
        this.tdspIdentifier = tdspIdentifier;
    }
    public String getTdspName()
    {
        return tdspName;
    }
    public void setTdspName(String tdspName)
    {
        this.tdspName = tdspName;
    }
    public boolean isValid()
    {
        return valid;
    }
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
    
    public Date getReadDate(String cycle, Date monthYear)
    {
        Iterator itr = this.meterReadCycles.iterator();
        Date readDate = null;
        while(itr.hasNext())
        {
            MeterReadCyclesVO objMeterReadCyclesVO = (MeterReadCyclesVO)itr.next();
            if(objMeterReadCyclesVO.getCycle().equalsIgnoreCase(cycle) && objMeterReadCyclesVO.getMonthYear().equals(monthYear))
            {
                readDate = objMeterReadCyclesVO.getReadDate();
                break;
            }
        }
        return readDate;
    }
}

/*
*$Log: TDSPVO.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:45  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.16  2007/08/27 15:14:43  kduraisamy
*weatherZone also included
*
*Revision 1.15  2007/07/30 04:43:56  jnadesan
*cycle checked against ingnore case
*
*Revision 1.14  2007/04/18 09:35:00  kduraisamy
*set removed.
*
*Revision 1.13  2007/04/17 06:25:22  kduraisamy
*dlfcode set removed from TDSP.
*
*Revision 1.12  2007/04/17 04:49:39  kduraisamy
*rateCodes set removed from TDSP.
*
*Revision 1.11  2007/04/12 06:48:50  kduraisamy
*rateCodes set added.
*
*Revision 1.10  2007/04/09 10:19:12  kduraisamy
*Tdsp wise dlf and values added.
*
*Revision 1.9  2007/03/26 11:19:55  kduraisamy
*esiIdPrefix data type error resolved.
*
*Revision 1.8  2007/03/15 12:47:52  kduraisamy
*necessary break added inside if.
*
*Revision 1.7  2007/03/09 04:15:44  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.6  2007/03/08 16:34:26  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.5  2007/02/12 06:04:04  kduraisamy
*unwanted set Removed.
*
*Revision 1.4  2007/02/09 11:58:18  kduraisamy
*pricing core algorithm almost finished.
*
*Revision 1.3  2007/02/02 15:15:27  kduraisamy
*initial commit.
*
*Revision 1.2  2007/02/02 10:59:48  kduraisamy
*initial commit.
*
*Revision 1.1  2007/02/02 06:55:39  kduraisamy
*initial commit.
*
*
*/