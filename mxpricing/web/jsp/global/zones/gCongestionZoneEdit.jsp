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

<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/mm_menu.js"></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
</head>
<script>
  function EditZone(submitAction)
    {
		var temp = document.forms[0];
		temp.formAction.value =submitAction;
		temp.submit();
	}
	function callCancel()
	{
	document.forms[0].action = '<%=request.getContextPath()%>/global/gCongestionZone.jsp';
	document.forms[0].submit();
	}
</script>
<body> 
<script language="JavaScript1.2">mmLoadMenus();</script> 
<html:form action="CongestionZoneEdit">
<html:hidden property="formAction" />
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"><jsp:include page="/jsp/menu.jsp"/>

      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="250" class="page_title">Congestion Zone </td>
          </table></td>
        </tr>
      </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100" class="fieldtitle">Name</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><html:text property="txtCongestionZoneName" size="30" maxlength="40"/>
          </td>
          </tr>
          <tr>
            <td valign="top" class="fieldtitle">Description</td>
            <td valign="top" class="fieldtitle">:</td>
            <td class="fieldata"><html:textarea property="txtaDescription" cols="30" rows="4"></html:textarea></td>
            </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="btnbg"><html:button property="Modify"  styleClass="button" onclick="EditZone('update')" value="Modify"></html:button>
			<html:reset styleClass="button" value="Reset" onclick="document.forms[0].reset()"/>
            <html:button property="cancel" value="Cancel" styleClass="button" onclick="callCancel"/></td>
           </tr>
        </table></td> 
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
