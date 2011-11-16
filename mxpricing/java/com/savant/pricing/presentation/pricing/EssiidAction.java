/*
 * Created on Apr 2, 2007
 *
 * ClassName	:  	EssiidAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pricing;

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

import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.ercotdownload.EsiidReportDetail;
import com.savant.pricing.ercotdownload.EsiidReportMain;
import com.savant.pricing.ercotdownload.EsiidResponseParser;
import com.savant.pricing.valueobjects.ESIIDDetailsVO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EssiidAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ESIIDDetailsVO objESIIDDetailsVO = new ESIIDDetailsVO();
		ProspectiveCustomerVO objProspectiveCustomerVO = new ProspectiveCustomerVO();
		ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
		EsiidReportDetail objDetail= new EsiidReportDetail();
		EsiidReportMain obj=new EsiidReportMain();
		EsiidResponseParser objParser=new EsiidResponseParser();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		
		 if(form instanceof EssiidForm)
		 {
		     EssiidForm frm = (EssiidForm)form;
		     String esiidNo = frm.getTxtEssiid();
		     boolean addResult = false;
		     if(frm.getFormActions().equalsIgnoreCase("add"))
		     {
		         if(esiidNo!=null)
		         {
		             try
		             {
		                 obj.searchEsiidDetail(esiidNo);
		                 objDetail=objParser.esiidReportparser();
		                 if(objDetail!=null)
		                 {
		                     String customerId = (String)request.getSession().getAttribute("customerId");
		                     objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(customerId));
		                     objESIIDDetailsVO.setProspectiveCustomer(objProspectiveCustomerVO);
		                     objESIIDDetailsVO.setEsiId(objDetail.getEsiid());
		                     objESIIDDetailsVO.setZip(Integer.parseInt(objDetail.getZip()));
		                     objESIIDDetailsVO.setCounty(objDetail.getState());
		                     objESIIDDetailsVO.setCity(objDetail.getCity());
		                     objESIIDDetailsVO.setDunsNo(Float.parseFloat(objDetail.getDuns()));
		                     objESIIDDetailsVO.setMetered(objDetail.getMeterRead());
		                     objESIIDDetailsVO.setPowerRegion(objDetail.getPowerRegion());
		                     objESIIDDetailsVO.setTdsp(objDetail.getMpName());
		                     objESIIDDetailsVO.setServiceAddress1(objDetail.getAddress());
		                     objESIIDDetailsVO.setState(objDetail.getState());
		                     objESIIDDetailsVO.setStationCode(objDetail.getStationCode());
		                     objESIIDDetailsVO.setStationName(objDetail.getStationName());
		                     objESIIDDetailsVO.setStatus(objDetail.getEsiidStatus());
		                     objESIIDDetailsVO.setServiceAddress2(objDetail.getStreet());
		                     addResult = objProspectiveCustomerDAO.addESIIDDetails(objESIIDDetailsVO);
		                     messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Esiid.add"));
		                     request.setAttribute("message","message");
		                 }
		                 else
		                 {
		                     System.out.println("inside try");
		                     errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Esiid.invalid"));
		                     request.setAttribute("message","error");
		                 }
		             }
		             catch(Exception e)
		             {
		                 if((e.getMessage().indexOf("duplicate key")!=-1))
		                 {
		                     errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("ESIID.Exist"));
		                     request.setAttribute("message","error");
		                 }
		             }
		             
		         }
		         else
		         {
		             errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("ESIID.empty"));
		             request.setAttribute("message","error");
		         }
		         if(addResult)
		         	messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Esiid.add"));
		             
		         saveMessages(request,messages);
		         if(!errors.isEmpty())
		         {
		            saveErrors(request,errors);
		         }
		         
		     }
		 }
	
        String action="success";
        
        return mapping.findForward(action);
    }
}

/*
*$Log: EssiidAction.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:49  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.9  2007/08/16 12:53:19  jnadesan
*message checked
*
*Revision 1.8  2007/08/16 10:14:41  spandiyarajan
*unwanted msg removed
*
*Revision 1.7  2007/07/03 05:01:40  kduraisamy
*message key added.
*
*Revision 1.6  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.5  2007/04/24 12:56:00  jnadesan
*Esiid error messages are added
*
*Revision 1.4  2007/04/24 10:12:44  jnadesan
*entry for esiid validation
*
*Revision 1.3  2007/04/18 03:56:49  kduraisamy
*imports organized.
*
*Revision 1.2  2007/04/07 12:12:15  rraman
*esiid action class created
*
*Revision 1.1  2007/04/02 14:34:31  rraman
*new form and action created
*
*
*/