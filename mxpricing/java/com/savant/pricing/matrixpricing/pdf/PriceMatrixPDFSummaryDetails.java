/*
 * 
 * PriceMatrixPDFSummaryDetails.java    May 29, 2007
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

/**
 * 
 */
public class PriceMatrixPDFSummaryDetails
{
    private String zoneName = "";
    private String profile;
    private double term12;
    private double term24;
    private double term36;
    private double term6;
    private double term18;

    /**
     * 
     */
    public PriceMatrixPDFSummaryDetails()
    {
        // TODO Auto-generated constructor stub
    }
    public PriceMatrixPDFSummaryDetails(String zoneName, String profile,
            double term12, double term24, double term36,double term18,double term6)
    {
        this.zoneName = zoneName;
        this.profile = profile;
        this.term12 = term12;
        this.term24 = term24;
        this.term36 = term36;
        this.term6 = term6;
        this.term18 = term18;
    }

    
    public String getProfile()
    {
        return profile;
    }

    public double getTerm12()
    {
        return term12;
    }

    public double getTerm24()
    {
        return term24;
    }

    public double getTerm36()
    {
        return term36;
    }

    public void setProfile(String profile)
    {
        this.profile = profile;
    }

    public String getZoneName()
    {
        return zoneName;
    }

    public void setZoneName(String zoneName)
    {
        this.zoneName = zoneName;
    }

    public void setTerm12(double term12)
    {
        this.term12 = term12;
    }

    public void setTerm24(double term24)
    {
        this.term24 = term24;
    }

    public void setTerm36(double term36)
    {
        this.term36 = term36;
    }
    
    public double getTerm18()
    {
        return term18;
    }
    public void setTerm18(double term18)
    {
        this.term18 = term18;
    }
    public double getTerm6()
    {
        return term6;
    }
    public void setTerm6(double term6)
    {
        this.term6 = term6;
    }
}
/*
 *$Log: PriceMatrixPDFSummaryDetails.java,v $
 *Revision 1.3  2008/11/21 09:46:48  jvediyappan
 *Trieagle changed as MXEnergy.
 *
 *Revision 1.2  2008/01/23 08:35:13  tannamalai
 *jasper reports changes
 *
 *Revision 1.1  2007/12/07 06:18:54  jvediyappan
 *initial commit.
 *
 *Revision 1.2  2007/11/28 13:05:51  jnadesan
 *changes based on zone wise
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