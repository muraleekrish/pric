/*
 * 
 * PriceMatrixPDFServlet.java    May 3, 2007
 * 
 * Copyright (c) Savant Technologies Pvt Ltd.
 * 'Savant House' 127 Chamiers Road, Nandanam, Chennai-600 035. INDIA 
 * All rights reserved.

 * @author: jnadesan
 * @company: Savant Technologies
 * @client:  Energy
 * @version: 
 * @Description: 
 * 
*/
 
package com.savant.pricing.matrixpricing.pdf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
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

import com.savant.pricing.calculation.valueobjects.MarketCommentryVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.chart.DualAxisDemo;
import com.savant.pricing.common.chart.MatrixPricePDFChart;
import com.savant.pricing.common.chart.PriceMatrixChart;
import com.savant.pricing.dao.ForwardCurveBlockDAO;
import com.savant.pricing.dao.GasPriceDAO;
import com.savant.pricing.dao.MarketCommentryDAO;
import com.savant.pricing.matrixpricing.dao.MMPriceRunStatusDAO;
import com.savant.pricing.matrixpricing.dao.MatrixRunResultDAO;
import com.savant.pricing.matrixpricing.valueobjects.MMPriceRunHeaderVO;
import com.savant.pricing.matrixpricing.valueobjects.MatrixRunResultVO;
import com.savant.pricing.pdf.ValidateString;

/**
 * 
 */
public class PriceMatrixPDFServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
    throws ServletException, IOException {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        JasperReport jr = null;
        JRDataSource dataSource = null;
        MatrixRunResultDAO objMatrixRunResultDAO = new MatrixRunResultDAO();
        
        SimpleDateFormat dfofferdate = new SimpleDateFormat("MMMM dd, yyyy");
        SimpleDateFormat dfexpDate = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        MMPriceRunStatusDAO objMMPriceRunStatusDAO = new MMPriceRunStatusDAO();
        MarketCommentryDAO objMarketCommentryDAO = new MarketCommentryDAO();
        GasPriceDAO objGasPriceDAO = new GasPriceDAO();
        List gasPrice = objGasPriceDAO.getAllGasPrices();
        ForwardCurveBlockDAO objForwardCurveBlockDAO = new ForwardCurveBlockDAO();
        List forwardPrice = objForwardCurveBlockDAO.getForwardCurves(4,1,7);
       // MatrixPricePDFChart objMatrixPricePDFChart = new MatrixPricePDFChart();
        //objMatrixPricePDFChart.getchart(gasPrice,forwardPrice);
        DualAxisDemo objDualAxisDemo = new DualAxisDemo("");
        objDualAxisDemo.getchart(gasPrice,forwardPrice);
        MarketCommentryVO objMarketCommentryVO = null;
        MMPriceRunHeaderVO objMMPriceRunHeaderVO = new MMPriceRunHeaderVO();
        MMPriceRunHeaderVO objMMPriceRunHeaderDateVO = new MMPriceRunHeaderVO();
        
        
        String referNo = request.getParameter("referNo");
        objMMPriceRunHeaderDateVO = objMMPriceRunStatusDAO.getRunresultDetails(referNo);
        List lstchartDetails = new ArrayList();
        MatrixRunResultVO objMatrixRunResultVO = null;
        File jasperFile = null;
        String mailId = "feedback@mxenergy.com";
        //URL url = null;
        //URLConnection urlConn = null;
        InputStream  inStream = null;
        byte[] bytes = null;
        HashMap parameters = new HashMap();
        List lstrecords = new ArrayList();
        // disclaimer content
        //Date gasPriceLastImported = objGasPriceDAO.teeNaturalGasPriceLastImportedOn();
        Date  gasPriceLastImported = null;
        gasPriceLastImported =(objMMPriceRunHeaderDateVO.getGasPriceDate());
        String disclaimer = "Indicative energy prices include: Commodity, Ancillary charges, Inter and Intra-zonal congestion, Line Losses, Congestion management fees and miscellaneous ERCOT fees. Indicative prices are based on NYMEX "+df.format(gasPriceLastImported)+" forward strip prices and are subject to change, up or down, until a sales agreement is executed.";
        
        try
        { 
            LinkedHashMap hmZone = new LinkedHashMap();
            LinkedHashMap hmZone2 = new LinkedHashMap();
            List lstDetals = null;
            List lstDetals2 = null;
            List zone = objMatrixRunResultDAO.getDistinctZones(referNo);
            objMMPriceRunHeaderVO = objMMPriceRunStatusDAO.getRunresultDetails(referNo);
            Iterator iteZone = zone.iterator();
            while(iteZone.hasNext())
            {
                HashMap hmNoDem = new HashMap();
                HashMap hmBusLow = new HashMap();
                HashMap hmBusMed = new HashMap();
                HashMap hmBusHi = new HashMap();
                HashMap hmResHi = new HashMap();
                HashMap hmResLo = new HashMap();
                int zoneId = ((Integer)iteZone.next()).intValue();
                List lstZoneDetails = new ArrayList();
                List lstTerm = objMatrixRunResultDAO.getDistinctTermForZone(referNo,zoneId);
                Iterator iteTerm = lstTerm.iterator();
                List lstResult = new ArrayList();
                while(iteTerm.hasNext())
                {
                    int term = ((Integer)iteTerm.next()).intValue();
                    lstResult = objMatrixRunResultDAO.getResultByZone(referNo,zoneId,term);
                    Iterator iteResult = lstResult.iterator();
                    while(iteResult.hasNext())
                    { 
                        objMatrixRunResultVO = (MatrixRunResultVO)iteResult.next();
                        if(objMatrixRunResultVO.getLoadProfile().getProfileIdentifier()==1)
                            hmNoDem.put(new Integer(term),new Double(objMatrixRunResultVO.getEnergyOnlyPrice()));
                        else if(objMatrixRunResultVO.getLoadProfile().getProfileIdentifier()==4)
                            hmBusLow.put(new Integer(term),new Double(objMatrixRunResultVO.getEnergyOnlyPrice()));
                        else if(objMatrixRunResultVO.getLoadProfile().getProfileIdentifier()==5)
                            hmBusMed.put(new Integer(term),new Double(objMatrixRunResultVO.getEnergyOnlyPrice()));
                        else if(objMatrixRunResultVO.getLoadProfile().getProfileIdentifier()==2)
                            hmBusHi.put(new Integer(term),new Double(objMatrixRunResultVO.getEnergyOnlyPrice()));
                        else if(objMatrixRunResultVO.getLoadProfile().getProfileIdentifier()==9)
                            hmResHi.put(new Integer(term),new Double(objMatrixRunResultVO.getEnergyOnlyPrice()));
                        else if(objMatrixRunResultVO.getLoadProfile().getProfileIdentifier()==10)
                            hmResLo.put(new Integer(term),new Double(objMatrixRunResultVO.getEnergyOnlyPrice()));
                    }
                }
               
                LinkedHashMap hmprofiles = new LinkedHashMap();
                
                hmprofiles.put("BUSHILF",hmBusHi);
                hmprofiles.put("BUSMEDLF",hmBusMed);
                hmprofiles.put("BUSLOLF",hmBusLow);
                hmprofiles.put("BUSNODEM",hmNoDem);
                hmprofiles.put("RESHIWR",hmResHi);
                hmprofiles.put("RESLOWR",hmResLo);
                if(objMatrixRunResultVO!=null)
                {
                    if(objMatrixRunResultVO.getCongestion().getCongestionZone().equalsIgnoreCase("North")||objMatrixRunResultVO.getCongestion().getCongestionZone().equalsIgnoreCase("South")||objMatrixRunResultVO.getCongestion().getCongestionZone().equalsIgnoreCase("Non Ercot")||objMatrixRunResultVO.getCongestion().getCongestionZone().equalsIgnoreCase("Gas"))
                        hmZone.put(objMatrixRunResultVO.getCongestion().getCongestionZone(),hmprofiles);
                    else
                        hmZone2.put(objMatrixRunResultVO.getCongestion().getCongestionZone(),hmprofiles);
                }
                PriceMatrixChart objPriceMatrixChart = new PriceMatrixChart();
                objPriceMatrixChart.pricematrixChart(request.getSession(),objMatrixRunResultDAO.getResultByZone(referNo,zoneId),objMatrixRunResultVO.getCongestion().getCongestionZone());
            } 
          
	       	lstDetals = this.getPDFMainDetails(hmZone);
	       	lstDetals2 = this.getPDFMainDetails(hmZone2);
	        List lstResult = new ArrayList();
            objMarketCommentryVO = objMarketCommentryDAO.getMarketCommentryVOByDate(objMMPriceRunHeaderVO.getOfferDate());
            PriceMatrixMainDetails objPriceMatrixMainDetails = new PriceMatrixMainDetails();
            objPriceMatrixMainDetails.setLstTdspDetails(lstDetals);
            objPriceMatrixMainDetails.setLstTdspDetails2(lstDetals2);
            objPriceMatrixMainDetails.setMarketCommentry(objMarketCommentryVO==null?null:objMarketCommentryVO.getMarketComments());
            objPriceMatrixMainDetails.setDisc(disclaimer);
            lstResult.add(objPriceMatrixMainDetails);
             //lstchartDetails.add(new PriceMatrixPDFSummaryChartDetails(objMatrixRunResultVO.getTdsp().getTdspName(),"E:/pricingdata/jasper/"+objMatrixRunResultVO.getTdsp().getTdspName()+".jpeg",lstTdspDetails));
            parameters.put("ExpDate",dfexpDate.format(objMMPriceRunHeaderVO.getExpiredate())+" at "+sdf.format(objMMPriceRunHeaderVO.getExpiredate()));
            parameters.put("ReportDate",dfofferdate.format(objMMPriceRunHeaderVO.getOfferDate()));
            parameters.put("disclaimer",disclaimer);
            parameters.put("Email",mailId);
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
            {
                parameters.put("subRepPath","D:/J2EEProjects/pricingdata/jasper/");
                parameters.put("tireagleLogoPath","D:/J2EEProjects/pricingdata/jasper/");
            }
            else
            {
                parameters.put("subRepPath","D:/J2EEProjects/pricingdata/jasper/");
                parameters.put("tireagleLogoPath","D:/J2EEProjects/pricingdata/jasper/");
            }
            ExpressPricingReportDataSource objds =null;
            objds = new ExpressPricingReportDataSource(PriceMatrixMainDetails.class);
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
            {
                jasperFile = new File("D:/J2EEProjects/pricingdata/jasper/TDSPMainReport.jasper");
            }
            else
            {
                jasperFile = new File("D:/J2EEProjects/pricingdata/jasper/TDSPMainReport.jasper");
            }
            
            //url  = new URL(filePath+"/jsp/matrix/jasper/TDSPMainReport.jasper" );
            //urlConn = url.openConnection();
            //inStream =urlConn.getInputStream();
            jr = (JasperReport)JRLoader.loadObject(jasperFile);		            
            dataSource = objds.createDataSource(jr,lstResult);
            /*jr = (JasperReport)JRLoader.loadObject(inStream);		            
             dataSource = objds.createDataSource(jr,lstrecords);*/
            bytes = JasperRunManager.runReportToPdf(jr,parameters, dataSource);
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        response.setHeader("Content-Disposition", "attachment;filename=\"" +ValidateString.checkMetaCharacters(referNo)+"_PriceMatrix.pdf" + "\";");
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);
        ServletOutputStream servletoutputstream = response.getOutputStream();
        servletoutputstream.write(bytes, 0, bytes.length);
        servletoutputstream.flush();
        servletoutputstream.close();
    }
    private List getPDFMainDetails(LinkedHashMap hm)
    {
        List lstResult = new ArrayList();
        List termDetails = new ArrayList();
        Set setZones = hm.keySet();
        Iterator ite = setZones.iterator();
        List chartDetails = null;
        PriceMatrixPDFSummaryDetails objPriceMatrixPDFSummaryDetails = null;
        PriceMatrixPDFSummaryChartDetails objPriceMatrixPDFSummaryChartDetails = null;
        chartDetails = new ArrayList();
        while(ite.hasNext())
        { 
            termDetails = new ArrayList();
            String zoneName = (String)ite.next();
            LinkedHashMap hmProfiles = (LinkedHashMap)hm.get(zoneName);
            Set setprofiles = hmProfiles.keySet();
            Iterator iteProfile = setprofiles.iterator();
            while(iteProfile.hasNext())
            {
                String profileName = (String)iteProfile.next();
                HashMap hmTerms = (HashMap)hmProfiles.get(profileName);
                objPriceMatrixPDFSummaryDetails = new PriceMatrixPDFSummaryDetails();
                objPriceMatrixPDFSummaryDetails.setProfile(profileName);
                objPriceMatrixPDFSummaryDetails.setZoneName(zoneName);
                objPriceMatrixPDFSummaryDetails.setTerm12(hmTerms.get(new Integer(12))==null?0:((Double)hmTerms.get(new Integer(12))).doubleValue());
                objPriceMatrixPDFSummaryDetails.setTerm24(hmTerms.get(new Integer(24))==null?0:((Double)hmTerms.get(new Integer(24))).doubleValue());
                objPriceMatrixPDFSummaryDetails.setTerm36(hmTerms.get(new Integer(36))==null?0:((Double)hmTerms.get(new Integer(36))).doubleValue());
                objPriceMatrixPDFSummaryDetails.setTerm6(hmTerms.get(new Integer(6))==null?0:((Double)hmTerms.get(new Integer(6))).doubleValue());
                objPriceMatrixPDFSummaryDetails.setTerm18(hmTerms.get(new Integer(18))==null?0:((Double)hmTerms.get(new Integer(18))).doubleValue());
                termDetails.add(objPriceMatrixPDFSummaryDetails);
            } 
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
            {
                objPriceMatrixPDFSummaryChartDetails = new PriceMatrixPDFSummaryChartDetails(zoneName,"D:/J2EEProjects/pricingdata/jasper/"+zoneName+".jpeg",termDetails);
            }
            else
            {
                objPriceMatrixPDFSummaryChartDetails = new PriceMatrixPDFSummaryChartDetails(zoneName,"D:/J2EEProjects/pricingdata/jasper/"+zoneName+".jpeg",termDetails);
            }
            chartDetails.add(objPriceMatrixPDFSummaryChartDetails);
        }
        return chartDetails;
    }
}

/*
*$Log: PriceMatrixPDFServlet.java,v $
*Revision 1.7  2009/01/27 05:47:57  tannamalai
*changes - according to MX server
*
*Revision 1.6  2008/12/03 10:55:10  tannamalai
*URL Updated Based on Remote Environment.
*
*Revision 1.5  2008/02/06 06:42:21  tannamalai
*minor changhes done jasper
*
*Revision 1.4  2008/01/29 07:02:58  tannamalai
*price matrix pdf templates changed
*
*Revision 1.3  2008/01/24 08:35:09  tannamalai
*chart changed
*
*Revision 1.2  2008/01/23 08:35:13  tannamalai
*jasper reports changes
*
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.5  2007/11/29 13:15:57  srajan
*run enabled
*
*Revision 1.4  2007/11/28 14:11:27  jnadesan
*path added
*
*Revision 1.3  2007/11/28 13:05:51  jnadesan
*changes based on zone wise
*
*Revision 1.2  2007/11/27 03:41:39  jnadesan
*imports organized
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/09/07 10:03:47  jnadesan
*print removed
*
*Revision 1.3  2007/09/04 12:52:31  jnadesan
*Date sent as parameter
*
*Revision 1.2  2007/09/04 05:24:07  spandiyarajan
*removed unwanted imports
*
*Revision 1.1  2007/09/03 15:06:03  jnadesan
*servlet to get Matrix pdf
*
*
*/