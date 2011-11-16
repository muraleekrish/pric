/*
 * 
 * XPressPricingViewResultAction.java    Aug 24, 2007
 * 
 * Copyright (c) Savant Technologies Pvt Ltd.
 * 'Savant House' 127 Chamiers Road, Nandanam, Chennai-600 035. INDIA 
 * All rights reserved.

 * @author: jnadesan
 * @company: Savant Technologies
 * @client: MX Energy
 * @version: 
 * @Description: 
 * 
*/

package com.savant.pricing.presentation.matrix;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.matrixpricing.dao.MMCustomersPDFDAO;
import com.savant.pricing.matrixpricing.dao.MMPriceRunStatusDAO;
import com.savant.pricing.matrixpricing.pdf.ExpressPricingDetails;
import com.savant.pricing.matrixpricing.pdf.ExpressPricingReportDataSource;
import com.savant.pricing.matrixpricing.pdf.TermDetails;
import com.savant.pricing.matrixpricing.valueobjects.MMPriceRunHeaderVO;
import com.savant.pricing.matrixpricing.valueobjects.MMPricingPDFVO;
import com.savant.pricing.securityadmin.valueobject.UsersVO;

/**
 * 
 */
public class XPressPricingViewResultAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action="failure";
        boolean result = false;
        ActionErrors actionErrors = new ActionErrors();
        
        if(form instanceof XPressPricingViewResultForm)
        {
            XPressPricingViewResultForm frm = (XPressPricingViewResultForm)form;
            java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("##,####.00");
            MMCustomersPDFDAO objMMCustomersPDFDAO = new MMCustomersPDFDAO();
            MMPriceRunStatusDAO objMMPriceRunStatusDAO = new MMPriceRunStatusDAO();
            MMPricingPDFVO objMMPricingPDFVO = new MMPricingPDFVO();
            MMPriceRunHeaderVO objMMPriceRunHeaderVO = new MMPriceRunHeaderVO();
            MMPriceRunHeaderVO objMMPriceRunHeaderVO1 = new MMPriceRunHeaderVO();
            UsersVO objUsersVO = new UsersVO();
            
            JasperReport jr = null;
            JRDataSource dataSource = null;
            String urlStr[] = request.getRequestURL().toString().split(request.getContextPath());
            String filePath = urlStr[0]+ request.getContextPath();
            /*URL url = null;
            URLConnection urlConn = null;*/
            InputStream  inStream = null;
            File jasperFile = null;
            byte[] bytes = null;
            if(frm.getFormAction().equalsIgnoreCase("makepdf"))
            {
                objMMPricingPDFVO.setRefNo(frm.getReferenceNo());
                String priceRunId = request.getParameter("priceRunId");
                objMMPriceRunHeaderVO.setPriceRunRefNo(priceRunId);
                objMMPricingPDFVO.setPriceRunRefNo(objMMPriceRunHeaderVO);
                objMMPricingPDFVO.setCustName(frm.getCustomerName());
                objUsersVO.setUserId(frm.getCmbSalesRep());
                objMMPricingPDFVO.setSalesRep(objUsersVO);
                objUsersVO = new UsersVO();   
                objUsersVO.setUserId(frm.getSalesManager());
                objMMPricingPDFVO.setSalesManager(objUsersVO);
                objMMPricingPDFVO.setCreatedBy((String)request.getSession().getAttribute("userName"));
                objMMPricingPDFVO.setCreatedDate(new Date());
                
                HashMap parameters = new HashMap();
                ArrayList termDetails = new ArrayList();
                ArrayList listCollection = new ArrayList();
                try
                { 
                    ExpressPricingReportDataSource objds =null;
                    objds = new ExpressPricingReportDataSource(ExpressPricingDetails.class);
                    if(BuildConfig.DMODE)
                        System.out.println("Jasper Path:"+filePath+"/jsp/matrix/jasper/");
                    objMMPriceRunHeaderVO1 = objMMPriceRunStatusDAO.getRunresultDetails(priceRunId);
                    String strMntKva = request.getParameter("monthlykva")==null?"Monthly kW":request.getParameter("monthlykva");
                    parameters.put("monthlykva", strMntKva.equalsIgnoreCase("Monthly kW")?"Monthly Demand kW":"Monthly Demand kVA");
                    if(objMMPriceRunHeaderVO1!=null)
                    {
                        parameters.put("offerDate", objMMPriceRunHeaderVO1!=null?objMMPriceRunHeaderVO1.getOfferDate():null);
                        parameters.put("expDate", objMMPriceRunHeaderVO1!=null?objMMPriceRunHeaderVO1.getExpiredate():null);
                        parameters.put("tireagleLogoPath","E:/pricingdata/jasper/");
                        String 	payHist = "";
                        if(frm.getPaymenthistory().trim().equals("1"))
                            payHist = "Yes";
                        else
                            payHist = "No";
                        
                        String termValues = request.getParameter("termStr");
                        if(termValues.trim().length()>0)
                        {
                            TermDetails objTermDetails = null;
                            String strTermDet1[] = null;
                            String strTermDet2[] = termValues.split("\\&\\&\\&");
                            int term=0; String energyPrice=""; String tdspCharge=""; String compPrice=""; String savDol=""; String savPercent="";
                            
                            for(int i=0; i<strTermDet2.length; i++)
                            {
                                strTermDet1 = strTermDet2[i].split("\\@\\#\\$");
                                term = strTermDet1[0].trim().equals("")?0:Integer.parseInt(strTermDet1[0]);
                                energyPrice = strTermDet1[1].trim();
                                tdspCharge = strTermDet1[2].trim();
                                compPrice = strTermDet1[3].trim();
                                savDol = strTermDet1[4].trim();
                                savPercent = strTermDet1[5].trim();
                                objTermDetails = new TermDetails(term,energyPrice,tdspCharge,compPrice,savDol,savPercent);
                                termDetails.add(objTermDetails);
                            }
                        }
                        listCollection.add(new ExpressPricingDetails(frm.getCustomerName(), frm.getMonthlyDemand(), request.getParameter("referenceNo"), request.getParameter("strTdsp"), frm.getMonthlyEnergy(), frm.getCmbSalesRep(), request.getParameter("cogZone"), request.getParameter("estimatedkWh"), frm.getSalesManager(), request.getParameter("strBillMnth"), frm.getCustomerCharge(), frm.getAnalyst(),  request.getParameter("strTerms"), request.getParameter("loadFactor"), payHist, frm.getDepositAmount(),termDetails));
                        jasperFile = new File("E:/pricingdata/jasper/ExpressPricing.jasper");
                        /*url  = new URL(filePath+"/jsp/matrix/jasper/ExpressPricing.jasper" );
                        urlConn = url.openConnection();
                        inStream =urlConn.getInputStream();
                        jr = (JasperReport)JRLoader.loadObject(inStream);	*/
                        
                        jr = (JasperReport)JRLoader.loadObject(jasperFile);		      
                        dataSource = objds.createDataSource(jr,listCollection);
                        bytes = JasperRunManager.runReportToPdf(jr,parameters, dataSource);
                        result = objMMCustomersPDFDAO.addFile(objMMPricingPDFVO, bytes);
                        if(BuildConfig.DMODE)
                            System.out.println("result:"+result);
                    }
                    else
                    {
                        result = false;
                        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("MMRun.No.Result"));
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        if(result)
        {
            action = "pdfsuccess";
        }
        else
        {
            action = "failure";
        }
        
        if(!actionErrors.isEmpty())
        {
            saveErrors(request, actionErrors);
        }
        return mapping.findForward(action);
    }
}


/*
*$Log: XPressPricingViewResultAction.java,v $
*Revision 1.3  2009/01/27 05:47:57  tannamalai
*changes - according to MX server
*
*Revision 1.2  2008/11/21 09:47:23  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.2  2007/10/30 09:42:13  jnadesan
*jasper files are taken from d:\pricingdata\jasper\
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.11  2007/09/17 10:57:01  spandiyarajan
*salesrep not to be added properly... now that bug fixed
*
*Revision 1.10  2007/09/10 12:31:36  spandiyarajan
*float val8e changed to string
*
*Revision 1.9  2007/09/06 07:37:35  spandiyarajan
*passed the parameter monthly kva label
*
*Revision 1.8  2007/09/05 09:48:59  jnadesan
*decimal format added
*
*Revision 1.7  2007/09/04 07:35:57  kduraisamy
*make pdf restricted against run result
*
*Revision 1.6  2007/09/03 15:04:18  jnadesan
*unwanted lines are removed
*
*Revision 1.5  2007/09/03 09:31:55  spandiyarajan
*offer date and expiry date added
*
*Revision 1.4  2007/08/31 06:23:22  spandiyarajan
*removed unwanted system.out.println statement
*
*Revision 1.3  2007/08/30 12:37:40  spandiyarajan
*makepdf changes
*
*Revision 1.2  2007/08/29 07:23:55  spandiyarajan
*makepdf initially commited
*
*Revision 1.1  2007/08/27 04:43:02  jnadesan
*computaion for mAtrixPricer page
*
*
*/