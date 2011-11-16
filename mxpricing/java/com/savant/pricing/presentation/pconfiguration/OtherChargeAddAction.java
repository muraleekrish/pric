/*
 * Created on Jun 11, 2007
 * 
 * Class Name OtherChargeAddAction.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pconfiguration;

import java.util.Calendar;
import java.util.Iterator;
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

import com.savant.pricing.calculation.valueobjects.EnergyChargeNamesVO;
import com.savant.pricing.dao.CongestionZonesDAO;
import com.savant.pricing.dao.EnergyChargeRatesDAO;
import com.savant.pricing.valueobjects.CongestionZonesVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OtherChargeAddAction extends Action
{
        public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
        {
            String action="failure";        
            ActionErrors actionErrors = new ActionErrors();
            ActionMessages messages = new ActionMessages();
            boolean addUpdateResult = false;
            if(form instanceof OtherChargesAddForm)
            {
                OtherChargesAddForm frm = (OtherChargesAddForm)form;
                if(frm.getFormActions().equalsIgnoreCase("list"))
                {
                    action = "list";
                }
                else  if(frm.getFormActions().equalsIgnoreCase("apply"))
                {
                    try
                    {
                        CongestionZonesDAO objCongestionZonesDAO = new CongestionZonesDAO();
                        boolean rampRate = true;
                        EnergyChargeRatesDAO objEnergyChargeRatesDAO = new EnergyChargeRatesDAO();
                        EnergyChargeNamesVO objEnergyChargeNamesVO = null;
                        List lstCongeston = null;
                        int enrgyId = Integer.parseInt(frm.getCmbEnergeChrgeId());
                        float value = 0;
                        if(!frm.getTxtValue().equalsIgnoreCase(""))
                            value = Float.parseFloat(frm.getTxtValue());
                        objEnergyChargeNamesVO = objEnergyChargeRatesDAO.getEnergyChargeName(enrgyId);
                        if(objEnergyChargeNamesVO.getChargeName().startsWith("Ramp Up"))
                            rampRate = false;
                        if(rampRate)
                        {
                            int congId = Integer.parseInt(frm.getCmbCongestionId());
                            if(congId==0)
                            {
                                lstCongeston = objCongestionZonesDAO.getAllCongestionZones();
                                Iterator iteZone = lstCongeston.iterator();
                                while(iteZone.hasNext())
                                {
                                    congId = ((CongestionZonesVO)iteZone.next()).getCongestionZoneId();
                                    this.update(congId,enrgyId,Integer.parseInt(frm.getCmbUnit()),Integer.parseInt(frm.getCmbYear()),frm.getMonth(),value);
                                }
                            }
                            else
                            {
                                this.update(congId,enrgyId,Integer.parseInt(frm.getCmbUnit()),Integer.parseInt(frm.getCmbYear()),frm.getMonth(),value);
                            }
                            
                        }
                        else
                        {
                            this.update(0,enrgyId,Integer.parseInt(frm.getCmbUnit()),Integer.parseInt(frm.getCmbYear()),frm.getMonth(),value);
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                        actionErrors.add(actionErrors.GLOBAL_ERROR,new ActionError("common.Update.failure"));
                        saveErrors(request,actionErrors);
                    }
                    if(addUpdateResult)
                    {
                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.Update.success"));
                        saveMessages(request,messages);
                    }
                    
                }
                frm.setMonth(null);
            }
            return mapping.findForward(action);
        }
        private boolean update(int congestionId,int enrgyId,int unitId,int yr,String[] strMnth,float value)
        {
            Calendar objCalendar = Calendar.getInstance();EnergyChargeRatesDAO objEnergyChargeRatesDAO = new EnergyChargeRatesDAO();
            boolean addUpdateResult = false;
            String[] mnth = {"0","1","2","3","4","5","6","7","8","9","10","11"};
            if(strMnth!=null)
            {
                mnth = strMnth;
            }
            for(int loopCount=0;loopCount<mnth.length;loopCount++)
            {
                objCalendar.clear();
                objCalendar.set(yr,Integer.parseInt(mnth[loopCount]),1);
               
                addUpdateResult = objEnergyChargeRatesDAO.updateEnergyCharge(enrgyId,congestionId,objCalendar.getTime(),value,unitId);
            }
            objEnergyChargeRatesDAO.reload();
            return addUpdateResult;
        }
        
}


/*
*$Log: OtherChargeAddAction.java,v $
*Revision 1.2  2008/04/24 05:56:42  tannamalai
*reload method called
*
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/07/23 11:07:04  spandiyarajan
*removed unwanted imports
*
*Revision 1.1  2007/07/11 13:21:28  jnadesan
*energy chargerates add/update provision given
*
*
*/