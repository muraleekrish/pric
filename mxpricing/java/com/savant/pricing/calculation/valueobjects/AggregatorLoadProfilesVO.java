/*
 * Created on Feb 5, 2007
 *
 * ClassName	:  	UsageVO.java
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

import com.savant.pricing.valueobjects.DayTypesVO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AggregatorLoadProfilesVO implements Serializable
{
    private ProspectiveCustomerVO prospectiveCustomer;
    private String esiId;
    private int month;
    private int hour;
    private DayTypesVO dayType;
    private float value;

    public AggregatorLoadProfilesVO()
    {
    }
    
    public ProspectiveCustomerVO getProspectiveCustomer()
    {
        return prospectiveCustomer;
    }
    public void setProspectiveCustomer(ProspectiveCustomerVO prospectiveCustomer)
    {
        this.prospectiveCustomer = prospectiveCustomer;
    }
    
    public String getEsiId()
    {
        return esiId;
    }
    public void setEsiId(String esiId)
    {
        this.esiId = esiId;
    }
    public DayTypesVO getDayType()
    {
        return dayType;
    }
    public void setDayType(DayTypesVO dayType)
    {
        this.dayType = dayType;
    }
    public int getHour()
    {
        return hour;
    }
    public void setHour(int hour)
    {
        this.hour = hour;
    }
    public int getMonth()
    {
        return month;
    }
    public void setMonth(int month)
    {
        this.month = month;
    }
    public float getValue()
    {
        return value;
    }
    public void setValue(float value)
    {
        this.value = value;
    }
}

/*
*$Log: AggregatorLoadProfilesVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/05/08 08:51:54  kduraisamy
*getAggregatedProfileDetails() added.
*
*Revision 1.1  2007/05/07 12:14:34  kduraisamy
*IDR Profile calculation added.
*
*Revision 1.1  2007/02/09 11:56:55  kduraisamy
*pricing core algorithm almost finished.
*
*
*/