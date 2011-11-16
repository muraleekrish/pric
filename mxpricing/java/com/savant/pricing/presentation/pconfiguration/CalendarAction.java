/*
 * Created on Apr 18, 2007
 *
 * ClassName	:  	CalendarAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pconfiguration;

import java.text.ParseException;
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
import org.hibernate.HibernateException;

import com.savant.pricing.calculation.valueobjects.HolidayVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.dao.HolidayDAO;
import com.savant.pricing.valueobjects.DayTypesVO;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CalendarAction extends Action 
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws ParseException
    {
        String action="failure";
        ActionErrors actionErrors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        boolean result=false;
        
        if(form instanceof CalendarForm)
        {
            CalendarForm frm = (CalendarForm)form;
            HolidayDAO objHolidayDAO = new HolidayDAO(); 
            SimpleDateFormat sdf =  new SimpleDateFormat("MM-dd-yyyy");
            
            if(frm.getFormActions().equalsIgnoreCase("add")||frm.getFormActions().trim().equalsIgnoreCase("update") )
            {
                try
                {
                    HolidayVO objHolidayVO = new HolidayVO();
                    objHolidayVO.setDate(sdf.parse(frm.getTxtDate()));
                    objHolidayVO.setReason(frm.getTxtReason());
                    
                    DayTypesVO objDayTypesVO = new DayTypesVO();
                    objDayTypesVO.setDayTypeId(1); // 1 Hotcoded for Holiday
                    objHolidayVO.setDayType(objDayTypesVO);
                    if(frm.getFormActions().trim().equalsIgnoreCase("add"))
                    {
                        result = objHolidayDAO.addHolidays(objHolidayVO);
                        if(result)
                        {
                            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.added","Holiday",frm.getTxtDate()));
                            saveMessages(request,messages);  
                            request.setAttribute("message","message");
                        }
                    }
                     if(frm.getFormActions().trim().equalsIgnoreCase("update"))
                    {
                        result = objHolidayDAO.updateHolidays(objHolidayVO);
                        if(result)
                        {
                            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.modified","Holiday",frm.getTxtDate()));
                            saveMessages(request,messages);
                            request.setAttribute("message","message");
                        }
                    }
                }
                catch(HibernateException e)
                {
                    if(BuildConfig.DMODE)
                        System.out.println("hiber exec :"+e.toString());
                    if(e.toString().indexOf("Date already exist") >= 0)
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.holdatealreadyexist"));
                        if(!actionErrors.isEmpty())
                        {
                            saveErrors(request,actionErrors);
                            request.setAttribute("message","error");
                        }
                    }
                }   
            }
          
            if(frm.getFormActions().trim().equalsIgnoreCase("edit"))
            {
                try{ 
                    String holDate ="";
                    HolidayVO objHolidayVO = null;
                    if(request.getParameter("HolidayDate")!=null)
                    {
                        holDate = request.getParameter("HolidayDate");
                        objHolidayVO = objHolidayDAO.getHolidays(sdf.parse(holDate));
                        frm.setTxtDate(sdf.format(objHolidayVO.getDate()));
                        frm.setTxtReason(objHolidayVO.getReason());
                    } 
                }catch (Exception e) {
                    e.printStackTrace();
                   actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(""));
                } 
            }
        }
       if(result)
       {
       action = "success";
       }
        if(!actionErrors.isEmpty())
        {
            saveErrors(request, actionErrors);
            request.setAttribute("message","error");
        }
        return mapping.findForward(action);
    }
}

/*
*$Log: CalendarAction.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.6  2007/06/12 12:59:03  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.5  2007/06/11 04:31:31  spandiyarajan
*changed the holiday date already exist
*
*Revision 1.4  2007/06/08 07:16:41  spandiyarajan
*holidays date bug fixed
*
*Revision 1.3  2007/05/26 06:46:09  spandiyarajan
*added message for browserheight
*
*Revision 1.2  2007/04/22 15:43:52  jnadesan
*unwanted print removed
*
*Revision 1.1  2007/04/19 04:13:18  spandiyarajan
*pccaledndar(holiday) add/modify/delete functionality initially added
*
*
*/