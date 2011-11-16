/*
 * Created on Jan 30, 2007
 *
 * ClassName	:  	CongestionZonesDAO.java
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

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.CongestionZonesVO;

/**
 * @author kduraisamy
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CongestionZonesDAO
{
    private static Logger logger = Logger.getLogger(CongestionZonesDAO.class);
    
    public HashMap getAllCongestionZones(Filter[] filter, String sortBy, boolean ascending, int startIndex, int displayCount)
    {
        /**
         * Requires - Modifies - Effects -
         * 
         * @throws -
         */
        Criteria objCriteria = null;
        Session objSession = null;
        Integer totRecordCount = null;
        List objList = null;
        HashMap hmResult = new HashMap();
        try
        {
            logger.info("GETTING ALL CONGESTION ZONES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(CongestionZonesVO.class);
            if(filter != null)
            {
                for(int i = 0; i < filter.length; i++)
                {
                    objCriteria.add(Restrictions.like(filter[i].getFieldName(), filter[i].getFieldValue(), (MatchMode) HibernateUtil.htMatchCase.get(filter[i].getSpecialFunction())));
                }
            }
            totRecordCount = new Integer(objCriteria.list().size());
            if(ascending)
            {
                objCriteria.addOrder(Order.asc(sortBy));
            } else
            {
                objCriteria.addOrder(Order.desc(sortBy));
            }
            objCriteria.setFirstResult(startIndex);
            objCriteria.setMaxResults(displayCount);
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount", totRecordCount);
            hmResult.put("Records", objList);
            logger.info("GOT ALL CONGESTION ZONES");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CONGESTION ZONES", e); 
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

    public List getAllCongestionZones()
    {
        List objList = null;
        Session objSession = null;
        Criteria objCriteria = null;
        try
        {
            logger.info("GETTING ALL CONGESTION ZONES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(CongestionZonesVO.class).add(Restrictions.eq("valid", new Boolean(true)));
            objCriteria.addOrder(Order.asc("congestionZone"));
            objList = objCriteria.list();
            logger.info("GOT ALL CONGESTION ZONES");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CONGESTION ZONES", e);
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

    public CongestionZonesVO getCongestionZone(int congestionId)
    {
        Session objSession = null;
        Criteria objCriteria = null;
        CongestionZonesVO objCongestionZonesVO = null;
        try
        {
            logger.info("GETTING CONGESTION ZONE BY CONGESTION ZONE ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(CongestionZonesVO.class).add(Restrictions.eq("congestionZoneId", new Integer(congestionId)));
            objCongestionZonesVO = (CongestionZonesVO) objCriteria.uniqueResult();
            logger.info("GOT CONGESTION ZONE BY CONGESTION ZONE ID");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET CONGESTION ZONE BY CONGESTION ZONE ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objCongestionZonesVO;
    }

    public boolean updateCongestion(CongestionZonesVO objCongestionZonesVO)
    {
        boolean updateResult = false;
        Session objSession = null;
        try
        {
            logger.info("UPDATING CONGESTION ZONE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update(objCongestionZonesVO);
            objSession.getTransaction().commit();
            updateResult = true;
            objSession.flush();
            logger.info("CONGESTION ZONE IS UPDATED");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE CONGESTION ZONE", e);
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
        if(BuildConfig.DMODE)
            /*
             * CongestionZonesDAO objCongestionZonesDAO = new
             * CongestionZonesDAO(); CongestionZonesVO objCongestionZonesVO =
             * objCongestionZonesDAO.getCongestionZone(1);
             * objCongestionZonesVO.setValid(false);
             * objCongestionZonesDAO.updateCongestion(objCongestionZonesVO);
             */
            System.out.println("size:" + new CongestionZonesDAO().getAllCongestionZones().size());
    }
}
/*
 * $Log: CongestionZonesDAO.java,v $
 * Revision 1.1  2007/12/07 06:18:35  jvediyappan
 * initial commit.
 *
 * Revision 1.1  2007/10/30 05:51:43  jnadesan
 * Initial commit.
 *
 * Revision 1.1  2007/10/26 15:19:17  jnadesan
 * initail MXEP commit
 *
 * Revision 1.18  2007/09/25 05:46:13  sramasamy
 * Log Message correction.
 *
 * Revision 1.17  2007/09/04 05:23:55  spandiyarajan
 * removed unwanted imports
 *
 * Revision 1.16  2007/09/03 14:09:42  sramasamy
 * Log message is added for log file.
 *
 * Revision 1.15  2007/08/23 07:27:11  jnadesan
 * format changed
 *
 * Revision 1.14  2007/08/09 15:05:44  sramasamy
 * Make Valid/invalid option provided
 * Revision 1.13 2007/07/11 13:20:42 jnadesan
 * method added to get congestion zonevo for given id
 * 
 * Revision 1.12 2007/06/12 12:56:42 spandiyarajan removed unwanted s.o.p /
 * removed unwanted spaces / removed unwanted codes
 * 
 * Revision 1.11 2007/04/20 07:12:03 kduraisamy order by name added.
 * 
 * Revision 1.10 2007/03/26 05:17:44 kduraisamy filter added for congestion Zone
 * also.
 * 
 * Revision 1.9 2007/03/24 08:06:41 rraman method return value changed from
 * collection to hashmap
 * 
 * Revision 1.8 2007/03/24 07:13:38 kduraisamy congestion Zone Filter changed.
 * 
 * Revision 1.7 2007/03/22 06:40:34 kduraisamy imports organized.
 * 
 * Revision 1.6 2007/03/17 07:04:36 kduraisamy getAllCongestionZones() added.
 * 
 * Revision 1.5 2007/02/02 04:13:47 jnadesan array filter added
 * 
 * Revision 1.4 2007/01/31 09:31:42 kduraisamy filter method added.
 * 
 * Revision 1.3 2007/01/31 07:31:47 kduraisamy filter null checked.
 * 
 * Revision 1.2 2007/01/31 05:09:22 kduraisamy missiing congestionZoneVO.class
 * added.
 * 
 * Revision 1.1 2007/01/30 14:59:39 kduraisamy initial commit.
 * 
 *  
 */