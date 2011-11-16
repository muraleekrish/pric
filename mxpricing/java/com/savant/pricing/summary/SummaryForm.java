/*
 * 
 * SummaryForm.java    Aug 6, 2007
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
package com.savant.pricing.summary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.struts.action.ActionForm;

/**
 * 
 */
public class SummaryForm extends ActionForm
{
    private String product    = "";
    private String term       = "";
    private String runType    = "all";
    private String toDate     = "";
    private String fromDate   = "";
    private String formAction = "";
    private String custId     = "";
    private String pageUser   = "";

    /**
     * 
     */
    public SummaryForm()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Calendar objCalendar = Calendar.getInstance();
        this.toDate = sdf.format(objCalendar.getTime());
        objCalendar.add(Calendar.DAY_OF_MONTH, -12);
        this.fromDate = sdf.format(objCalendar.getTime());
    }
    public String getCustId()
    {
        return custId;
    }

    public String getFormAction()
    {
        return formAction;
    }

    public String getFromDate()
    {
        return fromDate;
    }

    public String getPageUser()
    {
        return pageUser;
    }

    public String getProduct()
    {
        return product;
    }

    public String getRunType()
    {
        return runType;
    }

    public String getTerm()
    {
        return term;
    }

    public String getToDate()
    {
        return toDate;
    }

    public void setCustId(String custId)
    {
        this.custId = custId;
    }

    public void setFormAction(String formAction)
    {
        this.formAction = formAction;
    }

    public void setFromDate(String fromDate)
    {
        this.fromDate = fromDate;
    }

    public void setPageUser(String pageUser)
    {
        this.pageUser = pageUser;
    }

    public void setProduct(String product)
    {
        this.product = product;
    }

    public void setRunType(String runType)
    {
        this.runType = runType;
    }

    public void setTerm(String term)
    {
        this.term = term;
    }

    public void setToDate(String toDate)
    {
        this.toDate = toDate;
    }
}
/*
 *$Log: SummaryForm.java,v $
 *Revision 1.2  2008/11/21 09:47:53  jvediyappan
 *Trieagle changed as MXEnergy.
 *
 *Revision 1.1  2007/12/07 06:06:39  jvediyappan
 *initial commit.
 *
 *Revision 1.1  2007/10/30 05:51:59  jnadesan
 *Initial commit.
 *
 *Revision 1.1  2007/10/26 15:19:30  jnadesan
 *initail MXEP commit
 *
 *Revision 1.2  2007/08/23 07:39:55  jnadesan
 *imports organized
 *
 *Revision 1.1  2007/08/06 15:56:40  jnadesan
 *initial commit for history page
 *
 *
 */