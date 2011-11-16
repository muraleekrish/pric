/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	TDSPRateCodesDAO.java
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
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.TDSPRateCodesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TDSPRateCodesDAO
{
    private static Logger logger = Logger.getLogger(TDSPRateCodesDAO.class);

    public HashMap getAllTDSPRateCodes()
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
            logger.info("GETTING ALL TDSP RATE CODES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(TDSPRateCodesVO.class);
            totRecordCount = new Integer(objCriteria.list().size());
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount", totRecordCount);
            hmResult.put("Records", objList);
            logger.info("GOT ALL TDSP RATE CODES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL TDSP RATE CODES", e);
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
    
    public int getServiceVoltageId(int rateCodeId)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Session objSession = null;
        int serviceVoltageId = 0;
        TDSPRateCodesVO objTDSPRateCodesVO = null;
        try
        {
            logger.info("GETTING SERVICE VOLTAGE ID BY RATE CODE ID");
            objSession = HibernateUtil.getSession();
            objTDSPRateCodesVO = (TDSPRateCodesVO)objSession.get(TDSPRateCodesVO.class, new Integer(rateCodeId));
            serviceVoltageId = objTDSPRateCodesVO.getServiceVoltage().getServiceVoltageIdentifier();
            logger.info("GOT SERVICE VOLTAGE ID BY RATE CODE ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET SERVICE VOLTAGE ID BY RATE CODE ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return serviceVoltageId;
    }
    
    public Collection getAllTDSPRateCodes(int rateCodeId)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL TDSP RATE CODES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(TDSPRateCodesVO.class).add(Restrictions.eq("rateCodeIdentifier", new Integer(rateCodeId)));
            objList = objCriteria.list();
            logger.info("GOT ALL TDSP RATE CODES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL TDSP RATE CODES", e);
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
    
    public static void main(String args[])
    {
        
        new TDSPRateCodesDAO().getAllTDSPRateCodes(1);
        /*List objList = (List)new TDSPRateCodesDAO().getAllTDSPRateCodes().get("Records");
        TDSPRateCodesVO[] objTDSPRateCodesVOList = new TDSPRateCodesVO[objList.size()];
        
        objList.toArray(objTDSPRateCodesVOList);
        
        for(int i=0;i<objTDSPRateCodesVOList.length;i++)
        {
            TDSPRateCodesVO obj = new TDSPRateCodesVO();
            obj = objTDSPRateCodesVOList[i];
            System.out.println("Rate code:"+obj.getRateCode().getRateCode());
            System.out.println("Rate class:"+obj.getRateCode().getRateClass());
            System.out.println("desc:"+obj.getRateCode().getDescription());
            System.out.println("Category:"+obj.getMeterCategory().getMeterCategory());
            System.out.println("service Voltage:"+obj.getServiceVoltage().getServiceVoltageType());
            System.out.println("Scud Percentage:"+obj.getScudPercentage());
        }*/
    }
    

}

/*
*$Log: TDSPRateCodesDAO.java,v $
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
*Revision 1.5  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.4  2007/04/19 11:13:57  kduraisamy
*Set is changed to List.
*
*Revision 1.3  2007/04/06 12:32:45  kduraisamy
*TLF AND DLF COMPLETED.
*
*Revision 1.2  2007/02/13 14:05:17  kduraisamy
*BillingDemand related mapping completed.
*
*Revision 1.1  2007/02/02 15:15:27  kduraisamy
*initial commit.
*
*
*/