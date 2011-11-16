/*
 * Created on Apr 8, 2007
 *
 * ClassName	:  	ProductsDAO.java
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

import com.savant.pricing.calculation.valueobjects.SeasonsVO;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SeasonsDAO
{
    private static Logger logger = Logger.getLogger(SeasonsDAO.class);
    
    public SeasonsDAO()
    {
    }
    
    public HashMap getAllSeasons(Filter filter, String sortBy, boolean ascending, int startIndex, int displayCount)
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
            logger.info("GETTING ALL SEASONS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(SeasonsVO.class);
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
            logger.info("GOT ALL SEASONS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL SEASONS", e);
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
    
    public static void main(String[] args)
    {
    }
}

/*
*$Log: SeasonsDAO.java,v $
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
*Revision 1.5  2007/04/18 03:56:06  kduraisamy
*imports organized.
*
*Revision 1.4  2007/04/17 06:25:17  kduraisamy
*dlfcode set removed from TDSP.
*
*Revision 1.3  2007/04/12 13:57:51  kduraisamy
*unwanted println commented.
*
*Revision 1.2  2007/04/08 14:45:50  rraman
*initial commit for season
*
*Revision 1.1  2007/04/08 12:06:05  kduraisamy
*initial commit.
*
*Revision 1.1  2007/04/08 08:22:31  kduraisamy
*initial commit.
*
*
*/