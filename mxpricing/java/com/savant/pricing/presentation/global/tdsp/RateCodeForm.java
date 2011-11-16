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
public class RateCodeForm extends ActionForm {


    private String startPosition ="1";
    private String sortOrder="ascending";
	private String sortField="rateCode";
	private String maxItems="10";
	private String page="1";
	private String pageTop ="0";
	private String formActions = "list";
	private String txtRateCode="";
	private String searchRateCode="0";
	private String txtRateClassName="";
	private String searchRateClsName="0";
	
    public String getSearchRateClsName() {
        return searchRateClsName;
    }
    public void setSearchRateClsName(String searchRateClsName) {
        this.searchRateClsName = searchRateClsName;
    }
    public String getTxtRateClassName() {
        return txtRateClassName;
    }
    public void setTxtRateClassName(String txtRateClassName) {
        this.txtRateClassName = txtRateClassName;
    }
    public String getTxtRateCode() {
        return txtRateCode;
    }
    public void setTxtRateCode(String txtRateCode) {
        this.txtRateCode = txtRateCode;
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
    /**
     * @return Returns the searchRateCode.
     */
    public String getSearchRateCode()
    {
        return searchRateCode;
    }
    /**
     * @param searchRateCode The searchRateCode to set.
     */
    public void setSearchRateCode(String searchRateCode)
    {
        this.searchRateCode = searchRateCode;
    }
}
