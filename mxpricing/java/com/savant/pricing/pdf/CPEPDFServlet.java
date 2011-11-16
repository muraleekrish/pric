/*
 * Created on Apr 11, 2007
 * 
 * Class Name CPEPDFServlet.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pdf;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import org.hibernate.Session;

import com.savant.pricing.calculation.dao.PricingDAO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO;
import com.savant.pricing.calculation.valueobjects.PriceRunHeaderVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.NumberUtil;
import com.savant.pricing.common.SortString;
import com.savant.pricing.common.chart.AnnualChartforPDF;
import com.savant.pricing.dao.ContractsDAO;
import com.savant.pricing.dao.CustEnergyComponentsDAO;
import com.savant.pricing.dao.MarketPriceDAO;
import com.savant.pricing.dao.PriceRunCustomerDAO;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.transferobjects.AnnualEnergyDetails;
import com.savant.pricing.transferobjects.PricingDashBoard;
import com.savant.pricing.valueobjects.ContractsVO;
import com.savant.pricing.valueobjects.MarketPriceVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CPEPDFServlet extends HttpServlet{
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
        ServletOutputStream servletoutputstream = null;
        try 
        {
            JasperReport jr = null;
            JRDataSource dataSource = null;
            int custId = 0;
            float diff = 0;
            String strTerm = request.getParameter("Term");
            int productId = Integer.parseInt(request.getParameter("ProductId"));
            String strCustId = request.getParameter("custIdvalue");
        	PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
    		ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
    		CustEnergyComponentsDAO objCustEnergyComponentsDAO = new CustEnergyComponentsDAO();
    		PriceRunCustomerVO objPriceRunCustomerVO = new PriceRunCustomerVO();
    		PricingDashBoard objPricingDashBoard = null;
    		PricingDashBoard objPricingDashBoardDetails = null;
    		objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(Integer.parseInt(strCustId));
    		custId = objPriceRunCustomerVO.getProspectiveCustomer().getProspectiveCustomerId();
    		HashMap hmRes = new HashMap();
    		HashMap hmValid = new HashMap();
    		HashMap hmNotValid = new HashMap();
            hmRes = objCustEnergyComponentsDAO.getEngCompo(custId);
    		hmValid = (HashMap)hmRes.get("valid");
    		hmNotValid = (HashMap)hmRes.get("notvalid");
    		String valid = "";
    		String invalid = "";
    		
    		for (Iterator it1=hmNotValid.entrySet().iterator(); it1.hasNext(); ) 
            {
                  Map.Entry entry1 = (Map.Entry) it1.next();
                  String value = entry1.getValue().toString();
                  if(productId==1)
                  {
	                  if(invalid.equalsIgnoreCase(""))
	                      invalid = value;
	                  else if(it1.hasNext())
	                      invalid = invalid+", "+value;
	                  else 
	                      invalid = invalid+" and "+value;
	              }
                  else if(productId==2)
                  {
                      if(invalid.equalsIgnoreCase("")&&!entry1.getKey().toString().equalsIgnoreCase("1")&&!entry1.getKey().toString().equalsIgnoreCase("2")&&!entry1.getKey().toString().equalsIgnoreCase("8")&&!entry1.getKey().toString().equalsIgnoreCase("13"))
	                      invalid = value;
	                  else if(it1.hasNext()&&!entry1.getKey().toString().equalsIgnoreCase("1")&&!entry1.getKey().toString().equalsIgnoreCase("2")&&!entry1.getKey().toString().equalsIgnoreCase("8")&&!entry1.getKey().toString().equalsIgnoreCase("13"))
	                      invalid = invalid+", "+value;
	                  else if(!entry1.getKey().toString().equalsIgnoreCase("1")&&!entry1.getKey().toString().equalsIgnoreCase("2")&&!entry1.getKey().toString().equalsIgnoreCase("8")&&!entry1.getKey().toString().equalsIgnoreCase("13"))
	                      invalid = invalid+" and "+value;
                  }
            }
    		for (Iterator it1=hmValid.entrySet().iterator(); it1.hasNext(); ) 
            {
                  Map.Entry entry1 = (Map.Entry) it1.next();
                  String value = entry1.getValue().toString();
                  if(productId==1)
                  {
	                  if(valid.equalsIgnoreCase(""))
	                      valid = value;
	                  else if(it1.hasNext())
	                      valid = valid+", "+value;
	                  else 
	                      valid = valid+" and "+value;
	              }
                  else if(productId==2)
                  {
                      if(valid.equalsIgnoreCase("")&&!entry1.getKey().toString().equalsIgnoreCase("1")&&!entry1.getKey().toString().equalsIgnoreCase("2")&&!entry1.getKey().toString().equalsIgnoreCase("8")&&!entry1.getKey().toString().equalsIgnoreCase("13"))
                          valid = value;
	                  else if(it1.hasNext()&&!entry1.getKey().toString().equalsIgnoreCase("1")&&!entry1.getKey().toString().equalsIgnoreCase("2")&&!entry1.getKey().toString().equalsIgnoreCase("8")&&!entry1.getKey().toString().equalsIgnoreCase("13"))
	                      valid = valid+", "+value;
	                  else if(!entry1.getKey().toString().equalsIgnoreCase("1")&&!entry1.getKey().toString().equalsIgnoreCase("2")&&!entry1.getKey().toString().equalsIgnoreCase("8")&&!entry1.getKey().toString().equalsIgnoreCase("13"))
	                      valid = valid+" and "+value;
                  }
            }
    		
    		  String terms="";
    		if(productId==1)
    		{
    		    if(hmNotValid.size()!=0)
    		        terms = "The contract energy price includes "+valid+". The contract energy price does not include taxes, "+invalid+ ".";
    		    else
    		        terms = "The contract energy price includes "+valid+". The contract energy price does not include taxes.";
    		}
    		if(productId==2)
    		{
    		    if(hmNotValid.size()!=0)
    		        terms = "The MCPE adder includes "+valid+". The MCPE adder does not include taxes, "+invalid+ ".";
    		    else
    		        terms = "The MCPE adder includes "+valid+". The MCPE adder does not include taxes.";
    		}
            SortString objSortString = new SortString();
            strTerm = objSortString.sortString(strTerm);
            File jasperFile = null;
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat sdfforFileName = new SimpleDateFormat("MMddyy"); 
            SimpleDateFormat contDate = new SimpleDateFormat("MMM yyyy"); 
            String urlStr[] = request.getRequestURL().toString().split(request.getContextPath());
            String filePath = urlStr[0]+ request.getContextPath();
            
            NumberFormat nf = NumberUtil.doubleFraction();
            NumberFormat tf = NumberUtil.tetraFraction();
            NumberFormat noDecimalFormat = NumberFormat.getInstance();
            noDecimalFormat.setMaximumFractionDigits(0);
            
            
            String message = request.getParameter("Message");
            HashMap hmParameters = new HashMap();
           
            hmParameters.put("terms",terms);
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
            {
                hmParameters.put("subRepPath","E:/pricingdata/jasper/");
            }
            else
            {
                hmParameters.put("subRepPath","E:/pricingdata/jasper/");
            }
            ContractsDAO objContractsDAO = new ContractsDAO();
            int custRunId = Integer.parseInt(strCustId);
            PricingDAO objPricingDAO = new PricingDAO();
            AnnualChartforPDF chart = new AnnualChartforPDF();
            PriceRunHeaderVO objPriceRunHeaderVO = objPriceRunCustomerDAO.getPriceRunCustomer(custRunId).getPriceRunRef();
            
            float avgGasPrice = objPriceRunHeaderVO.getGasPrice();
            ContractsVO objContractsVO = null;
            List lstAnnualDetails = new ArrayList();
            List vecmainDetails = new ArrayList();
            int term = 0;
            LinkedHashMap lhmTerm = new LinkedHashMap();
            LinkedHashMap hmTermCompare = new LinkedHashMap();
            StringTokenizer strToken = new StringTokenizer(strTerm,",");
            String productName = "";
            if(productId==1)
            {
                int indx = 0;
                HashMap lhmTermDetails = new HashMap();
                lhmTermDetails = this.addintoHahMap("Components","Energy (kWh)","Energy ($)","Energy ($/kWh)","TDSP ($)","TDSP ($/kWh)","Cust Charge ($)","Cust Charge ($/kWh)","Total ($)","Total ($/kWh)",lhmTermDetails);
                lhmTerm.put(new Integer(indx++),lhmTermDetails);
                while(strToken.hasMoreTokens())
                {
                    term = Integer.parseInt(strToken.nextToken());
                    objContractsVO = objContractsDAO.getCPE(custRunId,term,1);
                    lhmTermDetails = new HashMap();
                    lhmTermDetails = this.addintoHahMap(objContractsVO.getTerm()+"",noDecimalFormat.format(Math.round(objContractsVO.getContractkWh())),noDecimalFormat.format(Math.round((objContractsVO.getFixedPrice$PerMWh()/1000)*(objContractsVO.getContractkWh()))),tf.format(((objContractsVO.getFixedPrice$PerMWh()/1000)*(objContractsVO.getContractkWh()))/objContractsVO.getContractkWh())+" ",noDecimalFormat.format(Math.round(objContractsVO.getTdspCharges())),tf.format(objContractsVO.getTdspCharges()/objContractsVO.getContractkWh()),noDecimalFormat.format(Math.round(objContractsVO.getCustomerCharge())),tf.format((objContractsVO.getCustomerCharge()/objContractsVO.getContractkWh()))+" ",noDecimalFormat.format(Math.round((((objContractsVO.getFixedPrice$PerMWh()/1000)*(objContractsVO.getContractkWh()))+objContractsVO.getTdspCharges()+objContractsVO.getCustomerCharge()))),tf.format(((((objContractsVO.getFixedPrice$PerMWh()/1000)*(objContractsVO.getContractkWh()))+objContractsVO.getTdspCharges()+objContractsVO.getCustomerCharge())/objContractsVO.getContractkWh()))+" ",lhmTermDetails);
                    lhmTerm.put(new Integer(indx++),lhmTermDetails);
                    hmTermCompare.put(new Integer(term),new Float(objContractsVO.getFixedPrice$PerMWh()/1000));
                }
                productName = "Fixed Price";
            }
            else if(productId==2)
            { 
                int indx = 0;
                HashMap lhmTermDetails = new HashMap();
                lhmTermDetails = this.addintoHahMap("Components","Energy (kWh)","MCPE Adder ($)","MCPE Adder ($/kWh)","TDSP ($)","TDSP ($/kWh)","Cust Charge ($)","Cust Charge ($/kWh)","Total ($)","Total ($/kWh)",lhmTermDetails);
                lhmTerm.put(new Integer(indx++),lhmTermDetails);
                while(strToken.hasMoreTokens())
                {
                    term = Integer.parseInt(strToken.nextToken());
                    objContractsVO = objContractsDAO.getCPE(custRunId,term,2);
                    objPricingDashBoardDetails = objPricingDAO.getDashBoardDetails(custRunId,term,objContractsVO.getEsiIds());
                    diff = (objPricingDashBoardDetails.getEnergyCharge()-objPricingDashBoardDetails.getEnergyChargeWithOutLoss())/objPricingDashBoardDetails.getContractkWh();
                    lhmTermDetails = new HashMap();
                    lhmTermDetails = this.addintoHahMap(objContractsVO.getTerm()+"",noDecimalFormat.format(Math.round(objContractsVO.getContractkWh())),noDecimalFormat.format(Math.round((objContractsVO.getMcpeAdder()+diff)*(objContractsVO.getContractkWh()))),tf.format(objContractsVO.getMcpeAdder()+diff),noDecimalFormat.format(Math.round(objContractsVO.getTdspCharges())),tf.format(objContractsVO.getTdspCharges()/objContractsVO.getContractkWh()),noDecimalFormat.format(Math.round(objContractsVO.getCustomerCharge())),tf.format((objContractsVO.getCustomerCharge()/objContractsVO.getContractkWh()))+" ",noDecimalFormat.format(Math.round((objContractsVO.getMcpeAdder()+diff)*(objContractsVO.getContractkWh())+objContractsVO.getTdspCharges()+objContractsVO.getCustomerCharge())),tf.format(((Math.round(((objContractsVO.getMcpeAdder()+diff)*(objContractsVO.getContractkWh())+objContractsVO.getTdspCharges()+objContractsVO.getCustomerCharge())))/objContractsVO.getContractkWh()))+" ",lhmTermDetails);
                    lhmTerm.put(new Integer(indx++),lhmTermDetails);
                    hmTermCompare.put(new Integer(term),new Float(objContractsVO.getMcpeAdder()+diff));
                }
                productName = "MCPE";
            }
            
            List lst = objContractsDAO.getMXPricingCPE(lhmTerm);
            objPricingDashBoard = objPricingDAO.getDashBoardDetails(custRunId,term,objContractsVO.getEsiIds());
            Vector vecEngDet = objPricingDashBoard.getVecAnnualEnergyDetails();
            for(int i=0;i<vecEngDet.size();i++)
            {
                AnnualEnergyDetails objAnnualEnergyDetails = (AnnualEnergyDetails)vecEngDet.get(i);
                lstAnnualDetails.add(new AnnualSummaryDetails(objAnnualEnergyDetails.getUsagekWh(),objAnnualEnergyDetails.getMonth()+""));
            }
            chart.annualMultipleCpechart(request.getSession(),objPricingDashBoard.getVecAnnualEnergyDetails());
            chart.comparisionChart(request.getSession(),hmTermCompare);
            vecmainDetails.add(new CPEMultipleMainDetails(objPricingDashBoard.getCustomerName(),objPricingDashBoard.getLoadFactorPercentage(),objPricingDashBoard.getAnnualkWh(),objPricingDashBoard.getNoOfEsiIds(),avgGasPrice,objPricingDashBoard.getContractStartMonth(),objPriceRunHeaderVO.getFwdCurveDate(),objContractsVO.getPriceRunCustomerRef().getPriceRunRef().getGasPrice(),lst,lstAnnualDetails));
            CPEMultipleDataSource objCPEMultipleDataSource = new CPEMultipleDataSource(CPEMultipleMainDetails.class);
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
            {
                jasperFile = new File("E:/pricingdata/jasper/PriceQuoteMain.jasper");
            }
            else
            {
                jasperFile = new File("E:/pricingdata/jasper/PriceQuoteMain.jasper");
            }
            jr = (JasperReport)JRLoader.loadObject(jasperFile);	
            dataSource = objCPEMultipleDataSource.callcreate(jr,vecmainDetails);
            String cntStDate = "";
            hmParameters.put("cntrStartdate",objContractsVO.getStartDate());
            hmParameters.put("cntrStartdate",objContractsVO.getStartDate());
            hmParameters.put("expDate",objContractsVO.getExpDate());
            hmParameters.put("contractStrtDate",contDate.parse(contDate.format(objContractsVO.getPriceRunCustomerRef().getStartDate())));
            hmParameters.put("annualkWh", new Double(objPricingDashBoard.getAnnualkWh()));
            hmParameters.put("productName", productName);
            
            
            if(request.getSession().getAttribute("userName")!=null)
            {
                String menupath = request.getSession().getAttribute("home")!=null?(String)request.getSession().getAttribute("home"):" ";
            	String menuFtr[]= menupath.split("For");
            	if(menuFtr.length>0)
            	menupath = menupath.replaceFirst(menuFtr[1].trim(),(objContractsVO.getPriceRunCustomerRef().getProspectiveCustomer().getCustomerId())==null?"All Customers":objContractsVO.getPriceRunCustomerRef().getProspectiveCustomer().getCustomerId()+" - "+objContractsVO.getPriceRunCustomerRef().getProspectiveCustomer().getCustomerName());
            	request.getSession().removeAttribute("home");
            	request.getSession().setAttribute("home",menupath);
            }
            /*String salesComm = 0.0000+"";
            if(objContractsVO.getContractkWh()!=0)
                salesComm = (objContractsVO.getSalesCommision()/objContractsVO.getContractkWh())+"";
            String ss[] = salesComm.split("\\.");
            hmParameters.put("version",sdfforFileName.format(new Date())+"_"+ss[1]);*/
            byte[] bytes = JasperRunManager.runReportToPdf(jr,hmParameters,dataSource);
            SimpleDateFormat sdfname = new SimpleDateFormat("MMddyy");
            if(!message.equalsIgnoreCase("CPE2"))
            {
                if(productId==1)
                    response.setHeader("Content-Disposition", "attachment;filename=\"" + "CPE_"+ValidateString.checkMetaCharacters(objContractsVO.getPriceRunCustomerRef().getProspectiveCustomer().getCustomerName())+"_"+sdfname.format(new Date())+"_"+objContractsVO.getTerm()+"Months "+"Fixed Price"+".pdf" + "\";");
                else if(productId==2)
                    response.setHeader("Content-Disposition", "attachment;filename=\"" + "CPE_"+ValidateString.checkMetaCharacters(objContractsVO.getPriceRunCustomerRef().getProspectiveCustomer().getCustomerName())+"_"+sdfname.format(new Date())+"_"+objContractsVO.getTerm()+"Months "+"MCPE"+".pdf" + "\";");
                else 
                    response.setHeader("Content-Disposition", "attachment;filename=\"" + "CPE_"+ValidateString.checkMetaCharacters(objContractsVO.getPriceRunCustomerRef().getProspectiveCustomer().getCustomerName())+"_"+sdfname.format(new Date())+"_"+objContractsVO.getTerm()+"Months "+objContractsVO.getProduct().getProductName()+".pdf" + "\";");
            }
            else
              response.setHeader("Content-Disposition", "attachment;filename=\"" + "CPE_"+ValidateString.checkMetaCharacters(objContractsVO.getPriceRunCustomerRef().getProspectiveCustomer().getCustomerName())+"_"+sdfname.format(new Date())+"_"+objContractsVO.getTerm()+"Months Historical.pdf" + "\";");
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            servletoutputstream = response.getOutputStream();
            servletoutputstream.write(bytes, 0, bytes.length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(servletoutputstream != null)
            {
            servletoutputstream.flush();
            servletoutputstream.close();
        }
        }
         
    }
    private void importMarketPrice(Date marketDate) throws SQLException
    {
        CallableStatement cstmnt = null;
        Session objSession = null;
        try
        {
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            cstmnt = objSession.connection().prepareCall("{call Sp_Marketperiod_price (?)}");
            cstmnt.setDate(1, new java.sql.Date(marketDate.getTime()));
            cstmnt.execute();
            objSession.getTransaction().commit();
        }
        catch (Exception e) 
        {
            objSession.getTransaction().rollback();
            e.printStackTrace();
        }
        finally
        {
            cstmnt.close();
            objSession.close();
        }
    }
    private HashMap addintoHahMap(String term,String engkWh,String e$,String e$perKWh,String tdsp$,String tdsp$perkWh,String tax$,String tax$perkwh,String total$,String total$perkWh,HashMap lhmTermDetails)
    {
        lhmTermDetails.put("term", term);
        lhmTermDetails.put("Energy (kWh)", engkWh);
        lhmTermDetails.put("Energy ($)", e$);
        lhmTermDetails.put("Energy ($/kWh)", e$perKWh);
        lhmTermDetails.put("TDSP ($)", tdsp$);
        lhmTermDetails.put("TDSP ($/kWh)", tdsp$perkWh);
        lhmTermDetails.put("Taxes ($)", tax$);
        lhmTermDetails.put("Taxes ($/kWh)", tax$perkwh);
        lhmTermDetails.put("Total ($)", total$);
        lhmTermDetails.put("Total ($/kWh)", total$perkWh);
        return lhmTermDetails;
    }
    
    private List getHistoricalDetails(double maxBasenatGasPrice,double maxFuelFactor,double maxBaseRate)
    {
        MarketPriceDAO objMarketPriceDAO = new MarketPriceDAO();
        List historicalValues = new LinkedList();
        Iterator iteMarketPeriodVOs = objMarketPriceDAO.getAllMarketPrice().iterator();
        while(iteMarketPeriodVOs.hasNext())
        {
            MarketPriceVO objMarketPriceVO = (MarketPriceVO)iteMarketPeriodVOs.next();
            double fuelAdjRate = (objMarketPriceVO.getPrice()-maxBasenatGasPrice)*maxFuelFactor;
            historicalValues.add(new CPEFAR2HistoricalPriceDetails(objMarketPriceVO.getMarketPeriod(),objMarketPriceVO.getPrice(),fuelAdjRate,maxBaseRate+fuelAdjRate));
        }
        return historicalValues;
    } 
    
    private HashMap getHistoricalDetailsforWtAvg(double maxBasenatGasPrice,double maxFuelFactor,double maxBaseRate)
    {
        MarketPriceDAO objMarketPriceDAO = new MarketPriceDAO();
        HashMap historicalValues = new HashMap();
        Iterator iteMarketPeriodVOs = objMarketPriceDAO.getAllMarketPrice().iterator();
        while(iteMarketPeriodVOs.hasNext())
        {
            MarketPriceVO objMarketPriceVO = (MarketPriceVO)iteMarketPeriodVOs.next();
            double fuelAdjRate = (objMarketPriceVO.getPrice()-maxBasenatGasPrice)*maxFuelFactor;
            historicalValues.put(new Integer(objMarketPriceVO.getMarketPeriod().getMonth()),new CPEFAR2HistoricalPriceDetails(objMarketPriceVO.getMarketPeriod(),objMarketPriceVO.getPrice(),fuelAdjRate,maxBaseRate+fuelAdjRate));
        }
        return historicalValues;
    } 
    
    private double getWtAvgPrice(HashMap lsthistorialvalue,List lstAnnualUsage)
    {
        double totalAnnualUsage = 0.0;
        double totalEnergyRate = 0.0;
        double wtAvg =0.0;
        for(int i=0;i<lsthistorialvalue.size()&&i<lstAnnualUsage.size();i++)
        {
            AnnualEnergyDetails objAnnualEnergyDetails = (AnnualEnergyDetails)lstAnnualUsage.get(i);
            CPEFAR2HistoricalPriceDetails objCPEFAR2HistoricalPriceDetails = (CPEFAR2HistoricalPriceDetails)lsthistorialvalue.get(new Integer(i));
            totalAnnualUsage +=  objAnnualEnergyDetails.getUsagekWh();
            totalEnergyRate += objCPEFAR2HistoricalPriceDetails.getEnergyRate()*objAnnualEnergyDetails.getUsagekWh();
        }
        if(totalEnergyRate!=0)
        wtAvg = totalEnergyRate/totalAnnualUsage;
        return wtAvg;
    }
}

   


/*
*$Log: CPEPDFServlet.java,v $
*Revision 1.9  2008/12/03 10:55:10  tannamalai
*URL Updated Based on Remote Environment.
*
*Revision 1.8  2008/02/27 10:32:10  tannamalai
*enerygy diff added to mcpe
*
*Revision 1.7  2008/02/25 09:34:03  tannamalai
**** empty log message ***
*
*Revision 1.6  2008/02/25 09:32:27  tannamalai
*extra column added in prc_cost table to calculate energy charge without loss
*
*Revision 1.5  2008/02/08 06:53:47  tannamalai
*last commit before table split up
*
*Revision 1.4  2008/02/06 06:42:39  tannamalai
*mcpe calculations corrected
*
*Revision 1.3  2008/01/24 08:35:48  tannamalai
*pdf's format changed
*
*Revision 1.2  2008/01/23 08:35:13  tannamalai
*jasper reports changes
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.8  2007/11/28 05:43:12  jnadesan
*numberformat changed
*
*Revision 1.7  2007/11/27 07:29:38  srajan
*Compoents name changed
*
*Revision 1.6  2007/11/27 05:03:12  jnadesan
*index exception solved
*
*Revision 1.5  2007/11/26 18:11:09  jnadesan
*values are formatted
*
*Revision 1.4  2007/11/26 14:44:55  srajan
*Price quote enabled for MCPE
*
*Revision 1.3  2007/11/22 11:54:41  jnadesan
*CPE finished for fixed product
*
*Revision 1.2  2007/11/22 05:27:09  spandiyarajan
*PDF generation method changed
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.38  2007/10/23 05:39:06  spandiyarajan
*ComputedFAF used insted of fuelfactor
*
*Revision 1.37  2007/09/04 05:02:10  kduraisamy
*obj != null condition checking added.
*
*Revision 1.36  2007/08/24 13:34:11  kduraisamy
*instead of type casting new java.sqlDate() added.
*
*Revision 1.35  2007/08/23 07:37:58  jnadesan
*file name validated
*
*Revision 1.34  2007/08/13 11:19:49  spandiyarajan
*Customer name display error is fixed
*
*Revision 1.33  2007/08/03 11:30:51  jnadesan
*footer changed
*
*Revision 1.32  2007/07/31 11:40:17  kduraisamy
*objsession properly closed or calling procedure properly done.
*
*Revision 1.31  2007/07/30 12:43:27  jnadesan
*streams closed in finally
*
*Revision 1.30  2007/07/13 14:01:26  jnadesan
*filename changed
*
*Revision 1.29  2007/07/03 04:46:11  jnadesan
*checked for contract kwh
*
*Revision 1.28  2007/07/02 06:05:42  kduraisamy
*version added in CPE.
*
*Revision 1.27  2007/06/28 11:25:06  jnadesan
*version added
*
*Revision 1.26  2007/06/28 10:49:13  kduraisamy
*cpe file save format changed
*
*Revision 1.25  2007/06/25 05:33:14  jnadesan
*proposal strat date and exp modification enabled only for analyst
*
*Revision 1.24  2007/06/23 08:02:27  jnadesan
*updating startDate and ExpDate
*
*Revision 1.23  2007/06/20 11:23:14  jnadesan
*wt average computation changed
*
*Revision 1.22  2007/06/20 06:26:07  jnadesan
*weighted average computaion completed
*
*Revision 1.21  2007/06/18 10:00:31  kduraisamy
*indentation
*
*Revision 1.20  2007/06/16 07:11:40  spandiyarajan
**** empty log message ***
*
*Revision 1.19  2007/06/13 04:10:44  kduraisamy
*2005 jar included.
*
*Revision 1.18  2007/06/02 09:36:14  jnadesan
*unwanted line removed
*
*Revision 1.17  2007/06/01 05:43:31  spandiyarajan
*the pdf name chaged
*
*Revision 1.16  2007/05/28 15:06:58  jnadesan
*price divided by 1000 to get $/kwh
*
*Revision 1.15  2007/05/28 10:47:57  jnadesan
*column names are changed
*
*Revision 1.14  2007/05/22 13:56:02  jnadesan
*fafmultiplier changed
*
*Revision 1.13  2007/05/21 11:46:48  jnadesan
*procedure called for getting market price
*
*Revision 1.12  2007/05/21 06:03:05  jnadesan
*resources for cpe2 for FAR product
*
*Revision 1.11  2007/05/14 12:47:32  jnadesan
*expire date problem solved
*
*Revision 1.10  2007/05/14 10:21:11  jnadesan
*time format also provided for expire date
*
*Revision 1.9  2007/05/11 10:55:11  jnadesan
*all jasper files are added into web module
*
*Revision 1.8  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.7  2007/05/04 06:35:03  kduraisamy
*gas price added in runHeaderVO.
*
*Revision 1.6  2007/04/30 09:41:03  jnadesan
*fwd date stored in database
*
*Revision 1.5  2007/04/24 04:12:39  jnadesan
*average gas price included
*
*Revision 1.4  2007/04/19 10:13:05  kduraisamy
*natural order string added.
*
*Revision 1.3  2007/04/17 15:17:40  jnadesan
*CPE And Contrcat details for FAR
*
*Revision 1.2  2007/04/12 13:58:10  kduraisamy
*unwanted println commented.
*
*Revision 1.1  2007/04/12 09:03:21  jnadesan
*Contract PDF and Servlet pdf will be opened without new page
*
*
*/