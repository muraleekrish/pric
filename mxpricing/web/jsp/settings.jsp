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
<%@ page import="com.savant.pricing.securityadmin.valueobject.UsersVO"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.security.UserSettingsForm"/>
<jsp:setProperty name="frm" property="*"/>

<%
	UserDAO objUserDAO = new UserDAO();
	String usrName = String.valueOf(session.getAttribute("userName"));
	UsersVO objUsersVO = null;
	objUsersVO = (UsersVO)objUserDAO.getUsers(usrName);
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/mm_menu.js"></script>
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
</head>
<script>

function updateSettings(submitAction)
{
	var temp = document.forms[0];
	temp.formAction.value =submitAction;
}

function callCancel()
{
	history.back();
}

</script>
<body> 
<html:form action="settingsEdit" >
<html:hidden property="formAction" />
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td valign="top"><jsp:include page="menu.jsp"/><font size="1px" face="Verdana" style="color:#FF0000"><html:errors/></font>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="250" class="page_title">Settings</td>
          </tr>
      </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
       <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		 <tr>
            <td width="100" class="fieldtitle">User Id</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><%=usrName%></td>
          </tr>
          <tr>
            <td width="120" class="fieldtitle">First Name*</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><html:text styleClass="textbox" property="firstName" size="30" maxlength="50" /></td>
          </tr>
		   <tr>
            <td width="120" class="fieldtitle">Last Name</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><html:text styleClass="textbox" property="lastName" size="30" maxlength="50" /></td>
          </tr>
		  <tr>
            <td width="120" class="fieldtitle">Password*</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><html:password styleClass="textbox" property="password" size="30" maxlength="20" /></td>
          </tr>
          <tr>
            <td width="120" class="fieldtitle">Confirm Password*</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><html:password styleClass="textbox" property="confirmPwd" size="30" maxlength="20" /></td>
          </tr>
          <tr>
            <td width="120" class="fieldtitle">Email Id*</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><html:text styleClass="textbox" property="email" size="30" maxlength="50" /></td>
          </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="btnbg">
				<html:submit property="update" styleClass="button" value="Update" onclick="updateSettings('update')"></html:submit>
				<html:reset value="Reset" styleClass="button"></html:reset>
                <html:button property="Cancel" styleClass="button" value="Cancel" onclick="callCancel()"></html:button >
            </td>
          </tr>
        </table>
      </td> 
  </tr> 
  <tr> 
    <td height="20"><table width="100%"  border="0" cellpadding="0" cellspacing="0" class="copyright"> 
        <tr> 
          <td>Design Rights Savant Technologies </td> 
        </tr> 
      </table></td> 
  </tr> 
</table> 

</html:form>
</body>
</html:html>
<%}%>