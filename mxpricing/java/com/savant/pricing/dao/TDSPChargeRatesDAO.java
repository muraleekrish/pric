/*
 * Created on Apr 11, 2007
 *
 * ClassName	:  	TDSPChargeRatesDAO.java
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
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.calculation.valueobjects.TDSPChargeRatesVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.RateCodesVO;


/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TDSPChargeRatesDAO
{
    private static Logger logger = Logger.getLogger(TDSPChargeRatesDAO.class);
    
    public Collection getAllTDSPChargeRates(int tdspId, String rateCode)
    {
        Session objSession = null;
        Criteria objCriteria = null;
        Criteria objCriteriaRateCodes = null;
        List objList = null;
        List tdspChargeRates = null;
        TDSPChargeRatesDAO objTDSPChargeRatesDAO = new TDSPChargeRatesDAO();
        try
        {
            logger.info("GETTING ALL TDSP CHARGE RATES BY TDSP ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(RateCodesVO.class);
            objCriteriaRateCodes = objCriteria.createCriteria("tdsp");
            objCriteriaRateCodes.add(Restrictions.eq("tdspIdentifier",new Integer(tdspId)));
            objCriteria.add(Restrictions.eq("rateCode",rateCode));
            objList = objCriteria.list();
            Iterator itr = objList.iterator();
            if(itr.hasNext())
            {
                RateCodesVO objRateCodesVO = (RateCodesVO)itr.next();
                tdspChargeRates = (List)objTDSPChargeRatesDAO.getAllChargeRates(objRateCodesVO.getRateCodeIdentifier());
            }
            logger.info("GOT ALL TDSP CHARGE RATES BY TDSP ID");
        }
        catch(Exception e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL TDSP CHARGE RATES", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return tdspChargeRates;
    }
    
    public Collection getAllChargeRates(int rateCodeId)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL CHARGE RATES BY RATE CODE ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(TDSPChargeRatesVO.class).add(Restrictions.eq("tdspRateCode.rateCodeIdentifier", new Integer(rateCodeId)));
            objList = objCriteria.list();
            logger.info("GOT ALL CHARGE RATES BY RATE CODE ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CHARGE RATES BY RATE CODE ID", e);
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
    
    public TDSPChargeRatesDAO()
    {
    }
    
    public static void main(String[] args)
    {
        if(BuildConfig.DMODE)
            System.out.println(new TDSPChargeRatesDAO().getAllChargeRates(3));
    }
}

/*
*$Log: TDSPChargeRatesDAO.java,v $
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
*Revision 1.4  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.3  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.2  2007/04/19 11:13:57  kduraisamy
*Set is changed to List.
*
*Revision 1.1  2007/04/12 05:59:30  kduraisamy
*initial commit.
*
*
*/