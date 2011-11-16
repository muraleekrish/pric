/*
 * 
 * PriceMatrixCustAction.java    Aug 28, 2007
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

package com.savant.pricing.presentation.matrix;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.savant.pricing.matrixpricing.dao.MMCustomersPDFDAO;
import com.savant.pricing.matrixpricing.valueobjects.MMPricingPDFVO;

/**
 * 
 */
public class PriceMatrixCustAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action="failure";
        MMCustomersPDFDAO objMMCustomersPDFDAO = new MMCustomersPDFDAO();
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        boolean result = false;
        if(form instanceof PriceMatrixCustomerForm)
        {
            PriceMatrixCustomerForm frm = (PriceMatrixCustomerForm)form;
            
            if(frm.getFormActions().equalsIgnoreCase("search"))
            {
                action = "success";
            }
            else if(frm.getFormActions().equalsIgnoreCase("delete"))
            {
               MMPricingPDFVO objMMPricingPDFVO = null;
               String refNum = frm.getReferNum();
               try
               {
	               objMMPricingPDFVO = objMMCustomersPDFDAO.getCustFileDetails(refNum);
	               result = objMMCustomersPDFDAO.deleteCustFile(objMMPricingPDFVO);
               }
               catch (Exception e) 
               {
                   errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.general"));
               }
               if(result)
               {
                   action = "success";
                   messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.delete.message","Customer File","'"+refNum+"'"));
                   saveMessages(request,messages);
                   request.setAttribute("message","message");
               }
               else
               {
                   errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.general"));
               }
            }
        }

        if(!errors.isEmpty())
        {
             saveErrors(request, errors);
             request.setAttribute("message","error");
        }
        return mapping.findForward(action);
    }
}


/*
*$Log: PriceMatrixCustAction.java,v $
*Revision 1.2  2008/11/21 09:47:23  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/08/31 07:46:59  spandiyarajan
**** empty log message ***
*
*Revision 1.2  2007/08/31 06:11:54  spandiyarajan
*dekete option added in Customer Files list page
*
*Revision 1.1  2007/08/29 05:47:01  jnadesan
*initial commit
*
*
*/