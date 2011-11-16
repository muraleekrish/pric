/*
 * Created on Apr 8, 2007
 *
 * ClassName	:  	SeasonListForm.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.global.calendar;

import org.apache.struts.action.ActionForm;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SeasonListForm extends ActionForm
{

    private String startPosition ="1";
    private String sortOrder="ascending";
	private String sortField="seasonName";
	private String maxItems="10";
	private String page="1";
	private String pageTop ="0";
	private String formActions = "list";
	private String txtSeason="";
	private String searchSeason="0";

    public String getFormActions()
    {
        return formActions;
    }
    public void setFormActions(String formActions)
    {
        this.formActions = formActions;
    }
    public String getMaxItems()
    {
        return maxItems;
    }
    public void setMaxItems(String maxItems)
    {
        this.maxItems = maxItems;
    }
    public String getPage()
    {
        return page;
    }
    public void setPage(String page)
    {
        this.page = page;
    }
    public String getPageTop()
    {
        return pageTop;
    }
    public void setPageTop(String pageTop)
    {
        this.pageTop = pageTop;
    }
    public String getSearchSeason()
    {
        return searchSeason;
    }
    public void setSearchSeason(String searchSeason)
    {
        this.searchSeason = searchSeason;
    }
    public String getSortField()
    {
        return sortField;
    }
    public void setSortField(String sortField)
    {
        this.sortField = sortField;
    }
    public String getSortOrder()
    {
        return sortOrder;
    }
    public void setSortOrder(String sortOrder)
    {
        this.sortOrder = sortOrder;
    }
    public String getStartPosition()
    {
        return startPosition;
    }
    public void setStartPosition(String startPosition)
    {
        this.startPosition = startPosition;
    }
    public String getTxtSeason()
    {
        return txtSeason;
    }
    public void setTxtSeason(String txtSeason)
    {
        this.txtSeason = txtSeason;
    }
}

/*
*$Log: SeasonListForm.java,v $
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:59  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:32  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/08 14:45:50  rraman
*initial commit for season
*
*
*/