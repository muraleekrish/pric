/*
 * Created on Feb 1, 2008
 *
 * ClassName	:  	CostofCapitalEditAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.pconfig.deallevers;

import java.text.DecimalFormat;
import java.text.NumberFormat;


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

import com.savant.pricing.calculation.valueobjects.CostOfCapitalVO;
import com.savant.pricing.calculation.valueobjects.UOMVO;
import com.savant.pricing.dao.CostOfCapitalDAO;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CostofCapitalEditAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action="failure";
        
        if (form instanceof CostofCapitalEditForm)
        {
            CostofCapitalEditForm frm = (CostofCapitalEditForm)form;
            NumberFormat nf = new DecimalFormat("0.0000##");
            ActionErrors errors = new ActionErrors();
            ActionMessages messages = new ActionMessages();
            CostOfCapitalDAO  objCostOfCapitalDAO = new CostOfCapitalDAO();
            CostOfCapitalVO objCostOfCapitalVO = new CostOfCapitalVO();
            try
            {
                if((frm.getFormAction()).equalsIgnoreCase("edit"))
                { 
                    objCostOfCapitalVO = objCostOfCapitalDAO.getCoc(Integer.parseInt(request.getParameter("cocId")));
                    frm.setCocName(objCostOfCapitalVO.getCocName());
                    frm.setCocId(objCostOfCapitalVO.getCocId());
                    if(objCostOfCapitalVO.getUnit().getUomIdentifier()==10)
                        frm.setValue(String.valueOf(objCostOfCapitalVO.getCocValue()));
                    else
                        frm.setValue(nf.format(objCostOfCapitalVO.getCocValue()));
                    
                    frm.setCmboUnit(objCostOfCapitalVO.getUnit().getUomIdentifier());
                    action="failure"; 
                }
               
              else if(frm.getFormAction().equalsIgnoreCase("Modify"))
                {
                   	objCostOfCapitalVO.setCocName(frm.getCocName());
                  	objCostOfCapitalVO.setCocId(frm.getCocId());
                    UOMVO objUOMVO = new UOMVO();
                    objUOMVO.setUomIdentifier(frm.getCmboUnit());
                    objCostOfCapitalVO.setUnit(objUOMVO);
                    
                    objCostOfCapitalVO.setCocValue(Float.parseFloat(frm.getValue()));
                    objCostOfCapitalVO.setIsValid(1);
                    boolean result = objCostOfCapitalDAO.updateCocs(objCostOfCapitalVO);
                    if(result==true)
                    {
                        action = "success";
                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Coc.modify.success"));
                        request.setAttribute("message","message");
                    }
                    else
                    {
                        errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("Coc.modify.failuer"));
                        request.setAttribute("message","error");
                    }
               }
             }
            catch(Exception se)
            {
                se.printStackTrace();
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("Coc.modify.failuer",se.getMessage()));
                action = "failure";
            }
            if(!errors.isEmpty())
            {
                saveErrors(request,errors);
                request.setAttribute("message","error");
            }
            saveMessages(request,messages);
        }
        return mapping.findForward(action);
    }


}

/*
*$Log: CostofCapitalEditAction.java,v $
*Revision 1.1  2008/02/06 06:42:32  tannamalai
*cost of capital added
*
*
*/