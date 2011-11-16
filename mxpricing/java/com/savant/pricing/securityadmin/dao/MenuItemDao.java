/*
 * Created on Feb 27, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.securityadmin.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.securityadmin.valueobject.MenuItemsVO;
import com.savant.pricing.securityadmin.valueobject.RoleMenuItemVO;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MenuItemDao 
{
    private static Logger logger = Logger.getLogger(MenuItemDao.class);
    
    public HashMap getAllMenuName()
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria = null;
        Session objSession = null;
        Integer totRecordCount = null;
        List objList = null;
        HashMap hmResult = new HashMap();
        try
        {
            logger.info("GETTING ALL MENU NAME");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MenuItemsVO.class).addOrder(Order.asc("menuItem.menuItemID"));
            totRecordCount = new Integer(objCriteria.list().size());
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",objList);
            logger.info("GOT ALL MENU NAME");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL MENU NAME", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hmResult;
    }
    public List getMenuItems(int roleId)
    {        
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING MENU ITEMS");
            objSession = HibernateUtil.getSession();
            objList = objSession.createCriteria(RoleMenuItemVO.class).add(Restrictions.eq("role.groupId", new Integer(roleId))).list();
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE MENU ITEMS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession !=  null)
            {
                objSession.close();
            }
        }
        return objList;
    }
    public Collection getMenuItem(String menuItemIds)
    {
        Session objSession = null;
        Criteria objCriteria = null;
        Collection objCollection = null;
        try
        {
            logger.info("GETTING MENU ITEMS FOR MENU ITEM IDS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MenuItemsVO.class);
            StringTokenizer st = new StringTokenizer(menuItemIds, ",");
            Integer[] objInteger = new Integer[st.countTokens()];
            int index=0;
            while(st.hasMoreTokens())
            {
                objInteger[index++]=new Integer(st.nextToken());
            }
            objCriteria.add(Restrictions.in("menuItemID", objInteger));
            objCollection = objCriteria.list();
            logger.info("GOT MENU ITEMS FOR MENU ITEM IDS");
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING GET MENU ITEM", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objCollection;
    }
    public static void main(String[] args)
    {
        MenuItemDao itemDao = new  MenuItemDao();
        if(BuildConfig.DMODE)
            System.out.println("menu all items:"+itemDao.getAllMenuName());
    }

}
