/*
 * Created on Mar 23, 2007
 *
 * ClassName	:  	DealLeversDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.calculation.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.calculation.valueobjects.CustomerDealLeversVO;
import com.savant.pricing.calculation.valueobjects.CustomerPreferencesTermsVO;
import com.savant.pricing.calculation.valueobjects.DealLeversVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.SortString;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.transferobjects.DealLevers;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DealLeversDAO
{
    static Logger logger = Logger.getLogger(DealLeversDAO.class);
   
    public void saveDealLevers(int priceRunCustomerRefId, int term, Vector vecDealLevers)
    {
        int prospectiveCustomerId = 0;
        try
        {
            logger.info("SAVING DEAL LEVERS");
            prospectiveCustomerId = this.getProspectiveCustomerByPriceRunCustomerRefId(priceRunCustomerRefId);
            this.saveDealLeversByProspectiveCustomerId(prospectiveCustomerId, term, vecDealLevers);
            logger.info("DEAL LEVERS SAVED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING SAVE DEAL LEVERS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING SAVE DEAL LEVERS", e);
            e.printStackTrace();
        }
    }
   
    public void saveDealLeversByProspectiveCustomerId(int prospectiveCustomerId, int term, Vector vecDealLevers)
    {
        Session objSession = null;
        try
        {
            logger.info("SAVING DEAL LEVERS BY PROSPECTIVE CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            for(int i = 0;i<vecDealLevers.size();i++)
            {
                DealLevers objDealLevers = (DealLevers)vecDealLevers.get(i);
                Query objQuery = objSession.createQuery("update CustomerDealLeversVO as customerdealleversvo set customerdealleversvo.value = ?, customerdealleversvo.modifiedDate = ?  where customerdealleversvo.prospectiveCustomer.prospectiveCustomerId = ? and customerdealleversvo.term = ? and customerdealleversvo.dealLever.dealLeverIdentifier = ?");
                objQuery.setFloat(0, objDealLevers.getValue());
                objQuery.setTimestamp(1, new Date());
                objQuery.setInteger(2, prospectiveCustomerId);
                objQuery.setInteger(3,term);
                objQuery.setInteger(4,objDealLevers.getDealLeverIdentifier());
                if(objQuery.executeUpdate()<=0)
                {
                    CustomerDealLeversVO objCustomerDealLeversVO = new CustomerDealLeversVO();
                    ProspectiveCustomerVO objProspectiveCustomerVO = new ProspectiveCustomerVO();
                    objProspectiveCustomerVO.setProspectiveCustomerId(prospectiveCustomerId);
                    objCustomerDealLeversVO.setProspectiveCustomer(objProspectiveCustomerVO);
                    objCustomerDealLeversVO.setTerm(term);
                    objCustomerDealLeversVO.setValue(objDealLevers.getValue());
                    DealLeversVO objDealLeversVO = new DealLeversVO();
                    objDealLeversVO.setDealLeverIdentifier(objDealLevers.getDealLeverIdentifier());
                    objCustomerDealLeversVO.setDealLever(objDealLeversVO);
                    objSession.save(objCustomerDealLeversVO);
                }
                
            }
            objSession.getTransaction().commit();
            logger.info("DEAL LEVERS BY PROSPECTIVE CUSTOMER ID IS SAVED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING SAVE DEAL LEVERS BY PROSPECTIVE CUSTOMER ID", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING SAVE DEAL LEVERS BY PROSPECTIVE CUSTOMER ID", e);
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
    }
   
    public DealLeversVO getDealLever(int dealLeverId)
    {
        Session objSession = null;
        DealLeversVO objDealLeversVO = null;
        try
        {
            logger.info("GETTING DEAL LEVER BY DEAL LEVER ID");
            objSession = HibernateUtil.getSession();
            objDealLeversVO = (DealLeversVO)objSession.get(DealLeversVO.class, new Integer(dealLeverId));
            logger.info("GOT DEAL LEVER");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DEAL LEVER BY DEAL LEVER ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objDealLeversVO;
    }
    
    
    public boolean applyAll(DealLeversVO objDealLeversVO)
    {
        Session objSession = null;
        boolean updateResult = false;
        try
        {
            logger.info("APPLYING DEAL LEVER VALUE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            Query objQuery = objSession.createQuery("update CustomerDealLeversVO as customerDealLevers set customerDealLevers.value = ?, customerDealLevers.modifiedDate = ? where dealLever.dealLeverIdentifier = ?");
            objQuery.setFloat(0, objDealLeversVO.getValue());
            objQuery.setTimestamp(1, new Date());
            objQuery.setInteger(2, objDealLeversVO.getDealLeverIdentifier());
            objQuery.executeUpdate();
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("DEAL LEVER VALUE IS UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING APPLY DEAL LEVER VALUE", e);
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
    public boolean overRide(boolean isOverRide)
    {
        Session objSession = null;
        boolean updateResult = false;
        try
        {
            logger.info("UPDATING OVERRIDE VALUE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            Query objQuery = objSession.createQuery("update DealLeversVO as dealLevers set dealLevers.overRide = ?");
            objQuery.setBoolean(0, isOverRide);
            objQuery.executeUpdate();
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("OVERRIDE VALUE IS UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE OVERRIDE VALUE", e);
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
    
    public boolean modifyDealLever(DealLeversVO objDealLeversVO)
    {
        Session objSession = null;
        boolean updateResult = false;
        try
        {
            logger.info("MODIFYING DEAL LEVER");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update(objDealLeversVO);
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("DEAL LEVER IS MODIFIED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING MODIFY THE DEAL LEVER", e);
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
    public List getAllDealLevers()
    {
        Session objSession = null;
        List objList = null;
        Criteria objCriteria = null;
        try
        {
            logger.info("GETTING ALL DEAL LEVERS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(DealLeversVO.class);
            objList = objCriteria.list();
            logger.info("GOT ALL DEAL LEVERS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL DEAL LEVERS", e);
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
    public HashMap getAllDealLevers(Filter dealLeverfilter, String sortBy, boolean ascending, int startIndex, int displayCount)
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
            logger.info("GETTING ALL DEAL LEVERS WITH SOME FILTER OPTION");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(DealLeversVO.class);
            if(dealLeverfilter != null)
            {
                    objCriteria.add(Restrictions.like(dealLeverfilter.getFieldName(),dealLeverfilter.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(dealLeverfilter.getSpecialFunction())));
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
            logger.info("GOT ALL DEAL LEVERS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL DEAL LEVERS", e);
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
    
    private int getProspectiveCustomerByPriceRunCustomerRefId(int priceRunCustRefId)
    {
        Session objSession = null;
        int prospectiveCustomerId = 0;
        try
        {
            logger.info("GETTING PROSPECTIVE CUSTOMER BY PRICERUNCUSTOMERREFID");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select priceRunCustomer.prospectiveCustomer.prospectiveCustomerId from PriceRunCustomerVO as priceRunCustomer where priceRunCustomer.priceRunCustomerRefId = ?");
            objQuery.setInteger(0, priceRunCustRefId);
            Iterator itr = objQuery.iterate();
            if(itr.hasNext())
            {
                prospectiveCustomerId = ((Integer)itr.next()).intValue();
                logger.info("GOT PROSPECTIVE CUSTOMER BY PRICERUNCUSTOMERREFID");
            }
            else
            {
                logger.info("NOT GET PROSPECTIVE CUSTOMER BY PRICERUNCUSTOMERREFID");
            }
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET PROSPECTIVE CUSTOMER", e);
            e.printStackTrace();
        }
        finally 
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return prospectiveCustomerId;
    }
    public LinkedHashMap getDealLeversTermDetails(int prospectiveCustomerId)
    {
        Session objSession = null;
        Query objQuery = null;
        HashMap hmResult = new HashMap();
        LinkedHashMap lhm = new LinkedHashMap();
        
        ProspectiveCustomerDAO  objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        try
        {
            logger.info("GETTING DEAL LEVERS TERM DETAILS");
            objSession = HibernateUtil.getSession();
            ProspectiveCustomerVO objProspectiveCustomerVO = (ProspectiveCustomerVO)objSession.get(ProspectiveCustomerVO.class,new Integer(prospectiveCustomerId));
            Iterator itr = objProspectiveCustomerDAO.getProspectiveCustomerPreferenceTerms(objProspectiveCustomerVO.getProspectiveCustomerId()).iterator();
            while(itr.hasNext())
            {
                CustomerPreferencesTermsVO objCustomerPreferencesTermsVO = (CustomerPreferencesTermsVO)itr.next();
                hmResult.put(new Integer(objCustomerPreferencesTermsVO.getTerm()), new Date());
            }
            objQuery = objSession.createQuery("select custDealLevers.term, max(custDealLevers.modifiedDate) from CustomerDealLeversVO as custDealLevers where custDealLevers.prospectiveCustomer.prospectiveCustomerId = ? group by custDealLevers.term order by custDealLevers.term");
            objQuery.setInteger(0, prospectiveCustomerId);
            itr = objQuery.iterate();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                hmResult.put(innerRow[0],innerRow[1]);
            }
            Set objSet = hmResult.keySet();
            itr = objSet.iterator();
            String terms = "";
            while(itr.hasNext())
            {
                if(terms.length()<=0)
                {
                    terms = String.valueOf(itr.next());
                }
                else
                {
                    terms = terms +","+String.valueOf(itr.next());
                }
            }
            SortString objSortString = new SortString();
            terms = objSortString.sortString(terms);
            StringTokenizer st = new StringTokenizer(terms,",");
            while(st.hasMoreTokens())
            {
                int token = Integer.parseInt((String)st.nextElement());
                lhm.put(new Integer(token),hmResult.get(new Integer(token)));
            }
            logger.info("GOT DEAL LEVERS TERM DETAILS FOR THE PROSPECTIVE CUSTOMER");	
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET DEAL LEVERS TERM DETAILS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET DEAL LEVERS TERM DETAILS", e);
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
    public boolean deleteDealLeversByCustomerIdTerm(int prospectiveCustomerId, int term)
    {
        Session objSession = null;
        boolean deleteResult = false;
        try
        {
            logger.info("DELETING DEAL LEVERS BY CUSTOMER ID AND TERM");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            Query objQuery = objSession.createQuery("delete from CustomerDealLeversVO as custDealLevers where custDealLevers.prospectiveCustomer.prospectiveCustomerId = ? and custDealLevers.term = ?");
            objQuery.setInteger(0, prospectiveCustomerId);
            objQuery.setInteger(1, term);
            int noOfRowsAffected = objQuery.executeUpdate();
            if(BuildConfig.DMODE)
                System.out.println("noOfRowsAffected:"+noOfRowsAffected);
            objSession.getTransaction().commit();
            if(noOfRowsAffected>0)
            {
                deleteResult = true;
                logger.info("DEAL LEVERS DELETED");
            }
            else
            {
                deleteResult = false;
                logger.info("DEAL LEVERS NOT DELETED");
            }
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DELETE THE DEAL LEVERS", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING DELETE THE DEAL LEVERS", e);
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
        return deleteResult;
    }
    public HashMap getDealLeversByCustomerIdTerm(int prospectiveCustomerId, int term)
    {
        Session objSession = null;
        List objResultList = new ArrayList();
        HashMap hmResult = new HashMap();
        Criteria objCriteria = null;
        try
        {
            logger.info("GETTING DEAL LEVERS BY CUSTOMER ID AND TERM");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(CustomerDealLeversVO.class);
            objResultList = objCriteria.add(Restrictions.eq("prospectiveCustomer.prospectiveCustomerId",new Integer(prospectiveCustomerId))).add(Restrictions.eq("term", new Integer(term))).list();
            Iterator itr = objResultList.iterator();
            if(!itr.hasNext())
            {
                objCriteria = objSession.createCriteria(DealLeversVO.class);
                objResultList = objCriteria.list();
                hmResult.put("DealLeversVO", objResultList);
                logger.info("GOT DEAL LEVERS FOR THE CUSTOMER");
            }
            else
            {
                hmResult.put("CustomerDealLeversVO", objResultList);
                logger.info("NO DEAL LEVERS FOR THE CUSTOMER");
            }
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE DEAL LEVERS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET THE DEAL LEVERS", e);
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
    
    public List getDealLevers(int priceRunCustomerRefId, int term)
    {
        Session objSession = null;
        List objResultList = new ArrayList();
        int prospectiveCustomerId = 0;
        boolean isOverRide = false;
        try
        {
            logger.info("GETTING DEAL LEVERS BY PRICERUN CUSTOMERREFID AND TERM");
            objSession = HibernateUtil.getSession();
            List dealLeversGlobal = this.getAllDealLevers();
            Iterator itrDealLeversGlobal = dealLeversGlobal.iterator();
            if(itrDealLeversGlobal.hasNext())
            {
                DealLeversVO objDealLeversVO  = (DealLeversVO)itrDealLeversGlobal.next();
                isOverRide = objDealLeversVO.isOverRide();
            }
            //System.out.println("OverRide:"+isOverRide);
            if(!isOverRide)
            {
                prospectiveCustomerId = this.getProspectiveCustomerByPriceRunCustomerRefId(priceRunCustomerRefId);
                Query objQuery = objSession.createQuery("select customerdealleversvo.dealLever.dealLeverIdentifier, customerdealleversvo.value from CustomerDealLeversVO as customerdealleversvo where customerdealleversvo.prospectiveCustomer.prospectiveCustomerId = ? and customerdealleversvo.term = ?");
                objQuery.setInteger(0, prospectiveCustomerId);
                objQuery.setInteger(1,term);
                Iterator itr = objQuery.iterate();
                if(itr.hasNext())
                {
                    while(itr.hasNext())
                    {
                        Object[] innerRow = (Object[]) itr.next();
                        DealLevers objDealLevers = new DealLevers();
                        objDealLevers.setDealLeverIdentifier(((Integer)innerRow[0]).intValue());
                        objDealLevers.setValue(((Float)innerRow[1]).floatValue());
                        objResultList.add(objDealLevers);
                    }
                }
                else
                {
                    isOverRide =  true;
                }
                
            }
            if(isOverRide)
            {
                Iterator itr = dealLeversGlobal.iterator();
                while(itr.hasNext())
                {
                    DealLeversVO objDealLeversVO = (DealLeversVO)itr.next();
                    DealLevers objDealLevers = new DealLevers();
                    objDealLevers.setDealLeverIdentifier(objDealLeversVO.getDealLeverIdentifier());
                    objDealLevers.setValue(objDealLeversVO.getValue());
                    objResultList.add(objDealLevers);
                }
            }
            logger.info("GOT DEAL LEVERS BY PRICERUN CUSTOMERREFID AND TERM");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET DEAL LEVERS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET DEAL LEVERS", e);
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
    
    public LinkedHashMap getDealLeverDetails(int CMSId, int cdrStatId, int autoRun)
    {
        Session objSession = null;
        Query objQuery = null;
        String queryString = "";
        LinkedHashMap lhmProspect = new LinkedHashMap();
        try
        {
            logger.info("GETTING DEAL LEVER DETAILS BY CMSID, CDRSTATID AND AUTORUN");
            objSession = HibernateUtil.getSession();
            // objQuery = objSession.createQuery("select custDealLevers.prospectiveCustomer.prospectiveCustomerId, custDealLevers.term, dealLevers.dealLever, custDealLevers.value, dealLevers.dealLeverIdentifier from CustomerDealLeversVO as custDealLevers, DealLeversVO as dealLevers, ProspectiveCustomerVO as prospectiveCustomer where custDealLevers.prospectiveCustomer.prospectiveCustomerId = prospectiveCustomer.prospectiveCustomerId and custDealLevers.dealLever.dealLeverIdentifier = dealLevers.dealLeverIdentifier order by custDealLevers.prospectiveCustomer.prospectiveCustomerId, custDealLevers.term");
            queryString = "select cust.prospectiveCustomerId, products.productIdentifier, products.productName, "+
                    "custdeal.term, deal.dealLeverIdentifier, custdeal.value from ProspectiveCustomerVO cust, CustomerPreferenceProductsVO custproducts, "+
                    "ProductsVO products, CustomerPreferencesTermsVO terms, CustomerDealLeversVO custdeal, DealLeversVO deal, CustomerPreferencesVO custPref "+
                    "where cust.prospectiveCustomerId = custPref.prospectiveCustomer.prospectiveCustomerId and cust.prospectiveCustomerId = custproducts.customer.prospectiveCustomerId and " +
                    "cust.prospectiveCustomerId = terms.prospectiveCustomer.prospectiveCustomerId and "+
                    "cust.prospectiveCustomerId = custdeal.prospectiveCustomer.prospectiveCustomerId and terms.term = custdeal.term and "+
                    "products.productIdentifier = custproducts.product.productIdentifier and custdeal.dealLever.dealLeverIdentifier = deal.dealLeverIdentifier";
            
            if(cdrStatId != 0)
            {
                queryString = queryString+ " and cust.cdrStatus.cdrStateId = "+cdrStatId;
            }
            if(CMSId != 0)
            {
                queryString = queryString + " and cust.customerId = "+new Integer(CMSId);
            }
            if(autoRun != 0)
            {
                // 1 for auto run enabled customer
                if(autoRun == 1)
                queryString = queryString + " and custPref.autoRun = "+1;
                else
                queryString = queryString + " and custPref.autoRun = "+0;
            }
            queryString = queryString +" order by cust.customerName, products.productName, custdeal.term, deal.dealLeverIdentifier";
            objQuery = objSession.createQuery(queryString);
            List objList = objQuery.list();
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                String keyProspect = String.valueOf(innerRow[0]);
                String keyProd = String.valueOf(innerRow[2]);
                String keyTerm = String.valueOf(innerRow[3]);
                LinkedHashMap lhmProducts = new LinkedHashMap();
                LinkedHashMap lhmterms = new LinkedHashMap();
                HashMap dealLevers = new HashMap();
                if(lhmProspect.containsKey(keyProspect))
                {
                    lhmProducts = (LinkedHashMap)lhmProspect.get(keyProspect);
                    if(lhmProducts.containsKey(keyProd))
                    {
                        lhmterms = (LinkedHashMap)lhmProducts.get(keyProd);
                        if(lhmterms.containsKey(keyTerm))
                            dealLevers = (HashMap)lhmterms.get(keyTerm);
                    }
                }
                dealLevers.put(innerRow[4],innerRow[5]);
                lhmterms.put(keyTerm, dealLevers);
                lhmProducts.put(keyProd, lhmterms);
                lhmProspect.put(keyProspect, lhmProducts);
                logger.info("GOT DEAL LEVERS DETAILS");
            }
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET DEAL LEVERS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET DEAL LEVERS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return lhmProspect;
    }
    
    public static void main(String[] args)
    {
        //System.out.println(new DealLeversDAO().getDealLevers(15, 24));
        /*DealLeversVO obj = new DealLeversVO();
        obj.setDealLeverIdentifier(1);
        obj.setValue(16.5f);*/
        //new DealLeversDAO().applyAll(obj);
        /*List objList = new DealLeversDAO().getDealLeversByCustomerIdTerm(1,4);
        Iterator itr = objList.iterator();
        while(itr.hasNext())
        {
            CustomerDealLeversVO objCustomerDealLeversVO = (CustomerDealLeversVO)itr.next();
            System.out.println("term:"+objCustomerDealLeversVO.getTerm());
            System.out.println("DealLeverValue:"+objCustomerDealLeversVO.getValue());
            System.out.println("DeallevId:"+objCustomerDealLeversVO.getDealLever().getDealLever());
        }*/
        //System.out.println(new DealLeversDAO().deleteDealLeversByCustomerIdTerm(1,45));
        //System.out.println(new DealLeversDAO().getDealLevers(41,12));
        //new DealLeversDAO().getAllDealLevers();
        /*Filter filobj = new Filter();
        filobj.setFieldName("dealLever");
        filobj.setFieldValue("a");
        filobj.setSpecialFunction(HibernateUtil.STARTS_WITH);*/
        //HashMap hmgetAllDealLevers = objDealLeversDAO.getAllDealLevers(filobj,"dealLever",true,0,10); 
        //System.out.println("hmgetAllDealLevers :"+new DealLeversDAO().getDealLeversTermDetails(453));
        
        DealLeversDAO objDealLeversDAO = new DealLeversDAO();
        System.out.println("hmresult:"+objDealLeversDAO.getDealLeverDetails(0,1,0));
    }
        
}

/*
*$Log: DealLeversDAO.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:54  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:23  jnadesan
*initail MXEP commit
*
*Revision 1.33  2007/09/10 05:07:29  sramasamy
*Log Message Correction.
*
*Revision 1.32  2007/09/04 05:06:17  spandiyarajan
*removed unwanted imports
*
*Revision 1.31  2007/08/31 14:49:27  sramasamy
*Log message is added for log file.
*
*Revision 1.30  2007/08/10 13:55:52  spandiyarajan
*fileter added in schedule page
*
*Revision 1.29  2007/08/09 07:45:38  spandiyarajan
*Initial commit for schedule page
*
*Revision 1.28  2007/07/31 12:26:53  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.27  2007/07/31 11:39:32  kduraisamy
*objsession properly closed or calling procedure properly done.
*
*Revision 1.26  2007/07/04 13:17:22  jnadesan
*import organized
*
*Revision 1.25  2007/06/21 13:03:17  kduraisamy
*terms ascending added.
*
*Revision 1.24  2007/06/12 12:55:25  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.23  2007/06/05 06:19:18  jnadesan
*terms are taken from customer preference
*
*Revision 1.22  2007/04/18 06:28:04  kduraisamy
*imports organized.
*
*Revision 1.21  2007/04/18 06:27:12  kduraisamy
*set removed.
*
*Revision 1.20  2007/04/12 13:57:33  kduraisamy
*unwanted println commented.
*
*Revision 1.19  2007/04/12 07:36:45  kduraisamy
*imports organized.
*
*Revision 1.18  2007/04/12 05:15:57  spandiyarajan
*bug fixed
*
*Revision 1.17  2007/04/08 15:09:49  jnadesan
*no of records checked
*
*Revision 1.16  2007/04/08 06:37:35  kduraisamy
*return type changed to hashMap.
*
*Revision 1.15  2007/04/07 12:54:36  kduraisamy
*global Deal levers for customer id and term added.
*
*Revision 1.14  2007/04/07 12:45:20  kduraisamy
*global Deal levers for customer id and term added.
*
*Revision 1.13  2007/04/07 09:37:10  kduraisamy
*override added for deallevers
*
*Revision 1.12  2007/04/07 05:51:21  kduraisamy
*overRide added for deal Levers
*
*Revision 1.11  2007/03/31 07:11:49  kduraisamy
*delete deal lever values added.
*
*Revision 1.10  2007/03/31 06:39:31  kduraisamy
*unwanted method removed.
*
*Revision 1.9  2007/03/31 06:21:03  kduraisamy
**** empty log message ***
*
*Revision 1.8  2007/03/30 10:07:00  kduraisamy
*getDealLeversByCustomerIdTerm() added.
*
*Revision 1.7  2007/03/30 09:59:33  kduraisamy
*getDealLeverTermDate() added.
*
*Revision 1.6  2007/03/30 09:56:47  kduraisamy
*getDealLeverTermDate() added.
*
*Revision 1.5  2007/03/28 13:56:25  rraman
*new getAllDealLevers(filte,...) method created
*
*Revision 1.4  2007/03/23 10:41:33  kduraisamy
*applyAll() for dealLevers added.
*
*Revision 1.3  2007/03/23 10:39:39  jnadesan
*get deallevers method added
*
*Revision 1.2  2007/03/23 07:25:21  kduraisamy
*deal Levers stored by customer and term wise.
*
*Revision 1.1  2007/03/23 07:14:34  kduraisamy
*deal Levers stored by customer and term wise.
*
*
*/