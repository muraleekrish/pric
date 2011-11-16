/*
 * Created on Mar 30, 2007
 * 
 * Class Name DealLeverAnalystServlet.java
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.savant.pricing.presentation.pricing;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.savant.pricing.calculation.dao.DealLeversDAO;
import com.savant.pricing.calculation.valueobjects.CustomerDealLeversVO;
import com.savant.pricing.calculation.valueobjects.DealLeversVO;
import com.savant.pricing.common.BuildConfig;
import com.savant.pricing.transferobjects.DealLevers;

/**
 * @author jnadesan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DealLeverAnalystServlet extends HttpServlet{
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
    throws ServletException, IOException {
        
        super.doPost(arg0, arg1);
        this.doGet(arg0,arg1);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String prsCustId = request.getParameter("CustId");
        DealLeversDAO objDealLeversDAO = new DealLeversDAO();
        NumberFormat format = new DecimalFormat("0.000##");
        String select="";
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        String resultMessage ="";
        String resultFlag = ""; 
        String table = "";
        String addtable = "";
        String modifyTable ="";
        String viewTable = "";
        
        String Cust ="0.0";
        String addl ="0.0";
        String Agnt ="0.0"; 
        String agg ="0.0";
        String bW ="0.0";
        String other ="0.0";
        String margin ="0.0"; 
        String longterm ="0.0"; 
        String term =  request.getParameter("Term");
        String message =request.getParameter("Message");
        HashMap mapdealvalue =  new HashMap();
        try
        {
            if(message.equalsIgnoreCase("add")||message.equalsIgnoreCase("Update")||message.equalsIgnoreCase("ApplyToAll"))
            {
                try
                {
                Cust = (request.getParameter("Cust").equals("")?"0.0":request.getParameter("Cust"));
                addl = (request.getParameter("addl").equals("")?"0.0":request.getParameter("addl"));
                Agnt = (request.getParameter("Agnt").equals("")?"0.0":request.getParameter("Agnt"));
                agg =  (request.getParameter("agg").equals("")?"0.0":request.getParameter("agg"));
                bW = (request.getParameter("bW").equals("")?"0.0":request.getParameter("bW"));
                other = (request.getParameter("other").equals("")?"0.0":request.getParameter("other"));
                margin = (request.getParameter("margin").equals("")?"0.0":request.getParameter("margin"));
                longterm = (request.getParameter("longterm").equals("")?"0.0":request.getParameter("longterm"));
                
                Vector vecDealLevers = new Vector();
                DealLevers objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(1);
                objDealLevers.setValue(Float.parseFloat(Cust));
                vecDealLevers.add(objDealLevers);
                
                objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(7);
                objDealLevers.setValue(Float.parseFloat(addl));
                vecDealLevers.add(objDealLevers);
                
                objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(4);
                objDealLevers.setValue(Float.parseFloat(Agnt));
                vecDealLevers.add(objDealLevers);
                
                objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(5);
                objDealLevers.setValue(Float.parseFloat(agg));
                vecDealLevers.add(objDealLevers);
                
                objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(6);
                objDealLevers.setValue(Float.parseFloat(bW));
                vecDealLevers.add(objDealLevers);
                
                objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(2);
                objDealLevers.setValue(Float.parseFloat(other));
                vecDealLevers.add(objDealLevers);
                
                objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(3);
                objDealLevers.setValue(Float.parseFloat(margin));
                vecDealLevers.add(objDealLevers);
                
                objDealLevers = new DealLevers();
                objDealLevers.setDealLeverIdentifier(8);
                objDealLevers.setValue(Float.parseFloat(longterm));
                vecDealLevers.add(objDealLevers);
                
                if(message.equalsIgnoreCase("ApplyToAll"))
                {
                    LinkedHashMap hmTermDetails = objDealLeversDAO.getDealLeversTermDetails(Integer.parseInt(prsCustId));
                    Set settermDetails = hmTermDetails.keySet();
                    Iterator iteTermDetails = settermDetails.iterator();
                    while(iteTermDetails.hasNext())
                    {
                        int intTerm = ((Integer)iteTermDetails.next()).intValue();
                        objDealLeversDAO.saveDealLeversByProspectiveCustomerId(Integer.parseInt(prsCustId),intTerm, vecDealLevers);
                    }
                }
                else
                {
                objDealLeversDAO.saveDealLeversByProspectiveCustomerId(Integer.parseInt(prsCustId),Integer.parseInt(term), vecDealLevers);
                }
                
                resultFlag = "success";
                if(message.equalsIgnoreCase("add"))
                    resultMessage = "Deal Adjustment values are added successfully";
                else
                    resultMessage = "Deal Adjustment values are Modified successfully";
                }
                catch(NumberFormatException e)
                {
                    resultFlag = "failure";
                    if(message.equalsIgnoreCase("add"))
                        resultMessage = "Error while adding Deal Adjustment values.Please provide valid term value.";
                    else
                        resultMessage = "Error while modifying Deal Adjustment values";
                    e.printStackTrace();
                }
                catch (Exception e) {
                    resultFlag = "failure";
                    if(message.equalsIgnoreCase("add"))
                        resultMessage = "Error while adding Deal Adjustment values.Please provide term value.";
                    else
                        resultMessage = "Error while modifying Deal Adjustment values";
                    e.printStackTrace();
                }
                    LinkedHashMap hmTermDetails = objDealLeversDAO.getDealLeversTermDetails(Integer.parseInt(prsCustId));
                    Set settermDetails = hmTermDetails.keySet();
                    Iterator iteTermDetails = settermDetails.iterator();
                    table+="<table width='272' border='0' cellspacing='0' cellpadding='0'>";
                    while(iteTermDetails.hasNext())
                    {
                        int intTerm = ((Integer)iteTermDetails.next()).intValue();
                        table += "<tr><td class='tbldata'><input name='buttonGroup' type='radio' value="+intTerm+"></td><td width='77' class='tbldata'>" +intTerm+"</td><td width='169' class='tbldata' align='right'>" +
                        "<a href='#' onClick='viewDealLevers("+intTerm+")'>"+df.format(hmTermDetails.get(new Integer(intTerm)))+"</a></td>" +
                        "</tr>";
                    }
                    table+="</table>";
                    select = resultFlag+"!@#"+resultMessage+"!@#"+table;
            }
            else if(message.equalsIgnoreCase("view")||message.equalsIgnoreCase("modify"))
            {
                HashMap hm = null;
                hm = objDealLeversDAO.getDealLeversByCustomerIdTerm(Integer.parseInt(prsCustId),Integer.parseInt(term));
                if(hm.get("CustomerDealLeversVO") != null)
                {
                    List custDealLevers = (List)hm.get("CustomerDealLeversVO");
                    Iterator iter = custDealLevers.iterator();
                    
                    while(iter.hasNext())
                    {
                        CustomerDealLeversVO objCustomerDealLeversVO = (CustomerDealLeversVO)iter.next();
                        mapdealvalue.put(new Integer(objCustomerDealLeversVO.getDealLever().getDealLeverIdentifier()),new Double(objCustomerDealLeversVO.getValue()));
                    }
                }
                else if(hm.get("DealLeversVO") != null)
                {
                    List dealLevers = (List)hm.get("DealLeversVO");
                    Iterator iter = dealLevers.iterator();
                    
                    while(iter.hasNext())
                    {
                        DealLeversVO objDealLeversVO = (DealLeversVO)iter.next();
                        mapdealvalue.put(new Integer(objDealLeversVO.getDealLeverIdentifier()),new Double(objDealLeversVO.getValue()));
                    }
                }
                Cust = (format.format(((Double)mapdealvalue.get(new Integer(1))).doubleValue()));
                addl =(format.format(((Double)mapdealvalue.get(new Integer(7))).doubleValue()));
                Agnt = (format.format(((Double)mapdealvalue.get(new Integer(4))).doubleValue()));
                agg =(format.format(((Double)mapdealvalue.get(new Integer(5))).doubleValue()));
                bW = (format.format(((Double)mapdealvalue.get(new Integer(6))).doubleValue()));
                other = (format.format(((Double)mapdealvalue.get(new Integer(2))).doubleValue()));
                margin = (format.format(((Double)mapdealvalue.get(new Integer(3))).doubleValue()));
                longterm = (format.format(((Double)mapdealvalue.get(new Integer(8))).doubleValue()));
                
                
                if(message.equalsIgnoreCase("view"))
                {
                    viewTable = "<br><table width='100%'  border='0' cellpadding='0' cellspacing='0' >" +
                    "<tr><td width='200' class='fieldata' style='color:#F38809'><b>Deal Adjustment Values For the Term </td><td width='10' class='fieldata' style='color:#F38809'>-</td><td class='fieldata' style='color:#F38809'><b>"+term+"<b></td><tr><td>&nbsp;</td></tr><tr><td width='177' class='fieldtitle'>Customer Charge $/mth </td><td width='10' class='fieldtitle'>:</td><td width='384' class='fieldata'>" +
                    Cust+"</td></tr><tr><td width='177' class='fieldtitle'>Additional Volatility Premium </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                    addl+"</td></tr><tr><td width='177' class='fieldtitle'>Long Term Weather Premium</td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                    longterm+"</td></tr><tr><td width='177' class='fieldtitle'>Sales Agents Fee </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                    Agnt+"</td></tr><tr><td width='177' class='fieldtitle'>ABC Fee </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                    agg+"</td></tr><tr><td width='177' class='fieldtitle'>BandWidth Charge </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                    bW+"</td></tr><tr><td width='177' class='fieldtitle'>Other Fee </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                    other+"</td></tr><tr><td width='177' class='fieldtitle'>Margin</td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                    margin+"</td></tr></table><br>";
                    select = viewTable;
                }
                else
                {
                    modifyTable = "<br><table width='100%'  border='0' cellpadding='0' cellspacing='0' ><tr><td width='177' class='fieldtitle'>Customer Charge $/mth </td><td width='10' class='fieldtitle'>:</td><td width='384' class='fieldata'>" +
                    "<input name=\"txtCust\" type='text' class='textbox'  size='15' value='"+Cust+"' onkeypress=\"return checkentry(event)\"></td></tr><tr><td width='177' class='fieldtitle'>Additional Volatility Premium </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                    "<input name=\"txtAddl\" type='text' class='textbox'  size='15' value="+addl+" onkeypress=\"return checkentry(event)\" ></td></tr><tr><td width='177' class='fieldtitle'>Long Term Weather Premium </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                    "<input name=\"txtLong\" type='text' class='textbox'  size='15' value="+longterm+" onkeypress=\"return checkentry(event)\" ></td></tr><tr><td width='177' class='fieldtitle'>Sales Agent Fee </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                    "<input name=\"txtAgnt\" type='text' class='textbox' id = 'txtAgnt' size='15'   value="+Agnt+" onkeypress=\"return checkentry(event)\"></td></tr><tr><td width='177' class='fieldtitle'>Aggregator Fee </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                    "<input name=\"txtAgg\" type='text' class='textbox' id = 'txtAgg' size='15'   value="+agg+" onkeypress=\"return checkentry(event)\"></td></tr><tr><td width='177' class='fieldtitle'>BandWidth Charge </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                    "<input name=\"txtBW\" type='text' class='textbox' id = 'txtBw' size='15' value="+bW+" onkeypress=\"return checkentry(event)\"></td></tr><tr><td width='177' class='fieldtitle'>Other Fee </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                    "<input name=\"txtOther\" type='text' class='textbox' size='15' value="+other+" onkeypress=\"return checkentry(event)\"></td></tr><tr><td width='177' class='fieldtitle'>Margin</td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                    "<input name=\"txtMargin\" type='text' class='textbox' size='15' value="+margin+" onkeypress=\"return checkentry(event)\"></td></tr></table><br>" +
                    "<table width='570' border='0' cellspacing='0' cellpadding='0' ><tr id='btn'><td width='50' class='fieldata'><input name='Button3' type='button' class='button' value='Apply'  onClick=\"callServlet("+"'Update'"+","+term+")\"></td>" +
                    "<td width='50' class='fieldata'><input name='Button2' type='button' class='button' value='Apply To All'  onClick=\"callServlet("+"'ApplyToAll'"+","+term+")\"></td><td width='50' class='fieldata'><input name='Button22' type='button' class='button' id='resetbutton' value='Reset' onClick=\"callModify('modify')\";></td>" +
                    "<td width='400' class='fieldata'><input name='Button' type='button' class='button' value='Cancel' onClick=\"callCancel();\"></td></tr></table>";
                    select = modifyTable;
                }
            }
            else if(message.equalsIgnoreCase("addBlock"))
            {
                
                List lst = objDealLeversDAO.getAllDealLevers();
                Iterator iter = lst.iterator();
                while(iter.hasNext())
                {
                    DealLeversVO objDealLeversVO = (DealLeversVO)iter.next();
                    mapdealvalue.put(new Integer(objDealLeversVO.getDealLeverIdentifier()),new Double(objDealLeversVO.getValue()));
                }
                
                Cust = (format.format(((Double)mapdealvalue.get(new Integer(1))).doubleValue()));
                addl =(format.format(((Double)mapdealvalue.get(new Integer(7))).doubleValue()));
                Agnt = (format.format(((Double)mapdealvalue.get(new Integer(4))).doubleValue()));
                agg =(format.format(((Double)mapdealvalue.get(new Integer(5))).doubleValue()));
                bW = (format.format(((Double)mapdealvalue.get(new Integer(6))).doubleValue()));
                other = (format.format(((Double)mapdealvalue.get(new Integer(2))).doubleValue()));
                margin = (format.format(((Double)mapdealvalue.get(new Integer(3))).doubleValue()));
                longterm = (format.format(((Double)mapdealvalue.get(new Integer(8))).doubleValue()));
                
                
                addtable =  "<br><table width='547' border='0' cellspacing='0' cellpadding='0' ><tr><td class='fieldtitle'>Term*</td><td width='10' class='fieldtitle'>:</td><td class='fieldata'><input name='txtterm' type='text' class='textbox' size='15' onkeypress=\"return checkentry(event)\">" +
                "Eg.:12</td></tr></table><br><table width='100%'  border='0' cellpadding='0' cellspacing='0' ><tr><td width='177' class='fieldtitle'>Customer Charge $/mth </td><td width='10' class='fieldtitle'>:</td><td width='384' class='fieldata'>" +
                "<input name='txtCust' type='text' class='textbox' size='15' onkeypress=\"return checkentry(event)\" value='"+Cust+"' ></td></tr><tr><td width='177' class='fieldtitle'>Additional Volatility Premium </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                "<input name='txtAddl' type='text' class='textbox' size='15' onkeypress=\"return checkentry(event)\" value='"+addl+"'></td></tr><tr><td width='177' class='fieldtitle'>Long Term Weather Premium</td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                "<input name='txtLong' type='text' class='textbox' size='15' onkeypress=\"return checkentry(event)\" value='"+longterm+"'></td></tr><tr><td width='177' class='fieldtitle'>Sales Agent Fee </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                "<input name='txtAgnt' type='text' class='textbox' size='15' onkeypress=\"return checkentry(event)\" value='"+Agnt+"'></td></tr><tr><td width='177' class='fieldtitle'>Aggregator Fee </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                "<input name='txtAgg' type='text' class='textbox' size='15' onkeypress=\"return checkentry(event)\" value='"+agg+"'></td></tr><tr><td width='177' class='fieldtitle'>BandWidth Charge </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                "<input name='txtBW' type='text' class='textbox'  size='15' onkeypress=\"return checkentry(event)\" value='"+bW+"'></td></tr><tr><td width='177' class='fieldtitle'>Other Fee </td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                "<input name='txtOther' type='text' class='textbox' size='15' onkeypress=\"return checkentry(event)\" value='"+other+"'></td></tr><tr><td width='177' class='fieldtitle'>Margin</td><td width='10' class='fieldtitle'>:</td><td class='fieldata'>" +
                "<input name='txtMargin' type='text' class='textbox' size='15' onkeypress=\"return checkentry(event)\" value='"+margin+"'></td></tr></table><br>" +
                "<table width='570' border='0' cellspacing='0' cellpadding='0'><tr id='btn'><td width='30' class='fieldata'>" +
                "<input name='Button2' type='button' class='button' value='Add'  onClick=\"calladd()\"></td>" +
                "<td width='50' class='fieldata'>" +
                "<input name='Button22' type='button' class='button' value='Reset' onClick=\"callServlet('addBlock',0);\"></td>" +
                "<td width='400' class='fieldata'><input name='Button' type='button' class='button' value='Cancel' onClick=\"callCancel();\"></td></tr></table>";
                select = addtable;
            }
            else if(message.equalsIgnoreCase("delete"))
            {
                try
                {
                    boolean result = true;
                    result = objDealLeversDAO.deleteDealLeversByCustomerIdTerm(Integer.parseInt(prsCustId),Integer.parseInt(term));
                    LinkedHashMap hmTermDetails = objDealLeversDAO.getDealLeversTermDetails(Integer.parseInt(prsCustId));
                    Set settermDetails = hmTermDetails.keySet();
                    Iterator iteTermDetails = settermDetails.iterator();
                    table+="<table width='272' border='0' cellspacing='0' cellpadding='0'>";
                    while(iteTermDetails.hasNext())
                    {
                        int intTerm = ((Integer)iteTermDetails.next()).intValue();
                        table += "<tr><td class='tbldata'><input name='buttonGroup' type='radio' value="+intTerm+"></td><td width='77' class='tbldata'>" +intTerm+"</td><td width='169' class='tbldata' align='right'>" +
                        "<a href='#' onClick='viewDealLevers("+intTerm+")'>"+df.format(hmTermDetails.get(new Integer(intTerm)))+"</a></td>" +
                        "</tr>";
                    }
                    table+="</table>";
                    if(result)
                    {
                        resultFlag ="success";
                        resultMessage = "Deal Adjustment values for the term "+term+" is successfully deleted.";
                    }
                    else
                    {
                        resultFlag = "failure";
                        resultMessage = "Deal Adjustment values for the term "+term+" is not deleted.This term is set in preference.";
                    }
                }
                catch(Exception e)
                {
                    resultFlag ="failure";
                    e.printStackTrace();
                    resultMessage = "";
                }
                select = resultFlag+"!@#"+resultMessage+"!@#"+table;
            }
                
            if(BuildConfig.DMODE)
                System.out.println("Cust :"+Cust+" addl :"+addl +" Agnt :"+Agnt+" agg : "+agg+" margin"+margin+" bw"+bW+" other"+other);
            
            response.setContentType("*/*");
            response.getWriter().write(select);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0L);
            System.gc();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


/*
*$Log: DealLeverAnalystServlet.java,v $
*Revision 1.2  2008/01/23 08:35:14  tannamalai
*jasper reports changes
*
*Revision 1.1  2007/12/07 06:18:47  jvediyappan
*initial commit.
*
*Revision 1.3  2007/11/29 10:20:10  tannamalai
*readonly removed
*
*Revision 1.2  2007/11/17 07:59:01  tannamalai
**** empty log message ***
*
*Revision 1.1  2007/10/30 05:51:49  jnadesan
*Initial commit.
*
*Revision 1.1  2007/10/26 15:19:22  jnadesan
*initail MXEP commit
*
*Revision 1.11  2007/10/24 10:32:01  spandiyarajan
*Sales Agent Fee and ABC fee not editable in deallevers page
*
*Revision 1.10  2007/10/23 11:54:03  spandiyarajan
*altered the salesagentfee & abc fee as readonly
*
*Revision 1.9  2007/06/21 12:00:47  jnadesan
*apply to all for deallevers provision added
*
*Revision 1.8  2007/06/20 08:46:32  kduraisamy
*apply to all functionality started for deal levers.
*
*Revision 1.7  2007/06/12 12:59:03  spandiyarajan
*removed unwanted s.o.p / removed unwanted spaces / removed unwanted codes
*
*Revision 1.6  2007/04/19 13:24:38  jnadesan
*reset problem solved
*
*Revision 1.5  2007/04/13 05:00:52  kduraisamy
*unwanted println commented.
*
*Revision 1.4  2007/04/08 15:10:56  jnadesan
*for add system default value shown
*
*Revision 1.3  2007/04/08 07:46:05  kduraisamy
*dealLevers return type changed.
*
*Revision 1.2  2007/04/07 11:49:33  jnadesan
*methods changed to have access acroos three type of users
*
*Revision 1.1  2007/04/02 11:17:44  jnadesan
*deallevers added
*
*
*/