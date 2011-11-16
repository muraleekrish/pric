/*
 * Created on Jan 30, 2008
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
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.loader.custom.Return;

import com.savant.pricing.calculation.valueobjects.CustEnergyComponentsVO;
import com.savant.pricing.calculation.valueobjects.EnergyComponentsVO;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.MeterCategoryVO;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EnergyComponentsDAO
{
    private static Logger logger = Logger.getLogger(EnergyComponentsDAO.class);
    private static List engyComp = null;
    
    public List getEids()
    {
        Session objSession = null;
        Criteria objCriteria = null;
        try
        {
            logger.info("GETTING ALL Energy Components ");
            engyComp = new ArrayList();
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(EnergyComponentsVO.class);
            engyComp = objCriteria.list();
            logger.info("GOT Energy Components ");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET Energy Components ", e);
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
    
    public HashMap getEnergyComp(List eids)
    {
        EnergyComponentsVO objEngyVO = new EnergyComponentsVO();
        CustEnergyComponentsVO custengVO = new CustEnergyComponentsVO();
        Session objSession = null;
        Criteria objCriteria = null;
        HashMap hmresult = new HashMap();
        try
        {
            logger.info("GETTING ALL Energy Components Eid Wise");
            engyComp = new ArrayList();
            objSession = HibernateUtil.getSession();
            for(int s=0;s<eids.size();s++)
            {
                custengVO = (CustEnergyComponentsVO) eids.get(s);
                objCriteria = objSession.createCriteria(EnergyComponentsVO.class).add(Restrictions.eq("engCompId",new Integer(custengVO.getEngComp().getEngCompId())));
                engyComp = objCriteria.list();
                objEngyVO = (EnergyComponentsVO) engyComp.get(0);
                hmresult.put(new Integer(objEngyVO.getEngCompId()),objEngyVO.getEnergyComponents());
            }
            logger.info("GOT ALL HOLIDAYS");
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
        return hmresult;
    }
   
    public static void main(String[] args)
    {
        EnergyComponentsDAO obj = new EnergyComponentsDAO();
        EnergyComponentsVO vo = new EnergyComponentsVO(); 
        List lst = new ArrayList();
        lst = obj.getEids();
        System.out.println(" size " + lst.size());
        for(int s=0;s<lst.size();s++)
        {
            vo = (EnergyComponentsVO)lst.get(s);
            System.out.println(" Eids " +vo.getEngCompId());
        }
        
    }
    
}

/*
*$Log: EnergyComponentsDAO.java,v $
*Revision 1.1  2008/01/30 13:43:24  tannamalai
*DAO added for energy components added
*
*
*/