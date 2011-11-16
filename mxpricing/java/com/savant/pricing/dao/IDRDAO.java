/*
 * Created on May 31, 2007
 *
 * ClassName	:  	IDRDAO.java
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.transferobjects.HourValueDetails;


/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IDRDAO
{
    private static Logger logger = Logger.getLogger(IDRDAO.class);
    
    public HashMap getIDRProfileDetails(String esiId)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Session objSession = null;
        List objList = null;
        HashMap hmResult = new HashMap();
        try
        {
            logger.info("GETTING IDR PROFILE DETAILS FOR THE ESIID");
            objSession = HibernateUtil.getSession();
            objList = objSession.createQuery("select idrVo.month, idrVo.hour, idrVo.value  from IDRVO as idrVo where idrVo.esiId = ? and dayType.dayTypeId = ? order by idrVo.month, idrVo.hour").setString(0, esiId).setInteger(1,2).list();
            
            Iterator itr = objList.iterator();
            LinkedHashMap hmIDRDetails = new LinkedHashMap();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                int month = (new Double(String.valueOf(innerRow[0]))).intValue();
                int hour = (new Double(String.valueOf(innerRow[1]))).intValue();
                if(hmIDRDetails.containsKey(new Integer(month)))
                {
                    List objHourDetailsList = (List)hmIDRDetails.get(new Integer(month));
                    HourValueDetails objHourValueDetails = new HourValueDetails();
                    objHourValueDetails.setHour(hour);
                    if(null != innerRow[2])
                    objHourValueDetails.setValue(((Float)(innerRow[2])).floatValue());
                    objHourDetailsList.add(objHourValueDetails);
                }
                else
                {
                    List objHourDetailsList = new ArrayList();
                    HourValueDetails objHourValueDetails = new HourValueDetails();
                    objHourValueDetails.setHour(hour);
                    if(null != innerRow[2])
                    objHourValueDetails.setValue(((Float)(innerRow[2])).floatValue());
                    objHourDetailsList.add(objHourValueDetails);
                    hmIDRDetails.put(new Integer(month), objHourDetailsList);
                }
            }
            hmResult.put(new Integer(2), hmIDRDetails);
            objList = objSession.createQuery("select idrVo.month, idrVo.hour, idrVo.value  from IDRVO as idrVo where idrVo.esiId = ? and dayType.dayTypeId = ? order by idrVo.month, idrVo.hour").setString(0, esiId).setInteger(1,3).list();
            itr = objList.iterator();
            hmIDRDetails = new LinkedHashMap();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                int month = (new Double(String.valueOf(innerRow[0]))).intValue();
                int hour = (new Double(String.valueOf(innerRow[1]))).intValue();
                if(hmIDRDetails.containsKey(new Integer(month)))
                {
                    List objHourDetailsList = (List)hmIDRDetails.get(new Integer(month));
                    HourValueDetails objHourValueDetails = new HourValueDetails();
                    objHourValueDetails.setHour(hour);
                    if(null != innerRow[2])
                    objHourValueDetails.setValue(((Float)(innerRow[2])).floatValue());
                    objHourDetailsList.add(objHourValueDetails);
                }
                else
                {
                    List objHourDetailsList = new ArrayList();
                    HourValueDetails objHourValueDetails = new HourValueDetails();
                    objHourValueDetails.setHour(hour);
                    if(null != innerRow[2])
                    objHourValueDetails.setValue(((Float)(innerRow[2])).floatValue());
                    objHourDetailsList.add(objHourValueDetails);
                    hmIDRDetails.put(new Integer(month), objHourDetailsList);
                }
            }
            hmResult.put(new Integer(3),hmIDRDetails);
            logger.info("GOT IDR PROFILE DETAILS FOR THE ESIID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET IDR PROFILE DETAILS FOR THE ESIID", e);
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
    
    public Collection getAllEsiids()
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL ESIIDs");
            objSession = HibernateUtil.getSession();
            objList = objSession.createQuery("select DISTINCT idrVo.esiId from IDRVO as idrVo ORDER BY idrVo.esiId").list();
            logger.info("GOT ALL ESIIDs");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ESIIDs", e);
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
            System.out.println(new IDRDAO().getAllEsiids());
    }
}

/*
*$Log: IDRDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.9  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.8  2007/06/13 04:10:44  kduraisamy
*2005 jar included.
*
*Revision 1.7  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.6  2007/06/12 05:18:46  kduraisamy
*for sqlserver 2005 data types changed.
*
*Revision 1.5  2007/06/01 07:24:41  jnadesan
*profile details for single esiid
*
*Revision 1.4  2007/06/01 06:02:03  jnadesan
*method to get all esiid and profiles from idr and nidr table
*
*Revision 1.3  2007/06/01 05:57:24  kduraisamy
*null value checked before applying parseFloat.
*
*Revision 1.2  2007/05/31 06:45:55  kduraisamy
*IDR NIDR VIEW ADDED.GRAPH.
*
*Revision 1.1  2007/05/31 06:07:17  kduraisamy
*IDR NIDR VIEW ADDED.GRAPH.
*
*
*/