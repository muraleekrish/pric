<%try{
	if(session.getAttribute("userName")==null)
	{
		if(session.getLastAccessedTime()>0)
		{
%>
    		<jsp:include page="/jsp/sessionout.jsp"/>
<%
		}
		else
		{
%>
			<jsp:include page="/jsp/index.jsp"/>
<%
		}
	}
	else
	{
%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.savant.pricing.dao.GasPriceDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.GasPriceVO"%>
<%@ page import="com.savant.pricing.common.chart.GasPriceCurve"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>

<html:html>
	<%
	
	GasPriceDAO objGasPriceDAO = new GasPriceDAO();
	List listgaspricedetails = null;
	Date gasPriceLastImported = null;
	SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
	GasPriceCurve chart = new GasPriceCurve();
	GasPriceVO objGasPriceVO =new GasPriceVO();
	try{
	listgaspricedetails = objGasPriceDAO.getAllGasPrices();
	gasPriceLastImported = objGasPriceDAO.teeNaturalGasPriceLastImportedOn();
	if(listgaspricedetails.size()>0)
	{
	 objGasPriceVO = (GasPriceVO)listgaspricedetails.get(0);
	}
	}
	catch (Exception e) {
	e.printStackTrace();
    }
	pageContext.setAttribute("listgaspricedetails",listgaspricedetails);
	pageContext.setAttribute("objGasPriceVO",objGasPriceVO);
	
	%>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
</head>
<body> 
<html:form action="/viewgasprice" >

<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top">
<jsp:include page="/jsp/menu.jsp"/> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title">Natural&nbsp;Gas&nbsp;Price</td> 
        </tr> 
      </table>
	  <fieldset><legend>
		Natural Gas Price </legend>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr >
          <td width="147" class="fieldtitle" align="center">Market Date</td>
          <td width="4" class="fieldtitle">:</td>
          <td width="224" class="fieldtitle"><%=(gasPriceLastImported==null)?"":df.format(gasPriceLastImported)%></td>
          <td width="166" class="fieldtitle" align="center">Data Source</td>
          <td width="1" class="fieldtitle">:</td>
          <td width="422" class="fieldtitle" ><%=listgaspricedetails.size()<=0?"":((GasPriceVO)listgaspricedetails.get(0)).getDataSource().getDataSource()%></td>
        </tr>       
      </table>
	  </fieldset>

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  	  	<tr>
    		<td width="40%">
    		<div style="overflow:auto; width:100%; height:300;"> 
    		<table border="0" width="100%" cellspacing="0" cellpadding="0">
      			<tr class='staticHeader'>
		        	<td width="50%" align="center"  class="tblheader">Month - Year</td>
		        	<td width="50%" align="center"  class="tblheader">Price($/MMBtu)</td>
      			</tr>
				<logic:present name="objGasPriceVO" property="monthYear">
					<%
						SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
						for (int i = 0; i < listgaspricedetails.size(); i++)
        				{
            				GasPriceVO vo = (GasPriceVO) listgaspricedetails.get(i);
            		%>
            			<tr>
			       			<td class="tbldata" align="right"><%=sdf.format(vo.getMonthYear())%></td> 
			       			<td class="tbldata" align="right"><%=vo.getPrice()%></td>
			      		</tr>
            		<%
        			}
					%>

				</logic:present>
			</table>
			</div>
	 </td>
	 <td width="60%">
		<%
			String filenamepieChart = chart.getchart(session,new PrintWriter(out));
			String chartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filenamepieChart;
		%> 
		<img src="<%= chartURL%>"  border=0 usemap="#<%=filenamepieChart%>">
  	</td>
   </tr>
  </table>

    </td>
  </tr> 
  <tr> 
    <td height="20"><table width="100%"  border="0" cellpadding="0" cellspacing="0" > 
        <tr> 
        	<%String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";%>
          <td   class="copyright_link"><%=menupath%></td>
          <td width="250"   class="copyright" align='right'>Design Rights Savant Technologies </td> 
        </tr> 
      </table></td> 
  </tr> 
</table> 
</html:form> 
</body>
</html:html>
<%
}}catch(Exception e)
{
	e.printStackTrace();
}
%>