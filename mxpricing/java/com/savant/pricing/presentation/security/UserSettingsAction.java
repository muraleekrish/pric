/*
 * Created on Jun 14, 2007
 *
 * ClassName	:  	UserSettingsAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
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

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.securityadmin.dao.UserDAO;
import com.savant.pricing.securityadmin.valueobject.UsersVO;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserSettingsAction extends Action 
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalStateException, IllegalBlockSizeException, BadPaddingException
    {
        String action="failure";
        ActionErrors actionErrors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        boolean result=false;
        if(form instanceof UserSettingsForm)
        {
            
            UserSettingsForm frm = (UserSettingsForm)form;
            UserDAO usersDAO = new UserDAO();
            if(request.getParameter("formAction").trim().equalsIgnoreCase("update"))
            {
                try
                {
                    UsersVO usersVO = new UsersVO();
                    usersVO = usersDAO.getUsers(String.valueOf(request.getSession().getAttribute("userName")));
                    usersVO.setFirstName(frm.getFirstName());
                    usersVO.setLastName(frm.getLastName());
                    usersVO.setPassword(frm.getPassword());
                    usersVO.setEmailId(frm.getEmail());
                    usersVO.setCreatedDate(new Date());
                    usersVO.setValid(true);
                    frm.setUserName((String)request.getSession().getAttribute("userName"));
                    if(request.getParameter("formAction").trim().equalsIgnoreCase("update"))
                    {
                        result = usersDAO.updateUser(usersVO, null);
                        if(result)
                        {
                            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.modified","User",frm.getUserName()));
                            saveMessages(request,messages); 
                            request.setAttribute("message","message");
                        }
                    }
                }
                catch(HibernateException e)
                {
                    actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.general"));
                    if(!actionErrors.isEmpty())
                    {
                        saveErrors(request,actionErrors);
                        request.setAttribute("message","error");
                    }
                }
                catch(Exception e)
                {
                    actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.general"));
                    if(!actionErrors.isEmpty())
                    {
                        saveErrors(request,actionErrors);
                        request.setAttribute("message","error");
                    }
                } 
            }
          
            if(request.getParameter("formAction").trim().equalsIgnoreCase("edit"))
            {
                try{ 
                    String userid ="";
                    UsersVO usersVO = null;
                    if(request.getParameter("userid")!=null)
                    {
                        if(BuildConfig.DMODE)
                            System.out.println("UserId :"+request.getParameter("userid"));

                        userid = request.getParameter("userid");
                        usersVO = usersDAO.getUsers(userid);
                        frm.setUserName(usersVO.getUserId());
                        if(BuildConfig.DMODE)
                            System.out.println("UserName :"+usersVO.getUserId());
                        frm.setPassword(usersVO.getPassword());
                        frm.setConfirmPwd(usersVO.getPassword());
                        frm.setFirstName(usersVO.getFirstName());
                        frm.setLastName(usersVO.getLastName());
                        frm.setEmail(usersVO.getEmailId());
                    } 
                }catch (Exception e) 
                {
                   e.printStackTrace();
                   actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Prospective.customer.customerId.numeric"));
                   request.setAttribute("message","error");
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
        }
        return mapping.findForward(action);
    }
}

/*
*$Log: UserSettingsAction.java,v $
*Revision 1.1  2007/12/07 06:18:50  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/08/24 13:34:20  kduraisamy
*calling menu item in all pages problem solved.
*
*Revision 1.4  2007/08/02 07:30:38  sramasamy
*In message user id set
*
*Revision 1.3  2007/07/30 06:21:48  spandiyarajan
*throw exception
*
*Revision 1.2  2007/06/16 05:44:57  kduraisamy
*exception handling for encrypting password added.
*
*Revision 1.1  2007/06/14 09:03:00  spandiyarajan
*initially commit settings functionality
*
*
*/