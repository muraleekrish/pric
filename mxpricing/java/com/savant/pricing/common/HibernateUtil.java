/*
 * Created on Jan 16, 2007
 *
 * ClassName	:  	HibernateUtil.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.common;



import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.MatchMode;


/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HibernateUtil
{
    public static final String STARTS_WITH = "startswith";
    public static final String ENDS_WITH = "endswith";
    public static final String EXACTLY = "exactly";
    public static final String ANYWHERE = "anywhere";
    public static int priceBlock5x16Id = 0;
    public static int priceBlock2x16Id = 0;
    public static int priceBlock7x8Id = 0;
    public static boolean applyLoss = true;
    public static final Hashtable htMatchCase = new Hashtable();
    public static StringBuffer sbCost = new StringBuffer();
    public static List objUsageVOS = null;
    public static HashMap hmTLF = new HashMap();
    private static final SessionFactory sessionFactory;
    static
    {
        try
        {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        catch(HibernateException ex) 
        {
            ex.printStackTrace();
            throw new RuntimeException("Exception building SessionFactory: "+ ex.getMessage(), ex);
        }
        
        htMatchCase.put(STARTS_WITH,MatchMode.START);
        htMatchCase.put(ENDS_WITH,MatchMode.END);
        htMatchCase.put(EXACTLY,MatchMode.EXACT);
        htMatchCase.put(ANYWHERE,MatchMode.ANYWHERE);
        
    }
    public static Session getSession()
    {
        return sessionFactory.openSession();
    }
    
}

/*
*$Log: HibernateUtil.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.18  2007/06/13 04:10:44  kduraisamy
*2005 jar included.
*
*Revision 1.17  2007/04/17 13:48:46  kduraisamy
*price run performance took place.
*
*Revision 1.16  2007/04/16 13:19:15  kduraisamy
*apply Loss set as true.
*
*Revision 1.15  2007/04/06 12:32:29  kduraisamy
*TLF AND DLF COMPLETED.
*
*Revision 1.14  2007/03/27 04:43:05  kduraisamy
*imports organized.
*
*Revision 1.13  2007/03/24 08:04:51  rraman
*private variable changed tp public
*
*Revision 1.12  2007/03/21 10:26:02  jnadesan
*Price block id taken by name.
*
*Revision 1.11  2007/03/09 08:52:27  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.10  2007/03/08 16:32:33  kduraisamy
*Optimization with Sriram Completed.
*
*Revision 1.9  2007/02/14 09:14:15  kduraisamy
*esiId wise details stored in static hashMap.
*
*Revision 1.8  2007/01/31 09:31:56  kduraisamy
*general addFilter method removed.
*
*Revision 1.7  2007/01/30 15:00:05  kduraisamy
*variable scope changed to public.
*
*Revision 1.6  2007/01/30 11:50:59  kduraisamy
*setFirstResult() added.
*
*Revision 1.5  2007/01/30 07:43:17  kduraisamy
*anywhere added in the condition.
*
*Revision 1.4  2007/01/29 11:03:00  kduraisamy
*filter method added separately.
*
*Revision 1.3  2007/01/27 10:19:20  kduraisamy
*printStackTrace() included.
*
*Revision 1.2  2007/01/24 11:01:37  kduraisamy
*instead of session factory returned session itself.
*
*Revision 1.1  2007/01/22 13:35:58  kduraisamy
*initial commit.
*
*
*/