/*
 * Created on Apr 6, 2007
 *
 * ClassName	:  	UserListForm.java
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
public class UserListForm extends ActionForm
{   
    private String txtUsreId = "";
    private String selectUsreId = "0";
    private String selectUserType = "0";
    private String selectParentUserType = "";    
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
     * @return Returns the selectParentUserType.
     */
    public String getSelectParentUserType()
    {
        return selectParentUserType;
    }
    /**
     * @param selectParentUserType The selectParentUserType to set.
     */
    public void setSelectParentUserType(String selectParentUserType)
    {
        this.selectParentUserType = selectParentUserType;
    }
    /**
     * @return Returns the selectUsreId.
     */
    public String getSelectUsreId()
    {
        return selectUsreId;
    }
    /**
     * @param selectUsreId The selectUsreId to set.
     */
    public void setSelectUsreId(String selectUsreId)
    {
        this.selectUsreId = selectUsreId;
    }    
    /**
     * @return Returns the selectUserType.
     */
    public String getSelectUserType()
    {
        return selectUserType;
    }
    /**
     * @param selectUserType The selectUserType to set.
     */
    public void setSelectUserType(String selectUserType)
    {
        this.selectUserType = selectUserType;
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
     * @return Returns the txtUsreId.
     */
    public String getTxtUsreId()
    {
        return txtUsreId;
    }
    /**
     * @param txtUsreId The txtUsreId to set.
     */
    public void setTxtUsreId(String txtUsreId)
    {
        this.txtUsreId = txtUsreId;
    }
}

/*
*$Log: UserListForm.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/07/31 11:55:33  kduraisamy
*unwanted imports removed.
*
*Revision 1.3  2007/07/17 14:02:32  sramasamy
*error fixed in user add modify update
*
*Revision 1.2  2007/04/06 15:44:48  spandiyarajan
*user bug fixed
*
*Revision 1.1  2007/04/06 13:33:26  spandiyarajan
*fix the  bug in user
*
*
*/