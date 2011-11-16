/*
 * Created on Mar 7, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.util.Date;

import org.apache.struts.action.ActionForm;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OverridesForm extends ActionForm{

    private String formActions="";
    private String prsCustId="";
    private String esiid = "";
    private Date startMonth = null;
    private Date endMonth = null;
    private String istaID = "";
    private String serviceAddress = "";
    
    
    
    public Date getEndMonth() {
        return endMonth;
    }
    public String getEsiid() {
        return esiid;
    }
    public String getFormActions() {
        return formActions;
    }
    public String getIstaID() {
        return istaID;
    }
    public String getPrsCustId() {
        return prsCustId;
    }
    public String getServiceAddress() {
        return serviceAddress;
    }
    public Date getStartMonth() {
        return startMonth;
    }
    public void setEndMonth(Date endMonth) {
        this.endMonth = endMonth;
    }
    public void setEsiid(String esiid) {
        this.esiid = esiid;
    }
    public void setFormActions(String formActions) {
        this.formActions = formActions;
    }
    public void setIstaID(String istaID) {
        this.istaID = istaID;
    }
    public void setPrsCustId(String prsCustId) {
        this.prsCustId = prsCustId;
    }
    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
    public void setStartMonth(Date startMonth) {
        this.startMonth = startMonth;
    }
}
