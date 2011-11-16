/*
 * Created on May 7, 2007
 *
 * ClassName	:  	OtherChargesListAction.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.presentation.pconfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.hibernate.Session;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.PricingException;
import com.savant.pricing.dao.EnergyChargeRatesDAO;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class OtherChargesListAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action="failure";  
        ActionErrors actionErrors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        boolean result = false;
        if(form instanceof OtherChargesListForm)
        {
            OtherChargesListForm frm = (OtherChargesListForm)form;
            
            if(frm.getFormAction().equalsIgnoreCase("import"))
            {
                EnergyChargeRatesDAO objEnergyChargeRatesDAO = new EnergyChargeRatesDAO();
                if(frm.getOthrBrowse().getFileSize()>0 && frm.getOthrBrowse().getContentType().equalsIgnoreCase("application/vnd.ms-excel")  )
                {
                        try
                        {
                            result = this.uploadFile(frm);
                            objEnergyChargeRatesDAO.reload();
                        }
                        catch(SQLException se)
                        {
                            se.printStackTrace();
                            actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Import.othrCharge.failed"));
                        }
                        catch(PricingException pe)
                        {
                            if(pe.getMessage().trim().equalsIgnoreCase("com.savant.pricing.common.PricingException: Invalid Format"))
                            {
                                actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.othrCharge.InvalidFormat"));
                            }
                        }
                        if(result)
                        {
                            System.out.println("inside result" + result);
                            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Import.othrChrage.success"));
                        }
                        else
                        {
                            System.out.println("inside result" + result);
                            actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Import.othrCharge.failed"));
                        }
                }
                else
                {
                    result = false;
                    request.setAttribute("message","error");
                    if(frm.getOthrBrowse().getFileSize()==0)
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.othrCharge.nofile"));
                    }
                    else if(frm.getOthrBrowse().getContentType().equalsIgnoreCase("application/octet-stream"))
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.othrCharge.fileopen"));
                    }
                    else if(frm.getOthrBrowse().getFileSize()>0)
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.othrCharge.format"));
                    }
                }
                
            }
            else if(frm.getFormAction().equalsIgnoreCase("search"))
            {
                action="success";  
                frm.setFormAction("search");
                request.setAttribute("EnergyCharge",frm.getCmbEnergyCharge());
                request.setAttribute("CongestionZone",frm.getCmbCongestionZone());
                request.setAttribute("MaxItem",frm.getMaxItems());
                request.setAttribute("Page",frm.getPage());
            }
            
        }
        if(!actionErrors.isEmpty())
        {
            action = "failure";
            saveErrors(request, actionErrors);
        }
        saveMessages(request,messages);
        return mapping.findForward(action);
    }
    private boolean uploadFile(OtherChargesListForm myForm)throws SQLException, PricingException 
    {
        System.out.println("inside import method");
        boolean result=false;
        String serverPath = "";
        ActionErrors actionErrors = new ActionErrors();
        try
        {
            FormFile myFile = myForm.getOthrBrowse();
            byte[] fileData    = myFile.getFileData();
            FileOutputStream testfile = null;
            POIFSFileSystem fs ;
            HSSFWorkbook wb;
            HSSFRow row ;
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Development"))
            {
                serverPath = BuildConfig.DEV_SERVER_PATH+ "pricing\\DataAutomation\\OtherCharges.xls";
            }
            else if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
            {
                serverPath = "\\\\houvmwpsavdb\\pricingdata_prod\\DataAutomation\\OtherCharges.xls";
            }
            else if(BuildConfig.Env_Variable.equalsIgnoreCase("Testing"))
            {
                serverPath = "\\\\houvmwpsavdb\\pricingdata\\DataAutomation\\OtherCharges.xls";
            }
            testfile = new FileOutputStream(serverPath);
            testfile.write(fileData);
            testfile.close();
            String revFileName = serverPath.replace('\\','/');
            fs = new POIFSFileSystem(new FileInputStream(revFileName));
            wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            int lastRow = sheet.getLastRowNum();
            int firstRow = sheet.getFirstRowNum();
            String last = String.valueOf(lastRow+1);
            String  sName = wb.getSheetName(0);
            if(sheet.getRow(3).getCell((short)12)!=null)
            {
                Session objSession = null;
                CallableStatement cstmnt = null;
                try
                {
                    objSession = HibernateUtil.getSession();
                    objSession.beginTransaction();
                    System.out.println("inside import method calling proc");
                    cstmnt = objSession.connection().prepareCall("{call Sp_Mxep_Import_Energy_Charge(?,?,?)}");
                    String filePath = "";
                    if(BuildConfig.Env_Variable.equalsIgnoreCase("Development"))
                    {
                        filePath = BuildConfig.DEV_SERVER_PATH+ "pricing\\DataAutomation\\OtherCharges.xls";
                    }
                    else if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
                    {
                        filePath = "\\\\houvmwpsavdb\\pricingdata_prod\\DataAutomation\\OtherCharges.xls";
                    }
                    else if(BuildConfig.Env_Variable.equalsIgnoreCase("Testing"))
                    {
                        filePath = "\\\\houvmwpsavdb\\Pricingdata\\DataAutomation\\OtherCharges.xls";
                    }
                    cstmnt.setString(1, filePath);
                    cstmnt.setString(2, sName);
                    cstmnt.setString(3,last);
                    cstmnt.execute();
                    objSession.getTransaction().commit();
                    result = true;
                   
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    throw new PricingException("Import Failed");
                }
                catch(Exception e)
                {
                    objSession.getTransaction().rollback();
                    e.printStackTrace();
                    throw new PricingException("Import Failed");
                }
                
                finally
                {
                    cstmnt.close();
                    objSession.close();
                }
                File dsd=new File(serverPath);
                dsd.delete();
            }
            else
            {
                result = false;
                throw new PricingException("Invalid Format");
            }
        }
        catch(Exception e)
        {
            throw new PricingException(e.toString());
        }
        return result;
    }
}

/*
*$Log: OtherChargesListAction.java,v $
*Revision 1.6  2009/01/27 05:47:57  tannamalai
*changes - according to MX server
*
*Revision 1.5  2008/12/03 10:55:10  tannamalai
*URL Updated Based on Remote Environment.
*
*Revision 1.4  2008/04/25 10:43:37  tannamalai
*reload method called
*
*Revision 1.3  2007/12/14 09:30:57  tannamalai
*filter aplied
*
*Revision 1.2  2007/12/14 06:47:14  tannamalai
*path changed
*
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.4  2007/12/03 07:09:53  tannamalai
*test  database changed
*
*Revision 1.3  2007/11/23 11:44:43  tannamalai
**** empty log message ***
*
*Revision 1.2  2007/11/23 05:29:12  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/07/11 13:21:28  jnadesan
*energy chargerates add/update provision given
*
*Revision 1.1  2007/06/07 11:03:18  spandiyarajan
*other charges partially committed
*
*
*/