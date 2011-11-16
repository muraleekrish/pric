/*
 * Created on Apr 2, 2007
 *
 * ClassName	:  	EssiidForm.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pricing;

import org.apache.struts.action.ActionForm;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EcompoForm extends ActionForm
{

    private String formActions="";
    private String prsCustId="";
    private String energyComponents="";
    private String unselenergyComponents="";
    private String possibleEngComp = "";
    private String selectedEngComp = "";
    
    public String getEnergyComponents()
    {
        return energyComponents;
    }
    public void setEnergyComponents(String energyComponents)
    {
        this.energyComponents = energyComponents;
    }
    public String getFormActions()
    {
        return formActions;
    }
    public void setFormActions(String formActions)
    {
        this.formActions = formActions;
    }
    public String getPossibleEngComp()
    {
        return possibleEngComp;
    }
    public void setPossibleEngComp(String possibleEngComp)
    {
        this.possibleEngComp = possibleEngComp;
    }
    public String getPrsCustId()
    {
        return prsCustId;
    }
    public void setPrsCustId(String prsCustId)
    {
        this.prsCustId = prsCustId;
    }
    public String getSelectedEngComp()
    {
        return selectedEngComp;
    }
    public void setSelectedEngComp(String selectedEngComp)
    {
        this.selectedEngComp = selectedEngComp;
    }
    public String getUnselenergyComponents()
    {
        return unselenergyComponents;
    }
    public void setUnselenergyComponents(String unselenergyComponents)
    {
        this.unselenergyComponents = unselenergyComponents;
    }
}

/*
*$Log: EcompoForm.java,v $
*Revision 1.1  2008/01/30 13:44:12  tannamalai
*new page added for energy components
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
*Revision 1.6  2007/05/30 11:33:09  jnadesan
*unwanted lines removed
*
*Revision 1.5  2007/04/07 12:51:53  rraman
*extra properties included
*
*Revision 1.4  2007/04/07 11:57:03  rraman
*extra properties included
*
*Revision 1.2  2007/04/03 10:01:37  rraman
*new form for esiid page
*
*Revision 1.1  2007/04/02 14:34:31  rraman
*new form and action created
*
*
*/