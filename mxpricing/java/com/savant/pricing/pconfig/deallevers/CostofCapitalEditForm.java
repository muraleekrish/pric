/*
 * Created on Feb 1, 2008
 *
 * ClassName	:  	CostofCapitalEditForm.java
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
public class CostofCapitalEditForm extends ActionForm
{
    private String formAction="list";
    private String value = "";
    private int cmboUnit = 0;
    private String cocName = "";
    private int cocId = 0;
    
    

    public int getCmboUnit()
    {
        return cmboUnit;
    }
    public void setCmboUnit(int cmboUnit)
    {
        this.cmboUnit = cmboUnit;
    }
    public int getCocId()
    {
        return cocId;
    }
    public void setCocId(int cocId)
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
    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
}

/*
*$Log: CostofCapitalEditForm.java,v $
*Revision 1.1  2008/02/06 06:42:32  tannamalai
*cost of capital added
*
*
*/