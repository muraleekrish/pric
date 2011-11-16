/*
 * Created on Apr 10, 2007
 *
 * ClassName	:  	PICDAO.java
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.calculation.dao.LoadExtrapolationDAO;
import com.savant.pricing.calculation.valueobjects.PICUsageVO;
import com.savant.pricing.calculation.valueobjects.PICVO;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.transferobjects.AnnualkWhDetails;
import com.savant.pricing.transferobjects.ESIIDDetails;
import com.savant.pricing.valueobjects.TDSPVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PICDAO
{
    private static Logger logger = Logger.getLogger(PICDAO.class);
    
    LoadExtrapolationDAO objLoadExtrapolationDAO = new LoadExtrapolationDAO();
    public ESIIDDetails getESIIDInfo(String esiId)
    {
        Session objSession = null;
        Criteria objCriteria = null;
        ESIIDDetails objESIIDDetails = null;
        try
        {
            logger.info("GETTING ESIID INFORMATION");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(PICVO.class).add(Restrictions.eq("esiId",esiId));
            Iterator itr = objCriteria.list().iterator();
            if(itr.hasNext())
            {
                objESIIDDetails = new ESIIDDetails();
                PICVO objPICVO = (PICVO)itr.next();
                TDSPVO objTDSPVO = objLoadExtrapolationDAO.getTDSPByESIID(esiId);
                objESIIDDetails.setEsiId(esiId);
                objESIIDDetails.setTdspName(objTDSPVO.getTdspName());
                objESIIDDetails.setServiceAddress(objPICVO.getAddress1());
                objESIIDDetails.setZipCode(objPICVO.getZipCode().getZipCode());
            }
            logger.info("GOT ESIID INFORMATION");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE ESIID INFORMATION", e);
            e.printStackTrace();
            if(e.toString().indexOf("hibernate.exception.ConstraintViolationException")!= -1)
            {
                throw new HibernateException("Customer Name Or CMS Id Already Exists");
            }
            throw new HibernateException("General Error");
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING  GET THE ESIID INFORMATION", e);
           e.printStackTrace();
           throw new HibernateException("General Error");
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objESIIDDetails;
    }

    public int getNoOfESIID(int prospectiveCustomerId)
    {
        Session objSession = null;
        int count = 0;
        try
        {
            logger.info("GETTING NUMBER OF ESIID FOR THE CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            
            Query objQuery = objSession.createQuery("select count(picvo.esiId) as count from PICVO as picvo where picvo.valid = ? and picvo.customer.prospectiveCustomerId = ?");
            objQuery.setBoolean(0,true);
            objQuery.setInteger(1,prospectiveCustomerId);
            Iterator itr = objQuery.iterate();
            if(itr.hasNext())
            {
                count = ((Long)itr.next()).intValue();
                logger.info("GOT NUMBER OF ESIID FOR THE CUSTOMER ID");
            }
            else
            {
                logger.info("NO ESIID FOR THE CUSTOMER");
            }
            
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE NUMBER OF ESIID FOR THE CUSTOMER", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return count;
    }
    
    public PICVO getPICVO(int prscustId, String ESIID)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        PICVO objPICVO = null;
        try
        {
            logger.info("GETTING PIC BY CUSTOMER ID AND ESIID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(PICVO.class).add(Restrictions.eq("customer.prospectiveCustomerId", new Integer(prscustId))).add(Restrictions.eq("esiId",ESIID));
            objPICVO = (PICVO)objCriteria.uniqueResult();
            logger.info("GOT PIC BY CUSTOMER ID AND ESIID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE PIC BY CUSTOMER ID AND ESIID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objPICVO;
    }

    public PICDAO()
    {
    }
    
    public Collection getAllPICUsages(int picReferenceId)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL PIC USAGE");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(PICUsageVO.class).add(Restrictions.eq("picRef.picReferenceId", new Integer(picReferenceId)));
            objList = objCriteria.list();
            logger.info("GOT ALL PIC USAGE");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL PIC USAGE", e);
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
    
    public Collection getTotalkWh(int prospectiveCustomerId, String esiIds)
    {
        Session objSession = null;
        List objResultList = new ArrayList();
        try
        {
            logger.info("GETTING TOTAL kWh BY CUSTOMER ID AND ESIIDs");
            objSession = HibernateUtil.getSession();
            StringTokenizer st = new StringTokenizer(esiIds.trim(),",");
            Query objQuery = null;
            float totalkWh = 0;
            List objList = new ArrayList();
            AnnualkWhDetails objAnnualkWhDetails = null;
            

            objAnnualkWhDetails = new AnnualkWhDetails();
            objQuery = objSession.createQuery("select sum(picUsage.historicalUsage) as usage from PICVO as picvo, PICUsageVO as picUsage where picvo.customer.prospectiveCustomerId = ? and picvo.picReferenceId = picUsage.picRef.picReferenceId");
            objQuery.setInteger(0, prospectiveCustomerId);
            Iterator itr = objQuery.iterate();
            if(itr.hasNext())
            {
                totalkWh = ((Double)itr.next()).floatValue();
            }
            while(st.hasMoreTokens())
            {
                String esiId = st.nextToken().trim();
                if(!esiId.equals("All"))
                {
                    objAnnualkWhDetails = new AnnualkWhDetails();
                    objQuery = objSession.createQuery("select sum(picUsage.historicalUsage) as usage from PICVO as picvo, PICUsageVO as picUsage where picvo.customer.prospectiveCustomerId = ? and picvo.esiId = ? and picvo.picReferenceId = picUsage.picRef.picReferenceId");
                    objQuery.setInteger(0, prospectiveCustomerId);
                    objQuery.setString(1, esiId);
                    itr = objQuery.iterate();
                    float kWh = 0;
                    if(itr.hasNext())
                    {
                        kWh = ((Double)itr.next()).floatValue();
                        objAnnualkWhDetails.setEsiId(esiId);
                        objAnnualkWhDetails.setKWh(kWh);
                        objList.add(objAnnualkWhDetails);
                    }
                }
                else
                {
                    objAnnualkWhDetails.setEsiId(esiId);
                    objAnnualkWhDetails.setKWh(totalkWh);
                    objList.add(objAnnualkWhDetails);
                }
            }
            itr = objList.iterator();
            while(itr.hasNext())
            {
                objAnnualkWhDetails = (AnnualkWhDetails)itr.next();
                objAnnualkWhDetails.setKWhPercentage((objAnnualkWhDetails.getKWh()*100)/totalkWh);
                objResultList.add(objAnnualkWhDetails);
            }
            logger.info("GOT TOTAL kWh BY CUSTOMER ID AND ESIIDs");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET TOTAL kWh BY CUSTOMER ID AND ESIIDs", e);
            e.printStackTrace();
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING GET TOTAL kWh BY CUSTOMER ID AND ESIIDs", e);
           e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objResultList;
    }
    
    public boolean makeValid(int prospectiveCustomerId, String esiIds)
    {
        Session objSession = null;
        String str = "";
        boolean updateResult = false;
        try
        {
            logger.info("MAKING VALID BY CUSTOMER ID AND ESIID");
            objSession = HibernateUtil.getSession();
            StringTokenizer st = new StringTokenizer(esiIds.trim(),",");
            while(st.hasMoreTokens())
            {
                if(str.length()<=0)
                    str = "'"+st.nextToken()+"'";
                else
                    str = str + ",'"+st.nextToken()+"'";
            }
            objSession.beginTransaction();
            if(str.length()>0)
            {
                objSession.createQuery("update PICVO as picvo set picvo.valid = ? where picvo.customer.prospectiveCustomerId = ? and picvo.esiId in ("+str+")").setBoolean(0,true).setInteger(1,prospectiveCustomerId).executeUpdate();
                objSession.createQuery("update PICVO as picvo set picvo.valid = ? where picvo.customer.prospectiveCustomerId = ? and picvo.esiId not in ("+str+")").setBoolean(0,false).setInteger(1,prospectiveCustomerId).executeUpdate();
            }
            else
            {
                objSession.createQuery("update PICVO as picvo set picvo.valid = ? where picvo.customer.prospectiveCustomerId = ?").setBoolean(0, false).setInteger(1,prospectiveCustomerId).executeUpdate();
            }
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("VALID IS UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE VALID COLUMN", e);
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
    
    public List getAllValidESIID(int prospectiveCustomerId, Session objSession)
    {
        List objList = null;
        try
        {
            logger.info("GETTING ALL VALID ESIIDs");
            Criteria objCriteria = null;
            objCriteria = objSession.createCriteria(PICVO.class).add(Restrictions.eq("customer.prospectiveCustomerId", new Integer(prospectiveCustomerId))).add(Restrictions.eq("valid",new Boolean(true)));
            objList = objCriteria.list();
            logger.info("GOT ALL VALID ESIIDs");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL VALID ESIIDs", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        return objList;
    }
    
    public List getAllValidESIID(int prospectiveCustomerId)
    {
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL VALID ESIIDs BY CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            objList = getAllValidESIID(prospectiveCustomerId, objSession);
            logger.info("GOT ALL VALID ESIIDs BY CUSTOMER ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL VALID ESIID BY CUSTOMER ID", e);
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
    
    public List getAllInValidESIID(int prospectiveCustomerId)
    {
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL INVALID ESIIDs BY CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            Criteria objCriteria = null;
            objCriteria = objSession.createCriteria(PICVO.class).add(Restrictions.eq("customer.prospectiveCustomerId", new Integer(prospectiveCustomerId))).add(Restrictions.eq("valid",new Boolean(false)));
            objList = objCriteria.list();
            logger.info("GOT ALL INVALID ESIIDs BY CUSTOMER ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL INVALID ESIIDs BY CUSTOMER ID", e);
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

    public boolean deleteESIID(int prospectiveCustomerId, String esiIds, int totESIIDs)
    {
        Session objSession = null;
        PICVO objPICVO = null;
        Query objQuery = null;
        Query objImpPicQuery = null;
        boolean delResult = false;
        int noOfRowsAffected = 0;
        try
        {
            logger.info("DELETE ESIDD BY CUSTOMER ID AND ESIID");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            if(esiIds.equalsIgnoreCase("all") || totESIIDs==1)
            {
                objImpPicQuery = objSession.createQuery("update ProspectiveCustomerVO as prospectVO set prospectVO.importedPICOn = ? where prospectVO.prospectiveCustomerId = ? ");
                objImpPicQuery.setDate(0,null);
                objImpPicQuery.setInteger(1,prospectiveCustomerId);
                objImpPicQuery.executeUpdate();
            }
            if(esiIds.equalsIgnoreCase("all"))
            {
                objQuery = objSession.createQuery("select picvo.picReferenceId from PICVO as picvo where picvo.customer.prospectiveCustomerId = ?");
                objQuery.setInteger(0, prospectiveCustomerId);
            }
            else
            {
                objQuery = objSession.createQuery("select picvo.picReferenceId from PICVO as picvo where picvo.customer.prospectiveCustomerId = ? and picvo.esiId = ?");
                objQuery.setInteger(0, prospectiveCustomerId);
                objQuery.setString(1, esiIds);
            }
            Iterator itr = objQuery.iterate();
            
            while(itr.hasNext())
            {
                String picRefId = String.valueOf(itr.next());
                PICUsageVO objPICUsageVO = null;
                
                noOfRowsAffected = objSession.createQuery("delete from PICUsageVO as picUsageVo where picUsageVo.picRef.picReferenceId = ?").setInteger(0, Integer.parseInt(picRefId)).executeUpdate();
                logger.info("No Of Rows Affected in PICUsageVO "+noOfRowsAffected);
                
                noOfRowsAffected = objSession.createQuery("delete from PICVO as picVo where picVo.picReferenceId = ?").setInteger(0, Integer.parseInt(picRefId)).executeUpdate();
                logger.info("No Of Rows Affecteds in PICVO "+noOfRowsAffected);
            }
            objSession.getTransaction().commit();
            delResult = true;
            logger.info("ESIID(S) DELETED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DELETE ESIID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return delResult;
    }
    
    public static void main(String[] args)
    {
        //10443720006302563,10443720006415675,1008901001183669397100,1008901011113303566100
        /*Iterator itr = new PICDAO().getTotalkWh(2,"10443720006302563,10443720006415675,1008901001183669397100,1008901011113303566100").iterator();
        while(itr.hasNext())
        {
            AnnualkWhDetails objAnnualkWhDetails = (AnnualkWhDetails)itr.next();
            System.out.println("ESIID:"+objAnnualkWhDetails.getEsiId());
            System.out.println("kWh:"+objAnnualkWhDetails.getKWh());
            System.out.println("kWh Percentage:"+objAnnualkWhDetails.getKWhPercentage());
        }*/
        //if(BuildConfig.DMODE)
            //System.out.println(new PICDAO().makeValid(2,""));
        System.out.println("\nFinal Result :"+new PICDAO().deleteESIID(566,"10032789430652480",1));
    }
}

/*
*$Log: PICDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.20  2007/09/19 06:56:36  spandiyarajan
*delete esiid bug fixed - update the importedon date code added.
*
*Revision 1.19  2007/09/17 08:33:41  spandiyarajan
*removed system.out.println
*
*Revision 1.18  2007/09/17 08:21:51  spandiyarajan
*ESIID deletion functionality added.
*
*Revision 1.17  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.16  2007/08/23 07:27:32  jnadesan
*method added to get load profile
*
*Revision 1.15  2007/07/31 12:28:42  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.14  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.13  2007/04/25 09:51:13  kduraisamy
*error make valid() rectified.
*
*Revision 1.12  2007/04/24 03:53:32  kduraisamy
*only valid esiIds taken for run.
*
*Revision 1.11  2007/04/23 12:07:55  kduraisamy
*getAllValid() and invalid() methods added.
*
*Revision 1.10  2007/04/23 11:36:39  jnadesan
*esiid selection completed
*
*Revision 1.9  2007/04/23 07:04:41  kduraisamy
*getAllValid() and invalid() methods added.
*
*Revision 1.8  2007/04/23 05:32:44  kduraisamy
*esiId Preference added.
*
*Revision 1.7  2007/04/20 13:26:32  kduraisamy
*bug rectified.
*
*Revision 1.6  2007/04/20 06:51:02  kduraisamy
*getTotkWh() added.
*
*Revision 1.5  2007/04/20 06:42:05  kduraisamy
*getTotkWh() added.
*
*Revision 1.4  2007/04/18 05:37:02  kduraisamy
*getNoofEsiid() added.
*
*Revision 1.3  2007/04/17 04:49:39  kduraisamy
*rateCodes set removed from TDSP.
*
*Revision 1.2  2007/04/12 13:57:51  kduraisamy
*unwanted println commented.
*
*Revision 1.1  2007/04/10 06:34:33  kduraisamy
*iniitial commit.
*
*
*/