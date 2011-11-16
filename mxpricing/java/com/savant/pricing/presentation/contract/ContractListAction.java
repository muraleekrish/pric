/*
 * Created on Mar 28, 2007
 * 
 * Class Name ContractListAction.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.contract;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractListAction extends Action{

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String action="failure";
        if(form instanceof ContactListForm)
        {
            ContactListForm frm = (ContactListForm)form;
            if(frm.getFormActions().equalsIgnoreCase("pdf"))
            action = "pdf";
            if(frm.getFormActions().equalsIgnoreCase("Contract"))
            {
                action = "contract";
            }
            else if(frm.getFormActions().equalsIgnoreCase("clear"))
            {
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");           
                Date objdate = new Date();
                frm.setTxtDateFrom(sdf.format(new Date(objdate.getYear(),objdate.getMonth(),objdate.getDate()-2,0,0,0)));
                frm.setTxtDateTo(sdf.format(new Date(objdate.getYear(),objdate.getMonth(),objdate.getDate(),0,0,0)));
              
            }
            
            
        }
        return mapping.findForward(action);
    }
    
    
}


/*
*$Log: ContractListAction.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/04/25 04:09:41  jnadesan
*while clearing page default date is set
*
*Revision 1.3  2007/04/10 09:54:24  jnadesan
*flow control  for contract page
*
*Revision 1.2  2007/04/02 11:18:05  jnadesan
*contract listpage
*
*Revision 1.1  2007/03/29 06:39:13  jnadesan
*Contact page added
*
*
*/