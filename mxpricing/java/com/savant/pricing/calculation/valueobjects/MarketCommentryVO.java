/*
 * Created on Nov 19, 2007
 *
 * ClassName	:  	MarketCommentryVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.calculation.valueobjects;

import java.util.Date;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MarketCommentryVO
{
    private Date marketDate ;
    private String marketComments = "";
    private Date createdDate ;
    private String createdBy = "";
    private Date modifiedDate ;
    private String modifiedBy = "";
    private int commentryId = 0;
    
    
    
    public int getCommentryId()
    {
        return commentryId;
    }
    public void setCommentryId(int commentryId)
    {
        this.commentryId = commentryId;
    }
    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
  
    public String getMarketComments()
    {
        return marketComments;
    }
    public void setMarketComments(String marketComments)
    {
        this.marketComments = marketComments;
    }
    public String getModifiedBy()
    {
        return modifiedBy;
    }
    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }
    
    public Date getCreatedDate()
    {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }
    public Date getMarketDate()
    {
        return marketDate;
    }
    public void setMarketDate(Date marketDate)
    {
        this.marketDate = marketDate;
    }
    public Date getModifiedDate()
    {
        return modifiedDate;
    }
    public void setModifiedDate(Date modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }
}

/*
*$Log: MarketCommentryVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/23 05:29:12  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/11/20 04:52:53  tannamalai
**** empty log message ***
*
*
*/