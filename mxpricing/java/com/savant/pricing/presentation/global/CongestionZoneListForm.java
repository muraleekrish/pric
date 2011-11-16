/*
 * Created on Jan 30, 2007
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
public class CongestionZoneListForm extends ActionForm {

    
    
	private String startPosition ="1";
	private String congId =""; 
	private String congestionZone="";
    private String description="";
    private String sortOrder="ascending";
	private String sortField="";
	private String search="";
	private String maxItems="10";
	private String page="1";
	private String pageTop ="0";
	private String formActions = "list";
	private String searchZone="";
	
    
    /**
     * @return Returns the congId.
     */
    public String getCongId() {
        return congId;
    }
    /**
     * @param congId The congId to set.
     */
    public void setCongId(String congId) {
        this.congId = congId;
    }
    public String getSearchZone() {
        return searchZone;
    }
    public void setSearchZone(String searchZone) {
        this.searchZone = searchZone;
    }
    public String getDescription() {
        return description;
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
    public String getPageTop() {
        return pageTop;
    }
    public String getSearch() {
        return search;
    }
    public String getSortField() {
        return sortField;
    }
    public String getSortOrder() {
        return sortOrder;
    }
    
    public String getCongestionZone() {
        return congestionZone;
    }
    public void setCongestionZone(String congestionZone) {
        this.congestionZone = congestionZone;
    }
    public void setDescription(String description) {
        this.description = description;
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
    public void setPageTop(String pageTop) {
        this.pageTop = pageTop;
    }
    public void setSearch(String search) {
        this.search = search;
    }
    public void setSortField(String sortField) {
        this.sortField = sortField;
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
	
}
