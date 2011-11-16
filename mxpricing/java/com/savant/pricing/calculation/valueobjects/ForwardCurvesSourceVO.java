/*
 * Created on Feb 1, 2007
 *
 * ClassName	:  	ForwardCurvesSourceVO.java
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
public class ForwardCurvesSourceVO implements Serializable
{
    private int dataSourceId;
    private String dataSource;
    public ForwardCurvesSourceVO()
    {
    }
    
    
    public String getDataSource()
    {
        return dataSource;
    }
    public void setDataSource(String dataSource)
    {
        this.dataSource = dataSource;
    }
    public int getDataSourceId()
    {
        return dataSourceId;
    }
    public void setDataSourceId(int dataSourceId)
    {
        this.dataSourceId = dataSourceId;
    }
}

/*
*$Log: ForwardCurvesSourceVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/02/09 11:56:55  kduraisamy
*pricing core algorithm almost finished.
*
*
*/