/*
 * Created on Apr 2, 2007
 * 
 * Class Name CPEFixedDetails.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pdf;

import java.util.Date;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CPEFixedDetails {
    
    private String CustName = "";
    private float loadFactor = 0;
    private float annualConsumption = 0;
    private int term =0;
    private int esiidCount = 0;
    private float forwdCurvePrice=0;
    private float contractkWh =0;
    private float CommidityAmount =0;
    private float passthroughAmount = 0;
    private float totalAmount = 0;
    private Date contractStMnth =null;
    private float tax =0;
    private float commoditityperkwh = 0;
    private float passthroughperkwh =0;
    private float taxesperkwh =0;
    private float totalperkwh =0;
    private Date gaspriceDate =null;
    
    /**
     * 
     */
    
    public CPEFixedDetails(String Custname,float lf,float annualCun,Date stmnth,int term,int esiidCount,float forwdCurvePrice, float contractkWh,float CommidityAmount,float passthroughAmount,float tax,Date gaspriceDate) {
        this.CustName = Custname;
        this.loadFactor = lf/100;
        this.annualConsumption = annualCun;
        this.term = term;
        this.esiidCount = esiidCount;
        this.forwdCurvePrice = forwdCurvePrice;
        this.contractkWh = contractkWh;
        this.CommidityAmount = CommidityAmount*this.contractkWh;
        this.passthroughAmount = passthroughAmount;
        this.tax = tax;
        this.contractStMnth = stmnth;
        this.contractkWh = contractkWh;
        this.totalAmount = this.CommidityAmount+passthroughAmount+tax;
        this.totalperkwh = this.totalAmount/this.contractkWh;
        this.commoditityperkwh = this.CommidityAmount/this.contractkWh;
        this.passthroughperkwh = this.passthroughAmount/this.contractkWh;
        this.taxesperkwh = this.tax/this.contractkWh;
        this.gaspriceDate = gaspriceDate;
        
        // TODO Auto-generated constructor stub
    }
    
    public Date getContractStMnth() {
        return contractStMnth;
    }
    public void setContractStMnth(Date contractStMnth) {
        this.contractStMnth = contractStMnth;
    }
    public float getCommoditityperkwh() {
        return commoditityperkwh;
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
    public float getTotalperkwh() {
        return totalperkwh;
    }
    public void setCommoditityperkwh(float commoditityperkwh) {
        this.commoditityperkwh = commoditityperkwh;
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
    public void setTotalperkwh(float totalperkwh) {
        this.totalperkwh = totalperkwh;
    }
    public float getAnnualConsumption() {
        return annualConsumption;
    }
    public float getCommidityAmount() {
        return CommidityAmount;
    }
    public float getContractkWh() {
        return contractkWh;
    }
    
    public String getCustName() {
        return CustName;
    }
    
    public int getEsiidCount() {
        return esiidCount;
    }
    public float getForwdCurvePrice() {
        return forwdCurvePrice;
    }
    public float getLoadFactor() {
        return loadFactor;
    }
    public float getPassthroughAmount() {
        return passthroughAmount;
    }
    public int getTerm() {
        return term;
    }
    public float getTotalAmount() {
        return totalAmount;
    }
    public void setAnnualConsumption(float annualConsumption) {
        this.annualConsumption = annualConsumption;
    }
    public void setCommidityAmount(float commidityAmount) {
        CommidityAmount = commidityAmount;
    }
    public void setContractkWh(float contractkWh) {
        this.contractkWh = contractkWh;
    }
    
    public void setCustName(String custName) {
        CustName = custName;
    }
    public void setEsiidCount(int esiidCount) {
        this.esiidCount = esiidCount;
    }
    public void setForwdCurvePrice(float forwdCurvePrice) {
        this.forwdCurvePrice = forwdCurvePrice;
    }
    public void setLoadFactor(float loadFactor) {
        this.loadFactor = loadFactor;
    }
    public void setPassthroughAmount(float passthroughAmount) {
        this.passthroughAmount = passthroughAmount;
    }
    public void setTerm(int term) {
        this.term = term;
    }
    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
    public Date getGaspriceDate() {
        return gaspriceDate;
    }
    public void setGaspriceDate(Date gaspriceDate) {
        this.gaspriceDate = gaspriceDate;
    }
}


/*
*$Log: CPEFixedDetails.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/05/15 05:19:56  jnadesan
*unwanted fields are remove
*
*Revision 1.1  2007/04/10 09:53:43  jnadesan
*DataSource for fixed Contract
*
*Revision 1.4  2007/04/06 03:53:53  jnadesan
*Datasource files for pdf creation
*
*Revision 1.3  2007/04/03 07:02:22  jnadesan
*Details for pdf
*
*Revision 1.2  2007/04/02 14:16:17  jnadesan
*fields added
*
*Revision 1.1  2007/04/02 11:21:12  jnadesan
*pdf added
*
*
*/