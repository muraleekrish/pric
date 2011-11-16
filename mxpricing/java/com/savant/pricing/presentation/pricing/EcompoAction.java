/*
 * Created on Apr 2, 2007
 *
 * ClassName	:  	EssiidAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pricing;



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
import org.hibernate.HibernateException;

import com.savant.pricing.calculation.valueobjects.CustEnergyComponentsVO;
import com.savant.pricing.calculation.valueobjects.EnergyComponentsVO;
import com.savant.pricing.dao.CustEnergyComponentsDAO;

import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EcompoAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String action="failure";
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        boolean result = false;
        try
        {
            if(form instanceof EcompoForm)
            {
                EcompoForm frm = (EcompoForm)form;
                if(frm.getFormActions().equalsIgnoreCase("update"))
                {
                    CustEnergyComponentsDAO objCustEnergyComponentsDAO = new CustEnergyComponentsDAO();
                    CustEnergyComponentsVO objCustEnergyComponentsVO = new CustEnergyComponentsVO();
                    ProspectiveCustomerVO objProspectiveCustomerVO = new ProspectiveCustomerVO();
                    EnergyComponentsVO objEnergyComponentsVO = new EnergyComponentsVO();
                    int custId = Integer.parseInt(frm.getPrsCustId());
                    String[] selarr= frm.getEnergyComponents().split(",");
                    String[] unselarr= frm.getUnselenergyComponents().split(",");
                    try
                    {
	                    for(int s=0;s<selarr.length;s++)
	                    {
	                        objCustEnergyComponentsVO.setIsValid(1);
	                        objProspectiveCustomerVO.setProspectiveCustomerId(custId);
	                        objEnergyComponentsVO.setEngCompId(Integer.parseInt(selarr[s]));
	                        objCustEnergyComponentsVO.setPrcCustId(objProspectiveCustomerVO);
	                        objCustEnergyComponentsVO.setEngComp(objEnergyComponentsVO);
	                        result = objCustEnergyComponentsDAO.updateCustEngyComp(objCustEnergyComponentsVO);
	                    }
	                    if(unselarr.length>1)
		                    {
		                    for(int s=0;s<unselarr.length;s++)
		                    {
	                            objCustEnergyComponentsVO.setIsValid(0);
		                        objProspectiveCustomerVO.setProspectiveCustomerId(custId);
		                        objEnergyComponentsVO.setEngCompId(Integer.parseInt(unselarr[s]));
		                        objCustEnergyComponentsVO.setPrcCustId(objProspectiveCustomerVO);
		                        objCustEnergyComponentsVO.setEngComp(objEnergyComponentsVO);
		                        result = objCustEnergyComponentsDAO.updateCustEngyComp(objCustEnergyComponentsVO);
		                       
		                    }
		                   
		                    }
                    }
                    catch (HibernateException e) {
                        System.out.println("hibernate exception");
                    }
                    if(result)
                    {
                        if(selarr.length>0)
                        {
                            if(selarr.length==1)
                            {
                                messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("EngCompSelection.success",new Integer(selarr.length)));
                                saveMessages(request,messages);
                            }
                            else
                            {
                                messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("EngCompSelection.success.more",new Integer(selarr.length)));
                                saveMessages(request,messages);
                            }
                        }
                        else
                        {
                            errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("EngCompSelection.noEngComp"));
                            saveErrors(request,errors);
                        }
                    }
                }
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("EngCompSelection.failure"));
            saveErrors(request,errors);
        }
        return mapping.findForward(action);
    }
}

/*
*$Log: EcompoAction.java,v $
*Revision 1.1  2008/01/30 13:44:12  tannamalai
*new page added for energy components
*
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:49  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.9  2007/08/16 12:53:19  jnadesan
*message checked
*
*Revision 1.8  2007/08/16 10:14:41  spandiyarajan
*unwanted msg removed
*
*Revision 1.7  2007/07/03 05:01:40  kduraisamy
*message key added.
*
*Revision 1.6  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.5  2007/04/24 12:56:00  jnadesan
*Esiid error messages are added
*
*Revision 1.4  2007/04/24 10:12:44  jnadesan
*entry for esiid validation
*
*Revision 1.3  2007/04/18 03:56:49  kduraisamy
*imports organized.
*
*Revision 1.2  2007/04/07 12:12:15  rraman
*esiid action class created
*
*Revision 1.1  2007/04/02 14:34:31  rraman
*new form and action created
*
*
*/