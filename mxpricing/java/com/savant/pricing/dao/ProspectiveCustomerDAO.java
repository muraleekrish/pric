/*
 * Created on Jan 24, 2007
 *
 * ClassName	:  	ProspectiveCustomerDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import com.savant.pricing.calculation.dao.PricingDAO;
import com.savant.pricing.calculation.valueobjects.CustomerPreferenceProductsVO;
import com.savant.pricing.calculation.valueobjects.CustomerPreferencesTermsVO;
import com.savant.pricing.calculation.valueobjects.CustomerPreferencesVO;
import com.savant.pricing.calculation.valueobjects.PICVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.securityadmin.dao.RolesDAO;
import com.savant.pricing.securityadmin.valueobject.UsersVO;
import com.savant.pricing.transferobjects.TeamDetails;
import com.savant.pricing.valueobjects.CDRStatusVO;
import com.savant.pricing.valueobjects.CustomerCommentsVO;
import com.savant.pricing.valueobjects.ESIIDDetailsVO;
import com.savant.pricing.valueobjects.ProductsVO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
 
public class ProspectiveCustomerDAO
{
    private static Logger logger = Logger.getLogger(ProspectiveCustomerDAO.class);
    
    public int addProspectiveCustomer(ProspectiveCustomerVO objProspectiveCustomerVO)
    {
        int prospectiveCustomerId=0;
        Session objSession = null;
        try
        {
            logger.info("ADDING PROSPECTIVE CUSTOMER DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.save(objProspectiveCustomerVO);
            prospectiveCustomerId = objProspectiveCustomerVO.getProspectiveCustomerId();
            objProspectiveCustomerVO.setCustomerId(new Integer(prospectiveCustomerId));
            objSession.update(objProspectiveCustomerVO);
            CustomerCommentsVO objCustomerCommentsVO = objProspectiveCustomerVO.getComments();
            if(objCustomerCommentsVO != null)
            {
                objCustomerCommentsVO.setProspectiveCustomer(objProspectiveCustomerVO);
                objCustomerCommentsVO.setVersion(1);
                objSession.save(objCustomerCommentsVO);
            }
            objSession.getTransaction().commit();
            objSession.flush();
           // this.getEsiidInfoFromCMS(objProspectiveCustomerVO.getProspectiveCustomerId());
            logger.info("PROSPECTIVE CUSTOMER DETAILS ARE ADDED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD THE PROSPECTIVE CUSTOMER DETAILS", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING ADD THE PROSPECTIVE CUSTOMER DETAILS", e);
            objSession.getTransaction().rollback();
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
        return prospectiveCustomerId;
    }
    
    public boolean deleteProspectiveCustomer(int prospectiveCustomerId)
    { 
        Session objSession = null;
        boolean result = false;
        try
        {
            logger.info("DELETING PROSPECTIVE CUSTOMER DETAILS");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select count(*) from PriceRunCustomerVO as priceRunCustomer, ContractsVO contracts, ContractsTrackingVO contractTracking where priceRunCustomer.prospectiveCustomer.prospectiveCustomerId = ? and priceRunCustomer.priceRunCustomerRefId = contracts.priceRunCustomerRef.priceRunCustomerRefId and contracts.contractIdentifier = contractTracking.contractRef.contractIdentifier and contractTracking.customerStatus.customerStatusId = ?");
            objQuery.setInteger(0,prospectiveCustomerId);
            // 2 for checking wheather customer is signed or not.
            objQuery.setInteger(1,2);
            Iterator itr = objQuery.iterate();
            int count = 0;
            if(itr.hasNext())
            {
                count = ((Long)itr.next()).intValue();
            }
            
            if(count==0)
            {
                objSession.beginTransaction();
                
                objQuery = objSession.createQuery("select priceRunCustomer.priceRunCustomerRefId from PriceRunCustomerVO as priceRunCustomer where priceRunCustomer.prospectiveCustomer.prospectiveCustomerId = ?");
                objQuery.setInteger(0, prospectiveCustomerId);
                itr = objQuery.iterate();
                String priceRunCustomerRefIds = "";
                while(itr.hasNext())
                {
                    if(priceRunCustomerRefIds.length()<=0)
                    {
                        priceRunCustomerRefIds = String.valueOf(itr.next());
                    }
                    else
                    {
                        priceRunCustomerRefIds = priceRunCustomerRefIds +","+String.valueOf(itr.next());
                    }
                }
                if(BuildConfig.DMODE)
                    System.out.println("priceRunCustomerRefIds:"+priceRunCustomerRefIds);
                
                new PricingDAO().deleteRunResult(priceRunCustomerRefIds);
                
                int noOfRowsAffected = objSession.createQuery("delete from ESIIDDetailsVO as esiIdDetails where esiIdDetails.prospectiveCustomer.prospectiveCustomerId = ?").setInteger(0,prospectiveCustomerId).executeUpdate();
                if(BuildConfig.DMODE)
                    System.out.println("No Of Rows Affecteds "+noOfRowsAffected);
                
                noOfRowsAffected = objSession.createQuery("delete from AggregatorLoadProfilesVO as aggregatedLoadProfile where aggregatedLoadProfile.prospectiveCustomer.prospectiveCustomerId = ?").setInteger(0,prospectiveCustomerId).executeUpdate();
                if(BuildConfig.DMODE)
                    System.out.println("No Of Rows Affecteds "+noOfRowsAffected);
                
                noOfRowsAffected = objSession.createQuery("delete from CustomerPreferencesVO as customerPreference where customerPreference.prospectiveCustomer.prospectiveCustomerId = ?").setInteger(0,prospectiveCustomerId).executeUpdate();
                if(BuildConfig.DMODE)
                    System.out.println("No Of Rows Affecteds "+noOfRowsAffected);
                
                noOfRowsAffected = objSession.createQuery("delete from CustomerPreferencesTermsVO as customerPreferenceTerms where customerPreferenceTerms.prospectiveCustomer.prospectiveCustomerId = ?").setInteger(0,prospectiveCustomerId).executeUpdate();
                if(BuildConfig.DMODE)
                    System.out.println("No Of Rows Affecteds "+noOfRowsAffected);
                
                noOfRowsAffected = objSession.createQuery("delete from CustomerPreferenceProductsVO as customerPreferenceProducts where customerPreferenceProducts.customer.prospectiveCustomerId = ?").setInteger(0,prospectiveCustomerId).executeUpdate();
                if(BuildConfig.DMODE)
                    System.out.println("No Of Rows Affecteds "+noOfRowsAffected);
                
                noOfRowsAffected = objSession.createQuery("delete from CustomerDealLeversVO as customerDealLevers where customerDealLevers.prospectiveCustomer.prospectiveCustomerId = ?").setInteger(0,prospectiveCustomerId).executeUpdate();
                if(BuildConfig.DMODE)
                    System.out.println("No Of Rows Affecteds "+noOfRowsAffected);
                
                noOfRowsAffected = objSession.createQuery("delete from CustomerDealLeversVO as customerDealLevers where customerDealLevers.prospectiveCustomer.prospectiveCustomerId = ?").setInteger(0,prospectiveCustomerId).executeUpdate();
                if(BuildConfig.DMODE)
                    System.out.println("No Of Rows Affecteds "+noOfRowsAffected);
                
                noOfRowsAffected = objSession.createQuery("delete from CustomerCommentsVO as customerComments where customerComments.prospectiveCustomer.prospectiveCustomerId = ?").setInteger(0,prospectiveCustomerId).executeUpdate();
                if(BuildConfig.DMODE)
                    System.out.println("No Of Rows Affecteds "+noOfRowsAffected);
                
                noOfRowsAffected = objSession.createQuery("delete from PICUsageVO as usage where usage.picRef.picReferenceId in(select picvo.picReferenceId from PICVO as picvo where picvo.customer.prospectiveCustomerId = ?)").setInteger(0,prospectiveCustomerId).executeUpdate();
                if(BuildConfig.DMODE)
                    System.out.println("No Of Rows Affected in PICUsageVO "+noOfRowsAffected);
               
                noOfRowsAffected = objSession.createQuery("delete from PICVO as picvo where picvo.customer.prospectiveCustomerId = ?").setInteger(0, prospectiveCustomerId).executeUpdate();
                if(BuildConfig.DMODE)
                    System.out.println("No Of Rows Affected in PICVO "+noOfRowsAffected);
                
                noOfRowsAffected = objSession.createQuery("delete from CustomerFilesVO as filesVo where filesVo.prospectiveCust.prospectiveCustomerId = ?").setInteger(0, prospectiveCustomerId).executeUpdate();
                if(BuildConfig.DMODE)
                    System.out.println("No Of Rows Affected in CustomerFilesVO "+noOfRowsAffected);
                
                noOfRowsAffected = objSession.createQuery("delete from CustEnergyComponentsVO as custenergycomponentsvo where custenergycomponentsvo.prcCustId.prospectiveCustomerId = ?").setInteger(0,prospectiveCustomerId).executeUpdate();
                if(BuildConfig.DMODE)
                    System.out.println("No Of Rows Affecteds "+noOfRowsAffected);
                               
                noOfRowsAffected = objSession.createQuery("delete from ProspectiveCustomerVO as customer where customer.prospectiveCustomerId = ?").setInteger(0,prospectiveCustomerId).executeUpdate();
                if(BuildConfig.DMODE)
                    System.out.println("No Of Rows Affecteds "+noOfRowsAffected);
                
               
                
                objSession.getTransaction().commit();
                result = true;
                logger.info("PROSPECTIVE CUSTOMER DETAILS ARE DELETED");
            }
            else
            {
                logger.info("PROSPECTIVE CUSTOMER DETAILS ARE NOT DELETED");
                logger.error("HIBERNATE EXCEPTION DURING DELETE FROM THE LIST");
                throw new HibernateException("Active customer cannot be deleted from the list");
            }
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DELETE THE PROSPECTIVE CUSTOMER DETAILS", e);
            e.printStackTrace();
            throw new HibernateException(e.getMessage());
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING DELETE THE PROSPECTIVE CUSTOMER DETAILS", e);
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
    
    public boolean getEsiidInfoFromCMS(int prospectiveCustomerId) throws SQLException
    { 
        Session objSession = null;
        boolean result = false;
        CallableStatement callableWriteIntoCMS = null;
        try
        {
            logger.info("GETTING ESIID INFORMATION FROM CMS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            callableWriteIntoCMS = objSession.connection().prepareCall("{call sp_GetESIIDInfo(?)}");
            callableWriteIntoCMS.setInt(1, prospectiveCustomerId);
            callableWriteIntoCMS.execute();
            objSession.getTransaction().commit();
            result = true;
            logger.info("GOT ESIID INFORMATION FROM CMS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ESIID INFORMATION FROM CMS", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
            result = false;
            throw new HibernateException(e.toString());
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING GET ESIID INFORMATION FROM CMS", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
            result = false;
            throw new HibernateException("General Error");
        }
        finally
        {
            if(callableWriteIntoCMS != null)
            {
                callableWriteIntoCMS.close();
            }
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return result;
    }
    
     public String getValidandtotalESIIDCount(int prsCustId)
    {
        Session objSession = null;
        String validESIID = "0";
        String totalEsiid = "0";
        objSession = HibernateUtil.getSession();
        List results = null; 
        try
        {
            results = objSession.createCriteria(PICVO.class) .add( Restrictions.eq("customer.prospectiveCustomerId",new Integer(prsCustId)) ).add(Restrictions.eq("valid",new Boolean(true)))
            .setProjection( Projections.projectionList().add( Projections.rowCount() )
            )
            .list();
            if(results.size()>0)
                validESIID = results.get(0).toString();
            results = objSession.createCriteria(PICVO.class) .add( Restrictions.eq("customer.prospectiveCustomerId",new Integer(prsCustId)) )
            .setProjection( Projections.projectionList().add( Projections.rowCount() )
            )
            .list();
            if(results.size()>0)
                totalEsiid = results.get(0).toString();
        }
        catch(HibernateException e) 
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL MM CUSTOMERS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL MM CUSTOMERS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return validESIID+" / "+totalEsiid;
        
    }
    
    public List getAllMMCustomers()
    {
        List lstResult = new ArrayList();
        Criteria objCriteria = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING ALL MM CUSTOMERS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ProspectiveCustomerVO.class);
            objCriteria.add(Restrictions.eq("mmCust",new Boolean(true)));
            lstResult =  objCriteria.list();
            logger.info("GOT ALL MM CUSTOMERS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL MM CUSTOMERS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL MM CUSTOMERS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return lstResult;
    }
    
    
    public boolean writeCDRIntoCMS(int prospectiveCustomerId) throws SQLException
    {
        Session objSession = null;
        boolean result = false;
        ResultSet rs = null;
        CallableStatement callableWriteIntoCMS = null; 
        try
        {
            logger.info("WRITING CDS INTO CMS BY PROSPECTIVE CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            callableWriteIntoCMS = objSession.connection().prepareCall("{call sp_UpdateCustomerDetailsfromPricingToCMS(?)}");
            callableWriteIntoCMS.setInt(1, prospectiveCustomerId);
            rs = callableWriteIntoCMS.executeQuery();
            int custId = 0;
            if(rs.next())
            {
                custId = rs.getInt(1);
            }
            objSession.getTransaction().commit();
            ProspectiveCustomerVO objProspectiveCustomerVO = this.getProspectiveCustomer(prospectiveCustomerId);
            objProspectiveCustomerVO.setCustomerId(new Integer(custId));
            objSession.beginTransaction();
            objSession.update(objProspectiveCustomerVO);
            objSession.getTransaction().commit();
            result = true;
            logger.info("CDS INTO CMS BY PROSPECTIVE CUSTOMER ID ENDS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING WRITE CDS INTO CMS BY PROSPECTIVE CUSTOMER ID", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
            result = false;
            throw new HibernateException(e.toString());
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING WRITE CDS INTO CMS BY PROSPECTIVE CUSTOMER ID", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
            result = false;
            throw new HibernateException("General Error");
        }
        finally
        {
            if(rs != null)
            {
                rs.close();
            }
            if(callableWriteIntoCMS != null)
            {
                callableWriteIntoCMS.close();
            }
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return result;
    }
    
    public boolean addESIIDDetails(ESIIDDetailsVO objESIIDDetailsVO)
    {
        Session objSession = null;
        boolean addResult = false;
        try
        {
            logger.info("ADDING ESIID DETAILS");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.save(objESIIDDetailsVO);
            objSession.getTransaction().commit();
            addResult = true;
            objSession.flush();
            logger.info("ESIID DETAILS ARE ADDED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD THE ESIID DETAILS", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
            if(e.toString().indexOf("hibernate.exception.ConstraintViolationException")!= -1)
            {
                throw new HibernateException("ESIID Already Exists");
            }
            throw new HibernateException("General Error");
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING ADD THE ESIID DETAILS", e);
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
        return addResult;
    }
    
    public List getAllESIIDDetails(int prospectiveCustomerId)
    {
        Session objSession = null;
        List objList = null;
        Criteria objCriteria = null;
        try
        {
            logger.info("GETTING ALL ESIID DETAILS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ESIIDDetailsVO.class).add(Restrictions.eq("prospectiveCustomer.prospectiveCustomerId", new Integer(prospectiveCustomerId)));
            objList = objCriteria.list();
            logger.info("GOT ALL ESIID DETAILS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ESIID DETAILS", e);
            e.printStackTrace();
            throw new HibernateException("General Error");
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL ESIID DETAILS", e);
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
        return objList;
    }
    
    public boolean updateProspectiveCustomer(ProspectiveCustomerVO objProspectiveCustomerVO)
    {
        boolean updateResult = false;
        Session objSession = null;
        int version = 0;
        try
        {
            logger.info("UPDATING PROSPECTIVE CUSTOMER DETAILS");
            int prospectiveCustomerId = objProspectiveCustomerVO.getProspectiveCustomerId();
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.update(objProspectiveCustomerVO);
            CustomerCommentsVO objCustomerCommentsVO = objProspectiveCustomerVO.getComments();
            if(objCustomerCommentsVO != null)
            {
                Query objQuery = objSession.createQuery("select max(commentsVO.version) from CustomerCommentsVO as commentsVO where commentsVO.prospectiveCustomer.prospectiveCustomerId = ?");
                objQuery.setInteger(0, prospectiveCustomerId);
                Iterator itr = objQuery.iterate();
                if(itr.hasNext())
                {
                    Object obj = itr.next();
                    if(obj != null)
                        version = ((Integer)obj).intValue();
                }
                objCustomerCommentsVO.setProspectiveCustomer(objProspectiveCustomerVO);
                objCustomerCommentsVO.setVersion(version+1);
                objSession.save(objCustomerCommentsVO);
            }
            updateResult = true;
            objSession.getTransaction().commit();
            objSession.flush();
            //this.getEsiidInfoFromCMS(prospectiveCustomerId);
            logger.info("PROSPECTIVE CUSTOMER DETAILS ARE UPDATED");
            /*CallableStatement callableWriteIntoCMS;
             objSession.beginTransaction();
             callableWriteIntoCMS = objSession.connection().prepareCall("{call sp_UpdateCustomerDetailsfromPricingToCMS(?)}");
             callableWriteIntoCMS.setInt(1, objProspectiveCustomerVO.getProspectiveCustomerId());
             callableWriteIntoCMS.executeUpdate();
             objSession.getTransaction().commit();*/
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE PROSPECTIVE CUSTOMER DETAILS", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING UPDATE THE PROSPECTIVE CUSTOMER DETAILS", e);
            objSession.getTransaction().rollback();
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
        return updateResult;
    }
    
    public boolean updatecustPreference(boolean autorun, int prsCustId)
    {
        CustomerPreferencesVO objCustomerPreferencesVO = this.getProspectiveCustomerPreferences(prsCustId);
        if(objCustomerPreferencesVO==null)
        {
            objCustomerPreferencesVO = new CustomerPreferencesVO();
            ProspectiveCustomerVO objProspectiveCustomerVO=  new ProspectiveCustomerVO();
            objProspectiveCustomerVO.setProspectiveCustomerId(prsCustId);
            objCustomerPreferencesVO.setProspectiveCustomer(objProspectiveCustomerVO);
        }
        objCustomerPreferencesVO.setAutoRun(autorun);            
        Session objSession = null;
        boolean updateResult = false;
        try
        {
            logger.info("UPDATING CUSTOMER PREFERENCE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.saveOrUpdate(objCustomerPreferencesVO);
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("CUSTOMER PREFERENCE IS UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE CUSTOMER PREFERENCE", e);
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
    
    public boolean updateProspectiveStartDate(String prospectiveCustIds, Calendar stratDate)
    { 
        Session objSession = null;
        Calendar objCalendarEndDate = Calendar.getInstance();
        objCalendarEndDate.set(stratDate.get(Calendar.YEAR),stratDate.get(Calendar.MONTH)+59,1);
        boolean result = false;
        try
        {
            logger.info("UPDATING PROSPECTIVE START DATE");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            Query objQuery = objSession.createSQLQuery("UPDATE dbo.PRC_Prospective_Cust SET Contract_Start_Date = ? WHERE Contract_Start_Date < ? AND Prospective_Cust_ID IN ("+prospectiveCustIds+")");
            objQuery.setDate(0,stratDate.getTime());
            objQuery.setDate(1,stratDate.getTime());
            int resultCount = objQuery.executeUpdate();
            
            Query objQueryPreference = objSession.createSQLQuery("UPDATE dbo.PRC_Cust_Preference SET Contract_Start_Date = ? ,Contract_End_Date = ? WHERE Contract_Start_Date < ? AND Prospective_Cust_ID IN ("+prospectiveCustIds+")");
            objQueryPreference.setDate(0,stratDate.getTime());
            objQueryPreference.setDate(1,objCalendarEndDate.getTime());
            objQueryPreference.setDate(2,stratDate.getTime());
            
            resultCount = objQueryPreference.executeUpdate();
            if(resultCount>0)
                result = true;
            objSession.getTransaction().commit();
            logger.info("PROSPECTIVE START DATE IS UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE PROSPECTIVE START DATE", e);
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
        return result;
    } 
    
    public boolean updateStartDate(String custIds)
    {
        boolean result = false;
        try
        {
            logger.info("UPDATING START DATE BY CUSTOMER IDs");
            ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
            Calendar objCalendar = Calendar.getInstance();
            Calendar tempCalendar = Calendar.getInstance();
            objCalendar.clear();
            Date objDate = GasPriceDAO.getContractStartMonth();
            if(objDate!=null)
                objCalendar.setTime(objDate);
            else
                objCalendar.set(tempCalendar.get(Calendar.YEAR),tempCalendar.get(Calendar.MONTH)+1,1);
            objProspectiveCustomerDAO.updateProspectiveStartDate(custIds,objCalendar);
            result = true;
            logger.info("START DATE IS UPDATED");
        }  
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING UPDATE THE START DATE BY CUSTOMER IDs", e);
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public boolean updtaeProspectiveCommentsIntoCMS(int prospectiveCustId, int cmsId)
    {
        Session objSession = null;
        boolean result = false;
        try
        {
            logger.info("UPDATING PROSPECTIVE COMMENTS INTO CMS BY CUSTOMER ID AND CMS ID");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.connection().prepareCall("{call sp_PricingCommentsIntoCMS('"+prospectiveCustId+"','"+cmsId+"')}").executeUpdate();
            objSession.getTransaction().commit();
            result = true;
            logger.info("PROSPECTIVE COMMENTS INTO CMS IS UPDATED");
        }
        catch (Exception e) 
        {
            logger.error("GENERAL EXCEPTION DURING UPDATE THE PROSPECTIVE COMMENTS INTO CMS", e);
            objSession.getTransaction().rollback();
            result = false;
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
    
    public boolean updateProspectiveCustomerStatus(int prospectiveCustomerId, int statusId)
    {
        boolean updateResult = false;
        Session objSession = null;
        try
        {
            logger.info("UPDATING PROSPECTIVE CUSTOMER STATUS BY CUSTOMER ID AND STATUS ID");
            objSession = HibernateUtil.getSession();
            ProspectiveCustomerVO customerVO = this.getProspectiveCustomer(prospectiveCustomerId);
            CDRStatusVO statusVO = customerVO.getCdrStatus();
            statusVO.setCdrStateId(statusId);
            customerVO.setCdrStatus(statusVO);
            objSession.beginTransaction();
            objSession.update(customerVO);
            updateResult = true;
            objSession.getTransaction().commit();
            objSession.flush();
            logger.info("PROSPECTIVE CUSTOMER STATUS IS UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DUTING UPDATE THE PROSPECTIVE CUSTOMER STATUS", e);
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
    
    public ProspectiveCustomerVO getProspectiveCustomer(int prospectiveCustomerId)
    {
        ProspectiveCustomerVO objProspectiveCustomerVO = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING PROSPECTIVE CUSTOMER BY CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            objProspectiveCustomerVO = (ProspectiveCustomerVO)objSession.get(ProspectiveCustomerVO.class,new Integer(prospectiveCustomerId));
            logger.info("GOT PROSPECTIVE CUSTOMER BY CUSTOMER ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE PROSPECTIVE CUSTOMER", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING UPDATE THE PROSPECTIVE CUSTOMER", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objProspectiveCustomerVO;
    }
    
    public boolean checkProspectiveCustomer(Integer customerId)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        try
        {
            logger.info("CHECKING PROSPECTIVE CUSTOMER BY CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ProspectiveCustomerVO.class).add(Restrictions.eq("customerId", customerId));
            Iterator itr = objCriteria.list().iterator();
            if(itr.hasNext())
            {
                logger.info("PROSPECTIVE CUSTOMER IS AVAILABLE");
                return true;
            }
            else
            {
                logger.info("PROSPECTIVE CUSTOMER IS NOT AVAILABLE");
            }
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING CHECK THE PROSPECTIVE CUSTOMER", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING CHECK THE PROSPECTIVE CUSTOMER", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return false;
    }
    
    public String getAllAutoRunProspectives()
    {
        Session objSession = null;
        String prospectives = "";
        try
        {
            logger.info("GETTING ALL AUTO RUN PROSPECTIVES");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select customerPref.prospectiveCustomer.prospectiveCustomerId from CustomerPreferencesVO as customerPref where customerPref.autoRun = ? and customerPref.prospectiveCustomer.mmCust = ? and customerPref.contractStartDate  is not null");
            objQuery.setBoolean(0, true);
            objQuery.setBoolean(1, false);
            Iterator itr = objQuery.iterate();
            while(itr.hasNext())
            {
                if(prospectives.length()<=0)
                     prospectives = String.valueOf(itr.next());
                else
                	 prospectives = prospectives+","+String.valueOf(itr.next());
            }
            logger.info("GOT ALL AUTO RUN PROSPECTIVES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL AUTO RUN PROSPECTIVES", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL AUTO RUN PROSPECTIVES", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return prospectives;
    }
    
    
    public LinkedHashMap getAllLocationTypesFromCMS()
    {
        Session objSession = null;
        LinkedHashMap lhm = new LinkedHashMap();
        try
        {
            logger.info("GETTING ALL LOCATION TYPES ");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createSQLQuery("SELECT [LocationTypeId], [Name] FROM [dbo].[PRC_LocationType] order by [Name]");
            Iterator itr = objQuery.list().iterator();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                lhm.put(innerRow[0], innerRow[1]);
            }
            logger.info("GOT ALL LOCATION TYPES ");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL LOCATION TYPES ", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return lhm;
    }
    
    public LinkedHashMap getAllAddressTypesFromCMS()
    {
        Session objSession = null;
        LinkedHashMap lhm = new LinkedHashMap();
        try
        {
            logger.info("GETTING ALL ADDRESS TYPES ");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createSQLQuery("SELECT [AddressTypeId], [Name] FROM [dbo].[PRC_AddressType] order by [Name]");
            Iterator itr = objQuery.list().iterator();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                lhm.put(innerRow[0], innerRow[1]);
            }
            logger.info("GOT ALL ADDRESS TYPES ");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ADDRESS TYPES ", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return lhm;
    }
    
    public boolean checkPostalCodeInCMS(double zipCode)
    {
        Session objSession = null;
        boolean isAvail = false;
        try
        {
            logger.info("CHECKING POSTAL CODE  BY ZIP CODE");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createSQLQuery("SELECT zip_code FROM [dbo].[PRC_Zip_Code] WHERE [zip_code] = ? ");
            objQuery.setDouble(0, zipCode);
            Iterator itr = objQuery.list().iterator();
            if(itr.hasNext())
            {
                isAvail = true;
                logger.info("POSTAL CODE IS AVAILABLE");
            }
            else
            {
                logger.info("POSTAL CODE IS NOT AVAILABLE");
            }
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING CHECK THE POSTAL CODE ", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return isAvail;
    }
    
    public LinkedHashMap getAllStatesFromCMS()
    {
        Session objSession = null;
        LinkedHashMap lhm = new LinkedHashMap();
        try
        {
            logger.info("GETTING ALL STATUS FROM CMS");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createSQLQuery("SELECT [StateId], [Name] FROM [dbo].[PRC_State] order by [Name]");
            Iterator itr = objQuery.list().iterator();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                lhm.put(innerRow[0], innerRow[1]);
            }
            logger.info("GOT ALL STATUS FROM CMS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL STATUS ", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return lhm;
    }
    
    public ProspectiveCustomerVO getCDRDetailsFromCMS(int customerId)
    {
        ProspectiveCustomerVO objProspectiveCustomerVO = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING CDR DETAILS  BY CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createSQLQuery("select v.Name, v.FirstName, v.LastName, v.Street, v.City, state.Name as state, v.PostalCode, v.PhoneNumber, v.DBA, v.LocationTypeId, v.AddressTypeId from vwPRCCustomerBillingContactsRpt v left outer join State as state on  v.StateCode = state.StateCode where v.CustomerId = ?"); // New query genertated by Vani 
            // select v.Name, v.FirstName, v.LastName, v.Street, v.City, state.Name as state, v.PostalCode, v.PhoneNumber, v.DBA, v.LocationTypeId, v.AddressTypeId from vwPRCCustomerBillingContactsRpt as v, CustomerManagementSystem..State as state  where v.StateCode = state.StateCode and v.CustomerId = ? // Old one
            objQuery.setInteger(0, customerId);
            List objList = objQuery.list();
            Iterator itr = objList.iterator();
            if(itr.hasNext())
            {
                objProspectiveCustomerVO = new ProspectiveCustomerVO();
                Object[] innerRow = (Object[]) itr.next();
                objProspectiveCustomerVO.setCustomerName((String)innerRow[0]);
                objProspectiveCustomerVO.setCustomerId(new Integer(customerId));
                objProspectiveCustomerVO.setPocFirstName((String)innerRow[1]);
                objProspectiveCustomerVO.setPocLastName((String)innerRow[2]);
                objProspectiveCustomerVO.setTitle("");
                objProspectiveCustomerVO.setAddress((String)innerRow[3]);
                objProspectiveCustomerVO.setCity((String)innerRow[4]);
                objProspectiveCustomerVO.setState((String)innerRow[5]);
                if(innerRow[6] != null)
                    objProspectiveCustomerVO.setZipCode(String.valueOf(innerRow[6]));
                objProspectiveCustomerVO.setPhone((String)innerRow[7]);
                objProspectiveCustomerVO.setFax("");
                objProspectiveCustomerVO.setMobile("");
                objProspectiveCustomerVO.setEmail("");
                objProspectiveCustomerVO.setCurrentProvider("");
                objProspectiveCustomerVO.setBusinessType("");
                objProspectiveCustomerVO.setContractOpenDate(new Date());
                objProspectiveCustomerVO.setValid(true);
                objProspectiveCustomerVO.setCustomerDBA((String)innerRow[8]);
                objProspectiveCustomerVO.setContractStartDate(new Date());
                objProspectiveCustomerVO.setLocationTypeId(innerRow[9]==null?0:((Integer)(innerRow[9])).intValue());
                objProspectiveCustomerVO.setAddressTypeId(innerRow[10]==null?0:((Integer)(innerRow[10])).intValue());
            }
            logger.info("GOT CDR DETAILS  BY CUSTOMER ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET CDR DETAILS ", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET CDR DETAILS ", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objProspectiveCustomerVO;
    }
    
    public List getProspectiveCustomers(String prospectiveCustomerIds)
    {
        Session objSession = null;
        Criteria objCriteria = null;
        List objList = null;
        try
        {
            logger.info("GETTING PROSPECTIVE CUSTOMERS BY PROSPECTIVE CUSTOMER IDs");
            objSession = HibernateUtil.getSession();
            StringTokenizer st = new StringTokenizer(prospectiveCustomerIds, ",");
            Integer[] objInteger = new Integer[st.countTokens()];
            int index = 0; 
            while(st.hasMoreTokens())
            {
                objInteger[index++] =new Integer(st.nextToken());
            }
            objCriteria = objSession.createCriteria(ProspectiveCustomerVO.class);
            objCriteria.add(Restrictions.in("prospectiveCustomerId",objInteger)).addOrder(Order.asc("customerId"));
            objList = objCriteria.list();
            logger.info("GOT PROSPECTIVE CUSTOMERS BY PROSPECTIVE CUSTOMER IDs");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET PROSPECTIVE CUSTOMERS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET PROSPECTIVE CUSTOMERS", e);
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
    
    public boolean isCDRApprove(int prospectiveCustomerId)
    {
        Session objSession = null;
        Criteria objCriteria = null;
        boolean result = false;
        try
        {
            logger.info("CHECKING THE CUSTOMER WHETHER CDR IS APPROVER OR NOT");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ProspectiveCustomerVO.class).add(Restrictions.eq("prospectiveCustomerId",new Integer(prospectiveCustomerId))).createCriteria("cdrStatus").add(Restrictions.eq("cdrStateId",new Integer(1)));
            Iterator itr = objCriteria.list().iterator();
            if(itr.hasNext())
            {
                result = true;
                logger.info("CDR IS APPROVED");
            }
            else
            {
                logger.info("CDR IS NOT APPROVED");
            }
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING CHECK THE CUSTOMER", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING CHECK THE CUSTOMER", e);
            e.printStackTrace();
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
    
    public TeamDetails getTeam(int prospectiveCustomerId)
    {
        TeamDetails objTeamDetails = new TeamDetails();
        try
        {
            logger.info("GETTING TEAM DETAILS");
            ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
            ProspectiveCustomerVO objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(prospectiveCustomerId);
            if(BuildConfig.DMODE)
                System.out.println("Prospective customer Id:"+prospectiveCustomerId+"  "+objProspectiveCustomerVO.getSalesRep());
            UsersVO objSalesRep = objProspectiveCustomerVO.getSalesRep();
            
            RolesDAO objRolesDAO = new RolesDAO();
            String roleName = objRolesDAO.getRoleName(objSalesRep.getUserId());
            UsersVO objSalesManager = null;
            
            if(roleName.equals("SALES REP"))
            {
                if(objSalesRep != null)
                {
                    objSalesManager = objSalesRep.getParentUser();
                }
                objTeamDetails.setSalesRep(objSalesRep);
                objTeamDetails.setSalesManager(objSalesManager);
                objTeamDetails.setPricingAnalyst(objSalesManager.getParentUser());
            }
            else if(roleName.equals("SALES MANAGER"))
            {
                objTeamDetails.setSalesRep(objSalesRep);
                objTeamDetails.setSalesManager(objSalesRep);
                objSalesManager = objSalesRep;
                objTeamDetails.setPricingAnalyst(objSalesManager.getParentUser());
            }
            else
            {
                objTeamDetails.setSalesRep(objSalesRep);
                objTeamDetails.setSalesManager(objSalesRep);
                objTeamDetails.setPricingAnalyst(objSalesRep);
            }
            logger.info("GOT TEAM DETAILS");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET THE TEAM DETAILS", e);
            e.printStackTrace();
        }
        return objTeamDetails;
    }
    
    public HashMap getAllProspectiveCustomer(Filter filter, Filter filtSalesRep, Filter filtSalesManager, int cmsId, int custStatusId, int cdrStatusId,  String crFromDate, String crToDate, String mdFromDate, String mdToDate, String sortBy, boolean ascending, int startIndex, int displayCount, int autoRun, String strMMCust)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria = null;
        Criteria objSalesRepCriteria = null;
        Session objSession = null;
        Integer totRecordCount = null;
        boolean mmCust = false;
        List objList = null;
        HashMap hmResult = new HashMap();
        Calendar cal = Calendar.getInstance();
        if(strMMCust.equalsIgnoreCase("1"))
            mmCust = true;
        Date createFromDate = null; Date createToDate = null; Date modifiedFromDate = null; Date modifiedToDate = null;
        SimpleDateFormat sdf =  new SimpleDateFormat("MM-dd-yyyy");
        try
        {
            logger.info("GETTING ALL PROSPECTIVE CUSTOMER");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ProspectiveCustomerVO.class).add(Restrictions.eq("valid", new Boolean(true)));
            objCriteria.add(Restrictions.eq("mmCust",new Boolean(mmCust)));
            if(filter != null)
            {
                objCriteria.add(Restrictions.like(filter.getFieldName(),filter.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filter.getSpecialFunction())));
            }
            if(filtSalesRep != null)
            {
                objSalesRepCriteria = objCriteria.createCriteria("salesRep");
                Criterion objFirstName = Restrictions.like("firstName",filtSalesRep.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filtSalesRep.getSpecialFunction())); 
                Criterion objLastName = Restrictions.like("lastName",filtSalesRep.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filtSalesRep.getSpecialFunction())); 
                objSalesRepCriteria.add(Restrictions.or(objFirstName, objLastName));
            }
            if(filtSalesManager != null)
            {
                if(objSalesRepCriteria == null)
                {
                    objSalesRepCriteria = objCriteria.createCriteria("salesRep");
                }
                Criteria objParentUser = objSalesRepCriteria.createCriteria("parentUser");
                Criterion objFirstName = Restrictions.like("firstName",filtSalesManager.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filtSalesManager.getSpecialFunction())); 
                Criterion objLastName = Restrictions.like("lastName",filtSalesManager.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filtSalesManager.getSpecialFunction()));
                objParentUser.add(Restrictions.or(objFirstName, objLastName));
            }
            if(cmsId != 0)
            {
                objCriteria.add(Restrictions.eq("customerId", new Integer(cmsId)));
            }
            if(custStatusId != 0)
            {
                objCriteria.createCriteria("customerStatus").add(Restrictions.eq("customerStatusId", new Integer(custStatusId)));
            }
            if(cdrStatusId != 0)
            {
                objCriteria.createCriteria("cdrStatus").add(Restrictions.eq("cdrStateId", new Integer(cdrStatusId)));
            }
            if(!crFromDate.trim().equals(""))
                createFromDate = sdf.parse(crFromDate);
            if(!crToDate.trim().equals(""))
            {
                createToDate = sdf.parse(crToDate);
                cal.setTime(createToDate);
                cal.add(Calendar.DATE, 1);
                cal.add(Calendar.SECOND,-1);
                createToDate = cal.getTime();
            }
            if(!mdFromDate.trim().equals(""))
                modifiedFromDate = sdf.parse(mdFromDate);
            if(!mdToDate.trim().equals(""))
            {
                modifiedToDate = sdf.parse(mdToDate);
                cal.setTime(modifiedToDate);
                cal.add(Calendar.DATE, 1);
                cal.add(Calendar.SECOND,-1);
                modifiedToDate = cal.getTime();
            }
            if(createFromDate != null && createToDate != null)
                objCriteria.add(Restrictions.between("createdDate", createFromDate, createToDate));
            else if(createFromDate != null)
                objCriteria.add(Restrictions.ge("createdDate", createFromDate));
            else if(createToDate != null)
                objCriteria.add(Restrictions.le("createdDate", createToDate));
            
            if(modifiedFromDate != null && modifiedToDate != null)
                objCriteria.add(Restrictions.between("modifiedDate",modifiedFromDate, modifiedToDate));
            else if(modifiedFromDate != null)
                objCriteria.add(Restrictions.ge("modifiedDate", modifiedFromDate));
            else if(modifiedToDate != null)
                objCriteria.add(Restrictions.le("modifiedDate", modifiedToDate));
            if(autoRun==1)
            {
                objCriteria.createCriteria("customerPreference").add(Restrictions.eq("autoRun", new Boolean(true)));
            }
            else if(autoRun == 2)
            {
                objCriteria.createCriteria("customerPreference").add(Restrictions.eq("autoRun", new Boolean(false)));
        	}
            totRecordCount = new Integer(objCriteria.list().size());
            if(ascending)
            {
                objCriteria.addOrder(Order.asc(sortBy));
            }
            else
            {
                objCriteria.addOrder(Order.desc(sortBy));
            }
            if(startIndex !=-1 && displayCount !=-1 )
            {
                objCriteria.setFirstResult(startIndex);
                objCriteria.setMaxResults(displayCount);
            }
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",objList);
            logger.info("GOT ALL PROSPECTIVE CUSTOMER");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL PROSPECTIVE CUSTOMER", e); 
            e.printStackTrace();
        }
        catch(ParseException e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL PROSPECTIVE CUSTOMER", e);
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
    
    public HashMap getAllProspectiveCustomerBySalesRep(String salesRep, Filter filter,int cmsId, int custStatusId, int cdrStatusId, String sortBy, boolean ascending, int startIndex, int displayCount, int autoRun)
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
            logger.info("GETTING ALL PROSPECTIVE CUSTOMER BY SALES REP");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ProspectiveCustomerVO.class).add(Restrictions.eq("valid", new Boolean(true))).add(Restrictions.eq("salesRep.userId",salesRep));
            objCriteria.add(Restrictions.eq("mmCust",new Boolean(false)));
            if(filter != null)
            {
                objCriteria.add(Restrictions.like(filter.getFieldName(),filter.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filter.getSpecialFunction())));
            }
            if(cmsId != 0)
            {
                objCriteria.add(Restrictions.eq("customerId", new Integer(cmsId)));
            }
            
            if(custStatusId != 0)
            {
                objCriteria.createCriteria("customerStatus").add(Restrictions.eq("customerStatusId", new Integer(custStatusId)));
            }
            
            if(cdrStatusId != 0)
            {
                objCriteria.createCriteria("cdrStatus").add(Restrictions.eq("cdrStateId", new Integer(cdrStatusId)));
            }
            if(autoRun==1)
            {
                objCriteria.createCriteria("customerPreference").add(Restrictions.eq("autoRun", new Boolean(true)));
            }
            else if(autoRun == 2)
            {
                objCriteria.createCriteria("customerPreference").add(Restrictions.eq("autoRun", new Boolean(false)));
        	}
            totRecordCount = new Integer(objCriteria.list().size());
            if(sortBy.length()>0)
            {
                if(ascending)
                {
                    objCriteria.addOrder(Order.asc(sortBy));
                }
                else
                {
                    objCriteria.addOrder(Order.desc(sortBy));
                }
            }
            if(startIndex !=-1 && displayCount !=-1)
            {
                objCriteria.setFirstResult(startIndex);
                objCriteria.setMaxResults(displayCount);
            }
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount", totRecordCount);
            hmResult.put("Records", objList);
            logger.info("GOT ALL PROSPECTIVE CUSTOMER BY SALES REP");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL PROSPECTIVE CUSTOMER BY SALES REP", e);
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
    
    public HashMap getAllProspectiveCustomerBySalesManager(String salesManager, Filter filtSalesRep, Filter filter,int cmsId, int custStatusId, int cdrStatusId, int startIndex, int displayCount, int autoRun)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */
        Criteria objCriteria = null;
        Criteria objSalesRepCriteria = null;
        Session objSession = null;
        Integer totRecordCount = null;
        List objList = null;
        HashMap hmResult = new HashMap();
        try
        {
            logger.info("GETTING ALL PROSPECTIVE CUSTOMER BY SALES MANAGER");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(ProspectiveCustomerVO.class).add(Restrictions.eq("valid", new Boolean(true)));
            objCriteria.add(Restrictions.eq("mmCust",new Boolean(false)));
            objSalesRepCriteria = objCriteria.createCriteria("salesRep");
            Criterion objAddedBy = Restrictions.like("userId", salesManager);
            Criterion objParentUserCustomers = Restrictions.like("parentUser.userId", salesManager);
            objSalesRepCriteria.add(Restrictions.or(objAddedBy, objParentUserCustomers));
            
            if(filter != null)
            {
                objCriteria.add(Restrictions.like(filter.getFieldName(),filter.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filter.getSpecialFunction())));
            }
            if(filtSalesRep != null)
            {
                Criterion objFirstName = Restrictions.like("firstName",filtSalesRep.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filtSalesRep.getSpecialFunction())); 
                Criterion objLastName = Restrictions.like("lastName",filtSalesRep.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filtSalesRep.getSpecialFunction())); 
                objSalesRepCriteria.add(Restrictions.or(objFirstName, objLastName));
            }
            if(cmsId != 0)
            {
                objCriteria.add(Restrictions.eq("customerId", new Integer(cmsId)));
            }
            
            if(custStatusId != 0)
            {
                objCriteria.createCriteria("customerStatus").add(Restrictions.eq("customerStatusId", new Integer(custStatusId)));
            }
            
            if(cdrStatusId != 0)
            {
                objCriteria.createCriteria("cdrStatus").add(Restrictions.eq("cdrStateId", new Integer(cdrStatusId)));
            }
            if(autoRun==1)
            {
                objCriteria.createCriteria("customerPreference").add(Restrictions.eq("autoRun", new Boolean(true)));
            }
            else if(autoRun == 2)
            {
                objCriteria.createCriteria("customerPreference").add(Restrictions.eq("autoRun", new Boolean(false)));
        	}
            totRecordCount = new Integer(objCriteria.list().size());
            if(startIndex !=-1 && displayCount !=-1)
            {
                objCriteria.setFirstResult(startIndex);
                objCriteria.setMaxResults(displayCount);
            }
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records", objList);
            logger.info("GOT ALL PROSPECTIVE CUSTOMER BY SALES MANAGER");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL PROSPECTIVE CUSTOMER BY SALES MANAGER", e);
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
    
    /*public List getAllProspectiveCustomerByPricingAnalyst(String pricingAnalyst)
     {
     List objList = null;
     List objResultList = new ArrayList();
     Session objSession = null;
     try
     {
     objSession = HibernateUtil.getSession();
     //to fetch all the sales Manager under the pricing Analyst...
      
      objList = objSession.createCriteria(UsersVO.class).add(Restrictions.eq("parentUser.userId",pricingAnalyst)).list();
      Iterator itr = objList.iterator();
      while(itr.hasNext())
      {
      UsersVO objUsersVO = (UsersVO)itr.next();
      objResultList.addAll(getAllProspectiveCustomerBySalesManager(objUsersVO.getUserId()));
      }
      }
      catch(HibernateException e)
      {
      e.printStackTrace();
      }
      finally
      {
      objSession.close();
      }
      return objResultList;
      }
      */
    
    public CustomerPreferencesVO getProspectiveCustomerPreferences(int prospectiveCustomerId)
    {
        Session objSession = null;
        CustomerPreferencesVO objCustomerPreferenceVO = null;
        try
        {
            logger.info("GETTING PROSPECTIVE CUSTOMER PREFERENCES BY CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            objCustomerPreferenceVO = (CustomerPreferencesVO)objSession.createCriteria(CustomerPreferencesVO.class).add(Restrictions.eq("prospectiveCustomer.prospectiveCustomerId", new Integer(prospectiveCustomerId))).uniqueResult();
            logger.info("GOT PROSPECTIVE CUSTOMER PREFERENCES BY CUSTOMER ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET PROSPECTIVE CUSTOMER PREFERENCES", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objCustomerPreferenceVO;
    }
    
    public List getProspectiveCustomerPreferenceTerms(int prospectiveCustomerId)
    {
        Session objSession = null;
        Criteria objCriteria = null;
        List objList = null;
        try
        {
            logger.info("GETTING PROSPECTIVE CUSTOMER PREFERENCE TERMS BY CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(CustomerPreferencesTermsVO.class).add(Restrictions.eq("prospectiveCustomer.prospectiveCustomerId", new Integer(prospectiveCustomerId))).addOrder(Order.asc("term"));
            objList = objCriteria.list();
            logger.info("GOT PROSPECTIVE CUSTOMER PREFERENCE TERMS BY CUSTOMER ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET PROSPECTIVE CUSTOMER PREFERENCE TERMS BY CUSTOMER ID", e);
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
    
    public List getProspectiveCustomerPreferenceProducts(int prospectiveCustomerId)
    {
        Session objSession = null;
        Criteria objCriteria = null;
        List objList = null;
        try
        {
            logger.info("GETTING PROSPECTIVE CUSTOMER PREFERENCE PRODUCTS BY CUSTOMER ID");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(CustomerPreferenceProductsVO.class).add(Restrictions.eq("customer.prospectiveCustomerId", new Integer(prospectiveCustomerId)));
            objList = objCriteria.list();
            logger.info("GOT PROSPECTIVE CUSTOMER PREFERENCE PRODUCTS BY CUSTOMER ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET PROSPECTIVE CUSTOMER PREFERENCE PRODUCTS", e);
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
    
    public boolean addProspectiveCustomerPreferences(CustomerPreferencesVO objCustomerPreferencesVO,String terms, String productIds)
    {
        Session objSession = null;
        boolean addResult = false;
        try
        {
            logger.info("ADDING PROSPECTIVE CUSTOMER PREFERENCES");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.save(objCustomerPreferencesVO);
            StringTokenizer st = new StringTokenizer(terms,",");
            CustomerPreferencesTermsVO objCustomerPreferencesTermsVO = null;
            while (st.hasMoreTokens())
            {
                objCustomerPreferencesTermsVO = new CustomerPreferencesTermsVO();
                objCustomerPreferencesTermsVO.setTerm(Integer.parseInt(st.nextToken().trim()));
                objCustomerPreferencesTermsVO.setProspectiveCustomer(objCustomerPreferencesVO.getProspectiveCustomer());
                objSession.save(objCustomerPreferencesTermsVO);
            }
            st = new StringTokenizer(productIds,",");
            CustomerPreferenceProductsVO objCustomerPreferenceProductsVO = null;
            while(st.hasMoreTokens())
            {
                objCustomerPreferenceProductsVO = new CustomerPreferenceProductsVO();
                ProductsVO objProductsVO = new ProductsVO();
                objProductsVO.setProductIdentifier(Integer.parseInt(st.nextToken().trim()));
                objCustomerPreferenceProductsVO.setProduct(objProductsVO);
                objCustomerPreferenceProductsVO.setCustomer(objCustomerPreferencesVO.getProspectiveCustomer());
                objSession.save(objCustomerPreferenceProductsVO);
            }
            objSession.getTransaction().commit();
            addResult = true;
            logger.info("PROSPECTIVE CUSTOMER PREFERENCES ARE ADDED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD THE PROSPECTIVE CUSTOMER PREFERENCES", e);
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
        return addResult;
    }
    
    public boolean updateProspectiveCustomerPreferences(CustomerPreferencesVO objCustomerPreferencesVO, String terms, String productIds)
    {
        Session objSession = null;
        boolean updateResult = false;
        try
        {
            logger.info("UPDATING PROSPECTIVE CUSTOMER PREFERENCES");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.saveOrUpdate(objCustomerPreferencesVO);
            Query objQuery = objSession.createQuery("delete from CustomerPreferencesTermsVO as preferencetermsvo where preferencetermsvo.prospectiveCustomer.prospectiveCustomerId = ?");
            objQuery.setInteger(0, objCustomerPreferencesVO.getProspectiveCustomer().getProspectiveCustomerId());
            objQuery.executeUpdate();
            
            objQuery = objSession.createQuery("delete from CustomerPreferenceProductsVO as preferenceproductsvo where preferenceproductsvo.customer.prospectiveCustomerId = ?");
            objQuery.setInteger(0, objCustomerPreferencesVO.getProspectiveCustomer().getProspectiveCustomerId());
            objQuery.executeUpdate();
            
            StringTokenizer st = new StringTokenizer(terms,",");
            CustomerPreferencesTermsVO objCustomerPreferencesTermsVO = null;
            while (st.hasMoreTokens())
            {
                objCustomerPreferencesTermsVO = new CustomerPreferencesTermsVO();
                String term = st.nextToken();
                if(!term.trim().equalsIgnoreCase(""))
                {
                    objCustomerPreferencesTermsVO.setTerm(Integer.parseInt(term.trim()));
                    objCustomerPreferencesTermsVO.setProspectiveCustomer(objCustomerPreferencesVO.getProspectiveCustomer());
                    objSession.save(objCustomerPreferencesTermsVO);
                }
            }
            st = new StringTokenizer(productIds,",");
            CustomerPreferenceProductsVO objCustomerPreferenceProductsVO = null;
            while(st.hasMoreTokens())
            {
                objCustomerPreferenceProductsVO = new CustomerPreferenceProductsVO();
                ProductsVO objProductsVO = new ProductsVO();
                objProductsVO.setProductIdentifier(Integer.parseInt(st.nextToken().trim()));
                objCustomerPreferenceProductsVO.setProduct(objProductsVO);
                objCustomerPreferenceProductsVO.setCustomer(objCustomerPreferencesVO.getProspectiveCustomer());
                objSession.save(objCustomerPreferenceProductsVO);
            }
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("PROSPECTIVE CUSTOMER PREFERENCES IS UPDATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE THE PROSPECTIVE CUSTOMER PREFERENCES", e);
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
    
    public static void main(String args[])
    {
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        List myList = null; 
        myList = objProspectiveCustomerDAO.getAllESIIDDetails(824);
        System.out.println("********** "+myList.size());
        /*Calendar objCalendar = Calendar.getInstance();
         Calendar tempCalendar = Calendar.getInstance();
         objCalendar.clear();
         objCalendar.set(tempCalendar.get(Calendar.YEAR),tempCalendar.get(Calendar.MONTH)+1,1);
         System.out.println(objCalendar.getTime());
         objProspectiveCustomerDAO.updateProspectiveStartDate("484,500",objCalendar);
         if(BuildConfig.DMODE)
         System.out.println(objProspectiveCustomerDAO.checkPostalCodeInCMS(46031));*/
        
    }
}

/*
*$Log: ProspectiveCustomerDAO.java,v $
*Revision 1.2  2008/01/30 13:43:24  tannamalai
*DAO added for energy components added
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.12  2007/11/28 14:08:57  jvediyappan
*indentation.
*
*Revision 1.11  2007/11/27 12:03:23  jvediyappan
*contract start removed and disable getesiid from CMS.
*
*Revision 1.8  2007/11/26 05:33:55  tannamalai
*latest changes for tdp
*
*Revision 1.7  2007/11/23 11:44:43  tannamalai
**** empty log message ***
*
*Revision 1.6  2007/11/23 05:29:12  tannamalai
**** empty log message ***
*
*Revision 1.5  2007/11/21 05:18:31  tannamalai
**** empty log message ***
*
*Revision 1.4  2007/11/20 04:52:53  tannamalai
**** empty log message ***
*
*Revision 1.3  2007/11/19 08:41:51  tannamalai
**** empty log message ***
*
*Revision 1.2  2007/11/17 07:59:01  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/10/30 05:51:43  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.123  2007/10/23 12:15:41  kduraisamy
**** empty log message ***
*
*Revision 1.118  2007/09/06 12:36:42  jnadesan
*mm customers excluded from normal customers run
*
*Revision 1.117  2007/09/04 07:05:18  sramasamy
*Log message is added for log file.
*
*Revision 1.116  2007/08/29 07:41:17  jnadesan
*delete added for customer files
*
*Revision 1.115  2007/08/28 05:19:46  jnadesan
*method added to get MM customers
*
*Revision 1.114  2007/08/27 04:39:38  jnadesan
*unwanted print removed
*
*Revision 1.113  2007/08/23 14:34:17  jnadesan
*restricted viewing both MM customers with normal customers
*
*Revision 1.112  2007/08/23 07:27:53  jnadesan
*imports organized
*
*Revision 1.111  2007/08/17 15:11:39  jnadesan
*Start Date modified
*
*Revision 1.110  2007/08/16 06:07:33  jnadesan
*strat date added for one month
*
*Revision 1.109  2007/08/07 10:02:53  jnadesan
*entry for customer file upload
*
*Revision 1.108  2007/07/31 12:28:42  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.107  2007/07/31 11:40:08  kduraisamy
*objsession properly closed or calling procedure properly done.
*
*Revision 1.106  2007/07/26 07:40:36  kduraisamy
*Data got deleted from PIC while deleting Prospective Customer.
*
*Revision 1.105  2007/07/24 14:03:38  kduraisamy
*Auto run filter added
*
*Revision 1.104  2007/07/23 11:00:50  spandiyarajan
*added 4 more parameters. createdfromdate, createdtodate, modifiedfromdate and modifiedtodate.
*
*Revision 1.103  2007/07/19 10:20:15  jnadesan
*transaction begined
*
*Revision 1.102  2007/07/17 07:20:10  spandiyarajan
*getCDRDetailsFromCMS query changed
*
*Revision 1.101  2007/07/06 14:14:08  sramasamy
*auto run showed
*
*Revision 1.100  2007/07/06 13:06:55  sramasamy
*customer preference update method
*
*Revision 1.99  2007/07/05 12:41:48  jnadesan
*zipcode validation removed
*
*Revision 1.98  2007/07/04 13:18:31  jnadesan
*customer satrtdate has updated if its in past or current month
*
*Revision 1.97  2007/07/03 10:19:35  jnadesan
*prospective customer delete option provided
*
*Revision 1.96  2007/07/03 09:59:54  kduraisamy
*exception handling added while deleting the customer.
*
*Revision 1.95  2007/07/03 09:58:08  kduraisamy
*exception handling added.
*
*Revision 1.94  2007/07/03 09:45:34  jnadesan
*null value checked before saving comments
*
*Revision 1.93  2007/07/03 09:01:08  kduraisamy
*getTeam() placed inside the ProspectiveCustomerDAO.
*
*Revision 1.92  2007/07/03 07:46:26  srajan
*delete method completed for prospectiveCustomer.
*
*Revision 1.91  2007/07/03 04:57:55  kduraisamy
*calling getEsiIdInfo method place changed.
*
*Revision 1.90  2007/06/26 12:46:29  jnadesan
*calling procedure chaged
*
*Revision 1.89  2007/06/26 12:02:44  jnadesan
*comments added into cms while updating
*
*Revision 1.88  2007/06/20 13:21:10  kduraisamy
*cpeExpiryDate() added.
*
*Revision 1.87  2007/06/19 13:55:57  spandiyarajan
*update functionality for comments
*
*Revision 1.86  2007/06/18 10:14:28  kduraisamy
*comments added.
*
*Revision 1.85  2007/06/13 11:03:35  kduraisamy
*2005 jar included.
*
*Revision 1.84  2007/06/13 04:10:44  kduraisamy
*2005 jar included.
*
*Revision 1.83  2007/06/12 12:56:42  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.82  2007/06/11 05:37:56  jnadesan
*start index changed for displaying customers in excel file
*
*Revision 1.81  2007/06/05 11:02:28  jnadesan
*unwanted methods removed
*
*Revision 1.80  2007/06/04 11:49:43  kduraisamy
*priceRunCustomer preferences added.
*
*Revision 1.79  2007/06/01 09:20:45  spandiyarajan
*in prospective cust chaged the import query
*
*Revision 1.78  2007/06/01 06:03:33  jnadesan
*while update writing customer details into cms is avoidaed
*
*Revision 1.77  2007/05/31 11:30:17  jnadesan
*getting esiid details from cms is applicaple for on adding customer.this will get changed
*
*Revision 1.76  2007/05/30 14:54:53  jnadesan
*method to get essid details
*
*Revision 1.75  2007/05/30 11:31:53  jnadesan
*return rype changed
*
*Revision 1.74  2007/05/30 10:17:23  kduraisamy
*pricing analyst only can write into CMS.
*
*Revision 1.73  2007/05/29 13:39:08  kduraisamy
*checkPostalCodeInCMS() added.
*
*Revision 1.71  2007/05/29 07:08:57  kduraisamy
*
*addressTypeId added.
*
*Revision 1.70  2007/05/29 05:44:52  kduraisamy
*getAllLocationTypeFromCMS() added.
*
*Revision 1.68  2007/05/28 13:27:19  kduraisamy
*CDR INFORMATION ADDED INTO CMS.
*
*Revision 1.67  2007/05/23 04:52:44  kduraisamy
*sales Rep, sale Manager first name, last name added into the filter.
*
*Revision 1.66  2007/05/22 13:55:27  jnadesan
*method to get productvo for given customerid and productid
*
*Revision 1.65  2007/05/22 12:24:57  kduraisamy
*String.valueOf() removed and string type casting added.
*
*Revision 1.64  2007/05/22 07:57:00  kduraisamy
*first name and last name filter added. Salesmanager list error rectified.
*
*Revision 1.63  2007/05/19 07:23:54  spandiyarajan
*removed unwated comments and throw the exxception when update the prospective customer
*
*Revision 1.62  2007/05/08 08:51:54  kduraisamy
*getAggregatedProfileDetails() added.
*
*Revision 1.61  2007/05/07 10:19:27  jnadesan
*term validated
*
*Revision 1.60  2007/05/07 07:30:10  spandiyarajan
*cmsid type chageed int type to integer
*
*Revision 1.59  2007/05/07 04:53:28  kduraisamy
*order by Customer Id added.
*
*Revision 1.58  2007/05/07 04:38:54  kduraisamy
*checkProspectiveCustomer() added.
*
*Revision 1.57  2007/04/26 12:28:31  kduraisamy
*runType Added.
*
*Revision 1.56  2007/04/20 05:02:45  kduraisamy
*method signature changed.
*
*Revision 1.55  2007/04/19 13:20:03  jnadesan
*terms will be allowed with space
*
*Revision 1.54  2007/04/18 11:37:42  jnadesan
*unmaped error solved
*
*Revision 1.53  2007/04/18 06:47:22  kduraisamy
*preference products added.
*
*Revision 1.52  2007/04/18 06:27:22  kduraisamy
*set removed.
*
*Revision 1.51  2007/04/17 06:25:17  kduraisamy
*dlfcode set removed from TDSP.
*
*Revision 1.50  2007/04/12 13:57:51  kduraisamy
*unwanted println commented.
*
*Revision 1.49  2007/04/12 11:56:11  kduraisamy
*isCDRApprove() added.
*
*Revision 1.48  2007/04/11 11:31:35  kduraisamy
*initial commit.
*
*Revision 1.47  2007/04/07 09:52:53  kduraisamy
*Additional fields for ESIID Details added.
*
*Revision 1.46  2007/04/06 12:45:39  kduraisamy
*unique key constraint added.
*
*Revision 1.45  2007/04/06 11:32:19  rraman
*one method commented by kanagaraj
*
*Revision 1.44  2007/04/05 13:40:31  kduraisamy
*point of contact is splited in to first and last name.
*
*Revision 1.43  2007/04/04 11:31:25  kduraisamy
*proper erro messages for CDR add Added.
*
*Revision 1.42  2007/04/03 11:58:01  kduraisamy
*returning null if record not found.
*
*Revision 1.41  2007/04/03 10:15:46  jnadesan
*customerPreference Save or update problem rectified.
*
*Revision 1.40  2007/04/03 05:56:22  kduraisamy
*DBA IS TAKEN FROM CMS.
*
*Revision 1.39  2007/04/02 10:26:19  kduraisamy
*wrong commit identified
*
*Revision 1.38  2007/04/02 10:17:14  kduraisamy
*wrong commit identified
*
*Revision 1.37  2007/04/02 09:45:43  rraman
**** empty log message ***
*
*Revision 1.36  2007/03/31 12:23:54  kduraisamy
*getAllCustomerbyManager() added.
*
*Revision 1.35  2007/03/31 11:18:41  rraman
*filter  method created for prospectivecustomerrep list page
*
*Revision 1.34  2007/03/31 10:30:07  rraman
*filter  method created for prospectivecustomerrep list page
*
*Revision 1.33  2007/03/30 10:51:59  rraman
*state alias added.
*
*Revision 1.32  2007/03/30 07:17:10  kduraisamy
*getCDRDetailsFromCMS() completed.
*
*Revision 1.31  2007/03/22 13:12:28  kduraisamy
*userWise prospectives List added.
*
*Revision 1.30  2007/03/15 11:56:42  kduraisamy
*indentation.
*
*Revision 1.29  2007/03/15 11:37:54  srajappan
*vo query into HQL query
*
*Revision 1.28  2007/03/14 11:57:01  kduraisamy
*updateProspectiveCustomerPreference() Completed.
*
*Revision 1.27  2007/03/14 11:44:06  kduraisamy
*getCDRDataFromCMS() template added.
*
*Revision 1.26  2007/03/14 11:41:49  srajappan
*add method return type changed
*
*Revision 1.25  2007/03/14 08:27:53  srajappan
*customerPreference Add completed.
*
*Revision 1.24  2007/03/10 12:21:08  srajappan
*string tokenizer method changed
*
*Revision 1.23  2007/03/07 05:11:08  kduraisamy
*get Method for customerpreferenceTerms added.
*
*Revision 1.22  2007/03/07 04:47:36  kduraisamy
*customer preference add, update, get completed.
*
*Revision 1.21  2007/03/02 13:33:23  srajappan
*unwanted link removed
*
*Revision 1.20  2007/03/01 10:19:47  kduraisamy
*prospective customer id changed to int.
*
*Revision 1.19  2007/02/13 14:05:17  kduraisamy
*BillingDemand related mapping completed.
*
*Revision 1.18  2007/02/13 09:24:52  kduraisamy
*getProspectiveCustomer by ids string added.
*
*Revision 1.17  2007/02/09 11:57:52  kduraisamy
*pricing core algorithm almost finished.
*
*Revision 1.16  2007/02/01 10:35:08  kduraisamy
*filter methods problem rectified.
*
*Revision 1.15  2007/01/31 09:50:01  kduraisamy
*load is changed to get.
*
*Revision 1.14  2007/01/31 09:31:42  kduraisamy
*filter method added.
*
*Revision 1.13  2007/01/30 13:14:54  kduraisamy
*unwanted imports removed.
*
*Revision 1.12  2007/01/30 13:14:16  kduraisamy
*updateProspectiveCustomer() added.
*
*Revision 1.11  2007/01/30 12:58:34  kduraisamy
*get is changed to load.
*
*Revision 1.10  2007/01/30 10:13:32  kduraisamy
*array changed to list.
*
*Revision 1.9  2007/01/30 09:03:54  kduraisamy
*unwanted imports removed.
*
*Revision 1.8  2007/01/30 09:02:35  kduraisamy
*unwanted imports removed.
*
*Revision 1.7  2007/01/30 09:00:56  kduraisamy
*unwanted beginTransaction() removed.
*
*Revision 1.6  2007/01/30 08:45:44  kduraisamy
*parent child relation problem recitified
*
*Revision 1.5  2007/01/29 11:41:14  kduraisamy
*customerStatusDAO created and getAllCustomerStatus() moved from ProspectiveCustomerDAO to CustomerStatusDAO.
*
*Revision 1.4  2007/01/29 11:02:14  kduraisamy
*getAllCustomerStatus() method added.
*
*Revision 1.3  2007/01/29 09:21:47  kduraisamy
*session.flush() placed inside the code block.
*
*Revision 1.2  2007/01/27 10:15:28  kduraisamy
*get and getAll methods partially finished.
*
*Revision 1.1  2007/01/25 13:09:06  kduraisamy
*initial commit.
*
*
*/