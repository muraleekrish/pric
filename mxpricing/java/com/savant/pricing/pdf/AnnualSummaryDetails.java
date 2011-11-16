/*
 * @(#) AnnualSummaryDetails.java	Aug 21, 2007
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. 
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use it 
 * only in accordance with the terms of the license agreement you entered into with Savant Technologies Pvt Ltd.
 * 
 */
 
package com.savant.pricing.pdf;


/*
 * Class description goes here.
 * 
 * @author	spandiyarajan
 * 
 */

public class AnnualSummaryDetails
{
    private float kwh = 0;
    private String month = "";
    
    public AnnualSummaryDetails(float kWh, String month)
    {
        this.kwh = kWh;
        this.month = month;
    }
    
    public float getKwh()
    {
        return kwh;
    }
    public void setKwh(float kwh)
    {
        this.kwh = kwh;
    }
    public String getMonth()
    {
        return month;
    }
    public void setMonth(String month)
    {
        this.month = month;
    }
}


/*
*$Log: AnnualSummaryDetails.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/22 11:54:03  jnadesan
*kwh changed
*
*Revision 1.1  2007/11/22 05:26:02  spandiyarajan
*annual chart deatils initial commit
*
*
*/