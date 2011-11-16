/*
 * Created on Mar 16, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.fulcruminput;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

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
import com.savant.pricing.dao.ForwardCurveBlockDAO;
import com.savant.pricing.inputs.dao.InputsDAO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ImportInputEnergyAction extends  Action{
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException
    {
        ActionErrors actionErrors = new ActionErrors();
        ActionMessages actionmessages = new ActionMessages();
        ForwardCurveBlockDAO objForwardCurveBlockDAO = new ForwardCurveBlockDAO();
        boolean result = false;
        boolean resultforward = false;
        String action="failure";
        if(form instanceof ImportInputEnergyForm)
        {
            ImportInputEnergyForm frm = (ImportInputEnergyForm)form;
            InputsDAO objInputsDAO = new InputsDAO();
            if(frm.getFormActions().equalsIgnoreCase("Import"))
            {
                if(frm.getGasBrowse().getFileSize()>0 && frm.getGasBrowse().getContentType().equalsIgnoreCase("application/vnd.ms-excel") && !frm.getTxtgasdate().trim().equalsIgnoreCase("") )
                {
                        try
                        {
                            result = objInputsDAO.uploadGasXls(frm,frm.getTxtgasdate());
                        }
                        catch(PricingException pe)
                        {
                            if(pe.getMessage().trim().equalsIgnoreCase("com.savant.pricing.common.PricingException: Market Period Mismatch"))
                            {
                                actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.gasprice.MarketPeriodMisMatch"));
                            }
                            else if(pe.getMessage().trim().equalsIgnoreCase("com.savant.pricing.common.PricingException: Invalid Format"))
                            {
                                actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.gasprice.InvalidFormat"));
                            }
                            else if(pe.getMessage().trim().equalsIgnoreCase("com.savant.pricing.common.PricingException: Import Failed"))
                            {
                                actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Import.naturalgas.failed"));
                            }
                            else if(pe.getMessage().trim().equalsIgnoreCase("com.savant.pricing.common.PricingException: Invalid Sheet Name"))
                            {
                                actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Import.naturalgas.invalid.sheet"));
                            }
                            else if(pe.getMessage().trim().equalsIgnoreCase("com.savant.pricing.common.PricingException: Import Failed Due to Date"))
                            {
                                actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Import.naturalgas.failed.date"));
                            }
                        }
                        if(result)
                        {
                            actionmessages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Import.naturalgas.success"));
                        }
                }
                else
                {
                    result = false;
                    request.setAttribute("message","error");
                    System.out.println(" file open :"+frm.getGasBrowse().getContentType());
                    if(frm.getGasBrowse().getFileSize()==0)
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.gasprice.nofile"));
                    }
                    else if(frm.getTxtgasdate().trim().equalsIgnoreCase(""))
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.gasprice.MarketPeriod"));
                    }
                    else if(frm.getGasBrowse().getContentType().equalsIgnoreCase("application/octet-stream"))
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.gasprice.fileopen"));
                    }
                    else if(frm.getGasBrowse().getFileSize()>0)
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.gasprice.format"));
                    }
                   
                }
                if(frm.getGasBrowse().getFileSize()>0 && frm.getGasBrowse().getContentType().equalsIgnoreCase("application/vnd.ms-excel") && !frm.getTxtgasdate().trim().equalsIgnoreCase(""))
                {
                        try
                        {
                            resultforward = objInputsDAO.uploadForwardXls(frm,frm.getTxtgasdate());
                            System.out.println(" ------------forward result :"+resultforward );
                        }
                        catch(PricingException pe)
                        {
                            if(pe.getMessage().trim().equalsIgnoreCase("com.savant.pricing.common.PricingException: Market Period Mismatch"))
                            {
                                actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.forwardprice.MarketPeriodMisMatch"));
                            }
                            else if(pe.getMessage().trim().equalsIgnoreCase("com.savant.pricing.common.PricingException: Invalid Format"))
                            {
                                actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.forwardprice.InvalidFormat"));
                            }
                            else if(pe.getMessage().trim().equalsIgnoreCase("com.savant.pricing.common.PricingException: Import Failed"))
                            {
                                actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Import.forwardprice.failed"));
                            }
                            else if(pe.getMessage().trim().equalsIgnoreCase("com.savant.pricing.common.PricingException: Invalid Sheet Name"))
                            {
                                actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Import.forwardprice.invalid.sheet"));
                            }
                            else if(pe.getMessage().trim().equalsIgnoreCase("com.savant.pricing.common.PricingException: Import Failed Due to Date"))
                            {
                                actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Import.forwardprice.failed.date"));
                            }
                            else if(pe.getMessage().trim().equalsIgnoreCase("com.savant.pricing.common.PricingException: Natural Gas Price Zero"))
                            {
                                actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Import.forwardprice.failed.zero"));
                            }
                        }
                        if(resultforward)
                        {
                            actionmessages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Import.forward.success"));
                        }
                       
                        objForwardCurveBlockDAO.reload();
                        EnergyChargeRatesDAO objEnergyChargeRatesDAO = new EnergyChargeRatesDAO();
                        objEnergyChargeRatesDAO.reload();
                }
                else
                {
                    resultforward = false;
                    request.setAttribute("message","error");
                    System.out.println(" file open forward:"+frm.getGasBrowse().getContentType());
                    if(frm.getGasBrowse().getFileSize()==0)
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.forwardprice.nofile"));
                    }
                    else if(frm.getTxtgasdate().trim().equalsIgnoreCase(""))
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.forwardprice.MarketPeriod"));
                    }
                    else if(frm.getGasBrowse().getContentType().equalsIgnoreCase("application/octet-stream"))
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.forwardprice.fileopen"));
                    }
                    else if(frm.getGasBrowse().getFileSize()>0)
                    {
                        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.forwardprice.format"));
                    }
                    objForwardCurveBlockDAO.reload();
                    EnergyChargeRatesDAO objEnergyChargeRatesDAO = new EnergyChargeRatesDAO();
                    objEnergyChargeRatesDAO.reload();
                }
                frm.setTxtgasdate("");
                frm.setTxtforwarddate("");
            }
            else
            {
                actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("common.error.generalerror"));
            }
        }
        if(!actionErrors.isEmpty())
        {
            action = "failure";
            saveErrors(request, actionErrors);
        }
        saveMessages(request,actionmessages);
        return mapping.findForward(action);
    }
    
   }
