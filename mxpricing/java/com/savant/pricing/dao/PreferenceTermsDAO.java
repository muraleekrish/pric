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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerTermsVO;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.transferobjects.TermDetails;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PreferenceTermsDAO
{
    private static Logger logger = Logger.getLogger(PreferenceTermsDAO.class);
    
    public Collection getAllPreferenceTerms(int priceRunCustomerRefId)
    {
        List objList = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL PREFERENCE TERMS BY PRICE RUN CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            objList = objSession.createCriteria(PriceRunCustomerTermsVO.class).add(Restrictions.eq("priceRunCustomer.priceRunCustomerRefId", new Integer(priceRunCustomerRefId))).addOrder(Order.asc("term")).list();
            logger.info("GOT ALL PREFERENCE TERMS BY PRICE RUN CUSTOMER ID");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL PREFERENCE TERMS BY PRICE RUN CUSTOMER ID", e);
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
    
    public Collection getAllTermsByCust(int prsCustId)
    {
        List objList = null;
        List objResult = new ArrayList();
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL TERMS BY CUSTOMER BY CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            objList = objSession.createQuery("select distinct(termsVO.term) as term from PriceRunCustomerTermsVO as termsVO, PriceRunCustomerVO as priceRunVO where priceRunVO.prospectiveCustomer.prospectiveCustomerId = ? and priceRunVO.priceRunCustomerRefId = termsVO.priceRunCustomer.priceRunCustomerRefId order by termsVO.term").setInteger(0, prsCustId).list();
            Iterator itr = objList.iterator();
            while (itr.hasNext())
            {
                Object objVal = itr.next();
                TermDetails objTermDetails = new TermDetails();
                objTermDetails.setTerm(((Integer)objVal).intValue());
                objResult.add(objTermDetails);
            }
            logger.info("GOT ALL TERMS BY CUSTOMER BY CUSTOMER ID");
        }
        catch(Exception e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL TERMS BY CUSTOMER BY CUSTOMER ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objResult;
    }
    
    public PreferenceTermsDAO()
    {
    }
    
    public static void main(String[] args)
    {
        PreferenceTermsDAO objPreferenceTermsDAO = new PreferenceTermsDAO();
        System.out.println(objPreferenceTermsDAO.getAllTermsByCust(485));
    }
}

/*
*$Log: PreferenceTermsDAO.java,v $
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
*Revision 1.7  2007/08/10 15:04:05  kduraisamy
*terms order by added.
*
*Revision 1.6  2007/08/07 10:01:33  spandiyarajan
*method name changed
*
*Revision 1.5  2007/08/06 11:43:20  kduraisamy
*query for fetching priceRun Terms.
*
*Revision 1.4  2007/08/06 11:19:30  jnadesan
*To solve error
*
*Revision 1.3  2007/06/04 12:11:20  kduraisamy
*priceRunCustomer preferences added.
*
*Revision 1.2  2007/06/04 11:49:43  kduraisamy
*priceRunCustomer preferences added.
*
*Revision 1.1  2007/04/18 06:27:22  kduraisamy
*set removed.
*
*
*/