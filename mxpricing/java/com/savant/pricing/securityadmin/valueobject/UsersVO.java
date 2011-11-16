/*
 * Created on Jan 25, 2007
 *
 * ClassName	:  	UsersVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.securityadmin.valueobject;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UsersVO implements Serializable
{
    private String userId;
    private String password;
    private String firstName;
    private String lastName;
    private String emailId;
    private String comment;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private boolean valid;
    private UsersVO parentUser;
    private UserTypesVO userTypes;
    
    public UsersVO()
    {
    }
    
  
    public String getComment()
    {
        return comment;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
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
    public String getEmailId()
    {
        return emailId;
    }
    public void setEmailId(String emailId)
    {
        this.emailId = emailId;
    }
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
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
    
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        String hashedPwd = password;//MessageDigestor.getInstance().encrypt(password.trim());
        this.password = hashedPwd;
      
    }
    
    public String getUserId()
    {
        return userId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    public boolean isValid()
    {
        return valid;
    }
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
    public UsersVO getParentUser()
    {
        return parentUser;
    }
    public void setParentUser(UsersVO parentUser)
    {
        this.parentUser = parentUser;
    }
   
    public UserTypesVO getUserTypes() {
        return userTypes;
    }
    public void setUserTypes(UserTypesVO userTypes) {
        this.userTypes = userTypes;
    }
}

/*
*$Log: UsersVO.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:54  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:24  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/08/27 06:38:42  spandiyarajan
*removed unwanted imports
*
*Revision 1.4  2007/08/24 13:34:36  kduraisamy
*calling menu item in all pages problem solved.
*
*Revision 1.3  2007/03/10 12:22:12  srajappan
*security admin vo changed
*
*Revision 1.2  2007/03/08 04:54:53  srajappan
*user group mapping added
*
*Revision 1.1  2007/03/02 11:52:03  kduraisamy
*menu added
*
*Revision 1.5  2007/02/23 05:11:42  srajappan
*usertype changed to usertypes
*
*Revision 1.4  2007/02/12 06:04:04  kduraisamy
*unwanted set Removed.
*
*Revision 1.3  2007/01/29 10:11:24  kduraisamy
*userid changed to string.
*
*Revision 1.2  2007/01/27 10:15:45  kduraisamy
*mapping problem resolved.
*
*Revision 1.1  2007/01/25 13:08:47  kduraisamy
*initial commit.
*
*
*/