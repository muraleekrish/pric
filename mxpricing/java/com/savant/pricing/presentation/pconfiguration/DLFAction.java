/*
 * Created on Apr 12, 2007
 *
 * ClassName	:  	DLFAction.java
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

import com.savant.pricing.calculation.valueobjects.DLFCodeVO;
import com.savant.pricing.calculation.valueobjects.DLFVO;
import com.savant.pricing.dao.DLFDAO;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DLFAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action="failure";
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        boolean resultFlag = false;
        if(form instanceof DLFForm) 
        {
            DLFForm frm = (DLFForm)form;
            if(frm.getFormActions().equalsIgnoreCase("edit"))
            {
                String[] values = request.getParameter("dlfEdit").split("substr");
                List lstDLFVO = new ArrayList();
                for(int i = 0;i<values.length;i++)
                {
                    DLFVO objDLFVO = new DLFVO();
                    DLFCodeVO objDLFCodeVO = new DLFCodeVO();
                    String[] splitValues = values[i].split(",");
                    if(!splitValues[0].equals("0"))
                    {
                        objDLFCodeVO.setDlfCodeIdentifier(Integer.parseInt(splitValues[0]));
                        objDLFVO.setDlfCode(objDLFCodeVO);
	                    objDLFVO.setDlf(Float.parseFloat(splitValues[1]));
	                    lstDLFVO.add(objDLFVO);
                    }
                }
                DLFDAO objDLFDAO = new DLFDAO();
                resultFlag = objDLFDAO.updateDlf(lstDLFVO);
                objDLFDAO.reload();
                
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
*$Log: DLFAction.java,v $
*Revision 1.2  2008/04/24 05:56:42  tannamalai
*reload method called
*
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/06/13 11:02:18  spandiyarajan
**** empty log message ***
*
*Revision 1.3  2007/06/12 12:59:03  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.2  2007/06/07 08:41:01  spandiyarajan
*added update function for dlf
*
*Revision 1.1  2007/04/12 06:31:21  spandiyarajan
*DLF page initially committed
*
*
*/