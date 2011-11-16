/*
 * Created on Apr 9, 2007
 *
 * ClassName	:  	TLFAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 *  
 */
package com.savant.pricing.presentation.pconfiguration;

import java.text.NumberFormat;
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

import com.savant.pricing.calculation.valueobjects.SeasonsVO;
import com.savant.pricing.calculation.valueobjects.TLFVO;
import com.savant.pricing.common.NumberUtil;
import com.savant.pricing.dao.TLFDAO;


/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TLFAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action="failure";
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        boolean resultFlag = false;
        NumberFormat dnf = NumberUtil.doubleFraction();
        if(form instanceof TLFForm) 
        {
            TLFForm frm = (TLFForm)form;
            if(frm.getFormActions().equalsIgnoreCase("edit"))
            {
                /*Enumeration table = request.getParameterNames();
                while(table.hasMoreElements())
                {
                    String str = (String)table.nextElement();
                    System.out.println("values :"+str);
                    System.out.println("sd "+request.getParameter(str));
                }*/
                System.out.println("values :"+request.getParameter("seasonedit"));
                String[] values = request.getParameter("seasonedit").split("substr");
                List lstTLFVO = new ArrayList();
                for(int i = 0;i<values.length;i++)
                {
                    TLFVO objTlfvo = new TLFVO();
                    SeasonsVO objSeasonsVO = new SeasonsVO();
                    String[] splitValues = values[i].split(",");
                    objSeasonsVO.setSeasonId(Integer.parseInt(splitValues[0]));
                    objTlfvo.setSeason(objSeasonsVO);
                    objTlfvo.setOnPeakLoss(Float.parseFloat(splitValues[1]));
                    objTlfvo.setOffPeakLoss(Float.parseFloat(splitValues[2]));
                    lstTLFVO.add(objTlfvo);
                }
                TLFDAO objTLFDAO = new TLFDAO();
                resultFlag = objTLFDAO.updateTlf(lstTLFVO);
                objTLFDAO.reload();
                
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
*$Log: TLFAction.java,v $
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
*Revision 1.8  2007/06/13 09:14:08  spandiyarajan
*bug fixed
*
*Revision 1.7  2007/06/07 08:41:15  spandiyarajan
*error corrected
*
*Revision 1.6  2007/05/10 09:25:34  spandiyarajan
*float bug value fixed
*
*Revision 1.5  2007/05/07 07:37:25  jnadesan
*edit value validated
*
*Revision 1.4  2007/05/05 04:42:34  spandiyarajan
*tlf messages bug fixed
*
*Revision 1.3  2007/05/03 09:11:22  jnadesan
*condition checked for avoiding empty values
*
*Revision 1.2  2007/05/02 11:39:01  jnadesan
*TLF modify finished
*
*Revision 1.1  2007/04/09 12:48:30  spandiyarajan
*pcTLF - initial commit
*
*
*/