/*
 * Created on Feb 3, 2007
 *
 * ClassName	:  	TaxTypesDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.TaxTypesVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxTypesDAO
{
    private static Logger logger = Logger.getLogger(TaxTypesDAO.class);
    
    public HashMap getAllTaxTypes()
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria = null;
        Session objSession = null;
        Integer totRecordCount = null;
        List objList = null;
        HashMap hmResult = new HashMap();
        try
        {
            logger.info("GETTING ALL TAX TYPES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(TaxTypesVO.class);
            totRecordCount = new Integer(objCriteria.list().size());
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount", totRecordCount);
            hmResult.put("Records", objList);
            logger.info("GOT ALL TAX TYPES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL TAX TYPES", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hmResult;
    }
    
    public static void main(String args[])
    {
        
        List objList = (List)new TaxTypesDAO().getAllTaxTypes().get("Records");
        TaxTypesVO[] objTaxTypesVOList = new TaxTypesVO[objList.size()];
        
        objList.toArray(objTaxTypesVOList);
        
        for(int i=0;i<objTaxTypesVOList.length;i++)
        {
            TaxTypesVO obj = new TaxTypesVO();
            obj = objTaxTypesVOList[i];
            if(BuildConfig.DMODE)
            {
                System.out.println("Day Types:"+obj.getTaxType());
                System.out.println("Desc:"+obj.getDescription());
            }
        }
    }
}

/*
*$Log: TaxTypesDAO.java,v $
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
*Revision 1.1  2007/02/03 06:10:40  kduraisamy
*Tax Types mapping included.
*
*
*/