/*
 * Created on Apr 6, 2007
 *
 * ClassName	:  	DLFCODEVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.calculation.valueobjects;

import java.io.Serializable;
import java.util.Date;

import com.savant.pricing.valueobjects.TDSPVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DLFCodeVO implements Serializable
{
    private int dlfCodeIdentifier;
    private TDSPVO tdsp;
    private String dflName;
    private String description;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private boolean valid;
    /*private Set dlfValues = new HashSet();
    public DLFCodeVO()
    {
    }
    
    public float getDlf()
    {
        Iterator itr = dlfValues.iterator();
        float dlf = 0;
        if(itr.hasNext())
        {
            DLFVO objDLFVO = (DLFVO)itr.next();
            dlf = objDLFVO.getDlf();
        }
        return dlf;
    }
    
    public Set getDlfValues()
    {
        return dlfValues;
    }
    public void setDlfValues(Set dlfValues)
    {
        this.dlfValues = dlfValues;
    }
    */
    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    public Date getCreatedDate()
    {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public String getDflName()
    {
        return dflName;
    }
    public void setDflName(String dflName)
    {
        this.dflName = dflName;
    }
    public int getDlfCodeIdentifier()
    {
        return dlfCodeIdentifier;
    }
    public void setDlfCodeIdentifier(int dlfCodeIdentifier)
    {
        this.dlfCodeIdentifier = dlfCodeIdentifier;
    }
    public String getModifiedBy()
    {
        return modifiedBy;
    }
    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }
    public Date getModifiedDate()
    {
        return modifiedDate;
    }
    public void setModifiedDate(Date modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }
    public TDSPVO getTdsp()
    {
        return tdsp;
    }
    public void setTdsp(TDSPVO tdsp)
    {
        this.tdsp = tdsp;
    }
    public boolean isValid()
    {
        return valid;
    }
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
}

/*
*$Log: DLFCodeVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/04/17 06:25:10  kduraisamy
*dlfcode set removed from TDSP.
*
*Revision 1.2  2007/04/09 10:18:30  kduraisamy
*Tdsp wise dlf and values added.
*
*Revision 1.1  2007/04/06 12:32:19  kduraisamy
*TLF AND DLF COMPLETED.
*
*
*/