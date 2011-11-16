/*
 * Created on Jan 25, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author srajappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProspectiveCustomerAddForm extends ActionForm
{

    private String txtCustomerCMSId="";
    private String txtCustomerName="";
    private String txtCustomerAddress="";
    private String cmbCustomerStatus="0";
    private String txtCustomerCity="";
    private String pntofCntFirstname="";
    private String txtSalesRep= "";
    private String pntofCntLastname="";
    private String cmbCustomerState="";
    private String txtCustomerTitle="";
    private String txtzipcode="";
    private String txtPhone="";
    private String cmbSalesRep;
    private String txtFax="";
    private String txtCurentProvider="";
    private String txtMobile="";
    private String txtBiztype="";
    private String txtEmail="";
    private String formActions="";
    private String firstName = "";
    private String prospectiveCustId;
    private String cmbCDRState;
    private String cmbtaxExempt = "0";
    private String txtcensus = "";
    private String txtusage = "";
    private String txtcommission = "";
    private String txtcommissionIncome = "";
    private String txtcycleSwitch = "";
    private String txtcomments = "";
    private String txtcommentsGeneral = "";
    private String commentsModifiedBy = "";
    private String txtcompetitor = "";
    private String txtcompetitorPrice = "";
    private String txtCustomerDBA = "";
    private String cmbcycle = "0";
    private String pageUser = "";
    private String cmbCMSLocationId = "";
    private String cmbCMSAddressTypeId = "";
    private String createdDate ="";
    private String isMMCust = "0";
    private String createdBy ="";
    Date objdate = new Date();
    private int stMonth = objdate.getMonth()+2;
    private int stYear = objdate.getYear(); 
    private String records = "0";
    private String typeNewRenewal = "";
    private String aggregator = "";
    private String broker = "";
    private String consultant = "";
    private String noofESIIDs = "";    
    
    public String getCommentsModifiedBy() {
        return commentsModifiedBy;
    }
    public void setCommentsModifiedBy(String commentsModifiedBy) {
        this.commentsModifiedBy = commentsModifiedBy;
    }
    public String getTxtSalesRep() {
        return txtSalesRep;
    }
    public String getIsMMCust()
    {
        return isMMCust;
    }
    public void setIsMMCust(String isMMCust)
    {
        this.isMMCust = isMMCust;
    }
    public void setTxtSalesRep(String txtSalesRep) {
        this.txtSalesRep = txtSalesRep;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return Returns the records.
     */
    public String getRecords()
    {
        return records;
    }
    /**
     * @param records The records to set.
     */
    public void setRecords(String records)
    {
        this.records = records;
    }
    /**
     * @return Returns the createdBy.
     */
    public String getCreatedBy()
    {
        return createdBy;
    }
    /**
     * @param createdBy The createdBy to set.
     */
    
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    /**
     * @return Returns the createdDate.
     */
    public String getCreatedDate()
    {
        return createdDate;
    }
    /**
     * @param createdDate The createdDate to set.
     */
    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }
    SimpleDateFormat df = new SimpleDateFormat("MMM-yyyy");
    
    public String getCmbCMSAddressTypeId() {
        return cmbCMSAddressTypeId;
    }
    public void setCmbCMSAddressTypeId(String cmbCMSAddressTypeId) {
        this.cmbCMSAddressTypeId = cmbCMSAddressTypeId;
    }
    public String getCmbCMSLocationId() {
        return cmbCMSLocationId;
    }
    public void setCmbCMSLocationId(String cmbCMSLocationId) {
        this.cmbCMSLocationId = cmbCMSLocationId;
    }
    public String getPntofCntFirstname()
    {
        return pntofCntFirstname;
    }
    public void setPntofCntFirstname(String pntofCntFirstname)
    {
        this.pntofCntFirstname = pntofCntFirstname;
    }
    public String getPntofCntLastname()
    {
        return pntofCntLastname;
    }
    public SimpleDateFormat getDf() {
        return df;
    }
    public String getPageUser() {
        return pageUser;
    }
    public void setDf(SimpleDateFormat df) {
        this.df = df;
    }
    public void setPageUser(String pageUser) {
        this.pageUser = pageUser;
    }
    public void setPntofCntLastname(String pntofCntLastname)
    {
        this.pntofCntLastname = pntofCntLastname;
    }
    public String getTxtCustomerDBA()
    {
        return txtCustomerDBA;
    }
    public void setTxtCustomerDBA(String txtCustomerDBA)
    {
        this.txtCustomerDBA = txtCustomerDBA;
    }
    public String getCmbcycle()
    {
        return cmbcycle;
    }
    public void setCmbcycle(String cmbcycle)
    {
        this.cmbcycle = cmbcycle;
    }
    public String getTxtcompetitor()
    {
        return txtcompetitor;
    }
    public void setTxtcompetitor(String txtcompetitor)
    {
        this.txtcompetitor = txtcompetitor;
    }
    public String getTxtcompetitorPrice()
    {
        return txtcompetitorPrice;
    }
    public void setTxtcompetitorPrice(String txtcompetitorPrice)
    {
        this.txtcompetitorPrice = txtcompetitorPrice;
    }
    public String getCmbtaxExempt()
    {
        return cmbtaxExempt;
    }
    public void setCmbtaxExempt(String cmbtaxExempt)
    {
        this.cmbtaxExempt = cmbtaxExempt;
    }
    public String getTxtcensus()
    {
        return txtcensus;
    }
    public void setTxtcensus(String txtcensus)
    {
        this.txtcensus = txtcensus;
    }
    public String getTxtcomments()
    {
        return txtcomments;
    }
    public void setTxtcomments(String txtcomments)
    {
        this.txtcomments = txtcomments;
    }
    public String getTxtcommission()
    {
        return txtcommission;
    }
    public void setTxtcommission(String txtcommission)
    {
        this.txtcommission = txtcommission;
    }
    public String getTxtcommissionIncome()
    {
        return txtcommissionIncome;
    }
    public void setTxtcommissionIncome(String txtcommissionIncome)
    {
        this.txtcommissionIncome = txtcommissionIncome;
    }
    public String getTxtcycleSwitch()
    {
        return txtcycleSwitch;
    }
    public void setTxtcycleSwitch(String txtcycleSwitch)
    {
        this.txtcycleSwitch = txtcycleSwitch;
    }
    public String getTxtusage()
    {
        return txtusage;
    }
    public void setTxtusage(String txtusage)
    {
        this.txtusage = txtusage;
    }
    public String getCmbCDRState() {
        return cmbCDRState;
    }
    public void setCmbCDRState(String cmbCDRState) {
        this.cmbCDRState = cmbCDRState;
    }
    public String getProspectiveCustId() {
        return prospectiveCustId;
    }
    public void setProspectiveCustId(String prospectiveCustId) {
        this.prospectiveCustId = prospectiveCustId;
    }
    
    public String getFormActions() {
        return formActions;
    }
    public void setFormActions(String formActions) {
        this.formActions = formActions;
    }
    public String getCmbCustomerStatus() {
        return cmbCustomerStatus;
    }
    public void setCmbCustomerStatus(String cmbCustomerStatus) {
        this.cmbCustomerStatus = cmbCustomerStatus;
    }
    public String getCmbSalesRep() {
        return cmbSalesRep;
    }
    public void setCmbSalesRep(String cmbSalesRep) {
        this.cmbSalesRep = cmbSalesRep;
    }
    public String getTxtBiztype() {
        return txtBiztype;
    }
    public void setTxtBiztype(String txtBiztype) {
        this.txtBiztype = txtBiztype;
    }
    public String getTxtCurentProvider() {
        return txtCurentProvider;
    }
    public void setTxtCurentProvider(String txtCurentProvider) {
        this.txtCurentProvider = txtCurentProvider;
    }
    public String getTxtCustomerAddress() {
        return txtCustomerAddress;
    }
    public void setTxtCustomerAddress(String txtCustomerAddress) {
        this.txtCustomerAddress = txtCustomerAddress;
    }
    public String getTxtCustomerCity() {
        return txtCustomerCity;
    }
    public void setTxtCustomerCity(String txtCustomerCity) {
        this.txtCustomerCity = txtCustomerCity;
    }
    public String getTxtCustomerCMSId() {
        return txtCustomerCMSId;
    }
    public void setTxtCustomerCMSId(String txtCustomerCMSId) {
        this.txtCustomerCMSId = txtCustomerCMSId;
    }
    public String getTxtCustomerName() {
        return txtCustomerName;
    }
    public void setTxtCustomerName(String txtCustomerName) {
        this.txtCustomerName = txtCustomerName;
    }
    public String getCmbCustomerState() {
        return cmbCustomerState;
    }
    public void setCmbCustomerState(String cmbCustomerState) {
        this.cmbCustomerState = cmbCustomerState;
    }
    public String getTxtCustomerTitle() {
        return txtCustomerTitle;
    }
    public void setTxtCustomerTitle(String txtCustomerTitle) {
        this.txtCustomerTitle = txtCustomerTitle;
    }
    public String getTxtEmail() {
        return txtEmail;
    }
    public void setTxtEmail(String txtEmail) {
        this.txtEmail = txtEmail;
    }
    public String getTxtFax() {
        return txtFax;
    }
    public void setTxtFax(String txtFax) {
        this.txtFax = txtFax;
    }
    public String getTxtMobile() {
        return txtMobile;
    }
    public void setTxtMobile(String txtMobile) {
        this.txtMobile = txtMobile;
    }
    public String getTxtPhone() {
        return txtPhone;
    }
    public void setTxtPhone(String txtPhone) {
        this.txtPhone = txtPhone;
    }
    public String getTxtzipcode() {
        return txtzipcode;
    }
    public void setTxtzipcode(String txtzipcode) {
        this.txtzipcode = txtzipcode;
    }
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors actionErrors = new ActionErrors();        
        if(formActions.equalsIgnoreCase("Add") || formActions.equalsIgnoreCase("preference")||formActions.equalsIgnoreCase("update"))
        {
	        if(txtCustomerName==null || txtCustomerName.trim().length()<1 || txtCustomerName.trim().equals(""))
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Name"));
	        }
	        
	        if(txtCustomerAddress==null || txtCustomerAddress.trim().length()<1 || txtCustomerAddress.trim().equals(""))
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Address"));
	        }
	        
	        if(txtCustomerCity==null || txtCustomerCity.trim().length()<1 || txtCustomerCity.trim().equals(""))
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","City"));
	        }
	        
	        if(cmbCustomerState==null || cmbCustomerState.equalsIgnoreCase("0") || cmbCustomerState.trim().equals(""))
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","State"));
	        }
	        if(cmbCMSLocationId==null || cmbCMSLocationId.trim().equals("0") || cmbCMSLocationId.trim().equals(""))
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Location Type"));
	        }
	        if(cmbCMSAddressTypeId==null || cmbCMSAddressTypeId.equalsIgnoreCase("0") || cmbCMSAddressTypeId.trim().equals(""))
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Address Type"));
	        }
	        if(Integer.parseInt(cmbCustomerStatus) <= 0)
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Status"));
	        }
	        if(pntofCntFirstname==null || pntofCntFirstname.trim().length()<1 || pntofCntFirstname.trim().equals(""))
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","First name"));
	        }
	        if(pntofCntLastname==null || pntofCntLastname.trim().length()<1 || pntofCntLastname.trim().equals(""))
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Last name"));
	        }
	        if(txtzipcode==null || txtzipcode.trim().length()<1 || txtzipcode.trim().equals(""))
	        {
	            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("common.error.required","Zipcode"));
	        }
	        if(txtEmail!=null && txtEmail.trim().length()>0 && !txtEmail.trim().equals(""))
	        {
				boolean invalidMail = false;
	            String at="@";
				String dot=".";
				int lat=txtEmail.indexOf(at);
				int lstr=txtEmail.length();
				int ldot=txtEmail.indexOf(dot);
				if (txtEmail.indexOf(at)==-1){
				    invalidMail = true;
				}

				if (txtEmail.indexOf(at)==-1 || txtEmail.indexOf(at)==0 || txtEmail.indexOf(at)==lstr){
				    invalidMail = true;
				}

				else if (txtEmail.indexOf(dot)==-1 || txtEmail.indexOf(dot)==0 || txtEmail.indexOf(dot)==lstr){
				    invalidMail = true;
				}

				else if (txtEmail.indexOf(at,(lat+1))!=-1){
				    invalidMail = true;
				 }

				else if (txtEmail.substring(lat-1,lat)==dot || txtEmail.substring(lat+1,lat+2)==dot){
				    invalidMail = true;
				 }

				else if (txtEmail.indexOf(dot,(lat+2))==-1){
				    invalidMail = true;
				 }
				
				 else if (txtEmail.indexOf(" ")!=-1){
				     invalidMail = true;
				 }		
	            
				if(invalidMail)
				    actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.email.invalidEmailId"));
	        }
	        
	        Calendar objCalendar = Calendar.getInstance();
	        
	        if(stYear <= objCalendar.get(Calendar.YEAR))
	            if(stMonth-1 <= objCalendar.get(Calendar.MONTH))
	                actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("error.invalidmonth"));
        }
        return actionErrors;
    }
    public Date getObjdate() {
        return objdate;
    }
    public int getStMonth() {
        return stMonth;
    }
    public int getStYear() {
        return stYear;
    }
    public void setObjdate(Date objdate) {
        this.objdate = objdate;
    }
    public void setStMonth(int stMonth) {
        this.stMonth = stMonth;
    }
    public void setStYear(int stYear) {
        this.stYear = stYear;
    }
    public String getTxtcommentsGeneral() {
        return txtcommentsGeneral;
    }
    public void setTxtcommentsGeneral(String txtcommentsGeneral) {
        this.txtcommentsGeneral = txtcommentsGeneral;
    }
    public String getAggregator()
    {
        return aggregator;
    }
    public void setAggregator(String aggregator)
    {
        this.aggregator = aggregator;
    }
    public String getBroker()
    {
        return broker;
    }
    public void setBroker(String broker)
    {
        this.broker = broker;
    }
    public String getConsultant()
    {
        return consultant;
    }
    public void setConsultant(String consultant)
    {
        this.consultant = consultant;
    }
    public String getNoofESIIDs()
    {
        return noofESIIDs;
    }
    public void setNoofESIIDs(String noofESIIDs)
    {
        this.noofESIIDs = noofESIIDs;
    }
    public String getTypeNewRenewal()
    {
        return typeNewRenewal;
    }
    public void setTypeNewRenewal(String typeNewRenewal)
    {
        this.typeNewRenewal = typeNewRenewal;
    }
}
