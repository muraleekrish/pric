/*
 * Created on Feb 1, 2008
 *
 * ClassName	:  	PriceRunCostCapitalDAO.java
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
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.calculation.valueobjects.CostOfCapitalVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCostCapitalVO;
import com.savant.pricing.calculation.valueobjects.PriceRunHeaderVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;


/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PriceRunCostCapitalDAO
{
    private static Logger logger = Logger.getLogger(PriceRunCostCapitalDAO.class);
    private static List prcCostCap = null;
    
    public boolean addPrcCostCap(String prcId,Session objSession)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        boolean addResult = false;
        CostOfCapitalDAO obj = new CostOfCapitalDAO();
        PriceRunCostCapitalVO objPriceRunCostCapitalVO = null;
        CostOfCapitalVO objCostOfCapitalVO = new CostOfCapitalVO();
        PriceRunHeaderVO objPriceRunHeaderVO = new PriceRunHeaderVO();
        try
        {
            List lst = new ArrayList();
            lst = obj.getCocs();
            objPriceRunHeaderVO.setPriceRunRefNo(prcId);
            for(int s=0;s<lst.size();s++)
            {
                objCostOfCapitalVO = (CostOfCapitalVO)lst.get(s);
                objPriceRunCostCapitalVO =  new PriceRunCostCapitalVO();
                objPriceRunCostCapitalVO.setCocId(objCostOfCapitalVO);
                objPriceRunCostCapitalVO.setPrcId(objPriceRunHeaderVO);
                objPriceRunCostCapitalVO.setCocValue(objCostOfCapitalVO.getCocValue());
                addResult = this.add(objPriceRunCostCapitalVO, objSession);
            }
            logger.info("UPDATING CUSTOMER ENERGY COMPONENT DETAILS"); 
            addResult = true;
            logger.info("CUSTOMER ENERGY COMPONENT DETAILS ARE UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE CUSTOMER ENERGY COMPONENT", e);
            e.printStackTrace();
        }
        return addResult;
    }
    
    public boolean add(PriceRunCostCapitalVO objPriceRunCostCapitalVO,Session objSession)
    {
        
       // Session objSession = null;
        boolean addResult = false;
        try
        {
          //  objSession = HibernateUtil.getSession();
          //  objSession.beginTransaction();
            objSession.save(objPriceRunCostCapitalVO);
            logger.info("Adding PriceRunCostCapital"); 
          //  objSession.getTransaction().commit();
            addResult = true;
            logger.info("PriceRunCostCapital ARE Adding");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING PriceRunCostCapital", e);
            e.printStackTrace();
          //  objSession.getTransaction().rollback();
        }
       
        return addResult;
    }
    
    public List getPrcCost(String prcId)
    {
        PriceRunCostCapitalVO objPriceRunCostCapitalVO = new PriceRunCostCapitalVO();
        Session objSession = null;
        Criteria objCriteria = null;
        try
        {
            logger.info("GETTING ALL ");
            prcCostCap = new ArrayList();
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(PriceRunCostCapitalVO.class).add(Restrictions.eq("prcId.priceRunRefNo",prcId));
            prcCostCap = objCriteria.list();
            logger.info("GOT ALL ");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL ", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return prcCostCap;
    }
    
    public HashMap getHmPrcCost(String prcRunId)
    {
        HashMap hmCocs = new HashMap();
        PriceRunCostCapitalDAO objPriceRunCostCapitalDAO  = new PriceRunCostCapitalDAO();
        PriceRunCostCapitalVO objPriceRunCostCapitalVO = null;
        
        List lst = new ArrayList();
        lst = objPriceRunCostCapitalDAO.getPrcCost(prcRunId);        
        
        for(int s=0;s<lst.size();s++)
        {
            objPriceRunCostCapitalVO = new PriceRunCostCapitalVO();
            objPriceRunCostCapitalVO = (PriceRunCostCapitalVO)lst.get(s);
            hmCocs.put(new Integer(objPriceRunCostCapitalVO.getCocId().getCocId()),new Float(objPriceRunCostCapitalVO.getCocValue()));
            if(BuildConfig.DMODE)
	         {
	            System.out.println("coc id " + objPriceRunCostCapitalVO.getCocId().getCocId());
	            System.out.println("prc id " + objPriceRunCostCapitalVO.getPrcId().getPriceRunRefNo());
	            System.out.println("value " + objPriceRunCostCapitalVO.getCocValue());
	         }
        }
        
        return hmCocs;
    }
    
    public static void main(String[] args)
    {
        PriceRunCostCapitalDAO obj  = new PriceRunCostCapitalDAO();
        PriceRunCostCapitalVO vo = null;
        /*boolean result = false;
        result = obj.addPrcCostCap("01-03-2008 16:09:22");
        
        List lst = new ArrayList();
        lst = obj.getPrcCost("01-03-2008 16:09:22");        
        
        for(int s=0;s<lst.size();s++)
        {
            vo = new PriceRunCostCapitalVO();
            vo = (PriceRunCostCapitalVO)lst.get(s);
            System.out.println("coc id " + vo.getCocId().getCocId());
            System.out.println("prc id " + vo.getPrcId().getPriceRunRefNo());
            System.out.println("value " + vo.getCocValue());
        }*/
        HashMap hm = new HashMap();
        hm = obj.getHmPrcCost("01-03-2008 16:09:22");
        System.out.println("size :" + hm.size());
        System.out.println(" val " + hm.get(new Integer(1)));
        
    }
}

/*
*$Log: PriceRunCostCapitalDAO.java,v $
*Revision 1.2  2008/02/08 06:53:47  tannamalai
*last commit before table split up
*
*Revision 1.1  2008/02/06 06:41:53  tannamalai
*cost of capital added
*
*
*/