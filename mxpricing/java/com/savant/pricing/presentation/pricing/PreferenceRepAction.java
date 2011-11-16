/*
 * Created on Mar 7, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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

import com.savant.pricing.calculation.valueobjects.CustomerPreferenceProductsVO;
import com.savant.pricing.calculation.valueobjects.CustomerPreferencesTermsVO;
import com.savant.pricing.calculation.valueobjects.CustomerPreferencesVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.SortString;
import com.savant.pricing.dao.CustEnergyComponentsDAO;
import com.savant.pricing.dao.EnergyComponentsDAO;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.valueobjects.CDRStatusVO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PreferenceRepAction extends Action{

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
    {
        String action="failure";
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
      //  CustEnergyComponentsDAO objCustEnergyComponentsDAO = new CustEnergyComponentsDAO();
        String custName = "";
       
        if(form instanceof PreferenceRepForm)
        {
            PreferenceRepForm frm = (PreferenceRepForm)form;        
            if(frm.getFormActions().equalsIgnoreCase("update"))
            {
                frm.setFormActions("edit");
            }
            boolean result =false;
            boolean engcompResult = false;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            if(frm.getFormActions().equalsIgnoreCase("add"))
            {
                CustomerPreferencesVO customerPreferencesVO = new CustomerPreferencesVO();
                customerPreferencesVO.setAutoRun(frm.isAutoRun());
                if(BuildConfig.DMODE)
                    System.out.println("Product ids :"+frm.getProductIds());
                try{
                    customerPreferencesVO.setContractStartDate(sdf.parse("1-"+frm.getStMonth()+"-"+frm.getStYear()));
                    customerPreferencesVO.setContractEndDate(sdf.parse(sdf.format(new Date(customerPreferencesVO.getContractStartDate().getYear(),customerPreferencesVO.getContractStartDate().getMonth()+60,customerPreferencesVO.getContractStartDate().getDate()))));
                    ProspectiveCustomerVO customerVO = new ProspectiveCustomerVO();
                    customerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(request.getSession().getAttribute("customerId")+""));
                    custName = customerVO.getCustomerName();
                    customerPreferencesVO.setProspectiveCustomer(customerVO);
                    customerPreferencesVO.setAutoRun(frm.isAutoRun());
                    customerPreferencesVO.setUnitary(frm.isUnitary());
                    String ids[]=frm.getProductIds();
                    String productIds ="";
                    for (int i = 0; i < ids.length; i++) 
                    {
                        if(productIds.length()<=0)
                            productIds = ids[i];
                        else
                            productIds += ","+ids[i];
                    }
                    result = objProspectiveCustomerDAO.addProspectiveCustomerPreferences(customerPreferencesVO,frm.getTerms(),productIds);
                    customerPreferencesVO.setAutoRun(frm.isAutoRun());
                    CDRStatusVO cdrstatusVO = new CDRStatusVO();
                    cdrstatusVO.setCdrStateId(3);
                    customerVO.setCdrStatus(cdrstatusVO);
                    customerVO.setContractStartDate(sdf.parse("1-"+frm.getStMonth()+"-"+frm.getStYear()));
                    objProspectiveCustomerDAO.updateProspectiveCustomer(customerVO);
                   /* System.out.println(" customer ID :" + Integer.parseInt(request.getSession().getAttribute("customerId")+""));
                    engcompResult = objCustEnergyComponentsDAO.addCustEngyComp(Integer.parseInt(request.getSession().getAttribute("customerId")+""));
                    System.out.println("result : " + engcompResult);*/
                    
                    
                }catch (Exception e) {
                    e.printStackTrace();
                } 
                if(result)
                {
                    action = frm.getPageUser(); 
                    ActionMessages actionMessages = new ActionMessages();
                    actionMessages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.added","Prospective Customer Preferences",custName.toUpperCase()));
                    saveMessages(request,actionMessages);
                    request.setAttribute("message","message");
                    request.getSession().removeAttribute("customerId");
                }else{
                    ActionErrors actionErrors = new ActionErrors();
                    actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("Prospective.customer.add.madatory"));
                    request.setAttribute("message","error");
                }
            }
            if(frm.getFormActions().trim().equalsIgnoreCase("edit")||frm.getFormActions().equalsIgnoreCase(""))
            {
                SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
                SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
                
               try
                {
                    List objCustPreferenceVO =   objProspectiveCustomerDAO.getProspectiveCustomerPreferenceTerms(Integer.parseInt(request.getSession().getAttribute("customerId")+""));
                    List lstPreferenceProducts =   objProspectiveCustomerDAO.getProspectiveCustomerPreferenceProducts(Integer.parseInt(request.getSession().getAttribute("customerId")+""));
                    CustomerPreferencesVO customerPreferencesVO = objProspectiveCustomerDAO.getProspectiveCustomerPreferences(Integer.parseInt(request.getSession().getAttribute("customerId")+""));
                    String term="";
                    String[] productId =  new String[lstPreferenceProducts.size()];
                    int j=0;
                    for (Iterator iter = lstPreferenceProducts.iterator(); iter.hasNext();) 
                    {
                        CustomerPreferenceProductsVO objProduct = (CustomerPreferenceProductsVO ) iter.next();
                        productId[j] = String.valueOf(objProduct.getProduct().getProductIdentifier());
                        j++;
                    }
                    frm.setProductIds(productId);
                    Iterator it = objCustPreferenceVO.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        CustomerPreferencesTermsVO objCustomerPreferencesTermsVO = (CustomerPreferencesTermsVO) it.next();
                        
                        if(i==0)
                        {
                            term=objCustomerPreferencesTermsVO.getTerm()+"";
                            i++;
                        }
                        else{
                            term +=", "+objCustomerPreferencesTermsVO.getTerm();
                        }
                    }        
                    SortString objSortString = new SortString();
                    term = objSortString.sortString(term);
                    frm.setTerms(term);
                    if(customerPreferencesVO!=null)
                        frm.setUnitary(customerPreferencesVO.isUnitary());
                    else
                        frm.setUnitary(false);
                    if(customerPreferencesVO==null)
                    {
                        Date currentDate = new Date();
                        frm.setStMonth(Integer.parseInt(sdfMonth.format(currentDate)));
                        frm.setStYear(Integer.parseInt(sdfYear.format(currentDate)));
                        frm.setAutoRun(false);
                    }
                    else
                    {
                        frm.setStMonth(Integer.parseInt(sdfMonth.format(customerPreferencesVO.getProspectiveCustomer().getContractStartDate())));
                        frm.setStYear(Integer.parseInt(sdfYear.format(customerPreferencesVO.getProspectiveCustomer().getContractStartDate())));
                        frm.setAutoRun(customerPreferencesVO.isAutoRun());
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(frm.getFormActions().equalsIgnoreCase("Modify")||frm.getFormActions().equalsIgnoreCase(""))
            {
                CustomerPreferencesVO customerPreferencesVO = new CustomerPreferencesVO();
                customerPreferencesVO.setAutoRun(frm.isAutoRun());
               
                try{
                    customerPreferencesVO.setContractStartDate(sdf.parse("1-"+frm.getStMonth()+"-"+frm.getStYear()));
                    customerPreferencesVO.setContractEndDate(sdf.parse(sdf.format(new Date(customerPreferencesVO.getContractStartDate().getYear(),customerPreferencesVO.getContractStartDate().getMonth()+60,customerPreferencesVO.getContractStartDate().getDate()))));
                    
                    ProspectiveCustomerVO customerVO = new ProspectiveCustomerVO();
                    customerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(request.getSession().getAttribute("customerId")+""));
                    custName = customerVO.getCustomerName();
                    customerVO.setProspectiveCustomerId(Integer.parseInt(request.getSession().getAttribute("customerId")+""));
                    customerPreferencesVO.setProspectiveCustomer(customerVO);
                    customerPreferencesVO.setUnitary(frm.isUnitary());
                    if(BuildConfig.DMODE)
                        System.out.println("frm.isAutoRun() :"+frm.isAutoRun());
                    customerPreferencesVO.setAutoRun(frm.isAutoRun());
                    CDRStatusVO cdrstatusVO = new CDRStatusVO();
                    cdrstatusVO.setCdrStateId(3);
                    customerVO.setCdrStatus(cdrstatusVO);
                    customerVO.setContractStartDate(sdf.parse("1-"+frm.getStMonth()+"-"+frm.getStYear()));
                    objProspectiveCustomerDAO.updateProspectiveCustomer(customerVO);
                    
                    String ids[]=frm.getProductIds();
                    String productIds ="";
                    for (int i = 0; i < ids.length; i++) 
                    {
                        if(productIds.length()<=0)
                            productIds = ids[i];
                        else
                            productIds += ","+ids[i];
                    }
                    if(BuildConfig.DMODE)
                        System.out.println("productIds : "+productIds);
                    result = objProspectiveCustomerDAO.updateProspectiveCustomerPreferences(customerPreferencesVO,frm.getTerms(),productIds);
                }catch (Exception e) {
                    e.printStackTrace();
                    ActionErrors actionErrors = new ActionErrors();
                    actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("Prospective.customer.add.madatory"));
                } 
             
                if(result)
                {
                    action = frm.getPageUser(); 
                    ActionMessages actionMessages = new ActionMessages();
                    actionMessages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.message.modified","Prospective Customer preferences for",custName.toUpperCase()));
                    saveMessages(request,actionMessages);
                    request.setAttribute("message","message");
                    request.getSession().removeAttribute("customerId");
                }else{
                    ActionErrors actionErrors = new ActionErrors();
                    actionErrors.add(ActionErrors.GLOBAL_ERROR, new ActionError("Prospective.customer.add.madatory"));
                    saveErrors(request,actionErrors);
                    request.setAttribute("message","error");
                }
            }
         }
        return mapping.findForward(action);
    }
}
