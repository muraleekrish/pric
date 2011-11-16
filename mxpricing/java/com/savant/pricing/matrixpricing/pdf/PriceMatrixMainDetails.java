/*
 * 
 * PriceMatrixMainDetails.java    Nov 28, 2007
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

package com.savant.pricing.matrixpricing.pdf;

import java.util.List;

/**
 * 
 */
public class PriceMatrixMainDetails
{
    private List lstTdspDetails = null;
    private List lstTdspDetails2 = null;
    private String marketCommentry = null;
    private String disc = null;
    
    
    public String getDisc()
    {
        return disc;
    }
    public void setDisc(String disc)
    {
        this.disc = disc;
    }
    public List getLstTdspDetails()
    {
        return lstTdspDetails;
    }
    public String getMarketCommentry()
    {
        return marketCommentry;
    }
    public void setLstTdspDetails(List lstTdspDetails)
    {
        this.lstTdspDetails = lstTdspDetails;
    }
    public void setMarketCommentry(String marketCommentry)
    {
        this.marketCommentry = marketCommentry;
    }
    
    public List getLstTdspDetails2()
    {
        return lstTdspDetails2;
    }
    public void setLstTdspDetails2(List lstTdspDetails2)
    {
        this.lstTdspDetails2 = lstTdspDetails2;
    }
}


/*
*$Log: PriceMatrixMainDetails.java,v $
*Revision 1.4  2008/11/21 09:46:48  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.3  2008/01/29 07:02:58  tannamalai
*price matrix pdf templates changed
*
*Revision 1.2  2008/01/23 08:35:13  tannamalai
*jasper reports changes
*
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/11/28 13:05:23  jnadesan
*initial commit
*
*
*/