/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	ZipCodesDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.securityadmin.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.securityadmin.valueobject.MenuItemsVO;
import com.savant.pricing.securityadmin.valueobject.RoleMenuItemVO;
import com.savant.pricing.securityadmin.valueobject.RolesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RolesDAO
{
    private static Logger logger = Logger.getLogger(RolesDAO.class);
    
    public boolean addRoles(RolesVO rolesVO, List objMenuItemList)
    {
        boolean addResult = false;
        Session objSession = null;
        try
        {
            logger.info("ADDING ROLES");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.save(rolesVO);
            objSession.getTransaction().commit();
            
            objSession.beginTransaction();
            Iterator itr = objMenuItemList.iterator();
            while(itr.hasNext())
            {
                MenuItemsVO objMenuItemsVO = (MenuItemsVO)itr.next();
                RoleMenuItemVO objRoleMenuItemVO = new RoleMenuItemVO();
                objRoleMenuItemVO.setRole(rolesVO);
                objRoleMenuItemVO.setMenuItem(objMenuItemsVO);
                objSession.save(objRoleMenuItemVO);
            }
            objSession.getTransaction().commit();
            addResult = true;
            logger.info("ROLE IS ADDED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD A ROLE", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
            if(e.toString().indexOf("hibernate.exception.ConstraintViolationException")!= -1)
            {
                throw new HibernateException("Role Already Exists"+rolesVO.getGroupName());
            }
            throw new HibernateException("General Error");
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return addResult;
    }
    
    public HashMap getAllRoles(Filter filterRoleName,String sortBy, boolean ascending, int startIndex, int displayCount)
    {        
        Criteria objCriteria = null;
        Session objSession = null;
        Integer totRecordCount = null;
        List objList = null;
        HashMap hmResult = new HashMap();
        try
        {
            logger.info("GETTING ALL ROLES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(RolesVO.class);
            
            if(filterRoleName != null)
            {
                objCriteria.add(Restrictions.like(filterRoleName.getFieldName(),filterRoleName.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filterRoleName.getSpecialFunction())));
            }                
            
            totRecordCount = new Integer(objCriteria.list().size());
            if(ascending)
            {
                objCriteria.addOrder(Order.asc(sortBy));
            }
            else
            {
                objCriteria.addOrder(Order.desc(sortBy));
            }
            objCriteria.setFirstResult(startIndex);
            objCriteria.setMaxResults(displayCount);
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",objList);
            logger.info("GOT ALL ROLES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ROLES", e);
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
    public String getRoleName(String userId)
    {        
        Session objSession = null;
        String roleName = "";
        try
        {
            logger.info("GETTING ROLE NAME FOR USER " + userId);
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select userGrpVO.role.groupName from UserGroupVO as userGrpVO where userGrpVO.userVo.userId = ?");
            objQuery.setString(0, userId);
            Iterator itr = objQuery.iterate();
            if(itr.hasNext())
            {
                roleName = (String)itr.next();
                logger.info("GOT ROLE NAME");
            }
            else
            {
                logger.info("NO ROLE FOR THE USER");
            }
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE ROLE FOR A USER", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return roleName.trim();
    }
    public int getRoleId(String userId)
    {        
        Session objSession = null;
        int roleId = 0;
        try
        {
            logger.info("GETTING ROLE ID FOR USER " + userId);
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select userGrpVO.role.groupId from UserGroupVO as userGrpVO where userGrpVO.userVo.userId = ?");
            objQuery.setString(0, userId);
            Iterator itr = objQuery.iterate();
            if(itr.hasNext())
            {
                roleId = ((Integer)itr.next()).intValue();
                logger.info("GOT ROLE ID");
            }
            else
            {
                logger.info("NO ROLE ID FOR THE USER");
            }
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ROLE ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return roleId;
    }
    
    public RolesVO getRoles(String roleId)
    {
        RolesVO objRolesVO = null;
        Session objSession = null;
        try
        {
            if(BuildConfig.DMODE)
                System.out.println("RoleId:"+roleId);
            
            logger.info("GETTING ROLES");
            objSession = HibernateUtil.getSession();
            objRolesVO = (RolesVO)objSession.get(RolesVO.class,new Integer(roleId));
            logger.info("GOT ROLES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE ROLE", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET THE USER ROLE", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objRolesVO;
    } 

    public HashMap getRoles()
    {        
        Criteria objCriteria = null;
        Session objSession = null;
        Integer totRecordCount = null;
        List objList = null;
        HashMap hmResult = new HashMap();
        try
        {
            logger.info("GETTING ROLES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(RolesVO.class);
            totRecordCount = new Integer(objCriteria.list().size());
            objCriteria.addOrder(Order.asc("groupName"));
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",objList);
            logger.info("GOT ROLES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE USER ROLE", e);
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
    
    public boolean updateRole(RolesVO  rolesVO, List objMenuItemList)
    {
        
        boolean updateResult = false; 
        Session objSession = null;
        try
        {
            logger.info("UPDATING ROLE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update(rolesVO);
            objSession.getTransaction().commit();
            
            objSession.beginTransaction();
            objSession.createQuery("delete from RoleMenuItemVO as roleMenuVo where roleMenuVo.role.groupId = ?").setInteger(0, rolesVO.getGroupId()).executeUpdate();
            Iterator itr = objMenuItemList.iterator();
            while(itr.hasNext())
            {
                MenuItemsVO objMenuItemsVO = (MenuItemsVO)itr.next();
                RoleMenuItemVO objRoleMenuItemVO = new RoleMenuItemVO();
                objRoleMenuItemVO.setRole(rolesVO);
                objRoleMenuItemVO.setMenuItem(objMenuItemsVO);
                objSession.save(objRoleMenuItemVO);
            }
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("ROLE UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE ROLE", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
        }catch (Exception e) {
            logger.error("GENERAL EXCEPTION DURING UPDATE THE ROLE", e);
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
        return updateResult;
    }
    
    public boolean deleteRole(RolesVO rolesVO) throws SQLException
    {
        boolean updateResult = false;
        Session objSession = null;
        try
        {
            logger.info("DElETING ROLE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            
            objSession.delete(rolesVO);
            updateResult = true;
            objSession.getTransaction().commit();
            objSession.flush();
            logger.info("ROLE IS DELETED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DELETE THE USER ROLE", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
            throw new SQLException();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();    
            }
        }
        return updateResult;
    }
    
    public static void main(String args[])
    {
        RolesDAO userTypeDAO = new RolesDAO();
	   /* RolesVO rolesVO = new RolesVO();
	    rolesVO.setGroupName("insert op");
	    rolesVO.setValid(true);*/    
        Filter obj = new Filter();
        obj.setFieldName("groupName");
        obj.setFieldValue("t");
        obj.setSpecialFunction(HibernateUtil.STARTS_WITH);
        //HashMap hmgetAllRoles = userTypeDAO.getAllRoles(null,"groupId",true,0,10); 
        //if(BuildConfig.DMODE)
        System.out.println("hmgetAllRoles :"+userTypeDAO.getRoleName("bala"));
   }
}

/*
*$Log: RolesDAO.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.18  2007/08/31 14:50:19  sramasamy
*Log message is added for log file.
*
*Revision 1.17  2007/08/24 13:34:30  kduraisamy
*calling menu item in all pages problem solved.
*
*Revision 1.16  2007/07/31 12:30:11  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.15  2007/06/21 13:04:34  kduraisamy
*roleName wise mail added.
*
*Revision 1.14  2007/06/12 12:59:27  spandiyarajan
**** empty log message ***
*
*Revision 1.13  2007/04/25 05:49:30  spandiyarajan
*sort order added for list
*
*Revision 1.12  2007/04/23 05:52:20  kduraisamy
*unwanted println removed.
*
*Revision 1.11  2007/04/08 07:59:14  rraman
*altered
*
*Revision 1.10  2007/04/08 05:45:43  kduraisamy
*unique key added for Roles.
*
*Revision 1.9  2007/04/07 11:28:46  spandiyarajan
*committed for role
*
*Revision 1.8  2007/03/20 13:59:20  kduraisamy
*Menu Item Id order changed.
*
*Revision 1.7  2007/03/13 07:16:36  srajappan
*no role configuration mapping added
*
*Revision 1.6  2007/03/10 12:22:02  srajappan
*security admin code changed
*
*Revision 1.5  2007/03/09 14:11:02  srajappan
*security admin actions added
*
*Revision 1.4  2007/03/08 04:54:30  srajappan
*role base menu added
*
*Revision 1.3  2007/03/06 09:18:10  kduraisamy
*Roles Add problem solved.
*
*Revision 1.2  2007/03/06 09:00:48  srajappan
*roles DAO added
*
*Revision 1.1  2007/02/28 09:02:06  srajappan
*security admin DAOs
*
*Revision 1.1  2007/02/23 05:11:28  srajappan
*security admin DAO added
*
*Revision 1.2  2007/02/02 06:07:51  kduraisamy
*imports organized.
*
*Revision 1.1  2007/02/02 06:06:03  kduraisamy
*initial commit.
*
*
*/