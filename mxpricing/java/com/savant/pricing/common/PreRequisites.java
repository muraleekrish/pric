/*
 * Created on Apr 17, 2007
 *
 * ClassName	:  	PreRequisites.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savant.pricing.common;

import java.sql.CallableStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.savant.pricing.dao.CalendarDAO;
import com.savant.pricing.dao.DLFCodeDAO;
import com.savant.pricing.dao.DLFDAO;
import com.savant.pricing.dao.EnergyChargeRatesDAO;
import com.savant.pricing.dao.ForwardCurveBlockDAO;
import com.savant.pricing.dao.ForwardCurveProfileDAO;
import com.savant.pricing.dao.GasPriceDAO;
import com.savant.pricing.dao.LossFactorLookupDAO;
import com.savant.pricing.dao.PriceBlockDAO;
import com.savant.pricing.dao.ShapingPremiumRatioDAO;
import com.savant.pricing.dao.TLFDAO;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PreRequisites extends HttpServlet
{

    public void init()
    {
        try
        {
            this.loadPrerequisites();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private void loadPrerequisites() throws HibernateException
    {
        try
        {
            CalendarDAO.getAllHolidays();
            ForwardCurveProfileDAO.getAllForwardCurves();
            this.loadShapingPremiumRatios();
            ShapingPremiumRatioDAO.getAllShapingPremiumRatios();
            EnergyChargeRatesDAO.getAllEnergyChargeRates();
            ForwardCurveBlockDAO.getAllForwardCurves();
            PriceBlockDAO objPriceBlockDAO  = new PriceBlockDAO();
            HibernateUtil.priceBlock5x16Id = objPriceBlockDAO.getPriceBlockIdByName("5x16");
            HibernateUtil.priceBlock2x16Id = objPriceBlockDAO.getPriceBlockIdByName("2x16");
            HibernateUtil.priceBlock7x8Id = objPriceBlockDAO.getPriceBlockIdByName("7x8");
            LossFactorLookupDAO.getAllLossFactorLookupVOS();
            DLFCodeDAO.getAllDLFCodeIdentifiers();
            DLFDAO.getAllDLF();
            TLFDAO objTLFDAO = new TLFDAO();
            HibernateUtil.hmTLF = objTLFDAO.getAllTLF();
            GasPriceDAO.getAllNGFutureTerminations();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private void loadShapingPremiumRatios() throws HibernateException, SQLException
    {
        Session objSession = null;
        CallableStatement cstmnt = null;
        try
        {
            objSession = HibernateUtil.getSession();
            objSession.beginTransaction();
            cstmnt = objSession.connection().prepareCall("{call sp_InsertRatioFrmScalars}");
            cstmnt.execute();
            objSession.getTransaction().commit();
        }
        catch(HibernateException e)
        {
            objSession.getTransaction().rollback();
            e.printStackTrace();
        }
        finally
        {
            cstmnt.close();
            objSession.close();
        }
	}
    

}

/*
*$Log: PreRequisites.java,v $
*Revision 1.1  2007/12/07 06:18:44  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:28  jnadesan
*initail MXEP commit
*
*Revision 1.5  2007/08/17 11:18:00  kduraisamy
*NG Termination tables and lookups added.
*
*Revision 1.4  2007/07/31 12:27:18  spandiyarajan
*added rolback transaction in catch block
*
*Revision 1.3  2007/07/31 11:39:43  kduraisamy
*objsession properly closed or calling procedure properly done.
*
*Revision 1.2  2007/06/13 04:10:44  kduraisamy
*2005 jar included.
*
*Revision 1.1  2007/04/17 13:48:46  kduraisamy
*price run performance took place.
*
*
*/