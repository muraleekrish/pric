/*
 * Created on Feb 9, 2007
 *
 */
package com.savant.pricing.presentation.tdp;

import org.apache.struts.action.ActionForm;

/**
 * @author Karthikeyan Chellamuthu
 *
 */
public class DailyProfileForm extends ActionForm
{
    private String formAction = "";
    private String slcProfile = "Usage Details";
    private String txtESIID = "";
    private String txtHourlyfrmDate = "";
    private String txtHourlytoDate = "";
    private String txtDailyfrmDate = "";
    private String txtDailytoDate = "";
    private String slcWeeklyProfMonth ="All"; 
    private String txtloadtdp = "";
    
    

    public String getTxtloadtdp()
    {
        return txtloadtdp;
    }
    public void setTxtloadtdp(String txtloadtdp)
    {
        this.txtloadtdp = txtloadtdp;
    }
    /**
     * @return Returns the formAction.
     */
    public String getFormAction()
    {
        return formAction;
    }
    /**
     * @param formAction The formAction to set.
     */
    public void setFormAction(String formAction)
    {
        this.formAction = formAction;
    }
    /**
     * @return Returns the slcProfile.
     */
    public String getSlcProfile()
    {
        return slcProfile;
    }
    /**
     * @param slcProfile The slcProfile to set.
     */
    public void setSlcProfile(String slcProfile)
    {
        this.slcProfile = slcProfile;
    }
    /**
     * @return Returns the slcWeeklyProfMonth.
     */
    public String getSlcWeeklyProfMonth()
    {
        return slcWeeklyProfMonth;
    }
    /**
     * @param slcWeeklyProfMonth The slcWeeklyProfMonth to set.
     */
    public void setSlcWeeklyProfMonth(String slcWeeklyProfMonth)
    {
        this.slcWeeklyProfMonth = slcWeeklyProfMonth;
    }
    /**
     * @return Returns the txtDailyfrmDate.
     */
    public String getTxtDailyfrmDate()
    {
        return txtDailyfrmDate;
    }
    /**
     * @param txtDailyfrmDate The txtDailyfrmDate to set.
     */
    public void setTxtDailyfrmDate(String txtDailyfrmDate)
    {
        this.txtDailyfrmDate = txtDailyfrmDate;
    }
    /**
     * @return Returns the txtDailytoDate.
     */
    public String getTxtDailytoDate()
    {
        return txtDailytoDate;
    }
    /**
     * @param txtDailytoDate The txtDailytoDate to set.
     */
    public void setTxtDailytoDate(String txtDailytoDate)
    {
        this.txtDailytoDate = txtDailytoDate;
    }
    /**
     * @return Returns the txtESIID.
     */
    public String getTxtESIID()
    {
        return txtESIID;
    }
    /**
     * @param txtESIID The txtESIID to set.
     */
    public void setTxtESIID(String txtESIID)
    {
        this.txtESIID = txtESIID;
    }
    /**
     * @return Returns the txtHourlyfrmDate.
     */
    public String getTxtHourlyfrmDate()
    {
        return txtHourlyfrmDate;
    }
    /**
     * @param txtHourlyfrmDate The txtHourlyfrmDate to set.
     */
    public void setTxtHourlyfrmDate(String txtHourlyfrmDate)
    {
        this.txtHourlyfrmDate = txtHourlyfrmDate;
    }
    /**
     * @return Returns the txtHourlytoDate.
     */
    public String getTxtHourlytoDate()
    {
        return txtHourlytoDate;
    }
    /**
     * @param txtHourlytoDate The txtHourlytoDate to set.
     */
    public void setTxtHourlytoDate(String txtHourlytoDate)
    {
        this.txtHourlytoDate = txtHourlytoDate;
    }
    
   
}
