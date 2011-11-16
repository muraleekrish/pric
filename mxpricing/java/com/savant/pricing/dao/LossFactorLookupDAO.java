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
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.savant.pricing.calculation.valueobjects.LossFactorLookupVO;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.TDSPVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LossFactorLookupDAO
{
    private static Logger logger = Logger.getLogger(LossFactorLookupDAO.class);
    private static List lossFactorLookupVOS = null;
    
    public static void getAllLossFactorLookupVOS()
    {
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL LOSS FACTOR LOOKUP");
            lossFactorLookupVOS = new ArrayList();
            objSession = HibernateUtil.getSession();
            lossFactorLookupVOS = objSession.createCriteria(LossFactorLookupVO.class).list();
            logger.info("GOT ALL LOSS FACTOR LOOKUP");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL LOSS FACTOR LOOKUP", e);
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
    
    public static String getDLF(TDSPVO tdsp, int serviceVoltageId)
    {
        logger.info("GETTING DLF");
        Iterator itr = lossFactorLookupVOS.iterator();
        String dlf = "";
        while(itr.hasNext())
        {
            LossFactorLookupVO objLossFactorLookupVO = (LossFactorLookupVO)itr.next();
            if(objLossFactorLookupVO.getTdsp().getTdspIdentifier() == tdsp.getTdspIdentifier() && objLossFactorLookupVO.getServiceVoltage().getServiceVoltageIdentifier() == serviceVoltageId)
            {
                dlf = objLossFactorLookupVO.getDlfCode();
                break;
            }
        }
        logger.info("GOT DLF");
        return dlf;
    }
    
    public static void main(String[] args)
    {
        LossFactorLookupDAO.getAllLossFactorLookupVOS();
    }

}

/*
*$Log: LossFactorLookupDAO.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.3  2007/07/05 14:00:21  kduraisamy
*Prerequisites object re initialized.
*
*Revision 1.2  2007/04/16 13:17:25  kduraisamy
*imports organized.
*
*Revision 1.1  2007/04/06 12:32:45  kduraisamy
*TLF AND DLF COMPLETED.
*
*
*/