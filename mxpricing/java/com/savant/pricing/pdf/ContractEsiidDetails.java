/*
 * Created on Apr 9, 2007
 * 
 * Class Name ContractEsiidDetails.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pdf;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractEsiidDetails {
    private String esiId = "";
    private String serviceAddress = "";
    private String tdsp = "";
    
    /**
     * 
     */
    public ContractEsiidDetails(String esiId,String serviceAddress,String tdsp) {
        this.esiId = esiId;
        this.serviceAddress = serviceAddress;
        this.tdsp = tdsp;
    }
    
    public String getEsiId() {
        return esiId;
    }
    public String getServiceAddress() {
        return serviceAddress;
    }
    public String getTdsp() {
        return tdsp;
    }
    public void setEsiId(String esiId) {
        this.esiId = esiId;
    }
    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
    public void setTdsp(String tdsp) {
        this.tdsp = tdsp;
    }
}


/*
*$Log: ContractEsiidDetails.java,v $
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