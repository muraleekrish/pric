/*
 * 
 * ComputeMMPrice.java    Aug 24, 2007
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

import com.savant.pricing.dao.LoadProfileTypesDAO;
import com.savant.pricing.dao.TDSPDAO;
import com.savant.pricing.matrixpricing.valueobjects.MatrixMonthlyWeightsVO;
import com.savant.pricing.valueobjects.LoadProfileTypesVO;

/**
 * 
 */
public class ComputeMMPrice
{
    static Logger logger = Logger.getLogger(ComputeMMPrice.class);
    
    public double getLoadFactor(String strkWh, String strdemand, int tdsp)
    {
        logger.info("GETTING LOAD FACTOR");
            
        double kWh = 0;
        double demand = 0;
        double loadFactor = 0;
        double powerFactor = 1;
        if(!strkWh.equalsIgnoreCase(""))
            kWh = Float.parseFloat(strkWh);
        if(!strdemand.equalsIgnoreCase(""))
            demand = Float.parseFloat(strdemand);
        if(tdsp==1)
            powerFactor = 0.9;
        if(demand !=0 && powerFactor !=0)
            loadFactor = (kWh / ((demand/powerFactor)*30.1 * 24));
        logger.info("GOT LOAD FACTOR");
        return loadFactor;
    }
    
    public int getLoadProfile(double loadFactor)
    {
        logger.info("GETTING LOAD PROFILE");
        LoadProfileTypesDAO objLoadProfileTypesDAO = new LoadProfileTypesDAO();
        LoadProfileTypesVO objLoadProfileTypesVO = null;
        String loadProfileType = "";
        int profileId= 0;
        if(loadFactor==0)
            loadProfileType = "BusNoDem";
        else if(loadFactor<0.4)
            loadProfileType = "BusLoLF";
        else if(loadFactor<=0.6)
            loadProfileType = "BusMedLF";
        else if(loadFactor<=1)
            loadProfileType = "BusHiLF";
        else 
            loadProfileType = "error";
        if(!loadProfileType.equalsIgnoreCase("error"))
        {
            objLoadProfileTypesVO = objLoadProfileTypesDAO.getLoadProfile(loadProfileType);
            profileId = objLoadProfileTypesVO.getProfileIdentifier();
        }
        logger.info("GOT LOAD PROFILE");
        return profileId;
    }
    
    public String get$Savings(double tdspCharge,double energyOnlyPrice, double comPrice, double estimatedKwh,int year, double customerCharge)
    {
        String result = "";
        double savings$ = 0;
        double savings$percentage = 0;
        try
        {
            DecimalFormat df = new DecimalFormat("##,##0.00");
            if(!(comPrice+"").equalsIgnoreCase("NaN"))
            {
            savings$ = (((tdspCharge+energyOnlyPrice) - comPrice)*year*estimatedKwh)-(customerCharge*year*12);
            savings$percentage = savings$*100 /((tdspCharge+energyOnlyPrice)*estimatedKwh*year);
            }
            result = (df.format(savings$)+"$#%@"+df.format(savings$percentage)+"%");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
    
    public double getEstimatedkWh(String strbillMonth,String strEergykWh,int profileId, String strCongestion,String strtdsp)
    {
        logger.info("GETTING ESTIMATED kWh");
        int billMonth = 1;
        double energykWh = 0;
        int congestionId = 1;
        int tdsp = 1;
        if(strbillMonth!=null && !strbillMonth.equalsIgnoreCase(""))
            billMonth = Integer.parseInt(strbillMonth)+1;
        if(strEergykWh!=null && !strEergykWh.equalsIgnoreCase(""))
            energykWh = Double.parseDouble(strEergykWh);
        if(strCongestion!=null && !strCongestion.equalsIgnoreCase(""))
            congestionId = Integer.parseInt(strCongestion);
        if(strtdsp!=null && !strtdsp.equalsIgnoreCase(""))
            tdsp = Integer.parseInt(strtdsp);
        
        double estimetedkWh = 0;
        int weatherZoneId = 0; 
        MatrixMonthlyWeightsDAO objMatrixMonthlyWeightsDAO = new MatrixMonthlyWeightsDAO();
        TDSPDAO objTDSPDAO = new TDSPDAO();
        weatherZoneId = (objTDSPDAO.getTDSP(tdsp)).getWeatherZone().getWeatherZoneId();
        List lstValues = objMatrixMonthlyWeightsDAO.getValue(profileId, weatherZoneId);
        MatrixMonthlyWeightsVO objMatrixMonthlyWeightsVO = objMatrixMonthlyWeightsDAO.getValue(profileId, weatherZoneId, billMonth);
        Iterator ite = lstValues.iterator();
        while(ite.hasNext())
        {
            estimetedkWh +=  (energykWh * ((MatrixMonthlyWeightsVO)ite.next()).getValue())/objMatrixMonthlyWeightsVO.getValue();
        }
        logger.info("GOT ESTIMATED kWh");
        return estimetedkWh;
    }
}


/*
*$Log: ComputeMMPrice.java,v $
*Revision 1.2  2008/11/21 09:46:39  jvediyappan
*Trieagle changed as MXEnergy.
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
*Revision 1.10  2007/09/06 14:03:56  jnadesan
*savings computed
*
*Revision 1.9  2007/09/06 12:37:04  jnadesan
*DWR Concept is implemented
*
*Revision 1.8  2007/09/04 09:16:29  sramasamy
*Log message is added for log file.
*
*Revision 1.7  2007/08/29 07:41:47  jnadesan
*energy kwh computed  with decimal
*
*Revision 1.6  2007/08/29 05:45:12  jnadesan
*print removed
*
*Revision 1.5  2007/08/28 11:17:49  jnadesan
*imports organized
*
*Revision 1.4  2007/08/27 15:12:45  kduraisamy
*computaion changed
*
*Revision 1.3  2007/08/27 06:37:59  spandiyarajan
*removed unwanted imports
*
*Revision 1.2  2007/08/27 04:42:27  jnadesan
*computaion for mAtrixPricer page
*
*
*/