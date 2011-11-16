/*

 * Created on Jan 24, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import org.apache.struts.action.ActionForm;

/**
 * @author srajappan
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EPPForm extends ActionForm {

    private String formAction = "list";
    private String term ;
    private String runRefId = "0";
    private String  energyOnlyPrice ="";
    private String  ffMultiplier ="0.010255";    
    private String  baseGasPrice ="7";    
  
    
    
    
    
    public String getBaseGasPrice() {
        return baseGasPrice;
    }
    public void setBaseGasPrice(String baseGasPrice) {
        this.baseGasPrice = baseGasPrice;
    }
    public String getFfMultiplier() {
        return ffMultiplier;
    }
    public void setFfMultiplier(String ffMultiplier) {
        this.ffMultiplier = ffMultiplier;
    }
    public String getEnergyOnlyPrice() {
        return energyOnlyPrice;
    }
    public void setEnergyOnlyPrice(String energyOnlyPrice) {
        this.energyOnlyPrice = energyOnlyPrice;
    }
    public String getFormAction() {
        return formAction;
    }
    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }
    public String getRunRefId() {
        return runRefId;
    }
    public void setRunRefId(String runRefId) {
        this.runRefId = runRefId;
    }
    public String getTerm() {
        return term;
    }
    public void setTerm(String term) {
        this.term = term;
    }
}