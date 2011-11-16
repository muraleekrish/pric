/*
 * Created on Apr 9, 2007
 * 
 * Class Name ContractDataSource.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pdf;

import java.util.ArrayList;
import java.util.Collection;

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
public class ContractDataSource extends JRAbstractBeanDataSourceProvider {
    
    
    
    /**
     * @param arg0
     */
    ArrayList listCollection = new ArrayList();
    public ContractDataSource(Class obj) {
        super(obj);
    }
    
    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSourceProvider#create(net.sf.jasperreports.engine.JasperReport)
     */
    
    public JRDataSource create(JasperReport arg0) throws JRException {
        // TODO Auto-generated method stub
       /* ReportsDAO objReportsDAO = new ReportsDAO();
        subreport.add(new ContractSubReportDetails());
        esiIdReport.add(new ContractEsiidDetails("10443720003004617","04303 MARIPOSA DR","TXU"));
        esiIdReport.add(new ContractEsiidDetails("10443720003013328","04311 CALETA","TXU"));
        esiIdReport.add(new ContractEsiidDetails("10443720003013700","04335 CALETA","TXU"));
        esiIdReport.add(new ContractEsiidDetails("10443720003014785","04312 CALETA","TXU"));
        esiIdReport.add(new ContractEsiidDetails("10443720003004617","04303 MARIPOSA DR","TXU"));
        listCollection.add(new ContractSampleDetails(objReportsDAO.getParamValue(1,"Address"),objReportsDAO.getParamValue(1,"Pricing"),objReportsDAO.getParamValue(1,"Term"),objReportsDAO.getParamValue(1,"Billing Fees"),objReportsDAO.getParamValue(1,"Billing Agreement"),objReportsDAO.getParamValue(1,"Switch Authorization And Enrollment"),"Savant Tech",24,9,subreport,esiIdReport));
        //listCollection.add(new ContractSampleDetails("Savant","savant","tech","techh","aaaa","asas","",12,(float)1.023,new ContractSubReportDetails()));*/
        return new JRBeanCollectionDataSource(listCollection);
    }
    
    public JRDataSource createDataSource(JasperReport report,Collection coll) throws JRException
    {
        listCollection.addAll(coll);
        return this.create(report);
    }
    
    /* (non-Javadoc)
     * @see net.sf.jasperreports.engine.JRDataSourceProvider#dispose(net.sf.jasperreports.engine.JRDataSource)
     */
    public void dispose(JRDataSource arg0) throws JRException {
        // TODO Auto-generated method stub
        
    }
    
    
    
    
}


/*
*$Log: ContractDataSource.java,v $
*Revision 1.1  2007/12/07 06:18:35  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:48  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/05/05 11:05:07  jnadesan
*bundle and unbundle contracts are finished
*
*Revision 1.2  2007/04/18 03:56:26  kduraisamy
*imports organized.
*
*Revision 1.1  2007/04/10 09:53:43  jnadesan
*DataSource for fixed Contract
*
*
*/