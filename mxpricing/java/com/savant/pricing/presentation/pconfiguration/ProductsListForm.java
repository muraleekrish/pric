/*
 * Created on Apr 8, 2007
 *
 * ClassName	:  	ProductsListForm.java
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
public class ProductsListForm extends ActionForm
{
    private String startPosition ="1";
    private String sortOrder="ascending";
	private String sortField="productName";
	private String maxItems="10";
	private String page="1";
	private String pageTop ="0";
	private String formActions = "list";
	private String txtProductName="";
	private String searchProductName="0";
	private String prductsId = "";


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
    public String getSearchProductName()
    {
        return searchProductName;
    }
    public void setSearchProductName(String searchProductName)
    {
        this.searchProductName = searchProductName;
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
    public String getTxtProductName()
    {
        return txtProductName;
    }
    public void setTxtProductName(String txtProductName)
    {
        this.txtProductName = txtProductName;
    }
    
    /**
     * @return Returns the prductsId.
     */
    public String getPrductsId() {
        return prductsId;
    }
    /**
     * @param prductsId The prductsId to set.
     */
    public void setPrductsId(String prductsId) {
        this.prductsId = prductsId;
    }
}

/*
*$Log: ProductsListForm.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/08/10 09:31:57  sramasamy
*Property productId is added for Make Valid/Invalid link.
*
*Revision 1.1  2007/04/08 11:05:36  rraman
*products list finished
*
*
*/