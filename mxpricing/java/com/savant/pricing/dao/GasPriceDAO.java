/*
 * Created on Mar 7, 2007
 *
 * ClassName	:  	ForwardCurveBlockDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.savant.pricing.calculation.valueobjects.GasPriceVO;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.LoadPrerequisites;
import com.savant.pricing.valueobjects.NGFutureTerminateVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GasPriceDAO implements LoadPrerequisites
{
    private static Logger logger = Logger.getLogger(GasPriceDAO.class);
    private static List ngFutureTerminationVOS = null;
    
    public static void getAllNGFutureTerminations()
    {
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL NG FUTURE TERMINATIONS");
            objSession = HibernateUtil.getSession();
            ngFutureTerminationVOS = new ArrayList();
            ngFutureTerminationVOS = objSession.createCriteria(NGFutureTerminateVO.class).addOrder(Order.asc("deliveryMonth")).list();
            logger.info("GOT ALL NG FUTURE TERMINATIONS");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL NG FUTURE TERMINATIONS", e);
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
    
    public static Date getNGTermination(Date deliveryMonth)
    {
        logger.info("GETTING NG TERMINATION BY DELIVERY MONTH");
        Iterator itr = ngFutureTerminationVOS.iterator();
        Date noticeDate = null;
        while(itr.hasNext())
        {
            NGFutureTerminateVO objNGFutureTerminateVO = (NGFutureTerminateVO)itr.next(); 
            if(objNGFutureTerminateVO.getDeliveryMonth().equals(deliveryMonth))
            {
                noticeDate = objNGFutureTerminateVO.getNoticeDate();
            }
        }
        logger.info("GOT NG TERMINATION BY DELIVERY MONTH");
        return noticeDate;
    }
    
    public boolean reload()
    {
        GasPriceDAO.getAllNGFutureTerminations();
        return true;
    }
    public static Date getContractStartMonth()
    {
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date contractStartMonth = null;
        try
        {
            logger.info("GETTING CONTRACT START MONTH");
            Date currentDate = sdf.parse(sdf.format(gc.getTime()));
            System.out.println("current"+currentDate);
            gc.setTime(currentDate);
            gc.add(Calendar.MONTH,1);
            gc.set(Calendar.DATE,1);
            System.out.println("time"+gc.getTime());
            if(currentDate.compareTo(GasPriceDAO.getNGTermination(gc.getTime()))<0)
            {
                contractStartMonth = gc.getTime();
                logger.info("y");
                
            }
            else
            {
                gc.add(Calendar.MONTH,1);
                contractStartMonth = gc.getTime();
                logger.info("n");
                System.out.println("time"+contractStartMonth);
            }
            logger.info("GOT CONTRACT START MONTH");
        }
        catch (ParseException e)
        {
            logger.error("PARSE EXCEPTION DURING GET CONTRACT START DATE", e);
            e.printStackTrace();
        }
        return contractStartMonth;
    }
    
    public List getAllGasPrices()
    {
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL GAS PRICES");
            objSession = HibernateUtil.getSession();
            objList = objSession.createCriteria(GasPriceVO.class).addOrder(Order.asc("monthYear")).list();
            logger.info("GOT ALL GAS PRICES");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL GAS PRICES", e);
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
    
    public float getAvgGasPrice()
    {
        Session objSession = null;
        List objList = null;
        float avgGasPrice = 0;
        try
        {
            logger.info("GETTING AVERAGE GAS PRICE");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createSQLQuery("select avg(a.LastValue) as avg from (SELECT top 12 * FROM [dbo].[PRC_Gas_Price])a");
            objList = objQuery.list();
            Iterator itr = objList.iterator();
            if(itr.hasNext())
            {
                Object obj = itr.next();
                if(obj != null)
                avgGasPrice = ((Double)obj).floatValue();
            }
            logger.info("GOT AVERAGE GAS PRICE");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET AVERAGE GAS PRICE", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return avgGasPrice;
    }
    
    public Date teeNaturalGasPriceLastImportedOn()
    {
        Session objSession = null;
        try
        {
            logger.info("GETTING TEE NATURAL GAS PRICE LAST IMPORTEDON DATE");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select max(gaspricevo.marketDate) from GasPriceVO as gaspricevo");
            Iterator itr = objQuery.iterate();
            if(itr.hasNext())
            {
                return (Date)itr.next();
            }
            logger.info("GOT TEE NATURAL GAS PRICE LAST IMPORTEDON DATE");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET TEE NATURAL GAS PRICE LAST IMPORTEDON DATE", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET TEE NATURAL GAS PRICE LAST IMPORTEDON DATE", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return null;
    }
    
    public static void main(String[] args)
    {
        GasPriceDAO obj = new GasPriceDAO();
        List  l = obj.getAllGasPrices();
        for (int i = 0; i < l.size(); i++)
        {
            GasPriceVO vo = (GasPriceVO) l.get(i);
            
            System.out.println("val :"+vo);
            System.out.println("MarketDate( :"+vo.getMarketDate());
            System.out.println("Month year  :"+vo.getMonthYear());
        }
        System.out.println(GasPriceDAO.getContractStartMonth());
    }
}

/*
*$Log: GasPriceDAO.java,v $
*Revision 1.2  2009/01/27 05:47:57  tannamalai
*changes - according to MX server
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.4  2007/11/27 15:47:52  tannamalai
*column names changed
*
*Revision 1.3  2007/11/27 15:28:15  tannamalai
**** empty log message ***
*
*Revision 1.2  2007/11/17 05:46:35  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.12  2007/09/14 06:54:24  sramasamy
*Log Message Correction.
*
*Revision 1.11  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.10  2007/08/17 11:18:40  kduraisamy
*NG Termination tables and lookups added.
*
*Revision 1.9  2007/08/03 12:22:12  kduraisamy
*forward curve date is taken from memory.
*
*Revision 1.8  2007/07/02 10:15:30  srajan
*order by added
*
*Revision 1.7  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.6  2007/06/12 05:18:46  kduraisamy
*for sqlserver 2005 data types changed.
*
*Revision 1.5  2007/05/04 06:34:58  kduraisamy
*gas price added in runHeaderVO.
*
*Revision 1.4  2007/04/30 09:21:42  kduraisamy
*forward Curve Date included.
*
*Revision 1.3  2007/04/23 11:25:04  kduraisamy
*getAvgGasPrice() added.
*
*Revision 1.2  2007/03/22 06:40:49  kduraisamy
*imports organized.
*
*Revision 1.1  2007/03/16 13:23:21  kduraisamy
*initial commit.
*
*Revision 1.3  2007/03/16 10:35:18  kduraisamy
*dividedByZero Error Corrected.
*
*Revision 1.2  2007/03/09 08:52:34  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.1  2007/03/08 16:32:44  kduraisamy
*Optimization with Sriram Completed.
*
*
*/