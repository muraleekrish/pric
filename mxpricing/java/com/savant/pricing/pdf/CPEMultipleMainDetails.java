/*
 * Created on Apr 5, 2007
 *
 * ClassName	:  	CPEMultipleCustDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.pdf;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.savant.pricing.common.NumberUtil;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CPEMultipleMainDetails
{
    private String custName = "";
    private float loadFactor = 0;
    private float annualConsumption = 0;
    private int esiidCount = 0;
    private String forwdCurvePrice = null;
    private String contractStMnth = null;
    private String gaspriceDate ="";
    private String gasprice = "";
    private List termDetails = new ArrayList();
    private List annualDetails = new ArrayList();
    
    /**
     * 
     */ 
    public CPEMultipleMainDetails(String custName,float loadfactor,float annualCun,int esiid,float forwdPrice,Date stmnth,Date gaspriceDate,float gasprice,List termDetails, List annualDetails)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        NumberFormat nf = NumberUtil.doubleFraction();
        NumberFormat nf1 = NumberFormat.getInstance();
        nf1.setMaximumFractionDigits(0);
        nf1.setMinimumFractionDigits(0);
        this.custName = custName;
        this.loadFactor = loadfactor/100;
        this.annualConsumption = annualCun;
        this.esiidCount = esiid;
        this.forwdCurvePrice = nf.format(forwdPrice);
        this.contractStMnth = sdf.format(stmnth);
        this.gaspriceDate = sdf.format(gaspriceDate);
        this.termDetails.addAll(termDetails);
        this.gasprice = nf.format(gasprice);
        this.annualDetails = annualDetails;
    }
   
    public List getTermDetails() {
        return termDetails;
    }
    public void setTermDetails(List termDetails) {
        this.termDetails = termDetails;
    }
    public float getAnnualConsumption()
    {
        return annualConsumption;
    }
    
    public String getGasprice()
    {
        return gasprice;
    }
    public void setGasprice(String gasprice)
    {
        this.gasprice = gasprice;
    }
    public void setAnnualConsumption(float annualConsumption)
    {
        this.annualConsumption = annualConsumption;
    }
    
    public String getCustName()
    {
        return custName;
    }
    public void setCustName(String custName)
    {
        this.custName = custName;
    }
    public int getEsiidCount()
    {
        return esiidCount;
    }
    public void setEsiidCount(int esiidCount)
    {
        this.esiidCount = esiidCount;
    }
    public String getContractStMnth()
    {
        return contractStMnth;
    }
    public String getForwdCurvePrice()
    {
        return forwdCurvePrice;
    }
    public String getGaspriceDate()
    {
        return gaspriceDate;
    }
    public void setContractStMnth(String contractStMnth)
    {
        this.contractStMnth = contractStMnth;
    }
    public void setForwdCurvePrice(String forwdCurvePrice)
    {
        this.forwdCurvePrice = forwdCurvePrice;
    }
    public void setGaspriceDate(String gaspriceDate)
    {
        this.gaspriceDate = gaspriceDate;
    }
    public float getLoadFactor()
    {
        return loadFactor;
    }
    public void setLoadFactor(float loadFactor)
    {
        this.loadFactor = loadFactor;
    }
    public List getAnnualDetails()
    {
        return annualDetails;
    }
    public void setAnnualDetails(List annualDetails)
    {
        this.annualDetails = annualDetails;
    }
}

/*
*$Log: CPEMultipleMainDetails.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.4  2007/11/27 05:02:57  jnadesan
*format changed
*
*Revision 1.3  2007/11/26 18:11:09  jnadesan
*values are formatted
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
*Revision 1.3  2007/05/15 05:19:56  jnadesan
*unwanted fields are remove
*
*Revision 1.2  2007/04/17 15:17:40  jnadesan
*CPE And Contrcat details for FAR
*
*Revision 1.1  2007/04/05 11:47:46  kduraisamy
*resources forcpe multiple term
*
*
*/