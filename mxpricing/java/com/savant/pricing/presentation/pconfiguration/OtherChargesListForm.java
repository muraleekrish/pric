/*
 * Created on May 7, 2007
 *
 * ClassName	:  	OtherChargesListForm.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pconfiguration;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OtherChargesListForm extends ActionForm
{
    private String sortOrder="ascending";
    private String mnthYearOrder = "ascending";
	private String sortField="";
	private String search="";
	private String maxItems="10";
	private String page="1";
	private String pageTop ="0";
	private String formAction = "List";
	private String startPosition ="1";
	private String cmbEnergyCharge = "0";
	private String cmbCongestionZone = "0";
	private String cmbProfile = "0";
	private String slctYear = "";
	private FormFile othrBrowse ;  
	
	
	
    public FormFile getOthrBrowse()
    {
        return othrBrowse;
    }
    public void setOthrBrowse(FormFile othrBrowse)
    {
        this.othrBrowse = othrBrowse;
    }
    /**
     * @return Returns the cmbProfile.
     */
    public String getCmbProfile()
    { 
        return cmbProfile;
    }
    public String getCmbEnergyCharge() {
        return cmbEnergyCharge;
    }
    public void setCmbEnergyCharge(String cmbEnergyCharge) {
        this.cmbEnergyCharge = cmbEnergyCharge;
    }
    /**
     * @param cmbProfile The cmbProfile to set.
     */
    public void setCmbProfile(String cmbProfile)
    {
        this.cmbProfile = cmbProfile;
    }
    /**
     * @return Returns the cmbWeatherZone.
     */
    
    public String getFormAction()
    {
        return formAction;
    }
    /**
     * @param formAction The formAction to set.
     */
    public void setFormAction(String formAction)
    {
        this.formAction = formAction;
    }
    /**
     * @return Returns the maxItems.
     */
    public String getMaxItems()
    {
        return maxItems;
    }
    /**
     * @param maxItems The maxItems to set.
     */
    public void setMaxItems(String maxItems)
    {
        this.maxItems = maxItems;
    }
    /**
     * @return Returns the page.
     */
    public String getPage()
    {
        return page;
    }
    /**
     * @param page The page to set.
     */
    public void setPage(String page)
    {
        this.page = page;
    }
    /**
     * @return Returns the pageTop.
     */
    public String getPageTop()
    {
        return pageTop;
    }
    /**
     * @param pageTop The pageTop to set.
     */
    public void setPageTop(String pageTop)
    {
        this.pageTop = pageTop;
    }
    /**
     * @return Returns the search.
     */
    public String getSearch()
    {
        return search;
    }
    /**
     * @param search The search to set.
     */
    public void setSearch(String search)
    {
        this.search = search;
    }
    /**
     * @return Returns the slctYear.
     */
    public String getSlctYear()
    {
        return slctYear;
    }
    /**
     * @param slctYear The slctYear to set.
     */
    public void setSlctYear(String slctYear)
    {
        this.slctYear = slctYear;
    }
    /**
     * @return Returns the sortField.
     */
    public String getSortField()
    {
        return sortField;
    }
    /**
     * @param sortField The sortField to set.
     */
    public void setSortField(String sortField)
    {
        this.sortField = sortField;
    }
    /**
     * @return Returns the sortOrder.
     */
    public String getSortOrder()
    {
        return sortOrder;
    }
    /**
     * @param sortOrder The sortOrder to set.
     */
    public void setSortOrder(String sortOrder)
    {
        this.sortOrder = sortOrder;
    }
    /**
     * @return Returns the startPosition.
     */
    public String getStartPosition()
    {
        return startPosition;
    }
    /**
     * @param startPosition The startPosition to set.
     */
    public void setStartPosition(String startPosition)
    {
        this.startPosition = startPosition;
    }
    public String getMnthYearOrder() {
        return mnthYearOrder;
    }
    public void setMnthYearOrder(String mnthYearOrder) {
        this.mnthYearOrder = mnthYearOrder;
    }
    public String getCmbCongestionZone() {
        return cmbCongestionZone;
    }
    public void setCmbCongestionZone(String cmbCongestionZone) {
        this.cmbCongestionZone = cmbCongestionZone;
    }
}

/*
*$Log: OtherChargesListForm.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/23 05:29:12  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/07/11 13:21:28  jnadesan
*energy chargerates add/update provision given
*
*Revision 1.2  2007/06/11 13:11:33  jnadesan
*formActions initial value chaged
*
*Revision 1.1  2007/06/07 11:03:18  spandiyarajan
*other charges partially committed
*
*
*/