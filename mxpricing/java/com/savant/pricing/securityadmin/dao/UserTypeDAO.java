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

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.securityadmin.valueobject.UserTypesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserTypeDAO
{
    private static Logger logger = Logger.getLogger(UserTypeDAO.class);
    
    public boolean addUserType(UserTypesVO userTypesVO)
    {
        boolean addResult = false;
        Session objSession = null;
        try
        {
            logger.info("ADDING A USER TYPE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.save(userTypesVO);
            addResult = true;
            objSession.getTransaction().commit();
            logger.info("USER TYPE IS ADDED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD A USER TYPE", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
            if(e.toString().indexOf("hibernate.exception.ConstraintViolationException")!= -1)
            {
                throw new HibernateException("User Type Already Exists "+userTypesVO.getUserType());
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
    
    public UserTypesVO getUserType(int userTypeId)
    {
        UserTypesVO objUserTypesVO = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING USER TYPE");
            objSession = HibernateUtil.getSession();
            objUserTypesVO = (UserTypesVO)objSession.get(UserTypesVO.class,new Integer(userTypeId));
            logger.info("GOT USER TYPE");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET A USER TYPE", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET A USER TYPE", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objUserTypesVO;
    }
    
    public HashMap getAllUserType(Filter filterUserType,Filter filterParentType, String sortBy, boolean ascending, int startIndex, int displayCount)
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
            logger.info("GETTING ALL USER TYPE");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(UserTypesVO.class);
            if(filterUserType != null)
            {
                    objCriteria.add(Restrictions.like(filterUserType.getFieldName(),filterUserType.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filterUserType.getSpecialFunction())));
            }
             
            if(filterParentType != null)
            {
                    objCriteria.createCriteria("parentUserType").add(Restrictions.like(filterParentType.getFieldName(),filterParentType.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filterParentType.getSpecialFunction())));
            }
            
            totRecordCount = new Integer(objCriteria.list().size());
            if(BuildConfig.DMODE)
                System.out.println("totRecordCount:"+totRecordCount);
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
            logger.info("GOT ALL USER TYPE");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL USER TYPE", e);
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
    
    public List getUserType()
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING USER TYPE");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(UserTypesVO.class).add(Restrictions.eq("valid",new Boolean(true))).addOrder(Order.asc("userType"));
            objList = objCriteria.list();
            logger.info("GOT USER TYPE");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE USER TYPE", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objList;
    }

    public boolean updateUserType(UserTypesVO  userTypesVO)
    {
        boolean updateResult = false;
        Session objSession = null;
        try
        {
            logger.info("UPDATING USER TYPE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update(userTypesVO);
            updateResult = true;
            objSession.getTransaction().commit();
            objSession.flush();
            logger.info("USER TYPE UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE USER TYPE", e);
            objSession.getTransaction().rollback();
            updateResult = false;
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
    
    public boolean deleteUserType(UserTypesVO userTypesVO)
    {
        boolean updateResult = false;
        Session objSession = null;
        try
        {
            logger.info("DELETING USER TYPE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            
            objSession.delete(userTypesVO);
            
            updateResult = true;
            objSession.getTransaction().commit();
            objSession.flush();
            logger.info("USER TYPE DELETED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DELETE THE USER TYPE", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
            throw new HibernateException(e.toString());
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
	    //System.out.println(userTypeDAO.getUserType(8));
	    /* Filter obj = new Filter();
	    obj.setFieldName("userType");
	    obj.setFieldValue("a");
	    obj.setSpecialFunction(HibernateUtil.STARTS_WITH);
	    //System.out.println(userTypeDAO.getAllUserType(null,obj,"userType",true,0,10));
	    UserTypesVO objU = new UserTypesVO();
	    objU.setUserType("Testing");
	    userTypeDAO.addUserType(objU); */    
        if(BuildConfig.DMODE)
            System.out.println("size:");
    }
}

/*
*$Log: UserTypeDAO.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.14  2007/08/31 14:50:26  sramasamy
*Log message is added for log file.
*
*Revision 1.13  2007/07/31 12:30:11  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.12  2007/06/12 12:59:27  spandiyarajan
**** empty log message ***
*
*Revision 1.11  2007/05/25 10:06:19  jnadesan
*unwanted lines removed
*
*Revision 1.10  2007/04/25 05:49:30  spandiyarajan
*sort order added for list
*
*Revision 1.9  2007/04/13 05:01:12  kduraisamy
*unwanted println commented.
*
*Revision 1.8  2007/04/11 10:31:28  spandiyarajan
*throw error msg when parent usertype delete(if have constraints)
*
*Revision 1.7  2007/04/06 13:34:06  spandiyarajan
*altered the getalluers method for filter
*
*Revision 1.6  2007/04/06 07:33:43  spandiyarajan
*fixed the bug in user type
*
*Revision 1.5  2007/04/06 04:08:11  spandiyarajan
*alter the code for adding unique constraints for user type
*
*Revision 1.4  2007/04/05 13:38:38  spandiyarajan
*fixed the bug in user type
*
*Revision 1.3  2007/03/09 14:11:02  srajappan
*security admin actions added
*
*Revision 1.2  2007/03/02 11:51:42  kduraisamy
*menu added
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