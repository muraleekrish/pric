/*
 * Created on Feb 1, 2008
 *
 * ClassName	:  	CostofCapitalAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.pconfig.deallevers;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CostofCapitalAction extends Action
{ public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
{
    String action="failure";
    return mapping.findForward(action);
}}

/*
*$Log: CostofCapitalAction.java,v $
*Revision 1.1  2008/02/06 06:42:32  tannamalai
*cost of capital added
*
*
*/