/*
 * Created on Jun 18, 2007
 *
 * ClassName	:  	CustomerCommentsVO.java
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
public class CustomerCommentsVO implements Serializable
{

    public CustomerCommentsVO()
    {
    }
    private int commentId;
    private int cmsCommentId;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private String comments;
    private int version;
    private ProspectiveCustomerVO prospectiveCustomer;
    
    
    public int getCmsCommentId()
    {
        return cmsCommentId;
    }
    public void setCmsCommentId(int cmsCommentId)
    {
        this.cmsCommentId = cmsCommentId;
    }
    public int getCommentId()
    {
        return commentId;
    }
    public void setCommentId(int commentId)
    {
        this.commentId = commentId;
    }
    public String getComments()
    {
        return comments;
    }
    public void setComments(String comments)
    {
        this.comments = comments;
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
    public ProspectiveCustomerVO getProspectiveCustomer()
    {
        return prospectiveCustomer;
    }
    public void setProspectiveCustomer(ProspectiveCustomerVO prospectiveCustomer)
    {
        this.prospectiveCustomer = prospectiveCustomer;
    }
    public int getVersion()
    {
        return version;
    }
    public void setVersion(int version)
    {
        this.version = version;
    }
}

/*
*$Log: CustomerCommentsVO.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:45  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/06/26 12:12:25  kduraisamy
*CMS_CommentId added.
*
*Revision 1.1  2007/06/18 10:00:54  kduraisamy
*comments added.
*
*
*/