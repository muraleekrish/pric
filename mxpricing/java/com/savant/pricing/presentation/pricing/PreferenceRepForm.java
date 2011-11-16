/*
 * Created on Mar 7, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.savant.pricing.common.BuildConfig;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PreferenceRepForm extends ActionForm{
 
    Date objdate = new Date();
    private String formActions="";
    private String prsCustId="";
    private int stMonth ;
    private int stYear = Calendar.YEAR; 
    private String endMonth=null;
    private String terms = "12, 24, 36";
    private boolean autoRun = false;
    private String product = "";
    private String pageUser = "";
    private boolean unitary = false;
    String[] productIds = {"1"};
    
    public PreferenceRepForm()
    {
        Calendar objCalendar = Calendar.getInstance();     
        objCalendar.add(2,2);
        stMonth = objCalendar.getTime().getMonth();
    } 
   
    public int getStYear() {
        return stYear;
    }
    public void setStYear(int stYear) {
        this.stYear = stYear;
    }
    public int getStMonth() {
        return stMonth;
    }
    public void setStMonth(int stMonth) {
        this.stMonth = stMonth;
    }
    public String[] getProductIds() {
        return productIds;
    }
    public void setProductIds(String[] productIds) {
        this.productIds = productIds;
    }
    public String getPageUser() {
        return pageUser;
    }
    public Date getObjdate() {
        return objdate;
    }
   
    public boolean isUnitary() {
        return unitary;
    }
    public void setObjdate(Date objdate) {
        this.objdate = objdate;
    }
    
    public void setUnitary(boolean unitary) {
        this.unitary = unitary;
    }
    public void setPageUser(String pageUser) {
        this.pageUser = pageUser;
    }
    public boolean isAutoRun() {
        return autoRun;
    }
    public void setAutoRun(boolean autoRun) {
        this.autoRun = autoRun;
    }
    public String getEndMonth() {
        return endMonth;
    }
    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }
    public String getFormActions() {
        return formActions;
    }
    public void setFormActions(String formActions) {
        this.formActions = formActions;
    }
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public String getPrsCustId() {
        return prsCustId;
    }
    public void setPrsCustId(String prsCustId) {
        this.prsCustId = prsCustId;
    }
    public String getTerms() {
        return terms;
    }
    public void setTerms(String terms) {
        this.terms = terms;
    }
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors actionErrors = new ActionErrors();        
        
        if(formActions.equalsIgnoreCase("Add") || formActions.equalsIgnoreCase("Modify"))
        {
	        if(terms==null || terms.trim().length()<1 || terms.trim().equals(""))
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Terms"));
	        }
	        if(productIds==null || productIds.length <= 0 || productIds.equals(null))
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Products"));
	        }
	        Calendar objCalendar = Calendar.getInstance();
	        
	        if(stYear <= objCalendar.get(Calendar.YEAR))
	            if(stMonth-1 <= objCalendar.get(Calendar.MONTH))
	                actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.invalidmonth"));
	            boolean termValidation = true;
	            
	            System.out.println("*********** terms :"+terms);
	        if(terms.length()>1)
	        {
	            StringTokenizer st = new StringTokenizer(terms,",");
	            while(st.hasMoreTokens())
	            {
	                if(st.nextToken().trim().length()>2)
	                {
	                    actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.inValid","Terms"));
	                    termValidation = false;
	                    break;
	                }
	            }
	            if(termValidation)
	            {
	            String[] termsAry = null;
                LinkedHashMap hmTerms = new LinkedHashMap();
                String strTerms = terms;
                String filteredTerm = "";
                if(BuildConfig.DMODE)
                    System.out.println("frm.getTerms() :"+terms);
                termsAry = strTerms.split(",");
                for (int i = 0; i < termsAry.length; i++) 
                { 
                    if(!termsAry[i].trim().equalsIgnoreCase(""))
                        hmTerms.put(new Integer(termsAry[i].trim()),new Integer(termsAry[i].trim()));
                }
                if(BuildConfig.DMODE)
                    System.out.println("hmTerms :"+hmTerms);
                Set termsSet = hmTerms.keySet();
                Iterator termIte = termsSet.iterator();
                while(termIte.hasNext())
                {
                    filteredTerm += ((Integer)termIte.next()).intValue()+(termIte.hasNext()?", ":"");
                }
                if(BuildConfig.DMODE)
                    System.out.println("strterms :"+filteredTerm);
                this.terms = filteredTerm;
	            }
	        }
        }
        return actionErrors;
    }
}
