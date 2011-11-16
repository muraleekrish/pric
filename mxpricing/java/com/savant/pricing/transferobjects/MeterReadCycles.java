/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	MeterReadCycles.java
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

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MeterReadCycles implements Serializable
{
    private int tdspIdentifier;
    private String tdspName;
    private String meterReadCycles;
    
    
    public String getMeterReadCycles()
    {
        return meterReadCycles;
    }
    public void setMeterReadCycles(String meterReadCycles)
    {
        this.meterReadCycles = meterReadCycles;
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
}

/*
*$Log: MeterReadCycles.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/02/09 11:58:04  kduraisamy
*pricing core algorithm almost finished.
*
*
*/