/*
 * Created on Apr 11, 2007
 *
 * ClassName	:  	TaxRatesDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.TaxRatesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxRatesDAO
{
    private static Logger logger = Logger.getLogger(TaxRatesDAO.class);
    
    public TaxRatesDAO()
    {
    }
    
    public List getAllTaxRates()
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
            logger.info("GETTING ALL TAX RATES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(TaxRatesVO.class);
            objCriteria.createCriteria("taxType").addOrder(Order.asc("taxType"));
            objList = objCriteria.list();
            logger.info("GOT ALL TAX RATES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL TAX RATES", e);
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
    
    public boolean updateTaxes(List objTaxRatesVOs)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Session objSession = null;
        boolean updateResult = false;
        try
        {
            logger.info("UPDATING TAXES DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            Iterator itr = objTaxRatesVOs.iterator();
            while(itr.hasNext())
            {
                TaxRatesVO objTaxRatesVO = (TaxRatesVO)itr.next();
                objSession.update(objTaxRatesVO);
            }
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("TAXES DETAILS ARE UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE TAXES DETAILS", e);
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
    
    public static void main(String[] args)
    {
        List lstTaxRatesDAO = new TaxRatesDAO().getAllTaxRates();
        if(BuildConfig.DMODE)
            System.out.println("lstTaxRatesDAO:"+lstTaxRatesDAO);
        TaxRatesVO objTaxRatesVO = new TaxRatesVO();
        for(int i=0;i<lstTaxRatesDAO.size();i++)
		{
			objTaxRatesVO = (TaxRatesVO)lstTaxRatesDAO.get(i);
			if(BuildConfig.DMODE)
			{
			    System.out.println("lst:"+objTaxRatesVO.getTaxType().getTaxType());
			    System.out.println("val:"+objTaxRatesVO.getTaxRate());
			}
		}
    }
}

/*
*$Log: TaxRatesDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.7  2007/09/04 07:05:18  sramasamy
*Log message is added for log file.
*
*Revision 1.6  2007/07/31 12:28:42  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.5  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.4  2007/06/11 13:12:12  jnadesan
*unwanted print removed
*
*Revision 1.3  2007/05/08 07:25:00  spandiyarajan
*tax edit functionality finished
*
*Revision 1.2  2007/04/11 14:55:06  spandiyarajan
*tax page initially commited
*
*Revision 1.1  2007/04/11 11:31:35  kduraisamy
*initial commit.
*
*
*/