/*
 * Created on Apr 9, 2007
 *
 * ClassName	:  	LossFactorCodeListAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
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
import com.savant.pricing.calculation.valueobjects.DLFCodeVO;
import com.savant.pricing.dao.DLFCodeDAO;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LossFactorCodeListAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action = "failure";
        ActionMessages messages = new ActionMessages();
        
        if( form instanceof LossFactorCodeListForm )
        {
            String formActions = "";
            int lossFactorId;
            boolean result = false;
            
            LossFactorCodeListForm frm = ( LossFactorCodeListForm ) form;
            formActions = frm.getFormActions();
            
            if( formActions.equalsIgnoreCase( "edit" ) )
            {
                try
                {
                    lossFactorId      = Integer.parseInt( frm.getLossFactorId() );
                    DLFCodeDAO objDLFCodeDAO = new DLFCodeDAO();
                    DLFCodeVO objDLFCodeVO   = objDLFCodeDAO.getLossFactorCode( lossFactorId );
                    
                    if( objDLFCodeVO.isValid() )
                    {
                        objDLFCodeVO.setValid( false );
                    }
                    else
                    {
                        objDLFCodeVO.setValid( true );
                    }
                    
                    result = objDLFCodeDAO.updateLossFactorCode( objDLFCodeVO );
                    objDLFCodeDAO.reload();
                    if ( result ) 
                    {
                        messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "common.message.modified", "Loss Factor Code", "'" + objDLFCodeVO.getDflName() + "'" ) );
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
*$Log: LossFactorCodeListAction.java,v $
*Revision 1.2  2008/04/24 05:56:35  tannamalai
*reload method called
*
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:30  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/08/23 07:38:38  jnadesan
*imports organized
*
*Revision 1.4  2007/08/10 13:33:34  sramasamy
*Scrollbar error solved.
*
*Revision 1.3  2007/08/10 09:28:07  sramasamy
*Action Message is added for Make Valid/Invalid link.
*
*Revision 1.2  2007/08/09 15:10:44  sramasamy
*Make Valid/invalid option provided
*
*Revision 1.1  2007/04/09 08:28:59  spandiyarajan
*initial commit - lossfactorcode
*
*
*/