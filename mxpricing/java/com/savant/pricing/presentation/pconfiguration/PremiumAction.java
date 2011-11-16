/*
 * Created on May 17, 2007
 * 
 * Class Name PremiumAction.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pconfiguration;

import java.util.ArrayList;
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

import com.savant.pricing.dao.PremiumDAO;
import com.savant.pricing.dao.ShappingPremiumDAO;
import com.savant.pricing.valueobjects.PremiumVO;
import com.savant.pricing.valueobjects.ShappingPremiumVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PremiumAction extends Action{
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response)
    {
        String action="failure";        
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
        boolean resPremFlag = false;
        boolean resShapFlag = false;
        if(form instanceof PremiumForm) 
        {
            PremiumForm frm = (PremiumForm)form;
            if(frm.getFormActions().equalsIgnoreCase("edit"))
            {
                String[] valuespremium =  request.getParameter("premiumUpdate").split("premiumstr");
                List lstPremium= new ArrayList();
                List lstShapePremium= new ArrayList();
                for(int i=0;i<valuespremium.length;i++)
                {
                    PremiumVO objPremiumVO = new PremiumVO();
                    String[] splitValues = valuespremium[i].split(",");
                    objPremiumVO.setPremiumId(Integer.parseInt(splitValues[0]));
                    objPremiumVO.setPremiumType(splitValues[1]);
                    objPremiumVO.setValue(Float.parseFloat(splitValues[2]));
                    lstPremium.add(objPremiumVO);
                }
                PremiumDAO objPremiumDAO = new PremiumDAO();
                resPremFlag = objPremiumDAO.updatePremiumVOs(lstPremium);
                
                String[] valuescombo = request.getParameter("shapeUpdate").split("combo");
                for(int i=0;i<valuescombo.length;i++)
                {
                    ShappingPremiumVO objShappingPremiumVO = new ShappingPremiumVO();
                    String[] splitValues = valuescombo[i].split(",");
                    objShappingPremiumVO.setShappingPremiumId(Integer.parseInt(splitValues[0]));
                    objShappingPremiumVO.setShappingPremiumType(splitValues[1]);
                    objShappingPremiumVO.setApply(splitValues[2].equals("true"));
                    lstShapePremium.add(objShappingPremiumVO);
                }
                ShappingPremiumDAO objShappingPremiumDAO = new ShappingPremiumDAO();
                resShapFlag = objShappingPremiumDAO.updateShappingPremium(lstShapePremium);
                
                if(resPremFlag&&resShapFlag)
                {
                    messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("common.Update.success"));
                    saveMessages(request,messages);
                }
                else
                {
                    messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage(""));
                    errors.add(ActionErrors.GLOBAL_ERROR,new ActionError("common.Update.failure"));
                    saveErrors(request,errors);
                }
            }
        }
        return mapping.findForward(action);
    }
}


/*
*$Log: PremiumAction.java,v $
*Revision 1.1  2007/12/07 06:18:37  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:42  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:21  jnadesan
*initail MXEP commit
*
*Revision 1.2  2007/05/18 14:13:57  spandiyarajan
*premium edit functionality over
*
*Revision 1.1  2007/05/18 03:38:14  jnadesan
*initial commit
*
*
*/