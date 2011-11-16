/*
 * Created on Feb 13, 2006
 *
 * ClassName	:  	ProfileSelectionServlet.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India
 *
 */
package com.savant.pricing.presentation.pricing;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.calculation.dao.DealLeversDAO;
import com.savant.pricing.calculation.dao.PricingDAO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.NumberUtil;
import com.savant.pricing.common.chart.AnnualChart;
import com.savant.pricing.common.chart.ChargePieChart;
import com.savant.pricing.common.chart.SensitivityChart;
import com.savant.pricing.dao.CustEnergyComponentsDAO;
import com.savant.pricing.dao.PriceRunCostCapitalDAO;
import com.savant.pricing.dao.PriceRunCustomerDAO;
import com.savant.pricing.transferobjects.DealLevers;
import com.savant.pricing.transferobjects.PricingDashBoard;
import com.savant.pricing.valueobjects.TaxRatesVO;


/**
 * @author srajappan
 */
public class DashBoardServlet extends HttpServlet
{
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
        String priceRunId =   request.getParameter("priceRunId");
        PricingDAO objPricingDAO = new PricingDAO();
        PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
        PriceRunCustomerVO objPriceRunCustomerVO = null;
        CustEnergyComponentsDAO objCustEnergyComponentsDAO = new CustEnergyComponentsDAO();
        PriceRunCostCapitalDAO objPriceRunCostCapitalDAO = new PriceRunCostCapitalDAO();
        HashMap hmResult = new HashMap();
        HashMap hmValid = new HashMap();
        HashMap hmNotValid = new HashMap();
        DealLeversDAO objDealLeversDAO = new DealLeversDAO();
        PricingDashBoard objPricingDashBoard = null;
        NumberFormat dnf = NumberUtil.doubleFraction();
        NumberFormat tnf = NumberUtil.tetraFraction();
        NumberFormat df = NumberFormat.getIntegerInstance();
        AttractiveIndex objAttractiveIndex = new AttractiveIndex();
        String esiId =request.getParameter("esiId");
        AnnualChart objAnnualChart = new AnnualChart();
        HashMap hmCostCap = new HashMap();
        HashMap mapdealvalue =  new HashMap();
        String select="";
        String priceRunRefNo = request.getParameter("costcapital");
        int trm = Integer.parseInt(request.getParameter("Term"));
        
        objPricingDashBoard = objPricingDAO.getDashBoardDetails(Integer.parseInt(request.getParameter("priceRunId")),trm,request.getParameter("esiId"));
        int esiidcount = 0; 
        double reduced = 0;
        double total$ =0;
        double total$KWh =0;
        double oHtotal$ =0;
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
    	float diff = 0;
    	hmCostCap = objPriceRunCostCapitalDAO.getHmPrcCost(priceRunRefNo);
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
    	
    	supplierMargin =(objPricingDashBoard.getEnergyCharge()*(1+planlong)*((trm/2.0)+.5)*(maxmtm*(costlc/12)));
    	floatOnAr =(objPricingDashBoard.getAncillaryServices() + (objPricingDashBoard.getEnergyCharge()*(1+planlong)))*Interestrate*(arfloat/365.0);
    	floatOnTdsp =(objPricingDashBoard.getTdspCharges() * Interestrate * (tdspfloat/365.0));
    	isoCrdit =((objPricingDashBoard.getAncillaryServices()+(objPricingDashBoard.getEnergyCharge()*mcpe))*Interestrate*(isomargin/365.0));
    	totalCostCapital = supplierMargin+floatOnAr+floatOnTdsp+isoCrdit;
  
        HashMap pievalue = new HashMap();
        NumberFormat nf = new DecimalFormat("0.00000##");
        String term =  request.getParameter("Term");
        String message =request.getParameter("Save");
        try
        {
            if(term!=null && priceRunId != null)
            {
                if(esiId.equalsIgnoreCase(""))
                    esiId = getEsiidstr(getEsiidHashMap(request));
            }
            //selected esiid set into to session to get it from epp and heat rate computaion. 
            if(term != null && term.length()>0 && Integer.parseInt(term)==0)
                term = "1";
            objPricingDashBoard =  objPricingDAO.getDashBoardDetails(Integer.parseInt(priceRunId),Integer.parseInt(term), esiId);
            esiidcount = objPricingDashBoard.getNoOfEsiIds();
            objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(Integer.parseInt(priceRunId));
            hmResult = objCustEnergyComponentsDAO.getEngCompo(objPriceRunCustomerVO.getProspectiveCustomer().getProspectiveCustomerId());
            hmNotValid = (HashMap) hmResult.get("notvalid") ;
            hmValid = (HashMap)hmResult.get("valid");
            if(message.equalsIgnoreCase("true"))
            {
                Cust = Double.parseDouble(request.getParameter("Cust").trim().equalsIgnoreCase(".")?"0.0":request.getParameter("Cust"));
                addl = Double.parseDouble(request.getParameter("addl").equalsIgnoreCase(".")?"0.0":request.getParameter("addl"));
                Agnt = Double.parseDouble(request.getParameter("Agnt").equalsIgnoreCase(".")?"0.0":request.getParameter("Agnt"));
                agg = Double.parseDouble(request.getParameter("agg").equalsIgnoreCase(".")?"0.0":request.getParameter("agg"));
                bW = Double.parseDouble(request.getParameter("bW").equalsIgnoreCase(".")?"0.0":request.getParameter("bW"));
                other = Double.parseDouble(request.getParameter("other").equalsIgnoreCase(".")?"0.0":request.getParameter("other"));
                margin = Double.parseDouble(request.getParameter("margin").equalsIgnoreCase(".")?"0.0":request.getParameter("margin"));
                longterm = Double.parseDouble(request.getParameter("longterm").equalsIgnoreCase(".")?"0.0":request.getParameter("longterm"));
                
                
                Vector vecDealLevers = new Vector();
                DealLevers objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(1);
                objDealLevers.setValue((float)Cust);
                vecDealLevers.add(objDealLevers);
                
                objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(7);
                objDealLevers.setValue((float)addl);
                vecDealLevers.add(objDealLevers);
                
                objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(4);
                objDealLevers.setValue((float)Agnt);
                vecDealLevers.add(objDealLevers);
                
                objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(5);
                objDealLevers.setValue((float)agg);
                vecDealLevers.add(objDealLevers);
                
                objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(6);
                objDealLevers.setValue((float)bW);
                vecDealLevers.add(objDealLevers);
                
                objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(2);
                objDealLevers.setValue((float)other);
                vecDealLevers.add(objDealLevers);
                
                objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(3);
                objDealLevers.setValue((float)margin);
                vecDealLevers.add(objDealLevers);
                
                objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(8);
                objDealLevers.setValue((float)longterm);
                vecDealLevers.add(objDealLevers);
                objDealLeversDAO.saveDealLevers(Integer.parseInt(priceRunId),Integer.parseInt(term), vecDealLevers);
            }
            else
            {
                List deallevers =objDealLeversDAO.getDealLevers(Integer.parseInt(priceRunId),Integer.parseInt(term));
                Iterator iter = deallevers.iterator();
               
                while(iter.hasNext())
                {
                    DealLevers objdeallevers = (DealLevers)iter.next();
                    mapdealvalue.put(new Integer(objdeallevers.getDealLeverIdentifier()),new Double(objdeallevers.getValue()));
                }
                Cust = ((Double)mapdealvalue.get(new Integer(1))).doubleValue();
                addl =((Double)mapdealvalue.get(new Integer(7))).doubleValue();
                Agnt = ((Double)mapdealvalue.get(new Integer(4))).doubleValue();
                agg =((Double)mapdealvalue.get(new Integer(5))).doubleValue();
                bW = ((Double)mapdealvalue.get(new Integer(6))).doubleValue();
                other = ((Double)mapdealvalue.get(new Integer(2))).doubleValue();
                margin = ((Double)mapdealvalue.get(new Integer(3))).doubleValue();
                longterm = ((Double)mapdealvalue.get(new Integer(8))).doubleValue();
            } 
            if(BuildConfig.DMODE)
                System.out.println("Cust :"+Cust+" addl :"+addl +" Agnt :"+Agnt+" agg : "+agg+" margin"+margin+" bw"+bW+" other"+other);
            
            double contractkWh = objPricingDashBoard.getContractkWh();
            double shapPremium = objPricingDashBoard.getShapingPremium();
            shapPremium = objPricingDashBoard.getShapingPremium()*((longterm+100)/100);
            objPricingDashBoard.setShapingPremium((float) shapPremium);
            
            total$ += objPricingDashBoard.getEnergyCharge();
            total$ += objPricingDashBoard.getShapingPremium();
            total$ += objPricingDashBoard.getVolatilityPremium();
            total$ += objPricingDashBoard.getAncillaryServices();
            total$ += objPricingDashBoard.getIntraZonalCongestion();
            total$ += objPricingDashBoard.getFeesAndRegulatory();
            total$ += totalCostCapital;
            
            
            oHtotal$ = objPricingDashBoard.getCustomerCharge()*Cust;
            oHtotal$ += objPricingDashBoard.getAdditionalVolatilityPremium()*addl/100;
            oHtotal$ +=objPricingDashBoard.getSalesAgentFee()*Agnt;
            oHtotal$ +=objPricingDashBoard.getAggregatorFee()*agg;
            oHtotal$ +=objPricingDashBoard.getBandwidthCharge()*bW;
            oHtotal$ +=objPricingDashBoard.getOtherFee()*other;
           
            
           /* if(hmNotValid.containsKey(new Integer(1)))
                total$ =total$ - objPricingDashBoard.getEnergyCharge();
			if(hmNotValid.containsKey(new Integer(2)))
			    total$ =total$ -  objPricingDashBoard.getShapingPremium();
			if(hmNotValid.containsKey(new Integer(3)))
			    total$ =total$ -  objPricingDashBoard.getVolatilityPremium();
			if(hmNotValid.containsKey(new Integer(4)))
			    total$ =total$ -  objPricingDashBoard.getAncillaryServices();
			if(hmNotValid.containsKey(new Integer(5)))
			    total$ =total$ -  objPricingDashBoard.getIntraZonalCongestion();
			if(hmNotValid.containsKey(new Integer(6)))
			    total$ =total$ -  objPricingDashBoard.getFeesAndRegulatory();
			if(hmNotValid.containsKey(new Integer(13)))
			    total$ =total$ -  (double)totalCostCapital;
			
			
			if(hmNotValid.containsKey(new Integer(7)))
			    oHtotal$ =oHtotal$ -  (double)objPricingDashBoard.getCustomerCharge()*Cust;
			if(hmNotValid.containsKey(new Integer(8)))
			    oHtotal$ =oHtotal$ -   (double)objPricingDashBoard.getAdditionalVolatilityPremium()*(addl/100);
			if(hmNotValid.containsKey(new Integer(9)))
			    oHtotal$ =oHtotal$ -  (double)objPricingDashBoard.getSalesAgentFee()*Agnt;
			if(hmNotValid.containsKey(new Integer(10)))
			    oHtotal$ =oHtotal$ -  (double)objPricingDashBoard.getAggregatorFee()*agg;
			if(hmNotValid.containsKey(new Integer(11)))
			    oHtotal$ =oHtotal$ -  (double)objPricingDashBoard.getBandwidthCharge()*bW;
			if(hmNotValid.containsKey(new Integer(12)))
			    oHtotal$ =oHtotal$ -  (double)objPricingDashBoard.getOtherFee()*other;*/
		
          
            
            if(contractkWh!=0)
            {
                if(hmValid.containsKey(new Integer(1)))
                    reduced += (objPricingDashBoard.getEnergyCharge());
                if(hmValid.containsKey(new Integer(2)))
                    reduced += (objPricingDashBoard.getShapingPremium());
                if(hmValid.containsKey(new Integer(3)))
                    reduced += (objPricingDashBoard.getVolatilityPremium());
                if(hmValid.containsKey(new Integer(4)))
                    reduced += (objPricingDashBoard.getAncillaryServices());
                if(hmValid.containsKey(new Integer(5)))
                    reduced += (objPricingDashBoard.getIntraZonalCongestion());
                if(hmValid.containsKey(new Integer(6)))
                    reduced += (objPricingDashBoard.getFeesAndRegulatory());
                if(hmValid.containsKey(new Integer(13)))
                    reduced += (totalCostCapital);
                
                if(hmValid.containsKey(new Integer(7)))
                    reduced = (objPricingDashBoard.getCustomerCharge()*Cust);
                if(hmValid.containsKey(new Integer(8)))
                    reduced += (objPricingDashBoard.getAdditionalVolatilityPremium()*addl/(100));
                if(hmValid.containsKey(new Integer(9)))
                    reduced += (objPricingDashBoard.getSalesAgentFee()*Agnt);
                if(hmValid.containsKey(new Integer(10)))
                    reduced += (objPricingDashBoard.getAggregatorFee()*agg);
                if(hmValid.containsKey(new Integer(11)))
                    reduced += (objPricingDashBoard.getBandwidthCharge()*bW);
                if(hmValid.containsKey(new Integer(12)))
                    reduced += (objPricingDashBoard.getOtherFee()*other);
                
                diff = objPricingDashBoard.getEnergyCharge()-objPricingDashBoard.getEnergyChargeWithOutLoss();
                System.out.println("diff in servlet :" + diff);
            System.out.println("reduced"+reduced);
            System.out.println("hmValid"+hmValid.size());
                    total$KWh += (objPricingDashBoard.getEnergyCharge()/contractkWh);
                    total$KWh += (objPricingDashBoard.getShapingPremium()/contractkWh);
                    total$KWh += (objPricingDashBoard.getVolatilityPremium()/contractkWh);
                    total$KWh += (objPricingDashBoard.getAncillaryServices()/contractkWh);
                   	total$KWh += (objPricingDashBoard.getIntraZonalCongestion()/contractkWh);
                    total$KWh += (objPricingDashBoard.getFeesAndRegulatory()/contractkWh);
                    total$KWh += (totalCostCapital/contractkWh);
                
                    oHtotal$KWh = (objPricingDashBoard.getCustomerCharge()*Cust/contractkWh);
                    oHtotal$KWh += (objPricingDashBoard.getAdditionalVolatilityPremium()*addl/(100*contractkWh));
                    oHtotal$KWh += (objPricingDashBoard.getSalesAgentFee()*Agnt/contractkWh);
                    oHtotal$KWh += (objPricingDashBoard.getAggregatorFee()*agg/contractkWh);
                    oHtotal$KWh += (objPricingDashBoard.getBandwidthCharge()*bW/contractkWh);
                    oHtotal$KWh += (objPricingDashBoard.getOtherFee()*other/contractkWh);
            }
            
            double fptc=objPricingDashBoard.getMargin()*margin+oHtotal$+total$;
            
            double index = objAttractiveIndex.attaractiveIndex(contractkWh,objPricingDashBoard.getMargin()*margin,objPricingDashBoard.getMargin()*margin+oHtotal$+total$,objPricingDashBoard.getSalesAgentFee()*Agnt,objPricingDashBoard.getLoadFactorPercentage());
            String slogan = objAttractiveIndex.getSlogan(1-index);
            
            pievalue.put("sales",new Double (objPricingDashBoard.getSalesAgentFee()*Agnt));
            pievalue.put("margin",new Double (objPricingDashBoard.getMargin()*margin));
            pievalue.put("Fixed",new Double (objPricingDashBoard.getMargin()*margin+oHtotal$+total$));
            pievalue.put("TDSPCharge",new Double (objPricingDashBoard.getTdspCharges()));
            ChargePieChart piechart = new ChargePieChart();
            String filenamepieChart = piechart.chart(request.getSession(),pievalue,response.getWriter());
            String pieURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filenamepieChart;
            
            Vector chartDetails = objPricingDashBoard.getVecAnnualEnergyDetails();
            List lstsenValues = objPricingDAO.getSensitivityGraphDetails(Integer.parseInt(priceRunId.trim()),esiId.trim(),Integer.parseInt(term.trim()));
            int minIndex = objPricingDAO.getMinValue(lstsenValues);
            if(minIndex<=3)
                minIndex = 3;
            SensitivityChart senChart = new SensitivityChart();
            String fileNameSenChart = senChart.chart(request.getSession(),lstsenValues,response.getWriter());
            String senChartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + fileNameSenChart;

            String fileName = objAnnualChart.annualchart(request.getSession(),chartDetails,response.getWriter());
            String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + fileName;
            
            Collection objtaxcollection=new PricingDAO().getTaxRates();
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
            String taxAmount ="";
            if(objPriceRunCustomerVO.isTaxExempt())
             taxAmount = "<tr><td  colspan='2' class='tbldata_dashboard' height='19'>Bundled Cost W/Taxes</td><td class='tbldata_dashboard' align='right'>"+dnf.format((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100)))+"</td><td class='tbldata_dashboard' align='right'>"+(contractkWh==0&&((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100)))==0?"0.0":contractkWh==0?"-":tnf.format(((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100)))/contractkWh))+"</td></tr>";
            else
              taxAmount = "<tr><td  colspan='2' class='tbldata_dashboard' height='19'>Bundled Cost W/Taxes</td><td class='tbldata_dashboard' align='right'>"+dnf.format((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100))*(1+(slt/100)+(ct/100)))+"</td><td class='tbldata_dashboard' align='right'>"+(contractkWh==0&&((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100))*(1+(slt/100)+(ct/100)))==0?"0.0":contractkWh==0?"-":tnf.format((fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100))*(1+(slt/100)+(ct/100))/contractkWh))+"</td></tr>";
            
            select = "<table width='100%' border='0' cellpadding='0' cellspacing='0' style='border-bottom:1px solid #000; border-top:1px solid #000; border-right:1px solid #000; border-left:1px solid #000;' >" +
            "<tr><td height='18' colspan='2' align='center' class='tblheader'>Components</td><td width='70' class='tblheader' align='center'>$</td>" +
            "<td width='42' class='tblheader' align='center'>$/kWh</td></tr><tr><td height='19' colspan='4' class='tbltitlebold'>Energy Cost Components </td>"+
            "</tr><tr id='1'> <td  colspan='2' class='tbldata_dashboard' height='18'> Energy</td>" +
            "<td class='tbldata_dashboard' align='right'>" +dnf.format(objPricingDashBoard.getEnergyCharge())+"</td>" +
            "<td class='tbldata_dashboard' align='right'>"+(contractkWh==0&&objPricingDashBoard.getEnergyCharge()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getEnergyCharge()/contractkWh))+"</td>" +
            "</tr><tr id='2'><td  colspan='2' class='tbldata_dashboard' height='18'>Shaping Premium </td>"+
            "<td class='tbldata_dashboard' align='right'>"+ dnf.format(objPricingDashBoard.getShapingPremium())+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+ (contractkWh==0&&objPricingDashBoard.getShapingPremium()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getShapingPremium()/contractkWh))+"</td>"+
            "</tr><tr id='13'> <td  colspan='2' class='tbldata_dashboard' height='18'>Cost of Capital</td>" +
            "<td class='tbldata_dashboard' align='right'>" +dnf.format(totalCostCapital)+"</td>" +
            "<td class='tbldata_dashboard' align='right'>"+(contractkWh==0&&totalCostCapital==0?"0.0000":contractkWh==0?"-":tnf.format(totalCostCapital/contractkWh))+"</td>" +
            "</tr><tr id='3'><td height='18' colspan='2' class='tbldata_dashboard'>Volatility Premium</td>"+
            "<td class='tbldata_dashboard' align='right'>"+ dnf.format(objPricingDashBoard.getVolatilityPremium())+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+ (contractkWh==0&&objPricingDashBoard.getVolatilityPremium()==0?"0.0000":tnf.format(objPricingDashBoard.getVolatilityPremium()/contractkWh))+"</td>"+
            "</tr><tr id='4'><td height='18' colspan='2' class='tbldata_dashboard'>Ancillary Services </td>"+
            "<td class='tbldata_dashboard' align='right'>"+ dnf.format(objPricingDashBoard.getAncillaryServices())+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+ (contractkWh==0&&objPricingDashBoard.getAncillaryServices()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getAncillaryServices()/contractkWh))+"</td>"+
            "</tr><tr id='5'><td height='18' colspan='2' class='tbldata_dashboard'>Intrazonal Congestion </td>"+
            "<td class='tbldata_dashboard' align='right'>"+ dnf.format(objPricingDashBoard.getIntraZonalCongestion())+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+(contractkWh==0&&objPricingDashBoard.getIntraZonalCongestion()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getIntraZonalCongestion()/contractkWh))+"</td>"+
            "</tr><tr id='6'><td height='18' colspan='2' class='tbldata_dashboard'>Fees &amp; Regulatory </td>"+
            "<td class='tbldata_dashboard' align='right'>"+ dnf.format(objPricingDashBoard.getFeesAndRegulatory())+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+ (contractkWh==0&&objPricingDashBoard.getFeesAndRegulatory()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getFeesAndRegulatory()/contractkWh))+"</td>"+
            "</tr><tr class='tbltitlebold'><td height='19' colspan='2' class='tbldata_dashboard'>Sub-Total</td>"+
            "<td class='tbldata_dashboard' align='right'>"+dnf.format(total$)+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+tnf.format(total$KWh)+"</td>"+
            "</tr><tr><td height='19' colspan='4' class='tbltitlebold'>OH &amp; Deal Components </td>"+
            "</tr> <tr id='7'><td height='18' colspan='2' class='tbldata_dashboard'>Customer Charge </td>"+
            "<td class='tbldata_dashboard' align='right'>"+ dnf.format(objPricingDashBoard.getCustomerCharge()*Cust)+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+(contractkWh==0&&objPricingDashBoard.getCustomerCharge()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getCustomerCharge()*Cust/contractkWh))+"</td>"+
            "</tr><tr id='8'><td height='18' colspan='2' class='tbldata_dashboard'>Additional&nbsp;Volatility&nbsp;Premium </td>"+
            "<td class='tbldata_dashboard' align='right'>"+ dnf.format(objPricingDashBoard.getAdditionalVolatilityPremium()*addl/100)+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+ (contractkWh==0&&objPricingDashBoard.getAdditionalVolatilityPremium()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getAdditionalVolatilityPremium()*addl/(100*contractkWh)))+"</td>"+
            "</tr><tr id='9'><td height='18' colspan='2' class='tbldata_dashboard'>Sales Agent Fee </td>"+
            "<td class='tbldata_dashboard' align='right'>"+dnf.format(objPricingDashBoard.getSalesAgentFee()*Agnt)+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+(contractkWh==0&&objPricingDashBoard.getSalesAgentFee()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getSalesAgentFee()*Agnt/contractkWh))+"</td>"+
            "</tr><tr id='10'><td height='18' colspan='2' class='tbldata_dashboard'>Aggregator Fee </td>"+
            "<td class='tbldata_dashboard' align='right'>"+dnf.format(objPricingDashBoard.getAggregatorFee()*agg)+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+(contractkWh==0&&objPricingDashBoard.getAggregatorFee()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getAggregatorFee()*agg/contractkWh))+"</td>"+
            "</tr><tr id='11'><td height='18' colspan='2' class='tbldata_dashboard'>Bandwidth Charge </td>"+
            "<td class='tbldata_dashboard' align='right'>"+dnf.format(objPricingDashBoard.getBandwidthCharge()*bW)+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+(contractkWh==0&&objPricingDashBoard.getBandwidthCharge()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getBandwidthCharge()*bW/contractkWh))+"</td>"+
            " </tr><tr id='12'><td height='18' colspan='2' class='tbldata_dashboard'>Other Fee </td>"+
            " <td class='tbldata_dashboard' align='right'>"+ dnf.format(objPricingDashBoard.getOtherFee()*other)+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+ (contractkWh==0&&objPricingDashBoard.getOtherFee()==0?"0.0000":contractkWh==0?"-":tnf.format((objPricingDashBoard.getOtherFee()*other)/contractkWh))+"</td>"+
            "</tr><tr class='tbltitlebold'><td height='19' colspan='2' class='tbldata_dashboard'>Sub-Total</td>"+
            "<td class='tbldata_dashboard' align='right'>"+ dnf.format(oHtotal$)+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+ (contractkWh==0&&oHtotal$==0?"0.0000":contractkWh==0?"-":tnf.format(oHtotal$KWh))+"</td>"+
            "</tr><tr class='tbltitlebold'><td height='19' colspan='2' class='tbldata_dashboard'>Total Cost and Overhead</td>"+
            "<td class='tbldata_dashboard' align='right'>"+ dnf.format(oHtotal$+total$)+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+ (contractkWh==0&&oHtotal$==0?"0.0000":contractkWh==0?"-":tnf.format(oHtotal$KWh+total$KWh))+"</td>"+
            "</tr><tr class='tbltitlebold'><td height='19' colspan='2' class='tbldata_dashboard'>Margin</td>"+
            "<td class='tbldata_dashboard' align='right'>"+ dnf.format(objPricingDashBoard.getMargin()*margin)+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+ (contractkWh==0&&objPricingDashBoard.getMargin()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getMargin()*margin/contractkWh))+"</td>"+
            "</tr><tr id='fxdPrice' class='tbltitlebold'><td  colspan='2' class='tbldata_dashboard' height='19' style='color:#009900; font-weight: bold;'>Fixed Price to Customer</td>"+
            "<td class='tbldata_dashboard' style='color:#009900; font-weight: bold;' ><div align='right'>"+dnf.format(objPricingDashBoard.getMargin()*margin+oHtotal$+total$)+"</div></td>"+
            "<td class='tbldata_dashboard' style='color:#009900; font-weight: bold;' ><div align='right'>"+(contractkWh==0&&(objPricingDashBoard.getMargin()*margin+oHtotal$+total$)==0?"0.0":contractkWh==0?"-":tnf.format((objPricingDashBoard.getMargin()*margin+oHtotal$+total$)/contractkWh))+"</div></td>"+
            "</tr><tr class='tbltitlebold'><td  colspan='2' class='tbldata_dashboard' height='19' style='color:#009900; font-weight: bold;'>Retail Adder for MCPE </td>" +
            "<td class='tbldata_dashboard' style='color:#009900;font-weight: bold;'><div align='right'>"+dnf.format(fptc-totalCostCapital-(objPricingDashBoard.getAdditionalVolatilityPremium()*addl/100)-objPricingDashBoard.getShapingPremium()-objPricingDashBoard.getEnergyCharge()+diff)+"</div></td>" +
            "<td class='tbldata_dashboard' style='color:#009900;font-weight: bold;'><div align='right'>"+(contractkWh==0&&(fptc-totalCostCapital-(objPricingDashBoard.getAdditionalVolatilityPremium()*addl/100)-objPricingDashBoard.getShapingPremium()-objPricingDashBoard.getEnergyCharge()+diff)==0?"0.0000":contractkWh==0?"-":tnf.format((fptc-totalCostCapital-(objPricingDashBoard.getAdditionalVolatilityPremium()*addl/100)-objPricingDashBoard.getShapingPremium()-objPricingDashBoard.getEnergyCharge()+diff)/contractkWh))+"</div></td></tr>"+
            "<tr><td height='18' colspan='2' class='tbldata_dashboard'>TDSP Charges </td>"+
            "<td class='tbldata_dashboard' align='right'>"+ dnf.format(objPricingDashBoard.getTdspCharges())+"</td>"+
            "<td class='tbldata_dashboard' align='right'>"+ (contractkWh==0&&objPricingDashBoard.getTdspCharges()==0?"0.0000":contractkWh==0?"-":tnf.format(objPricingDashBoard.getTdspCharges()/contractkWh))+"</td>"+
            "</tr><tr class='tbltitlebold'><td  colspan='2' class='tbldata_dashboard' height='19'>Bundled Cost </td>"+
            "<td class='tbldata_dashboard' align='right'>"+dnf.format(fptc+objPricingDashBoard.getTdspCharges())+"</td>"+ 
            "<td class='tbldata_dashboard' align='right'>"+(contractkWh==0&&(fptc+objPricingDashBoard.getTdspCharges())==0?"0.0":contractkWh==0?"-":tnf.format((fptc+objPricingDashBoard.getTdspCharges())/contractkWh))+"</td></tr>"+
            taxAmount+ 
            "</table>"+"@@@"+df.format(objPricingDashBoard.getContractkWh())+"@@@"+
            "<img src="+ pieURL+"  border=0 usemap=#"+filenamepieChart + ">"+"@@@"+Cust+"@@@"+ addl +"@@@"+nf.format(Agnt)+"@@@"+nf.format(agg)+"@@@"+nf.format(bW)+
            "@@@"+nf.format(other)+"@@@"+nf.format(margin)+"@@@"+index+"@@@"+slogan+"@@@"+esiidcount+" of "+objPricingDAO.getAllEsiIds(Integer.parseInt(priceRunId)).size()+
            "@@@"+df.format(objPricingDashBoard.getAnnualkWh())+"@@@"+dnf.format(objPricingDashBoard.getMaxDemandkW())+"@@@"+dnf.format(objPricingDashBoard.getLoadFactorPercentage())+"@@@"+
            "<img src="+ graphURL+" border=0 usemap='#"+fileName+"'>"+"@@@"+objPricingDashBoard.getTdspCharges()+"@@@"+(fptc+objPricingDashBoard.getTdspCharges())*(1+(gr/100)*(objPriceRunCustomerVO.isTaxExempt()?1:(1+(slt/100)+(ct/100))))+"@@@"+
            ((objPricingDashBoard.getMargin()*margin)+reduced)+"@@@"+(objPricingDashBoard.getSalesAgentFee()*Agnt)+"@@@"+esiId+"@@@"+
            "<img src="+ senChartURL+"  border=0 usemap=#"+fileNameSenChart+ ">"+"@@@"+("Note: "+minIndex+" month term has the lowest price ("+dnf.format(((Double)lstsenValues.get(minIndex-1)).doubleValue()*100))
            +"@@@"+longterm+"@@@"+totalCostCapital;
            System.out.println("fixed :" + (objPricingDashBoard.getMargin()*margin+reduced) ); 
            System.out.println("fixed :" +totalCostCapital ); 
            response.setContentType("*/*");
            response.getWriter().write(select);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0L);
            System.gc();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block 
            e.printStackTrace();
        }
    }
    private HashMap getEsiidHashMap(HttpServletRequest request)
    {
        PricingDAO objPricingDAO = new PricingDAO();
        String tdsp = request.getParameter("TDSP");
        String zone = request.getParameter("Zone");
        String message = request.getParameter("Save");
        String custRefId = request.getParameter("priceRunId");
        HashMap mapEsiid = new HashMap();
        if(message.equalsIgnoreCase("Zone")||message.equalsIgnoreCase("true")||message.equalsIgnoreCase("month"))
        {
            if(zone.equalsIgnoreCase("0"))
            {
                mapEsiid = tdsp.equalsIgnoreCase("0")?objPricingDAO.getAllEsiIds(Integer.parseInt(custRefId)):objPricingDAO.getAllEsiIds(Integer.parseInt(custRefId),Integer.parseInt(tdsp));
            }
            else
            {
                mapEsiid = tdsp.equalsIgnoreCase("0")?objPricingDAO.getAllEsiIdsByCongestionZone(Integer.parseInt(custRefId),Integer.parseInt(zone)):objPricingDAO.getAllEsiIdsByTDSPCongestionZone(Integer.parseInt(custRefId),Integer.parseInt(tdsp),Integer.parseInt(zone));
            }
        }
        else if(message.equalsIgnoreCase("TDSP"))
        {
            mapEsiid = tdsp.equalsIgnoreCase("0")?objPricingDAO.getAllEsiIds(Integer.parseInt(custRefId)):objPricingDAO.getAllEsiIds(Integer.parseInt(custRefId),Integer.parseInt(tdsp));
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
