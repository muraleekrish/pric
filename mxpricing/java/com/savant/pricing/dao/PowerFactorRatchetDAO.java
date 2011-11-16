/*
 * Created on Apr 19, 2007
 *
 * ClassName	:  	PowerFactorRatchetDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.calculation.valueobjects.PowerFactorRatchetVO;
import com.savant.pricing.common.HibernateUtil;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PowerFactorRatchetDAO
{
    private static Logger logger = Logger.getLogger(PowerFactorRatchetDAO.class);

    public PowerFactorRatchetDAO()
    {
    }
    
    public Collection getAllPowerFactorRatchet(int rateCodeId)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL POWER FACTOR RATCHET BY RATE CODE ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(PowerFactorRatchetVO.class).add(Restrictions.eq("tdspRateCode.rateCodeIdentifier", new Integer(rateCodeId)));
            objList = objCriteria.list();
            logger.info("GOT ALL POWER FACTOR RATCHET BY RATE CODE ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL POWER FACTOR RATCHET BY RATE CODE ID", e);
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
        new PowerFactorRatchetDAO().getAllPowerFactorRatchet(1);
    }
}

/*
*$Log: PowerFactorRatchetDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.1  2007/04/19 11:13:57  kduraisamy
*Set is changed to List.
*
*
*/