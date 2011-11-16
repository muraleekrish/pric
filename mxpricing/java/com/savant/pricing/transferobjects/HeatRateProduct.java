/*
 * Created on Feb 26, 2007
 *
 * ClassName	:  	HeatRateProduct.java
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
public class HeatRateProduct implements Serializable
{
    private float fixedHeatRateInput;
    private int terms;
    private int noOfEsiIds;
    private Date contractStartDate;
    private Date contractEndDate;
    private String customerName;
    private String heatRateDataSource;
    private float fixedPrice;
    private float equivalentWholeSalePrice;
    private Vector vecIndividualHeatRateProduct;
    private float retailAdder;
    
    public HeatRateProduct()
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
    public float getRetailAdder()
    {
        return retailAdder;
    }
    public void setRetailAdder(float retailAdder)
    {
        this.retailAdder = retailAdder;
    }
    public Date getContractEndDate()
    {
        return contractEndDate;
    }
    public void setContractEndDate(Date contractEndDate)
    {
        this.contractEndDate = contractEndDate;
    }
    public Date getContractStartDate()
    {
        return contractStartDate;
    }
    public void setContractStartDate(Date contractStartDate)
    {
        this.contractStartDate = contractStartDate;
    }
    public float getEquivalentWholeSalePrice()
    {
        return equivalentWholeSalePrice;
    }
    public void setEquivalentWholeSalePrice(float equivalentWholeSalePrice)
    {
        this.equivalentWholeSalePrice = equivalentWholeSalePrice;
    }
    public float getFixedHeatRateInput()
    {
        return fixedHeatRateInput;
    }
    public void setFixedHeatRateInput(float fixedHeatRateInput)
    {
        this.fixedHeatRateInput = fixedHeatRateInput;
    }
    public float getFixedPrice()
    {
        return fixedPrice;
    }
    public void setFixedPrice(float fixedPrice)
    {
        this.fixedPrice = fixedPrice;
    }
    public String getHeatRateDataSource()
    {
        return heatRateDataSource;
    }
    public void setHeatRateDataSource(String heatRateDataSource)
    {
        this.heatRateDataSource = heatRateDataSource;
    }
    public int getNoOfEsiIds()
    {
        return noOfEsiIds;
    }
    public void setNoOfEsiIds(int noOfEsiIds)
    {
        this.noOfEsiIds = noOfEsiIds;
    }
    public int getTerms()
    {
        return terms;
    }
    public void setTerms(int terms)
    {
        this.terms = terms;
    }
    
    public Vector getVecIndividualHeatRateProduct()
    {
        return vecIndividualHeatRateProduct;
    }
    public void setVecIndividualHeatRateProduct(
            Vector vecIndividualHeatRateProduct)
    {
        this.vecIndividualHeatRateProduct = vecIndividualHeatRateProduct;
    }
}

/*
*$Log: HeatRateProduct.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/02/28 09:09:56  kduraisamy
*customerName added.
*
*Revision 1.1  2007/02/26 13:27:02  kduraisamy
*HeatRate and energy Partner Plan added.
*
*
*/