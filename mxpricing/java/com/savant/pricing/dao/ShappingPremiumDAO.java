/*
 * Created on May 17, 2007
 * 
 * Class Name ShappingPremiumDAO.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.ShappingPremiumVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ShappingPremiumDAO {
    private static Logger logger = Logger.getLogger(ShappingPremiumDAO.class);
    
    public ShappingPremiumDAO()
    {
    }
    
    public HashMap getShappingPremiumVO()
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Session objSession = null;
        ShappingPremiumVO objShappingPremiumVO = null;
        Criteria objCriteria = null;
        List objList = null;
        HashMap hmPremiumVO = new HashMap();
        try
        {
            logger.info("GETTING SHAPPING PREMIUM DETAILS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ShappingPremiumVO.class);
            objList = objCriteria.list();
            Iterator itePremium = objList.iterator();
            while(itePremium.hasNext())
            {
                objShappingPremiumVO = (ShappingPremiumVO)itePremium.next();
                hmPremiumVO.put(objShappingPremiumVO.getShappingPremiumType(),objShappingPremiumVO);
            }
            logger.info("GOT SHAPPING PREMIUM DETAILS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE SHAPPING PREMIUM DETAILS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hmPremiumVO;
    }
    
    public boolean updateShappingPremium(List lstShappingPremiumVOs)
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
            logger.info("UPDATING SHAPPING PREMIUM DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            Iterator itr = lstShappingPremiumVOs.iterator();
            while(itr.hasNext())
            {
                ShappingPremiumVO objShappingPremiumVO = (ShappingPremiumVO)itr.next();
                objSession.update(objShappingPremiumVO);
            }
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("SHAPPING PREMIUM DETAILS IS UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE SHAPPING PREMIUM DETAILS", e);
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
    
    public static void main(String[] args) 
    {
        ShappingPremiumVO objShappingPremiumVO = new ShappingPremiumVO();
        objShappingPremiumVO.setShappingPremiumId(1);
        objShappingPremiumVO.setShappingPremiumType("Use Fulcrum Shaped Price Where Available");
        objShappingPremiumVO.setApply(false);
        List lst = new ArrayList();
        lst.add(objShappingPremiumVO);
        new ShappingPremiumDAO().updateShappingPremium(lst);
        if(BuildConfig.DMODE)
            System.out.println(((ShappingPremiumVO)new ShappingPremiumDAO().getShappingPremiumVO().get("Use Fulcrum Shaped Price Where Available")).isApply());
    }
}


/*
*$Log: ShappingPremiumDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/09/04 07:05:18  sramasamy
*Log message is added for log file.
*
*Revision 1.4  2007/07/31 12:28:42  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.3  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.2  2007/05/18 09:30:18  jnadesan
*update method added for premiums
*
*Revision 1.1  2007/05/18 03:38:21  jnadesan
*initial commit
*
*
*/