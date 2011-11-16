/*
 * Created on Feb 7, 2007
 *
 * ClassName	:  	PricingCostVO.java
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
import java.util.Date;

import com.savant.pricing.valueobjects.LoadProfileTypesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PricingCostVO implements Serializable
{

    private int id;
    private PricingUsageHeaderVO usageRef;
    private EnergyChargeNamesVO energyChargeName;
    private PriceBlockHeaderVO priceBlock;
    private TDSPChargeNamesVO tdspChargeName;
    private ChargeTypesVO chargeType;
    private LoadProfileTypesVO profileType;
    private Date termMonth;
    private int term;
    private float charge;
    private float chargeWOL;
    
    public PricingCostVO()
    {
    }
    
    public ChargeTypesVO getChargeType()
    {
        return chargeType;
    }
    
    public void setChargeType(ChargeTypesVO chargeType)
    {
        this.chargeType = chargeType;
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public TDSPChargeNamesVO getTdspChargeName()
    {
        return tdspChargeName;
    }
    public void setTdspChargeName(TDSPChargeNamesVO tdspChargeName)
    {
        this.tdspChargeName = tdspChargeName;
    }
    public LoadProfileTypesVO getProfileType()
    {
        return profileType;
    }
    public void setProfileType(LoadProfileTypesVO profileType)
    {
        this.profileType = profileType;
    }
    public PricingUsageHeaderVO getUsageRef()
    {
        return usageRef;
    }
    public void setUsageRef(PricingUsageHeaderVO usageRef)
    {
        this.usageRef = usageRef;
    }
    
    public EnergyChargeNamesVO getEnergyChargeName()
    {
        return energyChargeName;
    }
    public void setEnergyChargeName(EnergyChargeNamesVO energyChargeName)
    {
        this.energyChargeName = energyChargeName;
    }
    public PriceBlockHeaderVO getPriceBlock()
    {
        return priceBlock;
    }
    public void setPriceBlock(PriceBlockHeaderVO priceBlock)
    {
        this.priceBlock = priceBlock;
    }
   
    public float getCharge()
    {
        return charge;
    }
    public void setCharge(float charge)
    {
        this.charge = charge;
    }
    public int getTerm()
    {
        return term;
    }
    public void setTerm(int term)
    {
        this.term = term;
    }
    public Date getTermMonth()
    {
        return termMonth;
    }
    public void setTermMonth(Date termMonth)
    {
        this.termMonth = termMonth;
    }
    
    public float getChargeWOL()
    {
        return chargeWOL;
    }
    public void setChargeWOL(float chargeWOL)
    {
        this.chargeWOL = chargeWOL;
    }
}

/*
*$Log: PricingCostVO.java,v $
*Revision 1.2  2008/02/25 09:32:20  tannamalai
*extra column added in prc_cost table to calculate energy charge without loss
*
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/02/13 14:05:04  kduraisamy
*BillingDemand related mapping completed.
*
*Revision 1.1  2007/02/09 11:56:55  kduraisamy
*pricing core algorithm almost finished.
*
*
*/