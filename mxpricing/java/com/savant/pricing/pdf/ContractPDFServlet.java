/*
 * Created on Apr 12, 2007
 * 
 * Class Name ContractPDFServlet.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pdf;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import com.savant.pricing.calculation.dao.DealLeversDAO;
import com.savant.pricing.calculation.dao.PricingDAO;
import com.savant.pricing.calculation.dao.ReportsDAO;
import com.savant.pricing.calculation.valueobjects.CustomerPreferencesVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO;
import com.savant.pricing.calculation.valueobjects.PriceRunHeaderVO;
import com.savant.pricing.calculation.valueobjects.ReportsParamVO;
import com.savant.pricing.calculation.valueobjects.ReportsTemplateHeaderVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.NumberUtil;
import com.savant.pricing.dao.ContractsDAO;
import com.savant.pricing.dao.ContractsTrackingDAO;
import com.savant.pricing.dao.PICDAO;
import com.savant.pricing.dao.PriceRunCustomerDAO;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.transferobjects.DealLevers;
import com.savant.pricing.transferobjects.ESIIDDetails;
import com.savant.pricing.transferobjects.PricingDashBoard;
import com.savant.pricing.valueobjects.ContractsTrackingVO;
import com.savant.pricing.valueobjects.ContractsVO;
import com.savant.pricing.valueobjects.CustomerStatusVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractPDFServlet extends HttpServlet
{
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
            throws ServletException, IOException
    {
        super.doPost(arg0, arg1);
        this.doGet(arg0, arg1);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        ServletOutputStream servletoutputstream = null;
        try
        {
            JasperReport jr = null;
            File jasperFile = null;
            JRDataSource dataSource = null;
            int contractId = Integer.parseInt(request.getParameter("ContractId"));
            ContractsDAO objContractsDAO = new ContractsDAO();
            ContractsTrackingVO objContractsTrackingVO = null;
            String message = request.getParameter("message");
            int reportID  = 0;
            int productId = Integer.parseInt(request.getParameter("productId"));
            float price = Float.parseFloat(request.getParameter("price"));
            String reportCode = request.getParameter("template");
            ContractDataSource objds = null;
            ContractsVO objContractsVO = null;
            String contractTrackingId = "";
           
            PriceRunCustomerVO objPriceRunCustomerVO = null;
            PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
            HashMap parameters = new HashMap();
            ContractsTrackingDAO objContractsTrackingDAO = new ContractsTrackingDAO();
            ArrayList listCollection = new ArrayList();
            objContractsVO = objContractsDAO.getContractVO(contractId);
            if(message != null && message.equalsIgnoreCase("tracking"))
            {
                String trackingId = request.getParameter("trackingId");
                objContractsTrackingVO = objContractsTrackingDAO.getContracts(trackingId);
                contractTrackingId = trackingId;
                parameters.put("ExpDate", objContractsTrackingVO.getExpiryDate());
            } else
            {
                if(productId==1 && reportCode.equalsIgnoreCase("A"))
                {
                    reportID=1;
                }
                else if(productId==1 && reportCode.equalsIgnoreCase("B"))
                {
                    reportID=2;
                }
                else if(productId==2 && reportCode.equalsIgnoreCase("A"))
                {
                    reportID=3;
                }
                else if(productId==2 && reportCode.equalsIgnoreCase("B"))
                {
                    reportID=4;
                }
                objContractsTrackingVO = new ContractsTrackingVO();
                CustomerStatusVO objCustomerStatusVO = new CustomerStatusVO();
                objCustomerStatusVO.setCustomerStatusId(3);
                objContractsTrackingVO.setContractRef(objContractsVO);
                CustomerPreferencesVO objCustomerPreferencesVO = new ProspectiveCustomerDAO().getProspectiveCustomerPreferences(objContractsVO.getPriceRunCustomerRef().getProspectiveCustomer().getProspectiveCustomerId());
                Date contractEndDate = new Date(objCustomerPreferencesVO.getContractStartDate().getYear(), objCustomerPreferencesVO.getContractStartDate().getMonth() + objContractsVO.getTerm(), objCustomerPreferencesVO.getContractStartDate().getDate());
                objContractsTrackingVO.setContractStartDate(objCustomerPreferencesVO.getContractStartDate());
                objContractsTrackingVO.setContractEndDate(contractEndDate);
                parameters.put("ExpDate", objContractsVO.getExpDate());
                objContractsTrackingVO.setCustomerStatus(objCustomerStatusVO);
                objContractsTrackingVO.setExpiryDate(objContractsVO.getExpDate());
                objContractsTrackingVO.setProposalDate(objContractsVO.getStartDate());
                ReportsTemplateHeaderVO objReportsTemplateHeaderVO = new ReportsTemplateHeaderVO();
                objReportsTemplateHeaderVO.setReportIdentifier(reportID);
                objContractsTrackingVO.setReportIdentifier(objReportsTemplateHeaderVO); 
                objContractsTrackingVO.setReportCode(reportCode);
                contractTrackingId = objContractsTrackingDAO.addContract(objContractsTrackingVO);
            }
            parameters.put("contractTrackingId", contractTrackingId);
            int custRunId = objContractsVO.getPriceRunCustomerRef().getPriceRunCustomerRefId();
            PriceRunHeaderVO objPriceRunHeaderVO = objPriceRunCustomerDAO.getPriceRunCustomer(custRunId).getPriceRunRef();
            float avgGasPrice = objPriceRunHeaderVO.getGasPrice();
            productId = objContractsVO.getProduct().getProductIdentifier();
            objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(custRunId);
            if(reportCode.equalsIgnoreCase("B"))
                objds = new ContractDataSource(MxContractBDetails.class);
            else 
                objds = new ContractDataSource(MxContrcatADetails.class);
            switch (productId)
            {
                case 1:
                {
                    if(reportCode.equalsIgnoreCase("B"))
                    {
                        listCollection.add(this.fillContractDetails(productId, "Fixed Price Contract >50 kW", objContractsVO, objPriceRunCustomerVO,price));
                        if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
                        {
                            jasperFile = new File("E:/pricingdata/jasper/MxContractBMain.jasper");
                        }
                        else
                        {
                            jasperFile = new File("E:/pricingdata/jasper/MxContractBMain.jasper");
                        }
                    }
                    else if(reportCode.equalsIgnoreCase("A"))
                    {
                        listCollection.add(this.fillContractADetails(productId, "Fixed Price Contract <50 kW", objContractsVO, objPriceRunCustomerVO,price));
                        if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
                        {
                            jasperFile = new File("E:/pricingdata/jasper/MXContractAReport.jasper");
                        }
                        else
                        {
                            jasperFile = new File("E:/pricingdata/jasper/MXContractAReport.jasper");
                        }
                        
                    }
                    parameters.put("product", "Fixed");
                    break;
                }
                case 2:
                {
                    
                    if(reportCode.equalsIgnoreCase("B"))
                    {
                        listCollection.add(this.fillContractDetails(productId, "MCPE Contract >50 kW", objContractsVO, objPriceRunCustomerVO,price));
                        if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
                        {
                            jasperFile = new File("E:/pricingdata/jasper/MxContractBMain.jasper");
                        }
                        else
                        {
                            jasperFile = new File("E:/pricingdata/jasper/MxContractBMain.jasper");
                        }
                    }
                    else if(reportCode.equalsIgnoreCase("A"))
                    {
                        listCollection.add(this.fillContractADetails(productId, "MCPE Contract <50 kW", objContractsVO, objPriceRunCustomerVO,price));
                        if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
                        {
                            jasperFile = new File("E:/pricingdata/jasper/MXContractAReport.jasper");
                        }
                        else
                        {
                            jasperFile = new File("E:/pricingdata/jasper/MXContractAReport.jasper");
                        }
                    }
                    parameters.put("product", "MCPE");
                    break;
                }
                default:
                    break;
            }
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
            {
               parameters.put("subRepPath", "E:/pricingdata/jasper/");
            }
            else
            {
               parameters.put("subRepPath", "E:/pricingdata/jasper/");
            }
            jr = (JasperReport) JRLoader.loadObject(jasperFile);
            dataSource = objds.createDataSource(jr, listCollection);
            if(request.getSession().getAttribute("home") != null)
            {
                String menupath = request.getSession().getAttribute("home") != null ? (String) request.getSession().getAttribute("home") : " ";
                String menuFtr[] = menupath.split("For");
                menupath = menupath.replaceFirst(menuFtr[1].trim(), (objContractsVO.getPriceRunCustomerRef().getProspectiveCustomer().getCustomerId()) == null ? "All Customers" : objContractsVO.getPriceRunCustomerRef().getProspectiveCustomer().getCustomerId() + " - " + objContractsVO.getPriceRunCustomerRef().getProspectiveCustomer().getCustomerName());
                request.getSession().removeAttribute("home");
                request.getSession().setAttribute("home", menupath);
            }
            byte[] bytes = JasperRunManager.runReportToPdf(jr, parameters, dataSource);
            SimpleDateFormat sdfname = new SimpleDateFormat("MMddyy");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + "Contract_" + ValidateString.checkMetaCharacters(objContractsVO.getPriceRunCustomerRef().getProspectiveCustomer().getCustomerName()) + "_" + sdfname.format(new Date()) + "_" + objContractsVO.getTerm() + "Months " + objContractsVO.getProduct().getProductName() + ".pdf" + "\";");
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            servletoutputstream = response.getOutputStream();
            servletoutputstream.write(bytes, 0, bytes.length);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }
        finally
        {
            servletoutputstream.flush();
            servletoutputstream.close();
        }
    }

    private MxContractBDetails fillContractDetails(int productId, String reportTemplate, ContractsVO objContractsVO, PriceRunCustomerVO objPriceRunCustomerVO,float mcpe)
    {
        ArrayList esiIdReport = new ArrayList();
        HashMap mapdealvalue = null;
        MxCustomerDetails objMxCustomerDetails = null;
        List lstReportParam = new ArrayList();
        String custName = objPriceRunCustomerVO.getProspectiveCustomer().getCustomerDBA();
        ReportsDAO objReportsDAO = new ReportsDAO();
        ReportsTemplateHeaderVO objReportsTemplateHeaderVO = objReportsDAO.getParamValue(productId, reportTemplate);
        lstReportParam = (List) objReportsDAO.getAllReportParams(objReportsTemplateHeaderVO.getReportIdentifier());
        Iterator ite = lstReportParam.iterator();
        MxContractBDetails objMxContractBDetails = null;
    	DealLeversDAO objDealLeversDAO = new DealLeversDAO();
        List deallevers =objDealLeversDAO.getDealLevers(objContractsVO.getPriceRunCustomerRef().getPriceRunCustomerRefId(),objContractsVO.getTerm());
    	Iterator iter = deallevers.iterator();
    	mapdealvalue =  new HashMap();
    	while(iter.hasNext())
    	{
    		DealLevers objdeallevers = (DealLevers)iter.next();
    		mapdealvalue.put(new Integer(objdeallevers.getDealLeverIdentifier()),new Float(objdeallevers.getValue()));
    	}
        HashMap hm = new HashMap();
        while (ite.hasNext())
        {
            ReportsParamVO objReportsParamVO = (ReportsParamVO) ite.next();
            String paaramValue = objReportsDAO.getReportParamValue(objReportsParamVO.getReportParamIdentifier()).getReportParamValue();
            hm.put(objReportsParamVO.getReportParamName(), paaramValue);
        }
        objMxContractBDetails = this.fillContractText(hm);
        String strEsiId = objContractsVO.getEsiIds();
        PICDAO objPICDAO = new PICDAO();
        StringTokenizer stEsiid = new StringTokenizer(strEsiId, ",");
        while (stEsiid.hasMoreTokens())
        {
            String esiId = stEsiid.nextToken();
            ESIIDDetails objEsiIdDetails = objPICDAO.getESIIDInfo(esiId);
            esiIdReport.add(new ContractEsiidDetails(esiId, objEsiIdDetails.getServiceAddress(), objEsiIdDetails.getTdspName()));
        }
        String productName = "";
        NumberFormat nf = NumberUtil.doubleFraction();
        if(productId == 1)
        {
            productName = "Fixed Price";
            objMxContractBDetails.setProductName("Commercial Agreement FP-0507");
            objMxCustomerDetails = new MxCustomerDetails(objPriceRunCustomerVO.getProspectiveCustomer().getCustomerName(), objPriceRunCustomerVO.getProspectiveCustomer().getAddress(), objPriceRunCustomerVO.getProspectiveCustomer().getAddress(), objPriceRunCustomerVO.getProspectiveCustomer().getPocFirstName()+" "+objPriceRunCustomerVO.getProspectiveCustomer().getPocLastName(), objPriceRunCustomerVO.getProspectiveCustomer().getPhone(), objPriceRunCustomerVO.getProspectiveCustomer().getFax(), objPriceRunCustomerVO.getProspectiveCustomer().getEmail(), "", objContractsVO.getTerm() + "", nf.format(objContractsVO.getFixedPrice$PerMWh()* objContractsVO.getAnnualkWh()/ 5000), objContractsVO.getStartDate(), this.getMonthlyPriceText(objContractsVO.getFixedPrice$PerMWh() / 1000, productId), productName,mapdealvalue.get(new Integer(1)).toString());
        } else if(productId == 2)
        {
            productName = "MCPE Index Price w/option";
            objMxContractBDetails.setProductName("Commercial Agreement MCPE-0507");
            objMxCustomerDetails = new MxCustomerDetails(objPriceRunCustomerVO.getProspectiveCustomer().getCustomerName(), objPriceRunCustomerVO.getProspectiveCustomer().getAddress(), objPriceRunCustomerVO.getProspectiveCustomer().getAddress(), objPriceRunCustomerVO.getProspectiveCustomer().getPocFirstName()+" "+objPriceRunCustomerVO.getProspectiveCustomer().getPocLastName(), objPriceRunCustomerVO.getProspectiveCustomer().getPhone(), objPriceRunCustomerVO.getProspectiveCustomer().getFax(), objPriceRunCustomerVO.getProspectiveCustomer().getEmail(), "", objContractsVO.getTerm() + "", String.valueOf(mcpe), objContractsVO.getStartDate(), this.getMonthlyPriceText(mcpe, productId), productName,mapdealvalue.get(new Integer(1)).toString());
        }
        List customerDetails = new ArrayList();
        customerDetails.add(objMxCustomerDetails);
        objMxContractBDetails.setLstCustomerDetails(customerDetails);
        objMxContractBDetails.setLstEsiid(esiIdReport);
        return objMxContractBDetails;
    }

    private MxContractBDetails fillContractText(HashMap hm)
    {
        MxContractBDetails objMxContractBDetails = new MxContractBDetails();
        objMxContractBDetails.setService(hm.get("Service") == null ? null : hm.get("Service").toString());
        objMxContractBDetails.setTermandRenewal(hm.get("Term and Renewals") == null ? null : hm.get("Term and Renewals").toString());
        objMxContractBDetails.setPrice(hm.get("Price") == null ? null : hm.get("Price").toString());
        objMxContractBDetails.setPointOfDelivery(hm.get("Point of Delivery / Metering") == null ? null : hm.get("Point of Delivery / Metering").toString());
        objMxContractBDetails.setBillingPaymnet(hm.get("Billing and Payment") == null ? null : hm.get("Billing and Payment").toString());
        objMxContractBDetails.setCreditandDeposit(hm.get("Credit and Deposits") == null ? null : hm.get("Credit and Deposits").toString());
        objMxContractBDetails.setRefusalOfservice(hm.get("Refusal of Service") == null ? null : hm.get("Refusal of Service").toString());
        objMxContractBDetails.setMaterialChange(hm.get("Material Changes in Customer Usage") == null ? null : hm.get("Material Changes in Customer Usage").toString());
        objMxContractBDetails.setEarlyTermination(hm.get("Early Termination") == null ? null : hm.get("Early Termination").toString());
        objMxContractBDetails.setRegulatoryCharge(hm.get("Regulatory Change") == null ? null : hm.get("Regulatory Change").toString());
        objMxContractBDetails.setLimitationOfliability(hm.get("Limitations of Liability") == null ? null : hm.get("Limitations of Liability").toString());
        objMxContractBDetails.setRepresentationandWarranties(hm.get("Representations and Warranties") == null ? null : hm.get("Representations and Warranties").toString());
        objMxContractBDetails.setRequestToTDSP(hm.get("Request to TDSP") == null ? null : hm.get("Request to TDSP").toString());
        objMxContractBDetails.setGoveringLaw(hm.get("Governing Law") == null ? null : hm.get("Governing Law").toString());
        objMxContractBDetails.setEntireAgreement(hm.get("Entire Agreement") == null ? null : hm.get("Entire Agreement").toString());
        objMxContractBDetails.setResponsibilitiesofTDSP(hm.get("Responsibilities of the TDSP") == null ? null : hm.get("Responsibilities of the TDSP").toString());
        objMxContractBDetails.setTdspContacs(hm.get("TDSP Contacts") == null ? null : hm.get("TDSP Contacts").toString());
        objMxContractBDetails.setTdspAddress(hm.get("Address") == null ? null : hm.get("Address").toString());
        objMxContractBDetails.setForceMajeure(hm.get("Force Majeure") == null ? null : hm.get("Force Majeure").toString());
        return objMxContractBDetails;
    }

    private MxContrcatADetails fillContractADetails(int productId, String reportTemplate, ContractsVO objContractsVO, PriceRunCustomerVO objPriceRunCustomerVO,float mcpe)
    {
        ArrayList esiIdReport = new ArrayList();
        HashMap mapdealvalue=null;
        MxContrcatACustomerDetails objMxContrcatACustomerDetails = null;
        List lstReportParam = new ArrayList();
        String custName = objPriceRunCustomerVO.getProspectiveCustomer().getCustomerDBA();
        ReportsDAO objReportsDAO = new ReportsDAO();
        ReportsTemplateHeaderVO objReportsTemplateHeaderVO = objReportsDAO.getParamValue(productId, reportTemplate);
        lstReportParam = (List) objReportsDAO.getAllReportParams(objReportsTemplateHeaderVO.getReportIdentifier());
        Iterator ite = lstReportParam.iterator();
        DealLeversDAO objDealLeversDAO = new DealLeversDAO();
        List deallevers =objDealLeversDAO.getDealLevers(objContractsVO.getPriceRunCustomerRef().getPriceRunCustomerRefId(),objContractsVO.getTerm());
    	Iterator iter = deallevers.iterator();
    	mapdealvalue =  new HashMap();
    	while(iter.hasNext())
    	{
    		DealLevers objdeallevers = (DealLevers)iter.next();
    		mapdealvalue.put(new Integer(objdeallevers.getDealLeverIdentifier()),new Float(objdeallevers.getValue()));
    	}
        MxContrcatADetails objMxContrcatADetails = null;
        HashMap hm = new HashMap();
        while (ite.hasNext())
        {
            ReportsParamVO objReportsParamVO = (ReportsParamVO) ite.next();
            String paaramValue = objReportsDAO.getReportParamValue(objReportsParamVO.getReportParamIdentifier()).getReportParamValue();
            hm.put(objReportsParamVO.getReportParamName(), paaramValue);
        }
        hm.put("customercharge",mapdealvalue.get(new Integer(1)));
        objMxContrcatADetails = this.fillContractAText(hm);
        String strEsiId = objContractsVO.getEsiIds();
        PICDAO objPICDAO = new PICDAO();
        NumberFormat nf = NumberUtil.tetraFraction();
        StringTokenizer stEsiid = new StringTokenizer(strEsiId, ",");
        while (stEsiid.hasMoreTokens())
        {
            String esiId = stEsiid.nextToken();
            ESIIDDetails objEsiIdDetails = objPICDAO.getESIIDInfo(esiId);
            esiIdReport.add(new ContractEsiidDetails(esiId, objEsiIdDetails.getServiceAddress(), objEsiIdDetails.getTdspName()));
        }
        if(productId == 1)
        {
            objMxContrcatADetails.setPriceValue("The monthly price you pay to MXenergy during the term of this Agreement shall be $ "+ nf.format(objContractsVO.getFixedPrice$PerMWh()/1000)+" per kWh for electricity.  All TDSP charges, fees and taxes, if applicable, will be directly passed through to you.");
            objMxContrcatADetails.setProductName("FP0507");
            objMxContrcatACustomerDetails = new MxContrcatACustomerDetails(objPriceRunCustomerVO.getProspectiveCustomer().getCustomerName(),objPriceRunCustomerVO.getProspectiveCustomer().getAddress(),objPriceRunCustomerVO.getProspectiveCustomer().getAddress(),objPriceRunCustomerVO.getProspectiveCustomer().getCustomerDBA(),objPriceRunCustomerVO.getProspectiveCustomer().getPhone(),objPriceRunCustomerVO.getProspectiveCustomer().getFax(),objPriceRunCustomerVO.getProspectiveCustomer().getCity()+", "+objPriceRunCustomerVO.getProspectiveCustomer().getState()+", "+objPriceRunCustomerVO.getProspectiveCustomer().getZipCode(),objPriceRunCustomerVO.getProspectiveCustomer().getEmail(),objContractsVO.getTerm()+"", nf.format(objContractsVO.getFixedPrice$PerMWh() / 1000),objContractsVO.getStartDate(),mapdealvalue.get(new Integer(1)).toString());
        } else if(productId == 2)
        {
            objMxContrcatADetails.setPriceValue("The monthly price to be paid by Customer to MXenergy during the term of this Agreement shall equal the MCPE as reported by ERCOT (monthly weighted average MCPE based on ERCOT assigned Non-IDR profile and Congestion Zone for the facility) plus   $ "+nf.format(objContractsVO.getMcpeAdder())+" /kw-hr. All TDSP charges, non-recurring fees and taxes, if applicable, will be directly passed through to the Customer.");
            objMxContrcatADetails.setProductName("MCPE with Option 0507");
            objMxContrcatACustomerDetails = new MxContrcatACustomerDetails(objPriceRunCustomerVO.getProspectiveCustomer().getCustomerName(),objPriceRunCustomerVO.getProspectiveCustomer().getAddress(),objPriceRunCustomerVO.getProspectiveCustomer().getAddress(),objPriceRunCustomerVO.getProspectiveCustomer().getCustomerDBA(),objPriceRunCustomerVO.getProspectiveCustomer().getPhone(),objPriceRunCustomerVO.getProspectiveCustomer().getFax(),objPriceRunCustomerVO.getProspectiveCustomer().getCity()+", "+objPriceRunCustomerVO.getProspectiveCustomer().getState()+", "+objPriceRunCustomerVO.getProspectiveCustomer().getZipCode(),objPriceRunCustomerVO.getProspectiveCustomer().getEmail(),objContractsVO.getTerm()+"",String.valueOf(mcpe),objContractsVO.getStartDate(),mapdealvalue.get(new Integer(1)).toString());
        }
       
        List customerDetails = new ArrayList();
        customerDetails.add(objMxContrcatACustomerDetails);
        objMxContrcatADetails.setLstCustomerDetails(customerDetails);
        objMxContrcatADetails.setLstEsiid(esiIdReport);
        return objMxContrcatADetails;
    }

    private MxContrcatADetails fillContractAText(HashMap hm)
    {
        MxContrcatADetails objMxContrcatADetails = new MxContrcatADetails();
        objMxContrcatADetails.setTermsOfservice(hm.get("Terms of Service") == null ? null : hm.get("Terms of Service").toString());
        objMxContrcatADetails.setNatureOfService(hm.get("Nature of Service") == null ? null : hm.get("Nature of Service").toString());
        objMxContrcatADetails.setPricing(hm.get("Pricing") == null ? null : hm.get("Pricing").toString());
        objMxContrcatADetails.setNonRecuring(hm.get("Non-recurring fees") == null ? null : hm.get("Non-recurring fees").toString());
        objMxContrcatADetails.setTaxesAndFees(hm.get("Taxes and Fees") == null ? null : hm.get("Taxes and Fees").toString());
        objMxContrcatADetails.setOption(hm.get("Option") == null ? null : hm.get("Option").toString());
        objMxContrcatADetails.setApplicableAndpenality(hm.get("Applicable Fees and Penalties") == null ? null : hm.get("Applicable Fees and Penalties").toString());
        objMxContrcatADetails.setBillingandPayment(hm.get("Billing and Payment") == null ? null : hm.get("Billing and Payment").toString());
        objMxContrcatADetails.setLevelBillingplan(hm.get("Level Billing Plan") == null ? null : hm.get("Level Billing Plan").toString());
        objMxContrcatADetails.setBillPaymentandOtherAssistant(hm.get("Bill Payment or Other Assistance") == null ? null : hm.get("Bill Payment or Other Assistance").toString());
        objMxContrcatADetails.setLowIncomeDiscount(hm.get("Low Income Discounts") == null ? null : hm.get("Low Income Discounts").toString());
        objMxContrcatADetails.setCredit(hm.get("Credit") == null ? null : hm.get("Credit").toString());
        objMxContrcatADetails.setDeposit(hm.get("Deposits") == null ? null : hm.get("Deposits").toString());
        objMxContrcatADetails.setRefusalOfServive(hm.get("Refusal of Service") == null ? null : hm.get("Refusal of Service").toString());
        objMxContrcatADetails.setAggreeRenewal(hm.get("Agreement Term, Renewal, and Cancellation") == null ? null : hm.get("Agreement Term, Renewal, and Cancellation").toString());
        objMxContrcatADetails.setMaterialChanges(hm.get("Material Changes") == null ? null : hm.get("Material Changes").toString());
        objMxContrcatADetails.setCustInfo(hm.get("Customer Information") == null ? null : hm.get("Customer Information").toString());
        objMxContrcatADetails.setDiscrimination(hm.get("Discrimination") == null ? null : hm.get("Discrimination").toString());
        objMxContrcatADetails.setRightsCust(hm.get("Your Rights As A Customer") == null ? null : hm.get("Your Rights As A Customer").toString());
        objMxContrcatADetails.setAssignment(hm.get("Assignment") == null ? null : hm.get("Assignment").toString());
        objMxContrcatADetails.setChangeInLaw(hm.get("Change in Law or Regulation") == null ? null : hm.get("Change in Law or Regulation").toString());
        objMxContrcatADetails.setLimitationsOfliab(hm.get("Limitations of Liability") == null ? null : hm.get("Limitations of Liability").toString());
        objMxContrcatADetails.setRepresentationandWarranties(hm.get("Representations and Warranties") == null ? null : hm.get("Representations and Warranties").toString());
        objMxContrcatADetails.setRequestToTDSP(hm.get("Requests to TDSP") == null ? null : hm.get("Requests to TDSP").toString());
        objMxContrcatADetails.setGoveringLaw(hm.get("Governing Law") == null ? null : hm.get("Governing Law").toString());
        objMxContrcatADetails.setEntireAgreement(hm.get("Entire Agreement") == null ? null : hm.get("Entire Agreement").toString());
        objMxContrcatADetails.setTdspContacs(hm.get("TDSP Contacts") == null ? null : hm.get("TDSP Contacts").toString());
        objMxContrcatADetails.setTdspAddress(hm.get("Address") == null ? null : hm.get("Address").toString());
        objMxContrcatADetails.setForceMajeure(hm.get("Force Majeure") == null ? null : hm.get("Force Majeure").toString());
        objMxContrcatADetails.setMonthlySerChrge(hm.get("customercharge")== null ? null : hm.get("customercharge").toString());
        return objMxContrcatADetails;
    }

    private String getMonthlyPriceText(float price, int product)
    {
        System.out.println(" price :" + price);
        System.out.println(" product :" + product);
        NumberFormat tnf = NumberUtil.tetraFraction();
        String monthlyPrice = "";
        if(product == 1)
            monthlyPrice = "The fixed monthly price to be paid by Customer to MXenergy during the term of this Agreement shall be $" + tnf.format(price) + "per kWh.The price is for the energy charge only.  All TDSP charges and taxes, if applicable, will be directly passed through to the Customer.";
        else if(product == 2)
            monthlyPrice = "The monthly price to be paid by Customer to MXenergy during the term of this Agreement shall equal the MCPE as reported by ERCOT (monthly weighted average MCPE based on ERCOT assigned Non-IDR profile and Congestion Zone for the facility) plus $ " + tnf.format(price) + "/kw-hr. All TDSP charges, non-recurring fees and taxes, if applicable, will be directly passed through to the Customer.";
        return monthlyPrice;
    }
}
/*
 *$Log: ContractPDFServlet.java,v $
 *Revision 1.4  2008/12/03 10:55:10  tannamalai
 *URL Updated Based on Remote Environment.
 *
 *Revision 1.3  2008/02/27 10:32:10  tannamalai
 *enerygy diff added to mcpe
 *
 *Revision 1.2  2008/01/24 08:35:48  tannamalai
 *pdf's format changed
 *
 *Revision 1.1  2007/12/07 06:18:35  jvediyappan
 *initial commit.
 *
 *Revision 1.13  2007/11/27 09:59:52  srajan
 *Contact name added
 *
 *Revision 1.12  2007/11/27 09:53:22  jnadesan
 *price changed
 *
 *Revision 1.11  2007/11/27 09:07:41  jnadesan
 *report id checked
 *
 *Revision 1.10  2007/11/27 07:31:43  jnadesan
 *parameters added
 *
 *Revision 1.9  2007/11/27 03:42:13  jnadesan
 *unwanted variable removed
 *
 *Revision 1.8  2007/11/26 16:12:54  jvediyappan
 *field name corrected.
 *
 *Revision 1.7  2007/11/26 14:22:16  tannamalai
 **** empty log message ***
 *
 *Revision 1.6  2007/11/26 11:48:44  tannamalai
 *latest changes
 *
 *Revision 1.5  2007/11/24 06:13:45  tannamalai
 **** empty log message ***
 *
 *Revision 1.4  2007/11/23 14:32:27  jnadesan
 *template A designed
 *
 *Revision 1.3  2007/11/23 10:15:55  jnadesan
 *subreport added
 *
 *Revision 1.2  2007/11/23 08:43:56  jnadesan
 *contract designed
 *
 *Revision 1.1  2007/10/30 05:51:48  jnadesan
 *Initial commit.
 *
 *Revision 1.1  2007/10/26 15:19:22  jnadesan
 *initail MXEP commit
 *
 *Revision 1.28  2007/09/11 14:15:03  jnadesan
 *DBA added
 *
 *Revision 1.27  2007/09/11 13:33:03  jnadesan
 *DBA added with customer Name
 *
 *Revision 1.26  2007/09/11 12:32:51  spandiyarajan
 *cust dba added
 *
 *Revision 1.25  2007/08/27 09:04:38  jnadesan
 *Filename Validated
 *
 *Revision 1.24  2007/08/03 11:30:51  jnadesan
 *footer changed
 *
 *Revision 1.23  2007/07/30 12:43:27  jnadesan
 *streams closed in finally
 *
 *Revision 1.22  2007/07/14 07:50:52  kduraisamy
 *mcpeadder added
 *
 *Revision 1.21  2007/07/13 14:01:26  jnadesan
 *filename changed
 *
 *Revision 1.20  2007/07/05 12:42:05  jnadesan
 *zipcode validation removed
 *
 *Revision 1.19  2007/06/25 05:33:14  jnadesan
 *proposal strat date and exp modification enabled only for analyst
 *
 *Revision 1.18  2007/06/23 08:02:27  jnadesan
 *updating startDate and ExpDate
 *
 *Revision 1.17  2007/06/06 16:16:07  jnadesan
 *contract end Date changed
 *
 *Revision 1.16  2007/06/02 10:37:52  jnadesan
 *contrcat start and stop month changed
 *
 *Revision 1.15  2007/06/02 09:35:51  jnadesan
 *contract end date changed
 *
 *Revision 1.14  2007/06/01 05:43:31  spandiyarajan
 *the pdf name chaged
 *
 *Revision 1.13  2007/05/28 15:06:58  jnadesan
 *price divided by 1000 to get $/kwh
 *
 *Revision 1.12  2007/05/28 10:46:58  jnadesan
 *contract expiredate and start date are also stored
 *
 *Revision 1.11  2007/05/22 13:55:47  jnadesan
 *fafmultiplier changed
 *
 *Revision 1.10  2007/05/14 12:47:32  jnadesan
 *expire date problem solved
 *
 *Revision 1.9  2007/05/11 10:55:11  jnadesan
 *all jasper files are added into web module
 *
 *Revision 1.8  2007/05/05 11:05:07  jnadesan
 *bundle and unbundle contracts are finished
 *
 *Revision 1.7  2007/05/03 12:20:25  jnadesan
 *contract tracking id added as  parameter
 *
 *Revision 1.6  2007/05/02 11:38:34  jnadesan
 *all values are got from contract id
 *
 *Revision 1.5  2007/04/18 14:16:21  jnadesan
 *pdf generated for mcpe and res products
 *
 *Revision 1.4  2007/04/18 11:38:03  jnadesan
 *contract selection for other contracts
 *
 *Revision 1.3  2007/04/17 15:16:10  jnadesan
 *Contract for FAR
 *
 *Revision 1.2  2007/04/12 13:58:09  kduraisamy
 *unwanted println commented.
 *
 *Revision 1.1  2007/04/12 09:03:21  jnadesan
 *Contract PDF and Servlet pdf will be opened without new page
 *
 *
 */