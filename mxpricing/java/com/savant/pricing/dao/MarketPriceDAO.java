/*
 * Created on May 21, 2007
 * 
 * Class Name MarketPriceDAO.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.MarketPriceVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MarketPriceDAO 
{
    private static Logger logger = Logger.getLogger(MarketPriceDAO.class);
    
    public List getAllMarketPrice()
    {
        List objList = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL MARKET PRICE");
            objSession = HibernateUtil.getSession();
            objList = objSession.createCriteria(MarketPriceVO.class).addOrder(Order.asc("marketPeriod")).list();
            logger.info("GOT ALL MARKET PRICE");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL MARKET PRICE", e);
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
    
    public static void main(String[] args)
    {
        if(BuildConfig.DMODE)
            System.out.println(((MarketPriceVO)new MarketPriceDAO().getAllMarketPrice().get(0)).getMarketPeriod());
    }
}


/*
*$Log: MarketPriceDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.2  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.1  2007/05/21 11:45:54  jnadesan
*entry for market price
*
*
*/