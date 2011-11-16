/*
 * Created on Jan 29, 2008
 *
 * ClassName	:  	EnergyComponentsVO.java
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

import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CustEnergyComponentsVO implements Serializable
{
    private ProspectiveCustomerVO prcCustId;
    private EnergyComponentsVO engComp;
    private int isValid;
    
    

    
    public EnergyComponentsVO getEngComp()
    {
        return engComp;
    }
    public void setEngComp(EnergyComponentsVO engComp)
    {
        this.engComp = engComp;
    }
    public int getIsValid()
    {
        return isValid;
    }
    public void setIsValid(int isValid)
    {
        this.isValid = isValid;
    }
   
    public ProspectiveCustomerVO getPrcCustId()
    {
        return prcCustId;
    }
    public void setPrcCustId(ProspectiveCustomerVO prcCustId)
    {
        this.prcCustId = prcCustId;
    }
}

/*
*$Log: CustEnergyComponentsVO.java,v $
*Revision 1.1  2008/01/30 13:42:42  tannamalai
*value objects added for energy components
*
*
*/