/*
 * Created on Apr 8, 2007
 *
 * ClassName	:  	HolidayDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.savant.pricing.calculation.valueobjects.HolidayVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HolidayDAO
{
    private static Logger logger = Logger.getLogger(HolidayDAO.class);
    
    public HolidayDAO()
    {
    }
    
    public boolean addHolidays(HolidayVO objHolidayVO) throws ConstraintViolationException,HibernateException
    {
        boolean addResult = false;
        Session objSession = null;
        try
        {
            logger.info("ADDING HOLIDAY");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.save(objHolidayVO);
            objSession.getTransaction().commit();
            addResult= true;
            logger.info("HOLIDAY IS ADDED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD THE HOLIDAY", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
            if(e.toString().indexOf("could not insert")>0)
            {
                throw new HibernateException("Date already exist");
            }
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return addResult;
    }
    
    public boolean updateHolidays(HolidayVO objHolidayVO)
    {        
        boolean updateResult = false;
        Session objSession = null;
        try
        {
            logger.info("UPDATING HOLIDAY");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update(objHolidayVO);
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("HOLIDAY IS UPDATEED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE HOLIDAY", e);
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
    
    public HolidayVO getHolidays(Date hldDate)
    {
        HolidayVO objHolidayVO = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING HOLIDAYS");
            objSession = HibernateUtil.getSession();
            objHolidayVO = (HolidayVO)objSession.createCriteria(HolidayVO.class).add(Restrictions.eq("date",hldDate)).uniqueResult();
            logger.info("GOT HOLIDAYS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE HOLIDAYS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET THE HOLIDAYS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objHolidayVO;
    }
    
    public boolean deleteHolidays(HolidayVO objHolidayVO) throws SQLException
    {
        boolean deleteResult = false;
        Session objSession = null;
        try
        {
            logger.info("DELETING HOLIDAY");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.delete(objHolidayVO);
            objSession.getTransaction().commit();
            deleteResult = true; 
            logger.info("HOLIDAY IS DELETED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DELETE THE HOLIDAY", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
            throw new HibernateException("");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING DELETE THE HOLIDAY", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
            throw new SQLException();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return deleteResult;
    }
    
    public HashMap getAllHolidays(Filter filter, Date fromDate, Date toDate, String sortBy, boolean ascending, int startIndex, int displayCount)
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
            logger.info("GETTING ALL HOLIDAYS");
            if(BuildConfig.DMODE)
            {
                System.out.println("fromDate :"+fromDate);
                System.out.println("toDate :"+toDate);
            }
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(HolidayVO.class);
            if(filter != null)
            {
                objCriteria.add(Restrictions.like(filter.getFieldName(),filter.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filter.getSpecialFunction())));
            }
            if(fromDate != null && toDate != null)
            {
                objCriteria.add(Restrictions.between("date", fromDate, toDate));
            }
            else if(fromDate != null)
            {
                objCriteria.add(Restrictions.ge("date", fromDate));
            }
            else if(toDate != null)
            {
                objCriteria.add(Restrictions.le("date", toDate));
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
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",objList);
            logger.info("GOT ALL HOLIDAYS");
            
            if(BuildConfig.DMODE)
                System.out.println("Records :"+hmResult);
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL HOLIDAYS", e);
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
    
    public HashMap getHolidaysAfter()
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
        Date currDate = new Date();
        HashMap hmResult = new HashMap();
        try
        {
            logger.info("GETTING ALL HOLIDAYS AFTER CURRENT DAY");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(HolidayVO.class);
            objCriteria.add(Restrictions.ge("date", currDate));
            totRecordCount = new Integer(objCriteria.list().size());
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",objList);
            logger.info("GOT ALL HOLIDAYS AFTER CUURENT DAY");
            
            if(BuildConfig.DMODE)
                System.out.println("Records :"+hmResult);
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL HOLIDAYS", e);
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
    
    public static void main(String[] args) throws ParseException
    {
       //HashMap hm = new HolidayDAO().getAllHolidays(null,null,null,"date", true, 0, 10);
        String hldDate = "07-24-2005";
        String reason = "";
        HolidayDAO obj = new HolidayDAO();
        HolidayVO holVO = new HolidayVO();
    	SimpleDateFormat sdf =  new SimpleDateFormat("MM-dd-yyyy");
    	if(BuildConfig.DMODE)
    	    System.out.println(new HolidayDAO().getHolidays(sdf.parse(hldDate)));
        
    	
    	/* System.out.println(new HolidayDAO().getHolidays(sdf.parse(hldDate))+" holiday ");
    	 holVO = obj.getHolidays(sdf.parse(hldDate));
    	 reason = holVO.getReason();
    	 System.out.println(" Reason : "+reason);*/
    	obj.getHolidaysAfter();
       //System.out.println("hm :"+hm);
    }
}

/*
*$Log: HolidayDAO.java,v $
*Revision 1.2  2008/01/29 07:02:35  tannamalai
*new method added to get holidays after current day
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.13  2007/09/04 05:23:55  spandiyarajan
*removed unwanted imports
*
*Revision 1.12  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.11  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.10  2007/06/12 10:19:46  spandiyarajan
*bug fixed
*
*Revision 1.9  2007/06/08 07:16:34  spandiyarajan
*holidays date bug fixed
*
*Revision 1.8  2007/06/08 04:54:58  spandiyarajan
**** empty log message ***
*
*Revision 1.7  2007/05/22 08:00:00  kduraisamy
*save or update method added.
*
*Revision 1.6  2007/04/19 06:46:28  kduraisamy
*getHoliday() error rectified.
*
*Revision 1.5  2007/04/19 06:33:45  kduraisamy
*methods overviewed and made some small changes.
*
*Revision 1.4  2007/04/19 06:28:30  spandiyarajan
*modified uservo to holidayvo
*
*Revision 1.3  2007/04/19 04:13:18  spandiyarajan
*pccaledndar(holiday) add/modify/delete functionality initially added
*
*Revision 1.2  2007/04/09 05:23:47  spandiyarajan
*added buildconfig
*
*Revision 1.1  2007/04/08 14:32:56  kduraisamy
*initial commit.
*
*
*/