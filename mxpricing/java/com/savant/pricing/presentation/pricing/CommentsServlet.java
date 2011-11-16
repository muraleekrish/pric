/*
 * Created on Apr 25, 2007
 *
 * ClassName	:  	GetParentUserServlet.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pricing;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.valueobjects.CustomerCommentsVO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommentsServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)throws ServletException, IOException 
    {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(BuildConfig.DMODE)
        {
            System.out.println("CMSId :"+request.getParameter("CMSId"));
            System.out.println("version :"+request.getParameter("version"));
        }
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        String select = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        try
        {
            String CMSId =  request.getParameter("CMSId");
            String version =  request.getParameter("version");
            String message =  request.getParameter("message");
            String pageAct =  request.getParameter("pageAct");
            if(!CMSId.equals(""))
            {
                try
                {                    
                    ProspectiveCustomerVO  objProspectiveCustomerVO = null;
                    objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(CMSId));
                    int txtRecVal=0;
                    
                    if(BuildConfig.DMODE)
                    {
	                    System.out.println("version :"+version);
	                    System.out.println("message :"+message);
                    }
                    
                    Set objSet = null;
                	objSet = objProspectiveCustomerVO.getCustomerComments();
                	if(message.equals("prev"))
                        txtRecVal = Integer.parseInt(version)-1;
                	else if(message.equals("next"))
                	    txtRecVal = Integer.parseInt(version)+1;
                	
                	String idPrev = "";
                	String idNext = "";
                	if(txtRecVal >= objSet.size() || txtRecVal <= 1)
                	{
                	    if(message.equals("prev"))
                	    {
                	        idPrev = "true";
                	        idNext = "false";
                	    }
                	    else if(message.equals("next"))
                	    {
                	        idPrev = "false";
                	        idNext = "true";
                	    }
                	}
                	
                	Iterator itr = objSet.iterator();
                  	CustomerCommentsVO objCustomerCommentsVO = null;
                  	while(itr.hasNext())
                  	{
                  		objCustomerCommentsVO = (CustomerCommentsVO)itr.next();
                  		if(objCustomerCommentsVO.getVersion() == txtRecVal)
                  			break;
                  	}
                    
                  	String custId = objProspectiveCustomerVO.getCustomerId()==null?"":String.valueOf(objProspectiveCustomerVO.getCustomerId());
                    String commentId = String.valueOf(objCustomerCommentsVO.getCommentId());
                    String comments = objCustomerCommentsVO.getComments()==null?"":String.valueOf(objCustomerCommentsVO.getComments());
                    String createdBy = objCustomerCommentsVO.getCreatedBy()==null?"":String.valueOf(objCustomerCommentsVO.getCreatedBy());
                    String createdDate = objCustomerCommentsVO.getCreatedDate()==null?"":sdf.format(objCustomerCommentsVO.getCreatedDate());
                    String modifiedBy = objCustomerCommentsVO.getModifiedBy()==null?"":String.valueOf(objCustomerCommentsVO.getModifiedBy());
                    String modifiedDate = objCustomerCommentsVO.getModifiedDate()==null?"":sdf.format(objCustomerCommentsVO.getModifiedDate());
                    String txtVerVal = String.valueOf(objCustomerCommentsVO.getVersion());
                    
                    select += "<fieldset><legend>Comments</legend>"+
                      	"<table border='0' cellspacing='0' cellpadding='0'><tr>"+
                      	"<td width='170' height='22' class='fieldtitle'>Comment Id</td>"+
                        "<td width='10' class='fieldtitle'>:</td>"+
                        "<td width='230' class='fieldata' id='idCommentId'>"+commentId+
                        "</td><td width='135' class='fieldtitle'>Customer Id</td>"+
                        "<td width='10' class='fieldtitle'>:</td>"+
                        "<td width='405' class='fieldata'>"+custId+
                        "</td></tr><tr><td width='170' class='fieldtitle' valign='top'>Comments</td>"+
                        "<td width='10' class='fieldtitle' valign='top'>:</td><td colspan='4' class='fieldata'>";
                    	
                    	if(pageAct.equals("view"))
                    	{
                    	    select += "<textarea name='txtcommentsGeneral' style='border:solid 1px #DDDDDD;color:0000FF' rows='4' cols='80' readonly='true'>"+
                            comments+"</textarea>";
                    	}
                    	else
                    	{
                    	    select += "<textarea name='txtcommentsGeneral' style='border:solid 1px #DDDDDD;color:0000FF' rows='4' cols='80' id='commentsId'>"+
                            comments+"</textarea>";
                    	}
                    
                    	select += "</td></tr><tr><td colspan='2' valign='middle'>&nbsp;Version(s)&nbsp;	";
                    	
                    	if(idPrev.equals("true") && !idPrev.equals(""))
                    	{}else
                    	{
                    	    select += "<a href=\"javascript:servletaccess('prev')\" ><img src='"+request.getContextPath()+"/images/prev.gif' alt='Previous' width='6' height='12' border='1'></a>";
                    	}
                    	select +=  "&nbsp;<input type='text' name='records' readonly='true' size='3' value='"+txtVerVal+"' />&nbsp;"; 
                    	if(idNext.equals("true") && !idNext.equals(""))
                     	{}
                    	else
                    	{
                     	    select += "<a href=\"javascript:servletaccess('next')\" ><img src='"+request.getContextPath()+"/images/next.gif' alt='Next' width='6' height='12' border='1'></a>";
                     	}
                    	select += "&nbsp; of "+objSet.size()+"</td><td colspan='4'>"+
                    	"<table border='0' widht='100%' cellspacing='0' cellpadding='0'>"+
                    	"<tr><td width='120' height='22' class='fieldtitle'>Created By :</td>"+
                    	"<td width='100' class='fieldata' id='idCreatedBy'>"+createdBy+"</td>"+
                    	"<td width='133' class='fieldtitle'>Modified By :</td>"+
                    	"<td class='fieldata' id='idModifiedBy'>"+modifiedBy+"</td></tr>"+
                    	"<tr><td width='120' height='22' class='fieldtitle'>Created Date :</td>"+
                    	"<td width='100' class='fieldata' id='idCreatedDate'>"+createdDate+"</td>"+
                    	"<td width='133' class='fieldtitle'>Modified Date :</td>"+
                    	"<td class='fieldata' id='idModifiedDate'>"+modifiedDate+"</td></tr></table></td></tr></table></legend>";
                    
                    if(BuildConfig.DMODE)
                        System.out.println("select :"+select);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }   
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        if(BuildConfig.DMODE)
            System.out.println("select:"+select);
        response.setContentType("*/*");
        response.getWriter().write(select);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        System.gc();
    }
}



	




/*
*$Log: CommentsServlet.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:49  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/06/25 09:21:40  jnadesan
*comments box size changed
*
*Revision 1.3  2007/06/21 10:17:07  jnadesan
*editing comments problem solved
*
*Revision 1.2  2007/06/20 08:08:11  spandiyarajan
*comments updated
*
*Revision 1.1  2007/06/19 14:02:34  spandiyarajan
*new fucntionality for comments
*
*Revision 1.6  2007/06/14 04:12:17  spandiyarajan
*code aligned properly
*
*Revision 1.5  2007/06/13 13:44:42  spandiyarajan
*trim added
*
*Revision 1.4  2007/06/12 07:31:13  spandiyarajan
*alteration in user add & modify page
*
*Revision 1.3  2007/05/25 10:06:10  jnadesan
*unwanted lines removed
*
*Revision 1.2  2007/05/07 07:32:27  spandiyarajan
*choose option properly chaged to 'select one'
*
*Revision 1.1  2007/04/30 05:42:11  spandiyarajan
*added getparentuser servlet - initial commit
*
*
*/