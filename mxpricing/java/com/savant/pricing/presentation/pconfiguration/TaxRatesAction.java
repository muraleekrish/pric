/*
 * Created on Apr 11, 2007
 *
 * ClassName	:  	TaxRatesAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pconfiguration;

import java.util.ArrayList;
import java.util.List;

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

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.dao.TaxRatesDAO;
import com.savant.pricing.valueobjects.TaxRatesVO;
import com.savant.pricing.valueobjects.TaxTypesVO;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TaxRatesAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action="failure";
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        boolean resultFlag = false;
        if(form instanceof TaxRatesForm) 
        {
            TaxRatesForm frm = (TaxRatesForm)form;
            if(frm.getFormActions().equalsIgnoreCase("edit"))
            {
                String[] values = request.getParameter("taxEdit").split("taxstr");
                if(BuildConfig.DMODE)
                {
                    System.out.println("values:"+values);
                    System.out.println("values.size:"+values.length);
                }
                
                List lstTax = new ArrayList();
                for(int i=0;i<values.length;i++)
                {
                    TaxRatesVO objTaxRatesVO = new TaxRatesVO();
                    TaxTypesVO objTaxTypesVO = new TaxTypesVO();
                    String[] splitValues = values[i].split(",");
                    if(BuildConfig.DMODE)
                    {
                        System.out.println("splitValues[0]"+splitValues[0]);
                        System.out.println("splitValues[1]"+splitValues[1]);
                    }
                    objTaxTypesVO.setTaxTypeIdentifier(Integer.parseInt(splitValues[0]));
                    objTaxRatesVO.setTaxType(objTaxTypesVO);
                    objTaxRatesVO.setTaxRateIdentifier(Integer.parseInt(splitValues[1]));
                    objTaxRatesVO.setTaxRate(Float.parseFloat(splitValues[2].equalsIgnoreCase("")?"0.0":splitValues[2]));
                    lstTax.add(objTaxRatesVO);
                }
                TaxRatesDAO objTaxRatesDAO = new TaxRatesDAO();
                resultFlag = objTaxRatesDAO.updateTaxes(lstTax);
                
                if(resultFlag)
                {
                    messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.Update.success"));
                    saveMessages(request,messages);
                }
                else
                {
                    messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage(""));
                    errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("common.Update.failure"));
                    saveErrors(request,errors);
                }
            }
        }
        return mapping.findForward(action);
    }
}

/*
*$Log: TaxRatesAction.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/06/12 12:59:03  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.2  2007/05/08 07:25:00  spandiyarajan
*tax edit functionality finished
*
*Revision 1.1  2007/04/11 14:55:06  spandiyarajan
*tax page initially commited
*
*
*/