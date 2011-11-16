/*
 * 
 * MatrixMonthlyWeightsDAO.java    Aug 24, 2007
 * 
 * Copyright (c) Savant Technologies Pvt Ltd.
 * 'Savant House' 127 Chamiers Road, Nandanam, Chennai-600 035. INDIA 
 * All rights reserved.

 * @author: jnadesan
 * @company: Savant Technologies
 * @client: MX Energy
 * @version: 
 * @Description: 
 * 
*/

package com.savant.pricing.matrixpricing.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.matrixpricing.valueobjects.MatrixMonthlyWeightsVO;

/**
 * 
 */
public class MatrixMonthlyWeightsDAO
{
    static Logger logger = Logger.getLogger(MatrixMonthlyWeightsDAO.class);
    
    public MatrixMonthlyWeightsVO getValue(int profileId, int weatherZoneId, int mnth)
    {
        Session objSession = null;
        Criteria objCriteria = null;
        MatrixMonthlyWeightsVO objMatrixMonthlyWeightsVO = null;
        try
        {
            logger.info("GETTING VALUE DETAILS FOR PROFILE ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MatrixMonthlyWeightsVO.class);
            objCriteria.add(Restrictions.eq("loadProfile.profileIdentifier",new Integer(profileId))).add(Restrictions.eq("weatherZone.weatherZoneId",new Integer(weatherZoneId))).add(Restrictions.eq("month",new Integer(mnth)));
            objMatrixMonthlyWeightsVO = (MatrixMonthlyWeightsVO)objCriteria.uniqueResult();
            logger.info("GOT VALUE DETAILS");
        }
        catch (HibernateException e) {
            logger.error("HIBERNATE EXCEPTION DURING GET THE VALUE DETAILS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objMatrixMonthlyWeightsVO;
    }
    public List getValue(int profileId,int weatherZoneId)
    {
        Session objSession = null;
        Criteria objCriteria = null;
        List objList = null;
        try
        {
            logger.info("GETTING VALUE FOR PROFILE ID AND WEATHER ZONE ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MatrixMonthlyWeightsVO.class);
            objCriteria.add(Restrictions.eq("loadProfile.profileIdentifier",new Integer(profileId))).add(Restrictions.eq("weatherZone.weatherZoneId",new Integer(weatherZoneId)));
            objList = objCriteria.list();
            logger.info("GOT VALUE FOR PROFILE ID AND WEATHER ZONE ID");
        }
        catch (HibernateException e) {
            logger.error("HIBERNATE EXCEPTION DURING GET VALUE", e);
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
}


/*
*$Log: MatrixMonthlyWeightsDAO.java,v $
*Revision 1.2  2008/11/21 09:46:39  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/09/04 10:17:35  sramasamy
*Session object is closed.
*
*Revision 1.3  2007/09/04 09:16:29  sramasamy
*Log message is added for log file.
*
*Revision 1.2  2007/08/27 15:13:19  kduraisamy
*weatherzone id added into condition
*
*Revision 1.1  2007/08/27 04:42:10  jnadesan
*initial commit for MonthlyWeights
*
*
*/