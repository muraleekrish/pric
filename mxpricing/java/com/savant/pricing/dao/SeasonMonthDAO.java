/*
 * Created on Apr 19, 2007
 *
 * ClassName	:  	SeasonMonthDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.calculation.valueobjects.SeasonMonthVO;
import com.savant.pricing.common.HibernateUtil;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SeasonMonthDAO
{
    private static Logger logger = Logger.getLogger(SeasonMonthDAO.class);
    
    public SeasonMonthDAO()
    {
    }
    
    public Collection getAllSeasonMonths(int seasonId)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL SEASON MONTHS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(SeasonMonthVO.class).add(Restrictions.eq("season.seasonId", new Integer(seasonId)));
            objList = objCriteria.list();
            logger.info("GOT ALL SEASON MONTHS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL SEASON MONTHS", e);
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
}

/*
*$Log: SeasonMonthDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/09/04 07:05:18  sramasamy
*Log message is added for log file.
*
*Revision 1.1  2007/04/19 13:27:51  kduraisamy
*Set is changed to List.
*
*
*/