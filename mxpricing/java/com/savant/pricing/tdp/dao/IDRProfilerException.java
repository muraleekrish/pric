/*
 * Created on Feb 7, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.tdp.dao;

/**
 * @author Karthikeyan Chellamuthu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IDRProfilerException extends Exception
{
	private String message = "";
	private String errorCode = "";	

	
    /**
     * @param message
     * @param errorCode
     */
    public IDRProfilerException(String message, String errorCode) {
        super();
        this.message = message;
        this.errorCode = errorCode;
    }
    
    /**
     * 
     */
    public IDRProfilerException() 
    {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @param message
     */
    public IDRProfilerException(String message) {
        super(message);
        this.message = message;
    }
    /**
     * @param message
     * @param cause
     */
    public IDRProfilerException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
    /**
     * @param cause
     */
    public IDRProfilerException(Throwable cause) 
    {
        super(cause);
    }
}
