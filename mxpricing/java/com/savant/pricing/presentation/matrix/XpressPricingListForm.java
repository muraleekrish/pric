/*
 * 
 * XpressPricingListForm.java    Aug 23, 2007
 * 
 * Copyright (c) Savant Technologies Pvt Ltd.
 * 'Savant House' 127 Chamiers Road, Nandanam, Chennai-600 035. INDIA 
 * All rights reserved.

 * @author: jnadesan
 * @company: Savant Technologies
 * @client: MX Energy
 * @version: 
 * @Description: 
 * 
*/

package com.savant.pricing.presentation.matrix;

import org.apache.struts.action.ActionForm;

/**
 * 
 */
public class XpressPricingListForm extends ActionForm
{
    private String formAction="list";
    private String pageTop ="0";    
    private String maxItems="10";
    private String page="1";
    private String fromDate = "";
    private String toDate = "";
    private String searchRunDate="";
    private String searchStatus = "";
    private String expDate = "";
    private String offerDate = "";
    
    public String getExpDate()
    {
        return expDate;
    }
    public String getFromDate()
    {
        return fromDate;
    }
    public String getOfferDate()
    {
        return offerDate;
    }
    public String getSearchRunDate()
    {
        return searchRunDate;
    }
    public String getSearchStatus()
    {
        return searchStatus;
    }
    public String getToDate()
    {
        return toDate;
    }
    public void setExpDate(String expDate)
    {
        this.expDate = expDate;
    }
    public void setFromDate(String fromDate)
    {
        this.fromDate = fromDate;
    }
    public void setOfferDate(String offerDate)
    {
        this.offerDate = offerDate;
    }
    public void setSearchRunDate(String searchRunDate)
    {
        this.searchRunDate = searchRunDate;
    }
    public void setSearchStatus(String searchStatus)
    {
        this.searchStatus = searchStatus;
    }
    public void setToDate(String toDate)
    {
        this.toDate = toDate;
    }
    public String getFormAction()
    {
        return formAction;
    }
    public String getMaxItems()
    {
        return maxItems;
    }
    public String getPage()
    {
        return page;
    }
    public String getPageTop()
    {
        return pageTop;
    }
    public void setFormAction(String formAction)
    {
        this.formAction = formAction;
    }
    public void setMaxItems(String maxItems)
    {
        this.maxItems = maxItems;
    }
    public void setPage(String page)
    {
        this.page = page;
    }
    public void setPageTop(String pageTop)
    {
        this.pageTop = pageTop;
    }
}


/*
*$Log: XpressPricingListForm.java,v $
*Revision 1.2  2008/11/21 09:47:23  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/10/05 11:14:59  jnadesan
*imports organized
*
*Revision 1.3  2007/09/20 11:46:33  spandiyarajan
*removed the throwing error like fromdate/todate required
*
*Revision 1.2  2007/09/06 12:01:59  spandiyarajan
*validate the fromdate and todate for run date as between
*
*Revision 1.1  2007/08/23 14:33:06  jnadesan
*XpressPricing form action added
*
*
*/