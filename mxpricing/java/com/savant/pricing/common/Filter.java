/*
 * Created on Oct 30, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savant.pricing.common;

import java.io.Serializable;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Filter implements Serializable 
{
	private String fieldName;
	private String fieldValue;
	private String specialFunction;
	private String fieldType;
	
    public String getFieldType()
    {
        return fieldType;
    }
    public void setFieldType(String fieldType)
    {
        this.fieldType = fieldType;
    }
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}
	
	public String getFieldName()
	{
		return this.fieldName;
	}
	
	public void setFieldValue(String fieldValue)
	{
		this.fieldValue = fieldValue;
	}
	
	public String getFieldValue()
	{
		return this.fieldValue;
	}
	
	public void setSpecialFunction(String specialFunction)
	{
		this.specialFunction = specialFunction;
	}
	
	public String getSpecialFunction()
	{
		return this.specialFunction;
	}
}

/***
$Log: Filter.java,v $
Revision 1.1  2007/12/07 06:18:44  jvediyappan
initial commit.

Revision 1.1  2007/10/30 05:51:56  jnadesan
Initial commit.

Revision 1.1  2007/10/26 15:19:28  jnadesan
initail MXEP commit

Revision 1.2  2007/02/01 10:35:27  kduraisamy
field type added.

Revision 1.1  2007/01/24 11:00:00  srajappan
initial commit

Revision 1.2  2004/11/06 07:43:14  sduraisamy
Log inclusion for Filter,DBConnection,DBNull,SQLMaster,DateUtil,Debug and LoggerOutput

***/