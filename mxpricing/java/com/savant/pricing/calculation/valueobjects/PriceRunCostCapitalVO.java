/*
 * Created on Feb 1, 2008
 *
 * ClassName	:  	PriceRunCostCapitalVO.java
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
public class PriceRunCostCapitalVO implements Serializable
{
    private CostOfCapitalVO cocId;
    private PriceRunHeaderVO prcId;
    private float cocValue;
    
    
    public CostOfCapitalVO getCocId()
    {
        return cocId;
    }
    public void setCocId(CostOfCapitalVO cocId)
    {
        this.cocId = cocId;
    }
    public float getCocValue()
    {
        return cocValue;
    }
    public void setCocValue(float cocValue)
    {
        this.cocValue = cocValue;
    }
    public PriceRunHeaderVO getPrcId()
    {
        return prcId;
    }
    public void setPrcId(PriceRunHeaderVO prcId)
    {
        this.prcId = prcId;
    }
}

/*
*$Log: PriceRunCostCapitalVO.java,v $
*Revision 1.1  2008/02/06 06:41:19  tannamalai
*cost of capital added
*
*
*/