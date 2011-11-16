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
public class ProspectiveCustomerListForm extends ActionForm
{

    private String customerId="0";
    private String ProspectiveCustomerId="";    
    private String customerName;
    private String pointofContact="";
    private String pntofCntFirstname="";
    private String pntofCntLastname="";
    private String businessType="";
    private String customerStatusId="0";
    private String statusDate="";
    private String userId="";
    private String salesRepName; 
    private String salesManager;
    private String customerStatus;
    private String pageTop ="0";
    private String formActions = "list";
    private String maxItems="10";
    private String page="1";
    private String startPosition ="1";
    private String txtCustomerName="";
    private String searchCustomerName="0";
    private String txtCustomerId="";
    private String cmbCustomerStatus="0";
    private String txtsalesRep="";
    private String searchsalesRep="0";
    private String sortField="customerName";
    private String sortOrder="ascending";
    private String txtmanagerName = ""; 
    private String searchSalesManager="0";
    private String user = "";
    private String isMMCust = "0";
    private String  createdFromDate = "";
    private String  createdToDate = "";
    private String  modifiedFromDate = "";
    private String  modifiedToDate = "";
    private int cmbAutoRun = 0;
    
    public int getCmbAutoRun()
    {
        return cmbAutoRun;
    }
    public void setCmbAutoRun(int cmbAutoRun)
    {
        this.cmbAutoRun = cmbAutoRun;
    }
    public String getUser() {
        return user;
    }
    public String getIsMMCust()
    {
        return isMMCust;
    }
    public void setIsMMCust(String isMMCust)
    {
        this.isMMCust = isMMCust;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPntofCntFirstname()
    {
        return pntofCntFirstname;
    }
    public void setPntofCntFirstname(String pntofCntFirstname)
    {
        this.pntofCntFirstname = pntofCntFirstname;
    }
    public String getPntofCntLastname()
    {
        return pntofCntLastname;
    }
    public void setPntofCntLastname(String pntofCntLastname)
    {
        this.pntofCntLastname = pntofCntLastname;
    }
    public String getSearchSalesManager() {
        return searchSalesManager;
    }
    public void setSearchSalesManager(String searchSalesManager) {
        this.searchSalesManager = searchSalesManager;
    }
    public String getTxtmanagerName() {
        return txtmanagerName;
    }
    public void setTxtmanagerName(String txtmanagerName) {
        this.txtmanagerName = txtmanagerName;
    }
    public String getSortField() {
        return sortField;
    }
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }
    public String getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    public String getRdPrsCustId() {
        return rdPrsCustId;
    }
    public void setRdPrsCustId(String rdPrsCustId) {
        this.rdPrsCustId = rdPrsCustId;
    }
    private String cmbCDRStatus ="0";
    private String rdPrsCustId="";
   
    public String getBusinessType() {
        return businessType;
    }
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
   
  
    public String getCmbCustomerStatus() {
        return cmbCustomerStatus;
    }
    public void setCmbCustomerStatus(String cmbCustomerStatus) {
        this.cmbCustomerStatus = cmbCustomerStatus;
    }
    
   
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerStatus() {
        return customerStatus;
    }
    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }
    public String getCustomerStatusId() {
        return customerStatusId;
    }
    public void setCustomerStatusId(String customerStatusId) {
        this.customerStatusId = customerStatusId;
    }
    public String getFormActions() {
        return formActions;
    }
    public void setFormActions(String formActions) {
        this.formActions = formActions;
    }
    public String getMaxItems() {
        return maxItems;
    }
    public void setMaxItems(String maxItems) {
        this.maxItems = maxItems;
    }
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public String getPageTop() {
        return pageTop;
    }
    public void setPageTop(String pageTop) {
        this.pageTop = pageTop;
    }
    public String getPointofContact() {
        return pointofContact;
    }
    public void setPointofContact(String pointofContact) {
        this.pointofContact = pointofContact;
    }
    public String getProspectiveCustomerId() {
        return ProspectiveCustomerId;
    }
    public void setProspectiveCustomerId(String prospectiveCustomerId) {
        ProspectiveCustomerId = prospectiveCustomerId;
    }
    public String getSalesManager() {
        return salesManager;
    }
    public void setSalesManager(String salesManager) {
        this.salesManager = salesManager;
    }
    public String getSalesRepName() {
        return salesRepName;
    }
    public void setSalesRepName(String salesRepName) {
        this.salesRepName = salesRepName;
    }
    public String getSearchCustomerName() {
        return searchCustomerName;
    }
    public void setSearchCustomerName(String searchCustomerName) {
        this.searchCustomerName = searchCustomerName;
    }
    public String getSearchsalesRep() {
        return searchsalesRep;
    }
    public void setSearchsalesRep(String searchsalesRep) {
        this.searchsalesRep = searchsalesRep;
    }
    public String getStartPosition() {
        return startPosition;
    }
    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }
    public String getStatusDate() {
        return statusDate;
    }
    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }
    public String getTxtCustomerId() {
        return txtCustomerId;
    }
    public void setTxtCustomerId(String txtCustomerId) {
        this.txtCustomerId = txtCustomerId;
    }
    public String getTxtCustomerName() {
        return txtCustomerName;
    }
    public void setTxtCustomerName(String txtCustomerName) {
        this.txtCustomerName = txtCustomerName;
    }
    public String getTxtsalesRep() {
        return txtsalesRep;
    }
    public void setTxtsalesRep(String txtsalesRep) {
        this.txtsalesRep = txtsalesRep;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getCmbCDRStatus() {
        return cmbCDRStatus;
    }
    public void setCmbCDRStatus(String cmbCDRStatus) {
        this.cmbCDRStatus = cmbCDRStatus;
    }
    public String getCreatedFromDate()
    {
        return createdFromDate;
    }
    public void setCreatedFromDate(String createdFromDate)
    {
        this.createdFromDate = createdFromDate;
    }
    public String getCreatedToDate()
    {
        return createdToDate;
    }
    public void setCreatedToDate(String createdToDate)
    {
        this.createdToDate = createdToDate;
    }
    public String getModifiedFromDate()
    {
        return modifiedFromDate;
    }
    public void setModifiedFromDate(String modifiedFromDate)
    {
        this.modifiedFromDate = modifiedFromDate;
    }
    public String getModifiedToDate()
    {
        return modifiedToDate;
    }
    public void setModifiedToDate(String modifiedToDate)
    {
        this.modifiedToDate = modifiedToDate;
    }
}
