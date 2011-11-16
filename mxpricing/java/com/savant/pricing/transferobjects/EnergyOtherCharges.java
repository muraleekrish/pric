/*
 * Created on Feb 8, 2007
 *
 * ClassName	:  	EnergyOtherCharges.java
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

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EnergyOtherCharges implements Serializable
{
    private Date termMonth;
    private float asCharges;
    private float congestionCharges;
    private float grossReceiptsAssesmentFee;
    private float ufe;
    private float ercotAdminFee;
    private float recCharge;
    
    public EnergyOtherCharges()
    {
    }
    

    public float getAsCharges()
    {
        return asCharges;
    }
    public void setAsCharges(float asCharges)
    {
        this.asCharges = asCharges;
    }
    public float getCongestionCharges()
    {
        return congestionCharges;
    }
    public void setCongestionCharges(float congestionCharges)
    {
        this.congestionCharges = congestionCharges;
    }
    public float getErcotAdminFee()
    {
        return ercotAdminFee;
    }
    public void setErcotAdminFee(float ercotAdminFee)
    {
        this.ercotAdminFee = ercotAdminFee;
    }
    public float getGrossReceiptsAssesmentFee()
    {
        return grossReceiptsAssesmentFee;
    }
    public void setGrossReceiptsAssesmentFee(float grossReceiptsAssesmentFee)
    {
        this.grossReceiptsAssesmentFee = grossReceiptsAssesmentFee;
    }
    public Date getTermMonth()
    {
        return termMonth;
    }
    public void setTermMonth(Date termMonth)
    {
        this.termMonth = termMonth;
    }
    public float getUfe()
    {
        return ufe;
    }
    public void setUfe(float ufe)
    {
        this.ufe = ufe;
    }
    
    public float getRecCharge()
    {
        return recCharge;
    }
    public void setRecCharge(float recCharge)
    {
        this.recCharge = recCharge;
    }
}

/*
*$Log: EnergyOtherCharges.java,v $
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