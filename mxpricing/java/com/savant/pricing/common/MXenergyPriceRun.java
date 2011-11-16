/*
 * Created on Dec 12, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.common;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.savant.pricing.calculation.dao.PricingDAO;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.mailer.MailManager;

/**
 * @author jvediyappan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MXenergyPriceRun{
	
			public void execute() {
	        PreRequisites objPreRequisites = new PreRequisites();
	        MailManager objMailManager = new MailManager();
	        PricingDAO pricingDAO = new PricingDAO();
	        boolean isCustomer=false;
	        boolean sendMail =false;
	        objPreRequisites.init();
	        ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
	        if(!(objProspectiveCustomerDAO.getAllAutoRunProspectives()).equalsIgnoreCase(""))
				try {
					isCustomer = pricingDAO.execute(objProspectiveCustomerDAO.getAllAutoRunProspectives(),"A","Automatic Pricing Call",false);
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        if(isCustomer)
	             sendMail = objMailManager.sendMail();
	        else
	            objMailManager.sentMailFailed();
	    
			
		}
	    public Collection lastAutomaticRunCustomers(){
	    	Session objSession = null;
	    	List objList = null;
	    	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	    	Date cuurDate = new Date();
	    	try
			{
	    		objSession = HibernateUtil.getSession();
	    		Query objQuery = objSession.createQuery("From PriceRunCustomerVO as priceRunCustomer where priceRunCustomer.priceRunRef.priceRunRefNo in (select header.priceRunRefNo from PriceRunHeaderVO as header where header.runType = ? and header.priceRunTime > ?)");
	    		objQuery.setString(0,"A");
	    		objQuery.setDate(1,sdf.parse(sdf.format(cuurDate)));
	    		objList = objQuery.list();
			}
	    	catch(HibernateException e)
			{
	    		e.printStackTrace();
			}
	    	catch (Exception e)
			{
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
			}
	    	finally
			{
	    		objSession.close();
			}
	    	return objList;
	    }
	    public Collection lastAutomaticSuccessRunCustomers(String runType)
	    {
	        Session objSession = null;
	        List objList = null;
	        try
	        {
	            objSession = HibernateUtil.getSession();
	            Query objQuery = objSession.createQuery("From PriceRunCustomerVO as priceRunCustomer where priceRunCustomer.runStatus = ? and priceRunCustomer.priceRunRef.priceRunRefNo in (select max(header.priceRunRefNo) from PriceRunHeaderVO as header where header.runType = ?)");
	            objQuery.setBoolean(0,true);
	            objQuery.setString(1,runType);
	            objList = objQuery.list();
	        }
	        catch(HibernateException e)
	        {
	            e.printStackTrace();
	        }
	        catch (Exception e)
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        finally
	        {
	            objSession.close();
	        }
	        return objList;
	    }
	    
		 public static void main(String[] args) throws SQLException
		    {
		        AutoMXenergyPriceRun autoRun = new AutoMXenergyPriceRun();
		        autoRun.run();
		    }
}
