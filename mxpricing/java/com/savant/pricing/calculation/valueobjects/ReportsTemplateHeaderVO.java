/*
 * Created on Apr 8, 2007
 *
 * ClassName	:  	ReportsTemplateHeaderVO.java
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

import com.savant.pricing.valueobjects.ProductsVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportsTemplateHeaderVO implements Serializable
{

    private int reportIdentifier;
    private ProductsVO products;
    private String reportName;
    private String description;
    
    public ReportsTemplateHeaderVO()
    {
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public ProductsVO getProducts()
    {
        return products;
    }
    public void setProducts(ProductsVO products)
    {
        this.products = products;
    }
    public int getReportIdentifier()
    {
        return reportIdentifier;
    }
    public void setReportIdentifier(int reportIdentifier)
    {
        this.reportIdentifier = reportIdentifier;
    }
    public String getReportName()
    {
        return reportName;
    }
    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }
}

/*
*$Log: ReportsTemplateHeaderVO.java,v $
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