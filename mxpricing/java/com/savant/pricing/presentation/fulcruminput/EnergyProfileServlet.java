/*
 * Created on Mar 22, 2007
 * 
 * Class Name EnergyProfileServlet.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.fulcruminput;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.calculation.valueobjects.ForwardCurveProfileVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.common.NumberUtil;
import com.savant.pricing.common.chart.FulcrumCurve;
import com.savant.pricing.dao.ForwardCurveProfileDAO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EnergyProfileServlet extends HttpServlet{
    
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)throws ServletException, IOException 
    {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
        ForwardCurveProfileDAO objForwardCurveProfileDAO = new ForwardCurveProfileDAO();
        ForwardCurveProfileVO  objForwardCurveProfileVO = new ForwardCurveProfileVO(); 
        String zoneid = request.getParameter("weatherZoneId");
        String profileId = request.getParameter("profileId");
        NumberFormat df = NumberUtil.doubleFraction();
        DateFormat dateformat = new SimpleDateFormat("MMM yyyy");
        String show = "";
        String table = "<table width='100%' border='0' cellspacing='0' cellpadding='0'  style='display:block'>";
        List lstProfiledetails = (List)objForwardCurveProfileDAO.getAllForwardCurveProfiles(Integer.parseInt(zoneid),Integer.parseInt(profileId),1);
        String filename = new FulcrumCurve().getProfileChart(request.getSession(),Integer.parseInt(zoneid),Integer.parseInt(profileId),response.getWriter());
        
        Iterator iteprofile = lstProfiledetails.iterator();
        if(BuildConfig.DMODE)
            System.out.println("lstProfiledetails"+lstProfiledetails);
        while(iteprofile.hasNext())
        {
            objForwardCurveProfileVO = (ForwardCurveProfileVO)iteprofile.next();
            table += "<tr><td width='93' class='tbldata' align='left'>"+objForwardCurveProfileVO.getWeatherZone().getWeatherZone()+"</td>" +
            "<td width='93' class='tbldata' align='left'>"+objForwardCurveProfileVO.getLoadProfile().getProfileType()+"</td>" +
            "<td width='75' class='tbldata' align='right'>"+dateformat.format(objForwardCurveProfileVO.getMonthYear())+"</td>" +
            "<td width='83' class='tbldata' align='right'>"+df.format(objForwardCurveProfileVO.getPrice())+"</td>" +
            "</tr>";
        }
        table +="</table>";
        
        String chartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
        show = table +"###"+"<img src='"+ chartURL +"'border='0' usemap='#"+filename.trim()+"'>";
        
        response.setContentType("*/*");
        response.getWriter().write(show);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        System.gc();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}


/*
*$Log: EnergyProfileServlet.java,v $
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.9  2007/06/12 12:57:53  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.8  2007/04/30 15:43:53  jnadesan
*imagemap added
*
*Revision 1.7  2007/04/30 09:09:31  jnadesan
*writing image properties problem solved
*
*Revision 1.6  2007/04/24 05:33:19  jnadesan
*width changed
*
*Revision 1.5  2007/04/23 05:52:20  kduraisamy
*unwanted println removed.
*
*Revision 1.4  2007/04/22 15:43:43  jnadesan
*date and number format changed
*
*Revision 1.3  2007/04/20 14:04:46  jnadesan
*width changed
*
*Revision 1.2  2007/04/18 03:56:37  kduraisamy
*imports organized.
*
*Revision 1.1  2007/03/22 14:39:20  jnadesan
*Curve for EnergyProfilePrice added
*
*
*/