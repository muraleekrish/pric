/*
 * Created on Feb 2, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.global;

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

import com.savant.pricing.dao.ZipCodesDAO;
import com.savant.pricing.valueobjects.CongestionZonesVO;
import com.savant.pricing.valueobjects.WeatherZonesVO;
import com.savant.pricing.valueobjects.ZipCodeVO;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ZipCodeListAction extends Action{

    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action = "failure";
        ActionMessages messages = new ActionMessages();
        ActionErrors actionErrors = new ActionErrors();
        String formActions = "";
        boolean result = false;
        
        if( form instanceof ZipCodeListForm )
        {
            int zipId;
            
            ZipCodeListForm frm = ( ZipCodeListForm ) form;
            formActions = frm.getFormActions();
            if( formActions.equalsIgnoreCase( "edit" ) )
            {
                try
                {
                    zipId  = Integer.parseInt( frm.getZipId() );
                    ZipCodesDAO objZipCodesDAO = new ZipCodesDAO();
                    ZipCodeVO objZipCodeVO     = objZipCodesDAO.getZipCode( zipId );
                    
                    if( objZipCodeVO.isValid() )
                    {
                        objZipCodeVO.setValid( false );
                    }
                    else
                    {
                        objZipCodeVO.setValid( true );
                    }
                    
                    result = objZipCodesDAO.updateZipCode( objZipCodeVO );
                    
                    if ( result ) 
                    {
                        messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "common.message.modified", "Zip code", "'" + objZipCodeVO.getZipCode() + "'" ) );
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
        
        if(form instanceof ZipCodeAddForm)
        {
            int zipCode;
            int congZone;
            int weatherZone;
            
            ZipCodeAddForm frm         = ( ZipCodeAddForm ) form;
            ZipCodesDAO objZipCodesDAO = null;
            
            formActions = frm.getFormAction();
            
            if( formActions.equalsIgnoreCase( "Add" ) )
            {

                try
                {
                    if( !frm.getZipCode().trim().equals("") )
                    {
                    	zipCode        = Integer.parseInt( frm.getZipCode() );
	                    objZipCodesDAO = new ZipCodesDAO();
	                    
	                    if( objZipCodesDAO.getZipCode( zipCode ) == null )
		                {
	                        congZone    = Integer.parseInt( frm.getCongestionName() );
		                    weatherZone = Integer.parseInt( frm.getWeatherZoneName() );
		                    
		                    ZipCodeVO objZipCodeVO = new ZipCodeVO();
		                    
		                    CongestionZonesVO objCongestionZonesVO = new CongestionZonesVO();
		                    WeatherZonesVO objWeatherZonesVO       = new WeatherZonesVO();
		                    
		                    objCongestionZonesVO.setCongestionZoneId( congZone );
		                    objWeatherZonesVO.setWeatherZoneId( weatherZone );
		                    
		                    objZipCodeVO.setZipCode( zipCode );
		                    objZipCodeVO.setCongestionZone( objCongestionZonesVO );
		                    objZipCodeVO.setWeatherZone( objWeatherZonesVO );
		                    objZipCodeVO.setValid( true );
		                    
		                    result = objZipCodesDAO.addZipCode( objZipCodeVO );
		                   
		                    if( result )
		                    {
		                        messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "common.message.added", "Zip Code", "'" + frm.getZipCode() + "'" ) );
	                            saveMessages( request, messages );
	                            request.setAttribute("message", "message");
		                        action = "success";
		                    }
		                }
	                    else
	                    {
	                        actionErrors.add( ActionErrors.GLOBAL_MESSAGE, new ActionError( "common.message.ConstraintViolation", "Zip Code \"" + zipCode + "\" Already Exist" ) );
	                        saveErrors(request, actionErrors);
	                        request.setAttribute("message", "error");
	                        action = "failure";
	                    }
	                }
                    else
                    {
                        actionErrors.add( ActionErrors.GLOBAL_MESSAGE, new ActionError( "common.error.required", "Zip Code" ) );
                        saveErrors(request, actionErrors);
                        request.setAttribute("message", "error");
                        action = "failure";
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
