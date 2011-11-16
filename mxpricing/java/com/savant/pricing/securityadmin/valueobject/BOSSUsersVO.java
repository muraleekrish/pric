/*
 * Created on Jun 12, 2007
 *
 * ClassName	:  	BOSSUsersVO.java
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

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BOSSUsersVO implements Serializable
{

    public BOSSUsersVO()
    {
    }
    private String userId;
    private String userFirstName;
    private String userLastName;
    private String email;
    private int valid;
    
    public int getValid()
    {
        return valid;
    }
    public void setValid(int valid)
    {
        this.valid = valid;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getUserFirstName()
    {
        return userFirstName;
    }
    public void setUserFirstName(String userFirstName)
    {
        this.userFirstName = userFirstName;
    }
    public String getUserId()
    {
        return userId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    public String getUserLastName()
    {
        return userLastName;
    }
    public void setUserLastName(String userLastName)
    {
        this.userLastName = userLastName;
    }
}

/*
*$Log: BOSSUsersVO.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:54  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:24  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/06/12 06:27:33  kduraisamy
*userIds are taken from BOSS.
*
*
*/