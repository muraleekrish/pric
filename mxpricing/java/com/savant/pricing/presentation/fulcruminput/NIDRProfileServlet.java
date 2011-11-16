/*
 * Created on May 31, 2007
 * 
 * Class Name NIDRProfileServlet.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.fulcruminput;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.common.NumberUtil;
import com.savant.pricing.common.chart.ProfileChart;
import com.savant.pricing.dao.NIDRDAO;
import com.savant.pricing.transferobjects.HourValueDetails;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NIDRProfileServlet extends HttpServlet{
    
    
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)throws ServletException, IOException 
    {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            String profile = request.getParameter("profile");
            String weekdayTable = "<table width='100%' border='0' cellspacing='0' cellpadding='0'><tr class='staticHeader'><td width='50'  class='tblheader' align='center'>Month</td>" +
            "<td width='50' class='tblheader' align='center'>Hour</td><td  width='85' class='tblheader' align='center'>Value(kWh)</td></tr>";
            String[] month = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
            String weekEndTable = weekdayTable;
            String weekdayFileName ="";
            String weekendFileName = "";
            
            NumberFormat df = NumberUtil.tetraFraction();
            if(profile!=null||!profile.equalsIgnoreCase("")||!profile.equals(null))
            {
                NIDRDAO objNIDRDAO = new NIDRDAO();
                HashMap hmNIDRDetails = objNIDRDAO.getNIDRProfileDetails(profile);
                HashMap hmNIDRWeekDayDetails = (HashMap) hmNIDRDetails.get(new Integer(2));
                HashMap hmNIDRWeekEndDetails = (HashMap) hmNIDRDetails.get(new Integer(3));
                
                ProfileChart objProfileChart = new ProfileChart();
                HashMap filename =  objProfileChart.getchart(request.getSession(),hmNIDRDetails,response.getWriter());
                
                weekdayFileName = (String)filename.get("weekday");
                weekendFileName = (String)filename.get("weekend");
                
                Set keySet = hmNIDRWeekDayDetails.keySet();
                Iterator iteMonth = keySet.iterator();
                while(iteMonth.hasNext())
                {
                    int monthCount = ((Integer)iteMonth.next()).intValue();
                    List lsthourDetails = (List)hmNIDRWeekDayDetails.get(new Integer(monthCount));
                    int hourCount = 1;
                    if(lsthourDetails!=null)
                        while(hourCount<=lsthourDetails.size())
                        {
                            HourValueDetails objHourValueDetails = (HourValueDetails)lsthourDetails.get(hourCount-1);
                            weekdayTable +="<tr><td  class='tbldata' align='left'>"+month[monthCount-1]+"</td>" +
                            "<td class='tbldata' align='right'>" +objHourValueDetails.getHour()+"</td>" +
                            "<td  class='tbldata' align='right'>" +df.format(objHourValueDetails.getValue())+"</td></tr>";
                            hourCount++;
                        }
                }
                keySet = hmNIDRWeekEndDetails.keySet();
                iteMonth = keySet.iterator();
                while(iteMonth.hasNext())
                {
                    int monthCount = ((Integer)iteMonth.next()).intValue();
                    List lsthourDetails = (List)hmNIDRWeekEndDetails.get(new Integer(monthCount));
                    int hourCount = 1;
                    if(lsthourDetails!=null)
                        while(hourCount<=lsthourDetails.size())
                        {
                            HourValueDetails objHourValueDetails = (HourValueDetails)lsthourDetails.get(hourCount-1);
                            weekEndTable +="<tr><td  class='tbldata' align='left'>"+month[monthCount-1]+"</td>" +
                            "<td class='tbldata' align='right'>" +objHourValueDetails.getHour()+"</td>" +
                            "<td  class='tbldata' align='right'>" +df.format(objHourValueDetails.getValue())+"</td></tr>";
                            hourCount++;
                        }
                }
                 
            }
            weekdayTable +="</table>";
            weekEndTable +="</table>";
            
            String weekdayChartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + weekdayFileName;
            String weekendChartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + weekendFileName;
            String chart = "<img src='"+ weekdayChartURL +"'  border='0' usemap='#"+weekdayFileName.trim()+"'>"+"@@@"+
            "<img src='"+ weekendChartURL +"'  border='0' usemap='#"+weekendFileName.trim()+"'>";
            
            
            response.setContentType("*/*");
            response.getWriter().write(weekdayTable+"@@@"+weekEndTable+"@@@"+chart);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0L);
            System.gc();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}


/*
*$Log: NIDRProfileServlet.java,v $
*Revision 1.1  2007/12/07 06:18:54  jvediyappan
*initial commit.
*
*Revision 1.1  2007/10/30 05:51:56  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:26  jnadesan
*initail MXEP commit
*
*Revision 1.3  2007/06/01 07:48:12  jnadesan
*header made as static
*
*Revision 1.2  2007/06/01 07:25:15  jnadesan
*taking profile details logic chaged
*
*Revision 1.1  2007/06/01 06:04:32  jnadesan
*initial commit for profile details view
*
*
*/