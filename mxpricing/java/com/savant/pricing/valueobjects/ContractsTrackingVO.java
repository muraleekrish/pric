/*
 * Created on May 2, 2007
 *
 * ClassName	:  	ContractsTrackingVO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.valueobjects;

import java.io.Serializable;
import java.util.Date;
import com.savant.pricing.calculation.valueobjects.ReportsTemplateHeaderVO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContractsTrackingVO implements Serializable
{
    public ContractsTrackingVO()
    {
    }
    private ContractsVO contractRef;
    private CustomerStatusVO customerStatus;
    private String contractTrackingIdentifier;
    private Date proposalDate;
    private Date expiryDate;
    private Date contractStartDate;
    private Date contractEndDate;
    private int cmsContractTypeId;
    private int cmsContractStatusId;
    private int cmsMXenergyRateClassId;
    private ReportsTemplateHeaderVO reportIdentifier;
    private String reportCode;
    
    public int getCmsContractStatusId()
    {
        return cmsContractStatusId;
    }
    public int getCmsMXenergyRateClassId()
    {
        return cmsMXenergyRateClassId;
    }
    public void setCmsContractStatusId(int cmsContractStatusId)
    {
        this.cmsContractStatusId = cmsContractStatusId;
    }
    public void setCmsMXenergyRateClassId(int cmsMXenergyRateClassId)
    {
        this.cmsMXenergyRateClassId = cmsMXenergyRateClassId;
    }
    public String getReportCode()
    {
        return reportCode;
    }
    public ReportsTemplateHeaderVO getReportIdentifier()
    {
        return reportIdentifier;
    }
    public void setReportCode(String reportCode)
    {
        this.reportCode = reportCode;
    }
    public void setReportIdentifier(ReportsTemplateHeaderVO reportIdentifier)
    {
        this.reportIdentifier = reportIdentifier;
    }
    public int getCmsContractTypeId()
    {
        return cmsContractTypeId;
    }
    public void setCmsContractTypeId(int cmsContractTypeId)
    {
        this.cmsContractTypeId = cmsContractTypeId;
    }
    public ContractsVO getContractRef()
    {
        return contractRef;
    }
    
    public Date getContractEndDate()
    {
        return contractEndDate;
    }
    public void setContractEndDate(Date contractEndDate)
    {
        this.contractEndDate = contractEndDate;
    }
    public Date getContractStartDate()
    {
        return contractStartDate;
    }
    public void setContractStartDate(Date contractStartDate)
    {
        this.contractStartDate = contractStartDate;
    }
    public Date getExpiryDate()
    {
        return expiryDate;
    }
    public void setExpiryDate(Date expiryDate)
    {
        this.expiryDate = expiryDate;
    }
    public Date getProposalDate()
    {
        return proposalDate;
    }
    public void setProposalDate(Date proposalDate)
    {
        this.proposalDate = proposalDate;
    }
    public void setContractRef(ContractsVO contractRef)
    {
        this.contractRef = contractRef;
    }
    public String getContractTrackingIdentifier()
    {
        return contractTrackingIdentifier;
    }
    public void setContractTrackingIdentifier(String contractTrackingIdentifier)
    {
        this.contractTrackingIdentifier = contractTrackingIdentifier;
    }
    public CustomerStatusVO getCustomerStatus()
    {
        return customerStatus;
    }
    public void setCustomerStatus(CustomerStatusVO customerStatus)
    {
        this.customerStatus = customerStatus;
    }
}

/*
*$Log: ContractsTrackingVO.java,v $
*Revision 1.2  2007/12/20 10:08:01  tannamalai
*trieagle name removed
*
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.3  2007/11/26 17:04:22  jnadesan
*server startup problem solved
*
*Revision 1.1  2007/10/30 05:51:45  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/06/04 05:03:01  kduraisamy
*getAllTriEagleRateClass() added.
*
*Revision 1.4  2007/06/01 08:53:41  kduraisamy
*cmsContractType Id and status id added in pricing table.
*
*Revision 1.3  2007/05/26 07:06:24  kduraisamy
*contract start and contract end date fields added.
*
*Revision 1.2  2007/05/26 06:49:29  kduraisamy
*proposal and expiry date fields added.
*
*Revision 1.1  2007/05/02 10:50:13  kduraisamy
*contractsTrackingVO added.
*
*
*/