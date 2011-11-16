/*
 * Created on May 8, 2007
 * 
 * Class Name ExportToExcel.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.aggregatorlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.savant.pricing.calculation.valueobjects.HolidayVO;
import com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.dao.HolidayDAO;
import com.savant.pricing.dao.LoadProfileTypesDAO;
import com.savant.pricing.dao.PriceRunCustomerDAO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExportToExcel {
    
    public byte[] getLoadProfileexcelData(int prsCustrunId,String esiid,String filePath)
    {
        
        PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
        PriceRunCustomerVO objPriceRunCustomerVO = null;
        LoadProfileTypesDAO objLoadProfileTypesDAO = new LoadProfileTypesDAO();
        HashMap objAll = objLoadProfileTypesDAO.getAggregatedLoadProfile(prsCustrunId,esiid);
        objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(prsCustrunId);
        HolidayDAO objHolidayDAO = new HolidayDAO();
        HolidayVO objHolidayVO = new HolidayVO();
        HashMap holiday = new HashMap();
        List lstHolidays = null;
        Integer totHolidays = null;
        String totHol = "0";
        StringTokenizer st = new StringTokenizer(esiid,",");
        int esiIdCount = st.countTokens();
        holiday = objHolidayDAO.getHolidaysAfter();
        totHolidays = (Integer)holiday.get("TotalRecordCount");
        totHol = String.valueOf(totHolidays);
        lstHolidays = (List)holiday.get("Records");
        System.out.println("total holidays :" + totHolidays);
        POIFSFileSystem fs;
        HSSFWorkbook wb = null ;
        try
        {
            /*URL url = null;
            URLConnection urlConn = null;*/
            File excelfile = null;
            if(BuildConfig.Env_Variable.equalsIgnoreCase("production"))
            {
                excelfile = new File("E:/pricingdata/jasper/AggregatedLP.xls");
            }
            else
            {
                excelfile = new File("d:/testpricingdata/jasper/AggregatedLP.xls");
            }
            FileInputStream fsInput = new FileInputStream(excelfile);
            /*url  = new URL(filePath+"/jsp/AggregatedLP.xls" );
            urlConn = url.openConnection();*/
            fs = new POIFSFileSystem(fsInput);
            wb = new HSSFWorkbook(fs);
            
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        gc.add(Calendar.MONTH,1);
        
        HSSFSheet holidaySheet = wb.getSheet("Holidays");
        HSSFRow holidayRow = holidaySheet.getRow((0));
        HSSFCell holidayCell = holidayRow.getCell((short)3);
        holidayCell.setCellValue(totHol);  
        for(int s=0;s<lstHolidays.size();s++)
        {
            objHolidayVO = (HolidayVO)lstHolidays.get(s);
            holidayRow = holidaySheet.getRow((short)s+1);
            holidayCell = holidayRow.getCell((short)0);
            holidayCell.setCellValue(objHolidayVO.getDate());
            holidayCell = holidayRow.getCell((short)1);
            holidayCell.setCellValue(objHolidayVO.getReason());
        }
        
        HSSFSheet sheet = wb.getSheet("Aggregated LP");
        HSSFFont font = wb.createFont();
        HSSFFont font1 = wb.createFont();
        font.setFontHeightInPoints((short)9);
        font.setFontName("Arial");
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style1 = wb.createCellStyle();
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style1.setFont(font1);
        style.setFont(font);
        style.setDataFormat((short)4);
        wb.setPrintArea(0,"$A$1:$p$20");
        HSSFRow row = sheet.getRow((short)2);
        sheet.setZoom(3,4);  //75 percent zoom 
        HSSFCell cell = row.createCell((short) 1);
        cell.setCellValue(objPriceRunCustomerVO.getProspectiveCustomer().getCustomerName());
        cell.setCellStyle(style1);
        row = sheet.getRow((short)4);
        sheet.setZoom(3,4);  //75 percent zoom 
        cell = row.createCell((short) 1);
        cell.setCellValue(esiIdCount);
        cell.setCellStyle(style1);
        int i = 0;
        while(st.hasMoreTokens())
        {
            row = sheet.getRow((short)i+7);
            cell = row.createCell((short) 0);
            cell.setCellStyle(style1);
            cell.setCellValue(st.nextToken());
            i++;
        }
        
        HashMap hmWeekDay = (HashMap)objAll.get(new Integer(2));
        for(int rowCount=1;rowCount<=12;rowCount++)
        {
            if(null!=hmWeekDay)
            {
                HashMap hmDetails =(HashMap)hmWeekDay.get(new Integer(rowCount));
                for(int hourCount=1;hourCount<=hmDetails.size();hourCount++)
                {
                    row = sheet.getRow((short)rowCount+24);
                    cell = row.createCell((short)(hourCount+1));
                    cell.setCellStyle(style);
                    cell.setCellValue(((Float)hmDetails.get(new Integer(hourCount))).doubleValue());
                    i++;
                }
            }
        }
        
        HashMap hmWeekEnd = (HashMap)objAll.get(new Integer(3));
        for(int rowCount=1;rowCount<=12;rowCount++)
        {
            if(null!=hmWeekEnd)
            {
                HashMap hmDetails =(HashMap)hmWeekEnd.get(new Integer(rowCount));
                for(int hourCount=1;hourCount<=hmDetails.size();hourCount++)
                {
                    row = sheet.getRow((short)rowCount+37);
                    cell = row.createCell((short)(hourCount+1));
                    cell.setCellStyle(style);
                    cell.setCellValue(((Float)hmDetails.get(new Integer(hourCount))).doubleValue());
                    i++;
                }
            }
        }
        
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
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
*$Log: ExportToExcel.java,v $
*Revision 1.3  2009/01/27 05:47:57  tannamalai
*changes - according to MX server
*
*Revision 1.2  2008/01/29 07:03:36  tannamalai
*aggregated LP template changed , holidays added in the excel
*
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.2  2007/10/30 11:16:00  jnadesan
*Template file taken from local
*
*Revision 1.1  2007/10/30 05:51:58  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:30  jnadesan
*initail MXEP commit
*
*Revision 1.4  2007/06/13 04:10:44  kduraisamy
*2005 jar included.
*
*Revision 1.3  2007/05/09 14:23:04  jnadesan
*export to excel wormk finished
*
*Revision 1.1  2007/05/08 13:47:03  jnadesan
*load profile chart plotted by esiid wise and export excel option added
*
*
*/