/*
 * Created on Mar 23, 2007
 * 
 * Class Name PcDealLeversEditForm.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pconfig.deallevers;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PcDealLeversEditForm extends ActionForm{

    private String formAction="list";
    private String value = "";
    private int cmboUnit = 0;
    private String dealLever = "";
    private int dealLeverId = 0;
    
    
    public String getDealLever() {
        return dealLever;
    }
    public int getDealLeverId() {
        return dealLeverId;
    }
    public String getFormAction() {
        return formAction;
    }
    
    public void setDealLever(String dealLever) {
        this.dealLever = dealLever;
    }
    public void setDealLeverId(int dealLeverId) {
        this.dealLeverId = dealLeverId;
    }
    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public int getCmboUnit() {
        return cmboUnit;
    }
    public void setCmboUnit(int cmboUnit) {
        this.cmboUnit = cmboUnit;
    }
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {        
        ActionErrors actionErrors = new ActionErrors();        
        if(formAction.equalsIgnoreCase("Modify"))
        {         
            if(value == null || value.trim().length()<1 || value.trim().equals(""))
            value = "0.0";
        }
       return actionErrors;               
    }
}


/*
*$Log: PcDealLeversEditForm.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:30  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.4  2007/05/07 10:19:47  jnadesan
*deallever edit changed
*
*Revision 1.3  2007/04/30 09:57:25  spandiyarajan
*added error class and message class also fixed the bug in value
*
*Revision 1.2  2007/03/26 04:57:51  jnadesan
*deallevers edit added
*
*Revision 1.1  2007/03/23 11:20:39  jnadesan
*System DealLevers added
*
*
*/