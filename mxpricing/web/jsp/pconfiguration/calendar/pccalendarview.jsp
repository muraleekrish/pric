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

<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.savant.pricing.dao.HolidayDAO" %>
<%@ page import="com.savant.pricing.calculation.valueobjects.HolidayVO" %>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.security.NewRoleAddForm"/>
<jsp:setProperty name="frm" property="*" />

<%
	HolidayDAO objHolidayDAO = new HolidayDAO();
	HolidayVO objHolidayVO = new HolidayVO();
	String holidayDate = "";
	String holidayReason = "";
	
	String hldDate = request.getParameter("hldDate");
	SimpleDateFormat sdf =  new SimpleDateFormat("MM-dd-yyyy");
	try
	{
		objHolidayVO = objHolidayDAO.getHolidays(sdf.parse(hldDate));
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	pageContext.setAttribute("objHolidayVO",objHolidayVO);
%>
<script>
	function listHolidays()
	{
		temp = document.forms[0];
		temp.action = '<%=request.getContextPath()%>/jsp/pconfiguration/calendar/pcCalendar.jsp';
		temp.submit();
	}
</script>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/Styles.css">
</head>
<body> 
<html:form action="holidaysView"> 
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td valign="top"><jsp:include page="../../menu.jsp"/>
  	   <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        	<tr><td colspan='3' class='error'><html:errors/></td></tr>
        	<tr>
          		<td width="250" class="page_title">Holidays</td>
          	</tr>
  	  </table>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
      		<tr>
        		<td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          	</tr>
      </table>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
	        <tr> 
	          <td width="100" class="fieldtitle">Date*</td> 
	          <td width="1" class="fieldtitle">:</td> 
	          <td class="form_data"><bean:write name="objHolidayVO" property="date" format="MM-dd-yyyy"/></td> 
	        </tr> 
			<tr> 
	          <td width="100" valign="top" class="fieldtitle">Reason</td> 
	          <td width="1" valign="top" class="fieldtitle">:</td>
			  <td class="form_data"><bean:write name="objHolidayVO" property="reason"/></td> 
	        </tr> 
       </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="form_btn_table"> 
        <tr> 
          <td><html:button property="return" styleClass="button" value="List" onclick="listHolidays();"/></td> 
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
<%}%>