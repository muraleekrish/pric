/*
 * Created on Feb 19, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.tdp;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author Karthikeyan Chellamuthu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IDRProfileForm extends ActionForm
{
    private String formAction = "";
    private String txtEsiid = "";
    private String rdoImportType = "";
    private String rdoTdsp = "CenterPoint";
    private String message = "";
    private FormFile file;

    
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    public FormFile getFile()
    {
        return file;
    }
    public void setFile(FormFile file)
    {
        this.file = file;
    }
    public String getFormAction()
    {
        return formAction;
    }
    public void setFormAction(String formAction)
    {
        this.formAction = formAction;
    }
    public String getRdoImportType()
    {
        return rdoImportType;
    }
    public void setRdoImportType(String rdoImportType)
    {
        this.rdoImportType = rdoImportType;
    }
    public String getRdoTdsp()
    {
        return rdoTdsp;
    }
    public void setRdoTdsp(String rdoTdsp)
    {
        this.rdoTdsp = rdoTdsp;
    }
    public String getTxtEsiid()
    {
        return txtEsiid;
    }
    public void setTxtEsiid(String txtEsiid)
    {
        this.txtEsiid = txtEsiid;
    }
}
