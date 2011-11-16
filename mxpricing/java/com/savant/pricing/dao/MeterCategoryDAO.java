/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	MeterCategoryDAO.java
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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.MeterCategoryVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MeterCategoryDAO
{
    private static Logger logger = Logger.getLogger(MeterCategoryDAO.class);

    public HashMap getAllMeterCategory(Filter[] filter, String sortBy, boolean ascending, int startIndex, int displayCount)
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
            logger.info("GETTING ALL METER CATEGORY");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MeterCategoryVO.class);
            if(filter != null)
            {
                for(int i=0;i<filter.length;i++)
                {
                    objCriteria.add(Restrictions.like(filter[i].getFieldName(),filter[i].getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filter[i].getSpecialFunction())));
                }
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
            logger.info("GOT ALL METER CATEGORY");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL METER CATEGORY", e);
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

    public List getAllMeterCategory()
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
            logger.info("GETTING ALL METER CATEGORY");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MeterCategoryVO.class);
            objList = objCriteria.list();
            logger.info("GOT ALL METER CATEGORY");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL METER CATEGORY", e);
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
    
    public MeterCategoryVO getMeterType( int meterId ) 
    {
        Session objSession                 = null;
        MeterCategoryVO objMeterCategoryVO = null;
        
        try 
        {
            logger.info("GETTING METER TYPE FOR METER ID");
            objSession = HibernateUtil.getSession();
            objMeterCategoryVO = ( MeterCategoryVO ) objSession.get( MeterCategoryVO.class, new Integer( meterId ) );
            logger.info("GOT METER TYPE FOR METER ID");
        } 
        catch ( HibernateException e ) 
        {
            logger.error("HIBERNATE EXCEPTION DURING GET METER TYPE FOR MEER ID", e);
            e.printStackTrace();
        }
        finally 
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objMeterCategoryVO;
    }
    
    public boolean updateMeterType( MeterCategoryVO objMeterCategoryVO )
    {
        boolean updateResult = false;
        Session objSession   = null;
        
        try
        {
            logger.info("UPDATING METER TYPE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update( objMeterCategoryVO );
            objSession.getTransaction().commit();
            updateResult = true;
            objSession.flush();
            logger.info("METER TYPE IS UPDATED");
        }
        catch( HibernateException e )
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE METER TYPE", e);
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
    }
}

/*
*$Log: MeterCategoryDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.4  2007/08/23 07:27:53  jnadesan
*imports organized
*
*Revision 1.3  2007/08/09 15:08:07  sramasamy
*Make Valid/invalid option provided
*
*Revision 1.2  2007/03/28 06:36:04  kduraisamy
*getAll filter method Added.
*
*Revision 1.1  2007/02/02 09:06:57  kduraisamy
*initial commit.
*
*
*/