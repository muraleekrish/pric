/*
 * Created on Mar 23, 2007
 *
 * ClassName	:  	UOMDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


import com.savant.pricing.calculation.valueobjects.UOMVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UOMDAO
{
    private static Logger logger = Logger.getLogger(UOMDAO.class);
    
    public List getAllUOM()
    {
        List objList = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL UOM DETAILS");
            objSession = HibernateUtil.getSession();
            objList = objSession.createCriteria(UOMVO.class).list();
            logger.info("GOT ALL UOM DETAILS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL UOM DETAILS", e);
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
    
    public UOMVO getUOM(int id)
    {
        UOMVO objUOMVO = new UOMVO();
        Session objSession = null;
        Criteria objCriteria = null;
        
        try
        {
            logger.info("");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(UOMVO.class).add(Restrictions.eq("uomIdentifier", new Integer(id)));
            objUOMVO = (UOMVO)objCriteria.uniqueResult();
            logger.info("");
        }
        catch(HibernateException e)
        {
            logger.error("", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        
        
        return objUOMVO;
    }
    
    public static void main(String[] args)
    {
        if(BuildConfig.DMODE)
            System.out.println(new UOMDAO().getAllUOM());
    }
}

/*
*$Log: UOMDAO.java,v $
*Revision 1.2  2008/02/06 06:41:53  tannamalai
*cost of capital added
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
*Revision 1.3  2007/09/04 07:05:18  sramasamy
*Log message is added for log file.
*
*Revision 1.2  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.1  2007/03/23 09:27:35  kduraisamy
*initial commit.
*
*
*/