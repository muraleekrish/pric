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
import com.savant.pricing.dao.TDSPDAO;
import com.savant.pricing.valueobjects.TDSPVO;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TDSPListAction extends Action
{
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action = "failure";
        ActionMessages messages = new ActionMessages();
        
        if( form instanceof TDSPListForm )
        {
            String formActions = "";
            int tdspId;
            boolean result = false;
            
            TDSPListForm frm = ( TDSPListForm ) form;
            formActions = frm.getFormActions();
            
            if( formActions.equalsIgnoreCase( "edit" ) )
            {
                try
                {
                    tdspId      = Integer.parseInt( frm.getTdspId() );
                    TDSPDAO objTDSPDAO = new TDSPDAO();
                    TDSPVO objTDSPVO   = objTDSPDAO.getTDSP( tdspId );
                    
                    if( objTDSPVO.isValid() )
                    {
                        objTDSPVO.setValid( false );
                    }
                    else
                    {
                        objTDSPVO.setValid( true );
                    }
                    
                    result = objTDSPDAO.updateTDSP( objTDSPVO );
                    
                    if ( result ) 
                    {
                        messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "common.message.modified", "TDSP", "'" + objTDSPVO.getTdspName() + "'" ) );
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
        
        return mapping.findForward( action );
    }
}
