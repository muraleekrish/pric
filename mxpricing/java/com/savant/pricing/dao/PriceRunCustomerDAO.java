/*
 * Created on Apr 2, 2007
 *
 * ClassName	:  	PriceRunCustomerDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO;
import com.savant.pricing.calculation.valueobjects.PriceRunHeaderVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PriceRunCustomerDAO
{
    private static Logger logger = Logger.getLogger(PriceRunCustomerDAO.class);
    
    public PriceRunCustomerDAO()
    {
    }
    
    public PriceRunCustomerVO getPriceRunCustomer(int priceRunCustomerRefId)
    {
        PriceRunCustomerVO objPriceRunCustomerVO  = null; 
        Session objSession = null;
        try
        {
            logger.info("GETTING PRICE RUN CUSTOMER BY PRICE RUN CUSTOMER REF ID");
            objSession = HibernateUtil.getSession();
            objPriceRunCustomerVO = (PriceRunCustomerVO)objSession.get(PriceRunCustomerVO.class, new Integer(priceRunCustomerRefId));
            logger.info("GOT PRICE RUN CUSTOMER BY PRICE RUN CUSTOMER REF ID");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCPTION DURING GET PRICE RUN CUSTOMER BY PRICE RUN CUSTOMER REF ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objPriceRunCustomerVO;
    }
    
    public int getPriceRunCustomerCount(String priceRunRef)
    {
        Session objSession = null;
        int count = 0;
        try
        {
            logger.info("GETTING PRICE RUN CUSTOMER COUNT BY PRICE RUN REF");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select count(priceRunCustomer.priceRunCustomerRefId) from PriceRunCustomerVO as priceRunCustomer where priceRunCustomer.priceRunRef.priceRunRefNo = ?");
            objQuery.setString(0, priceRunRef);
            Iterator itr = objQuery.iterate();
            if(itr.hasNext())
            {
                count = ((Long)itr.next()).intValue();
                logger.info("GOT PRICE RUN CUSTOMER COUNT BY PRICE RUN REF");
            }
            else
            {
                logger.info("NO RECORD FOR PRICE RUN REF");
            }
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET THE PRICE RUN CUSTOMER COUNT", e);
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
    
    public boolean deleteRunEntry(PriceRunHeaderVO objPriceRunHeaderVO)
    {
        Session objSession = null;
        boolean updateResult = false;
        try
        {
            logger.info("DELETING RUN ENTRY DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.delete(objPriceRunHeaderVO);
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("RUN ENTRY DETAILS ARE DELETED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DELETE THE RUN ENTRY DETAILS", e);
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
    
    public LinkedHashMap getAllCPE(String salesManager,boolean isAnalyst, Date runStartDate, Date runEndDate, Filter filterCust, Filter filterSalesPerson, int priceRunCustId, int cmsId, boolean ascending, String sortBy,int startIndex,int maxCount)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        LinkedHashMap hmResult = new LinkedHashMap();
        try
        {
            logger.info("GETTING ALL CPE");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(PriceRunCustomerVO.class);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(runEndDate);
            gc.add(Calendar.DATE, 1);
            Criteria objCriteriaPriceRunRef = null;
            Criteria objCriteriaPriceRunHeader = null;
            Criteria objCriteriaProspective = null;
            Criteria objCriteriaRep = null;
         //   objCriteriaPriceRunRef = objCriteria.createCriteria("priceRunCustRefId");
            objCriteriaPriceRunHeader = objCriteria.createCriteria("priceRunRef");
            objCriteriaProspective = objCriteria.createCriteria("prospectiveCustomer");
            if(priceRunCustId != 0)
            {
                objCriteria.add(Restrictions.eq("priceRunCustomerRefId", new Integer(priceRunCustId)));
            }
            if(!isAnalyst)
            {
                objCriteriaRep = objCriteriaProspective.createCriteria("salesRep");
                objCriteriaRep.add(Restrictions.or(Restrictions.like("userId", salesManager), (Restrictions.like("parentUser.userId", salesManager))));
            }
            if(runStartDate != null && runEndDate != null)
            {
                objCriteriaPriceRunHeader.add(Restrictions.ge("priceRunTime", runStartDate)).add(Restrictions.lt("priceRunTime", gc.getTime()));
            } 
            if(filterCust != null)
            {
                objCriteriaProspective.add(Restrictions.like("customerName", filterCust.getFieldValue(), (MatchMode) HibernateUtil.htMatchCase.get(filterCust.getSpecialFunction())));
            }
            if(filterSalesPerson != null)
            {
                if(isAnalyst)
                {
                    objCriteriaRep = objCriteriaProspective.createCriteria("salesRep");
                }
                Criterion objFirstName = Restrictions.like("firstName", filterSalesPerson.getFieldValue(), (MatchMode) HibernateUtil.htMatchCase.get(filterSalesPerson.getSpecialFunction()));
                Criterion objLastName = Restrictions.like("lastName", filterSalesPerson.getFieldValue(), (MatchMode) HibernateUtil.htMatchCase.get(filterSalesPerson.getSpecialFunction()));
                objCriteriaRep.add(Restrictions.or(objFirstName, objLastName));
            }
            if(cmsId != 0)
            {
                objCriteriaProspective.add(Restrictions.eq("customerId", new Integer(cmsId)));
            } 
            if(ascending)
                objCriteriaProspective.addOrder(Order.asc(sortBy));
            else
                objCriteriaProspective.addOrder(Order.desc(sortBy));
            objCriteriaPriceRunHeader.addOrder(Order.desc("priceRunTime"));
            /*Criteria objCriteriaContract = objCriteria.createCriteria("setContracts");
            objCriteriaContract.addOrder(Order.asc("term"));*/
            objCriteria.setFirstResult(startIndex);
            objCriteria.setMaxResults(maxCount);
            List objList = objCriteria.list();
            int totalCount = this.getAllCPETotalCount(salesManager,isAnalyst,runStartDate,runEndDate,filterCust,filterSalesPerson,priceRunCustId,cmsId);
            hmResult.put("TotalRecordCount",new Integer(totalCount));
            hmResult.put("Records",objList);
            logger.info("GOT ALL CPE");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CPE", e);
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
    
    public int getAllCPETotalCount(String salesManager, boolean isAnalyst, Date runStartDate, Date runEndDate, Filter filterCust, Filter filterSalesPerson, int priceRunCustId, int cmsId)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        int totalCount = 0;
        try
        {
            logger.info("GETTING ALL CPE");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(PriceRunCustomerVO.class);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(runEndDate);
            gc.add(Calendar.DATE, 1);
            Criteria objCriteriaPriceRunRef = null;
            Criteria objCriteriaPriceRunHeader = null;
            Criteria objCriteriaProspective = null;
            Criteria objCriteriaRep = null;
          //  objCriteriaPriceRunRef = objCriteria.createCriteria("priceRunCustRefId");
            objCriteriaPriceRunHeader = objCriteria.createCriteria("priceRunRef");
            objCriteriaProspective = objCriteria.createCriteria("prospectiveCustomer");
            
            if(priceRunCustId != 0)
            {
                objCriteria.add(Restrictions.eq("priceRunCustomerRefId", new Integer(priceRunCustId)));
            }
            if(!isAnalyst)
            {
                objCriteriaRep = objCriteriaProspective.createCriteria("salesRep");
                objCriteriaRep.add(Restrictions.or(Restrictions.like("userId", salesManager), (Restrictions.like("parentUser.userId", salesManager))));
            }
            if(runStartDate != null && runEndDate != null)
            {
                objCriteriaPriceRunHeader.add(Restrictions.ge("priceRunTime", runStartDate)).add(Restrictions.lt("priceRunTime", gc.getTime()));
            } 
            if(filterCust != null)
            {
                objCriteriaProspective.add(Restrictions.like("customerName", filterCust.getFieldValue(), (MatchMode) HibernateUtil.htMatchCase.get(filterCust.getSpecialFunction())));
            }
          
            if(filterSalesPerson != null)
            {
                if(isAnalyst)
                {
                    objCriteriaRep = objCriteriaProspective.createCriteria("salesRep");
                }
                Criterion objFirstName = Restrictions.like("firstName", filterSalesPerson.getFieldValue(), (MatchMode) HibernateUtil.htMatchCase.get(filterSalesPerson.getSpecialFunction()));
                Criterion objLastName = Restrictions.like("lastName", filterSalesPerson.getFieldValue(), (MatchMode) HibernateUtil.htMatchCase.get(filterSalesPerson.getSpecialFunction()));
                objCriteriaRep.add(Restrictions.or(objFirstName, objLastName));
            }
            if(cmsId != 0)
            {
                objCriteriaProspective.add(Restrictions.eq("customerId", new Integer(cmsId)));
            } 
            objCriteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
            List results = objCriteria.list();
            if(results != null && results.size() > 0)
            {
                totalCount = ((Integer) results.get(0)).intValue();
            }
            logger.info("GOT ALL CPE");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CPE", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return totalCount;
        
    }
    
    public static void main(String[] args)
    {
        if(BuildConfig.DMODE)
            System.out.println(new PriceRunCustomerDAO().getPriceRunCustomer(4).getProspectiveCustomer().getCustomerName());
        PriceRunHeaderVO objPriceRunHeaderVO = new PriceRunHeaderVO();
        objPriceRunHeaderVO.setPriceRunRefNo("08-24-2007 12:48:45");
        new PriceRunCustomerDAO().deleteRunEntry(objPriceRunHeaderVO);
        
    }
}

/*
*$Log: PriceRunCustomerDAO.java,v $
*Revision 1.2  2008/02/14 05:43:56  tannamalai
*pagination done for price quote page
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
*Revision 1.6  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.5  2007/08/27 04:40:18  jnadesan
*delete entry in MMPriceRunheader
*
*Revision 1.4  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.3  2007/05/23 11:57:05  kduraisamy
*priceRunResult delete method added.
*
*Revision 1.2  2007/04/10 07:36:23  kduraisamy
*imports organized.
*
*Revision 1.1  2007/04/02 15:59:16  kduraisamy
*initial commit.
*
*
*/