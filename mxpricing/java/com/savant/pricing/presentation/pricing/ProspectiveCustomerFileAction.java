/*
 * Created on Aug 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;



import java.util.Date;
import java.util.List;
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
import org.hibernate.HibernateException;
import com.savant.pricing.dao.CustomerFileDAO;
import com.savant.pricing.valueobjects.CustomerFileTypesVO;
import com.savant.pricing.valueobjects.CustomerFilesVO;
import com.savant.pricing.valueobjects.ProspectiveCustomerVO;

/**
 * @author rraman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProspectiveCustomerFileAction extends Action 
{

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    { 
        String action="failure";
        ActionErrors actionErrors = new ActionErrors();
        ActionMessages actionMessages = new ActionMessages();
        boolean result=false;
        if(form instanceof ProspectiveCustomerFileForm)
        {
        	ProspectiveCustomerFileForm frm = (ProspectiveCustomerFileForm)form;
        	if(frm.getFormActions().equalsIgnoreCase("deleteProspectiveCustomerFile"))
            {
            	CustomerFileDAO objCustomerFileDAO = new CustomerFileDAO();
            	CustomerFilesVO objCustomerFilesVO = new CustomerFilesVO();
            	CustomerFileTypesVO objCustomerFileTypesVO = new CustomerFileTypesVO();
            	ProspectiveCustomerVO objProspectiveCustomerVO = new ProspectiveCustomerVO();
            	try
				{
            		objCustomerFileTypesVO.setFileTypeIdentifier(Integer.parseInt(frm.getFileTypeId()));
            		objCustomerFilesVO.setFileName(frm.getFileName());
            		objCustomerFilesVO.setFileType(objCustomerFileTypesVO);
            		objProspectiveCustomerVO.setProspectiveCustomerId(Integer.parseInt(frm.getCustId()));
            		objCustomerFilesVO.setProspectiveCust(objProspectiveCustomerVO);
            		boolean delete = objCustomerFileDAO.deleteCustomerFile(objCustomerFilesVO);
            		if(delete)
            			action = "success";
            		else
            			action = "failure";
            		
				}
            	catch(Exception e)
				{
            		e.printStackTrace();
				}
            }
        	if(frm.getFormActions().equalsIgnoreCase("addProspectiveCustomerFile"))
            {
        	    FormFile myFile = frm.getTheFile();
            	CustomerFileDAO objCustomerFileDAO = new CustomerFileDAO();
            	CustomerFileTypesVO objCustomerFileTypesVO = new CustomerFileTypesVO();
            	ProspectiveCustomerVO objProspectiveCustomerVO = new ProspectiveCustomerVO();
            	List lstResult = null;
            	try
            	{
            		
            	    if(myFile.getFileSize()<=0)
            	    { 
            	        
            	    	actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.customer.import.nofile"));
            	    }
            	    else
            	    {
            	        lstResult = objCustomerFileDAO.getFile(Integer.parseInt(frm.getCustId()),myFile.getFileName(),Integer.parseInt(frm.getFileTypeId()));
            	    	if((lstResult!=null && lstResult.size()>0))
            	    	{
            	    		actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("Prospective.customer.import.samefile"));
            	    	}
            	    	else
            	    	{
            	    	    CustomerFilesVO objCustomerFilesVO = new CustomerFilesVO();
            	    		objCustomerFileTypesVO.setFileTypeIdentifier(Integer.parseInt(frm.getFileTypeId()));
            	    		objCustomerFilesVO.setFileType(objCustomerFileTypesVO);
            	    		objCustomerFilesVO.setFileName(myFile.getFileName());
	            	        objCustomerFilesVO.setCreatedDate(new Date());
	            	        objCustomerFilesVO.setDescription(frm.getDesc());
	            	        objCustomerFilesVO.setCreatedBy((String)request.getSession().getAttribute("userName"));
	            	        objProspectiveCustomerVO.setProspectiveCustomerId(Integer.parseInt(frm.getCustId()));
	            	        objCustomerFilesVO.setProspectiveCust(objProspectiveCustomerVO);
	            	        objCustomerFileDAO.addOrupdateCustomerFile(objCustomerFilesVO,myFile.getFileData());
	            	        actionMessages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("Prospective.customer.import.success"));
	            	        frm.setDesc("");
            	    	}
            	    }
            	     
            	}
            	catch (HibernateException e) 
				{
            		e.printStackTrace();
            	    
            	    if(e.getCause().getMessage().compareTo(e.getCause().getMessage()) >= 0)
            	    {
            	   
            	        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("export.to.CMS.error"));
            	   
            	    }
                }
            	catch(Exception e)
				{
            		if(e.getCause().getMessage().compareTo(e.getCause().getMessage()) >= 0)
            	    {
            	    	
            	        actionErrors.add(ActionErrors.GLOBAL_ERROR,new ActionError("export.to.CMS.error"));
            	        
            	    }
            		e.printStackTrace();
				}
            }
        
        }
        if(!actionErrors.isEmpty())
        {
            action = "failure";
            saveErrors(request, actionErrors);
        }
        return mapping.findForward(action);
    }
}
