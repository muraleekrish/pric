/*
 * Created on Aug 17, 2007
 *
 * ClassName	:  	NGFutureTerminateVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.valueobjects;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NGFutureTerminateVO implements Serializable
{
    private Date deliveryMonth;
    private Date noticeDate;
    public NGFutureTerminateVO()
    {
    }
    public Date getDeliveryMonth()
    {
        return deliveryMonth;
    }
    public void setDeliveryMonth(Date deliveryMonth)
    {
        this.deliveryMonth = deliveryMonth;
    }
    public Date getNoticeDate()
    {
        return noticeDate;
    }
    public void setNoticeDate(Date noticeDate)
    {
        this.noticeDate = noticeDate;
    }
}

/*
*$Log: NGFutureTerminateVO.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:45  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/08/17 11:19:11  kduraisamy
*initial commit.
*
*
*/