/*
 * Created on Jan 24, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.util.List;

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
import com.savant.pricing.securityadmin.valueobject.UsersVO;
import com.savant.pricing.valueobjects.CDRStatusVO;
import com.savant.pricing.valueobjects.CustomerStatusVO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author srajappan
 *o
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProspectiveCustomerMngrListAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String action="failure";
        ActionErrors actionErrors = new ActionErrors();
        ActionMessages actionMessage = new ActionMessages();
        boolean result=false;
        if(form instanceof ProspectiveCustomerAddForm)
        {
            ProspectiveCustomerAddForm frm = (ProspectiveCustomerAddForm)form;
            ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
            if(request.getParameter("formActoin")!= null && request.getParameter("formActoin").trim().equalsIgnoreCase("edit"))
                frm.setFormActions("edit");
            if(frm.getFormActions().trim().equalsIgnoreCase("next"))
            {
                action="next";
            }
            if(frm.getFormActions().trim().equalsIgnoreCase("edit"))
            {
                try
                { 
                    int prsCustId =0;
                    if(request.getParameter("prsCustId")!=null)
                    {
                        prsCustId = Integer.parseInt(request.getParameter("prsCustId"));
                        result =  this.editProspectiveCustomer(frm,prsCustId);
                    }
                }catch (Exception e) 
                {
                    e.printStackTrace();
                    actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Prospective.customer.customerId.numeric"));
                }
                if(result)
                {
                    action="success";
                }
                
            }
            else if(frm.getFormActions().trim().equalsIgnoreCase("update"))
            {
                ProspectiveCustomerVO objProspectiveCustomerVO = this.addProspectiveCustomer(frm);
                if(objProspectiveCustomerVO!=null)
                {
                    objProspectiveCustomerVO.setProspectiveCustomerId(Integer.parseInt(frm.getProspectiveCustId()));
                    result=objProspectiveCustomerDAO.updateProspectiveCustomer(objProspectiveCustomerVO);
                }
                else
                {
                    result=false;
                }
                if(!result)
                {
                    action="success";
                    actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("Prospective.customer.add.madatory"));
                }      
            }else if(frm.getFormActions().trim().equalsIgnoreCase("approve"))
            {
                frm.setProspectiveCustId((String)request.getSession().getAttribute("prsCustId"));
                List lstPreferenceProducts =   objProspectiveCustomerDAO.getProspectiveCustomerPreferenceProducts(Integer.parseInt(frm.getProspectiveCustId()));
                if(lstPreferenceProducts.size()>0)
                {
                    if(frm.getProspectiveCustId()!=null)
                        result = objProspectiveCustomerDAO.updateProspectiveCustomerStatus(Integer.parseInt(frm.getProspectiveCustId()),1);
                    action = "approve";
                    if(result)
                    {
                        actionMessage.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Prospective.customer.status.success","Approved"));
                        saveMessages(request,actionMessage);
                    }
                    else
                    {
                        actionErrors.add(ActionErrors.GLOBAL_MESSAGE,new ActionError("Prospective.customer.status.failure"));
                        saveErrors(request,actionErrors);
                    }
                    saveMessages(request,actionMessage);
                }
                else
                {
                    action = "approve";
                    actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.customer.preference.check"));
                    saveErrors(request,actionErrors);
                }
            }else if(frm.getFormActions().trim().equalsIgnoreCase("reject"))
            {
                frm.setProspectiveCustId((String)request.getSession().getAttribute("prsCustId"));
                if(frm.getProspectiveCustId()!=null)
                    result = objProspectiveCustomerDAO.updateProspectiveCustomerStatus(Integer.parseInt(frm.getProspectiveCustId()),2);
                if(result)
                    actionMessage.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Prospective.customer.status.success","Rejected"));
             
                action="failure";
            }
            saveMessages(request,actionMessage);
        }
       
        if(!actionErrors.isEmpty())
        {
            saveErrors(request, actionErrors);
        }
        return mapping.findForward(action);
    }

    private boolean editProspectiveCustomer(ProspectiveCustomerAddForm frm,int prsCustId)
    {
        boolean result=true;
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        ProspectiveCustomerVO  objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(prsCustId);
        frm.setProspectiveCustId(prsCustId+"");
        frm.setTxtCustomerCMSId(objProspectiveCustomerVO.getCustomerId()+"");
        frm.setTxtCustomerName(objProspectiveCustomerVO.getCustomerName());
        frm.setTxtCustomerAddress( objProspectiveCustomerVO.getAddress());
        frm.setTxtBiztype(objProspectiveCustomerVO.getBusinessType());
        frm.setTxtCustomerCity(objProspectiveCustomerVO.getCity());
        frm.setTxtCurentProvider(objProspectiveCustomerVO.getCurrentProvider());
        frm.setCmbCustomerState(objProspectiveCustomerVO.getState());
        CustomerStatusVO  objCustomerStatusVO =objProspectiveCustomerVO.getCustomerStatus();
        frm.setCmbCustomerStatus(objCustomerStatusVO.getCustomerStatusId()+"");
        frm.setPntofCntFirstname(objProspectiveCustomerVO.getPocFirstName());
        frm.setPntofCntLastname(objProspectiveCustomerVO.getPocLastName());
        frm.setTxtCustomerTitle( objProspectiveCustomerVO.getTitle());
        frm.setTxtPhone(objProspectiveCustomerVO.getPhone());
        frm.setTxtFax(objProspectiveCustomerVO.getFax());
        frm.setTxtMobile(objProspectiveCustomerVO.getMobile());
        frm.setTxtEmail(objProspectiveCustomerVO.getEmail());
        frm.setTxtzipcode(objProspectiveCustomerVO.getZipCode()==null?"":objProspectiveCustomerVO.getZipCode());
        UsersVO objUsersVO = objProspectiveCustomerVO.getSalesRep();
    if(objUsersVO != null)
    {
        frm.setCmbSalesRep(objUsersVO.getUserId()+"");
    }
        CDRStatusVO statusVO = objProspectiveCustomerVO.getCdrStatus();
        frm.setCmbCDRState(statusVO.getCdrStateId()+"");
        return result;
    }
    
    private ProspectiveCustomerVO addProspectiveCustomer(ProspectiveCustomerAddForm frm) throws NumberFormatException
    {
        boolean result=true;
        ProspectiveCustomerVO objProspectiveCustomerVO = new ProspectiveCustomerVO();
        if(!frm.getTxtCustomerCMSId().trim().equalsIgnoreCase(""))
        {
            int custId = 0;
            try{
                custId = Integer.parseInt(frm.getTxtCustomerCMSId().trim());
            }catch (NumberFormatException e) {
               throw new NumberFormatException("Customer Id must be numeric");
            }
            objProspectiveCustomerVO.setCustomerId(new Integer(custId));
        }
        if(!frm.getTxtCustomerName().trim().equalsIgnoreCase(""))
        {
            objProspectiveCustomerVO.setCustomerName(frm.getTxtCustomerName());
        }
        else
        {
            result=false;
        }
        objProspectiveCustomerVO.setAddress(frm.getTxtCustomerAddress());
        objProspectiveCustomerVO.setBusinessType(frm.getTxtBiztype());
        objProspectiveCustomerVO.setCity(frm.getTxtCustomerCity());
        objProspectiveCustomerVO.setCurrentProvider(frm.getTxtCurentProvider());
        objProspectiveCustomerVO.setState(frm.getCmbCustomerState());
        CustomerStatusVO objCustomerStatusVO = new CustomerStatusVO();
        if(!frm.getCmbCustomerStatus().trim().equalsIgnoreCase("0"))
            objCustomerStatusVO.setCustomerStatusId(Integer.parseInt(frm.getCmbCustomerStatus()));
        else 
            result=false;
        objProspectiveCustomerVO.setCustomerStatus(objCustomerStatusVO);
        objProspectiveCustomerVO.setPocFirstName(frm.getPntofCntFirstname());
        objProspectiveCustomerVO.setPocLastName(frm.getPntofCntLastname());
        objProspectiveCustomerVO.setTitle(frm.getTxtCustomerTitle());
        objProspectiveCustomerVO.setPhone(frm.getTxtPhone());
        objProspectiveCustomerVO.setFax(frm.getTxtFax());
        objProspectiveCustomerVO.setMobile(frm.getTxtMobile());
        objProspectiveCustomerVO.setEmail(frm.getTxtEmail());
        
        if(!frm.getTxtzipcode().trim().equalsIgnoreCase(""))
        {
            objProspectiveCustomerVO.setZipCode(frm.getTxtzipcode());
        }
        else
            result=false;
        UsersVO objUsersVO = new UsersVO();
        if(!frm.getCmbSalesRep().trim().equalsIgnoreCase("0"))
        {
            objUsersVO.setUserId(frm.getCmbSalesRep().trim());
            objProspectiveCustomerVO.setSalesRep(objUsersVO);
        }else
            result=false;
        CDRStatusVO cdrstatusVO = new CDRStatusVO();
        if(!frm.getCmbCDRState().trim().equalsIgnoreCase("0"))
        {
            cdrstatusVO.setCdrStateId(Integer.parseInt(frm.getCmbCDRState().trim()));
        }else{
            result=false;
        }
        objProspectiveCustomerVO.setCdrStatus(cdrstatusVO);
        objProspectiveCustomerVO.setValid(true);
        return result==true ?objProspectiveCustomerVO:null;
    }
}
