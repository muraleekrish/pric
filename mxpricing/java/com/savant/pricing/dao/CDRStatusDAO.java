/*
 * Created on Jan 30, 2007
 *
 * ClassName	:  	CDRStatusDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.CDRStatusVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CDRStatusDAO
{
    private static Logger logger = Logger.getLogger(CDRStatusDAO.class);

    public List getAllCDRStatus()
    {
        List objList = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL CDR STATUS");
            objSession = HibernateUtil.getSession();
            objList = objSession.createCriteria(CDRStatusVO.class).addOrder(Order.asc("cdrState")).list();
            logger.info("GOT ALL CDR STATUS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CDR STATUS", e);
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
    
    public String getCDRStatus(int prsCustID)
    {
        List objList = null;
        Session objSession = null;
        String status = "";
        try
        {
            logger.info("GETTING  CDR STATUS");
            objSession = HibernateUtil.getSession();
            String query = "";
            query = "select CDR_State from dbo.PRC_CDR_Status where CDR_State_ID ="+prsCustID;
            Query objQuery = objSession.createSQLQuery(query);
            Iterator itr = objQuery.list().iterator();
            while(itr.hasNext())
            {
                status = String.valueOf(itr.next());
                
            }
            logger.info("GOT  CDR STATUS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CDR STATUS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return status;
    }

    public static void main(String args[])
    {
        if(BuildConfig.DMODE)
            System.out.println("size:"+new CDRStatusDAO().getAllCDRStatus().size());
    }
}

/*
*$Log: CDRStatusDAO.java,v $
*Revision 1.2  2007/12/12 08:55:40  tannamalai
*new method added to get cdr state
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
*Revision 1.5  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.4  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.3  2007/04/25 06:37:45  spandiyarajan
*list the details in sort order
*
*Revision 1.2  2007/01/31 05:10:15  kduraisamy
*unwanted imports removed.
*
*Revision 1.1  2007/01/30 13:10:26  kduraisamy
*initial commit.
*
*
*/