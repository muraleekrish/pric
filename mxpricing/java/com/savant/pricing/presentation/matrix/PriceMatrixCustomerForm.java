/*
 * 
 * PriceMatrixCustomerForm.java    Aug 28, 2007
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

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * 
 */
public class PriceMatrixCustomerForm extends ActionForm
{
    private String formActions = "list";
    private String maxItems="10";
    private String page="1";
    private String custName="";
    private String searchCustName="0";
    private String refNo="";
    private String searchrefNo="";
    private String fromDate = "";
    private String toDate="";
    private String searchrunDate="";
    private String rep="0";
    private String manager="0";
    private String referNum="";
    
    public String getCustName()
    {
        return custName;
    }
    public String getFormActions()
    {
        return formActions;
    }
    public String getManager()
    {
        return manager;
    }
    public String getMaxItems()
    {
        return maxItems;
    }
    public String getPage()
    {
        return page;
    }
    public String getRefNo()
    {
        return refNo;
    }
    public String getRep()
    {
        return rep;
    }
    
    public String getSearchCustName()
    {
        return searchCustName;
    }
    public String getSearchrefNo()
    {
        return searchrefNo;
    }
    public String getSearchrunDate()
    {
        return searchrunDate;
    }
    public void setCustName(String custName)
    {
        this.custName = custName;
    }
    public void setFormActions(String formActions)
    {
        this.formActions = formActions;
    }
    public void setManager(String manager)
    {
        this.manager = manager;
    }
    public void setMaxItems(String maxItems)
    {
        this.maxItems = maxItems;
    }
    public void setPage(String page)
    {
        this.page = page;
    }
    public void setRefNo(String refNo)
    {
        this.refNo = refNo;
    }
    public void setRep(String rep)
    {
        this.rep = rep;
    }
    public String getFromDate()
    {
        return fromDate;
    }
    public String getToDate()
    {
        return toDate;
    }
    public void setFromDate(String fromDate)
    {
        this.fromDate = fromDate;
    }
    public void setToDate(String toDate)
    {
        this.toDate = toDate;
    }
    public void setSearchCustName(String searchCustName)
    {
        this.searchCustName = searchCustName;
    }
    public void setSearchrefNo(String searchrefNo)
    {
        this.searchrefNo = searchrefNo;
    }
    public void setSearchrunDate(String searchrunDate)
    {
        this.searchrunDate = searchrunDate;
    }
    public String getReferNum()
    {
        return referNum;
    }
    public void setReferNum(String referNum)
    {
        this.referNum = referNum;
    }
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors actionErrors = new ActionErrors();
        if(formActions.equalsIgnoreCase("search"))
        {
            if(searchrunDate.equalsIgnoreCase("between"))
            {
                if(fromDate !=null || toDate != null)
                {
	                if(fromDate==null || fromDate.trim().length()<1 || fromDate.trim().equals(""))
	    	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Start Date"));
	                
	                if(toDate==null || toDate.trim().length()<1 || toDate.trim().equals(""))
	    	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","End Date"));
                }
            }
        }
        
        return actionErrors;
    }
}


/*
*$Log: PriceMatrixCustomerForm.java,v $
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
*Revision 1.4  2007/09/06 12:01:59  spandiyarajan
*validate the fromdate and todate for run date as between
*
*Revision 1.3  2007/08/31 06:11:54  spandiyarajan
*dekete option added in Customer Files list page
*
*Revision 1.2  2007/08/30 12:37:50  spandiyarajan
*changed for MM Pricing - Customer Files list page
*
*Revision 1.1  2007/08/29 05:47:01  jnadesan
*initial commit
*
*
*/