/*
 * Created on May 16, 2006
 *
 * ClassName	:  	BizSysElement.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.securityadmin.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * @author spalanisamy
 *
 */
public class MenuItemDetails implements Serializable
{
    private String menuName="";
    private String menuPath="";
    private int parentFunctionID;    
    private int menuOrder;
    private int menuLevel;    
    							
    
    private LinkedHashMap childMap = new LinkedHashMap();
  
    
    /**
     * @return Returns the menuLevel.
     */
    public int getMenuLevel()
    {
        return menuLevel;
    }
    /**
     * @param menuLevel The menuLevel to set.
     */
    public void setMenuLevel(int menuLevel)
    {
        this.menuLevel = menuLevel;
    }
    /**
     * @return Returns the parentFunctionID.
     */
    public int getParentFunctionID()
    {
        return parentFunctionID;
    }
    /**
     * @param parentFunctionID The parentFunctionID to set.
     */
    public void setParentFunctionID(int parentFunctionID)
    {
        this.parentFunctionID = parentFunctionID;
    }
    /**
     * @return Returns the childMap.
     */
    public LinkedHashMap getChildMap()
    {
        return childMap;
    }
    /**
     * @param childMap The childMap to set.
     */
    public void setChildMap(LinkedHashMap childMap)
    {
        this.childMap = childMap;
    }
    /**
     * @return Returns the menuName.
     */
    public String getMenuName()
    {
        return menuName;
    }
    /**
     * @param menuName The menuName to set.
     */
    public void setMenuName(String menuName)
    {
        this.menuName = menuName;
    }
    /**
     * @return Returns the menuPath.
     */
    public String getMenuPath()
    {
        return menuPath;
    }
    /**
     * @param menuPath The menuPath to set.
     */
    public void setMenuPath(String menuPath)
    {
        this.menuPath = menuPath;
    }

    /**
     * @return Returns the menuOrder.
     */
    public int getMenuOrder()
    {
        return menuOrder;
    }
    /**
     * @param menuOrder The menuOrder to set.
     */
    public void setMenuOrder(int menuOrder)
    {
        this.menuOrder = menuOrder;
    }
}

/*
*$Log: MenuItemDetails.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/03/02 11:51:42  kduraisamy
*menu added
*
*Revision 1.2  2006/07/31 13:10:46  srajappan
*empty block removed
*
*Revision 1.1  2006/06/22 11:17:06  kduraisamy
*Role Based access added
*
*Revision 1.3  2006/05/28 06:23:38  bkannusamy
*menu constructed from the function table
*
*Revision 1.2  2006/05/27 13:04:06  bkannusamy
*new menu is added
*
*

*/