/*
 * Created on Feb 6, 2007
 *
 * ClassName	:  	PricingDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.calculation.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.calculation.valueobjects.ActualUsageAggregationVO;
import com.savant.pricing.calculation.valueobjects.ApparentPowerVO;
import com.savant.pricing.calculation.valueobjects.CustomerPreferenceProductsVO;
import com.savant.pricing.calculation.valueobjects.CustomerPreferencesTermsVO;
import com.savant.pricing.calculation.valueobjects.CustomerPreferencesVO;
import com.savant.pricing.calculation.valueobjects.EnergyDemandVO;
import com.savant.pricing.calculation.valueobjects.IDRVO;
import com.savant.pricing.calculation.valueobjects.PICUsageVO;
import com.savant.pricing.calculation.valueobjects.PICVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerProductsVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerTermsVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO;
import com.savant.pricing.calculation.valueobjects.PriceRunHeaderVO;
import com.savant.pricing.calculation.valueobjects.PricingUsageHeaderVO;
import com.savant.pricing.calculation.valueobjects.UOMVO;
import com.savant.pricing.calculation.valueobjects.UsageVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.Filter;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.dao.CalendarDAO;
import com.savant.pricing.dao.ContractsDAO;
import com.savant.pricing.dao.DLFCodeDAO;
import com.savant.pricing.dao.DLFDAO;
import com.savant.pricing.dao.ForwardCurveBlockDAO;
import com.savant.pricing.dao.GasPriceDAO;
import com.savant.pricing.dao.LossFactorLookupDAO;
import com.savant.pricing.dao.MeterReadCyclesDAO;
import com.savant.pricing.dao.PICDAO;
import com.savant.pricing.dao.PowerFactorRatchetDAO;
import com.savant.pricing.dao.PriceRunCostCapitalDAO;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.dao.TDSPChargeRatesDAO;
import com.savant.pricing.dao.TDSPRateCodesDAO;
import com.savant.pricing.matrixpricing.dao.LoadMMPriceResult;
import com.savant.pricing.matrixpricing.valueobjects.MMPriceRunHeaderVO;
import com.savant.pricing.presentation.contract.CreateAutomaticCPEContract;
import com.savant.pricing.transferobjects.AnnualEnergyDetails;
import com.savant.pricing.transferobjects.DealLevers;
import com.savant.pricing.transferobjects.EPP;
import com.savant.pricing.transferobjects.HeatRateProduct;
import com.savant.pricing.transferobjects.IndividualEPP;
import com.savant.pricing.transferobjects.IndividualHeatRateProduct;
import com.savant.pricing.transferobjects.IndividualTermDetails;
import com.savant.pricing.transferobjects.OnPkOffPkDetails;
import com.savant.pricing.transferobjects.PricingDashBoard;
import com.savant.pricing.transferobjects.TermDetails;
import com.savant.pricing.valueobjects.DayTypesVO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;
import com.savant.pricing.valueobjects.RateCodesVO;
import com.savant.pricing.valueobjects.TDSPVO;
import com.savant.pricing.valueobjects.TaxRatesVO;


/**
 * @author kduraisamy
 * 
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PricingDAO
{
    LoadExtrapolationDAO objLoadExtrapolationDAO = new LoadExtrapolationDAO();  
    CostComputationDAO objCostComputationDAO = new CostComputationDAO();
    DealLeversDAO objDealLeversDAO = new DealLeversDAO();
    MeterReadCyclesDAO objMeterReadCyclesDAO = new MeterReadCyclesDAO();
    TDSPChargeRatesDAO objTDSPChargeRatesDAO = new TDSPChargeRatesDAO();
    TDSPRateCodesDAO objTDSPRateCodesDAO = new TDSPRateCodesDAO();
    PowerFactorRatchetDAO objPowerFactorRatchetDAO = new PowerFactorRatchetDAO();
    PriceRunCostCapitalDAO objPriceRunCostCapitalDAO = new PriceRunCostCapitalDAO();
    GasPriceDAO objGasPriceDAO = new GasPriceDAO();
    PICDAO objPICDAO = new PICDAO();
    StringBuffer sbUsage;
    HashMap hmIDRDetails = new HashMap();
    HashMap hmCharges = null;
    HashMap hmWithOutLoss = null;
    
    static Logger logger = Logger.getLogger(PricingDAO.class);
        
    public boolean execute(String prospectiveCustomerIds,String runType, String runBy,boolean isMMCust) throws SQLException
    {
        Session objSession = null;
        boolean run = false;
        float gasPrice = 0;
        int esiIdCount = 1;
        try
        {
            objSession = HibernateUtil.getSession();
            Date marketDate = new ForwardCurveBlockDAO().fwdCurveLastImportedOn();
            Date gasMrktDate = new GasPriceDAO().teeNaturalGasPriceLastImportedOn();
            gasPrice = objGasPriceDAO.getAvgGasPrice();
            new ProspectiveCustomerDAO().updateStartDate(prospectiveCustomerIds);
            if(runType.equals("A"))
            	 logger.info("MXenergy Auto Price Run Starts:"+new Date());
            if(marketDate != null)
            {
                if(BuildConfig.DMODE)
                    System.out.println("loadPrerequisites:Starts:"+new Date());
                if(BuildConfig.DMODE)
                    System.out.println("loadPrerequisites:Ends:"+new Date());
                objSession.beginTransaction();
                int noOfRowsDeleted = objSession.createSQLQuery("delete from prc_aggregatedloadprofile").executeUpdate();
                //objSession.getTransaction().commit();
                if(BuildConfig.DMODE)
                {
                    System.out.println("No of rows deleted from prc_aggregatedloadprofile:"+noOfRowsDeleted);
                }
                
                String priceRunId = this.createPriceRun(objSession, runType, marketDate, gasPrice ,runBy,gasMrktDate);
                boolean costCapital = false;
                if(!isMMCust)
                    costCapital = objPriceRunCostCapitalDAO.addPrcCostCap(priceRunId,objSession);
                if(BuildConfig.DMODE)
                    System.out.println(" cost capital :"+costCapital);
                if(isMMCust)
                    this.createMMPriceRun(objSession, runBy,priceRunId,objGasPriceDAO.teeNaturalGasPriceLastImportedOn(),marketDate, gasPrice);
                objSession.getTransaction().commit();
                boolean shapingPremiumUnitary = false;
                StringTokenizer st = new StringTokenizer(prospectiveCustomerIds, ",");
                while(st.hasMoreTokens())
                {
                    run = true;
                    int prospectiveCustomerId = (new Integer(st.nextToken())).intValue();
                    objSession.beginTransaction();
                    if(BuildConfig.DMODE)
                        System.out.println("loadProfileUsage:Starts:"+new Date());
                    objLoadExtrapolationDAO.loadProfileUsage(prospectiveCustomerId, objSession);
                    if(BuildConfig.DMODE)
                    {
                        System.out.println("loadProfileUsage:Ends:"+new Date());
                        System.out.println("createCustomerPriceReference:Starts:"+new Date());
                    }
                    int priceRunCustomerRefId = this.createCustomerPriceReference(priceRunId,prospectiveCustomerId,objSession);
                    CallableStatement cstmt = null;
                    try
                    {
                        //to store the customer preference run wise.
                        this.saveCustomerPreferences(priceRunCustomerRefId, prospectiveCustomerId,objSession);
                        objSession.getTransaction().commit();
                        if(BuildConfig.DMODE)
                            System.out.println("createCustomerPriceReference:Ends:"+new Date());
                        if(BuildConfig.DMODE)
                            System.out.println("getAllESIID:Starts:"+new Date());
                        
                        List objPICVOList = objPICDAO.getAllValidESIID(prospectiveCustomerId, objSession);
                        if(BuildConfig.DMODE)
                            System.out.println("getAllESIID:Ends:"+new Date());
                        PriceRunCustomerVO objPriceRunCustomerVO = new PriceRunCustomerVO();
                        objPriceRunCustomerVO.setPriceRunCustomerRefId(priceRunCustomerRefId);
                        if(BuildConfig.DMODE)
                            System.out.println("getContract:Starts:"+new Date());
                        CustomerPreferencesVO  objCustomerPreferencesVO = this.getContract(prospectiveCustomerId);
                        shapingPremiumUnitary = objCustomerPreferencesVO.isUnitary();
                        if(BuildConfig.DMODE)
                            System.out.println("getContract:Ends:"+new Date()+"********"+objCustomerPreferencesVO);
                        Iterator iterator = objPICVOList.iterator();
                        String esiId = "";
                        if(iterator.hasNext())
                        {
                            int rateCodeId = 0;
                            while(iterator.hasNext())
                            {
                                try
                                {
                                    hmIDRDetails.clear();
                                    PICVO  objPICVO = (PICVO)iterator.next();
                                    objSession.beginTransaction();
                                    RateCodesVO objRateCodesVO = objPICVO.getRateCode();
                                    rateCodeId = objRateCodesVO.getRateCodeIdentifier();
                                    
                                    int serviceVoltageId = objTDSPRateCodesDAO.getServiceVoltageId(rateCodeId);
                                    String dlfCode = LossFactorLookupDAO.getDLF(objPICVO.getRateCode().getTdsp(), serviceVoltageId);
                                    int dlfCodeId = DLFCodeDAO.getDLFCodeId(objPICVO.getRateCode().getTdsp(),dlfCode);
                                    float dlf = DLFDAO.getDLF(dlfCodeId);
                                   
                                    
                                    objRateCodesVO.setTdspChargeRates((List)objTDSPChargeRatesDAO.getAllChargeRates(rateCodeId));
                                    objRateCodesVO.setTdspRateCodesVo((List)objTDSPRateCodesDAO.getAllTDSPRateCodes(rateCodeId));
                                    objRateCodesVO.setPowerFactorRatchet((List)objPowerFactorRatchetDAO.getAllPowerFactorRatchet(rateCodeId));
                                    objPICVO.setRateCode(objRateCodesVO);
                                    esiId = objPICVO.getEsiId();
                                    String meterReadCycle = objPICVO.getMeterReadCycle();
                                    HibernateUtil.sbCost = new StringBuffer();
                                    sbUsage = new StringBuffer();
                                    TDSPVO objTDSPVO = objLoadExtrapolationDAO.getTDSPByESIID(esiId);
                                    objTDSPVO.setMeterReadCycles((List)objMeterReadCyclesDAO.getAllMeterReadCycles(objTDSPVO.getTdspIdentifier()));
                                    HibernateUtil.objUsageVOS = new ArrayList(5000);
                                    PricingUsageHeaderVO[] objUsageTerm = new PricingUsageHeaderVO[60];
                                    TermDetails[] objTerms = new TermDetails[60];
                                    if(BuildConfig.DMODE)
                                        System.out.println(esiIdCount+++":"+esiId);
                                    List listPICUsages = (List) objPICDAO.getAllPICUsages(objPICVO.getPicReferenceId());
                                    hmIDRDetails = objLoadExtrapolationDAO.getIDRDetails(esiId);
                                    boolean availIdr = objLoadExtrapolationDAO.checkAvailIDR(objPICVO.getEsiId().trim(), objSession);
                                    String meterType = objPICVO.getProfileDetails().getMeterType();
                                    for(int i=0;i<60;i++)
                                    {
                                        objSession.beginTransaction();
                                        PricingUsageHeaderVO objPricingUsageHeaderVO = new PricingUsageHeaderVO();
                                        objPricingUsageHeaderVO.setEsiId(esiId);
                                        if(BuildConfig.DMODE)
                                            System.out.println("objPriceRunCustomerVO:"+objPriceRunCustomerVO.getPriceRunCustomerRefId());
                                        objPricingUsageHeaderVO.setPriceRunCustomer(objPriceRunCustomerVO);
                                        objPricingUsageHeaderVO.setTerm(i+1);
                                        objSession.save(objPricingUsageHeaderVO);
                                        objSession.getTransaction().commit();
                                        TermDetails objTermDetails = new TermDetails();
                                        objTermDetails.setTerm(i+1);
                                        objTerms[i] = objTermDetails;
                                        if(BuildConfig.DMODE)
                                            System.out.println("loadForecastedUsage:Starts:"+new Date()+":"+objCustomerPreferencesVO);
                                        this.loadForecastedUsage(dlf,objPICVO, listPICUsages, objTDSPVO, objPricingUsageHeaderVO, objCustomerPreferencesVO.getContractStartDate(), objTermDetails, esiId, meterReadCycle, meterType, availIdr, objSession);
                                        if(BuildConfig.DMODE)
                                            System.out.println("loadForecastedUsage:Ends:"+new Date());
                                        objUsageTerm[i] = objPricingUsageHeaderVO;
                                    }
                                    objSession.getTransaction().commit();
                                    if(sbUsage.length()>0)
                                    {
                                        if(BuildConfig.DMODE)
                                            System.out.println("Usage Available:"+new Date());
                                        objSession.beginTransaction();
                                        objSession.createSQLQuery("delete from PRC_USAGETEXT where ESIID = ?").setString(0, esiId).executeUpdate();
                                        objSession.createSQLQuery("insert into PRC_USAGETEXT(ESIID,USAGE) VALUES(?,?)").setString(0,esiId).setText(1,sbUsage.toString()).executeUpdate();
                                        objSession.getTransaction().commit();
                                        
                                        objSession.beginTransaction();
                                        cstmt = objSession.connection().prepareCall("{call Sp_BulkInsert (?)}");
                                        cstmt.setString(1, esiId);
                                        cstmt.execute();
                                        objSession.getTransaction().commit();
                                    }
                                    if(BuildConfig.DMODE)
                                        System.out.println("computeEnergyCost:Starts:"+new Date());
                                    
                                    for(int j = 0; j<60;j++)
                                    {
                                        objCostComputationDAO.computeEnergyCost(objTDSPVO, objPICVO, marketDate, objUsageTerm[j],objTerms[j], shapingPremiumUnitary);
                                    }
                                    objSession.beginTransaction();
                                    objSession.createSQLQuery("delete from PRC_COSTTEXT where ESIID = ?").setString(0, esiId).executeUpdate();
                                    objSession.createSQLQuery("insert into PRC_COSTTEXT(ESIID, COST) VALUES(?,?)").setString(0,esiId).setText(1,HibernateUtil.sbCost.toString()).executeUpdate();
                                    objSession.getTransaction().commit();
                                    
                                    
                                    objSession.beginTransaction();
                                    cstmt = objSession.connection().prepareCall("{call Sp_CostBulkInsert(?)}");
                                    cstmt.setString(1,esiId);
                                    cstmt.execute();
                                    objSession.getTransaction().commit();
                                    if(BuildConfig.DMODE)
                                        System.out.println("computeEnergyCost:Ends:"+new Date());
                                }
                                catch(SQLException e)
                                {
                                    logger.error("SQL EXCEPTION DURING PRICE RUN -> PROBLEM IN ESIID", e);
                                    e.printStackTrace();
                                    objSession.getTransaction().rollback();
                                    throw new SQLException("Problem in ESIID:"+esiId);
                                }
                                catch(Exception e)
                                {
                                    logger.error("GENERAL EXCEPTION DURING PRICE RUN -> PROBLEM IN ESIID", e);
                                    e.printStackTrace();
                                    objSession.getTransaction().rollback();
                                    throw new Exception("Problem in ESIID:"+esiId);
                                }
                                finally
                                {
                                    if(cstmt != null)
                                    {
                                        cstmt.close();
                                    }
                                }
                            }
                            objSession.beginTransaction();
                            this.updateResult(priceRunCustomerRefId,true,"Price Run Success",objSession);
                            objSession.getTransaction().commit();
                            if(runType.equals("A"))
                            {
                                ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
                                ProspectiveCustomerVO objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(prospectiveCustomerId);
                                logger.info("CustomerId: "+objProspectiveCustomerVO.getCustomerId());
                                logger.info("CustomerName: "+objProspectiveCustomerVO.getCustomerName());
                                logger.info("Completed Time: "+new Date());
                                logger.info("Status: Success");
                            }
                            if(isMMCust)
                                new LoadMMPriceResult().loadRunResult(priceRunCustomerRefId);
                            else
                                new CreateAutomaticCPEContract().createCPEAndContract(priceRunCustomerRefId);
                        }
                        else 
                        {
                            if(isMMCust)
                                deleteRunResult(priceRunCustomerRefId+"");
                            else
                            {
                                objSession.beginTransaction();
                                if(objLoadExtrapolationDAO.getAllESIID(prospectiveCustomerId).size()==0)
                                {
                                    this.updateResult(priceRunCustomerRefId,false,"CUD File Not Found for this Customer",objSession);
                                }
                                else
                                {
                                    this.updateResult(priceRunCustomerRefId,false,"No ESIID selected for this Customer",objSession);
                                }
                                objSession.getTransaction().commit();
                                if(runType.equals("A"))
                                {
                                    ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
                                    ProspectiveCustomerVO objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(prospectiveCustomerId);
                                    logger.info("CustomerId: "+objProspectiveCustomerVO.getCustomerId());
                                    logger.info("CustomerName: "+objProspectiveCustomerVO.getCustomerName());
                                    logger.info("Completed Time: "+new Date());
                                    logger.info("Status: Failure");
                                }
                            }
                        }
                    }
                    catch (SQLException e)
                    {
                        logger.error("SQL EXCEPTION DURING PRICE RUN", e);
                        e.printStackTrace();
                        if(isMMCust)
                            deleteRunResult(priceRunCustomerRefId+"");
                        else
                        {
                            objSession.beginTransaction();
                            this.updateResult(priceRunCustomerRefId,false, e.getMessage(),objSession);
                            objSession.getTransaction().commit();
                        }
                        if(runType.equals("A"))
                        {
                            ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
                            ProspectiveCustomerVO objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(prospectiveCustomerId);
                            System.out.println("CustomerId: "+objProspectiveCustomerVO.getCustomerId());
                            System.out.println("CustomerName: "+objProspectiveCustomerVO.getCustomerName());
                            System.out.println("Completed Time: "+new Date());
                            System.out.println("Status: Failure");
                        }
                    }
                    catch(Exception e)
                    {
                        logger.error("GENERAL EXCEPTION DURING MXENERGY PRICE RUN", e);
                        e.printStackTrace();
                        if(isMMCust)
                            deleteRunResult(priceRunCustomerRefId+"");
                        else
                         {
                            objSession.beginTransaction();
                            this.updateResult(priceRunCustomerRefId,false, e.getMessage(),objSession);
                            objSession.getTransaction().commit();
                         }
                        if(runType.equals("A"))
                        {
                            ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
                            ProspectiveCustomerVO objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(prospectiveCustomerId);
                            System.out.println("CustomerId: "+objProspectiveCustomerVO.getCustomerId());
                            System.out.println("CustomerName: "+objProspectiveCustomerVO.getCustomerName());
                            System.out.println("Completed Time: "+new Date());
                            System.out.println("Status: Failure");
                        }
                    }
                }
                if(!isMMCust)
                {
                    PreparedStatement ps;
                    objSession.beginTransaction();
                    ps = objSession.connection().prepareCall("{call SP_AggregatedLoadProfile_Insert}");
                    ps.executeUpdate();
                    ps.close();
                    objSession.getTransaction().commit();
                    logger.info("MXenergy PRICE RUN ENDS");
                }
                else
                {
                    LoadMMPriceResult objLoadMMPriceResult = new LoadMMPriceResult();
                    objLoadMMPriceResult.deletePriceRunHeader(priceRunId);
                    objLoadMMPriceResult.deleteaggLoadProfile();
                }
            }
            else
            {
                logger.info("NO FORWARD CURVES FOUND");
                throw new HibernateException("No Forward Curves Found");
            }
            if(runType.equals("A"))
                logger.info("MXenergy Auto Price Run Ends:"+new Date());
            
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING PRICE RUN", e);
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
        return run;
    }
    
    private boolean saveCustomerPreferences(int priceRunCustomerRefId, int prospectiveCustomerId, Session objSession)
    {
        //Session objSession = null;
        boolean saveResult = false;
        try
        {
            logger.info("SAVING CUSTOMER PREFERENCES");
            
           // objSession = HibernateUtil.getSession();
            List objList = objSession.createCriteria(CustomerPreferenceProductsVO.class).add(Restrictions.eq("customer.prospectiveCustomerId", new Integer(prospectiveCustomerId))).list();
           // objSession.beginTransaction();
            Iterator itr = objList.iterator();
            PriceRunCustomerVO objPriceRunCustomerVO = new PriceRunCustomerVO();
            objPriceRunCustomerVO.setPriceRunCustomerRefId(priceRunCustomerRefId);
            
            while(itr.hasNext())
            {
                CustomerPreferenceProductsVO objCustomerPreferenceProductsVO = (CustomerPreferenceProductsVO)itr.next();
                PriceRunCustomerProductsVO objPriceRunCustomerProductsVO = new PriceRunCustomerProductsVO();
                objPriceRunCustomerProductsVO.setPriceRunCustomer(objPriceRunCustomerVO);
                objPriceRunCustomerProductsVO.setProduct(objCustomerPreferenceProductsVO.getProduct());
                objSession.save(objPriceRunCustomerProductsVO);
            }
          //  objSession.getTransaction().commit();
            
            objList = objSession.createCriteria(CustomerPreferencesTermsVO.class).add(Restrictions.eq("prospectiveCustomer.prospectiveCustomerId", new Integer(prospectiveCustomerId))).list();
           // objSession.beginTransaction();
            itr = objList.iterator();
            
            while(itr.hasNext())
            {
                CustomerPreferencesTermsVO objCustomerPreferencesTermsVO = (CustomerPreferencesTermsVO)itr.next();
                PriceRunCustomerTermsVO objPriceRunCustomerTermsVO = new PriceRunCustomerTermsVO();
                objPriceRunCustomerTermsVO.setPriceRunCustomer(objPriceRunCustomerVO);
                objPriceRunCustomerTermsVO.setTerm(objCustomerPreferencesTermsVO.getTerm());
                objSession.save(objPriceRunCustomerTermsVO);
            }
           // objSession.getTransaction().commit();
            saveResult = true;
            logger.info("CUSTOMER PREFERENCES ARE SAVED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING SAVE CUSTOMER PREFERENCES:", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING SAVE CUSTOMER PREFERENCES:", e);
            e.printStackTrace();
        }
       /* finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }*/
        return saveResult;
    }
    
    private boolean updateResult(int priceRunCustomerRefId, boolean stat, String reason, Session objSession)
    {
       // Session objSession = null;
        boolean updateResult = false;
        try
        {
            
            logger.info("UPDATING PRICE RUN CUSTOMER");
            
            //objSession = HibernateUtil.getSession();
            PriceRunCustomerVO objPriceRunCustomerVO = (PriceRunCustomerVO)objSession.get(PriceRunCustomerVO.class,new Integer(priceRunCustomerRefId));
            objPriceRunCustomerVO.setRunStatus(stat);
            objPriceRunCustomerVO.setReason(reason);
           // objSession.beginTransaction();
            
            if(!stat)
                this.deletePriceRunCustomerInfo(priceRunCustomerRefId, objSession);
            
            objSession.update(objPriceRunCustomerVO);
           // objSession.getTransaction().commit();
            updateResult = true;
            
            logger.info("PRICE RUN CUSTOMER IS UPDATED");
            
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING UPDATE PRICE RUN CUSTOMER STATUS", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING UPDATE PRICE RUN CUSTOMER STATUS", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
        }
      /*  finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }*/
        return updateResult;
    }
    
    public boolean deleteRunResult(String priceRunCustomerRefIds) throws SQLException
    {
        Session objSession = null;
        boolean deleteResult = false;
        CallableStatement cstmnt = null;
        try
        {
            logger.info("DELETING RUN RESULTS");
            
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();         
            cstmnt = objSession.connection().prepareCall("{call sp_DeletePriceRun (?)}");
            cstmnt.setString(1, priceRunCustomerRefIds.trim());
            cstmnt.execute();
            objSession.getTransaction().commit();
            deleteResult = true;
            
            logger.info("RUN RESULTS ARE DELETED:");
            
            /*
            PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
            objSession = HibernateUtil.getSession();
            
            StringTokenizer st = new StringTokenizer(priceRunCustomerRefIds,",");
            while(st.hasMoreTokens())
            {
                objSession.beginTransaction();
                int priceRunCustomerRefId = Integer.parseInt(st.nextToken().trim());
                if(BuildConfig.DMODE)
                    System.out.println("priceRunCustomerRefId:"+priceRunCustomerRefId);
                PriceRunCustomerVO objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(priceRunCustomerRefId);
                if(BuildConfig.DMODE)
                    System.out.println("objPriceRunCustomerVO:"+objPriceRunCustomerVO);
                int priceRunCustomerCount = 0;
                if(objPriceRunCustomerVO != null)
                    priceRunCustomerCount = objPriceRunCustomerDAO.getPriceRunCustomerCount(objPriceRunCustomerVO.getPriceRunRef().getPriceRunRefNo());
                if(BuildConfig.DMODE)
                    System.out.println("priceRunCustomerCount:"+priceRunCustomerCount);
                try
                {
                    if(BuildConfig.DMODE)
                        System.out.println("before entering deletePriceRunCustomerInfo");
                    this.deletePriceRunCustomerInfo(priceRunCustomerRefId, objSession);
                    if(BuildConfig.DMODE)
                        System.out.println("after entering deletePriceRunCustomerInfo");
                    noOfRowsAffected = objSession.createQuery("delete from PriceRunCustomerVO as pricerunCustomer where pricerunCustomer.priceRunCustomerRefId = ?").setInteger(0,priceRunCustomerRefId).executeUpdate();
                    if(BuildConfig.DMODE)
                        System.out.println("No Of Rows Affecteds "+noOfRowsAffected);
                    if(priceRunCustomerCount==1)
                    {
                        noOfRowsAffected = objSession.createQuery("delete from PriceRunHeaderVO as pricerunHeader where pricerunHeader.priceRunRefNo = ?").setString(0,objPriceRunCustomerVO.getPriceRunRef().getPriceRunRefNo()).executeUpdate();
                        if(BuildConfig.DMODE)
                            System.out.println("No Of Rows Affected for Header "+noOfRowsAffected);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    objSession.getTransaction().rollback();
                }
                objSession.getTransaction().commit();
            }
            deleteResult = true;
        */
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DELETE THE RUN RESULTS", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING DELETE THE RUN RESULTS", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
        }
        finally
        {
            if(cstmnt != null)
            {
                cstmnt.close();
            }
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return deleteResult;
    }
    
    private boolean deletePriceRunCustomerInfo(int priceRunCustomerRefId, Session objSession) throws Exception
    {
        boolean deleteResult = false;
        try
        {
            logger.info("DELETING PRICE RUN CUSTOMER INFORMATION");
            
            //ApparentPowerVO
            int noOfRowsAffected = objSession.createQuery("delete from ApparentPowerVO as apparentPower where apparentPower.usageRef.pricingUsageRefId in (select pricingUsageHeader.pricingUsageRefId from PricingUsageHeaderVO as pricingUsageHeader where pricingUsageHeader.priceRunCustomer.priceRunCustomerRefId = ?)").setInteger(0,priceRunCustomerRefId).executeUpdate();
            if(BuildConfig.DMODE)
                System.out.println("No Of Rows Affected "+noOfRowsAffected);
            
            noOfRowsAffected = objSession.createQuery("delete from PriceRunCustomerProductsVO as priceRunCustomerProducts where priceRunCustomerProducts.priceRunCustomer.priceRunCustomerRefId = ?").setInteger(0,priceRunCustomerRefId).executeUpdate();
            if(BuildConfig.DMODE)
                System.out.println("No Of Rows Affected "+noOfRowsAffected);
            
            noOfRowsAffected = objSession.createQuery("delete from PriceRunCustomerTermsVO as priceRunCustomerTerms where priceRunCustomerTerms.priceRunCustomer.priceRunCustomerRefId = ?").setInteger(0,priceRunCustomerRefId).executeUpdate();
            if(BuildConfig.DMODE)
                System.out.println("No Of Rows Affected "+noOfRowsAffected);
            
            noOfRowsAffected = objSession.createQuery("delete from EnergyDemandVO as enrgyDmnd where enrgyDmnd.usageRef.pricingUsageRefId in (select pricingUsageHeader.pricingUsageRefId from PricingUsageHeaderVO as pricingUsageHeader where pricingUsageHeader.priceRunCustomer.priceRunCustomerRefId = ?)").setInteger(0,priceRunCustomerRefId).executeUpdate();
            if(BuildConfig.DMODE)
                System.out.println("No Of Rows Affected "+noOfRowsAffected);
            
            noOfRowsAffected = objSession.createQuery("delete from UsageVO as usage where usage.usageRef.pricingUsageRefId in (select pricingUsageHeader.pricingUsageRefId from PricingUsageHeaderVO as pricingUsageHeader where pricingUsageHeader.priceRunCustomer.priceRunCustomerRefId = ?)").setInteger(0,priceRunCustomerRefId).executeUpdate();
            if(BuildConfig.DMODE)
                System.out.println("No Of Rows Affected "+noOfRowsAffected);
            
            noOfRowsAffected = objSession.createQuery("delete from PricingCostVO as cost where cost.usageRef.pricingUsageRefId in (select pricingUsageHeader.pricingUsageRefId from PricingUsageHeaderVO as pricingUsageHeader where pricingUsageHeader.priceRunCustomer.priceRunCustomerRefId = ?)").setInteger(0,priceRunCustomerRefId).executeUpdate();
            if(BuildConfig.DMODE)
                System.out.println("No Of Rows Affected "+noOfRowsAffected);
            
            noOfRowsAffected = objSession.createQuery("delete from PricingUsageHeaderVO as pricingUsageHeader where pricingUsageHeader.priceRunCustomer.priceRunCustomerRefId = ?").setInteger(0,priceRunCustomerRefId).executeUpdate();
            if(BuildConfig.DMODE)
                System.out.println("No Of Rows Affected "+noOfRowsAffected);
            
            noOfRowsAffected = objSession.createQuery("delete from ContractsTrackingVO as contractTracking where contractTracking.contractRef.contractIdentifier in (select contracts.contractIdentifier from ContractsVO as contracts where contracts.priceRunCustomerRef.priceRunCustomerRefId = ?)").setInteger(0,priceRunCustomerRefId).executeUpdate();
            if(BuildConfig.DMODE)
                System.out.println("No Of Rows Affected "+noOfRowsAffected);
            
            noOfRowsAffected = objSession.createQuery("delete from ContractsVO as contracts where contracts.priceRunCustomerRef.priceRunCustomerRefId = ?").setInteger(0,priceRunCustomerRefId).executeUpdate();
            if(BuildConfig.DMODE)
                System.out.println("No Of Rows Affected "+noOfRowsAffected);

            deleteResult = true;
            if(BuildConfig.DMODE)
                System.out.println("deleteResult:"+deleteResult);
            
            logger.info("PRICE RUN CUSTOMER INFORMATION IS DELETED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING DELETE PRICE RUN CUSTOMER INFO:", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING DELETE PRICE RUN CUSTOMER INFO:", e);
            e.printStackTrace();
            throw new Exception(e.toString());
        }
        return deleteResult;
    }
    
    public int getMinValue(List lstSenValues)
    {
        logger.info("GETTING MIN VALUE");
        Iterator ite = lstSenValues.iterator();       
        int minValueIndex = 0;
        int i = 0;
        double minValue = 0;
        while(ite.hasNext())
        {
            double price = ((Double)ite.next()).doubleValue();
            if(i==0)
            {
                minValue = price;
            }
            if(minValue>price)
            {
                minValueIndex = i;
                minValue = price;
            }
            i++;
        }
        logger.info("GOT MIN VALUE");
        return minValueIndex+1;
    }
    public HashMap getAllEsiIds(int customerRefId)
    {
        Session objSession = null;
        HashMap hm = new HashMap();
        try
        {
            logger.info("GETTING ALL ESIIDs");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select pricingusageheadervo.esiId from PricingUsageHeaderVO as pricingusageheadervo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ?");
            objQuery.setInteger(0,customerRefId);
            Iterator itr = objQuery.iterate();
            while(itr.hasNext())
            {
                Object row = itr.next();
                hm.put(row,row);
            }
            logger.info("GOT ALL ESIIDs");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ESIIDs", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL ESIIDs", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hm;
    }
    
    public HashMap getAllEsiIds(int customerRefId, int tdspId)
    {
        Session objSession = null;
        HashMap hm = new HashMap();
        try
        {
            logger.info("GETTING ALL ESIIDs BY TDSP ID");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select pricingusageheadervo.esiId from PricingUsageHeaderVO as pricingusageheadervo, TDSPVO as tdspvo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and substring(pricingusageheadervo.esiId,1,7) = tdspvo.esiIdPrefix and tdspvo.tdspIdentifier = ?");
            objQuery.setInteger(0,customerRefId);
            objQuery.setInteger(1,tdspId);
            Iterator itr = objQuery.iterate();
            while(itr.hasNext())
            {
                Object row = itr.next();
                hm.put(row,row);
            }
            logger.info("GOT ALL ESIIDs BY TDSP ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ESIIDs BY TDSP ID", e); 
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL ESIIDs BY TDSP ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hm;
    }
    
    public HashMap getAllEsiIdsByCongestionZone(int customerRefId, int congestionZoneId)
    {
        Session objSession = null;
        HashMap hm = new HashMap();
        try
        {
            logger.info("GETTING ALL ESIIDs BY CONGESTION ZONE ID");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createSQLQuery("SELECT DISTINCT PRCUSAGEHEADER.ESIID FROM PRC_USAGE_HEADER AS PRCUSAGEHEADER, PRC_PIC AS PRCPIC, PRC_WEATHER_ZONES AS WEATHERZONES WHERE PRCUSAGEHEADER.ESIID = PRCPIC.ESIID AND WEATHERZONES.CONGESTION_ZONE_ID = ? AND SUBSTRING(Load_Profile,CHARINDEX('_',Load_Profile,0)+1,CHARINDEX('_',Load_Profile,CHARINDEX('_',Load_Profile,0)+1)-CHARINDEX('_',Load_Profile,0)-1) = WEATHERZONES.WEATHER_ZONE_CODE  AND PRCUSAGEHEADER.PRICE_RUN_CUST_REF_ID = ?");
            objQuery.setInteger(0,congestionZoneId);
            objQuery.setInteger(1,customerRefId);
            List objList = objQuery.list();
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                Object row = itr.next();
                hm.put(row,row);
            }
            logger.info("GOT ALL ESIIDs BY CONGESTION ZONE ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ESIIDs BY CONGESTION ZONE ID", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL ESIIDs BY CONGESTION ZONE ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hm;
    }
    
    public HashMap getAllEsiIdsByTDSPCongestionZone(int customerRefId, int tdspId, int congestionZoneId)
    {
        Session objSession = null;
        HashMap hm = new HashMap();
        try
        {
            logger.info("GETTING ALL ESIIDs BY TDSPs AND CONGESTION ZONE ID");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createSQLQuery("SELECT DISTINCT PRCUSAGEHEADER.ESIID FROM PRC_USAGE_HEADER AS PRCUSAGEHEADER, PRC_TDSP AS TDSP, PRC_PIC AS PRCPIC, PRC_WEATHER_ZONES AS WEATHERZONES WHERE PRCUSAGEHEADER.ESIID = PRCPIC.ESIID AND WEATHERZONES.CONGESTION_ZONE_ID = ? AND SUBSTRING(Load_Profile,CHARINDEX('_',Load_Profile,0)+1,CHARINDEX('_',Load_Profile,CHARINDEX('_',Load_Profile,0)+1)-CHARINDEX('_',Load_Profile,0)-1) = WEATHERZONES.WEATHER_ZONE_CODE  AND PRCUSAGEHEADER.PRICE_RUN_CUST_REF_ID = ? AND TDSP.TDSP_ID = ? AND substring(PRCUSAGEHEADER.ESIID,1,7) = TDSP.ESIID_Prefix");
            objQuery.setInteger(0,congestionZoneId);
            objQuery.setInteger(1,customerRefId);
            objQuery.setInteger(2,tdspId);
            List objList = objQuery.list();
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                Object row = itr.next();
                hm.put(row,row);
            }
            logger.info("GOT ALL ESIIDs BY TDSPs AND CONGESTION ZONE ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL ESIIDs BY TDSPs AND CONGESTION ZONE ID", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL ESIIDs BY TDSPs AND CONGESTION ZONE ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hm;
    }
    
    public HashMap getAllTDSPs(int customerRefId)
    {
        Session objSession = null;
        HashMap hm = new HashMap();
        try
        {
            logger.info("GETTING ALL TDSPs BY CUSTOMER REF ID");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select tdspvo.tdspIdentifier, tdspvo.tdspName  from PricingUsageHeaderVO as pricingusageheadervo, TDSPVO as tdspvo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and substring(pricingusageheadervo.esiId,1,7) = tdspvo.esiIdPrefix");
            objQuery.setInteger(0,customerRefId);
            Iterator itr = objQuery.iterate();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                hm.put(innerRow[0],innerRow[1]);
            }
            logger.info("GOT ALL TDSPs BY CUSTOMER REF ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL TDSPs BY CUSTOMER REF ID", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL TDSPs BY CUSTOMER REF ID", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hm;
    }
    
    public HashMap getAllCongestionZones(int custRefId)
    {
        Session objSession = null;
        HashMap hm = new HashMap();
        try
        {
            logger.info("GETTING ALL CONGESTION ZONES BY CUSTOMER REF ID");
            objSession = HibernateUtil.getSession();
            SQLQuery objQuery = objSession.createSQLQuery("SELECT DISTINCT CONGESTIONZONES.CONGESTION_ZONE_ID, CONGESTIONZONES.CONGESTION_ZONE  FROM PRC_USAGE_HEADER AS PRCUSAGEHEADER, PRC_PIC AS PRCPIC, PRC_WEATHER_ZONES AS WEATHERZONES, PRC_CONGESTION_ZONES AS CONGESTIONZONES WHERE PRCUSAGEHEADER.ESIID = PRCPIC.ESIID AND SUBSTRING(Load_Profile,CHARINDEX('_',Load_Profile,0)+1,CHARINDEX('_',Load_Profile,CHARINDEX('_',Load_Profile,0)+1)-CHARINDEX('_',Load_Profile,0)-1) = WEATHERZONES.WEATHER_ZONE_CODE AND WEATHERZONES.CONGESTION_ZONE_ID = CONGESTIONZONES.CONGESTION_ZONE_ID AND PRCUSAGEHEADER.PRICE_RUN_CUST_REF_ID = ?");
            objQuery.setInteger(0, custRefId);
            List objList = objQuery.list();
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                hm.put(innerRow[0],innerRow[1]);
            }
            logger.info("GOT ALL CONGESTION ZONES BY CUSTOMER REF ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CONGESTION ZONES", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL CONGESTION ZONES", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hm;
    }
    
    public HashMap getAllCongestionZones(int custRefId, int tdspId)
    {
        Session objSession = null;
        HashMap hm = new HashMap();
        try
        {
            logger.info("GETTING ALL CONGESTION ZONES BY CUSTOMER REF ID AND TDSP ID");
            objSession = HibernateUtil.getSession();
            SQLQuery objQuery = objSession.createSQLQuery("SELECT DISTINCT CONGESTIONZONES.CONGESTION_ZONE_ID, CONGESTIONZONES.CONGESTION_ZONE  FROM PRC_USAGE_HEADER AS PRCUSAGEHEADER, PRC_PIC AS PRCPIC, PRC_WEATHER_ZONES AS WEATHERZONES, PRC_CONGESTION_ZONES AS CONGESTIONZONES, PRC_TDSP AS TDSP WHERE PRCUSAGEHEADER.ESIID = PRCPIC.ESIID AND SUBSTRING(Load_Profile,CHARINDEX('_',Load_Profile,0)+1,CHARINDEX('_',Load_Profile,CHARINDEX('_',Load_Profile,0)+1)-CHARINDEX('_',Load_Profile,0)-1) = WEATHERZONES.WEATHER_ZONE_CODE AND WEATHERZONES.CONGESTION_ZONE_ID = CONGESTIONZONES.CONGESTION_ZONE_ID AND PRCUSAGEHEADER.PRICE_RUN_CUST_REF_ID = ? AND SUBSTRING(PRCUSAGEHEADER.ESIID,1,7) = TDSP.ESIID_PREFIX AND TDSP.TDSP_ID = ?");
            objQuery.setInteger(0, custRefId);
            objQuery.setInteger(1, tdspId);
            List objList = objQuery.list();
            Iterator itr = objList.iterator();
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                hm.put(innerRow[0],innerRow[1]);
            }
            logger.info("GOT ALL CONGESTION ZONES BY CUSTOMER REF ID AND TDSP ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL CONGESTION ZONES", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL CONGESTION ZONES", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return hm;
    }
    
    public List getSensitivityGraphDetails(int customerRefId, String esiId, int term)
    {
        Session objSession = null;
        List objList = null;
        int noOfESIId = 0;
        try
        {
            logger.info("GETTING SENSITIVITY GRAPH DETAILS");
            objSession = HibernateUtil.getSession();
            
            if(esiId.trim().length() == 0)
            {
                //No Of EsiIds
                Query objQuery = objSession.createQuery("select count(distinct pricingusageheadervo.esiId) from PricingUsageHeaderVO as pricingusageheadervo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ?");
                objQuery.setInteger(0,customerRefId);
                Iterator itr = objQuery.iterate();
                if(itr.hasNext())
                {
                    noOfESIId = ((Long)itr.next()).intValue();
                }
            }
            else
            {
                StringTokenizer st = new StringTokenizer(esiId,",");
                noOfESIId = st.countTokens();
            }
            List deallevers =objDealLeversDAO.getDealLevers(customerRefId,term);
            Iterator iter = deallevers.iterator();
            HashMap mapdealvalue =  new HashMap();
            while(iter.hasNext())
            {
                DealLevers objdeallevers = (DealLevers)iter.next();
                mapdealvalue.put(new Integer(objdeallevers.getDealLeverIdentifier()),new Double(objdeallevers.getValue()));
            }
            double cust = ((Double)mapdealvalue.get(new Integer(1))).doubleValue();
            double addl =((Double)mapdealvalue.get(new Integer(7))).doubleValue();
            double agnt = ((Double)mapdealvalue.get(new Integer(4))).doubleValue();
            double agg =((Double)mapdealvalue.get(new Integer(5))).doubleValue();
            double bW = ((Double)mapdealvalue.get(new Integer(6))).doubleValue();
            double other = ((Double)mapdealvalue.get(new Integer(2))).doubleValue();
            double margin = ((Double)mapdealvalue.get(new Integer(3))).doubleValue();
            objList = this.getPricesOptimized(customerRefId, esiId,cust,addl,agnt,agg,bW,other,margin,noOfESIId);
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE SENSITIVITY GRAPH DETAILS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET THE SENSITIVITY GRAPH DETAILS", e);
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
    private List getPricesOptimized(int customerRefId, String esiId,double cust,double addl,double agnt,double agg,double bW,double other,double margin, int noOfESIID)
    {
        Session objSession = null;
        List objList = new ArrayList();
        PricingDashBoard objPricingDashBoard = new PricingDashBoard();
        LinkedHashMap lhmContractkWh = new LinkedHashMap();
        LinkedHashMap lhmTotalChargesOptimized = new LinkedHashMap();//Ancillary, Intra Zonal, Fees and Regulatory, Energy Charges, Shaping Premium..
        LinkedHashMap lhmAdditionalVolatilityOptimized = new LinkedHashMap();// Energy Charges, Shaping Premium..
        
        double fptc = 0;
        try
        {
            logger.info("GETTING PRICES");
            objSession = HibernateUtil.getSession();
            String str = "";
            StringTokenizer st = new StringTokenizer(esiId,",");
            while(st.hasMoreTokens())
            {
                if(str.length()<=0)
                    str = "'"+st.nextToken()+"'";
                else
                    str = str + ",'"+st.nextToken()+"'";
            }
            if(hmCharges == null)
            {
                hmCharges = this.loadCharges(customerRefId, esiId, str, objSession);
            }
            
            //Additional Volatility...(Energy Charges, Shaping Premium..)
            Set objSet = hmCharges.keySet();
            Iterator itr = objSet.iterator();
            float charge = 0;
            String key = "";
            String[] keyElements = {};
            int term = 0;
            while(itr.hasNext())
            {
                charge = 0;
                key = (String)itr.next();
                keyElements = key.split(":");
                term = (new Integer(keyElements[4])).intValue();
                if(keyElements[0].equals("2") || (keyElements[0].equals("null") && !keyElements[1].equals("null")))
                {
                    charge =((Float)hmCharges.get(key)).floatValue();
                }
                if(lhmAdditionalVolatilityOptimized.containsKey(new Integer(term)))
                {
                    charge = charge + ((Float)lhmAdditionalVolatilityOptimized.get(new Integer(term))).floatValue();
                }
                lhmAdditionalVolatilityOptimized.put(new Integer(term), new Float(charge));
            }
            
            // Total Charges (Ancillary, Intra Zonal, Fees and Regulatory, Energy Charges, Shaping Premium..)
            itr = objSet.iterator();
            key = "";
            term = 0;
            while(itr.hasNext())
            {
                charge = 0;
                key = (String)itr.next();
                keyElements = key.split(":");
                term = (new Integer(keyElements[4])).intValue();
                if(keyElements[0].equals("1") || keyElements[0].equals("2") || keyElements[0].equals("3") || keyElements[0].equals("4") || keyElements[0].equals("6") || keyElements[0].equals("7") || keyElements[0].equals("8") || (keyElements[0].equals("null") && !keyElements[1].equals("null")))
                {
                    charge =((Float)hmCharges.get(key)).floatValue();
                }
                if(lhmTotalChargesOptimized.containsKey(new Integer(term)))
                {
                    charge = charge + ((Float)lhmTotalChargesOptimized.get(new Integer(term))).floatValue();
                }
                lhmTotalChargesOptimized.put(new Integer(term), new Float(charge));
            }
            
            //Contract kWh
            
            String queryString = "";
            if(!isUsageArchived(customerRefId))
            {
                queryString = "SELECT b.term,sum(c.Forecasted_Usage) FROM PRC_USAGE_HEADER a,PRC_USAGE_HEADER b,PRC_USAGE c WHERE a.PRICE_RUN_CUST_REF_ID = ? and a.term <=b.term AND a.PRICE_RUN_CUST_REF_ID = b.PRICE_RUN_CUST_REF_ID AND a.ESIID = b.ESIID AND a.PRC_USAGE_REF_ID=c.PRC_USAGE_REF_ID";
            }
            else
            {
                queryString = "SELECT b.term,sum(c.Forecasted_Usage) FROM PRC_USAGE_HEADER a,PRC_USAGE_HEADER b,PRC_Usage_Archive c WHERE a.PRICE_RUN_CUST_REF_ID = ? and a.term <=b.term AND a.PRICE_RUN_CUST_REF_ID = b.PRICE_RUN_CUST_REF_ID AND a.ESIID = b.ESIID AND a.PRC_USAGE_REF_ID=c.PRC_USAGE_REF_ID";
            }
            
            if(esiId.length()>0)
            {
                queryString = queryString+" AND a.ESIID IN ("+str+")";
            }
            queryString = queryString+" group by b.term";
            
            Query objQuery = objSession.createSQLQuery(queryString);
            objQuery.setInteger(0,customerRefId);
            itr = objQuery.list().iterator();
            
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                term = ((Integer)innerRow[0]).intValue();
                float usage = ((Double)innerRow[1]).floatValue();
                lhmContractkWh.put(new Integer(term),new Float(usage));
            }
            float contractkWh = 0;
            float cumulativeAddnlVolatility = 0;
            float cumulativeTotalCharges = 0;
            
            for(int i = 1;i <= 60 ;i++)
            {
                objPricingDashBoard.setCustomerCharge(i*noOfESIID);
                Object obj = lhmContractkWh.get(new Integer(i));
                if(obj != null)
                    contractkWh = ((Float)obj).floatValue();
                if(lhmAdditionalVolatilityOptimized.get(new Integer(i)) != null)
                {
                    cumulativeAddnlVolatility = cumulativeAddnlVolatility + ((Float)lhmAdditionalVolatilityOptimized.get(new Integer(i))).floatValue();
                }
                    
                objPricingDashBoard.setAdditionalVolatilityPremium(cumulativeAddnlVolatility);
                objPricingDashBoard.setSalesAgentFee(contractkWh);
                objPricingDashBoard.setAggregatorFee(contractkWh);
                objPricingDashBoard.setBandwidthCharge(contractkWh);
                objPricingDashBoard.setOtherFee(contractkWh);
                objPricingDashBoard.setMargin(contractkWh);
                double total$ = 0;
                if(lhmTotalChargesOptimized.get(new Integer(i)) != null)
                {
                    cumulativeTotalCharges = cumulativeTotalCharges + ((Float)lhmTotalChargesOptimized.get(new Integer(i))).floatValue();
                }
                total$ = cumulativeTotalCharges;
                double oHtotal$ = 0;
                total$ += objPricingDashBoard.getVolatilityPremium();
                oHtotal$ = objPricingDashBoard.getCustomerCharge()*cust;
                oHtotal$ += objPricingDashBoard.getAdditionalVolatilityPremium()*addl/100;
                oHtotal$ +=objPricingDashBoard.getSalesAgentFee()*agnt;
                oHtotal$ +=objPricingDashBoard.getAggregatorFee()*agg;
                oHtotal$ +=objPricingDashBoard.getBandwidthCharge()*bW;
                oHtotal$ +=objPricingDashBoard.getOtherFee()*other;
                fptc = contractkWh==0?0.0:(objPricingDashBoard.getMargin()*margin+oHtotal$+total$)/contractkWh;
                objList.add(new Double(fptc));
            }
            logger.info("GOT PRICES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET PRICES", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET PRICES", e);
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
    private HashMap loadCharges(int customerRefId, String esiId, String esiIdStr, Session objSession)
    {
        logger.info("LOADING CHARGES");
        hmCharges = new HashMap();
        String queryString = "";
        if(!isCostArchived(customerRefId))
        {
            queryString = "select pricingcostvo.energyChargeName.energyChargeIdentifier, pricingcostvo.priceBlock.priceBlockIdentifier, pricingcostvo.tdspChargeName.tdspChargeIdentifier, pricingcostvo.chargeType.chargeTypeIdentifier, pricingcostvo.charge, pricingusageheadervo.term from PricingUsageHeaderVO as pricingusageheadervo, PricingCostVO as pricingcostvo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and pricingusageheadervo.pricingUsageRefId = pricingcostvo.usageRef.pricingUsageRefId";
        }
        else
        {
            queryString = "select pricingcostvo.energyChargeName.energyChargeIdentifier, pricingcostvo.priceBlock.priceBlockIdentifier, pricingcostvo.tdspChargeName.tdspChargeIdentifier, pricingcostvo.chargeType.chargeTypeIdentifier, pricingcostvo.charge, pricingusageheadervo.term from PricingUsageHeaderVO as pricingusageheadervo, PricingCostArchiveVO as pricingcostvo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and pricingusageheadervo.pricingUsageRefId = pricingcostvo.usageRef.pricingUsageRefId";
        }
        if(esiId.length()>0)
        {
            queryString = queryString+" and pricingusageheadervo.esiId in ("+esiIdStr+")";
        }
        Query objQuery = objSession.createQuery(queryString);
        objQuery.setInteger(0, customerRefId);
        Iterator itr = objQuery.iterate();
        float existing = 0;
        float newCharge = 0;
        while(itr.hasNext())
        {
            Object[] innerQuery = (Object[])itr.next();
            String key = innerQuery[0]+":"+innerQuery[1]+":"+innerQuery[2]+":"+innerQuery[3]+":"+innerQuery[5];
            if(hmCharges.containsKey(key))
            {
                existing = ((Float)hmCharges.get(key)).floatValue();
            }
            else
            {
                existing = 0;
            }
            newCharge = existing + ((Float)innerQuery[4]).floatValue(); 
            hmCharges.put(key, new Float(newCharge));
        }
        logger.info("CHARGES ARE LOADED");
        return hmCharges;
    }
    
    private HashMap loadChargesForEnergy(int customerRefId, String esiId, String esiIdStr, Session objSession)
    {
        logger.info("LOADING CHARGES");
        hmWithOutLoss = new HashMap();
        String queryString = "";
        if(!isCostArchived(customerRefId))
        {
            queryString = "select pricingcostvo.energyChargeName.energyChargeIdentifier, pricingcostvo.priceBlock.priceBlockIdentifier, pricingcostvo.tdspChargeName.tdspChargeIdentifier, pricingcostvo.chargeType.chargeTypeIdentifier, pricingcostvo.chargeWOL, pricingusageheadervo.term from PricingUsageHeaderVO as pricingusageheadervo, PricingCostVO as pricingcostvo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and pricingusageheadervo.pricingUsageRefId = pricingcostvo.usageRef.pricingUsageRefId";
        }
        else
        {
            queryString = "select pricingcostvo.energyChargeName.energyChargeIdentifier, pricingcostvo.priceBlock.priceBlockIdentifier, pricingcostvo.tdspChargeName.tdspChargeIdentifier, pricingcostvo.chargeType.chargeTypeIdentifier, pricingcostvo.chargeWOL, pricingusageheadervo.term from PricingUsageHeaderVO as pricingusageheadervo, PricingCostArchiveVO as pricingcostvo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and pricingusageheadervo.pricingUsageRefId = pricingcostvo.usageRef.pricingUsageRefId";
        }
        if(esiId.length()>0)
        {
            queryString = queryString+" and pricingusageheadervo.esiId in ("+esiIdStr+")";
        }
        Query objQuery = objSession.createQuery(queryString);
        objQuery.setInteger(0, customerRefId);
        Iterator itr = objQuery.iterate();
        float existing = 0;
        float newCharge = 0;
        while(itr.hasNext())
        {
            Object[] innerQuery = (Object[])itr.next();
            String key = innerQuery[0]+":"+innerQuery[1]+":"+innerQuery[2]+":"+innerQuery[3]+":"+innerQuery[5];
            if(hmWithOutLoss.containsKey(key))
            {
                existing = ((Float)hmWithOutLoss.get(key)).floatValue();
            }
            else
            {
                existing = 0;
            }
            newCharge = existing + ((Float)innerQuery[4]).floatValue();
            hmWithOutLoss.put(key, new Float(newCharge));
        }
        logger.info("CHARGES ARE LOADED");
        return hmWithOutLoss;
    }
    
    public boolean isUsageArchived(int priceRunCustomerRefId)
    {
        Session objSession = null;
        boolean isArchived = false;
        try
        {
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select count(*) from PricingUsageHeaderVO as pricingusageheadervo, UsageVO as usagevo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and pricingusageheadervo.pricingUsageRefId = usagevo.usageRef.pricingUsageRefId");
            objQuery.setInteger(0, priceRunCustomerRefId);
            Iterator itr = objQuery.iterate();
            if(itr.hasNext())
            {
                if(((Long)itr.next()).intValue()<=0)
                {
                    isArchived = true;
                }
            }
        }
        catch(HibernateException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            objSession.close();
        }
        return isArchived;
    }
    
    public boolean isCostArchived(int priceRunCustomerRefId)
    {
        Session objSession = null;
        boolean isArchived = false;
        try
        {
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select count(*) from PricingUsageHeaderVO as pricingusageheadervo, PricingCostVO as costvo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and pricingusageheadervo.pricingUsageRefId = costvo.usageRef.pricingUsageRefId");
            objQuery.setInteger(0, priceRunCustomerRefId);
            Iterator itr = objQuery.iterate();
            if(itr.hasNext())
            {
                if(((Long)itr.next()).intValue()<=0)
                {
                    isArchived = true;
                }
            }
        }
        catch(HibernateException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            objSession.close();
        }
        return isArchived;
    }
    
    
    
    
    public PricingDashBoard getDashBoardDetails(int customerRefId, int term, String esiId)
    {       
        if(BuildConfig.DMODE)
            System.out.println("UsageRefId:"+customerRefId+":esiId:"+esiId+":term:"+term+"Time:"+new Date());
        
        System.out.println("UsageRefId:"+customerRefId+":esiId:"+esiId+":term:"+term+"Time:"+new Date());
        
        Session objSession = null;
        hmWithOutLoss = new HashMap();
        
        PricingDashBoard objPricingDashBoard = new PricingDashBoard();
        try
        {
            logger.info("GETTING DASH BOARD DETAILS");
            objSession = HibernateUtil.getSession();
            String str = "";
            StringTokenizer st = new StringTokenizer(esiId.trim(),",");
            int noOfEsiIds = 0;
            noOfEsiIds = st.countTokens();
            while(st.hasMoreTokens())
            {
                if(str.length()<=0)
                    str = "'"+st.nextToken()+"'";
                else
                    str = str + ",'"+st.nextToken()+"'";
            }
            
            if(esiId.trim().length()>0)
            {
                objPricingDashBoard.setNoOfEsiIds(noOfEsiIds);
            }
            else
            {
                //No Of EsiIds
                Query objQuery = objSession.createQuery("select count(distinct pricingusageheadervo.esiId) from PricingUsageHeaderVO as pricingusageheadervo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ?");
                objQuery.setInteger(0,customerRefId);
                Iterator itr = objQuery.iterate();
                if(itr.hasNext())
                {
                    objPricingDashBoard.setNoOfEsiIds(((Long)itr.next()).intValue());
                }
            }
            if(BuildConfig.DMODE)
                System.out.println("No Of EsiIds:"+objPricingDashBoard.getNoOfEsiIds());
            
            //to store all the chages into the memory (HashMap)
            hmCharges = this.loadCharges(customerRefId, esiId, str, objSession);
            hmWithOutLoss = this.loadChargesForEnergy(customerRefId, esiId, str, objSession);
            //Shapping premium           
            Set objSet = hmCharges.keySet();
            Iterator itr = objSet.iterator();
            float charge = 0;
            String key = "";
            String[] keyElements = {}; 
            
            String[] keyElementsWOL = {}; 
            
            while(itr.hasNext())
            {
                key = (String)itr.next();
                keyElements = key.split(":");
                if((new Integer(keyElements[4].trim())).intValue()<=term && !keyElements[0].equals("null"))
                {
                    if(keyElements[0].equals("2"))
                    {
                        charge = charge + ((Float)hmCharges.get(key)).floatValue();
                    }
                }
            }
            objPricingDashBoard.setShapingPremium(charge);
            
            //Energy Charges
            itr = objSet.iterator();
            charge = 0;
            while(itr.hasNext())
            {
                key = (String)itr.next();
                keyElements = key.split(":");
                if((new Integer(keyElements[4].trim())).intValue()<=term && keyElements[0].equals("null"))
                {
                    if(!keyElements[1].equals("null"))
                    {
                        charge = charge + ((Float)hmCharges.get(key)).floatValue();
                    }
                }
            }
            objPricingDashBoard.setEnergyCharge(charge);
            
            Set objSetWOL = hmWithOutLoss.keySet();
            Iterator itrWOL = objSetWOL.iterator();
            float chargeWOL = 0;
            String keyWOL = "";
            while(itrWOL.hasNext())
            {
                keyWOL = (String)itrWOL.next();
                keyElementsWOL = keyWOL.split(":");
                if((new Integer(keyElementsWOL[4].trim())).intValue()<=term && keyElementsWOL[0].equals("null"))
                {
                    if(!keyElementsWOL[1].equals("null"))
                    {
                        chargeWOL = chargeWOL + ((Float)hmWithOutLoss.get(keyWOL)).floatValue();
                    }
                }
            }
            objPricingDashBoard.setEnergyChargeWithOutLoss(chargeWOL);
            objPricingDashBoard.setEnergyDiff(objPricingDashBoard.getEnergyCharge()-objPricingDashBoard.getEnergyChargeWithOutLoss());
            //Ancillary Services
            itr = objSet.iterator();
            charge = 0;
            while(itr.hasNext())
            {
                key = (String)itr.next();
                keyElements = key.split(":");
                if((new Integer(keyElements[4].trim())).intValue()<=term  && !keyElements[0].equals("null"))
                {
                    if(keyElements[0].equals("6"))
                    {
                        charge = charge + ((Float)hmCharges.get(key)).floatValue();
                    }
                }
            }
            objPricingDashBoard.setAncillaryServices(charge);
            
            //IntraZonal Congestion Charges
            itr = objSet.iterator();
            charge = 0;
            while(itr.hasNext())
            {
                key = (String)itr.next();
                keyElements = key.split(":");
                if((new Integer(keyElements[4].trim())).intValue()<=term  && !keyElements[0].equals("null"))
                {
                    if(keyElements[0].equals("7"))
                    {
                        charge = charge + ((Float)hmCharges.get(key)).floatValue();
                    }
                }
            }
            objPricingDashBoard.setIntraZonalCongestion(charge);
            
            //Fees And Regulatory
            itr = objSet.iterator();
            charge = 0;
            while(itr.hasNext())
            {
                key = (String)itr.next();
                keyElements = key.split(":");
                if((new Integer(keyElements[4].trim())).intValue()<=term  && !keyElements[0].equals("null"))
                {
                    if(keyElements[0].equals("8") || keyElements[0].equals("3") || keyElements[0].equals("4") || keyElements[0].equals("1"))
                    {
                        charge = charge + ((Float)hmCharges.get(key)).floatValue();
                    }
                }
            }
            objPricingDashBoard.setFeesAndRegulatory(charge);
            //select sum(pricingcostvo.charge) from PricingUsageHeaderVO as pricingusageheadervo, PricingCostVO as pricingcostvo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and pricingusageheadervo.term <= ? and pricingusageheadervo.pricingUsageRefId = pricingcostvo.usageRef.pricingUsageRefId and (pricingcostvo.tdspChargeName.tdspChargeIdentifier is not null or pricingcostvo.chargeType.chargeTypeIdentifier is not null)
            //TDSP Charges
            itr = objSet.iterator();
            charge = 0;
            while(itr.hasNext())
            {
                key = (String)itr.next();
                keyElements = key.split(":");
                if((new Integer(keyElements[4].trim())).intValue()<=term  &&  (!keyElements[2].equals("null") || !keyElements[3].equals("null")))
                {
                    charge = charge + ((Float)hmCharges.get(key)).floatValue();
                }
            }
            objPricingDashBoard.setTdspCharges(charge);

            
            //Contract kWh
            String queryString = "";
            if(!isUsageArchived(customerRefId))
            {
                queryString = "select sum(usagevo.forecastedUsage) from PricingUsageHeaderVO as pricingusageheadervo, UsageVO as usagevo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and pricingusageheadervo.pricingUsageRefId = usagevo.usageRef.pricingUsageRefId and pricingusageheadervo.term <= ?";
            }
            else
            {
                queryString = "select sum(usagevo.forecastedUsage) from PricingUsageHeaderVO as pricingusageheadervo, UsageArchiveVO as usagevo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and pricingusageheadervo.pricingUsageRefId = usagevo.usageRef.pricingUsageRefId and pricingusageheadervo.term <= ?";
            }
            if(esiId.length()>0)
            {
                queryString = queryString+" and pricingusageheadervo.esiId in ("+str+")";
            }
            Query objQuery = objSession.createQuery(queryString);
            objQuery.setInteger(0,customerRefId);
            objQuery.setInteger(1,term);
            
            itr = objQuery.iterate();
            if(itr.hasNext())
            {
                Object obj = itr.next();
                if(obj != null)
                    objPricingDashBoard.setContractkWh(((Double)obj).floatValue());
            }
            if(BuildConfig.DMODE)
                System.out.println("Contract kWh:"+objPricingDashBoard.getContractkWh());
            
            objQuery = objSession.createQuery("select premiumvo.value from PremiumVO as premiumvo where premiumvo.premiumId = ? ");
            objQuery.setInteger(0,5);
            itr = objQuery.iterate();
            float volPre = 0;
            float finalVolPre = 0;
            if(itr.hasNext())
            {
                Object obj = itr.next();
                 volPre = Float.parseFloat(obj.toString());
            }
            //contract Start Date
            objQuery = objSession.createQuery("select priceruncustvo.startDate from PriceRunCustomerVO as priceruncustvo where priceruncustvo.priceRunCustomerRefId = ? ");
            objQuery.setInteger(0,customerRefId);
            itr = objQuery.iterate();
            if(itr.hasNext())
            {
                objPricingDashBoard.setContractStartMonth((Date)itr.next());
            }
            if(BuildConfig.DMODE)
                System.out.println("Contract start Month:"+objPricingDashBoard.getContractStartMonth());
            
            objQuery = objSession.createQuery("select prospective.customerName from PriceRunCustomerVO as priceruncustomervo, ProspectiveCustomerVO as prospective where priceruncustomervo.prospectiveCustomer.prospectiveCustomerId = prospective.prospectiveCustomerId and priceruncustomervo.priceRunCustomerRefId = ?");
            objQuery.setInteger(0, customerRefId);
            itr = objQuery.iterate();
            if(itr.hasNext())
            {
                objPricingDashBoard.setCustomerName((String)itr.next());
            }
            
            //Annual kWh
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(objPricingDashBoard.getContractStartMonth());
            gc.add(Calendar.YEAR,1);
            gc.set(Calendar.MONTH,0);
            gc.set(Calendar.DATE,1);
            AnnualEnergyDetails objAnnualEnergyDetails = null;
            Vector vecAnnualEnergyDetails = new Vector();
            float annualUsage = 0;
            float maxDmnd = 0;
            float maxLoadFactor = 0;
            int noOfDays = 0;
            HashMap hmHistoricalDmnd = new HashMap(); 
             
            queryString = "select picusage.month,sum(picusage.historicalDemand) from PICVO as picvo,PICUsageVO as picusage, PriceRunCustomerVO as cust where picvo.esiId in (select distinct usageHeader.esiId from PricingUsageHeaderVO usageHeader where usageHeader.priceRunCustomer.priceRunCustomerRefId = ?) and picvo.picReferenceId = picusage.picRef.picReferenceId and cust.priceRunCustomerRefId = "+customerRefId+" and picvo.customer.prospectiveCustomerId = cust.prospectiveCustomer.prospectiveCustomerId";
            //queryString = "select picusage.month, sum(picusage.historicalDemand) from PICVO as picvo, PICUsageVO as picusage where picvo.esiId in (select distinct usageHeader.esiId from PricingUsageHeaderVO usageHeader where usageHeader.priceRunCustomer.priceRunCustomerRefId = ?) and picvo.picReferenceId = picusage.picRef.picReferenceId and picvo.";
            if(esiId.length()>0)
            {
                queryString = queryString+" and picvo.esiId in ("+str+")";
            }
            queryString = queryString + " group by picusage.month";
            
            objQuery = objSession.createQuery(queryString);
            objQuery.setInteger(0, customerRefId);
            itr = objQuery.iterate();
            float historicalDmnd = 0;
            while(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                hmHistoricalDmnd.put(innerRow[0],innerRow[1]);
            }
            if(!isUsageArchived(customerRefId))
            {
                queryString = "select sum(usagevo.forecastedUsage) from PricingUsageHeaderVO as pricingusageheadervo, UsageVO as usagevo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and pricingusageheadervo.pricingUsageRefId = usagevo.usageRef.pricingUsageRefId and usagevo.usageStartDate >= ? and usagevo.usageEndDate <= ?";
            }
            else
            {
                queryString = "select sum(usagevo.forecastedUsage) from PricingUsageHeaderVO as pricingusageheadervo, UsageArchiveVO as usagevo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and pricingusageheadervo.pricingUsageRefId = usagevo.usageRef.pricingUsageRefId and usagevo.usageStartDate >= ? and usagevo.usageEndDate <= ?"; 
            }
            if(esiId.length()>0)
            {
                queryString = queryString+" and pricingusageheadervo.esiId in ("+str+")";
            }
           
            for(int i=1;i<=12;i++)
            {
                objAnnualEnergyDetails = new AnnualEnergyDetails();
                objAnnualEnergyDetails.setMonth(i);
                objQuery = objSession.createQuery(queryString);
                objQuery.setInteger(0,customerRefId);
                
                noOfDays = gc.getActualMaximum(Calendar.DATE);
                
                if(BuildConfig.DMODE)
                    System.out.println("NoOFDays:"+noOfDays);
                
                if(BuildConfig.DMODE)
                    System.out.println("Datestart:"+gc.getTime());
                objQuery.setDate(1,gc.getTime());
                gc.add(Calendar.MONTH,1);
                gc.add(Calendar.DATE,-1);
                objQuery.setDate(2,gc.getTime());
                if(BuildConfig.DMODE)
                    System.out.println("DateEnd:"+gc.getTime());
                itr = objQuery.iterate();
                if(itr.hasNext())
                {
                    Object obj = itr.next();
                    if(obj != null)
                        objAnnualEnergyDetails.setUsagekWh(((Double)obj).floatValue());
                }
                gc.add(Calendar.DATE,1);  
                if(BuildConfig.DMODE)
                    System.out.println("kWh:"+objAnnualEnergyDetails.getUsagekWh());
                historicalDmnd = ((Double)hmHistoricalDmnd.get(new Integer(i))).floatValue();
                if(maxDmnd<historicalDmnd)
                {                    
                    maxDmnd = historicalDmnd;
                }
                  if(BuildConfig.DMODE)
                {
                    System.out.println("historicalDmnd:"+historicalDmnd);
                }
                //LoadFactor..
                
                float loadFactor = 0;
                if(historicalDmnd != 0)
                    loadFactor = (objAnnualEnergyDetails.getUsagekWh()* 100)/(historicalDmnd*24*noOfDays);
                
                  maxLoadFactor += loadFactor;
                
                annualUsage += objAnnualEnergyDetails.getUsagekWh(); 
                objAnnualEnergyDetails.setEnergyDemandkW(historicalDmnd);
                objAnnualEnergyDetails.setLoadFactor(loadFactor);
                vecAnnualEnergyDetails.add(objAnnualEnergyDetails);
            }
          
            objPricingDashBoard.setVecAnnualEnergyDetails(vecAnnualEnergyDetails);
            if(BuildConfig.DMODE)
                System.out.println("Annudal kWh:"+annualUsage);
            objPricingDashBoard.setAnnualkWh(annualUsage);
            objPricingDashBoard.setMaxDemandkW(maxDmnd);
            if(BuildConfig.DMODE)
            {
                System.out.println("MaxDemandkW:"+maxDmnd);
                System.out.println("MaxLoadFactor:"+maxLoadFactor);
            }
            
            
            finalVolPre = ((objPricingDashBoard.getEnergyCharge()+objPricingDashBoard.getShapingPremium())*(volPre/100));
            objPricingDashBoard.setVolatilityPremium(finalVolPre);
            objPricingDashBoard.setLoadFactorPercentage(maxLoadFactor/12);
            
            objPricingDashBoard.setCustomerCharge(term*objPricingDashBoard.getNoOfEsiIds());
            objPricingDashBoard.setAdditionalVolatilityPremium(objPricingDashBoard.getEnergyCharge()+objPricingDashBoard.getShapingPremium());
            objPricingDashBoard.setSalesAgentFee(objPricingDashBoard.getContractkWh());
            objPricingDashBoard.setAggregatorFee(objPricingDashBoard.getContractkWh());
            objPricingDashBoard.setBandwidthCharge(objPricingDashBoard.getContractkWh());
            objPricingDashBoard.setOtherFee(objPricingDashBoard.getContractkWh());
            objPricingDashBoard.setMargin(objPricingDashBoard.getContractkWh());
            logger.info("GOT DASH BOARD DETAILS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET DASH BOARD DETAILS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET DASH BOARD DETAILS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        System.out.println("contract kwh b4 :"+objPricingDashBoard.getContractkWh());
        //objPricingDashBoard.setContractkWh(0);
        //System.out.println("contract kwh after :"+objPricingDashBoard.getContractkWh());
        return objPricingDashBoard;
    }
    
    public HeatRateProduct getHeatRateProduct(float fixedHeatRateInput, int term, int customerRefId, String esiId)
    {
        Session objSession = null;
        HeatRateProduct objHeatRateProduct = new HeatRateProduct();
        objHeatRateProduct.setFixedHeatRateInput(fixedHeatRateInput);
        try
        {
            logger.info("GETTING HEAT RATE PRODUCT");
            objSession = HibernateUtil.getSession();
            String str = "";
            StringTokenizer st = new StringTokenizer(esiId.trim(),",");
            int noOfEsiIds = 0;
            noOfEsiIds = st.countTokens();
            while(st.hasMoreTokens())
            {
                if(str.length()<=0)
                    str = "'"+st.nextToken()+"'";
                else
                    str = str + ",'"+st.nextToken()+"'";
            }
            if(esiId.trim().length()>0)
            {
                objHeatRateProduct.setNoOfEsiIds(noOfEsiIds);
            }
            else
            {
                //No Of EsiIds
                Query objQuery = objSession.createQuery("select count(distinct pricingusageheadervo.esiId) from PricingUsageHeaderVO as pricingusageheadervo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ?");
                objQuery.setInteger(0,customerRefId);
                Iterator itr = objQuery.iterate();
                if(itr.hasNext())
                {
                    objHeatRateProduct.setNoOfEsiIds(((Long)itr.next()).intValue());
                }
            }
            
            if(BuildConfig.DMODE)
                System.out.println("No Of EsiIds:"+objHeatRateProduct.getNoOfEsiIds());
            
            //contract Start Date
            Query objQuery = objSession.createQuery("select custpreferencevo.contractStartDate, custpreferencevo.contractEndDate from PriceRunCustomerVO as priceruncustvo, CustomerPreferencesVO as custpreferencevo where priceruncustvo.priceRunCustomerRefId = ? and priceruncustvo.prospectiveCustomer.prospectiveCustomerId = custpreferencevo.prospectiveCustomer.prospectiveCustomerId");
            objQuery.setInteger(0,customerRefId);
            Iterator itr = objQuery.iterate();
            if(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                objHeatRateProduct.setContractStartDate((Date)innerRow[0]);
                objHeatRateProduct.setContractEndDate((Date)innerRow[1]);
            }
            if(BuildConfig.DMODE)
                System.out.println("Contract start Month:"+objHeatRateProduct.getContractStartDate());
            
            PricingDashBoard objPricingDashBoard = this.getDashBoardDetails(customerRefId, term, esiId);
            
            List dealLevers = objDealLeversDAO.getDealLevers(customerRefId,term);
            Iterator dealLeversItr = dealLevers.iterator();
            while(dealLeversItr.hasNext())
            {
                DealLevers objDealLevers = (DealLevers)dealLeversItr.next();
                switch(objDealLevers.getDealLeverIdentifier())
                {
                case 1:
                    objPricingDashBoard.setCustomerCharge(objPricingDashBoard.getCustomerCharge()*objDealLevers.getValue());
                    break;
                case 7:
                    objPricingDashBoard.setAdditionalVolatilityPremium(objPricingDashBoard.getAdditionalVolatilityPremium()*objDealLevers.getValue());
                    break;
                case 4:
                    objPricingDashBoard.setSalesAgentFee(objPricingDashBoard.getSalesAgentFee()*objDealLevers.getValue());
                    break;
                    
                case 5:
                    objPricingDashBoard.setAggregatorFee(objPricingDashBoard.getAggregatorFee()*objDealLevers.getValue());
                    break;
                    
                case 6:
                    objPricingDashBoard.setBandwidthCharge(objPricingDashBoard.getBandwidthCharge()*objDealLevers.getValue());
                    break;
                    
                case 2:
                    objPricingDashBoard.setOtherFee(objPricingDashBoard.getOtherFee()*objDealLevers.getValue());
                    break;
                    
                case 3:
                    objPricingDashBoard.setMargin(objPricingDashBoard.getMargin()*objDealLevers.getValue());
                    break;
                }
            }
            if(BuildConfig.DMODE)
            {
                System.out.println("TotkWH:"+objPricingDashBoard.getContractkWh());
                System.out.println("getCustomerCharge:"+objPricingDashBoard.getCustomerCharge());
                System.out.println("getSalesAgentFee:"+objPricingDashBoard.getSalesAgentFee());
                System.out.println("getAggregatorFee:"+objPricingDashBoard.getAggregatorFee());
                System.out.println("getBandwidthCharge:"+objPricingDashBoard.getBandwidthCharge());
                System.out.println("getOtherFee:"+objPricingDashBoard.getOtherFee());
                System.out.println("getEnergyCharge:"+objPricingDashBoard.getEnergyCharge());
                System.out.println("getShapingPremium:"+objPricingDashBoard.getShapingPremium());
                System.out.println("getAncillaryServices:"+objPricingDashBoard.getAncillaryServices());
                System.out.println("getIntraZonalCongestion:"+objPricingDashBoard.getIntraZonalCongestion());
                System.out.println("getFeesAndRegulatory:"+objPricingDashBoard.getFeesAndRegulatory());
                System.out.println("getMargin:"+objPricingDashBoard.getMargin());
            }
            
            if(objPricingDashBoard.getContractkWh() != 0)
                objHeatRateProduct.setFixedPrice(((objPricingDashBoard.getCustomerCharge()+objPricingDashBoard.getSalesAgentFee()+objPricingDashBoard.getAggregatorFee()+objPricingDashBoard.getBandwidthCharge()+objPricingDashBoard.getOtherFee()+objPricingDashBoard.getEnergyCharge()+objPricingDashBoard.getShapingPremium()+objPricingDashBoard.getAncillaryServices()+objPricingDashBoard.getIntraZonalCongestion()+objPricingDashBoard.getFeesAndRegulatory()+objPricingDashBoard.getMargin())*1000)/objPricingDashBoard.getContractkWh());
            if(BuildConfig.DMODE)
                System.out.println("FixedPrice:"+objHeatRateProduct.getFixedPrice());
            
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(objHeatRateProduct.getContractStartDate());
            gc.set(Calendar.DATE,1);
            Vector vecResult = new Vector();
            float equivalentWholeSalePrice = 0;
            float totKwh = 0;
            for(int i = 1;i<= term;i++)
            {
                IndividualHeatRateProduct objIndividualHeatRateProduct = new IndividualHeatRateProduct();
                objIndividualHeatRateProduct.setContractMonth(i);
                objIndividualHeatRateProduct.setTermMonth(gc.getTime());
                // market date and data source id to be considered..
                objQuery = objSession.createQuery("select hhpricevo.price from HenryHubPriceVO as hhpricevo where hhpricevo.monthYear = ?");
                objQuery.setDate(0, gc.getTime());
                itr = objQuery.iterate();
                if(itr.hasNext())
                {
                    Object obj = itr.next();
                    objIndividualHeatRateProduct.setIndexPrice(((Float)obj).floatValue());
                    if(BuildConfig.DMODE)
                        System.out.println("Price:"+objIndividualHeatRateProduct.getIndexPrice());
                }
                else
                {
                    if(BuildConfig.DMODE)
                        System.out.println("Henre Hub Price Not available for term:"+i+"MonthYear:"+gc.getTime());
                    //market date and data source id to be considered..
                    objQuery = objSession.createQuery("select gaspricevo.price from GasPriceVO as gaspricevo where gaspricevo.monthYear = ?");
                    objQuery.setDate(0, gc.getTime());
                    itr = objQuery.iterate();
                    if(itr.hasNext())
                    {
                        Object obj = itr.next();
                        objIndividualHeatRateProduct.setIndexPrice(((Float)obj).floatValue());
                    }
                }
                String queryString = "select sum(usagevo.forecastedUsage) from PricingUsageHeaderVO as pricingusageheadervo, UsageVO as usagevo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and pricingusageheadervo.pricingUsageRefId = usagevo.usageRef.pricingUsageRefId and usagevo.usageStartDate >= ? and usagevo.usageEndDate <= ?";
                if(esiId.trim().length()>0)
                {
                    queryString = queryString+" and pricingusageheadervo.esiId in ("+str+")";
                }
                objQuery = objSession.createQuery(queryString);
                
                objQuery.setInteger(0,customerRefId);
                if(BuildConfig.DMODE)
                    System.out.println("Datestart:"+gc.getTime());
                objQuery.setDate(1,gc.getTime());
                gc.add(Calendar.MONTH,1);
                gc.add(Calendar.DATE,-1);
                objQuery.setDate(2,gc.getTime());
                
                if(BuildConfig.DMODE)
                    System.out.println("DateEnd:"+gc.getTime());
                itr = objQuery.iterate();
                if(itr.hasNext())
                {
                    Object obj = itr.next();
                    if(obj != null)
                        objIndividualHeatRateProduct.setTotKwh(((Double)obj).floatValue());
                }
                gc.add(Calendar.DATE,1);
                if(BuildConfig.DMODE)
                    System.out.println("kWh:"+objIndividualHeatRateProduct.getTotKwh());
                objIndividualHeatRateProduct.setEquiWholeSale$PerMWH(objIndividualHeatRateProduct.getIndexPrice()*objHeatRateProduct.getFixedHeatRateInput()/1000);
                
                equivalentWholeSalePrice += objIndividualHeatRateProduct.getEquiWholeSale$PerMWH()*objIndividualHeatRateProduct.getTotKwh(); 
                totKwh += objIndividualHeatRateProduct.getTotKwh();
                vecResult.add(objIndividualHeatRateProduct);
            }
            
            if(BuildConfig.DMODE)
                System.out.println("Ratio:"+equivalentWholeSalePrice/totKwh);
            
            if(totKwh != 0)
            objHeatRateProduct.setEquivalentWholeSalePrice(equivalentWholeSalePrice/totKwh);
            objHeatRateProduct.setRetailAdder(objHeatRateProduct.getFixedPrice()-objHeatRateProduct.getEquivalentWholeSalePrice());
            objHeatRateProduct.setVecIndividualHeatRateProduct(vecResult);
            
            objQuery = objSession.createQuery("select prospective.customerName from PriceRunCustomerVO as priceruncustomervo, ProspectiveCustomerVO as prospective where priceruncustomervo.prospectiveCustomer.prospectiveCustomerId = prospective.prospectiveCustomerId and priceruncustomervo.priceRunCustomerRefId = ?");
            objQuery.setInteger(0, customerRefId);
            itr = objQuery.iterate();
            if(itr.hasNext())
            {
                objHeatRateProduct.setCustomerName((String)itr.next());
            }
            logger.info("GOT HEAT RATE PRODUCT");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET HEAT RATE PRODUCT", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET HEAT RATE PRODUCT", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objHeatRateProduct;
    }
    
    public EPP getEPP(float baseGasPrice, int term, int customerRefId, String esiId)
    {
        Session objSession = null;
        EPP objEPP = new EPP();
        objEPP.setBaseGasPrice(baseGasPrice);
        try
        {
            logger.info("GETTING EPP");
            objSession = HibernateUtil.getSession();
            String str = "";
            StringTokenizer st = new StringTokenizer(esiId.trim(),",");
            int noOfEsiIds = 0;
            noOfEsiIds = st.countTokens();
            while(st.hasMoreTokens())
            {
                if(str.length()<=0)
                    str = "'"+st.nextToken()+"'";
                else
                    str = str + ",'"+st.nextToken()+"'";
            }
            
            if(esiId.trim().length()>0)
            {
                objEPP.setNoOfEsiIds(noOfEsiIds);
            }
            else
            {
                //No Of EsiIds
                Query objQuery = objSession.createQuery("select count(distinct pricingusageheadervo.esiId) from PricingUsageHeaderVO as pricingusageheadervo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ?");
                objQuery.setInteger(0,customerRefId);
                Iterator itr = objQuery.iterate();
                if(itr.hasNext())
                {
                    objEPP.setNoOfEsiIds(((Long)itr.next()).intValue());
                }
            }
            if(BuildConfig.DMODE)
                System.out.println("No Of EsiIds:"+objEPP.getNoOfEsiIds());
            
            //contract Start Date
            Query objQuery = objSession.createQuery("select custpreferencevo.contractStartDate, custpreferencevo.contractEndDate from PriceRunCustomerVO as priceruncustvo, CustomerPreferencesVO as custpreferencevo where priceruncustvo.priceRunCustomerRefId = ? and priceruncustvo.prospectiveCustomer.prospectiveCustomerId = custpreferencevo.prospectiveCustomer.prospectiveCustomerId");
            objQuery.setInteger(0,customerRefId);
            Iterator itr = objQuery.iterate();
            if(itr.hasNext())
            {
                Object[] innerRow = (Object[]) itr.next();
                objEPP.setContractStartMonth(((Date)innerRow[0]));
                objEPP.setContractEndMonth(((Date)innerRow[1]));
            }
            if(BuildConfig.DMODE)
                System.out.println("Contract start Month:"+objEPP.getContractStartMonth());
            
            PricingDashBoard objPricingDashBoard = this.getDashBoardDetails(customerRefId,term,esiId.trim());
            List dealLevers = objDealLeversDAO.getDealLevers(customerRefId,term);
            Iterator dealLeversItr = dealLevers.iterator();
            while(dealLeversItr.hasNext())
            {
                DealLevers objDealLevers = (DealLevers)dealLeversItr.next();
                switch(objDealLevers.getDealLeverIdentifier())
                {
                case 1:
                    objPricingDashBoard.setCustomerCharge(objPricingDashBoard.getCustomerCharge()*objDealLevers.getValue());
                    break;
                case 7:
                    objPricingDashBoard.setAdditionalVolatilityPremium(objPricingDashBoard.getAdditionalVolatilityPremium()*objDealLevers.getValue());
                    break;
                case 4:
                    objPricingDashBoard.setSalesAgentFee(objPricingDashBoard.getSalesAgentFee()*objDealLevers.getValue());
                    break;
                    
                case 5:
                    objPricingDashBoard.setAggregatorFee(objPricingDashBoard.getAggregatorFee()*objDealLevers.getValue());
                    break;
                    
                case 6:
                    objPricingDashBoard.setBandwidthCharge(objPricingDashBoard.getBandwidthCharge()*objDealLevers.getValue());
                    break;
                    
                case 2:
                    objPricingDashBoard.setOtherFee(objPricingDashBoard.getOtherFee()*objDealLevers.getValue());
                    break;
                    
                case 3:
                    objPricingDashBoard.setMargin(objPricingDashBoard.getMargin()*objDealLevers.getValue());
                    break;
                }
            }
            
            
            if(BuildConfig.DMODE)
            {
                System.out.println("TotkWH:"+objPricingDashBoard.getContractkWh());
                System.out.println("getCustomerCharge:"+objPricingDashBoard.getCustomerCharge());
                System.out.println("Additional Volatility:"+objPricingDashBoard.getAdditionalVolatilityPremium());
                System.out.println("getSalesAgentFee:"+objPricingDashBoard.getSalesAgentFee());
                System.out.println("getAggregatorFee:"+objPricingDashBoard.getAggregatorFee());
                System.out.println("getBandwidthCharge:"+objPricingDashBoard.getBandwidthCharge());
                System.out.println("getOtherFee:"+objPricingDashBoard.getOtherFee());
                System.out.println("getEnergyCharge:"+objPricingDashBoard.getEnergyCharge());
                System.out.println("getShapingPremium:"+objPricingDashBoard.getShapingPremium());
                System.out.println("getAncillaryServices:"+objPricingDashBoard.getAncillaryServices());
                System.out.println("getIntraZonalCongestion:"+objPricingDashBoard.getIntraZonalCongestion());
                System.out.println("getFeesAndRegulatory:"+objPricingDashBoard.getFeesAndRegulatory());
                System.out.println("getMargin:"+objPricingDashBoard.getMargin());
            }
            
            if(objPricingDashBoard.getContractkWh() != 0)
                objEPP.setFixedPrice(((objPricingDashBoard.getCustomerCharge()+objPricingDashBoard.getSalesAgentFee()+objPricingDashBoard.getAggregatorFee()+objPricingDashBoard.getBandwidthCharge()+objPricingDashBoard.getOtherFee()+objPricingDashBoard.getEnergyCharge()+objPricingDashBoard.getShapingPremium()+objPricingDashBoard.getAncillaryServices()+objPricingDashBoard.getIntraZonalCongestion()+objPricingDashBoard.getFeesAndRegulatory()+objPricingDashBoard.getMargin())*1000)/objPricingDashBoard.getContractkWh());
            if(BuildConfig.DMODE)
                System.out.println("FixedPrice:"+objEPP.getFixedPrice());
            
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(objEPP.getContractStartMonth());
            gc.set(Calendar.DATE,1);
            Vector vecResult = new Vector();
            float weightedAvgGasPriceSumProduct = 0;
            float totKwh = 0;
            float fafMultiplier = 0;
            IndividualEPP objIndividualEPP = null;
            for(int i = 1;i<= term;i++)
            {
                objIndividualEPP = new IndividualEPP();
                objIndividualEPP.setContractMonth(i);
                objIndividualEPP.setTermMonth(gc.getTime());
                // market date and data source id to be considered..
                objQuery = objSession.createQuery("select hhpricevo.price from HenryHubPriceVO as hhpricevo where hhpricevo.monthYear = ?");
                objQuery.setDate(0, gc.getTime());
                itr = objQuery.iterate();
                if(itr.hasNext())
                {
                    Object obj = itr.next();
                    objIndividualEPP.setIndexPrice(((Float)obj).floatValue());
                }
                else
                {
                    //System.out.println("Henre Hub Price Not available for term:"+i+"MonthYear:"+gc.getTime());
                    //market date and data source id to be considered..
                    objQuery = objSession.createQuery("select gaspricevo.price from GasPriceVO as gaspricevo where gaspricevo.monthYear = ?");
                    objQuery.setDate(0, gc.getTime());
                    itr = objQuery.iterate();
                    if(itr.hasNext())
                    {
                        Object obj = itr.next();
                        objIndividualEPP.setIndexPrice(((Float)obj).floatValue());
                    }
                }
                String queryString = "select sum(usagevo.forecastedUsage) from PricingUsageHeaderVO as pricingusageheadervo, UsageVO as usagevo where pricingusageheadervo.priceRunCustomer.priceRunCustomerRefId = ? and pricingusageheadervo.pricingUsageRefId = usagevo.usageRef.pricingUsageRefId and usagevo.usageStartDate >= ? and usagevo.usageEndDate <= ?"; 
                if(esiId.trim().length()>0)
                {
                    queryString = queryString+" and pricingusageheadervo.esiId in ("+str+")";
                }
                objQuery = objSession.createQuery(queryString);
                objQuery.setInteger(0,customerRefId);
                if(BuildConfig.DMODE)
                    System.out.println("Datestart:"+gc.getTime());
                objQuery.setDate(1,gc.getTime());
                gc.add(Calendar.MONTH,1);
                gc.add(Calendar.DATE,-1);
                objQuery.setDate(2,gc.getTime());
                
                if(BuildConfig.DMODE)
                    System.out.println("DateEnd:"+gc.getTime());
                itr = objQuery.iterate();
                if(itr.hasNext())
                {
                    Object obj = itr.next();
                    if(obj != null)
                        objIndividualEPP.setTotKwh(((Double)obj).floatValue());
                }
                gc.add(Calendar.DATE,1);
                if(BuildConfig.DMODE)
                    System.out.println("kWh:"+objIndividualEPP.getTotKwh());
                weightedAvgGasPriceSumProduct += objIndividualEPP.getIndexPrice() * objIndividualEPP.getTotKwh();
                totKwh += objIndividualEPP.getTotKwh();
                vecResult.add(objIndividualEPP);
            }
            if(totKwh != 0)
            objEPP.setWeightedAvgGasPrice(weightedAvgGasPriceSumProduct/totKwh);
            if(BuildConfig.DMODE)
                System.out.println("getWeightedAvgGasPrice:"+objEPP.getWeightedAvgGasPrice());
            if(objEPP.getWeightedAvgGasPrice() != 0)
                fafMultiplier = objEPP.getFixedPrice()/objEPP.getWeightedAvgGasPrice()/1000;
            objEPP.setFafMultiplier(fafMultiplier);
            
            for(int i = 0;i <vecResult.size();i++)
            {
                objIndividualEPP = (IndividualEPP)vecResult.get(i);
                objIndividualEPP.setEquiWholeSale$perMWH(objIndividualEPP.getIndexPrice()*fafMultiplier*1000);
            }
            objEPP.setBaseRate(fafMultiplier*baseGasPrice);
            objEPP.setFuelAdjustmentRate((objEPP.getFixedPrice()/1000)-objEPP.getBaseRate());
            objEPP.setVecIndividualEPP(vecResult);
            objQuery = objSession.createQuery("select prospective.customerName from PriceRunCustomerVO as priceruncustomervo, ProspectiveCustomerVO as prospective where priceruncustomervo.prospectiveCustomer.prospectiveCustomerId = prospective.prospectiveCustomerId and priceruncustomervo.priceRunCustomerRefId = ?");
            objQuery.setInteger(0, customerRefId);
            itr = objQuery.iterate();
            if(itr.hasNext())
            {
                objEPP.setCustomerName((String)itr.next());
            }
            logger.info("GOT EPP");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET EPP", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET EPP", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objEPP;
    }
    
    public List getPriceRunCustomerReferences()
    {
        Session objSession = null;
        Criteria objCriteria = null;
        List objList = null;
        try
        {
            logger.info("GETTING PRICE RUN CUSTOMER REFERENCES");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(PriceRunCustomerVO.class).addOrder(Order.desc("priceRunCustomerRefId"));
            objList = objCriteria.list(); 
            logger.info("GOT PRICE RUN CUSTOMER REFERENCES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET PRICE RUN CUSTOMER REFERENCES", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET PRICE RUN CUSTOMER REFERENCES", e);
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
    
    public HashMap getAllPriceRunResults(Date runFromDate,Date runEndDate, Filter filtCustName, Filter filtPriceRunId, Filter filtSalesRep, Filter filtSalesManager, Filter filtEsiId, int startIndex, int displayCount)
    {
        Session objSession = null;
        Criteria objCriteria = null;
        Criteria objPriceRunRefCriteria = null;
        Criteria objSalesRepCriteria = null;
        Criteria prospectiveCustomerCriteria = null;
        HashMap hmResult = new HashMap();
        Integer totRecordCount = null;
        List objList = null;
        try
        {
            logger.info("GETTING ALL PRICE RUN RESULTS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(PriceRunCustomerVO.class);
            if(runFromDate != null && runEndDate != null)
            {
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(runEndDate);
                gc.add(Calendar.DATE,1);
                objPriceRunRefCriteria = objCriteria.createCriteria("priceRunRef");
                objPriceRunRefCriteria.add(Restrictions.ge("priceRunTime",runFromDate)).add(Restrictions.lt("priceRunTime", gc.getTime())).addOrder(Order.desc("priceRunTime"));
            }
            if(objPriceRunRefCriteria == null)
            {
                objPriceRunRefCriteria = objCriteria.createCriteria("priceRunRef");
            }
            if(filtPriceRunId != null)
            {
                objPriceRunRefCriteria.add(Restrictions.like("priceRunRefNo",filtPriceRunId.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filtPriceRunId.getSpecialFunction())));
            }
            prospectiveCustomerCriteria = objCriteria.createCriteria("prospectiveCustomer");
            if(filtCustName != null)
            {
                prospectiveCustomerCriteria.add(Restrictions.like("customerName", filtCustName.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filtCustName.getSpecialFunction())));
            }
            HashMap hm = new HashMap();
            if(filtEsiId != null)
            {
                Criteria objPICCriteria = objSession.createCriteria(PICVO.class).add(Restrictions.like("esiId", filtEsiId.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filtEsiId.getSpecialFunction())));
                Iterator itr = objPICCriteria.list().iterator();
                while(itr.hasNext())
                {
                    PICVO objPICVO = (PICVO)itr.next();
                    hm.put(new Integer(objPICVO.getCustomer().getProspectiveCustomerId()), new Integer(objPICVO.getCustomer().getProspectiveCustomerId()));
                }
                hm.put(new Integer(0), new Integer(0));
            }
            if(hm.keySet().size()>0)
            {
                Integer[] objInteger = new Integer[hm.keySet().size()];
                hm.keySet().toArray(objInteger);
                Criterion objCustomerId = Restrictions.in("prospectiveCustomerId",objInteger);
                Criterion objCMSID = null;
                if(filtEsiId.getFieldValue().length()<=9)
                {
                    objCMSID = Restrictions.eq("customerId",new Integer(filtEsiId.getFieldValue().trim()));
                    prospectiveCustomerCriteria.add(Restrictions.or(objCustomerId, objCMSID));
                }
                else
                {
                    prospectiveCustomerCriteria.add(objCustomerId);
                }
            }
            if(filtSalesRep != null)
            {
                objSalesRepCriteria = prospectiveCustomerCriteria.createCriteria("salesRep");
                Criterion objFirstName = Restrictions.like("firstName",filtSalesRep.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filtSalesRep.getSpecialFunction())); 
                Criterion objLastName = Restrictions.like("lastName",filtSalesRep.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filtSalesRep.getSpecialFunction())); 
                objSalesRepCriteria.add(Restrictions.or(objFirstName, objLastName));
            }
            if(filtSalesManager != null)
            {
                if(objSalesRepCriteria == null)
                {
                    objSalesRepCriteria = prospectiveCustomerCriteria.createCriteria("salesRep");
                }
                Criteria objParentUser = objSalesRepCriteria.createCriteria("parentUser");
                Criterion objFirstName = Restrictions.like("firstName",filtSalesManager.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filtSalesManager.getSpecialFunction())); 
                Criterion objLastName = Restrictions.like("lastName",filtSalesManager.getFieldValue(),(MatchMode)HibernateUtil.htMatchCase.get(filtSalesManager.getSpecialFunction()));
                objParentUser.add(Restrictions.or(objFirstName, objLastName));
            }
            totRecordCount = new Integer(objCriteria.list().size());
            objCriteria.setFirstResult(startIndex);
            objCriteria.setMaxResults(displayCount);
            objList = objCriteria.list();
            hmResult.put("TotalRecordCount",totRecordCount);
            hmResult.put("Records",objList);
            logger.info("GOT ALL PRICE RUN RESULTS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET ALL PRICE RUN RESULTS", e);
            e.printStackTrace();
        }
        catch (Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET ALL PRICE RUN RESULTS", e);
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
    private void loadTermForcastedUsageIDR(float dlf,PricingUsageHeaderVO objPricingUsageHeaderVO,TermDetails objTermDetails,String esiId, Session objSession)
    {
        try
        {
            logger.info("LOADING TERM FORCASTED USAGE IDR");
            Vector vecIndividualTermDetails = objTermDetails.getVecIndividualTermDetails();
            for(int i=0;i<vecIndividualTermDetails.size();i++)
            {
                IndividualTermDetails objIndividualTermDetails = (IndividualTermDetails)vecIndividualTermDetails.get(i);
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(objIndividualTermDetails.getTermMonth());
                float tlf = 0;
                float lossFactor = 1.0f;
                int month = gc.get(Calendar.MONTH)+1;
                OnPkOffPkDetails objOnPkOffPkDetails =  (OnPkOffPkDetails)HibernateUtil.hmTLF.get(new Integer(month));
                
                String key = esiId+":"+(gc.get(Calendar.MONTH)+1)+":"+2;
                List objList = (List)hmIDRDetails.get(key);
                Iterator itr = null;
                if(objList != null)
                {
                    itr = objList.iterator();
                    while(itr.hasNext())
                    {
                        IDRVO objIDRVO = (IDRVO)itr.next();
                        int hour = objIDRVO.getHour();
                        if(hour <7 || hour >22)
                        {
                            tlf = objOnPkOffPkDetails.getOffPeakLoss();
                        }
                        else
                        {
                            tlf = objOnPkOffPkDetails.getOnPeakLoss();
                        }
                        
                        lossFactor = 1 + dlf + tlf;
                        System.out.println("Inside 1st cond :");
                        System.out.println("off peak :"+ objOnPkOffPkDetails.getOffPeakLoss());  
                        System.out.println("on peak :"+ objOnPkOffPkDetails.getOnPeakLoss());  
                        System.out.println("loss factor :"+lossFactor);
                       UsageVO objUsageVO = new UsageVO();
                        DayTypesVO objDayTypesVO = new DayTypesVO();
                        objDayTypesVO.setDayTypeId(2);
                        objUsageVO.setDayType(objDayTypesVO);
                       // int usageTermDetailsId = this.saveUsageTermDetails(objPricingUsageHeaderVO, objIndividualTermDetails.getTermMonth(), 2, objIndividualTermDetails.getStartDate(),objIndividualTermDetails.getEndDate(), objSession);
                        this.saveUsage(objUsageVO,objPricingUsageHeaderVO,objIndividualTermDetails.getTermMonth(),hour,objIndividualTermDetails.getStartDate(),objIndividualTermDetails.getEndDate(),objIndividualTermDetails.getNoOfWD() * objIDRVO.getValue(), objIndividualTermDetails.getNoOfWD() * objIDRVO.getValue()*lossFactor);
                    }
                }
                key = esiId+":"+(gc.get(Calendar.MONTH)+1)+":"+3;
                objList = (List)hmIDRDetails.get(key);
                if(objList != null)
                {
                    itr = objList.iterator();
                    while(itr.hasNext())
                    {
                        IDRVO objIDRVO = (IDRVO)itr.next();
                        int hour = objIDRVO.getHour();
                        if(hour <7 || hour >22)
                        {
                            tlf = objOnPkOffPkDetails.getOffPeakLoss();
                        }
                        else
                        {
                            tlf = objOnPkOffPkDetails.getOnPeakLoss();
                        }
                        lossFactor = 1 + dlf + tlf;
                        System.out.println("Inside 2nd cond :");  
                        System.out.println("off peak :"+ objOnPkOffPkDetails.getOffPeakLoss());  
                        System.out.println("on peak :"+ objOnPkOffPkDetails.getOnPeakLoss());  
                        System.out.println("loss factor :"+lossFactor);
                        UsageVO objUsageVO = new UsageVO();
                        DayTypesVO objDayTypesVO = new DayTypesVO();
                        objDayTypesVO.setDayTypeId(3);
                        objUsageVO.setDayType(objDayTypesVO);
                        this.saveUsage(objUsageVO,objPricingUsageHeaderVO,objIndividualTermDetails.getTermMonth(),hour,objIndividualTermDetails.getStartDate(),objIndividualTermDetails.getEndDate(),objIndividualTermDetails.getNoOfWE() * objIDRVO.getValue(), objIndividualTermDetails.getNoOfWE() * objIDRVO.getValue()*lossFactor);
                    }
                }
            }
            logger.info("TERM FORCASTED USAGE IDR IS LOADED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING LOAD TERM FORCASTED USAGE IDR", e);
            e.printStackTrace();
        }
    }
    
    private void loadForecastedUsage(float dlf,PICVO objPICVO, List listPICUsages, TDSPVO objTDSPVO, PricingUsageHeaderVO objPricingUsageHeaderVO, Date contractStartDate,TermDetails objTermDetails,String esiId,String meterReadCycle, String meterType, boolean availIdr, Session objSession)
    {
        try
        {
            logger.info("LOADING FORCASTED USAGE FOR " + meterType);
            objTermDetails = this.getTermDetails(objTDSPVO, meterReadCycle, objTermDetails, contractStartDate);
            objTermDetails = this.splitTermDetails(objTermDetails);
            if(BuildConfig.DMODE)
                System.out.println("loadTermForcastedUsage:Starts:"+new Date());
            
            if(meterType.equals("IDR") && availIdr)
            {
                if(BuildConfig.DMODE)
                    System.out.println("loadTermForecastedUsageIDR:starts:"+new Date());
                this.loadTermForcastedUsageIDR(dlf,objPricingUsageHeaderVO, objTermDetails, esiId, objSession);
                if(BuildConfig.DMODE)
                    System.out.println("loadTermForecastedUsageIDR:ends:"+new Date());
            }
            else
            {
                if(BuildConfig.DMODE)
                    System.out.println("loadTermForcastedUsageNIDR:starts:"+new Date());
                this.loadTermForcastedUsageNIDR(objPICVO, objPricingUsageHeaderVO, objTermDetails, esiId);
                if(BuildConfig.DMODE)
                    System.out.println("loadTermForcastedUsageNIDR:ends:"+new Date());
            }
            
            if(BuildConfig.DMODE)
                System.out.println("loadTermForcastedUsage:ends:"+new Date());
            objTermDetails = this.loadApparentPwrAndNcpDmnd(objPICVO, listPICUsages, objTDSPVO, objTermDetails);
            if(BuildConfig.DMODE)
                System.out.println("loadApparentPwrAndNcpDmnd:ends:"+new Date());
            this.loadApparentPower(objPricingUsageHeaderVO,objTermDetails);
            if(BuildConfig.DMODE)
                System.out.println("loadApparentPower:ends:"+new Date());
            this.loadNCPDemand(objPricingUsageHeaderVO,objTermDetails);
            if(BuildConfig.DMODE)
                System.out.println("loadNCPDemand:ends:"+new Date());
            logger.info("FORCASTED USAGE IS LOADED FOR " + meterType);
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING LOAD FORCASTED USAGE", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
    }
    
    private void loadApparentPower(PricingUsageHeaderVO objPricingUsageHeaderVO, TermDetails objTermDetails)
    {
        Session objSession = null;
        try
        {
            logger.info("LOADING APPARENT POWER");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            ApparentPowerVO objApparentPowerVO = new ApparentPowerVO();
            objApparentPowerVO.setApparentPower(objTermDetails.getApparentPower());
            objApparentPowerVO.setUsageRef(objPricingUsageHeaderVO);
            UOMVO objUOMVO  = new UOMVO();
            objUOMVO.setUomIdentifier(6);
            objApparentPowerVO.setUnit(objUOMVO);
            objSession.save(objApparentPowerVO);
            objSession.getTransaction().commit();
            logger.info("APPARENT POWER IS LOADED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING LOAD THE APPARENT POWER", e);
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
        
    }
    private void loadNCPDemand(PricingUsageHeaderVO objPricingUsageHeaderVO, TermDetails objTermDetails)
    {
        Session objSession = null;
        try
        {
            logger.info("LOADING NCP DEMAND");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            EnergyDemandVO objEnergyDemandVO = new EnergyDemandVO();
            objEnergyDemandVO.setEnergyDemand(objTermDetails.getEnergyDemand());
            objEnergyDemandVO.setUsageRef(objPricingUsageHeaderVO);
            UOMVO objUOMVO  = new UOMVO();
            objUOMVO.setUomIdentifier(2);
            objEnergyDemandVO.setUnit(objUOMVO);
            objEnergyDemandVO.setBillingDemand(objTermDetails.getBillingDemand());
            objSession.save(objEnergyDemandVO);
            objSession.getTransaction().commit();
            logger.info("NCP DEMAND IS LOADED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING LOAD THE NCP DEMAND", e);
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
    }
    
    private TermDetails loadApparentPwrAndNcpDmnd(PICVO objPICVO, List listPICUsages, TDSPVO objTDSPVO, TermDetails objTermDetails)
    {
        Session objSession = null;
        try
        {
            logger.info("LOADING APPARENT PWR AND NCP DEMAND");
            objSession = HibernateUtil.getSession();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(objTermDetails.getTermEndDate());
            float apparentPower = 0;
            float energyDemand = 0;
            PICUsageVO objPICUsageVO = null;
            Iterator itr = listPICUsages.iterator();
            while(itr.hasNext())
            {
                objPICUsageVO = (PICUsageVO)itr.next();
                if(objPICUsageVO.getMonth() == gc.get(Calendar.MONTH)+1)
                {
                    break;
                }
            }
            apparentPower = objPICUsageVO.getHistoricalApparentPower();
            energyDemand = objPICUsageVO.getHistoricalDemand();
            objTermDetails.setApparentPower(apparentPower);
            objTermDetails.setEnergyDemand(energyDemand);
            if(BuildConfig.DMODE)
            {
                System.out.println("****TDSP:TDSPID****:"+objTDSPVO.getTdspIdentifier());
                System.out.println("****PICVO:PicRefId****:"+objPICVO.getPicReferenceId());
                System.out.println("****PICVO:Month****:"+objPICUsageVO.getMonth());
                System.out.println("****ApparentPower****:"+apparentPower);
                System.out.println("****energyDemand****:"+energyDemand);
            }
            
            float demandRatchetPercentage = objPICVO.getDemandRatchetPercentage();
            float maxDemandPower = 0;
            float maxApparentPower = 0;
            if(objTDSPVO.getTdspIdentifier() == 1)
            {
                maxApparentPower = objPICVO.getMaxkVA();
                if(energyDemand>demandRatchetPercentage * maxApparentPower)
                {
                    objTermDetails.setBillingDemand(energyDemand);
                }
                else
                {
                    objTermDetails.setBillingDemand(demandRatchetPercentage * maxApparentPower);
                }
            }
            else
            {
                maxDemandPower = objPICVO.getMaxkW();
                if(energyDemand>demandRatchetPercentage * maxDemandPower)
                {
                    objTermDetails.setBillingDemand(energyDemand);
                }
                else
                {
                    objTermDetails.setBillingDemand(demandRatchetPercentage * maxDemandPower);
                }
            }
            if(BuildConfig.DMODE)
            {
                System.out.println("****maxApparentPower****:"+maxApparentPower);
                System.out.println("****maxDemandPower****:"+maxDemandPower);
                System.out.println("****demandRatchetPercentage****:"+demandRatchetPercentage);
            }
            logger.info("APPARENT PWR AND NCP DEMAND IS LOADED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING LOAD APPARENT PWR AND NCP DEMAND", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objTermDetails;
    }
    
    private TermDetails splitTermDetails(TermDetails objTermDetails)
    {
        logger.info("SPLITTING TERM DETAILS");
        Date startDate = objTermDetails.getTermStartDate();
        Date endDate = objTermDetails.getTermEndDate();
        GregorianCalendar gcStart = new GregorianCalendar();
        gcStart.setTime(startDate);
        GregorianCalendar gcEnd = new GregorianCalendar();
        gcEnd.setTime(endDate);
        Vector vecIndividualTermDetails = new Vector();
        GregorianCalendar gcTermMonth = new GregorianCalendar();
        while(gcStart.getTime().compareTo(gcEnd.getTime())<=0)
        {
            IndividualTermDetails  objIndividualTermDetails = new IndividualTermDetails();
            gcTermMonth.setTime(gcStart.getTime());
            gcTermMonth.set(Calendar.DATE,1);
            objIndividualTermDetails.setTermMonth(gcTermMonth.getTime());
            objIndividualTermDetails.setStartDate(gcStart.getTime());
            gcStart.set(Calendar.DATE,gcStart.getActualMaximum(Calendar.DATE));
            if(gcStart.getTime().after(gcEnd.getTime()))
            {
                objIndividualTermDetails.setEndDate(gcEnd.getTime());
            }
            else
            {
                objIndividualTermDetails.setEndDate(gcStart.getTime());
            }
            objIndividualTermDetails = this.updateDayTypeDetails(objIndividualTermDetails);
            vecIndividualTermDetails.add(objIndividualTermDetails);
            gcStart.add(Calendar.DATE,1);
        }
        objTermDetails.setVecIndividualTermDetails(vecIndividualTermDetails);
        logger.info("TERM DETAILS ARE SPLITTED");
        return objTermDetails;
    }
    
    private IndividualTermDetails updateDayTypeDetails(IndividualTermDetails objIndividualTermDetails)
    {
        logger.info("UPDATING DAY TYPE DETAILS");
        int noOfWD = 0;
        int noOfWE = 0;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(objIndividualTermDetails.getStartDate());
        
        while(gc.getTime().compareTo(objIndividualTermDetails.getEndDate())<=0)
        {
            if(gc.get(Calendar.DAY_OF_WEEK) == 1 || gc.get(Calendar.DAY_OF_WEEK) == 7)
            {
                noOfWE++;
            }
            else
            {
                noOfWD++;
            }
            gc.add(Calendar.DATE,1);
        }
        List objList = (ArrayList)CalendarDAO.getHolidays(objIndividualTermDetails.getStartDate(), objIndividualTermDetails.getEndDate());
        Iterator itr = objList.iterator();
        while(itr.hasNext())
        {
            Date date = (Date)itr.next();
            gc.setTime(date);
            if(gc.get(Calendar.DAY_OF_WEEK) != 1 && gc.get(Calendar.DAY_OF_WEEK) != 7)
            {
                noOfWD--;
                noOfWE++;
            }
        }
        objIndividualTermDetails.setNoOfWD(noOfWD);
        objIndividualTermDetails.setNoOfWE(noOfWE);
        logger.info("DAY TYPE DETAILS ARE UPDATED");
        return objIndividualTermDetails;
    }
    
    private TermDetails getTermDetails(TDSPVO objTDSPV,String meterReadCycle,TermDetails objTermDetails,Date contractStartDate)
    {
        logger.info("GETTING TERM DETAILS");
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(contractStartDate);
        gc.set(Calendar.DATE,1);
        gc.add(Calendar.MONTH,objTermDetails.getTerm()-1);
        Date startDate = objTDSPV.getReadDate(meterReadCycle, gc.getTime());
        if(BuildConfig.DMODE)
        {
            System.out.println("TDSPVO:"+objTDSPV.getTdspIdentifier());
            System.out.println("MRCycle:"+meterReadCycle);
            System.out.println("Month:"+gc.getTime());
            System.out.println("ReadStartDate:"+startDate);
        }
        gc.add(Calendar.MONTH,1);
        Date endDate = objTDSPV.getReadDate(meterReadCycle, gc.getTime());
        objTermDetails.setTermStartDate(startDate);
        if(BuildConfig.DMODE)
        {
            System.out.println("Month:"+gc.getTime());
            System.out.println("ReadStartDate:"+endDate);
        }
        gc.setTime(endDate);
        gc.add(Calendar.DATE,-1);
        objTermDetails.setTermEndDate(gc.getTime());
        logger.info("GOT TERM DETAILS");
        return objTermDetails;
    }
    
    private void loadTermForcastedUsageNIDR(PICVO objPICVO, PricingUsageHeaderVO objPricingUsageHeaderVO,TermDetails objTermDetails,String esiId)
    {
        try
        {
            logger.info("LOADING TERM FORCASTED USAGE FOR NIDR");
            Vector vecIndividualTermDetails = objTermDetails.getVecIndividualTermDetails();
            for(int i=0;i<vecIndividualTermDetails.size();i++)
            {
                IndividualTermDetails objIndividualTermDetails = (IndividualTermDetails)vecIndividualTermDetails.get(i);
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(objIndividualTermDetails.getTermMonth());
                List aggregateVOS = objPICVO.getAggregateVOS(esiId, gc.get(Calendar.MONTH)+1, 2);
                Iterator itr = null;
                if(aggregateVOS != null)
                {
                    itr = aggregateVOS.iterator();
                    while(itr.hasNext())
                    {
                        ActualUsageAggregationVO objTempActualUsageAggregationVO = (ActualUsageAggregationVO)itr.next();
                        int hour = objTempActualUsageAggregationVO.getHour();
                        float perDayUsage = objTempActualUsageAggregationVO.getPerDayUsage();
                        float perDayUsageWL = objTempActualUsageAggregationVO.getPerDayUsageWL();
                        UsageVO objUsageVO = new UsageVO();
                        DayTypesVO objDayTypesVO = new DayTypesVO();
                        objDayTypesVO.setDayTypeId(2);
                        objUsageVO.setDayType(objDayTypesVO);
                        this.saveUsage(objUsageVO,objPricingUsageHeaderVO,objIndividualTermDetails.getTermMonth(),hour,objIndividualTermDetails.getStartDate(),objIndividualTermDetails.getEndDate(),objIndividualTermDetails.getNoOfWD() * perDayUsage, objIndividualTermDetails.getNoOfWD() * perDayUsageWL);
                    }
                }
                aggregateVOS = objPICVO.getAggregateVOS(esiId,gc.get(Calendar.MONTH)+1,3);
                if(aggregateVOS != null)
                {
                    itr = aggregateVOS.iterator();
                    while(itr.hasNext())
                    {
                        ActualUsageAggregationVO objTempActualUsageAggregationVO = (ActualUsageAggregationVO)itr.next();
                        int hour = objTempActualUsageAggregationVO.getHour();
                        float perDayUsage = objTempActualUsageAggregationVO.getPerDayUsage();
                        float perDayUsageWL = objTempActualUsageAggregationVO.getPerDayUsageWL();
                        UsageVO objUsageVO = new UsageVO();
                        DayTypesVO objDayTypesVO = new DayTypesVO();
                        objDayTypesVO.setDayTypeId(3);
                        objUsageVO.setDayType(objDayTypesVO);
                        this.saveUsage(objUsageVO,objPricingUsageHeaderVO,objIndividualTermDetails.getTermMonth(),hour,objIndividualTermDetails.getStartDate(),objIndividualTermDetails.getEndDate(),objIndividualTermDetails.getNoOfWE() * perDayUsage, objIndividualTermDetails.getNoOfWE() * perDayUsageWL);
                    }
                }
            }
            logger.info("TERM FORCASTED USAGE FOR NIDR IS LOADED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING LOAD THE TERM FORCASTED USAGE FOR NIDR", e);
            e.printStackTrace();
        }
    }
    private void saveUsage(UsageVO objUsageVO,PricingUsageHeaderVO objPricingUsageHeaderVO, Date termMonth,int hour,Date startDate, Date endDate,float usage, float usageWL)
    {
        logger.info("SAVING THE USAGE");
        final String delimiter=",";
        final String lineSeparator=System.getProperty("line.separator");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        objUsageVO.setUsageRef(objPricingUsageHeaderVO);
        objUsageVO.setTermMonth(termMonth);
        objUsageVO.setHour(hour);
        UOMVO objUOMVO = new UOMVO();
        objUOMVO.setUomIdentifier(3);
        objUsageVO.setUnit(objUOMVO);
        objUsageVO.setUsageStartDate(startDate);
        objUsageVO.setUsageEndDate(endDate);
        objUsageVO.setForecastedUsage(usage);
        objUsageVO.setForecastedUsageWL(usageWL);
        HibernateUtil.objUsageVOS.add(objUsageVO);
        
        //System.out.println(objUsageVO.getUsageRef().getPricingUsageRefId()+delimiter+objUOMVO.getUomIdentifier()+delimiter+sdf.format(termMonth)+delimiter+objUsageVO.getDayType().getDayTypeId()+delimiter+objUsageVO.getHour()+delimiter+sdf.format(startDate)+delimiter+sdf.format(endDate)+delimiter+objUsageVO.getForecastedUsage());
        
        sbUsage.append(objUsageVO.getUsageRef().getPricingUsageRefId()).append(delimiter)
        .append(objUOMVO.getUomIdentifier()).append(delimiter)
        .append(sdf.format(termMonth)).append(delimiter)
        .append(objUsageVO.getDayType().getDayTypeId()).append(delimiter)
        .append(objUsageVO.getHour()).append(delimiter)
        .append(sdf.format(startDate)).append(delimiter)
        .append(sdf.format(endDate)).append(delimiter)
        .append(objUsageVO.getForecastedUsage()).append(delimiter)
        .append(objUsageVO.getForecastedUsageWL()).append(lineSeparator);
        logger.info("USAGE IS SAVED");
    }
    
    public int[] getPreferenceTerms(int customerRefId)
    {
        Session objSession = null;
        int[] terms = null;
        
        try
        {
            logger.info("GETTING PREFERENCE TERMS BY CUSTOMER REF ID");
            objSession = HibernateUtil.getSession();
            Query objQuery = objSession.createQuery("select customerpreferenceTerms.term from PriceRunCustomerVO as priceruncustomervo, CustomerPreferencesTermsVO as customerpreferenceTerms where customerpreferenceTerms.prospectiveCustomer.prospectiveCustomerId = priceruncustomervo.prospectiveCustomer.prospectiveCustomerId and priceruncustomervo.priceRunCustomerRefId = ? order by customerpreferenceTerms.term");
            objQuery.setInteger(0,customerRefId);
            Iterator itr = objQuery.iterate();
            Vector vecTerms = new Vector();
            while(itr.hasNext())
            {
                vecTerms.add(itr.next());
            }
            terms = new int[vecTerms.size()];
            for(int i = 0;i<vecTerms.size();i++)
            {
                terms[i] = ((Integer)vecTerms.get(i)).intValue();
            }
            logger.info("GOT PREFERENCE TERMS BY CUSTOMER REF ID");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE PREFERENCE TERMS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return terms;
    }
    
    private CustomerPreferencesVO getContract(int prospectiveCustomerId)
    {
        Session objSession = null;
        CustomerPreferencesVO objCustomerPreferencesVO = null;
        try
        {
            logger.info("GETTING CONTRACT");
            objSession = HibernateUtil.getSession();
            objCustomerPreferencesVO = (CustomerPreferencesVO)objSession.createCriteria(CustomerPreferencesVO.class).add(Restrictions.eq("prospectiveCustomer.prospectiveCustomerId",new Integer(prospectiveCustomerId))).uniqueResult();
            logger.info("GOT CONTRACT");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE CONTRACT", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objCustomerPreferencesVO;
    }
    
    private String createPriceRun(Session objSession, String runType, Date marketDate, float gasPrice, String runBy,Date gasPriceDate)
    {
        PriceRunHeaderVO objPriceRunHeaderVO = new PriceRunHeaderVO();
        try
        {
            logger.info("CREATING PRICE RUN");
           // objSession.beginTransaction();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            objPriceRunHeaderVO.setPriceRunRefNo(sdf.format(new Date()));
            objPriceRunHeaderVO.setPriceRunTime(new Date());
            objPriceRunHeaderVO.setFwdCurveDate(marketDate);
            objPriceRunHeaderVO.setGasPrice(gasPrice);
            objPriceRunHeaderVO.setRunBy(runBy);
            objPriceRunHeaderVO.setRunType(runType);
            objPriceRunHeaderVO.setGasPriceDate(gasPriceDate);
            objPriceRunHeaderVO.setComments("Success");
            objSession.save(objPriceRunHeaderVO);
           // objSession.getTransaction().commit();
            logger.info("PRICE RUN IS CREATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING CREATE THE PRICE RUN", e);
            //objSession.getTransaction().rollback();
            e.printStackTrace();
        }
        return objPriceRunHeaderVO.getPriceRunRefNo();
    }

    private String createMMPriceRun(Session objSession, String runBy, String priceRunId,Date gasDate, Date forwardDate, float value)
    {
        MMPriceRunHeaderVO objMMPriceRunHeaderVO = new MMPriceRunHeaderVO();
        try
        {
            logger.info("CREATING MM PRICE RUN");
            ContractsDAO objContractsDAO = new ContractsDAO();
           // objSession.beginTransaction();
            objMMPriceRunHeaderVO.setPriceRunRefNo(priceRunId);
            objMMPriceRunHeaderVO.setPriceRunTime(new Date());
            objMMPriceRunHeaderVO.setRunBy(runBy);
            objMMPriceRunHeaderVO.setStatus(true);
            objMMPriceRunHeaderVO.setGasPriceDate(gasDate);
            objMMPriceRunHeaderVO.setForwardCurveDate(forwardDate);
            objMMPriceRunHeaderVO.setGasValue(value);
            objMMPriceRunHeaderVO.setOfferDate(new Date());
            objMMPriceRunHeaderVO.setExpiredate(objContractsDAO.cpeExpiryDate(new Date()));
            objMMPriceRunHeaderVO.setComments("Success");
            objSession.save(objMMPriceRunHeaderVO);
           // objSession.getTransaction().commit();
            logger.info("MM PRICE RUN IS CREATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING CREATE MM PRICE RUN", e);
           // objSession.getTransaction().rollback();
            e.printStackTrace();
        }
        return objMMPriceRunHeaderVO.getPriceRunRefNo();
    }
    
    private int createCustomerPriceReference(String priceRunRefId, int prospectiveCustomerId, Session objSession)
    {
        PriceRunCustomerVO objPriceRunCustomerVO = null;
        PriceRunHeaderVO objPriceRunHeaderVO = new PriceRunHeaderVO();
        ProspectiveCustomerDAO objProspectiveCustomerDAO =  new ProspectiveCustomerDAO();
        objPriceRunHeaderVO.setPriceRunRefNo(priceRunRefId);
        Date contractStartDate = objProspectiveCustomerDAO.getProspectiveCustomerPreferences(prospectiveCustomerId).getContractStartDate();
        ProspectiveCustomerVO objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(prospectiveCustomerId);
        //Session objSession = null;
        try
        {
            logger.info("CREATING CUSTOMER PRICE REFERENCE BY PRICE RUN REF ID AND PROSPECTIVE CUSTOMER ID");
           // objSession = HibernateUtil.getSession();
           // objSession.beginTransaction();
            objPriceRunCustomerVO = new PriceRunCustomerVO();
            objPriceRunCustomerVO.setPriceRunRef(objPriceRunHeaderVO);
            objPriceRunCustomerVO.setProspectiveCustomer(objProspectiveCustomerVO);
            objPriceRunCustomerVO.setStartDate(contractStartDate);
            objPriceRunCustomerVO.setTaxExempt(objProspectiveCustomerVO.isTaxExempt());
            objSession.save(objPriceRunCustomerVO);
           // objSession.getTransaction().commit();
            logger.info("CUSTOMER PRICE REFERENCE IS CREATED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING CREATE THE CUSTOMER PRICE REFERENCE", e);
            //objSession.getTransaction().rollback();
            e.printStackTrace();
        }
       /* finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }*/
        return objPriceRunCustomerVO.getPriceRunCustomerRefId(); 
    }
    
    public Collection getTaxRates()
    {
        Session objSession = null;
        Collection objCollection = null;
        try
        {
            logger.info("GETTING TAX RATES");
            objSession = HibernateUtil.getSession();
            objCollection = objSession.createCriteria(TaxRatesVO.class).list();
            logger.info("GOT TAX RATES");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE TAX RATES", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objCollection;
    }
    
    
    public static void main(String[] args) 
    {
        try
        {
            //System.out.println("starts:"+new Date());
            //new PricingDAO().getHeatRateProduct(1.5f,12,46,"");
            //System.out.println(new PricingDAO().getSensitivityGraphDetails(197,"1008901023804610040100",1));
            //System.out.println("Value:"+new PricingDAO().execute("485", "", "savant"));
            //new PricingDAO().execute("486","","savant");
            //System.out.println("ends:"+new Date());
            //System.out.println("starts:"+new Date());
            //new PricingDAO().getSensitivityGraphDetails(46,"",12);
            //System.out.println("ends:"+new Date());
            //System.out.println(new PricingDAO().getTaxRates());
            //String temp = new PricingDAO().execute("1");
            //new PricingDAO().getPriceRunCustomerReferences();
            //GregorianCalendar gc = new GregorianCalendar();
            //gc.setTime(new Date("2007/03/04"));
            //System.out.println("Day:"+gc.get(Calendar.DAY_OF_WEEK));
            
            //System.out.println(new PricingDAO().getAllTDSPs(514));
            //System.out.println(new PricingDAO().getEPP(8150, 30, 562));
            //int[] terms = new PricingDAO().getPreferenceTerms(580);
            // for(int i = 0; i<terms.length;i++)
            //{
            //    System.out.println("Terms:"+terms[i]);
            // }
            
            //List obj = new PricingDAO().getDealLevers(580,2);
            // Iterator itr = obj.iterator();
            //while(itr.hasNext())
            //{
            //    DealLevers objDealLevers = (DealLevers)itr.next();
            //    System.out.println("id:"+objDealLevers.getDealLeverIdentifier());
            //    System.out.println("value:"+objDealLevers.getValue());
            // }
            //System.out.println(new PricingDAO().teeNaturalGasPriceLastImportedOn());
            //new PricingDAO().getPriceRunCustomerReferences();
            //PricingDashBoard obj = new PricingDAO().getDashBoardDetails(336,1);
            
            //System.out.println(obj.getVecAnnualEnergyDetails().size());
            
            /* DealLevers objDeal = new DealLevers();
             objDeal.setDealLeverIdentifier(1);
             objDeal.setValue(120);
             Vector vec = new Vector();
             vec.add(objDeal);*/
            //new PricingDAO().getEPP(6.5f,12,900);
            //System.out.println(new PricingDAO().getDashBoardDetails(585,12,""));
            //System.out.println(new PricingDAO().fwdCurveLastImportedOn()+" "+new PricingDAO().teeNaturalGasPriceLastImportedOn()+" ");
            //System.out.println(new PricingDAO().fwdCurveLastImportedOn().getTime()-new PricingDAO().teeNaturalGasPriceLastImportedOn().getTime());
            /*System.out.println(new PricingDAO().getAllEsiIds(1));
             System.out.println(new PricingDAO().getAllEsiIds(2));
             System.out.println(new PricingDAO().getAllEsiIds(3));
             System.out.println(new PricingDAO().getAllEsiIds(4));
             System.out.println(new PricingDAO().getAllEsiIds(5));
             System.out.println(new PricingDAO().getAllEsiIds(6));
             System.out.println(new PricingDAO().getAllEsiIds(11));
             System.out.println(new PricingDAO().getAllEsiIds(7));
             System.out.println(new PricingDAO().getAllEsiIds(8));
             System.out.println(new PricingDAO().getAllEsiIds(9));
             System.out.println(new PricingDAO().getAllEsiIds(10));*/
            /*Filter obj = new Filter();
            obj.setFieldValue("p");
            obj.setSpecialFunction(HibernateUtil.STARTS_WITH);
            Filter o = new Filter();
            o.setFieldValue("t");
            o.setSpecialFunction(HibernateUtil.STARTS_WITH);
            
            Filter ob = new Filter();
            ob.setFieldValue("388");
            ob.setSpecialFunction(HibernateUtil.STARTS_WITH);*/
            
            //System.out.println(new PricingDAO().getAllPriceRunResults(new Date("apr-04-2007"),new Date("apr-04-2007"), null, null,null,null,ob,0,10));
            //new PricingDAO().getDashBoardDetailsOptimized(1808,12,"");
            //new PricingDAO().getDashBoardDetails(1808,12,"");
            PricingDAO objPricingDAO = new PricingDAO();
            objPricingDAO.getDashBoardDetails(841,12,"11111111111");
            
            //objPricingDAO.execute("720","M","Marty",true);
            /*objPricingDAO.getPricesOptimized(1808,"",0,0,0,0,0,0,0,0);
            objPricingDAO.getSensitivityGraphDetails(1808,"",12);*/
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

/*
*$Log: PricingDAO.java,v $
*Revision 1.15  2009/01/27 05:47:57  tannamalai
*changes - according to MX server
*
*Revision 1.14  2008/04/07 10:41:53  tannamalai
**** empty log message ***
*
*Revision 1.13  2008/03/28 10:42:02  srajan
*get dashboard details issue
*
*Revision 1.12  2008/03/28 09:32:58  srajan
*Dashboard Loadfactor Issue Fixed
*
*Revision 1.11  2008/03/13 11:02:13  tannamalai
*loss factor added
*
*Revision 1.10  2008/02/27 10:32:27  tannamalai
*enerygy diff added to mcpe
*
*Revision 1.9  2008/02/25 09:32:04  tannamalai
*extra column added in prc_cost table to calculate energy charge without loss
*
*Revision 1.8  2008/02/14 05:34:30  tannamalai
**** empty log message ***
*
*Revision 1.7  2008/02/08 06:53:47  tannamalai
*last commit before table split up
*
*Revision 1.6  2008/02/06 06:41:11  tannamalai
*cost of capital added
*
*Revision 1.5  2008/01/23 08:35:14  tannamalai
*jasper reports changes
*
*Revision 1.4  2007/12/21 07:23:15  kduraisamy
*Holiday checking error solved.
*
*Revision 1.3  2007/12/12 13:32:06  jvediyappan
*indentation.
*
*Revision 1.2  2007/12/12 10:33:22  jvediyappan
*indentation.
*
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.4  2007/11/30 10:44:51  tannamalai
*new column added in pricerunheader
*
*Revision 1.3  2007/11/28 14:05:58  jvediyappan
*unwanted print statement removed.
*
*Revision 1.2  2007/11/20 04:52:53  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/10/30 05:51:54  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:23  jnadesan
*initail MXEP commit
*
*Revision 1.157  2007/09/26 09:55:18  kduraisamy
*unwanted print removed
*
*Revision 1.156  2007/09/26 09:01:56  jnadesan
*IDR forecastUsegae computation problem solved
*
*Revision 1.155  2007/09/07 11:05:42  jnadesan
*Exp problem solved
*
*Revision 1.154  2007/09/07 09:30:19  jnadesan
*GasPrice Value Stored by run wise
*
*Revision 1.153  2007/09/04 13:09:29  jnadesan
*Expdtae applied
*
*Revision 1.152  2007/09/04 12:01:03  jnadesan
*aggloadprofile deleted
*
*Revision 1.151  2007/09/04 07:05:27  kduraisamy
*Is mm customer checked
*
*Revision 1.150  2007/09/04 05:01:09  kduraisamy
*Archive mapping added and method changed according to the changes.
*
*Revision 1.149  2007/08/31 14:49:27  sramasamy
*Log message is added for log file.
*
*Revision 1.148  2007/08/27 15:12:18  kduraisamy
*results will be deleted eventhough if its failed
*
*Revision 1.147  2007/08/27 04:38:55  jnadesan
*entry in MMPriceRun Header
*
*Revision 1.146  2007/08/23 14:33:20  jnadesan
*MMCust run option added
*
*Revision 1.145  2007/08/23 07:26:10  jnadesan
*Entry for MMPrice
*
*Revision 1.144  2007/08/17 11:17:43  kduraisamy
*Network calls avoided in getting dashboard.
*
*Revision 1.143  2007/08/10 07:39:11  kduraisamy
*Problem in unimported customers while creating CPE error solved.
*
*Revision 1.142  2007/08/06 07:48:24  kduraisamy
*delete query included for deleting PRC_AGGREGATEDLOADPROFILE before starting the run.
*
*Revision 1.141  2007/08/03 12:22:23  kduraisamy
*forward curve date is taken from memory.
*
*Revision 1.140  2007/08/03 06:37:12  kduraisamy
*If IDR is not having 576 value, We have assigned BUSMEDLF profile.
*
*Revision 1.139  2007/08/01 05:18:02  kduraisamy
*objsession properly closed or calling procedure properly done.
*
*Revision 1.138  2007/07/31 12:27:04  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.137  2007/07/31 12:02:58  kduraisamy
*unwanted imports removed.
*
*Revision 1.136  2007/07/31 11:39:32  kduraisamy
*objsession properly closed or calling procedure properly done.
*
*Revision 1.135  2007/07/27 12:02:26  kduraisamy
*price run delete procedure called instead of the method.
*
*Revision 1.134  2007/07/26 07:40:36  kduraisamy
*Data got deleted from PIC while deleting Prospective Customer.
*
*Revision 1.133  2007/07/26 04:56:15  jnadesan
*loadfactor changed
*
*Revision 1.132  2007/07/25 05:57:17  jnadesan
*Esiid selection checked against valid
*
*Revision 1.131  2007/07/24 14:03:26  kduraisamy
*Auto run filter added
*
*Revision 1.130  2007/07/18 04:54:28  jnadesan
*tax exception added
*
*Revision 1.129  2007/07/05 13:26:19  jnadesan
*startdate maintained run wise
*
*Revision 1.128  2007/07/05 07:09:25  jnadesan
*loadfactor avrage computed
*
*Revision 1.127  2007/07/05 06:12:43  srajan
*Epp fixed price computaion problem solved
*
*Revision 1.126  2007/07/04 13:18:11  jnadesan
*customer satrtdate has updated if its in past or current month
*
*Revision 1.125  2007/06/28 10:48:37  kduraisamy
*getting miv value of sensitivity chart logic
*is cahnged
*
*Revision 1.124  2007/06/26 12:01:25  jnadesan
*method added to get min value in sensitivity chart
*
*Revision 1.123  2007/06/14 10:14:19  kduraisamy
*unitary added for shaping premium.
*
*Revision 1.122  2007/06/13 11:03:35  kduraisamy
*2005 jar included.
*
*Revision 1.121  2007/06/13 04:10:21  kduraisamy
*2005 jar included.
*
*Revision 1.120  2007/06/12 12:55:25  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.119  2007/06/12 06:06:20  kduraisamy
*for sqlserver 2005 data types changed.
*
*Revision 1.118  2007/06/05 05:07:32  kduraisamy
*priceRunCustomer preferences added.
*
*Revision 1.117  2007/06/04 12:04:30  kduraisamy
*priceRunCustomer preferences added.
*
*Revision 1.116  2007/06/04 11:49:43  kduraisamy
*priceRunCustomer preferences added.
*
*Revision 1.115  2007/05/23 11:57:05  kduraisamy
*priceRunResult delete method added.
*
*Revision 1.114  2007/05/23 04:52:44  kduraisamy
*sales Rep, sale Manager first name, last name added into the filter.
*
*Revision 1.113  2007/05/22 12:25:14  kduraisamy
*esiId Count variable added.
*
*Revision 1.112  2007/05/21 04:51:20  kduraisamy
*proper variable name placed.
*
*Revision 1.110  2007/05/13 07:53:16  kduraisamy
*block usage error rectified.
*
*Revision 1.109  2007/05/12 06:19:52  kduraisamy
*contract kWh without loss taken for dashboard.
*
*Revision 1.108  2007/05/11 14:13:59  jnadesan
*divided by zero error resolved
*
*Revision 1.107  2007/05/11 13:45:36  kduraisamy
*divided by zero error rectified.
*
*Revision 1.106  2007/05/11 12:57:45  kduraisamy
*aggregated load profile error rectified.
*
*Revision 1.105  2007/05/11 11:42:17  kduraisamy
*aggregated load profile error rectified.
*
*Revision 1.104  2007/05/10 06:53:32  kduraisamy
*aggregated load profiles error rectified.
*
*Revision 1.103  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.102  2007/05/08 12:16:13  kduraisamy
*getAggregatedProfileDetails() added.
*
*Revision 1.101  2007/05/07 12:14:14  kduraisamy
*IDR Profile calculation added.
*
*Revision 1.100  2007/05/04 12:22:02  kduraisamy
*unwanted println removed.
*
*Revision 1.99  2007/05/04 12:18:20  kduraisamy
*cms Id/EsiId search added.
*
*Revision 1.98  2007/05/04 06:34:43  kduraisamy
*gas price added in runHeaderVO.
*
*Revision 1.97  2007/05/02 10:49:56  kduraisamy
*indentation.
*
*Revision 1.96  2007/04/30 09:05:05  kduraisamy
*forward Curve Date included.
*
*Revision 1.95  2007/04/28 05:31:30  kduraisamy
*run return type boolean added.
*
*Revision 1.94  2007/04/26 12:28:04  kduraisamy
*runType Added.
*
*Revision 1.93  2007/04/24 05:41:27  kduraisamy
*unwanted call removed.getAllPICUsages().
*
*Revision 1.92  2007/04/24 03:55:35  kduraisamy
*only valid esiIds taken for run.
*
*Revision 1.91  2007/04/24 03:53:32  kduraisamy
*only valid esiIds taken for run.
*
*Revision 1.87  2007/04/23 05:32:23  kduraisamy
*esiId Preference added.
*
*Revision 1.86  2007/04/22 16:34:45  kduraisamy
*unwanted println removed.
*
*Revision 1.85  2007/04/22 16:25:52  kduraisamy
*error handling added.
*
*Revision 1.84  2007/04/21 12:29:56  kduraisamy
*status and reason added.
*
*Revision 1.83  2007/04/21 05:21:11  kduraisamy
*noOf Esiid changed according to the esiId selection.
*
*Revision 1.82  2007/04/19 11:44:22  kduraisamy
*Set is changed to List.
*
*Revision 1.81  2007/04/19 10:05:17  kduraisamy
*unwanted println removed.
*
*Revision 1.80  2007/04/18 13:16:54  kduraisamy
*a.ESIID = b.ESIID condition added.
*
*Revision 1.79  2007/04/18 09:34:40  kduraisamy
*set removed.
*
*Revision 1.78  2007/04/18 06:27:12  kduraisamy
*set removed.
*
*Revision 1.77  2007/04/18 03:48:29  kduraisamy
*begin and commit transaction added.
*
*Revision 1.76  2007/04/17 15:15:04  jnadesan
*FAF Multiplier value assigned to object
*
*Revision 1.75  2007/04/17 13:48:21  kduraisamy
*price run performance took place.
*
*Revision 1.74  2007/04/17 04:49:39  kduraisamy
*rateCodes set removed from TDSP.
*
*Revision 1.73  2007/04/16 13:18:52  kduraisamy
*sensitivity graph method added.
*
*Revision 1.72  2007/04/16 08:03:05  kduraisamy
*sensitivity graph method added.
*
*Revision 1.71  2007/04/16 07:36:24  kduraisamy
*sensitivity graph method added.
*
*Revision 1.70  2007/04/16 07:34:46  kduraisamy
*sensitivity graph method added.
*
*Revision 1.69  2007/04/13 11:18:10  kduraisamy
*getEPP() and getHeatRate() esiId included.
*
*Revision 1.68  2007/04/12 13:57:33  kduraisamy
*unwanted println commented.
*
*Revision 1.67  2007/04/12 10:08:20  jnadesan
*additionalvol divide by 100 added
*
*Revision 1.66  2007/04/12 06:48:40  kduraisamy
*method for sensitivity added.
*
*Revision 1.65  2007/04/11 09:18:15  kduraisamy
*If henryHub Price is not available Gas price is taken.
*
*Revision 1.64  2007/04/06 12:31:58  kduraisamy
*TLF AND DLF COMPLETED.
*
*Revision 1.63  2007/04/04 15:57:44  kduraisamy
*getAllPriceRunResult() added.
*
*Revision 1.62  2007/04/04 12:49:20  kduraisamy
*filter by ESIID template added.
*
*Revision 1.61  2007/04/04 04:54:29  rraman
*fiter method for runresult completed
*
*Revision 1.60  2007/04/02 16:32:15  kduraisamy
*market Date condition checking added.
*
*Revision 1.59  2007/03/26 04:55:58  jnadesan
*comments changed as runstatus
*
*Revision 1.58  2007/03/23 07:14:34  kduraisamy
*deal Levers stored by customer and term wise.
*
*Revision 1.57  2007/03/22 08:15:34  jnadesan
*noofEsiid corrected
*
*Revision 1.56  2007/03/21 15:15:53  jnadesan
*buildcofig added
*
*Revision 1.55  2007/03/21 10:25:34  jnadesan
*Id hard coded problem solved.
*
*Revision 1.54  2007/03/17 05:59:31  kduraisamy
*unwanted parameter removed.
*
*Revision 1.53  2007/03/16 10:35:02  kduraisamy
*dividedByZero Error Corrected.
*
*Revision 1.52  2007/03/15 12:47:31  kduraisamy
*necessary break added inside if.
*
*Revision 1.51  2007/03/15 11:55:01  kduraisamy
*comma changed as delimiter.
*
*Revision 1.50  2007/03/15 05:43:07  kduraisamy
*DMODE added.
*
*Revision 1.49  2007/03/14 15:19:54  kduraisamy
*MonthDate set as 1 for readCycle.
*
*Revision 1.48  2007/03/14 13:29:13  kduraisamy
*comment removed.
*
*Revision 1.47  2007/03/14 09:52:02  kduraisamy
*taxRates get method completed.
*
*Revision 1.46  2007/03/14 08:25:26  srajappan
*prospectiveCustomerId changed to an prospectiveCustomer object.
*
*Revision 1.45  2007/03/13 13:21:27  kduraisamy
*loadFactor and max demand calculation added.
*
*Revision 1.44  2007/03/13 10:00:27  kduraisamy
*getAllESIIDsByTDSPCongestionZone() added.
*
*Revision 1.43  2007/03/13 08:43:09  kduraisamy
*desc in price run id.
*
*Revision 1.42  2007/03/13 08:33:09  kduraisamy
*Ratchet and demand power directly taken from PICVO.
*
*Revision 1.39  2007/03/09 08:52:14  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.38  2007/03/09 04:14:24  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.37  2007/03/08 16:30:34  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.36  2007/02/28 14:33:28  kduraisamy
*customer name taken.
*
*Revision 1.35  2007/02/28 14:20:12  jnadesan
*Dashboard chart added
*
*Revision 1.34  2007/02/28 12:16:02  kduraisamy
*saveDealLevers() finished.
*
*Revision 1.33  2007/02/28 11:46:52  kduraisamy
*deal lever values taken out.
*
*Revision 1.32  2007/02/28 10:25:05  srajappan
*start date line removed
*
*Revision 1.31  2007/02/28 10:11:48  kduraisamy
*unwanted code removed.
*
*Revision 1.30  2007/02/28 10:07:00  kduraisamy
*contract end date taken out.
*
*Revision 1.29  2007/02/28 10:03:30  kduraisamy
*preference terms taken out.
*
*Revision 1.28  2007/02/28 09:46:21  kduraisamy
*preference terms taken out.
*
*Revision 1.27  2007/02/28 08:45:19  kduraisamy
*contract end date taken out.
*
*Revision 1.26  2007/02/28 06:53:57  kduraisamy
*optimization started by sriram.
*
*Revision 1.25  2007/02/27 04:48:58  kduraisamy
*teeNatural gas price last imported method finished.
*
*Revision 1.24  2007/02/26 13:26:44  kduraisamy
*HeatRate and energy Partner Plan added.
*
*Revision 1.23  2007/02/26 05:40:05  kduraisamy
*esiId wise filter added.
*
*Revision 1.22  2007/02/20 12:37:23  kduraisamy
*optimization took place.
*
*Revision 1.21  2007/02/19 12:16:57  kduraisamy
*max(marketDate) taken as market date for a run.
*
*Revision 1.20  2007/02/19 10:14:16  kduraisamy
*customer charge, etc computation added.
*
*Revision 1.19  2007/02/19 09:55:19  kduraisamy
*annualkWh computation added.
*
*Revision 1.18  2007/02/19 06:19:55  kduraisamy
*annualEnergyDetails Added.
*
*Revision 1.17  2007/02/16 13:03:28  kduraisamy
*contract kwh computation changed.
*
*Revision 1.16  2007/02/15 13:38:06  kduraisamy
*getAllCustomerRef() method changed.
*
*Revision 1.15  2007/02/15 13:23:08  kduraisamy
*market date changed.
*
*Revision 1.14  2007/02/15 09:46:51  kduraisamy
*private changed as public.
*
*Revision 1.13  2007/02/15 07:25:03  kduraisamy
*dashBoardTemplate commited.
*
*Revision 1.12  2007/02/15 06:30:46  kduraisamy
*dashBoardTemplate commited.
*
*Revision 1.11  2007/02/15 04:29:13  kduraisamy
*unwanted code removed.
*
*Revision 1.10  2007/02/14 12:45:43  kduraisamy
*excecute() return type changed.
*
*Revision 1.9  2007/02/14 08:57:03  kduraisamy
*excecute() return type changed.
*
*Revision 1.8  2007/02/14 08:49:46  kduraisamy
*excecute() return type changed.
*
*Revision 1.7  2007/02/13 14:04:57  kduraisamy
*BillingDemand related mapping completed.
*
*Revision 1.6  2007/02/12 07:19:07  kduraisamy
*loadPrerequisites() added.
*
*Revision 1.5  2007/02/12 05:37:57  kduraisamy
*unwanted print removed.
*
*Revision 1.4  2007/02/10 12:22:49  kduraisamy
*delete query included.
*
*Revision 1.3  2007/02/10 09:30:18  kduraisamy
*split method optimized.
*
*Revision 1.2  2007/02/10 07:41:20  kduraisamy
*method abstraction done.
*
*Revision 1.1  2007/02/09 11:56:42  kduraisamy
*pricing core algorithm almost finished.
*
*
*/