/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	LoadProfileTypesDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.savant.pricing.calculation.valueobjects.AggregatorLoadProfilesVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.transferobjects.AggregatedLoadProfileDetails;
import com.savant.pricing.valueobjects.LoadProfileTypesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoadProfileTypesDAO
{
    private static Logger logger = Logger.getLogger(LoadProfileTypesDAO.class);
    
    public HashMap getAllProfileTypes(Filter[] filter, String sortBy, boolean ascending, int startIndex, int displayCount)
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
            logger.info("GETTING ALL PROFILE DETAILS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(LoadProfileTypesVO.class);
            if(filter != null)
            {
                for(int i=0;i<filter.length;i++)
                {
                    objCriteria.add(Restrictions.like(filter[i].getFieldName(),filter[i].getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filter[i].getSpecialFunction())));
                }
            }
            totRecordCount = new Integer(objCriteria.list().size());
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
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",objList);
            logger.info("GOT ALL PROFILE DETAILS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL PROFILE DETAILS", e);
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

    public LoadProfileTypesVO getLoadProfile(String loadProfile)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        LoadProfileTypesVO objLoadProfileTypesVO = null;
        try
        {
            logger.info("GETTING LOAD PROFITLE");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(LoadProfileTypesVO.class).add(Restrictions.eq("profileType",loadProfile));
            objLoadProfileTypesVO = (LoadProfileTypesVO)objCriteria.uniqueResult();
            logger.info("GOT LOAD PROFILE");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE LOAD PROFILE DETAILS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objLoadProfileTypesVO;
    }

    public List getAllProfileTypes()
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
            logger.info("GETTING ALL PROFILE DETAILS TYPES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(LoadProfileTypesVO.class);
            objList = objCriteria.list();
            logger.info("GOT ALL PROFILE DETAILS TYPES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL PROFILE TYPES", e);
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
    
    public HashMap getAggregatedLoadProfile(int customerRefId, String esiId)
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
        HashMap hmHourDetails = null;
        HashMap hmResult = new HashMap();
        HashMap hm = new HashMap();
        float oldValue = 0;
        PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
        int prospectiveCustomerId = objPriceRunCustomerDAO.getPriceRunCustomer(customerRefId).getProspectiveCustomer().getProspectiveCustomerId();
        try
        {
            logger.info("GETTING AGGREGATED LOAD PROFILE DETAILS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(AggregatorLoadProfilesVO.class).add(Restrictions.eq("prospectiveCustomer.prospectiveCustomerId",new Integer(prospectiveCustomerId)));
            StringTokenizer st = new StringTokenizer(esiId,",");
            String[] esiIds = new String[st.countTokens()];
            int i = 0;
            while(st.hasMoreTokens())
            {
                esiIds[i] = st.nextToken();	
                i++;
            }
            if(esiIds.length>0)
                objCriteria.add(Restrictions.in("esiId", esiIds));
            objList = objCriteria.list();
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                AggregatorLoadProfilesVO objAggregatorLoadProfilesVO = (AggregatorLoadProfilesVO)itr.next();
                if(hmResult.containsKey(new Integer(objAggregatorLoadProfilesVO.getDayType().getDayTypeId())))
                {
                    hm = (HashMap)hmResult.get(new Integer(objAggregatorLoadProfilesVO.getDayType().getDayTypeId()));
                    if(hm.containsKey(new Integer(objAggregatorLoadProfilesVO.getMonth())))
                    {
                        hmHourDetails = (HashMap)hm.get(new Integer(objAggregatorLoadProfilesVO.getMonth()));
                        if(hmHourDetails.containsKey(new Integer(objAggregatorLoadProfilesVO.getHour())))
                        {
                            oldValue = Float.parseFloat(String.valueOf(hmHourDetails.get(new Integer(objAggregatorLoadProfilesVO.getHour()))));
                        }
                        else
                        {
                            oldValue = 0;
                        }
                        float newValue = oldValue + objAggregatorLoadProfilesVO.getValue();
                        hmHourDetails.put(new Integer(objAggregatorLoadProfilesVO.getHour()), new Float(newValue));
                        hm.put(new Integer(objAggregatorLoadProfilesVO.getMonth()), hmHourDetails);
                    }
                    else
                    {
                        hmHourDetails = new HashMap();
                        hmHourDetails.put(new Integer(objAggregatorLoadProfilesVO.getHour()), new Float(objAggregatorLoadProfilesVO.getValue()));
                        hm.put(new Integer(objAggregatorLoadProfilesVO.getMonth()), hmHourDetails);
                    }
                }
                else
                {
                    hm = new HashMap();
                    hmHourDetails = new HashMap();
                    hmHourDetails.put(new Integer(objAggregatorLoadProfilesVO.getHour()), new Float(objAggregatorLoadProfilesVO.getValue()));
                    hm.put(new Integer(objAggregatorLoadProfilesVO.getMonth()), hmHourDetails);
                    hmResult.put(new Integer(objAggregatorLoadProfilesVO.getDayType().getDayTypeId()),hm);
                }
            }
            logger.info("GOT AGGREGATED LOAD PROFILE DETAILS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET AGGREGATED LOAD PROFILE DETAILS", e);
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
    
    public HashMap getAggregatedLoadProfileDetails(int customerRefId, String esiId)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Session objSession = null;
        List objList = null;
        HashMap hmResult = new HashMap();
        PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
        int prospectiveCustomerId = objPriceRunCustomerDAO.getPriceRunCustomer(customerRefId).getProspectiveCustomer().getProspectiveCustomerId();
        
        try
        {
            logger.info("GETTING AGGREGATED LOAD PROFILE DETAILS");
            objSession = HibernateUtil.getSession();
            String str = "";
            StringTokenizer st = new StringTokenizer(esiId.trim(),",");
            while(st.hasMoreTokens())
            {
                if(str.length()<=0)
                    str = "'"+st.nextToken()+"'";
                else
                    str = str + ",'"+st.nextToken()+"'";
            }
            
            if(esiId.trim().length()>0)
            {
                objList = objSession.createSQLQuery("SELECT a.mnth,sum(OnPeakMax) as OnPeakMax, sum(OnPeakMin) as OnPeakMin, sum(OnPeakAvg) as OnPeakAvg, sum(OffPeakMax) as OffPeakMax, sum(OffPeakMin) as OffPeakMin,sum(OffPeakAvg)as OffPeakAvg from (select [Mnth],  max(value) as OnPeakMax, min (value) as OnPeakMin,ESIID, avg(value) as OnPeakAvg FROM [dbo].[PRC_Aggregated_Load_Profile] where Prospective_Cust_Id =? and ESIID IN("+str+") and [hour] between 7 and 22 and day_type_id = ? group by mnth,ESIID) a inner join (select [Mnth],  max(value) as OffPeakMax, min (value) as OffPeakMin,ESIID , avg(value) as OffPeakAvg FROM [PRC_Aggregated_Load_Profile] where Prospective_Cust_Id = ? and ESIID IN("+str+") and [hour] in (1,2,3,4,5,6,23,24) and day_type_id = ? group by mnth,esiid) b on a.mnth = b.mnth AND A.ESIID=B.ESIID group by a.mnth order by a.mnth").setInteger(0,prospectiveCustomerId).setInteger(1,2).setInteger(2,prospectiveCustomerId).setInteger(3,2).list();
            }
            else
            {
                objList = objSession.createSQLQuery("SELECT a.mnth,sum(OnPeakMax) as OnPeakMax, sum(OnPeakMin) as OnPeakMin, sum(OnPeakAvg) as OnPeakAvg, sum(OffPeakMax) as OffPeakMax, sum(OffPeakMin) as OffPeakMin,sum(OffPeakAvg)as OffPeakAvg from (select [Mnth],  max(value) as OnPeakMax, min (value) as OnPeakMin,ESIID, avg(value) as OnPeakAvg FROM [dbo].[PRC_Aggregated_Load_Profile] where Prospective_Cust_Id =? and [hour] between 7 and 22 and day_type_id = ? group by mnth,ESIID) a inner join (select [Mnth],  max(value) as OffPeakMax, min (value) as OffPeakMin,ESIID , avg(value) as OffPeakAvg FROM [dbo].[PRC_Aggregated_Load_Profile] where Prospective_Cust_Id = ? and [hour] in (1,2,3,4,5,6,23,24) and day_type_id = ? group by mnth,esiid) b on a.mnth = b.mnth AND A.ESIID=B.ESIID group by a.mnth order by a.mnth").setInteger(0,prospectiveCustomerId).setInteger(1,2).setInteger(2,prospectiveCustomerId).setInteger(3,2).list();
            }
            Iterator itr = objList.iterator();
            LinkedHashMap hmAggregatedDetails = new LinkedHashMap();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                int month = Integer.parseInt(String.valueOf(innerRow[0]));
                AggregatedLoadProfileDetails objAggregatedLoadProfileDetails = new AggregatedLoadProfileDetails();
                objAggregatedLoadProfileDetails.setOnPeakMax(Float.parseFloat(String.valueOf(innerRow[1])));
                objAggregatedLoadProfileDetails.setOnPeakMin(Float.parseFloat(String.valueOf(innerRow[2])));
                objAggregatedLoadProfileDetails.setOnPeakAvg(Float.parseFloat(String.valueOf(innerRow[3])));
                objAggregatedLoadProfileDetails.setOffPeakMax(Float.parseFloat(String.valueOf(innerRow[4])));
                objAggregatedLoadProfileDetails.setOffPeakMin(Float.parseFloat(String.valueOf(innerRow[5])));
                objAggregatedLoadProfileDetails.setOffPeakAvg(Float.parseFloat(String.valueOf(innerRow[6])));
                hmAggregatedDetails.put(new Integer(month), objAggregatedLoadProfileDetails);
            }
            hmResult.put(new Integer(2),hmAggregatedDetails);
            if(esiId.trim().length()>0)
            {
                objList = objSession.createSQLQuery("SELECT a.mnth,sum(OnPeakMax) as OnPeakMax, sum(OnPeakMin) as OnPeakMin, sum(OnPeakAvg) as OnPeakAvg, sum(OffPeakMax) as OffPeakMax, sum(OffPeakMin) as OffPeakMin,sum(OffPeakAvg)as OffPeakAvg from (select [Mnth],  max(value) as OnPeakMax, min (value) as OnPeakMin,ESIID, avg(value) as OnPeakAvg FROM [PRC_Aggregated_Load_Profile] where Prospective_Cust_Id =? and ESIID IN("+str+") and [hour] between 7 and 22 and day_type_id = ? group by mnth,ESIID) a inner join (select [Mnth],  max(value) as OffPeakMax, min (value) as OffPeakMin,ESIID , avg(value) as OffPeakAvg FROM [PRC_Aggregated_Load_Profile] where Prospective_Cust_Id = ? and ESIID IN("+str+") and [hour] in (1,2,3,4,5,6,23,24) and day_type_id = ? group by mnth,esiid) b on a.mnth = b.mnth AND A.ESIID=B.ESIID group by a.mnth order by a.mnth").setInteger(0,prospectiveCustomerId).setInteger(1,3).setInteger(2,prospectiveCustomerId).setInteger(3,3).list();
            }
            else
            {
                objList = objSession.createSQLQuery("SELECT a.mnth,sum(OnPeakMax) as OnPeakMax, sum(OnPeakMin) as OnPeakMin, sum(OnPeakAvg) as OnPeakAvg, sum(OffPeakMax) as OffPeakMax, sum(OffPeakMin) as OffPeakMin,sum(OffPeakAvg)as OffPeakAvg from (select [Mnth],  max(value) as OnPeakMax, min (value) as OnPeakMin,ESIID, avg(value) as OnPeakAvg FROM [PRC_Aggregated_Load_Profile] where Prospective_Cust_Id =? and [hour] between 7 and 22 and day_type_id = ? group by mnth,ESIID) a inner join (select [Mnth],  max(value) as OffPeakMax, min (value) as OffPeakMin,ESIID , avg(value) as OffPeakAvg FROM [PRC_Aggregated_Load_Profile] where Prospective_Cust_Id = ? and [hour] in (1,2,3,4,5,6,23,24) and day_type_id = ? group by mnth,esiid) b on a.mnth = b.mnth AND A.ESIID=B.ESIID group by a.mnth order by a.mnth").setInteger(0,prospectiveCustomerId).setInteger(1,3).setInteger(2,prospectiveCustomerId).setInteger(3,3).list();
            }
            itr = objList.iterator();
            hmAggregatedDetails = new LinkedHashMap();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                int month = Integer.parseInt(String.valueOf(innerRow[0]));
                AggregatedLoadProfileDetails objAggregatedLoadProfileDetails = new AggregatedLoadProfileDetails();
                objAggregatedLoadProfileDetails.setOnPeakMax(Float.parseFloat(String.valueOf(innerRow[1])));
                objAggregatedLoadProfileDetails.setOnPeakMin(Float.parseFloat(String.valueOf(innerRow[2])));
                objAggregatedLoadProfileDetails.setOnPeakAvg(Float.parseFloat(String.valueOf(innerRow[3])));
                objAggregatedLoadProfileDetails.setOffPeakMax(Float.parseFloat(String.valueOf(innerRow[4])));
                objAggregatedLoadProfileDetails.setOffPeakMin(Float.parseFloat(String.valueOf(innerRow[5])));
                objAggregatedLoadProfileDetails.setOffPeakAvg(Float.parseFloat(String.valueOf(innerRow[6])));
                hmAggregatedDetails.put(new Integer(month), objAggregatedLoadProfileDetails);
            }
            hmResult.put(new Integer(3),hmAggregatedDetails);
            logger.info("GOT AGGREGATED LOAD PROFILE DETAILS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET AGGREGATED LOAD PROFILE DETAILS", e);
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
    
    public HashMap getAllEsiIds(int prospectiveCustomerId)
    {
        Session objSession = null;
        HashMap hm = new HashMap();
        try
        {
            logger.info("GETTING ALL ESIIDs BY CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select distinct esiId from AggregatorLoadProfilesVO as aggregated where aggregated.prospectiveCustomer.prospectiveCustomerId = ?");
            objQuery.setInteger(0,prospectiveCustomerId);
            Iterator itr = objQuery.iterate();
            while(itr.hasNext())
            {
                Object row = itr.next();
                hm.put(row,row);
            }
            logger.info("GOT ALL ESIIDs BY CUSTOMER ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ESIIDs BY CUSTOMER ID", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL ESIIDs BY CUSTOMER ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hm;
    }
    
    public HashMap getAllEsiIds(int prospectiveCustomerId, int tdspId)
    {
        Session objSession = null;
        HashMap hm = new HashMap();
        try
        {
            logger.info("GETTING ALL ESIIDs BY CUSTOMER ID AND TDSP ID");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select distinct aggregated.esiId from AggregatorLoadProfilesVO as aggregated, TDSPVO as tdspvo where aggregated.prospectiveCustomer.prospectiveCustomerId = ? and substring(aggregated.esiId,1,7) = tdspvo.esiIdPrefix and tdspvo.tdspIdentifier = ?");
            objQuery.setInteger(0,prospectiveCustomerId);
            objQuery.setInteger(1,tdspId);
            Iterator itr = objQuery.iterate();
            while(itr.hasNext())
            {
                Object row = itr.next();
                hm.put(row,row);
            }
            logger.info("GOT ALL ESIIDs BY CUSTOMER ID AND TDSP ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ESIIDs BY CUSTOMER ID AND TDSP ID", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL ESIIDs BY CUSTOMER ID AND TDSP ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hm;
    }
    
    public HashMap getAllEsiIdsByCongestionZone(int prospectiveCustomerId, int congestionZoneId)
    {
        Session objSession = null;
        HashMap hm = new HashMap();
        try
        {
            logger.info("GETTING ALL ESIIDs BY CONGESTION ZONE");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createSQLQuery("select distinct aggregated.ESIID from PRC_Aggregated_Load_Profile as aggregated, PRC_PIC AS PRCPIC, PRC_WEATHER_ZONES AS WEATHERZONES WHERE aggregated.ESIID = PRCPIC.ESIID AND WEATHERZONES.CONGESTION_ZONE_ID = ? AND SUBSTRING(Load_Profile,CHARINDEX('_',Load_Profile,0)+1,CHARINDEX('_',Load_Profile,CHARINDEX('_',Load_Profile,0)+1)-CHARINDEX('_',Load_Profile,0)-1) = WEATHERZONES.WEATHER_ZONE_CODE  AND aggregated.Prospective_Cust_ID = ?");
            objQuery.setInteger(0,congestionZoneId);
            objQuery.setInteger(1,prospectiveCustomerId);
            List objList = objQuery.list();
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                Object row = itr.next();
                hm.put(row,row);
            }
            logger.info("GOT ALL ESIIDs BY CONGESTION ZONE");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ESIIDs BY CONGESTION ZONE", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL ESIIDs BY CONGESTION ZONE", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hm;
    }
    
    public HashMap getAllEsiIdsByTDSPCongestionZone(int prospectiveCustomerId, int tdspId, int congestionZoneId)
    {
        Session objSession = null;
        HashMap hm = new HashMap();
        try
        {
            logger.info("GETTING ALL ESIIDs BY TDSP ID AND CONGESTION ZONE ID");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createSQLQuery("select distinct aggregated.ESIID from PRC_Aggregated_Load_Profile as aggregated, PRC_TDSP AS TDSP, PRC_PIC AS PRCPIC, PRC_WEATHER_ZONES AS WEATHERZONES WHERE aggregated.ESIID = PRCPIC.ESIID AND WEATHERZONES.CONGESTION_ZONE_ID = ? AND SUBSTRING(Load_Profile,CHARINDEX('_',Load_Profile,0)+1,CHARINDEX('_',Load_Profile,CHARINDEX('_',Load_Profile,0)+1)-CHARINDEX('_',Load_Profile,0)-1) = WEATHERZONES.WEATHER_ZONE_CODE  AND aggregated.Prospective_Cust_ID = ? AND TDSP.TDSP_ID = ? AND substring(aggregated.ESIID,1,7) = TDSP.ESIID_Prefix");
            objQuery.setInteger(0,congestionZoneId);
            objQuery.setInteger(1,prospectiveCustomerId);
            objQuery.setInteger(2,tdspId);
            List objList = objQuery.list();
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                Object row = itr.next();
                hm.put(row,row);
            }
            logger.info("GOT ALL ESIIDs BY TDSP ID AND CONGESTION ZONE ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ESIIDs BY TDSP ID AND CONGESTION ZONE ID", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL ESIIDs BY TDSP ID AND CONGESTION ZONE ID", e);
            e.printStackTrace();
        }
        finally
        {
            if (objSession != null)
            {
                objSession.close();
            }
        }
        return hm;
    }
    
    public LoadProfileTypesVO getLoadProfile( int loadId ) 
    {
        Session objSession                       = null;
        LoadProfileTypesVO objLoadProfileTypesVO = null;
        
        try 
        {
            logger.info("GETTING LOAD PROFILE TYPES BY LOAD ID");
            objSession  = HibernateUtil.getSession();
            objLoadProfileTypesVO = ( LoadProfileTypesVO ) objSession.get( LoadProfileTypesVO.class, new Integer( loadId ) );
            logger.info("GOT LOAD PROFILE TYPES BY LOAD ID");
        } 
        catch ( HibernateException e ) 
        {
            logger.error("HIBERNATE EXCEPTION DURING GET LOAD PROFILE TYPES BY LOAD ID", e);
            e.printStackTrace();
        }
        finally 
        {
            if(objSession != null)
            {                    
                objSession.close();
            }
        }
        return objLoadProfileTypesVO;
    }
    
    public boolean updateLoadProfile( LoadProfileTypesVO objLoadProfileTypesVO )
    {
        boolean updateResult = false;
        Session objSession   = null;
        
        try
        {
            logger.info("UPDATING LOAD PROFILE DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update( objLoadProfileTypesVO );
            objSession.getTransaction().commit();
            updateResult = true;
            objSession.flush();
            logger.info("GOT LOAD PROFILE DETAILS");
        }
        catch( HibernateException e )
        {
            logger.error("HIBERNATE EXCEPTION DURING GET LOAD PROFILE DETAILS", e);
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
    
    public static void main(String args[])
    {
        if(BuildConfig.DMODE)
            System.out.println(new LoadProfileTypesDAO().getAggregatedLoadProfile(590,"1008901012126105259100"));
        
        LoadProfileTypesDAO obj = new LoadProfileTypesDAO();
        System.out.print(" ** "+ obj.getLoadProfile("RESLOWR"));
		//HashMap obj = new LoadProfileTypesDAO().getAggregatedLoadProfileDetails(453);
		//System.out.println(obj.get(new Integer(2)));
    }
    
    
    
    
    
}

/*
*$Log: LoadProfileTypesDAO.java,v $
*Revision 1.2  2008/01/23 08:35:14  tannamalai
*jasper reports changes
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.20  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.19  2007/08/23 07:27:53  jnadesan
*imports organized
*
*Revision 1.18  2007/08/09 15:06:55  sramasamy
*Make Valid/invalid option provided
*
*Revision 1.17  2007/06/13 04:10:44  kduraisamy
*2005 jar included.
*
*Revision 1.16  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.15  2007/05/10 06:53:32  kduraisamy
*aggregated load profiles error rectified.
*
*Revision 1.14  2007/05/09 13:37:14  kduraisamy
*min max query problem recitified.
*
*Revision 1.13  2007/05/09 13:27:57  kduraisamy
*min max query problem recitified.
*
*Revision 1.12  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.11  2007/05/08 13:46:37  jnadesan
*load profile chart plotted by esiid wise
*
*Revision 1.10  2007/05/08 13:23:48  kduraisamy
*getAggregatedProfileDetails() added.
*
*Revision 1.9  2007/05/08 12:43:52  kduraisamy
*getAggregatedProfileDetails() added.
*
*Revision 1.8  2007/05/08 12:08:03  kduraisamy
*getAggregatedProfileDetails() added.
*
*Revision 1.7  2007/05/08 09:19:13  kduraisamy
*getAggregatedProfileDetails() added.
*
*Revision 1.6  2007/05/07 14:36:19  jnadesan
*return value chnged
*
*Revision 1.4  2007/05/07 13:07:24  kduraisamy
*getAggregatedProfileDetails() added.
*
*Revision 1.3  2007/05/07 12:14:42  kduraisamy
*IDR Profile calculation added.
*
*Revision 1.2  2007/03/28 07:01:19  kduraisamy
*getAllLoadProfileTypes filter added.
*
*Revision 1.1  2007/02/03 05:33:15  kduraisamy
*Load Profile Types mapping included.
*
*
*/