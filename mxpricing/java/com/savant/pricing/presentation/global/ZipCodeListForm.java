/*
 * Created on Jan 27, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.global;

import org.apache.struts.action.ActionForm;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ZipCodeListForm extends ActionForm {

    private String formActions="";
    private String congestionZone="0";
    private String weatherZone="0";
    private String sortOrder="ascending";
	private String sortField="";
	private String search="";
	private String maxItems="10";
	private String show="";
	private String page="1";
	private String txtZipCode="";
	private String startPosition="1";
	private String pageTop="1";
	private String zipId = "";
	
    public String getPageTop() {
        return pageTop;
    }
    public void setPageTop(String pageTop) {
        this.pageTop = pageTop;
    }
    public String getStartPosition() {
        return startPosition;
    }
    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }
    public String getTxtZipCode() {
        return txtZipCode;
    }
    public void setTxtZipCode(String txtZipCode) {
        this.txtZipCode = txtZipCode;
    }
    public String getCongestionZone() {
        return congestionZone;
    }
    public String getFormActions() {
        return formActions;
    }
    public String getMaxItems() {
        return maxItems;
    }
    public String getPage() {
        return page;
    }
    public String getSearch() {
        return search;
    }
    public String getShow() {
        return show;
    }
    public String getSortField() {
        return sortField;
    }
    public String getSortOrder() {
        return sortOrder;
    }
    public String getWeatherZone() {
        return weatherZone;
    }
    public void setCongestionZone(String congestionZone) {
        this.congestionZone = congestionZone;
    }
    public void setFormActions(String formActions) {
        this.formActions = formActions;
    }
    public void setMaxItems(String maxItems) {
        this.maxItems = maxItems;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public void setSearch(String search) {
        this.search = search;
    }
    public void setShow(String show) {
        this.show = show;
    }
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    public void setWeatherZone(String weatherZone) {
        this.weatherZone = weatherZone;
    }
    
    /**
     * @return Returns the zipId.
     */
    public String getZipId() 
    {
        return zipId;
    }
    /**
     * @param zipId The zipId to set.
     */
    public void setZipId( String zipId ) 
    {
        this.zipId = zipId;
    }
}
