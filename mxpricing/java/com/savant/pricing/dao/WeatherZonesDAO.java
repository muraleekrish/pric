/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	WeatherZonesDAO.java
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
import com.savant.pricing.valueobjects.WeatherZonesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WeatherZonesDAO
{
    private static Logger logger = Logger.getLogger(WeatherZonesDAO.class);
    
    public List getAllWeatherZones()
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
            logger.info("GETTING ALL WEATHER ZONES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(WeatherZonesVO.class).add(Restrictions.eq("valid", new Boolean(true)));
            objCriteria.addOrder(Order.asc("weatherZone"));
            objList = objCriteria.list();
            logger.info("GOT ALL WEATHER ZONES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL WEATHER ZONES", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null);
            objSession.close();
        }
        return objList;
    }
    
    public HashMap getAllWeatherZones(Filter[] filter, int congestionZoneId, String sortBy, boolean ascending, int startIndex, int displayCount)
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
            logger.info("GETTING ALL WEATHER ZONES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(WeatherZonesVO.class);
            if(filter != null)
            {
                for(int i=0;i<filter.length;i++)
                {
                    objCriteria.add(Restrictions.like(filter[i].getFieldName(),filter[i].getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filter[i].getSpecialFunction())));
                }
            }
            if(congestionZoneId != 0)
            {
                objCriteria.add(Restrictions.eq("congestionZone.congestionZoneId", new Integer(congestionZoneId)));
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
            logger.info("GOT ALL WEATHER ZONES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL WEATHER ZONES", e);
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
    
    public WeatherZonesVO getWeatherZone( int weatherId ) 
    {
        Session objSession               = null;
        WeatherZonesVO objWeatherZonesVO = null;
        
        try 
        {
            logger.info("GETTING WEATHER ZONE DETAILS BY WEATHER ID");
            objSession = HibernateUtil.getSession();
            objWeatherZonesVO = ( WeatherZonesVO ) objSession.get( WeatherZonesVO.class, new Integer( weatherId ) );
            logger.info("GOT WEATHER ZONE DETAILS BY WEATHER ID");
        } 
        catch ( HibernateException e ) 
        {
            logger.error("HIBERNATE EXCEPTION DURING GET WEATHER ZONE DETAILS BY WEATHER ID", e);
            e.printStackTrace();
        }
        finally 
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objWeatherZonesVO;
    }
    
    public boolean updateWeatherZone( WeatherZonesVO objWeatherZonesVO )
    {
        boolean updateResult = false;
        Session objSession   = null;
        
        try
        {
            logger.info("UPDATING WEATHER ZONE DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update( objWeatherZonesVO );
            objSession.getTransaction().commit();
            updateResult = true;
            objSession.flush();
            logger.info("WEATHER ZONE DETAILS ARE UPDATED");
        }
        catch( HibernateException e )
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE WEATHER ZONE DETAILS", e);
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
        //System.out.println(((List)new WeatherZonesDAO().getAllWeatherZones().get("Records")).size());
        //Filter objFi = new Filter();
        //objFi.setFieldName("congestionZone.congestionZone");
        //objFi.setFieldValue("N");
        //objFi.setSpecialFunction(HibernateUtil.STARTS_WITH);
        Filter[] obj = new Filter[0];
        //obj[0] = objFi;
        Filter o = new Filter();
        o.setFieldName("congestionZone");
        o.setFieldValue("S");
        o.setSpecialFunction(HibernateUtil.STARTS_WITH);
        //HashMap hm = new WeatherZonesDAO().getAllWeatherZones(obj,5,"weatherZone",true,0,10);
        //System.out.println("TotalRecord:"+((Integer)hm.get("TotalRecordCount")).intValue());
        new WeatherZonesDAO().getAllWeatherZones();
    }
}

/*
*$Log: WeatherZonesDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.12  2007/09/04 07:05:18  sramasamy
*Log message is added for log file.
*
*Revision 1.11  2007/08/23 07:27:53  jnadesan
*imports organized
*
*Revision 1.10  2007/08/09 15:08:07  sramasamy
*Make Valid/invalid option provided
*
*Revision 1.9  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.8  2007/04/20 07:12:03  kduraisamy
*order by name added.
*
*Revision 1.7  2007/04/17 06:25:17  kduraisamy
*dlfcode set removed from TDSP.
*
*Revision 1.6  2007/03/30 11:41:09  rraman
*getAllWeatherZones() return type changed to list.
*
*Revision 1.5  2007/03/26 05:18:24  kduraisamy
*imports organized.
*
*Revision 1.4  2007/03/26 05:07:12  kduraisamy
*filter added for congestion Zone also.
*
*Revision 1.3  2007/03/26 04:58:20  kduraisamy
*filter added for congestion Zone also.
*
*Revision 1.2  2007/02/02 05:51:51  kduraisamy
*imports organized.
*
*Revision 1.1  2007/02/02 05:50:53  kduraisamy
*filter method inlcuded.initial commit.
*
*
*/