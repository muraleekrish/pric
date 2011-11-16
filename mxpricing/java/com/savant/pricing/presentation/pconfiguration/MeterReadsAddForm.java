/*
 * Created on Jul 24, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pconfiguration;

import org.apache.struts.action.ActionForm;

/**
 * @author sramasamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MeterReadsAddForm extends ActionForm
{
    private String tdsp              = "1";
    private String meterReadCycle    = "";
    private String meterReadCycleTxt = "";
    private String year              = "";  
    private String[] mnths  =   null;
    private String formActions       = "";
    
    public MeterReadsAddForm() 
    {
    } 
    /**
     * @return Returns the formActions.
     */
    public String getFormActions() {
        return formActions;
    }
    /**
     * @param formActions The formActions to set.
     */
    public void setFormActions(String formActions) {
        this.formActions = formActions;
    }
    /**
     * @return Returns the meterReadCycle.
     */
    public String getMeterReadCycle() {
        return meterReadCycle;
    }
    /**
     * @param meterReadCycle The meterReadCycle to set.
     */
    public void setMeterReadCycle(String meterReadCycle) {
        this.meterReadCycle = meterReadCycle;
    }
    
    
    /**
     * @return Returns the meterReadCycleTxt.
     */
    public String getMeterReadCycleTxt() {
        return meterReadCycleTxt;
    }
    /**
     * @param meterReadCycleTxt The meterReadCycleTxt to set.
     */
    public void setMeterReadCycleTxt(String meterReadCycleTxt) {
        this.meterReadCycleTxt = meterReadCycleTxt;
    }
    /**
     * @return Returns the year.
     */
    public String getYear() {
        return year;
    }
    /**
     * 
     * @param year The year to set.
     */
    public void setYear(String year) {
        this.year = year;
    }
    /**
     * @return Returns the tdsp.
     */
    public String getTdsp() {
        return tdsp;
    }
    /**
     * @param tdsp The tdsp to set.
     */
    public void setTdsp(String tdsp) {
        this.tdsp = tdsp;
    }
    
    /**
     * @return Returns the apr.
     */
   
    /**
     * @return Returns the mnths.
     */
    public String[] getMnths() {
        return mnths;
    }
    /**
     * @param mnths The mnths to set.
     */
    public void setMnths(String[] mnths) {
        this.mnths = mnths;
    }
}
