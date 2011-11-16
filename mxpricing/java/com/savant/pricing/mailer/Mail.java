/*
 * Created on Feb 2, 2005
 *
 * ClassName	:  	Mail.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.mailer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.savant.pricing.common.BuildConfig;



/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Mail implements TransportListener
{
    public static final int SUCCESSFULLYSENT = 1;
    public static final int NOTSENT = 2;
    public static final int PARTIALLYSENT = 3;
	private Message message;
	private Session session;
	private Multipart multipart;
	private BodyPart messageBodyPart;
	private Properties props;
	private int deliveryStatus=0;
	private InternetAddress addresses[] = new InternetAddress[0];
	private Vector addressVec = new Vector();
	
	private String smtpHostServer = "";
    private String userId = "";
    private String userPassword = "";
    private String defaultSender = "";
	/**
	 * This method constructs an empty email (With no Email Ids, Subject, MessageBody etc.) 
	 * @throws IOException
	 */	
    public Mail() throws Exception
    {
        this.init();
    }
	/**
	 * This method constructs an email with the given parameters set
	 * @param from 
	 * @param to
	 * @param subject 
	 * @param bodyText - Body text of the mail 
	 * @throws Exception
	 */	
	public Mail(String from, String to, String subject, String bodyText) throws Exception
	{
		this.init();
		this.setFrom(from);
		this.addRecipient(to);
		this.setSubject(subject);
		this.setBodyText(bodyText);		
	}
	
	public void setFrom(String from) throws Exception
	{
		if(this.isValid(from))
			this.message.setFrom(new InternetAddress(from));	
	}
	
	public void addRecipient(String to) throws Exception
	{
		if(this.isValid(to))
		{
			
			this.message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		}
	}
	
	public void addRecipients(String[] emailIds, String recipientType) throws Exception
	{
	    if(emailIds!=null){
			for(int index=0, n=emailIds.length; index < n; index++)
			{
				String id = emailIds[index];
				if(this.isValid(id)) 
				{
					addressVec.add(new InternetAddress(id));
					if(recipientType.equalsIgnoreCase("to"))
						this.message.addRecipient(Message.RecipientType.TO, new InternetAddress(id));
				    else if(recipientType.equalsIgnoreCase("cc")){
						this.message.addRecipient(Message.RecipientType.CC, new InternetAddress(id));
                        if(BuildConfig.DMODE)   
                            System.out.println("Carbon copy mail ids:"+message);
					}
					else if(recipientType.equalsIgnoreCase("bcc"))
						this.message.addRecipient(Message.RecipientType.BCC, new InternetAddress(id));
					else 
						throw new Exception("Attempt to resolve unknown RecipientType: " + recipientType);
				}				
			}		
	    }
	}
	
	public void setSubject(String subject) throws Exception
	{
		subject = subject.replaceAll("\n"," ");
		this.message.setSubject(subject);
	}
	
	public void setBodyText(String bodyText) throws Exception
	{
		this.messageBodyPart.setText(bodyText);
		this.multipart.addBodyPart(this.messageBodyPart);
	}
	
	// Temp ?? TODO remove
	public void setBodyTextAsHTML(String htmlBodyText) throws Exception
	{
		this.messageBodyPart.setContent(htmlBodyText, "text/html");
		this.multipart.addBodyPart(this.messageBodyPart);
	}
	
	/**
	 * This method sets the Email's address fields (from & to) and sends it along with an attachment
	 * @param from
	 * @param to
	 * @param subject
	 * @param bodyText - Body text of the mail
	 * @param bytes - byte[] of the data to be transmitted
	 * @param filename - File name with Extension
	 * @return boolean status of the operation performed
	 */	
	public boolean sendAttachment(String from, String to, String subject, String bodyText,  byte[] bytes , String filename)
	{
		try
		{
			this.setFrom(from);
			this.addRecipient(to);			
			this.setSubject(subject);
			this.setBodyText(bodyText);			
			// Part two is attachment
			
//			this.addAttachment(bytes, filename);
			
			return this.send();
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}		
		return false;
	}
	
	/**
	 * This method sets the Email's address fields (from & to) and sends it along with multiple attachments
	 * @param from
	 * @param to
	 * @param copyTo
	 * @param blackCarbonCopyTo
	 * @param subject
	 * @param bodyText - Body text of the mail
	 * @param files - a collection of pair of key:Filename & value:Data - ByteArrayInputStream
	 * @return boolean status of the operation performed
	 */
	public boolean sendMultipleAttachments(Hashtable files)
	{
		try
		{	
			//this.setBodyText(bodyText);
			
			// Remaining Parts are attachments
			
			Enumeration enumer = files.keys();
			
			String filename; 
			
			while(enumer.hasMoreElements())
			{
				filename = (String)enumer.nextElement();
				/*ByteArrayInputStream bis = (ByteArrayInputStream)();
				bytes = new byte[bis.available()];
				bis.read(bytes);*/
				File f=new File(filename);
				
				this.addAttachment((byte[])(files.get(filename)),filename);
			}
			//return this.send();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	/**
	 *  This method sets the cc and bcc fields of the Email
	 * @param cc
	 * @param bcc
	 * @return boolean status of the operation performed
	 */	
	public boolean setCarbonCopies(String[] cc, String[] bcc)
	{
		try
		{
			if(cc != null)				
				for(int index=0, n=cc.length; index < n; index++)
				{
					String id = cc[index];
					if(id.lastIndexOf(".") > id.indexOf("@")) 
					{
						message.addRecipient(Message.RecipientType.CC, new InternetAddress(id));					
					}				
				}		
			
			if(! bcc.equals(null))
				for(int index=0, n=bcc.length; index < n; index++)
				{
					String id = bcc[index];
					if(id.lastIndexOf(".") > id.indexOf("@")) 
					{
						message.addRecipient(Message.RecipientType.BCC, new InternetAddress(id));					
					}				
				}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;	
	}
	/**
	 * This method attaches the given data to the Email
	 * @param bytes - byte[] of the data to be transmitted
	 * @param filename - File name with Extension
	 * @return boolean status of the operation performed
	 */	
	public boolean addAttachment(byte[] bytes,String filename)
	{
		try
		{
			// Create second body part
			BodyPart attachment = new MimeBodyPart();
			
			// Get the attachment
			DataSource source = new ByteDataSource(bytes);
			
			// Set the data handler to the attachment
			attachment.setDataHandler(new DataHandler(source));
			
			// Set the filename
			attachment.setFileName(filename);
			attachment.setHeader("Content-ID","<"+filename+">");
			
			// Add part two
			this.multipart.addBodyPart(attachment);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * This method does the actual transmission of the Email
	 * @return boolean status of the operation performed
	 * @throws MessagingException
	 * @throws ConstellationException
	 */	
	public boolean send() throws MessagingException
	{
		// Put parts in message
		this.message.setContent(this.multipart);
		
		// Send the message
		//Transport.send(this.message);
		//TODO - revisit the code for sending message
		//URLName urlName = new URLName("SMTP","smtp.mail.yahoo.co.uk",25,null,"senthilp007","savant123");
		//SMTPTransport transport = new SMTPTransport(session,urlName);
		
		Transport transport = null;
		try
		{
		    transport = this.session.getTransport("smtp");
		    transport.addTransportListener(this);
		    this.message.setFrom(new InternetAddress(defaultSender.trim()));
		    transport.connect(smtpHostServer,userId,userPassword);
		    addresses = new InternetAddress[addressVec.size()];
		    addressVec.copyInto(addresses);
		    this.message.setContent(multipart);
		    this.message.saveChanges();
		    transport.sendMessage(this.message,addresses);
		}
		catch(Exception e)
		{
		    e.printStackTrace();
	       
		}
		
		return true;
	}	
	
	/**
	 *  This method creates a new Email with no contents
	 * @throws IOException
	 */	
	
	
	
	private void init() throws Exception
	{
		this.props = new Properties();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("ApplicationResources.properties");
		this.props.load(is);
		
		// Get session
		
		this.smtpHostServer = this.props.getProperty("mail.host");
		this.userId = this.props.getProperty("mail.user");
		this.userPassword = this.props.getProperty("mail.password");
		this.defaultSender = this.props.getProperty("mail.defaultmail.sender");
		this.session = Session.getInstance(this.props);
		// Define message
		this.message = new MimeMessage(session);
		// Create a Multipart
		this.multipart = new MimeMultipart("relative");
		// Create the message part 
		this.messageBodyPart = new MimeBodyPart();
		
	}
	
	private boolean isValid(String id)
	{
		return (id.indexOf("@")!=-1);		
	}
    /* (non-Javadoc)
     * @see javax.mail.event.TransportListener#messageDelivered(javax.mail.event.TransportEvent)
     */
    public void messageDelivered(TransportEvent transportEvent)
    {
        deliveryStatus = SUCCESSFULLYSENT;
        
    }
    /* (non-Javadoc)
     * @see javax.mail.event.TransportListener#messageNotDelivered(javax.mail.event.TransportEvent)
     */
    public void messageNotDelivered(TransportEvent transportEvent)
    {
        deliveryStatus = NOTSENT;
    }
    /* (non-Javadoc)
     * @see javax.mail.event.TransportListener#messagePartiallyDelivered(javax.mail.event.TransportEvent)
     */
    public void messagePartiallyDelivered(TransportEvent transportEvent)
    {
        deliveryStatus = PARTIALLYSENT; 
    }

    /**
     * @return Returns the deliveryStatus.
     */
    public int getDeliveryStatus()
    {
        return deliveryStatus;
    }
    /**
     * @param deliveryStatus The deliveryStatus to set.
     */
    public void setDeliveryStatus(int deliveryStatus)
    {
        this.deliveryStatus = deliveryStatus;
    }
    /**
     * @param multiPart2
     */
    public void setMultiPartContent(MimeMultipart multiPart)
    {
        this.multipart = multiPart;
    }
    /**
     * @return Returns the response.
     */
    
    public static void main(String args[]) throws Exception
    {
        String str[] = {"muralee@hayagriv.com"};
        Mail mail = new Mail();
        mail.addRecipients(str,"to");
        mail.setFrom("muralee@hayagriv.com");
        mail.setSubject("Test Mail");
        mail.setBodyText("Test Body");
        //if(BuildConfig.DMODE)
            System.out.println(mail.send()+"Mail Status");
     }

    
}

/*
*$Log: Mail.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/28 14:08:34  jvediyappan
*email ids are configured for auto run service.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:23  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/06/12 12:57:53  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.2  2007/04/28 06:57:24  jnadesan
*mail content added for no customer
*
*Revision 1.1  2007/04/28 05:20:04  jnadesan
*mail work finished
*
*
*/