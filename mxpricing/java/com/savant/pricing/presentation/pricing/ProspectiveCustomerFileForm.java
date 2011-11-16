/*
 * Created on Aug 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author rraman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProspectiveCustomerFileForm extends ActionForm{

	private String cmbFileTypes = "0";
	private String custId = "";
    private String fileTypeId;
    private FormFile theFile;
    private String fileName ="";
    private String formActions;
	private String submit1;
	private String txtFileName;
	private String desc;
	
    public String getDesc()
    {
        return desc;
    }
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public FormFile getTheFile() {
		return theFile;
	}
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	public String getTxtFileName() {
		return txtFileName;
	}
	public void setTxtFileName(String txtFileName) {
		this.txtFileName = txtFileName;
	}
	public String getCmbFileTypes() {
		return cmbFileTypes;
	}
	public void setCmbFileTypes(String cmbFileTypes) {
		this.cmbFileTypes = cmbFileTypes;
	}
	
	public String getFileTypeId() {
		return fileTypeId;
	}
	public void setFileTypeId(String fileTypeId) {
		this.fileTypeId = fileTypeId;
	}
	public String getFormActions() {
		return formActions;
	}
	public void setFormActions(String formActions) {
		this.formActions = formActions;
	}
	public String getSubmit1() {
		return submit1;
	}
	public void setSubmit1(String submit1) {
		this.submit1 = submit1;
	}
}
