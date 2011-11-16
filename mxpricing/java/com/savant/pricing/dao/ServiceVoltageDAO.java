/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	ServiceVoltageDAO.java
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
import com.savant.pricing.valueobjects.ServiceVoltageVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ServiceVoltageDAO
{
    private static Logger logger = Logger.getLogger(ServiceVoltageDAO.class);

    public HashMap getAllServiceVoltage(Filter filter, String sortBy, boolean ascending, int startIndex, int displayCount)
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
            logger.info("GETTING ALL SERVICE VOLTAGE");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ServiceVoltageVO.class);
            if(filter != null)
            {
                    objCriteria.add(Restrictions.like(filter.getFieldName(),filter.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filter.getSpecialFunction())));
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
            hmResult.put("TotalRecordCount", totRecordCount);
            hmResult.put("Records", objList);
            logger.info("GOT ALL SERVICE VOLTAGE");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL SERVICE VOLTAGE", e);
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

    public List getAllServiceVoltage()
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
            logger.info("GETTING ALL SERVICE VOLTAGE");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ServiceVoltageVO.class);
            objList = objCriteria.list();
            logger.info("GOT ALL SERVICE VOLTAGE");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL SERVICE VOLTAGE", e);
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
    
    public ServiceVoltageVO getServiceVoltage( int serviceId ) 
    {
        Session objSession                   = null;
        ServiceVoltageVO objServiceVoltageVO = null;
        try 
        {
            logger.info("GETTING SERVICE VOLTAGE FOR SERVICE ID");
            objSession = HibernateUtil.getSession();
            objServiceVoltageVO = ( ServiceVoltageVO ) objSession.get(ServiceVoltageVO.class, new Integer( serviceId ));
            logger.info("GOT SERVICE VOLTAGE FOR SERVICE ID");
        } 
        catch ( HibernateException e ) 
        {
            logger.error("HIBERNATE EXCEPTION DURING GET SERVICE VOLTAGE FOR SERVICE ID", e);
            e.printStackTrace();
        }
        finally 
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objServiceVoltageVO;
    }
    
    public boolean updateServiceVoltage( ServiceVoltageVO objServiceVoltageVO )
    {
        boolean updateResult = false;
        Session objSession   = null;
        try
        {
            logger.info("UPDATING SERVICE VOLTAGE DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update( objServiceVoltageVO );
            objSession.getTransaction().commit();
            updateResult = true;
            objSession.flush();
            logger.info("SERVICE VOLTAGE DETAILS IS UPDATED");
        }
        catch( HibernateException e )
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE SERVICE VOLTAGE DETAILS", e);
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
*$Log: ServiceVoltageDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.6  2007/09/04 07:05:18  sramasamy
*Log message is added for log file.
*
*Revision 1.5  2007/08/23 07:27:53  jnadesan
*imports organized
*
*Revision 1.4  2007/08/09 15:08:07  sramasamy
*Make Valid/invalid option provided
*
*Revision 1.3  2007/04/09 11:38:21  spandiyarajan
*serviceVoltage list initialy commited
*
*Revision 1.2  2007/03/28 06:58:07  kduraisamy
*filter for all service voltages added.
*
*Revision 1.1  2007/02/02 10:59:32  kduraisamy
*initial commit.
*
*
*/