/*
 * Created on Nov 20, 2007
 *
 * ClassName	:  	MarketCommentryDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.savant.pricing.calculation.valueobjects.MarketCommentryVO;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.PricingException;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MarketCommentryDAO
{
    private static Logger logger = Logger.getLogger(MarketCommentryDAO.class);
    
    public List getAllMarketCommentries()
    {
        List objList = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL MARKET COMMENTRIES");
            objSession = HibernateUtil.getSession();
            objList = objSession.createCriteria(MarketCommentryVO.class).addOrder(Order.desc("marketDate")).list();
            logger.info("GOT ALL MARKET COMMENTRIES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL MARKET COMMENTRIES", e);
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
    
    public boolean addMrktCommtry(MarketCommentryVO commentryVO) 
    {
        boolean addResult = false;
        Session objSession = null;
        try
        {
            logger.info("ADDING A NEW MARKET COMMENTRY");
            objSession = HibernateUtil.getSession();
            // To save the user
            objSession.beginTransaction();
            objSession.save(commentryVO);
            objSession.getTransaction().commit();
            addResult= true;
            logger.info("NEW MARKET COMMENTRY ADDED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD A MARKET COMMENTRY", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
            throw new HibernateException("MARKET COMMENTRY does not saved.");
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return addResult;
    }
    
    public boolean updateMrktCmmtry( MarketCommentryVO commentryVO)
    {
        boolean updateResult = false;
        Session objSession   = null;
        try
        {
            logger.info("UPDATING MARKETCOMMENTRY DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update(commentryVO);
            objSession.getTransaction().commit();
            updateResult = true;
            objSession.flush();
            logger.info("MARKETCOMMENTRY DETAILS ARE UPDATED");
        }
        catch( HibernateException e )
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE MARKETCOMMENTRY DETAILS", e);
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
    
    public boolean deleteMrktCmmtry(int mrktId)
    {
        boolean totdeleted = false;
        Session objSession = null;
       int  noOfRowsAffected = 0;
        try
        {
            logger.info("DELETE MARKETCOMMENTRY STARTED");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            noOfRowsAffected = objSession.createQuery("delete from MarketCommentryVO as commentryVO where commentryVO.commentryId = ?").setInteger(0, mrktId).executeUpdate();
            logger.info("No Of Rows Affecteds in PICVO "+noOfRowsAffected);
            objSession.getTransaction().commit();
            totdeleted = true;
            logger.info("MARKETCOMMENTRY DELETED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DELETE MARKETCOMMENTRY", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return totdeleted; 
    }
    public MarketCommentryVO getMarketCommentryVOByDate(Date marketDate)
    {
        Session objSession = null;
        Criteria objCriteria = null;
        MarketCommentryVO objMarketCommentryVO = null;
        try
        {
            logger.info("GETTING MARKETCOMMENTRY BY ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MarketCommentryVO.class).add(Restrictions.eq("marketDate", marketDate));
            objMarketCommentryVO = (MarketCommentryVO) objCriteria.uniqueResult();
            logger.info("GOT MARKETCOMMENTRY BY ID");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET MARKETCOMMENTRY BY ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objMarketCommentryVO;
    }
    
    
    public MarketCommentryVO getMarketCommentryVO(int commentID)
    {
        Session objSession = null;
        Criteria objCriteria = null;
        MarketCommentryVO objMarketCommentryVO = null;
        try
        {
            logger.info("GETTING MARKETCOMMENTRY BY ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MarketCommentryVO.class).add(Restrictions.eq("commentryId", new Integer(commentID)));
            objMarketCommentryVO = (MarketCommentryVO) objCriteria.uniqueResult();
            logger.info("GOT MARKETCOMMENTRY BY ID");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET MARKETCOMMENTRY BY ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objMarketCommentryVO;
    }
    
    public int checkDate(String str)
    {	
        int result = 0;
        String retVal = "";
        Session objSession = null;
        try
        {
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            String query = "";
            query = "Select count(*)as tot from prc_daily_market_commentry where market_date = '"+str+"'";
            System.out.println("query :" + query);
            Query objQuery = objSession.createSQLQuery(query);
            Iterator itr = objQuery.list().iterator();
            while(itr.hasNext())
            {
                retVal = String.valueOf(itr.next());
            }
               result = Integer.parseInt(retVal) ;
               objSession.getTransaction().commit();
        }
        catch(Exception e)
        {
            objSession.getTransaction().rollback();
            e.printStackTrace();
        }
        finally
        {
            objSession.close();
        }
        return result;
    }
    
    /**
     * 
     */
      public static void main(String[] args)
    {
        MarketCommentryDAO commentryDAO = new MarketCommentryDAO();
        int s = commentryDAO.checkDate("2007-11-22");
        System.out.println("*********** " + s);

    }
    
}

/*
*$Log: MarketCommentryDAO.java,v $
*Revision 1.2  2008/02/14 05:43:56  tannamalai
*pagination done for price quote page
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.4  2007/11/29 10:19:46  tannamalai
*add validation done
*
*Revision 1.3  2007/11/28 13:04:48  jnadesan
*method added to get market commentry by date
*
*Revision 1.2  2007/11/23 05:29:12  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/11/21 05:18:31  tannamalai
**** empty log message ***
*
*
*/