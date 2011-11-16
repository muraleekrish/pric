/*
 * Created on Mar 23, 2007
 * 
 * Class Name PcDealLeversListForm.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pconfig.deallevers;

import org.apache.struts.action.ActionForm;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PcDealLeversListForm extends ActionForm{

    private String formAction="list";
    private float value = 0;
    private String unit = "";
    private String dealLever = "";
    private String dealLeverId = "";
    private String startPosition ="1";
    private String sortOrder="ascending";
	private String sortField="";
	private String search="";
	private String maxItems="10";
	private String page="1";
	private String pageTop ="0";
	private String cmbLevers="0";
	private String txtLevers="";
    
    
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
    public String getStartPosition() {
        return startPosition;
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
    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }
    public String getDealLever() {
        return dealLever;
    }
    
    public String getFormAction() {
        return formAction;
    }
    public String getUnit() {
        return unit;
    }
    public float getValue() {
        return value;
    }
    public void setDealLever(String dealLever) {
        this.dealLever = dealLever;
    }
     
    public String getDealLeverId() {
        return dealLeverId;
    }
    public void setDealLeverId(String dealLeverId) {
        this.dealLeverId = dealLeverId;
    }
    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public void setValue(float value) {
        this.value = value;
    }
    
    public String getCmbLevers()
    {
        return cmbLevers;
    }
    public void setCmbLevers(String cmbLevers)
    {
        this.cmbLevers = cmbLevers;
    }
    public String getTxtLevers()
    {
        return txtLevers;
    }
    public void setTxtLevers(String txtLevers)
    {
        this.txtLevers = txtLevers;
    }
}


/*
*$Log: PcDealLeversListForm.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:30  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/04/02 11:18:20  jnadesan
*deallevers added
*
*Revision 1.2  2007/03/28 13:56:52  rraman
*new properties added
*
*Revision 1.1  2007/03/23 11:20:39  jnadesan
*System DealLevers added
*
*
*/