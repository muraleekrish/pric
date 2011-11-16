/*
 * Created on Apr 8, 2007
 *
 * ClassName	:  	ProductsDAO.java
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
import com.savant.pricing.valueobjects.ProductsVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProductsDAO
{
    private static Logger logger = Logger.getLogger(ProductsDAO.class);
    
    public ProductsDAO()
    {
    }
    
    public HashMap getAllProducts(Filter filter, String sortBy, boolean ascending, int startIndex, int displayCount,boolean valid)
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
            logger.info("GETTING ALL PRODUCTS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ProductsVO.class);
            if(filter != null)
            {
                objCriteria.add(Restrictions.like(filter.getFieldName(),filter.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filter.getSpecialFunction())));
            }
            if(valid)
            {
            objCriteria.add(Restrictions.eq("valid",new Boolean(valid)));
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
            logger.info("GOT ALL PRODUCTS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL PRODUCTS", e);
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

    public ProductsVO getProduct( int productId ) 
    {
        Session objSession       = null;
        ProductsVO objProductsVO = null;
        
        try 
        {
            logger.info("GET PRODUCTS DETAILS FOR PRODUCT ID");
            objSession = HibernateUtil.getSession();
            objProductsVO = ( ProductsVO ) objSession.get( ProductsVO.class, new Integer( productId ) );
            logger.info("GOT PRODUCTS DETAILS FOR PRODUCT ID");
        } 
        catch ( HibernateException e ) 
        {
            logger.error("HIBERNATE EXCEPTION DURING GET PRODUCTS DETAILS FOR PRODUCT ID", e);
            e.printStackTrace();
        }
        finally 
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objProductsVO;
    }
    
    public boolean updateProduct( ProductsVO objProductsVO )
    {
        boolean updateResult = false;
        Session objSession   = null;
        
        try
        {
            logger.info("UPDATING PRODUCT DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update( objProductsVO );
            objSession.getTransaction().commit();
            updateResult = true;
            objSession.flush();
            logger.info("PRODUCT DETAILS ARE UPDATED");
        }
        catch( HibernateException e )
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE PRODUCT DETAILS", e);
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
        ProductsDAO objProductsDAO = new ProductsDAO();
        Filter obj = new Filter();
        obj.setFieldName("groupName");
        obj.setFieldValue("t");
        obj.setSpecialFunction(HibernateUtil.STARTS_WITH);
        HashMap hmgetAllRoles = objProductsDAO.getAllProducts(null,"groupId",true,0,10,true); 
        if(BuildConfig.DMODE)
            System.out.println("hmgetAllRoles :"+hmgetAllRoles);
    }
}

/*
*$Log: ProductsDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.9  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.8  2007/08/23 07:27:53  jnadesan
*imports organized
*
*Revision 1.7  2007/08/09 15:08:07  sramasamy
*Make Valid/invalid option provided
*
*Revision 1.6  2007/08/06 09:18:38  jnadesan
*product filter by valid
*
*Revision 1.5  2007/07/03 04:45:41  jnadesan
*products are taken if its valid
*
*Revision 1.4  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.3  2007/04/10 09:52:53  jnadesan
*import organized
*
*Revision 1.2  2007/04/08 11:05:36  rraman
*products list finished
*
*Revision 1.1  2007/04/08 08:22:31  kduraisamy
*initial commit.
*
*
*/