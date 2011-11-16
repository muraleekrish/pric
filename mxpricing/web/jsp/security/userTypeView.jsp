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
<%@ page import="com.savant.pricing.securityadmin.dao.UserTypeDAO"%>
<%@ page import="com.savant.pricing.securityadmin.valueobject.UserTypesVO"%>

<%
try{
UserTypeDAO objUserTypeDAO = new UserTypeDAO();
UserTypesVO objUserType = null;
UserTypesVO parentobjUserType = null;

if(request.getParameter("userId")!=null)
objUserType = objUserTypeDAO.getUserType(Integer.parseInt(request.getParameter("userId")));
else 
objUserType = new UserTypesVO();

if(objUserType.getParentUserType()!=null)
parentobjUserType = objUserType.getParentUserType();
else
parentobjUserType =  new UserTypesVO();

%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
</head>
<script>
function returnToList()
{
document.forms[0].formAction.value="list";
document.forms[0].submit();
}
</script>
<body> 
<html:form action="UserTypeview">
<html:hidden property="formAction" />
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="250" class="page_title">User Type </td>
          <td class="page_title">&nbsp;</td>
        </tr>
      </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%= request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100" class="fieldtitle">User Type</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><%=objUserType.getUserType()== null?"":objUserType.getUserType()%></td>
          </tr>
		  <tr>
            <td width="100" class="fieldtitle">Parent Type</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><%=parentobjUserType.getUserType()==null? "":parentobjUserType.getUserType()%> </td>
          </tr>
          <tr>
            <td valign="top" class="fieldtitle">Description</td>
            <td valign="top" class="fieldtitle">:</td>
            <td class="fieldata"><%=objUserType.getDescription()==null? "":objUserType.getDescription()%></td>
          </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="btnbg">             
             <input name="Submit3" type="submit" class="button" value="List" onclick="returnToList()"></td>
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
<%}catch(Exception e)
{
e.printStackTrace();
}
}
%>
