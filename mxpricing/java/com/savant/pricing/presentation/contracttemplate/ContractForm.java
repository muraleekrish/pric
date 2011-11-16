/*
 * Created on Apr 8, 2007
 * 
 * Class Name ContractForm.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.contracttemplate;

import org.apache.struts.action.ActionForm;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractForm extends ActionForm{
    
    private int report = 0;
    private int reportParam = 0;
    private String formAction = "";
    private String paramValue = "";

   
    public String getFormAction() {
        return formAction;
    }
    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }
    public String getParamValue() {
        return paramValue;
    }
    public int getReport() {
        return report;
    }
    public int getReportParam() {
        return reportParam;
    }
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
    public void setReport(int report) {
        this.report = report;
    }
    public void setReportParam(int reportParam) {
        this.reportParam = reportParam;
    }
}


/*
*$Log: ContractForm.java,v $
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:30  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/08 15:10:18  jnadesan
*initial commit for contract template
*
*
*/
