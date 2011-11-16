/*
 * Created on Feb 3, 2007
 *
 * ClassName	:  	DayTypesDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.DayTypesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DayTypesDAO
{
    private static Logger logger = Logger.getLogger(DayTypesDAO.class);
    
    public List getAllDayTypes()
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
            logger.info("GETTING ALL DAY TYPES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(DayTypesVO.class);
            objList = objCriteria.list();
            logger.info("GOT ALL DAY TYPES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL DAY TYPES", e);
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
    
    public HashMap getAllDayTypes(int dayTypeId,String sortBy, boolean ascending, int startIndex, int displayCount)
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
            logger.info("GETTING ALL DAY TYPES BY DAY TYPE ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(DayTypesVO.class);
            if(dayTypeId != 0)
            {
                objCriteria.add(Restrictions.eq("dayTypeId", new Integer(dayTypeId)));
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
            logger.info("GOT ALL DAY TYPES BY DAY TYPE ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL DAY TYPES BY DAY TYPE ID", e);
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
    
    public DayTypesVO getDayType( int dayTypeId ) 
    {
        Session objSession       = null;
        DayTypesVO objDayTypesVO = null;
        
        try 
        {
            logger.info("GETTING DAY TYPE FOR DAY TYPE ID");
            objSession = HibernateUtil.getSession();
            objDayTypesVO = ( DayTypesVO ) objSession.get( DayTypesVO.class, new Integer( dayTypeId ) );
            logger.info("GOT DAY TYPE FOR THE DAY TYPE ID");
        } 
        catch ( HibernateException e ) 
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE DAY TYPE FOR DAY TYPE ID", e);
            e.printStackTrace();
        }
        finally 
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objDayTypesVO;
    }
    
    public boolean updateDayType( DayTypesVO objDayTypesVO )
    {
        boolean updateResult = false;
        Session objSession   = null;
        
        try
        {
            logger.info("UPDATING DAY TYPE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update( objDayTypesVO );
            objSession.getTransaction().commit();
            updateResult = true;
            objSession.flush();
            logger.info("DAY TYPE UPDATED");
        }
        catch( HibernateException e )
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE DAY TYPE", e);
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
    
    public static void main(String args[])
    {
        DayTypesDAO objDayTypesDAO = new DayTypesDAO();
        Filter obj = new Filter();
        obj.setFieldName("dayTypeId");
        obj.setFieldValue("k");
        obj.setSpecialFunction(HibernateUtil.STARTS_WITH);
        HashMap hmDayType = new HashMap();
        hmDayType = objDayTypesDAO.getAllDayTypes(0,"dayType",true,0,10);        
        if(BuildConfig.DMODE)
            System.out.println("hmDayType :"+hmDayType);
    }
}

/*
*$Log: DayTypesDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.9  2007/09/14 06:54:24  sramasamy
*Log Message Correction.
*
*Revision 1.8  2007/09/04 05:23:55  spandiyarajan
*removed unwanted imports
*
*Revision 1.7  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.6  2007/08/23 07:27:53  jnadesan
*imports organized
*
*Revision 1.5  2007/08/09 15:06:15  sramasamy
*Make Valid/invalid option provided
*
*Revision 1.4  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.3  2007/04/18 03:56:06  kduraisamy
*imports organized.
*
*Revision 1.2  2007/04/09 11:03:48  spandiyarajan
*daytype iniitaily commited
*
*Revision 1.1  2007/02/03 05:57:26  kduraisamy
*Day Types mapping included.
*
*
*/