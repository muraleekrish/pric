/*
 * Created on Apr 7, 2007
 *
 * ClassName	:  	ErcotConfigLoader.java
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
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.savant.pricing.common.BuildConfig;

public class ErcotConfigLoader
{
	public ErcotConfigLoader()
	{
		confiLoader();
	}
	
	private String mailSubject = "Ran Successfully";
	private String keyStoreFile = "";
	private String keyStorePassword = "";
	private String keyStoreFileTestEnv = "";
	private String keyStorePasswordTestEnv = "";
	private String importCount = "0";
	private HashMap mailPorpertiesHm = new HashMap();
	private String userName = "";
	private String password = "";
	private String host = "";
	private HashMap mailAddressHm = new HashMap();
	private String downloadPath = "";
	private String imagePath = "";
	private String logPath = "";
	private String mimePath = "";
	private String url = "";
	private String urltest = "";
	private int margin = 0;
	
	
	/**
	 * @return Returns the keyStoreFileTestEnv.
	 */
	public String getKeyStoreFileTestEnv()
	{
		return keyStoreFileTestEnv;
	}

	/**
	 * @return Returns the keyStorePasswordTestEnv.
	 */
	public String getKeyStorePasswordTestEnv()
	{
		return keyStorePasswordTestEnv;
	}

	/**
	 * @return Returns the urltest.
	 */
	public String getUrltest()
	{
		return urltest;
	}

	/**
	 * @return Returns the margin.
	 */
	public int getMargin()
	{
		return margin;
	}

	/**
	 * @return Returns the downloadPath.
	 */
	public String getDownloadPath()
	{
		return downloadPath;
	}

	/**
	 * @return Returns the logPath.
	 */
	public String getLogPath()
	{
		return logPath;
	}

	/**
	 * @return Returns the mailAddressHm.
	 */
	public HashMap getMailAddressHm()
	{
		return mailAddressHm;
	}

	/**
	 * @return Returns the mailPorpertiesHm.
	 */
	public HashMap getMailPorpertiesHm()
	{
		return mailPorpertiesHm;
	}

	/**
	 * @return Returns the mimePath.
	 */
	public String getMimePath()
	{
		return mimePath;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @return Returns the url.
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName()
	{
		return userName;
	}

	public boolean confiLoader() 
	{
		boolean result = false;
		
		try
		{
		    //FileInputStream fis = new FileInputStream("d:/ercot-tasks/config/test.xml");
		    FileInputStream fis = new FileInputStream("d:/ercot-tasks/config/ercot-config.xml");
		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    DocumentBuilder db = dbf.newDocumentBuilder();
		    Document doc = db.parse(fis);
		    NodeList nlst = doc.getElementsByTagName("mail-tracer");		
		    Element element= (Element)nlst.item(0).getChildNodes();
		    this.userName = element.getElementsByTagName("username").item(0).getChildNodes().item(0).getNodeValue();
		    if(BuildConfig.DMODE)
		        System.out.println(this.userName);
		    this.password = element.getElementsByTagName("password").item(0).getChildNodes().item(0).getNodeValue();
		    if(BuildConfig.DMODE)
		        System.out.println(this.password);
		    Vector vecValue = new Vector();
		    for(int i=0;i<element.getElementsByTagName("mail.address").getLength();i++)
		    {
		        element = (Element)element.getElementsByTagName("mail.address").item(0).getChildNodes();
		        for(int j=0;j<element.getElementsByTagName("from").item(0).getChildNodes().getLength();j++)
		        {
		            vecValue.addElement(element.getElementsByTagName("from").item(j).getChildNodes().item(0).getNodeValue().trim());
		            if(BuildConfig.DMODE)
		                System.out.println(vecValue);
		        }
		    }
		    mailAddressHm.put("from",vecValue);
		    
		    vecValue = new Vector();
		    nlst = doc.getElementsByTagName("mail-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    element= (Element)element.getElementsByTagName("mail.address").item(0).getChildNodes();
		    element = (Element)element.getElementsByTagName("to").item(0).getChildNodes();
		    for(int i=0;i<element.getChildNodes().getLength();i++)
		    {
		        if(element.getChildNodes().item(i).getNodeType()==1)
		        {
		            for(int j=0;j<element.getChildNodes().item(i).getChildNodes().getLength();j++)
		            {
		                vecValue.addElement(element.getChildNodes().item(i).getChildNodes().item(j));
		            }
		        }
		        else if(element.getChildNodes().item(i).getNodeType()==3)
		        {
		            
		        }
		    }
		    mailAddressHm.put("general",vecValue);
		    
		    //eal to list
		    vecValue = new Vector();
		    nlst = doc.getElementsByTagName("mail-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    element= (Element)element.getElementsByTagName("ealmail.address").item(0).getChildNodes();
		    element = (Element)element.getElementsByTagName("to").item(0).getChildNodes();
		    for(int i=0;i<element.getChildNodes().getLength();i++)
		    {
		        if(element.getChildNodes().item(i).getNodeType()==1)
		        {
		            for(int j=0;j<element.getChildNodes().item(i).getChildNodes().getLength();j++)
		            {
		                vecValue.addElement(element.getChildNodes().item(i).getChildNodes().item(j));
		            }
		        }
		        else if(element.getChildNodes().item(i).getNodeType()==3)
		        {
		            
		        }
		    }
		    mailAddressHm.put("eal",vecValue);
		    
		    //		eal to listbcc
		    vecValue = new Vector();
		    nlst = doc.getElementsByTagName("mail-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    element= (Element)element.getElementsByTagName("ealmail.address").item(0).getChildNodes();
		    element = (Element)element.getElementsByTagName("bcc").item(0).getChildNodes();
		    for(int i=0;i<element.getChildNodes().getLength();i++)
		    {
		        if(element.getChildNodes().item(i).getNodeType()==1)
		        {
		            for(int j=0;j<element.getChildNodes().item(i).getChildNodes().getLength();j++)
		            {
		                vecValue.addElement(element.getChildNodes().item(i).getChildNodes().item(j));
		            }
		        }
		        else if(element.getChildNodes().item(i).getNodeType()==3)
		        {
		            
		        }
		    }
		    mailAddressHm.put("ealbcc",vecValue);
		    
		    //		<forecastmailload.address>
		    vecValue = new Vector();
		    nlst = doc.getElementsByTagName("mail-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    element= (Element)element.getElementsByTagName("forecastmailload.address").item(0).getChildNodes();
		    element = (Element)element.getElementsByTagName("to").item(0).getChildNodes();
		    for(int i=0;i<element.getChildNodes().getLength();i++)
		    {
		        if(element.getChildNodes().item(i).getNodeType()==1)
		        {
		            for(int j=0;j<element.getChildNodes().item(i).getChildNodes().getLength();j++)
		            {
		                vecValue.addElement(element.getChildNodes().item(i).getChildNodes().item(j));
		            }
		        }
		        else if(element.getChildNodes().item(i).getNodeType()==3)
		        {
		            
		        }
		    }
		    mailAddressHm.put("forecastload",vecValue);
		    
		    //		<forecastmailload.address> bcc
		    vecValue = new Vector();
		    nlst = doc.getElementsByTagName("mail-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    element= (Element)element.getElementsByTagName("forecastmailload.address").item(0).getChildNodes();
		    element = (Element)element.getElementsByTagName("bcc").item(0).getChildNodes();
		    for(int i=0;i<element.getChildNodes().getLength();i++)
		    {
		        if(element.getChildNodes().item(i).getNodeType()==1)
		        {
		            for(int j=0;j<element.getChildNodes().item(i).getChildNodes().getLength();j++)
		            {
		                vecValue.addElement(element.getChildNodes().item(i).getChildNodes().item(j));
		            }
		        }
		        else if(element.getChildNodes().item(i).getNodeType()==3)
		        {
		            
		        }
		    }
		    mailAddressHm.put("forecastloadbcc",vecValue);
		    
		    //		<<naturalgasmail.address>>
		    vecValue = new Vector();
		    nlst = doc.getElementsByTagName("mail-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    element= (Element)element.getElementsByTagName("naturalgasmail.address").item(0).getChildNodes();
		    element = (Element)element.getElementsByTagName("to").item(0).getChildNodes();
		    for(int i=0;i<element.getChildNodes().getLength();i++)
		    {
		        if(element.getChildNodes().item(i).getNodeType()==1)
		        {
		            for(int j=0;j<element.getChildNodes().item(i).getChildNodes().getLength();j++)
		            {
		                vecValue.addElement(element.getChildNodes().item(i).getChildNodes().item(j));
		            }
		        }
		        else if(element.getChildNodes().item(i).getNodeType()==3)
		        {
		            
		        }
		    }
		    mailAddressHm.put("naturalgas",vecValue);
		    
		    //		<<naturalgasmail.address>>bcc
		    vecValue = new Vector();
		    nlst = doc.getElementsByTagName("mail-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    element= (Element)element.getElementsByTagName("naturalgasmail.address").item(0).getChildNodes();
		    element = (Element)element.getElementsByTagName("bcc").item(0).getChildNodes();
		    for(int i=0;i<element.getChildNodes().getLength();i++)
		    {
		        if(element.getChildNodes().item(i).getNodeType()==1)
		        {
		            for(int j=0;j<element.getChildNodes().item(i).getChildNodes().getLength();j++)
		            {
		                vecValue.addElement(element.getChildNodes().item(i).getChildNodes().item(j));
		            }
		        }
		        else if(element.getChildNodes().item(i).getNodeType()==3)
		        {
		            
		        }
		    }
		    mailAddressHm.put("naturalgasbcc",vecValue);
		    
		    //		<<dlfmail.address>>
		    vecValue = new Vector();
		    nlst = doc.getElementsByTagName("mail-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    element= (Element)element.getElementsByTagName("dlfmail.address").item(0).getChildNodes();
		    element = (Element)element.getElementsByTagName("to").item(0).getChildNodes();
		    for(int i=0;i<element.getChildNodes().getLength();i++)
		    {
		        if(element.getChildNodes().item(i).getNodeType()==1)
		        {
		            for(int j=0;j<element.getChildNodes().item(i).getChildNodes().getLength();j++)
		            {
		                vecValue.addElement(element.getChildNodes().item(i).getChildNodes().item(j));
		            }
		        }
		        else if(element.getChildNodes().item(i).getNodeType()==3)
		        {
		            
		        }
		    }
		    if(BuildConfig.DMODE)
		        System.out.println("dlfmail : "+vecValue);
		    mailAddressHm.put("dlfmail",vecValue);
		    
		    vecValue = new Vector();
		    nlst = doc.getElementsByTagName("mail-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    element= (Element)element.getElementsByTagName("mail.address").item(0).getChildNodes();
		    element = (Element)element.getElementsByTagName("bcc").item(0).getChildNodes();
		    for(int i=0;i<element.getChildNodes().getLength();i++)
		    {
		        if(element.getChildNodes().item(i).getNodeType()==1)
		        {
		            for(int j=0;j<element.getChildNodes().item(i).getChildNodes().getLength();j++)
		            {
		                vecValue.addElement(element.getChildNodes().item(i).getChildNodes().item(j));
		            }
		        }
		        else if(element.getChildNodes().item(i).getNodeType()==3)
		        {
		            
		        }
		    }
		    mailAddressHm.put("bcc",vecValue);
		    
		    vecValue = new Vector();
		    nlst = doc.getElementsByTagName("mail-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    element= (Element)element.getElementsByTagName("mail.address").item(0).getChildNodes();
		    element = (Element)element.getElementsByTagName("carboncopy").item(0).getChildNodes();
		    for(int i=0;i<element.getChildNodes().getLength();i++)
		    {
		        if(element.getChildNodes().item(i).getNodeType()==1)
		        {
		            for(int j=0;j<element.getChildNodes().item(i).getChildNodes().getLength();j++)
		            {
		                vecValue.addElement(element.getChildNodes().item(i).getChildNodes().item(j));
		            }
		        }
		        else if(element.getChildNodes().item(i).getNodeType()==3)
		        {
		            
		        }
		    }
		    
		    mailAddressHm.put("cc",vecValue);
		    if(BuildConfig.DMODE)
		        System.out.println(mailAddressHm);
		    
		    vecValue = new Vector();
		    nlst = doc.getElementsByTagName("mail-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    element= (Element)element.getElementsByTagName("session-properties").item(0).getChildNodes();
		    for(int i=0;i<element.getChildNodes().getLength();i++)
		    {
		        if(element.getChildNodes().item(i).getNodeType()==1)
		        {
		            vecValue.addElement(element.getChildNodes().item(i).getChildNodes().item(0));
		            mailPorpertiesHm.put(element.getChildNodes().item(i).getNodeName(),element.getChildNodes().item(i).getChildNodes().item(0).getNodeValue().trim());
		        }
		        if(element.getChildNodes().item(i).getNodeType()==3)
		        {
		            
		        }
		    }
		    if(BuildConfig.DMODE)
		        System.out.println(mailPorpertiesHm);
		    
		    nlst = doc.getElementsByTagName("path-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    this.downloadPath = element.getElementsByTagName("download-path").item(0).getChildNodes().item(0).getNodeValue();
		    if(BuildConfig.DMODE)
		        System.out.println(downloadPath);
		    
		    nlst = doc.getElementsByTagName("path-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    this.logPath = element.getElementsByTagName("log-path").item(0).getChildNodes().item(0).getNodeValue();
		    if(BuildConfig.DMODE)
		        System.out.println(this.logPath);
		    
		    nlst = doc.getElementsByTagName("path-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    this.mimePath = element.getElementsByTagName("mime-path").item(0).getChildNodes().item(0).getNodeValue();
		    if(BuildConfig.DMODE)
		        System.out.println(this.mimePath);
		    
		    nlst = doc.getElementsByTagName("path-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    this.url = element.getElementsByTagName("url").item(0).getChildNodes().item(0).getNodeValue();
		    if(BuildConfig.DMODE)
		        System.out.println(this.url);
		    
		    nlst = doc.getElementsByTagName("path-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    this.urltest = element.getElementsByTagName("urltest").item(0).getChildNodes().item(0).getNodeValue();
		    if(BuildConfig.DMODE)
		        System.out.println(this.url);
		    
		    nlst = doc.getElementsByTagName("path-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    this.imagePath = element.getElementsByTagName("image-path").item(0).getChildNodes().item(0).getNodeValue();
		    
		    nlst = doc.getElementsByTagName("mail-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    this.host = element.getElementsByTagName("host").item(0).getChildNodes().item(0).getNodeValue();
		    if(BuildConfig.DMODE)
		        System.out.println(this.host);
		    
		    nlst = doc.getElementsByTagName("lookback");		
		    this.margin = Integer.parseInt(nlst.item(0).getChildNodes().item(0).getNodeValue());
		    //element= (Element)nlst.item(0).getChildNodes();
		    //this.margin = 
		    //System.out.println(this.margin);
		    
		    nlst = doc.getElementsByTagName("connection-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    this.keyStoreFile = element.getElementsByTagName("keystore-file").item(0).getChildNodes().item(0).getNodeValue();
		    if(BuildConfig.DMODE)
		        System.out.println(this.keyStoreFile);
		    
		    nlst = doc.getElementsByTagName("connection-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    this.keyStorePassword = element.getElementsByTagName("keystore-password").item(0).getChildNodes().item(0).getNodeValue();
		    if(BuildConfig.DMODE)
		        System.out.println(this.keyStorePassword);
		    
		    nlst = doc.getElementsByTagName("connection-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    this.keyStoreFileTestEnv = element.getElementsByTagName("keystore-file-test").item(0).getChildNodes().item(0).getNodeValue();
		    if(BuildConfig.DMODE)
		        System.out.println(this.keyStoreFileTestEnv);
		    
		    nlst = doc.getElementsByTagName("connection-tracer");		
		    element= (Element)nlst.item(0).getChildNodes();
		    this.keyStorePasswordTestEnv = element.getElementsByTagName("keystore-password-test").item(0).getChildNodes().item(0).getNodeValue();
		    if(BuildConfig.DMODE)
		        System.out.println(this.keyStorePasswordTestEnv);
		}
		catch(Exception e)
		{		
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args)
	{ 
		ErcotConfigLoader obj = new ErcotConfigLoader();
		obj.confiLoader();
	}

	/**
	 * @return Returns the host.
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * @return Returns the mailSubject.
	 */
	public String getMailSubject()
	{
		return mailSubject;
	}

	/**
	 * @param mailSubject The mailSubject to set.
	 */
	public void setMailSubject(String mailSubject)
	{
		this.mailSubject = mailSubject;
	}

	/**
	 * @return Returns the importCount.
	 */
	public String getImportCount()
	{
		return importCount;
	}

	/**
	 * @param importCount The importCount to set.
	 */
	public void setImportCount(String importCount)
	{
		this.importCount = importCount;
	}

	/**
	 * @return Returns the keyStoreFile.
	 */
	public String getKeyStoreFile()
	{
		return keyStoreFile;
	}

	/**
	 * @return Returns the keyStorePassword.
	 */
	public String getKeyStorePassword()
	{
		return keyStorePassword;
	}

    /**
     * @return Returns the imagePath.
     */
    public String getImagePath()
    {
        return imagePath;
    }
}


/*
*$Log: ErcotConfigLoader.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.6  2007/06/12 12:57:01  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.5  2007/05/09 07:25:06  kduraisamy
*imports organized.
*
*Revision 1.4  2007/04/23 05:52:20  kduraisamy
*unwanted println removed.
*
*Revision 1.3  2007/04/11 09:18:33  kduraisamy
*some changes made by ramya.
*
*Revision 1.2  2007/04/10 07:17:17  kduraisamy
*ercot download path changed.
*
*Revision 1.1  2007/04/07 12:11:47  rraman
*new package and classes added for esiid download
*
*
*/