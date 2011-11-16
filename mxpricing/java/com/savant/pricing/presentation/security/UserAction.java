/*
 * Created on Feb 21, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
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
import com.savant.pricing.securityadmin.dao.RolesDAO;
import com.savant.pricing.securityadmin.dao.UserDAO;
import com.savant.pricing.securityadmin.valueobject.RolesVO;
import com.savant.pricing.securityadmin.valueobject.UserTypesVO;
import com.savant.pricing.securityadmin.valueobject.UsersVO;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserAction extends Action 
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalStateException, IllegalBlockSizeException, BadPaddingException
    {
        String action="failure";
        ActionErrors actionErrors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        boolean result=false;
        RolesDAO rolesDAO = new RolesDAO();
        if(form instanceof UserForm)
        {
            UserForm frm = (UserForm)form;
            UserDAO usersDAO = new UserDAO();
            
            if(frm.getFormAction().trim().equalsIgnoreCase("userTypeChange"))
            {
                action = "failure";    
            }
            if(frm.getFormAction().equalsIgnoreCase("Add")||frm.getFormAction().trim().equalsIgnoreCase("update") )
            {
                if(frm.getChangePasswordStatus() != null && frm.getChangePasswordStatus().equalsIgnoreCase("changed"))
                {	                
                    if(frm.getOldPassword() == null || frm.getOldPassword().length()<1 || frm.getOldPassword().equals(""))
                        actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Old Passworddd"));
                    else if(frm.getOldPassword() != null && !frm.getPassword().equals(frm.getOldPassword()))
                        actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.password"));
                    else if(frm.getNewPassword() == null || frm.getNewPassword().length()<1 || frm.getNewPassword().equals(""))
                        actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","New Password"));
                    else if(frm.getConPassword() == null || frm.getConPassword().length()<1 || frm.getConPassword().equals(""))
                        actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Confirm Password"));
                    else if(frm.getNewPassword() != null && frm.getConPassword() != null && !frm.getNewPassword().equals(frm.getConPassword()))
                        actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.modpwddoesnotmatch"));
                    result = true;
                }
                if(actionErrors.isEmpty())
	            {
	                try
	                {
	                    UserTypesVO userTypeVO = new UserTypesVO();
	                    UsersVO usersVO = new UsersVO();
	                    UsersVO parentUsersVO = new UsersVO();
	                    usersVO.setUserId(frm.getUserName());
	                    usersVO.setFirstName(frm.getFirstName());
	                    usersVO.setLastName(frm.getLastName());
	                    if(frm.getChangePasswordStatus() != null && frm.getChangePasswordStatus().equalsIgnoreCase("changed"))
	                    {
	                        usersVO.setPassword(frm.getNewPassword());
	                    }
	                    else
	                    {    
	                        usersVO.setPassword(frm.getPassword());
	                    }
	                    usersVO.setEmailId(frm.getEmail());
	                    usersVO.setComment(frm.getComment());
	                    usersVO.setCreatedDate(new Date());
	                    userTypeVO.setUserTypeId(Integer
	                            .parseInt(frm.getUserType()));
	                    usersVO.setUserTypes(userTypeVO);
	                    if (!frm.getParentUserid().equals("0")) 
	                    {
	                        parentUsersVO.setUserId(frm.getParentUserid());
	                        usersVO.setParentUser(parentUsersVO);
	                    }
	                    usersVO.setValid(true);
	                    
	                    
	                    RolesVO rolesVO = null;
	                    if (frm.getRole() != null)
	                        rolesVO = rolesDAO.getRoles(frm.getRole());
	                    
	                    if (frm.getFormAction().trim().equalsIgnoreCase("Add")) 
	                    {
	                        result = usersDAO.addUsers(usersVO, rolesVO);
	                        if (result) 
	                        {
	                            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("common.message.added", "User", frm.getUserName()));
	                            saveMessages(request, messages);
	                            request.setAttribute("message", "message");
	                        }
	                        result = true;
	                    }
	                    if (frm.getFormAction().trim().equalsIgnoreCase("update")) 
	                    {
	                        result = usersDAO.updateUser(usersVO, rolesVO);
	                        if (result) 
	                        {
	                            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("common.message.modified", "User", frm.getUserName()));
	                            saveMessages(request, messages);
	                            request.setAttribute("message", "message");
	                        }
	                        result = false;
	                    }
	                }
	                catch(HibernateException e)
	                {
	                    if(e.toString().indexOf("User Id already exist") >= 0)
	                    {
	                        actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.useridalreadyexists"));
	                        if(!actionErrors.isEmpty())
	                        {
	                            saveErrors(request,actionErrors);
	                            request.setAttribute("message","error");
	                        }
	                    }
	                 }
	            }
            }
            
            if(frm.getFormAction().trim().equalsIgnoreCase("edit"))
            {
                String userName = (String) request.getSession().getAttribute("userName");
                String userId   = request.getParameter("userid").trim();
                userName = userName.trim();

                if(!userName.equalsIgnoreCase(userId))
                {
	                try
	                { 
	                    String userid ="";
	                    UsersVO usersVO = null;
	                    UsersVO pusersVO = null;
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
	                        frm.setFirstName(usersVO.getFirstName());
	                        frm.setLastName(usersVO.getLastName());
	                        frm.setEmail(usersVO.getEmailId());
	                        frm.setComment(usersVO.getComment());
	                        pusersVO = usersVO.getParentUser();
	                        frm.setParentUserid(pusersVO.getUserId());
	                        UserTypesVO userTypesVO =  usersVO.getUserTypes();
	                        frm.setUserType(userTypesVO.getUserTypeId()+"");
	                        int roleId = rolesDAO.getRoleId(userId);
	                        frm.setRole(roleId+"");
	                    }
	                    result  = true;
	                }
	                catch (Exception e) 
	                {
	                    e.printStackTrace();
	                    actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Prospective.customer.customerId.numeric"));
	                    request.setAttribute("message","error");
	                }
                }
                else
                {
                    actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.loginuser", "User", userId, "modified"));
	                saveErrors(request, actionErrors);
	                request.setAttribute("message", "error");
	                frm.setFormAction("list");
	                result = false;
                }
            }
        }
        if(result)
        {
            action = "success";
        }
        else
        {
            action = "failure";
        }
        			
        if(!actionErrors.isEmpty())
        {
            saveErrors(request, actionErrors);
        }
        return mapping.findForward(action);
    }
}
