/*
 * Created on Apr 8, 2007
 *
 * ClassName	:  	CalendarListForm.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pconfiguration;

import java.text.SimpleDateFormat;

import org.apache.struts.action.ActionForm;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CalendarListForm extends ActionForm
{
    private String startPosition ="1";
    private String sortOrder="ascending";
	private String sortField="date";
	private String maxItems="10";
	private String page="1";
	private String pageTop ="0";
	private String formActions = "list";
	private String txtReason="";
	private String searchReason="0";
	private String txtStartDate="";
	private String txtEndDate="";
	SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
	
	public CalendarListForm()
	{
	    //txtStartDate = df.format(new Date());
        //txtEndDate = df.format(new Date());
	}
    public SimpleDateFormat getDf()
    {
        return df;
    }
    public void setDf(SimpleDateFormat df)
    {
        this.df = df;
    }
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
    public String getSearchReason()
    {
        return searchReason;
    }
    public void setSearchReason(String searchReason)
    {
        this.searchReason = searchReason;
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
    public String getTxtEndDate()
    {
        return txtEndDate;
    }
    public void setTxtEndDate(String txtEndDate)
    {
        this.txtEndDate = txtEndDate;
    }
    public String getTxtReason()
    {
        return txtReason;
    }
    public void setTxtReason(String txtReason)
    {
        this.txtReason = txtReason;
    }
    public String getTxtStartDate()
    {
        return txtStartDate;
    }
    public void setTxtStartDate(String txtStartDate)
    {
        this.txtStartDate = txtStartDate;
    }
}

/*
*$Log: CalendarListForm.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/04/18 03:56:43  kduraisamy
*imports organized.
*
*Revision 1.2  2007/04/09 05:24:46  spandiyarajan
*search functionlity finished in calendar
*
*Revision 1.1  2007/04/08 16:57:34  rraman
*for holidays jsp
*
*
*/