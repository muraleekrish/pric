/*
 * Created on Feb 27, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.securityadmin.valueobject;

import java.io.Serializable;
import java.util.Date;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MenuItemsVO implements Serializable{
    
    private int menuItemID;
    private String menuItemName;
    private String displayName;
    private String discription;
    private MenuItemsVO menuItem;
    private long menuLevels;
    private long menuOrder;
    private String menuPath;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private boolean valid;
    
    public MenuItemsVO(){
        
    }

    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public String getDiscription() {
        return discription;
    }
    public void setDiscription(String discription) {
        this.discription = discription;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public MenuItemsVO getMenuItem() {
        return menuItem;
    }
    public void setMenuItem(MenuItemsVO menuItem) {
        this.menuItem = menuItem;
    }
    public int getMenuItemID() {
        return menuItemID;
    }
    public void setMenuItemID(int menuItemID) {
        this.menuItemID = menuItemID;
    }
    public String getMenuItemName() {
        return menuItemName;
    }
    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }
    public long getMenuLevels() {
        return menuLevels;
    }
    public void setMenuLevels(long menuLevels) {
        this.menuLevels = menuLevels;
    }
    public long getMenuOrder() {
        return menuOrder;
    }
    public void setMenuOrder(long menuOrder) {
        this.menuOrder = menuOrder;
    }
    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }
    public String getMenuPath()
    {
        return menuPath;
    }
    public void setMenuPath(String menuPath)
    {
        this.menuPath = menuPath;
    }
    public String getModifiedBy() {
        return modifiedBy;
    }
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    public Date getModifiedDate() {
        return modifiedDate;
    }
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    public boolean isValid() {
        return valid;
    }
    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
