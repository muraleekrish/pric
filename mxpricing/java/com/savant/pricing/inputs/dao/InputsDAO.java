/*
 * Created on Nov 5, 2007
 *
 * ClassName	:  	InputsDAO.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.inputs.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.upload.FormFile;
import org.hibernate.Session;

import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.HibernateUtil;
import com.savant.pricing.common.PricingException;
import com.savant.pricing.presentation.fulcruminput.ImportInputEnergyForm;

/**
 * @author tannamalai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InputsDAO
{
    public boolean uploadGasXls(ImportInputEnergyForm myForm, String importDate)throws SQLException, PricingException 
    {
        boolean result=false;
        String serverPath = "";
        ActionErrors actionErrors = new ActionErrors();
        
        try
        {
            FormFile myFile = myForm.getGasBrowse();
            byte[] fileData    = myFile.getFileData();
            SimpleDateFormat dbFormat = new SimpleDateFormat("MM-dd-yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd-yyyy KK:mm:ss");
            String excelDate = new String();
            FileOutputStream testfile = null;
            String sheetName="Natural Gas Price";
            POIFSFileSystem fs ;
    		HSSFWorkbook wb = null;
    		HSSFRow row ;
    		int noofsheets = 0;
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Development"))
            {
                serverPath = BuildConfig.DEV_SERVER_PATH +"pricing\\DataAutomation\\gasprice.xls";
            }
            else if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
            {
                serverPath = "\\\\houvmwpsavdb\\pricingdata_prod\\DataAutomation\\gasprice.xls";
            }
            else if(BuildConfig.Env_Variable.equalsIgnoreCase("Testing"))
            {
                serverPath = "\\\\houvmwpsavdb\\pricingdata\\DataAutomation\\gasprice.xls";
            }
            testfile = new FileOutputStream(serverPath);
            testfile.write(fileData);
            testfile.close();
            String revFileName = serverPath.replace('\\','/');
            fs = new POIFSFileSystem(new FileInputStream(revFileName));
 			
 			 try
             {
 			    wb = new HSSFWorkbook(fs);
                 FileInputStream fis = new FileInputStream(revFileName);
                 noofsheets =  wb.getNumberOfSheets();
                 for(int s=0;s<noofsheets;s++)
                 {
                     wb.setSheetName(s,wb.getSheetName(s).trim());
                 }
                 FileOutputStream fileOutNew = new FileOutputStream(serverPath);
                 wb.write(fileOutNew);
                 if(fileOutNew!=null)
                     fileOutNew.close();
             }
 			 catch (Exception e) {
                 e.printStackTrace();
                 throw new PricingException("Import Failed");
             }
 			int sheetno = wb.getSheetIndex("Natural Gas Price");
 			HSSFSheet sheet = null;
 			try
 			{
 			    sheet = wb.getSheetAt(sheetno);
 			}
 			catch(Exception e)
 			{
 			   e.printStackTrace();
 			    result = false;
 			    throw new PricingException("Invalid Sheet Name");
 			}
 			int lastRow = sheet.getLastRowNum();
            int firstRow = sheet.getFirstRowNum();
            String last = String.valueOf(lastRow+1);
            String dateTime = new String();
            if(sheet.getRow(0).getCell((short)1)!=null)
            {
                if(sheet.getRow(0).getCell((short)1).getDateCellValue()!=null)
                {
                    excelDate = dbFormat.format(sheet.getRow(0).getCell((short)1).getDateCellValue());
                    dateTime = timeFormat.format(sheet.getRow(0).getCell((short)1).getDateCellValue());
                }
                else
                {
                    excelDate = sheet.getRow(0).getCell((short)1).getStringCellValue();
                    dateTime = sheet.getRow(0).getCell((short)1).getStringCellValue();
                }
            }
            if(excelDate.equalsIgnoreCase(importDate))
            {
                if(sheet.getRow(2).getCell((short)8)!=null)
                {
                Session objSession = null;
                CallableStatement cstmnt = null;
                try
                {
                    objSession = HibernateUtil.getSession();
                    objSession.beginTransaction();
                    
                    cstmnt = objSession.connection().prepareCall("{call sp_mxep_gasprice_import (?,?,?,?)}");
                    String filePath = "";
                    if(BuildConfig.Env_Variable.equalsIgnoreCase("Development"))
                    {
                        filePath = BuildConfig.DEV_SERVER_PATH+ "pricing\\DataAutomation\\gasprice.xls";
                    }
                    else if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
                    {
                        filePath = "\\\\houvmwpsavdb\\pricingdata_prod\\DataAutomation\\gasprice.xls";
                    }
                    else if(BuildConfig.Env_Variable.equalsIgnoreCase("Testing"))
                    {
                        filePath = "\\\\houvmwpsavdb\\Pricingdata\\DataAutomation\\gasprice.xls";
                    }
                    cstmnt.setString(1,dateTime);
                    cstmnt.setString(2, filePath);
                    cstmnt.setString(3, last);
                    cstmnt.setString(4,sheetName);
                    cstmnt.execute();
                    objSession.getTransaction().commit();
                    result = true;
                }
                catch(SQLException se)
                {
                    se.printStackTrace();
                    objSession.getTransaction().rollback();
                    throw new PricingException("Import Failed");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    objSession.getTransaction().rollback();
                    throw new PricingException("Import Failed");
                }
                
                finally
                {
                    cstmnt.close();
                    objSession.close();
                }
                result = true;
                File dsd=new File(serverPath);
                dsd.delete();
                }
                else
                {
                    result = false;
                    throw new PricingException("Invalid Format");
                }
            }
            else
            {
                result = false;
                throw new PricingException("Market Period Mismatch");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new PricingException(e.toString());
        }
        return result;
    }
    
    public boolean uploadForwardXls(ImportInputEnergyForm myForm, String importDate)throws SQLException, PricingException 
    {
        boolean result=false;
        String serverPath = "";
        try
        {
            FormFile myFile = myForm.getGasBrowse();
            byte[] fileData    = myFile.getFileData();
            FileOutputStream testfile = null;
            SimpleDateFormat dbFormat = new SimpleDateFormat("MM-dd-yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd-yyyy KK:mm:ss");
            String excelDate = new String();
            POIFSFileSystem fs ;
     		HSSFWorkbook wb = null;
     		HSSFRow row ;
     		int noofsheets=0;
     		String sheetName = "Forward Electric Price";
            if(BuildConfig.Env_Variable.equalsIgnoreCase("Development"))
            {
                serverPath = BuildConfig.DEV_SERVER_PATH+ "pricing\\DataAutomation\\forward.xls";
            }
            else if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
            {
                serverPath = "\\\\houvmwpsavdb\\pricingdata_prod\\DataAutomation\\forward.xls";
            }
            else if(BuildConfig.Env_Variable.equalsIgnoreCase("Testing"))
            {
                serverPath = "\\\\houvmwpsavdb\\pricingdata\\DataAutomation\\forward.xls";
            }
            testfile = new FileOutputStream(serverPath);
            testfile.write(fileData);
            testfile.close();
            String revFileName = serverPath.replace('\\','/');
            fs = new POIFSFileSystem(new FileInputStream(revFileName));
 			 try
             {
 			    wb = new HSSFWorkbook(fs);
                 FileInputStream fis = new FileInputStream(revFileName);
                 noofsheets =  wb.getNumberOfSheets();
                 for(int s=0;s<noofsheets;s++)
                 {
                     wb.setSheetName(s,wb.getSheetName(s).trim());
                 }
                 FileOutputStream fileOutNew = new FileOutputStream(serverPath);
                 wb.write(fileOutNew);
                 if(fileOutNew!=null)
                     fileOutNew.close();
             }
 			 catch (Exception e) {
                 e.printStackTrace();
                 throw new PricingException("Import Failed");
             }
 			int sheetno = wb.getSheetIndex("Forward Electric Price");
 			HSSFSheet sheet = null;
 			try
 			{
 			   sheet =  wb.getSheetAt(sheetno);
 			}
 			catch(Exception e)
 			{
 			   result = false;
               throw new PricingException("Invalid Sheet Name");
 			}
 			int lastRow = sheet.getLastRowNum();
            int firstRow = sheet.getFirstRowNum();
            String last = String.valueOf(lastRow+1);
            String dateTime = new String();
            if(sheet.getRow(0).getCell((short)1)!=null)
            {
                if(sheet.getRow(0).getCell((short)1).getDateCellValue()!=null)
                {
                    excelDate = dbFormat.format(sheet.getRow(0).getCell((short)1).getDateCellValue());
                    dateTime = timeFormat.format(sheet.getRow(0).getCell((short)1).getDateCellValue());
                }
                else
                {
                    excelDate = sheet.getRow(0).getCell((short)1).getStringCellValue();
                    dateTime = sheet.getRow(0).getCell((short)1).getStringCellValue();
                }
            }
            if(excelDate.equalsIgnoreCase(importDate) )
            {
                if(sheet.getRow(2).getCell((short)26)!=null)
                {
                    result = true;
                    Session objSession = null;
                    CallableStatement cstmnt = null;
                    try
                    {
                        objSession = HibernateUtil.getSession();
                        objSession.beginTransaction();
                        System.out.println("importDate :" + importDate);
                        cstmnt = objSession.connection().prepareCall("{call Sp_Mxep_forwardprice_import (?,?,?,?)}");
                        cstmnt.setString(1, importDate);
                        String filePath = "";
                        if(BuildConfig.Env_Variable.equalsIgnoreCase("Development"))
                        {
                            filePath = BuildConfig.DEV_SERVER_PATH+ "pricing\\DataAutomation\\forward.xls";
                        }
                        else if(BuildConfig.Env_Variable.equalsIgnoreCase("Production"))
                        {
                            filePath = "\\\\houvmwpsavdb\\pricingdata_prod\\DataAutomation\\forward.xls";
                        }
                        else if(BuildConfig.Env_Variable.equalsIgnoreCase("Testing"))
                        {
                            filePath = "\\\\houvmwpsavdb\\Pricingdata\\DataAutomation\\forward.xls";
                        }
                        cstmnt.setString(1,dateTime);
                        cstmnt.setString(2, filePath);
                        cstmnt.setString(3, last);
                        cstmnt.setString(4,sheetName);
                        cstmnt.execute();
                        objSession.getTransaction().commit();
                        result = true;
                    }
                    catch(SQLException se)
                    {
                        se.printStackTrace();
                        objSession.getTransaction().rollback();
                        if(se.getErrorCode()==8134){
                            throw new PricingException("Natural Gas Price Zero");
                        }
                        else{
                            throw new PricingException("Import Failed");
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        objSession.getTransaction().rollback();
                        throw new PricingException("Import Failed");
                    }
                    finally
                    {
                        cstmnt.close();
                        objSession.close();
                    }
                    result = true;
                    File dsd=new File(serverPath);
                    dsd.delete();
                }
                else
                {
                    result = false;
                    throw new PricingException("Invalid Format");
                }
            }
            else
            {
                result = false;
                throw new PricingException("Market Period Mismatch");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new PricingException(e.toString());
        }
        return result;
    }
    
    public String chkDate(String date)
    {
        String exlDate = date;
        String finalDate = "";
        SimpleDateFormat ss = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss"); 
        try
        {
            finalDate = ss.format(ss.parse(exlDate));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return exlDate;
    }
    
   public static void main(String[] args)
   {
       InputsDAO obj =  new InputsDAO();
       obj.chkDate("01-03-2012 24:59:00");
   }
    
}

/*
*$Log: InputsDAO.java,v $
*Revision 1.9  2009/01/27 05:47:57  tannamalai
*changes - according to MX server
*
*Revision 1.8  2008/12/03 10:55:10  tannamalai
*URL Updated Based on Remote Environment.
*
*Revision 1.7  2008/11/17 10:03:46  tannamalai
*final commit
*
*Revision 1.6  2008/02/08 06:53:47  tannamalai
*last commit before table split up
*
*Revision 1.5  2008/02/06 06:41:58  tannamalai
**** empty log message ***
*
*Revision 1.4  2008/01/03 12:59:26  tannamalai
*streams chked for null
*
*Revision 1.3  2008/01/03 09:39:47  tannamalai
*date format changed
*
*Revision 1.2  2007/12/12 08:55:56  tannamalai
*wrong sheet name handled
*
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.9  2007/12/03 07:09:53  tannamalai
*test  database changed
*
*Revision 1.8  2007/12/03 06:50:26  tannamalai
*sheet name space problm handled
*
*Revision 1.7  2007/11/30 10:45:14  tannamalai
*method changed for new template
*
*Revision 1.6  2007/11/28 09:57:58  tannamalai
*xl import changed for forward curves
*
*Revision 1.5  2007/11/23 05:29:12  tannamalai
**** empty log message ***
*
*Revision 1.4  2007/11/17 05:46:35  tannamalai
**** empty log message ***
*
*Revision 1.3  2007/11/15 06:20:26  tannamalai
**** empty log message ***
*
*Revision 1.2  2007/11/06 10:55:32  tannamalai
* changed for natural gas and forwrad price
*
*Revision 1.1  2007/11/06 08:50:01  rraman
*new class added to import  natural gas and forwrad price
*
*
*/