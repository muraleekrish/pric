/*
 * Created on Aug 22, 2007
 * 
 * Class Name ExportScheduleintoExcel.java
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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
import com.savant.pricing.common.NumberUtil;
import com.savant.pricing.dao.PICDAO;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.dao.ScheduleDAO;
import com.savant.pricing.transferobjects.TeamDetails;
import com.savant.pricing.valueobjects.CustomerCommentsVO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author spandiyarajan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExportScheduleintoExcel 
{
    public byte[] getSchedule(String userType,LinkedHashMap lhmSchedule)
    {
        HSSFWorkbook wb = null;
        byte[] b = null;
        if(userType.equalsIgnoreCase("Analyst"))
        {
            wb = this.getScheduleExcelFile(lhmSchedule);
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
            try
            {
                b= this.getBytesFromFile();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return b;
    }
    
    private HSSFWorkbook getScheduleExcelFile(LinkedHashMap lhmProspects)
    {
        HSSFWorkbook wb = new HSSFWorkbook();
        
        ScheduleDAO objScheduleDAO = new ScheduleDAO();
        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
        ProspectiveCustomerVO objProspectiveCustomerVO = null;
        HSSFSheet sheet = wb.createSheet("Customers");
        TeamDetails objTeamDetails = null;
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
        style1.setWrapText(true);
        style1.setFillBackgroundColor((short)150);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);
        HSSFRow row = sheet.createRow((short)0);
        HSSFCell cell = row.createCell((short) 0);
        
        cell.setCellValue("List of Schedule on :");
        Region objRegion = new Region();
        cell.setCellStyle(cellStyle);
        
        cell = row.createCell((short)1);
        cell.setCellStyle(style);
        cell.setCellValue(sdf.format(new Date()));
        
        row = sheet.createRow((short)2);
        int columnCount = 0;
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("S.No");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Customer Name");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("CMS Id");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("CDR Status");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("PIC Imported On");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Total ESIID's");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Selected ESIID's");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Sales Rep");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Sales Manager");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Contract Start Month");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Products");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Terms");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("CC");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("AVP");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("SAF");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("AF");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("BWC");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("OF");
        
        cell = row.createCell((short)(columnCount++));
        cell.setCellStyle(style1);
        cell.setCellValue("Margin");
	        
        if(lhmProspects.size()>0)
  		{        
            Set setProspect = lhmProspects.keySet();
            Iterator iteProspect = setProspect.iterator();
            List vecValidESIID  = null;	List custDetails = null;
            LinkedHashMap lhmProducts = new LinkedHashMap();
            LinkedHashMap lhmTerms = new LinkedHashMap();
            Set commentSet = null;
            boolean autoRun = false;
            String toolTip = ""; String salesRep = ""; String salesMgr = "";
            SimpleDateFormat mnthFormat = new SimpleDateFormat("MMM yyyy");
            SimpleDateFormat sdfhrmin = new SimpleDateFormat("MM-dd-yyyy HH:mm");
            NumberFormat tnf = NumberUtil.tetraFraction();
            PICDAO objPICDAO = new PICDAO();
            CustomerPreferencesVO objCustomerPreferencesVO = null;
            CustomerCommentsVO objCustomerCommentsVO = null;
            
            // get Prospective Customers List
        	List lstProspectCust = objScheduleDAO.getProspectivCust();
        	
        	// get Autorun Customers
        	HashMap hmAutorunCust = objScheduleDAO.getAutoRunCust();
        	
        	// get Total ESIID'd by Customer
        	HashMap hmTotEsiids = objScheduleDAO.getTotEsiidbyCust();
        	
        	// get Total ESIID'd by Customer
        	HashMap hmTotValidEsiids = objScheduleDAO.getTotValidEsiidbyCust();
            
            int rowCount = 3;
	        int sNo = 0;
	        String oldProductKey = "";
	        String oldTermKey = "";
	        while(iteProspect.hasNext())
	        {
	            sNo++;
	            String productKey = (String)iteProspect.next();
	            lhmProducts = (LinkedHashMap)lhmProspects.get(productKey);
	            oldProductKey = productKey;
	            
	            int prospectId = Integer.parseInt(productKey);

	            Iterator itrProspectCust = lstProspectCust.iterator();
				while(itrProspectCust.hasNext())
				{
					objProspectiveCustomerVO = (ProspectiveCustomerVO)itrProspectCust.next();
					if(objProspectiveCustomerVO.getProspectiveCustomerId() == prospectId)
						break;
				}
	            
				String cmsId = objProspectiveCustomerVO.getCustomerId()==null?"":objProspectiveCustomerVO.getCustomerId()+"";
				String importPicOn = "";
				if(objProspectiveCustomerVO.getImportedPICOn()!=null)
					importPicOn = sdfhrmin.format(objProspectiveCustomerVO.getImportedPICOn());
				else
					importPicOn = "";
				objTeamDetails =  objProspectiveCustomerDAO.getTeam(prospectId);
				salesRep = objTeamDetails.getSalesRep().getFirstName()+" "+objTeamDetails.getSalesRep().getLastName();
				salesMgr = objTeamDetails.getSalesManager().getFirstName()+" "+objTeamDetails.getSalesManager().getLastName();
				String totEsiid = "";					
				if(hmTotEsiids.get(new Integer(prospectId))!=null)
					totEsiid = ""+hmTotEsiids.get(new Integer(prospectId));
				else
					totEsiid = "";
				String totValEsiid = "";
				if(hmTotValidEsiids.get(new Integer(prospectId))!=null)
					totValEsiid = ""+hmTotValidEsiids.get(new Integer(prospectId));
				else
					totValEsiid = "";
				
				toolTip = importPicOn+"+"+totEsiid+"+"+totValEsiid+"+"+salesRep+"+"+salesMgr+"+"+mnthFormat.format(objProspectiveCustomerVO.getContractStartDate());
				objCustomerPreferencesVO = objProspectiveCustomerDAO.getProspectiveCustomerPreferences(prospectId);
				autoRun = false;
	   			autoRun = ((Boolean)hmAutorunCust.get(new Integer(prospectId))).booleanValue();
			   	
	            columnCount = 0;
	            row = sheet.createRow((short)rowCount++);
	            
	            cell = row.createCell((short)(columnCount++));
	            cell.setCellStyle(style);
	            cell.setCellValue(sNo);
	            
	            cell = row.createCell((short)(columnCount++));
	            cell.setCellStyle(style);
	            if(objProspectiveCustomerVO!=null)
	                cell.setCellValue(objProspectiveCustomerVO.getCustomerName()==null?"":objProspectiveCustomerVO.getCustomerName());
	            
	            cell = row.createCell((short)(columnCount++));
	            cell.setCellStyle(style);
	            cell.setCellValue(cmsId);
	            
	            cell = row.createCell((short)(columnCount++));
	            cell.setCellStyle(style);
	            cell.setCellValue(objProspectiveCustomerVO.getCdrStatus().getCdrState()==null?"":objProspectiveCustomerVO.getCdrStatus().getCdrState());
	            
	            cell = row.createCell((short)(columnCount++));
	            cell.setCellStyle(style);
	            cell.setCellValue(importPicOn);
	            
	            cell = row.createCell((short)(columnCount++));
	            cell.setCellStyle(style);
	            System.out.println("prospectId:"+prospectId);
	            cell.setCellValue(((Long)hmTotEsiids.get(new Integer(prospectId))).longValue());
	            
	            cell = row.createCell((short)(columnCount++));
	            cell.setCellStyle(style);
	            cell.setCellValue(((Long)hmTotValidEsiids.get(new Integer(prospectId))).longValue());
	            
	            cell = row.createCell((short)(columnCount++));
	            cell.setCellStyle(style);
	            cell.setCellValue(salesRep);
	            
	            cell = row.createCell((short)(columnCount++));
	            cell.setCellStyle(style);
	            cell.setCellValue(salesMgr);
	            
	            cell = row.createCell((short)(columnCount++));
	            cell.setCellStyle(style);
	            cell.setCellValue(mnthFormat.format(objProspectiveCustomerVO.getContractStartDate()));
	            
	            Set setProducts = lhmProducts.keySet();
	            Iterator iteProducts = setProducts.iterator();
	            while(iteProducts.hasNext())
		        {
		            String termKey = (String)iteProducts.next();
	                lhmTerms = (LinkedHashMap)lhmProducts.get(termKey);
	                oldTermKey = termKey;
	                
	                columnCount = 10;
		            cell = row.createCell((short)(columnCount++));
		            cell.setCellStyle(style);
		            cell.setCellValue(termKey);
		            
		            Set setTerms = lhmTerms.keySet();
		            Iterator iteTerms = setTerms.iterator();
		            while(iteTerms.hasNext())
			        {
		                String strTerm = (String)iteTerms.next();
		                HashMap hmDealLevers = (HashMap)lhmTerms.get(strTerm);
		                
		                if(productKey.equalsIgnoreCase(oldProductKey))
					   	{
					   	    for(int i=0; i<=9; i++)
					   	    {
					   	    objRegion.setColumnTo((short)i);
					   	    objRegion.setColumnFrom((short)i);
					   	    objRegion.setRowFrom((short)(rowCount-1));
					   	    objRegion.setRowTo((short)(rowCount));
				            sheet.addMergedRegion(objRegion);
					   	    }
					   	}
		                
		                columnCount = 11;
		                cell = row.createCell((short)(columnCount++));
			            cell.setCellStyle(style);
			            cell.setCellValue(new Double(strTerm).doubleValue());
			            
			            cell = row.createCell((short)(columnCount++));
			            cell.setCellStyle(style);
			            cell.setCellValue(new Double(tnf.format(((Float)hmDealLevers.get(new Integer(1))).floatValue())).doubleValue());
			            
			            cell = row.createCell((short)(columnCount++));
			            cell.setCellStyle(style);
			            cell.setCellValue(new Double(tnf.format(((Float)hmDealLevers.get(new Integer(7))).floatValue())).doubleValue());
			            
			            cell = row.createCell((short)(columnCount++));
			            cell.setCellStyle(style);
			            cell.setCellValue(new Double(tnf.format(((Float)hmDealLevers.get(new Integer(4))).floatValue())).doubleValue());
			            
			            cell = row.createCell((short)(columnCount++));
			            cell.setCellStyle(style);
			            cell.setCellValue(new Double(tnf.format(((Float)hmDealLevers.get(new Integer(5))).floatValue())).doubleValue());
			            
			            cell = row.createCell((short)(columnCount++));
			            cell.setCellStyle(style);
			            cell.setCellValue(new Double(tnf.format(((Float)hmDealLevers.get(new Integer(6))).floatValue())).doubleValue());
			            
			            cell = row.createCell((short)(columnCount++));
			            cell.setCellStyle(style);
			            cell.setCellValue(new Double(tnf.format(((Float)hmDealLevers.get(new Integer(2))).floatValue())).doubleValue());
			            
			            cell = row.createCell((short)(columnCount++));
			            cell.setCellStyle(style);
			            cell.setCellValue(new Double(tnf.format(((Float)hmDealLevers.get(new Integer(3))).floatValue())).doubleValue());
			            
			            row = sheet.createRow((short)rowCount++);
			        }
		        }
	            
	            commentSet = objProspectiveCustomerVO.getCustomerComments();
	            cell = row.createCell((short)(columnCount-9));
	            cell.setCellStyle(style);
	            cell.setCellValue("Comments");
	            
			  	int size = commentSet.size();
			    if(size>0)
			    {
			        Iterator itr = commentSet.iterator();
				  	if(itr.hasNext())
				  	{
				  		objCustomerCommentsVO = (CustomerCommentsVO)itr.next();
	            
			            cell = row.createCell((short)(columnCount-8));
			            cell.setCellStyle(style);
			            cell.setCellValue(objCustomerCommentsVO.getComments());
				  	}
			    }
			    else
			    {
		            cell = row.createCell((short)(columnCount-8));
		            cell.setCellStyle(style);
		            cell.setCellValue("-- No Comments --");
			    }
	        }
	        row = sheet.createRow((short)rowCount++);
  		}
        else
        {
            cell = row.createCell((short)(columnCount-7));
            cell.setCellStyle(style);
            cell.setCellValue("-- No Data");
        }
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
*
*
*/