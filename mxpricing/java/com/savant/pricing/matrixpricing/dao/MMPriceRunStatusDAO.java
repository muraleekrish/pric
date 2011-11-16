/*
 * 
 * MMPriceRunStatusDAO.java    Aug 23, 2007
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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.matrixpricing.valueobjects.MMPriceRunHeaderVO;

/**
 * 
 */
public class MMPriceRunStatusDAO
{
    static Logger logger = Logger.getLogger(MMPriceRunStatusDAO.class);
    
    public HashMap getAllRunResults( Date fromDate, String filter, Date toDate, String status, Date expDate,Date offerDate, int startIndex, int displayCount)
    {
        Session objSession = null;
        Criteria objPriceRunRefCriteria = null;
        HashMap hmResult = new HashMap();
        Integer totRecordCount = null;
        List objList = null;
        GregorianCalendar gc = new GregorianCalendar();
        try
        {
            logger.info("GETTING ALL RUN RESULTS");
            objSession = HibernateUtil.getSession();
            objPriceRunRefCriteria = objSession.createCriteria(MMPriceRunHeaderVO.class);
            if(fromDate != null)
            {
                if(filter.equalsIgnoreCase("on"))
                {
                    Calendar objCalendar = Calendar.getInstance();
                    objCalendar.setTime(fromDate);
                    objCalendar.add(Calendar.DATE,1);
                    objPriceRunRefCriteria.add(Restrictions.between("priceRunTime", fromDate, objCalendar.getTime()));
                }
                else if(filter.equalsIgnoreCase("before"))
                    objPriceRunRefCriteria.add(Restrictions.le("priceRunTime",fromDate));
                else if(filter.equalsIgnoreCase("after"))
                    objPriceRunRefCriteria.add(Restrictions.gt("priceRunTime",fromDate));
                else if(filter.equalsIgnoreCase("between"))
                {
                    if(toDate != null)
                    {
                        gc.setTime(toDate);
                        gc.add(Calendar.DATE,1);
                        objPriceRunRefCriteria.add(Restrictions.ge("priceRunTime",fromDate)).add(Restrictions.lt("priceRunTime", gc.getTime()));
                    }
                    else
                        objPriceRunRefCriteria.add(Restrictions.gt("priceRunTime",fromDate));
                }
            }
            else if(toDate != null)
            {
                gc.setTime(toDate);
                gc.add(Calendar.DATE,1);
                objPriceRunRefCriteria.add(Restrictions.le("priceRunTime",gc.getTime()));
            }
                
            if(offerDate!=null)
            {
                Calendar objCalendar = Calendar.getInstance();
                objCalendar.setTime(offerDate);
                objCalendar.add(Calendar.DATE,1);
                objPriceRunRefCriteria.add(Restrictions.between("offerDate",offerDate,objCalendar.getTime()));
            }
            if(expDate!=null)
            {
                Calendar objCalendar = Calendar.getInstance();
                objCalendar.setTime(expDate);
                objCalendar.add(Calendar.DATE,1);
                objPriceRunRefCriteria.add(Restrictions.between("offerDate",expDate,objCalendar.getTime()));
            }
            if(status.length()>0)
            {
                if(status.equalsIgnoreCase("true"))
                {
                    objPriceRunRefCriteria.add(Restrictions.eq("status",new Boolean(true)));
                }
                else 
                    objPriceRunRefCriteria.add(Restrictions.eq("status",new Boolean(false)));
            }
            objPriceRunRefCriteria.addOrder(Order.desc("priceRunTime"));
            totRecordCount = new Integer(objPriceRunRefCriteria.list().size());
            objPriceRunRefCriteria.setFirstResult(startIndex);
            objPriceRunRefCriteria.setMaxResults(displayCount);
            objList = objPriceRunRefCriteria.list();
            hmResult.put("TotalRecordCount", totRecordCount);
            hmResult.put("Records", objList);
            logger.info("GOT ALL RUN RESULTS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL RUN RESULTS", e); 
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL RUN RESULTS", e);
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

    public MMPriceRunHeaderVO getLatsetRun()
    {
        Session objSession = null;
        MMPriceRunHeaderVO objMMPriceRunHeaderVO = null;
        try
        {
            logger.info("GETTING RECENT PRICE RUN TIME");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("from MMPriceRunHeaderVO as header where header.priceRunTime = (select max(tempheader.priceRunTime) from MMPriceRunHeaderVO as tempheader) ");
            objMMPriceRunHeaderVO = (MMPriceRunHeaderVO)objQuery.uniqueResult();
            logger.info("GOT RECENT PRICE RUN TIME");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET RECENT PRICE RUN TIME", e); 
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET RECENT PRICE RUN TIME", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objMMPriceRunHeaderVO;
    }
    
    public MMPriceRunHeaderVO getRunresultDetails(String priceRunRefNo)
    {
        MMPriceRunHeaderVO objMMPriceRunHeaderVO = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING RUN RESULT DETAILS");
            if(BuildConfig.DMODE)
                System.out.println("priceRunRefNo :"+priceRunRefNo);
            objSession = HibernateUtil.getSession();
            objMMPriceRunHeaderVO = (MMPriceRunHeaderVO)objSession.get(MMPriceRunHeaderVO.class,priceRunRefNo);
            logger.info("GOT RUN RESULT DETAILS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET RUN RESULT DETAILS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET RUN RESULT DETAILS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objMMPriceRunHeaderVO;
    }
    
    public boolean deleteRunResult(String priceRunRefNo)
    { 
        Session objSession = null;
        boolean result = false;
        int noOfRowsAffected = 0;
        try
        {
            logger.info("DELETING RUN RESULT");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            
            noOfRowsAffected = objSession.createQuery("delete from MMPricingPDFVO as pricingPdfVO where pricingPdfVO.priceRunRefNo.priceRunRefNo = ?").setString(0,priceRunRefNo).executeUpdate();
            if(BuildConfig.DMODE)
                System.out.println("No Of Rows Affecteds "+noOfRowsAffected);
            
            noOfRowsAffected = objSession.createQuery("delete from MatrixRunResultVO as matrixRunResultVO where matrixRunResultVO.priceRunRefNo = ?").setString(0,priceRunRefNo).executeUpdate();
            if(BuildConfig.DMODE)
                System.out.println("No Of Rows Affecteds "+noOfRowsAffected);
            
            noOfRowsAffected = objSession.createQuery("delete from MMPriceRunHeaderVO as mmPriceRunHeaderVO where mmPriceRunHeaderVO.priceRunRefNo = ?").setString(0,priceRunRefNo).executeUpdate();
            if(BuildConfig.DMODE)
                System.out.println("No Of Rows Affecteds "+noOfRowsAffected);
            
            objSession.getTransaction().commit();
            result = true;
            logger.info("RUN RESULT IS DELETED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DELETE THE RUN RESULT", e);
            e.printStackTrace();
            throw new HibernateException(e.getMessage());
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING DELETE THE RUN RESULT", e);
            e.printStackTrace();
            throw new HibernateException("General Error");
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return result;
    }
    
    public static void main(String[] args)
    {
        System.out.println(new MMPriceRunStatusDAO().getAllRunResults(null,"",null,"",null,null,0,10));
        //System.out.println(new MMPriceRunStatusDAO().deleteRunResult("09-03-2007 12:11:28"));
    }
    
}


/*
*$Log: MMPriceRunStatusDAO.java,v $
*Revision 1.2  2008/11/21 09:46:39  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/27 04:19:57  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.12  2007/09/20 11:47:43  spandiyarajan
*one more condition added for if user select between combo and give todate alone.
*
*Revision 1.11  2007/09/06 12:00:37  spandiyarajan
*between search bug fixed
*
*Revision 1.10  2007/09/04 13:09:40  jnadesan
*print removed
*
*Revision 1.9  2007/09/04 09:16:29  sramasamy
*Log message is added for log file.
*
*Revision 1.8  2007/09/03 11:50:35  spandiyarajan
*deleteRunResult() method added
*
*Revision 1.7  2007/09/03 09:30:29  spandiyarajan
*getRunresultDetails() method added
*
*Revision 1.6  2007/08/31 06:18:18  spandiyarajan
*aligned properly
*
*Revision 1.5  2007/08/29 07:42:40  jnadesan
*condition changed for on in date comparision
*
*Revision 1.4  2007/08/28 11:17:38  jnadesan
*condition added for offer date and exp date
*
*Revision 1.3  2007/08/27 04:42:27  jnadesan
*computaion for mAtrixPricer page
*
*Revision 1.2  2007/08/23 14:34:35  jnadesan
*restricted viewing both MM customers with normal customers
*
*Revision 1.1  2007/08/23 07:36:25  jnadesan
*initial commit MMPrice entry
*
*
*/