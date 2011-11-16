/*
 * Created on Mar 30, 2007
 *
 * ClassName	:  	SeasonsVO.java
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
import java.util.List;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SeasonsVO implements Serializable
{

    public SeasonsVO()
    {
    }
    private int seasonId;
    private String seasonName;
    private	String createdBy;
    private	Date createdDate;
    private	String modifiedBy;
    private	Date modifiedDate;
    private boolean valid;
    private List listMonths = null;
    
    public List getListMonths()
    {
        return listMonths;
    }
    public void setListMonths(List listMonths)
    {
        this.listMonths = listMonths;
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
    public int getSeasonId()
    {
        return seasonId;
    }
    public void setSeasonId(int seasonId)
    {
        this.seasonId = seasonId;
    }
    public String getSeasonName()
    {
        return seasonName;
    }
    public void setSeasonName(String seasonName)
    {
        this.seasonName = seasonName;
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
*$Log: SeasonsVO.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:17  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/04/19 13:27:43  kduraisamy
*Set is changed to List.
*
*Revision 1.2  2007/04/08 12:11:18  kduraisamy
*seasonMonth set added.
*
*Revision 1.1  2007/03/31 06:22:23  kduraisamy
*initial commit.
*
*
*/