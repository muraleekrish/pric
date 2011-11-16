/*
 * Created on Jan 27, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.global;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.savant.pricing.dao.CongestionZonesDAO;
import com.savant.pricing.valueobjects.CongestionZonesVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CongestionZoneAction extends Action{
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action = "failure";
        ActionMessages messages = new ActionMessages();
        
        if( form instanceof CongestionZoneListForm )
        {
            String formActions = "";
            int zoneId;
            boolean result = false;
            
            CongestionZoneListForm frm = ( CongestionZoneListForm ) form;
            formActions = frm.getFormActions();
            
            if( formActions.equalsIgnoreCase( "edit" ) )
            {
                try
                {
                    zoneId = Integer.parseInt( frm.getCongId() );
                    CongestionZonesDAO objCongestionZonesDAO = new CongestionZonesDAO();
                    CongestionZonesVO objCongestionZonesVO   = objCongestionZonesDAO.getCongestionZone( zoneId );
                    
                    if( objCongestionZonesVO.isValid() )
                    {
                        objCongestionZonesVO.setValid( false );
                    }
                    else
                    {
                        objCongestionZonesVO.setValid( true );
                    }
                    
                    result = objCongestionZonesDAO.updateCongestion( objCongestionZonesVO );
                    
                    if ( result ) 
                    {
                        messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "common.message.modified", "Congestion Zone", "'" + objCongestionZonesVO.getCongestionZone() + "'" ) );
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
