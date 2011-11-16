/*
 * Created on Mar 2, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FileUploadForm extends ActionForm{
    
    private FormFile theFile;
    private String prsCustId;
    private String prospectiveCustName;
    private String formActions;
    private String esiids = "";
    private String totEsiids = "0";
    
    public String getFormActions() {
        return formActions;
    }
    public void setFormActions(String formActions) {
        this.formActions = formActions;
    }
    public String getPrsCustId() {
        return prsCustId;
    }
    public void setPrsCustId(String prsCustId) {
        this.prsCustId = prsCustId;
    }
    public String getProspectiveCustName() {
        return prospectiveCustName;
    }
    public void setProspectiveCustName(String prospectiveCustName) {
        this.prospectiveCustName = prospectiveCustName;
    }
    public FormFile getTheFile() {
        return theFile;
    }
    public void setTheFile(FormFile theFile) {
        this.theFile = theFile;
    }
    public String getEsiids() {
        return esiids;
    }
    public void setEsiids(String esiids) {
        this.esiids = esiids;
    }
    public String getTotEsiids()
    {
        return totEsiids;
    }
    public void setTotEsiids(String totEsiids)
    {
        this.totEsiids = totEsiids;
    }
}
