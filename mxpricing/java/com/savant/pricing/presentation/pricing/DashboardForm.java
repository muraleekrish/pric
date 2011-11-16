/*

 * Created on Jan 24, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author srajappan
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DashboardForm extends ActionForm {

    private String formActions = "list";
    private String txtContMnth  ;
    private String runRefId = "0";
    private String tdsps = "";
    private String esiids = "";
    private String congestionZones = "";
    
    public String getCongestionZones()
    {
        return congestionZones;
    }
    public void setCongestionZones(String congestionZones)
    {
        this.congestionZones = congestionZones;
    }
    public String getEsiids()
    {
        return esiids;
    }
    public void setEsiids(String esiids)
    {
        this.esiids = esiids;
    }
    public String getTdsps()
    {
        return tdsps;
    }
    public void setTdsps(String tdsps)
    {
        this.tdsps = tdsps;
    }
    public String getFormActions() {
        return formActions;
    }

    public void setFormActions(String formActions) {
        this.formActions = formActions;
    }

    public String getTxtContMnth() {
        return txtContMnth;
    }

    public void setTxtContMnth(String txtContMnth) {
        this.txtContMnth = txtContMnth;
    }

    public String getRunRefId() {
        return runRefId;
    }

    public void setRunRefId(String runRefId) {
        this.runRefId = runRefId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
       
       
        return super.validate(arg0, arg1);
        
    }
}