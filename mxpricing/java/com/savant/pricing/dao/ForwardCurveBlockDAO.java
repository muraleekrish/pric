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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import com.savant.pricing.calculation.valueobjects.ForwardCurveBlockVO;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.LoadPrerequisites;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ForwardCurveBlockDAO implements LoadPrerequisites
{
    private static Logger logger = Logger.getLogger(ForwardCurveBlockDAO.class);
    private static List forwardCurveVOS = null;
    PriceBlockDAO objPriceBlockDAO = new PriceBlockDAO();
    
    public static void getAllForwardCurves()
    {
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL FORWARD CURVES");
            objSession = HibernateUtil.getSession();
            forwardCurveVOS = new ArrayList();
            forwardCurveVOS = objSession.createCriteria(ForwardCurveBlockVO.class).addOrder(Order.asc("monthYear")).list();
            logger.info("GOT ALL FORWARD CURVES");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL FORWARD CURVES", e);
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
    
    public List getAllForwardCurveBlocks()
    {
        return forwardCurveVOS;
    }
    
    public HashMap getAllForwardCurveBlocks(int congestionZoneId, int dataSourceId)
    {
        int wrapId = objPriceBlockDAO.getPriceBlockIdByName("wrap");
        int blk2x16 = objPriceBlockDAO.getPriceBlockIdByName("2x16"); 
        int blk5x16 = objPriceBlockDAO.getPriceBlockIdByName("5x16");
        int blk7x24 = objPriceBlockDAO.getPriceBlockIdByName("7x24");
        int blk7x8 = objPriceBlockDAO.getPriceBlockIdByName("7x8");
        HashMap hm = new HashMap();
        try
        {
            logger.info("GETTING ALL FORWARD CURVE BLOCKS BY CONGESTION ZONE ID AND DATA SOURCE ID");
            hm.put(new Integer(wrapId), this.getForwardCurves(congestionZoneId, dataSourceId, wrapId));
            hm.put(new Integer(blk2x16), this.getForwardCurves(congestionZoneId, dataSourceId, blk2x16));  
            hm.put(new Integer(blk5x16), this.getForwardCurves(congestionZoneId, dataSourceId, blk5x16));
            hm.put(new Integer(blk7x24), this.getForwardCurves(congestionZoneId, dataSourceId, blk7x24));
            hm.put(new Integer(blk7x8), this.getForwardCurves(congestionZoneId, dataSourceId, blk7x8));
            logger.info("GOT ALL FORWARD CURVE BLOCKS BY CONGESTION ZONE ID AND DATA SOURCE ID");
        }
        catch(Exception e)
        {
            logger.error("GENERL EXCEPTION DURING GET ALL FORWARD CURVE BLOCKS BY CONGESTION ZONE ID AND DATA SOURCE ID", e);
            e.printStackTrace();
        }
        return hm;
    }
    
    public List getForwardCurves(int congestionZoneId, int dataSourceId, int priceBlockId)
    {
        logger.info("GETTING FORWARD CURVES");
        Iterator itr = forwardCurveVOS.iterator();
        List objList = new ArrayList();
        while(itr.hasNext())
        {
            ForwardCurveBlockVO objForwardCurveBlockVO = (ForwardCurveBlockVO)itr.next(); 
            if(objForwardCurveBlockVO.getPriceBlock().getPriceBlockIdentifier() == priceBlockId && objForwardCurveBlockVO.getCongestionZone().getCongestionZoneId() == congestionZoneId && objForwardCurveBlockVO.getDataSource().getDataSourceId() == dataSourceId)
            {
                objList.add(objForwardCurveBlockVO);
            }
        }
        logger.info("GOT FORWARD CURVES");
        return objList;
    }
    
    public static float getPrice(Date marketDate, Date monthYear, int priceBlockId, int congestionZoneId, int dataSrcId)
    {
        logger.info("GETTING PRICE");
        Iterator itr = forwardCurveVOS.iterator();
        float price = 0;
                
        while(itr.hasNext())
        {
            ForwardCurveBlockVO objForwardCurveBlockVO = (ForwardCurveBlockVO)itr.next(); 
            if(objForwardCurveBlockVO.getMarketDate().equals(marketDate) && objForwardCurveBlockVO.getMonthYear().equals(monthYear) && objForwardCurveBlockVO.getPriceBlock().getPriceBlockIdentifier() == priceBlockId && objForwardCurveBlockVO.getCongestionZone().getCongestionZoneId() == congestionZoneId && objForwardCurveBlockVO.getDataSource().getDataSourceId() == dataSrcId)
            {
                price = objForwardCurveBlockVO.getPrice();
                break;
            }
        }
        logger.info("GOT PRICE");
        return price;
    }
    public boolean reload()
    {
        ForwardCurveBlockDAO.getAllForwardCurves();
        return true;
    }
    
    public Date fwdCurveLastImportedOn()
    {
        try
        {
            logger.info("FINDING FORWAD CURVE LAST IMPORTED DATE");
            Iterator itr = forwardCurveVOS.iterator();
            Date marketDate = null;
            while(itr.hasNext())
            {
                ForwardCurveBlockVO objForwardCurveBlockVO = (ForwardCurveBlockVO)itr.next(); 
                if(marketDate != null && marketDate.compareTo(objForwardCurveBlockVO.getMarketDate())<0)
                {
                    marketDate = objForwardCurveBlockVO.getMarketDate();
                }
                else
                {
                    marketDate = objForwardCurveBlockVO.getMarketDate();
                }
            }
            logger.info("FOUND FORWAD CURVE LAST IMPORTED DATE");
            return marketDate;
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING FIND THE LASD IMPORTED DATE", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING FIND THE LASD IMPORTED DATE", e);
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args)
    {
       
            System.out.println(new ForwardCurveBlockDAO().getForwardCurves(1,1,8));
    }
}

/*
*$Log: ForwardCurveBlockDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.3  2007/11/28 13:04:27  jnadesan
*method access specifier changed
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
*Revision 1.15  2007/09/04 05:23:55  spandiyarajan
*removed unwanted imports
*
*Revision 1.14  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.13  2007/08/03 12:22:12  kduraisamy
*forward curve date is taken from memory.
*
*Revision 1.12  2007/07/05 14:00:21  kduraisamy
*Prerequisites object re initialized.
*
*Revision 1.11  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.10  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.9  2007/04/24 04:26:36  kduraisamy
*order by month year added.
*
*Revision 1.8  2007/04/23 10:24:30  kduraisamy
*getAllValid() and invalid() methods added.
*
*Revision 1.7  2007/04/17 15:42:58  kduraisamy
*price run performance took place.
*
*Revision 1.6  2007/03/22 08:16:42  jnadesan
*blockid taken by name
*
*Revision 1.5  2007/03/21 10:26:49  jnadesan
*Price block id taken by name.
*
*Revision 1.4  2007/03/17 07:00:21  kduraisamy
*getForwardcurvesByZone() added.
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