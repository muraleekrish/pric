/*
 * Created on Aug 23, 2004
 */
package com.savant.pricing.mailer;


/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.activation.DataSource;

import com.savant.pricing.common.BuildConfig;

/**
 * This Class is an implementation of the Interface javax.activation.DataSource and is alomost similar to the 
 * FileDataSource except that this ByteDataSource deals with the byte[] data instead of a File in a FileSystem.  
 */

public class ByteDataSource implements DataSource, Serializable
{
	private byte[] bytes;
	String name						=	null;
	byte [] bArrContent				=	null;
 

	/**
	 * 
	 * @param bytes
	 */	
	public ByteDataSource(byte[] bytes)
	{
	    if(BuildConfig.DMODE)
	        System.out.println("ByteDataSource(byte[] bytes) called - " + bytes.length);
		this.bytes = bytes;
	//	this.inputStreamData = new ByteArrayInputStream(this.bytes);
	}
	
	/**
	 * 
	 * @param is
	 * @throws IOException
	 */
	public ByteDataSource(InputStream is) throws IOException
	{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int c;
		while ((c = is.read()) != -1)
		{
			baos.write(c);
		}
		this.bytes = baos.toByteArray();
	}

	/**
	 * Default MIME Type of any binary Data
	 */	
	public String getContentType()
	{
		return "application/octet-stream";
	}
	
	/**
	 * 
	 */		
	public InputStream getInputStream()	throws IOException
	{	
		return new ByteArrayInputStream(this.bytes);
	}
	
	/**
	 * 
	 */	
	public String getName()
	{
		return "objName"; // dummy name
	}

	public ByteArrayOutputStream getByteArrayOutputStream() throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos.write(bytes);
		return bos;	
	}
	
	public byte[] getBytes()
	{
		return this.bytes;
	}

	/**
	 * @see javax.activation.DataSource#getOutputStream()
	 */
	public OutputStream getOutputStream() throws IOException 
	{
		return getByteArrayOutputStream();
	}
}


/*
*$Log: ByteDataSource.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:23  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/06/12 12:57:53  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.1  2007/04/28 05:20:04  jnadesan
*mail work finished
*
*
*/