/*
 * Created on Apr 17, 2007
 * 
 * Class Name edf.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pdf;

import com.savant.pricing.common.BuildConfig;


/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CPEFARSubDetails
{

    private int term =0;
    private float ff = 0;
    private float contractkWh =0;
    private float baseRate =0;
    private float baseRateperkwh = 0;
    private float fuelFactor = 0;
    private float fuelfactorperkWh = 0;
    private float passthroughAmount = 0;
    private float passthroughperkwh =0;
    private float tax =0;
    private float taxesperkwh =0;
    private float totalAmount = 0;
    private float totalperkwh =0;
    
    public CPEFARSubDetails(int term,float ff,float contractperkWh,float baseRateperkwh,float fuelFactor,float passthroughAmount,float tax)
    {
        this.term = term;
        this.contractkWh = contractperkWh;
        this.ff = ff;
        this.baseRate = baseRateperkwh*contractperkWh;
        this.baseRateperkwh = baseRateperkwh;
        this.fuelFactor = fuelFactor*contractperkWh;
        this.fuelfactorperkWh = fuelFactor;
        this.passthroughAmount = passthroughAmount;
        this.passthroughperkwh = passthroughAmount/contractperkWh;
        this.tax = tax;
        this.taxesperkwh = tax/contractperkWh;
        this.totalAmount = this.tax + this.baseRate + this.fuelFactor + this.passthroughAmount;
        this.totalperkwh = this.totalAmount/contractperkWh;
        if(BuildConfig.DMODE)
            System.out.println("total  : "+this.totalperkwh);
    }

    
    public float getBaseRate() {
        return baseRate;
    }
    public float getBaseRateperkwh() {
        return baseRateperkwh;
    }
    public float getContractkWh() {
        return contractkWh;
    }
    public float getFf() {
        return ff;
    }
    public float getFuelFactor() {
        return fuelFactor;
    }
    public float getFuelfactorperkWh() {
        return fuelfactorperkWh;
    }
    public float getPassthroughAmount() {
        return passthroughAmount;
    }
    public float getPassthroughperkwh() {
        return passthroughperkwh;
    }
    public float getTax() {
        return tax;
    }
    public float getTaxesperkwh() {
        return taxesperkwh;
    }
    public int getTerm() {
        return term;
    }
    public float getTotalAmount() {
        return totalAmount;
    }
    public float getTotalperkwh() {
        return totalperkwh;
    }
    public void setBaseRate(float baseRate) {
        this.baseRate = baseRate;
    }
    public void setBaseRateperkwh(float baseRateperkwh) {
        this.baseRateperkwh = baseRateperkwh;
    }
    public void setContractkWh(float contractkWh) {
        this.contractkWh = contractkWh;
    }
    public void setFf(float ff) {
        this.ff = ff;
    }
    public void setFuelFactor(float fuelFactor) {
        this.fuelFactor = fuelFactor;
    }
    public void setFuelfactorperkWh(float fuelfactorperkWh) {
        this.fuelfactorperkWh = fuelfactorperkWh;
    }
    public void setPassthroughAmount(float passthroughAmount) {
        this.passthroughAmount = passthroughAmount;
    }
    public void setPassthroughperkwh(float passthroughperkwh) {
        this.passthroughperkwh = passthroughperkwh;
    }
    public void setTax(float tax) {
        this.tax = tax;
    }
    public void setTaxesperkwh(float taxesperkwh) {
        this.taxesperkwh = taxesperkwh;
    }
    public void setTerm(int term) {
        this.term = term;
    }
    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
    public void setTotalperkwh(float totalperkwh) {
        this.totalperkwh = totalperkwh;
    }
}


/*
*$Log: CPEFARSubDetails.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/06/12 12:57:53  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.2  2007/04/23 05:52:20  kduraisamy
*unwanted println removed.
*
*Revision 1.1  2007/04/17 15:17:40  jnadesan
*CPE And Contrcat details for FAR
*
*
*/