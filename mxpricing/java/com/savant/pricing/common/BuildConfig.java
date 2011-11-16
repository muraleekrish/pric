/*
 * Created on Feb 3, 2006
 *
 * ClassName	:  	BuildConfig.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India
 *
 */
package com.savant.pricing.common;

import org.apache.log4j.Level;



public final class BuildConfig 
{
	public static final boolean DMODE = false;
	public static Level LOGGER_LEVEL = Level.INFO;
	public static final String Env_Variable = "Development";   // Local settings
	//public static final String Env_Variable = "Testing";  	// Remote local settings
	//public static final String Env_Variable = "Production";     // Rmote production settings 
	public static final String DEV_SERVER_PATH = "\\\\db1\\";
	
}

/***
$Log: BuildConfig.java,v $
Revision 1.5  2009/01/27 05:48:30  tannamalai
changes - according to MX server

Revision 1.4  2008/12/03 10:55:10  tannamalai
URL Updated Based on Remote Environment.

Revision 1.3  2008/01/23 08:35:14  tannamalai
jasper reports changes

Revision 1.2  2007/12/14 10:15:34  jvediyappan
remote settings.

Revision 1.1  2007/12/07 06:18:44  jvediyappan
initial commit.

Revision 1.3  2007/11/30 13:40:11  jvediyappan
 settings for remote machine.

Revision 1.2  2007/11/15 06:20:13  tannamalai
*** empty log message ***

Revision 1.1  2007/10/30 05:51:56  jnadesan
Initial commit.

Revision 1.1  2007/10/26 15:19:28  jnadesan
initail MXEP commit

Revision 1.6  2007/08/31 14:50:04  sramasamy
Log message is added for log file.

Revision 1.5  2007/08/24 13:33:18  kduraisamy
calling menu item in all pages problem solved.

Revision 1.4  2007/08/09 06:05:28  jnadesan
environment variable showed in menu

Revision 1.3  2007/07/31 11:53:17  kduraisamy
log4j added.

Revision 1.2  2007/05/11 12:57:45  kduraisamy
aggregated load profile error rectified.

Revision 1.1  2007/01/20 05:25:13  srajappan
initial commit


*/