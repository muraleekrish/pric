/*
 * Created on Apr 21, 2007
 * 
 * Class Name RunListErrorAction.java
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
public class RunListErrorAction extends Action{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String action="failure";
        if(form instanceof RunListErrorForm)
        {
            RunListErrorForm frm = (RunListErrorForm)form;
            if(frm.getFormAction().equalsIgnoreCase("back"))
                action="back";
        }
        return mapping.findForward(action);
    }
}


/*
*$Log: RunListErrorAction.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:49  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/22 15:45:26  jnadesan
*page designed for showing error while run
*
*
*/