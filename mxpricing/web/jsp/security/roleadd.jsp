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
<%@ page  import ="java.util.HashMap" %>
<%@ page  import ="java.util.List" %>
<%@ page  import ="java.util.Iterator" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@page import="com.savant.pricing.securityadmin.dao.MenuItemDao"%>
<%@page import="com.savant.pricing.securityadmin.valueobject.MenuItemsVO"%>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.security.NewRoleAddForm"/>
<jsp:setProperty name="frm" property="*"/>

<%
	MenuItemDao objMenuItemDao = new MenuItemDao();
	HashMap hmFunctions = objMenuItemDao.getAllMenuName();
	List lstmenuItemVo =  (List)hmFunctions.get("Records");
	
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

function callCancel()
{
	document.forms[0].action = '<%=request.getContextPath()%>/jsp/security/roles.jsp';
	document.forms[0].submit();
}

function userRoleAdd()
{	
	document.forms[0].formAction.value = "add";
	document.forms[0].submit();

   /* document.forms[0].formAction.value = "addRoles";
   if(document.forms[0].roleName.value.length >0)
   {
     var flag = false;
	for(var i = 0;i< document.forms[0].resourceIds.length;i++)
	{
		if(document.forms[0].resourceIds[i].checked)
		{
			flag = true;
			break;
		}
	}
	if(flag)
		document.forms[0].submit();
	else
		alert("Resources are required");
   }	
	else
	{
		alert("Role name required");
	}	*/
}

</script>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/Styles.css">
</head>
<body>
<html:form action="RoleAdd">
<html:hidden property="formAction"/>
<jsp:include page="../menu.jsp"/>
 <table width="100%" height="<%=browserHt%>" border="0" cellspacing="0" cellpadding="0"> 
    <tr> 
    	<td valign='top'>
    	 <font size="1px" face="Verdana" style="color:#FF0000"><html:errors/></font>
    	 <table width="100%" border="0" cellspacing="0" cellpadding="0">
         	<tr>
          		<td width="250" class="page_title">Role Add</td>
          	</tr>
      	 </table>
		 <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
    	      <tr> 
        	      <td class="fieldtitle">Role Name*</td> 
            	  <td class="fieldtitle">:</td> 
              	  <td  colspan = "2" class="form_data"><html:text maxlength="30" style="text-transform: uppercase" size="30" property="roleName" maxlength="50" /></td>
              </tr> 
	          <tr> 
                 <td valign="top" class="fieldtitle">Description</td> 
                 <td valign="top" class="fieldtitle">:</td> 
                 <td class="form_data" colspan = "2"><html:textarea cols="35" rows="4" property="userRoleDesc"/></td> 
	          </tr> 
			  <tr> 
                 <td valign="top" class="fieldtitle">Resources*</td> 
                 <td valign="top" class="fieldtitle">:</td> 
	          </tr>
	          <tr>
				<td></td>
				<td></td>
				<td>
					<div style="overflow:auto;height:<%=divHt%>;width:300" id="divList">
						<table> 
						       <%  
						      Iterator itr = lstmenuItemVo.iterator();
				              while(itr.hasNext())
				              {
								MenuItemsVO mvo = (MenuItemsVO)itr.next();
								%>
				               <tr> 
									<td></td>
									<td></td>
									<td class="tbldata" width='10'>
										<html:multibox property="resourceIds"  value='<%=String.valueOf(mvo.getMenuItemID())%>' />
					            	</td>	
					                <%if(mvo.getMenuItemID() == mvo.getMenuItem().getMenuItemID())
					                {
					                %>
				                	<td class="tbldata"><b><%= mvo.getMenuItemName()%></b></td>
					                <%
					                }
					                else
					                {
					                %>
					                <td class="tbldata"><%= mvo.getMenuItemName()%></td> 
									<%
									}
									%>
				              </tr> 
					          <%  
					           }
					           %>
						</table>
			   		</div>
		     	</td>
	       </tr>
	      </table>
          <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
            <tr> 
              <td class="btnbg">
              	<input name="Submit" type="button" class="button" id="btnRole" onclick = "userRoleAdd()" value="Add Role"> 
			    <html:button property="resetBtn" value="Reset" styleClass="button" onclick="document.forms[0].reset();document.forms[0].submit()"></html:button>
                <input name="Submit3" type="submit" class="button" value="Cancel" onclick = "callCancel()">
              </td> 
            </tr> 
         </table>
       </td> 
     </tr>
     <tr> 
		<td height="20">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" > 
        <tr> 
        	<%String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";%>
          <td   class="copyright_link"><%=menupath%></td>
          <td width="250"   class="copyright" align='right'>Design Rights Savant Technologies </td> 
        </tr> 
      </table>
		</td> 
  	</tr>  
 </table> 
</html:form>
</body>
</html:html>
<%}%>