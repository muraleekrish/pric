/*
 * Created on Apr 6, 2007
 *
 * ClassName	:  	LossFactorLookupDAO.java
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.savant.pricing.calculation.valueobjects.DLFCodeVO;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.LoadPrerequisites;
import com.savant.pricing.securityadmin.dao.UserDAO;
import com.savant.pricing.valueobjects.TDSPVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DLFCodeDAO implements LoadPrerequisites
{
    private static Logger logger = Logger.getLogger(UserDAO.class);
    private static List dlfCodeVOS = null;
    
    public static void getAllDLFCodeIdentifiers()
    {
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL DLF CODE ID");
            dlfCodeVOS = new ArrayList();
            objSession = HibernateUtil.getSession();
            dlfCodeVOS = objSession.createCriteria(DLFCodeVO.class).list();
            logger.info("GOT ALL DLF CODE ID");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL DLF CODE ID", e);
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
    public List getAllDLFCodes()
    {
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL DLF CODES");
            objSession = HibernateUtil.getSession();
            objList = objSession.createQuery("select distinct dlfCodes.dflName from DLFCodeVO as dlfCodes order by dlfCodes.dflName").list();
            logger.info("GOT ALL DLF CODES");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL DLF CODES", e);
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
    
    public HashMap getAllDLFCodes(Filter filter, int tdspId, String sortBy, boolean ascending, int startIndex, int displayCount)
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
            logger.info("GETTING ALL DLF CODES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(DLFCodeVO.class);
            if(filter != null)
            {
                objCriteria.add(Restrictions.like(filter.getFieldName(),filter.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filter.getSpecialFunction())));
            }
            if(tdspId != 0)
            {
                objCriteria.add(Restrictions.eq("tdsp.tdspIdentifier", new Integer(tdspId)));
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
            logger.info("GOT ALL DLF CODES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL DLF CODES", e);
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
    
    public static int getDLFCodeId(TDSPVO tdsp, String dlfCode)
    {
        logger.info("GETTING DLF CODE ID BY TDSP ID AND DLF CODE");
        Iterator itr = dlfCodeVOS.iterator();
        int dlfCodeId = 0;
        while(itr.hasNext())
        {
            DLFCodeVO objDLFCodeVO = (DLFCodeVO)itr.next();
            if(objDLFCodeVO.getTdsp().getTdspIdentifier() == tdsp.getTdspIdentifier() && objDLFCodeVO.getDflName().equalsIgnoreCase(dlfCode))
            {
                dlfCodeId = objDLFCodeVO.getDlfCodeIdentifier();
            }
        }
        logger.info("GOT DLF CODE ID BY TDSP ID AND DLF CODE");
        return dlfCodeId;
    }
    public Collection getAllDLFCodes(int tdspId)
    {
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL DLF CODES BY TDSP ID");
            objSession = HibernateUtil.getSession();
            objList = objSession.createCriteria(DLFCodeVO.class).add(Restrictions.like("tdsp.tdspIdentifier", new Integer(tdspId))).list();
            logger.info("GOT ALL DLF CODES BY TDSP ID");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL DLF CODES BY TDSP ID", e);
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
    
    public DLFCodeVO getLossFactorCode( int lossFactorId ) 
    {
        Session objSession     = null;
        DLFCodeVO objDLFCodeVO = null;
        
        try 
        {
            logger.info("GETTING LOSS FACTOR CODE BY LOSS FACTOR ID");
            objSession = HibernateUtil.getSession();
            objDLFCodeVO = ( DLFCodeVO ) objSession.get( DLFCodeVO.class, new Integer( lossFactorId ) );
            logger.info("GOT LOSS FACTOR CODE BY LOSS FACTOR ID");
        } 
        catch ( HibernateException e ) 
        {
            logger.error("HIBERNATE EXCEPTION DURING GET LOSS FACTOR CODE", e);
            e.printStackTrace();
        }
        finally 
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objDLFCodeVO;
    }
    
    public boolean updateLossFactorCode( DLFCodeVO objDLFCodeVO )
    {
        boolean updateResult = false;
        Session objSession   = null;
        
        try
        {
            logger.info("UPDATING LOSS FACTOR CODE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update( objDLFCodeVO );
            objSession.getTransaction().commit();
            updateResult = true;
            objSession.flush();
            logger.info("LOSS FACTOR CODE IS UPDATED");
        }
        catch( HibernateException e )
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE LOSS FACTOR CODE", e);
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
        DLFCodeDAO.getAllDLFCodeIdentifiers();
        return true;
    }
}

/*
*$Log: DLFCodeDAO.java,v $
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
*Revision 1.11  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.10  2007/08/23 07:27:53  jnadesan
*imports organized
*
*Revision 1.9  2007/08/09 15:06:35  sramasamy
*Make Valid/invalid option provided
*
*Revision 1.8  2007/07/05 14:00:21  kduraisamy
*Prerequisites object re initialized.
*
*Revision 1.7  2007/04/17 15:42:58  kduraisamy
*price run performance took place.
*
*Revision 1.6  2007/04/17 14:49:33  spandiyarajan
*DLF code chaged after the review
*
*Revision 1.5  2007/04/17 06:25:17  kduraisamy
*dlfcode set removed from TDSP.
*
*Revision 1.4  2007/04/12 06:31:21  spandiyarajan
*DLF page initially committed
*
*Revision 1.3  2007/04/09 10:18:38  kduraisamy
*Tdsp wise dlf and values added.
*
*Revision 1.2  2007/04/08 15:05:05  kduraisamy
*filter method for DLF codes added.
*
*Revision 1.1  2007/04/06 12:32:45  kduraisamy
*TLF AND DLF COMPLETED.
*
*
*/