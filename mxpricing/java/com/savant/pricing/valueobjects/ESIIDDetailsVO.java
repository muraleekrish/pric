/*
 * Created on Apr 2, 2007
 *
 * ClassName	:  	ESIIDDetailsVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.valueobjects;

import java.io.Serializable;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ESIIDDetailsVO implements Serializable
{
    private ProspectiveCustomerVO prospectiveCustomer;
    private String esiId;
    private String serviceAddress1;
    private String serviceAddress2;
    private String city;
    private String state;
    private int zip;
    private String county;
    private String tdsp;
    private float dunsNo;
    private String meterReadCycle;
    private String status;
    private String premiseType;
    private String powerRegion;
    private String stationCode;
    private String stationName;
    private String metered;
    private String openServiceOrder;
    private String polrCustClass;
    
    public ESIIDDetailsVO()
    {
    }
    
    
    public float getDunsNo()
    {
        return dunsNo;
    }
    public void setDunsNo(float dunsNo)
    {
        this.dunsNo = dunsNo;
    }
    public String getMetered()
    {
        return metered;
    }
    public void setMetered(String metered)
    {
        this.metered = metered;
    }
    public String getMeterReadCycle()
    {
        return meterReadCycle;
    }
    public void setMeterReadCycle(String meterReadCycle)
    {
        this.meterReadCycle = meterReadCycle;
    }
    public String getOpenServiceOrder()
    {
        return openServiceOrder;
    }
    public void setOpenServiceOrder(String openServiceOrder)
    {
        this.openServiceOrder = openServiceOrder;
    }
    public String getPolrCustClass()
    {
        return polrCustClass;
    }
    public void setPolrCustClass(String polrCustClass)
    {
        this.polrCustClass = polrCustClass;
    }
    public String getPowerRegion()
    {
        return powerRegion;
    }
    public void setPowerRegion(String powerRegion)
    {
        this.powerRegion = powerRegion;
    }
    public String getPremiseType()
    {
        return premiseType;
    }
    public void setPremiseType(String premiseType)
    {
        this.premiseType = premiseType;
    }
    public String getServiceAddress1()
    {
        return serviceAddress1;
    }
    public void setServiceAddress1(String serviceAddress1)
    {
        this.serviceAddress1 = serviceAddress1;
    }
    public String getServiceAddress2()
    {
        return serviceAddress2;
    }
    public void setServiceAddress2(String serviceAddress2)
    {
        this.serviceAddress2 = serviceAddress2;
    }
    public String getStationCode()
    {
        return stationCode;
    }
    public void setStationCode(String stationCode)
    {
        this.stationCode = stationCode;
    }
    public String getStationName()
    {
        return stationName;
    }
    public void setStationName(String stationName)
    {
        this.stationName = stationName;
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public String getCounty()
    {
        return county;
    }
    public void setCounty(String county)
    {
        this.county = county;
    }
    public String getEsiId()
    {
        return esiId;
    }
    public void setEsiId(String esiId)
    {
        this.esiId = esiId;
    }
    public ProspectiveCustomerVO getProspectiveCustomer()
    {
        return prospectiveCustomer;
    }
    public void setProspectiveCustomer(ProspectiveCustomerVO prospectiveCustomer)
    {
        this.prospectiveCustomer = prospectiveCustomer;
    }
    public String getState()
    {
        return state;
    }
    public void setState(String state)
    {
        this.state = state;
    }
    public String getTdsp()
    {
        return tdsp;
    }
    public void setTdsp(String tdsp)
    {
        this.tdsp = tdsp;
    }
    public int getZip()
    {
        return zip;
    }
    public void setZip(int zip)
    {
        this.zip = zip;
    }
}

/*
*$Log: ESIIDDetailsVO.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:45  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/04/07 09:53:02  kduraisamy
*Additional fields for ESIID Details added.
*
*Revision 1.1  2007/04/02 08:59:43  kduraisamy
*initial commit.
*
*
*/