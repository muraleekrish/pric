/*
 * 
 * PriceMatrixPDFSummaryDataSource.java    May 29, 2007
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 */
public class PriceMatrixPDFSummaryDataSource  extends JRAbstractBeanDataSourceProvider
{

    List lstrecords = new ArrayList();
    public PriceMatrixPDFSummaryDataSource() {
        super(PriceMatrixPDFSummaryChartDetails.class);
    }
    
    public JRDataSource create(JasperReport arg0) throws JRException
    {  
        /*List lst = new ArrayList();
        List lstTdsp = new ArrayList();
        List lstmain = new ArrayList();
        lst.add(new PriceMatrixPDFSummaryDetails("CenterPoint","12",0.7899,0.5469,0.32648,0.4568,0.4568));
        lst.add(new PriceMatrixPDFSummaryDetails("CenterPoint","24",0.7899,0.5469,0.32648,0.4568,0.4568));
        lst.add(new PriceMatrixPDFSummaryDetails("CenterPoint","36",0.7899,0.5469,0.32648,0.4568,0.4568));
        lst.add(new PriceMatrixPDFSummaryDetails("CenterPoint","18",0.7899,0.5469,0.32648,0.4568,0.4568));
        lst.add(new PriceMatrixPDFSummaryDetails("CenterPoint","6",0.7899,0.5469,0.32648,0.4568,0.4568));
        lstTdsp.add(new PriceMatrixPDFSummaryChartDetails("Center Point", "chartname.gif",lst));
        lst.clear();
        lst.add(new PriceMatrixPDFSummaryDetails("CP&L","12",0.7899,0.5469,0.32648,0.4568,0.4568));
        lst.add(new PriceMatrixPDFSummaryDetails("CP&L",24,0.7899,0.5469,0.32648,0.4568,0.4568,0.4568));
        lstTdsp.add(new PriceMatrixPDFSummaryChartDetails("CP&L", "chartname.gif",lst));
        lstrecords.addAll(lstTdsp);
        lstmain.add(new PriceMatrixPDFSummaryChartDetails("", "",lstTdsp) ); 
        List term = new ArrayList();
        term.add(new PriceMatrixPDFSummaryDetails("Houston","BusMedLF",0.098645,0.5468436,0.7849));
        term.add(new PriceMatrixPDFSummaryDetails("Houston","BusLOWLF",0.0986,0.5463468,0.7849));
        term.add(new PriceMatrixPDFSummaryDetails("Houston","BusHiLF",0.0986,0.546358,0.7849));
        term.add(new PriceMatrixPDFSummaryDetails("Houston","BusMEDLF",0.0982356,0.5235468,0.7849));
        List term1 = new ArrayList();
        
        term1.add(new PriceMatrixPDFSummaryDetails("West","BusMedLF",0.098645,0.5468436,0.7849));
        term1.add(new PriceMatrixPDFSummaryDetails("West","BusLOWLF",0.0986,0.5463468,0.7849));
        term1.add(new PriceMatrixPDFSummaryDetails("West","BusHiLF",0.0986,0.546358,0.7849));
        term1.add(new PriceMatrixPDFSummaryDetails("West","BusMEDLF",0.0982356,0.5235468,0.7849));
        
        lstrecords.add(new PriceMatrixPDFSummaryChartDetails("Zone","E:/pricingdata/jasper/Zone.jpeg",term));
        lstrecords.add(new PriceMatrixPDFSummaryChartDetails("Zone1","E:/pricingdata/jasper/Zone.jpeg",term));*/
        return new JRBeanCollectionDataSource(lstrecords);
    }
    
    public JRDataSource createDataSource(JasperReport report,Collection coll) throws JRException
    {
        lstrecords.addAll(coll);
        return this.create(report);
    }
    
    public void dispose(JRDataSource arg0) throws JRException
    {
        
    }
}


/*
*$Log: PriceMatrixPDFSummaryDataSource.java,v $
*Revision 1.4  2009/01/27 05:47:57  tannamalai
*changes - according to MX server
*
*Revision 1.3  2008/11/21 09:46:48  jvediyappan
*Trieagle changed as MXEnergy.
*
*Revision 1.2  2008/01/23 08:35:13  tannamalai
*jasper reports changes
*
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.3  2007/11/28 14:11:27  jnadesan
*path added
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
*Revision 1.2  2007/09/03 15:05:46  jnadesan
*imports organized
*
*Revision 1.1  2007/08/29 13:37:51  jnadesan
*initail commit
*
*
*/