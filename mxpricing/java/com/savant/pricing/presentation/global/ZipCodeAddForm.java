/*
 * Created on Aug 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.global;

import org.apache.struts.action.ActionForm;

/**
 * @author sramasamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ZipCodeAddForm extends ActionForm
{
    private String zipCode         = "";
    private String congestionName  = "";
    private String weatherZoneName = "";
    private String formAction      = "";
    
    /**
     * @return Returns the congestionName.
     */
    public String getCongestionName() {
        return congestionName;
    }
    /**
     * @param congestionName The congestionName to set.
     */
    public void setCongestionName(String congestionName) {
        this.congestionName = congestionName;
    }
    /**
     * @return Returns the weatherZoneName.
     */
    public String getWeatherZoneName() {
        return weatherZoneName;
    }
    /**
     * @param weatherZoneName The weatherZoneName to set.
     */
    public void setWeatherZoneName(String weatherZoneName) {
        this.weatherZoneName = weatherZoneName;
    }
    /**
     * @return Returns the zipCode.
     */
    public String getZipCode() {
        return zipCode;
    }
    /**
     * @param zipCode The zipCode to set.
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    /**
     * @return Returns the formAction.
     */
    public String getFormAction() {
        return formAction;
    }
    /**
     * @param formAction The formAction to set.
     */
    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }
}
