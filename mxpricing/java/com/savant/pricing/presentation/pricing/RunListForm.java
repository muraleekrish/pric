/*
 * Created on Jan 24, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts.action.ActionForm;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RunListForm extends ActionForm
{

    private String customerId= "1";
    private String ProspectiveCustomerId= "";    
    private String formActions = "list";
    private String pricerRunCustomerId= "";
    private String txtSalesRep = "";
    private String txtSalesManager = "";
    private String cmbSalesRep = "0";
    private String cmbPriceRunId = "0";
    private String cmbSalesManager ="0";
    private String cmbEsiId ="0";
    
    private String cmbCustName = "0";
    private String txtEsiid = "";
    private String txtCustName = "";
    private String txtPrcRunId = "";
    private String txtStartDate = "";
    private String txtEndDate = "";
    private String maxItems="10";
    private String page="1";
    SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
    
    public RunListForm()
    {
        Date startDate = new Date();
        startDate.setDate(startDate.getDate()-7);
        txtStartDate = df.format(startDate);
        txtEndDate = df.format(new Date());
    }
    
    
    
    public String getCmbEsiId()
    {
        return cmbEsiId;
    }
    public void setCmbEsiId(String cmbEsiId)
    {
        this.cmbEsiId = cmbEsiId;
    }
    public SimpleDateFormat getDf()
    {
        return df;
    }
    public void setDf(SimpleDateFormat df)
    {
        this.df = df;
    }
    public String getMaxItems()
    {
        return maxItems;
    }
    public void setMaxItems(String maxItems)
    {
        this.maxItems = maxItems;
    }
    public String getPage()
    {
        return page;
    }
    public void setPage(String page)
    {
        this.page = page;
    }
    public String getCmbPriceRunId()
    {
        return cmbPriceRunId;
    }
    public void setCmbPriceRunId(String cmbPriceRunId)
    {
        this.cmbPriceRunId = cmbPriceRunId;
    }
    public String getCmbSalesManager()
    {
        return cmbSalesManager;
    }
    public void setCmbSalesManager(String cmbSalesManager)
    {
        this.cmbSalesManager = cmbSalesManager;
    }
    public String getCmbSalesRep()
    {
        return cmbSalesRep;
    }
    public void setCmbSalesRep(String cmbSalesRep)
    {
        this.cmbSalesRep = cmbSalesRep;
    }
   
    public String getTxtEsiid()
    {
        return txtEsiid;
    }
    public void setTxtEsiid(String txtEsiid)
    {
        this.txtEsiid = txtEsiid;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getFormActions() {
        return formActions;
    }
    public void setFormActions(String formActions) {
        this.formActions = formActions;
    }
    public String getProspectiveCustomerId() {
        return ProspectiveCustomerId;
    }
    public void setProspectiveCustomerId(String prospectiveCustomerId) {
        ProspectiveCustomerId = prospectiveCustomerId;
    }
    public String getPricerRunCustomerId() {
        return pricerRunCustomerId;
    }
    public void setPricerRunCustomerId(String pricerRunCustomerId) {
        this.pricerRunCustomerId = pricerRunCustomerId;
    }
    public String getCmbCustName()
    {
        return cmbCustName;
    }
    public void setCmbCustName(String cmbCustName)
    {
        this.cmbCustName = cmbCustName;
    }
    public String getTxtCustName()
    {
        return txtCustName;
    }
    public void setTxtCustName(String txtCustName)
    {
        this.txtCustName = txtCustName;
    }
    public String getTxtEndDate()
    {
        return txtEndDate;
    }
    public void setTxtEndDate(String txtEndDate)
    {
        this.txtEndDate = txtEndDate;
    }
    public String getTxtPrcRunId()
    {
        return txtPrcRunId;
    }
    public void setTxtPrcRunId(String txtPrcRunId)
    {
        this.txtPrcRunId = txtPrcRunId;
    }
    public String getTxtSalesManager()
    {
        return txtSalesManager;
    }
    public void setTxtSalesManager(String txtSalesManager)
    {
        this.txtSalesManager = txtSalesManager;
    }
    public String getTxtSalesRep()
    {
        return txtSalesRep;
    }
    public void setTxtSalesRep(String txtSalesRep)
    {
        this.txtSalesRep = txtSalesRep;
    }
    public String getTxtStartDate()
    {
        return txtStartDate;
    }
    public void setTxtStartDate(String txtStartDate)
    {
        this.txtStartDate = txtStartDate;
    }
}
