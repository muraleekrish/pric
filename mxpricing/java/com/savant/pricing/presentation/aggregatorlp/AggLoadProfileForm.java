/*
 * Created on May 8, 2007
 * 
 * Class Name AggLoadProfileForm.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.aggregatorlp;

import org.apache.struts.action.ActionForm;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AggLoadProfileForm extends ActionForm{
    
    private String formActions = "list";
    private String txtContMnth  ;
    private String runRefId = "0";
    private String tdsps = "";
    private String esiids = "";
    private String congestionZones = "";

    public String getCongestionZones() {
        return congestionZones;
    }
    public String getEsiids() {
        return esiids;
    }
    public String getFormActions() {
        return formActions;
    }
    public String getRunRefId() {
        return runRefId;
    }
    public String getTdsps() {
        return tdsps;
    }
    public String getTxtContMnth() {
        return txtContMnth;
    }
    public void setCongestionZones(String congestionZones) {
        this.congestionZones = congestionZones;
    }
    public void setEsiids(String esiids) {
        this.esiids = esiids;
    }
    public void setFormActions(String formActions) {
        this.formActions = formActions;
    }
    public void setRunRefId(String runRefId) {
        this.runRefId = runRefId;
    }
    public void setTdsps(String tdsps) {
        this.tdsps = tdsps;
    }
    public void setTxtContMnth(String txtContMnth) {
        this.txtContMnth = txtContMnth;
    }
}


/*
*$Log: AggLoadProfileForm.java,v $
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:30  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/05/08 13:47:03  jnadesan
*load profile chart plotted by esiid wise and export excel option added
*
*
*/