/*
 * Created on Feb 1, 2008
 *
 * ClassName	:  	CostofCapitalForm.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.pconfig.deallevers;

import org.apache.struts.action.ActionForm;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CostofCapitalForm extends ActionForm
{
    private String formAction="list";
    private float value = 0;
    private String unit = "";
    private String cocName = "";
    private String cocId = "";
    private String startPosition ="1";
    private String sortOrder="ascending";
	private String sortField="";
	private String search="";
	private String maxItems="10";
	private String page="1";
	private String pageTop ="0";
	private String txtCocs="";
	private String cmbCocs="0";
	
	

	
    public String getCmbCocs()
    {
        return cmbCocs;
    }
    public void setCmbCocs(String cmbCocs)
    {
        this.cmbCocs = cmbCocs;
    }
    public String getCocId()
    {
        return cocId;
    }
    public void setCocId(String cocId)
    {
        this.cocId = cocId;
    }
    public String getCocName()
    {
        return cocName;
    }
    public void setCocName(String cocName)
    {
        this.cocName = cocName;
    }
    public String getFormAction()
    {
        return formAction;
    }
    public void setFormAction(String formAction)
    {
        this.formAction = formAction;
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
    public String getStartPosition()
    {
        return startPosition;
    }
    public void setStartPosition(String startPosition)
    {
        this.startPosition = startPosition;
    }
    public String getTxtCocs()
    {
        return txtCocs;
    }
    public void setTxtCocs(String txtCocs)
    {
        this.txtCocs = txtCocs;
    }
    public String getUnit()
    {
        return unit;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }
    public float getValue()
    {
        return value;
    }
    public void setValue(float value)
    {
        this.value = value;
    }
}

/*
*$Log: CostofCapitalForm.java,v $
*Revision 1.1  2008/02/06 06:42:32  tannamalai
*cost of capital added
*
*
*/