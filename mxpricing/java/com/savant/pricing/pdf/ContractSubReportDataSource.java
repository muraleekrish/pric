/*
 * Created on Apr 9, 2007
 * 
 * Class Name ContractSubReportDataSource.java
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
public class ContractSubReportDataSource extends JRAbstractBeanDataSourceProvider{

    /**
     * 
     */
    ArrayList lst = new ArrayList();
    public ContractSubReportDataSource() {
        super(ContractEsiidDetails.class);
        // TODO Auto-generated constructor stub
    }
    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSourceProvider#create(net.sf.jasperreports.engine.JasperReport)
     */
    public JRDataSource create(JasperReport arg0) throws JRException {
        // TODO Auto-generated method stub
        lst.add(new ContractEsiidDetails("10443720003004617","04303 MARIPOSA DR","TXU"));
        lst.add(new ContractEsiidDetails("10443720003013328","04311 CALETA","TXU"));
        lst.add(new ContractEsiidDetails("10443720003013700","04335 CALETA","TXU"));
        lst.add(new ContractEsiidDetails("10443720003014785","04312 CALETA","TXU"));
        lst.add(new ContractEsiidDetails("10443720003004617","04303 MARIPOSA DR","TXU"));
        return new JRBeanCollectionDataSource(lst);
    }

    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSourceProvider#dispose(net.sf.jasperreports.engine.JRDataSource)
     */
    public void dispose(JRDataSource arg0) throws JRException {
        // TODO Auto-generated method stub
        
    }

    
}


/*
*$Log: ContractSubReportDataSource.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.1  2007/04/10 09:53:43  jnadesan
*DataSource for fixed Contract
*
*
*/