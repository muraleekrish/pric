/*
 * Created on Apr 7, 2007
 *
 * ClassName	:  	EsiidResponseParser.java
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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.savant.pricing.common.BuildConfig;

public class EsiidResponseParser
{
	public EsiidReportDetail esiidReportparser() throws ParserConfigurationException, SAXException, IOException
	{
	    EsiidReportDetail objDetail=null;
		
		try{
			ErcotConfigLoader objErcotConfigLoader = new ErcotConfigLoader();
			String mimePath = objErcotConfigLoader.getMimePath();
			String reportName="ESIID REPORTS_NEW_";
			String str="";
			
			String responseFileName= mimePath+reportName.trim()+" SearchResult.mime";
			String fileNameNew= mimePath+reportName.trim()+" SearchResultReplaced.xml";
			FileReader isr=new FileReader(responseFileName);
			
			FileInputStream fis=new FileInputStream(responseFileName);
			FileOutputStream fos=new FileOutputStream(fileNameNew);
			BufferedReader bfr=new BufferedReader(isr);
			//BufferedWriter bfw=new BufferedWriter(osw);
			StringBuffer sbf=new StringBuffer();
			StringBuffer sbf1=new StringBuffer();
			boolean eof=false;int lineNo=1,start=1000;
			boolean flag=false;
			boolean flagResult=false;
			while(!eof)
			{
				str=bfr.readLine();
				
				if(str==null)
				{eof=true;}
				else
				{
					
					int n=str.indexOf("<QueryEsiidOutput>");
					if(n!=-1)
					{
						//System.out.println("LINENO"+lineNo+"START:"+start);
						start=lineNo;
						flag=true;
					}
					
					if(lineNo>=start)
					{
						//System.out.println("LINENO:::"+lineNo);
						//System.out.println("STARTNO::"+start);
						//System.out.println("HI");
						if(str.indexOf("</Results>")!=-1)
						{
							flagResult=true;
							//System.out.println("FLAG RESULT::::::::::::::"+flagResult);
						}
						if(str.indexOf("</QueryEsiidOutput>")!=-1)
						{
							String str1="";
							str1=str;
							if(!flagResult)
							{
								str1=(str).replaceAll("</QueryEsiidOutput>","</Results></QueryEsiidOutput>");
							}
							
							str=str1;
							sbf1.append(sbf.toString());
							sbf1.append(str);
						}
						sbf.append(str);
						sbf.append(System.getProperty("line.separator"));
					}
					else
					{
						
					}
					lineNo++;
				}
			}
			
			byte[] b=sbf1.toString().getBytes();
			fos.write(b,0,b.length);
			
			if(flag)
			{
			    objDetail=new EsiidReportDetail();
				FileInputStream fis1 = new FileInputStream(fileNameNew);
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(fis1);
				
				NodeList nlist=doc.getElementsByTagName("QueryEsiidOutput");
				//System.out.println("ELEMENT SIZE::"+nlist.item(0).getFirstChild().getNodeValue());
				
				Element element1=(Element)nlist.item(0).getChildNodes();
				String esiid="";
				if(element1.getElementsByTagName("Street").item(0).getChildNodes().getLength()!=0)
				{
					esiid=element1.getElementsByTagName("Esiid").item(0).getChildNodes().item(0).getNodeValue();
				}
				String duns="";
				if(element1.getElementsByTagName("Street").item(0).getChildNodes().getLength()!=0)
				{
					duns=element1.getElementsByTagName("Duns").item(0).getChildNodes().item(0).getNodeValue();
				}
				String mpName="";
				if(element1.getElementsByTagName("Street").item(0).getChildNodes().getLength()!=0)
				{
					mpName=element1.getElementsByTagName("MpName").item(0).getChildNodes().item(0).getNodeValue();
				}
				String eligDate="";
				if(element1.getElementsByTagName("Street").item(0).getChildNodes().getLength()!=0)
				{
					eligDate=element1.getElementsByTagName("ESIIDEligibilityDate").item(0).getChildNodes().item(0).getNodeValue();
				}
				String esiidStartDate="";
				if(element1.getElementsByTagName("Street").item(0).getChildNodes().getLength()!=0)
				{
					esiidStartDate=element1.getElementsByTagName("ESIIDStartDate").item(0).getChildNodes().item(0).getNodeValue();
				}
				String esiidCreateDate="";
				if(element1.getElementsByTagName("Street").item(0).getChildNodes().getLength()!=0)
				{
					esiidCreateDate=element1.getElementsByTagName("ESIIDCreateDate").item(0).getChildNodes().item(0).getNodeValue();
				}
				String esiidEndDate="";
				if(element1.getElementsByTagName("Street").item(0).getChildNodes().getLength()!=0)
				{
					esiidEndDate=element1.getElementsByTagName("ESIIDEndDate").item(0).getChildNodes().item(0).getNodeValue();
				}
				String esiidEffectiveDate="";
				if(element1.getElementsByTagName("Street").item(0).getChildNodes().getLength()!=0)
				{
					esiidEffectiveDate=element1.getElementsByTagName("ESIIDEffectiveDate").item(0).getChildNodes().item(0).getNodeValue();
				}
				String esiidStatus="";
				if(element1.getElementsByTagName("Street").item(0).getChildNodes().getLength()!=0)
				{
					esiidStatus=element1.getElementsByTagName("ESIIDStatus").item(0).getChildNodes().item(0).getNodeValue();
				}
				String esiidPremiseType="";
				if(element1.getElementsByTagName("Street").item(0).getChildNodes().getLength()!=0)
				{
					esiidPremiseType=element1.getElementsByTagName("ESIIDPremiseType").item(0).getChildNodes().item(0).getNodeValue();
				}
				
				objDetail.setEsiid(esiid);
				objDetail.setDuns(duns);
				objDetail.setMpName(mpName);
				objDetail.setEsiidEligDate(eligDate);
				objDetail.setEsiidStartDate(esiidStartDate);
				objDetail.setEsiidCreateDate(esiidCreateDate);
				objDetail.setEsiidEndDate(esiidEndDate);
				objDetail.setEsiidEffectiveDate(esiidEffectiveDate);
				objDetail.setEsiidStatus(esiidStatus);
				objDetail.setEsiidPremiseDate(esiidPremiseType);
				
				Element element=(Element)element1.getElementsByTagName("Address").item(0).getChildNodes();
				String street="";
				if(element.getElementsByTagName("Street").item(0).getChildNodes().getLength()!=0)
				{
					street=element.getElementsByTagName("Street").item(0).getChildNodes().item(0).getNodeValue();
				}
				String streetOverflow="";
				if(element.getElementsByTagName("StreetOverflow").item(0).getChildNodes().getLength()!=0)
				{
					streetOverflow=element.getElementsByTagName("StreetOverflow").item(0).getChildNodes().item(0).getNodeValue();
				}
				String city="";
				if(element.getElementsByTagName("City").item(0).getChildNodes().getLength()!=0)
				{
					city=element.getElementsByTagName("City").item(0).getChildNodes().item(0).getNodeValue();
				}
				String state="";
				if(element.getElementsByTagName("State").item(0).getChildNodes().getLength()!=0)
				{
					state=element.getElementsByTagName("State").item(0).getChildNodes().item(0).getNodeValue();
				}
				String zip="";
				if(element.getElementsByTagName("Zip").item(0).getChildNodes().getLength()!=0)
				{
					zip=element.getElementsByTagName("Zip").item(0).getChildNodes().item(0).getNodeValue();
				}
				
				objDetail.setStreet(street);
				objDetail.setStreetOverflow(streetOverflow);
				objDetail.setCity(city);
				objDetail.setState(state);
				objDetail.setZip(zip);

				element=(Element)element1.getElementsByTagName("MeterRead").item(0).getChildNodes();
				
				String meterFlag="";
				if(element.getElementsByTagName("Flag").item(0).getChildNodes().getLength()!=0)
				{
					meterFlag=element.getElementsByTagName("Flag").item(0).getChildNodes().item(0).getNodeValue();
				}
				
				String meterCode="";
				if(element.getElementsByTagName("Code").item(0).getChildNodes().getLength()!=0)
				{
					meterCode=element.getElementsByTagName("Code").item(0).getChildNodes().item(0).getNodeValue();
				}
				String meterCalculationDate="";
				if(element.getElementsByTagName("CalculationDate").item(0).getChildNodes().getLength()!=0)
				{
					meterCalculationDate=element.getElementsByTagName("CalculationDate").item(0).getChildNodes().item(0).getNodeValue();
				}
				
				objDetail.setFlag(meterFlag);
				objDetail.setMeterCode(meterCode);
				objDetail.setCalculationDate(meterCalculationDate);
				
				element=(Element)element1.getElementsByTagName("Station").item(0).getChildNodes();
				String stationName="";
				if(element.getElementsByTagName("Name").item(0).getChildNodes().getLength()!=0)
				{
					stationName=element.getElementsByTagName("Name").item(0).getChildNodes().item(0).getNodeValue();
				}
				
				String stationCode="";
				if(element.getElementsByTagName("Code").item(0).getChildNodes().getLength()!=0)
				{
					stationCode=element.getElementsByTagName("Code").item(0).getChildNodes().item(0).getNodeValue();
				}
				
				objDetail.setStationName(stationName);
				objDetail.setStationCode(stationCode);
				
				String PowerRegion=element1.getElementsByTagName("PowerRegion").item(0).getChildNodes().item(0).getNodeValue();
				objDetail.setPowerRegion(PowerRegion);
				
				String RORStartDate="";
				element=(Element)element1.getElementsByTagName("RepOfRecord").item(0).getChildNodes();
				String repFlag=element.getElementsByTagName("Flag").item(0).getChildNodes().item(0).getNodeValue();
				if(element.getElementsByTagName("RoRStartDate").item(0).getChildNodes().getLength()!=0)
				{
					RORStartDate=element.getElementsByTagName("RoRStartDate").item(0).getChildNodes().item(0).getNodeValue();
				}
				
				objDetail.setRepOfRecordFLAG(repFlag);
				objDetail.setRoRStartDate(RORStartDate);
				
				element=(Element)element1.getElementsByTagName("CSA").item(0).getChildNodes();
				
				String csaDuns="";
				if(element.getElementsByTagName("Duns").item(0).getChildNodes().getLength()!=0)
				{
					csaDuns=element.getElementsByTagName("Duns").item(0).getChildNodes().item(0).getNodeValue();
				}
				
				String csaStartDate="";
				if(element.getElementsByTagName("CsaStartDate").item(0).getChildNodes().getLength()!=0)
				{
					csaStartDate=element.getElementsByTagName("CsaStartDate").item(0).getChildNodes().item(0).getNodeValue();
				}
				
				objDetail.setCsaDuns(csaDuns);
				objDetail.setCsaStartDate(csaStartDate);
				if(BuildConfig.DMODE)
				    System.out.println("ESIID NO:"+esiid+"ADDRESS:"+street+" "+state+" "+city+" "+zip);
			}
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return objDetail;
	}
	
	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException
	{
		EsiidResponseParser obj=new EsiidResponseParser();
		obj.esiidReportparser();
	}
}

/*
*$Log: EsiidResponseParser.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/06/12 12:57:01  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.4  2007/04/24 10:12:22  jnadesan
*if esiid is not present it returns null
*
*Revision 1.3  2007/04/18 03:56:13  kduraisamy
*imports organized.
*
*Revision 1.2  2007/04/12 13:58:00  kduraisamy
*unwanted println commented.
*
*Revision 1.1  2007/04/07 12:11:47  rraman
*new package and classes added for esiid download
*
*
*/