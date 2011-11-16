/*
 * Created on May 18, 2007
 * 
 * Class Name CPEFARTwoMainDetails.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pdf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CPEFARTwoMainDetails {
    
    private Date netGasFwdDate = null;
    private double newGasFwdPrice = 0;
    private double loadFactor = 0;
    private double annualkWh = 0;
    private List termDetails = new ArrayList();
    private List termDetailsForMonthlyElectticRate = new ArrayList();
    private List historicalPriceInfo = new ArrayList();
    private String custName ="";
    
    public CPEFARTwoMainDetails(String custName,Date netGasDate, double netGasPrice,double lf,double annualkWh,List termDetails,List historicalPriceInfo)
    {  
        this.custName = custName;
        this.netGasFwdDate = netGasDate;
        this.newGasFwdPrice = netGasPrice;
        this.loadFactor = lf;
        this.annualkWh = annualkWh;
        this.termDetails = termDetails;
        this.historicalPriceInfo = historicalPriceInfo;
        this.termDetailsForMonthlyElectticRate = this.termDetails;
    }
    
    public List getTermDetailsForMonthlyElectticRate() {
        return termDetailsForMonthlyElectticRate;
    }
    public void setTermDetailsForMonthlyElectticRate(
            List termDetailsForMonthlyElectticRate) {
        this.termDetailsForMonthlyElectticRate = termDetailsForMonthlyElectticRate;
    }
    public String getCustName() {
        return custName;
    }
    public void setCustName(String custName) {
        this.custName = custName;
    }
    public double getAnnualkWh() {
        return annualkWh;
    }
    public List getHistoricalPriceInfo() {
        return historicalPriceInfo;
    }
    public double getLoadFactor() {
        return loadFactor;
    }
    public Date getNetGasFwdDate() {
        return netGasFwdDate;
    }
    public double getNewGasFwdPrice() {
        return newGasFwdPrice;
    }
    public List getTermDetails() {
        return termDetails;
    }
    public void setAnnualkWh(double annualkWh) {
        this.annualkWh = annualkWh;
    }
    public void setHistoricalPriceInfo(List historicalPriceInfo) {
        this.historicalPriceInfo = historicalPriceInfo;
    }
    public void setLoadFactor(double loadFactor) {
        this.loadFactor = loadFactor;
    }
    public void setNetGasFwdDate(Date netGasFwdDate) {
        this.netGasFwdDate = netGasFwdDate;
    }
    public void setNewGasFwdPrice(double newGasFwdPrice) {
        this.newGasFwdPrice = newGasFwdPrice;
    }
    public void setTermDetails(List termDetails) {
        this.termDetails = termDetails;
    }
}


/*
*$Log: CPEFARTwoMainDetails.java,v $
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