/*
 * Created on Apr 8, 2007
 *
 * ClassName	:  	ReportsParamValuesVO.java
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
import java.util.Date;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportsParamValuesVO implements Serializable
{
    private int reportParamValueIdentifier;
    private ReportsParamVO reportParam;
    private String reportParamValue;
    private String modifiedBy;
    private Date modifiedDate;
    public ReportsParamValuesVO()
    {
    }
    public String getModifiedBy()
    {
        return modifiedBy;
    }
    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }
    public Date getModifiedDate()
    {
        return modifiedDate;
    }
    public void setModifiedDate(Date modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }
    public ReportsParamVO getReportParam()
    {
        return reportParam;
    }
    public void setReportParam(ReportsParamVO reportParam)
    {
        this.reportParam = reportParam;
    }
    public String getReportParamValue()
    {
        return reportParamValue;
    }
    public void setReportParamValue(String reportParamValue)
    {
        this.reportParamValue = reportParamValue;
    }
    public int getReportParamValueIdentifier()
    {
        return reportParamValueIdentifier;
    }
    public void setReportParamValueIdentifier(int reportParamValueIdentifier)
    {
        this.reportParamValueIdentifier = reportParamValueIdentifier;
    }
}

/*
*$Log: ReportsParamValuesVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/08 10:09:42  kduraisamy
*initial commit.
*
*
*/