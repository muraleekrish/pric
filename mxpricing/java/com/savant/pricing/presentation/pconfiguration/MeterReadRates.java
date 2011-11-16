/*
 * Created on Mar 28, 2007
 *
 * ClassName	:  	MeterReadRates.java
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

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MeterReadRates extends ActionForm
{

    private String sortOrder="ascending";
	private String sortField="";
	private String search="";
	private String maxItems="10";
	private String page="1";
	private String pageTop ="0";
	private String formActions = "list";
	private String cmbTDSP = "1";
	private String cmbMeterCycle = "0";
	private String slctYear = "";
	private String formAction = "";
	private String startPosition ="1";
	
	
	
	
	
    
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
    public String getSearch()
    {
        return search;
    }
    public void setSearch(String search)
    {
        this.search = search;
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
    
    public String getCmbMeterCycle()
    {
        return cmbMeterCycle;
    }
    public void setCmbMeterCycle(String cmbMeterCycle)
    {
        this.cmbMeterCycle = cmbMeterCycle;
    }
    public String getCmbTDSP()
    {
        return cmbTDSP;
    }
    public void setCmbTDSP(String cmbTDSP)
    {
        this.cmbTDSP = cmbTDSP;
    }
    public String getSlctYear()
    {
        return slctYear;
    }
    public void setSlctYear(String slctYear)
    {
        this.slctYear = slctYear;
    }
    
    public String getFormAction()
    {
        return formAction;
    }
    public void setFormAction(String formAction)
    {
        this.formAction = formAction;
    }
    public String getStartPosition()
    {
        return startPosition;
    }
    public void setStartPosition(String startPosition)
    {
        this.startPosition = startPosition;
    }
}

/*
*$Log: MeterReadRates.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/04/19 13:24:22  jnadesan
*default assign  value changed
*
*Revision 1.3  2007/03/29 11:24:29  rraman
*default tdsp value set to 1
*
*Revision 1.2  2007/03/29 10:11:52  rraman
*new form and action created for meterreadrate configuration page
*
*Revision 1.1  2007/03/28 13:57:33  rraman
*new form and action created for meterreadrates page
*
*
*/