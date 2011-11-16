/*
 * Created on Mar 28, 2007
 * 
 * Class Name ContactListForm.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.contract;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts.action.ActionForm;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContactListForm extends ActionForm{
    
    private String txtCustomerName="";
    private String searchCustomerName="0";
    private String txtSalesName="";
    private String searchSalesName="0";
    private String sortField="customerName";
    private String sortOrder="ascending";
    private String pageTop ="0";
    private String formActions = "list";
    private String maxItems="10";
    private String page="1";
    private String startPosition ="1";
    private String searchDate = "0";
    private String txtDateFrom =""; 
    private String txtDateTo ="";
    private String txtCPEStratDate = "";
    private String txtCPEExpireDate = "";
    private String txtExpireTime = "03:00";
    private String searchTimeZone = "1";
    private String txtCMSId = "";
    
    public String getTxtCMSId()
    {
        return txtCMSId;
    }
    public void setTxtCMSId(String txtCMSId)
    {
        this.txtCMSId = txtCMSId;
    }
    public String getSearchTimeZone() {
        return searchTimeZone;
    }
    public String getTxtExpireTime() {
        return txtExpireTime;
    }
    public void setSearchTimeZone(String searchTimeZone) {
        this.searchTimeZone = searchTimeZone;
    }
    public void setTxtExpireTime(String txtExpireTime) {
        this.txtExpireTime = txtExpireTime;
    }
    public ContactListForm()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date objdate = new Date();
        this.txtDateFrom = sdf.format(new Date(objdate.getYear(),objdate.getMonth(),objdate.getDate()-2,0,0,0));
        this.txtDateTo = sdf.format(new Date(objdate.getYear(),objdate.getMonth(),objdate.getDate(),0,0,0));
        this.txtCPEStratDate = sdf.format(new Date(objdate.getYear(),objdate.getMonth(),objdate.getDate(),0,0,0));
        this.txtCPEExpireDate = sdf.format(new Date(objdate.getYear(),objdate.getMonth(),objdate.getDate(),0,0,0));
    }
  
    
    public String getTxtCPEExpireDate() {
        return txtCPEExpireDate;
    }
    public String getTxtCPEStratDate() {
        return txtCPEStratDate;
    }
    public void setTxtCPEExpireDate(String txtCPEExpireDate) {
        this.txtCPEExpireDate = txtCPEExpireDate;
    }
    public void setTxtCPEStratDate(String txtCPEStratDate) {
        this.txtCPEStratDate = txtCPEStratDate;
    }
    public String getTxtDateTo() {
        return txtDateTo;
    }
    public void setTxtDateTo(String txtDateTo) {
        this.txtDateTo = txtDateTo;
    }
    public String getSearchDate() {
        return searchDate;
    }
    
    public String getTxtDateFrom() {
        return txtDateFrom;
    }
    public void setTxtDateFrom(String txtDateFrom) {
        this.txtDateFrom = txtDateFrom;
    }
    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
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
    public String getSearchCustomerName() {
        return searchCustomerName;
    }
    public String getSearchSalesName() {
        return searchSalesName;
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
    public String getTxtCustomerName() {
        return txtCustomerName;
    }
    public String getTxtSalesName() {
        return txtSalesName;
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
    public void setSearchCustomerName(String searchCustomerName) {
        this.searchCustomerName = searchCustomerName;
    }
    public void setSearchSalesName(String searchSalesName) {
        this.searchSalesName = searchSalesName;
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
    public void setTxtCustomerName(String txtCustomerName) {
        this.txtCustomerName = txtCustomerName;
    }
    public void setTxtSalesName(String txtSalesName) {
        this.txtSalesName = txtSalesName;
    }
    }


/*
*$Log: ContactListForm.java,v $
*Revision 1.2  2008/02/14 05:44:46  tannamalai
*pagination done for price quote page
*
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.10  2007/07/30 09:21:55  spandiyarajan
*in cpe page added cms id filter and data ordered by customer name
*
*Revision 1.9  2007/06/12 12:57:37  spandiyarajan
*removed unwanted s.o.p
*
*Revision 1.8  2007/05/15 05:20:15  jnadesan
*initial selection changed
*
*Revision 1.7  2007/05/14 10:21:36  jnadesan
*two variables for time formats
*
*Revision 1.6  2007/04/16 13:23:19  jnadesan
*contrcat will be shown for past two days
*
*Revision 1.5  2007/04/12 13:58:18  kduraisamy
*unwanted println commented.
*
*Revision 1.4  2007/04/12 09:03:53  jnadesan
*contract start date and exp date cofig option added
*
*Revision 1.3  2007/04/02 16:28:46  jnadesan
*Contract page
*
*Revision 1.2  2007/03/29 10:39:43  jnadesan
*Date format validated
*
*Revision 1.1  2007/03/29 06:39:13  jnadesan
*Contact page added
*
*
*/