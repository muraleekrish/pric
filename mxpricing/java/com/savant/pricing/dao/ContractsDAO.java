/*
 * Created on Mar 28, 2007
 *
 * ClassName	:  	ContractsDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.PricingException;
import com.savant.pricing.pdf.PriceComparisionDetails;
import com.savant.pricing.transferobjects.PriceRunResultDetails;
import com.savant.pricing.valueobjects.ContractsVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractsDAO
{
    private static Logger logger = Logger.getLogger(ContractsDAO.class);
    
    public ContractsDAO()
    {
    }

    public boolean addContract(ContractsVO objContractsVO)
    {
        Session objSession = null;
        boolean addResult = false;
        try
        {
            logger.info("ADDING CONTRACT DETAILS");
            System.out.println("ADDING CONTRACT DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            Query objQuery = objSession.createQuery("select contracts.contractIdentifier from ContractsVO as contracts where contracts.priceRunCustomerRef.priceRunCustomerRefId = ? and contracts.term = ? and contracts.product.productIdentifier = ?");
            objQuery.setInteger(0, objContractsVO.getPriceRunCustomerRef().getPriceRunCustomerRefId());
            objQuery.setInteger(1, objContractsVO.getTerm());
            objQuery.setInteger(2, objContractsVO.getProduct().getProductIdentifier());
            Iterator itr = objQuery.iterate();
            if(itr.hasNext())
            {
                objContractsVO.setContractIdentifier(((Integer) itr.next()).intValue());
                if(BuildConfig.DMODE)
                    System.out.println("contractId:" + objContractsVO.getContractIdentifier());
            }
            System.out.println("*************************");
            System.out.println("AggregatorFe :"+objContractsVO.getAggregatorFee());
            System.out.println("AnnualkW :"+objContractsVO.getAnnualkW());
            System.out.println("AnnualkWh :"+objContractsVO.getAnnualkWh());
            System.out.println("AvgGasPrice :"+objContractsVO.getAvgGasPrice());
            System.out.println("BaseRate$PerMWh :"+objContractsVO.getBaseRate$PerMWh());
            System.out.println("CityTax :"+objContractsVO.getCityTax());
            System.out.println("CompetitorPrice$PerkWh :"+objContractsVO.getCompetitorPrice$PerkWh());
            System.out.println("ComputedFAF :"+objContractsVO.getComputedFAF());
            System.out.println("ContractIdentifier :"+objContractsVO.getContractIdentifier());
            System.out.println("ContractkWh :"+objContractsVO.getContractkWh());
            System.out.println("ContractPrice$PerkWh :"+objContractsVO.getContractPrice$PerkWh());
            System.out.println("CountyTax :"+objContractsVO.getCountyTax());
            System.out.println("CustomerCharge :"+objContractsVO.getCustomerCharge());
            System.out.println("EsiIds :"+objContractsVO.getEsiIds());
            System.out.println("FixedPrice$PerMWh :"+objContractsVO.getFixedPrice$PerMWh());
            System.out.println("FuelFactor :"+objContractsVO.getFuelFactor());
            System.out.println("GasPrice$PerMMBtu :"+objContractsVO.getGasPrice$PerMMBtu());
            System.out.println("HeatRate :"+objContractsVO.getHeatRate());
            System.out.println("HeatRateAdder :"+objContractsVO.getHeatRateAdder());
            System.out.println("LoadFactor :"+objContractsVO.getLoadFactor());
            System.out.println("McpeAdder :"+objContractsVO.getMcpeAdder());
            System.out.println("SalesCommision :"+objContractsVO.getSalesCommision());
            System.out.println("StateTax :"+objContractsVO.getStateTax());
            System.out.println("Taxes :"+objContractsVO.getTaxes());
            System.out.println("TdspCharges :"+objContractsVO.getTdspCharges());
            System.out.println("Term :"+objContractsVO.getTerm());
            System.out.println("TotalAnnualBill :"+objContractsVO.getTotalAnnualBill());
            System.out.println("tClass :"+objContractsVO.getClass());
            System.out.println("ExpDate :"+objContractsVO.getExpDate());
            System.out.println("PriceRunCustomerRef() :"+objContractsVO.getPriceRunCustomerRef());
            if(objContractsVO.getPriceRunCustomerRef()!=null)
                System.out.println("PriceRunCustomerRef().getPriceRunCustomerRefId() :"+objContractsVO.getPriceRunCustomerRef().getPriceRunCustomerRefId());
            System.out.println("Product :"+objContractsVO.getProduct());
            System.out.println("SalesRep :"+objContractsVO.getSalesRep());
            System.out.println("StartDate :"+objContractsVO.getStartDate());
            System.out.println("*************************");
            objSession.saveOrUpdate(objContractsVO);
            objSession.getTransaction().commit();
            addResult = true;
            System.out.println("COTRACT DETAILS IS ADDED");
            logger.info("COTRACT DETAILS IS ADDED");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD THE CONTRACT DETAILS", e);
            System.out.println("*************************");
            System.out.println("AggregatorFe :"+objContractsVO.getAggregatorFee());
            System.out.println("AnnualkW :"+objContractsVO.getAnnualkW());
            System.out.println("AnnualkWh :"+objContractsVO.getAnnualkWh());
            System.out.println("AvgGasPrice :"+objContractsVO.getAvgGasPrice());
            System.out.println("BaseRate$PerMWh :"+objContractsVO.getBaseRate$PerMWh());
            System.out.println("CityTax :"+objContractsVO.getCityTax());
            System.out.println("CompetitorPrice$PerkWh :"+objContractsVO.getCompetitorPrice$PerkWh());
            System.out.println("ComputedFAF :"+objContractsVO.getComputedFAF());
            System.out.println("ContractIdentifier :"+objContractsVO.getContractIdentifier());
            System.out.println("ContractkWh :"+objContractsVO.getContractkWh());
            System.out.println("ContractPrice$PerkWh :"+objContractsVO.getContractPrice$PerkWh());
            System.out.println("CountyTax :"+objContractsVO.getCountyTax());
            System.out.println("CustomerCharge :"+objContractsVO.getCustomerCharge());
            System.out.println("EsiIds :"+objContractsVO.getEsiIds());
            System.out.println("FixedPrice$PerMWh :"+objContractsVO.getFixedPrice$PerMWh());
            System.out.println("FuelFactor :"+objContractsVO.getFuelFactor());
            System.out.println("GasPrice$PerMMBtu :"+objContractsVO.getGasPrice$PerMMBtu());
            System.out.println("HeatRate :"+objContractsVO.getHeatRate());
            System.out.println("HeatRateAdder :"+objContractsVO.getHeatRateAdder());
            System.out.println("LoadFactor :"+objContractsVO.getLoadFactor());
            System.out.println("McpeAdder :"+objContractsVO.getMcpeAdder());
            System.out.println("SalesCommision :"+objContractsVO.getSalesCommision());
            System.out.println("StateTax :"+objContractsVO.getStateTax());
            System.out.println("Taxes :"+objContractsVO.getTaxes());
            System.out.println("TdspCharges :"+objContractsVO.getTdspCharges());
            System.out.println("Term :"+objContractsVO.getTerm());
            System.out.println("TotalAnnualBill :"+objContractsVO.getTotalAnnualBill());
            System.out.println("tClass :"+objContractsVO.getClass());
            System.out.println("ExpDate :"+objContractsVO.getExpDate());
            System.out.println("PriceRunCustomerRef() :"+objContractsVO.getPriceRunCustomerRef());
            if(objContractsVO.getPriceRunCustomerRef()!=null)
                System.out.println("PriceRunCustomerRef().getPriceRunCustomerRefId() :"+objContractsVO.getPriceRunCustomerRef().getPriceRunCustomerRefId());
            System.out.println("Product :"+objContractsVO.getProduct());
            System.out.println("SalesRep :"+objContractsVO.getSalesRep());
            System.out.println("StartDate :"+objContractsVO.getStartDate());
            System.out.println("*************************");
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
        return addResult;
    }

    public List getCPEbyCustandProduct(int priceRunCustId, int productId)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria = null;
        Session objSession = null;
        List lstContracts = null;
        try
        {
            logger.info("GETTING CPE BY CUSTOMER ID AND PRODUCT ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ContractsVO.class).add(Restrictions.eq("priceRunCustomerRef.priceRunCustomerRefId", new Integer(priceRunCustId))).add(Restrictions.eq("product.productIdentifier", new Integer(productId)));
            lstContracts = objCriteria.list();
            logger.info("GOT CPE BY CUSTOMER ID AND PRODUCT ID");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET CPE", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return lstContracts;
    }

    public List getCPEbyRunIDWise(int priceRunCustId)
    {
        /**
         * Requires - PriceRun ID.
         * Modifies - nothing.
         * Effects -  returns list of CPE in different product for same run id.
         * @throws -
         */
        Criteria objCriteria = null;
        Session objSession = null;
        List lstContracts = null;
        try
        {
            logger.info("GETTING CPE BY RUN ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ContractsVO.class).add(Restrictions.eq("priceRunCustomerRef.priceRunCustomerRefId", new Integer(priceRunCustId)));
            lstContracts = objCriteria.list();
            logger.info("GOT CPE BY RUN ID");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET CPE", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return lstContracts;
    }

    public boolean updateContractsExpAndStartsDates(int priceRunCustId, Date startDate, Date expDate)
    {
        logger.info("UPDATING CONTRACTS START AND EXPIRY DATES");
        List lstContracts = getCPEbyRunIDWise(priceRunCustId);
        ContractsVO objContractsVO = null;
        Iterator iteContract = lstContracts.iterator();
        while (iteContract.hasNext())
        {
            objContractsVO = (ContractsVO) iteContract.next();
            objContractsVO.setStartDate(startDate);
            objContractsVO.setExpDate(expDate);
            addContract(objContractsVO);
        }
        logger.info("CONTRACTS START AND EXPIRY DATES ARE UPDATED");
        return true;
    }

    public LinkedHashMap getAllContractTypesFromCMS()
    {
        Session objSession = null;
        LinkedHashMap lhm = new LinkedHashMap();
        try
        {
            logger.info("GETTING ALL CONTRACT TYPES FROM CMS");
            objSession = HibernateUtil.getSession();
           /* Query objQuery = objSession.createSQLQuery("SELECT [Contract Type ID], [Contract Type] FROM [CustomerManagementSystem].[dbo].[Contract Type] order by [Contract Type]");
            Iterator itr = objQuery.list().iterator();
            while (itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                lhm.put(innerRow[0], innerRow[1]);
            }*/
            logger.info("GOT ALL CONTRACT TYPES FROM CMS");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CONTRACT TYPES FROM CMS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return lhm;
    }
    
    public LinkedHashMap getAllTermbyCust(int prospectiveCustId)
    {
        Session objSession = null;
        LinkedHashMap lhm = new LinkedHashMap();
        try
        {
            logger.info("GETTING ALL TERM BY CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createSQLQuery("SELECT distinct (Term) FROM [Pricing].[dbo].[PRC_Contracts] where PriceRunCustomer_Ref_ID in (select PriceRunCustomer_Ref_ID from dbo.PRC_Price_Run_Cust where Prospective_Cust_ID = ?)");
            objQuery.setInteger(0,prospectiveCustId);
            Iterator itr = objQuery.list().iterator();
            while (itr.hasNext())
            {
                Object innerRow = itr.next();
                lhm.put(innerRow,innerRow);
            }
            logger.info("GOT ALL TERM BY CUSTOMER ID");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL TERM BY CUSTOMER ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return lhm;
    }

    public LinkedHashMap getAllContractStatusFromCMS()
    {
        Session objSession = null;
        LinkedHashMap lhm = new LinkedHashMap();
        try
        {
            logger.info("GETTING ALL CONTRACT STATUS FROM CMS");
            objSession = HibernateUtil.getSession();
           /* Query objQuery = objSession.createSQLQuery("SELECT [Contract Status ID], [Status] FROM [CustomerManagementSystem].[dbo].[Contract Status] order by [Status]");
            Iterator itr = objQuery.list().iterator();
            while (itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                lhm.put(innerRow[0], innerRow[1]);
            }*/
            logger.info("GOT ALL CONTRACT STATUS FROM CMS");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GETT ALL CONTRACT STATUS FROM CMS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return lhm;
    }

    public LinkedHashMap getAllMXEnergyRateClassFromCMS()
    {
        Session objSession = null;
        LinkedHashMap lhm = new LinkedHashMap();
        try
        {
            logger.info("GETTING ALL TRIEAGLE RATE CLASS FROM CMS");
            objSession = HibernateUtil.getSession();
           /* Query objQuery = objSession.createSQLQuery("SELECT [TriEagleRateClassId], [Name] FROM [CustomerManagementSystem].[dbo].[TriEagleRateClass] order by [Name]");
            Iterator itr = objQuery.list().iterator();
            while (itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                lhm.put(innerRow[0], innerRow[1]);
            }*/
            logger.info("GOT ALL TRIEAGLE RATE CLASS FROM CMS");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL TRIEAGLE RATE CLASS FROM CMS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return lhm;
    }

    public LinkedHashMap getAllCPEByManager(String salesManager, Date runStartDate, Date runEndDate, Filter filterCust, Filter filterSalesPerson, int priceRunCustId, int cmsId, boolean ascending, String sortBy)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria = null;
        Session objSession = null;
        LinkedHashMap hmResult = new LinkedHashMap();
        LinkedHashMap hmProductResult = null;
        try
        {
            logger.info("GETTING ALL CPE BY MANAGER");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ContractsVO.class);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(runEndDate);
            gc.add(Calendar.DATE, 1);
            Criteria objCriteriaCustomerRef = null;
            Criteria objCriteriaPriceRunRef = null;
            Criteria objCriteriaProspective = null;
            Criteria objCriteriaRep = null;
            objCriteriaCustomerRef = objCriteria.createCriteria("priceRunCustomerRef");
            objCriteriaPriceRunRef = objCriteriaCustomerRef.createCriteria("priceRunRef");
            objCriteriaProspective = objCriteriaCustomerRef.createCriteria("prospectiveCustomer");
            objCriteriaRep = objCriteriaProspective.createCriteria("salesRep");
            objCriteriaRep.add(Restrictions.or(Restrictions.like("userId", salesManager), (Restrictions.like("parentUser.userId", salesManager))));
            if(runStartDate != null && runEndDate != null)
            {
                objCriteriaPriceRunRef.add(Restrictions.ge("priceRunTime", runStartDate)).add(Restrictions.lt("priceRunTime", gc.getTime()));
            }
            if(filterCust != null)
            {
                objCriteriaProspective.add(Restrictions.like("customerName", filterCust.getFieldValue(), (MatchMode) HibernateUtil.htMatchCase.get(filterCust.getSpecialFunction())));
            }
            if(priceRunCustId != 0)
            {
                objCriteriaCustomerRef.add(Restrictions.eq("priceRunCustomerRefId", new Integer(priceRunCustId)));
            }
            if(filterSalesPerson != null)
            {
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
            objCriteriaPriceRunRef.addOrder(Order.desc("priceRunTime"));
            objCriteria.addOrder(Order.asc("term"));
            
        //    objCriteria.setFirstResult(0);
          //  objCriteria.setMaxResults(10);
            
            List objList = objCriteria.list();
            Iterator itr = objList.iterator();
            while (itr.hasNext())
            {
                ContractsVO objContractsVO = (ContractsVO) itr.next();
                if(hmResult.containsKey(new Integer(objContractsVO.getPriceRunCustomerRef().getPriceRunCustomerRefId())))
                {
                    LinkedHashMap lhmProduct = (LinkedHashMap) hmResult.get(new Integer(objContractsVO.getPriceRunCustomerRef().getPriceRunCustomerRefId()));
                    List objContractList = (List) lhmProduct.get(new Integer(objContractsVO.getProduct().getProductIdentifier()));
                    if(objContractList == null)
                    {
                        List obj = new ArrayList();
                        obj.add(objContractsVO);
                        lhmProduct.put(new Integer(objContractsVO.getProduct().getProductIdentifier()), obj);
                        hmResult.put(new Integer(objContractsVO.getPriceRunCustomerRef().getPriceRunCustomerRefId()), lhmProduct);
                    } else
                    {
                        objContractList.add(objContractsVO);
                    }
                } else
                {
                    List obj = new ArrayList();
                    obj.add(objContractsVO);
                    hmProductResult = new LinkedHashMap();
                    hmProductResult.put(new Integer(objContractsVO.getProduct().getProductIdentifier()), obj);
                    hmResult.put(new Integer(objContractsVO.getPriceRunCustomerRef().getPriceRunCustomerRefId()), hmProductResult);
                }
            }
            logger.info("GOT ALL CPE BY MANAGER");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CPE BY MANAGER", e);
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

    public LinkedHashMap getAllCPE(Date runStartDate, Date runEndDate, Filter filterCust, Filter filterSalesPerson, int priceRunCustId, int cmsId, boolean ascending, String sortBy)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria = null;
        Session objSession = null;
        LinkedHashMap hmResult = new LinkedHashMap();
        LinkedHashMap hmProductResult = null;
        try
        {
            logger.info("GETTING ALL CPE");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ContractsVO.class);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(runEndDate);
            gc.add(Calendar.DATE, 1);
            Criteria objCriteriaCustomerRef = null;
            Criteria objCriteriaPriceRunRef = null;
            Criteria objCriteriaProspective = null;
            objCriteriaCustomerRef = objCriteria.createCriteria("priceRunCustomerRef");
            objCriteriaPriceRunRef = objCriteriaCustomerRef.createCriteria("priceRunRef");
            objCriteriaProspective = objCriteriaCustomerRef.createCriteria("prospectiveCustomer");
            if(runStartDate != null && runEndDate != null)
            {
                objCriteriaPriceRunRef.add(Restrictions.ge("priceRunTime", runStartDate)).add(Restrictions.lt("priceRunTime", gc.getTime()));
            }
            if(filterCust != null)
            {
                objCriteriaProspective.add(Restrictions.like("customerName", filterCust.getFieldValue(), (MatchMode) HibernateUtil.htMatchCase.get(filterCust.getSpecialFunction())));
            }
            if(priceRunCustId != 0)
            {
                objCriteriaCustomerRef.add(Restrictions.eq("priceRunCustomerRefId", new Integer(priceRunCustId)));
            }
            if(filterSalesPerson != null)
            {
                Criteria objSalesRepCriteria = objCriteriaProspective.createCriteria("salesRep");
                Criterion objFirstName = Restrictions.like("firstName", filterSalesPerson.getFieldValue(), (MatchMode) HibernateUtil.htMatchCase.get(filterSalesPerson.getSpecialFunction()));
                Criterion objLastName = Restrictions.like("lastName", filterSalesPerson.getFieldValue(), (MatchMode) HibernateUtil.htMatchCase.get(filterSalesPerson.getSpecialFunction()));
                objSalesRepCriteria.add(Restrictions.or(objFirstName, objLastName));
            }
            if(cmsId != 0)
            {
                objCriteriaProspective.add(Restrictions.eq("customerId", new Integer(cmsId)));
            }
            if(ascending)
                objCriteriaProspective.addOrder(Order.asc(sortBy));
            else
                objCriteriaProspective.addOrder(Order.desc(sortBy));
            objCriteriaPriceRunRef.addOrder(Order.desc("priceRunTime"));
            objCriteria.addOrder(Order.asc("term"));
            List objList = objCriteria.list();
            Iterator itr = objList.iterator();
            while (itr.hasNext())
            {
                ContractsVO objContractsVO = (ContractsVO) itr.next();
                if(hmResult.containsKey(new Integer(objContractsVO.getPriceRunCustomerRef().getPriceRunCustomerRefId())))
                {
                    LinkedHashMap lhmProduct = (LinkedHashMap) hmResult.get(new Integer(objContractsVO.getPriceRunCustomerRef().getPriceRunCustomerRefId()));
                    List objContractList = (List) lhmProduct.get(new Integer(objContractsVO.getProduct().getProductIdentifier()));
                    if(objContractList == null)
                    {
                        List obj = new ArrayList();
                        obj.add(objContractsVO);
                        lhmProduct.put(new Integer(objContractsVO.getProduct().getProductIdentifier()), obj);
                        hmResult.put(new Integer(objContractsVO.getPriceRunCustomerRef().getPriceRunCustomerRefId()), lhmProduct);
                    } else
                    {
                        objContractList.add(objContractsVO);
                    }
                } else
                {
                    List obj = new ArrayList();
                    obj.add(objContractsVO);
                    hmProductResult = new LinkedHashMap();
                    hmProductResult.put(new Integer(objContractsVO.getProduct().getProductIdentifier()), obj);
                    hmResult.put(new Integer(objContractsVO.getPriceRunCustomerRef().getPriceRunCustomerRefId()), hmProductResult);
                }
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
        return hmResult;
    }

    public Collection getAllPriceByCustmerWise(int prospectiveCustId, int productId, String runType, int term, Date fromDate,Date toDate )
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Session objSession = null;
        List objList = null;
        List resultList = new ArrayList();
        try
        {
            logger.info("GETTING ALL PRICE BY CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(toDate);
            gc.add(Calendar.DATE, 1);
            String queryString = "select PRH.PRICE_RUN_TIME,C.FixedPrice$PerMWh,C.Fuel_Fctr,C.baseRate$PerMWh FROM PRC_Price_Run_Header PRH,PRC_Price_Run_Cust PRC,(select MAX(PRICE_RUN_TIME) PRICE_RUN_TIME FROM PRC_Price_Run_Cust PRC, PRC_Price_Run_Header PRH where PRC.Prospective_Cust_ID = ? AND PRC.RUN_STATUS = 1 AND PRC.Pricerun_Ref_No = PRH.Pricerun_Ref_No AND PRICE_RUN_TIME >= ? AND PRICE_RUN_TIME <= ? GROUP BY convert(varchar(10),PRICE_RUN_TIME,101)) AS MAXTIME,PRC_CONTRACTS C WHERE MAXTIME.PRICE_RUN_TIME = PRH.PRICE_RUN_TIME AND PRH.Pricerun_Ref_No = PRC.Pricerun_Ref_No AND PRC.PriceRunCustomer_Ref_ID = C.PriceRunCustomer_Ref_ID AND PRC.Prospective_Cust_ID = ? AND C.Prdct_ID = ? AND C.TERM = ?";
            if(!runType.equalsIgnoreCase("all"))
            {
                queryString = queryString + " AND PRH.Run_Type = '"+runType+"'";
            }
            queryString = queryString +" ORDER BY 1";
            Query objQuery = objSession.createSQLQuery(queryString);
            objQuery.setInteger(0, prospectiveCustId);
            objQuery.setDate(1, fromDate);
            objQuery.setDate(2, gc.getTime());
            objQuery.setInteger(3, prospectiveCustId);
            objQuery.setInteger(4, productId);
            objQuery.setInteger(5, term);
            objList = objQuery.list();
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                PriceRunResultDetails objPriceRunResultDetails = new PriceRunResultDetails();
                objPriceRunResultDetails.setRunDateTime((Date)innerRow[0]);
                objPriceRunResultDetails.setFixedPrice(((Double)innerRow[1]).floatValue());
                objPriceRunResultDetails.setFuelFactor(((Double)innerRow[2]).floatValue());
                objPriceRunResultDetails.setBaseRate(((Double)innerRow[3]).floatValue());
                resultList.add(objPriceRunResultDetails);
            }
            logger.info("GOT ALL PRICE BY CUSTOMER ID");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL PRICE BY CUSTOMER ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return resultList;
    }

    public ContractsVO getCPE(int customerRefId, int term, int productId)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria = null;
        Session objSession = null;
        ContractsVO objContractsVO = null;
        try
        {
            logger.info("GETTING CPE");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ContractsVO.class).add(Restrictions.eq("priceRunCustomerRef.priceRunCustomerRefId", new Integer(customerRefId))).add(Restrictions.eq("term", new Integer(term))).add(Restrictions.eq("product.productIdentifier", new Integer(productId)));
            objContractsVO = (ContractsVO) objCriteria.uniqueResult();
            logger.info("GOT CPE");
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
        return objContractsVO;
    }
    
    public List getMXPricingCPE(LinkedHashMap lhmTermDetails)
    { 
        List lstTermDet = new ArrayList();
        HashMap hmterm1 = lhmTermDetails.size()>0?(HashMap)lhmTermDetails.get(new Integer(0)):new HashMap();
        HashMap hmterm2 = lhmTermDetails.size()>1?(HashMap)lhmTermDetails.get(new Integer(1)):new HashMap();
        HashMap hmterm3 = lhmTermDetails.size()>2?(HashMap)lhmTermDetails.get(new Integer(2)):new HashMap();
        HashMap hmterm4 = lhmTermDetails.size()>3?(HashMap)lhmTermDetails.get(new Integer(3)):new HashMap() ;
        HashMap hmterm5 = lhmTermDetails.size()>4?(HashMap)lhmTermDetails.get(new Integer(4)):new HashMap();
        HashMap hmterm6 = lhmTermDetails.size()>5?(HashMap)lhmTermDetails.get(new Integer(5)):new HashMap();
        String[] components = {"term","Energy (kWh)","Energy ($)","Energy ($/kWh)","TDSP ($)","TDSP ($/kWh)","Taxes ($)","Taxes ($/kWh)","Total ($)","Total ($/kWh)"};
        for(int i=0;i<10;i++)
        {
            PriceComparisionDetails pcdTerm = new PriceComparisionDetails();
            pcdTerm.setComponents(hmterm1.get(components[i])==null?null:hmterm1.get(components[i]).toString());
            pcdTerm.setTerm1(hmterm2.get(components[i])==null?null:hmterm2.get(components[i]).toString());
            pcdTerm.setTerm2(hmterm3.get(components[i])==null?null:hmterm3.get(components[i]).toString());
            pcdTerm.setTerm3(hmterm4.get(components[i])==null?null:hmterm4.get(components[i]).toString());
            pcdTerm.setTerm4(hmterm5.get(components[i])==null?null:hmterm5.get(components[i]).toString());
            pcdTerm.setTerm5(hmterm6.get(components[i])==null?null:hmterm6.get(components[i]).toString());
            lstTermDet.add(pcdTerm);
        }
        return lstTermDet;
    }
    
    public ContractsVO getContractVO(int contractId)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Session objSession = null;
        ContractsVO objContractsVO = null;
        try
        {
            logger.info("GETTING CONTRACT DETAILS BY CONTRACT ID");
            objSession = HibernateUtil.getSession();
            objContractsVO = (ContractsVO) objSession.get(ContractsVO.class, new Integer(contractId));
            logger.info("GOT CONTRACT DETAILS BY CONTRACT ID");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET CONTRACT DETAILS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objContractsVO;
    }

    public Date cpeExpiryDate(Date runDate)
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(runDate);
        int hour = gc.get(Calendar.HOUR_OF_DAY);
        int day = gc.get(Calendar.DAY_OF_WEEK);
        if(hour < 15)
        {
            if(day == 7)
            {
                gc.add(Calendar.DATE, 2);
            } else if(day == 1)
            {
                gc.add(Calendar.DATE, 1);
            }
        } else
        {
            if(day <= 5 && day >= 1)
            {
                gc.add(Calendar.DATE, 1);
            } else if(day == 6)
            {
                gc.add(Calendar.DATE, 3);
            } else if(day == 7)
            {
                gc.add(Calendar.DATE, 2);
            }
        }
        gc.set(Calendar.HOUR_OF_DAY, 15);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        return gc.getTime();
    }

    public String getDates(int prscustID) throws PricingException
    {
        String Dates = "";
        Session objSession = null;
        CallableStatement cstmnt = null;
        ResultSet rs = null;
        List objList = null;
        try
        {
            objSession = HibernateUtil.getSession();
            String query = "";
            query = "select max(pricerun_ref_no)as maxi ,min(pricerun_ref_no)as mini from dbo.PRC_Price_Run_Cust where prospective_cust_id=" + prscustID;
            Query objQuery = objSession.createSQLQuery(query);
            Iterator itr = objQuery.list().iterator();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                Dates = String.valueOf(innerRow[0]);
                Dates +=","+ String.valueOf(innerRow[1]);
            }
        }
        catch(Exception e)
        {
            throw new PricingException(e.toString());
        }
        finally
        {
            objSession.close();
        }
        return Dates;
    }
    
    public String getPriceRunId(int prscustID) throws PricingException
    {
        String pricerun = "";
        Session objSession = null;
        CallableStatement cstmnt = null;
        ResultSet rs = null;
        List objList = null;
        try
        {
            objSession = HibernateUtil.getSession();
            String query = "";
            query = "select B.PriceRunCustomer_ref_Id from(select max(pricerun_ref_no) PriceRun  from   dbo.PRC_Price_Run_Cust where Prospective_cust_id = "+ prscustID+" and run_status=1 )A inner join (select max(pricerun_ref_no) as PriceRun,PriceRunCustomer_ref_Id from dbo.PRC_Price_Run_Cust where Prospective_cust_id ="+ prscustID+"  group by PriceRunCustomer_ref_Id)B on A.PriceRun=B.PriceRun";
            Query objQuery = objSession.createSQLQuery(query);
            Iterator itr = objQuery.list().iterator();
            while(itr.hasNext())
            {
                pricerun = String.valueOf(itr.next());
            }
        }
        catch(Exception e)
        {
            throw new PricingException(e.toString());
        }
        finally
        {
            objSession.close();
        }
        return pricerun;
    }
    
    public String[] getesiids(int prscustID) throws PricingException
    {
        if(prscustID==0)
            throw new PricingException("Invalid Price Run Id");
        
        String values[] = new String[2];
        Session objSession = null;
        CallableStatement cstmnt = null;
        ResultSet rs = null;
        List objList = null;
        int term = 0;
        try
        {
            objSession = HibernateUtil.getSession();
            String query = "";
            query = "select min(Term)as term from dbo.PRC_Contracts where PriceRunCustomer_ref_Id = "+ prscustID +" and prdct_id=1" ;
            Query objQuery = objSession.createSQLQuery(query);
            Iterator itr = objQuery.list().iterator();
            while(itr.hasNext())
            {
                try
                {
                    term = Integer.parseInt(String.valueOf(itr.next()));
                }
                catch (NumberFormatException e) {
                    throw new PricingException("Invalid Price Run Id");
                }
            }
            Criteria crt = objSession.createCriteria(ContractsVO.class).add(Restrictions.eq("priceRunCustomerRef.priceRunCustomerRefId",new Integer(prscustID))).add(Restrictions.eq("product.productIdentifier", new Integer(1))).add(Restrictions.eq("term", new Integer(term)));
            objList = crt.list();
            values[0] = ((ContractsVO)objList.get(0)).getEsiIds();
            values[1] = String.valueOf(((ContractsVO)objList.get(0)).getAnnualkWh());
        }
        catch(Exception e)
        {
            throw new PricingException(e.toString());
        }
        finally
        {
            objSession.close();
        }
        return values;
    }
    
    // method for pagination
    
    public HashMap getContractsByProduct(Set lstContracts)
    {
        HashMap hmProduct = new HashMap();
        try
        {
            Iterator ite = lstContracts.iterator();
            List lstfixed = new java.util.ArrayList();; 
            List lstFAR = new java.util.ArrayList();;
            List lstbundle = new java.util.ArrayList();;
            List lstunbundle = new java.util.ArrayList();;
            List lstblock = new java.util.ArrayList();;
            List lstblockMCPE =new java.util.ArrayList();;
            List lstmcpe =new java.util.ArrayList();;
            List lstres = new java.util.ArrayList();;
            while(ite.hasNext())
            {
                ContractsVO objContractsVO = (ContractsVO)ite.next();
                int productId = objContractsVO.getProduct().getProductIdentifier();
                switch (productId)
                {
                    case 1:
                        lstfixed.add(objContractsVO);
                        break;
                    case 2:
                        lstmcpe.add(objContractsVO);
                        break;
                    case 3:
                        lstblock.add(objContractsVO);
                        break;
                    case 4:
                        lstblockMCPE.add(objContractsVO);
                        break;
                    case 5:
                        lstFAR.add(objContractsVO);
                        break;
                    case 6:
                        lstres.add(objContractsVO);
                        break;
                    case 7:
                        lstbundle.add(objContractsVO);
                        break;
                    case 8:
                        lstunbundle.add(objContractsVO);
                        break;
                    default:
                        break;
                }
            }
            hmProduct.put(new Integer(1),lstfixed);
            hmProduct.put(new Integer(2),lstmcpe);
            hmProduct.put(new Integer(3),lstblock);
            hmProduct.put(new Integer(4),lstblockMCPE);
            hmProduct.put(new Integer(5),lstFAR);
            hmProduct.put(new Integer(6),lstres);
            hmProduct.put(new Integer(7),lstbundle);
            hmProduct.put(new Integer(8),lstunbundle);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return hmProduct;
    }
    
    public static void main(String[] args)
    {
        /*Filter obj = new Filter();
        obj.setFieldValue("p");
        obj.setSpecialFunction(HibernateUtil.STARTS_WITH);
        Filter o = new Filter();
        o.setFieldValue("t");
        o.setSpecialFunction(HibernateUtil.STARTS_WITH);*/
        //System.out.println(new ContractsDAO().getAllCPE(new Date("apr-03-2007"),new Date("apr-17-2007"), null,null));
        //System.out.println(new ContractsDAO().getCPE(4,36,1));
        //System.out.println(new ContractsDAO().getAllContractStatusFromCMS());
        ContractsDAO objContractsDAO = new ContractsDAO();
        try
        {
           String st[] =  objContractsDAO.getesiids(3439);
           //List objList = (List)objContractsDAO.getAllPriceByCustmerWise(485,5,"all",12,new Date("aug-01-2007"),new Date("aug-01-2007"));
           System.out.println(st.length +" * * * * " +st[0] +" * * * " + st[1]);
           //String st = objContractsDAO.getDates(841);
           //System.out.println(" * * "+st);
        }
        catch (PricingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
/*
 *$Log: ContractsDAO.java,v $
 *Revision 1.5  2009/01/27 05:47:57  tannamalai
 *changes - according to MX server
 *
 *Revision 1.4  2008/11/21 09:46:19  jvediyappan
 *Trieagle changed as MXEnergy.
 *
 *Revision 1.3  2008/02/14 05:43:56  tannamalai
 *pagination done for price quote page
 *
 *Revision 1.2  2007/12/18 09:22:56  tannamalai
 *method changed
 *
 *Revision 1.1  2007/12/07 06:18:35  jvediyappan
 *initial commit.
 *
 *Revision 1.7  2007/11/28 10:12:52  tannamalai
 *cms links removed
 *
 *Revision 1.6  2007/11/28 05:42:59  jnadesan
 *imports organized
 *
 *Revision 1.5  2007/11/27 07:25:45  tannamalai
 *unwanted prints removed
 *
 *Revision 1.4  2007/11/27 04:19:57  tannamalai
 **** empty log message ***
 *
 *Revision 1.3  2007/11/22 11:53:40  jnadesan
 *hashmap vaue chaned
 *
 *Revision 1.2  2007/11/22 05:25:36  spandiyarajan
 *PDF details changed
 *
 *Revision 1.1  2007/10/30 05:51:43  jnadesan
 *Initial commit.
 *
 *Revision 1.1  2007/10/26 15:19:17  jnadesan
 *initail MXEP commit
 *
 *Revision 1.39  2007/09/04 05:23:55  spandiyarajan
 *removed unwanted imports
 *
 *Revision 1.38  2007/09/03 14:09:42  sramasamy
 *Log message is added for log file.
 *
 *Revision 1.37  2007/08/13 10:12:14  spandiyarajan
 *contract tracking bug fixed
 *
 *Revision 1.36  2007/08/13 04:55:27  kduraisamy
 *duplicate runresults for a day problem solved.
 *
 *Revision 1.35  2007/08/10 12:29:16  kduraisamy
 *DUPLICATE ELIMINATED IN SALESTOOL
 *
 *Revision 1.34  2007/08/10 10:13:34  kduraisamy
 *runType all handled.
 *
 *Revision 1.33  2007/08/10 10:05:23  jnadesan
 *baserate also selected from query
 *
 *Revision 1.32  2007/08/10 06:46:53  kduraisamy
 *latest success run for every day has been taken for the graph.
 *
 *Revision 1.31  2007/08/06 15:56:02  jnadesan
 *methods added to show history page
 *
 *Revision 1.30  2007/07/31 12:28:42  spandiyarajan
 *added rolback transaction in catch block
 *
 *Revision 1.29  2007/07/30 09:21:38  spandiyarajan
 *in cpe page added cms id filter and data ordered by customer name
 *
 *Revision 1.28  2007/07/02 12:42:34  jnadesan
 *expire date set as per pricerunCustomer wise
 *
 *Revision 1.27  2007/06/23 08:02:00  jnadesan
 *updating startDate and ExpDate
 *
 *Revision 1.26  2007/06/20 13:21:10  kduraisamy
 *cpeExpiryDate() added.
 *
 *Revision 1.25  2007/06/12 12:56:42  spandiyarajan
 *removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
 *
 *Revision 1.24  2007/06/04 04:51:08  kduraisamy
 *getAllTriEagleRateClass() added.
 *
 *Revision 1.23  2007/05/28 13:27:19  kduraisamy
 *CDR INFORMATION ADDED INTO CMS.
 *
 *Revision 1.22  2007/05/23 04:52:44  kduraisamy
 *sales Rep, sale Manager first name, last name added into the filter.
 *
 *Revision 1.21  2007/05/16 11:27:46  jnadesan
 *filter applied to get customer while going from dasboard page
 *
 *Revision 1.20  2007/05/16 07:21:37  jnadesan
 *filter by salesmanager wise
 *
 *Revision 1.19  2007/05/02 10:50:05  kduraisamy
 *contractsTrackingVO added.
 *
 *Revision 1.18  2007/04/23 05:52:20  kduraisamy
 *unwanted println removed.
 *
 *Revision 1.17  2007/04/17 07:24:05  kduraisamy
 *getAllCPE productwise added.
 *
 *Revision 1.16  2007/04/17 06:22:04  kduraisamy
 *getAllCPE productwise added.
 *
 *Revision 1.15  2007/04/03 07:39:06  kduraisamy
 *order by term added.
 *
 *Revision 1.14  2007/04/03 07:26:35  kduraisamy
 *order by term added.
 *
 *Revision 1.13  2007/04/03 07:21:27  kduraisamy
 *order by term added.
 *
 *Revision 1.12  2007/04/02 16:30:03  kduraisamy
 *getAllCPE() added.
 *
 *Revision 1.11  2007/04/02 16:23:23  kduraisamy
 *getCPE() added.
 *
 *Revision 1.10  2007/04/02 14:43:33  kduraisamy
 *getCPE() added.
 *
 *Revision 1.9  2007/04/02 09:39:31  kduraisamy
 *getALLCPE() modified.
 *
 *Revision 1.8  2007/03/28 12:21:09  kduraisamy
 *addContract changed.
 *
 *Revision 1.7  2007/03/28 12:07:31  kduraisamy
 *getAllcontracts() completed.
 *
 *Revision 1.6  2007/03/28 11:36:14  kduraisamy
 *template for getAllContracts() added.
 *
 *Revision 1.5  2007/03/28 11:06:00  kduraisamy
 *template for getAllContracts() added.
 *
 *Revision 1.4  2007/03/28 10:23:15  kduraisamy
 *filter for contract added.
 *
 *Revision 1.3  2007/03/28 06:04:01  kduraisamy
 *add Method for contract added.
 *
 *Revision 1.2  2007/03/28 05:32:14  kduraisamy
 *mapping for contracts added.
 *
 *Revision 1.1  2007/03/28 05:09:31  kduraisamy
 *initial commit.
 *
 *
 */