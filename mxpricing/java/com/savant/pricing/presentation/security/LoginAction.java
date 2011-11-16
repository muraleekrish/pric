/*
 * Created on May 19, 2006
 *
 * ClassName	:  	LoginAdmin.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India
 *
 */
package com.savant.pricing.presentation.security;

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
import org.jfree.util.Log;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.mailer.Mail;
import com.savant.pricing.mailer.MailManager;
import com.savant.pricing.securityadmin.dao.MenuConstructor;
import com.savant.pricing.securityadmin.dao.UserDAO;
import com.savant.pricing.securityadmin.valueobject.UsersVO;

/**
 * @author vsubramanian
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoginAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String action = "failure";
        ActionErrors errors     = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        
        boolean login = false;
        
        if(form instanceof LoginForm)
        {
            LoginForm frm = (LoginForm)form;
            String formAction = frm.getFormAction();
            UserDAO userDAO = new UserDAO();
            Mail mail = null;
            try
            {
                if(formAction.equals("login"))
                {
                    String userId = frm.getUserId();
                    String passWord = frm.getPassword();
                    login = userDAO.Authentication(userId,passWord);
                    if(login)
                    {
                        action = "success";
                        
                        String browserHt = request.getParameter("browserHeight");
                        String browserWd = request.getParameter("browserWidth");
                        MenuConstructor objMenuConstructor = new MenuConstructor();
                        String constructedMenu = objMenuConstructor.getConstructedMenuScripts(userId.trim(),request.getContextPath()); 
                        String menu[]=constructedMenu.split("###");
                        if(menu!= null && menu.length>1)
                        {
                            request.getSession().setAttribute("constructedMenu",menu[0]);
                            request.getSession().setAttribute("firstPage",menu[1]);
                            UsersVO objUsersVO = userDAO.getUsers(userId);
                            request.getSession().setAttribute("home",this.footerLink(objUsersVO.getUserId(),request.getContextPath()));
                            request.getSession().setAttribute("firstName",objUsersVO.getFirstName());
                            request.getSession().setAttribute("userName",objUsersVO.getUserId());	                        
                            request.getSession().setAttribute("browserHeight",browserHt);
                            request.getSession().setAttribute("browserWidth",browserWd);
                            request.getSession().setAttribute("userType",objUsersVO.getUserTypes().getUserType());
                            request.getSession().setAttribute("contextName",request.getContextPath());
                            System.out.println("contextName:"+ request.getSession().getAttribute("contextName"));
                            System.out.println("************** user type :" + objUsersVO.getUserTypes().getUserType());
                        }
                    }
                    else
                    {
                        action = "failure";
                        errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("login.error.invalidUser"));
                    }
                }
                else if( formAction.equals( "sendmail" ) )
                {
                    String userId  = frm.getUserId().trim();
                    boolean isSent = false;
                    if( !userId.trim().equals( "" ) )
	                {
	                   UsersVO usersVO    = userDAO.getUsers(userId);
	                        
	                   if(usersVO != null)
	                   {
	                        String toId        = ""; 
	                        String passwordMsg = "";
	                                                
	                        toId               = usersVO.getEmailId();
	                        System.out.println("id->"+toId);
		                    passwordMsg        = "Login details for MXEnergy Pricing portal:\n\n\tLogin Id: " +userId+"\n\tPassword: "+ usersVO.getPassword().trim();
		                    try
		                    {
		                        mail = new Mail();
		                        String[] toIds = toId.trim().split(",");
		                        
		                        mail.addRecipients( toIds, "to" );
		                        mail.setSubject( "Portal Pricing Password" );
		                        
		                        MailManager objMailManager = new MailManager();
		                        Log.info("testcon");
		                        
		                        mail.setBodyTextAsHTML( objMailManager.prepareHTMLContent( "Hi " + userId + ",",passwordMsg,"","Regards,\nPricer Ver 1.0","" ) );
		                        isSent = mail.send();
		                        if( isSent )
		                        {
		                            frm.setUserId( "" );
		                            messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "common.message.mailsent" ) );
		                            saveMessages( request, messages );
		                            request.setAttribute( "message", "message" );
		                        }
		                    }
		                    catch( Exception e )
		                    {
		                        e.printStackTrace();
		                    }
	                   }
	                   else
	                   {
	                       frm.setUserId( "" );
	                       errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.inValid", "User Id"));
	                   }
	                }
                    else
                    {
                        errors.add( ActionErrors.GLOBAL_ERROR, new ActionError( "common.error.req" ) );
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                if(e.toString().indexOf("Cannot open connection") >= 0)
                {
                    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.dbconfailed"));
                }
                else 
                {
                    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("login.error.invalidUser"));
                }
            }
        }
        if(!errors.isEmpty())
        {
            action = "failure";
            saveErrors(request, errors);
        }
        if(BuildConfig.DMODE) 
        {
            System.out.println("error:"+errors.size());
            System.out.println("outside Login"+action);
        }
        return mapping.findForward(action);
    }
    private String footerLink(String userId,String contextName)
    {
        UserDAO objUserDAO = new UserDAO();
        String  footerString = "&nbsp;";
        try
        {
            String firstMneuPath = objUserDAO.getFirstMenuPath(userId);
            footerString +="&nbsp;&nbsp;<a href='"+contextName+""+firstMneuPath+"?ftmsg=footer"+"'>Prospects</a>&nbsp;&nbsp;";
             if(objUserDAO.isUserElgible(userId,"Run"))
                footerString += "| &nbsp;&nbsp;<a  href='"+contextName+"/jsp/pricerun/run.jsp?ftmsg=footer'>Run</a>&nbsp;&nbsp;";
            if(objUserDAO.isUserElgible(userId,"Run Result"))
                footerString +="| &nbsp;&nbsp;<a href='"+contextName+"/jsp/pricerun/runresult.jsp?ftmsg=footer'>Run Results</a>&nbsp;&nbsp;";
            if(objUserDAO.isUserElgible(userId,"Contracts"))
                footerString +="| &nbsp;&nbsp;<a href='"+contextName+"/jsp/pricing/Contracts.jsp?ftmsg=footer'>Contracts</a>&nbsp;";
            
            footerString +="| &nbsp;&nbsp;<a href='"+contextName+"/jsp/pricerun/proposal.jsp?ftmsg=footer'>Price Quote</a>&nbsp;&nbsp;";
            footerString += " :&nbsp;&nbsp;For All Customers";
        }catch (Exception e) {
            e.printStackTrace();
        }
        return footerString;
    }
} 
