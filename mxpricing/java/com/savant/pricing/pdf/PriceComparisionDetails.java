/*
 * @(#) PriceComparisionDetails.java	Aug 21, 2007
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
 * @author		spandiyarajan
 * 
 */

public class PriceComparisionDetails
{
    private String components = "";
    private String term1 = "";
    private String term2 = "";
    private String term3 = "";
    private String term4 = "";
    private String term5 = "";
    
    public PriceComparisionDetails(String components, String term1,
            String term2, String term3, String term4, String term5)
    {
        super();
        this.components = components;
        this.term1 = term1;
        this.term2 = term2;
        this.term3 = term3;
        this.term4 = term4;
        this.term5 = term5;
    }
    /**
     * 
     */
    public PriceComparisionDetails()
    {

        // TODO Auto-generated constructor stub
    }
    
    public String getComponents()
    {
        return components;
    }
    public void setComponents(String components)
    {
        this.components = components;
    }
    public String getTerm1()
    {
        return term1;
    }
    public void setTerm1(String term1)
    {
        this.term1 = term1;
    }
    public String getTerm2()
    {
        return term2;
    }
    public void setTerm2(String term2)
    {
        this.term2 = term2;
    }
    public String getTerm3()
    {
        return term3;
    }
    public void setTerm3(String term3)
    {
        this.term3 = term3;
    }
    public String getTerm4()
    {
        return term4;
    }
    public void setTerm4(String term4)
    {
        this.term4 = term4;
    }
    public String getTerm5()
    {
        return term5;
    }
    public void setTerm5(String term5)
    {
        this.term5 = term5;
    }
}


/*
*$Log: PriceComparisionDetails.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/11/22 05:26:49  spandiyarajan
*initial commit
*
*
*/