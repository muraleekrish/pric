/*
 * Created on Mar 23, 2007
 * 
 * Class Name PcDealLeversEditAction.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.pconfig.deallevers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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

import com.savant.pricing.calculation.dao.DealLeversDAO;
import com.savant.pricing.calculation.valueobjects.DealLeversVO;
import com.savant.pricing.calculation.valueobjects.UOMVO;
import com.savant.pricing.common.BuildConfig;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PcDealLeversEditAction extends Action{
    
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action="failure";
        
        if (form instanceof PcDealLeversEditForm)
        {
            
            PcDealLeversEditForm frm = (PcDealLeversEditForm)form;
            NumberFormat nf = new DecimalFormat("0.0000##");
            ActionErrors errors = new ActionErrors();
            ActionMessages messages = new ActionMessages();
            DealLeversDAO  objDealLeversDAO = new DealLeversDAO();
            DealLeversVO objDealLeversVO = new DealLeversVO();
            try
            {
                if((frm.getFormAction()).equalsIgnoreCase("edit"))
                { 
                    objDealLeversVO = objDealLeversDAO.getDealLever(Integer.parseInt(request.getParameter("dealLeverId")));
                    frm.setDealLever(objDealLeversVO.getDealLever());
                    frm.setDealLeverId(objDealLeversVO.getDealLeverIdentifier());
                    frm.setValue(nf.format(objDealLeversVO.getValue()));
                    frm.setCmboUnit(objDealLeversVO.getUnit().getUomIdentifier());
                    action="failure"; 
                }
                else if(frm.getFormAction().equals("Modify"))
                {
                    objDealLeversVO.setDealLever(frm.getDealLever());
                    objDealLeversVO.setDealLeverIdentifier(frm.getDealLeverId());
                    UOMVO objUOMVO = new UOMVO();
                    objUOMVO.setUomIdentifier(frm.getCmboUnit());
                    objDealLeversVO.setUnit(objUOMVO);
                    objDealLeversVO.setValue(Float.parseFloat(frm.getValue()));
                    boolean result = objDealLeversDAO.modifyDealLever(objDealLeversVO);
                    if(result==true)
                    {
                        action = "success";
                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("DealLever.modify.success"));
                        request.setAttribute("message","message");
                    }
                    else
                    {
                        errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("DealLever.modify.failuer"));
                        request.setAttribute("message","error");
                    }
                }
                else if(frm.getFormAction().equalsIgnoreCase("ApplytoAll"))
                {
                    if(BuildConfig.DMODE)
                        System.out.println(request.getParameter("dealLeverId"));
                    String dealleverid = request.getParameter("dealLeverId");
                    StringTokenizer stoken = new StringTokenizer(dealleverid,",");
                    int deallevercount = stoken.countTokens();
                    int i =0;
                    while(stoken.hasMoreTokens())
                    {
                        objDealLeversVO = objDealLeversDAO.getDealLever(Integer.parseInt(stoken.nextToken()));
                        //objDealLeversDAO.applyAll(objDealLeversVO);
                        i++;
                        if(BuildConfig.DMODE)
                            System.out.println("DealLevers" +objDealLeversVO.getDealLever());
                    }
                    if(deallevercount == i) 
                        action = "success";
                    if(deallevercount==1)
                    {
                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("DealLever.Applytoall.success",objDealLeversVO.getDealLever(),new Float(objDealLeversVO.getValue()).toString()));
                        request.setAttribute("message","message");
                    }
                    else
                    {
                        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("DealLever.Overrideall.success"));
                        request.setAttribute("message","message");
                    }
                }
            }
            catch(Exception se)
            {
                se.printStackTrace();
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("DealLever.modify.failuer",se.getMessage()));
                action = "failure";
            }
            if(!errors.isEmpty())
            {
                saveErrors(request,errors);
                request.setAttribute("message","error");
            }
            saveMessages(request,messages);
        }
        return mapping.findForward(action);
    }
}


/*
*$Log: PcDealLeversEditAction.java,v $
*Revision 1.1  2007/12/07 06:18:55  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:30  jnadesan
*initail MXEP commit
*
*Revision 1.9  2007/06/12 12:57:53  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.8  2007/05/26 06:45:47  spandiyarajan
*added message for browserheight
*
*Revision 1.7  2007/04/23 05:52:20  kduraisamy
*unwanted println removed.
*
*Revision 1.6  2007/04/22 15:43:22  jnadesan
*unwanted print removed
*
*Revision 1.5  2007/04/18 03:56:20  kduraisamy
*imports organized.
*
*Revision 1.4  2007/04/02 11:18:20  jnadesan
*deallevers added
*
*Revision 1.3  2007/03/26 04:57:51  jnadesan
*deallevers edit added
*
*Revision 1.2  2007/03/24 05:07:27  kduraisamy
*DealLevers edit
*
*Revision 1.1  2007/03/23 11:20:39  jnadesan
*System DealLevers added
*
*
*/