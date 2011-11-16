/*
 * Created on Jan 24, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.text.SimpleDateFormat;
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

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.dao.CustEnergyComponentsDAO;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.securityadmin.valueobject.UsersVO;
import com.savant.pricing.valueobjects.CDRStatusVO;
import com.savant.pricing.valueobjects.CustomerCommentsVO;
import com.savant.pricing.valueobjects.CustomerStatusVO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author srajappan
 *o
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProspectiveCustomerListAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String action="failure";
        ActionErrors actionErrors = new ActionErrors();
        ActionMessages actionMessages = new ActionMessages();
        boolean result=false;
        boolean engcompResult = false;
        CustEnergyComponentsDAO objCustEnergyComponentsDAO = new CustEnergyComponentsDAO();
        if(form instanceof ProspectiveCustomerAddForm)
        {
            ProspectiveCustomerAddForm frm = (ProspectiveCustomerAddForm)form;
            if(request.getParameter("formAction")!= null && request.getParameter("formActoin").equalsIgnoreCase("edit"))
            {
                frm.setFormActions("edit");
            }
            if(frm.getFormActions().equalsIgnoreCase("cancel"))
            {
              
                action = frm.getPageUser();
            }
            else if(frm.getFormActions().equalsIgnoreCase("Add")||frm.getFormActions().trim().equalsIgnoreCase("preference"))
            {
                try
                {
                    if(frm.getPageUser().equalsIgnoreCase("Rep"))
                        frm.setCmbCDRState("3");
                    else
                    {
                        frm.setCmbCDRState("1");
                    }
                    if(frm.getPageUser().equalsIgnoreCase("rep"))
                        frm.setCmbSalesRep((String)request.getSession().getAttribute("userName"));
                    frm.setCommentsModifiedBy((String)request.getSession().getAttribute("userName"));
                    ProspectiveCustomerVO objProspectiveCustomerVO = this.addProspectiveCustomer(frm);
                    ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
                    int custId = 0;
                    if(objProspectiveCustomerVO!=null)
                    { 
                        boolean chkCMSidAvail = false;
                        boolean chkZipcodeValid = false;
                        
                        chkCMSidAvail = objProspectiveCustomerDAO.checkProspectiveCustomer(objProspectiveCustomerVO.getCustomerId());
                        chkZipcodeValid = objProspectiveCustomerDAO.checkPostalCodeInCMS(Double.parseDouble(objProspectiveCustomerVO.getZipCode()));
                        if(!chkCMSidAvail && chkZipcodeValid)
                        {
                            custId = objProspectiveCustomerDAO.addProspectiveCustomer(objProspectiveCustomerVO);
                            if(custId>0)
                            {
                                request.getSession().setAttribute("customerId",custId+"");
                                result=true;
                            }
                        }
                        else if(!chkZipcodeValid)
                        {
                            actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.inValid","ZipCode"));
                        }
                        else
                        {
                            actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("Prospective.customer.cmsid.alreadyexist"));
                        }
                    }
                    
                    if(result)
                    {
                        System.out.println(" customer ID :" + custId);
                        engcompResult = objCustEnergyComponentsDAO.addCustEngyComp(custId);
                        System.out.println("result : " + engcompResult);
                        if(frm.getFormActions().equalsIgnoreCase("preference"))
                        {
                            action="preference";
                            actionMessages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.added","Prospective Customer ",frm.getTxtCustomerName().toUpperCase()));
                            saveMessages(request,actionMessages);
                        }
                        else 
                        {
                            action="success";
                        }
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    if(e.toString().indexOf("org.hibernate.exception.ConstraintViolationException: could not update") != -1)
                    {
                        actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Prospective.customer.zipcode.invalid"));
                    }
                    else if(e.toString().indexOf("org.hibernate.HibernateException: org.hibernate.exception.ConstraintViolationException: could not insert:")!= -1)
                    {
                        actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Prospective.customer.customerId/customername.numeric"));
                    }
                    else if(e.toString().indexOf("Violation of UNIQUE KEY constraint")!=-1)
                    {
                        actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Prospective.customer.exists"));
                    }
                }
            }
            else if(frm.getFormActions().trim().equalsIgnoreCase("edit") ||(frm.getFormActions().trim().equalsIgnoreCase("Manageredit")))
            {
                try
                { 
                    int prsCustId =0;
                    if(request.getParameter("prsCustId")!=null)
                    {
                        prsCustId = Integer.parseInt(request.getParameter("prsCustId"));
                        if(BuildConfig.DMODE)
                            System.out.println("prsCustId edit:"+prsCustId);
                        frm.setCmbSalesRep((String)request.getSession().getAttribute("userName"));
                        frm.setCmbCDRState("3");
                        result =  this.editProspectiveCustomer(frm,prsCustId);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Prospective.customer.customerId.numeric"));
                }
            }
            else if(frm.getFormActions().trim().equalsIgnoreCase("update"))
            {
                try
                {
                    ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
                    frm.setTxtcomments(request.getParameter("txtcomments"));
                    if(frm.getPageUser().equalsIgnoreCase("Rep"))
                        frm.setCmbCDRState("3");
                    else
                        frm.setCmbCDRState("1");
                    boolean chkZipcodeValid = false;
                    chkZipcodeValid = objProspectiveCustomerDAO.checkPostalCodeInCMS(Integer.parseInt(frm.getTxtzipcode()));
                    if(!chkZipcodeValid)
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("common.error.inValid","ZipCode"));
                    }
                    else
                    {
                        frm.setCommentsModifiedBy((String)request.getSession().getAttribute("userName"));
	                    ProspectiveCustomerVO objProspectiveCustomerVO = this.addProspectiveCustomer(frm);
	                    if(objProspectiveCustomerVO!=null)
	                    {
	                        objProspectiveCustomerVO.setModifiedDate(new Date());
	                        objProspectiveCustomerVO.setModifiedBy((String)request.getSession().getAttribute("userName"));
	                        objProspectiveCustomerVO.setProspectiveCustomerId(Integer.parseInt(frm.getProspectiveCustId().trim()));
	                        request.getSession().setAttribute("customerId",frm.getProspectiveCustId().trim());  
	                        result=objProspectiveCustomerDAO.updateProspectiveCustomer(objProspectiveCustomerVO);
	                    }
	                    else
	                    {
	                        result=false;
	                    }
	                    if(result)
	                    {
	                        action="preference";
	                        actionMessages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.modified","Prospective Customer ",frm.getTxtCustomerName().toUpperCase()));
	                        saveMessages(request,actionMessages);
	                    }
	                    else
	                    {
	                        actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("Prospective.customer.add.madatory"));  
	                    }
                     }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    if(e.toString().indexOf("org.hibernate.exception.ConstraintViolationException: could not update") != -1)
                    {
                        actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Prospective.customer.zipcode.invalid"));
                    }
                }
            }
            else if(frm.getFormActions().equals("Import"))
            {
                ProspectiveCustomerVO objProspectiveCustomerVO = null;
                ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
                try
                {
                    if(!frm.getTxtCustomerCMSId().trim().equalsIgnoreCase(""))
                        objProspectiveCustomerVO = objProspectiveCustomerDAO.getCDRDetailsFromCMS(Integer.parseInt(frm.getTxtCustomerCMSId().trim()));
                    if(objProspectiveCustomerVO!=null)
                    {
                        Date objDate = objProspectiveCustomerVO.getContractStartDate();
                        if(objDate==null)
                            objDate=new Date();
                        frm.setTxtCustomerName(objProspectiveCustomerVO.getCustomerName()==null?"":objProspectiveCustomerVO.getCustomerName());
                        frm.setPntofCntFirstname(objProspectiveCustomerVO.getPocFirstName()==null?"":objProspectiveCustomerVO.getPocFirstName());
                        frm.setPntofCntLastname(objProspectiveCustomerVO.getPocLastName()==null?"":objProspectiveCustomerVO.getPocLastName());
                        frm.setTxtCustomerTitle(objProspectiveCustomerVO.getTitle()==null?"":objProspectiveCustomerVO.getTitle());
                        frm.setTxtPhone(objProspectiveCustomerVO.getPhone()==null?"":objProspectiveCustomerVO.getPhone());
                        frm.setTxtFax(objProspectiveCustomerVO.getFax()==null?"":objProspectiveCustomerVO.getFax());
                        frm.setTxtMobile(objProspectiveCustomerVO.getMobile()==null?"":objProspectiveCustomerVO.getMobile());
                        frm.setTxtEmail(objProspectiveCustomerVO.getEmail()==null?"":objProspectiveCustomerVO.getEmail());
                        frm.setCmbCustomerState(objProspectiveCustomerVO.getState()==null?"":objProspectiveCustomerVO.getState());
                        frm.setTxtCustomerAddress(objProspectiveCustomerVO.getAddress()==null?"":objProspectiveCustomerVO.getAddress());
                        frm.setTxtCustomerCity(objProspectiveCustomerVO.getCity()==null?"":objProspectiveCustomerVO.getCity());
                        frm.setTxtzipcode(objProspectiveCustomerVO.getZipCode()==null||objProspectiveCustomerVO.getZipCode()==null?"":objProspectiveCustomerVO.getZipCode());
                        frm.setTxtCurentProvider(objProspectiveCustomerVO.getCurrentProvider()==null?"":objProspectiveCustomerVO.getCurrentProvider());
                        frm.setTxtBiztype(objProspectiveCustomerVO.getBusinessType()==null?"":objProspectiveCustomerVO.getBusinessType());
                        frm.setStMonth(objDate.getMonth());
                        frm.setStYear(objDate.getYear());
                        frm.setTxtcensus(objProspectiveCustomerVO.getCensusTract()==null?"":objProspectiveCustomerVO.getCensusTract());
                        frm.setTxtusage(""+objProspectiveCustomerVO.getEstimatedUsage());
                        frm.setTxtcommission(""+objProspectiveCustomerVO.getCommission());
                        frm.setTxtcommissionIncome(""+objProspectiveCustomerVO.getCommissionIncome());
                        frm.setTxtcompetitor(objProspectiveCustomerVO.getCompetitor());
                        frm.setTxtcompetitorPrice(""+objProspectiveCustomerVO.getCompetitorPrice());
                        if(objProspectiveCustomerVO.isOutOfCycleSwitch())
                            frm.setCmbcycle("1");
                        else
                            frm.setCmbcycle("0");
                        if(objProspectiveCustomerVO.isTaxExempt())
                            frm.setCmbtaxExempt("1");
                        else
                            frm.setCmbtaxExempt("0");
                        frm.setTxtCustomerDBA(objProspectiveCustomerVO.getCustomerDBA()==null?"":objProspectiveCustomerVO.getCustomerDBA());
                        frm.setCmbCMSLocationId(""+objProspectiveCustomerVO.getLocationTypeId());
                        frm.setCmbCMSAddressTypeId(""+objProspectiveCustomerVO.getAddressTypeId());
                    }
                    else
                    {
                        frm.setTxtCustomerName("");
                        frm.setPntofCntFirstname("");
                        frm.setPntofCntLastname("");
                        frm.setTxtCustomerTitle("");
                        frm.setTxtPhone("");
                        frm.setTxtFax("");
                        frm.setTxtMobile("");
                        frm.setTxtEmail("");
                        frm.setCmbCustomerState("");
                        frm.setTxtCustomerAddress("");
                        frm.setTxtCustomerCity("");
                        frm.setTxtzipcode("");
                        frm.setTxtCurentProvider("");
                        frm.setTxtBiztype("");
                        Date currentDate = new Date();
                        frm.setStMonth(currentDate.getMonth());
                        frm.setStYear(currentDate.getYear());
                        frm.setTxtcensus("");
                        frm.setTxtusage("");
                        frm.setTxtcommission("");
                        frm.setTxtcommissionIncome("");
                        frm.setTxtcompetitor("");
                        frm.setTxtcompetitorPrice("");
                        frm.setCmbcycle("0");
                        frm.setCmbtaxExempt("0");
                        frm.setTxtCustomerDBA("");
                        frm.setTxtcomments("");
                        if(!frm.getTxtCustomerCMSId().trim().equalsIgnoreCase(""))
                        {
                            actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.customer.import.CMS.failure"));
                            saveErrors(request,actionErrors);
                        }
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else if(form instanceof ProspectiveCustomerListForm)
        {
            ProspectiveCustomerListForm frm =(ProspectiveCustomerListForm)form;
            if(frm.getFormActions().equalsIgnoreCase("search"))
            {
                action="failure";
            }
            else
                action="success";
        }
        else
        {
            action = "view";
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
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        ProspectiveCustomerVO  objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(prsCustId);
        frm.setProspectiveCustId(prsCustId+"");
        frm.setTxtCustomerCMSId(objProspectiveCustomerVO.getCustomerId()==null?"":objProspectiveCustomerVO.getCustomerId()+"");
        frm.setTxtCustomerName(objProspectiveCustomerVO.getCustomerName());
        frm.setTxtCustomerAddress( objProspectiveCustomerVO.getAddress());
        frm.setTxtBiztype(objProspectiveCustomerVO.getBusinessType());
        frm.setTxtCustomerCity(objProspectiveCustomerVO.getCity());
        frm.setTxtCurentProvider(objProspectiveCustomerVO.getCurrentProvider());
        frm.setCmbCustomerState(objProspectiveCustomerVO.getState());
        CustomerStatusVO  objCustomerStatusVO =objProspectiveCustomerVO.getCustomerStatus();
        frm.setCmbCustomerStatus(objCustomerStatusVO.getCustomerStatusId()+"");
        
        frm.setTxtusage(String.valueOf(objProspectiveCustomerVO.getEstimatedUsage()).trim());
        frm.setTxtCustomerTitle( objProspectiveCustomerVO.getTitle());
        frm.setTxtPhone(objProspectiveCustomerVO.getPhone());
        frm.setTxtFax(objProspectiveCustomerVO.getFax());
        frm.setTxtMobile(objProspectiveCustomerVO.getMobile());
        frm.setTxtEmail(objProspectiveCustomerVO.getEmail());
        frm.setPntofCntFirstname(objProspectiveCustomerVO.getPocFirstName());
        frm.setPntofCntLastname(objProspectiveCustomerVO.getPocLastName());
        frm.setTxtzipcode(objProspectiveCustomerVO.getZipCode()==null?"":objProspectiveCustomerVO.getZipCode());
        if(objProspectiveCustomerVO.getContractStartDate()==null)
        {
            Date currentDate = new Date();
            frm.setStMonth(Integer.parseInt(sdfMonth.format(currentDate)));
            frm.setStYear(Integer.parseInt(sdfYear.format(currentDate)));
        }
        else
        {
            frm.setStMonth(Integer.parseInt(sdfMonth.format(objProspectiveCustomerVO.getContractStartDate())));
            frm.setStYear(Integer.parseInt(sdfYear.format(objProspectiveCustomerVO.getContractStartDate())));
        }
        frm.setTxtCustomerDBA(objProspectiveCustomerVO.getCustomerDBA());
        frm.setTxtcensus(objProspectiveCustomerVO.getCensusTract());
        frm.setTxtcommission(""+objProspectiveCustomerVO.getCommission());
        frm.setTxtcommissionIncome(""+objProspectiveCustomerVO.getCommissionIncome());
        frm.setTxtcompetitor(objProspectiveCustomerVO.getCompetitor());
        frm.setTxtcompetitorPrice(""+objProspectiveCustomerVO.getCompetitorPrice());
        frm.setCmbcycle(objProspectiveCustomerVO.isOutOfCycleSwitch()?"1":"0");
        frm.setCmbtaxExempt(objProspectiveCustomerVO.isTaxExempt()?"1":"0");
        frm.setCmbCMSLocationId(objProspectiveCustomerVO.getLocationTypeId()+"");
        frm.setIsMMCust((objProspectiveCustomerVO.isMmCust()==true?1:0)+"");
        frm.setCmbCMSAddressTypeId(objProspectiveCustomerVO.getAddressTypeId()+"");
        frm.setFirstName(objProspectiveCustomerVO.getSalesRep().getUserId());
        UsersVO objUsersVO = objProspectiveCustomerVO.getSalesRep();
        if(objUsersVO!=null)
        {
            frm.setCmbSalesRep(objUsersVO.getUserId());
        }
        CDRStatusVO statusVO = objProspectiveCustomerVO.getCdrStatus();
        frm.setCmbCDRState(String.valueOf(statusVO.getCdrStateId()));
        //frm.setCreatedDate(String.valueOf());
        //frm.setCreatedBy(String.valueOf());
        return result;
    }
    
    private ProspectiveCustomerVO addProspectiveCustomer(ProspectiveCustomerAddForm frm) throws Exception
    {
        ProspectiveCustomerVO objProspectiveCustomerVO = null;
        CustomerCommentsVO objCustomerCommentsVO = null;
        UsersVO objUsersVO = new UsersVO();
        
        if(frm.getFormActions().equalsIgnoreCase("preference"))
        {
            objProspectiveCustomerVO = new ProspectiveCustomerVO();
            objProspectiveCustomerVO.setCreatedDate(new Date());
            objProspectiveCustomerVO.setCreatedBy(frm.getCmbSalesRep());
            objUsersVO.setUserId(frm.getCmbSalesRep());
            objProspectiveCustomerVO.setSalesRep(objUsersVO);
        }
        else
        {
            ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
            objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(frm.getProspectiveCustId()));
            objProspectiveCustomerVO.setModifiedDate(new Date());
            objProspectiveCustomerVO.setModifiedBy(frm.getCmbSalesRep());
        } 
        if(!frm.getTxtcomments().trim().equalsIgnoreCase(""))
        {
            objCustomerCommentsVO = new CustomerCommentsVO();
            objCustomerCommentsVO.setComments(frm.getTxtcomments());
            objCustomerCommentsVO.setModifiedBy(frm.getCommentsModifiedBy());
            objCustomerCommentsVO.setModifiedDate(new Date());
            objCustomerCommentsVO.setCreatedDate(new Date());
            objCustomerCommentsVO.setCreatedBy(frm.getCommentsModifiedBy());
        }
        if(!frm.getTxtCustomerCMSId().trim().equalsIgnoreCase(""))
        {
            int custId = 0;
            custId = Integer.parseInt(frm.getTxtCustomerCMSId().trim());
            objProspectiveCustomerVO.setCustomerId(new Integer(custId));
        }
        if(frm.getIsMMCust().equalsIgnoreCase("1"))
            objProspectiveCustomerVO.setMmCust(true);
        else if(frm.getIsMMCust().equalsIgnoreCase("0"))
            objProspectiveCustomerVO.setMmCust(false);
        objProspectiveCustomerVO.setBusinessType(frm.getTxtBiztype());
        objProspectiveCustomerVO.setCustomerName(frm.getTxtCustomerName());
        objProspectiveCustomerVO.setCurrentProvider(frm.getTxtCurentProvider());
        objProspectiveCustomerVO.setAddress(frm.getTxtCustomerAddress());
        objProspectiveCustomerVO.setCity(frm.getTxtCustomerCity());
        objProspectiveCustomerVO.setState(frm.getCmbCustomerState());
        objProspectiveCustomerVO.setPocFirstName(frm.getPntofCntFirstname());
        objProspectiveCustomerVO.setPocLastName(frm.getPntofCntLastname());
        CustomerStatusVO objCustomerStatusVO = new CustomerStatusVO();
        objCustomerStatusVO.setCustomerStatusId(Integer.parseInt(frm.getCmbCustomerStatus()));
        objProspectiveCustomerVO.setCustomerStatus(objCustomerStatusVO);
        objProspectiveCustomerVO.setTitle(frm.getTxtCustomerTitle());
        objProspectiveCustomerVO.setPhone(frm.getTxtPhone());
        objProspectiveCustomerVO.setFax(frm.getTxtFax());
        objProspectiveCustomerVO.setMobile(frm.getTxtMobile());
        objProspectiveCustomerVO.setEmail(frm.getTxtEmail());
        objProspectiveCustomerVO.setZipCode(frm.getTxtzipcode());
        if(!frm.getPageUser().equalsIgnoreCase("rep"))
        {
            objUsersVO.setUserId(frm.getCmbSalesRep());
            objProspectiveCustomerVO.setSalesRep(objUsersVO);
        }
        CDRStatusVO cdrstatusVO = new CDRStatusVO();
        if(BuildConfig.DMODE)
            System.out.println("CDRState:"+frm.getCmbCDRState());
        cdrstatusVO.setCdrStateId(3);
        objProspectiveCustomerVO.setCdrStatus(cdrstatusVO);
        objProspectiveCustomerVO.setValid(true);
        SimpleDateFormat sdf =  new SimpleDateFormat("dd-MM-yyyy");
        objProspectiveCustomerVO.setCustomerDBA(frm.getTxtCustomerDBA());
        //objProspectiveCustomerVO.setContractStartDate(sdf.parse("1-"+frm.getStMonth()+"-"+frm.getStYear()));
        
        objProspectiveCustomerVO.setTaxExempt(frm.getCmbtaxExempt().equals("1"));
        objProspectiveCustomerVO.setOutOfCycleSwitch(frm.getCmbcycle().equals("1"));
        objProspectiveCustomerVO.setCensusTract(frm.getTxtcensus());
        objProspectiveCustomerVO.setLocationTypeId(Integer.parseInt(frm.getCmbCMSLocationId()));
        objProspectiveCustomerVO.setAddressTypeId(Integer.parseInt(frm.getCmbCMSAddressTypeId()));
        
        if(frm.getTxtusage().equals(""))
        {	
            frm.setTxtusage("0.0");
        }
        if(frm.getTxtcommission().equals(""))
        {	
            frm.setTxtcommission("0.0");
        }
        if(frm.getTxtcommissionIncome().equals(""))
        {	
            frm.setTxtcommissionIncome("0.0");
        }
        if(frm.getTxtcompetitorPrice().equals(""))
        {	
            frm.setTxtcompetitorPrice("0.0");
        }
        objProspectiveCustomerVO.setEstimatedUsage(Float.parseFloat(frm.getTxtusage()));
        objProspectiveCustomerVO.setCommission(Float.parseFloat(frm.getTxtcommission()));
        objProspectiveCustomerVO.setCommissionIncome(Float.parseFloat(frm.getTxtcommissionIncome()));
        objProspectiveCustomerVO.setComments(objCustomerCommentsVO);
        
        objProspectiveCustomerVO.setCompetitor(frm.getTxtcompetitor());
        objProspectiveCustomerVO.setCompetitorPrice(Float.parseFloat(frm.getTxtcompetitorPrice()));
        return objProspectiveCustomerVO;
    }
}
