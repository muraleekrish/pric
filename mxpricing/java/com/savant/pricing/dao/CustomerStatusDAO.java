/*
 * Created on Jan 29, 2007
 *
 * ClassName	:  	CustomerStatusDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CustomerStatusDAO
{
    private static Logger logger = Logger.getLogger(CustomerStatusDAO.class);
    
    public List getAllCustomerStatus()
    {
        List objList = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL CUSTOMER STATUS");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("From CustomerStatusVO as customerStatus where customerStatus.valid = ? order by customerStatus" ).setBoolean(0,true);
            objList = objQuery.list();
            logger.info("GOT ALL CUSTOMER STATUS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CUSTOMER STATUS", e);
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
    
    public List getAllCustStatforProspect()
    {
        List objList = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL CUSTOMER STATUS");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("From CustomerStatusVO as customerStatus where customerStatus.valid = ? and customerStatus.customerStatusId != 11 order by customerStatus" ).setBoolean(0,true);
            objList = objQuery.list();
            logger.info("GOT ALL CUSTOMER STATUS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CUSTOMER STATUS", e);
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

    public static void main(String args[])
    {
        if(BuildConfig.DMODE)
            System.out.println("size:"+(new CustomerStatusDAO().getAllCustomerStatus().size()));
    }
}

/*
*$Log: CustomerStatusDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.10  2007/10/25 10:10:58  spandiyarajan
*getAllCustStatforProspect() method added.
*
*Revision 1.9  2007/09/04 05:23:55  spandiyarajan
*removed unwanted imports
*
*Revision 1.8  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.7  2007/08/02 06:31:07  spandiyarajan
*getAllCustomerStatus Query changed
*
*Revision 1.6  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.5  2007/04/25 06:37:45  spandiyarajan
*list the details in sort order
*
*Revision 1.4  2007/01/30 13:10:26  kduraisamy
*initial commit.
*
*Revision 1.3  2007/01/30 10:14:52  kduraisamy
*array changed to list.
*
*Revision 1.2  2007/01/30 09:03:54  kduraisamy
*unwanted imports removed.
*
*Revision 1.1  2007/01/29 11:41:14  kduraisamy
*customerStatusDAO created and getAllCustomerStatus() moved from ProspectiveCustomerDAO to CustomerStatusDAO.
*
*
*/