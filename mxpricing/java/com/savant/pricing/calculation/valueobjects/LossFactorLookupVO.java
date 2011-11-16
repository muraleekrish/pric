/*
 * Created on Apr 6, 2007
 *
 * ClassName	:  	LossFactorLookupVO.java
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

import com.savant.pricing.valueobjects.ServiceVoltageVO;
import com.savant.pricing.valueobjects.TDSPVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LossFactorLookupVO implements Serializable
{
    private TDSPVO tdsp;
    private ServiceVoltageVO serviceVoltage;
    private String dlfCode;
    public LossFactorLookupVO()
    {
    }
    
    public String getDlfCode()
    {
        return dlfCode;
    }
    public void setDlfCode(String dlfCode)
    {
        this.dlfCode = dlfCode;
    }
    public ServiceVoltageVO getServiceVoltage()
    {
        return serviceVoltage;
    }
    public void setServiceVoltage(ServiceVoltageVO serviceVoltage)
    {
        this.serviceVoltage = serviceVoltage;
    }
    public TDSPVO getTdsp()
    {
        return tdsp;
    }
    public void setTdsp(TDSPVO tdsp)
    {
        this.tdsp = tdsp;
    }
}

/*
*$Log: LossFactorLookupVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/06 12:32:19  kduraisamy
*TLF AND DLF COMPLETED.
*
*
*/