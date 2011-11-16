/*
 * Created on Mar 2, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.sql.SQLException;
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

import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProspectiveCustomerAnalystListAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException
    {
        String action="";
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        boolean result = true;
        if(form instanceof ProspectiveCustomerListForm)
        {
            ProspectiveCustomerListForm frm = (ProspectiveCustomerListForm)form;
            if(frm.getFormActions().equalsIgnoreCase("Add"))
            {
                action = "addCustomer";
                frm.setUser("Analyst");
            }
            else if(frm.getFormActions().equalsIgnoreCase("writeIntoCMS"))
            {
                if(frm.getCustomerId()!=null && !frm.getCustomerId().equalsIgnoreCase(""))
                {
                    result = objProspectiveCustomerDAO.writeCDRIntoCMS(Integer.parseInt(frm.getCustomerId()));
                    ProspectiveCustomerVO objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(frm.getCustomerId()));
                    if(result)
                    {
                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Prospective.customer.write.CMS.success",objProspectiveCustomerVO.getCustomerName()));
                        saveMessages(request,messages);
                        request.setAttribute("message","message");
                    }
                    else
                    {
                        errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("Prospective.customer.write.CMS.failure"));
                        saveErrors(request,errors);
                        request.setAttribute("message","error");
                    }
                }
                action = "success";
            }
            else if(frm.getFormActions().equalsIgnoreCase("delete"))
            {
                try
                {
                    result = objProspectiveCustomerDAO.deleteProspectiveCustomer(Integer.parseInt(frm.getCustomerId()));
                }
                catch (HibernateException e) {
                    result = false;
                    if(e.getMessage().equalsIgnoreCase("Active customer cannot be deleted from the list"))
                    {
                        errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("Prospective.customer.delete.use",e.getMessage()));
                        saveErrors(request,errors);
                        request.setAttribute("message","error");
                    }
                    else
                    {
                        errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("Prospective.customer.delete.general"));
                        saveErrors(request,errors);
                        request.setAttribute("message","error");
                    }
                }
                
                if(result)
                {
                    messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Prospective.customer.delete.success"));
                    saveMessages(request,messages);
                    request.setAttribute("message","message");
                }
                action = "success";
            }
            else
                action = "success";
        }
        else if(form instanceof ProspectiveCustomerAddForm)
        {
            try
            {
                ProspectiveCustomerAddForm frm = (ProspectiveCustomerAddForm)form;
                if(frm.getFormActions().trim().equalsIgnoreCase("approve"))
                {
                    List lstPreferenceProducts =   objProspectiveCustomerDAO.getProspectiveCustomerPreferenceProducts(Integer.parseInt(frm.getProspectiveCustId()));
                    if(lstPreferenceProducts.size()>0)
                    {
                        
                        if(frm.getProspectiveCustId()!=null)
                            result = objProspectiveCustomerDAO.updateProspectiveCustomerStatus(Integer.parseInt(frm.getProspectiveCustId()),1);
                        action = "approve";
                        if(result)
                        {
                            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Prospective.customer.status.success","Approved"));
                            saveMessages(request,messages);
                        }
                        else
                        {
                            errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("Prospective.customer.status.failure"));
                            saveErrors(request,errors);
                        }
                        saveMessages(request,messages);
                    }
                    else
                    {
                        action = "approve";
                        errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.customer.preference.check"));
                        saveErrors(request,errors);
                    }
                    
                }else if(frm.getFormActions().trim().equalsIgnoreCase("reject"))
                {
                    frm.setProspectiveCustId((String)request.getSession().getAttribute("prsCustId"));
                    if(frm.getProspectiveCustId()!=null)
                        result = objProspectiveCustomerDAO.updateProspectiveCustomerStatus(Integer.parseInt(frm.getProspectiveCustId()),2);
                    if(result)
                    {
                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Prospective.customer.status.success","Rejected"));
                        saveMessages(request,messages);
                    }
                    else
                    {
                        errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("Prospective.customer.status.failure"));
                        saveErrors(request,errors);
                    }
                    action="failure";
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return mapping.findForward(action);
    }
}
