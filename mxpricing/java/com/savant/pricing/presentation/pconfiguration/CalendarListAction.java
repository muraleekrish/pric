/*
 * Created on Apr 8, 2007
 *
 * ClassName	:  	CalendarListAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pconfiguration;

import java.text.SimpleDateFormat;

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

import com.savant.pricing.calculation.valueobjects.HolidayVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.dao.HolidayDAO;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CalendarListAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {    
        String action="failure";		
		ActionErrors actionErrors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        boolean result=false;
        SimpleDateFormat sdf =  new SimpleDateFormat("MM-dd-yyyy");
        
	    if(form instanceof CalendarListForm)
        {
	        CalendarListForm frm = (CalendarListForm)form;
	        HolidayDAO objHolidayDAO = new HolidayDAO();
	        if(BuildConfig.DMODE)
	        System.out.println("FormAction:"+frm.getFormActions());
	        if(frm.getFormActions().trim().equalsIgnoreCase("delete"))        
	        {	            
	            HolidayVO objHolidayVO = null;
	            String temp[] = request.getParameter("HolidayDate").split(","); 
	            boolean deleteResult=false;
	            for (int i = 0; i < temp.length; i++) 
	            { 
	                System.out.println("Date:"+temp[i]);
	                try 
		              {
	                    objHolidayVO = objHolidayDAO.getHolidays(sdf.parse(temp[i].trim()));
	                    if(BuildConfig.DMODE)
	                        System.out.println("Date.getReason:"+objHolidayVO.getReason());
		                deleteResult = objHolidayDAO.deleteHolidays(objHolidayVO);
		                if(BuildConfig.DMODE)
		                    System.out.println("deleteResult:"+deleteResult);
		                if(deleteResult)
		                {
		                    messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.delete.message","Date",sdf.format(objHolidayVO.getDate())));
		                    saveMessages(request,messages);
		                    request.setAttribute("message","message");
		                }
		              } 
		              catch (Exception e) 
		              {
		                actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("childRecord.found"));
		                frm.setFormActions("list");
		              }
	            }
	        }
        }
	    if(result)
        {
            action = "success";
        }
		return mapping.findForward(action);		
	}
}

/*
*$Log: CalendarListAction.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/05/30 11:06:22  spandiyarajan
*message added fo browser height
*
*Revision 1.4  2007/04/23 05:52:20  kduraisamy
*unwanted println removed.
*
*Revision 1.3  2007/04/19 07:53:04  spandiyarajan
*pccaledndar(holiday) modify/delete/view functionality altered
*
*Revision 1.2  2007/04/19 04:13:18  spandiyarajan
*pccaledndar(holiday) add/modify/delete functionality initially added
*
*Revision 1.1  2007/04/08 16:57:34  rraman
*for holidays jsp
*
*
*/