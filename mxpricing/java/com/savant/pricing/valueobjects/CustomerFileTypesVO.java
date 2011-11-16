/*
 * 
 * CustomerFileTypesVO.java    Aug 7, 2007
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

package com.savant.pricing.valueobjects;

import java.io.Serializable;

/**
 * 
 */
public class CustomerFileTypesVO implements Serializable
{
    
    private int fileTypeIdentifier;
    private String fileType; 
    
    public String getFileType()
    {
        return fileType;
    }
    public int getFileTypeIdentifier()
    {
        return fileTypeIdentifier;
    }
    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }
    public void setFileTypeIdentifier(int fileTypeIdentifier)
    {
        this.fileTypeIdentifier = fileTypeIdentifier;
    }
}


/*
*$Log: CustomerFileTypesVO.java,v $
*Revision 1.2  2008/11/21 09:48:04  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:45  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/08/07 10:03:13  jnadesan
*entry for customer file upload
*
*
*/