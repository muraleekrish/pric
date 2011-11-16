/*
 * 
 * XpressPricingListAction.java    Aug 23, 2007
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

import java.util.Date;
import java.util.Iterator;
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
import org.hibernate.HibernateException;

import com.savant.pricing.calculation.dao.PricingDAO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.dao.ForwardCurveBlockDAO;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.matrixpricing.dao.MMPriceRunStatusDAO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * 
 */
public class XpressPricingListAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action="failure";   
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        boolean runresult = false;
        if(form instanceof XpressPricingListForm)
        {
            XpressPricingListForm frm = (XpressPricingListForm)form;
            MMPriceRunStatusDAO objMMPriceRunStatusDAO = new MMPriceRunStatusDAO();
            if(frm.getFormAction().equalsIgnoreCase("Run"))
            {
                action = "run";
                String custIds = getPrsCustIds(new ProspectiveCustomerDAO().getAllMMCustomers());
                if(custIds.length()>1)
                {
                    ForwardCurveBlockDAO objForwardCurveBlockDAO = new ForwardCurveBlockDAO();
                    PricingDAO  objPricingDAO = new PricingDAO();
                    Date marketDate = objForwardCurveBlockDAO.fwdCurveLastImportedOn();
                    String custId = (String)request.getSession().getAttribute("userName");
                    boolean isMMCust = true;
                    if(BuildConfig.DMODE)
                        System.out.println("marketDate :"+marketDate);
                    if(marketDate!=null)
                    {
                        try 
                        {
                            runresult = objPricingDAO.execute(custIds,"M",custId,isMMCust);
                        } 
                        catch (HibernateException e)
                        {
                            action="failure";
                            if(e.getMessage().equalsIgnoreCase("No Forward Curves Found"))
                                errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Pricerun.noFulcrumdata"));
                            else
                                errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Pricerun.general.Error"));
                            saveErrors(request,errors);
                            request.setAttribute("message","error");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            action="failure";
                        }
                    }
                    else
                    {
                        action="failure";
                        errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Pricerun.noFulcrumdata"));
                        saveErrors(request,errors);
                        request.setAttribute("message","error");
                    }
                }
                else
                {
                    action="failure";
                    errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("MMRun.No.Cust"));
                    saveErrors(request,errors);
                    request.setAttribute("message","error");
                    
                }
                if(runresult)
                {
                    messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("MMRun.No.Cust.success"));
                    saveMessages(request,messages);
                    action="success";
                    request.setAttribute("message","message");
                }
            }
            else if(frm.getFormAction().equalsIgnoreCase("delete"))
            {
                String priceRunId = request.getParameter("priceRunId");
                boolean result = false;
                try
                {
                    result = objMMPriceRunStatusDAO.deleteRunResult(priceRunId);
                }
                catch (Exception e) 
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.general"));
                }
                if(result)
                {
                    action = "success";
                    messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.delete.message","Run Date","'"+priceRunId+"'"));
                    saveMessages(request,messages);
                    request.setAttribute("message","message");
                }
                else
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.general"));
                }
            }
        }
        return mapping.findForward(action);
    }
    private String getPrsCustIds(List lstCustomers)
    {
        String custIds = "";
        Iterator iteCust = lstCustomers.iterator();
        while(iteCust.hasNext())
        {
            ProspectiveCustomerVO objProspectiveCustomerVO = (ProspectiveCustomerVO) iteCust.next();
            custIds += objProspectiveCustomerVO.getProspectiveCustomerId()+"";
            if(iteCust.hasNext())
                custIds+=",";
        }
        return custIds;
    }
}


/*
*$Log: XpressPricingListAction.java,v $
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
*Revision 1.5  2007/09/06 14:03:22  spandiyarajan
**** empty log message ***
*
*Revision 1.4  2007/09/06 12:02:20  spandiyarajan
*for reduce the browserheight  when message occured
*
*Revision 1.3  2007/09/03 11:50:58  spandiyarajan
*added action for deleteRunResult.
*
*Revision 1.2  2007/08/27 15:14:17  kduraisamy
*action added for running customer
*
*Revision 1.1  2007/08/23 14:33:06  jnadesan
*XpressPricing form action added
*
*
*/