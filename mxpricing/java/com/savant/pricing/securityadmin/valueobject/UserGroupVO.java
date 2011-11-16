/*
 * Created on Mar 2, 2007
 *
 * ClassName	:  	RoleMenuItemVO.java
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
public class UserGroupVO implements Serializable
{
    private RolesVO role;
    private UsersVO userVo;
    public UserGroupVO()
    {
    }
    
   
    public RolesVO getRole() {
        return role;
    }
    public void setRole(RolesVO role) {
        this.role = role;
    }
    public UsersVO getUserVo() {
        return userVo;
    }
    public void setUserVo(UsersVO userVo) {
        this.userVo = userVo;
    }
}

/*
*$Log: UserGroupVO.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:54  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:24  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/03/08 04:54:53  srajappan
*user group mapping added
*
*Revision 1.1  2007/03/02 11:52:03  kduraisamy
*menu added
*
*
*/