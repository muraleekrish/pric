/*
 * Created on Feb 5, 2007
 *
 * ClassName	:  	PICVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.calculation.valueobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.transferobjects.OnPkOffPkDetails;
import com.savant.pricing.transferobjects.ProfileDetails;
import com.savant.pricing.valueobjects.DayTypesVO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;
import com.savant.pricing.valueobjects.RateCodesVO;
import com.savant.pricing.valueobjects.TDSPRateCodesVO;
import com.savant.pricing.valueobjects.ZipCodeVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PICVO implements Serializable
{
    private HashMap actualAggregation = new HashMap();
    private int picReferenceId;
    private ProspectiveCustomerVO customer;
    private String customerName;
    private String esiId;
    private String address1;
    private String address2;
    private String address3;
    private Date picImportedOn;
    private String loadProfile;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private boolean valid;
    private String meterReadCycle;
    private ZipCodeVO zipCode;
    private RateCodesVO rateCode;
    private float maxkVA;
    private float maxkW;
    private float avg4CPkVA;
    private float avg4CPkW;
    private ProfileDetails profileDetails;
    final String delimiter=",";
    final String lineSeparator=System.getProperty("line.separator");
    
    public PICVO()
    {
    }
    public String getAddress1()
    {
        return address1;
    }
    public void setAddress1(String address1)
    {
        this.address1 = address1;
    }
    public String getAddress2()
    {
        return address2;
    }
    public void setAddress2(String address2)
    {
        this.address2 = address2;
    }
    public String getAddress3()
    {
        return address3;
    }
    public void setAddress3(String address3)
    {
        this.address3 = address3;
    }
    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    public Date getCreatedDate()
    {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }
    public ProspectiveCustomerVO getCustomer()
    {
        return customer;
    }
    public void setCustomer(ProspectiveCustomerVO customer)
    {
        this.customer = customer;
    }
    public String getCustomerName()
    {
        return customerName;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    public String getEsiId()
    {
        return esiId;
    }
    public void setEsiId(String esiId)
    {
        this.esiId = esiId;
    }
    public String getLoadProfile()
    {
        return loadProfile;
    }
    public void setLoadProfile(String loadProfile)
    {
        this.loadProfile = loadProfile;
    }
    
    public String getMeterReadCycle()
    {
        return meterReadCycle;
    }
    public void setMeterReadCycle(String meterReadCycle)
    {
        this.meterReadCycle = meterReadCycle;
    }
    public String getModifiedBy()
    {
        return modifiedBy;
    }
    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }
    public Date getModifiedDate()
    {
        return modifiedDate;
    }
    public void setModifiedDate(Date modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }
    public Date getPicImportedOn()
    {
        return picImportedOn;
    }
    public void setPicImportedOn(Date picImportedOn)
    {
        this.picImportedOn = picImportedOn;
    }
    public int getPicReferenceId()
    {
        return picReferenceId;
    }
    public void setPicReferenceId(int picReferenceId)
    {
        this.picReferenceId = picReferenceId;
    }
    public RateCodesVO getRateCode()
    {
        return rateCode;
    }
    public void setRateCode(RateCodesVO rateCode)
    {
        this.rateCode = rateCode;
    }
    public boolean isValid()
    {
        return valid;
    }
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
    public ZipCodeVO getZipCode()
    {
        return zipCode;
    }
    public void setZipCode(ZipCodeVO zipCode)
    {
        this.zipCode = zipCode;
    }
    public float getAvg4CPkVA()
    {
        return avg4CPkVA;
    }
    public void setAvg4CPkVA(float avg4CPkVA)
    {
        this.avg4CPkVA = avg4CPkVA;
    }
    public float getAvg4CPkW()
    {
        return avg4CPkW;
    }
    public void setAvg4CPkW(float avg4CPkW)
    {
        this.avg4CPkW = avg4CPkW;
    }
    public float getMaxkVA()
    {
        return maxkVA;
    }
    public void setMaxkVA(float maxkVA)
    {
        this.maxkVA = maxkVA;
    }
    public float getMaxkW()
    {
        return maxkW;
    }
    public void setMaxkW(float maxkW)
    {
        this.maxkW = maxkW;
    }
    public void setProfileDetails(ProfileDetails objProfileDetails)
    {
        this.profileDetails = objProfileDetails; 
    }
    
    public ProfileDetails getProfileDetails()
    {
        if(null == this.profileDetails)
        {
            String prof[]=this.loadProfile.split("_");
            profileDetails = new ProfileDetails();
            profileDetails.setProfileString(this.loadProfile);
            profileDetails.setProfileType(prof[0]);
            profileDetails.setLoadProfile(prof[0]+"_"+prof[1]);
            profileDetails.setWeatherZoneCode(prof[1]);
            profileDetails.setMeterType(prof[2]);
            Session objSession = null;
            try
            {
                objSession = HibernateUtil.getSession();
                Query objQuery = objSession.createQuery("select weatherzonevo.weatherZoneId, weatherzonevo.congestionZone.congestionZoneId from WeatherZonesVO as weatherzonevo where weatherzonevo.weatherZoneCode = ?");
                objQuery.setString(0,profileDetails.getWeatherZoneCode());
                Object[] innerRow = (Object[]) objQuery.uniqueResult();
                profileDetails.setWeatherZoneId(((Integer)innerRow[0]).intValue());
                profileDetails.setCongestionZoneId(((Integer)innerRow[1]).intValue());
                objQuery = objSession.createQuery("select loadprofilevo.profileIdentifier from LoadProfileTypesVO as loadprofilevo where loadprofilevo.profileType=?");
                objQuery.setString(0,prof[0]);
                Iterator itr = objQuery.iterate();
                
                if(itr.hasNext())
                    profileDetails.setProfileTypeId(((Integer)itr.next()).intValue());
            }
            catch(HibernateException e)
            {
                e.printStackTrace();
            }
            finally
            {
                objSession.close();
            }
        }
        return profileDetails;
    }
    
    public float getCharge(int chargeTypeId)
    {
        Iterator itr = this.rateCode.getTdspChargeRates().iterator();
        float charge = 0;
        while(itr.hasNext())
        {
            TDSPChargeRatesVO objTDSPChargeRatesVO = (TDSPChargeRatesVO)itr.next();
            if(objTDSPChargeRatesVO.getTdspChargeName().getTdspChargeIdentifier() == chargeTypeId)
            {
                charge = objTDSPChargeRatesVO.getCharge();
                break;
            }
        }
        return charge;
    }
    public TDSPRateCodesVO getScud()
    {
        Iterator itr = this.rateCode.getTdspRateCodesVo().iterator();
        TDSPRateCodesVO objTDSPRateCodesVO = null;
        if(itr.hasNext())
        {
            objTDSPRateCodesVO = (TDSPRateCodesVO)itr.next();
        }
        return objTDSPRateCodesVO;
    }
    public float getDemandRatchetPercentage()
    {
        Iterator itr = this.rateCode.getPowerFactorRatchet().iterator();
        PowerFactorRatchetVO objPowerFactorRatchetVO = null;
        float demandRatchetPercent = 0;
        if(itr.hasNext())
        {
            objPowerFactorRatchetVO = (PowerFactorRatchetVO)itr.next();
            demandRatchetPercent = objPowerFactorRatchetVO.getDemandRatchetPercent();
        }
        return demandRatchetPercent;
    }
    private StringBuffer registerAggregateVO(String key, ActualUsageAggregationVO objTempActualUsageAggregationVO, StringBuffer sbAggregatedLoadProfile)
    {
        List objList;
        if(this.actualAggregation.containsKey(key))
        {
            objList = (ArrayList)this.actualAggregation.get(key);
        }
        else
        {
            objList = new ArrayList();
            this.actualAggregation.put(key, objList);
        }
        objList.add(objTempActualUsageAggregationVO);
        return this.saveAggregatedLoadProfile(this.getCustomer(), objTempActualUsageAggregationVO.getEsiId(), objTempActualUsageAggregationVO.getMonth(), objTempActualUsageAggregationVO.getHour(), objTempActualUsageAggregationVO.getDayType(), objTempActualUsageAggregationVO.getPerDayUsageWL(), sbAggregatedLoadProfile);
    }
    private StringBuffer saveAggregatedLoadProfile(ProspectiveCustomerVO objProspectiveCustomerVO, String esiId, int month, int hour, DayTypesVO objDayTypesVO, float value, StringBuffer sbAggregatedLoadProfile)
    {
        sbAggregatedLoadProfile = sbAggregatedLoadProfile.append(objProspectiveCustomerVO.getProspectiveCustomerId()).append(delimiter)
        .append(month).append(delimiter)
        .append(hour).append(delimiter)
        .append(objDayTypesVO.getDayTypeId()).append(delimiter)
        .append(value).append(delimiter)
        .append(esiId).append(lineSeparator);
        return sbAggregatedLoadProfile;
    }

    public StringBuffer createAggregateVO(String esiId, float dlf, String loadProfile, int month, int hour, DayTypesVO objDayTypesVO,float usage, int noOfWD, int noOfWE, StringBuffer sbAggregatedLoadProfile)
    {
        ActualUsageAggregationVO objTempActualUsageAggregationVO = new ActualUsageAggregationVO();
        objTempActualUsageAggregationVO.setEsiId(esiId);
        objTempActualUsageAggregationVO.setLoadProfile(loadProfile);
        objTempActualUsageAggregationVO.setMonth(month);
        objTempActualUsageAggregationVO.setHour(hour);
        objTempActualUsageAggregationVO.setDayType(objDayTypesVO);
        objTempActualUsageAggregationVO.setValue(usage);
        objTempActualUsageAggregationVO.setNoOfWD(noOfWD);
        objTempActualUsageAggregationVO.setNoOfWE(noOfWE);
        float lossFactor = 1.0f;
        if(BuildConfig.DMODE)
        {
            System.out.println("loadProfile:"+loadProfile);
            System.out.println("month:"+month);
            System.out.println("hour:"+hour);
            System.out.println("objDayTypesVO:"+objDayTypesVO.getDayType());
            System.out.println("usage:"+usage);
            System.out.println("NoOfWD:"+noOfWD);
            System.out.println("noOfWE:"+noOfWE);
        }
        float tlf = 0;
        
        OnPkOffPkDetails objOnPkOffPkDetails =  (OnPkOffPkDetails)HibernateUtil.hmTLF.get(new Integer(month));
        if(hour <7 || hour >22)
        {
            tlf = objOnPkOffPkDetails.getOffPeakLoss();
        }
        else
        {
            tlf = objOnPkOffPkDetails.getOnPeakLoss();
        }
        
        lossFactor = lossFactor + dlf + tlf;
        
        if(BuildConfig.DMODE)
        {
            System.out.println("TLF----->>"+tlf);
            System.out.println("DLF----->>"+dlf);
            System.out.println("lossFactor----->>"+lossFactor);
        }
       
        
        if((noOfWD+noOfWE)>0)
        {
            objTempActualUsageAggregationVO.setPerDayUsage(usage/(noOfWD+noOfWE));
            objTempActualUsageAggregationVO.setPerDayUsageWL(usage*lossFactor/(noOfWD+noOfWE));
        }
        return this.registerAggregateVO(this.generateKey(esiId, month, objDayTypesVO.getDayTypeId()), objTempActualUsageAggregationVO, sbAggregatedLoadProfile);
    }
    private String generateKey(String esiId, int month, int dayTypeId)
    {
        return esiId+"|"+month+"|"+dayTypeId;
    }
    
    public List getAggregateVOS(String esiId, int month, int dayTypeId)
    {
        return (ArrayList)this.actualAggregation.get(this.generateKey(esiId, month, dayTypeId));
    }
}

/*
*$Log: PICVO.java,v $
*Revision 1.2  2008/03/13 11:02:18  tannamalai
*loss factor added
*
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.22  2007/06/13 04:10:44  kduraisamy
*2005 jar included.
*
*Revision 1.21  2007/05/11 12:57:45  kduraisamy
*aggregated load profile error rectified.
*
*Revision 1.20  2007/05/11 11:42:17  kduraisamy
*aggregated load profile error rectified.
*
*Revision 1.19  2007/05/10 06:53:32  kduraisamy
*aggregated load profiles error rectified.
*
*Revision 1.18  2007/05/07 12:14:34  kduraisamy
*IDR Profile calculation added.
*
*Revision 1.17  2007/04/19 11:44:43  kduraisamy
*Set is changed to List.
*
*Revision 1.16  2007/04/19 11:14:24  kduraisamy
*Set is changed to List.
*
*Revision 1.15  2007/04/18 03:55:48  kduraisamy
*imports organized.
*
*Revision 1.14  2007/04/17 13:48:35  kduraisamy
*price run performance took place.
*
*Revision 1.13  2007/04/17 04:49:39  kduraisamy
*rateCodes set removed from TDSP.
*
*Revision 1.12  2007/04/06 12:32:19  kduraisamy
*TLF AND DLF COMPLETED.
*
*Revision 1.11  2007/04/02 12:25:31  kduraisamy
*loss factor computation removed.
*
*Revision 1.10  2007/03/31 06:41:02  kduraisamy
*losses included.
*
*Revision 1.9  2007/03/16 10:35:12  kduraisamy
*dividedByZero Error Corrected.
*
*Revision 1.8  2007/03/13 08:31:30  kduraisamy
*Ratchet and demand power directly taken from PICVO.
*
*Revision 1.7  2007/03/09 08:52:21  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.6  2007/03/09 04:14:43  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.5  2007/03/08 16:32:23  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.4  2007/02/14 12:45:50  kduraisamy
*esiId wise details stored in static hashMap.
*
*Revision 1.3  2007/02/12 06:03:57  kduraisamy
*unwanted set Removed.
*
*Revision 1.2  2007/02/12 04:49:37  kduraisamy
*unwanted Set removed.
*
*Revision 1.1  2007/02/09 11:56:55  kduraisamy
*pricing core algorithm almost finished.
*
*
*/