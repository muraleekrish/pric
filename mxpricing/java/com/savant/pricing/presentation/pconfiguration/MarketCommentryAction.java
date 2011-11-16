/*
 * Created on Nov 19, 2007
 *
 * ClassName	:  	MarketCommentryAction.java
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
import java.util.Date;
import java.util.Vector;
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
import com.savant.pricing.calculation.valueobjects.MarketCommentryVO;
import com.savant.pricing.dao.MarketCommentryDAO;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MarketCommentryAction extends Action
{

    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action = "failure";  
        ActionErrors actionErrors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        boolean addUpdateResult = false;
        if(form instanceof MarketCommentryForm)
        {
            MarketCommentryForm frm = (MarketCommentryForm)form;
            MarketCommentryVO commentryVO = new MarketCommentryVO();
            MarketCommentryDAO commentryDAO = new MarketCommentryDAO();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            if(frm.getFormAction().equalsIgnoreCase("add"))
            {
                frm.setCreatedDate(sdf.format(new Date()));
                frm.setModifiedDate(sdf.format(new Date()));
                commentryVO.setCreatedBy(frm.getCreatedBy());
                commentryVO.setModifiedBy(frm.getModifiedBy());
                commentryVO.setMarketComments(frm.getTxtMrktCommentry());
                try
                {
                    commentryVO.setMarketDate(sdf.parse(frm.getTxtMrktDate()));
                    commentryVO.setModifiedDate(sdf.parse(frm.getModifiedDate()));
                    commentryVO.setCreatedDate(sdf.parse(frm.getCreatedDate()));
                }
                catch (ParseException e1)
                {
                    e1.printStackTrace();
                }
                try
                {
                    addUpdateResult = commentryDAO.addMrktCommtry(commentryVO);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if(addUpdateResult)
                {
                    action = "success";
                    messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.mrkt.added","New Market Commentary",""));
                    saveMessages(request,messages);  
                    request.setAttribute("message","message");
                }
                else
                {
                    action = "failure";
                    actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.add.marketcmmtry"));
                    if(!actionErrors.isEmpty())
                    {
                        saveErrors(request,actionErrors);
                        request.setAttribute("message","error");
                    }
                }
                frm.setFormAction("");
            }
            else if(frm.getFormAction().equals("delete"))
            {
                String[] ids = frm.getMrktIds();
                Vector v= new Vector();
                boolean result = false;
                int totdeleted = 0;
                System.out.println("Ids Length ***** :"+ids.length);
                for( int i=0; i<ids.length;i++)
                {
                    System.out.println("Ids  ***** :"+ids[i]);
                    result = commentryDAO.deleteMrktCmmtry(Integer.parseInt(ids[i]));
                    System.out.println("Delete result :"+result);
                    if(result)
                    {
                        totdeleted++;
                    }
                }
                if(totdeleted==0)
                {
                    actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.delete.marketcmmtry"));
                    if(!actionErrors.isEmpty())
                    {
                        saveErrors(request,actionErrors);
                        request.setAttribute("message","error");
                    }
                }
                else
                {
                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.delete.mrketComntry"));
                        saveMessages(request,messages);  
                        request.setAttribute("message","message");
                }
                action="success";
            }
            else if(frm.getFormAction().equals("get"))
            {
                String id = "0";
                if(request.getParameter("hiddentxt")!=null)
            		id = request.getParameter("hiddentxt");
            	commentryVO = commentryDAO.getMarketCommentryVO(Integer.parseInt(id));
            	System.out.println(" /******/ "+commentryVO.getMarketComments());
            	frm.setCommentid(commentryVO.getCommentryId());
            	frm.setTxtMrktCommentry(commentryVO.getMarketComments());
            	frm.setTxtMrktDate(sdf.format(commentryVO.getMarketDate()));
            	frm.setCreatedBy(commentryVO.getCreatedBy());
            	frm.setModifiedBy(commentryVO.getModifiedBy());
            	frm.setCreatedBy(sdf.format(commentryVO.getCreatedDate()));
            	frm.setModifiedDate(sdf.format(commentryVO.getModifiedDate()));
            }
            else if(frm.getFormAction().equals("modify"))
            {
                if(frm.getCommentid()==0)
                {
                    actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.modify.marketcmmtry.novalue"));
                    if(!actionErrors.isEmpty())
                    {
                        saveErrors(request,actionErrors);
                        request.setAttribute("message","error");
                    }
                }
                else
                {
                    commentryVO = commentryDAO.getMarketCommentryVO(frm.getCommentid());
                    frm.setModifiedDate(sdf.format(new Date()));
                    commentryVO.setCommentryId(frm.getCommentid());
                    commentryVO.setModifiedBy(frm.getModifiedBy());
                    commentryVO.setMarketComments(frm.getTxtMrktCommentry());
                    try
                    {
                        commentryVO.setMarketDate(sdf.parse(frm.getTxtMrktDate()));
                        commentryVO.setModifiedDate(sdf.parse(frm.getModifiedDate()));
                    }
                    catch(ParseException e1)
                    {
                        e1.printStackTrace();
                    }
                    
                    try
                    {
                        addUpdateResult = commentryDAO.updateMrktCmmtry(commentryVO);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(addUpdateResult)
                    {
                        action="success";
                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.mrkt.modified","Selected Market Commentary",""));
                        saveMessages(request,messages);  
                        request.setAttribute("message","message");
                    }
                    else
                    {
                        action="failure";
                        actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.modify.marketcmmtry"));
                        if(!actionErrors.isEmpty())
                        {
                            saveErrors(request,actionErrors);
                            request.setAttribute("message","error");
                        }
                    }
                    
                }
                frm.setFormAction("");
            }
        }
        if(addUpdateResult)
        {
            action="success";
        }
        else
        {
            action="failure";
        }
        return mapping.findForward( action );
    }
}

/*
*$Log: MarketCommentryAction.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.5  2007/11/29 10:19:55  tannamalai
*add validation done
*
*Revision 1.4  2007/11/28 13:06:10  jnadesan
*impotrs organized
*
*Revision 1.3  2007/11/28 10:30:32  tannamalai
*new messages added in propery file
*
*Revision 1.2  2007/11/23 05:29:12  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/11/20 04:52:53  tannamalai
**** empty log message ***
*
*
*/