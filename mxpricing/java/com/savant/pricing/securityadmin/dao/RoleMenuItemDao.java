/*
 * Created on Feb 27, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.securityadmin.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.securityadmin.valueobject.RoleMenuItemVO;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RoleMenuItemDao 
{
    private static Logger logger = Logger.getLogger(RoleMenuItemDao.class);
    
    public HashMap getAllRoleMenu()
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
            logger.info("GETTING ALL ROLE MENU");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(RoleMenuItemVO.class);
            totRecordCount = new Integer(objCriteria.list().size());
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",objList);
            logger.info("GOT ALL ROLE MENU");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ROLE MENU", e);
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
    
    public Vector getRoleMenu(int roleId)
    {
        Criteria objCriteria=null;
        Session objSession = null;
        List objList = null;
        Vector vecMenuItems =null;
        try
        {
            logger.info("GETTING ROLE MENU");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(RoleMenuItemVO.class);
            
            objList = objCriteria.add(Restrictions.eq("role.groupId", new Integer(roleId))).list();
            vecMenuItems = new Vector(objList);
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                RoleMenuItemVO obj = (RoleMenuItemVO)itr.next();
                if(BuildConfig.DMODE)
                    System.out.println("Value:"+obj.getMenuItem().getDisplayName());
            }
            logger.info("GOT ROLE MENU");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ROLE MENU", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ROLE MENU", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return vecMenuItems;
    }

    public boolean deleteMapping(int roleId)
    {
        boolean result =false;
        Session objSession = null;
        try
        {
            logger.info("DELETE THE MAPPING");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.createQuery("delete from RoleMenuItemVO as roleMenuItem where roleMenuItem.role.groupId = ?").setInteger(0, roleId).executeUpdate();
            objSession.getTransaction().commit();
            result = true;
            logger.info("MAPPING IS DELETED");
        } 
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING DELETE THE MAPPING", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return result;
    }
    public static void main(String[] args)
    {
        RoleMenuItemDao dao = new RoleMenuItemDao();
        dao.deleteMapping(500);
    }
}
