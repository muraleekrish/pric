/*
 * Created on May 8, 2007
 * 
 * Class Name AggLoadProfileAction.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.aggregatorlp;

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
public class AggLoadProfileAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String action="failure";
        return mapping.findForward(action);
    }
}


/*
*$Log: AggLoadProfileAction.java,v $
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:30  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/05/08 13:47:03  jnadesan
*load profile chart plotted by esiid wise and export excel option added
*
*
*/