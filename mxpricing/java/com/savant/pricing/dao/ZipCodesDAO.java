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
package com.savant.pricing.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.ZipCodeVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ZipCodesDAO
{
    private static Logger logger = Logger.getLogger(ZipCodesDAO.class);
    
    public HashMap getAllZipCodes()
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
            logger.info("GETTING ALL ZIP CODES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ZipCodeVO.class);
            totRecordCount = new Integer(objCriteria.list().size());
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount", totRecordCount);
            hmResult.put("Records", objList);
            logger.info("GOT ALL ZIP CODES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ZIP CODES", e);
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
    
    public HashMap getAllZipCodes(Filter filter, int congestionZoneId, int weatherZoneId, String sortBy, boolean ascending, int startIndex, int displayCount)
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
            logger.info("GETTING ALL ZIP CODES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ZipCodeVO.class);
            if(filter != null)
            {
                objCriteria.add(Restrictions.eq(filter.getFieldName(),new Integer(filter.getFieldValue())));
            }
            if(congestionZoneId != 0)
            {
                objCriteria.add(Restrictions.eq("congestionZone.congestionZoneId", new Integer(congestionZoneId)));
            }
            
            if(weatherZoneId != 0)
            {
                objCriteria.add(Restrictions.eq("weatherZone.weatherZoneId", new Integer(weatherZoneId)));
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
            logger.info("GOT ALL ZIP CODES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ZIP CODES", e);
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
    
    public ZipCodeVO getZipCode( int zipId ) 
    {
        Session objSession     = null;
        ZipCodeVO objZipCodeVO = null;
        
        try 
        {
            logger.info("GETTING ZIP CODE BY ZIP CODE ID");
            objSession = HibernateUtil.getSession();
            objZipCodeVO = ( ZipCodeVO ) objSession.get( ZipCodeVO.class, new Integer( zipId ) );
            logger.info("GOT ZIP CODE");
        } 
        catch ( HibernateException e ) 
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ZIP CODE", e);
            e.printStackTrace();
        }
        finally 
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objZipCodeVO;
    }
    
    public boolean updateZipCode( ZipCodeVO objZipCodeVO )
    {
        boolean updateResult = false;
        Session objSession   = null;
        
        try
        {
            logger.info("UPDATING ZIP CODE DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update( objZipCodeVO );
            objSession.getTransaction().commit();
            updateResult = true;
            objSession.flush();
            logger.info("ZIP CODE DETAILS ARE UPDATED");
        }
        catch( HibernateException e )
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE ZIP CODE DETAILS", e);
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
    
    public boolean addZipCode( ZipCodeVO objZipCodeVO )
    {
        boolean updateResult = false;
        Session objSession   = null;
        
        try
        {
            logger.info("ADDING ZIP CODE DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.save( objZipCodeVO );
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("ZIP CODE DETAILS ARE ADDED");
        }
        catch( HibernateException e )
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD THE ZIP CODE DETAILS", e); 
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
        ZipCodesDAO objZipCodesDAO = new ZipCodesDAO();
        /*Filter obj = new Filter();
        obj.setFieldName("zipCode");
        obj.setFieldValue("7");
        obj.setSpecialFunction(HibernateUtil.STARTS_WITH);
        HashMap hmZipCodes = objZipCodesDAO.getAllZipCodes(obj,0,0,"zipCode",true,0,10); 
        if(BuildConfig.DMODE)
            System.out.println("hmZipCodes :"+hmZipCodes);*/
   }
}

/*
*$Log: ZipCodesDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.9  2007/09/04 07:05:18  sramasamy
*Log message is added for log file.
*
*Revision 1.8  2007/08/23 07:27:53  jnadesan
*imports organized
*
*Revision 1.7  2007/08/16 05:53:39  sramasamy
*New method addZipCode is added to add new zipcode for zipAdd page.
*
*Revision 1.6  2007/08/09 15:08:07  sramasamy
*Make Valid/invalid option provided
*
*Revision 1.5  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.4  2007/04/09 08:28:26  spandiyarajan
*zipcode search bug fixed and removed spl search function in jsp page
*
*Revision 1.3  2007/03/26 05:16:49  kduraisamy
*filter added for congestion Zone also.
*
*Revision 1.2  2007/02/02 06:07:51  kduraisamy
*imports organized.
*
*Revision 1.1  2007/02/02 06:06:03  kduraisamy
*initial commit.
*
*
*/