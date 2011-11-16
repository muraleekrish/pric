/*
 * Created on Apr 9, 2007
 *
 * ClassName	:  	DayTypeListAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.global.calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.savant.pricing.dao.DayTypesDAO;
import com.savant.pricing.valueobjects.DayTypesVO;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DayTypeListAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action = "failure";
        ActionMessages messages = new ActionMessages();
        
        if( form instanceof DayTypeListForm )
        {
            String formActions = "";
            int dayTypeId;
            boolean result = false;
            
            DayTypeListForm frm = ( DayTypeListForm ) form;
            formActions = frm.getFormActions();
            
            if( formActions.equalsIgnoreCase( "edit" ) )
            {
                try
                {
                    dayTypeId      = Integer.parseInt( frm.getDayId() );
                    DayTypesDAO objDayTypesDAO = new DayTypesDAO();
                    DayTypesVO objDayTypesVO   = objDayTypesDAO.getDayType( dayTypeId );
                    
                    if( objDayTypesVO.isValid() )
                    {
                        objDayTypesVO.setValid( false );
                    }
                    else
                    {
                        objDayTypesVO.setValid( true );
                    }
                    
                    result = objDayTypesDAO.updateDayType( objDayTypesVO );
                    
                    if ( result ) 
                    {
                        messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "common.message.modified", "Day Type", "'" + objDayTypesVO.getDayType() + "'" ) );
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

/*
*$Log: DayTypeListAction.java,v $
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:59  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:32  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/08/23 07:38:49  jnadesan
*imports organized
*
*Revision 1.4  2007/08/10 13:34:06  sramasamy
*Scrollbar error solved.
*
*Revision 1.3  2007/08/10 09:28:19  sramasamy
*Action Message is added for Make Valid/Invalid link.
*
*Revision 1.2  2007/08/09 15:10:57  sramasamy
*Make Valid/invalid option provided
*
*Revision 1.1  2007/04/09 11:03:48  spandiyarajan
*daytype iniitaily commited
*
*
*/