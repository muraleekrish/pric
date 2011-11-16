/*
 * Created on Mar 31, 2007
 * 
 * Class Name CustomDataSourceProvider.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pdf;

import java.util.ArrayList;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CustomDataSourceProvider extends JRAbstractBeanDataSourceProvider 
{
    ArrayList collection = new ArrayList();
    public CustomDataSourceProvider() 
    {
        super(CPEFixedDetails.class);
    }
    
    public JRDataSource create(JasperReport report) throws JRException {
        return new JRBeanCollectionDataSource(collection);
    }
    public JRDataSource callcretae( JasperReport report,Object obj) throws JRException
    {
        //collection.add(new CPEFixedDetails("Savant",0,123,"jan 2007","dsf"));
        collection.add(obj);
        return this.create(report);
    }
    
    
    public void dispose(JRDataSource dataSource)throws JRException {
        // nothing to dispose
    }
}


/*
*$Log: CustomDataSourceProvider.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/04/10 09:53:43  jnadesan
*DataSource for fixed Contract
*
*Revision 1.2  2007/04/06 03:53:53  jnadesan
*Datasource files for pdf creation
*
*Revision 1.1  2007/04/02 11:21:12  jnadesan
*pdf added
*
*
*/