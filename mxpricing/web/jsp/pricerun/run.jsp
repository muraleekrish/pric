<%
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
<%@ page import="java.util.List"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.dao.ForwardCurveBlockDAO"%>
<%@ page import="com.savant.pricing.dao.GasPriceDAO"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%
	String str="";
	String cusids = request.getParameter("custIds");
	if(cusids==null)
		cusids = "0";
	ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
	List lstProspectiveCust =new java.util.LinkedList();
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
	if(cusids!=null)
	{
		lstProspectiveCust = objProspectiveCustomerDAO.getProspectiveCustomers(cusids);
	}

	ForwardCurveBlockDAO objForwardCurveBlockDAO = new ForwardCurveBlockDAO();
	Date date = objForwardCurveBlockDAO.fwdCurveLastImportedOn();
	GasPriceDAO objGasPriceDAO = new GasPriceDAO();
	Date dategas = objGasPriceDAO.teeNaturalGasPriceLastImportedOn();
pageContext.setAttribute("lstProspectiveCust",lstProspectiveCust);
	
	int browserHt = 0;
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 320;
	else
		browserHt = 300;
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/mm_menu.js"></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ProspectiveCustomerDAO.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
<script>
function addCustomer()
{
	var temp=document.forms[0];
	temp.action='<%=request.getContextPath()%>/runSearch.do';
	<%if(request.getParameter("ftmsg")!=null&&request.getParameter("ftmsg").equalsIgnoreCase("footer")){%>
	temp.action='<%=request.getContextPath()%>/runSearch.do?ftmsg=footer';
	<%}%>
	temp.custIds.value = '<%=cusids%>';
	temp.submit();
}
function runnext()
{
	temp=document.forms[0];
	var custIds ='<%=cusids%>';
	temp.action='<%=request.getContextPath()%>/runnext.do';
	temp.custIds.value = '<%=cusids%>';
	if(custIds.length >0)
		temp.submit();
	else
		alert("please Add prospective customer(s)");
}
function constructToolTip(custName,dba,id)
{
	var table = "<span bgcolor='#E8E8E8'><table height=\'40\' border=\'0\' cellspacing=\'1\' cellpadding=\'0\'>"+
	"<tr ><td><b> Name</b></td>"+
	      "<td width=\'1\'>:</td>"+
	      "<td> "+custName+"</td></tr>"+
    "<tr><td><b>DBA</b></td>"+
	     "<td width=\'1\'>:</td>"+
      	 "<td>"+dba+"</td></tr>"+
    "</table></span>";
	Tip(table);
}
</script>
</head>
<body> 
<script type="text/javascript" src="<%=request.getContextPath()%>/script/tooltip/wz_tooltip.js"></script> 
<html:form action="runList">
<html:hidden property="formActions"/>
<input type="hidden" name="custIds"/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"><jsp:include page="../menu.jsp"/>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title"> Run</td>
          <td class="page_title"><table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
            <tr>
              <td><a href="<%=request.getContextPath()%>/jsp/pricerun/runresult.jsp">Run&nbsp;Result&nbsp;&nbsp;</a></td>
            </tr>
          </table></td>
        </tr> 
      </table> 
      <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
        <tr> 
          <td width="124" class="fieldtitle">Rundate</td> 
          <td width="12" class="fieldtitle">:</td> 
          <td width="235" class="fieldata"> <%= sdf.format(new Date())%></td> 
          <td width="149" class="fieldtitle">Pricing Analyst</td> 
          <td width="10" class="fieldtitle">:</td> 
          <td width="462" class="fieldata">&nbsp;<%=request.getSession().getAttribute("userName")%></td> 
        </tr> 
		 <tr> 
          <td colspan="6" class="fieldata"><strong>&nbsp;Forward Curves Last Imported Dates:</strong></td> 
        </tr> 
		 <tr> 
         <td colspan="6"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr> 
          <td width="125" class="fieldtitle">Electric Price </td> 
          <td width="10" class="fieldtitle">:</td> 
          <td width="235"   class="fieldata"><%= date==null?"":sdf.format(date)%> </td> 
		   <td width="151" class="fieldtitle">Natural Gas Price </td> 
           <td width="10" class="fieldtitle">:</td> 
          <td width="461"   class="fieldata"><%= dategas==null?"":sdf.format(dategas)%></td>
        </tr> 
          </table></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
		<td width="20">&nbsp;</td>
          <td><br>
            <input name="Add Customer" type="button" class="button" id="Add Customer" value="Add Customers to Run"  onClick="addCustomer()">            
			</td>
          <td>&nbsp;</td>
        </tr>
      </table> <br> 
      <logic:notEmpty name="lstProspectiveCust">
      <table width="93%"  border="0" align="center" cellpadding="0" cellspacing="0"> 
        <tr> 
          <td> <fieldset> 
            <legend>Selected Prospective Customers</legend> 
            <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
            <table width="98%"  border="0"  cellpadding="0" cellspacing="0"> 
              <tr class='staticHeader'> 
                <td width="186" class="tblheader" align="center">Customer Id </td> 
                <td width="257" class="tblheader" align="center">Customer Name </td> 
                <td width="225" align="center" class="tblheader">Business Type </td> 
                <td width="246" class="tblheader" align="center">Point of Contact </td> 
              </tr> 
              <logic:iterate id="prospectiveCust" name="lstProspectiveCust" indexId="s">
				<tr>
				<td class="tbldata" align='right' height='25';>
				<logic:notEqual value="0" name="prospectiveCust" property="customerId">
                  <bean:write name="prospectiveCust" property="customerId" ignore="true"/>
                </logic:notEqual>
                &nbsp;</td>
                
                <%
								String strCusName = "";
								int val = s.intValue();
								str=((ProspectiveCustomerVO)lstProspectiveCust.get(val)).getCustomerName();
        						strCusName = str;
								String tootipCustName = str;
								String dba = ((ProspectiveCustomerVO)lstProspectiveCust.get(val)).getCustomerDBA()==null?"":((ProspectiveCustomerVO)lstProspectiveCust.get(val)).getCustomerDBA();
								tootipCustName = tootipCustName.replaceAll("'","\\\\'");
				                tootipCustName = tootipCustName.replaceAll("\"","");
				                dba = dba.replaceAll("'","\\\\'");
				                dba = dba.replaceAll("\"","");
        						if(strCusName.length()>37)
								{
									strCusName = strCusName.substring(0,35)+"...";
							%>
									<td class="tbldata" onmouseover="constructToolTip('<%=tootipCustName%>','<%=dba%>',<bean:write name="prospectiveCust" property="prospectiveCustomerId"/>)"><%=strCusName%>&nbsp;</td>
								<%
								}
								else
								{
			%>
									<td class="tbldata" onmouseover="constructToolTip('<%=tootipCustName%>','<%=dba%>',<bean:write name="prospectiveCust" property="prospectiveCustomerId"/>)"><%=str%>&nbsp;</td>
							<%
								}
							%>
				<td class="tbldata"><bean:write name="prospectiveCust" property="businessType" ignore="true"/>&nbsp;</td>
				<td class="tbldata"><bean:write name="prospectiveCust" property="pocFirstName" ignore="true"/>&nbsp;</td>
			 </tr>
			</logic:iterate>
        
              <tr > 
                <td colspan="6" align="right">&nbsp;</td> 
              </tr> 
            </table> 
            </div>
            <table border="0" cellpadding="0" cellspacing="0" align='right'>
              <tr> 
                <td colspan="6" align="right"><input name="run" type="button" class="button" value="Next" onClick="runnext()"> 
                <input name="run" type="button" class="button" value="Cancel" onClick="window.open('<%=request.getContextPath()%>/jsp/pricerun/run.jsp','_self')"></td> 
              </tr> 
            </table>
            
            </fieldset></td> 
        </tr> 
      </table>
      </logic:notEmpty>
      
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
<%}%>
