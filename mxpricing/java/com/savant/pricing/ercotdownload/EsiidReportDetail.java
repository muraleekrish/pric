/*
 * Created on Apr 7, 2007
 *
 * ClassName	:  	EsiidReportDetail.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.ercotdownload;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EsiidReportDetail
{
	private String esiid="";
	private String duns="";
	private String mpName="";
	private String address="";
	private String 	street="";
	private String streetOverflow="";
	private String city="";
	private String state="";
	private String zip="";
	private String meterRead="";
	private String flag="";
	private String meterCode="";
	private String calculationDate="";
	private String stationName="";
	private String stationCode="";
	private String powerRegion="";
	private String repOfRecordFLAG="";
	private String roRStartDate="";
	private String csaDuns="";
	private String csaStartDate="";
	private String esiidEligDate="";
	private String esiidStartDate="";
	private String esiidCreateDate="";
	private String esiidEndDate="";
	private String esiidEffectiveDate="";
	private String esiidStatus="";
	private String esiidPremiseDate="";
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCalculationDate() {
		return calculationDate;
	}
	public void setCalculationDate(String calculationDate) {
		this.calculationDate = calculationDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCsaDuns() {
		return csaDuns;
	}
	public void setCsaDuns(String csaDuns) {
		this.csaDuns = csaDuns;
	}
	public String getCsaStartDate() {
		return csaStartDate;
	}
	public void setCsaStartDate(String csaStartDate) {
		this.csaStartDate = csaStartDate;
	}
	public String getDuns() {
		return duns;
	}
	public void setDuns(String duns) {
		this.duns = duns;
	}
	public String getEsiid() {
		return esiid;
	}
	public void setEsiid(String esiid) {
		this.esiid = esiid;
	}
	public String getEsiidCreateDate() {
		return esiidCreateDate;
	}
	public void setEsiidCreateDate(String esiidCreateDate) {
		this.esiidCreateDate = esiidCreateDate;
	}
	public String getEsiidEffectiveDate() {
		return esiidEffectiveDate;
	}
	public void setEsiidEffectiveDate(String esiidEffectiveDate) {
		this.esiidEffectiveDate = esiidEffectiveDate;
	}
	public String getEsiidEligDate() {
		return esiidEligDate;
	}
	public void setEsiidEligDate(String esiidEligDate) {
		this.esiidEligDate = esiidEligDate;
	}
	public String getEsiidEndDate() {
		return esiidEndDate;
	}
	public void setEsiidEndDate(String esiidEndDate) {
		this.esiidEndDate = esiidEndDate;
	}
	public String getEsiidPremiseDate() {
		return esiidPremiseDate;
	}
	public void setEsiidPremiseDate(String esiidPremiseDate) {
		this.esiidPremiseDate = esiidPremiseDate;
	}
	public String getEsiidStartDate() {
		return esiidStartDate;
	}
	public void setEsiidStartDate(String esiidStartDate) {
		this.esiidStartDate = esiidStartDate;
	}
	public String getEsiidStatus() {
		return esiidStatus;
	}
	public void setEsiidStatus(String esiidStatus) {
		this.esiidStatus = esiidStatus;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMeterCode() {
		return meterCode;
	}
	public void setMeterCode(String meterCode) {
		this.meterCode = meterCode;
	}
	public String getMeterRead() {
		return meterRead;
	}
	public void setMeterRead(String meterRead) {
		this.meterRead = meterRead;
	}
	public String getMpName() {
		return mpName;
	}
	public void setMpName(String mpName) {
		this.mpName = mpName;
	}
	public String getPowerRegion() {
		return powerRegion;
	}
	public void setPowerRegion(String powerRegion) {
		this.powerRegion = powerRegion;
	}
	public String getRepOfRecordFLAG() {
		return repOfRecordFLAG;
	}
	public void setRepOfRecordFLAG(String repOfRecordFLAG) {
		this.repOfRecordFLAG = repOfRecordFLAG;
	}
	public String getRoRStartDate() {
		return roRStartDate;
	}
	public void setRoRStartDate(String roRStartDate) {
		this.roRStartDate = roRStartDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreetOverflow() {
		return streetOverflow;
	}
	public void setStreetOverflow(String streetOverflow) {
		this.streetOverflow = streetOverflow;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}

}

/*
*$Log: EsiidReportDetail.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/07 12:11:47  rraman
*new package and classes added for esiid download
*
*
*/