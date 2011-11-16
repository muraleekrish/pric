/*
 * Created on Feb 2, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.global.tdsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.savant.pricing.dao.ServiceVoltageDAO;
import com.savant.pricing.valueobjects.ServiceVoltageVO;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ServiceVoltageListAction extends Action
{    
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action="failure";
        ActionMessages messages = new ActionMessages();
        
        if( form instanceof ServiceVoltageListForm )
        {
            String formActions = "";
            int serviceId;
            boolean result = false;
            
            ServiceVoltageListForm frm = ( ServiceVoltageListForm ) form;
            formActions = frm.getFormActions();
            
            if( formActions.equalsIgnoreCase( "edit" ) )
            {
                try
                {
                    serviceId      = Integer.parseInt( frm.getServiceId() );
                    ServiceVoltageDAO objServiceVoltageDAO = new ServiceVoltageDAO();
                    ServiceVoltageVO objServiceVoltageVO   = objServiceVoltageDAO.getServiceVoltage( serviceId );
                    
                    if( objServiceVoltageVO.isValid() )
                    {
                        objServiceVoltageVO.setValid( false );
                    }
                    else
                    {
                        objServiceVoltageVO.setValid( true );
                    }
                    
                    result = objServiceVoltageDAO.updateServiceVoltage( objServiceVoltageVO );
                    
                    if ( result ) 
                    {
                        messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "common.message.modified", "Service Voltage", "'" + objServiceVoltageVO.getServiceVoltageType() + "'" ) );
                        saveMessages( request, messages );
                        request.setAttribute("message", "message");
                    }
                }
                catch( Exception e )
                {
                    e.printStackTrace();
                }
                
            }
        }
        return mapping.findForward(action);
    }
}
