/*
 * 
 * SummaryAction.java    Aug 6, 2007
 * 
 * Copyright (c) Savant Technologies Pvt Ltd.
 * 'Savant House' 127 Chamiers Road, Nandanam, Chennai-600 035. INDIA 
 * All rights reserved.

 * @author: jnadesan
 * @company: Savant Technologies
 * @client: MX Energy
 * @version: 
 * @Description: 
 * 
 */
package com.savant.pricing.summary;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 */
public class SummaryAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String action = "failure";
        return mapping.findForward(action);
    }
}
/*
 *$Log: SummaryAction.java,v $
 *Revision 1.2  2008/11/21 09:47:53  jvediyappan
 *Trieagle changed as MXEnergy.
 *
 *Revision 1.1  2007/12/07 06:06:39  jvediyappan
 *initial commit.
 *
 *Revision 1.1  2007/10/30 05:51:59  jnadesan
 *Initial commit.
 *
 *Revision 1.1  2007/10/26 15:19:30  jnadesan
 *initail MXEP commit
 *
 *Revision 1.1  2007/08/06 15:56:40  jnadesan
 *initial commit for history page
 *
 *
 */