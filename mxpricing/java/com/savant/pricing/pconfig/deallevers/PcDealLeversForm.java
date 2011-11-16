/*
 * Created on Mar 23, 2007
 * 
 * Class Name PcDealLeversListForm.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pconfig.deallevers;

import org.apache.struts.action.ActionForm;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PcDealLeversForm extends ActionForm{

    private String formAction="list";
    private float value = 0;
    private String unit = "";
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
    public String getUnit() {
        return unit;
    }
    public float getValue() {
        return value;
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
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public void setValue(float value) {
        this.value = value;
    }
}


/*
*$Log: PcDealLeversForm.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:30  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/03/23 11:20:39  jnadesan
*System DealLevers added
*
*
*/