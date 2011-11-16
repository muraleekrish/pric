/*
 * Created on Feb 5, 2007
 *
 * ClassName	:  	LoadExtrapolationDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.calculation.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.calculation.valueobjects.IDRVO;
import com.savant.pricing.calculation.valueobjects.PICVO;
import com.savant.pricing.calculation.valueobjects.TempActualUsageVO;
import com.savant.pricing.calculation.valueobjects.TempProfileUsageVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.dao.CalendarDAO;
import com.savant.pricing.dao.DLFCodeDAO;
import com.savant.pricing.dao.DLFDAO;
import com.savant.pricing.dao.LossFactorLookupDAO;
import com.savant.pricing.dao.PICDAO;
import com.savant.pricing.dao.TDSPRateCodesDAO;
import com.savant.pricing.dao.TLFDAO;
import com.savant.pricing.transferobjects.HourValueDetails;
import com.savant.pricing.transferobjects.IndividualMonthDetails;
import com.savant.pricing.transferobjects.MonthDetails;
import com.savant.pricing.transferobjects.OnPkOffPkDetails;
import com.savant.pricing.transferobjects.ProfileDetails;
import com.savant.pricing.valueobjects.DayTypesVO;
import com.savant.pricing.valueobjects.TDSPVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoadExtrapolationDAO
{
    
    /** This method computes the usage by month,weekend/weekday for each esid for this customer for 12 months
     * 
     * @throws CustomerNotFoundException,ESIDNotFoundException
     * @param prospectiveCustomerId
     * @param objSession
     */
    private HashMap hmProfileUsage = new HashMap();
    private HashMap hmActualUsage = new HashMap();
    TDSPRateCodesDAO objTDSPRateCodesDAO = new TDSPRateCodesDAO();
    private HashMap hmUsageDetails = null;
    final String delimiter=",";
    final String lineSeparator=System.getProperty("line.separator");
    
    private static Logger logger = Logger.getLogger(LoadExtrapolationDAO.class);
    
    public void loadProfileUsage(int prospectiveCustomerId, Session objSession)
    {
        //        to fetch all the ESIID for this customer
        if(BuildConfig.DMODE)
            System.out.println("getAllESIID:Starts:"+new Date());
      
        List picVOList = new PICDAO().getAllValidESIID(prospectiveCustomerId, objSession);
        boolean availIdr = false;
        String profile = "";
        if(BuildConfig.DMODE)
            System.out.println("getAllESIID:ends:"+new Date());
        try
        {
            logger.info("GETTING LOAD PROFILE USAGE BY PROSPECTIVE CUSTOMER ID");
            //to fetch the month Details for each ESIID.
            for(int i = 0;i<picVOList.size();i++)
            {
                hmActualUsage.clear();
                StringBuffer sbAggregatedLoadProfile = new StringBuffer();
                PICVO objPICVO = (PICVO)picVOList.get(i);
                String meterType = objPICVO.getProfileDetails().getMeterType();
                
                if(BuildConfig.DMODE)
                    System.out.println("getMonthDetailsByESIID:Starts:"+new Date());
                
                List objMonthDetailsList = this.getMonthDetailsByESIID(objPICVO.getEsiId(),prospectiveCustomerId, objSession);
                if(BuildConfig.DMODE)
                    System.out.println("getMonthDetailsByESIID:ends:"+new Date());
                
                //to update the individual month details
                if(BuildConfig.DMODE)
                    System.out.println("updateIndividualMonthDetails:Starts:"+new Date());
                objMonthDetailsList = this.updateIndividualMonthDetails(objMonthDetailsList);
                if(BuildConfig.DMODE)
                    System.out.println("updateIndividualMonthDetails:ends:"+new Date());
                
                if(BuildConfig.DMODE)
                    System.out.println("meterType::"+meterType);
                int rateCodeId = objPICVO.getRateCode().getRateCodeIdentifier();
                int serviceVoltageId = objTDSPRateCodesDAO.getServiceVoltageId(rateCodeId);
                
                String dlfCode = LossFactorLookupDAO.getDLF(objPICVO.getRateCode().getTdsp(), serviceVoltageId);
                int dlfCodeId = DLFCodeDAO.getDLFCodeId(objPICVO.getRateCode().getTdsp(),dlfCode);
                float dlf = DLFDAO.getDLF(dlfCodeId);
                
                hmUsageDetails = new HashMap();
                profile = objPICVO.getProfileDetails().getLoadProfile();
                if(meterType.equals("IDR"))
                {
                    availIdr = this.checkAvailIDR(objPICVO.getEsiId().trim(), objSession); 
                    if(!availIdr)
                    {
                        String oldProfile = objPICVO.getLoadProfile();
                        if(BuildConfig.DMODE)
                        {
                            System.out.println("Old Profile:"+oldProfile+"ESIID:"+objPICVO.getEsiId());
                        }
                        String newProfile = oldProfile.replaceAll("BUSIDRRQ", "BUSMEDLF");
                        if(BuildConfig.DMODE)
                        {
                            System.out.println("New Profile:"+newProfile+"ESIID:"+objPICVO.getEsiId());
                        }
                        //objPICVO.setLoadProfile(newProfile);
                        //objPICVO.setProfileDetails(null);
                        profile = this.getProfileDetails(newProfile,objSession).getLoadProfile();
                    }
                    else
                    {
                        // to load the Aggregated Load Profile..
                        sbAggregatedLoadProfile = this.loadIDRAggregatedProfiles(objPICVO.getEsiId().trim(), prospectiveCustomerId, dlf, objSession);
                    }
                }
                //profile = objPICVO.getProfileDetails().getLoadProfile();
                if(meterType.equals("NIDR") || availIdr == false)
                {
                     
                    String wdHql = "select nidrvo.month, nidrvo.dayType.dayTypeId, nidrvo.hour, nidrvo.value from NIDRVO as nidrvo where nidrvo.loadProfile = ?";
                    Query queryObj = objSession.createQuery(wdHql);
                    queryObj.setString(0, profile);
                    Iterator itr = queryObj.iterate();
                    while(itr.hasNext())
                    {
                        Object[] innerRow = (Object[]) itr.next();
                        int month = ((Integer)innerRow[0]).intValue();
                        int dayTypeId = ((Integer)innerRow[1]).intValue();
                        int hour = ((Integer)innerRow[2]).intValue();
                        float value = ((Float)innerRow[3]).floatValue();
                        String key = profile+":"+month+":"+dayTypeId;
                        if(hmUsageDetails.containsKey(key))
                        {
                           
                            List objList = (List)hmUsageDetails.get(key);
                            HourValueDetails objHourValueDetails = new HourValueDetails();
                            objHourValueDetails.setHour(hour);
                            objHourValueDetails.setValue(value);
                            objList.add(objHourValueDetails);
                        }
                        else
                        {
                            List objList = new ArrayList();
                            HourValueDetails objHourValueDetails = new HourValueDetails();
                            objHourValueDetails.setHour(hour);
                            objHourValueDetails.setValue(value);
                            objList.add(objHourValueDetails);
                            hmUsageDetails.put(key,objList);
                        }
                    } 
                    if(BuildConfig.DMODE)
                        System.out.println("meterType ends::"+meterType);
                    if(BuildConfig.DMODE)
                        System.out.println("objMonthDetailsList for loop starts:"+new Date());
                    for(int k=0;k<objMonthDetailsList.size();k++)
                    {
                        MonthDetails obj = (MonthDetails)objMonthDetailsList.get(k);
                        float wdTotalProfileUsage = 0;
                        float weTotalProfileUsage = 0;
                        Vector vec = obj.getIndividualMonthDetails();
                        if(BuildConfig.DMODE)
                            System.out.println("obj.getMeterReadfromDate()"+obj.getMeterReadfromDate());
                        for(int j=0;j<vec.size();j++)
                        {
                            IndividualMonthDetails  objIndividualMonthDetails = new IndividualMonthDetails();
                            objIndividualMonthDetails = (IndividualMonthDetails)vec.get(j);
                            if(BuildConfig.DMODE)
                                System.out.println("loadProfileDetails starts:"+new Date());
                            objIndividualMonthDetails = this.loadProfileDetails(profile, objPICVO.getEsiId(), objIndividualMonthDetails);
                            if(BuildConfig.DMODE)
                            {
                                System.out.println("Month"+objIndividualMonthDetails.getMonth());
                                System.out.println("objIndividualMonthDetails.noOFWD"+objIndividualMonthDetails.getNoOfWD());
                                System.out.println("objIndividualMonthDetails.noOFWE"+objIndividualMonthDetails.getNoOfWE());
                            }
                            if(BuildConfig.DMODE)
                                System.out.println("loadProfileDetails ends:"+new Date());
                            wdTotalProfileUsage += objIndividualMonthDetails.getTotalProfileUsageWD();
                            weTotalProfileUsage += objIndividualMonthDetails.getTotalProfileUsageWE();
                        }
                        if(BuildConfig.DMODE)
                            System.out.println("obj.getMeterReadEndDate()"+obj.getMeterReadEndDate());
                        
                        obj.setTotalProfileUsageWD(wdTotalProfileUsage);
                        obj.setTotalProfileUsageWE(weTotalProfileUsage);
                        obj.setUsageRatio(obj.getPicUsage()/(obj.getTotalProfileUsageWD()+obj.getTotalProfileUsageWE()));
                        if(BuildConfig.DMODE)
                        {
                            System.out.println("MONTH:"+obj.getMonth());
                            System.out.println("PICUSAGE:"+obj.getPicUsage());
                            System.out.println("TOTALPROFILEUSAGEWD:"+obj.getTotalProfileUsageWD());
                            System.out.println("TOTALPROFILEUSAGEWE:"+obj.getTotalProfileUsageWE());
                        }
                        if(BuildConfig.DMODE)
                            System.out.println("loadActualUsage starts:"+new Date());
                        this.loadActualUsage(obj,profile,objPICVO.getEsiId());
                        if(BuildConfig.DMODE)
                            System.out.println("loadActualUsage ends:"+new Date());
                        if(BuildConfig.DMODE)
                        {
                            Vector v = obj.getIndividualMonthDetails();
                            int count = 0;
                            System.out.println("Term:"+obj.getMonth());
                            while(count<v.size())
                            {
                                IndividualMonthDetails ob = (IndividualMonthDetails)v.get(count);
                                System.out.println("Month:"+ob.getMonth());
                                System.out.println("NoOfWD:"+ob.getNoOfWD());
                                System.out.println("NoOfWE:"+ob.getNoOfWE());
                                count++;
                            }
                            
                            System.out.println("MSD "+obj.getMeterReadfromDate());
                            System.out.println("MED "+obj.getMeterReadEndDate());
                            System.out.println("PIC Usage:"+obj.getPicUsage());
                            System.out.println("Profile Usage WD:"+ obj.getTotalProfileUsageWD());
                            System.out.println("Profile Usage WE:"+ obj.getTotalProfileUsageWE());
                            System.out.println("Profile Usage:"+ (obj.getTotalProfileUsageWD()+ obj.getTotalProfileUsageWE()));
                            System.out.println("UsageRatio:"+obj.getUsageRatio());
                        }
                    }
                    
                    if(BuildConfig.DMODE)
                        System.out.println("objMonthDetailsList for loop ends:"+new Date());
                    // to fetch the service voltage for each esiId
                    if(BuildConfig.DMODE)
                        System.out.println("dlf:endsss:"+new Date());
                    sbAggregatedLoadProfile = this.aggregateActualUsage(objPICVO, dlf, sbAggregatedLoadProfile);
                    if(BuildConfig.DMODE)
                        System.out.println("aggregateActualUsage:ends:"+new Date());
                }
                if(sbAggregatedLoadProfile.length()>0)
                {
                   // objSession.beginTransaction();
                    objSession.createSQLQuery("insert into PRC_AGGREGATEDLOADPROFILE(Prospective_Cust_ID, ESIID, PROFILELOAD) VALUES(?, ?, ?)").setInteger(0, prospectiveCustomerId).setString(1, objPICVO.getEsiId().trim()).setText(2,sbAggregatedLoadProfile.toString()).executeUpdate();
                   // objSession.getTransaction().commit();
                    
                }
            }
            logger.info("GOT LOAD PROFILE USAGE BY PROSPECTIVE CUSTOMER ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET LOAD PROFILE USAGE", e);
           // objSession.getTransaction().rollback();
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
    }
    public boolean checkAvailIDR(String esiId, Session objSession)
    {
       // Session objSession = null;
        try
        {
            logger.info("CHECK AVAILABLE IDR FOR THE ESIID");
           // objSession = HibernateUtil.getSession();
            List objList = objSession.createQuery("select count(idr.esiId) as count from IDRVO as idr where idr.esiId = ? and idr.value > 0").setString(0, esiId.trim()).list();
            Iterator itr = objList.iterator();
            if(itr.hasNext() && ((Long)itr.next()).intValue() == 576)
            {
                logger.info("IDR AVAILABLE FOR THE ESIID");
                return true;
            }
            else
            {
                logger.info("IDR NOT AVAILABLE FOR THE ESIID");
            }
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING CHECK AVAILABLE IDR", e);
            e.printStackTrace();
        }
        
        return false;
    }
    public StringBuffer loadIDRAggregatedProfiles(String esiId, int prospectiveCustomerId, float dlf, Session objSession)
    {
        //Session objSession = null;
        StringBuffer sbAggregatedLoadProfile = new StringBuffer();
        try
        {
            logger.info("LOADING IDR AGGREGATED PROFILES");
            float tlf = 0;
           // objSession = HibernateUtil.getSession();
            Iterator itr = objSession.createCriteria(IDRVO.class).add(Restrictions.like("esiId", esiId)).list().iterator();
            while(itr.hasNext())
            {
                float lossFactor = 1.0f;
                IDRVO objIDRVO = (IDRVO)itr.next();
                int hour = objIDRVO.getHour();
                int month = objIDRVO.getMonth();
                OnPkOffPkDetails objOnPkOffPkDetails =  (OnPkOffPkDetails)HibernateUtil.hmTLF.get(new Integer(month));
                if(BuildConfig.DMODE)
                {
                    System.out.println("TLF----->>"+tlf);
                    System.out.println("DLF----->>"+dlf);
                    System.out.println("lossFactor----->>"+lossFactor);
                }
                if(hour <7 || hour >22)
                {
                    tlf = objOnPkOffPkDetails.getOffPeakLoss();
                }
                else
                {
                    tlf = objOnPkOffPkDetails.getOnPeakLoss();
                }
                lossFactor = lossFactor + dlf + tlf;
                sbAggregatedLoadProfile = sbAggregatedLoadProfile.append(prospectiveCustomerId).append(delimiter)
                .append(month).append(delimiter)
                .append(hour).append(delimiter)
                .append(objIDRVO.getDayType().getDayTypeId()).append(delimiter)
                .append(lossFactor * objIDRVO.getValue()).append(delimiter)
                .append(esiId).append(lineSeparator);
            }
            logger.info("IDR AGGREGATED PROFILES IS LOADED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING LOAD THE IDR AGGREGATED PROFILES", e);
            e.printStackTrace();
        }
       /* finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }*/
        return sbAggregatedLoadProfile;
    }
    public HashMap getIDRDetails(String esiId)
    {
        Session objSession = null;
        HashMap hmIDRDetails = new HashMap();
        List objList = null;
        try
        {
            logger.info("GETTING IDR DETAILS FOR THE ESIID");
            objSession = HibernateUtil.getSession();
            Iterator itr = objSession.createCriteria(IDRVO.class).add(Restrictions.like("esiId", esiId)).list().iterator();
            while(itr.hasNext())
            {
                IDRVO objIDRVO = (IDRVO)itr.next();
                String key = esiId+":"+objIDRVO.getMonth()+":"+objIDRVO.getDayType().getDayTypeId();
                
                if(hmIDRDetails.containsKey(key))
                {
                    objList = (List)hmIDRDetails.get(key);
                }
                else
                {
                    objList = new ArrayList();
                    hmIDRDetails.put(key, objList);
                }
                objList.add(objIDRVO);
                logger.info("GOT IDR DETAILS FOR THE ESIID");
            }
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE IDR DETAILS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hmIDRDetails;
    }
    
    private void loadActualUsage(MonthDetails objMonthDetails, String profile, String esiId)
    {
        int term = objMonthDetails.getMonth();
        try
        {
            logger.info("LOADING ACTUAL USAGE");
            String key = getKey(esiId,profile,term);
            List objList = (List)hmProfileUsage.get(key);
            if(objList != null)
            {
                Iterator itr = objList.iterator();
                while(itr.hasNext())
                {
                    TempProfileUsageVO objTempProfileUsageVO = (TempProfileUsageVO)itr.next();
                    int month = objTempProfileUsageVO.getMonth();
                    int hour = objTempProfileUsageVO.getHour();
                    DayTypesVO objDayTypesVO = objTempProfileUsageVO.getDayType();
                    
                    TempActualUsageVO objTempActualUsageVO = new TempActualUsageVO();
                    objTempActualUsageVO.setEsiId(esiId);
                    objTempActualUsageVO.setLoadProfile(profile);
                    objTempActualUsageVO.setMonth(month);
                    objTempActualUsageVO.setHour(hour);
                    objTempActualUsageVO.setTerm(term);
                    objTempActualUsageVO.setDayType(objDayTypesVO);
                    objTempActualUsageVO.setValue(objMonthDetails.getUsageRatio()* objTempProfileUsageVO.getValue());
                    
                    objTempActualUsageVO.setNoOfWD(objTempProfileUsageVO.getNoOfWD());
                    objTempActualUsageVO.setNoOfWE(objTempProfileUsageVO.getNoOfWE());
                    
                    key = esiId+profile+hour+objDayTypesVO.getDayTypeId()+month;
                    if(hmActualUsage.containsKey(key))
                    {
                        objList = (List)hmActualUsage.get(key);
                    }
                    else
                    {
                        objList = new ArrayList();
                        hmActualUsage.put(key, objList);
                    }
                    objList.add(objTempActualUsageVO);
                } 
            }
            logger.info("ACTUAL USAGE IS LOADED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING LOAD THE ACTUAL USAGE", e); 
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
    }
    public TDSPVO getTDSPByESIID(String esiId)
    {
        TDSPVO objTDSPVO = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING TDSP BY ESIID");
            objSession = HibernateUtil.getSession();
            String esiIdPrefix = esiId.substring(0,7);
            objTDSPVO = (TDSPVO)objSession.createCriteria(TDSPVO.class).add(Restrictions.eq("esiIdPrefix",esiIdPrefix)).uniqueResult();
            logger.info("GOT TDSP BY ESIID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE TDSP BY ESIID", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objTDSPVO;
    }
    private StringBuffer aggregateActualUsage(PICVO objPICVO, float dlf, StringBuffer sbAggregatedLoadProfile)
    {
        try
        {
            logger.info("AGGREGATING ACTUAL USAGE");
            Set set = hmActualUsage.keySet();
            Iterator itr = set.iterator();
            while(itr.hasNext())
            {
                String key = (String)itr.next();
                List objList = (List)hmActualUsage.get(key);
                Iterator innerItr = objList.iterator();
                int noOfWD = 0;
                int noOfWE = 0;
                float usage = 0;
                String loadProfile = "";
                int month = 0;
                int hour = 0;
                DayTypesVO  objDayTypesVO = null;
                while(innerItr.hasNext())
                {
                    TempActualUsageVO objTempActualUsageVO = (TempActualUsageVO)innerItr.next();
                    noOfWD = noOfWD + objTempActualUsageVO.getNoOfWD();
                    noOfWE = noOfWE + objTempActualUsageVO.getNoOfWE();
                    usage = usage + objTempActualUsageVO.getValue();
                    loadProfile = objTempActualUsageVO.getLoadProfile();
                    month = objTempActualUsageVO.getMonth();
                    hour = objTempActualUsageVO.getHour();
                    objDayTypesVO = objTempActualUsageVO.getDayType();
                }
                
                sbAggregatedLoadProfile = objPICVO.createAggregateVO(objPICVO.getEsiId(), dlf,  loadProfile, month, hour, objDayTypesVO, usage, noOfWD, noOfWE, sbAggregatedLoadProfile);
            }
            logger.info("ACTUAL USAGE IS AGGREGATED");
            return sbAggregatedLoadProfile;
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING AGGREGATE THE ACTUAL USAGE", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
    }
    private static String getKey(String esiId, String loadProfile, int term)
    {
        return esiId+loadProfile+term;
    }
    
    
    private IndividualMonthDetails loadProfileDetails(String profile, String esiId, IndividualMonthDetails objIndividualMonthDetails)
    {
        int month = objIndividualMonthDetails.getMonth();
        int noOfWD = objIndividualMonthDetails.getNoOfWD() ;
        int noOfWE = objIndividualMonthDetails.getNoOfWE();
        if(BuildConfig.DMODE)
            System.out.println("inside loadProfileDetails starts:"+new Date());
        
        try
        {
            logger.info("LOADING PROFILE DETAILS");
            int dayTypeId = 2;
            
            if(BuildConfig.DMODE)
                System.out.println("profile:"+profile+"***"+new Date());
            String key = profile+":"+month+":"+dayTypeId;
            if(BuildConfig.DMODE)
                System.out.println("hmUsageDetails.get starts"+new Date());
            
            List objList = (List)hmUsageDetails.get(key);
            
            if(BuildConfig.DMODE)
                System.out.println("hmUsageDetails.get ends"+new Date()+"objList:"+objList);
            DayTypesVO objDayTypesVO = null;
            float wdTotalProfileUsage = 0;
            float weTotalProfileUsage = 0;
            Iterator itr = null;
            if(objList != null)
            {
                itr = objList.iterator();
                objDayTypesVO = new DayTypesVO();
                objDayTypesVO.setDayTypeId(dayTypeId);
                while(itr.hasNext())
                {
                    if(BuildConfig.DMODE)
                        System.out.println("Inside While 1"+new Date());
                    
                    HourValueDetails objHourValueDetails = (HourValueDetails)itr.next();
                    TempProfileUsageVO objTempProfileUsageVO = new TempProfileUsageVO();
                    objTempProfileUsageVO.setValue(noOfWD * objHourValueDetails.getValue());
                    wdTotalProfileUsage += objTempProfileUsageVO.getValue();
                    objTempProfileUsageVO.setDayType(objDayTypesVO);
                    objTempProfileUsageVO.setNoOfWD(objIndividualMonthDetails.getNoOfWD());
                    objTempProfileUsageVO.setNoOfWE(0);
                    if(BuildConfig.DMODE)
                        System.out.println("saveTempProfileUsage starts:"+new Date());
                    
                    this.saveTempProfileUsage(objTempProfileUsageVO,objIndividualMonthDetails.getTerm(),esiId,profile,month,objHourValueDetails.getHour());
                    
                    if(BuildConfig.DMODE)
                        System.out.println("saveTempProfileUsage ends:"+new Date());
                } 
            }
            if(BuildConfig.DMODE)
                System.out.println("Outside While 1"+new Date());
            
            objIndividualMonthDetails.setTotalProfileUsageWD(wdTotalProfileUsage);
            dayTypeId = 3;
            key = profile+":"+month+":"+dayTypeId;
            objDayTypesVO = new DayTypesVO();
            objDayTypesVO.setDayTypeId(dayTypeId);
            objList = (List)hmUsageDetails.get(key);
            if(objList != null)
            {
                itr = objList.iterator();
                while(itr.hasNext())
                {
                    if(BuildConfig.DMODE)
                        System.out.println("inside While 2"+new Date());
                    
                    HourValueDetails objHourValueDetails = (HourValueDetails)itr.next();
                    TempProfileUsageVO objTempProfileUsageVO = new TempProfileUsageVO();
                    objTempProfileUsageVO.setValue(noOfWE * objHourValueDetails.getValue());
                    weTotalProfileUsage += objTempProfileUsageVO.getValue();
                    objTempProfileUsageVO.setDayType(objDayTypesVO);
                    objTempProfileUsageVO.setNoOfWD(0);
                    objTempProfileUsageVO.setNoOfWE(objIndividualMonthDetails.getNoOfWE());
                    
                    if(BuildConfig.DMODE)
                        System.out.println("saveTempProfileUsage II starts:"+new Date());
                    
                    this.saveTempProfileUsage(objTempProfileUsageVO,objIndividualMonthDetails.getTerm(),esiId,profile,month, objHourValueDetails.getHour());
                    
                    if(BuildConfig.DMODE)
                        System.out.println("saveTempProfileUsage II ends:"+new Date());
                }
            }
            if(BuildConfig.DMODE)
                System.out.println("outside While 2"+new Date());
            
            objIndividualMonthDetails.setTotalProfileUsageWE(weTotalProfileUsage);
            logger.info("PROFILE DETAILS ARE LOADED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING LOAD THE PROFILE DETAILS", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        if(BuildConfig.DMODE)
            System.out.println("inside loadProfileDetails ends:"+new Date());
        return objIndividualMonthDetails;
    }
    
    private void saveTempProfileUsage(TempProfileUsageVO objTempProfileUsageVO, int term, String esiId, String loadProfile, int month, int hour)
    {
        logger. info("SAVING TEMP PROFILE USAGE");
        objTempProfileUsageVO.setTerm(term);
        objTempProfileUsageVO.setEsiId(esiId);
        objTempProfileUsageVO.setLoadProfile(loadProfile);
        objTempProfileUsageVO.setMonth(month);
        objTempProfileUsageVO.setHour(hour);
        String key = getKey(esiId, loadProfile, term);
        List objList = null;
        if(hmProfileUsage.containsKey(key))
        {
            objList = (List)hmProfileUsage.get(key);
        }
        else
        {
            objList = new ArrayList();
        }
        objList.add(objTempProfileUsageVO);
        hmProfileUsage.put(key, objList);
        logger.info("TEMP PROFILE USAGE IS SAVED");
    }
    
    public List getAllESIID(int prospectiveCustomerId, Session objSession)
    {
        List objList = null;
        try
        {
            logger.info("GETTING ALL ESIID");
            Criteria objCriteria = null;
            objCriteria = objSession.createCriteria(PICVO.class).add(Restrictions.eq("customer.prospectiveCustomerId", new Integer(prospectiveCustomerId)));
            objList = objCriteria.list();
            logger.info("GOT ALL ESIID");
        }
        catch(HibernateException e) 
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ESIID", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        return objList;
    }
    public ProfileDetails getProfileDetails(String profileString, Session objSession)
    {
        String prof[]=profileString.split("_");
        ProfileDetails  objProfileDetails = new ProfileDetails();
        objProfileDetails.setProfileString(profileString);
        objProfileDetails.setProfileType(prof[0]);
        objProfileDetails.setLoadProfile(prof[0]+"_"+prof[1]);
        objProfileDetails.setWeatherZoneCode(prof[1]);
        objProfileDetails.setMeterType(prof[2]);
        try
        {
            logger.info("GETTING PROFILE DETAILS");
            Query objQuery = objSession.createQuery("select weatherzonevo.weatherZoneId, weatherzonevo.congestionZone.congestionZoneId from WeatherZonesVO as weatherzonevo where weatherzonevo.weatherZoneCode = ?");
            objQuery.setString(0,objProfileDetails.getWeatherZoneCode());
            Object[] innerRow = (Object[]) objQuery.uniqueResult();
            objProfileDetails.setWeatherZoneId(((Integer)innerRow[0]).intValue());
            objProfileDetails.setCongestionZoneId(((Integer)innerRow[1]).intValue());
            objQuery = objSession.createQuery("select loadprofilevo.profileIdentifier from LoadProfileTypesVO as loadprofilevo where loadprofilevo.profileType=?");
            objQuery.setString(0,prof[0]);
            Iterator itr = objQuery.iterate();
            
            if(itr.hasNext())
            {
                objProfileDetails.setProfileTypeId(((Integer)itr.next()).intValue());
            }
            logger.info("GOT PROFILE DETAILS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET PROFILE DETAILS", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        return objProfileDetails;
    }
    
    private List getMonthDetailsByESIID(String esiId,int prospectiveCustomerId, Session objSession)
    {
        List objList = new LinkedList() ;
        try
        {
            logger.info("GETTING MONTH DETAILS BY ESIID");
            String hql = "select picusagevo.month, picusagevo.meterReadDate, picusagevo.noOfDays, picusagevo.historicalUsage,picusagevo.historicalApparentPower,picusagevo.historicalDemand from PICVO as picvo, PICUsageVO as picusagevo where picvo.picReferenceId = picusagevo.picRef.picReferenceId and picvo.esiId = ? and picvo.customer.prospectiveCustomerId =?  order by picusagevo.month";
            Query queryObj = objSession.createQuery(hql);
            queryObj.setString(0, esiId);
            queryObj.setInteger(1, prospectiveCustomerId);
            Iterator itr = queryObj.iterate();
            Date meterReadDate = null;
            DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
            GregorianCalendar gc = new GregorianCalendar(); 
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                meterReadDate = ((Date)innerRow[1]);
                MonthDetails objMonthDetails = new MonthDetails();
                objMonthDetails.setMonth(((Integer)innerRow[0]).intValue());
                gc.setTime(meterReadDate);
                gc.add(Calendar.DATE,-1);
                objMonthDetails.setMeterReadEndDate(gc.getTime());
                gc.add(Calendar.DATE,-(((Integer)innerRow[2]).intValue()-1));
                objMonthDetails.setMeterReadfromDate(gc.getTime());
                objMonthDetails.setPicUsage(((Float)innerRow[3]).floatValue());
                objMonthDetails.setNoOfDays(((Integer)innerRow[2]).intValue());
                objMonthDetails.setHistoricalApparentPower(((Float)innerRow[4]).floatValue());
                objMonthDetails.setHistoricalDemand(((Float)innerRow[5]).floatValue());
                objMonthDetails.setMeterReadDate((formatter.format((Date)innerRow[1]).toString()));
                objList.add(objMonthDetails);
            }
            logger.info("GOT MONTH DETAILS BY ESIID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET MONTH DETAILS BY ESIID", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        return objList;
    }
    
    public List getMonthDetails(int prospectiveCustomerId)
    {
        List objList = new LinkedList() ;
        Session objSession = null;
        try
        {
            logger.info("GETTING MONTH DETAILS BY PROSPECTIVE CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            String hql = "select picusagevo.month, avg(picusagevo.noOfDays), sum(picusagevo.historicalUsage),sum(picusagevo.historicalApparentPower),sum(picusagevo.historicalDemand) from PICVO as picvo, PICUsageVO as picusagevo where picvo.picReferenceId = picusagevo.picRef.picReferenceId and picvo.customer.prospectiveCustomerId =?  group by picusagevo.month";
            Query queryObj = objSession.createQuery(hql);
            queryObj.setInteger(0, prospectiveCustomerId);
            Iterator itr = queryObj.iterate();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                MonthDetails objMonthDetails = new MonthDetails();
                objMonthDetails.setMonth(((Integer)innerRow[0]).intValue());
                objMonthDetails.setNoOfDays(((Double)innerRow[1]).intValue());
                objMonthDetails.setPicUsage(((Double)innerRow[2]).floatValue());
                objMonthDetails.setHistoricalApparentPower(((Double)innerRow[3]).floatValue());
                objMonthDetails.setHistoricalDemand(((Double)innerRow[4]).floatValue());
                objList.add(objMonthDetails);
            }
            logger.info("GOT MONTH DETAILS BY PROSPECTIVE CUSTOMER ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET MONTH DETAILS BY PROSPECTIVE CUSTOMER ID", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
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
        
    public double getLoadFactor(int prsCustId)
    {
        logger.info("GETTING LOAD FACTOR");
        double lf = 0.0;
        List picUsage= new ArrayList();
        picUsage = this.getMonthDetails(prsCustId);
        MonthDetails objMonthDetails=null;
        
        for(int i=0;i< picUsage.size();i++)
        {  
            double localLf = 0.0;
            objMonthDetails = (MonthDetails)picUsage.get(i); 
            if(!(objMonthDetails.getPicUsage() == 0.0 || objMonthDetails.getHistoricalDemand() == 0.0 || objMonthDetails.getNoOfDays() == 0 ))
                localLf = ((objMonthDetails.getPicUsage()*100)/(objMonthDetails.getHistoricalDemand()*objMonthDetails.getNoOfDays()*24));
           lf += localLf;
        }  
        lf = lf/12;
        logger.info("GOT LOAD FACTOR");
        return lf;
    }
    public List getMonthDetailsByESIID(String esiid,int custId)
    {
        List result = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING MONTH DETAILS BY ESIID AND CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            result=getMonthDetailsByESIID(esiid,custId,objSession);
            logger.info("GOT MONTH DETAILS BY ESIID AND CUSTOMER ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE MONTH DETAILS BY ESIID AND CUSTOMER ID", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return result;
    }
    public List getAllESIID(int prospectiveCustomerId)
    {
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL ESIID BY PROSPECTIVE CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            objList = getAllESIID(prospectiveCustomerId, objSession);
            logger.info("GOT ALL ESIID BY PROSPECTIVE CUSTOMER ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ESIID", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
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
    
    
    private MonthDetails splitMonthDetails(MonthDetails objMonthDetails)
    {
        logger.info("SPLITING MONTH DETAILS");
        Date startDate = objMonthDetails.getMeterReadfromDate();
        Date endDate = objMonthDetails.getMeterReadEndDate();
        GregorianCalendar gcStart = new GregorianCalendar();
        gcStart.setTime(startDate);
        GregorianCalendar gcEnd = new GregorianCalendar();
        gcEnd.setTime(endDate);
        Vector vecIndividualMonthDetails = new Vector();
        while(gcStart.getTime().compareTo(gcEnd.getTime())<=0)
        {
            IndividualMonthDetails  objIndividualMonthDetails = new IndividualMonthDetails();
            objIndividualMonthDetails.setTerm(objMonthDetails.getMonth());
            objIndividualMonthDetails.setStartDate(gcStart.getTime());
            objIndividualMonthDetails.setMonth(gcStart.get(Calendar.MONTH)+1);
            gcStart.set(Calendar.DATE,gcStart.getActualMaximum(Calendar.DATE));
            if(gcStart.getTime().after(gcEnd.getTime()))
            {
                objIndividualMonthDetails.setEndDate(gcEnd.getTime());
            }
            else
            {
                objIndividualMonthDetails.setEndDate(gcStart.getTime());
            }
            if(BuildConfig.DMODE)
                System.out.println("updateDayTypeDetails:Starts:"+new Date());
            objIndividualMonthDetails = this.updateDayTypeDetails(objIndividualMonthDetails);
            if(BuildConfig.DMODE)
                System.out.println("updateDayTypeDetails:Ends:"+new Date());
            vecIndividualMonthDetails.add(objIndividualMonthDetails);
            gcStart.add(Calendar.DATE,1);
        }
        objMonthDetails.setIndividualMonthDetails(vecIndividualMonthDetails);
        logger.info("MONTH DETAILS IS SPLITTED");
        return objMonthDetails;
    }
    
    private List updateIndividualMonthDetails(List objMonthDetailsList)
    {
        logger.info("UPDATING INDIVIDUAL MONTH DETAILS");
        MonthDetails objMonthDetails = null;
        List objList = new LinkedList();
        for(int i=0;i<objMonthDetailsList.size();i++)
        {
            objMonthDetails = (MonthDetails )objMonthDetailsList.get(i);
            objMonthDetails = this.splitMonthDetails(objMonthDetails);
            objList.add(objMonthDetails);
        }
        logger.info("INDIVIDUAL MONTH DETAILS IS UPDATED");
        return objList;
    }
     
    private IndividualMonthDetails updateDayTypeDetails(IndividualMonthDetails objIndividualMonthDetails)
    {
        logger.info("UPDATING DAY TYPE DETAILS");
        int noOfWD = 0;
        int noOfWE = 0;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(objIndividualMonthDetails.getStartDate());
        while(gc.getTime().compareTo(objIndividualMonthDetails.getEndDate())<=0)
        {
            if(gc.get(Calendar.DAY_OF_WEEK) == 1 || gc.get(Calendar.DAY_OF_WEEK) == 7)
            {
                noOfWE++;
            }
            else
            {
                noOfWD++;
            }
            gc.add(Calendar.DATE,1);
        }
        List objList = (ArrayList)CalendarDAO.getHolidays(objIndividualMonthDetails.getStartDate(), objIndividualMonthDetails.getEndDate());
        Iterator itr = objList.iterator();
        while(itr.hasNext())
        {
            Date date = (Date)itr.next();
            gc.setTime(date);
            if(gc.get(Calendar.DAY_OF_WEEK) != 1 && gc.get(Calendar.DAY_OF_WEEK) != 7)
            {
                noOfWD--;
                noOfWE++;
            }
        }
        objIndividualMonthDetails.setNoOfWD(noOfWD);
        objIndividualMonthDetails.setNoOfWE(noOfWE);
        logger.info("DAY TYPE DETAILS ARE UPDATED");
        return objIndividualMonthDetails;
    }
    
    public static void main(String args[])
    {
        /*Session objSession = HibernateUtil.getSession();
        if(BuildConfig.DMODE)
            System.out.println("Starts:"+new Date());
        new LoadExtrapolationDAO().loadProfileUsage(470, objSession);
        if(BuildConfig.DMODE)
            System.out.println("Ends:"+new Date());*/
        /*
        Date startDate = new Date("02/14/2003");
        Date endDate = new Date("01/01/2004");
        GregorianCalendar gcStart = new GregorianCalendar();
        gcStart.setTime(startDate);
        GregorianCalendar gcEnd = new GregorianCalendar();
        gcEnd.setTime(endDate);
        while(gcStart.getTime().compareTo(gcEnd.getTime())<=0)
        {
            System.out.println("startDate:"+gcStart.getTime());
            gcStart.set(Calendar.DATE,gcStart.getActualMaximum(Calendar.DATE));
            if(gcStart.getTime().after(gcEnd.getTime()))
            {
                System.out.println("endDate:"+gcEnd.getTime());
            }
            else
            {
                System.out.println("endDate:"+gcStart.getTime());
            }
            gcStart.add(Calendar.DATE,1);
        }
    */
        //new LoadExtrapolationDAO().getAllESIID(6);
        
        new CalendarDAO().reload();
        LossFactorLookupDAO.getAllLossFactorLookupVOS();
        DLFDAO.getAllDLF();
        DLFCodeDAO.getAllDLFCodeIdentifiers();
        TLFDAO objTLFDAO = new TLFDAO();
        HibernateUtil.hmTLF = objTLFDAO.getAllTLF();
        
        LoadExtrapolationDAO obj = new LoadExtrapolationDAO();
        
        //obj.getMonthDetailsByESIID("1008901023808149470100",16);
            //System.out.println(obj.checkAvailIDR("1008901010186262413100"));
        obj.loadProfileUsage(847,HibernateUtil.getSession());
    }
}

/*
*$Log: LoadExtrapolationDAO.java,v $
*Revision 1.4  2008/03/13 11:02:13  tannamalai
*loss factor added
*
*Revision 1.3  2008/02/08 06:53:47  tannamalai
*last commit before table split up
*
*Revision 1.2  2007/12/21 06:15:11  kduraisamy
*Holiday checking error solved.
*
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/27 14:12:38  tannamalai
*idr issue solved
*
*Revision 1.1  2007/10/30 05:51:54  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:23  jnadesan
*initail MXEP commit
*
*Revision 1.47  2007/10/25 07:03:14  kduraisamy
*load profile change IDR to BUSMED removed.
*
*Revision 1.46  2007/09/04 05:03:47  spandiyarajan
*removed unwanted imports
*
*Revision 1.45  2007/08/31 14:49:27  sramasamy
*Log message is added for log file.
*
*Revision 1.44  2007/08/03 06:37:12  kduraisamy
*If IDR is not having 576 value, We have assigned BUSMEDLF profile.
*
*Revision 1.43  2007/08/01 08:03:45  kduraisamy
*aggregated load profile maintained prospective customer id and esiId wise.
*
*Revision 1.42  2007/08/01 05:18:02  kduraisamy
*objsession properly closed or calling procedure properly done.
*
*Revision 1.41  2007/07/31 11:39:32  kduraisamy
*objsession properly closed or calling procedure properly done.
*
*Revision 1.40  2007/07/25 05:57:17  jnadesan
*Esiid selection checked against valid
*
*Revision 1.39  2007/07/20 11:05:26  jnadesan
*method added to compute overall load factor
*
*Revision 1.38  2007/06/12 12:55:25  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.37  2007/05/11 12:57:45  kduraisamy
*aggregated load profile error rectified.
*
*Revision 1.36  2007/05/11 11:42:17  kduraisamy
*aggregated load profile error rectified.
*
*Revision 1.35  2007/05/10 06:53:32  kduraisamy
*aggregated load profiles error rectified.
*
*Revision 1.34  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.33  2007/05/08 12:16:13  kduraisamy
*getAggregatedProfileDetails() added.
*
*Revision 1.32  2007/05/07 12:14:14  kduraisamy
*IDR Profile calculation added.
*
*Revision 1.31  2007/04/30 10:13:41  kduraisamy
*esi id valid condition removed.
*
*Revision 1.30  2007/04/25 09:42:45  kduraisamy
*formatter error rectified in PIC View.
*
*Revision 1.29  2007/04/24 09:27:37  kduraisamy
*date format changed to MM-dd-yyyy.
*
*Revision 1.28  2007/04/24 03:53:32  kduraisamy
*only valid esiIds taken for run.
*
*Revision 1.25  2007/04/23 05:32:23  kduraisamy
*esiId Preference added.
*
*Revision 1.24  2007/04/22 16:25:52  kduraisamy
*error handling added.
*
*Revision 1.23  2007/04/17 13:48:21  kduraisamy
*price run performance took place.
*
*Revision 1.22  2007/04/16 13:18:22  kduraisamy
*unwanted transaction removed.
*
*Revision 1.21  2007/04/06 12:31:58  kduraisamy
*TLF AND DLF COMPLETED.
*
*Revision 1.20  2007/03/31 06:40:30  kduraisamy
*unwanted println removed.
*
*Revision 1.19  2007/03/29 05:33:23  kduraisamy
*esiId Prefix datatype changed as string.
*
*Revision 1.18  2007/03/24 06:28:07  kduraisamy
*load factor taken removed.
*
*Revision 1.17  2007/03/24 05:07:05  kduraisamy
*divide by zero error
*
*Revision 1.16  2007/03/23 09:24:31  kduraisamy
*loadFactor and usage for all the EsiId methods added.
*
*Revision 1.15  2007/03/13 03:50:39  jnadesan
*esiid details taken by customer wise as well as esiid wise
*
*Revision 1.14  2007/03/09 04:14:24  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.13  2007/03/08 16:38:51  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.12  2007/03/08 16:30:34  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.11  2007/03/06 03:53:05  jnadesan
*session closed
*
*Revision 1.10  2007/03/06 03:27:21  jnadesan
*query and method has changed for pic import view page
*
*Revision 1.9  2007/02/28 06:53:57  kduraisamy
*optimization started by sriram.
*
*Revision 1.8  2007/02/27 04:49:09  kduraisamy
*indentation.
*
*Revision 1.7  2007/02/20 12:37:23  kduraisamy
*optimization took place.
*
*Revision 1.6  2007/02/13 14:04:57  kduraisamy
*BillingDemand related mapping completed.
*
*Revision 1.5  2007/02/12 04:49:09  kduraisamy
*session.beginTransaction() added.
*
*Revision 1.4  2007/02/10 12:22:49  kduraisamy
*delete query included.
*
*Revision 1.3  2007/02/10 09:30:18  kduraisamy
*split method optimized.
*
*Revision 1.2  2007/02/10 07:41:20  kduraisamy
*method abstraction done.
*
*Revision 1.1  2007/02/09 11:56:42  kduraisamy
*pricing core algorithm almost finished.
*
*
*/