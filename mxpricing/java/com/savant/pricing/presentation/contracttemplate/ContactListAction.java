/*
 * Created on Apr 8, 2007
 * 
 * Class Name ContactListAction.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.contracttemplate;

import java.util.Date;

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

import com.savant.pricing.calculation.dao.ReportsDAO;
import com.savant.pricing.calculation.valueobjects.ReportsParamVO;
import com.savant.pricing.calculation.valueobjects.ReportsParamValuesVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContactListAction extends Action{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String action="failure";
        ReportsParamValuesVO objReportsParamValuesVO =  new ReportsParamValuesVO();
        ReportsDAO objReportsDAO = new ReportsDAO();
        ReportsParamVO objReportsParamVO = new ReportsParamVO();
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        if(form instanceof ContractForm)
        {
            ContractForm frm = (ContractForm)form;
            if(frm.getFormAction().equalsIgnoreCase("cancel"))
            {
                action="failure";
            }
            else if(frm.getFormAction().equalsIgnoreCase("update"))
            {
                if(frm.getReportParam()!=0)
                {
                try
                {
                    boolean result = true;
                    
                    objReportsParamVO.setReportParamIdentifier(frm.getReportParam());
                    objReportsParamValuesVO.setReportParam(objReportsParamVO);
                    objReportsParamValuesVO.setReportParamValue(frm.getParamValue());
                    Date modifiedDate = new Date();
                    objReportsParamValuesVO.setModifiedDate(modifiedDate);
                    result = objReportsDAO.updateReportsParamValues(objReportsParamValuesVO);
                    if(result)
                    {
                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("ContractTemplate.update.success"));
                        saveMessages(request,messages);
                    }
                    
                    else
                    {
                        errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("ContractTemplate.update.failure"));
                        saveErrors(request,errors);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                }
                else
                {
                    errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("ContractTemplate.Noparam.update.failure"));
                    saveErrors(request,errors);
                }
            }
        }
        
        return mapping.findForward(action);
    }

}


/*
*$Log: ContactListAction.java,v $
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:30  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/04/24 07:35:15  jnadesan
*search option and other bugs solved
*
*Revision 1.2  2007/04/19 13:19:20  jnadesan
*unwanted print removed
*
*Revision 1.1  2007/04/08 15:10:18  jnadesan
*initial commit for contract template
*
*
*/