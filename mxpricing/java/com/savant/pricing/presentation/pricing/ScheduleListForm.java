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
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ScheduleListForm extends ActionForm
{
    private String txtCustomerId = "";
    private String cmbCDRStatus = "1";
    private String cmbAutoRun = "0";
    private String formActions = "search";
    
    public String getFormActions()
    {
        return formActions;
    }
    public void setFormActions(String formActions)
    {
        this.formActions = formActions;
    }
    public String getCmbAutoRun()
    {
        return cmbAutoRun;
    }
    public void setCmbAutoRun(String cmbAutoRun)
    {
        this.cmbAutoRun = cmbAutoRun;
    }
    public String getCmbCDRStatus()
    {
        return cmbCDRStatus;
    }
    public void setCmbCDRStatus(String cmbCDRStatus)
    {
        this.cmbCDRStatus = cmbCDRStatus;
    }
    public String getTxtCustomerId()
    {
        return txtCustomerId;
    }
    public void setTxtCustomerId(String txtCustomerId)
    {
        this.txtCustomerId = txtCustomerId;
    }
}
