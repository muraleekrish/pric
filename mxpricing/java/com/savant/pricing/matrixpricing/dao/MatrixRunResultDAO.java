/*
 * 
 * MatrixRunResultDAO.java    Aug 24, 2007
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

package com.savant.pricing.matrixpricing.dao;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.matrixpricing.valueobjects.MatrixRunResultVO;

/**
 * 
 */
public class MatrixRunResultDAO
{
    static Logger logger = Logger.getLogger(MatrixRunResultDAO.class);
    
    public List getRunresultDetails(String priceRunRefId, int tdsp, int congetsionZone, int loadProfileId, int term)
    {
        /**
         * Requires -
         * Modifies -
         * Effects -
         * @throws -
         */       
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING RUN RESULT DETAILS");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MatrixRunResultVO.class);
            objCriteria.add(Restrictions.eq("priceRunRefNo",priceRunRefId)).add(Restrictions.eq("tdsp.tdspIdentifier",new Integer(tdsp))).add(Restrictions.eq("congestion.congestionZoneId",new Integer(congetsionZone)));
            objCriteria.add(Restrictions.eq("loadProfile.profileIdentifier",new Integer(loadProfileId)));
            if(term!=0)
            objCriteria.add(Restrictions.eq("term",new Integer(term)));
            objCriteria.addOrder(Order.asc("term"));
            objList = objCriteria.list();
            logger.info("GOT RUN RESULT DETAILS");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE RUN RESULT DETAILS", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objList;
    }

    public List getResult(String priceRunRefNo, int tdsp, int term )
    {
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING RESULT");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MatrixRunResultVO.class);
            objCriteria.add(Restrictions.eq("priceRunRefNo",priceRunRefNo)).add(Restrictions.eq("tdsp.tdspIdentifier",new Integer(tdsp)));
            objCriteria.add(Restrictions.eq("term",new Integer(term)));
            objList = objCriteria.list();
            logger.info("GOT RESULT");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE RESULT", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objList;
    }
    
    public List getResultByZone(String priceRunRefNo, int zoneId, int term )
    {
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING RESULT");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MatrixRunResultVO.class);
            objCriteria.add(Restrictions.eq("priceRunRefNo",priceRunRefNo)).add(Restrictions.eq("congestion.congestionZoneId",new Integer(zoneId)));
            objCriteria.add(Restrictions.eq("term",new Integer(term)));
            objList = objCriteria.list();
            logger.info("GOT RESULT");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET THE RESULT", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objList;
    }
    
    public List getResultByTDSP(String priceRunRefNo, int tdsp)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING RESULTS BY TDSP");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MatrixRunResultVO.class);
            objCriteria.add(Restrictions.eq("priceRunRefNo",priceRunRefNo)).add(Restrictions.eq("tdsp.tdspIdentifier",new Integer(tdsp)));
            //objCriteria.add(Restrictions.eq("loadProfile.profileIdentifier",new Integer(profile)));
            objList = objCriteria.list();
            logger.info("GOT RESULTS BY TDSP");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET RESULTS BY TDSP", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objList;
    }
    
    public List getResultByZone(String priceRunRefNo, int zoneId)
    {
        Criteria objCriteria = null;
        Session objSession = null;
        List objList = null;
        try
        {
            logger.info("GETTING RESULTS BY TDSP");
            objSession = HibernateUtil.getSession();
            objCriteria = objSession.createCriteria(MatrixRunResultVO.class);
            objCriteria.add(Restrictions.eq("priceRunRefNo",priceRunRefNo)).add(Restrictions.eq("congestion.congestionZoneId",new Integer(zoneId)));
            //objCriteria.add(Restrictions.eq("loadProfile.profileIdentifier",new Integer(profile)));
            objList = objCriteria.list();
            logger.info("GOT RESULTS BY TDSP");
        }
        catch(HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING GET RESULTS BY TDSP", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objList;
    }
    
    public double getDepositAmount(List lstbundlePrice,double estimatedkWh)
    {
        Iterator iteResult = lstbundlePrice.iterator();
        MatrixRunResultVO objMatrixRunResultVO = null;
        double depositAmount = 0;
        double bundleCost = 0;
        while(iteResult.hasNext())
        {
            objMatrixRunResultVO = (MatrixRunResultVO)iteResult.next();
            if(objMatrixRunResultVO.getTerm()==12)
            {
                bundleCost = objMatrixRunResultVO.getTdspCharge()+objMatrixRunResultVO.getEnergyOnlyPrice();
            }
        } 
        if(bundleCost!=0)
            depositAmount = (bundleCost * estimatedkWh * 2) / (12*100);
        depositAmount = Math.round(depositAmount);
        depositAmount = ((depositAmount)*100);
        return depositAmount;
    }
    public String constructTable(String priceRunRefId, int tdsp, int congetsionZone, int loadProfileId, int term,int bundle, double estimatedkWh)
    { 
        DecimalFormat df = new DecimalFormat("##,##0.0000");
        DecimalFormat df1 = new DecimalFormat("##,##0.00");
        List lstResult = getRunresultDetails(priceRunRefId, tdsp, congetsionZone, loadProfileId, term);
        double tdspCharge = 0;
        double customerCharge = 0.0;
        String table = "<table width='98%' border='0' cellspacing='0' cellpadding='0' style='margin: 0px 10px 0px 10px'  id='idTermBundled'> " +
        "<tr class='staticHeader'><td width='50' class='tblheader' style='border-left:solid 1px #CCCCCC'>Term</td>" +
        "<td width='207' class='tblheader'>Energy Price ($/kWh)<span style='color:#FF0000'>**</span></td>" +
        "<td width='215' class='tblheader'>TDSP Charges ($/kWh)</td>" +
        "<td width='180' class='tblheader'>Competitor Price ($/kWh)</td>" +
        "<td width='190' class='tblheader'>Savings ($)</td>" +
        "<td width='120' class='tblheader'> % Savings</td></tr>";        
        
        if(lstResult.size()<1)
        {
            table += "<tr><td class = 'tbldata' colspan = '9' align = 'center'>No Records Found</td></tr>";
        }
        else   
        {
            Iterator ite = lstResult.iterator();
            while(ite.hasNext())
            {
                MatrixRunResultVO objMatrixRunResultVO = (MatrixRunResultVO)ite.next();
                customerCharge = objMatrixRunResultVO.getCustCharge();
                int termValue = objMatrixRunResultVO.getTerm();
                if(bundle==1)
                    tdspCharge = objMatrixRunResultVO.getTdspCharge();
                table += "<tr><td class='tbldata' style='border-left:dotted 1px #CCCCCC'>"+termValue+"</td>" +
                "<td class='tbldata' id='energyCharge"+termValue+"'>"+df.format(objMatrixRunResultVO.getEnergyOnlyPrice())+"</td>" +
                "<td class='tbldata' id='tdspCharge"+termValue+"'>"+df.format(tdspCharge)+"</td>" +
                "<td class='tbldata' style='background-color:#FDFDB3'>" +
                "<input name='competitorPrice' type='text' onchange='computeSavings(\""+termValue+"\")' id='competitorPrice"+termValue+"' size='30' style='background-color:#FDFDB3; border:0'></td>" +
                "<td class='tbldata' id='savings"+termValue+"'>&nbsp;</td>" +
                "<td class='tbldata' id='percent"+termValue+"'>&nbsp;</td></tr>";
            }
        }
        table += "</table>";
        table+="#$@%"+getDepositAmount(lstResult,estimatedkWh)+"#$@%"+df1.format(customerCharge);
        return table;
    }
    public List getDistinctTermForTDSP(String priceRunRefNo, int tdsp)
    {
        List objList = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING DISTINCT TERM FOR TDSP");
            objSession = HibernateUtil.getSession();
            objList = objSession.createQuery("select distinct(mmresultsVO.term) as profile from MatrixRunResultVO as mmresultsVO where mmresultsVO.priceRunRefNo = ? and mmresultsVO.tdsp.tdspIdentifier = ?").setString(0, priceRunRefNo).setInteger(1,tdsp).list();
            logger.info("GOT DISTINCT TERM FOR TDSP");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET DISTINCT TERM FOR TDSP", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objList;
    }
    
    public List getDistinctTermForZone(String priceRunRefNo, int zoneId)
    {
        List objList = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING DISTINCT TERM FOR TDSP");
            objSession = HibernateUtil.getSession();
            objList = objSession.createQuery("select distinct(mmresultsVO.term) as profile from MatrixRunResultVO as mmresultsVO where mmresultsVO.priceRunRefNo = ? and mmresultsVO.congestion.congestionZoneId = ?").setString(0, priceRunRefNo).setInteger(1,zoneId).list();
            logger.info("GOT DISTINCT TERM FOR TDSP");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET DISTINCT TERM FOR TDSP", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objList;
    }

    public List getDistinctTDSP(String priceRunRefNo)
    {
        List objList = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING DISTINCT TDSP");
            objSession = HibernateUtil.getSession();
            objList = objSession.createQuery("select distinct(mmresultsVO.tdsp.tdspIdentifier) as term from MatrixRunResultVO as mmresultsVO where mmresultsVO.priceRunRefNo = ? order by mmresultsVO.tdsp").setString(0, priceRunRefNo).list();
            logger.info("GOT DISTINCT TDSP");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET DISTINCT TDSP", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objList;
    }
    public List getDistinctZones(String priceRunRefNo)
    {
        List objList = null;
        Session objSession = null;
        try
        {
            logger.info("GETTING DISTINCT TDSP");
            objSession = HibernateUtil.getSession();
            objList = objSession.createQuery("select distinct(mmresultsVO.congestion.congestionZoneId) as zone from MatrixRunResultVO as mmresultsVO where mmresultsVO.priceRunRefNo = ? order by mmresultsVO.congestion.congestionZoneId").setString(0, priceRunRefNo).list();
            logger.info("GOT DISTINCT TDSP");
        }
        catch(Exception e)
        {
            logger.error("GENERAL EXCEPTION DURING GET DISTINCT TDSP", e);
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return objList;
    }
    public String dwrExample(String input)
    {
        return "dwrExample"+input;
    }

    public static void main(String[] args)
    {
        System.out.println(new MatrixRunResultDAO().getDistinctTDSP("08-29-2007 11:00:46"));
    }
}

/*
*$Log: MatrixRunResultDAO.java,v $
*Revision 1.2  2008/11/21 09:46:39  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/28 13:05:11  jnadesan
*methods added to get matrix run results by zone wise
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.13  2007/09/11 12:11:21  jnadesan
*rounded depositAmount
*
*Revision 1.12  2007/09/10 12:28:57  spandiyarajan
*removed unwanted td in the table
*
*Revision 1.11  2007/09/10 11:26:26  jnadesan
*customer charge added
*
*Revision 1.10  2007/09/07 13:31:46  sramasamy
*Log correction.
*
*Revision 1.9  2007/09/06 14:06:38  jnadesan
*Savings completed
*
*Revision 1.8  2007/09/06 12:37:04  jnadesan
*DWR Concept is implemented
*
*Revision 1.7  2007/09/05 09:48:38  jnadesan
*deposit amount computed
*
*Revision 1.6  2007/09/04 09:16:29  sramasamy
*Log message is added for log file.
*
*Revision 1.5  2007/09/03 15:05:23  jnadesan
*metod added
*
*Revision 1.4  2007/08/29 07:42:05  jnadesan
*term also included for condition
*
*Revision 1.3  2007/08/27 15:13:39  kduraisamy
*weatherzone id added into condition
*
*Revision 1.2  2007/08/27 06:37:59  spandiyarajan
*removed unwanted imports
*
*Revision 1.1  2007/08/27 04:42:10  jnadesan
*initial commit for MonthlyWeights
*
*
*/