/*
 * Created on Apr 10, 2007
 *
 * ClassName	:  	ESIIDDetails.java
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
public class ESIIDDetails implements Serializable
{
    private String esiId;
    private String tdspName;
    private String serviceAddress;
    private int zipCode;
    public ESIIDDetails()
    {
    }
    

    public String getEsiId()
    {
        return esiId;
    }
    public void setEsiId(String esiId)
    {
        this.esiId = esiId;
    }
    public String getServiceAddress()
    {
        return serviceAddress;
    }
    public void setServiceAddress(String serviceAddress)
    {
        this.serviceAddress = serviceAddress;
    }
    public String getTdspName()
    {
        return tdspName;
    }
    public void setTdspName(String tdspName)
    {
        this.tdspName = tdspName;
    }
    public int getZipCode()
    {
        return zipCode;
    }
    public void setZipCode(int zipCode)
    {
        this.zipCode = zipCode;
    }
}

/*
*$Log: ESIIDDetails.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/10 06:34:51  kduraisamy
*iniitial commit.
*
*
*/