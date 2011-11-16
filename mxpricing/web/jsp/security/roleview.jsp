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
<jsp:useBean id="frm" class="com.savant.pricing.presentation.security.NewRoleAddForm"/>
<jsp:setProperty name="frm" property="*" />

<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>

<%@ page import="com.savant.pricing.securityadmin.valueobject.RolesVO" %>
<%@ page import="com.savant.pricing.securityadmin.valueobject.RoleMenuItemVO" %>
<%@ page import="com.savant.pricing.securityadmin.dao.RolesDAO"%>
<%@ page import="com.savant.pricing.securityadmin.dao.MenuItemDao"%>

<%
	
	RolesVO objRoleDetails = new RolesVO();
	RolesDAO objSecurityAdminManager = new RolesDAO();
	MenuItemDao objMenuItemDao = new MenuItemDao();
	String id = request.getParameter("id");
	List objList = null;
	try
	{
		objRoleDetails = objSecurityAdminManager.getRoles(id);
		objList = objMenuItemDao.getMenuItems(objRoleDetails.getGroupId());
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
	
	pageContext.setAttribute("objList",objList);
	
	int browserHt = Integer.parseInt(String.valueOf(request.getSession().getAttribute("browserHeight")));
	int divHt = 300;
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
	{
		browserHt -= 107;
		divHt = browserHt-217;
	}
	else
	{
		browserHt -= 87;
		divHt = browserHt-197;
	}
%>
<script>
	function listUser()
	{
		temp = document.forms[0];
		temp.action = '<%=request.getContextPath()%>/jsp/security/roles.jsp';
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

<html:form action="frmRoleView"> 
<jsp:include page="../menu.jsp"/> 
<table width="100%" height="<%=browserHt%>" border="0" cellspacing="0" cellpadding="0"> 
  <tr> 
    <td valign='top'>
 		<font size="1px" face="Verdana" style="color:#FF0000"><html:errors/></font>
	 	<table width="100%" border="0" cellspacing="0" cellpadding="0">
     		<tr>
      			<td width="250" class="page_title">Role Information</td>
      		</tr>
  	 	</table>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="150" class="fieldtitle">Role Name</td> 
          <td width="1"class="fieldtitle">:</td> 
          <td class="form_data"><%= objRoleDetails.getGroupName()%></td> 
        </tr> 
		<tr> 
          <td width="150" valign="top" class="fieldtitle">Description</td> 
          <td width="1" valign="top" class="fieldtitle">:</td> 
		  <td class="form_data"><%= objRoleDetails.getDescription()%></td> 
        </tr> 
        <tr> 
          <td width="150" valign='top' class="fieldtitle">Functions</td> 
          <td width="1" valign="top" class="fieldtitle">:</td> 
          <td class="form_data">
			<div style="overflow:auto;height:<%=divHt%>;width:300" id="divList">
			<table border="0" cellspacing="0" cellpadding="0"> 
              <tr class='staticHeader'> 
                <td width="20" class="list_title">S.No</td> 
                <td width="150" class="list_title">Function Name </td> 
              </tr> 
              <%
              
              Iterator itr = objList.iterator();
              int i = 0;
              while(itr.hasNext())
              {
				RoleMenuItemVO mvo = (RoleMenuItemVO)itr.next();
				%>
               <tr> 
                <td class="list_data"><%=(i+1)+""%></td> 
                <td class="list_data"><%= mvo.getMenuItem().getMenuItemName()%>
               </td> 
              </tr> 
          <%  
          	i++;
           }
              %>
            </table>
            </div>
            </td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="form_btn_table"> 
        <tr> 
          <td><html:button property="return" styleClass="button" value="List" onclick="listUser();"/></td> 
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