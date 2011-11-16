/*
 * Created on Mar 23, 2007
 * 
 * Class Name PcDealLeversAction.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pconfig.deallevers;

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
public class PcDealLeversAction extends Action{
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action="failure";
        return mapping.findForward(action);
    }
}

/*
*$Log: PcDealLeversAction.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:30  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/05/26 06:45:47  spandiyarajan
*added message for browserheight
*
*Revision 1.2  2007/04/12 13:58:05  kduraisamy
*unwanted println commented.
*
*Revision 1.1  2007/03/23 11:20:39  jnadesan
*System DealLevers added
*
*
*/