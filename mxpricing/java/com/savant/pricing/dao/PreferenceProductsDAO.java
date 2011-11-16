/*
 * Created on Apr 18, 2007
 *
 * ClassName	:  	PreferenceTermsDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerProductsVO;
import com.savant.pricing.common.HibernateUtil;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PreferenceProductsDAO
{
   private static Logger logger = Logger.getLogger(PreferenceProductsDAO.class);
   		
   public Collection getAllPreferenceProducts(int priceRunCustomerRefId)
   {
        List objList = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL PREFERENCE PRODUCTS BY PRICE RUN CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            Criteria objBase = objSession.createCriteria(PriceRunCustomerProductsVO.class);
            objBase.add(Restrictions.like("priceRunCustomer.priceRunCustomerRefId", new Integer(priceRunCustomerRefId)));
            //Criteria objProduct = objBase.createCriteria("product").addOrder(Order.asc("productName"));
            objList = objBase.list();
            logger.info("GOT ALL PREFERENCE PRODUCTS BY PRICE RUN CUSTOMER ID");
        }
        catch(Exception e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL PREFERENCE PRODUCTS BY PRICE RUN CUSTOMER ID", e);
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
   
   public HashMap getAllPreferenceProductsByCust(int prospectId)
   {
       HashMap hmResult = new HashMap();
       Session objSession = null;
       List lst = null;
       try
       {
           logger.info("GETTING ALL PREFERENCE PRODUCTS BY PROSPECT ID");
           objSession = HibernateUtil.getSession();
           Query objQuery = objSession.createQuery( "select distinct custPro.product.productIdentifier, prod.productName "+
                   "from PriceRunCustomerProductsVO as custPro," +
                   "ProductsVO as prod," +
                   "PriceRunCustomerVO as runcust " +
                   "where runcust.priceRunCustomerRefId = custPro.priceRunCustomer.priceRunCustomerRefId " +
                   "and prod.productIdentifier = custPro.product.productIdentifier " +
                   "and runcust.prospectiveCustomer.prospectiveCustomerId = ? order by prod.productName");
           objQuery.setInteger(0,prospectId);
           lst = objQuery.list();
           Iterator itr = lst.iterator();
           while(itr.hasNext())
           {
               Object[] result =(Object[]) itr.next(); 
               hmResult.put(result[0],result[1]);
           }
           logger.info("GOT ALL PREFERENCE PRODUCTS BY PROSPECT ID");
       }
       catch(HibernateException e)
       {
           logger.error("HIBERNATE EXCEPTION DURING GET ALL PREFERENCE PRODUCTS BY PROSPECT ID", e);
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
   
   public PriceRunCustomerProductsVO getProspectiveCustomerPreferenceByProduct(int pricerunCustId, int productId)
   {
       Session objSession = null;
       PriceRunCustomerProductsVO objCustomerPreferenceProductsVO = null;
       try
       {
           logger.info("GETTING PROSPECTIVE CUSTOMER PREFERENCE BY PRODUCT ID");
           objSession = HibernateUtil.getSession();
           objCustomerPreferenceProductsVO = (PriceRunCustomerProductsVO)objSession.createCriteria(PriceRunCustomerProductsVO.class).add(Restrictions.eq("priceRunCustomer.priceRunCustomerRefId", new Integer(pricerunCustId))).add(Restrictions.eq("product.productIdentifier",new Integer(productId))).uniqueResult();
           logger.info("GOT PROSPECTIVE CUSTOMER PREFERENCE BY PRODUCT ID");
       }
       catch(HibernateException e)
       {
           logger.error("HIBERNATE EXCEPTION DURING GET PROSPECTIVE CUSTOMER PREFERENCE BY PRODUCT ID", e);
           e.printStackTrace();
       }
       finally
       {
           if(objSession != null)
           {
               objSession.close();
           }
       }
       return objCustomerPreferenceProductsVO;
   }
   
   public boolean checkOtherThanEPP(int pricerunCustId)
   {
       Session objSession = null;
       List objList = null;
       boolean otherProductsAvailable = false;
       try
       {
           logger.info("CHECKING OTHER THAN EPP BY PRICE RUN CUSTOMER ID");
           objSession = HibernateUtil.getSession();
           //Product Id hard Coded for EPP.
           objList = objSession.createCriteria(PriceRunCustomerProductsVO.class).add(Restrictions.eq("priceRunCustomer.priceRunCustomerRefId", new Integer(pricerunCustId))).add(Restrictions.ne("product.productIdentifier",new Integer(5))).list();
           Iterator iteProducts = objList.iterator();
           if(iteProducts.hasNext())
           {
               otherProductsAvailable = true;
               logger.info("OTHER PRODUCTS OTHER THAN EPP ARE AVAILABLE");
           }
           else
           {
               logger.info("OTHER PRODUCTS NOT AVAILABLE");
           }
       }
       catch(HibernateException e)
       {
           logger.error("HIBERNATE EXCEPTION DURING CHECK OTHER PRODUCTS OTHER THAN EPP", e);
           e.printStackTrace();
       }
       finally
       {
           if(objSession != null)
           {
               objSession.close();
           }
       }
       return otherProductsAvailable;
   }
   
   public PreferenceProductsDAO()
   {
        	
   }
   
   public static void main(String[] args) 
   {
            System.out.println(new PreferenceProductsDAO().getAllPreferenceProductsByCust(485));
   }
}

/*
*$Log: PreferenceProductsDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.8  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.7  2007/08/23 07:27:53  jnadesan
*imports organized
*
*Revision 1.6  2007/08/10 10:22:21  jnadesan
*products are taken by run wise
*
*Revision 1.5  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.4  2007/06/05 11:01:54  jnadesan
*methods are changed according priceruncustomerid
*
*Revision 1.3  2007/06/05 06:20:02  jnadesan
*methodformat changed
*
*Revision 1.2  2007/06/04 12:11:20  kduraisamy
*priceRunCustomer preferences added.
*
*Revision 1.1  2007/06/04 11:49:43  kduraisamy
*priceRunCustomer preferences added.
*
*Revision 1.1  2007/04/18 06:27:22  kduraisamy
*set removed.
*
*
*/