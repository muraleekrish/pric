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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.savant.pricing.dao.LoadProfileTypesDAO;
import com.savant.pricing.valueobjects.LoadProfileTypesVO;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoadProfileAction extends Action
{
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action = "failure";
        ActionMessages messages = new ActionMessages();
        
        if( form instanceof LoadProfileForm )
        {
            String formActions = "";
            int loadId;
            boolean result = false;
            
            LoadProfileForm frm = ( LoadProfileForm ) form;
            formActions = frm.getFormActions();
            
            if( formActions.equalsIgnoreCase( "edit" ) )
            {
                try
                {
                    loadId      = Integer.parseInt( frm.getLoadId() );
                    LoadProfileTypesDAO objLoadProfileTypesDAO = new LoadProfileTypesDAO();
                    LoadProfileTypesVO objLoadProfileTypesVO   = objLoadProfileTypesDAO.getLoadProfile( loadId );
                    
                    if( objLoadProfileTypesVO.isValid() )
                    {
                        objLoadProfileTypesVO.setValid( false );
                    }
                    else
                    {
                        objLoadProfileTypesVO.setValid( true );
                    }
                    
                    result = objLoadProfileTypesDAO.updateLoadProfile( objLoadProfileTypesVO );
                    
                    if ( result ) 
                    {
                        messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "common.message.modified", "Load Profile", "'" + objLoadProfileTypesVO.getProfileType() + "'" ) );
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
