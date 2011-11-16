/*
 * Created on Feb 15, 2007
 *
 * ClassName	:  	PricingDashBoard.java
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
public class PricingDashBoard implements Serializable
{
    
    private Date contractStartMonth = null;
    private int noOfEsiIds = 0;
    private String customerName = "";
    private float annualkWh = 0;
    private float contractkWh = 0;
    private float maxDemandkW = 0;
    private float loadFactorPercentage = 0;
    private float energyCharge = 0;
    private float shapingPremium = 0;
    private float volatilityPremium = 0;
    private float ancillaryServices = 0;
    private float intraZonalCongestion = 0;
    private float feesAndRegulatory = 0;
    private float customerCharge = 0;
    private float additionalVolatilityPremium = 0;
    private float salesAgentFee = 0;
    private float aggregatorFee = 0;
    private float bandwidthCharge = 0;
    private float otherFee = 0;
    private float margin = 0;
    private float tdspCharges = 0;
    private float energyChargeWithOutLoss = 0;
    private float energyDiff = 0;
    private Vector vecAnnualEnergyDetails;

    public PricingDashBoard()
    {
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    public float getMargin()
    {
        return margin;
    }
    public void setMargin(float margin)
    {
        this.margin = margin;
    }
    public Vector getVecAnnualEnergyDetails()
    {
        return vecAnnualEnergyDetails;
    }
    public void setVecAnnualEnergyDetails(Vector vecAnnualEnergyDetails)
    {
        this.vecAnnualEnergyDetails = vecAnnualEnergyDetails;
    }
    public float getAnnualkWh()
    {
        return annualkWh;
    }
    public void setAnnualkWh(float annualkWh)
    {
        this.annualkWh = annualkWh;
    }
    public float getContractkWh()
    {
        return contractkWh;
    }
    public void setContractkWh(float contractkWh)
    {
        this.contractkWh = contractkWh;
    }
    public Date getContractStartMonth()
    {
        return contractStartMonth;
    }
    public void setContractStartMonth(Date contractStartMonth)
    {
        this.contractStartMonth = contractStartMonth;
    }
    public float getLoadFactorPercentage()
    {
        return loadFactorPercentage;
    }
    public void setLoadFactorPercentage(float loadFactorPercentage)
    {
        this.loadFactorPercentage = loadFactorPercentage;
    }
    public float getMaxDemandkW()
    {
        return maxDemandkW;
    }
    public void setMaxDemandkW(float maxDemandkW)
    {
        this.maxDemandkW = maxDemandkW;
    }
    public int getNoOfEsiIds()
    {
        return noOfEsiIds;
    }
    public void setNoOfEsiIds(int noOfEsiIds)
    {
        this.noOfEsiIds = noOfEsiIds;
    }
    public float getAdditionalVolatilityPremium()
    {
        return additionalVolatilityPremium;
    }
    public void setAdditionalVolatilityPremium(float additionalVolatilityPremium)
    {
        this.additionalVolatilityPremium = additionalVolatilityPremium;
    }
    public float getAggregatorFee()
    {
        return aggregatorFee;
    }
    public void setAggregatorFee(float aggregatorFee)
    {
        this.aggregatorFee = aggregatorFee;
    }
    public float getAncillaryServices()
    {
        return ancillaryServices;
    }
    public void setAncillaryServices(float ancillaryServices)
    {
        this.ancillaryServices = ancillaryServices;
    }
    public float getBandwidthCharge()
    {
        return bandwidthCharge;
    }
    public void setBandwidthCharge(float bandwidthCharge)
    {
        this.bandwidthCharge = bandwidthCharge;
    }
    public float getCustomerCharge()
    {
        return customerCharge;
    }
    public void setCustomerCharge(float customerCharge)
    {
        this.customerCharge = customerCharge;
    }
    public float getEnergyCharge()
    {
        return energyCharge;
    }
    public void setEnergyCharge(float energyCharge)
    {
        this.energyCharge = energyCharge;
    }
    public float getFeesAndRegulatory()
    {
        return feesAndRegulatory;
    }
    public void setFeesAndRegulatory(float feesAndRegulatory)
    {
        this.feesAndRegulatory = feesAndRegulatory;
    }
    public float getIntraZonalCongestion()
    {
        return intraZonalCongestion;
    }
    public void setIntraZonalCongestion(float intraZonalCongestion)
    {
        this.intraZonalCongestion = intraZonalCongestion;
    }
    public float getOtherFee()
    {
        return otherFee;
    }
    public void setOtherFee(float otherFee)
    {
        this.otherFee = otherFee;
    }
   
    public float getSalesAgentFee()
    {
        return salesAgentFee;
    }
    public void setSalesAgentFee(float salesAgentFee)
    {
        this.salesAgentFee = salesAgentFee;
    }
    public float getShapingPremium()
    {
        return shapingPremium;
    }
    public void setShapingPremium(float shapingPremium)
    {
        this.shapingPremium = shapingPremium;
    }
    public float getTdspCharges()
    {
        return tdspCharges;
    }
    public void setTdspCharges(float tdspCharges)
    {
        this.tdspCharges = tdspCharges;
    }
    public float getVolatilityPremium()
    {
        return volatilityPremium;
    }
    public void setVolatilityPremium(float volatilityPremium)
    {
        this.volatilityPremium = volatilityPremium;
    }
    
  
    public float getEnergyChargeWithOutLoss()
    {
        return energyChargeWithOutLoss;
    }
    public void setEnergyChargeWithOutLoss(float energyChargeWithOutLoss)
    {
        this.energyChargeWithOutLoss = energyChargeWithOutLoss;
    }
    
    public float getEnergyDiff()
    {
        return energyDiff;
    }
    public void setEnergyDiff(float energyDiff)
    {
        this.energyDiff = energyDiff;
    }
}

/*
*$Log: PricingDashBoard.java,v $
*Revision 1.2  2008/02/25 09:32:38  tannamalai
*extra column added in prc_cost table to calculate energy charge without loss
*
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/02/28 09:09:15  kduraisamy
*customerName added.
*
*Revision 1.3  2007/02/19 10:15:46  kduraisamy
*spelling mistake corrected.
*
*Revision 1.2  2007/02/19 07:26:18  kduraisamy
*graph details object
*
*Revision 1.1  2007/02/15 06:30:54  kduraisamy
*dashBoardTemplate commited.
*
*
*/