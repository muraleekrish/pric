/*
 * Created on May 16, 2007
 * 
 * Class Name LockAllDeal.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.contract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.savant.pricing.calculation.dao.DealLeversDAO;
import com.savant.pricing.calculation.dao.PricingDAO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerProductsVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerTermsVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO;
import com.savant.pricing.dao.ContractsDAO;
import com.savant.pricing.dao.CustEnergyComponentsDAO;
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
public class LockAllDeal {
    
    public String createPreferenceCPEContract(int priceRunId, String strESIID, float baseGasPrice, String message, double costCapital)
    {
        String resultMessage = "Failed";
        try
        {
            PreferenceTermsDAO objPreferenceTermsDAO = new PreferenceTermsDAO();
            PreferenceProductsDAO objPreferenceProductsDAO = new PreferenceProductsDAO();
            PriceRunCostCapitalDAO objPriceRunCostCapitalDAO = new PriceRunCostCapitalDAO();
            PriceRunCustomerProductsVO objPriceRunCustomerProductsVO =null;
            PriceRunCustomerTermsVO objPriceRunCustomerTermsVO = null;
            boolean result = false;
            ProductsVO objProductsVO = new ProductsVO();
            PricingDAO objPricingDAO = new PricingDAO();
            CustEnergyComponentsDAO objCustEnergyComponentsDAO = new CustEnergyComponentsDAO();
            HashMap hmResult = new HashMap();
            HashMap hmValid = new HashMap();
            HashMap hmNotValid = new HashMap();
            float fptc = 0;
            float taxAmount = 0;
            ContractsVO objContractsVO = null;
            ContractsDAO objContractsDAO = new ContractsDAO();
            PricingDashBoard objPricingDashBoard = null;
            PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
            Collection objtaxcollection=new PricingDAO().getTaxRates();
            HeatRateProduct objHeatRateProduct = new HeatRateProduct();
            Iterator ite = objtaxcollection.iterator();
            
            float gr = 0;
            float ct = 0;
            float slt = 0;
            while(ite.hasNext())
            {
                TaxRatesVO rates = (TaxRatesVO)ite.next(); 
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
            List ltspreferenceproducts = null;
            PriceRunCustomerVO objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(priceRunId);
            hmResult = objCustEnergyComponentsDAO.getEngCompo(objPriceRunCustomerVO.getProspectiveCustomer().getProspectiveCustomerId());
            hmNotValid = (HashMap) hmResult.get("notvalid") ;
            hmValid = (HashMap)hmResult.get("valid");
            if(message.equalsIgnoreCase("FAR"))
            {
                ltspreferenceproducts =  new ArrayList();
                ltspreferenceproducts.add(objPreferenceProductsDAO.getProspectiveCustomerPreferenceByProduct(objPriceRunCustomerVO.getPriceRunCustomerRefId(),5));
            }
            else
                ltspreferenceproducts = (List)objPreferenceProductsDAO.getAllPreferenceProducts(objPriceRunCustomerVO.getPriceRunCustomerRefId());

            List lstPreferenceTerms = (List)objPreferenceTermsDAO.getAllPreferenceTerms(objPriceRunCustomerVO.getPriceRunCustomerRefId());
            Iterator iteTerm = lstPreferenceTerms.iterator();
            if(objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId()!=null)
            while(iteTerm.hasNext())
            {
                float total$ = 0;
                float oHtotal$ = 0;
                float mcpeadder = 0;
                objPriceRunCustomerTermsVO = (PriceRunCustomerTermsVO)iteTerm.next();
                DealLeversDAO objDealLeversDAO = new DealLeversDAO();
                objHeatRateProduct = objPricingDAO.getHeatRateProduct(7900,objPriceRunCustomerTermsVO.getTerm(),objPriceRunCustomerVO.getPriceRunCustomerRefId(),strESIID);
                List deallevers =objDealLeversDAO.getDealLevers(objPriceRunCustomerVO.getPriceRunCustomerRefId(),objPriceRunCustomerTermsVO.getTerm());
                Iterator iter = deallevers.iterator();
                HashMap mapdealvalue =  new HashMap();
                while(iter.hasNext())
                {
                    DealLevers objdeallevers = (DealLevers)iter.next();
                    mapdealvalue.put(new Integer(objdeallevers.getDealLeverIdentifier()),new Double(objdeallevers.getValue()));
                }
                objPricingDashBoard = objPricingDAO.getDashBoardDetails(objPriceRunCustomerVO.getPriceRunCustomerRefId(),objPriceRunCustomerTermsVO.getTerm(),strESIID);
                float shapPrm = 0;
                shapPrm =  objPricingDashBoard.getShapingPremium()*(((float)((Double)mapdealvalue.get(new Integer(8))).doubleValue()+100)/100);
                HashMap hmCostCap = new HashMap();
                int esiidcount = 0; 
                double reduced = 0;
                double total$KWh =0;
                double oHtotal$KWh =0;
                double Cust =0.0;
                double addl =0.0;
                double Agnt =0.0;
                double agg =0.0;
                double bW =0.0;
                double other =0.0;
                double margin =0.0;
                double longterm = 0.0;
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
                
                if(hmValid.containsKey(new Integer(1)))
                    total$ += objPricingDashBoard.getEnergyCharge();
                if(hmValid.containsKey(new Integer(2)))
                    total$ += shapPrm;
                if(hmValid.containsKey(new Integer(3)))
                {
                    total$ += objPricingDashBoard.getVolatilityPremium();
                    mcpeadder += objPricingDashBoard.getVolatilityPremium();
                }
                if(hmValid.containsKey(new Integer(4)))
                {
                    total$ += objPricingDashBoard.getAncillaryServices();
                    mcpeadder += objPricingDashBoard.getAncillaryServices();
                }
                if(hmValid.containsKey(new Integer(5)))
                {
                    total$ += objPricingDashBoard.getIntraZonalCongestion();
                    mcpeadder += objPricingDashBoard.getIntraZonalCongestion();
                }
                if(hmValid.containsKey(new Integer(6)))
                {
                    total$ += objPricingDashBoard.getFeesAndRegulatory();
                    mcpeadder += objPricingDashBoard.getFeesAndRegulatory();
                }
                if(hmValid.containsKey(new Integer(13)))
                {
                    total$ +=totalCostCapital;
                }
                if(hmValid.containsKey(new Integer(7)))
                {
                    oHtotal$ = objPricingDashBoard.getCustomerCharge()*(float)((Double)mapdealvalue.get(new Integer(1))).doubleValue();
                    mcpeadder += objPricingDashBoard.getCustomerCharge()*(float)((Double)mapdealvalue.get(new Integer(1))).doubleValue();
                }
                if(hmValid.containsKey(new Integer(8)))
                    oHtotal$ += objPricingDashBoard.getAdditionalVolatilityPremium()*(float)((Double)mapdealvalue.get(new Integer(7))).doubleValue()/100;
                if(hmValid.containsKey(new Integer(9)))
                {
                    oHtotal$ +=objPricingDashBoard.getSalesAgentFee()*(float)((Double)mapdealvalue.get(new Integer(4))).doubleValue();
                    mcpeadder += objPricingDashBoard.getSalesAgentFee()*(float)((Double)mapdealvalue.get(new Integer(4))).doubleValue();
                }
                if(hmValid.containsKey(new Integer(10)))
                {
                    oHtotal$ +=objPricingDashBoard.getAggregatorFee()*(float)((Double)mapdealvalue.get(new Integer(5))).doubleValue();
                    mcpeadder += objPricingDashBoard.getAggregatorFee()*(float)((Double)mapdealvalue.get(new Integer(5))).doubleValue();
                }
                if(hmValid.containsKey(new Integer(11)))
                {
                    oHtotal$ +=objPricingDashBoard.getBandwidthCharge()*(float)((Double)mapdealvalue.get(new Integer(6))).doubleValue();
                    mcpeadder += objPricingDashBoard.getBandwidthCharge()*(float)((Double)mapdealvalue.get(new Integer(6))).doubleValue();
                }
                if(hmValid.containsKey(new Integer(12)))
                {
                    oHtotal$ +=objPricingDashBoard.getOtherFee()*(float)((Double)mapdealvalue.get(new Integer(2))).doubleValue();
                    mcpeadder += objPricingDashBoard.getOtherFee()*(float)((Double)mapdealvalue.get(new Integer(2))).doubleValue();
                }
                Iterator iteProducts = ltspreferenceproducts.iterator();
                mcpeadder = mcpeadder+(objPricingDashBoard.getMargin()*(float)((Double)mapdealvalue.get(new Integer(3))).doubleValue());
                while(iteProducts.hasNext())
                {
                    objPriceRunCustomerProductsVO = (PriceRunCustomerProductsVO)iteProducts.next();
                    if(objPriceRunCustomerProductsVO.getProduct().getProductIdentifier()!=5 || message.equalsIgnoreCase("FAR"))
                    {   
                        fptc=objPricingDashBoard.getMargin()*(float)((Double)mapdealvalue.get(new Integer(3))).doubleValue()+oHtotal$+total$;
                        float total = 0;
                        if(objPriceRunCustomerVO.isTaxExempt())
                            total = (fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100));
                        else
                            total = (fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100))*(1+(slt/100)+(ct/100));
                        taxAmount = total-fptc-objPricingDashBoard.getTdspCharges();
                        objContractsVO = new ContractsVO();
                        objContractsVO.setTaxes(taxAmount);
                        objContractsVO.setAggregatorFee(objPricingDashBoard.getAggregatorFee()*(float)((Double)mapdealvalue.get(new Integer(5))).doubleValue());
                        objContractsVO.setContractkWh(objPricingDashBoard.getContractkWh());
                        objContractsVO.setPriceRunCustomerRef(objPriceRunCustomerVO);
                        objContractsVO.setSalesCommision(objPricingDashBoard.getSalesAgentFee()*(float)((Double)mapdealvalue.get(new Integer(4))).doubleValue());
                        objContractsVO.setTdspCharges(objPricingDashBoard.getTdspCharges());
                        objContractsVO.setTerm(objPriceRunCustomerTermsVO.getTerm());
                        objContractsVO.setEsiIds(strESIID);
                        objProductsVO = new ProductsVO();
                        objProductsVO.setProductIdentifier(objPriceRunCustomerProductsVO.getProduct().getProductIdentifier());
                        objContractsVO.setProduct(objProductsVO);
                        
                        objContractsVO.setAnnualkWh(objPricingDashBoard.getAnnualkWh());
                        objContractsVO.setAnnualkW(objPricingDashBoard.getMaxDemandkW());
                        objContractsVO.setCustomerCharge(objPricingDashBoard.getCustomerCharge()*(float)((Double)mapdealvalue.get(new Integer(1))).doubleValue());
                        objContractsVO.setLoadFactor(objPricingDashBoard.getLoadFactorPercentage());
                        objContractsVO.setCityTax(gr);
                        objContractsVO.setCountyTax(ct);
                        objContractsVO.setStateTax(slt);
                        Date expDate = objContractsDAO.cpeExpiryDate(objPriceRunCustomerVO.getPriceRunRef().getPriceRunTime());
                        objContractsVO.setExpDate(expDate);
                        objContractsVO.setStartDate(new Date());
                        
                        UserDAO objUserDAO = new UserDAO();
                        UsersVO objUsersVO = objUserDAO.getUsers(objPriceRunCustomerVO.getProspectiveCustomer().getSalesRep().getUserId());
                        objContractsVO.setSalesRep(objUsersVO);
                        objContractsVO.setContractPrice$PerkWh((fptc+objPricingDashBoard.getTdspCharges())/objPricingDashBoard.getContractkWh());
                        if(objPriceRunCustomerVO.isTaxExempt())
                            objContractsVO.setTotalAnnualBill((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100)));
                        else
                            objContractsVO.setTotalAnnualBill((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100))*(1+(slt/100)+(ct/100)));
                        objContractsVO.setHeatRate(7900);
                        objContractsVO.setHeatRateAdder(objHeatRateProduct.getRetailAdder());
                        
                        
                        System.out.println("mcpe :" + mcpeadder);
                        objContractsVO.setMcpeAdder((mcpeadder)/objPricingDashBoard.getContractkWh());
                        switch (objPriceRunCustomerProductsVO.getProduct().getProductIdentifier()) {
                        case 1: 
                            objContractsVO.setFixedPrice$PerMWh(fptc*1000/objPricingDashBoard.getContractkWh());
                            result = objContractsDAO.addContract(objContractsVO);
                            break;
                        case 2:
                            objContractsVO.setFixedPrice$PerMWh(fptc*1000/objPricingDashBoard.getContractkWh());
                            result = objContractsDAO.addContract(objContractsVO);
                            break;
                        case 3:
                            objContractsVO.setFixedPrice$PerMWh(fptc*1000/objPricingDashBoard.getContractkWh());
                            result = objContractsDAO.addContract(objContractsVO);
                            break;
                        case 4:
                            objContractsVO.setFixedPrice$PerMWh(fptc*1000/objPricingDashBoard.getContractkWh());
                            result = objContractsDAO.addContract(objContractsVO);
                            break;
                        case 5: 
                            EPP objEPP = objPricingDAO.getEPP(baseGasPrice, objPriceRunCustomerTermsVO.getTerm(),objPriceRunCustomerVO.getPriceRunCustomerRefId(),strESIID);
                            float taxAmountFAF = 0;
                            if(objPriceRunCustomerVO.isTaxExempt())
                                taxAmountFAF = (((objEPP.getFixedPrice()/1000)*objPricingDashBoard.getContractkWh())+objPricingDashBoard.getTdspCharges())*((gr/100));
                            else
                                taxAmountFAF = (((objEPP.getFixedPrice()/1000)*objPricingDashBoard.getContractkWh())+objPricingDashBoard.getTdspCharges())*((gr/100)+(1+gr/100)*((slt+ct)/100));
                            objContractsVO.setFixedPrice$PerMWh(fptc*1000/objPricingDashBoard.getContractkWh());
                            objContractsVO.setAvgGasPrice(objEPP.getWeightedAvgGasPrice());
                            objContractsVO.setFuelFactor(objEPP.getFuelAdjustmentRate());
                            objContractsVO.setGasPrice$PerMMBtu(baseGasPrice);
                            objContractsVO.setBaseRate$PerMWh(objEPP.getBaseRate()*1000);
                            objContractsVO.setSalesCommision(objPricingDashBoard.getSalesAgentFee()*(float)((Double)mapdealvalue.get(new Integer(4))).doubleValue());
                            objContractsVO.setTaxes(taxAmountFAF);
                            objContractsVO.setComputedFAF(objEPP.getFafMultiplier());
                            objContractsVO.setMcpeAdder(0);
                            result = objContractsDAO.addContract(objContractsVO); 
                            break;
                        case 6:
                            if(objPriceRunCustomerVO.isTaxExempt())
                                objContractsVO.setFixedPrice$PerMWh(((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100)))/objPricingDashBoard.getContractkWh()*1000);
                            else
                                objContractsVO.setFixedPrice$PerMWh(((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100))*(1+(slt/100)+(ct/100)))/objPricingDashBoard.getContractkWh()*1000);
                            result = objContractsDAO.addContract(objContractsVO);
                            break;
                        case 7:
                            if(objPriceRunCustomerVO.isTaxExempt())
                                objContractsVO.setFixedPrice$PerMWh(((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100)))/objPricingDashBoard.getContractkWh()*1000);
                            else
                                objContractsVO.setFixedPrice$PerMWh(((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100))*(1+(slt/100)+(ct/100)))/objPricingDashBoard.getContractkWh()*1000);
                            result = objContractsDAO.addContract(objContractsVO);
                            break;
                        case 8:
                            objContractsVO.setFixedPrice$PerMWh(fptc*1000/objPricingDashBoard.getContractkWh());
                            result = objContractsDAO.addContract(objContractsVO);
                            break;
                        default:
                            break;
                        }
                    }
                } 
            }
            if(result)
            {
                resultMessage = "Success";
            }
            else if(!result&&objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId()==null)
            {
                resultMessage = "CPE/Contract cannot be created for this customer.";
            }
            else
                resultMessage = "Failed";
            
            return resultMessage;
        }
        catch (Exception e) {
            e.printStackTrace();
            return resultMessage;
        }
    }
}





/*
*$Log: LockAllDeal.java,v $
*Revision 1.3  2008/02/08 06:53:47  tannamalai
*last commit before table split up
*
*Revision 1.2  2008/02/06 06:42:45  tannamalai
*mcpe calculations corrected
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
*Revision 1.16  2007/07/19 13:46:08  jnadesan
*CPE/Contract is not created is there is no ESIID
*
*Revision 1.15  2007/07/18 06:39:28  jnadesan
*unwanted loop removed
*
*Revision 1.14  2007/07/18 04:55:02  jnadesan
*tax exception added
*
*Revision 1.13  2007/07/17 08:41:17  kduraisamy
*Tax computation calculation changed
*
*Revision 1.12  2007/07/13 14:01:52  jnadesan
*taxe computaion changed
*
*Revision 1.11  2007/07/13 06:26:15  jnadesan
*taxes computation changed
*
*Revision 1.10  2007/07/04 13:19:39  jnadesan
*lock  deal avoided if cms id is not available.
*
*Revision 1.9  2007/06/23 08:03:04  jnadesan
*updating startDate and ExpDate
*
*Revision 1.8  2007/06/16 05:40:43  kduraisamy
*hard coded 5 removed for base gas price.
*
*Revision 1.7  2007/06/05 11:03:29  jnadesan
*methods are changed to take preference products after price run
*
*Revision 1.6  2007/06/05 06:21:22  jnadesan
*pricerun customer id sent instead of customer id
*
*Revision 1.5  2007/06/01 12:10:11  jnadesan
*cumulative add problem solved
*
*Revision 1.4  2007/05/28 11:58:40  jnadesan
*bundle cost calculation changed
*
*Revision 1.3  2007/05/28 10:48:11  jnadesan
*new column are added
*
*Revision 1.2  2007/05/22 13:56:55  jnadesan
*lock all deal provision given in epp page also
*
*Revision 1.1  2007/05/16 09:35:23  jnadesan
*lock all deal option provided
*
*
*/