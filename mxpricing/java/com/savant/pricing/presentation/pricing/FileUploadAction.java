/*
 * Created on Mar 2, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.savant.pricing.dao.PICDAO;
import com.savant.pricing.dao.ProspectiveCustomerDAO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FileUploadAction extends Action
{
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
    {
        String action="failure";
        String errMsg = "";
        ActionErrors actionErrors = new ActionErrors();
        ActionMessages actionmessages = new ActionMessages();
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        PICDAO objPICDAO = new PICDAO();
        try
        {
            boolean result=false;
            request.setAttribute("prsCustId",request.getParameter("prsCustId"));
            if(form instanceof FileUploadForm)
            {
                FileUploadForm frm = (FileUploadForm)form;
                if(frm.getFormActions()==null)
                {
                    frm.setFormActions("next");
                }
                if(request.getParameter("formActoin")!= null && request.getParameter("formActoin").trim().equalsIgnoreCase("upload"))
                    frm.setFormActions("upload");
                if(frm.getFormActions().trim().equalsIgnoreCase("upload") && frm.getFormActions()!=null)
                {
                    if(BuildConfig.DMODE)
                        System.out.println("upload "+frm.getTheFile().getContentType());
                    if(frm.getTheFile().getFileSize()>0 && frm.getTheFile().getContentType().equalsIgnoreCase("application/vnd.ms-excel"))
                    {
                        try{ 
                            int prsCustId =0;
                            if(frm.getPrsCustId()!=null)
                            {
                                prsCustId = Integer.parseInt(frm.getPrsCustId());
                                if(objProspectiveCustomerDAO.isCDRApprove(prsCustId))
                                {
                                    
                                    result =  this.uploadFile(frm,prsCustId);
                                }
                                else
                                {
                                    actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.customer.Approve.Check"));
                                }
                            }
                        }
                        catch (SQLException e) {
                            errMsg = e.getMessage();
                            e.printStackTrace();
                        }
                        catch (Exception e) {
                            errMsg = e.getMessage();
                            e.printStackTrace();
                        }
                        if(result)
                        {
                            action="success";
                            actionmessages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Prospective.customer.picimport.success"));
                            request.setAttribute("message","message");
                        }
                        else
                        {
                            if(actionErrors.isEmpty())
                            {
                                if(errMsg.indexOf("Cannot insert the value NULL into column 'Cust_Name'")>0)
                                    actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.customer.picimport.custnameEmpty"));
                                else
                                    actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.customer.picimport.notimported"));
                            }
                            request.setAttribute("message","error");
                        }
                    }
                    else
                    {
                        result = false;
                        request.setAttribute("message","error");
                        if(frm.getTheFile().getFileSize()==0)
                        {
                            actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.customer.picimport.nofile"));
                        }
                        else if(frm.getTheFile().getContentType().equalsIgnoreCase("application/octet-stream"))
                        {
                            actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.customer.picimport.fileopen"));
                        }
                        else if(frm.getTheFile().getFileSize()>0)
                        {
                            actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.customer.picimport.format"));
                        }
                    }
                }
                else if(frm.getFormActions().equalsIgnoreCase("exportToCMS"))
                {
                    Session objSession = null;
                    CallableStatement cstmnt = null;
                    try
                    {
                        String userId = (String)request.getSession().getAttribute("userName");
                        if(frm.getPrsCustId()!=null)
                        {
                            int prsCustId =0;
                            prsCustId = Integer.parseInt(frm.getPrsCustId());
                            objSession = HibernateUtil.getSession();
                            objSession.beginTransaction();         
                            cstmnt = objSession.connection().prepareCall("{call sp_ForeCastIntoCMS (?,?)}");
                            cstmnt.setInt(1, prsCustId);
                            cstmnt.setString(2, userId);
                            cstmnt.execute();
                            objSession.getTransaction().commit();
                            result = true;
                        }
                    }
                    catch(SQLException e)
                    {
                        e.printStackTrace();
                        objSession.getTransaction().rollback();
                    }
                    finally
                    {
                        cstmnt.close();
                        objSession.close();
                    }
                    if(result)
                    {
                        actionmessages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("export.to.cms.success"));
                    }
                    else
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("export.to.CMS.error"));
                        request.setAttribute("message","error");
                    }
                }
                else if(frm.getFormActions().equalsIgnoreCase("deleteesiid"))
                {
                    String esiids = "";
                    try
                    {
                        if(frm.getPrsCustId()!=null && frm.getEsiids()!=null)
                        {
                            if(frm.getEsiids().trim().equals("0"))
                                esiids = "All";
                            else
                                esiids = frm.getEsiids().trim();
                            
                            objPICDAO.deleteESIID(Integer.parseInt(frm.getPrsCustId()), esiids, Integer.parseInt(frm.getTotEsiids()));
                            result = true;
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    if(result)
                    {
                        if(frm.getEsiids().trim().equals("0"))
                            actionmessages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.delete.messages", " All ESIIDs", ""));
                       else
                            actionmessages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.delete.message", "ESIID", "'"+frm.getEsiids()+"'"));
                        request.setAttribute("message","message");
                    }
                    else
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("common.error.generalerror"));
                        request.setAttribute("message","error");
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if(!actionErrors.isEmpty())
        {
            action = "failure";
            saveErrors(request, actionErrors);
        }
        saveMessages(request,actionmessages);
        return mapping.findForward(action);
    }
    private boolean uploadFile(FileUploadForm myForm, int prcCustId)throws SQLException, PricingException 
    {
        
        boolean result=false;
        String serverPath = "";
        try
        {
            // Process the FormFile
            FormFile myFile = myForm.getTheFile();
            byte[] fileData    = myFile.getFileData();
            FileOutputStream testfile = null;
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Development"))
            {
                serverPath = BuildConfig.DEV_SERVER_PATH+ "pricing\\DataAutomation\\PicFile.xls";
            }
            else if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
            {
                serverPath = "\\\\houvmwpsavdb\\pricingdata_prod\\DataAutomation\\PICFile.xls";
            }
            else if(BuildConfig.Env_Variable.equalsIgnoreCase("Testing"))
            {
                serverPath = "\\\\houvmwpsavdb\\pricingdata\\DataAutomation\\PICFile.xls";
            }
            testfile = new FileOutputStream(serverPath);
            testfile.write(fileData);
            if(testfile!=null)
            {
                testfile.flush();
                testfile.close();
            }
            
            Session objSession = null;
            CallableStatement cstmnt = null;
            try
            {
                objSession = HibernateUtil.getSession();
                objSession.beginTransaction();
                cstmnt = objSession.connection().prepareCall("{call sp_importPICUsagefrmXLS (?,?)}");
                cstmnt.setString(1, String.valueOf(prcCustId));
                String filePath = "";
                if(BuildConfig.Env_Variable.equalsIgnoreCase("Development"))
                {
                    filePath = BuildConfig.DEV_SERVER_PATH+ "pricing\\DataAutomation\\PicFile.xls";
                }
                else if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
                {
                    filePath = "\\\\houvmwpsavdb\\pricingdata_prod\\DataAutomation\\PICFile.xls";
                }
                else if(BuildConfig.Env_Variable.equalsIgnoreCase("Testing"))
                {
                    filePath = "\\\\houvmwpsavdb\\Pricingdata\\DataAutomation\\PICFile.xls";
                }
                cstmnt.setString(2, filePath);
                cstmnt.execute();
                objSession.getTransaction().commit();
                result = true;
            }
            catch(SQLException e)
            {
                objSession.getTransaction().rollback();
                throw new PricingException(e.toString());
            }
            finally
            {
                cstmnt.close();
                objSession.close();
            }
            File dsd=new File(serverPath);
            dsd.delete();
        }
        catch(Exception e)
        {
            throw new PricingException(e.toString());
        }
        return result;
    }
}
    

   

