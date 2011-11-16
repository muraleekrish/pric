/*
 * Created on Feb 26, 2007
 *
 * ClassName	:  	EPP.java
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
public class EPP implements Serializable
{
    private String customerName;
    private int noOfEsiIds;
    private Date contractStartMonth;
    private Date contractEndMonth;
    private int term;
    private float baseGasPrice;
    private float fafMultiplier;
    private float weightedAvgGasPrice;
    private float fixedPrice;
    private float baseRate;
    private float fuelAdjustmentRate;
    private Vector vecIndividualEPP;
    public EPP()
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
    public Vector getVecIndividualEPP()
    {
        return vecIndividualEPP;
    }
    public void setVecIndividualEPP(Vector vecIndividualEPP)
    {
        this.vecIndividualEPP = vecIndividualEPP;
    }
    public float getBaseGasPrice()
    {
        return baseGasPrice;
    }
    public void setBaseGasPrice(float baseGasPrice)
    {
        this.baseGasPrice = baseGasPrice;
    }
    public float getBaseRate()
    {
        return baseRate;
    }
    public void setBaseRate(float baseRate)
    {
        this.baseRate = baseRate;
    }
    public Date getContractEndMonth()
    {
        return contractEndMonth;
    }
    public void setContractEndMonth(Date contractEndMonth)
    {
        this.contractEndMonth = contractEndMonth;
    }
    public Date getContractStartMonth()
    {
        return contractStartMonth;
    }
    public void setContractStartMonth(Date contractStartMonth)
    {
        this.contractStartMonth = contractStartMonth;
    }
    public float getFafMultiplier()
    {
        return fafMultiplier;
    }
    public void setFafMultiplier(float fafMultiplier)
    {
        this.fafMultiplier = fafMultiplier;
    }
    public float getFixedPrice()
    {
        return fixedPrice;
    }
    public void setFixedPrice(float fixedPrice)
    {
        this.fixedPrice = fixedPrice;
    }
    public float getFuelAdjustmentRate()
    {
        return fuelAdjustmentRate;
    }
    public void setFuelAdjustmentRate(float fuelAdjustmentRate)
    {
        this.fuelAdjustmentRate = fuelAdjustmentRate;
    }
    public int getNoOfEsiIds()
    {
        return noOfEsiIds;
    }
    public void setNoOfEsiIds(int noOfEsiIds)
    {
        this.noOfEsiIds = noOfEsiIds;
    }
    public int getTerm()
    {
        return term;
    }
    public void setTerm(int term)
    {
        this.term = term;
    }
    public float getWeightedAvgGasPrice()
    {
        return weightedAvgGasPrice;
    }
    public void setWeightedAvgGasPrice(float weightedAvgGasPrice)
    {
        this.weightedAvgGasPrice = weightedAvgGasPrice;
    }
}

/*
*$Log: EPP.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/02/28 09:34:06  kduraisamy
*customerName added.
*
*Revision 1.1  2007/02/26 13:27:02  kduraisamy
*HeatRate and energy Partner Plan added.
*
*
*/