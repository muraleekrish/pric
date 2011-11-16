/*
 * Created on May 18, 2007
 * 
 * Class Name CPEFARTwoTermDetails.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pdf;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CPEFARTwoTermDetails {
    
    private int term;
    private double baseRate;
    private double fuelFactor;
    private double baseNaturalGasPrice;

    public CPEFARTwoTermDetails(int localTerm,double baseRate,double fuelFactor,double gasPrice)
    {
        this.term = localTerm;
        this.baseRate = baseRate;
        this.fuelFactor = fuelFactor;
        this.baseNaturalGasPrice = gasPrice;
    }
    public double getBaseNaturalGasPrice() {
        return baseNaturalGasPrice;
    }
    public double getBaseRate() {
        return baseRate;
    }
    public double getFuelFactor() {
        return fuelFactor;
    }
    public int getTerm() {
        return term;
    }
    public void setBaseNaturalGasPrice(double baseNaturalGasPrice) {
        this.baseNaturalGasPrice = baseNaturalGasPrice;
    }
    public void setBaseRate(double baseRate) {
        this.baseRate = baseRate;
    }
    public void setFuelFactor(double fuelFactor) {
        this.fuelFactor = fuelFactor;
    }
    public void setTerm(int term) {
        this.term = term;
    }
}


/*
*$Log: CPEFARTwoTermDetails.java,v $
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