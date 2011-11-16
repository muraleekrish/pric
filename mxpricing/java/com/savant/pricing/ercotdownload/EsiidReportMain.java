/*
 * Created on Apr 7, 2007
 *
 * ClassName	:  	EsiidReportMain.java
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringBufferInputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.savant.pricing.common.BuildConfig;

public class EsiidReportMain 
{
	public EsiidReportMain() 
	{
		super();
	}
	
	/**
	 * @param args
	 */
	
	public void searchEsiidDetail(String esiidNo)
	{
		ErcotConfigLoader objErcotConfigLoader = new ErcotConfigLoader();
		String strResponseFileName="";
		String url = objErcotConfigLoader.getUrl();
		String mimePath = objErcotConfigLoader.getMimePath();
		String reportName="ESIID REPORTS_NEW_";
		String logStr=mimePath.trim()+"esiidReportLog.txt";
		
		try
		{
			FileOutputStream fo = new FileOutputStream(logStr,true);
			
			try 
			{
				StringBuffer sb = new StringBuffer();
				
				sb.append("MIME-Version: 1.0\n")
				.append("Content-Type: multipart/Related; ")
				.append("Type=\"Application/X-ERCOT-MessageSystem\"; ")
				.append("boundary=\"ERCOT-XML-DATA\"\n\n\n")
				.append("This is a multi-part message in MIME format\n\n")
				.append("--ERCOT-XML-DATA\n")
				.append("Content-Type: application/XML\n\n")
				.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n")
				.append("<Header>\n")
				.append("   <TransactionInfo>\n")
				.append("       <Action>")
				.append("Query")
				.append("</Action>\n")
				.append("       <Subject>")
				.append("Esiid")
				.append("</Subject>\n")
				.append("       <Version>1</Version>\n")
				.append("       <Environment>Production</Environment>\n")
				.append("    </TransactionInfo>\n")
				.append("</Header>\n\n")
				
				.append("--ERCOT-XML-DATA\n")
				.append("Content-Type: application/XML \n\n")
				.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n")
				.append("<QueryEsiidInput>\n")
				.append("    <Esiid>")
				.append(esiidNo)
				.append("</Esiid>\n")
				.append("</QueryEsiidInput>\n")
				.append("--ERCOT-XML-DATA--");   
				
				FileOutputStream fos = new FileOutputStream(mimePath+reportName.trim()+" SearchRequest.mime");	
				StringBufferInputStream sbis = new StringBufferInputStream(sb.toString());
				int read = 0;
				byte buff[] = new byte[1024];
				while((read=sbis.read(buff))!=-1)
				{
					fos.write(buff,0,read);
				}
				sbis.close();
				fos.close();
				File requestMime = new File(mimePath+reportName.trim()+" SearchRequest.mime");
				FileInputStream fis = new FileInputStream(requestMime);
				HttpsClient client = new HttpsClient( new URL( url ));
				
				// set connection properties
				client.setKeyStoreFile(objErcotConfigLoader.getKeyStoreFile());
				client.setKeyStorePasscode(objErcotConfigLoader.getKeyStorePassword());
				client.setRequestHeader( "Content-Type", "Application/X-Ercot-MessageSystem" );
				client.setRequestHeader( "Content-Length", Long.toString( requestMime.length()));
				
				// establish connection to host
				client.connect();
				
				// write to server
				OutputStream os = client.getOutputStream();
				
				//Read the mime request for report search and change the date value          
				
				while(( read = fis.read( buff )) >0 )
				{
					os.write( buff, 0, read );
					if(BuildConfig.DMODE)
					    System.out.println(new String(buff,0,read));
				}
				
				// get output stream from server
				InputStream is = client.getInputStream();            
				FileOutputStream mimeOutput  = new FileOutputStream(mimePath+reportName.trim()+" SearchResult.mime");
				
				String str="";	
				boolean flag=false;
				buff=new byte[1024];
				for(int i=0;i<5;i++)
				{
					read=is.read(buff);
					if(i==3)
					{
						if(read==100)
						{
							i=5;
							if(BuildConfig.DMODE)
							    System.out.println("NO ESIID RECORDS FOUND:");
						}
					}
					if(BuildConfig.DMODE)
					    System.out.println("NO OF BYTES:::::::"+read);
					str=new String(buff);
					mimeOutput.write(buff,0,read);
				}
				
				is.close();
				os.close();
				//mimeInput.close();
				fis.close();
				mimeOutput.close();
				
				strResponseFileName=mimePath+reportName.trim()+" SearchResult.mime";  
			} 
			catch (FileNotFoundException e) 
			{
			    if(BuildConfig.DMODE)
			        System.out.println(e.getClass());
				
				byte data[] = ("\n  ReportFinder :"+"\n\t"+this.getClass()+"\n\t "+e.getClass()+" : "+e.getMessage()).getBytes();
				fo.write(data);
				objErcotConfigLoader.setMailSubject("Failed its Execution");
				e.printStackTrace();
			} 
			catch (SecurityException e) 
			{
				byte data[] = ("\n  ReportFinder :"+"\n\t"+this.getClass()+"\n\t "+e.getClass()+" : "+e.getMessage()).getBytes();
				fo.write(data);
				objErcotConfigLoader.setMailSubject("Failed its Execution");
				e.printStackTrace();
			} 
			catch (MalformedURLException e) 
			{
				byte data[] = ("\n  ReportFinder :"+"\n\t"+this.getClass()+"\n\t "+e.getClass()+" : "+e.getMessage()).getBytes();
				fo.write(data);
				objErcotConfigLoader.setMailSubject("Failed its Execution");
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				byte data[] = ("\n  ReportFinder :"+"\n\t"+this.getClass()+"\n\t "+e.getClass()+" : "+e.getMessage()).getBytes();
				fo.write(data);
				objErcotConfigLoader.setMailSubject("Failed its Execution");
				e.printStackTrace();
			} 
			catch(Exception e)
			{
			    if(BuildConfig.DMODE)
			        System.out.println(e.getClass());
				objErcotConfigLoader.setMailSubject("Failed its Execution");
				byte data[] = ("\n  ReportFinder :"+"\n\t"+this.getClass()+"\n\t "+e.getClass()+" : "+e.getMessage()).getBytes();
				fo.write(data);
				e.printStackTrace();
			}
			fo.close();
		}
		catch(Exception e)
		{
			objErcotConfigLoader.setMailSubject("Failed its Execution");
			e.printStackTrace();
		}
		if(BuildConfig.DMODE)
		    System.out.println(strResponseFileName);
		//	return strResponseFileName;
	}
	
	public byte[] strReplace(String str)
	{
		if(str.indexOf("</QueryEsiidOutput>")!=-1)
		{
			String str1=(str).replaceAll("</QueryEsiidOutput>","</Results></QueryEsiidOutput>");
			str=new String(str1);
		}
		if(BuildConfig.DMODE)
		    System.out.println("STRING:"+str+"STRING_INDEX:"+str.indexOf("</QueryEsiidOutput>"));
		byte[] buff1=str.getBytes();
		return buff1;
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException 
	{
	    EsiidReportMain obj=new EsiidReportMain();
		EsiidResponseParser objParser=new EsiidResponseParser();
		EsiidReportDetail objDetail= new EsiidReportDetail();
		String esiidNo="1013830000087";
		if(esiidNo!=null)
		{
			obj.searchEsiidDetail(esiidNo);
			objDetail=objParser.esiidReportparser();
		}
		if(BuildConfig.DMODE)
		    System.out.println("City:"+objDetail.getCity());
		/*esiidReportMain rptFnd = new esiidReportMain();
		 String str=rptFnd.searchEsiidDetail("101383000008730");*/
	}
}

/*
*$Log: EsiidReportMain.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/06/12 12:57:01  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.3  2007/04/23 05:52:20  kduraisamy
*unwanted println removed.
*
*Revision 1.2  2007/04/18 03:56:13  kduraisamy
*imports organized.
*
*Revision 1.1  2007/04/07 12:11:47  rraman
*new package and classes added for esiid download
*
*
*/