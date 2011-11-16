package com.savant.pricing.pdf;

import java.util.Date;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CPEFAR2HistoricalPriceDetails {
    private Date billingMnth = null;
    private double nmxClosePrice = 0;
    private double fuelAdjRate = 0;
    private double energyRate = 0;
    
    
    public CPEFAR2HistoricalPriceDetails(Date billingMnth,double nmxclose,double fuelAdjRate,double energyPrice)
    {
        this.billingMnth = billingMnth;
        this.nmxClosePrice = nmxclose;
        this.fuelAdjRate = fuelAdjRate;
        this.energyRate = energyPrice;
    }

    public Date getBillingMnth() {
        return billingMnth;
    }
    public double getEnergyRate() {
        return energyRate;
    }
    public double getFuelAdjRate() {
        return fuelAdjRate;
    }
    public double getNmxClosePrice() {
        return nmxClosePrice;
    }
    public void setBillingMnth(Date billingMnth) {
        this.billingMnth = billingMnth;
    }
    public void setEnergyRate(double energyRate) {
        this.energyRate = energyRate;
    }
    public void setFuelAdjRate(double fuelAdjRate) {
        this.fuelAdjRate = fuelAdjRate;
    }
    public void setNmxClosePrice(double nmxClosePrice) {
        this.nmxClosePrice = nmxClosePrice;
    }
}


/*
*$Log: CPEFAR2HistoricalPriceDetails.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/05/21 06:03:05  jnadesan
*resources for cpe2 for FAR product
*
*
*/