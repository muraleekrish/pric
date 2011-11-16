/*
 * Created on Jul 24, 2007
 * 
 * Class Name MeterReadsAddAction.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pconfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

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

import com.savant.pricing.dao.MeterReadCyclesDAO;

/**
 * @author sramasamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class MeterReadsAddAction extends Action
{
        public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
        {
            String action = "failure";
            ActionErrors actionErrors = new ActionErrors();
            ActionMessages messages = new ActionMessages();
            
            if( form instanceof MeterReadsAddForm )
            {
                MeterReadsAddForm frm = (MeterReadsAddForm) form;
                
                if( frm.getFormActions().equalsIgnoreCase( "list" ) )
                {
                    action = "list";
                }
                else if( frm.getFormActions().equalsIgnoreCase( "apply" ) )
                {
                    MeterReadCyclesDAO objMeterReadCyclesDAO = new MeterReadCyclesDAO();
                    Calendar objCalendar    = Calendar.getInstance();
                    boolean addUpdateResult = false;
                    SimpleDateFormat sdf    = new SimpleDateFormat("MM-dd-yyyy");
                    
                    int tdsp        = Integer.parseInt(frm.getTdsp());
                    String cycle    = frm.getMeterReadCycle();
                    int year        = Integer.parseInt(frm.getYear());
                    String[] mnths  = frm.getMnths();
                    String strCycle = "";
                    StringTokenizer st = null;
                    if( cycle.equalsIgnoreCase( "All" ) )
                    {
                        try
                        {
	                        HashMap hmCycle =  objMeterReadCyclesDAO.getAllReadCycles( tdsp );
	                        if(hmCycle != null)
	                            cycle = this.getCycle( hmCycle );
	                    }
                        catch( Exception e )
                        {
                            e.printStackTrace();
                        }
                    }
                        
                    try
                    {
                        st = new StringTokenizer( cycle, "," );
                        objCalendar.clear();
                        while( st.hasMoreTokens() )
                        {
                            strCycle =  st.nextToken();
                            for( int loopCount=0; loopCount<mnths.length; loopCount++ )
                            {
                                if( !mnths[loopCount].equalsIgnoreCase("") )
                                {
                                    objCalendar.set( year, loopCount, 1 );
                                    addUpdateResult = objMeterReadCyclesDAO.updateMeterRead( tdsp, strCycle, objCalendar.getTime(), sdf.parse(mnths[loopCount]) );
                                }
                            }
                        }
                    }
                    catch( ParseException e )
                    {
                        e.printStackTrace();
                    }
                    if( addUpdateResult )
                    {
                        messages.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage( "common.Update.success" ) );
                        saveMessages( request,messages );
                    }
                    else 
                    {
                        actionErrors.add( ActionErrors.GLOBAL_ERROR, new ActionError( "common.Update.problem" ) );
                        saveErrors( request, actionErrors );
                    }
                    action = "success";	
                }
            }
            return mapping.findForward( action );
        }
        
        private String getCycle( HashMap hmCycle )
        {
            String cycle = "";
            Set set = hmCycle.keySet();
            Iterator it = set.iterator();
            while( it.hasNext() )
            {
                Object key = it.next();
                if( cycle.length() > 0 )
                    cycle += ",";
                cycle += ( String ) hmCycle.get( key );
            } 
            return cycle;
        }
}
