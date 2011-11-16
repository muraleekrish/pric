/*
 * Created on Mar 7, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
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

import com.savant.pricing.dao.PICDAO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PreferenceAnalystAction extends Action{

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String action="failure";
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        PICDAO objPICDAO = new PICDAO();
        boolean result = false;
        try
        {
            if(form instanceof PreferenceAnalystForm)
            {
                PreferenceAnalystForm frm = (PreferenceAnalystForm)form;
                if(frm.getFormActions().equalsIgnoreCase("update"))
                {
                    result = objPICDAO.makeValid(Integer.parseInt(frm.getPrsCustId()),frm.getEsiids());
                }
                int esiidCount = objPICDAO.getAllValidESIID(Integer.parseInt(frm.getPrsCustId())).size();
                if(result)
                {
                    if(esiidCount>0)
                    {
                        if(esiidCount==1)
                        {
                            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("EsiIdSelection.success",new Integer(esiidCount)));
                            saveMessages(request,messages);
                        }
                        else
                        {
                            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("EsiIdSelection.success.more",new Integer(esiidCount)));
                            saveMessages(request,messages);
                        }
                    }
                    else
                    {
                        errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("EsiIdSelection.noEsiid"));
                        saveErrors(request,errors);
                    }
                }
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("EsiIdSelection.failure"));
            saveErrors(request,errors);
        }
        
        return mapping.findForward(action);
    }
}
