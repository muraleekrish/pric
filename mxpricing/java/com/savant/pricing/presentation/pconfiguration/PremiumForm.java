/*
 * Created on May 17, 2007
 * 
 * Class Name PremiumForm.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pconfiguration;

import org.apache.struts.action.ActionForm;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PremiumForm extends ActionForm {
    private String formActions = "list";
    
    /**
     * @return Returns the formActions.
     */
    public String getFormActions()
    {
        return formActions;
    }
    /**
     * @param formActions The formActions to set.
     */
    public void setFormActions(String formActions)
    {
        this.formActions = formActions;
    }
    
}


/*
*$Log: PremiumForm.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/05/18 03:38:14  jnadesan
*initial commit
*
*
*/