/*
 * 
 * MxContractBDetails.java    Nov 22, 2007
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

package com.savant.pricing.pdf;

import java.util.List;

/**
 * 
 */
public class MxContractBDetails
{
    private String productName;
    private String service;
    private String termandRenewal;
    private String price;
    private String pointOfDelivery;
    private String billingPaymnet;
    private String creditandDeposit;
    private String refusalOfservice;
    private String materialChange;
    private String earlyTermination;
    private String regulatoryCharge;
    private String forceMajeure;
    private String limitationOfliability;
    private String representationandWarranties;
    private String requestToTDSP;
    private String goveringLaw;
    private String entireAgreement;
    private String responsibilitiesofTDSP;
    private String tdspContacs;
    private String tdspAddress;
    private List lstCustomerDetails;
    private List lstEsiid;
    
    
    /**
     * 
     */
    public MxContractBDetails(String productName,String service,String termandRenewal,String price,String pointOfDelivery,String billingPaymnet, String creditandDeposit,String refusalOfservice, String materialChange, String earlyTermination, String regulatoryCharge, String forceMajeure, String limitationOfliability, String representationandWarranties, String requestToTDSP, String goveringLaw, String entireAgreement, String responsibilitiesofTDSP, String tdspContacs, String tdspAddress, List lstCustomerDetails, List lstEsiid)
    {
        this.productName = productName;
        this.service = service;
        this.termandRenewal = termandRenewal;
        this.price = price ;
        this.pointOfDelivery = pointOfDelivery;
        this.billingPaymnet = billingPaymnet;
        this.creditandDeposit = creditandDeposit;
        this.refusalOfservice = refusalOfservice;
        this.materialChange = materialChange;
        this.earlyTermination = earlyTermination;
        this.regulatoryCharge = regulatoryCharge;
        this.forceMajeure = forceMajeure;
        this.limitationOfliability = limitationOfliability;
        this.representationandWarranties =representationandWarranties;
        this.requestToTDSP = requestToTDSP;
        this.goveringLaw = goveringLaw;
        this.entireAgreement = entireAgreement;
        this.responsibilitiesofTDSP = responsibilitiesofTDSP;
        this.tdspContacs = tdspContacs;
        this.tdspAddress = tdspAddress;
        this.lstCustomerDetails = lstCustomerDetails;
        this.lstEsiid = lstEsiid;
    }
    /**
     * 
     */
    public MxContractBDetails()
    {
        // TODO Auto-generated constructor stub
    }
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    public String getBillingPaymnet()
    {
        return billingPaymnet;
    }
    public String getCreditandDeposit()
    {
        return creditandDeposit;
    }
    public String getEarlyTermination()
    {
        return earlyTermination;
    }
    public String getEntireAgreement()
    {
        return entireAgreement;
    }
    public String getForceMajeure()
    {
        return forceMajeure;
    }
    public String getGoveringLaw()
    {
        return goveringLaw;
    }
    public String getLimitationOfliability()
    {
        return limitationOfliability;
    }
    public List getLstCustomerDetails()
    {
        return lstCustomerDetails;
    }
    public List getLstEsiid()
    {
        return lstEsiid;
    }
    public String getMaterialChange()
    {
        return materialChange;
    }
    public String getPointOfDelivery()
    {
        return pointOfDelivery;
    }
    public String getPrice()
    {
        return price;
    }
    public String getRefusalOfservice()
    {
        return refusalOfservice;
    }
    public String getRegulatoryCharge()
    {
        return regulatoryCharge;
    }
    public String getRepresentationandWarranties()
    {
        return representationandWarranties;
    }
    public String getRequestToTDSP()
    {
        return requestToTDSP;
    }
    public String getResponsibilitiesofTDSP()
    {
        return responsibilitiesofTDSP;
    }
    public String getService()
    {
        return service;
    }
    public String getTdspAddress()
    {
        return tdspAddress;
    }
    public String getTdspContacs()
    {
        return tdspContacs;
    }
    public String getTermandRenewal()
    {
        return termandRenewal;
    }
    public void setBillingPaymnet(String billingPaymnet)
    {
        this.billingPaymnet = billingPaymnet;
    }
    public void setCreditandDeposit(String creditandDeposit)
    {
        this.creditandDeposit = creditandDeposit;
    }
    public void setEarlyTermination(String earlyTermination)
    {
        this.earlyTermination = earlyTermination;
    }
    public void setEntireAgreement(String entireAgreement)
    {
        this.entireAgreement = entireAgreement;
    }
    public void setForceMajeure(String forceMajeure)
    {
        this.forceMajeure = forceMajeure;
    }
    public void setGoveringLaw(String goveringLaw)
    {
        this.goveringLaw = goveringLaw;
    }
    public void setLimitationOfliability(String limitationOfliability)
    {
        this.limitationOfliability = limitationOfliability;
    }
    public void setLstCustomerDetails(List lstCustomerDetails)
    {
        this.lstCustomerDetails = lstCustomerDetails;
    }
    public void setLstEsiid(List lstEsiid)
    {
        this.lstEsiid = lstEsiid;
    }
    public void setMaterialChange(String materialChange)
    {
        this.materialChange = materialChange;
    }
    public void setPointOfDelivery(String pointOfDelivery)
    {
        this.pointOfDelivery = pointOfDelivery;
    }
    public void setPrice(String price)
    {
        this.price = price;
    }
    public void setRefusalOfservice(String refusalOfservice)
    {
        this.refusalOfservice = refusalOfservice;
    }
    public void setRegulatoryCharge(String regulatoryCharge)
    {
        this.regulatoryCharge = regulatoryCharge;
    }
    public void setRepresentationandWarranties(String representationandWarranties)
    {
        this.representationandWarranties = representationandWarranties;
    }
    public void setRequestToTDSP(String requestToTDSP)
    {
        this.requestToTDSP = requestToTDSP;
    }
    public void setResponsibilitiesofTDSP(String responsibilitiesofTDSP)
    {
        this.responsibilitiesofTDSP = responsibilitiesofTDSP;
    }
    public void setService(String service)
    {
        this.service = service;
    }
    public void setTdspAddress(String tdspAddress)
    {
        this.tdspAddress = tdspAddress;
    }
    public void setTdspContacs(String tdspContacs)
    {
        this.tdspContacs = tdspContacs;
    }
    public void setTermandRenewal(String termandRenewal)
    {
        this.termandRenewal = termandRenewal;
    }
}


/*
*$Log: MxContractBDetails.java,v $
*Revision 1.2  2008/11/21 09:47:11  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/11/23 08:44:06  jnadesan
*initial commit
*
*
*/