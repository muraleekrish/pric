/*
 * 
 * LoadMMPriceResult.java    Aug 22, 2007
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

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.savant.pricing.calculation.dao.DealLeversDAO;
import com.savant.pricing.calculation.dao.PricingDAO;
import com.savant.pricing.calculation.valueobjects.PICVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO;
import com.savant.pricing.calculation.valueobjects.PriceRunHeaderVO;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.dao.CongestionZonesDAO;
import com.savant.pricing.dao.LoadProfileTypesDAO;
import com.savant.pricing.dao.PICDAO;
import com.savant.pricing.dao.PriceRunCustomerDAO;
import com.savant.pricing.dao.TDSPDAO;
import com.savant.pricing.matrixpricing.valueobjects.MatrixRunResultVO;
import com.savant.pricing.transferobjects.DealLevers;
import com.savant.pricing.transferobjects.PricingDashBoard;
import com.savant.pricing.valueobjects.CongestionZonesVO;
import com.savant.pricing.valueobjects.LoadProfileTypesVO;
import com.savant.pricing.valueobjects.TDSPVO;

/**
 * 
 */
public class LoadMMPriceResult
{
    static Logger logger = Logger.getLogger(LoadMMPriceResult.class);
    
    public void loadRunResult(int pricerunCustId)
    {
        logger.info("LOADING RUN RESULT BY CUSTOMER ID");
        PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
        DealLeversDAO objDealLeversDAO = new DealLeversDAO();
        PricingDAO objPricingDAO = new PricingDAO();
        PriceRunCustomerVO objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(pricerunCustId);
        PricingDashBoard objPricingDashBoard = null;
        String esiid = getEsiid(pricerunCustId);
        CongestionZonesVO objCongestionZoneVO = this.getCongestionzone(pricerunCustId);
        LoadProfileTypesVO objLoadProfileTypesVO= getLoadProfile(objPriceRunCustomerVO.getProspectiveCustomer().getProspectiveCustomerId(),esiid);
        System.out.println("objPriceRunCustomerVO:"+objPriceRunCustomerVO.getProspectiveCustomer().getProspectiveCustomerId());
        System.out.println("ESIID:"+esiid);
        System.out.println("ID:::"+objLoadProfileTypesVO.getProfileIdentifier());
        
        TDSPVO objTDSPVO = getTDSP(pricerunCustId);
        
        for(int i =6;i<37;i=i+6)
        {
            MatrixRunResultVO objMatrixRunResultVO = new MatrixRunResultVO();
            List deallevers = objDealLeversDAO.getDealLevers(objPriceRunCustomerVO.getPriceRunCustomerRefId(), i);
            objPricingDashBoard = objPricingDAO.getDashBoardDetails(objPriceRunCustomerVO.getPriceRunCustomerRefId(), i, "");
            objMatrixRunResultVO.setTerm(i);
            objMatrixRunResultVO.setEnergyOnlyPrice((float) getFixedPrice(objPricingDashBoard, deallevers));
            objMatrixRunResultVO.setAncCharge(objPricingDashBoard.getAncillaryServices()/objPricingDashBoard.getContractkWh());
            objMatrixRunResultVO.setCongestion(objCongestionZoneVO);
            objMatrixRunResultVO.setEsiId(esiid);
            objMatrixRunResultVO.setLoadProfile(objLoadProfileTypesVO);
            objMatrixRunResultVO.setPriceRunRefNo(objPriceRunCustomerVO.getPriceRunRef().getPriceRunRefNo());
            objMatrixRunResultVO.setProspectiveCust(objPriceRunCustomerVO.getProspectiveCustomer());
            objMatrixRunResultVO.setTdsp(objTDSPVO);
            objMatrixRunResultVO.setTdspCharge(objPricingDashBoard.getTdspCharges()/objPricingDashBoard.getContractkWh());
            
            Iterator iter = deallevers.iterator();
        	HashMap mapdealvalue =  new HashMap();
        	while(iter.hasNext())
        	{
        		DealLevers objdeallevers = (DealLevers)iter.next();
        		mapdealvalue.put(new Integer(objdeallevers.getDealLeverIdentifier()),new Float(objdeallevers.getValue()));
        	}
            objMatrixRunResultVO.setCustCharge(((Float)(mapdealvalue.get(new Integer(1)))).floatValue());
            
            addMMPRiceResult(objMatrixRunResultVO);
            if(i==24)
                i=i+6;
        }
        try
        {
            boolean result = objPricingDAO.deleteRunResult(pricerunCustId+"");
        }
        catch (SQLException e) 
        {
            logger.error("SQL EXCEPTION DURING LOAD THE RUN RESULT", e);
            e.printStackTrace();
        }
    }
    public boolean deletePriceRunHeader(String priceRunRefNumber)
    {
        PriceRunHeaderVO objPriceRunHeaderVO = new PriceRunHeaderVO();
        objPriceRunHeaderVO.setPriceRunRefNo(priceRunRefNumber);
        PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
        return objPriceRunCustomerDAO.deleteRunEntry(objPriceRunHeaderVO);
    } 
    public boolean deleteaggLoadProfile()
    {
        Session objSession = null;
        Query objQuery = null;
        boolean updateResult = false;
        try
        {
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objQuery = objSession.createSQLQuery("delete from prc_aggregatedloadprofile");
            objQuery.executeUpdate();
            objSession.getTransaction().commit();
            updateResult = true;
        }
        catch (HibernateException e)
        {
            objSession.getTransaction().rollback();
            e.printStackTrace();
        }
        finally
        {
            objSession.close();
        }
        return updateResult;
    
    }

    private double getFixedPrice(PricingDashBoard objPricingDashBoard, List deallevers)
    {
        logger.info("GETTING FIXED PRICE");	
        double fptc = 0;
        double oHtotal$ = 0;
        double total$ = 0;
        Iterator iter = deallevers.iterator();
        HashMap mapdealvalue = new HashMap();
        while (iter.hasNext())
        {
            DealLevers objdeallevers = (DealLevers) iter.next();
            mapdealvalue.put(new Integer(objdeallevers.getDealLeverIdentifier()), new Double(objdeallevers.getValue()));
        }
        total$ = getTotal$(objPricingDashBoard);
        oHtotal$ = getOhTotal(objPricingDashBoard, mapdealvalue);
        fptc = (objPricingDashBoard.getMargin() * (float) ((Double) mapdealvalue.get(new Integer(3))).doubleValue() + oHtotal$ + total$)/objPricingDashBoard.getContractkWh();
        logger.info("GOT FIXED PRICE");
        return fptc;
    }

    private double getTotal$(PricingDashBoard objPricingDashBoard)
    {
        float total$ = 0;
        total$ += objPricingDashBoard.getEnergyCharge();
        total$ += objPricingDashBoard.getShapingPremium();
        total$ += objPricingDashBoard.getVolatilityPremium();
        total$ += objPricingDashBoard.getAncillaryServices();
        total$ += objPricingDashBoard.getIntraZonalCongestion();
        total$ += objPricingDashBoard.getFeesAndRegulatory();
        return total$;
    }

    private double getOhTotal(PricingDashBoard objPricingDashBoard, HashMap mapdealvalue)
    {
        double oHtotal$;
        System.out.println("");
        oHtotal$ = objPricingDashBoard.getCustomerCharge() * (float) ((Double) mapdealvalue.get(new Integer(1))).doubleValue();
        oHtotal$ += objPricingDashBoard.getAdditionalVolatilityPremium() * (float) ((Double) mapdealvalue.get(new Integer(7))).doubleValue() / 100;
        oHtotal$ += objPricingDashBoard.getSalesAgentFee() * (float) ((Double) mapdealvalue.get(new Integer(4))).doubleValue();
        oHtotal$ += objPricingDashBoard.getAggregatorFee() * (float) ((Double) mapdealvalue.get(new Integer(5))).doubleValue();
        oHtotal$ += objPricingDashBoard.getBandwidthCharge() * (float) ((Double) mapdealvalue.get(new Integer(6))).doubleValue();
        oHtotal$ += objPricingDashBoard.getOtherFee() * (float) ((Double) mapdealvalue.get(new Integer(2))).doubleValue();
        return oHtotal$;
    }

    public boolean addMMPRiceResult(MatrixRunResultVO objMatrixRunResultVO)
    {
        Session objSession = null;
        boolean updateResult = false;
        try
        {
            logger.info("ADDING MM PRICE RESULT");
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            objSession.save(objMatrixRunResultVO);
            objSession.getTransaction().commit();
            updateResult = true;
            logger.info("MM PRICE RESULT IS ADDED");
        }
        catch (HibernateException e)
        {
            logger.error("HIBERNATE EXCEPTION DURING ADD MM PRICE RESULT", e);
            objSession.getTransaction().rollback();
            e.printStackTrace();
        }
        finally
        {
            if(objSession != null)
            {
                objSession.close();
            }
        }
        return updateResult;
    }

    private CongestionZonesVO getCongestionzone(int pricerunCustId)
    {
        logger.info("GETTING CONGESTION ZONE");
        PricingDAO objPricingDAO = new PricingDAO();
        CongestionZonesDAO objCongestionZonesDAO = new CongestionZonesDAO();
        CongestionZonesVO objCongestionZonesVO = null;
        int zoneId = 0;
        HashMap hmcongestion = objPricingDAO.getAllCongestionZones(pricerunCustId);
        Set setCongestionZone = hmcongestion.keySet();
        Iterator ite = setCongestionZone.iterator();
        if(ite.hasNext())
        {
            zoneId = ((Integer) ite.next()).intValue();
        }
        objCongestionZonesVO = objCongestionZonesDAO.getCongestionZone(zoneId);
        logger.info("GOT CONGESTION ZONE");
        return objCongestionZonesVO;
    }
    private TDSPVO getTDSP(int pricerunCustId)
    {
        logger.info("GETTING TDSP");
        PricingDAO objPricingDAO = new PricingDAO();
        TDSPVO objTDSPVO = null;
        TDSPDAO objTDSPDAO = new TDSPDAO();
        HashMap hmcongestion = objPricingDAO.getAllTDSPs(pricerunCustId);
        int tdspId = 0;
        Set setCongestionZone = hmcongestion.keySet();
        Iterator ite = setCongestionZone.iterator();
        if(ite.hasNext())
        {
            tdspId = ((Integer) ite.next()).intValue();
        }
        objTDSPVO = objTDSPDAO.getTDSP(tdspId);
        logger.info("GOT TDSP");
        return objTDSPVO;
    }
    private String getEsiid(int pricerunCustId)
    {
        logger.info("GETTING ESIID");
        PricingDAO objPricingDAO = new PricingDAO();
        String strEsiid = "";
        HashMap hmEsiid = objPricingDAO.getAllEsiIds(pricerunCustId);
        Set setEsiid = hmEsiid.keySet();
        Iterator ite = setEsiid.iterator();
        if(ite.hasNext())
        {
            strEsiid = (String) ite.next();
            logger.info("GOT ESIID");
        }
        else
        {
            logger.info("ESIID IS NOT AVAILABLE");
        }
        return strEsiid;
    }
    private LoadProfileTypesVO getLoadProfile(int prsCustId,String esiid)
    {
        logger.info("LOADING PROFILE DETAILS");
        PICDAO objPICDAO = new PICDAO();
        PICVO objPICVO = objPICDAO.getPICVO(prsCustId,esiid);
        LoadProfileTypesDAO objLoadProfileTypesDAO = new LoadProfileTypesDAO();
        LoadProfileTypesVO objLoadProfileTypesVO = null;
        StringTokenizer st = new StringTokenizer(objPICVO.getLoadProfile(),"_");
        String profile = "";
        if(st.hasMoreTokens())
            profile = st.nextToken();
        objLoadProfileTypesVO = objLoadProfileTypesDAO.getLoadProfile(profile);
        logger.info("GOT LOAD PROFILE DETAILS");
        return objLoadProfileTypesVO;
    }
}
/*
 *$Log: LoadMMPriceResult.java,v $
 *Revision 1.4  2008/11/21 09:46:39  jvediyappan
 *Trieagle changed as MXEnergy.
 *
 *Revision 1.3  2008/02/14 05:44:39  tannamalai
 *method changed
 *
 *Revision 1.2  2008/01/23 08:35:14  tannamalai
 *jasper reports changes
 *
 *Revision 1.1  2007/12/07 06:18:37  jvediyappan
 *initial commit.
 *
 *Revision 1.1  2007/10/30 05:51:53  jnadesan
 *Initial commit.
 *
 *Revision 1.1  2007/10/26 15:19:26  jnadesan
 *initail MXEP commit
 *
 *Revision 1.8  2007/09/06 11:59:25  spandiyarajan
 *get the custCharge value
 *
 *Revision 1.7  2007/09/04 12:01:30  jnadesan
 *aggloadprofile deleted
 *
 *Revision 1.6  2007/09/04 09:16:29  sramasamy
 *Log message is added for log file.
 *
 *Revision 1.5  2007/09/03 15:05:08  jnadesan
 *4/kwh computed
 *
 *Revision 1.4  2007/08/29 05:46:13  jnadesan
 *computation changed
 *
 *Revision 1.3  2007/08/27 04:42:27  jnadesan
 *computaion for mAtrixPricer page
 *
 *Revision 1.2  2007/08/23 14:34:35  jnadesan
 *restricted viewing both MM customers with normal customers
 *
 *Revision 1.1  2007/08/23 07:36:25  jnadesan
 *initial commit MMPrice entry
 *
 *
 */