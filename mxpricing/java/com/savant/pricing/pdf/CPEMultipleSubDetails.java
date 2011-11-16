/*
 * Created on Apr 5, 2007
 *
 * ClassName	:  	CPEMultipleSub.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.pdf;


/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CPEMultipleSubDetails
{

    private int term =0;
    private float contractkWh =0;
    private float commidityAmount =0;
    private float commoditityperkwh = 0;
    private float passthroughAmount = 0;
    private float passthroughperkwh =0;
    private float tax =0;
    private float taxesperkwh =0;
    private float totalAmount = 0;
    private float totalperkwh =0;
    
    public CPEMultipleSubDetails(int term,float contractperkWh,float commidityAmount,float passthroughAmount,float tax)
    {
        this.term = term;
        this.contractkWh = contractperkWh;
        this.commidityAmount = commidityAmount*this.contractkWh;
        this.passthroughAmount = passthroughAmount;
        this.tax = tax;
        this.totalAmount = this.commidityAmount+passthroughAmount+tax;
        this.totalperkwh = this.totalAmount/this.contractkWh;
        this.commoditityperkwh = this.commidityAmount/this.contractkWh;
        this.passthroughperkwh = this.passthroughAmount/this.contractkWh;
        this.taxesperkwh = this.tax/this.contractkWh;
    }

    public float getCommidityAmount()
    {
        return commidityAmount;
    }
    public void setCommidityAmount(float commidityAmount)
    {
        this.commidityAmount = commidityAmount;
    }
    public float getCommoditityperkwh()
    {
        return commoditityperkwh;
    }
    public void setCommoditityperkwh(float commoditityperkwh)
    {
        this.commoditityperkwh = commoditityperkwh;
    }
    public float getContractkWh()
    {
        return contractkWh;
    }
    public void setContractkWh(float contractkWh)
    {
        this.contractkWh = contractkWh;
    }
    public float getPassthroughAmount()
    {
        return passthroughAmount;
    }
    public void setPassthroughAmount(float passthroughAmount)
    {
        this.passthroughAmount = passthroughAmount;
    }
    public float getPassthroughperkwh()
    {
        return passthroughperkwh;
    }
    public void setPassthroughperkwh(float passthroughperkwh)
    {
        this.passthroughperkwh = passthroughperkwh;
    }
    public float getTax()
    {
        return tax;
    }
    public void setTax(float tax)
    {
        this.tax = tax;
    }
    public float getTaxesperkwh()
    {
        return taxesperkwh;
    }
    public void setTaxesperkwh(float taxesperkwh)
    {
        this.taxesperkwh = taxesperkwh;
    }
    public int getTerm()
    {
        return term;
    }
    public void setTerm(int term)
    {
        this.term = term;
    }
    public float getTotalAmount()
    {
        return totalAmount;
    }
    public void setTotalAmount(float totalAmount)
    {
        this.totalAmount = totalAmount;
    }
    public float getTotalperkwh()
    {
        return totalperkwh;
    }
    public void setTotalperkwh(float totalperkwh)
    {
        this.totalperkwh = totalperkwh;
    }
}

/*
*$Log: CPEMultipleSubDetails.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/04/18 03:56:26  kduraisamy
*imports organized.
*
*Revision 1.1  2007/04/05 11:47:46  kduraisamy
*resources forcpe multiple term
*
*
*/