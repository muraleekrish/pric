/*
 * Created on Apr 8, 2007
 *
 * ClassName	:  	ProductsListAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pconfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import com.savant.pricing.dao.ProductsDAO;
import com.savant.pricing.valueobjects.ProductsVO;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProductsListAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action = "failure";
        ActionMessages messages = new ActionMessages();
        
        if( form instanceof ProductsListForm )
        {
            String formActions = "";
            int productId;
            boolean result = false;
            
            ProductsListForm frm = ( ProductsListForm ) form;
            formActions = frm.getFormActions();
            
            if( formActions.equalsIgnoreCase( "edit" ) )
            {
                try
                {
                    productId      = Integer.parseInt( frm.getPrductsId() );
                    ProductsDAO objProductsDAO = new ProductsDAO();
                    ProductsVO objProductsVO   = objProductsDAO.getProduct( productId );
                    
                    if( objProductsVO.isValid() )
                    {
                        objProductsVO.setValid( false );
                    }
                    else
                    {
                        objProductsVO.setValid( true );
                    }
                    
                    result = objProductsDAO.updateProduct( objProductsVO );
                    
                    if ( result ) 
                    {
                        messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "common.message.modified", "Product name", "'" + objProductsVO.getProductName() + "'" ) );
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
*$Log: ProductsListAction.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/20 04:52:53  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/08/23 07:39:08  jnadesan
*imports organized
*
*Revision 1.4  2007/08/10 13:34:06  sramasamy
*Scrollbar error solved.
*
*Revision 1.3  2007/08/10 09:31:23  sramasamy
*Action Message is added for Make Valid/Invalid link.
*
*Revision 1.2  2007/08/09 15:11:36  sramasamy
*Make Valid/invalid option provided
*
*Revision 1.1  2007/04/08 11:05:36  rraman
*products list finished
*
*
*/