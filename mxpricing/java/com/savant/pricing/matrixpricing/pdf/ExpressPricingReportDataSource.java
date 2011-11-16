/*
 * @ ExpressPricingReportDataSource.java	Aug 27, 2007
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. 
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use it 
 * only in accordance with the terms of the license agreement you entered into with Savant Technologies Pvt Ltd.
 * 
 */
 
package com.savant.pricing.matrixpricing.pdf;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/*
 * Class description goes here.
 * 
 * @author	spandiyarajan
 * 
 */

public class ExpressPricingReportDataSource extends JRAbstractBeanDataSourceProvider
{
    ArrayList lstRecords = new ArrayList();
    
    public ExpressPricingReportDataSource(Class obj) {
        super(obj);
    }
    /**
     * 
     */
    public ExpressPricingReportDataSource()
    {
        super(PriceMatrixMainDetails.class);

    }
    public JRDataSource create(JasperReport arg0) throws JRException
    {  
       return new JRBeanCollectionDataSource(lstRecords);
    }
    
    public JRDataSource createDataSource(JasperReport report,Collection coll) throws JRException
    {
        lstRecords.addAll(coll);
        return this.create(report);
    }
    
    public void dispose(JRDataSource arg0) throws JRException
    {
        
    }
}


/*
*$Log: ExpressPricingReportDataSource.java,v $
*Revision 1.2  2008/01/29 07:02:58  tannamalai
*price matrix pdf templates changed
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
*Revision 1.4  2007/09/03 15:03:30  jnadesan
*chart showed in page
*
*Revision 1.2  2007/08/30 12:37:17  spandiyarajan
*makepdf changes
*
*Revision 1.1  2007/08/29 07:23:55  spandiyarajan
*makepdf initially commited
*
*
*/