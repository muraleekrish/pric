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
public class TDSPListForm extends ActionForm {

    
    
	private String startPosition ="1";
    private String sortOrder="ascending";
	private String sortField="";
	private String maxItems="10";
	private String page="1";
	private String pageTop ="0";
	private String formActions = "list";
	private String txtESIIDPrefix ="";
	private String txtTDSP="";
	private String searchTDSP="";
	private String searchESIIDPrefix = ""; 
	private String searchESIID = "";
	private String tdspId = "";
	
	
	
	public String getSearchESIID() {
		return searchESIID;
	}
	public void setSearchESIID(String searchESIID) {
		this.searchESIID = searchESIID;
	}
    public String getSearchESIIDPrefix()
    {
        return searchESIIDPrefix;
    }
    public void setSearchESIIDPrefix(String searchESIIDPrefix)
    {
        this.searchESIIDPrefix = searchESIIDPrefix;
    }
    public String getSearchTDSP() {
        return searchTDSP;
    }
    public void setSearchTDSP(String searchTDSP) {
        this.searchTDSP = searchTDSP;
    }
    public String getTxtESIIDPrefix() {
        return txtESIIDPrefix;
    }
    public void setTxtESIIDPrefix(String txtESIIDPrefix) {
        this.txtESIIDPrefix = txtESIIDPrefix;
    }
    public String getTxtTDSP() {
        return txtTDSP;
    }
    public void setTxtTDSP(String txtTDSP) {
        this.txtTDSP = txtTDSP;
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
     * @return Returns the tdspId.
     */
    public String getTdspId() {
        return tdspId;
    }
    /**
     * @param tdspId The tdspId to set.
     */
    public void setTdspId(String tdspId) {
        this.tdspId = tdspId;
    }
}
