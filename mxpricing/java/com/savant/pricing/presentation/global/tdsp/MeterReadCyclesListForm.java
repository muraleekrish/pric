/*
 * Created on Apr 9, 2007
 *
 * ClassName	:  	MeterReadCyclesListForm.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.global.tdsp;

import org.apache.struts.action.ActionForm;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MeterReadCyclesListForm  extends ActionForm
{
    private String startPosition ="1";
    private String sortOrder="ascending";
	private String sortField="tdspName";
	private String maxItems="10";
	private String page="1";
	private String pageTop ="0";
	private String formActions = "list";
	private String txtTDSP="";
	private String searchTDSP="0";
    /**
     * @return Returns the formActions.
     */
    public String getFormActions()
    {
        return formActions;
    }
    /**
     * @param formActions The formActions to set.
     */
    public void setFormActions(String formActions)
    {
        this.formActions = formActions;
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
     * @return Returns the searchTDSP.
     */
    public String getSearchTDSP()
    {
        return searchTDSP;
    }
    /**
     * @param searchTDSP The searchTDSP to set.
     */
    public void setSearchTDSP(String searchTDSP)
    {
        this.searchTDSP = searchTDSP;
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
    /**
     * @return Returns the txtTDSP.
     */
    public String getTxtTDSP()
    {
        return txtTDSP;
    }
    /**
     * @param txtTDSP The txtTDSP to set.
     */
    public void setTxtTDSP(String txtTDSP)
    {
        this.txtTDSP = txtTDSP;
    }
}

/*
*$Log: MeterReadCyclesListForm.java,v $
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:54  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:24  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/09 15:11:10  spandiyarajan
*meterreadcycles initially commited
*
*
*/