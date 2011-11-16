/*
 * Created on Jan 24, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.sql.SQLException;
import java.util.Date;
import java.util.StringTokenizer;

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
import com.savant.pricing.common.MXenergyPriceRun;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.PricingException;
import com.savant.pricing.dao.ForwardCurveBlockDAO;

/**
 * @author srajappan
 *o
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RunListAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException
    {
        String action="failure";
        ActionErrors erros = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        MXenergyPriceRun objMXenergyPriceRun = null;
        if(form instanceof RunListForm)
        {
            RunListForm frm = (RunListForm)form;
            if(frm.getFormActions().equalsIgnoreCase("searchCustomer"))
            {
                action = "searchCustomer";
            }
            if(frm.getFormActions().equalsIgnoreCase("run"))
            {
                action = "run";
                String custIds = frm.getPricerRunCustomerId();
                ForwardCurveBlockDAO objForwardCurveBlockDAO = new ForwardCurveBlockDAO();
                PricingDAO  objPricingDAO = new PricingDAO();
                Date marketDate = objForwardCurveBlockDAO.fwdCurveLastImportedOn();
                String custId = (String)request.getSession().getAttribute("userName");
                boolean isMMCust = false;
                if(BuildConfig.DMODE)
                    System.out.println("marketDate :"+marketDate);
                if(marketDate!=null)
                {
                    try 
                    {
                        objPricingDAO.execute(validateId(frm.getPricerRunCustomerId().trim()),"M",custId,isMMCust);
                    } 
                    catch (HibernateException e)
                    {
                        action="failure";
                        if(e.getMessage().equalsIgnoreCase("No Forward Curves Found"))
                            erros.add(ActionErrors.GLOBAL_ERROR,new ActionError("Pricerun.noFulcrumdata"));
                        else if(e.getMessage().equalsIgnoreCase("Transaction not successfully started"))
                            erros.add(ActionErrors.GLOBAL_ERROR,new ActionError("Pricerun.InsufficientData"));
                        else
                            erros.add(ActionErrors.GLOBAL_ERROR,new ActionError("Pricerun.general.Error"));
                        saveErrors(request,erros);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        erros.add(ActionErrors.GLOBAL_ERROR,new ActionError("Pricerun.InsufficientData"));
                        action="failure";
                    }
                }
                else
                {
                    frm.setPricerRunCustomerId(custIds);
                    action="failure";
                    erros.add(ActionErrors.GLOBAL_ERROR,new ActionError("Pricerun.noFulcrumdata"));
                    saveErrors(request,erros);
                }
            }
            if(frm.getFormActions().trim().equalsIgnoreCase("delete"))        
	        {	            
                String priceRunId = request.getParameter("priceRunId");
                if(BuildConfig.DMODE)
                    System.out.println("priceRunId :"+priceRunId);
	            String temp[] = priceRunId.split(",");
	            boolean deleteResult=false;
	            PricingDAO objPricingDAO = new PricingDAO();
	            try 
	            {
	                deleteResult = objPricingDAO.deleteRunResult(priceRunId);   
	                if(deleteResult)
	                {
	                    if(temp.length>1)
	                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.delete.messages","Selected Price Run's",""));
	                    else
	                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.delete.message","Selected Price Run",""));
	                    saveMessages(request,messages);
	                    action="success";
	                    request.setAttribute("message","message");
	                }
	            } 
	            catch (Exception e) 
	            {
	                erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Common.error.inuse","User",priceRunId));
	                saveErrors(request,erros);
	                action="failure";
	                request.setAttribute("message","error");
	                frm.setFormActions("list");
	            }
	        }
            if(frm.getFormActions().trim().equalsIgnoreCase("autorun"))
            {
                System.out.println("Auto Run Started");
                try
                {
                	objMXenergyPriceRun = new MXenergyPriceRun();
                	objMXenergyPriceRun.execute();
                }
                catch (HibernateException he)
                {
                    action="failure";
                    if(he.getMessage().equalsIgnoreCase("No Forward Curves Found"))
                        erros.add(ActionErrors.GLOBAL_ERROR,new ActionError("Pricerun.noFulcrumdata"));
                    else
                        erros.add(ActionErrors.GLOBAL_ERROR,new ActionError("Pricerun.general.Error"));
                    saveErrors(request,erros);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    erros.add(ActionErrors.GLOBAL_ERROR,new ActionError("Pricerun.InsufficientData"));
                    action="failure";
                }
                action="run";
                System.out.println("Auto Run Completed");
            }
        }
        return mapping.findForward(action);
    }
    private String validateId(String ids)
    {
        StringTokenizer st = new StringTokenizer(ids,",");
        String returnId = st.nextToken();
        while(st.hasMoreTokens())
        {
            returnId += ","+st.nextToken();
        }
       return returnId;
    }

    
    
}
