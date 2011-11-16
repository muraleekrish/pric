/*
 * Created on Feb 2, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.global.tdsp;

import org.apache.struts.action.ActionForm;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RateCodeAssociationForm extends ActionForm 
{
    private String startPosition ="1";
    private String sortOrder="ascending";
	private String sortField="rateCode";
	private String maxItems="10";
	private String page="1";
	private String pageTop ="0";
	private String formActions = "list";
	private String txtRateCode="";
	private String searchRateCode="0";
	private String txtRateClass="";
	private String searchRateClass="0";
	private String searchTDSP="0";
	
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
    public String getStartPosition() {
        return startPosition;
    }
    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }
    public String getSearchRateCode()
    {
        return searchRateCode;
    }
    public void setSearchRateCode(String searchRateCode)
    {
        this.searchRateCode = searchRateCode;
    }
    public String getSearchTDSP()
    {
        return searchTDSP;
    }
    public void setSearchTDSP(String searchTDSP)
    {
        this.searchTDSP = searchTDSP;
    }
    public String getTxtRateClass()
    {
        return txtRateClass;
    }
    public void setTxtRateClass(String txtRateClass)
    {
        this.txtRateClass = txtRateClass;
    }
    public String getTxtRateCode()
    {
        return txtRateCode;
    }
    public void setTxtRateCode(String txtRateCode)
    {
        this.txtRateCode = txtRateCode;
    }
    public String getSearchRateClass()
    {
        return searchRateClass;
    }
    public void setSearchRateClass(String searchRateClass)
    {
        this.searchRateClass = searchRateClass;
    }
}
