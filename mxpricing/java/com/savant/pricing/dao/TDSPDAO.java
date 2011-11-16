/*
 * Created on Feb 2, 2007
 *
 * ClassName	:  	TDSPDAO.java
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

import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.TDSPVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TDSPDAO
{
    private static Logger logger = Logger.getLogger(TDSPDAO.class);
    
    public List getAllTDSP()
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
            logger.info("GETTING ALL TDSP");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(TDSPVO.class).add(Restrictions.eq("valid",new Boolean(true)));
            objCriteria.addOrder(Order.asc("tdspName"));
            objList = objCriteria.list();
            logger.info("GOT ALL TDSP");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL TDSP", e);
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
    
    public HashMap getAllTDSP(Filter[] filter, String sortBy, boolean ascending, int startIndex, int displayCount)
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
            logger.info("GETTING ALL TDSP");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(TDSPVO.class).add(Restrictions.eq("valid",new Boolean(true)));
            if(filter != null)
            {
                for(int i=0;i < filter.length;i++)
                {
                    objCriteria.add(Restrictions.like(filter[i].getFieldName(),filter[i].getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filter[i].getSpecialFunction())));
                }
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
            hmResult.put("TotalRecordCount", totRecordCount);
            hmResult.put("Records", objList);
            logger.info("GOT ALL TDSP");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL TDSP", e);
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
    
    public HashMap getAllTDSPList( Filter[] filter, String sortBy, boolean ascending, int startIndex, int displayCount )
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria   = null;
        Session objSession     = null;
        Integer totRecordCount = null;
        List objList           = null;
        HashMap hmResult       = new HashMap();
       
        try
        {
            logger.info("GETTING ALL TDSP LIST");
            objSession  = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(TDSPVO.class);
            if( filter != null )
            {
                for( int i=0; i < filter.length; i++ )
                {
                    objCriteria.add( Restrictions.like( filter[i].getFieldName(), filter[i].getFieldValue(),( MatchMode ) HibernateUtil.htMatchCase.get( filter[i].getSpecialFunction() ) ) );
                }
            }
            totRecordCount = new Integer( objCriteria.list().size() );
            
            if( ascending )
            {
                objCriteria.addOrder( Order.asc( sortBy ) );
            }
            else
            {
                objCriteria.addOrder( Order.desc( sortBy ) );
            }
            objCriteria.setFirstResult( startIndex );
            objCriteria.setMaxResults( displayCount );
            objList = objCriteria.list();
            hmResult.put( "TotalRecordCount", totRecordCount );
            hmResult.put( "Records", objList );
            logger.info("GOT ALL TDSP LIST");
        }
        catch( HibernateException e )
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL TDSP LIST", e); 
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
    
    public TDSPVO getTDSP(int tdspId)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria = null;
        Session objSession = null;
        TDSPVO objTDSPVO = null;
        try
        {
            logger.info("GETTING TDSP BY TDSP ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(TDSPVO.class).add(Restrictions.eq("tdspIdentifier", new Integer(tdspId)));
            objTDSPVO = (TDSPVO)objCriteria.uniqueResult();
            logger.info("GOT TDSP BY TDSP ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET TDSP BY TDSP ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objTDSPVO;
    }
    public int getCongestionZoneId(int tdspId)
    {
        TDSPVO objTDSPVO = getTDSP(tdspId);
        int congestionZone = 0;
        if(objTDSPVO!=null)
            congestionZone = objTDSPVO.getCongestionZone().getCongestionZoneId();
        return congestionZone;
    }
    public String getCongestionZoneName(int tdspId)
    {
        TDSPVO objTDSPVO = getTDSP(tdspId);
        String congestionZone = "";
        if(objTDSPVO!=null)
            congestionZone = objTDSPVO.getCongestionZone().getCongestionZone();
        return congestionZone;
    
    }
    public boolean updateTDSP( TDSPVO objTDSPVO )
    {
        boolean updateResult = false;
        Session objSession   = null;
        
        try
        {
            logger.info("UPDATING TDSP DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update( objTDSPVO );
            objSession.getTransaction().commit();
            updateResult = true;
            objSession.flush();
            logger.info("TDSP DETAILS IS UPDATED");
        }
        catch( HibernateException e )
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE TDSP DETAILS", e);
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
    }
}

/*
*$Log: TDSPDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.20  2007/09/06 12:36:54  jnadesan
*DWR Concept is implemented
*
*Revision 1.19  2007/09/04 07:05:18  sramasamy
*Log message is added for log file.
*
*Revision 1.18  2007/08/23 07:27:53  jnadesan
*imports organized
*
*Revision 1.17  2007/08/09 15:08:07  sramasamy
*Make Valid/invalid option provided
*
*Revision 1.16  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.15  2007/04/20 07:03:21  kduraisamy
*valid tdsp taken out.
*
*Revision 1.14  2007/04/20 06:29:12  kduraisamy
*valid tdsp taken out.
*
*Revision 1.13  2007/04/17 04:49:39  kduraisamy
*rateCodes set removed from TDSP.
*
*Revision 1.12  2007/04/12 13:57:51  kduraisamy
*unwanted println commented.
*
*Revision 1.11  2007/04/12 11:57:24  spandiyarajan
**** empty log message ***
*
*Revision 1.10  2007/04/12 07:22:08  kduraisamy
*getTDSP() added.
*
*Revision 1.9  2007/04/09 05:22:27  spandiyarajan
*added asc for list
*
*Revision 1.8  2007/03/29 10:33:12  rraman
*print statements added
*
*Revision 1.7  2007/03/28 13:15:02  rraman
*getAllTDSP return type changed
*
*Revision 1.6  2007/03/26 07:16:59  kduraisamy
*filter for TDSP added.
*
*Revision 1.5  2007/03/22 08:16:42  jnadesan
*blockid taken by name
*
*Revision 1.4  2007/03/14 11:45:21  kduraisamy
*imports organized.
*
*Revision 1.3  2007/03/08 16:32:44  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.2  2007/02/09 11:57:52  kduraisamy
*pricing core algorithm almost finished.
*
*Revision 1.1  2007/02/02 06:55:28  kduraisamy
*initial commit.
*
*
*/