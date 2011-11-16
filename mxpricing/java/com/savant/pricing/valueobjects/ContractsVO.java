/*
 * Created on Mar 28, 2007
 *
 * ClassName	:  	ContractsVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.valueobjects;

import java.io.Serializable;
import java.util.Date;

import com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO;
import com.savant.pricing.securityadmin.valueobject.UsersVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractsVO implements Serializable
{
    private int contractIdentifier;
    private PriceRunCustomerVO priceRunCustomerRef;
    private ProductsVO product;
    private float aggregatorFee;
    private String esiIds;
    private float competitorPrice$PerkWh;
    private float fuelFactor;
    private float avgGasPrice;
    private float mcpeAdder;
    private float computedFAF;
    private float contractkWh;
    private	Date startDate;
    private	Date expDate;
    
    
    //CMS Integration
    
    //Actual
    private int term;
    private float fixedPrice$PerMWh;
    private float baseRate$PerMWh;
    
    private float salesCommision;
    private float tdspCharges;
    private float taxes;
    private float gasPrice$PerMMBtu;
    
    
    //New
    private float annualkWh;
    private float annualkW;
    private float customerCharge; //$
    //private float energySupplyCost;(contractkWh*price$PerkWh)
    private float loadFactor;
    //private float salesCommision$PerkWh;(salesCommision/contractkWh)
    //private float tdspCharges$PerkWh;(tdspCharges/contractkWh)
    private float cityTax;
    private float countyTax;
    private float stateTax;
    private UsersVO salesRep;
    private float contractPrice$PerkWh;//Bundled cost ($/kWh)
    private float totalAnnualBill;//Bundled cost with taxes ($)
    private float heatRate;
    private float heatRateAdder;
    
    
    public ContractsVO()
    {
    }
    
    
    public float getBaseRate$PerMWh()
    {
        return baseRate$PerMWh;
    }
    public void setBaseRate$PerMWh(float baseRate$PerMWh)
    {
        this.baseRate$PerMWh = baseRate$PerMWh;
    }
    public float getFixedPrice$PerMWh()
    {
        return fixedPrice$PerMWh;
    }
    public void setFixedPrice$PerMWh(float fixedPrice$PerMWh)
    {
        this.fixedPrice$PerMWh = fixedPrice$PerMWh;
    }
    public float getComputedFAF()
    {
        return computedFAF;
    }
    public void setComputedFAF(float computedFAF)
    {
        this.computedFAF = computedFAF;
    }
    public float getMcpeAdder()
    {
        return mcpeAdder;
    }
    public void setMcpeAdder(float mcpeAdder)
    {
        this.mcpeAdder = mcpeAdder;
    }
    public float getAvgGasPrice()
    {
        return avgGasPrice;
    }
    public void setAvgGasPrice(float avgGasPrice)
    {
        this.avgGasPrice = avgGasPrice;
    }
    public float getFuelFactor()
    {
        return fuelFactor;
    }
    public Date getExpDate() {
        return expDate;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setFuelFactor(float fuelFactor)
    {
        this.fuelFactor = fuelFactor;
    }
    public float getGasPrice$PerMMBtu()
    {
        return gasPrice$PerMMBtu;
    }
    public void setGasPrice$PerMMBtu(float gasPrice$PerMMBtu)
    {
        this.gasPrice$PerMMBtu = gasPrice$PerMMBtu;
    }
    public String getEsiIds()
    {
        return esiIds;
    }
    public void setEsiIds(String esiIds)
    {
        this.esiIds = esiIds;
    }
    public float getTaxes()
    {
        return taxes;
    }
    public void setTaxes(float taxes)
    {
        this.taxes = taxes;
    }
    public float getTdspCharges()
    {
        return tdspCharges;
    }
    public void setTdspCharges(float tdspCharges)
    {
        this.tdspCharges = tdspCharges;
    }
    public float getAggregatorFee()
    {
        return aggregatorFee;
    }
    public void setAggregatorFee(float aggregatorFee)
    {
        this.aggregatorFee = aggregatorFee;
    }
    public float getCompetitorPrice$PerkWh()
    {
        return competitorPrice$PerkWh;
    }
    public void setCompetitorPrice$PerkWh(float competitorPrice$PerkWh)
    {
        this.competitorPrice$PerkWh = competitorPrice$PerkWh;
    }
    public int getContractIdentifier()
    {
        return contractIdentifier;
    }
    public void setContractIdentifier(int contractIdentifier)
    {
        this.contractIdentifier = contractIdentifier;
    }
    public float getContractkWh()
    {
        return contractkWh;
    }
    public void setContractkWh(float contractkWh)
    {
        this.contractkWh = contractkWh;
    }
    public PriceRunCustomerVO getPriceRunCustomerRef()
    {
        return priceRunCustomerRef;
    }
    public void setPriceRunCustomerRef(PriceRunCustomerVO priceRunCustomerRef)
    {
        this.priceRunCustomerRef = priceRunCustomerRef;
    }
    public ProductsVO getProduct()
    {
        return product;
    }
    public void setProduct(ProductsVO product)
    {
        this.product = product;
    }
    public float getSalesCommision()
    {
        return salesCommision;
    }
    public void setSalesCommision(float salesCommision)
    {
        this.salesCommision = salesCommision;
    }
    public int getTerm()
    {
        return term;
    }
    public void setTerm(int term)
    {
        this.term = term;
    }
    
    public float getAnnualkW()
    {
        return annualkW;
    }
    public void setAnnualkW(float annualkW)
    {
        this.annualkW = annualkW;
    }
    public float getAnnualkWh()
    {
        return annualkWh;
    }
    public void setAnnualkWh(float annualkWh)
    {
        this.annualkWh = annualkWh;
    }
    public float getCityTax()
    {
        return cityTax;
    }
    public void setCityTax(float cityTax)
    {
        this.cityTax = cityTax;
    }
    public float getContractPrice$PerkWh()
    {
        return contractPrice$PerkWh;
    }
    public void setContractPrice$PerkWh(float contractPrice$PerkWh)
    {
        this.contractPrice$PerkWh = contractPrice$PerkWh;
    }
    public float getCountyTax()
    {
        return countyTax;
    }
    public void setCountyTax(float countyTax)
    {
        this.countyTax = countyTax;
    }
    public float getCustomerCharge()
    {
        return customerCharge;
    }
    public void setCustomerCharge(float customerCharge)
    {
        this.customerCharge = customerCharge;
    }
    
    public float getLoadFactor()
    {
        return loadFactor;
    }
    public void setLoadFactor(float loadFactor)
    {
        this.loadFactor = loadFactor;
    }
    public UsersVO getSalesRep()
    {
        return salesRep;
    }
    public void setSalesRep(UsersVO salesRep)
    {
        this.salesRep = salesRep;
    }
    public float getStateTax()
    {
        return stateTax;
    }
    public void setStateTax(float stateTax)
    {
        this.stateTax = stateTax;
    }
    public float getTotalAnnualBill()
    {
        return totalAnnualBill;
    }
    public void setTotalAnnualBill(float totalAnnualBill)
    {
        this.totalAnnualBill = totalAnnualBill;
    }
    public float getHeatRate()
    {
        return heatRate;
    }
    public void setHeatRate(float heatRate)
    {
        this.heatRate = heatRate;
    }
    public float getHeatRateAdder()
    {
        return heatRateAdder;
    }
    public void setHeatRateAdder(float heatRateAdder)
    {
        this.heatRateAdder = heatRateAdder;
    }
}

/*
*$Log: ContractsVO.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:45  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.10  2007/06/23 08:03:29  jnadesan
*updating startDate and ExpDate
*
*Revision 1.9  2007/05/28 10:49:47  jnadesan
*customer charge is in $
*
*Revision 1.8  2007/05/26 05:22:45  kduraisamy
*baseRate$PerMWh, fixedPrice$PerMWh added.
*
*Revision 1.7  2007/05/25 11:38:59  kduraisamy
*heat rate and heat rate adder added.
*
*Revision 1.6  2007/05/25 10:12:05  kduraisamy
*New fields added for CMS Integration.
*
*Revision 1.5  2007/04/17 04:21:18  kduraisamy
*computed FAF field added.
*
*Revision 1.4  2007/04/13 12:00:32  kduraisamy
*fields added for remaining products.
*
*Revision 1.3  2007/04/02 14:57:12  kduraisamy
*esiIds added in cpe.
*
*Revision 1.2  2007/03/28 07:25:07  kduraisamy
*tdsp Charges and taxes added for contract.
*
*Revision 1.1  2007/03/28 05:09:41  kduraisamy
*initial commit.
*
*
*/