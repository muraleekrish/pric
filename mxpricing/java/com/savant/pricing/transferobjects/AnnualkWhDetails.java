/*
 * Created on Apr 20, 2007
 *
 * ClassName	:  	AnnualkWhDetails.java
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
public class AnnualkWhDetails implements Serializable
{

    public AnnualkWhDetails()
    {
    }
    private String esiId;
    private float kWh;
    private float kWhPercentage;
    
    
    public String getEsiId()
    {
        return esiId;
    }
    public void setEsiId(String esiId)
    {
        this.esiId = esiId;
    }
    public float getKWh()
    {
        return kWh;
    }
    public void setKWh(float wh)
    {
        kWh = wh;
    }
    public float getKWhPercentage()
    {
        return kWhPercentage;
    }
    public void setKWhPercentage(float whPercentage)
    {
        kWhPercentage = whPercentage;
    }
}

/*
*$Log: AnnualkWhDetails.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/20 06:53:27  kduraisamy
*getTotkWh() added.
*
*
*/