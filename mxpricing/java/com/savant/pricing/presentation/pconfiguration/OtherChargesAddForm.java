/*
 * Created on Jun 11, 2007
 * 
 * Class Name OtherChargesAddForm.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pconfiguration;

import org.apache.struts.action.ActionForm;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OtherChargesAddForm extends ActionForm{
    
    private String cmbYear = "";
    private String cmbEnergeChrgeId = "1";
    private String cmbCongestionId = "0";
    private String cmbUnit = "1";
    private String[] month;
    private String formActions="";
    private String txtValue ="";
    
    public String getCmbUnit() {
        return cmbUnit;
    }
    public String getCmbCongestionId() {
        return cmbCongestionId;
    }
    public String getCmbYear() {
        return cmbYear;
    }
    public String[] getMonth() {
        return month;
    }
    public void setCmbCongestionId(String cmbCongestionId) {
        this.cmbCongestionId = cmbCongestionId;
    }
    public void setCmbYear(String cmbYear) {
        this.cmbYear = cmbYear;
    }
    public void setMonth(String[] month) {
        this.month = month;
    }
    public void setCmbUnit(String cmbUnit) {
        this.cmbUnit = cmbUnit;
    }
    public String getCmbEnergeChrgeId() {
        return cmbEnergeChrgeId;
    }
    public String getFormActions() {
        return formActions;
    }
    public String getTxtValue() {
        return txtValue;
    }
    public void setCmbEnergeChrgeId(String cmbEnergeChrgeId) {
        this.cmbEnergeChrgeId = cmbEnergeChrgeId;
    }
    public void setFormActions(String formActions) {
        this.formActions = formActions;
    }
    public void setTxtValue(String txtValue) {
        this.txtValue = txtValue;
    }
}


/*
*$Log: OtherChargesAddForm.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/07/11 13:21:28  jnadesan
*energy chargerates add/update provision given
*
*
*/