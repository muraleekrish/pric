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
<%@ page import="com.savant.pricing.securityadmin.dao.UserTypeDAO"%>

<%
UserTypeDAO objUserTypeDAO = new UserTypeDAO();
 List records = objUserTypeDAO.getUserType(); 
 pageContext.setAttribute("records",records);
%>

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

function addCustomer(submitAction)
{
	var temp = document.forms[0];
	if(temp.userType.value.length>0)
	{
		temp.formAction.value =submitAction;
		temp.submit();
	}
}

function callCancel()
{
	document.forms[0].action = '<%=request.getContextPath()%>/jsp/security/usertype.jsp';
	document.forms[0].submit();
}

</script>
<body> 
<html:form action="userTypeEdit">
<html:hidden property="formAction" />
<html:hidden property="userTypeId" />

<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/>
	<font size="1px" face="Verdana" style="color:#FF0000"><html:errors/></font>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="250" class="page_title">User Type </td>
          </tr>
      </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100" class="fieldtitle">User Type*</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><html:text styleClass="textbox" property="userType" size="30" readonly='true' /></td>
          </tr>
		   <tr>
            <td width="100" class="fieldtitle">Parent Type*</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><html:select property="parentType" >
								<html:optionsCollection name="records" label="userType" value="userTypeId"/>
								</html:select></td>
          </tr>
          <tr>
            <td valign="top" class="fieldtitle">Description</td>
            <td valign="top" class="fieldtitle">:</td>
            <td class="fieldata"><html:textarea property="comment" cols="40" rows="4"/></td>
          </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="btnbg"><html:button  property="button" styleClass="button" value="Update" onclick="addCustomer('update')"></html:button>
					<html:reset value="Reset" styleClass="button"></html:reset >
                   <html:button property="Cancel" styleClass="button" value="Cancel" onclick="callCancel()"></html:button >
                   </td>
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
