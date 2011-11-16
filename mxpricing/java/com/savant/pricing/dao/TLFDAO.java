/*
 * Created on Apr 9, 2007
 *
 * ClassName	:  	TLFDAO.java
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.savant.pricing.calculation.valueobjects.SeasonsVO;
import com.savant.pricing.calculation.valueobjects.TLFVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.LoadPrerequisites;
import com.savant.pricing.transferobjects.OnPkOffPkDetails;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TLFDAO implements LoadPrerequisites
{
    private static Logger logger = Logger.getLogger(TLFDAO.class);
    
    public TLFDAO()
    {
    }
    
    public List getAllTlf()
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL TLF");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(TLFVO.class);
            objList = objCriteria.list();
            logger.info("GOT ALL TLF");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE ECEPTION DURING GET ALL TLF", e);
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
    
    public boolean updateTlf(List objTLFVOs)
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
            logger.info("UPDATING TLF DETAILS"); 
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            Iterator itr = objTLFVOs.iterator();
            while(itr.hasNext())
            {
                TLFVO objTLFVO = (TLFVO)itr.next();
                objSession.update(objTLFVO);
            }
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("TLF DETAILS ARE UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE TLF DETAILS", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
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
    
    public HashMap getAllTLF()
    {
        HashMap hmTlf = new HashMap();
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL TLF DETAILS");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createSQLQuery("select Mnth,OnPeak_Loss,OffPeak_Loss from PRC_Season_Mnth A,PRC_TLF B WHERE A.Season_id = b.season_id order by mnth");
            Iterator itr = objQuery.list().iterator();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                OnPkOffPkDetails objOnPkOffPkDetails = new OnPkOffPkDetails();
                objOnPkOffPkDetails.setOnPeakLoss(((Double)innerRow[1]).floatValue());
                objOnPkOffPkDetails.setOffPeakLoss(((Double)innerRow[2]).floatValue());
                hmTlf.put(innerRow[0],objOnPkOffPkDetails);
            }
            logger.info("GOT ALL TLF DETAILS");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL TLF DETAILS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hmTlf;
    }
    
    public boolean reload()
    {
        TLFDAO objTLFDAO = new TLFDAO();
        HibernateUtil.hmTLF = objTLFDAO.getAllTLF();
        return true;
    }
    
   
}

/*
*$Log: TLFDAO.java,v $
*Revision 1.2  2008/04/24 05:56:22  tannamalai
*reload method added
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
*Revision 1.8  2007/09/04 07:05:18  sramasamy
*Log message is added for log file.
*
*Revision 1.7  2007/07/31 11:40:08  kduraisamy
*objsession properly closed or calling procedure properly done.
*
*Revision 1.6  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.5  2007/06/12 05:18:46  kduraisamy
*for sqlserver 2005 data types changed.
*
*Revision 1.4  2007/05/02 08:14:28  spandiyarajan
*removed unwanted imports statements
*
*Revision 1.3  2007/05/02 06:08:25  kduraisamy
*TLF update method added.
*
*Revision 1.2  2007/04/17 13:49:01  kduraisamy
*price run performance took place.
*
*Revision 1.1  2007/04/09 09:06:41  kduraisamy
*getAllTlf() added.
*
*
*/