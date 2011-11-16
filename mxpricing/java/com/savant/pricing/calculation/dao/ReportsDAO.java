/*
 * Created on Apr 8, 2007
 *
 * ClassName	:  	ReportsDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.calculation.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import com.savant.pricing.calculation.valueobjects.ReportsParamVO;
import com.savant.pricing.calculation.valueobjects.ReportsParamValuesVO;
import com.savant.pricing.calculation.valueobjects.ReportsTemplateHeaderVO;
import com.savant.pricing.common.HibernateUtil;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportsDAO
{
    static Logger logger = Logger.getLogger(ReportsDAO.class);
   
    public List getAllReportsTemplates()
    {
        Session objSession = null;
        List objList = null;
        Criteria objCriteria = null;
        try
        {
            logger.info("GETTING ALL REPORTS TEMPLATES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ReportsTemplateHeaderVO.class);
            objList = objCriteria.list();
            logger.info("GOT ALL REPORTS TEMPLATES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL REPORTS TEMPLATES", e);
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
    
    public List getAllReportParams(int reportId)
    {
        Session objSession = null;
        List objList = null;
        Criteria objCriteria = null;
        try
        {
            logger.info("GETTING ALL REPORT PARAM");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ReportsParamVO.class).add(Restrictions.eq("report.reportIdentifier", new Integer(reportId)));
            objList = objCriteria.list();
            logger.info("GOT ALL REPORT PARAM");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL REPORT PARAMS", e);
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
    
    public ReportsParamValuesVO getReportParamValue(int reportParamId)
    {
        Session objSession = null;
        ReportsParamValuesVO objReportsParamValuesVO = null;
        try
        {
            logger.info("GETTING REPORT PARAM VALUE");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("from ReportsParamValuesVO as reportParamValues where reportParamValues.modifiedDate = (select max(reportParamValues.modifiedDate) from ReportsParamValuesVO as reportParamValues where reportParamValues.reportParam.reportParamIdentifier = ?) and reportParamValues.reportParam.reportParamIdentifier = ?");
            objQuery.setInteger(0,reportParamId);
            objQuery.setInteger(1, reportParamId);
            Iterator itr =objQuery.list().iterator();
            if(itr.hasNext())
            {
                objReportsParamValuesVO = (ReportsParamValuesVO)itr.next(); 
            }
            if(objReportsParamValuesVO == null)
            {
                objReportsParamValuesVO = new ReportsParamValuesVO();
                ReportsParamVO objReportsParamVO = (ReportsParamVO)objSession.get(ReportsParamVO.class, new Integer(reportParamId));
                objReportsParamValuesVO.setReportParam(objReportsParamVO);
                objReportsParamValuesVO.setReportParamValue(objReportsParamVO.getDefaultText());
            }
            logger.info("GOT REPORT PARAM VALUE");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET REPORT PARAM VALUE", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objReportsParamValuesVO;
	}
    
    //to update the template value. but this is not update.typically add.Latest will be taken out.others will be maintained.
    public boolean updateReportsParamValues(ReportsParamValuesVO objReportsParamValuesVO)
    {
        Session objSession = null;
        boolean addResult = false;
        try
        {
            logger.info("UPDATING REPORTS PARAM VALUES");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.save(objReportsParamValuesVO);
            objSession.getTransaction().commit();
            addResult = true;
            logger.info("REPORTS PARAM VALUE IS UPDATED");
        }
        catch(HibernateException e)
        {
            objSession.getTransaction().rollback();
            e.printStackTrace();
            logger.error("HIBERNATE EXCEPTION DURING UPDATE REPORTS PARAM VALUES", e);
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
    
    public ReportsTemplateHeaderVO getParamValue(int productId, String reportParamName)
    {
        Session objSession = null;
        String reportParamValue = "";
        Criteria objCriteria = null;
        ReportsTemplateHeaderVO objReportsTemplateHeaderVO = null;
        try
        {
            logger.info("GETTING ALL REPORTS TEMPLATES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ReportsTemplateHeaderVO.class).add(Restrictions.eq("products.productIdentifier",new Integer(productId))).add(Restrictions.eq("reportName",reportParamName));
            objReportsTemplateHeaderVO = (ReportsTemplateHeaderVO)objCriteria.uniqueResult();
            logger.info("GOT ALL REPORTS TEMPLATES");
            
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION WHILE GETTING PARAM VALUE", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objReportsTemplateHeaderVO;
    }
}

/*
*$Log: ReportsDAO.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/23 08:43:25  jnadesan
*method changed
*
*Revision 1.1  2007/10/30 05:51:54  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:23  jnadesan
*initail MXEP commit
*
*Revision 1.10  2007/09/18 04:44:48  spandiyarajan
*removed unwanted imports
*
*Revision 1.9  2007/09/10 05:07:03  sramasamy
*Log message is added for log file.
*
*Revision 1.8  2007/09/06 14:03:26  jnadesan
*LoggerOutPut removed
*
*Revision 1.7  2007/07/31 12:26:53  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.6  2007/07/31 11:39:32  kduraisamy
*objsession properly closed or calling procedure properly done.
*
*Revision 1.5  2007/06/12 12:55:25  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.4  2007/04/26 05:49:34  jnadesan
*uniqueresult problem solved
*
*Revision 1.3  2007/04/09 07:32:42  kduraisamy
*getReportName(productId, reportParamName) added.
*
*Revision 1.2  2007/04/08 10:33:37  kduraisamy
*reports template added.
*
*Revision 1.1  2007/04/08 10:02:51  kduraisamy
*initial commit.
*
*
*/