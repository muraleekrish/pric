/*
 * Created on Apr 5, 2007
 *
 * ClassName	:  	CPEMultipleTermDataSource.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.pdf;

import java.util.Vector;

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
public class CPEMultipleTermDataSource extends JRAbstractBeanDataSourceProvider
{

    /**
     * 
     */
    Vector termdetails = new Vector();
    public CPEMultipleTermDataSource()
    {
        super(MxContrcatADetails.class);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSourceProvider#create(net.sf.jasperreports.engine.JasperReport)
     */
    public JRDataSource create(JasperReport arg0) throws JRException
    {
        // TODO Auto-generated method stub
       // termdetails.add(new CPEMultipleSubDetails(1,1111,(float)1.03,12342,333));
        //termdetails.add(new CPEMultipleSubDetails(34,222,(float)1.03,12342,333));
        /*termdetails.add(new CPEFARSubDetails(1,1,1111,(float)1.03,1,333,1));
        termdetails.add(new CPEFARSubDetails(34,2,222,(float)1.03,1,333,1));
        termdetails.add(new CPEFARSubDetails(35,2,3333,(float)1.03,1,333,1));
        termdetails.add(new CPEFARSubDetails(2,2,1111,(float)1.03,1,333,1));
        termdetails.add(new CPEFARSubDetails(3,2,222,(float)1.03,1,333,1));
        termdetails.add(new CPEFARSubDetails(4,2,3333,(float)1.03,1,333,1));
        */
        return new JRBeanCollectionDataSource(termdetails);
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
*$Log: CPEMultipleTermDataSource.java,v $
*Revision 1.2  2008/02/27 10:32:10  tannamalai
*enerygy diff added to mcpe
*
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/04/17 15:17:40  jnadesan
*CPE And Contrcat details for FAR
*
*Revision 1.2  2007/04/10 09:53:43  jnadesan
*DataSource for fixed Contract
*
*Revision 1.1  2007/04/05 11:47:46  kduraisamy
*resources forcpe multiple term
*
*
*/