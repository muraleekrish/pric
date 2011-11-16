/*
 * Created on Mar 16, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.fulcruminput;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ImportInputEnergyForm extends ActionForm{
    
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    String currentDate = sdf.format(new Date());
    
    
    private String txtdate="";
    private String formActions = "list";
    private String importPrice = "Energy";
    private String congestionZones = "";
    private String weatherzone = "";
    private String profile = "";
    private String esiid = "";
    private FormFile gasBrowse ;
    private FormFile forwardBrowse ;  
    private String txtgasdate=currentDate;
    private String txtforwarddate=currentDate;

    
    
   
    
    public String getTxtforwarddate()
    {
        return txtforwarddate;
    }
    public void setTxtforwarddate(String txtforwarddate)
    {
        this.txtforwarddate = txtforwarddate;
    }
    public String getTxtgasdate()
    {
        return txtgasdate;
    }
    public void setTxtgasdate(String txtgasdate)
    {
        this.txtgasdate = txtgasdate;
    }
    public FormFile getForwardBrowse()
    {
        return forwardBrowse;
    }
    public void setForwardBrowse(FormFile forwardBrowse)
    {
        this.forwardBrowse = forwardBrowse;
    }
    public FormFile getGasBrowse()
    {
        return gasBrowse;
    }
    public void setGasBrowse(FormFile gasBrowse)
    {
        this.gasBrowse = gasBrowse;
    }
    public String getEsiid() {
        return esiid;
    }
    public void setEsiid(String esiid) {
        this.esiid = esiid;
    }
    public ImportInputEnergyForm()
    {
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        txtdate = df.format(new Date());
    }
    public String getProfile() {
        return profile;
    }
    public String getWeatherzone() {
        return weatherzone;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }
    public void setWeatherzone(String weatherzone) {
        this.weatherzone = weatherzone;
    }
    public String getCongestionZones() {
        return congestionZones;
    }
    public void setCongestionZones(String congestionZones) {
        this.congestionZones = congestionZones;
    }
    public String getImportPrice() {
        return importPrice;
    }
    public void setImportPrice(String importPrice) {
        this.importPrice = importPrice;
    }
    public String getFormActions() {
        return formActions;
    }
    
    public void setFormActions(String formActions) {
        this.formActions = formActions;
    }
    
    public String getTxtdate() {
        return txtdate;
    }
    public void setTxtdate(String txtdate) {
        this.txtdate = txtdate;
    }
}
