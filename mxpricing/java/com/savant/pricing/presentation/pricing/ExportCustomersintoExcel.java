/*
 * Created on Jun 7, 2007
 * 
 * Class Name ExportCustomersintoExcel.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.savant.pricing.calculation.valueobjects.CustomerPreferencesVO;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.transferobjects.TeamDetails;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExportCustomersintoExcel 
{
    public byte[] getCustomers(String userType,HashMap hmcustomers)
    {
        HSSFWorkbook wb = null ;
        if(userType.equalsIgnoreCase("Analyst"))
            wb = this.getAnalystExcelFile(hmcustomers);
        else if(userType.equalsIgnoreCase("Manager"))
            wb = this.getManagerExcelFile(hmcustomers);
        else
            wb = this.getRepExcelFile(hmcustomers);
        FileOutputStream fileOutputStream=null;
        try
        {
            fileOutputStream = new FileOutputStream("test.xls");
            fileOutputStream.flush();
            wb.write(fileOutputStream);
            fileOutputStream.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        byte[] b = null;
        try
        {
            b= this.getBytesFromFile();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return b;
        
    }
    private HSSFWorkbook getRepExcelFile(HashMap hmCustomers)
    {
        List lstCustomers = (List)hmCustomers.get("Records");
        ProspectiveCustomerVO objProspectiveCustomerVO = null;
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        CustomerPreferencesVO objCustomerPreferencesVO   = new CustomerPreferencesVO();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Customers");
        sheet.setDefaultColumnWidth((short)25);
        
        HSSFFont font = wb.createFont();
        HSSFFont font1 = wb.createFont();
        font.setFontHeightInPoints((short)9);
        font.setFontName("Verdana");
        font1.setFontHeightInPoints((short)10);
        font1.setFontName("Verdana");
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style1 = wb.createCellStyle();
        HSSFCellStyle cellStyle = wb.createCellStyle();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        cellStyle.setFont(font1);
        
        style1.setFont(font1);
        style1.setBorderBottom((short)1.0);
        style1.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        HSSFFooter footer = sheet.getFooter();
        footer.setRight( "Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages() );
        
        sheet.createFreezePane(2,3);
        
        style1.setBorderLeft((short)1);
        style1.setBorderRight((short)1.0);
        style1.setBorderTop((short)1.0);
        style1.setFillBackgroundColor((short)200);
        style1.setWrapText(true);
        style1.setFillBackgroundColor((short)150);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short) 0);
        
        cell.setCellValue("List of Prospect/Renewal Customers on :");
        Region objRegion = new Region();
        objRegion.setColumnFrom((short)0);
        objRegion.setColumnTo((short)1);
        sheet.addMergedRegion(objRegion);
        cell.setCellStyle(cellStyle);
        
        cell = row.createCell((short)2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(sdf.format(new Date()));
      
        
        
        row = sheet.createRow((short)2);
        
        int columnCount = 0;
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("S.No");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Customer Name ");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Customer Id");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Business Type");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Service Address");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Customer Status");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Approval Status");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Auto Run");
        
        Iterator iteCust = lstCustomers.iterator();
        int rowCount = 3;
        while(iteCust.hasNext())
        {
            objProspectiveCustomerVO = (ProspectiveCustomerVO)iteCust.next();
            columnCount = 0;
            row = sheet.createRow((short)rowCount++);
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(rowCount-3);
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getCustomerName());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            if(objProspectiveCustomerVO.getCustomerId()!=null)
                cell.setCellValue(objProspectiveCustomerVO.getCustomerId().doubleValue());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getBusinessType());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getAddress());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getCustomerStatus().getCustomerStatus());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getCdrStatus().getCdrState());
            
            objCustomerPreferencesVO = objProspectiveCustomerDAO.getProspectiveCustomerPreferences(objProspectiveCustomerVO.getProspectiveCustomerId());
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            if(objCustomerPreferencesVO != null)
                cell.setCellValue(objCustomerPreferencesVO.isAutoRun()?"Yes":"No");
            else
                cell.setCellValue("No");
                
        }
        row = sheet.getRow((short)4);
        return wb;
    }
    private HSSFWorkbook getManagerExcelFile(HashMap hmCustomers)
    {
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        CustomerPreferencesVO objCustomerPreferencesVO   = new CustomerPreferencesVO();
        
        List lstCustomers = (List)hmCustomers.get("Records");
        ProspectiveCustomerVO objProspectiveCustomerVO = null;
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Customers");
        sheet.setDefaultColumnWidth((short)25);
        
        HSSFFont font = wb.createFont();
        HSSFFont font1 = wb.createFont();
        font.setFontHeightInPoints((short)9);
        font.setFontName("Verdana");
        
        font1.setFontHeightInPoints((short)10);
        font1.setFontName("Verdana");
        
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style1 = wb.createCellStyle();
        HSSFCellStyle cellStyle = wb.createCellStyle();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        cellStyle.setFont(font1);
        
        style1.setFont(font1);
        style1.setBorderBottom((short)1.0);
        style1.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        HSSFFooter footer = sheet.getFooter();
        footer.setRight( "Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages() );
        
        sheet.createFreezePane(2,3);
        
        style1.setBorderLeft((short)1);
        style1.setBorderRight((short)1.0);
        style1.setBorderTop((short)1.0);
        style1.setFillBackgroundColor((short)200);
        style1.setWrapText(true);
        style1.setFillBackgroundColor((short)150);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short) 0);
        
        cell.setCellValue("List of Prospect/Renewal Customers on :");
        Region objRegion = new Region();
        objRegion.setColumnFrom((short)0);
        objRegion.setColumnTo((short)1);
        sheet.addMergedRegion(objRegion);
        cell.setCellStyle(cellStyle);
        
        cell = row.createCell((short)2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(sdf.format(new Date()));
      
        row = sheet.createRow((short)2);
        int columnCount = 0;
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("S.No");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Customer Name ");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Customer Id");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Business Type");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Servise Address");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Sales Rep");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Customer Status");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Approval Status");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Auto Run");
        
        Iterator iteCust = lstCustomers.iterator();
        int rowCount = 3;
        while(iteCust.hasNext())
        {
            objProspectiveCustomerVO = (ProspectiveCustomerVO)iteCust.next();
            columnCount = 0;
            row = sheet.createRow((short)rowCount++);
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(rowCount-3);
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getCustomerName());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            if(objProspectiveCustomerVO.getCustomerId()!=null)
                cell.setCellValue(objProspectiveCustomerVO.getCustomerId().doubleValue());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getBusinessType());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getAddress());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            if(objProspectiveCustomerVO.getSalesRep()!=null)
                cell.setCellValue(objProspectiveCustomerVO.getSalesRep().getFirstName()+" "+objProspectiveCustomerVO.getSalesRep().getLastName());
            else
                cell.setCellValue("");
           
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getCustomerStatus().getCustomerStatus());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getCdrStatus().getCdrState());
            
            objCustomerPreferencesVO = objProspectiveCustomerDAO.getProspectiveCustomerPreferences(objProspectiveCustomerVO.getProspectiveCustomerId());
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            if(objCustomerPreferencesVO != null)
                cell.setCellValue(objCustomerPreferencesVO.isAutoRun()?"Yes":"No");
            else
                cell.setCellValue("No");
        }
        
        row = sheet.getRow((short)4);
        return wb;
        
    }
    private HSSFWorkbook getAnalystExcelFile(HashMap hmcustomers)
    {
        List lstCustomers = (List)hmcustomers.get("Records");
        ProspectiveCustomerVO objProspectiveCustomerVO = null;
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Customers");
        TeamDetails objTeamDetails = null;
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        CustomerPreferencesVO objCustomerPreferencesVO = new CustomerPreferencesVO();
        sheet.setDefaultColumnWidth((short)25);
        
        HSSFFont font = wb.createFont();
        HSSFFont font1 = wb.createFont();
        font.setFontHeightInPoints((short)9);
        font.setFontName("Verdana");
        
        font1.setFontHeightInPoints((short)10);
        font1.setFontName("Verdana");
        
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style1 = wb.createCellStyle();
        HSSFCellStyle cellStyle = wb.createCellStyle();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        cellStyle.setFont(font1);
       
        
        style1.setFont(font1);
        style1.setBorderBottom((short)1.0);
        style1.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        
        HSSFFooter footer = sheet.getFooter();
        footer.setRight( "Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages() );
        
        sheet.createFreezePane(2,3);
        
        style1.setBorderLeft((short)1);
        style1.setBorderRight((short)1.0);
        style1.setBorderTop((short)1.0);
        style1.setFillBackgroundColor((short)200);
        style1.setWrapText(true);
        style1.setFillBackgroundColor((short)150);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short) 0);
        
        cell.setCellValue("List of Prospect/Renewal Customers on :");
        Region objRegion = new Region();
        objRegion.setColumnFrom((short)0);
        objRegion.setColumnTo((short)1);
        sheet.addMergedRegion(objRegion);
        cell.setCellStyle(cellStyle);
        
        cell = row.createCell((short)2);
        cell.setCellStyle(style);
        cell.setCellValue(sdf.format(new Date()));
      
        
        
        row = sheet.createRow((short)2);
        int columnCount = 0;
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("S.No");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Customer Name ");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Customer Id");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("CUD Imported on");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Business Type");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Address");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Sales Rep");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Sales Manager");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Customer Status");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Approval Status");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Auto Run");
        
        Iterator iteCust = lstCustomers.iterator();
        int rowCount = 3;
        while(iteCust.hasNext())
        {
            objProspectiveCustomerVO = (ProspectiveCustomerVO)iteCust.next();
            columnCount = 0;
            row = sheet.createRow((short)rowCount++);
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(rowCount-3);
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getCustomerName());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            if(objProspectiveCustomerVO.getCustomerId()!=null)
                cell.setCellValue(objProspectiveCustomerVO.getCustomerId().doubleValue());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getImportedPICOn()==null?"":sdf.format(objProspectiveCustomerVO.getImportedPICOn()));
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getBusinessType());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getAddress());
            
            objTeamDetails = objProspectiveCustomerDAO.getTeam(objProspectiveCustomerVO.getProspectiveCustomerId());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            if(objProspectiveCustomerVO.getSalesRep()!=null)
                cell.setCellValue(objTeamDetails.getSalesRep().getFirstName()+" "+objTeamDetails.getSalesRep().getLastName());
            else
                cell.setCellValue("");
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            if(objProspectiveCustomerVO.getSalesRep().getParentUser()!=null)
                cell.setCellValue(objTeamDetails.getSalesManager().getFirstName()+" "+objTeamDetails.getSalesManager().getLastName());
            else
                cell.setCellValue("");
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getCustomerStatus().getCustomerStatus());
            
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            cell.setCellValue(objProspectiveCustomerVO.getCdrStatus().getCdrState());
            
            objCustomerPreferencesVO = objProspectiveCustomerDAO.getProspectiveCustomerPreferences(objProspectiveCustomerVO.getProspectiveCustomerId());
            cell = row.createCell((short)(columnCount++));
            cell.setCellStyle(style);
            
            if(objCustomerPreferencesVO != null)
                cell.setCellValue(objCustomerPreferencesVO.isAutoRun()?"Yes":"No");
            else
                cell.setCellValue("No");
                
        }
        
        row = sheet.getRow((short)4);
        return wb;
    }
    public byte[] getBytesFromFile() throws IOException
    {
        byte[] bytes =null;
        try
        {
            File file = new File("test.xls");
            InputStream is = new FileInputStream(file);
            long length = file.length();
            if (length > Integer.MAX_VALUE)
            {
                System.out.print("out of memory");
                
            }
            bytes = new byte[(int) length];
            
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
            {
                offset += numRead;
                
            }
            if (offset < bytes.length)
            {
                throw new IOException("Could not completely read file "
                        + file.getName());
            }
            is.close();
            
            
        }
        catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }
}


/*
*$Log: ExportCustomersintoExcel.java,v $
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.4  2007/11/20 04:52:53  tannamalai
**** empty log message ***
*
*Revision 1.3  2007/10/31 10:06:13  sramasamy
*CDR status is changed as Approval Status.
*
*Revision 1.2  2007/10/30 09:38:01  sramasamy
*Bug fixed.
*
*Revision 1.1  2007/10/30 05:51:49  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.8  2007/07/24 14:04:27  kduraisamy
*Auto run filter added
*
*Revision 1.7  2007/07/24 05:04:51  jnadesan
*auto run show changed
*
*Revision 1.6  2007/07/23 11:24:56  sramasamy
*Added Auto Run field in Excel sheet for Manager, Analyst and Rep form
*
*Revision 1.4  2007/07/05 11:05:56  jnadesan
*showing manager problem solved
*
*Revision 1.3  2007/06/15 14:15:17  jnadesan
*label added
*
*Revision 1.2  2007/06/13 11:42:25  kduraisamy
*font is set as bold for headers.
*
*Revision 1.1  2007/06/11 05:39:15  jnadesan
*excel option added for all users
*
*
*/