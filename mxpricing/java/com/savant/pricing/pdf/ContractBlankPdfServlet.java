/*
 * Created on May 22, 2007
 * 
 * Class Name ContractBlankPdfServlet.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import com.savant.pricing.calculation.dao.PricingDAO;
import com.savant.pricing.calculation.dao.ReportsDAO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.dao.ContractsDAO;
import com.savant.pricing.dao.PICDAO;
import com.savant.pricing.dao.PriceRunCustomerDAO;
import com.savant.pricing.transferobjects.ESIIDDetails;
import com.savant.pricing.valueobjects.ContractsVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractBlankPdfServlet  extends HttpServlet{
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
        
        JasperReport jr = null;
        File jasperFile = null;
        JRDataSource dataSource = null;
        //String urlStr[] = request.getRequestURL().toString().split(request.getContextPath());
        //String filePath = urlStr[0]+ request.getContextPath();
        
        String custRunId = request.getParameter("pricerunCustId");
        int productId = Integer.parseInt(request.getParameter("productId"));
        HashMap parameters = new HashMap();
        PricingDAO objPricingDAO = new PricingDAO();
        HashMap hmCustRunEsiid = new HashMap();
        ArrayList esiIdReport = new ArrayList();
        ArrayList subreport = new ArrayList();
        ContractsDAO objContractsDAO = new ContractsDAO();
        ContractsVO objContractsVO = new ContractsVO();
        PICDAO objPICDAO = new PICDAO();
        ArrayList listCollection = new ArrayList();
        PriceRunCustomerVO objPriceRunCustomerVO = null;
        PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
        try
        { 
            ContractDataSource objds =null;
            objContractsVO = (ContractsVO)objContractsDAO.getCPEbyCustandProduct(Integer.parseInt(custRunId),productId).get(0);
          
            parameters.put("ExpDate",objContractsVO.getExpDate());
            
            hmCustRunEsiid = objPricingDAO.getAllEsiIds(Integer.parseInt(custRunId));
            objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(Integer.parseInt(custRunId));
            
            Set set = hmCustRunEsiid.keySet();
            Iterator it = set.iterator();
            while(it.hasNext())
            {
                String esiId = (String)it.next();
                ESIIDDetails objEsiIdDetails = objPICDAO.getESIIDInfo(esiId);
                esiIdReport.add(new ContractEsiidDetails(esiId,objEsiIdDetails.getServiceAddress(),objEsiIdDetails.getTdspName()));
            }
            ReportsDAO objReportsDAO = new ReportsDAO();
            subreport.add(new ContractSubReportDetails());
            String custName = "DBA: ";
            String dba = objPriceRunCustomerVO.getProspectiveCustomer().getCustomerDBA();
            if(dba!=null&&dba.trim().length()>0)
                custName+=objPriceRunCustomerVO.getProspectiveCustomer().getCustomerDBA();
            custName += "\n"+objPriceRunCustomerVO.getProspectiveCustomer().getCustomerName();
            switch (productId) {/*
            case 1:
            { 
                objds = new ContractDataSource(ContractSampleDetails.class);
                listCollection.add(new ContractSampleDetails(objReportsDAO.getParamValue(productId,"Address"),objReportsDAO.getParamValue(productId,"Pricing"),"",objReportsDAO.getParamValue(productId,"Term"),"",objReportsDAO.getParamValue(productId,"Billing Fees"),objReportsDAO.getParamValue(productId,"Billing Agreement"),objReportsDAO.getParamValue(productId,"Switch Authorization And Enrollment"),custName,objPriceRunCustomerVO.getProspectiveCustomer().getAddress(),objPriceRunCustomerVO.getProspectiveCustomer().getCity(),objPriceRunCustomerVO.getProspectiveCustomer().getState(),objPriceRunCustomerVO.getProspectiveCustomer().getZipCode(),0,0,0,0,subreport,esiIdReport));
                jasperFile = new File("E:/pricingdata/jasper/ContractSampleblank.jasper");
                break;
            }
            case 5:
            {
                objds = new ContractDataSource(ContractSampleDetails.class);
                listCollection.add(new ContractSampleDetails(objReportsDAO.getParamValue(productId,"Address"),objReportsDAO.getParamValue(productId,"Pricing"),objReportsDAO.getParamValue(productId,"Pricing Details"),objReportsDAO.getParamValue(productId,"Term"),objReportsDAO.getParamValue(productId,"Term Details"),objReportsDAO.getParamValue(productId,"Billing Fees"),objReportsDAO.getParamValue(productId,"Billing Agreement"),objReportsDAO.getParamValue(productId,"Switch Authorization And Enrollment"),custName,objPriceRunCustomerVO.getProspectiveCustomer().getAddress(),objPriceRunCustomerVO.getProspectiveCustomer().getCity(),objPriceRunCustomerVO.getProspectiveCustomer().getState(),objPriceRunCustomerVO.getProspectiveCustomer().getZipCode(),0,0,0,0,subreport,esiIdReport));
                jasperFile = new File("E:/pricingdata/jasper/ContractFARblank.jasper");
                break;
            }
            case 6:
            { 
                objds = new ContractDataSource(ContractSampleDetails.class);
                listCollection.add(new ContractSampleDetails(objReportsDAO.getParamValue(productId,"Address"),objReportsDAO.getParamValue(productId,"Pricing"),"",objReportsDAO.getParamValue(productId,"Term"),"",objReportsDAO.getParamValue(productId,"Billing Fees"),objReportsDAO.getParamValue(productId,"Billing Agreement"),objReportsDAO.getParamValue(productId,"Switch Authorization And Enrollment"),custName,objPriceRunCustomerVO.getProspectiveCustomer().getAddress(),objPriceRunCustomerVO.getProspectiveCustomer().getCity(),objPriceRunCustomerVO.getProspectiveCustomer().getState(),objPriceRunCustomerVO.getProspectiveCustomer().getZipCode(),0,0,0,0,subreport,esiIdReport));
                jasperFile = new File("E:/pricingdata/jasper/ContractResidentialblank.jasper");
                break;
            }
            case 2:
            { 
                objds = new ContractDataSource(ContractSampleDetails.class);
                listCollection.add(new ContractSampleDetails(objReportsDAO.getParamValue(productId,"Address"),objReportsDAO.getParamValue(productId,"Pricing"),objReportsDAO.getParamValue(productId,"Pricing Details"),objReportsDAO.getParamValue(productId,"Term"),"",objReportsDAO.getParamValue(productId,"Billing Fees"),objReportsDAO.getParamValue(productId,"Billing Agreement"),objReportsDAO.getParamValue(productId,"Switch Authorization And Enrollment"),custName,objPriceRunCustomerVO.getProspectiveCustomer().getAddress(),objPriceRunCustomerVO.getProspectiveCustomer().getCity(),objPriceRunCustomerVO.getProspectiveCustomer().getState(),objPriceRunCustomerVO.getProspectiveCustomer().getZipCode(),0,0,0,0,subreport,esiIdReport));
                jasperFile = new File("E:/pricingdata/jasper/ContractMCPEblank.jasper");
                break;
            }
            case 7:
            { 
                objds = new ContractDataSource(ContractBundleDetails.class);
                listCollection.add(new ContractBundleDetails(objReportsDAO.getParamValue(productId,"Address"),objReportsDAO.getParamValue(productId,"Pricing"),objReportsDAO.getParamValue(productId,"Term"),objReportsDAO.getParamValue(productId,"Billing Fees"),objReportsDAO.getParamValue(productId,"Switch Authorization And Enrollment"),objPriceRunCustomerVO.getProspectiveCustomer(),0,0,subreport,esiIdReport));
                jasperFile = new File("E:/pricingdata/jasper/ContractBundleblank.jasper");
                break;
            }
            case 8:
            { 
                objds = new ContractDataSource(ContractBundleDetails.class);
                listCollection.add(new ContractBundleDetails(objReportsDAO.getParamValue(productId,"Address"),objReportsDAO.getParamValue(productId,"Pricing"),objReportsDAO.getParamValue(productId,"Term"),objReportsDAO.getParamValue(productId,"Billing Fees"),objReportsDAO.getParamValue(productId,"Switch Authorization And Enrollment"),objPriceRunCustomerVO.getProspectiveCustomer(),0,0,subreport,esiIdReport));
                jasperFile = new File("E:/pricingdata/jasper/ContractUnBundleblank.jasper");
                break;
            }
            
            
            default:
                break;
            */}
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
            {
                parameters.put("subRepPath","E:/pricingdata/jasper/");
            }
            else
            {
                parameters.put("subRepPath","E:/pricingdata/jasper/");
            }
            
            jr = (JasperReport)JRLoader.loadObject(jasperFile);		            
		    dataSource = objds.createDataSource(jr,listCollection);
            
            byte[] bytes = JasperRunManager.runReportToPdf(jr,parameters, dataSource);
            response.setHeader("Content-Disposition", "attachment;filename=\"" +ValidateString.checkMetaCharacters(objPriceRunCustomerVO.getProspectiveCustomer().getCustomerName()) +"_Blank_Contract.pdf" + "\";");
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream servletoutputstream = response.getOutputStream();
            servletoutputstream.write(bytes, 0, bytes.length);
            servletoutputstream.flush();
            servletoutputstream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
}


/*
*$Log: ContractBlankPdfServlet.java,v $
*Revision 1.4  2009/01/27 05:47:57  tannamalai
*changes - according to MX server
*
*Revision 1.3  2008/12/03 10:55:10  tannamalai
*URL Updated Based on Remote Environment.
*
*Revision 1.2  2008/01/24 08:35:48  tannamalai
*pdf's format changed
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/26 10:49:50  srajan
*unwanted files are romoved
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.7  2007/09/11 14:15:03  jnadesan
*DBA added
*
*Revision 1.6  2007/09/11 13:33:03  jnadesan
*DBA added with customer Name
*
*Revision 1.5  2007/08/23 14:32:38  jnadesan
*fileName Validated
*
*Revision 1.4  2007/08/23 09:04:16  spandiyarajan
*removed unwanted imports
*
*Revision 1.3  2007/07/05 12:42:05  jnadesan
*zipcode validation removed
*
*Revision 1.2  2007/06/25 05:33:14  jnadesan
*proposal strat date and exp modification enabled only for analyst
*
*Revision 1.1  2007/06/23 08:02:46  jnadesan
*servlet for blankDocument
*
*
*/