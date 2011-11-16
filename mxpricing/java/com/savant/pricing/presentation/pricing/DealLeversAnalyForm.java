/*
 * Created on Mar 7, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import org.apache.struts.action.ActionForm;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DealLeversAnalyForm extends ActionForm{
    
    private String formAction="";
    private String prsCustId="";
    private double custCharge =0;
    private double addlVol = 0;
    private double salesAgentFee = 0.0;
    private double aggregatorFee = 0.0;
    private double bWCharge = 0.0;
    private double otherFee = 0.0;
    private double margin = 0.0;
    private String modifiedDate = "";
    private int term=0;
    private String product="";
    
    public String getModifiedDate() {
        return modifiedDate;
    }
    public String getProduct() {
        return product;
    }
    public int getTerm() {
        return term;
    }
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public void setTerm(int term) {
        this.term = term;
    }
    public double getAddlVol() {
        return addlVol;
    }
    public double getAggregatorFee() {
        return aggregatorFee;
    }
    public double getBWCharge() {
        return bWCharge;
    }
    public double getCustCharge() {
        return custCharge;
    }
    public double getMargin() {
        return margin;
    }
    public double getOtherFee() {
        return otherFee;
    }
    public double getSalesAgentFee() {
        return salesAgentFee;
    }
    public void setAddlVol(double addlVol) {
        this.addlVol = addlVol;
    }
    public void setAggregatorFee(double aggregatorFee) {
        this.aggregatorFee = aggregatorFee;
    }
    public void setBWCharge(double charge) {
        bWCharge = charge;
    }
    public void setCustCharge(double custCharge) {
        this.custCharge = custCharge;
    }
    public void setMargin(double margin) {
        this.margin = margin;
    }
    public void setOtherFee(double otherFee) {
        this.otherFee = otherFee;
    }
    public void setSalesAgentFee(double salesAgentFee) {
        this.salesAgentFee = salesAgentFee;
    }
    public String getFormAction() {
        return formAction;
    }
    public String getPrsCustId() {
        return prsCustId;
    }
    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }
    public void setPrsCustId(String prsCustId) {
        this.prsCustId = prsCustId;
    }
    }
