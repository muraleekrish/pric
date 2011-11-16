/*
 * Created on Jan 25, 2007
 *
 * ClassName	:  	UserTypesVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.securityadmin.valueobject;

import java.util.Date;





/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserTypesVO
{
    private int userTypeId;
    private String userType;
    private String description;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private boolean valid;
    private UserTypesVO parentUserType;
  
    public UserTypesVO()
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
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
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
    public UserTypesVO getParentUserType()
    {
        return parentUserType;
    }
    public void setParentUserType(UserTypesVO parentUserType)
    {
        this.parentUserType = parentUserType;
    }
    public String getUserType()
    {
        return userType;
    }
    public void setUserType(String userType)
    {
        this.userType = userType;
    }
    public int getUserTypeId()
    {
        return userTypeId;
    }
    public void setUserTypeId(int userTypeId)
    {
        this.userTypeId = userTypeId;
    }
    public boolean isValid()
    {
        return valid;
    }
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
}

/*
*$Log: UserTypesVO.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:54  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:24  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/03/02 11:52:03  kduraisamy
*menu added
*
*Revision 1.3  2007/02/12 06:04:04  kduraisamy
*unwanted set Removed.
*
*Revision 1.2  2007/01/25 14:31:58  kduraisamy
*initial mapping.
*
*Revision 1.1  2007/01/25 13:08:47  kduraisamy
*initial commit.
*
*
*/