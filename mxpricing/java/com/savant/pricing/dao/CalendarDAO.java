/*
 * Created on Mar 8, 2007
 *
 * ClassName	:  	CalendarDAO.java
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.savant.pricing.calculation.valueobjects.CalendarVO;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.LoadPrerequisites;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CalendarDAO implements LoadPrerequisites
{
    private static Logger logger = Logger.getLogger(CalendarDAO.class);
    private static List holidaysVOS = null;
    
    public static void getAllHolidays()
    {
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL HOLIDAYS");
            holidaysVOS = new ArrayList();
            objSession = HibernateUtil.getSession();
            holidaysVOS = objSession.createCriteria(CalendarVO.class).list();
            logger.info("GOT ALL HOLIDAYS");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL HOLIDAYS", e);
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
    public static List getHolidays(Date fromDate, Date toDate)
    {
        logger.info("GETTING HOLIDAYS FOR PARTICULAR PERIOD");
        Iterator itr = holidaysVOS.iterator();
        List objList = new ArrayList();
        while(itr.hasNext())
        {
            CalendarVO objCalendarVO = (CalendarVO)itr.next();
            if(objCalendarVO.getDate().compareTo(fromDate)>=0 && objCalendarVO.getDate().compareTo(toDate)<=0)
            {
                objList.add(objCalendarVO.getDate());
            }
        }
        logger.info("GOT HOLIDAYS FOR PARTICULAR PERIOD");
        return objList;
        
    }
    /* (non-Javadoc)
     * @see com.savant.pricing.common.LoadPrerequisites#reload()
     */
    public boolean reload()
    {
        CalendarDAO.getAllHolidays();
        return true;
    }



}

/*
*$Log: CalendarDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/09/04 05:23:55  spandiyarajan
*removed unwanted imports
*
*Revision 1.4  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.3  2007/07/05 14:00:21  kduraisamy
*Prerequisites object re initialized.
*
*Revision 1.2  2007/04/23 10:24:30  kduraisamy
*getAllValid() and invalid() methods added.
*
*Revision 1.1  2007/03/08 16:32:44  kduraisamy
*Optimization with Sriram Completed.
*
*
*/