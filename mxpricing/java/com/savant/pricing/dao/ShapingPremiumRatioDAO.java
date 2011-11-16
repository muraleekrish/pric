/*
 * Created on Mar 8, 2007
 *
 * ClassName	:  	ShapingPremiumRatioDAO.java
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
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.savant.pricing.calculation.valueobjects.ShapingPremiumRatiosVO;
import com.savant.pricing.common.HibernateUtil;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ShapingPremiumRatioDAO
{
    private static Logger logger = Logger.getLogger(ShapingPremiumRatioDAO.class);
    private static List shapingPremiumRatiosVOS = null;
    
    public static void getAllShapingPremiumRatios()
    {
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL SHAPING PREMIUM RATIOS");
            shapingPremiumRatiosVOS = new ArrayList();
            objSession = HibernateUtil.getSession();
            shapingPremiumRatiosVOS = objSession.createCriteria(ShapingPremiumRatiosVO.class).list();
            logger.info("GOT ALL SHAPING PREMIUM RATIOS");
        }
        catch(Exception e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL SHAPING PREMIUM RATIOS", e);
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
    
    public static float getRatio(String loadProfile, int congestionZoneId, int priceBlockId, int month)
    {
        logger.info("GETTING RATIO BY LOAD PROFILE, CONGESTION ZONE ID, PRICE BLOCKMID AND MONTH");
        Iterator itr = shapingPremiumRatiosVOS.iterator();
        float ratio = 0;
        while(itr.hasNext())
        {
            ShapingPremiumRatiosVO objShapingPremiumRatiosVO = (ShapingPremiumRatiosVO)itr.next();
            if(objShapingPremiumRatiosVO.getLoadProfile().equals(loadProfile) && objShapingPremiumRatiosVO.getCongestionZone().getCongestionZoneId()==congestionZoneId && objShapingPremiumRatiosVO.getPriceBlock().getPriceBlockIdentifier() == priceBlockId && objShapingPremiumRatiosVO.getMonth() == month)
            {
                ratio = objShapingPremiumRatiosVO.getRatio();
                break;
            }
        }
        logger.info("GOT RATIO BY LOAD PROFILE, CONGESTION ZONE ID, PRICE BLOCKMID AND MONTH");
        return ratio;
    }
}

/*
*$Log: ShapingPremiumRatioDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/09/04 07:05:18  sramasamy
*Log message is added for log file.
*
*Revision 1.4  2007/07/05 14:00:21  kduraisamy
*Prerequisites object re initialized.
*
*Revision 1.3  2007/03/15 12:47:38  kduraisamy
*necessary break added inside if.
*
*Revision 1.2  2007/03/09 04:14:59  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.1  2007/03/08 16:32:44  kduraisamy
*Optimization with Sriram Completed.
*
*
*/