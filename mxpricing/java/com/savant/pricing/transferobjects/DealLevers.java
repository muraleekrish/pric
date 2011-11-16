/*
 * Created on Feb 28, 2007
 *
 * ClassName	:  	DealLevers.java
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
public class DealLevers implements Serializable
{
    private int dealLeverIdentifier;
    private float value;
    private String dealLever;
    
    public DealLevers()
    {
    }
    

    public int getDealLeverIdentifier()
    {
        return dealLeverIdentifier;
    }
    public void setDealLeverIdentifier(int dealLeverIdentifier)
    {
        this.dealLeverIdentifier = dealLeverIdentifier;
    }
    public float getValue()
    {
        return value;
    }
    public void setValue(float value)
    {
        this.value = value;
    }
    public String getDealLever()
    {
        return dealLever;
    }
    public void setDealLever(String dealLever)
    {
        this.dealLever = dealLever;
    }
}

/*
*$Log: DealLevers.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/08/09 07:45:38  spandiyarajan
*Initial commit for schedule page
*
*Revision 1.1  2007/02/28 11:47:27  kduraisamy
*initial commit.
*
*
*/