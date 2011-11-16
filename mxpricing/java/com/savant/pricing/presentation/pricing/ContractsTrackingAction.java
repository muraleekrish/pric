/*
 * Created on May 3, 2007
 *
 * ClassName	:  	ContractsTrackingAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pricing;


import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.NumberUtil;
import com.savant.pricing.dao.ContractsTrackingDAO;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.valueobjects.ContractsTrackingVO;
import com.savant.pricing.valueobjects.CustomerCommentsVO;
import com.savant.pricing.valueobjects.CustomerStatusVO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractsTrackingAction extends Action 
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action="failure";
        ActionErrors actionErrors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        NumberFormat tnf = NumberUtil.tetraFraction();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        boolean result=false;
        
        if(form instanceof ContractsTrackingForm)
        {
            ContractsTrackingForm frm = (ContractsTrackingForm)form;
            ContractsTrackingDAO objContractsTrackingDAO = new ContractsTrackingDAO();
            System.out.println("********** form action :"+frm.getFormActions());
            if(frm.getFormActions().trim().equalsIgnoreCase("update"))
            {
                try
                {
                    ContractsTrackingVO objContractsTrackingVO = null;
                    ProspectiveCustomerVO objProspectiveCustomerVO = null;
                    
                    ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
                    objContractsTrackingVO = objContractsTrackingDAO.getContracts(frm.getContractTrackingIdentifier().trim());
                   
                    try
                    {
	                    objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(objContractsTrackingVO.getContractRef().getPriceRunCustomerRef().getProspectiveCustomer().getProspectiveCustomerId());
	                    CustomerCommentsVO objCustomerCommentsVO = null;
	                    if(!frm.getTxtcomments().trim().equalsIgnoreCase(""))
	                    {
	                    objCustomerCommentsVO = new CustomerCommentsVO();
	                    objCustomerCommentsVO.setComments(frm.getTxtcomments());
	                    objCustomerCommentsVO.setModifiedBy(String.valueOf(request.getSession().getAttribute("userName")));
	                    objCustomerCommentsVO.setModifiedDate(new Date());
	                    objCustomerCommentsVO.setCreatedDate(new Date());
	                    objCustomerCommentsVO.setCreatedBy(String.valueOf(request.getSession().getAttribute("userName")));
	                    }
	                    objProspectiveCustomerVO.setComments(objCustomerCommentsVO);
	                    objProspectiveCustomerDAO.updateProspectiveCustomer(objProspectiveCustomerVO);
	                   // objProspectiveCustomerDAO.updtaeProspectiveCommentsIntoCMS(objProspectiveCustomerVO.getProspectiveCustomerId(),objProspectiveCustomerVO.getCustomerId().intValue());
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    CustomerStatusVO objCustomerStatusVO = new CustomerStatusVO();
                    System.out.println("cont status :"+frm.getContractStatus());
                    if(frm.getContractStatus().trim().equals("2")||frm.getContractStatus().trim().equals("11"))
                    {
	                    objContractsTrackingVO.setCmsContractTypeId(0);
	                    objContractsTrackingVO.setCmsContractStatusId(0);
	                    objContractsTrackingVO.setCmsMXenergyRateClassId(0);
	                    objContractsTrackingVO.setContractStartDate(df.parse(frm.getContractStartDate()));
	                    objContractsTrackingVO.setContractEndDate(new Date(df.parse(frm.getContractStartDate()).getYear(),df.parse(frm.getContractStartDate()).getMonth()+objContractsTrackingVO.getContractRef().getTerm(),df.parse(frm.getContractStartDate()).getDate()-1));
                    }
                    else
                    {
                        objContractsTrackingVO.setCmsContractTypeId(0);
	                    objContractsTrackingVO.setCmsContractStatusId(0);
	                    objContractsTrackingVO.setCmsMXenergyRateClassId(0);                        
                    }
                     
                    if(frm.getFormActions().trim().equalsIgnoreCase("update"))
                    {
                       /* boolean writeToCMSResult = false;
                        if(Integer.parseInt(frm.getContractStatus())==2||Integer.parseInt(frm.getContractStatus())==11)
                        {
                            writeToCMSResult = this.exportContractDetailsIntoCMS(objContractsTrackingVO,objProspectiveCustomerVO.getCustomerId().intValue(),frm);
                        }
                        // changes the status as Entered into CMS if the Contract details moved successfully to the CMS.
                        if(writeToCMSResult)
                            objCustomerStatusVO.setCustomerStatusId(11);
                        else*/
                        objCustomerStatusVO.setCustomerStatusId(Integer.parseInt(frm.getContractStatus()));
                        objContractsTrackingVO.setCustomerStatus(objCustomerStatusVO);
                        result = objContractsTrackingDAO.updateContract(objContractsTrackingVO);
                        if(result)
                        {
                            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.modified","Contracts Tracking",frm.getContractTrackingIdentifier()));
                            saveMessages(request,messages);
                            request.setAttribute("message","message");
                        }
                    }
                }
                catch(HibernateException e)
                {
                        if(!actionErrors.isEmpty())
                            saveErrors(request,actionErrors);	
                }   
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
          
            if(frm.getFormActions().trim().equalsIgnoreCase("edit"))
            {
                try{ 
                    String contrId = "";
                    ContractsTrackingVO objContractsTrackingVO = null;
                    if(request.getParameter("ContractId")!=null)
                    {
                        if(BuildConfig.DMODE)
                            System.out.println("ContractId :"+request.getParameter("ContractId"));
                        
                        contrId = request.getParameter("ContractId");
                        objContractsTrackingVO = objContractsTrackingDAO.getContracts(contrId);
                        frm.setContractTrackingIdentifier(objContractsTrackingVO.getContractTrackingIdentifier());
                        frm.setCustomerName(objContractsTrackingVO.getContractRef().getPriceRunCustomerRef().getProspectiveCustomer().getCustomerName());
                        frm.setCustomerId(objContractsTrackingVO.getContractRef().getPriceRunCustomerRef().getProspectiveCustomer().getCustomerId().intValue());
                        request.setAttribute("CustomerId",new Integer(objContractsTrackingVO.getContractRef().getPriceRunCustomerRef().getProspectiveCustomer().getProspectiveCustomerId()));
                        frm.setProductName(objContractsTrackingVO.getContractRef().getProduct().getProductName());
                        frm.setTerm(String.valueOf(objContractsTrackingVO.getContractRef().getTerm()));
                        if(objContractsTrackingVO.getContractRef().getProduct().getProductIdentifier()==5)
                            frm.setPrice(String.valueOf(tnf.format(objContractsTrackingVO.getContractRef().getBaseRate$PerMWh()/1000)));
                        else
                            frm.setPrice(String.valueOf(tnf.format(objContractsTrackingVO.getContractRef().getFixedPrice$PerMWh()/1000)));
                      //  frm.setContractType(String.valueOf(objContractsTrackingVO.getCmsContractTypeId()));
                       //frm.setContractCMSStatus(String.valueOf(objContractsTrackingVO.getCmsContractStatusId()));
                       // frm.setRateClass(String.valueOf(objContractsTrackingVO.getCmsTriEagleRateClassId()));
                        frm.setContractStartDate(df.format(objContractsTrackingVO.getContractStartDate()));
                        frm.setContractEndDate(df.format(objContractsTrackingVO.getContractEndDate()));
                    } 
                   
                }catch (Exception e) {
                    e.printStackTrace();
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
    private boolean exportContractDetailsIntoCMS(ContractsTrackingVO objContractsTrackingVO,int custId,ContractsTrackingForm frm) throws SQLException
    {
        String linkId = "EPP";
        Date proposaldate = objContractsTrackingVO.getContractRef().getPriceRunCustomerRef().getPriceRunRef().getPriceRunTime();
        Date currentTime = new Date();
        proposaldate.setHours(currentTime.getHours());
        proposaldate.setMinutes(currentTime.getMinutes());
        proposaldate.setSeconds(currentTime.getSeconds());
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
        linkId += custId+"_"+df.format(proposaldate);
        Session objSession = null;
        boolean result = false;
        CallableStatement cstmnt = null;
        try
        {
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            cstmnt = objSession.connection().prepareCall("{call sp_ContractsIntoCMS (?,?,?,?,?,?)}");
            cstmnt.setInt(1, objContractsTrackingVO.getContractRef().getContractIdentifier());
            cstmnt.setString(2, linkId);
            cstmnt.setInt(3, custId);
            cstmnt.setInt(4, Integer.parseInt(frm.getContractType().trim()));
            cstmnt.setInt(5, Integer.parseInt(frm.getContractCMSStatus().trim()));
            cstmnt.setInt(6, Integer.parseInt(frm.getRateClass().trim()));
            cstmnt.execute();
            objSession.getTransaction().commit();
            result = true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            objSession.getTransaction().rollback();
        }
        finally
        {
            cstmnt.close();
            objSession.close();
        }
        return result;
    }
}

/*
*$Log: ContractsTrackingAction.java,v $
*Revision 1.2  2007/12/20 10:07:32  tannamalai
*trieagle name removed
*
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/23 11:44:43  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/10/30 05:51:49  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.23  2007/09/24 13:14:49  jnadesan
*Rate class Contract type set as mandatory
*
*Revision 1.22  2007/09/24 12:08:51  jnadesan
*Entered into CMS status added.
*
*Revision 1.21  2007/07/31 11:40:55  kduraisamy
*objsession properly closed or calling procedure properly done.
*
*Revision 1.20  2007/07/04 14:46:09  jnadesan
*comments added by changed
*
*Revision 1.19  2007/06/27 12:38:06  jnadesan
*commets are not updated if its empty
*
*Revision 1.18  2007/06/26 12:03:47  jnadesan
*comments added into cms while updating
*
*Revision 1.17  2007/06/21 11:24:14  jnadesan
*viewing and editing comments in contract tracking page has finished
*
*Revision 1.16  2007/06/20 08:08:11  spandiyarajan
*comments updated
*
*Revision 1.15  2007/06/13 04:10:44  kduraisamy
*2005 jar included.
*
*Revision 1.14  2007/06/06 16:17:24  jnadesan
*Contract strartmoth will be selected while contrcat
*status is changed into signed
*
*Revision 1.13  2007/06/04 12:42:32  spandiyarajan
*added one more field
*
*Revision 1.12  2007/06/02 09:37:20  jnadesan
*while exporting into cms prospective customer id placed instead of prospective customer id
*
*Revision 1.11  2007/06/01 09:19:56  spandiyarajan
*edit and update - added cms contractstatus and contract type
*
*Revision 1.10  2007/05/30 11:33:09  jnadesan
*unwanted lines removed
*
*Revision 1.9  2007/05/29 11:48:09  jnadesan
*link id changed
*
*Revision 1.8  2007/05/28 15:07:34  jnadesan
*procedure called to export cotract details into cms
*
*Revision 1.7  2007/05/28 10:49:11  jnadesan
*taking fixed price is changed
*
*Revision 1.6  2007/05/23 06:17:18  spandiyarajan
*set the div height for list page according the screen height
*
*Revision 1.5  2007/05/15 10:42:17  spandiyarajan
*comments functionality changed.
*
*Revision 1.4  2007/05/15 09:41:51  spandiyarajan
*commited after fixed the bug
*
*Revision 1.3  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.2  2007/05/05 06:00:31  spandiyarajan
*price value fomated for 4 decimal point
*
*Revision 1.1  2007/05/03 12:45:05  spandiyarajan
*initilally commit the update part of contractstracking
*
*
*/