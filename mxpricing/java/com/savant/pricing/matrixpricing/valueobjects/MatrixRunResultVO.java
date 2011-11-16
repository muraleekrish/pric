/*
 * 
 * MatrixRunResultVO.java    Aug 22, 2007
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

package com.savant.pricing.matrixpricing.valueobjects;

import java.io.Serializable;
import com.savant.pricing.valueobjects.CongestionZonesVO;
import com.savant.pricing.valueobjects.LoadProfileTypesVO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;
import com.savant.pricing.valueobjects.TDSPVO;

/**
 * 
 */
public class MatrixRunResultVO implements Serializable
{
    private String priceRunRefNo;
    private ProspectiveCustomerVO prospectiveCust;
    private String esiId;
    private TDSPVO tdsp;
    private LoadProfileTypesVO loadProfile;
    private CongestionZonesVO congestion;
    private float energyOnlyPrice;
    private float tdspCharge;
    private float ancCharge;
    private float custCharge;
    private int term;
    
    
    public float getAncCharge()
    {
        return ancCharge;
    }
    public CongestionZonesVO getCongestion()
    {
        return congestion;
    }
    public float getEnergyOnlyPrice()
    {
        return energyOnlyPrice;
    }
    public String getEsiId()
    {
        return esiId;
    }
    public LoadProfileTypesVO getLoadProfile()
    {
        return loadProfile;
    }
    public String getPriceRunRefNo()
    {
        return priceRunRefNo;
    }
    public ProspectiveCustomerVO getProspectiveCust()
    {
        return prospectiveCust;
    }
    public TDSPVO getTdsp()
    {
        return tdsp;
    }
    public float getTdspCharge()
    {
        return tdspCharge;
    }
    public int getTerm()
    {
        return term;
    }
    public void setAncCharge(float ancCharge)
    {
        this.ancCharge = ancCharge;
    }
    public void setCongestion(CongestionZonesVO congestion)
    {
        this.congestion = congestion;
    }
    public void setEnergyOnlyPrice(float energyOnlyPrice)
    {
        this.energyOnlyPrice = energyOnlyPrice;
    }
    public void setEsiId(String esiId)
    {
        this.esiId = esiId;
    }
    public void setLoadProfile(LoadProfileTypesVO loadProfile)
    {
        this.loadProfile = loadProfile;
    }
    public void setPriceRunRefNo(String priceRunRefNo)
    {
        this.priceRunRefNo = priceRunRefNo;
    }
    public void setProspectiveCust(ProspectiveCustomerVO prospectiveCust)
    {
        this.prospectiveCust = prospectiveCust;
    }
    public void setTdsp(TDSPVO tdsp)
    {
        this.tdsp = tdsp;
    }
    public void setTdspCharge(float tdspCharge)
    {
        this.tdspCharge = tdspCharge;
    }
    public void setTerm(int term)
    {
        this.term = term;
    }
    public float getCustCharge()
    {
        return custCharge;
    }
    public void setCustCharge(float custCharge)
    {
        this.custCharge = custCharge;
    }
}


/*
*$Log: MatrixRunResultVO.java,v $
*Revision 1.2  2008/11/21 09:47:03  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:24  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/09/06 12:01:01  spandiyarajan
*get the custCharge value
*
*Revision 1.1  2007/08/23 07:36:34  jnadesan
*initial commit MMPrice entry
*
*
*/