/*
 * Created on May 2, 2007
 *
 * ClassName	:  	ContractsTrackingDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.ContractsTrackingVO;
import com.savant.pricing.valueobjects.ContractsVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractsTrackingDAO
{
    private static Logger logger = Logger.getLogger(ContractsTrackingDAO.class);
    
    public String addContract(ContractsTrackingVO objContractsTrackingVO)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Session objSession = null;
        ContractsVO objContractsVO = null;
        ContractsTrackingVO objContractsTrackingVONew = new ContractsTrackingVO();
        int contractId = 0;
        int reportId = 0;
        try
        {
            logger.info("ADDING CONTRACT DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            contractId = objContractsTrackingVO.getContractRef().getContractIdentifier();
            reportId = objContractsTrackingVO.getReportIdentifier().getReportIdentifier();
            objContractsVO = (ContractsVO)objSession.get(ContractsVO.class, new Integer(contractId));
            Iterator itr = objSession.createQuery("From ContractsTrackingVO as contractTracking where contractTracking.contractRef.contractIdentifier = ? and contractTracking.reportIdentifier.reportIdentifier = ?").setInteger(0,contractId).setInteger(1,reportId).iterate();
            if(itr.hasNext())
            {
                objContractsTrackingVONew = (ContractsTrackingVO)itr.next();
                System.out.println("already avaliable");
            }
            else
            {
                objContractsTrackingVONew.setContractTrackingIdentifier(this.getContractId(objContractsVO.getPriceRunCustomerRef().getPriceRunRef().getPriceRunTime(),objContractsVO.getPriceRunCustomerRef().getProspectiveCustomer().getCustomerId().intValue()));
            }
            objContractsTrackingVONew.setContractRef(objContractsTrackingVO.getContractRef());
            objContractsTrackingVONew.setCustomerStatus(objContractsTrackingVO.getCustomerStatus());
            objContractsTrackingVONew.setProposalDate(objContractsTrackingVO.getProposalDate());
            objContractsTrackingVONew.setExpiryDate(objContractsTrackingVO.getExpiryDate());
            objContractsTrackingVONew.setContractStartDate(objContractsTrackingVO.getContractStartDate());
            objContractsTrackingVONew.setContractEndDate(objContractsTrackingVO.getContractEndDate());
            objContractsTrackingVONew.setReportIdentifier(objContractsTrackingVO.getReportIdentifier());
            objContractsTrackingVONew.setReportCode(objContractsTrackingVO.getReportCode());
            objSession.saveOrUpdate(objContractsTrackingVONew);
            objSession.getTransaction().commit();
            logger.info("CONTRACT DEATILS IS ADDED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD THE CONTRACT DETAILS", e);
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
        return objContractsTrackingVONew.getContractTrackingIdentifier();
    }
    
    public boolean updateContract(ContractsTrackingVO objContractsTrackingVO)
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
            logger.info("UPDATING CONTRACT DETAILS");
            System.out.println("******** update cont " +objContractsTrackingVO.getCustomerStatus().getCustomerStatusId());
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update(objContractsTrackingVO);
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("CONTRACT DETAILS ARE UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE CONTRACT DETAILS", e);
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
    
    private String getContractId(Date priceRunTime, int cmsId)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Session objSession = null;
        String count = "";
        String contractRef = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd-HHmmss");
        if(cmsId<10)
        {
            contractRef = "0"+cmsId+"-"+sdf.format(priceRunTime);
        }
        else
            contractRef = cmsId+"-"+sdf.format(priceRunTime);
        int cnt = 0;
        try
        {
            logger.info("GETTING CONTRACT ID");
            objSession = HibernateUtil.getSession();
            cnt = objSession.createCriteria(ContractsTrackingVO.class).add(Restrictions.like("contractTrackingIdentifier", contractRef ,MatchMode.START)).list().size();
            if(cnt<9)
            {
                count = "0"+String.valueOf(cnt+1);
            }
            else
            {
                count = String.valueOf(cnt+1);
            }
            logger.info("GOT CONTRACT ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET CONTRACT ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return contractRef+"-"+count;
    }
    public List getAllContracts()
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL CONTRACTS");
            objSession = HibernateUtil.getSession();
            objList = objSession.createCriteria(ContractsTrackingVO.class).list();
            logger.info("GOT ALL CONTRACTS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CONTRACTS", e);
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
    public LinkedHashMap getAllContractsByManagerandRep(Filter filterCust,String strCMSId, Filter filterContractId,String salesManager, int contractStatusId, String sortBy, boolean ascending, int startIndex, int displayCount)
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
        LinkedHashMap hmResult = new LinkedHashMap();
        List objList = null;
        int CMSId;
        try
        {
            logger.info("CONVERTING CMSID TO INTEGER");
            CMSId = Integer.parseInt(strCMSId);
            logger.info("CMSID IS CONVERTED TO INTEGER");
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING CONVERT THE STRING CMSID TO INTEGER", e);
            CMSId = 0;
        }
        try
        {
            logger.info("GETTING ALL CONTRATS BY MANAGER AND REP");
            Criteria objCriteriaProspective = null;
            Criteria objCriteriaRep = null;
            
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ContractsTrackingVO.class);
            objCriteriaProspective = objCriteria.createCriteria("contractRef").createCriteria("priceRunCustomerRef").createCriteria("prospectiveCustomer");
            objCriteriaRep = objCriteriaProspective.createCriteria("salesRep");
            objCriteriaRep.add(Restrictions.or(Restrictions.like("userId",salesManager),(Restrictions.like("parentUser.userId",salesManager))));
            
            if(filterCust != null)
            {
                objCriteriaProspective.add(Restrictions.like("customerName",filterCust.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filterCust.getSpecialFunction())));
            }
            if(filterContractId != null)
            {
                objCriteria.add(Restrictions.like("contractTrackingIdentifier",filterContractId.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filterContractId.getSpecialFunction())));
            }
            if(CMSId !=0)
            {
                objCriteriaProspective.add(Restrictions.like("customerId",new Integer(CMSId)));
            }
            if(contractStatusId != 0)
            {
                objCriteria.createCriteria("customerStatus").add(Restrictions.eq("customerStatusId",new Integer(contractStatusId)));
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
            logger.info("GOT ALL CONTRATS BY MANAGER AND REP");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CONTRACTS BY MANAGER AND REP", e);
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

    public LinkedHashMap getAllContracts(Filter filterCust,String strCMSId, Filter filterContractId, int contractStatusId, String sortBy, boolean ascending, int startIndex, int displayCount)
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
        LinkedHashMap hmResult = new LinkedHashMap();
        List objList = null;
        int CMSId = 0;
        try
        {
            if( !strCMSId.equalsIgnoreCase("") && strCMSId.trim().length()>0)
                CMSId = Integer.parseInt(strCMSId);
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING CONVERT THE STRING TO INTEGER", e);
            CMSId = 0;
        }
        try
        {
            logger.info("GETTING ALL CONTRACTS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ContractsTrackingVO.class);
            Criteria objCriteriaProspective = objCriteria.createCriteria("contractRef").createCriteria("priceRunCustomerRef").createCriteria("prospectiveCustomer");
            if(filterCust != null)
            {
                objCriteriaProspective.add(Restrictions.like("customerName",filterCust.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filterCust.getSpecialFunction())));
            }
            if(CMSId !=0)
            {
                objCriteriaProspective.add(Restrictions.like("customerId",new Integer(CMSId)));
            }
            if(filterContractId != null)
            {
                objCriteria.add(Restrictions.like("contractTrackingIdentifier",filterContractId.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filterContractId.getSpecialFunction())));
            }
            if(contractStatusId != 0)
            {
                objCriteria.createCriteria("customerStatus").add(Restrictions.eq("customerStatusId",new Integer(contractStatusId)));
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
            logger.info("GOT ALL CONTRACTS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CONTRACTS", e);
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
    
    public ContractsTrackingVO getContracts(String contractTrackingIdentifier)
    {
        
        ContractsTrackingVO objContractsTrackingVO = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL CONTRACTS BY CONTRACT TRACKING ID");
            objSession = HibernateUtil.getSession();
            objContractsTrackingVO = (ContractsTrackingVO)objSession.get(ContractsTrackingVO.class,contractTrackingIdentifier);
            logger.info("GOT ALL CONTRACTS BY CONTRACT TRACKING ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CONTRACTS BY CONTRACT TRACKING ID", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL CONTRACTS BY CONTRACT TRACKING ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objContractsTrackingVO;
    }
    
   public static void main(String[] args)
   {
       if(BuildConfig.DMODE)
           System.out.println("ContractId:"+new ContractsTrackingDAO().getContractId(new Date(),10));
   } 
}

/*
*$Log: ContractsTrackingDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.4  2007/11/27 09:07:32  jnadesan
*report id checked
*
*Revision 1.3  2007/11/26 14:22:16  tannamalai
**** empty log message ***
*
*Revision 1.2  2007/11/23 11:44:43  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.20  2007/09/10 11:25:57  jnadesan
*Numberformat exception problem solved
*
*Revision 1.19  2007/09/04 05:23:55  spandiyarajan
*removed unwanted imports
*
*Revision 1.18  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.17  2007/07/31 12:28:42  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.16  2007/07/30 12:42:10  jnadesan
*CMS ID filter added
*
*Revision 1.15  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.14  2007/06/02 04:47:55  spandiyarajan
*removed unewanted imports
*
*Revision 1.13  2007/05/28 10:46:07  jnadesan
*instead contract id contracttracking id is given as parameter
*
*Revision 1.12  2007/05/17 09:23:22  jnadesan
*users are shown by manager and rep wise
*
*Revision 1.11  2007/05/07 07:29:57  spandiyarajan
*cmsid type chageed int type to integer
*
*Revision 1.10  2007/05/07 04:39:11  kduraisamy
*imports organized.
*
*Revision 1.9  2007/05/04 07:33:35  kduraisamy
*contract Id format changed.
*
*Revision 1.8  2007/05/03 12:44:24  spandiyarajan
*contractstraciking update completed
*
*Revision 1.6  2007/05/03 10:53:12  kduraisamy
*update method for contractsTrackingvo added.
*
*Revision 1.7  2007/05/03 11:32:49  kduraisamy
*contract Id returned in add Method.
*
*Revision 1.6  2007/05/03 10:53:12  kduraisamy
*update method for contractsTrackingvo added.
*
*Revision 1.5  2007/05/03 07:47:39  spandiyarajan
*search filter functionality finished
*
*Revision 1.4  2007/05/03 06:02:09  kduraisamy
*getAllContractsFilter() added.
*
*Revision 1.3  2007/05/02 11:38:07  jnadesan
*import organized
*
*Revision 1.2  2007/05/02 10:55:41  kduraisamy
*contractsTrackingVO added.
*
*Revision 1.1  2007/05/02 10:50:05  kduraisamy
*contractsTrackingVO added.
*
*
*/