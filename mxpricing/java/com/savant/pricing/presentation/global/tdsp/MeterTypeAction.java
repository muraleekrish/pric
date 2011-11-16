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
import com.savant.pricing.dao.MeterCategoryDAO;
import com.savant.pricing.valueobjects.MeterCategoryVO;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MeterTypeAction extends Action
{
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action = "failure";
        ActionMessages messages = new ActionMessages();
        
        if( form instanceof MeterTypeForm )
        {
            String formActions = "";
            int meterId;
            boolean result = false;
            
            MeterTypeForm frm = ( MeterTypeForm ) form;
            formActions = frm.getFormActions();
            
            if( formActions.equalsIgnoreCase( "edit" ) )
            {
                try
                {
                    meterId      = Integer.parseInt( frm.getMeterId() );
                    MeterCategoryDAO objMeterCategoryDAO = new MeterCategoryDAO();
                    MeterCategoryVO objMeterCategoryVO   = objMeterCategoryDAO.getMeterType( meterId );
                    
                    if( objMeterCategoryVO.isValid() )
                    {
                        objMeterCategoryVO.setValid( false );
                    }
                    else
                    {
                        objMeterCategoryVO.setValid( true );
                    }
                    
                    result = objMeterCategoryDAO.updateMeterType( objMeterCategoryVO );
                    
                    if ( result ) 
                    {
                        messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "common.message.modified", "Meter Type", "'" + objMeterCategoryVO.getMeterCategory() + "'" ) );
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
