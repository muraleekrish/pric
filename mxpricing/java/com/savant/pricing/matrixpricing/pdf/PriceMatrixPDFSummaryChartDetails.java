/*
 * 
 * PriceMatrixPDFSummaryChartDetails.java    May 29, 2007
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
public class PriceMatrixPDFSummaryChartDetails
{
    private String tdsp;
    private String fileName;
    private List listTDSPDetails;
    
    public PriceMatrixPDFSummaryChartDetails(String tdsp, String fileName, List tdspDetails)
    {
        this.tdsp = tdsp;
        this.fileName = fileName;
        this.listTDSPDetails = tdspDetails;
    }
    
    public String getFileName()
    {
        return fileName;
    }
    public List getListTDSPDetails()
    {
        return listTDSPDetails;
    }
    public String getTdsp()
    {
        return tdsp;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    public void setListTDSPDetails(List listTDSPDetails)
    {
        this.listTDSPDetails = listTDSPDetails;
    }
    public void setTdsp(String tdsp)
    {
        this.tdsp = tdsp;
    }
}


/*
*$Log: PriceMatrixPDFSummaryChartDetails.java,v $
*Revision 1.2  2008/11/21 09:46:48  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:53  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/08/29 13:37:51  jnadesan
*initail commit
*
*
*/