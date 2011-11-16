/*
 * Created on May 31, 2007
 *
 * ClassName	:  	NIDRDAO.java
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
import java.util.StringTokenizer;

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
public class NIDRDAO
{
    private static Logger logger = Logger.getLogger(NIDRDAO.class);
    
    public NIDRDAO()
    {
    }
    
    public HashMap getNIDRProfileDetails(String loadProfile)
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
            logger.info("GETTING NIDR PROFILE DETAILS BY LOAD PROFILE");
            objSession = HibernateUtil.getSession();
            String str = "";
            StringTokenizer st = new StringTokenizer(loadProfile.trim(),",");
            while(st.hasMoreTokens())
            {
                if(str.length()<=0)
                    str = "'"+st.nextToken()+"'";
                else
                    str = str + ",'"+st.nextToken()+"'";
            }
            objList = objSession.createQuery("select nidrVo.month, nidrVo.hour, nidrVo.value from NIDRVO as nidrVo where nidrVo.loadProfile = ? and nidrVo.dayType.dayTypeId = ? order by nidrVo.month, nidrVo.hour").setString(0, loadProfile).setInteger(1,2).list();
            Iterator itr = objList.iterator();
            LinkedHashMap hmNIDRDetails = new LinkedHashMap();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                int month = (new Double(String.valueOf(innerRow[0]))).intValue();
                int hour = (new Double(String.valueOf(innerRow[1]))).intValue();
                if(hmNIDRDetails.containsKey(new Integer(month)))
                {
                    List objHourDetailsList = (List)hmNIDRDetails.get(new Integer(month));
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
                    hmNIDRDetails.put(new Integer(month), objHourDetailsList);
                }
            }
            hmResult.put(new Integer(2), hmNIDRDetails);
            objList = objSession.createQuery("select nidrVo.month, nidrVo.hour, nidrVo.value from NIDRVO as nidrVo where nidrVo.loadProfile = ? and nidrVo.dayType.dayTypeId = ? order by nidrVo.month, nidrVo.hour").setString(0, loadProfile).setInteger(1,3).list();
            itr = objList.iterator();
            hmNIDRDetails = new LinkedHashMap();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                int month = (new Double(String.valueOf(innerRow[0]))).intValue();
                int hour = (new Double(String.valueOf(innerRow[1]))).intValue();
                if(hmNIDRDetails.containsKey(new Integer(month)))
                {
                    List objHourDetailsList = (List)hmNIDRDetails.get(new Integer(month));
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
                    hmNIDRDetails.put(new Integer(month), objHourDetailsList);
                }
            }
            hmResult.put(new Integer(3),hmNIDRDetails);
            logger.info("GOT NIDR PROFILE DETAILS BY LOAD PROFILE");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET NIDR PROFILE DETAILS BY LOAD PROFILE", e);
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
    
    public Collection getAllLoadProfiles()
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
            logger.info("GETTING ALL LOAD PROFILES");
            objSession = HibernateUtil.getSession();
            objList = objSession.createQuery("select DISTINCT nidrVo.loadProfile from NIDRVO as nidrVo ORDER BY nidrVo.loadProfile").list();
            logger.info("GOT ALL LOAD PROFILES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL LOAD PROFILES", e);
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
            System.out.println(new NIDRDAO().getAllLoadProfiles());
    }
}

/*
*$Log: NIDRDAO.java,v $
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
*Revision 1.5  2007/06/02 04:47:55  spandiyarajan
*removed unewanted imports
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