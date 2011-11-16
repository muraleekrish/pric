/*
 * Created on Jan 29, 2008
 *
 * ClassName	:  	EnergyComponentsDAO.java
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
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


import com.savant.pricing.calculation.valueobjects.CustEnergyComponentsVO;
import com.savant.pricing.calculation.valueobjects.EnergyComponentsVO;
import com.savant.pricing.calculation.valueobjects.TLFVO;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CustEnergyComponentsDAO
{
    private static Logger logger = Logger.getLogger(CustEnergyComponentsDAO.class);
    private static List engyComp = null;
    
    public CustEnergyComponentsVO getAll(int custId , int eid)
    {
        CustEnergyComponentsVO objEngyVO = new CustEnergyComponentsVO();
        Session objSession = null;
        Criteria objCriteria = null;
        try
        {
            logger.info("GETTING ALL Energy Components Customer Wise");
            engyComp = new ArrayList();
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(CustEnergyComponentsVO.class).add(Restrictions.eq("prcCustId.prospectiveCustomerId",new Integer(custId))).add(Restrictions.eq("engComp.engCompId", new Integer(eid)));
            engyComp = objCriteria.list();
            objEngyVO = (CustEnergyComponentsVO) engyComp.get(0);
            logger.info("GOT ALL Energy Components Customer Wise");
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
        return objEngyVO;
    }
    
    public List getAllValid(int custId)
    {
        CustEnergyComponentsVO objEngyVO = new CustEnergyComponentsVO();
        Session objSession = null;
        Criteria objCriteria = null;
        List lstResult = new ArrayList(); 
        try
        {
            logger.info("GETTING ALL Energy Components Customer Wise");
            engyComp = new ArrayList();
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(CustEnergyComponentsVO.class).add(Restrictions.eq("prcCustId.prospectiveCustomerId",new Integer(custId))).add(Restrictions.eq("isValid",new Integer(1)));
            engyComp = objCriteria.list();
            System.out.println("list size "+ engyComp.size());
            logger.info("GOT ALL Energy Components Customer Wise");
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
        return engyComp;
    }
    
    public List getAllInValid(int custId)
    {
        CustEnergyComponentsVO objEngyVO = new CustEnergyComponentsVO();
        Session objSession = null;
        Criteria objCriteria = null;
        try
        {
            logger.info("GETTING ALL Energy Components Customer Wise");
            engyComp = new ArrayList();
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(CustEnergyComponentsVO.class).add(Restrictions.eq("prcCustId.prospectiveCustomerId",new Integer(custId))).add(Restrictions.eq("isValid", new Integer(0)));
            engyComp = objCriteria.list();
            logger.info("GOT ALL Energy Components Customer Wise");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL Energy Components Customer Wise", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return engyComp;
    }
    
    public HashMap getEngCompo(int prscustid)
    {
        HashMap hmValid = new HashMap();
        EnergyComponentsDAO objEnergyComponentsDAO = new EnergyComponentsDAO();
        hmValid.put("valid",objEnergyComponentsDAO.getEnergyComp(this.getAllValid(prscustid))); 
        hmValid.put("notvalid",objEnergyComponentsDAO.getEnergyComp(this.getAllInValid(prscustid)));
        return hmValid;
    }
    
    public boolean updateCustEngyComp(List objCustEnergyVO)
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
            logger.info("UPDATING CUSTOMER ENERGY COMPONENT DETAILS"); 
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            Iterator itr = objCustEnergyVO.iterator();
            while(itr.hasNext())
            {
                CustEnergyComponentsVO objCustEnergyComponentsVO = (CustEnergyComponentsVO)itr.next();
                objSession.update(objCustEnergyComponentsVO);
            }
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("CUSTOMER ENERGY COMPONENT DETAILS ARE UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE CUSTOMER ENERGY COMPONENT", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
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
    
    public boolean updateCustEngyComp(CustEnergyComponentsVO objCustEnergyVO)
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
            logger.info("UPDATING CUSTOMER ENERGY COMPONENT DETAILS"); 
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update(objCustEnergyVO);
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("CUSTOMER ENERGY COMPONENT DETAILS ARE UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE CUSTOMER ENERGY COMPONENT", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
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
    
    public boolean addCustEngyComp(int custId)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        boolean addResult = false;
        EnergyComponentsDAO obj = new EnergyComponentsDAO();
        EnergyComponentsVO objEnergyComponentsVO = null;
        CustEnergyComponentsVO objCustEnergyComponentsVO = new CustEnergyComponentsVO();
        ProspectiveCustomerVO objProspectiveCustomerVO = new ProspectiveCustomerVO();
        try
        {
            List lst = new ArrayList();
            lst = obj.getEids();
            for(int s=0;s<lst.size();s++)
            {
                objEnergyComponentsVO =  new EnergyComponentsVO(); 
                objEnergyComponentsVO = (EnergyComponentsVO)lst.get(s);
                objCustEnergyComponentsVO.setIsValid(1);
                objProspectiveCustomerVO.setProspectiveCustomerId(custId);
                objCustEnergyComponentsVO.setPrcCustId(objProspectiveCustomerVO);
                objCustEnergyComponentsVO.setEngComp(objEnergyComponentsVO);
                addResult = this.add(objCustEnergyComponentsVO);
                System.out.println("inside energy compo add");
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
    
    
    public boolean add(CustEnergyComponentsVO objCustEnergyComponentsVO)
    {
        
        Session objSession = null;
        boolean addResult = false;
        ProspectiveCustomerVO objProspectiveCustomerVO = new ProspectiveCustomerVO();
        try
        {
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.save(objCustEnergyComponentsVO);
            logger.info("Adding CUSTOMER ENERGY COMPONENT DETAILS"); 
            objSession.getTransaction().commit();
            addResult = true;
            logger.info("CUSTOMER ENERGY COMPONENT DETAILS ARE Adding");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE CUSTOMER ENERGY COMPONENT", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
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
    
    public void deleteCustEngComp(int custID)
    {
        
    }
     
  public static void main(String[] args)
	{
      
      CustEnergyComponentsDAO obj = new CustEnergyComponentsDAO();
      boolean re  = false;
      re = obj.addCustEngyComp(842);
     /* HashMap hmRes = new HashMap();
      HashMap hmValid = new HashMap();
      HashMap hmNotValid = new HashMap();
      hmRes = obj.getEngCompo(842);
      hmValid = (HashMap)hmRes.get("valid");
      hmNotValid = (HashMap)hmRes.get("notvalid");
      System.out.println("valid size" + hmValid.size());
      System.out.println("Not valid size" + hmNotValid.size());
      */
	    
	}
    
    
}

/*
*$Log: CustEnergyComponentsDAO.java,v $
*Revision 1.2  2008/03/25 05:02:30  tannamalai
**** empty log message ***
*
*Revision 1.1  2008/01/30 13:43:24  tannamalai
*DAO added for energy components added
*
*
*/