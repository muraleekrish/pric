/*
 * 
 * CustomerFilesVO.java    Aug 7, 2007
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
import java.util.Date;

/**
 * 
 */
public class CustomerFilesVO implements Serializable
{
    private ProspectiveCustomerVO prospectiveCust;
    private String fileName;
    private CustomerFileTypesVO fileType;
    private Date createdDate;
    private String createdBy;
    private String description;
    
    public Date getCreatedDate()
    {
        return createdDate;
    }
    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    public String getDescription()
    {
        return description;
    }
   
    public String getFileName()
    {
        return fileName;
    }
    public CustomerFileTypesVO getFileType()
    {
        return fileType;
    }
    public ProspectiveCustomerVO getProspectiveCust()
    {
        return prospectiveCust;
    }
    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    public void setFileType(CustomerFileTypesVO fileType)
    {
        this.fileType = fileType;
    }
    public void setProspectiveCust(ProspectiveCustomerVO prospectiveCust)
    {
        this.prospectiveCust = prospectiveCust;
    }
}


/*
*$Log: CustomerFilesVO.java,v $
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
*Revision 1.2  2007/08/16 12:54:06  jnadesan
*descriptiopn added
*
*Revision 1.1  2007/08/14 13:16:26  jnadesan
*procedure created to upload ,delete files
*
*Revision 1.1  2007/08/07 10:03:13  jnadesan
*entry for customer file upload
*
*
*/