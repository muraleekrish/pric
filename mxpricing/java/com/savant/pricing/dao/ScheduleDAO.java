/*
 * @(#) ScheduleDAO.java	Aug 23, 2007
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. 
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use it 
 * only in accordance with the terms of the license agreement you entered into with Savant Technologies Pvt Ltd.
 * 
 */
 
package com.savant.pricing.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/*
 * @author	spandiyarajan
 * 
 */

public class ScheduleDAO
{
    private static Logger logger = Logger.getLogger(ScheduleDAO.class);
    
    public HashMap getAutoRunCust()
    {
        HashMap hmCustPref = new HashMap();
        Session objSession = null;
        Query objQuery = null;
        String queryString = "";
        try
        {
            logger.info("GETTING AUTO RUN CUSTOMER");
            objSession = HibernateUtil.getSession();
            queryString = "select custPref.prospectiveCustomer.prospectiveCustomerId, custPref.autoRun from CustomerPreferencesVO custPref";
            objQuery = objSession.createQuery(queryString);
            List objList = objQuery.list();
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                hmCustPref.put(innerRow[0], innerRow[1]);
            }
            logger.info("GOT AUTO RUN CUSTOMER");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET AUTO RUN CUSTOMER", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET AUTO RUN CUSTOMER", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hmCustPref;
    }
    
    public HashMap getTotEsiidbyCust()
    {
        HashMap hmEsiid = new HashMap();
        Session objSession = null;
        Query objQuery = null;
        String queryString = "";
        try
        {
            logger.info("GETTING TOTAL ESIID BY CUSTOMER");
            objSession = HibernateUtil.getSession();
            //queryString = "select pic.customer.prospectiveCustomerId, count(esiId) as EsiidTot from PICVO pic group by pic.customer.prospectiveCustomerId";
            queryString = "select cust.prospectiveCustomerId, count(pic.esiId) from ProspectiveCustomerVO as cust left join cust.picVOs as pic group by cust.prospectiveCustomerId";
            
            objQuery = objSession.createQuery(queryString);
            List objList = objQuery.list();
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                hmEsiid.put(innerRow[0], innerRow[1]);
            }
            logger.info("GOT TOTAL ESIID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET TOTAL ESIID", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET TOTAL ESIID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hmEsiid;
    }
    
    public HashMap getTotValidEsiidbyCust()
    {
        HashMap hmEsiid = new HashMap();
        Session objSession = null;
        Query objQuery = null;
        String queryString = "";
        try
        {
            logger.info("GETTING TOTAL VALID ESIID");
            objSession = HibernateUtil.getSession();
            //queryString = "select pic.customer.prospectiveCustomerId, count(esiId) as EsiidTot from PICVO pic where pic.valid=1 group by pic.customer.prospectiveCustomerId";
            queryString = "select cust.prospectiveCustomerId, count(pic.esiId) from ProspectiveCustomerVO as cust left join cust.picVOs as pic with pic.valid=1 group by cust.prospectiveCustomerId";
            objQuery = objSession.createQuery(queryString);
            List objList = objQuery.list();
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                hmEsiid.put(innerRow[0], innerRow[1]);
            }
            logger.info("GOT TOTAL VALID ESIID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET TOTAL VALID ESIID", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET TOTAL VALID ESIID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hmEsiid;
    }
    
    public List getProspectivCust()
    {
        Session objSession = null;
        Criteria objCriteria = null;
        List objList = null;
        try
        {
            logger.info("GETTING PROSPECTIVE CUSTOMER");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ProspectiveCustomerVO.class);
            objList = objCriteria.list();
            logger.info("GOT PROSPECTIVE CUSTOMER");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET PROSPECTIVE CUSTOMER", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET PROSPECTIVE CUSTOMER", e);
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
    
    public LinkedHashMap getDealLeverDetails(int CMSId, int cdrStatId, int autoRun)
    {
        Session objSession = null;
        Query objQuery = null;
        String queryString = "";
        LinkedHashMap lhmProspect = new LinkedHashMap();
        try
        {
            logger.info("GETTING DEAL LEVER DETAILS BY CMS ID");
            objSession = HibernateUtil.getSession();
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
            }
            logger.info("GOT DEAL LEVER DETAILS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET DEAL LEVER DETAILS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET DEAL LEVER DETAILS", e);
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
        // System.out.println(new ScheduleDAO().getAutoRunCust());
        // System.out.println(new ScheduleDAO().getTotEsiidbyCust());
        // System.out.println(new ScheduleDAO().getTotValidEsiidbyCust());
         System.out.println(new ScheduleDAO().getTotValidEsiidbyCust());
    }
}


/*
*$Log: ScheduleDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/09/21 10:38:46  spandiyarajan
*schedule bug fixed.
*
*Revision 1.2  2007/09/04 07:05:18  sramasamy
*Log message is added for log file.
*
*Revision 1.1  2007/09/04 04:35:13  spandiyarajan
*schedule page and methods are reconstructed because of perfromance issue.
*
*
*/