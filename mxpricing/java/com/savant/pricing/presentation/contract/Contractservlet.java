/*
 * Created on Mar 28, 2007
 * 
 * Class Name Contractservlet.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.contract;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.calculation.dao.DealLeversDAO;
import com.savant.pricing.calculation.dao.PricingDAO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerProductsVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO;
import com.savant.pricing.dao.ContractsDAO;
import com.savant.pricing.dao.CustEnergyComponentsDAO;
import com.savant.pricing.dao.PreferenceProductsDAO;
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
public class Contractservlet extends HttpServlet{
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
    throws ServletException, IOException {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String res = "Failure";
        boolean result = false;
        ContractsVO objContractsVO = new ContractsVO();
        ContractsDAO objContractsDAO = new ContractsDAO();
        PriceRunCustomerVO objPriceRunCustomerVO = new PriceRunCustomerVO();
        ProductsVO objProductsVO = new ProductsVO();
        PricingDAO objPricingDAO = new PricingDAO();
        PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
        PriceRunCustomerVO objPriceRunCustomerVOSecond = null;
        PreferenceProductsDAO objPreferenceProductsDAO = new PreferenceProductsDAO();
        CustEnergyComponentsDAO objCustEnergyComponentsDAO = new CustEnergyComponentsDAO();
        PriceRunCostCapitalDAO objPriceRunCostCapitalDAO = new PriceRunCostCapitalDAO();
        HashMap hmResult = new HashMap();
        HashMap hmValid = new HashMap();
        HashMap hmNotValid = new HashMap();
        PricingDashBoard objPricingDashBoard = null;
        int runId = Integer.parseInt(request.getParameter("priceRunId"));
        objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(runId);
        hmResult = objCustEnergyComponentsDAO.getEngCompo(objPriceRunCustomerVO.getProspectiveCustomer().getProspectiveCustomerId());
        hmNotValid = (HashMap) hmResult.get("notvalid") ;
        hmValid = (HashMap)hmResult.get("valid");
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
        try
        { 
            String esiId =request.getParameter("esiId");
            double totCostCapital = 0;
            float trmCostCapital = 0;
            if(request.getParameter("costcapital")!=null)
                totCostCapital = Double.parseDouble(request.getParameter("costcapital"));
            
            if(request.getParameter("trmCapital")!=null)
                trmCostCapital = Float.parseFloat(request.getParameter("trmCapital"));
            
            if(esiId.equalsIgnoreCase(""))
                esiId = getEsiidstr(getEsiidHashMap(request));
            if(request.getParameter("message").equalsIgnoreCase("allDeal"))
            {
                LockAllDeal objLockAllDeal = new LockAllDeal();
                res = objLockAllDeal.createPreferenceCPEContract(runId, esiId, 0, "All",totCostCapital);
            }
            else if(request.getParameter("message").equalsIgnoreCase("allDealEPP"))
            {
                float gasperMMBtu = Float.parseFloat(request.getParameter("gasperMMBtu"));
                LockAllDeal objLockAllDeal = new LockAllDeal();
                res = objLockAllDeal.createPreferenceCPEContract(runId, esiId, gasperMMBtu, "FAR",totCostCapital);
            }
            else
            {
                int term = Integer.parseInt(request.getParameter("Term"));
                HashMap mapdealvalue =  this.getDealLeverValues(runId,term);
                objHeatRateProduct = objPricingDAO.getHeatRateProduct(7900,term,runId,esiId);
                objPriceRunCustomerVOSecond = objPriceRunCustomerDAO.getPriceRunCustomer(runId);
                if(objPriceRunCustomerVOSecond.getProspectiveCustomer().getCustomerId()!=null)
                    if(request.getParameter("product").equalsIgnoreCase("fixed"))
                    {
                        objPricingDashBoard = objPricingDAO.getDashBoardDetails(runId,term,esiId);
                        float fptc = Float.parseFloat(request.getParameter("fixed"));
                        float mcpe = Float.parseFloat(request.getParameter("fixed"));
                        float shapPrm = 0;
                        shapPrm =  objPricingDashBoard.getShapingPremium()*(((float)((Double)mapdealvalue.get(new Integer(8))).doubleValue()+100)/100);
                         if(hmValid.containsKey(new Integer(13)))
                            mcpe = mcpe-trmCostCapital;
                        if(hmValid.containsKey(new Integer(1)))
                            mcpe = mcpe-objPricingDashBoard.getEnergyCharge();
                        if(hmValid.containsKey(new Integer(2)))
                            mcpe = mcpe-shapPrm;
                        if(hmValid.containsKey(new Integer(8)))
                            mcpe = mcpe-(objPricingDashBoard.getAdditionalVolatilityPremium()*(float)((Double)mapdealvalue.get(new Integer(7))).doubleValue()/100);

                        objPricingDashBoard = objPricingDAO.getDashBoardDetails(runId,term,esiId);
                        float total = 0;
                        if(objPriceRunCustomerVOSecond.isTaxExempt())
                            total = (fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100));
                        else
                            total = (fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100))*(1+(slt/100)+(ct/100)); 
                        float taxAmount = total-fptc-objPricingDashBoard.getTdspCharges();
                        List lstPreferenceProducts =   (List)objPreferenceProductsDAO.getAllPreferenceProducts(objPriceRunCustomerVOSecond.getPriceRunCustomerRefId());
                        Iterator iterProduct = lstPreferenceProducts.iterator();
                        while( iterProduct.hasNext()) 
                        {
                            objContractsVO = new ContractsVO();
                            objContractsVO.setAggregatorFee(objPricingDashBoard.getAggregatorFee()*(float)((Double)mapdealvalue.get(new Integer(5))).doubleValue());
                            objContractsVO.setContractkWh(objPricingDashBoard.getContractkWh());
                            objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(runId);
                            objContractsVO.setPriceRunCustomerRef(objPriceRunCustomerVO);
                            objContractsVO.setSalesCommision(objPricingDashBoard.getSalesAgentFee()*(float)((Double)mapdealvalue.get(new Integer(4))).doubleValue());
                            objContractsVO.setTaxes(taxAmount);
                            objContractsVO.setTdspCharges(objPricingDashBoard.getTdspCharges());
                            objContractsVO.setTerm(term);
                            objContractsVO.setEsiIds(esiId);
                            objContractsVO.setAnnualkWh(objPricingDashBoard.getAnnualkWh());
                            objContractsVO.setAnnualkW(objPricingDashBoard.getMaxDemandkW());
                            objContractsVO.setCustomerCharge(objPricingDashBoard.getCustomerCharge()*(float)((Double)mapdealvalue.get(new Integer(1))).doubleValue());
                            objContractsVO.setLoadFactor(objPricingDashBoard.getLoadFactorPercentage());
                            objContractsVO.setCityTax(gr);
                            objContractsVO.setCountyTax(ct);
                            Date expDate = objContractsDAO.cpeExpiryDate(objPriceRunCustomerVO.getPriceRunRef().getPriceRunTime());
                            objContractsVO.setExpDate(expDate);
                            objContractsVO.setStartDate(new Date());
                            objContractsVO.setStateTax(slt);
                            UserDAO objUserDAO = new UserDAO();
                            UsersVO objUsersVO = objUserDAO.getUsers(objPriceRunCustomerVOSecond.getProspectiveCustomer().getSalesRep().getUserId());
                            objContractsVO.setSalesRep(objUsersVO);
                            objContractsVO.setContractPrice$PerkWh((fptc+objPricingDashBoard.getTdspCharges())/objPricingDashBoard.getContractkWh());
                            if(objPriceRunCustomerVOSecond.isTaxExempt())
                                objContractsVO.setTotalAnnualBill((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100)));
                            else
                                objContractsVO.setTotalAnnualBill((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100))*(1+(slt/100)+(ct/100)));
                            objContractsVO.setHeatRate(7900);
                            objContractsVO.setHeatRateAdder(objHeatRateProduct.getRetailAdder());
                            objContractsVO.setMcpeAdder((mcpe)/objPricingDashBoard.getContractkWh());
                            
                            PriceRunCustomerProductsVO objProduct = (PriceRunCustomerProductsVO) iterProduct.next();
                            switch (objProduct.getProduct().getProductIdentifier()) {
                            case 1:
                                objProductsVO = new ProductsVO();
                                objProductsVO.setProductIdentifier(objProduct.getProduct().getProductIdentifier());
                                objContractsVO.setFixedPrice$PerMWh(fptc*1000/objPricingDashBoard.getContractkWh());
                                objContractsVO.setProduct(objProductsVO);
                                result = objContractsDAO.addContract(objContractsVO);
                                break;
                            case 2:
                                objProductsVO = new ProductsVO();
                                objProductsVO.setProductIdentifier(objProduct.getProduct().getProductIdentifier());
                                objContractsVO.setFixedPrice$PerMWh(fptc*1000/objPricingDashBoard.getContractkWh());
                                objContractsVO.setProduct(objProductsVO);
                                result = objContractsDAO.addContract(objContractsVO);
                                break;
                            case 3:
                                objProductsVO = new ProductsVO();
                                objProductsVO.setProductIdentifier(objProduct.getProduct().getProductIdentifier());
                                objContractsVO.setFixedPrice$PerMWh(fptc*1000/objPricingDashBoard.getContractkWh());
                                objContractsVO.setProduct(objProductsVO);
                                result = objContractsDAO.addContract(objContractsVO);
                                break;
                            case 4:
                                objProductsVO = new ProductsVO();
                                objProductsVO.setProductIdentifier(objProduct.getProduct().getProductIdentifier());
                                objContractsVO.setFixedPrice$PerMWh(fptc*1000/objPricingDashBoard.getContractkWh());
                                objContractsVO.setProduct(objProductsVO);
                                result = objContractsDAO.addContract(objContractsVO);
                                break;
                            case 6:
                                objProductsVO = new ProductsVO();
                                objProductsVO.setProductIdentifier(objProduct.getProduct().getProductIdentifier());
                                if(objPriceRunCustomerVOSecond.isTaxExempt())
                                    objContractsVO.setFixedPrice$PerMWh((fptc+objPricingDashBoard.getTdspCharges())* (1+(gr/100))*1000/objPricingDashBoard.getContractkWh());
                                else
                                    objContractsVO.setFixedPrice$PerMWh((fptc+objPricingDashBoard.getTdspCharges())* (1+(gr/100))*(1+(slt/100)+(ct/100))*1000/objPricingDashBoard.getContractkWh());
                                objContractsVO.setProduct(objProductsVO);
                                result = objContractsDAO.addContract(objContractsVO);
                                break;
                            case 7:
                                objProductsVO = new ProductsVO();
                                objProductsVO.setProductIdentifier(objProduct.getProduct().getProductIdentifier());
                                if(objPriceRunCustomerVOSecond.isTaxExempt())
                                    objContractsVO.setFixedPrice$PerMWh(((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100)))/objPricingDashBoard.getContractkWh()*1000);
                                else
                                    objContractsVO.setFixedPrice$PerMWh(((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100))*(1+(slt/100)+(ct/100)))/objPricingDashBoard.getContractkWh()*1000);
                                objContractsVO.setProduct(objProductsVO);
                                result = objContractsDAO.addContract(objContractsVO);
                                break;
                            case 8:
                                objProductsVO = new ProductsVO();
                                objProductsVO.setProductIdentifier(objProduct.getProduct().getProductIdentifier());
                                objContractsVO.setFixedPrice$PerMWh(fptc*1000/objPricingDashBoard.getContractkWh());
                                objContractsVO.setProduct(objProductsVO);
                                result = objContractsDAO.addContract(objContractsVO);
                                break;
                            case 10:
                                result = true;
                                break;
                            default:
                                break;
                            
                            }
                        }
                        if(result)
                            res = "Success";
                    }
                    else if(request.getParameter("product").equalsIgnoreCase("FAR"))
                    {
                        if(request.getParameter("esiId")!=null)
                        {
                            float gasperMMBtu = Float.parseFloat(request.getParameter("gasperMMBtu"));
                            objPricingDashBoard = objPricingDAO.getDashBoardDetails(runId,term,esiId);
                            EPP objEPP = objPricingDAO.getEPP(gasperMMBtu,term,runId,esiId);
                            float avp = (objPricingDashBoard.getAdditionalVolatilityPremium()*((Double)(mapdealvalue.get(new Integer(7)))).floatValue()/100);
                            float fptc = ((objEPP.getFixedPrice()*objPricingDashBoard.getContractkWh()/1000)+avp+objPricingDashBoard.getVolatilityPremium()/100);
                            objContractsVO.setFixedPrice$PerMWh(fptc*1000/objPricingDashBoard.getContractkWh());
                            float taxAmount = 0;
                            if(objPriceRunCustomerVOSecond.isTaxExempt())
                                taxAmount = ((objEPP.getFixedPrice()*objPricingDashBoard.getContractkWh()/1000)+objPricingDashBoard.getTdspCharges())*((gr/100));
                            else
                                taxAmount = ((objEPP.getFixedPrice()*objPricingDashBoard.getContractkWh()/1000)+objPricingDashBoard.getTdspCharges())*((gr/100)+(1+gr/100)*((slt+ct)/100));
                            
                            objContractsVO.setAggregatorFee(objPricingDashBoard.getAggregatorFee()*(float)((Double)mapdealvalue.get(new Integer(5))).doubleValue());
                            objContractsVO.setAvgGasPrice(objEPP.getWeightedAvgGasPrice());
                            objContractsVO.setFuelFactor(objEPP.getFuelAdjustmentRate());
                            objContractsVO.setGasPrice$PerMMBtu(gasperMMBtu);
                            objContractsVO.setContractkWh(objPricingDashBoard.getContractkWh());
                            objContractsVO.setBaseRate$PerMWh(objEPP.getBaseRate()*1000);
                            
                            objPriceRunCustomerVO.setPriceRunCustomerRefId(runId);
                            objContractsVO.setPriceRunCustomerRef(objPriceRunCustomerVO);
                            objProductsVO.setProductIdentifier(5);
                            objContractsVO.setProduct(objProductsVO);
                            objContractsVO.setSalesCommision(objPricingDashBoard.getSalesAgentFee()*(float)((Double)mapdealvalue.get(new Integer(4))).doubleValue());
                            objContractsVO.setTaxes(taxAmount);
                            objContractsVO.setTdspCharges(objPricingDashBoard.getTdspCharges());
                            objContractsVO.setTerm(term);
                            objContractsVO.setEsiIds(esiId);
                            objContractsVO.setComputedFAF(objEPP.getFafMultiplier());
                            
                            objContractsVO.setAnnualkWh(objPricingDashBoard.getAnnualkWh());
                            objContractsVO.setAnnualkW(objPricingDashBoard.getMaxDemandkW());
                            objContractsVO.setCustomerCharge(objPricingDashBoard.getCustomerCharge()*(float)((Double)mapdealvalue.get(new Integer(1))).doubleValue());
                            objContractsVO.setLoadFactor(objPricingDashBoard.getLoadFactorPercentage());
                            objContractsVO.setCityTax(gr);
                            Date expDate = objContractsDAO.cpeExpiryDate(objPriceRunCustomerVOSecond.getPriceRunRef().getPriceRunTime());
                            objContractsVO.setExpDate(expDate);
                            objContractsVO.setStartDate(new Date());
                            objContractsVO.setCountyTax(ct);
                            objContractsVO.setStateTax(slt);
                            UserDAO objUserDAO = new UserDAO();
                            UsersVO objUsersVO = objUserDAO.getUsers(objPriceRunCustomerVOSecond.getProspectiveCustomer().getSalesRep().getUserId());
                            objContractsVO.setSalesRep(objUsersVO);
                            objContractsVO.setContractPrice$PerkWh((fptc+objPricingDashBoard.getTdspCharges())/objPricingDashBoard.getContractkWh());
                            if(objPriceRunCustomerVOSecond.isTaxExempt())
                                objContractsVO.setTotalAnnualBill((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100)));
                            else
                                objContractsVO.setTotalAnnualBill((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100))*(1+(slt/100)+(ct/100)));
                            objContractsVO.setHeatRate(7900);
                            objContractsVO.setHeatRateAdder(objHeatRateProduct.getRetailAdder());
                            result = objContractsDAO.addContract(objContractsVO); 
                        }
                        else
                        {
                            res = "Failure";
                        }
                        if(result)
                            res = "Success";
                    }
                if(res.equalsIgnoreCase("Failure")&&objPriceRunCustomerVOSecond.getProspectiveCustomer().getCustomerId()==null)
                {
                    res = "CPE/Contract cannot be created for this customer.";
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("*/*");
        response.getWriter().write(res);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        System.gc();
    }
    private HashMap getDealLeverValues(int runId,int term)
    {
        DealLeversDAO objDealLeversDAO = new DealLeversDAO();
        List deallevers =objDealLeversDAO.getDealLevers(runId,term);
        Iterator iter = deallevers.iterator();
        HashMap hmDealValue = new HashMap();
        while(iter.hasNext())
        {
            DealLevers objdeallevers = (DealLevers)iter.next();
            hmDealValue.put(new Integer(objdeallevers.getDealLeverIdentifier()),new Double(objdeallevers.getValue()));
        }
        return hmDealValue;
    }
    private HashMap getEsiidHashMap(HttpServletRequest request)
    {
        PricingDAO objPricingDAO = new PricingDAO();
        String tdsp = request.getParameter("TDSP");
        String zone = request.getParameter("Zone");
        String custRefId = request.getParameter("priceRunId");
        HashMap mapEsiid = new HashMap();
        if(zone.equalsIgnoreCase("0"))
        {
            mapEsiid = tdsp.equalsIgnoreCase("0")?objPricingDAO.getAllEsiIds(Integer.parseInt(custRefId)):objPricingDAO.getAllEsiIds(Integer.parseInt(custRefId),Integer.parseInt(tdsp));
        }
        else
        {
            mapEsiid = tdsp.equalsIgnoreCase("0")?objPricingDAO.getAllEsiIdsByCongestionZone(Integer.parseInt(custRefId),Integer.parseInt(zone)):objPricingDAO.getAllEsiIdsByTDSPCongestionZone(Integer.parseInt(custRefId),Integer.parseInt(tdsp),Integer.parseInt(zone));
        }
        
        return mapEsiid;
    }
    private String getEsiidstr(HashMap hmEsiid)
    {
        String esiId = "";
        Set set = hmEsiid.keySet();
        Iterator it = set.iterator();
        while(it.hasNext())
        {
            Object key = it.next();
            if(esiId.length()>1)
                esiId+=",";
            esiId += (String)hmEsiid.get(key);
        }
        return esiId;
    }
}


/*
*$Log: Contractservlet.java,v $
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
*Revision 1.31  2007/07/23 11:06:31  spandiyarajan
*removed unwanted imports
*
*Revision 1.30  2007/07/19 13:46:08  jnadesan
*CPE/Contract is not created is there is no ESIID
*
*Revision 1.29  2007/07/18 04:55:02  jnadesan
*tax exception added
*
*Revision 1.28  2007/07/17 08:41:17  kduraisamy
*Tax computation calculation changed
*
*Revision 1.27  2007/07/13 14:01:52  jnadesan
*taxe computaion changed
*
*Revision 1.26  2007/07/04 13:19:38  jnadesan
*lock  deal avoided if cms id is not available.
*
*Revision 1.25  2007/06/25 07:41:36  jnadesan
*proposal strat date and exp modification enabled only for analyst
*
*Revision 1.24  2007/06/23 08:03:04  jnadesan
*updating startDate and ExpDate
*
*Revision 1.23  2007/06/16 05:40:43  kduraisamy
*hard coded 5 removed for base gas price.
*
*Revision 1.22  2007/06/13 09:07:33  spandiyarajan
*added calc part for heatrate product(10)
*
*Revision 1.21  2007/06/12 12:57:53  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.20  2007/06/07 09:33:04  kduraisamy
*multiple mail for a run added.
*
*Revision 1.19  2007/06/05 11:03:29  jnadesan
*methods are changed to take preference products after price run
*
*Revision 1.18  2007/06/04 12:14:44  jnadesan
*MCPE Adder added
*
*Revision 1.17  2007/06/01 12:10:29  jnadesan
*cumulative add problem solved
*
*Revision 1.16  2007/05/28 11:58:40  jnadesan
*bundle cost calculation changed
*
*Revision 1.15  2007/05/28 10:48:11  jnadesan
*new column are added
*
*Revision 1.14  2007/05/22 13:56:29  jnadesan
*lock all deal provision given in epp page also
*
*Revision 1.13  2007/05/21 11:47:07  jnadesan
*request parameter changed
*
*Revision 1.12  2007/05/16 09:35:23  jnadesan
*lock all deal option provided
*
*Revision 1.11  2007/05/09 11:31:25  jnadesan
*all values will be taken frm request
*
*Revision 1.10  2007/04/19 13:23:48  jnadesan
*agg problem sloved
*
*Revision 1.9  2007/04/18 14:16:33  jnadesan
*pdf generated for mcpe and res products
*
*Revision 1.8  2007/04/17 15:17:53  jnadesan
*entry for contract FAR
*
*Revision 1.7  2007/04/16 13:24:02  jnadesan
*Contract servlet changed
*
*Revision 1.6  2007/04/03 07:02:49  jnadesan
*contract add and update modified
*
*Revision 1.5  2007/04/02 16:28:46  jnadesan
*Contract page
*
*Revision 1.4  2007/04/02 14:55:11  jnadesan
*fixedprice$perKwh changed
*
*Revision 1.3  2007/04/02 14:16:35  jnadesan
*wrong commitment solved
*
*Revision 1.1  2007/03/29 06:39:13  jnadesan
*Contact page added
*
*
*/