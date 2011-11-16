/*
 * Created on Apr 26, 2006
 *
 * ClassName	:  	FilterHandler.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;



/**
 * @author kduraisamy
 *
 */
public class FilterHandler
{
    public Vector setFormDetails( String fieldName,String fieldValue, String uidSelected, Vector vecDetails)
    {
  		Filter temp = new Filter();
  		temp.setFieldName(fieldName);
  		temp.setFieldValue(fieldValue.trim());
  		String specialFunction = "";
  		if(uidSelected.equals("0"))
		{
			specialFunction = HibernateUtil.STARTS_WITH;
		}
		else if(uidSelected.equals("1"))
		{
			specialFunction = HibernateUtil.ENDS_WITH;
		}
		else if(uidSelected.equals("2"))
		{
			specialFunction = HibernateUtil.EXACTLY;
		}
		else if(uidSelected.equals("3"))
		{
			specialFunction = HibernateUtil.ANYWHERE;
		}
		temp.setSpecialFunction(specialFunction);
		vecDetails.add(temp);
  		return vecDetails;
    }
    public Vector setFormDateDetails(String fromDate, String toDate, String fieldName, Vector vecDetails) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat sf = new SimpleDateFormat("dd-MMM-yyyy");
    	String proDate = sf.format(sdf.parse(fromDate))+"$"+sf.format(sdf.parse(toDate));
    	Filter temp = new Filter();
    	temp.setFieldName(fieldName);
    	temp.setFieldValue(proDate.trim());
    	temp.setSpecialFunction("DateBetween");
    	vecDetails.add(temp);
    	return vecDetails;
    }
    
  
    
}

/*
*$Log: FilterHandler.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/04/26 06:26:07  jnadesan
*all fields are trimed before searching
*
*Revision 1.3  2007/03/24 08:05:42  rraman
*exact mapping to filters made
*
*Revision 1.2  2007/01/31 12:08:42  srajappan
*filter handler class initial commit
*
*Revision 1.1  2007/01/30 12:10:14  kduraisamy
*filter handler method initial commit
*
*Revision 1.2  2006/04/27 06:37:04  kduraisamy
*setFormDateDetails() method added.
*
*Revision 1.1  2006/04/26 12:04:07  kduraisamy
*initial commit.
*
*
*/