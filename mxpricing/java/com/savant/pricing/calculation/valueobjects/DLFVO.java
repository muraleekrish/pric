/*
 * Created on Apr 6, 2007
 *
 * ClassName	:  	DLFVO.java
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
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DLFVO implements Serializable
{
    private DLFCodeVO dlfCode;
    private float dlf;
    public DLFVO()
    {
    }
    public float getDlf()
    {
        return dlf;
    }
    public void setDlf(float dlf)
    {
        this.dlf = dlf;
    }
    public DLFCodeVO getDlfCode()
    {
        return dlfCode;
    }
    public void setDlfCode(DLFCodeVO dlfCode)
    {
        this.dlfCode = dlfCode;
    }
}

/*
*$Log: DLFVO.java,v $
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