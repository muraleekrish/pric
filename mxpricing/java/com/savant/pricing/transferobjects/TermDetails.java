/*
 * Created on Feb 7, 2007
 *
 * ClassName	:  	TermDetails.java
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
public class TermDetails implements Serializable
{
    private int term;
    private Date termStartDate;
    private Date termEndDate;
    private float energyDemand;
    private float apparentPower;
    private float billingDemand;
    private float customerCharge;
    private float meteringCharge;
    private float transmissionCharge;
    private float distributionCharge;
    private float systemBenefitFund;
    private float nuclearDecommissionCharge;
    private float totCharge;
    private Vector vecIndividualTermDetails;
    
    public TermDetails()
    {
    }
    
    public float getTotCharge()
    {
        return totCharge;
    }
    public void setTotCharge(float totCharge)
    {
        this.totCharge = totCharge;
    }
    public float getBillingDemand()
    {
        return billingDemand;
    }
    public void setBillingDemand(float billingDemand)
    {
        this.billingDemand = billingDemand;
    }
    public int getTerm()
    {
        return term;
    }
    public void setTerm(int term)
    {
        this.term = term;
    }
    public Date getTermEndDate()
    {
        return termEndDate;
    }
    public void setTermEndDate(Date termEndDate)
    {
        this.termEndDate = termEndDate;
    }
    public Date getTermStartDate()
    {
        return termStartDate;
    }
    public void setTermStartDate(Date termStartDate)
    {
        this.termStartDate = termStartDate;
    }
    public Vector getVecIndividualTermDetails()
    {
        return vecIndividualTermDetails;
    }
    public void setVecIndividualTermDetails(Vector vecIndividualTermDetails)
    {
        this.vecIndividualTermDetails = vecIndividualTermDetails;
    }
    public float getApparentPower()
    {
        return apparentPower;
    }
    public void setApparentPower(float apparentPower)
    {
        this.apparentPower = apparentPower;
    }
    public float getEnergyDemand()
    {
        return energyDemand;
    }
    public void setEnergyDemand(float energyDemand)
    {
        this.energyDemand = energyDemand;
    }
    public float getCustomerCharge()
    {
        return customerCharge;
    }
    public void setCustomerCharge(float customerCharge)
    {
        this.customerCharge = customerCharge;
    }
    public float getDistributionCharge()
    {
        return distributionCharge;
    }
    public void setDistributionCharge(float distributionCharge)
    {
        this.distributionCharge = distributionCharge;
    }
    public float getMeteringCharge()
    {
        return meteringCharge;
    }
    public void setMeteringCharge(float meteringCharge)
    {
        this.meteringCharge = meteringCharge;
    }
    public float getNuclearDecommissionCharge()
    {
        return nuclearDecommissionCharge;
    }
    public void setNuclearDecommissionCharge(float nuclearDecommissionCharge)
    {
        this.nuclearDecommissionCharge = nuclearDecommissionCharge;
    }
    public float getSystemBenefitFund()
    {
        return systemBenefitFund;
    }
    public void setSystemBenefitFund(float systemBenefitFund)
    {
        this.systemBenefitFund = systemBenefitFund;
    }
    public float getTransmissionCharge()
    {
        return transmissionCharge;
    }
    public void setTransmissionCharge(float transmissionCharge)
    {
        this.transmissionCharge = transmissionCharge;
    }
}

/*
*$Log: TermDetails.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/02/14 12:45:57  kduraisamy
*esiId wise details stored in static hashMap.
*
*Revision 1.2  2007/02/13 14:05:23  kduraisamy
*BillingDemand related mapping completed.
*
*Revision 1.1  2007/02/09 11:58:04  kduraisamy
*pricing core algorithm almost finished.
*
*
*/