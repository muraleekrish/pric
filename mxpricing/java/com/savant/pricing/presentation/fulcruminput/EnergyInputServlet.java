/*
 * Created on Mar 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.fulcruminput;

import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.calculation.valueobjects.ForwardCurveBlockVO;
import com.savant.pricing.common.NumberUtil;
import com.savant.pricing.common.chart.FulcrumCurve;
import com.savant.pricing.dao.CongestionZonesDAO;
import com.savant.pricing.dao.ForwardCurveBlockDAO;
import com.savant.pricing.valueobjects.CongestionZonesVO;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EnergyInputServlet extends HttpServlet{
    
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)throws ServletException, IOException 
    {
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
        String zoneid = request.getParameter("ZoneId");
        String show = "";
        String zoneName = "";
        String table = "<table width='100%' border='0' cellspacing='0' cellpadding='0'  style='display:block'>";
        NumberFormat df = NumberUtil.doubleFraction();
        DateFormat dateformat = new SimpleDateFormat("MMM yyyy");
        CongestionZonesDAO objCongestionZonesDAO = new CongestionZonesDAO();
        ForwardCurveBlockDAO objForwardCurveBlockDAO = new ForwardCurveBlockDAO();
        ForwardCurveBlockVO objForwardCurveBlockVO = new ForwardCurveBlockVO();
        List lstZone = objCongestionZonesDAO.getAllCongestionZones();
        Iterator iteZone = lstZone.iterator();
        while(iteZone.hasNext())
        {
            CongestionZonesVO objCongestionZonesVO = (CongestionZonesVO)iteZone.next();
            if(objCongestionZonesVO.getCongestionZoneId()==(Integer.parseInt(zoneid)))
            {
                zoneName = objCongestionZonesVO.getCongestionZone();
            }
        }
        
       HashMap hmforEnergyPrice = objForwardCurveBlockDAO.getAllForwardCurveBlocks(Integer.parseInt(zoneid),1);
       
       Set keySet = hmforEnergyPrice.keySet();
       Iterator iteBlck = keySet.iterator();
       List lstprice = null;
       while(iteBlck.hasNext())
       {
           int prcId = ((Integer)iteBlck.next()).intValue();
           lstprice = (List)hmforEnergyPrice.get(new Integer(prcId));
           if(null != lstprice)
           {
               Iterator itr = lstprice.iterator();
               while(itr.hasNext()) 
               {
                   objForwardCurveBlockVO = (ForwardCurveBlockVO)itr.next();
                   table +="<tr><td width='90' class='tbldata' align='left'>"+objForwardCurveBlockVO.getCongestionZone().getCongestionZone()+"</td>" +
                   "<td width='72' class='tbldata' align='left'>"+objForwardCurveBlockVO.getPriceBlock().getPriceBlock()+"</td>" +
                   "<td width='72' class='tbldata' align='right'>"+dateformat.format(objForwardCurveBlockVO.getMonthYear())+"</td>" +
                   "<td width='75' class='tbldata' align='right'>"+df.format(objForwardCurveBlockVO.getPrice())+"</td></tr>";
              
               }
           }
       }
       if( lstprice == null || keySet==null || keySet.size()<1 || lstprice.size() < 1 )
       {
           table = "<table width='98%' border='0' cellspacing='0' cellpadding='0'  style='display:block'><tr><td class = 'tbldata' align = 'center'>--- No Results Found ---</td></tr>";
       }
       
       table +="</table>";
        
        String filename = new FulcrumCurve().getchart(request.getSession(),Integer.parseInt(zoneid),zoneName,response.getWriter());
        String chartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
        show = table+"####"+"<img src='"+ chartURL +"'  border='0' usemap='#"+filename.trim()+"'>";
        response.setContentType("*/*");
        response.getWriter().write(show);
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
    

