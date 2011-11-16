/*
 * Created on Feb 8, 2007
 *
 * ClassName	:  	EnergyChargeNamesVO.java
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

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EnergyChargeNamesVO implements Serializable
{
    private int energyChargeIdentifier;
    private ChargeTypesVO chargeType;
    private String chargeName;
    private String description;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private boolean display;
    private boolean valid;
   
    public EnergyChargeNamesVO()
    {
    }
    
    public ChargeTypesVO getChargeType()
    {
        return chargeType;
    }
    
    public boolean isDisplay() {
        return display;
    }
    public void setDisplay(boolean display) {
        this.display = display;
    }
    public void setChargeType(ChargeTypesVO chargeType)
    {
        this.chargeType = chargeType;
    }
    public String getChargeName()
    {
        return chargeName;
    }
    public void setChargeName(String chargeName)
    {
        this.chargeName = chargeName;
    }
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
    public int getEnergyChargeIdentifier()
    {
        return energyChargeIdentifier;
    }
    public void setEnergyChargeIdentifier(int energyChargeIdentifier)
    {
        this.energyChargeIdentifier = energyChargeIdentifier;
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
*$Log: EnergyChargeNamesVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/07/12 13:02:17  jnadesan
*isdiplay option added
*
*Revision 1.1  2007/02/09 11:56:55  kduraisamy
*pricing core algorithm almost finished.
*
*
*/