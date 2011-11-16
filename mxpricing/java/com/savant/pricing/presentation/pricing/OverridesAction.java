/*
 * Created on Mar 7, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OverridesAction extends Action{

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String action="failure";
        if(form instanceof OverridesForm)
        {
            
            OverridesForm frm = (OverridesForm)form;
            if(frm.getFormActions().equalsIgnoreCase("searchCustomer"))
            {
                action = "searchCustomer";
              
            }
        }
        return mapping.findForward(action);
    }

    
    


  
}
