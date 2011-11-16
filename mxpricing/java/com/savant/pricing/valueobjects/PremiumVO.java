/*
 * Created on Jan 25, 2007
 *
 * ClassName	:  	CDRStatusVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.valueobjects;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PremiumVO implements Serializable
{
    private int premiumId;
    private String premiumType;
    private float value;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private boolean valid;
    
    public String getCreatedBy() {
        return createdBy;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public String getModifiedBy() {
        return modifiedBy;
    }
    public Date getModifiedDate() {
        return modifiedDate;
    }
    public int getPremiumId() {
        return premiumId;
    }
    public String getPremiumType() {
        return premiumType;
    }
    public boolean isValid() {
        return valid;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    public void setPremiumId(int premiumId) {
        this.premiumId = premiumId;
    }
    public void setPremiumType(String premiumType) {
        this.premiumType = premiumType;
    }
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public PremiumVO()
    {
        
    }
}

/*
*$Log: PremiumVO.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:45  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/05/18 09:30:00  jnadesan
*Float is changed into float
*
*Revision 1.1  2007/05/18 03:38:08  jnadesan
*initial commit
*
*Revision 1.4  2007/02/12 06:04:04  kduraisamy
*unwanted set Removed.
*
*Revision 1.3  2007/01/30 07:29:27  kduraisamy
*CDRStatus is changed to cdrStatus.
*
*Revision 1.2  2007/01/27 10:15:45  kduraisamy
*mapping problem resolved.
*
*Revision 1.1  2007/01/25 13:08:47  kduraisamy
*initial commit.
*
*
*/