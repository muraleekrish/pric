/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	RateCodesDAO.java
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
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.transferobjects.TDSPRateCodesDetails;
import com.savant.pricing.valueobjects.RateCodesVO;
import com.savant.pricing.valueobjects.TDSPRateCodesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RateCodesDAO
{
    private static Logger logger = Logger.getLogger(RateCodesDAO.class);
    
    public HashMap getAllRateCodes()
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
            logger.info("GETTING ALL RATE CODES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(RateCodesVO.class);
            totRecordCount = new Integer(objCriteria.list().size());
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",objList);
            logger.info("GOT ALL RATE CODES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL RATE CODES", e);
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
    
    public Collection getAllRateCodes(int tdspId)
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
        try
        {
            logger.info("GETTING ALL RATE CODES BY TDSP ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(RateCodesVO.class).add(Restrictions.eq("tdsp.tdspIdentifier", new Integer(tdspId)));
            objCriteria.addOrder(Order.asc("rateCode"));
            objList = objCriteria.list();
            logger.info("GOT ALL RATE CODES BY TDSP ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL RATE CODES BY TDSP ID", e);
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
    
    public HashMap getAllRateCodes(Filter rateCode, Filter filtRateClass, int tdspId, String sortBy, boolean ascending, int startIndex, int displayCount)
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
            logger.info("GETTING ALL RATE CODES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(RateCodesVO.class);
            if(rateCode != null)
            {
                objCriteria.add(Restrictions.like(rateCode.getFieldName(),rateCode.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(rateCode.getSpecialFunction())));
            }
            if(filtRateClass != null)
            {
                objCriteria.add(Restrictions.like(filtRateClass.getFieldName(),filtRateClass.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filtRateClass.getSpecialFunction())));
            }
            if(tdspId != 0)
            {
                objCriteria.add(Restrictions.eq("tdsp.tdspIdentifier",new Integer(tdspId)));
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
            Iterator itr = objList.iterator();
            List resultList = new ArrayList();
            TDSPRateCodesDetails objTDSPRateCodesDetails = null;
            while(itr.hasNext())
            {
                RateCodesVO objRateCodesVO = (RateCodesVO)itr.next();
                objTDSPRateCodesDetails = new TDSPRateCodesDetails();
                objTDSPRateCodesDetails.setRateCode(objRateCodesVO.getRateCode());
                objTDSPRateCodesDetails.setRateClass(objRateCodesVO.getRateClass());
                objTDSPRateCodesDetails.setDescription(objRateCodesVO.getDescription());
                TDSPRateCodesVO objTDSPRateCodesVO = (TDSPRateCodesVO)objSession.get(TDSPRateCodesVO.class,new Integer(objRateCodesVO.getRateCodeIdentifier()));
                objTDSPRateCodesDetails.setMeterCategory(objTDSPRateCodesVO.getMeterCategory());
                objTDSPRateCodesDetails.setServiceVoltage(objTDSPRateCodesVO.getServiceVoltage());
                objTDSPRateCodesDetails.setScudPercentage(objTDSPRateCodesVO.getScudPercentage());
                resultList.add(objTDSPRateCodesDetails);
            }
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",resultList);
            logger.info("GOT ALL RATE CODES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL RATE CODES", e);
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
    
    public static void main(String args[])
    {
        /*List objList = (List)new RateCodesDAO().getAllRateCodes().get("Records");
        RateCodesVO[] objRateCodesVOList = new RateCodesVO[objList.size()];
        
        objList.toArray(objRateCodesVOList);
        
        for(int i=0;i<objRateCodesVOList.length;i++)
        {
            RateCodesVO obj = new RateCodesVO();
            obj = objRateCodesVOList[i];
            System.out.println("Ratecode:"+obj.getRateCode());
            System.out.println("Rate class:"+obj.getRateClass());
            System.out.println("Desc:"+obj.getDescription());
        }*/
    }
}

/*
*$Log: RateCodesDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.9  2007/09/14 06:54:24  sramasamy
*Log Message Correction.
*
*Revision 1.8  2007/09/04 07:05:18  sramasamy
*Log message is added for log file.
*
*Revision 1.7  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.6  2007/05/18 05:20:55  spandiyarajan
*added sortorder
*
*Revision 1.5  2007/04/17 04:49:39  kduraisamy
*rateCodes set removed from TDSP.
*
*Revision 1.4  2007/04/10 06:09:31  kduraisamy
*ratecode and ratecodeassociation added
*
*Revision 1.3  2007/04/09 17:15:17  spandiyarajan
*ratecode added the filter option
*
*Revision 1.2  2007/04/09 14:13:58  kduraisamy
*getAllTDSPRateCodes() filter method added.
*
*Revision 1.1  2007/02/02 12:19:52  kduraisamy
*initial commit.
*
*
*/