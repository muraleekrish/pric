/*
 * @ ExpressPricingDetails.java	Aug 27, 2007
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. 
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use it 
 * only in accordance with the terms of the license agreement you entered into with Savant Technologies Pvt Ltd.
 * 
 */
 
package com.savant.pricing.matrixpricing.pdf;

import java.util.ArrayList;
import java.util.List;

/*
 * Class description goes here.
 * 
 * @author	spandiyarajan
 * 
 */

public class ExpressPricingDetails
{
    private String custName = "";
    private String monthlyDemandkVA = "";
    private String refNumber = "";
    private String tdsp = "";
    private String monthlyEnergykWh = "";
    private String salesRepName = "";
    private String cogZone = "";
    private String annualkWh = "";
    private String salesMgrName = "";
    private String billMonth = "";
    private String mnthCustCharge = "";
    private String pricingAnalyst = "";
    private String term = "";
    private String loadFactor = "";
    private String paymentHistory = "";
    private String depositAmt = "";
    
    private List termReport = new ArrayList();
    
    public ExpressPricingDetails(String custName, String monthlyDemandkVA, String refNumber, String tdsp, String monthlyEnergykWh, String salesRepName, String cogZone, String annualkWh, String salesMgrName, String billMonth, String mnthCustCharge, String pricingAnalyst, String term, String loadFactor, String paymentHistory, String depositAmt, List termReport) 
    {
        this.custName = custName;
        this.monthlyDemandkVA = monthlyDemandkVA;
        this.refNumber = refNumber;
        this.tdsp = tdsp;
        this.monthlyEnergykWh =  monthlyEnergykWh;
        this.salesRepName = salesRepName;
        this.cogZone = cogZone;
        this.annualkWh = annualkWh;
        this.salesMgrName = salesMgrName;
        this.billMonth = billMonth;
        this.mnthCustCharge = mnthCustCharge;
        this.pricingAnalyst = pricingAnalyst;
        this.term = term;
        this.loadFactor = loadFactor;
        this.paymentHistory = paymentHistory;
        this.depositAmt = depositAmt;
        this.termReport = termReport;
    }

    
    public String getAnnualkWh()
    {
        return annualkWh;
    }
    public void setAnnualkWh(String annualkWh)
    {
        this.annualkWh = annualkWh;
    }
    public String getBillMonth()
    {
        return billMonth;
    }
    public void setBillMonth(String billMonth)
    {
        this.billMonth = billMonth;
    }
    public String getCogZone()
    {
        return cogZone;
    }
    public void setCogZone(String cogZone)
    {
        this.cogZone = cogZone;
    }
    public String getTerm()
    {
        return term;
    }
    public void setTerm(String term)
    {
        this.term = term;
    }
    public String getCustName()
    {
        return custName;
    }
    public void setCustName(String custName)
    {
        this.custName = custName;
    }
    public String getDepositAmt()
    {
        return depositAmt;
    }
    public void setDepositAmt(String depositAmt)
    {
        this.depositAmt = depositAmt;
    }
    public String getLoadFactor()
    {
        return loadFactor;
    }
    public void setLoadFactor(String loadFactor)
    {
        this.loadFactor = loadFactor;
    }
    public String getMnthCustCharge()
    {
        return mnthCustCharge;
    }
    public void setMnthCustCharge(String mnthCustCharge)
    {
        this.mnthCustCharge = mnthCustCharge;
    }
    public String getMonthlyDemandkVA()
    {
        return monthlyDemandkVA;
    }
    public void setMonthlyDemandkVA(String monthlyDemandkVA)
    {
        this.monthlyDemandkVA = monthlyDemandkVA;
    }
    public String getMonthlyEnergykWh()
    {
        return monthlyEnergykWh;
    }
    public void setMonthlyEnergykWh(String monthlyEnergykWh)
    {
        this.monthlyEnergykWh = monthlyEnergykWh;
    }
    public String getPaymentHistory()
    {
        return paymentHistory;
    }
    public void setPaymentHistory(String paymentHistory)
    {
        this.paymentHistory = paymentHistory;
    }
    public String getPricingAnalyst()
    {
        return pricingAnalyst;
    }
    public void setPricingAnalyst(String pricingAnalyst)
    {
        this.pricingAnalyst = pricingAnalyst;
    }
    public String getRefNumber()
    {
        return refNumber;
    }
    public void setRefNumber(String refNumber)
    {
        this.refNumber = refNumber;
    }
    public String getSalesMgrName()
    {
        return salesMgrName;
    }
    public void setSalesMgrName(String salesMgrName)
    {
        this.salesMgrName = salesMgrName;
    }
    public String getSalesRepName()
    {
        return salesRepName;
    }
    public void setSalesRepName(String salesRepName)
    {
        this.salesRepName = salesRepName;
    }
    public String getTdsp()
    {
        return tdsp;
    }
    public void setTdsp(String tdsp)
    {
        this.tdsp = tdsp;
    }
    
    public List getTermReport()
    {
        return termReport;
    }
    public void setTermReport(List termReport)
    {
        this.termReport = termReport;
    }
}


/*
*$Log: ExpressPricingDetails.java,v $
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/09/10 12:29:46  spandiyarajan
**** empty log message ***
*
*Revision 1.1  2007/08/29 07:23:55  spandiyarajan
*makepdf initially commited
*
*
*/