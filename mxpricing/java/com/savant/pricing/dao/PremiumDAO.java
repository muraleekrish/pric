/*
 * Created on May 17, 2007
 * 
 * Class Name PremiumDAO.java
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
import com.savant.pricing.valueobjects.PremiumVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PremiumDAO {
    
    private static Logger logger = Logger.getLogger(PremiumDAO.class);
    
    public PremiumDAO()
    {
    }
     public HashMap getPremiumVO()
     {
         /**
          * Requires -
          * Modifies -
          * Effects -
          * @throws -
          */
         Session objSession = null;
         PremiumVO objPremiumVO = null;
         Criteria objCriteria = null;
         List objList = null;
         HashMap hmPremiumVO = new HashMap();
         try
         {
             logger.info("GETTING PREMIUM DETAILS");
             objSession = HibernateUtil.getSession();
             objCriteria = objSession.createCriteria(PremiumVO.class);
             objList = objCriteria.list();
             Iterator itePremium = objList.iterator();
             while(itePremium.hasNext())
             {
                 objPremiumVO = (PremiumVO)itePremium.next();
                 hmPremiumVO.put(objPremiumVO.getPremiumType(),objPremiumVO);
             }
             logger.info("GOT PREMIUM DETAILS");
         }
         catch(HibernateException e)
         {
             logger.error("HIBERNATE EXCEPTION DURING GET THE PREMIUM DETAILS", e);
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
     
     public boolean updatePremiumVOs(List lstPremiumVOs)
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
             logger.info("UPDATING PREMIUM DETAILS");
             objSession = HibernateUtil.getSession();
             objSession.beginTransaction();
             Iterator itr = lstPremiumVOs.iterator();
             while(itr.hasNext())
             {
                 PremiumVO objPremiumVO = (PremiumVO)itr.next();
                 objSession.update(objPremiumVO);
             }
             objSession.getTransaction().commit();
             updateResult = true;
             logger.info("PREMIUM DETAILS ARE UPDATED");
         }
         catch(HibernateException e)
         {
             logger.error("HIBERNATE EXCEPTION DURING UPDATE THE PREMIUM DETAILS", e);
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
         PremiumDAO objPremiumDAO = new PremiumDAO();
         PremiumVO objPremiumVO = new PremiumVO();
         //objPremiumVO.setPremiumId(1);
         objPremiumVO.setPremiumType("Premium %");
         objPremiumVO.setValue((float)(12.0));
         List lst = new ArrayList();
         lst.add(objPremiumVO);
         objPremiumDAO.updatePremiumVOs(lst);
         if(BuildConfig.DMODE)
             System.out.println(((PremiumVO)objPremiumDAO.getPremiumVO().get("Premium %")).getValue());
    }
}



/*
*$Log: PremiumDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.6  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.5  2007/07/31 12:28:42  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.4  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.3  2007/06/02 04:47:55  spandiyarajan
*removed unewanted imports
*
*Revision 1.2  2007/05/18 09:30:18  jnadesan
*update method added for premiums
*
*Revision 1.1  2007/05/18 03:38:21  jnadesan
*initial commit
*
*
*/