/*
 * Created on Feb 21, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.security;

import java.util.Date;

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

import com.savant.pricing.securityadmin.dao.UserTypeDAO;
import com.savant.pricing.securityadmin.valueobject.UserTypesVO;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UsertypeAction extends Action 
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action="failure";
        ActionErrors actionErrors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        boolean result=false;
        
        if(form instanceof UsertypeForm)
        {            
            UsertypeForm frm = (UsertypeForm)form;
            UserTypeDAO userTypeDAO = new UserTypeDAO();
            if(frm.getFormAction().equalsIgnoreCase("Add"))
            {                
                try
                {
                    UserTypesVO userTypesVO = new UserTypesVO();
                    userTypesVO.setUserType(frm.getUserType());
                    UserTypesVO parent = new UserTypesVO();
                    parent.setUserTypeId(Integer.parseInt(frm.getParentType()));
                    userTypesVO.setParentUserType(parent);
                    userTypesVO.setDescription(frm.getComment());
                    userTypesVO.setCreatedDate(new Date());
                    userTypesVO.setValid(true);
                    result = userTypeDAO.addUserType(userTypesVO);
                    if(result)
                    {
                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.added","User Type",frm.getUserType()));
                        saveMessages(request,messages);  
                        request.setAttribute("message","message");
                    }
                }
                catch (HibernateException e)
                {
                    if(e.toString().indexOf("User Type Already Exists") >= 0)
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.usertypealreadyexists"));
                        if(!actionErrors.isEmpty())
                        {
                            saveErrors(request,actionErrors);
                            request.setAttribute("message","error");
                        }
                    }
                }                
            }
            if(frm.getFormAction().trim().equalsIgnoreCase("edit"))
            {
                try{ 
                    int userTypeid =0;
                    UserTypesVO userTypesVO = null;
                    UserTypesVO puserTypesVO = null;
                    
                    if(request.getParameter("userTypeid")!=null)
                    {
                        userTypeid = Integer.parseInt(request.getParameter("userTypeid"));
                        userTypesVO = userTypeDAO.getUserType(userTypeid);
                        frm.setUserType(userTypesVO.getUserType());
                        puserTypesVO = userTypesVO.getParentUserType();
                        frm.setParentType(puserTypesVO.getUserTypeId()+"");
                        frm.setComment(userTypesVO.getDescription());
                        frm.setUserTypeId(userTypesVO.getUserTypeId());
                    }
                }catch (Exception e) 
                {
                   actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.general"));
                   if(!actionErrors.isEmpty())
                   {
                       saveErrors(request,actionErrors);
                       request.setAttribute("message","error");
                   }
                }
            }
            else if(frm.getFormAction().trim().equalsIgnoreCase("update"))
            {
                UserTypesVO userTypesVO = new UserTypesVO();
                UserTypesVO puserTypesVO = new UserTypesVO();
                userTypesVO.setUserType(frm.getUserType());
                userTypesVO.setUserTypeId(frm.getUserTypeId());
                userTypesVO.setDescription(frm.getComment());
                puserTypesVO.setUserTypeId(Integer.parseInt(frm.getParentType()));
                userTypesVO.setParentUserType(puserTypesVO);
                userTypesVO.setValid(true);
                result = userTypeDAO.updateUserType(userTypesVO);
                if(result)
                {
                    messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.modified","User Type",frm.getUserType()));
                    saveMessages(request,messages);
                    request.setAttribute("message","message");
                }
            }
            
            else if(frm.getFormAction().trim().equalsIgnoreCase("delete"))
            {
                UserTypesVO userTypesVO = null;
                String temp[] = request.getParameter("userTypeid").split(","); 
                for (int i = 0; i < temp.length; i++) {
                   int  userTypeid = Integer.parseInt(temp[i]);
                   userTypesVO = userTypeDAO.getUserType(userTypeid);
                   result = userTypeDAO.deleteUserType(userTypesVO);    
                   if(result)
                   {
                       messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.delete.message","User Type",userTypesVO.getUserType()));
                       saveMessages(request,messages);
                       request.setAttribute("message","message");
                   }
               }
            }
            else if(frm.getFormAction().trim().equalsIgnoreCase("view"))
            {
                action = "view";
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
