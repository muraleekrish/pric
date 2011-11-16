/*
 * Created on Mar 8, 2007
 *
 * ClassName	:  	PriceBlockDAO.java
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
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.calculation.valueobjects.PriceBlockHeaderVO;
import com.savant.pricing.calculation.valueobjects.PriceBlockVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PriceBlockDAO
{
    private static Logger logger = Logger.getLogger(PriceBlockDAO.class);
    private static List priceBlockVOS = new ArrayList();
    
    public static void getAllPriceBlocks()
    {
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL PRICE BLOCKS");
            objSession = HibernateUtil.getSession();
            priceBlockVOS = objSession.createCriteria(PriceBlockVO.class).list();
            logger.info("GOT ALL PRICE BLOCKS");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL PRICE BLOCKS", e);
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
    
    public static List getPriceBlocks()
    {
        return priceBlockVOS;
    }
    
    public List getAllPriceBlock()
    {
        List priceBlockHeaderVos =null;
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL PRICE BLOCK");
            objSession = HibernateUtil.getSession();
            priceBlockHeaderVos = objSession.createCriteria(PriceBlockHeaderVO.class).list();
            logger.info("GOT ALL PRICE BLOCK");
        }
        catch(Exception e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL PRICE BLOCK", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return priceBlockHeaderVos;
    }
    
    public PriceBlockHeaderVO getpriceBlck(int blckId)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria = null;
        Session objSession = null;
        PriceBlockHeaderVO objPriceBlockHeaderVO = null;
        try
        {
            logger.info("GETTING PRICE BLOCK FOR BLOCK ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(PriceBlockHeaderVO.class).add(Restrictions.eq("priceBlockIdentifier", new Integer(blckId)));
            objPriceBlockHeaderVO = (PriceBlockHeaderVO)objCriteria.uniqueResult();
            logger.info("GOT PRICE BLOCK FOR BLOCK");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET PRICE BLOCK FOR BLOCK", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objPriceBlockHeaderVO;
    }
    
    public int getPriceBlockIdByName(String priceBlockName)
    {
        Session objSession = null;
        int priceBlockId = 0;
        try
        {
            logger.info("GETTING PRICE BLOCK ID BY NAME");
            objSession = HibernateUtil.getSession();
            List objList = objSession.createCriteria(PriceBlockHeaderVO.class).add(Restrictions.eq("priceBlock", priceBlockName)).list();
            Iterator itr = objList.iterator();
            if(itr.hasNext())
            {
                PriceBlockHeaderVO objPriceBlockHeaderVO = (PriceBlockHeaderVO)itr.next();
                priceBlockId = objPriceBlockHeaderVO.getPriceBlockIdentifier();
                logger.info("GOT PRICE BLOCK ID BY NAME");
            }
            else
            {
                logger.info("NO PRICE BLOCK ID");
            }
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET PRICE BLOCK ID BY NAME", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return priceBlockId;
    }
    

public static void main(String args[])
{
     PriceBlockDAO.getAllPriceBlocks();
    if(BuildConfig.DMODE)
        System.out.println(new PriceBlockDAO().getpriceBlck(1).getPriceBlock());
}

}

/*
*$Log: PriceBlockDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/17 05:46:35  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.7  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.6  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.5  2007/06/02 09:35:33  jnadesan
*taking block vo method included
*
*Revision 1.4  2007/03/21 10:26:49  jnadesan
*Price block id taken by name.
*
*Revision 1.3  2007/03/17 14:02:41  jnadesan
*priceblock header taken
*
*Revision 1.2  2007/03/14 11:45:21  kduraisamy
*imports organized.
*
*Revision 1.1  2007/03/08 16:32:44  kduraisamy
*Optimization with Sriram Completed.
*
*
*/