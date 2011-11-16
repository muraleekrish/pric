/*
 * Created on Apr 26, 2007
 *
 * ClassName	:  	AutoMXenergyPriceRun.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.common;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.savant.pricing.calculation.dao.PricingDAO;
import com.savant.pricing.dao.ProspectiveCustomerDAO;
import com.savant.pricing.mailer.MailManager;
import com.savant.pricing.timer.MXenergyTimerTask;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AutoMXenergyPriceRun extends MXenergyTimerTask{
	private Logger log = Logger.getLogger(AutoMXenergyPriceRun.class);
	
	public void runTask(){
		System.out.println("MXenergy Auto Run Timer Started.");
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
			System.out.println(" auto run result :"+isCustomer);
			if(isCustomer)
				sendMail = objMailManager.sendMail();
			else
				objMailManager.sentMailFailed();
			
			System.out.println("MXenergy Auto Run Timer End.");
	}
	
	
	
	public static void main(String[] args) throws SQLException
	{
		AutoMXenergyPriceRun autoRun = new AutoMXenergyPriceRun();
		autoRun.run();
	}
}

/*
*$Log: AutoMXenergyPriceRun.java,v $
*Revision 1.4  2008/02/14 05:43:48  tannamalai
*auto run mail problem solved
*
*Revision 1.3  2007/12/12 13:33:06  jvediyappan
*indentation.
*
*Revision 1.2  2007/12/12 10:33:30  jvediyappan
*indentation.
*
*Revision 1.1  2007/12/12 09:54:56  jvediyappan
*initial commit.
*
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.3  2007/11/28 14:07:09  jvediyappan
*email id for analyst as a bcc added for testing purpose.
*
*Revision 1.2  2007/11/17 05:46:35  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.12  2007/08/23 14:33:31  jnadesan
*MMCust run option added
*
*Revision 1.11  2007/08/23 07:26:40  jnadesan
*imports organized
*
*Revision 1.10  2007/08/03 12:22:18  kduraisamy
*cpe creation with the run.
*
*Revision 1.9  2007/07/12 12:05:04  jnadesan
*cpe creation added for manual run too
*
*Revision 1.8  2007/05/08 12:16:13  kduraisamy
*getAggregatedProfileDetails() added.
*
*Revision 1.7  2007/05/03 09:17:49  jnadesan
*automatic pricerun will creates cpe and contract automatically.
*
*Revision 1.6  2007/05/03 09:10:32  jnadesan
*method added for listing successive customer
*
*Revision 1.5  2007/04/30 09:05:18  kduraisamy
*forward Curve Date included.
*
*Revision 1.4  2007/04/30 05:33:38  kduraisamy
* failure mail calling added.
*
*Revision 1.3  2007/04/28 05:32:29  kduraisamy
*run return type boolean added.
*
*Revision 1.2  2007/04/26 13:02:07  kduraisamy
*runType Added.
*
*Revision 1.1  2007/04/26 12:28:19  kduraisamy
*runType Added.
*
*
*/