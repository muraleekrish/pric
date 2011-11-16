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
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="com.savant.pricing.matrixpricing.dao.MMPriceRunStatusDAO"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.matrix.XpressPricingListForm" /><jsp:setProperty name="frm" property="*" />
<%
  
%>
<html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script src='<%=request.getContextPath()%>/script/commonSort.js'></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
</head>
<script>
</script>
<body onload=''> 
<html:form action="/frmPriceMatrix">

<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/> 
      <!-- Menu End --> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td class="message"> <logic:messagesPresent message="true" > 
		  <html:messages id="messageid" message="true"><bean:write name="messageid" /></html:messages> </logic:messagesPresent> </td> 
          <td class='error'><html:errors/></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="950" class="page_title" align="center"><font size="5"> Page Under Construction </font></td> 
        </tr> 
      </table> 
	  </td>
	  </tr>
	  </table>
     
</html:form> 
</body>
</html>
<%}%>
