/*
 * Created on Feb 1, 2008
 *
 * ClassName	:  	CostOfCapitalDAO.java
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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.calculation.valueobjects.CostOfCapitalVO;
import com.savant.pricing.calculation.valueobjects.DealLeversVO;
import com.savant.pricing.calculation.valueobjects.UOMVO;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CostOfCapitalDAO
{

    private static Logger logger = Logger.getLogger(CostOfCapitalDAO.class);
    private static List costCap = null;
    
    public List getCocs()
    {
        Session objSession = null;
        Criteria objCriteria = null;
        try
        {
            logger.info("GETTING ALL COST OF CAPITAL ");
            costCap = new ArrayList();
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(CostOfCapitalVO.class).addOrder(Order.asc("cocName"));
            costCap = objCriteria.list();
            logger.info("GOT Energy Components ");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET COST OF CAPITAL ", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return costCap;
    }
    
    public CostOfCapitalVO getCoc(int cocID)
    {
        Session objSession = null;
        CostOfCapitalVO objCostOfCapitalVO = null;
        try
        {
            logger.info("GETTING COST OF CAPTIAL BY ID");
            objSession = HibernateUtil.getSession();
            objCostOfCapitalVO = (CostOfCapitalVO)objSession.get(CostOfCapitalVO.class, new Integer(cocID));
            logger.info("GOT COST OF CAPTIAL");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING COST OF CAPTIAL BY ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objCostOfCapitalVO;
    }
    
    public boolean updateCocs(CostOfCapitalVO objCocVO)
    {        
        boolean updateResult = false;
        Session objSession = null;
        try
        {
            logger.info("UPDATING COST OF CAPITAL");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update(objCocVO);
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("COST OF CAPITAL IS UPDATEED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE COST OF CAPITAL", e);
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
    
    public HashMap getAllCoc(Filter costCapfilter, String sortBy, boolean ascending, int startIndex, int displayCount)
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
            logger.info("GETTING ALL COST OF CAPITAL WITH SOME FILTER OPTION");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(CostOfCapitalVO.class);
            if(costCapfilter != null)
            {
                    objCriteria.add(Restrictions.like(costCapfilter.getFieldName(),costCapfilter.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(costCapfilter.getSpecialFunction())));
            }
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
            totRecordCount = new Integer(objCriteria.list().size());
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",objList);
            logger.info("GOT ALL COST OF CAPITAL");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL COST OF CAPITAL", e);
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
        CostOfCapitalVO vo = new CostOfCapitalVO();
        UOMVO uo = new UOMVO(); 
        uo.setUomIdentifier(5);
        CostOfCapitalDAO dao = new CostOfCapitalDAO();
        boolean result  = false;
        List lst =  new ArrayList();
        lst = dao.getCocs();
        for (int i = 0; i < lst.size(); i++)
        {
            vo = (CostOfCapitalVO)lst.get(i);
            System.out.println("cocid " + vo.getCocId() );
            System.out.println("cocname " + vo.getCocName());
            System.out.println("cocvalue" + vo.getCocValue());
            System.out.println("unit " + vo.getUnit().getUnit());
            System.out.println("isvalid " + vo.getIsValid());
        }
       /*
        vo.setCocId(1);
        vo.setCocValue((float)8);
        vo.setCocName("Interest rate");
        vo.setUnit(uo);
        vo.setIsValid(1);
        result = dao.updateCocs(vo);
        System.out.println("result :" +result);*/
    }
    
}

/*
*$Log: CostOfCapitalDAO.java,v $
*Revision 1.1  2008/02/06 06:41:53  tannamalai
*cost of capital added
*
*
*/