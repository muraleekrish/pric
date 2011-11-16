/*
 * Created on Nov 19, 2007
 *
 * ClassName	:  	MarketCommentry.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pconfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts.action.ActionForm;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MarketCommentryForm extends ActionForm
{
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

    private String txtMrktDate = sdf.format(new Date());
    private String txtMrktCommentry = "";
    private String createdBy = "";
    private String createdDate = "";
    private String modifiedBy = "";
    private String modifiedDate = "";
    private String formAction = "";
    private int commentid = 0;
    private String[] mrktIds = new String[0];	
    
    
    
    
    public int getCommentid()
    {
        return commentid;
    }
    public void setCommentid(int commentid)
    {
        this.commentid = commentid;
    }
    public String[] getMrktIds()
    {
        return mrktIds;
    }
    public void setMrktIds(String[] mrktIds)
    {
        this.mrktIds = mrktIds;
    }
    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    public String getCreatedDate()
    {
        return createdDate;
    }
    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }
    public String getModifiedBy()
    {
        return modifiedBy;
    }
    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }
    public String getModifiedDate()
    {
        return modifiedDate;
    }
    public void setModifiedDate(String modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }
    public String getFormAction()
    {
        return formAction;
    }
    public void setFormAction(String formAction)
    {
        this.formAction = formAction;
    }
    public String getTxtMrktCommentry()
    {
        return txtMrktCommentry;
    }
    public void setTxtMrktCommentry(String txtMrktCommentry)
    {
        this.txtMrktCommentry = txtMrktCommentry;
    }
    public String getTxtMrktDate()
    {
        return txtMrktDate;
    }
    public void setTxtMrktDate(String txtMrktDate)
    {
        this.txtMrktDate = txtMrktDate;
    }
}

/*
*$Log: MarketCommentryForm.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.2  2007/11/23 05:29:12  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/11/20 04:52:53  tannamalai
**** empty log message ***
*
*
*/