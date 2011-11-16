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
public class MeterTypeForm extends ActionForm {

    
    
	private String startPosition ="1";
    private String sortOrder="ascending";
	private String sortField="MeterType";
	private String maxItems="10";
	private String page="1";
	private String pageTop ="0";
	private String formActions = "list";
	private String txtMeterType="";
	private String searchMeterType="";
	private String meterId = "";
	
	
	
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
    public String getSearchMeterType() {
        return searchMeterType;
    }
    public void setSearchMeterType(String searchMeterType) {
        this.searchMeterType = searchMeterType;
    }
    public String getTxtMeterType() {
        return txtMeterType;
    }
    public void setTxtMeterType(String txtMeterType) {
        this.txtMeterType = txtMeterType;
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
     * @return Returns the meterId.
     */
    public String getMeterId() {
        return meterId;
    }
    /**
     * @param meterId The meterId to set.
     */
    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }
}
