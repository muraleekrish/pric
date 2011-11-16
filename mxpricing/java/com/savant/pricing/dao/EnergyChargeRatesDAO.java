/*
 * Created on Mar 7, 2007
 *
 * ClassName	:  	EnergyChargeRatesDAO.java
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.calculation.valueobjects.EnergyChargeNamesVO;
import com.savant.pricing.calculation.valueobjects.EnergyChargeRatesVO;
import com.savant.pricing.calculation.valueobjects.UOMVO;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.LoadPrerequisites;
import com.savant.pricing.valueobjects.CongestionZonesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EnergyChargeRatesDAO implements LoadPrerequisites
{
    private static Logger logger = Logger.getLogger(EnergyChargeRatesDAO.class);
    private static List energyChargeRatesVOS = null;
    
    public static void getAllEnergyChargeRates()
    {
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL ENERGY RATES");
            objSession = HibernateUtil.getSession();
            energyChargeRatesVOS = new ArrayList();
            List obj = objSession.createQuery("select vo.energyChargeName, vo.monthYear, vo.charge, vo.profileType.profileIdentifier, vo.congestion from EnergyChargeRatesVO as vo").list();
            Iterator itr = obj.iterator();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                EnergyChargeRatesVO objEnergyChargeRatesVO = new EnergyChargeRatesVO();
                objEnergyChargeRatesVO.setEnergyChargeName((EnergyChargeNamesVO)innerRow[0]);
                objEnergyChargeRatesVO.setMonthYear((Date)innerRow[1]);
                objEnergyChargeRatesVO.setCharge(((Float)innerRow[2]).floatValue());
                objEnergyChargeRatesVO.setCongestion((CongestionZonesVO)innerRow[4]);
                energyChargeRatesVOS.add(objEnergyChargeRatesVO);
            }
            logger.info("GOT ALL ENERGY RATES");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL ENERGY RATES", e);
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
    
    public static float getCharge(int energyChargeId, Date monthYear, int congestionZoneId)
    {
        logger.info("GETTING CHARGE BY ENERGY CHARGE ID AND CONGESTION ZONE ID");
        Iterator itr = energyChargeRatesVOS.iterator();
        float charge = 0;
        while(itr.hasNext())
        {
            EnergyChargeRatesVO objEnergyChargeRatesVO = (EnergyChargeRatesVO)itr.next(); 
            if(energyChargeId == 6 && null != objEnergyChargeRatesVO.getCongestion())
            {
                if(objEnergyChargeRatesVO.getEnergyChargeName().getEnergyChargeIdentifier() == energyChargeId && 
                        objEnergyChargeRatesVO.getMonthYear().equals(monthYear)
                        && congestionZoneId == objEnergyChargeRatesVO.getCongestion().getCongestionZoneId())
                {
                    charge = objEnergyChargeRatesVO.getCharge();
                    break;
                }
            }
            else
            {
                if(objEnergyChargeRatesVO.getEnergyChargeName().getEnergyChargeIdentifier() == energyChargeId && 
                        objEnergyChargeRatesVO.getMonthYear().equals(monthYear))
                {
                    charge = objEnergyChargeRatesVO.getCharge();
                    break;
                }
            }
        }
        logger.info("GOT CHARGE BY ENERGY CHARGE ID AND CONGESTION ZONE ID");
        return charge;
    }
    
    public EnergyChargeNamesVO getEnergyChargeName(int enrgyId)
    {
        Session objSession = null;
        EnergyChargeNamesVO objEnergyChargeNamesVO = null;
        try
        {
            logger.info("GETTING ENERGY CHARGE NAME FOR ENERGY ID");
            objSession = HibernateUtil.getSession();
            objEnergyChargeNamesVO = (EnergyChargeNamesVO)objSession.get(EnergyChargeNamesVO.class, new Integer(enrgyId));
            logger.info("GOT ENERGY CHARGE NAME FOR ENERGY ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ENERGY NAME", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objEnergyChargeNamesVO;
    }
    
    public EnergyChargeRatesVO getEnergyCharge(int enrgychrgeId,int congestionId, Date mnthYr)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        EnergyChargeRatesVO objEnergyChargeRatesVO = null;
        List objList = null;
        try
        {
            logger.info("GETTING ENERGY CHARGE BY ENERGY ID AND CONGESTION ZONE ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(EnergyChargeRatesVO.class).add(Restrictions.eq("energyChargeName.energyChargeIdentifier",new Integer(enrgychrgeId)));
            if(congestionId!=0)
            {
                objCriteria.add(Restrictions.eq("congestion.congestionZoneId",new Integer(congestionId)));
            }
            objCriteria.add(Restrictions.eq("monthYear",mnthYr));
            objList = objCriteria.list();
            Iterator itr = objList.iterator();
            if(itr.hasNext())
            {
                objEnergyChargeRatesVO = (EnergyChargeRatesVO)itr.next();
            }
            logger.info("GOT ENERGY CHARGE BY ENERGY ID AND CONGESTION ZONE ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ENERGY CHARGE BY ENERGY ID AND CONGESTION ZONE ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objEnergyChargeRatesVO;
    }
    
    public Collection getAllEnergyChargeTypes()
    {
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL ENERGY CHARGE TYPES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(EnergyChargeNamesVO.class).add(Restrictions.eq("valid",new Boolean(true)));
            objCriteria.add(Restrictions.eq("display",new Boolean(true)));
            objCriteria.addOrder(Order.asc("chargeName"));
            objList = objCriteria.list();
            logger.info("GOT ALL ENERGY CHARGE TYPES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ALL ENERGY CHARGE TYPES", e);
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
    
    public boolean addorUpdateEnergyCharge(EnergyChargeRatesVO objEnergyChargeRatesVO)
    {
        boolean addResult = false;
        Session objSession = null;
        try
        {
            logger.info("ADDING OR UPDATING ENERGY CHARGE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.saveOrUpdate(objEnergyChargeRatesVO);
            objSession.getTransaction().commit();
            addResult= true;
            logger.info("ENERGY CHARGE IS ADDED OR UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD OR UPDATE", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
            throw new HibernateException("Charge does not saved.");
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

    public boolean updateEnergyCharge(int enrgyId,int congestionZoneId,Date mnthYr,float value,int unitId)
    {
        logger.info("UPDATING ENERGY CHARGE");
        boolean updateResult = false;
        EnergyChargeRatesVO objEnergyChargeRatesVO = this.getEnergyCharge(enrgyId,congestionZoneId,mnthYr);
        if(objEnergyChargeRatesVO==null)
        {
            objEnergyChargeRatesVO = new EnergyChargeRatesVO();
            EnergyChargeNamesVO objEnergyChargeNamesVO = new EnergyChargeNamesVO();
            objEnergyChargeNamesVO.setEnergyChargeIdentifier(enrgyId);
            objEnergyChargeRatesVO.setEnergyChargeName(objEnergyChargeNamesVO);
            if(congestionZoneId!=0)
            {
                CongestionZonesVO objCongestionZonesVO = new CongestionZonesVO();
                objCongestionZonesVO.setCongestionZoneId(congestionZoneId);
                objEnergyChargeRatesVO.setCongestion(objCongestionZonesVO);
            }
            objEnergyChargeRatesVO.setMonthYear(mnthYr);
        }
        objEnergyChargeRatesVO.setCharge(value);
        UOMVO objUOMVO = new UOMVO();
        objUOMVO.setUomIdentifier(unitId);
        objEnergyChargeRatesVO.setUnit(objUOMVO);
        updateResult = this.addorUpdateEnergyCharge(objEnergyChargeRatesVO);
        logger.info("ENERGY CHARGE IS UPDATED");
        return updateResult;
    }
    public HashMap getEnergyCharges(int energyChargeTypeId, int startIndex, int displayCount,int congestionZonID)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria = null;
        Criteria objSubCriteria = null;
        Session objSession = null;
        Integer totRecordCount = null;
        List objList = null;
        HashMap hmResult = new HashMap();
        try
        {
            logger.info("GETTING ENERGY CHARGES BY ENERGY TYPE ID AND CONGESTION ZONE ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(EnergyChargeRatesVO.class);
            objSubCriteria = objCriteria.createCriteria("energyChargeName");
            if(energyChargeTypeId != 0)
            {
                objCriteria.add(Restrictions.eq("energyChargeName.energyChargeIdentifier", new Integer(energyChargeTypeId)));
            }
            if(congestionZonID !=0)
            {
                objCriteria.add(Restrictions.eq("congestion.congestionZoneId", new Integer(congestionZonID)));
            }
            objSubCriteria.addOrder(Order.asc("chargeName"));
            objCriteria.addOrder(Order.asc("monthYear"));
            totRecordCount = new Integer(objCriteria.list().size());
            if(startIndex!=-1)
            {
            objCriteria.setFirstResult(startIndex);
            objCriteria.setMaxResults(displayCount);
            }
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",objList);
            logger.info("GOT ENERGY CHARGES BY ENERGY TYPE ID AND CONGESTION ZONE ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ENERGY CHARGES", e);
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

    public EnergyChargeRatesDAO()
    {
    }
    
    public static void main(String[] args) 
    {
        Calendar objCalendar = Calendar.getInstance();
        objCalendar.clear();
        objCalendar.set(2008,1,1);
        System.out.println(new EnergyChargeRatesDAO().getEnergyCharge(6,1,objCalendar.getTime()));
    }
    /* (non-Javadoc)
     * @see com.savant.pricing.common.LoadPrerequisites#reload()
     */
    public boolean reload()
    {
        EnergyChargeRatesDAO.getAllEnergyChargeRates();
        return true;
    }
}

/*
*$Log: EnergyChargeRatesDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.11  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.10  2007/08/17 11:18:28  kduraisamy
*unwanted commented code removed.
*
*Revision 1.9  2007/07/23 11:05:37  spandiyarajan
*removed unwanted imports
*
*Revision 1.8  2007/07/12 13:02:25  jnadesan
*isdiplay option added
*
*Revision 1.7  2007/07/12 12:05:23  jnadesan
*order added for charge name
*
*Revision 1.6  2007/07/11 13:21:05  jnadesan
*energy chargerates add/update provision given
*
*Revision 1.5  2007/07/05 14:00:21  kduraisamy
*Prerequisites object re initialized.
*
*Revision 1.4  2007/07/03 04:54:32  kduraisamy
*AS is taken by congestion zone wise.
*
*Revision 1.3  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.2  2007/06/11 13:11:09  jnadesan
*method for othercharges List page
*
*Revision 1.1  2007/03/08 16:32:44  kduraisamy
*Optimization with Sriram Completed.
*
*
*/