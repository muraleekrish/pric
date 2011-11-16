/*
 * Created on Feb 1, 2008
 *
 * ClassName	:  	CostOfCapitalVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.calculation.valueobjects;

import java.io.Serializable;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CostOfCapitalVO implements Serializable
{
    private int cocId;
    private String cocName;
    private float cocValue;
    private int isValid;
    private UOMVO unit;
    
    

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
    public float getCocValue()
    {
        return cocValue;
    }
    public void setCocValue(float cocValue)
    {
        this.cocValue = cocValue;
    }
    public int getIsValid()
    {
        return isValid;
    }
    public void setIsValid(int isValid)
    {
        this.isValid = isValid;
    }
    public UOMVO getUnit()
    {
        return unit;
    }
    public void setUnit(UOMVO unit)
    {
        this.unit = unit;
    }
}

/*
*$Log: CostOfCapitalVO.java,v $
*Revision 1.1  2008/02/06 06:41:19  tannamalai
*cost of capital added
*
*
*/