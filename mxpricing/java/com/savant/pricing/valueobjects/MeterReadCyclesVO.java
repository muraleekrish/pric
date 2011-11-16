/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	MeterReadCyclesVO.java
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

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MeterReadCyclesVO implements Serializable
{
    private TDSPVO tdsp;
    private String cycle;
    private Date monthYear;
    private Date readDate;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private boolean valid;

    public MeterReadCyclesVO()
    {
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
    public String getCycle()
    {
        return cycle;
    }
    public void setCycle(String cycle)
    {
        this.cycle = cycle;
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
    public Date getMonthYear()
    {
        return monthYear;
    }
    public void setMonthYear(Date monthYear)
    {
        this.monthYear = monthYear;
    }
    public Date getReadDate()
    {
        return readDate;
    }
    public void setReadDate(Date readDate)
    {
        this.readDate = readDate;
    }
    public TDSPVO getTdsp()
    {
        return tdsp;
    }
    public void setTdsp(TDSPVO tdsp)
    {
        this.tdsp = tdsp;
    }
    public boolean isValid()
    {
        return valid;
    }
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
}

/*
*$Log: MeterReadCyclesVO.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:45  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/02/09 11:58:18  kduraisamy
*pricing core algorithm almost finished.
*
*
*/