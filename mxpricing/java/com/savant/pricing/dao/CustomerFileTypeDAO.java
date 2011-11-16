/*
 * 
 * CustomerFileTypeDAO.java    Aug 7, 2007
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

package com.savant.pricing.dao;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.valueobjects.CustomerFileTypesVO;

/**
 * 
 */
public class CustomerFileTypeDAO
{
    private static Logger logger = Logger.getLogger(CustomerFileTypeDAO.class);
    
    public boolean addCustomerFileType(CustomerFileTypesVO objCustomerFileTypesVO)
    {
        Session objSession = null;
        boolean updateResult = false;
        try
        {
            logger.info("ADDING CUSTOMER FILE TYPE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.save(objCustomerFileTypesVO);
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("CUSTOMER FILE TYPE IS ADDED");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD THE CUSTOMER FILE TYPE", e);
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
    
    public Collection getAllFileTypes()
    {
        List lstCust = null;
        Session objSession = null;
        Criteria objcriteria = null;
        try
        {
            logger.info("GETTING ALL FILE TYPES");
            objSession = HibernateUtil.getSession();
            objcriteria = objSession.createCriteria(CustomerFileTypesVO.class);
            lstCust = objcriteria.list();
            logger.info("GOT ALL FILE TYPES");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL FILE TYPES", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return lstCust;
    }

    public static void main(String[] args)
    {
        CustomerFileTypeDAO objCustomerFileTypeDAO = new CustomerFileTypeDAO();
        /*CustomerFileTypesVO objCustomerFileTypesVO = new CustomerFileTypesVO();
        objCustomerFileTypesVO.setFileType("CDR Details");
        objCustomerFileTypeDAO.addCustomerFileType(objCustomerFileTypesVO);*/
        System.out.println(objCustomerFileTypeDAO.getAllFileTypes());
    }
    
}


/*
*$Log: CustomerFileTypeDAO.java,v $
*Revision 1.2  2008/11/21 09:46:19  jvediyappan
*Trieagle changed as MXEnergy.
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
*Revision 1.5  2007/09/04 05:23:55  spandiyarajan
*removed unwanted imports
*
*Revision 1.4  2007/09/03 14:09:42  sramasamy
*Log message is added for log file.
*
*Revision 1.3  2007/08/14 13:15:49  jnadesan
*procedure created to upload ,delete files
*
*Revision 1.2  2007/08/10 13:04:10  rraman
*methods added to get filetypes
*
*Revision 1.1  2007/08/07 10:02:53  jnadesan
*entry for customer file upload
*
*
*/