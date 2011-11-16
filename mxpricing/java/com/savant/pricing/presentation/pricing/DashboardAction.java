/*
 * Created on Jan 24, 2007
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

import com.savant.pricing.common.BuildConfig;

/**
 * @author srajappan
 *o
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DashboardAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String action="failure";
        if(form instanceof DashboardForm)
        {
            DashboardForm frm = (DashboardForm)form;
            if(frm.getFormActions().equalsIgnoreCase("cpe"))
                action="cpe";
        }
        if(BuildConfig.DMODE)
            System.out.println("DashBoard :  ");
        return mapping.findForward(action);
    }
}
