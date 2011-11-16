/*
 * @(#) PricingException.java	Sep 26, 2007
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. 
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use it 
 * only in accordance with the terms of the license agreement you entered into with Savant Technologies Pvt Ltd.
 * 
 */
 
package com.savant.pricing.common;

/*
 * Class description goes here.
 * 
 * @author	spandiyarajan
 * 
 */

public class PricingException extends Exception
{
    private String message = "";
	private String errorCode = "";
	
	public PricingException(String message, String errorCode, Throwable rootException) 
	{
		super();
		this.message = message;
		this.errorCode = errorCode;
		this.initCause(rootException);
	}
	
	public PricingException(String message, String errorCode) 
	{
		super();
		this.message = message;
		this.errorCode = errorCode;		
	}
	
	public PricingException(String message) 
	{
		super();
		this.message = message;
	}
	
	public PricingException()
	{
		super();
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}


/*
*$Log: PricingException.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/09/27 09:08:03  spandiyarajan
*pricing exception class initilally commited
*
*
*/