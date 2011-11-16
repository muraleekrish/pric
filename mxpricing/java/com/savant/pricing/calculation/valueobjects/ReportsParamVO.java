/*
 * Created on Apr 8, 2007
 *
 * ClassName	:  	ReportsParamVO.java
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
public class ReportsParamVO implements Serializable
{
    private int reportParamIdentifier;
    private ReportsTemplateHeaderVO report;
    private String reportParamName;
    private String defaultText;
    private String createdBy;
    private Date createdDate;
    public ReportsParamVO()
    {
    }
    
    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    public Date getCreatedDate()
    {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }
    public String getDefaultText()
    {
        return defaultText;
    }
    public void setDefaultText(String defaultText)
    {
        this.defaultText = defaultText;
    }
    public ReportsTemplateHeaderVO getReport()
    {
        return report;
    }
    public void setReport(ReportsTemplateHeaderVO report)
    {
        this.report = report;
    }
    public int getReportParamIdentifier()
    {
        return reportParamIdentifier;
    }
    public void setReportParamIdentifier(int reportParamIdentifier)
    {
        this.reportParamIdentifier = reportParamIdentifier;
    }
    public String getReportParamName()
    {
        return reportParamName;
    }
    public void setReportParamName(String reportParamName)
    {
        this.reportParamName = reportParamName;
    }
}

/*
*$Log: ReportsParamVO.java,v $
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