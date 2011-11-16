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
<%@ page import="com.savant.pricing.securityadmin.dao.UserDAO"%>
<%@ page import="com.savant.pricing.securityadmin.dao.RolesDAO"%>
<%@ page import="com.savant.pricing.securityadmin.valueobject.UsersVO"%>
<%@ page import="com.savant.pricing.securityadmin.valueobject.UserTypesVO"%>

<%	
	UserDAO objUserDAO = new UserDAO();
	RolesDAO objRolesDAO = new RolesDAO();
	UsersVO objUsersVO = objUserDAO.getUsers(request.getParameter("userId"));
	UsersVO objparentUsersVO = objUsersVO.getParentUser();
	UserTypesVO userTypesVO = objUsersVO.getUserTypes();
	String roleName = objRolesDAO.getRoleName(objUsersVO.getUserId());
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/Styles.css">

<script>
	function listUser()
	{
		temp = document.forms[0];
		temp.action = '<%=request.getContextPath()%>/jsp/security/user.jsp';
		temp.submit();
	}
	
</script>
</head>

<body>
<html:form action="frmUserview">
<input type="hidden" name="viewAll"/>
<jsp:include page="/menu.jsp"/>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td  valign="top"> <jsp:include page="../menu.jsp"/>
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
       <tr>
         <td width="200" class="page_title">User Information</td>
      </tr>
      <tr>
         <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
       </tr>
      </table>
	  </td>
    		<table width="100%" cellspacing="0" cellpadding="0" border ="0">
				<tr> 
					<td width="110" class="fieldtitle">User Id</td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%= objUsersVO.getUserId() %></td>
				</tr>
				<tr> 
					<td width="110" class="fieldtitle">First Name </td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%= objUsersVO.getFirstName()%></td>
				</tr>
				<tr> 
					<td width="110" class=" fieldtitle">Last Name </td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%= objUsersVO.getLastName()%></td>
				</tr>
				<tr> 
					<td width="110" class="fieldtitle">Email</td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%= objUsersVO.getEmailId()%></td>
				</tr>
				<tr> 
					<td width="110" class="fieldtitle">Comment</td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%= objUsersVO.getComment()%></td>
				</tr>
				<tr> 
						<td width="110" class="fieldtitle">User Type</td>
						<td width="1" class="fieldtitle">:</td>
						<td class="form_data"><%=userTypesVO.getUserType()%></td>
				</tr>				
				<tr> 
					<td width="110" class="fieldtitle">Parent User</td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%=objparentUsersVO.getFirstName()+" "+objparentUsersVO.getLastName()%></td>
				</tr>
				
				<tr>
						<td width="110" class="fieldtitle">Role</td>
						<td width="1" class="fieldtitle">:</td>
						<td class="form_data"><%=roleName%></td>
				</tr>
						</table><br>
	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
	<tr>
		<td><html:button property="return" styleClass="Button" value="List" onclick="listUser();"/>
	</tr>
	</table></td>
</tr>
</table>
</html:form>
</body>
</html:html>
<%}%>