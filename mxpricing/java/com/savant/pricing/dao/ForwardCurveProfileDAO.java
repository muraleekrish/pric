/*
 * Created on Mar 7, 2007
 *
 * ClassName	:  	ForwardCurveBlockDAO.java
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.savant.pricing.calculation.valueobjects.ForwardCurveProfileVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.LoadPrerequisites;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ForwardCurveProfileDAO implements LoadPrerequisites
{
    private static Logger logger = Logger.getLogger(ForwardCurveProfileDAO.class);
    private static List forwardCurveProfileVOS = null;
    
    public static void getAllForwardCurves()
    {
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL FORWARD CURVES");
            objSession = HibernateUtil.getSession();
            forwardCurveProfileVOS = new ArrayList();
            forwardCurveProfileVOS = objSession.createCriteria(ForwardCurveProfileVO.class).addOrder(Order.asc("monthYear")).list();
            logger.info("GOT ALL FORWARD CURVES");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL FORWARD CURVES", e); 
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
    }
    
    public List getAllForwardCurveProfiles()
    {
        return forwardCurveProfileVOS;
    }
    
    public static float getPrice(Date marketDate, Date monthYear, int profileTypeId, int weatherZoneId, int dataSrcId)
    {
        logger.info("GETTING PRICE");
        Iterator itr = forwardCurveProfileVOS.iterator();
        float price = 0;
        while(itr.hasNext())
        {
            ForwardCurveProfileVO objForwardCurveProfileVO = (ForwardCurveProfileVO)itr.next(); 
            if(objForwardCurveProfileVO.getMarketDate().equals(marketDate) && objForwardCurveProfileVO.getMonthYear().equals(monthYear) && objForwardCurveProfileVO.getLoadProfile().getProfileIdentifier() == profileTypeId && objForwardCurveProfileVO.getWeatherZone().getWeatherZoneId() == weatherZoneId && objForwardCurveProfileVO.getDataSource().getDataSourceId() == dataSrcId)
            {
                price = objForwardCurveProfileVO.getPrice();
                break;
            }
        }
        logger.info("GOT PRICE");
        return price;
        
    }
    
    public Collection getAllForwardCurveProfiles(int weatherZoneId, int loadProfileTypeId, int dataSourceId)
    {
        List objList = new ArrayList();
        try
        {
            logger.info("GETTING ALL FORWARD CURVE PROFILES BY WEATHER ZONE ID AND LOAD PROFILE TYPE ID");
            Iterator itr = forwardCurveProfileVOS.iterator();
            while(itr.hasNext())
            {
                ForwardCurveProfileVO objForwardCurveProfileVO = (ForwardCurveProfileVO)itr.next();
                if(objForwardCurveProfileVO.getLoadProfile().getProfileIdentifier() == loadProfileTypeId && objForwardCurveProfileVO.getWeatherZone().getWeatherZoneId() == weatherZoneId && objForwardCurveProfileVO.getDataSource().getDataSourceId() == dataSourceId)
                {
                    objList.add(objForwardCurveProfileVO);
                }
            }
            logger.info("GOT ALL FORWARD CURVE PROFILES BY WEATHER ZONE ID AND LOAD PROFILE TYPE ID");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL FORWARD CURVE PROFILES BY WEATHER ZONE ID AND LOAD PROFILE TYPE ID", e);
            e.printStackTrace();
        }
        return objList;
    }
    
    public boolean reload()
    {
        ForwardCurveProfileDAO.getAllForwardCurves();
        return true;
    }
    
    public static void main(String[] args)
    {
        if(BuildConfig.DMODE)
            System.out.println(new ForwardCurveProfileDAO().getAllForwardCurveProfiles(1,1,1));
    }
}

/*
*$Log: ForwardCurveProfileDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.11  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.10  2007/08/03 12:22:12  kduraisamy
*forward curve date is taken from memory.
*
*Revision 1.9  2007/07/05 14:00:21  kduraisamy
*Prerequisites object re initialized.
*
*Revision 1.8  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.7  2007/04/24 04:26:36  kduraisamy
*order by month year added.
*
*Revision 1.6  2007/04/23 10:24:30  kduraisamy
*getAllValid() and invalid() methods added.
*
*Revision 1.5  2007/04/17 15:42:58  kduraisamy
*price run performance took place.
*
*Revision 1.4  2007/03/22 06:40:49  kduraisamy
*imports organized.
*
*Revision 1.3  2007/03/22 06:40:17  kduraisamy
*getAllForwardCurveProfiles() added.
*
*Revision 1.2  2007/03/16 10:35:18  kduraisamy
*dividedByZero Error Corrected.
*
*Revision 1.1  2007/03/09 08:52:34  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.1  2007/03/08 16:32:44  kduraisamy
*Optimization with Sriram Completed.
*
*
*/