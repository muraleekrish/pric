/*
 * PricingCommissionForm.java	Oct 8, 2007
 *
 * Copyright (c) Savant Technologies Pvt Ltd.
 * 'Savant House' 127 Chamiers Road, Nandanam, Chennai-600 035. INDIA 
 * All rights reserved.

 * @author		: spandiyarajan
 * @company		: Savant Technologies
 * @client		: MX Energy
 * @version		: 
 * @Description	:
 * 
 */
 
package com.savant.pricing.presentation.pricing;

import org.apache.struts.action.ActionForm;

public class PricingCommissionForm extends ActionForm
{
    private String formAction="";
    private String prcCustId="";
    private String prcCommDet="";
    
    public String getFormAction()
    {
        return formAction;
    }
    public void setFormAction(String formAction)
    {
        this.formAction = formAction;
    }
    public String getPrcCustId()
    {
        return prcCustId;
    }
    public void setPrcCustId(String prcCustId)
    {
        this.prcCustId = prcCustId;
    }
    public String getPrcCommDet()
    {
        return prcCommDet;
    }
    public void setPrcCommDet(String prcCommDet)
    {
        this.prcCommDet = prcCommDet;
    }
}


/*
*$Log: PricingCommissionForm.java,v $
*Revision 1.2  2008/11/21 09:47:34  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:49  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/10/11 10:33:04  spandiyarajan
*Pricing commission action and action form files added
*
*
*/