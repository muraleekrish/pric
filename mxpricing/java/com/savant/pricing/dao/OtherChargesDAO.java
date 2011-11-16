/*
 * Created on May 7, 2007
 *
 * ClassName	:  	OtherChargesDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.calculation.valueobjects.EnergyChargeRatesVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OtherChargesDAO
{
    private static Logger logger = Logger.getLogger(OtherChargesDAO.class);
    
    public HashMap getAllMeterReadDates(int year, int profileId, int weathZoneId, int startIndex, int displayCount)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        Integer totRecordCount = null;
        GregorianCalendar gc = new GregorianCalendar(year,0,1);
        GregorianCalendar gcEnd = new GregorianCalendar();
        gcEnd.setTime(gc.getTime());
        gcEnd.add(Calendar.YEAR,1);
        LinkedHashMap hmRecords = new LinkedHashMap();
        HashMap hmResult = new HashMap();
        
        try
        {
            logger.info("GETTING ALL METER READ DATES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(EnergyChargeRatesVO.class);
            if(profileId != 0)
            {
                objCriteria.add(Restrictions.eq("profileType.profileIdentifier", new Integer(profileId)));
            }
            if(BuildConfig.DMODE)
            {
                System.out.println("gc.getTime() :"+gc.getTime());
                System.out.println("gcEnd.getTime() :"+gcEnd.getTime());
            }
            
            objCriteria.add(Restrictions.ge("monthYear",gc.getTime())).add(Restrictions.lt("monthYear",gcEnd.getTime()));
            
            if(weathZoneId != 0)
            {
                objCriteria.add(Restrictions.eq("weatherZone.weatherZoneId", new Integer(weathZoneId)));
            }
            objCriteria.addOrder(Order.asc("monthYear"));
            totRecordCount = new Integer(objCriteria.list().size());
            //objCriteria.setFirstResult(startIndex*12);
            //objCriteria.setMaxResults(displayCount*12);
            objList = objCriteria.list();
            if(BuildConfig.DMODE)
                System.out.println("objList :"+objList);
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                EnergyChargeRatesVO objEnergyChargeRatesVO = (EnergyChargeRatesVO)itr.next();
                if(BuildConfig.DMODE)
                {
                    System.out.println("year :"+year);
	                System.out.println("objEnergyChargeRatesVO.getMonthYear() :"+objEnergyChargeRatesVO.getMonthYear());
	                System.out.println("objEnergyChargeRatesVO.getEnergyChargeName().getEnergyChargeIdentifier() :"+objEnergyChargeRatesVO.getEnergyChargeName().getEnergyChargeIdentifier());
	                System.out.println("objEnergyChargeRatesVO.getProfileType().getProfileIdentifier() :"+objEnergyChargeRatesVO.getProfileType().getProfileIdentifier());
	                System.out.println("objEnergyChargeRatesVO.getWeatherZone().getWeatherZoneId() :"+objEnergyChargeRatesVO.getWeatherZone().getWeatherZoneId());
                }
                String key = year+":"+objEnergyChargeRatesVO.getEnergyChargeName().getEnergyChargeIdentifier()+":"+objEnergyChargeRatesVO.getProfileType().getProfileIdentifier()+":"+objEnergyChargeRatesVO.getWeatherZone().getWeatherZoneId();
                if(hmRecords.containsKey(key))
                {
                    List obj = (List)hmRecords.get(key);
                    obj.add(objEnergyChargeRatesVO.getMonthYear());
                }
                else
                {
                    List newList = new ArrayList();
                    newList.add(objEnergyChargeRatesVO.getMonthYear());
                    hmRecords.put(key, newList);
                }
            }
            hmResult.put("TotalRecordCount",totRecordCount);
            if(BuildConfig.DMODE)
            {
                System.out.println("TotalRecordCount :"+totRecordCount);
                System.out.println("Records :"+hmRecords);
            }
            hmResult.put("Records",hmRecords);
            logger.info("GOT ALL METER READ DATES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE ALL METER READ DATES", e);
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
    
    public List getAllEnrgyChrgeYears()
    {
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL ENERGY CHARGE YEARS");
            objSession = HibernateUtil.getSession();
            objList = objSession.createSQLQuery("select DISTINCT YEAR(Mnth_Yr) from PRC_Enrgy_Chrge_Rates ORDER BY 1").list();
            logger.info("GOT ALL ENERGY CHARGE YEARS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ENERGY CHARGE YEARS", e);
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
    
    public static void main(String[] args)
    {
        if(BuildConfig.DMODE)
            System.out.println("===>"+new OtherChargesDAO().getAllEnrgyChrgeYears());
        new OtherChargesDAO().getAllMeterReadDates(2008,0,0,1,10);
    }
}

/*
*$Log: OtherChargesDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.2  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.1  2007/06/07 11:03:18  spandiyarajan
*other charges partially committed
*
*
*/