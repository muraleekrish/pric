/*
 * Created on Apr 5, 2007
 *
 * ClassName	:  	CPEMultipleDataSource.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.pdf;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CPEMultipleDataSource extends JRAbstractBeanDataSourceProvider
{

    /**
     * 
     */
    List vecDetails = new ArrayList();
    List annaualDetails = new ArrayList();
    List termdetails = new ArrayList();
    public CPEMultipleDataSource(Class obj)
    {
        super(obj);
        // TODO Auto-generated constructor stub
    }
    /*public CPEMultipleDataSource() 
    {
        super(CPEMultipleMainDetails.class);
        // TODO Auto-generated constructor stub
    }*/
    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSourceProvider#create(net.sf.jasperreports.engine.JasperReport)
     */
    public JRDataSource create(JasperReport arg0) throws JRException
    {
        // TODO Auto-generated method stub
       /* termdetails.add(new CPEFARSubDetails(1,1,1111,(float)1.03,1,333,1));
        termdetails.add(new CPEFARSubDetails(34,2,222,(float)1.03,1,333,1));
        termdetails.add(new CPEFARSubDetails(35,2,3333,(float)1.03,1,333,1));
        termdetails.add(new CPEFARSubDetails(2,2,1111,(float)1.03,1,333,1));
        termdetails.add(new CPEFARSubDetails(3,2,222,(float)1.03,1,333,1));
        termdetails.add(new CPEFARSubDetails(4,2,3333,(float)1.03,1,333,1));
        */
       /* termdetails.add(new CPEMultipleSubDetails(5,1111,(float)1.03,12342,333));
        termdetails.add(new CPEMultipleSubDetails(6,222,(float)1.03,12342,333));
        termdetails.add(new CPEMultipleSubDetails(7,3333,(float)1.03,12342,333));
        
        termdetails.add(new CPEMultipleSubDetails(8,1111,(float)1.03,12342,333));
        termdetails.add(new CPEMultipleSubDetails(9,222,(float)1.03,12342,333));
        termdetails.add(new CPEMultipleSubDetails(10,3333,(float)1.03,12342,333));
        
        termdetails.add(new CPEMultipleSubDetails(11,1111,(float)1.03,12342,333));
        termdetails.add(new CPEMultipleSubDetails(12,222,(float)1.03,12342,333));
        termdetails.add(new CPEMultipleSubDetails(13,3333,(float)1.03,12342,333));
        
        vecDetails.add(new CPEMultipleMainDetails("Savant",(float)34.4,1234,1,7,new Date(),new Date(),termdetails));*/
        /*annaualDetails.add(new AnnualSummaryDetails((float)1234.0, "jan"));
        annaualDetails.add(new AnnualSummaryDetails((float)1567.0, "feb"));
        annaualDetails.add(new AnnualSummaryDetails((float)8435.0, "mar"));
        
        termdetails.add(new PriceComparisionDetails("Components","12","24","36","48","60"));
        termdetails.add(new PriceComparisionDetails("Energy (kWh)","77239","154063","","",""));
        termdetails.add(new PriceComparisionDetails("Energy ($)","7937.24","15208.97","","",""));
        termdetails.add(new PriceComparisionDetails("Energy ($/kWh)","0.1028","0.0987","","",""));
        CPEMultipleMainDetails objCPEMultipleMainDetails = new CPEMultipleMainDetails("TEST",0.0f,0.0f,1,0.0f,null,null,0.0f,termdetails,annaualDetails);
        vecDetails.add(objCPEMultipleMainDetails);*/
        return new JRBeanCollectionDataSource(vecDetails);
    }
    
    public JRDataSource callcreate(JasperReport arg0,List vecObj) throws JRException
    {
        // TODO Auto-generated method stub
        vecDetails.addAll(vecObj);
        return this.create(arg0);
    }

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSourceProvider#dispose(net.sf.jasperreports.engine.JRDataSource)
     */
    public void dispose(JRDataSource arg0) throws JRException
    {
        // TODO Auto-generated method stub
        
    }
   
}

/*
*$Log: CPEMultipleDataSource.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/22 05:27:09  spandiyarajan
*PDF generation method changed
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/05/21 06:03:05  jnadesan
*resources for cpe2 for FAR product
*
*Revision 1.4  2007/04/17 15:16:59  jnadesan
*unwanted lines removed
*
*Revision 1.3  2007/04/12 13:58:09  kduraisamy
*unwanted println commented.
*
*Revision 1.2  2007/04/06 03:53:53  jnadesan
*Datasource files for pdf creation
*
*Revision 1.1  2007/04/05 11:47:46  kduraisamy
*resources forcpe multiple term
*
*
*/