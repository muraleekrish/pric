/*
 * @	TermDetails.java	Aug 27, 2007
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

/*
 * Class description goes here.
 * 
 * @author	spandiyarajan
 * 
 */

public class TermDetails
{
    private int termMonths = 0;
    private String energyPrice = "";
    private String tdspCharge = "";
    private String compPrice = "";
    private String savingsDolar = "";
    private String savingsPercent = "";
    
    public TermDetails(int termMonths, String energyPrice, String tdspCharge, String compPrice, String savingsDolar, String savingsPercent) 
    {
        this.termMonths = termMonths;
        this.energyPrice = energyPrice;
        this.tdspCharge = tdspCharge;
        this.compPrice = compPrice;
        this.savingsDolar = savingsDolar;
        this.savingsPercent =  savingsPercent;
    }
    
    public int getTermMonths()
    {
        return termMonths;
    }
    public void setTermMonths(int termMonths)
    {
        this.termMonths = termMonths;
    }
    public String getCompPrice()
    {
        return compPrice;
    }
    public void setCompPrice(String compPrice)
    {
        this.compPrice = compPrice;
    }
    public String getEnergyPrice()
    {
        return energyPrice;
    }
    public void setEnergyPrice(String energyPrice)
    {
        this.energyPrice = energyPrice;
    }
    public String getSavingsDolar()
    {
        return savingsDolar;
    }
    public void setSavingsDolar(String savingsDolar)
    {
        this.savingsDolar = savingsDolar;
    }
    public String getSavingsPercent()
    {
        return savingsPercent;
    }
    public void setSavingsPercent(String savingsPercent)
    {
        this.savingsPercent = savingsPercent;
    }
    public String getTdspCharge()
    {
        return tdspCharge;
    }
    public void setTdspCharge(String tdspCharge)
    {
        this.tdspCharge = tdspCharge;
    }
}


/*
*$Log: TermDetails.java,v $
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/09/10 12:29:46  spandiyarajan
**** empty log message ***
*
*Revision 1.2  2007/08/30 12:37:17  spandiyarajan
*makepdf changes
*
*Revision 1.1  2007/08/29 07:23:55  spandiyarajan
*makepdf initially commited
*
*
*/