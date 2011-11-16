/*
 * Created on Feb 7, 2007
 *
 * ClassName	:  	CostComputationDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.calculation.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.savant.pricing.calculation.valueobjects.ChargeTypesVO;
import com.savant.pricing.calculation.valueobjects.EnergyChargeNamesVO;
import com.savant.pricing.calculation.valueobjects.PICVO;
import com.savant.pricing.calculation.valueobjects.PriceBlockHeaderVO;
import com.savant.pricing.calculation.valueobjects.PricingCostVO;
import com.savant.pricing.calculation.valueobjects.PricingUsageHeaderVO;
import com.savant.pricing.calculation.valueobjects.TDSPChargeNamesVO;
import com.savant.pricing.calculation.valueobjects.UsageVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.dao.EnergyChargeRatesDAO;
import com.savant.pricing.dao.ForwardCurveBlockDAO;
import com.savant.pricing.dao.ForwardCurveProfileDAO;
import com.savant.pricing.dao.PriceBlockDAO;
import com.savant.pricing.dao.ShapingPremiumRatioDAO;
import com.savant.pricing.transferobjects.EnergyOtherCharges;
import com.savant.pricing.transferobjects.IndividualTermDetails;
import com.savant.pricing.transferobjects.ProfileDetails;
import com.savant.pricing.transferobjects.TermDetails;
import com.savant.pricing.transferobjects.TermMonthBlockCost;
import com.savant.pricing.valueobjects.LoadProfileTypesVO;
import com.savant.pricing.valueobjects.TDSPRateCodesVO;
import com.savant.pricing.valueobjects.TDSPVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CostComputationDAO
{
    LoadExtrapolationDAO objLoadExtrapolationDAO = new LoadExtrapolationDAO();
    PriceBlockDAO objPriceBlockDAO = new PriceBlockDAO();
    final String delimiter=",";
    final String lineSeparator=System.getProperty("line.separator");

    private static Logger logger = Logger.getLogger(CostComputationDAO.class);
    
    public CostComputationDAO()
    {
    }
    public void computeEnergyCost(TDSPVO objTDSPVO, PICVO objPICVO, Date marketDate, PricingUsageHeaderVO objPricingUsageHeaderVO,TermDetails objTermDetails, boolean shapingPremiumUnitary)
    {
        Session objSession = null;
        try
        {
            logger.info("COMPUTING ENERGY COST");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            Vector vecIndividualTermDetails = objTermDetails.getVecIndividualTermDetails();
            double totUsage = 0;
            double totUsageWOL = 0;
            ProfileDetails objProfileDetails = null;
            for(int i=0;i<vecIndividualTermDetails.size();i++)
            {
                IndividualTermDetails objIndividualTermDetails = (IndividualTermDetails)vecIndividualTermDetails.get(i);
                double usage[] = this.getBlockUsage(objIndividualTermDetails.getTermMonth(),objPricingUsageHeaderVO.getPricingUsageRefId());
                double usage7x8 = usage[1]+usage[3];
                double usage7x8WOL = usage[5]+usage[7];
                
                totUsage += usage[0]+usage[2]+usage7x8;
                totUsageWOL += usage[4]+usage[6]+usage7x8WOL;
                
                objProfileDetails = objPICVO.getProfileDetails();
                int congestionZoneId = objProfileDetails.getCongestionZoneId();
                float price5x16 = this.getPrice(marketDate,objIndividualTermDetails.getTermMonth(),HibernateUtil.priceBlock5x16Id,congestionZoneId,1);
                float price2x16 = this.getPrice(marketDate,objIndividualTermDetails.getTermMonth(),HibernateUtil.priceBlock2x16Id,congestionZoneId,1);
                float price7x8 = this.getPrice(marketDate,objIndividualTermDetails.getTermMonth(),HibernateUtil.priceBlock7x8Id,congestionZoneId,1);
                float cost5x16 = (new Double(usage[0])).floatValue() * price5x16/1000;
                float cost2x16 = (new Double(usage[2])).floatValue() * price2x16/1000;
                float cost7x8 = (new Double(usage7x8)).floatValue() * price7x8/1000;
                
                float cost5x16WOL = (new Double(usage[4])).floatValue() * price5x16/1000;
                float cost2x16WOL = (new Double(usage[6])).floatValue() * price2x16/1000;
                float cost7x8WOL = (new Double(usage7x8WOL)).floatValue() * price7x8/1000;
                
                
                if(BuildConfig.DMODE)
                {
                    System.out.println("Term:"+objTermDetails.getTerm());
                    System.out.println("usage 5 X 16:"+usage[0]);
                    System.out.println("usage 2 X 16:"+usage[2]);
                    System.out.println("usage 7 X 8:"+usage7x8);
                    System.out.println("cost 5 X 16:"+cost5x16);
                    System.out.println("cost 2 X 16:"+cost2x16);
                    System.out.println("cost 7 X 8:"+cost7x8);
                }
                
                // Block 5 x 16
                PricingCostVO  objPricingCostVO = new PricingCostVO();
                PriceBlockHeaderVO objPriceBlockHeaderVO = new PriceBlockHeaderVO();
                objPriceBlockHeaderVO.setPriceBlockIdentifier(HibernateUtil.priceBlock5x16Id);
                objPricingCostVO.setPriceBlock(objPriceBlockHeaderVO);
                this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,objIndividualTermDetails.getTermMonth(),objTermDetails.getTerm(),cost5x16,cost5x16WOL);

                // Block 2 x 16
                objPricingCostVO = new PricingCostVO();
                objPriceBlockHeaderVO = new PriceBlockHeaderVO();
                objPriceBlockHeaderVO.setPriceBlockIdentifier(HibernateUtil.priceBlock2x16Id);
                objPricingCostVO.setPriceBlock(objPriceBlockHeaderVO);
                this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,objIndividualTermDetails.getTermMonth(),objTermDetails.getTerm(),cost2x16,cost2x16WOL);
                
                // Block 7 x 8
                objPricingCostVO = new PricingCostVO();
                objPriceBlockHeaderVO = new PriceBlockHeaderVO();
                objPriceBlockHeaderVO.setPriceBlockIdentifier(HibernateUtil.priceBlock7x8Id);
                objPricingCostVO.setPriceBlock(objPriceBlockHeaderVO);
                this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,objIndividualTermDetails.getTermMonth(),objTermDetails.getTerm(),cost7x8,cost7x8WOL);
                
                this.computeOtherCharges(objPricingUsageHeaderVO,objIndividualTermDetails.getTermMonth(),objTermDetails.getTerm(),(new Double(usage[0]+usage[2]+usage7x8)).floatValue(), congestionZoneId);
                this.computeProfileEnergyCost(marketDate,objProfileDetails,objPricingUsageHeaderVO,objIndividualTermDetails.getTermMonth(),objTermDetails.getTerm(),(new Double(usage[0]+usage[2]+usage7x8)).floatValue());
                
                TermMonthBlockCost objTermMonthBlockCost = new TermMonthBlockCost();
                objTermMonthBlockCost.setTermMonth(objIndividualTermDetails.getTermMonth());
                objTermMonthBlockCost.setCost5x16(cost5x16);
                objTermMonthBlockCost.setCost2x16(cost2x16);
                objTermMonthBlockCost.setCost7x8(cost7x8);
                this.computeShapingPremium(objProfileDetails,objTermMonthBlockCost,objPricingUsageHeaderVO,objIndividualTermDetails.getTermMonth(),objTermDetails.getTerm(), shapingPremiumUnitary);
                //for TDSP Charges termMonth wise
                objTermDetails = this.computeTDSPSystemBenefitCharges(objPICVO,objPricingUsageHeaderVO,objIndividualTermDetails.getTermMonth(),objTermDetails.getTerm(), (new Double(usage[0]+usage[2]+usage7x8)).floatValue(), objTermDetails);
            }
            //These charges are computed termwise without using usage.
            objTermDetails = this.computeTDSPCharges(3,objPICVO,objPricingUsageHeaderVO,objTermDetails.getTerm(), objTermDetails);
            objTermDetails = this.computeTDSPCharges(4,objPICVO,objPricingUsageHeaderVO,objTermDetails.getTerm(), objTermDetails);
            this.computeTDSPCharges(5,objPICVO,objPricingUsageHeaderVO,objTermDetails.getTerm(), objTermDetails);
            this.computeTDSPCharges(1,objPICVO,objPricingUsageHeaderVO,objTermDetails.getTerm(), objTermDetails);
            this.computeTransitionCharges(objPICVO,objPricingUsageHeaderVO, objTermDetails.getTerm(), totUsage, objTermDetails);
            objTermDetails = this.computeTDSPTransmissionCharge(objTDSPVO, objProfileDetails, objPICVO, objPricingUsageHeaderVO,objTermDetails.getTermEndDate(), objTermDetails.getTerm(),totUsage, objTermDetails);
            objTermDetails = this.computeTDSPDistributionCharge(objPICVO, objPricingUsageHeaderVO,objTermDetails.getTermEndDate(), objTermDetails.getTerm(), totUsage, objTermDetails);
            objTermDetails = this.computeNDCharges(objPICVO,objPricingUsageHeaderVO, objTermDetails.getTerm(), totUsage, objTermDetails);
            this.computeExcessMitigationCharges(objPICVO,objPricingUsageHeaderVO, objTermDetails.getTerm(), totUsage, objTermDetails);
            objTermDetails = this.computeTDSPSCUDCharge(objPICVO,objTermDetails,objPricingUsageHeaderVO);
            this.computeNMSandRRCredit(objPICVO,objTermDetails,objPricingUsageHeaderVO);
            this.computeTCRF(objTDSPVO, objPICVO,objPricingUsageHeaderVO,objProfileDetails,objTermDetails,totUsage);
            objSession.getTransaction().commit();
            logger.info("ENERGY COST IS COMPUTED");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING COMPUTE THE ENERGY COST", e);
            e.printStackTrace();
            objSession.getTransaction().rollback();
            throw new HibernateException(e.toString());
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
    }
    private void computeTCRF(TDSPVO objTDSPVO, PICVO objPICVO, PricingUsageHeaderVO objPricingUsageHeaderVO, ProfileDetails objProfileDetails, TermDetails objTermDetails, double usage)
    {
        float demandCharge = 0;
        float energyCharge = 0;
        float monthlyCharge = 0;
        double totCharge = 0;
        
        demandCharge = objPICVO.getCharge(25);
        energyCharge = objPICVO.getCharge(26);
        monthlyCharge = objPICVO.getCharge(24);

        logger.info("COMPUTING TCRF FOR METER TYPE " + objProfileDetails.getMeterType());
        if(objProfileDetails.getMeterType().equals("IDR"))
        {
            float avg4CPkVA = 0;
            float avg4CPkW = 0;
            
            if(objTDSPVO.getTdspIdentifier()==1)
            {
                avg4CPkVA = objPICVO.getAvg4CPkVA();
            }
            else
            {
                avg4CPkW = objPICVO.getAvg4CPkW();
                
            }
            totCharge = (avg4CPkVA+avg4CPkW)*demandCharge + usage * energyCharge+monthlyCharge;
        }
        if(objProfileDetails.getMeterType().equals("NIDR"))
        {
            float ncpDmnd = 0;
            if(objTDSPVO.getTdspIdentifier() == 1)
                ncpDmnd = objTermDetails.getApparentPower();
            else
                ncpDmnd = objTermDetails.getEnergyDemand();
            totCharge = ncpDmnd*demandCharge + usage * energyCharge+monthlyCharge;
        }
        PricingCostVO objPricingCostVO = new PricingCostVO();
        ChargeTypesVO objChargeTypesVO = new ChargeTypesVO();
        objChargeTypesVO.setChargeTypeIdentifier(11);
        objPricingCostVO.setChargeType(objChargeTypesVO);
        this.saveCost(objPricingCostVO, objPricingUsageHeaderVO, null, objTermDetails.getTerm(), (new Double(totCharge)).floatValue(),0);
        logger.info("COMPUTED TCRF FOR METER TYPE " + objProfileDetails.getMeterType());
    }
    
    private void computeNMSandRRCredit(PICVO objPICVO , TermDetails objTermDetails, PricingUsageHeaderVO objPricingUsageHeaderVO)
    {
        logger.info("COMPUTING NMS AND RR CREDIT");
        float nmsCharge = objPICVO.getCharge(22);
        float rrCharge = objPICVO.getCharge(23);
        
        float totCharge = objTermDetails.getTotCharge() * (rrCharge+nmsCharge);
        PricingCostVO objPricingCostVO = new PricingCostVO();
        ChargeTypesVO objChargeTypesVO = new ChargeTypesVO();
        objChargeTypesVO.setChargeTypeIdentifier(10);
        objPricingCostVO.setChargeType(objChargeTypesVO);
        this.saveCost(objPricingCostVO, objPricingUsageHeaderVO, null, objTermDetails.getTerm(), totCharge,0);
        logger.info("COMPUTED NMS AND RR CREDIT");
    }
    
    private TermDetails computeTDSPSCUDCharge(PICVO objPICVO, TermDetails objTermDetails, PricingUsageHeaderVO objPricingUsageHeaderVO)
    {
        try
        {
            logger.info("COMPUTING TDSPSCUD CHARGE");
            float charge = 0;
            TDSPRateCodesVO objTDSPRateCodesVO = objPICVO.getScud();
            boolean scud = objTDSPRateCodesVO.isScud();
            float scudPercentage = objTDSPRateCodesVO.getScudPercentage();
            charge = objTermDetails.getCustomerCharge()+objTermDetails.getMeteringCharge()+objTermDetails.getTransmissionCharge()+objTermDetails.getDistributionCharge()+objTermDetails.getSystemBenefitFund()+objTermDetails.getNuclearDecommissionCharge();
            objTermDetails.setTotCharge(charge);
            if(scud)
            {
                charge = charge * scudPercentage;
                PricingCostVO objPricingCostVO = new PricingCostVO();
                this.saveCost(objPricingCostVO, objPricingUsageHeaderVO,null,objTermDetails.getTerm(),(new Double(charge)).floatValue(),0);
                logger.info("TDSPSCUP CHARGE IS COMPUTED");
            }
            else
            {
                logger.info("TDSPSCUP CHARGE IS NOT COMPUTED");
            }
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING COMPUTE TDSPSCUD CHARGE", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        return objTermDetails;
    }
    
    private void computeProfileEnergyCost(Date marketDate, ProfileDetails objProfileDetails,PricingUsageHeaderVO objPricingUsageHeaderVO, Date termMonth, int term, float usage)
    {
        logger.info("COMPUTING PROFILE ENERGY COST");
        PricingCostVO  objPricingCostVO = new PricingCostVO();
        LoadProfileTypesVO objLoadProfileTypesVO = new LoadProfileTypesVO();
        objLoadProfileTypesVO.setProfileIdentifier(objProfileDetails.getProfileTypeId());
        objPricingCostVO.setProfileType(objLoadProfileTypesVO);
        float price = this.getProfilePrice(marketDate,termMonth,objProfileDetails,1);
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,termMonth,term,usage*price/1000,0);
        logger.info("PROFILE ENERGY COST IS COMPUTED");
    }
    
    //  for TDSP Charges termMonth wise
    private TermDetails computeTDSPSystemBenefitCharges(PICVO objPICVO, PricingUsageHeaderVO objPricingUsageHeaderVO, Date termMonth, int term, float usage, TermDetails objTermDetails)
    {
        logger.info("COMPUTING TDSP SYSTEM BENEFIT CHARGES");
        PricingCostVO objPricingCostVO = new PricingCostVO();
        //TDSPChargeNamesVO objTDSPChargeNamesVO = new TDSPChargeNamesVO();
        //objTDSPChargeNamesVO.setTdspChargeIdentifier(16);
        //objPricingCostVO.setTdspChargeName(objTDSPChargeNamesVO);
        ChargeTypesVO objChargeTypesVO = new ChargeTypesVO();
        objChargeTypesVO.setChargeTypeIdentifier(16);
        objPricingCostVO.setChargeType(objChargeTypesVO);
        float charge = 0;
        charge = objPICVO.getCharge(16);
        
        charge = charge * usage;
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,termMonth,term,charge,0);
        objTermDetails.setSystemBenefitFund(objTermDetails.getSystemBenefitFund()+charge);
        logger.info("TDSP SYSTEM BENEFIT CHARGES IS COMPUTED");
        return objTermDetails;
    }
    private TermDetails computeTDSPDistributionCharge(PICVO objPICVO, PricingUsageHeaderVO objPricingUsageHeaderVO,Date readDate, int term,double usage, TermDetails objTermDetails)
    {
        logger.info("COMPUTING TDSP DISTRIBUTION CHARGE");
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(readDate);
        int readMonth = 0;
        readMonth = gc.get(Calendar.MONTH)+1;
        float summerDmndCharge = 0;
        float summerEnergyCharge = 0;
        float winterDmndCharge = 0;
        float winterEnergyCharge = 0;
        double charge = 0;
        if(readMonth<=9 && readMonth >=6)
        {
            summerDmndCharge = objPICVO.getCharge(7);
            summerEnergyCharge = objPICVO.getCharge(8);
        }
        else
        {
            winterDmndCharge = objPICVO.getCharge(9);
            winterEnergyCharge = objPICVO.getCharge(6);
        }
        charge = objTermDetails.getBillingDemand()*(summerDmndCharge+winterDmndCharge)+usage*(summerEnergyCharge+winterEnergyCharge);
        PricingCostVO objPricingCostVO = new PricingCostVO();
        ChargeTypesVO objChargeTypesVO = new ChargeTypesVO();
        objChargeTypesVO.setChargeTypeIdentifier(4);
        objPricingCostVO.setChargeType(objChargeTypesVO);
        this.saveCost(objPricingCostVO, objPricingUsageHeaderVO,null,term,(new Double(charge)).floatValue(),0);
        objTermDetails.setDistributionCharge((new Double(charge)).floatValue());
        logger.info("TDSP DISTRIBUTION CHARGE IS COMPUTED");
        return objTermDetails;
    }
    
    private TermDetails computeTDSPTransmissionCharge(TDSPVO objTDSPVO, ProfileDetails objProfileDetails, PICVO objPICVO, PricingUsageHeaderVO objPricingUsageHeaderVO,Date readDate, int term,double usage, TermDetails objTermDetails)
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(readDate);
        int readMonth = 0;
        readMonth = gc.get(Calendar.MONTH)+1;
        float ncpDmnd = 0;
        float summerDmndCharge = 0;
        float summerEnergyCharge = 0;
        
        float winterDmndCharge = 0;
        float winterEnergyCharge = 0;
        double charge = 0;
        if(readMonth<=9 && readMonth >=6)
        {
            summerDmndCharge = objPICVO.getCharge(11);
            summerEnergyCharge = objPICVO.getCharge(12);
        }
        else
        {
            winterDmndCharge = objPICVO.getCharge(13);
            winterEnergyCharge = objPICVO.getCharge(10);
        }
        logger.info("COMPUTING TDSP TRANSMISSION CHARGE FOR METER TYPE " + objProfileDetails.getMeterType());
        if(objProfileDetails.getMeterType().equals("IDR"))
        {
            //take average 4 cp demand
            float avg4CPkVA = 0;
            float avg4CPkW = 0;
            if(objTDSPVO.getTdspIdentifier()==1)
            {
                avg4CPkVA = objPICVO.getAvg4CPkVA();
            }
            else
            {
                avg4CPkW = objPICVO.getAvg4CPkW();
            }
            
            charge = (avg4CPkVA+avg4CPkW)*(summerDmndCharge+winterDmndCharge)+usage*(summerEnergyCharge+winterEnergyCharge);
            PricingCostVO objPricingCostVO = new PricingCostVO();
            ChargeTypesVO objChargeTypesVO = new ChargeTypesVO();
            objChargeTypesVO.setChargeTypeIdentifier(5);
            objPricingCostVO.setChargeType(objChargeTypesVO);
            this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,null,term,(new Double(charge)).floatValue(),0);
        }
        if(objProfileDetails.getMeterType().equals("NIDR"))
        {
            if(objTDSPVO.getTdspIdentifier() == 1)
                ncpDmnd = objTermDetails.getApparentPower();
            else
                ncpDmnd = objTermDetails.getEnergyDemand();
            
            charge = ncpDmnd*(summerDmndCharge+winterDmndCharge)+usage*(summerEnergyCharge+winterEnergyCharge);
            PricingCostVO objPricingCostVO = new PricingCostVO();
            ChargeTypesVO objChargeTypesVO = new ChargeTypesVO();
            objChargeTypesVO.setChargeTypeIdentifier(5);
            objPricingCostVO.setChargeType(objChargeTypesVO);
            this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,null,term,(new Double(charge)).floatValue(),0);
        }
        objTermDetails.setTransmissionCharge((new Double(charge)).floatValue());
        logger.info("TDSP TRANSMISSION CHARGE IS COMPUTED FOR METER TYPE " + objProfileDetails.getMeterType());
        return objTermDetails;
    }
    private void computeTransitionCharges(PICVO objPICVO, PricingUsageHeaderVO objPricingUsageHeaderVO, int term, double usage, TermDetails objTermDetails)
    {
        logger.info("COMPUTING TRANSITION CHARGES");
        PricingCostVO objPricingCostVO = new PricingCostVO();
        ChargeTypesVO objChargeTypesVO = new ChargeTypesVO();
        objChargeTypesVO.setChargeTypeIdentifier(7);
        objPricingCostVO.setChargeType(objChargeTypesVO);
        double energyCharge = 0;
        float demandCharge = 0;
        double totCharge = 0;
        
        energyCharge = objPICVO.getCharge(18);
        energyCharge = energyCharge * usage;
        demandCharge = objPICVO.getCharge(17);
        demandCharge = demandCharge * objTermDetails.getBillingDemand();
        totCharge = energyCharge + demandCharge;
        logger.info("TRANSITION CHARGES IS COMPUTED");
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,null,term,(new Double(totCharge)).floatValue(),0);
    }
    
    private TermDetails computeNDCharges(PICVO objPICV, PricingUsageHeaderVO objPricingUsageHeaderVO, int term, double usage, TermDetails objTermDetails)
    {
        logger.info("COMPUTING ND CHARGES");
        PricingCostVO objPricingCostVO = new PricingCostVO();
        ChargeTypesVO objChargeTypesVO = new ChargeTypesVO();
        objChargeTypesVO.setChargeTypeIdentifier(6);
        objPricingCostVO.setChargeType(objChargeTypesVO);
        double energyCharge = 0;
        float demandCharge = 0;
        double totCharge = 0;
        
        energyCharge = objPICV.getCharge(15);
        energyCharge = energyCharge * usage;
        demandCharge = objPICV.getCharge(14);
        demandCharge = demandCharge * objTermDetails.getBillingDemand();
        totCharge = energyCharge + demandCharge;
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,null,term,(new Double(totCharge)).floatValue(),0);
        objTermDetails.setNuclearDecommissionCharge((new Double(totCharge)).floatValue());
        logger.info("ND CHARGES IS COMPUTED");
        return objTermDetails;
    }
    
    private void computeExcessMitigationCharges(PICVO objPICVO, PricingUsageHeaderVO objPricingUsageHeaderVO, int term, double usage, TermDetails objTermDetails)
    {
        logger.info("COMPUTING EXCESS MITIGATION CHARGES");
        PricingCostVO objPricingCostVO = new PricingCostVO();
        ChargeTypesVO objChargeTypesVO = new ChargeTypesVO();
        objChargeTypesVO.setChargeTypeIdentifier(8);
        objPricingCostVO.setChargeType(objChargeTypesVO);
        double energyCharge = 0;
        float demandCharge = 0;
        double totCharge = 0;
        
        energyCharge = objPICVO.getCharge(20);
        energyCharge = energyCharge * usage;
        demandCharge = objPICVO.getCharge(19);
        demandCharge = demandCharge * objTermDetails.getBillingDemand();
        totCharge = energyCharge + demandCharge;
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,null,term,(new Double(totCharge)).floatValue(),0);
        logger.info("EXCESS MITIGATION CHARGES ARE COMPUTED");
    }
    
    // to compute term wise tdsp charges..without using usage..
    private TermDetails computeTDSPCharges(int tdspChargeId, PICVO objPICVO, PricingUsageHeaderVO objPricingUsageHeaderVO, int term, TermDetails objTermDetails)
    {
        logger.info("COMPUTING TDSP CHARGES");
        PricingCostVO objPricingCostVO = new PricingCostVO();
        TDSPChargeNamesVO objTDSPChargeNamesVO = new TDSPChargeNamesVO();
        objTDSPChargeNamesVO.setTdspChargeIdentifier(tdspChargeId);
        objPricingCostVO.setTdspChargeName(objTDSPChargeNamesVO);
        
        float charge = 0;
        charge = objPICVO.getCharge(tdspChargeId);
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,null,term,charge,0);
        switch(tdspChargeId)
        {
        	case 3:
        	    objTermDetails.setCustomerCharge(charge);
        	    break;
        	    
        	case 4:
        	    objTermDetails.setMeteringCharge(charge);
        	    break;
        }
        logger.info("TDSP CHARGES ARE COMPUTED");
        return objTermDetails;
    }
    
    private void computeShapingPremium(ProfileDetails objProfileDetails, TermMonthBlockCost objTermMonthBlockCost, PricingUsageHeaderVO objPricingUsageHeaderVO, Date termMonth, int term, boolean shapingPremiumUnitary)
    {
        logger.info("COMPUTING SHAPING PREMIUM");
        //5x16
        PricingCostVO  objPricingCostVO = new PricingCostVO();
        EnergyChargeNamesVO objEnergyChargeNamesVO = new EnergyChargeNamesVO();
        objEnergyChargeNamesVO.setEnergyChargeIdentifier(2);
        objPricingCostVO.setEnergyChargeName(objEnergyChargeNamesVO);
        LoadProfileTypesVO objLoadProfileTypesVO = new LoadProfileTypesVO();
        objLoadProfileTypesVO.setProfileIdentifier(objProfileDetails.getProfileTypeId());
        objPricingCostVO.setProfileType(objLoadProfileTypesVO);
        PriceBlockHeaderVO objPriceBlockHeaderVO = new PriceBlockHeaderVO();
        objPriceBlockHeaderVO.setPriceBlockIdentifier(HibernateUtil.priceBlock5x16Id);
        if(BuildConfig.DMODE)
        {
            System.out.println("5x16 Id:"+objPriceBlockHeaderVO.getPriceBlockIdentifier());
        }
        objPricingCostVO.setPriceBlock(objPriceBlockHeaderVO);
        float shapingPremiumRatio5x16 = 0;
        float shapingPremiumRatio2x16 = 0;
        float shapingPremiumRatio7x8 = 0;
        
        if(shapingPremiumUnitary)
        {
            shapingPremiumRatio5x16 = 1;
        }
        else
        {
            shapingPremiumRatio5x16 = this.computeRatio5x16(objProfileDetails,objTermMonthBlockCost);
        }
        
        
        float charge = (shapingPremiumRatio5x16-1) * objTermMonthBlockCost.getCost5x16();
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,termMonth,term,charge,0);
        
        //2x16
        objPricingCostVO = new PricingCostVO();
        objEnergyChargeNamesVO = new EnergyChargeNamesVO();
        objEnergyChargeNamesVO.setEnergyChargeIdentifier(2);
        objPricingCostVO.setEnergyChargeName(objEnergyChargeNamesVO);
        objLoadProfileTypesVO = new LoadProfileTypesVO();
        objLoadProfileTypesVO.setProfileIdentifier(objProfileDetails.getProfileTypeId());
        objPricingCostVO.setProfileType(objLoadProfileTypesVO);
        objPriceBlockHeaderVO = new PriceBlockHeaderVO();
        objPriceBlockHeaderVO.setPriceBlockIdentifier(HibernateUtil.priceBlock2x16Id);
        if(BuildConfig.DMODE)
        {
            System.out.println("2x16 Id:"+objPriceBlockHeaderVO.getPriceBlockIdentifier());
        }
        
        objPricingCostVO.setPriceBlock(objPriceBlockHeaderVO);
        
        if(shapingPremiumUnitary)
        {
            shapingPremiumRatio2x16 = 1;
        }
        else
        {
            shapingPremiumRatio2x16 = this.computeRatio2x16(objProfileDetails,objTermMonthBlockCost);
        }
        
        charge = (shapingPremiumRatio2x16-1) * objTermMonthBlockCost.getCost2x16();
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,termMonth,term,charge,0);
        
        //7x8
        objPricingCostVO = new PricingCostVO();
        objEnergyChargeNamesVO = new EnergyChargeNamesVO();
        objEnergyChargeNamesVO.setEnergyChargeIdentifier(2);
        objPricingCostVO.setEnergyChargeName(objEnergyChargeNamesVO);
        objLoadProfileTypesVO = new LoadProfileTypesVO();
        objLoadProfileTypesVO.setProfileIdentifier(objProfileDetails.getProfileTypeId());
        objPricingCostVO.setProfileType(objLoadProfileTypesVO);
        objPriceBlockHeaderVO = new PriceBlockHeaderVO();
        objPriceBlockHeaderVO.setPriceBlockIdentifier(HibernateUtil.priceBlock7x8Id);
        if(BuildConfig.DMODE)
        {
            System.out.println("7x8 Id:"+objPriceBlockHeaderVO.getPriceBlockIdentifier());
        }
        
        objPricingCostVO.setPriceBlock(objPriceBlockHeaderVO);
        
        if(shapingPremiumUnitary)
        {
            shapingPremiumRatio7x8 = 1;
        }
        else
        {
            shapingPremiumRatio7x8 = this.computeRatio7x8(objProfileDetails,objTermMonthBlockCost);
        }
        
        charge = (shapingPremiumRatio7x8-1) * objTermMonthBlockCost.getCost7x8();
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,termMonth,term,charge,0);
        logger.info("SHAPING PREMIUM IS COMPUTED");
    }
    
    private float computeRatio5x16(ProfileDetails objProfileDetails, TermMonthBlockCost objTermMonthBlockCost)
    {
        logger.info("COMPUTING RATIO5x16");
        float ratio = 0;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(objTermMonthBlockCost.getTermMonth());
        ratio = ShapingPremiumRatioDAO.getRatio(objProfileDetails.getLoadProfile(), objProfileDetails.getCongestionZoneId(), HibernateUtil.priceBlock5x16Id, gc.get(Calendar.MONTH)+1);
        if(BuildConfig.DMODE)
        {
            System.out.println("5x16 Ratio:"+ratio);
        }
        logger.info("RATIO5x16 IS COMPUTED");
        return ratio;
    }
    
    private float computeRatio2x16(ProfileDetails objProfileDetails, TermMonthBlockCost objTermMonthBlockCost)
    {
        logger.info("COMPUTING RATIO2x16");
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(objTermMonthBlockCost.getTermMonth());
        float ratio = 0;
        ratio = ShapingPremiumRatioDAO.getRatio(objProfileDetails.getLoadProfile(), objProfileDetails.getCongestionZoneId(), HibernateUtil.priceBlock2x16Id, gc.get(Calendar.MONTH)+1);
        if(BuildConfig.DMODE)
        {
            System.out.println("2x16 Ratio:"+ratio);
        }
        logger.info("RATIO2x16 IS COMPUTED");
        return ratio;
    }
    
    private float computeRatio7x8(ProfileDetails objProfileDetails, TermMonthBlockCost objTermMonthBlockCost)
    {
        logger.info("COMPUTING RATIO7x8");
        float ratio = 0;
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(objTermMonthBlockCost.getTermMonth());
        ratio = ShapingPremiumRatioDAO.getRatio(objProfileDetails.getLoadProfile(), objProfileDetails.getCongestionZoneId(), HibernateUtil.priceBlock7x8Id, gc.get(Calendar.MONTH)+1);
        if(BuildConfig.DMODE)
        {
            System.out.println("7x8 Ratio:"+ratio);
        }
        logger.info("RATIO7x8 IS COMPUTED");
        return ratio;
    }
    
    private void computeOtherCharges(PricingUsageHeaderVO objPricingUsageHeaderVO, Date termMonth, int term, float usage, int congestionZoneId)
    {
        logger.info("COMPUTING OTHER CAHRGES");
        EnergyOtherCharges objEnergyOtherCharges = this.getOtherCharges(termMonth, congestionZoneId);
        
        //AS
        PricingCostVO  objPricingCostVO = new PricingCostVO();
        EnergyChargeNamesVO objEnergyChargeNamesVO = new EnergyChargeNamesVO();
        objEnergyChargeNamesVO.setEnergyChargeIdentifier(6);
        objPricingCostVO.setEnergyChargeName(objEnergyChargeNamesVO);
        float price = objEnergyOtherCharges.getAsCharges();
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,termMonth,term,usage*price/1000,0);
        
        //      Congestion zone charges
        objPricingCostVO = new PricingCostVO();
        objEnergyChargeNamesVO = new EnergyChargeNamesVO();
        objEnergyChargeNamesVO.setEnergyChargeIdentifier(7);
        objPricingCostVO.setEnergyChargeName(objEnergyChargeNamesVO);
        price = objEnergyOtherCharges.getCongestionCharges();
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,termMonth,term,usage*price/1000,0);
        
        //      Gross Receipts assessment fee
        objPricingCostVO = new PricingCostVO();
        objEnergyChargeNamesVO = new EnergyChargeNamesVO();
        objEnergyChargeNamesVO.setEnergyChargeIdentifier(8);
        objPricingCostVO.setEnergyChargeName(objEnergyChargeNamesVO);
        price = objEnergyOtherCharges.getGrossReceiptsAssesmentFee();
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,termMonth,term,usage*price/1000,0);
        
        //      UFE
        objPricingCostVO = new PricingCostVO();
        objEnergyChargeNamesVO = new EnergyChargeNamesVO();
        objEnergyChargeNamesVO.setEnergyChargeIdentifier(3);
        objPricingCostVO.setEnergyChargeName(objEnergyChargeNamesVO);
        price = objEnergyOtherCharges.getUfe();
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,termMonth,term,usage*price/1000,0);
        
        //      Ercot Admin fee
        objPricingCostVO = new PricingCostVO();
        objEnergyChargeNamesVO = new EnergyChargeNamesVO();
        objEnergyChargeNamesVO.setEnergyChargeIdentifier(4);
        objPricingCostVO.setEnergyChargeName(objEnergyChargeNamesVO);
        price = objEnergyOtherCharges.getErcotAdminFee();
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,termMonth,term,usage*price/1000,0);
        
        //      REC Charge
        objPricingCostVO = new PricingCostVO();
        objEnergyChargeNamesVO = new EnergyChargeNamesVO();
        objEnergyChargeNamesVO.setEnergyChargeIdentifier(1);
        objPricingCostVO.setEnergyChargeName(objEnergyChargeNamesVO);
        price = objEnergyOtherCharges.getRecCharge();
        this.saveCost(objPricingCostVO,objPricingUsageHeaderVO,termMonth,term,(new Double(usage*price*0.01*0.75/1000)).floatValue(),0);
        logger.info("OTHER CHARGE IS COMPUTED");
    }
    
    private void saveCost(PricingCostVO objPricingCostVO, PricingUsageHeaderVO objPricingUsageHeaderVO, Date termMonth, int term, float charge,float chargeWOL)
    {
        logger.info("SAVING COST");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HibernateUtil.sbCost.append(objPricingUsageHeaderVO.getPricingUsageRefId()).append(delimiter)
        .append(objPricingCostVO.getEnergyChargeName()!= null?objPricingCostVO.getEnergyChargeName().getEnergyChargeIdentifier()+"":"").append(delimiter)
        .append(objPricingCostVO.getPriceBlock() != null ? objPricingCostVO.getPriceBlock().getPriceBlockIdentifier()+"":"").append(delimiter)
        .append(objPricingCostVO.getTdspChargeName() != null ? objPricingCostVO.getTdspChargeName().getTdspChargeIdentifier()+"":"").append(delimiter)
        .append(objPricingCostVO.getProfileType() != null ? objPricingCostVO.getProfileType().getProfileIdentifier()+"":"").append(delimiter)
        .append(termMonth != null ?sdf.format(termMonth):"").append(delimiter)
        .append(term).append(delimiter)
        .append(charge).append(delimiter)
        .append(objPricingCostVO.getChargeType() != null ? objPricingCostVO.getChargeType().getChargeTypeIdentifier()+"":"").append(delimiter)
       	.append(chargeWOL).append(lineSeparator);
        logger.info("COST IS SAVED");
        
       /* System.out.println("Charge WOL :" + chargeWOL);
        System.out.println("String Buffer :" + HibernateUtil.sbCost);*/
        if(BuildConfig.DMODE)
        {
            System.out.println("Term:"+term);
            System.out.println("TermMonth:"+termMonth);
            if(objPricingCostVO.getEnergyChargeName()!= null)
                System.out.println("EnergyCharge:"+objPricingCostVO.getEnergyChargeName().getEnergyChargeIdentifier());
            if(objPricingCostVO.getPriceBlock() != null)
                System.out.println("Price Block:"+ objPricingCostVO.getPriceBlock().getPriceBlockIdentifier());
            System.out.println("Charge:"+charge);
        }
    }
    private EnergyOtherCharges getOtherCharges(Date termMonth, int congestionZoneId)
    {
        logger.info("GETTING OTHER CHARGES FOR CONGESTIONZONE");
        EnergyOtherCharges objEnergyOtherCharges = new EnergyOtherCharges();
        objEnergyOtherCharges.setAsCharges(EnergyChargeRatesDAO.getCharge(6, termMonth, congestionZoneId));
        objEnergyOtherCharges.setCongestionCharges(EnergyChargeRatesDAO.getCharge(7, termMonth, congestionZoneId));
        objEnergyOtherCharges.setGrossReceiptsAssesmentFee(EnergyChargeRatesDAO.getCharge(8, termMonth, congestionZoneId));
        objEnergyOtherCharges.setUfe(EnergyChargeRatesDAO.getCharge(3, termMonth, congestionZoneId));
        objEnergyOtherCharges.setErcotAdminFee(EnergyChargeRatesDAO.getCharge(4, termMonth, congestionZoneId));
        objEnergyOtherCharges.setRecCharge(EnergyChargeRatesDAO.getCharge(1, termMonth, congestionZoneId));
        logger.info("GOT OTHER CHARGES");
        return objEnergyOtherCharges;
    }
    
    private float getPrice(Date marketDate,Date monthYear,int priceBlockId,int congestionZoneId,int dataSrcId)
    {
        return ForwardCurveBlockDAO.getPrice(marketDate, monthYear, priceBlockId, congestionZoneId, dataSrcId);
    }
    
    private float getProfilePrice(Date marketDate, Date monthYear, ProfileDetails objProfileDetails, int dataSrcId)
    {
        float price = 0;
        try
        {
            logger.info("GETTING PROFILE PRICE");
            price = ForwardCurveProfileDAO.getPrice(marketDate, monthYear, objProfileDetails.getProfileTypeId(), objProfileDetails.getWeatherZoneId(), dataSrcId);
            logger.info("GOT PROFILE PRICE");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET PROFILE PRICE", e);
            e.printStackTrace();
            throw new HibernateException(e.toString());
        }
        return price;
    }
    private double[] getBlockUsage(Date monthYear, int usageRefId)
    {
        logger.info("GETTING BLOCK USAGE");
        double usage5X16 = 0; 
        double usage2X16 = 0;
        double usage5X8 = 0;
        double usage2X8 = 0;
        
        double usage5X16WOL = 0; 
        double usage2X16WOL = 0;
        double usage5X8WOL = 0;
        double usage2X8WOL = 0;
        List termWiseUsage=HibernateUtil.objUsageVOS;
        boolean applyLoss = HibernateUtil.applyLoss;
        double[] usage = new double[8];
        for(int i=0;i<termWiseUsage.size();i++)
        {
            UsageVO termUsage=(UsageVO)termWiseUsage.get(i);
            if(termUsage.getUsageRef().getPricingUsageRefId() == usageRefId && termUsage.getTermMonth().equals(monthYear))
            {
                if(termUsage.getDayType().getDayTypeId() == 2)
                {
                    if(termUsage.getHour() <23  && termUsage.getHour()>6)
                    {
                        if(applyLoss)
                        {
                            usage5X16 += termUsage.getForecastedUsageWL();
                            usage5X16WOL += termUsage.getForecastedUsage();
                        }
                        else
                            usage5X16 += termUsage.getForecastedUsage();
                    }
                    else
                    {
                        if(applyLoss)
                        {
                            usage5X8 += termUsage.getForecastedUsageWL();
                            usage5X8WOL += termUsage.getForecastedUsage();
                        }
                        else
                            usage5X8 += termUsage.getForecastedUsage();
                    }
                }
                else if(termUsage.getDayType().getDayTypeId() == 3)
                {
                    if(termUsage.getHour() <23  && termUsage.getHour()>6)
                    {
                        if(applyLoss)
                        {
                            usage2X16 += termUsage.getForecastedUsageWL();
                            usage2X16WOL += termUsage.getForecastedUsage();
                        }
                        else
                            usage2X16 += termUsage.getForecastedUsage();
                    }
                    else
                    {
                        if(applyLoss)
                        {
                            usage2X8 += termUsage.getForecastedUsageWL();
                            usage2X8WOL += termUsage.getForecastedUsage();
                        }
                        else
                            usage2X8 += termUsage.getForecastedUsage();
                    }
                }    
            }
        }
        usage[0] = usage5X16;
        usage[1] = usage5X8;
        usage[2] = usage2X16;
        usage[3] = usage2X8;
        usage[4] = usage5X16WOL;
        usage[5] = usage5X8WOL;
        usage[6] = usage2X16WOL;
        usage[7] = usage2X8WOL;
        logger.info("GOT BLOCK USAGE");
        return usage;
    }    
        
    public static void main(String[] args)
    {
       // System.out.println("5 X 16:"+new CostComputationDAO().getBlockUsage(new Date("01-01-2006"),312,6));
        //CostComputationDAO obj = new CostComputationDAO();
        //System.out.println("5 X 16:"+obj.getBlockUsage(new Date("01/01/2006"),312,6));
    }
}

/*
*$Log: CostComputationDAO.java,v $
*Revision 1.2  2008/02/25 09:32:04  tannamalai
*extra column added in prc_cost table to calculate energy charge without loss
*
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:54  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:23  jnadesan
*initail MXEP commit
*
*Revision 1.31  2007/09/10 05:07:29  sramasamy
*Log Message Correction.
*
*Revision 1.30  2007/09/04 05:01:09  kduraisamy
*Archive mapping added and method changed according to the changes.
*
*Revision 1.29  2007/08/31 14:49:19  sramasamy
*Log message is added for log file.
*
*Revision 1.28  2007/07/31 12:26:53  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.27  2007/07/18 10:02:29  kduraisamy
*tdsp charge computation hard coded value removed. (Customer Charge)
*
*Revision 1.26  2007/07/03 04:53:51  kduraisamy
*AS is taken by congestion zone wise.
*
*Revision 1.25  2007/06/14 10:14:19  kduraisamy
*unitary added for shaping premium.
*
*Revision 1.24  2007/06/12 12:55:25  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.23  2007/05/13 07:57:45  kduraisamy
*block usage error rectified.
*
*Revision 1.22  2007/05/13 07:53:16  kduraisamy
*block usage error rectified.
*
*Revision 1.21  2007/05/12 06:19:52  kduraisamy
*contract kWh without loss taken for dashboard.
*
*Revision 1.20  2007/04/22 16:25:52  kduraisamy
*error handling added.
*
*Revision 1.19  2007/04/06 12:31:58  kduraisamy
*TLF AND DLF COMPLETED.
*
*Revision 1.18  2007/03/21 15:15:53  jnadesan
*buildcofig added
*
*Revision 1.17  2007/03/21 10:25:34  jnadesan
*Id hard coded problem solved.
*
*Revision 1.16  2007/03/15 11:55:01  kduraisamy
*comma changed as delimiter.
*
*Revision 1.15  2007/03/09 08:52:14  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.14  2007/03/09 04:14:24  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.13  2007/03/08 16:30:34  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.12  2007/02/28 14:33:44  kduraisamy
*save added.
*
*Revision 1.11  2007/02/28 06:53:57  kduraisamy
*optimization started by sriram.
*
*Revision 1.10  2007/02/20 12:37:23  kduraisamy
*optimization took place.
*
*Revision 1.9  2007/02/14 12:45:43  kduraisamy
*excecute() return type changed.
*
*Revision 1.8  2007/02/14 08:50:08  kduraisamy
*esiId wise details stored in static hashMap.
*
*Revision 1.7  2007/02/13 14:04:57  kduraisamy
*BillingDemand related mapping completed.
*
*Revision 1.6  2007/02/12 06:55:01  kduraisamy
*shaping premium ratio added.
*
*Revision 1.5  2007/02/12 05:55:43  kduraisamy
*unwanted semicolon removed.
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