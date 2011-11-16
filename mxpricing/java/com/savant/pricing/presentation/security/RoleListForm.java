/*
 * Created on Apr 7, 2007
 *
 * ClassName	:  	RoleListForm.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.security;

import org.apache.struts.action.ActionForm;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RoleListForm extends ActionForm
{

    private String txtRoleName="";
    private String selectRoleName="0";
    private String formAction="list";
    
    private String sortOrder="ascending";
    private String sortField="userId";    
    private String pageTop ="0";    
    private String maxItems="10";
    private String page="1";
    /**
     * @return Returns the formAction.
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
     * @return Returns the selectRoleName.
     */
    public String getSelectRoleName()
    {
        return selectRoleName;
    }
    /**
     * @param selectRoleName The selectRoleName to set.
     */
    public void setSelectRoleName(String selectRoleName)
    {
        this.selectRoleName = selectRoleName;
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
     * @return Returns the txtRoleName.
     */
    public String getTxtRoleName()
    {
        return txtRoleName;
    }
    /**
     * @param txtRoleName The txtRoleName to set.
     */
    public void setTxtRoleName(String txtRoleName)
    {
        this.txtRoleName = txtRoleName;
    }
}

/*
*$Log: RoleListForm.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/07 11:28:46  spandiyarajan
*committed for role
*
*
*/