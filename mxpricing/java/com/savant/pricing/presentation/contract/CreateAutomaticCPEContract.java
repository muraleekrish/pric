/*
 * Created on May 3, 2007
 * 
 * Class Name CreateAutomaticCPEContract.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.contract;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.savant.pricing.calculation.dao.DealLeversDAO;
import com.savant.pricing.calculation.dao.PricingDAO;
import com.savant.pricing.calculation.valueobjects.PICVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerProductsVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerTermsVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO;
import com.savant.pricing.common.MXenergyPriceRun;
import com.savant.pricing.dao.ContractsDAO;
import com.savant.pricing.dao.CustEnergyComponentsDAO;
import com.savant.pricing.dao.PICDAO;
import com.savant.pricing.dao.PreferenceProductsDAO;
import com.savant.pricing.dao.PreferenceTermsDAO;
import com.savant.pricing.dao.PriceRunCostCapitalDAO;
import com.savant.pricing.dao.PriceRunCustomerDAO;
import com.savant.pricing.securityadmin.dao.UserDAO;
import com.savant.pricing.securityadmin.valueobject.UsersVO;
import com.savant.pricing.transferobjects.DealLevers;
import com.savant.pricing.transferobjects.EPP;
import com.savant.pricing.transferobjects.HeatRateProduct;
import com.savant.pricing.transferobjects.PricingDashBoard;
import com.savant.pricing.valueobjects.ContractsVO;
import com.savant.pricing.valueobjects.ProductsVO;
import com.savant.pricing.valueobjects.TaxRatesVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreateAutomaticCPEContract
{
    public void createCPEAndContract(int priceRunCustomerRefId)
    {
        PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
        PreferenceProductsDAO objPreferenceProductsDAO = new PreferenceProductsDAO();
        PreferenceTermsDAO objPreferenceTermsDAO = new PreferenceTermsDAO();
        PriceRunCustomerTermsVO objPriceRunCustomerTermsVO = null;
        PriceRunCustomerProductsVO objPriceRunCustomerProductsVO = null;
        
        CustEnergyComponentsDAO objCustEnergyComponentsDAO = new CustEnergyComponentsDAO();
        PriceRunCostCapitalDAO objPriceRunCostCapitalDAO = new PriceRunCostCapitalDAO();
        PriceRunCustomerVO objPriceRunCustomerVO = null;
        HashMap hmResult = new HashMap();
        HashMap hmValid = new HashMap();
        HashMap hmNotValid = new HashMap();
        HashMap hmCostCap = new HashMap();
        
        
        objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(priceRunCustomerRefId);
        hmResult = objCustEnergyComponentsDAO.getEngCompo(objPriceRunCustomerVO.getProspectiveCustomer().getProspectiveCustomerId());
        hmNotValid = (HashMap) hmResult.get("notvalid") ;
        hmValid = (HashMap)hmResult.get("valid");
        
      
        
        HeatRateProduct objHeatRateProduct = new HeatRateProduct();
        String strESIID = "";
        ProductsVO objProductsVO = new ProductsVO();
        PricingDAO objPricingDAO = new PricingDAO();
        PICDAO objPICDAO = new PICDAO();
        float fptc = 0;
        float taxAmount = 0;
        ContractsVO objContractsVO = new ContractsVO();
        ContractsDAO objContractsDAO = new ContractsDAO();
        PricingDashBoard objPricingDashBoard = null;
        Collection objtaxcollection = new PricingDAO().getTaxRates();
        Iterator ite = objtaxcollection.iterator();
        float gr = 0;
        float ct = 0;
        float slt = 0;
        while (ite.hasNext())
        {
            TaxRatesVO rates = (TaxRatesVO) ite.next();
            switch (rates.getTaxType().getTaxTypeIdentifier())
            {
            case 1:
                ct = rates.getTaxRate();
                break;
            case 2:
                slt = rates.getTaxRate();
                break;
            case 3:
                gr = rates.getTaxRate();
                break;
            default:
                break;
            }
        }
        List ltspreferenceproducts = (List) objPreferenceProductsDAO.getAllPreferenceProducts(objPriceRunCustomerVO.getPriceRunCustomerRefId());
        List lstEsiid = objPICDAO.getAllValidESIID(objPriceRunCustomerVO.getProspectiveCustomer().getProspectiveCustomerId());
        strESIID = this.getESIID(lstEsiid);
        List lstPreferenceTerms = (List) objPreferenceTermsDAO.getAllPreferenceTerms(objPriceRunCustomerVO.getPriceRunCustomerRefId());
        if(lstPreferenceTerms.size()>0)
        {
            Iterator iteTerm = lstPreferenceTerms.iterator();
            if(objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId() != null)
                while (iteTerm.hasNext())
                {
                    objPriceRunCustomerTermsVO = (PriceRunCustomerTermsVO) iteTerm.next();
                    DealLeversDAO objDealLeversDAO = new DealLeversDAO();
                    objHeatRateProduct = objPricingDAO.getHeatRateProduct(7900, objPriceRunCustomerTermsVO.getTerm(), objPriceRunCustomerVO.getPriceRunCustomerRefId(), this.getESIID(lstEsiid));
                    List deallevers = objDealLeversDAO.getDealLevers(objPriceRunCustomerVO.getPriceRunCustomerRefId(), objPriceRunCustomerTermsVO.getTerm());
                    Iterator iter = deallevers.iterator();
                    HashMap mapdealvalue = new HashMap();
                    while (iter.hasNext())
                    {
                        DealLevers objdeallevers = (DealLevers) iter.next();
                        mapdealvalue.put(new Integer(objdeallevers.getDealLeverIdentifier()), new Double(objdeallevers.getValue()));
                    }
                    objPricingDashBoard = objPricingDAO.getDashBoardDetails(objPriceRunCustomerVO.getPriceRunCustomerRefId(), objPriceRunCustomerTermsVO.getTerm(), this.getESIID(lstEsiid));
                    Iterator iteProducts = ltspreferenceproducts.iterator();
                    while (iteProducts.hasNext())
                    {
                        objPriceRunCustomerProductsVO = (PriceRunCustomerProductsVO) iteProducts.next();
                        objContractsVO = new ContractsVO();
                        float Interestrate = 0;
                    	float costlc = 0;
                    	float mcpe = 0;
                    	float planlong = 0;
                    	float maxmtm = 0;
                    	float arfloat = 0;
                    	float isomargin = 0;
                    	float tdspfloat = 0;
                    	
                    	hmCostCap = objPriceRunCostCapitalDAO.getHmPrcCost(objPriceRunCustomerVO.getPriceRunRef().getPriceRunRefNo());
                    	if(hmCostCap.get(new Integer(1))!=null)
                    		Interestrate = (((Float)hmCostCap.get(new Integer(1))).floatValue())/100;
                    	if(hmCostCap.get(new Integer(2))!=null)
                    		costlc = (((Float)hmCostCap.get(new Integer(2))).floatValue())/100;		
                    	if(hmCostCap.get(new Integer(3))!=null)
                    		mcpe = (((Float)hmCostCap.get(new Integer(3))).floatValue())/100;
                    	if(hmCostCap.get(new Integer(4))!=null)
                    		planlong = (((Float)hmCostCap.get(new Integer(4))).floatValue())/100;
                    	if(hmCostCap.get(new Integer(5))!=null)
                    		maxmtm = (((Float)hmCostCap.get(new Integer(5))).floatValue())/100;
                    	if(hmCostCap.get(new Integer(6))!=null)
                    		arfloat = (((Float)hmCostCap.get(new Integer(6))).floatValue());
                    	if(hmCostCap.get(new Integer(7))!=null)
                    		isomargin = (((Float)hmCostCap.get(new Integer(7))).floatValue());
                    	if(hmCostCap.get(new Integer(8))!=null)
                    		tdspfloat = (((Float)hmCostCap.get(new Integer(8))).floatValue());
                    	    	
                    	double totalCostCapital = 0 ;
                    	double supplierMargin  = 0 ;
                    	double floatOnAr = 0 ;
                    	double floatOnTdsp = 0 ;
                    	double isoCrdit = 0 ;
                    	
                    	supplierMargin =(objPricingDashBoard.getEnergyCharge()*(1+planlong)*((objPriceRunCustomerTermsVO.getTerm()/2.0)+.5)*(maxmtm*(costlc/12)));
                    	floatOnAr =(objPricingDashBoard.getAncillaryServices() + (objPricingDashBoard.getEnergyCharge()*(1+planlong)))*Interestrate*(arfloat/365.0);
                    	floatOnTdsp =(objPricingDashBoard.getTdspCharges() * Interestrate * (tdspfloat/365.0));
                    	isoCrdit =((objPricingDashBoard.getAncillaryServices()+(objPricingDashBoard.getEnergyCharge()*mcpe))*Interestrate*(isomargin/365.0));
                    	totalCostCapital = supplierMargin+floatOnAr+floatOnTdsp+isoCrdit;
                    	
                    	 float shapPrm = 0;
                    	 System.out.println("shap prem : " + objPricingDashBoard.getShapingPremium());
                    	 System.out.println("mapdeao 8  : " + ((float)((Double)mapdealvalue.get(new Integer(8))).doubleValue()+100)/100);
                         shapPrm =  objPricingDashBoard.getShapingPremium()*(((float)((Double)mapdealvalue.get(new Integer(8))).doubleValue()+100)/100);
                         
                        if(objPriceRunCustomerProductsVO.getProduct().getProductIdentifier() != 5)
                        {
                            
                            float total$ = 0;
                            float oHtotal$ = 0;
                            float mcpeadder = 0;
                            
                            total$ += objPricingDashBoard.getEnergyCharge();
                            total$ += shapPrm;
                            total$ += objPricingDashBoard.getVolatilityPremium();
                            total$ += objPricingDashBoard.getAncillaryServices();
                            total$ += objPricingDashBoard.getIntraZonalCongestion();
                            total$ += objPricingDashBoard.getFeesAndRegulatory();
                            total$ +=totalCostCapital;  
                            oHtotal$ = objPricingDashBoard.getCustomerCharge() * (float) ((Double) mapdealvalue.get(new Integer(1))).doubleValue();
                            oHtotal$ += objPricingDashBoard.getAdditionalVolatilityPremium() * (float) ((Double) mapdealvalue.get(new Integer(7))).doubleValue() / 100;
                            oHtotal$ += objPricingDashBoard.getSalesAgentFee() * (float) ((Double) mapdealvalue.get(new Integer(4))).doubleValue();
                            oHtotal$ += objPricingDashBoard.getAggregatorFee() * (float) ((Double) mapdealvalue.get(new Integer(5))).doubleValue();
                            oHtotal$ += objPricingDashBoard.getBandwidthCharge() * (float) ((Double) mapdealvalue.get(new Integer(6))).doubleValue();
                            oHtotal$ += objPricingDashBoard.getOtherFee() * (float) ((Double) mapdealvalue.get(new Integer(2))).doubleValue();
                            
                            if(hmNotValid.containsKey(new Integer(1)))
                                total$ =total$ - objPricingDashBoard.getEnergyCharge();
                			if(hmNotValid.containsKey(new Integer(2)))
                			    total$ =total$ -  shapPrm;
                			if(hmNotValid.containsKey(new Integer(3)))
                			    total$ =total$ -  objPricingDashBoard.getVolatilityPremium();
                			if(hmNotValid.containsKey(new Integer(4)))
                			    total$ =total$ -  objPricingDashBoard.getAncillaryServices();
                			if(hmNotValid.containsKey(new Integer(5)))
                			    total$ =total$ -  objPricingDashBoard.getIntraZonalCongestion();
                			if(hmNotValid.containsKey(new Integer(6)))
                			    total$ =total$ -  objPricingDashBoard.getFeesAndRegulatory();
                			if(hmNotValid.containsKey(new Integer(13)))
                			    total$ =(float) (total$ -  totalCostCapital);
                			
                			
                			if(hmNotValid.containsKey(new Integer(7)))
                			    oHtotal$ =oHtotal$ -  (float)objPricingDashBoard.getCustomerCharge()*(float) ((Double) mapdealvalue.get(new Integer(1))).doubleValue();
                			if(hmNotValid.containsKey(new Integer(8)))
                			    oHtotal$ =oHtotal$ -   (float)objPricingDashBoard.getAdditionalVolatilityPremium()*(float) ((Double) mapdealvalue.get(new Integer(7))).doubleValue() / 100;
                			if(hmNotValid.containsKey(new Integer(9)))
                			    oHtotal$ =oHtotal$ -  (float)objPricingDashBoard.getSalesAgentFee()*(float) ((Double) mapdealvalue.get(new Integer(4))).doubleValue();
                			if(hmNotValid.containsKey(new Integer(10)))
                			    oHtotal$ =oHtotal$ -  (float)objPricingDashBoard.getAggregatorFee()*(float) ((Double) mapdealvalue.get(new Integer(5))).doubleValue();
                			if(hmNotValid.containsKey(new Integer(11)))
                			    oHtotal$ =oHtotal$ -  (float)objPricingDashBoard.getBandwidthCharge()*(float) ((Double) mapdealvalue.get(new Integer(6))).doubleValue();
                			if(hmNotValid.containsKey(new Integer(12)))
                			    oHtotal$ =oHtotal$ -  (float)objPricingDashBoard.getOtherFee()*(float) ((Double) mapdealvalue.get(new Integer(2))).doubleValue();
                			
                			
                              if(hmValid.containsKey(new Integer(3)))
                              {
                                  mcpeadder += objPricingDashBoard.getVolatilityPremium();
                              }
                              if(hmValid.containsKey(new Integer(4)))
                              {
                                  mcpeadder += objPricingDashBoard.getAncillaryServices();
                              }
                              if(hmValid.containsKey(new Integer(5)))
                              {
                                  mcpeadder += objPricingDashBoard.getIntraZonalCongestion();
                              }
                              if(hmValid.containsKey(new Integer(6)))
                              {
                                  mcpeadder += objPricingDashBoard.getFeesAndRegulatory();
                              }
                              if(hmValid.containsKey(new Integer(7)))
                              {
                                  mcpeadder += objPricingDashBoard.getCustomerCharge()*(float)((Double)mapdealvalue.get(new Integer(1))).doubleValue();
                              }
                              if(hmValid.containsKey(new Integer(9)))
                              {
                                  mcpeadder += objPricingDashBoard.getSalesAgentFee()*(float)((Double)mapdealvalue.get(new Integer(4))).doubleValue();
                              }
                              if(hmValid.containsKey(new Integer(10)))
                              {
                                  mcpeadder += objPricingDashBoard.getAggregatorFee()*(float)((Double)mapdealvalue.get(new Integer(5))).doubleValue();
                              }
                              if(hmValid.containsKey(new Integer(11)))
                              {
                                  mcpeadder += objPricingDashBoard.getBandwidthCharge()*(float)((Double)mapdealvalue.get(new Integer(6))).doubleValue();
                              }
                              if(hmValid.containsKey(new Integer(12)))
                              {
                                  mcpeadder += objPricingDashBoard.getOtherFee()*(float)((Double)mapdealvalue.get(new Integer(2))).doubleValue();
                              }
                              
                            
                            fptc = objPricingDashBoard.getMargin() * (float) ((Double) mapdealvalue.get(new Integer(3))).doubleValue() + oHtotal$ + total$;
                            float total = 0;
                            if(objPriceRunCustomerVO.isTaxExempt())
                                total = (fptc + objPricingDashBoard.getTdspCharges()) * (1 + (gr / 100));
                            else
                                total = (fptc + objPricingDashBoard.getTdspCharges()) * (1 + (gr / 100)) * (1 + (slt / 100) + (ct / 100));
                            taxAmount = total - fptc - objPricingDashBoard.getTdspCharges();
                            objContractsVO.setTaxes(taxAmount);
                            System.out.println("ContractkWh : "+objPricingDashBoard.getContractkWh()+" mcpeadder : "+ mcpeadder);
                            if(objPricingDashBoard.getContractkWh()>0)
                            {
                                mcpeadder =  objPricingDashBoard.getMargin() * (float) ((Double) mapdealvalue.get(new Integer(3))).doubleValue()+mcpeadder;
                                objContractsVO.setMcpeAdder((mcpeadder) / objPricingDashBoard.getContractkWh());
                            }
                            else
                            {
                                objContractsVO.setMcpeAdder(0);
                            }
                           // objPricingDashBoard.setContractkWh(0);
                        }
                        objContractsVO.setAggregatorFee(objPricingDashBoard.getAggregatorFee() * (float) ((Double) mapdealvalue.get(new Integer(5))).doubleValue());
                        objContractsVO.setContractkWh(objPricingDashBoard.getContractkWh());
                        objContractsVO.setPriceRunCustomerRef(objPriceRunCustomerVO);
                        objContractsVO.setSalesCommision(objPricingDashBoard.getSalesAgentFee() * (float) ((Double) mapdealvalue.get(new Integer(4))).doubleValue());
                        objContractsVO.setTdspCharges(objPricingDashBoard.getTdspCharges());
                        objContractsVO.setTerm(objPriceRunCustomerTermsVO.getTerm());
                        objContractsVO.setEsiIds(strESIID);
                        objProductsVO = new ProductsVO();
                        objProductsVO.setProductIdentifier(objPriceRunCustomerProductsVO.getProduct().getProductIdentifier());
                        objContractsVO.setProduct(objProductsVO);
                        objContractsVO.setAnnualkWh(objPricingDashBoard.getAnnualkWh());
                        objContractsVO.setAnnualkW(objPricingDashBoard.getMaxDemandkW());
                        objContractsVO.setCustomerCharge(objPricingDashBoard.getCustomerCharge() * (float) ((Double) mapdealvalue.get(new Integer(1))).doubleValue());
                        objContractsVO.setLoadFactor(objPricingDashBoard.getLoadFactorPercentage());
                        objContractsVO.setCityTax(gr);
                        Date expDate = objContractsDAO.cpeExpiryDate(objPriceRunCustomerVO.getPriceRunRef().getPriceRunTime());
                        objContractsVO.setExpDate(expDate);
                        objContractsVO.setStartDate(new Date());
                        System.out.println("Start Date :"+objContractsVO.getStartDate());
                        System.out.println("Exp Date :"+objContractsVO.getExpDate());
                        objContractsVO.setCountyTax(ct);
                        objContractsVO.setStateTax(slt);
                        UserDAO objUserDAO = new UserDAO();
                        UsersVO objUsersVO = null;
                        try
                        {
                            objUsersVO = objUserDAO.getUsers(objPriceRunCustomerVO.getProspectiveCustomer().getSalesRep().getUserId());
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        objContractsVO.setSalesRep(objUsersVO);
                        if(objPricingDashBoard.getContractkWh()>0)
                        objContractsVO.setContractPrice$PerkWh(((fptc + objPricingDashBoard.getTdspCharges())) / objPricingDashBoard.getContractkWh());
                        if(objPriceRunCustomerVO.isTaxExempt())
                            objContractsVO.setTotalAnnualBill((fptc + objPricingDashBoard.getTdspCharges()) * (1 + (gr / 100)));
                        else
                            objContractsVO.setTotalAnnualBill((fptc + objPricingDashBoard.getTdspCharges()) * (1 + (gr / 100)) * (1 + (slt / 100) + (ct / 100)));
                        objContractsVO.setHeatRate(7900);
                        objContractsVO.setHeatRateAdder(objHeatRateProduct.getRetailAdder());
                        System.out.println("Case :" + objPriceRunCustomerProductsVO.getProduct().getProductIdentifier());
                        switch (objPriceRunCustomerProductsVO.getProduct().getProductIdentifier())
                        {
                        case 1:
                            if(objPricingDashBoard.getContractkWh()>0)
                            objContractsVO.setFixedPrice$PerMWh(fptc * 1000 / objPricingDashBoard.getContractkWh());
                            objContractsDAO.addContract(objContractsVO);
                            break;
                        case 2:
                            if(objPricingDashBoard.getContractkWh()>0)
                            objContractsVO.setFixedPrice$PerMWh(fptc * 1000 / objPricingDashBoard.getContractkWh());
                            objContractsDAO.addContract(objContractsVO);
                            break;
                        case 3:
                            if(objPricingDashBoard.getContractkWh()>0)
                            objContractsVO.setFixedPrice$PerMWh(fptc * 1000 / objPricingDashBoard.getContractkWh());
                            objContractsDAO.addContract(objContractsVO);
                            break;
                        case 4:
                            if(objPricingDashBoard.getContractkWh()>0)
                            objContractsVO.setFixedPrice$PerMWh(fptc * 1000 / objPricingDashBoard.getContractkWh());
                            objContractsDAO.addContract(objContractsVO);
                            break;
                        case 5:
                            EPP objEPP = objPricingDAO.getEPP(7, objPriceRunCustomerTermsVO.getTerm(), objPriceRunCustomerVO.getPriceRunCustomerRefId(), this.getESIID(lstEsiid));
                            float taxAmountFAF = 0;
                            if(objPriceRunCustomerVO.isTaxExempt())
                                taxAmountFAF = (((objEPP.getFixedPrice() / 1000) * objPricingDashBoard.getContractkWh()) + objPricingDashBoard.getTdspCharges()) * ((gr / 100));
                            else
                                taxAmountFAF = (((objEPP.getFixedPrice() / 1000) * objPricingDashBoard.getContractkWh()) + objPricingDashBoard.getTdspCharges()) * ((gr / 100) + (1 + gr / 100) * ((slt + ct) / 100));
                            objContractsVO.setFixedPrice$PerMWh(fptc*1000/objPricingDashBoard.getContractkWh());
                            objContractsVO.setAvgGasPrice(objEPP.getWeightedAvgGasPrice());
                            objContractsVO.setFuelFactor(objEPP.getFuelAdjustmentRate());
                            objContractsVO.setGasPrice$PerMMBtu(7);
                            objContractsVO.setBaseRate$PerMWh(objEPP.getBaseRate() * 1000);
                            objContractsVO.setSalesCommision(objPricingDashBoard.getSalesAgentFee() * (float) ((Double) mapdealvalue.get(new Integer(4))).doubleValue());
                            objContractsVO.setTaxes(taxAmountFAF);
                            objContractsVO.setComputedFAF(objEPP.getFafMultiplier());
                            objContractsDAO.addContract(objContractsVO);
                            break;
                        case 6:
                            if(objPriceRunCustomerVO.isTaxExempt())
                                if(objPricingDashBoard.getContractkWh()>0)
                                objContractsVO.setFixedPrice$PerMWh(((fptc + objPricingDashBoard.getTdspCharges()) * (1 + (gr / 100))) / objPricingDashBoard.getContractkWh() * 1000);
                            else
                                if(objPricingDashBoard.getContractkWh()>0)
                                objContractsVO.setFixedPrice$PerMWh(((fptc + objPricingDashBoard.getTdspCharges()) * (1 + (gr / 100)) * (1 + (slt / 100) + (ct / 100))) / objPricingDashBoard.getContractkWh() * 1000);
                            objContractsDAO.addContract(objContractsVO);
                            break;
                        case 7:
                            if(objPriceRunCustomerVO.isTaxExempt())
                                if(objPricingDashBoard.getContractkWh()>0)
                                objContractsVO.setFixedPrice$PerMWh(((fptc + objPricingDashBoard.getTdspCharges()) * (1 + (gr / 100))) / objPricingDashBoard.getContractkWh() * 1000);
                            else
                                if(objPricingDashBoard.getContractkWh()>0)
                                objContractsVO.setFixedPrice$PerMWh(((fptc + objPricingDashBoard.getTdspCharges()) * (1 + (gr / 100)) * (1 + (slt / 100) + (ct / 100))) / objPricingDashBoard.getContractkWh() * 1000);
                            objContractsDAO.addContract(objContractsVO);
                            break;
                        case 8:
                            if(objPricingDashBoard.getContractkWh()>0)
                            objContractsVO.setFixedPrice$PerMWh(fptc * 1000 / objPricingDashBoard.getContractkWh());
                            objContractsDAO.addContract(objContractsVO);
                            break;
                        default:
                            break;
                        }
                    }
                }
        }
    }
    
    public String getESIID(List lstEsiid)
    {
        Iterator itePIC = lstEsiid.iterator();
        String esiId = "";
        while (itePIC.hasNext())
        {
            if(esiId.length() > 1)
                esiId += ",";
            esiId += ((PICVO) itePIC.next()).getEsiId();
        }
        return esiId;
    }
    
    public static void main(String[] args)
    {
        CreateAutomaticCPEContract objCreateAutomaticCPEContract = new CreateAutomaticCPEContract();
        MXenergyPriceRun objMXenergyPriceRun = new MXenergyPriceRun();
        List lstCustomers = (List) objMXenergyPriceRun.lastAutomaticSuccessRunCustomers("M");
    }
}
/*
 *$Log: CreateAutomaticCPEContract.java,v $
 *Revision 1.6  2009/01/27 05:47:57  tannamalai
 *changes - according to MX server
 *
 *Revision 1.5  2008/02/14 05:44:46  tannamalai
 *pagination done for price quote page
 *
 *Revision 1.4  2008/02/08 06:53:47  tannamalai
 *last commit before table split up
 *
 *Revision 1.3  2008/02/06 06:42:45  tannamalai
 *mcpe calculations corrected
 *
 *Revision 1.2  2007/12/12 09:55:44  jvediyappan
 *filename changed.
 *
 *Revision 1.1  2007/12/07 06:18:50  jvediyappan
 *initial commit.
 *
 *Revision 1.1  2007/10/30 05:51:56  jnadesan
 *Initial commit.
 *
 *Revision 1.1  2007/10/26 15:19:28  jnadesan
 *initail MXEP commit
 *
 *Revision 1.28  2007/10/15 04:17:03  jnadesan
 *fixed price also imported for FAR contract
 *
 *Revision 1.27  2007/08/10 12:30:25  kduraisamy
 *contractkWh 0 error solved.
 *
 *Revision 1.26  2007/08/10 07:39:11  kduraisamy
 *Problem in unimported customers while creating CPE error solved.
 *
 *Revision 1.25  2007/08/09 06:06:12  jnadesan
 *lockterm cahnged to 3 if its less than 3
 *
 *Revision 1.24  2007/08/03 12:22:06  kduraisamy
 *cpe creation with the run.
 *
 *Revision 1.23  2007/07/30 10:37:55  jnadesan
 *if price is  less for less than 3 month cpe or contract is not created
 *
 *Revision 1.22  2007/07/30 09:22:26  spandiyarajan
 *exception handled
 *
 *Revision 1.21  2007/07/19 13:46:08  jnadesan
 *CPE/Contract is not created is there is no ESIID
 *
 *Revision 1.20  2007/07/18 06:39:28  jnadesan
 *unwanted loop removed
 *
 *Revision 1.19  2007/07/18 04:55:02  jnadesan
 *tax exception added
 *
 *Revision 1.18  2007/07/17 08:41:17  kduraisamy
 *Tax computation calculation changed
 *
 *Revision 1.17  2007/07/13 14:01:52  jnadesan
 *taxe computaion changed
 *
 *Revision 1.16  2007/07/13 05:02:54  kduraisamy
 *tax computaion changed
 *
 *Revision 1.15  2007/07/12 12:05:31  jnadesan
 *cpe creation added for manual run too
 *
 *Revision 1.14  2007/07/06 09:19:59  jnadesan
 *lowest term taken as per first preference term details.
 *
 *Revision 1.13  2007/07/05 06:13:23  srajan
 *Base gas price computaion changed
 *
 *Revision 1.12  2007/07/04 13:19:39  jnadesan
 *lock  deal avoided if cms id is not available.
 *
 *Revision 1.11  2007/06/28 10:49:46  kduraisamy
 *min value is added into cpe creation in auto run
 *
 *Revision 1.10  2007/06/23 08:03:04  jnadesan
 *updating startDate and ExpDate
 *
 *Revision 1.9  2007/06/05 11:03:29  jnadesan
 *methods are changed to take preference products after price run
 *
 *Revision 1.8  2007/06/05 06:21:41  jnadesan
 *pricerun customer id sent instead of customer id
 *
 *Revision 1.7  2007/05/28 11:58:40  jnadesan
 *bundle cost calculation changed
 *
 *Revision 1.6  2007/05/28 10:48:11  jnadesan
 *new column are added
 *
 *Revision 1.5  2007/05/22 13:56:41  jnadesan
 *fafmultiplier changed
 *
 *Revision 1.4  2007/05/16 09:35:23  jnadesan
 *lock all deal option provided
 *
 *Revision 1.3  2007/05/03 09:53:30  jnadesan
 *Contract created for far product by automatic
 *
 *Revision 1.2  2007/05/03 09:18:00  jnadesan
 *lines commmented
 *
 *Revision 1.1  2007/05/03 09:10:56  jnadesan
 *initial commit
 *
 *
 */