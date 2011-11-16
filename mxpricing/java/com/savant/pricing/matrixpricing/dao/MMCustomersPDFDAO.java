/*
 * 
 * MMCustomersPDFDAO.java    Aug 28, 2007
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

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.matrixpricing.valueobjects.MMPricingPDFVO;
import com.savant.pricing.securityadmin.dao.UserDAO;

/**
 * 
 */
public class MMCustomersPDFDAO
{
    static Logger logger = Logger.getLogger(MMCustomersPDFDAO.class);
    
    public boolean addFile(MMPricingPDFVO objMMPricingPDFVO, byte[] fileContent)
    {
        Session objSession = null;
        boolean updateResult = false;
        CallableStatement cstmnt;
        try
        {
            logger.info("ADDING FILE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            cstmnt = objSession.connection().prepareCall("{call UploadMMPDFFile(?,?,?,?,?,?,?,?)}");
            cstmnt.setString(1, objMMPricingPDFVO.getRefNo());
            cstmnt.setString(2, objMMPricingPDFVO.getPriceRunRefNo().getPriceRunRefNo());
            cstmnt.setString(3, objMMPricingPDFVO.getCustName());
            cstmnt.setBytes(4,fileContent);
            cstmnt.setString(5,objMMPricingPDFVO.getSalesRep().getUserId());
            cstmnt.setString(6,objMMPricingPDFVO.getSalesManager().getUserId());
            cstmnt.setString(7,objMMPricingPDFVO.getCreatedBy());
            cstmnt.setDate(8,new java.sql.Date(objMMPricingPDFVO.getCreatedDate().getTime()));
            cstmnt.execute();
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("FILE IS ADDED");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD THE FILE", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
            if(e.getCause().getMessage().startsWith("Cannot insert duplicate key row in object 'PRC_Cust_files"))
               throw new HibernateException("Cannot insert duplicate key row in object 'PRC_Cust_files'");
        }
        catch (Exception e) {
            logger.error("GENERAL EXCEPTION DURING ADD THE FILE", e);
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
    
    public byte[] getFile(String refNo)
    {
        Session objSession = null;
        CallableStatement cstmnt;
        ResultSet rs =null;
        byte[] byteVal = {0};
        try
        {
            logger.info("GETTING FILE BY REF NUMBER");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            cstmnt = objSession.connection().prepareCall("{call GetMMPDFFile (?)}");
            cstmnt.setString(1,refNo);
            rs = cstmnt.executeQuery();
            while(rs.next())
            {
                byteVal = rs.getBytes("File_Content");
            }
            logger.info("GOT FILE BY REF NUMBER");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET THE FILE BY REF NUMBER", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return byteVal;
    }
    
    public HashMap getCutomers(boolean isAnalyst, String userName, Filter custNameFil,Filter refNoFil,String rep,String manager,Date fromDate, Date toDate, String specFun, int startIndex, int displayCount )
    {
        Session objSession = null;
        Criteria objPriceRunRefCriteria = null;
        Criteria objpriceRunCriteria = null;
        HashMap hmResult = new HashMap();
        Integer totRecordCount = null;
        List objList = null;
        UserDAO objUserDAO = new UserDAO();
        try
        {
            logger.info("GETTING CUSTOMERS");
            objSession = HibernateUtil.getSession();
            objPriceRunRefCriteria = objSession.createCriteria(MMPricingPDFVO.class);
            objpriceRunCriteria = objPriceRunRefCriteria.createCriteria("priceRunRefNo");
            
            List lstChildPersons = objUserDAO.getChildPersons(userName, false);
            if(!isAnalyst)
            {
                if(lstChildPersons.size()>0)
                    objPriceRunRefCriteria.add(Restrictions.eq("salesManager.userId",userName));
                else
                    objPriceRunRefCriteria.add(Restrictions.eq("salesRep.userId",userName));
            }
            
            if(fromDate != null)
            {
                if(specFun.equalsIgnoreCase("on"))
                {
                    Calendar objCalendar = Calendar.getInstance();
                    objCalendar.setTime(fromDate);
                    objCalendar.add(Calendar.DATE,1);
                    objpriceRunCriteria.add(Restrictions.between("priceRunTime",fromDate, objCalendar.getTime()));
                }
                else if(specFun.equalsIgnoreCase("before"))
                    objpriceRunCriteria.add(Restrictions.le("priceRunTime",fromDate));
                else if(specFun.equalsIgnoreCase("after"))
                    objpriceRunCriteria.add(Restrictions.gt("priceRunTime",fromDate));
                else if(specFun.equalsIgnoreCase("between"))
                {
                    if(toDate != null)
                    {
                        GregorianCalendar gc = new GregorianCalendar();
                        gc.setTime(toDate);
                        gc.add(Calendar.DATE,1);
                        objpriceRunCriteria.add(Restrictions.ge("priceRunTime",fromDate)).add(Restrictions.lt("priceRunTime", gc.getTime()));
                    }
                    else
                        objpriceRunCriteria.add(Restrictions.gt("priceRunTime",fromDate));
                }
            }
            if(!rep.trim().equalsIgnoreCase("0"))
            {
                objPriceRunRefCriteria.add(Restrictions.eq("salesRep.userId",rep));
            }
            if(!manager.trim().equalsIgnoreCase("0"))
            {
                objPriceRunRefCriteria.add(Restrictions.eq("salesManager.userId",manager));
            }
            if(custNameFil != null)
            {
                objPriceRunRefCriteria.add(Restrictions.like("custName",custNameFil.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(custNameFil.getSpecialFunction())));
            }
            if(refNoFil != null)
            {
                objPriceRunRefCriteria.add(Restrictions.like("refNo",refNoFil.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(refNoFil.getSpecialFunction())));
            }
            objPriceRunRefCriteria.addOrder(Order.desc("createdDate"));
            totRecordCount = new Integer(objPriceRunRefCriteria.list().size());
            objPriceRunRefCriteria.setFirstResult(startIndex);
            objPriceRunRefCriteria.setMaxResults(displayCount);
            objList = objPriceRunRefCriteria.list();
            hmResult.put("TotalRecordCount", totRecordCount);
            hmResult.put("Records", objList);
            logger.info("GOT CUSTOMERS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE CUSTOMERS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET THE CUSTOMERS", e);
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
    
    public List getfiles()
    {
        Session objSession = null;
        Criteria objCriteria = null;
        List objList = null;
        try
        {
            logger.info("GETTING FILES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MMPricingPDFVO.class);
            objList = objCriteria.list();
            logger.info("GOT FILES");
        }
        catch (HibernateException e) {
            logger.error("HIBERNATE EXCEPTION DURING GET THE FILES", e);
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
    
    public MMPricingPDFVO getCustFileDetails(String refNo)
    {
        MMPricingPDFVO objMMPricingPDFVO = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING CUSTOMER FILE DETAILS");
            if(BuildConfig.DMODE)
                System.out.println("refNo :"+refNo);
            objSession = HibernateUtil.getSession();
            objMMPricingPDFVO = (MMPricingPDFVO)objSession.get(MMPricingPDFVO.class,refNo);
            logger.info("GOT CUSTOMER FILE DETAILS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET CUSTOMER FILE DETAILS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET CUSTOMER FILE DETAILS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objMMPricingPDFVO;
    }
    
    public boolean deleteCustFile(MMPricingPDFVO objMMPricingPDFVO)
    {
        boolean delResult = false;
        Session objSession = null;
        try
        {
            logger.info("DELETING CUSTOMER FILE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.delete(objMMPricingPDFVO);
            delResult = true;
            objSession.getTransaction().commit();
            objSession.flush();
            logger.info("CUSTOMER FILE IS DELETED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DELETE THE CUSTOMER FILE", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return delResult;
    }
    
    public static void main(String args[]) throws Exception
    {
        /*Filter obj = new Filter();
        obj.setFieldName("");
        obj.setFieldValue("");
        obj.setSpecialFunction(HibernateUtil.STARTS_WITH);
        HashMap hmgetCutomers = new HashMap();
        hmgetCutomers = new MMCustomersPDFDAO().getCutomers(null,null,"0","0",null,null,"",0,10);
        System.out.println("hmgetCutomers:"+hmgetCutomers);*/
        MMPricingPDFVO objMMPricingPDFVO = new MMCustomersPDFDAO().getCustFileDetails("MM1188465969804");
        System.out.println("getCustName:"+objMMPricingPDFVO.getCustName());
        
    }
}


/*
*$Log: MMCustomersPDFDAO.java,v $
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
*Revision 1.10  2007/09/17 10:59:03  spandiyarajan
*role based functionality added in customerfiles list.
*
*Revision 1.9  2007/09/10 12:29:22  spandiyarajan
*order by created date
*
*Revision 1.8  2007/09/06 07:37:01  spandiyarajan
*run date search bug corrected
*
*Revision 1.7  2007/09/04 10:17:51  sramasamy
*Session object is closed.
*
*Revision 1.6  2007/09/04 09:16:29  sramasamy
*Log message is added for log file.
*
*Revision 1.5  2007/08/31 06:15:52  spandiyarajan
*removed unwanted system.out.println statement
*
*Revision 1.4  2007/08/31 06:11:31  spandiyarajan
*dekete option added in Customer Files list page
*
*Revision 1.3  2007/08/30 12:36:43  spandiyarajan
*changed for MM Pricing - Customer Files list page
*
*Revision 1.2  2007/08/29 05:46:42  jnadesan
*method added to get all customers
*
*Revision 1.1  2007/08/28 11:17:15  jnadesan
*initial commit
*
*
*/