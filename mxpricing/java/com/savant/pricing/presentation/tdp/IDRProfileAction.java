/*
 * Created on Feb 19, 2007
 *
 */
package com.savant.pricing.presentation.tdp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.PricingException;
import com.savant.pricing.tdp.dao.IDRProfilerManger;

/**
 * @author Karthikeyan Chellamuthu
 *
 */
public class IDRProfileAction extends Action
{ 
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
	    String action="failure";
	    String esiidno=null;
	    if (form instanceof IDRProfileForm) 
	    {
	        HttpSession session = request.getSession();
	        IDRProfileForm frm = (IDRProfileForm) form;
	        String result = "";
	        if(!frm.getFormAction().equalsIgnoreCase(""));
	        {
	            FormFile myFile = frm.getFile();
	            String filename = "";
	            String tdspType = frm.getRdoTdsp();
	            String sheetName = "";
	            try 
	            {
	                byte[] fileData    = myFile.getFileData();
	                FileOutputStream fileOut = null;
	                esiidno = (String)session.getAttribute("old");
	                String fName="";
	                String folderName="";
	                if(tdspType.equalsIgnoreCase("oncor"))
	                {
	                    if(BuildConfig.Env_Variable.equalsIgnoreCase("Development"))
	                    {
	                        filename = BuildConfig.DEV_SERVER_PATH+ "pricing\\DataAutomation\\"+session.getId()+"oncur.txt";
	                    }
	                    else if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
	                    {
	                        filename = "\\\\houvmwpsavdb\\pricingdata_prod\\DataAutomation\\"+session.getId()+"oncur.txt";
	                    }
	                    else if(BuildConfig.Env_Variable.equalsIgnoreCase("Testing"))
	                    {
	                        filename = "\\\\houvmwpsavdb\\pricingdata\\DataAutomation\\"+session.getId()+"oncur.txt";
	                    }
	                    fName=session.getId()+"oncur.txt";
	                }
	                else if(tdspType.equalsIgnoreCase("centerpoint"))
	                {
	                    if(BuildConfig.Env_Variable.equalsIgnoreCase("Development"))
	                    {
	                        filename = BuildConfig.DEV_SERVER_PATH+ "pricing\\DataAutomation\\"+session.getId()+"nononcur.xls";
	                    }
	                    else if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
	                    {
	                        filename = "\\\\houvmwpsavdb\\pricingdata_prod\\DataAutomation\\"+session.getId()+"nononcur.xls";
	                    }
	                    else if(BuildConfig.Env_Variable.equalsIgnoreCase("Testing"))
	                    {
	                        filename = "\\\\houvmwpsavdb\\pricingdata\\DataAutomation\\"+session.getId()+"nononcur.xls";
	                    }
	                }
	                else
	                {
	                    if(BuildConfig.Env_Variable.equalsIgnoreCase("Development"))
	                    {
	                        filename = BuildConfig.DEV_SERVER_PATH+ "pricing\\DataAutomation\\"+myFile.getFileName();
	                    }
	                    else if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
	                    {
	                        filename = "\\\\houvmwpsavdb\\pricingdata_prod\\DataAutomation\\"+myFile.getFileName();
	                    }
	                    else if(BuildConfig.Env_Variable.equalsIgnoreCase("Testing"))
	                    {
	                        filename = "\\\\houvmwpsavdb\\pricingdata\\DataAutomation\\"+myFile.getFileName();
	                    }
	                }
	                if(tdspType.equalsIgnoreCase("aep"))
	                {
	                    fileOut = new FileOutputStream(filename);   
	                    fileOut.write(fileData);
	                    fileOut.close();
	                    HSSFWorkbook wb;
	                    try
	                    {
	                        FileInputStream fis = new FileInputStream(filename);
	                        wb = new HSSFWorkbook(fis);
	                        wb.setSheetName(0,"AEP");
	                        sheetName = wb.getSheetName(0);
	                        FileOutputStream fileOutNew = new FileOutputStream(filename);
	                        wb.write(fileOutNew);
	                        fileOutNew.close();
	                    }
	                    catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	                else if(tdspType.equalsIgnoreCase("CenterPoint"))
	                {
	                    fileOut = new FileOutputStream(filename);   
	                    fileOut.write(fileData);
	                    fileOut.close();
	                    HSSFWorkbook wb;
	                    try
	                    {
	                        FileInputStream fis = new FileInputStream(filename);
	                        wb = new HSSFWorkbook(fis);
	                        wb.setSheetName(0,"CenterPoint");
	                        sheetName = wb.getSheetName(0);
	                        FileOutputStream fileOutNew = new FileOutputStream(filename);
	                        wb.write(fileOutNew);
	                        fileOutNew.close();
	                    }
	                    catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	                else
	                {
	                    fileOut = new FileOutputStream(filename);
	                    fileOut.write(fileData);
	                    fileOut.close();
	                }
	            }
	            catch (FileNotFoundException e) 
	            {
	                request.setAttribute("Error","Error");
	                e.printStackTrace();
	            }
	            catch (IOException e) 
	            {
	                request.setAttribute("Error","Error");
	                e.printStackTrace();
	            }
	            catch (Exception e) 
	            {
	                request.setAttribute("Error","Error");
	                e.printStackTrace();
	            }
	            try
	            {
	                String userID = "";
	                String userType = "";
	                userType = (String) session.getAttribute("userName");
	                IDRProfilerManger objManager = new IDRProfilerManger();
	                result = objManager.importFile(esiidno,filename,tdspType,userType,sheetName);			       
	                if(result!=null)
	                {
	                    action = "success";
	                    request.getSession().setAttribute("ImportStatus",result);
	                    request.getSession().setAttribute("tdspType",tdspType);
	                }	
	                else
	                {
	                    frm.setMessage("Invalid File Format");
	                }
	                
	            }
	            catch(PricingException pe)
	            {
	                request.getSession().setAttribute("ImportStatus",pe.getMessage()); 
	                request.setAttribute("Error","Error");
	                pe.printStackTrace();
	                result = "Invalid File - Import Failed";
	            }
	            catch(Exception e)
	            {
	                request.setAttribute("Error","Error");
	                if(e.toString().equalsIgnoreCase("Invalid File Format"))
	                    request.getSession().setAttribute("ImportStatus","Invalid File - Import Failed");
	                e.printStackTrace();
	                result = "Invalid File - Import Failed";
	            }
	        }
	        
	    }
	    return mapping.findForward(action);
	}


}
