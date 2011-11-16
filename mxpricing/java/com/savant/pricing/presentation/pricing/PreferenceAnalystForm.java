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
public class PreferenceAnalystForm extends ActionForm{

    private String formActions="";
    private String prsCustId="";
    private String startMonth=null;
    private String endMonth=null;
    private String terms = "";
    private boolean autoRun;
    private String product;
    private String esiids="";
    private String possibleGroups = "";
    private String selectedGroups = "";
    
    
    
    public String getPossibleGroups() {
        return possibleGroups;
    }
    public void setPossibleGroups(String possibleGroups) {
        this.possibleGroups = possibleGroups;
    }
    public boolean isAutoRun() {
        return autoRun;
    }
    
    public String getFormActions() {
        return formActions;
    }
    public String getProduct() {
        return product;
    }
    public String getPrsCustId() {
        return prsCustId;
    }
    
   
    public void setAutoRun(boolean autoRun) {
        this.autoRun = autoRun;
    }
   
    public String getEsiids() {
        return esiids;
    }
    public void setEsiids(String esiids) {
        this.esiids = esiids;
    }
    public void setFormActions(String formActions) {
        this.formActions = formActions;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public void setPrsCustId(String prsCustId) {
        this.prsCustId = prsCustId;
    }
    
    public String getEndMonth() {
        return endMonth;
    }
    public String getStartMonth() {
        return startMonth;
    }
    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }
    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }
    public String getTerms() {
        return terms;
    }
    public void setTerms(String terms) {
        this.terms = terms;
    }
    public String getSelectedGroups() {
        return selectedGroups;
    }
    public void setSelectedGroups(String selectedGroups) {
        this.selectedGroups = selectedGroups;
    }
}
