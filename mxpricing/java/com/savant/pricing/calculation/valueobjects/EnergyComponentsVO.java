/*
 * Created on Jan 30, 2008
 *
 * ClassName	:  	EnergyComponentsVO.java
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

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EnergyComponentsVO implements Serializable
{

    private int engCompId;
    private String energyComponents;
    
    

    public int getEngCompId()
    {
        return engCompId;
    }
    public void setEngCompId(int engCompId)
    {
        this.engCompId = engCompId;
    }

    public String getEnergyComponents()
    {
        return energyComponents;
    }
    public void setEnergyComponents(String energyComponents)
    {
        this.energyComponents = energyComponents;
    }
}

/*
*$Log: EnergyComponentsVO.java,v $
*Revision 1.1  2008/01/30 13:42:42  tannamalai
*value objects added for energy components
*
*
*/