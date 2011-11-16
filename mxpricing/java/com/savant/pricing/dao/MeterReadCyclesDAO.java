/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	MeterReadCyclesDAO.java
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
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.MeterReadCyclesVO;
import com.savant.pricing.valueobjects.TDSPVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MeterReadCyclesDAO
{
    private static Logger logger = Logger.getLogger(MeterReadCyclesDAO.class);

    public HashMap getAllMeterReadDates(int year, int tdspId, String cycle, int startIndex, int displayCount)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
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
            objCriteria = objSession.createCriteria(MeterReadCyclesVO.class);
            if(tdspId != 0)
            {
                objCriteria.add(Restrictions.eq("tdsp.tdspIdentifier", new Integer(tdspId)));
            }
            objCriteria.add(Restrictions.ge("monthYear",gc.getTime())).add(Restrictions.lt("monthYear",gcEnd.getTime()));
            if(cycle.length()>0)
            {
                objCriteria.add(Restrictions.eq("cycle", cycle));
            }
            objCriteria.addOrder(Order.asc("cycle"));
            objCriteria.addOrder(Order.asc("monthYear"));
            totRecordCount = new Integer(objCriteria.list().size());
            
            objCriteria.setFirstResult(startIndex*12);
            objCriteria.setMaxResults(displayCount*12);
            
            objList = objCriteria.list();
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                MeterReadCyclesVO objMeterReadCyclesVO = (MeterReadCyclesVO)itr.next();
                String key = year+":"+objMeterReadCyclesVO.getTdsp().getTdspIdentifier()+":"+objMeterReadCyclesVO.getTdsp().getTdspName()+":"+objMeterReadCyclesVO.getCycle();
                if(hmRecords.containsKey(key))
                {
                    List obj = (List)hmRecords.get(key);
                    obj.add(objMeterReadCyclesVO.getReadDate());
                }
                else
                {
                    List newList = new ArrayList();
                    newList.add(objMeterReadCyclesVO.getReadDate());
                    hmRecords.put(key, newList);
                }
            }
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",hmRecords);
            logger.info("GOT ALL METER READ DATES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL METER READ DATES", e);
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
    
    public HashMap getMeterReadDates(int tdspId, String cycle, int year)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        Integer totRecordCount = null;
        GregorianCalendar gc = new GregorianCalendar(year,0,1);
        GregorianCalendar gcEnd = new GregorianCalendar();
        gcEnd.setTime(gc.getTime());
        gcEnd.add(Calendar.YEAR,1);
        HashMap hmResult = new HashMap();
        
        try
        {
            logger.info("GETTING METER READ DATES BY TDSP ID AND CYCLE");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MeterReadCyclesVO.class);
            if(tdspId != 0)
            {
                objCriteria.add(Restrictions.eq("tdsp.tdspIdentifier", new Integer(tdspId)));
            }
            objCriteria.add(Restrictions.ge("monthYear",gc.getTime())).add(Restrictions.lt("monthYear",gcEnd.getTime()));
            if(!cycle.equalsIgnoreCase("all")) //changed by sri
            {
                objCriteria.add(Restrictions.eq("cycle", cycle));
            }
            objCriteria.addOrder(Order.asc("cycle"));
            objCriteria.addOrder(Order.asc("monthYear"));
            objList = objCriteria.list();
            totRecordCount = new Integer(objList.size());
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",objList);
            logger.info("GOT METER READ DATES BY TDSP ID AND CYCLE");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET METER READ DATES BY TDSP ID AND CYCLE", e);
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
    public List getAllMeterReadYears()
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
            logger.info("GETTING ALL METER READ DATES");
            objSession = HibernateUtil.getSession();
            objList = objSession.createSQLQuery("select DISTINCT YEAR(Mnth_Yr) from PRC_Mtr_Read_Cycle ORDER BY 1").list();
            logger.info("GOT ALL METER READ DATES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL METER READ DATES", e);
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
    
    public Collection getAllMeterReadCycles(int tdspId)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Session objSession = null;
        Criteria objCriteria = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL METER READ CYCLES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MeterReadCyclesVO.class).add(Restrictions.eq("tdsp.tdspIdentifier", new Integer(tdspId)));
            objList = objCriteria.list();
            logger.info("GOT ALL METER READ CYCLES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL METER READ CYCLES", e);
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
    
    public HashMap getAllReadCycles(int tdspId)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Session objSession = null;
        LinkedHashMap hmResult = new LinkedHashMap();
        try
        {
            logger.info("GETTING ALL METER READ CYCLES BY TDSP ID");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select distinct cycle from MeterReadCyclesVO as meterReads where meterReads.tdsp.tdspIdentifier = ? order by meterReads.cycle");
            objQuery.setInteger(0,tdspId);
            Iterator itr = objQuery.iterate();
            while(itr.hasNext())
            {
                Object obj = itr.next();
                if(!obj.toString().equals("")) // added for test
                	hmResult.put( obj, obj );
            }
            logger.info("GOT ALL METER READ CYCLES BY TDSP ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL METER READ CYCLES BY TDSP ID", e);
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
    
    public MeterReadCyclesVO getMeterReadCycles(int tdspId, String meterReadId, Date mnthYr)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        MeterReadCyclesVO objMeterReadCyclesVO = null;
        List objList = null;
        try
        {
            logger.info("GETTING METER READ CYCLES BY TDSP ID AND METER READ ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MeterReadCyclesVO.class).add(Restrictions.eq("cycle",meterReadId));
            if(tdspId!=0)
            {
                objCriteria.add(Restrictions.eq("tdsp.tdspIdentifier",new Integer(tdspId)));
            }
            objCriteria.add(Restrictions.eq("monthYear",mnthYr));
            objList = objCriteria.list();
            Iterator itr = objList.iterator();
            if(itr.hasNext())
            {
                objMeterReadCyclesVO = (MeterReadCyclesVO)itr.next();
            }
            logger.info("GOT METER READ CYCLES BY TDSP ID AND METER READ ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET METER READ CYCLES BY TDSP ID AND METER READ ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objMeterReadCyclesVO;
    }
    
    public boolean updateMeterRead(int tdspId, String meterReadId, Date mnthYr, Date readDate)
    {
        logger.info("UPDATING METER READ");
        boolean updateResult = false;
        MeterReadCyclesVO objMeterReadCyclesVO = this.getMeterReadCycles(tdspId,meterReadId,mnthYr);
        if(objMeterReadCyclesVO==null)
        {
            objMeterReadCyclesVO = new MeterReadCyclesVO();
            TDSPVO objTDSPVO = new TDSPVO();
            objTDSPVO.setTdspIdentifier(tdspId);
            objMeterReadCyclesVO.setTdsp(objTDSPVO);
            if(meterReadId!=null)
                objMeterReadCyclesVO.setCycle(meterReadId);
            objMeterReadCyclesVO.setMonthYear(mnthYr);
        }
        objMeterReadCyclesVO.setReadDate(readDate);
        updateResult = this.addorUpdateMtrReadCycle(objMeterReadCyclesVO);
        logger.info("METER READ IS UPDATED");
        return updateResult;
    }
    
    public boolean addorUpdateMtrReadCycle(MeterReadCyclesVO objMeterReadCyclesVO)
    {
        boolean addResult = false;
        Session objSession = null;
        try
        {
            logger.info("ADDING OR UPDATING METER READ CYCLE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.saveOrUpdate(objMeterReadCyclesVO);
            objSession.getTransaction().commit();
            addResult= true;
            logger.info("METER READ CYCLE IS UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD OR UPDATE THE METER READ CYCLE", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
            throw new HibernateException("MeterReadCycles does not saved.");
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
    
    public static void main(String args[])
    {
        /*boolean result = false;
        Calendar calMtrDate = Calendar.getInstance();
        calMtrDate.set(2008, 8,1);
        Calendar calReadDate = Calendar.getInstance();
        calReadDate.set(2008, 8,2);*/
        
        MeterReadCyclesDAO objMeterReadCyclesDAO = new MeterReadCyclesDAO();
        //result = objMeterReadCyclesDAO.updateMeterRead(6,"14",calMtrDate.getTime(),calReadDate.getTime());
        objMeterReadCyclesDAO.getMeterReadDates(1, "",2007);
        /*if(BuildConfig.DMODE)
            System.out.println("result:"+result);*/
    }
}

/*
*$Log: MeterReadCyclesDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.16  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.15  2007/07/31 11:40:08  kduraisamy
*objsession properly closed or calling procedure properly done.
*
*Revision 1.14  2007/07/31 06:30:45  sramasamy
*entry given for meter read dates add/update
*
*Revision 1.13  2007/07/25 09:02:13  jnadesan
*method name changed
*
*Revision 1.12  2007/07/25 07:33:30  spandiyarajan
*add/update metod add for meterreadcycles
*
*Revision 1.11  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.10  2007/04/20 09:30:52  kduraisamy
*hashMap is changed to linkedHashMap().
*
*Revision 1.9  2007/04/19 13:19:41  jnadesan
*record count changed
*
*Revision 1.8  2007/04/19 07:22:38  kduraisamy
*getAllMeterReadDateYears() added.
*
*Revision 1.7  2007/04/19 07:19:35  kduraisamy
*filter added for meter read dates.
*
*Revision 1.6  2007/04/18 09:34:50  kduraisamy
*set removed.
*
*Revision 1.5  2007/04/12 13:57:51  kduraisamy
*unwanted println commented.
*
*Revision 1.4  2007/03/29 05:44:30  kduraisamy
*getAllReadCycles By TDSP ID() added.
*
*Revision 1.3  2007/03/28 14:42:15  kduraisamy
*getAllMeterReads() Added.
*
*Revision 1.1  2007/03/08 16:32:44  kduraisamy
*Optimization with Sriram Completed.
*
*
*/