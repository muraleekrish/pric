/*
 * Created on Apr 6, 2007
 *
 * ClassName	:  	DLFDAO.java
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
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.calculation.valueobjects.DLFVO;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.LoadPrerequisites;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DLFDAO implements LoadPrerequisites
{
    private static Logger logger = Logger.getLogger(DLFDAO.class);
    private static List dlfVOS = null;
    
    public static void getAllDLF()
    {
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL DLF");
            dlfVOS = new ArrayList();
            objSession = HibernateUtil.getSession();
            dlfVOS = objSession.createCriteria(DLFVO.class).list();
            logger.info("GOT ALL DLF");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL DLF", e);
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
    
    public static float getDLF(int dlfCodeId)
    {
        logger.info("GETTING DLF BY DLF CODE ID");
        Iterator itr = dlfVOS.iterator();
        float dlf = 0;
        while(itr.hasNext())
        {
            DLFVO objDLFVO = (DLFVO)itr.next();
            if(objDLFVO.getDlfCode().getDlfCodeIdentifier() == dlfCodeId)
            {
                dlf = objDLFVO.getDlf(); 
            }
        }
        logger.info("GOT DLF BY DLF CODE ID");
        return dlf;
    }
    
    public DLFVO getDLFVO(int dlfCodeId)
    {
        Session objSession = null;
        DLFVO objDLFVO = null;
        try
        {
            logger.info("GETTING DLF DETAILS BY DLF CODE ID");
            objSession = HibernateUtil.getSession();
            objDLFVO = (DLFVO)objSession.createCriteria(DLFVO.class).add(Restrictions.like("dlfCode.dlfCodeIdentifier", new Integer(dlfCodeId))).uniqueResult();
            logger.info("GOT DLF DETAILS BY DLF CODE ID");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET DLF DETAILS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objDLFVO;
    }
    
    public boolean updateDlf(List objDLFVOs)
    {
        Session objSession = null;
        boolean updateResult = false;
        try
        {
            logger.info("UPDATING DLF");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            Iterator itr = objDLFVOs.iterator();
            while(itr.hasNext())
            {
                DLFVO objDLFVO = (DLFVO)itr.next();
                objSession.update(objDLFVO);
            }
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("DLF IS UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE DLF", e);
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
    
    public boolean reload()
    {
        DLFDAO.getAllDLF();
        return true;
    }
    
}

/*
*$Log: DLFDAO.java,v $
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
*Revision 1.8  2007/09/04 05:23:55  spandiyarajan
*removed unwanted imports
*
*Revision 1.7  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.6  2007/07/31 12:28:42  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.5  2007/07/05 14:00:21  kduraisamy
*Prerequisites object re initialized.
*
*Revision 1.4  2007/06/07 08:40:41  spandiyarajan
*added update function for dlf
*
*Revision 1.3  2007/04/17 14:49:33  spandiyarajan
*DLF code chaged after the review
*
*Revision 1.2  2007/04/17 06:25:17  kduraisamy
*dlfcode set removed from TDSP.
*
*Revision 1.1  2007/04/06 12:32:45  kduraisamy
*TLF AND DLF COMPLETED.
*
*
*/