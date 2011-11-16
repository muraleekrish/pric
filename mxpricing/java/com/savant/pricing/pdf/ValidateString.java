/*
 * 
 * ValidateString.java    Aug 21, 2007
 * 
 * Copyright (c) Savant Technologies Pvt Ltd.
 * 'Savant House' 127 Chamiers Road, Nandanam, Chennai-600 035. INDIA 
 * All rights reserved.

 * @author: jnadesan
 * @company: Savant Technologies
 * @client: MX Energy
 * @version: 
 * @Description: 
 * 
*/

package com.savant.pricing.pdf;

/**
 * 
 */
public class ValidateString
{
    public static String checkMetaCharacters(String custName)
    {
        custName = custName.replaceAll("\\\\","");
        custName = custName.replaceAll("/","");
        custName = custName.replaceAll(":","");
        custName = custName.replaceAll("\\*","");
        custName = custName.replaceAll("\\?","");
        custName = custName.replaceAll("\"","");
        custName = custName.replaceAll("<","");
        custName = custName.replaceAll(">","");
        custName = custName.replaceAll("\\|","");
        return custName;
    }
}


/*
*$Log: ValidateString.java,v $
*Revision 1.2  2008/11/21 09:47:11  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/08/23 07:38:06  jnadesan
*file name validated
*
*
*/