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
import com.savant.pricing.dao.WeatherZonesDAO;
import com.savant.pricing.valueobjects.WeatherZonesVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WeatherZoneAction extends Action{

    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action = "failure";
        ActionMessages messages = new ActionMessages();
        
        if( form instanceof WeatherZoneListform )
        {
            String formAction = "";
            int weatherId;
            boolean result = false;
            
            WeatherZoneListform frm = ( WeatherZoneListform ) form;
            
            formAction = frm.getFormAction();
            
            if( formAction.equalsIgnoreCase( "edit" ) )
            {
                try
                {
                    weatherId  = Integer.parseInt( frm.getZoneId() );
                    WeatherZonesDAO objWeatherZonesDAO = new WeatherZonesDAO(); 
                    WeatherZonesVO objWeatherZonesVO   = objWeatherZonesDAO.getWeatherZone( weatherId );
                    
                    if( objWeatherZonesVO.isValid() )
                    {
                        objWeatherZonesVO.setValid( false );
                    }
                    else
                    {
                        objWeatherZonesVO.setValid( true );
                    }
                    
                    result = objWeatherZonesDAO.updateWeatherZone( objWeatherZonesVO );
                    
                    if ( result ) 
                    {
                        messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "common.message.modified", "Weather Zone", "'" + objWeatherZonesVO.getWeatherZone() + "'" ) );
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
        
        form.validate( mapping, request );
        return mapping.findForward( action );
    }
}
