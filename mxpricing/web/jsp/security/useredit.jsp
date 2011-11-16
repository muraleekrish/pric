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
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.savant.pricing.securityadmin.dao.UserTypeDAO"%>
<%@ page import="com.savant.pricing.securityadmin.dao.UserDAO"%>
<%@ page import="com.savant.pricing.securityadmin.valueobject.UsersVO"%>
<%@ page import="com.savant.pricing.securityadmin.dao.RolesDAO"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.security.UserForm"/>
<jsp:setProperty name="frm" property="*"/>

<%
	UserTypeDAO objUserTypeDAO = new UserTypeDAO();
	UserDAO objUserDAO = new UserDAO();
	List records = objUserTypeDAO.getUserType();
	String parntUsr = "";
	
	List userRecords = new ArrayList();
	if(frm.getFormAction().equals("userTypeChange") || request.getParameter("parentUsr")!=null || frm.getFormAction().equals("update"))
	{
		String usrType = frm.getUserType()==null?request.getParameter("usrType"):frm.getUserType();
		if(!usrType.equals("0"))
		{
			userRecords = (List)objUserDAO.getParentTypeUsers(Integer.parseInt(usrType.trim()));
			parntUsr = frm.getParentUserid()==null?request.getParameter("parentUsr"):frm.getParentUserid();
		}
	}
	RolesDAO rolesDAO = new RolesDAO();
	HashMap hmRoles =rolesDAO.getRoles();
	List roleRecords = (List)hmRoles.get("Records");
	pageContext.setAttribute("records",records);
	pageContext.setAttribute("userRecords",userRecords);
	pageContext.setAttribute("roleRecords",roleRecords);
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

function updateUser(submitAction)
{
	var temp = document.forms[0];
	temp.formAction.value =submitAction;
}

function callUserTypeChange(userChange)
{
	var temp = document.forms[0];
	var userTypeId = temp.userType.value;
	var userId = temp.userName.value;
	var url = '<%=request.getContextPath()%>/servlet/getParentUser';
	var param = 'userTypeId='+userTypeId+'&userId='+userId+'&formAction='+userChange;
	if (window.XMLHttpRequest) // Non-IE browsers
	{
		req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: loadPatentUser});
	}
	else if (window.ActiveXObject) // IE
	{
		req = new ActiveXObject("Microsoft.XMLHTTP");
		if (req)
		{
			req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: loadPatentUser});
		}
	}
}

function loadPatentUser(req)
{
	var temp = document.forms[0];
	var a = req.responseText.split("@#$");
    if(a[0]=="userIdChange")
    {
    	temp.firstName.value = a[1];
    	temp.lastName.value = a[2];
    	temp.email.value = a[3];
    }
    else if(a[0]=="userTypeChange")
    {
    	var resRateCode = document.getElementById("idPartentUser");
		resRateCode.innerHTML = a[1];
    }
}

function resetBtn()
{
	temp = document.forms[0];
	temp.userType.options[temp.userType.selectedIndex].text = "Select one";
}

function loadDefault()
{
	temp = document.forms[0];
	temp.parentUserid.value = "<%=parntUsr%>";
	disable();
}

function callCancel()
{
	document.forms[0].action = '<%=request.getContextPath()%>/jsp/security/user.jsp';
	document.forms[0].submit();
}
function disable()
{
	var temp = document.forms[0];
	if(temp.changePasswordStatus.value != "changed")
	{
			old_pwd.style.display = "none";
			new_pwd.style.display = "none";	
			con_pwd.style.display = "none";
			document.forms[0].changePasswordStatus.value = "notchanged";
	}
}
function showPassword()
{
	var temp = document.forms[0];
	if(temp.changePasswordStatus.value == "notchanged")
	{
		temp.changePasswordStatus.value = "changed";
		old_pwd.style.display = "block";
		new_pwd.style.display = "block";
		con_pwd.style.display = "block";
		temp.oldPassword.value = "";
		temp.newPassword.value = "";
		temp.conPassword.value = "";
	}
	else
	{
		temp.changePasswordStatus.value="";
		disable();
	}
}

</script>
<body onload='loadDefault();'> 
<html:form action="userEdit" >
<html:hidden property="formAction" />
<html:hidden property="changePasswordStatus" />
<html:hidden property="password" />

<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/><font size="1px" face="Verdana" style="color:#FF0000"><html:errors/></font>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="250" class="page_title">User</td>
          </tr>
      </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
       <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		 <tr>
            <td width="150" class="fieldtitle">User Id </td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><html:text styleClass="textbox" property="userName" size="30" maxlength="50" readonly = "true" style="border:0px"/>
			</td>
          </tr>
          <tr>
            <td width="150" class="fieldtitle">First Name </td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><html:text styleClass="textbox" property="firstName" size="30" maxlength="50" /></td>
          </tr>
		   <tr>
            <td width="150" class="fieldtitle">Last Name </td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><html:text styleClass="textbox" property="lastName" size="30" maxlength="50" /></td>
          </tr>
		  <tr>
            <td width="150" class="fieldtitle">Password*</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><a href = '#' onclick = 'showPassword()'> Change Password </a> </td>
          </tr>
          
          <tr id = 'old_pwd'>
          	<td width="150" class="fieldtitle">Old Password*</td>
          	<td width="1" class="fieldtitle">:</td>
          	<td class="fieldata"><html:password styleClass="textbox" property="oldPassword" size="30" maxlength="20" /></td>
          </tr>
          
          <tr id = 'new_pwd'>
          	<td width="150" class="fieldtitle">New Password*</td>
          	<td width="1" class="fieldtitle">:</td>
          	<td class="fieldata"><html:password styleClass="textbox" property="newPassword" size="30" maxlength="20" /></td>          
          </tr>
                    
          <tr id = 'con_pwd'>
          	<td width="150" class="fieldtitle">Confirm Password*</td>
          	<td width="1" class="fieldtitle">:</td>
           	<td class="fieldata"><html:password styleClass="textbox" property="conPassword" size="30" maxlength="20" /></td>
          </tr>
          
          <tr>
            <td width="150" class="fieldtitle">Email Id*</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><html:text styleClass="textbox" property="email" size="30" maxlength="50" /></td>
          </tr>
          <tr>
            <td valign="top" class="fieldtitle">Comment</td>
            <td valign="top" class="fieldtitle">:</td>
            <td class="fieldata"><html:textarea property="comment" cols="40" rows="4"/></td>
          </tr>
          <tr>
            <td width="150" class="fieldtitle">User Type*</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata">
				<html:select property="userType" onchange="callUserTypeChange('userTypeChange');">
					<option value="0">Select one</option>
					<html:optionsCollection name="records" label="userType" value="userTypeId"/>
				</html:select>
			</td>
		  </tr>
          <tr>
            <td width="150" class="fieldtitle">Parent User* </td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata" id='idPartentUser'>
				<html:select property="parentUserid" >
					<option value="0">Select one</option>
				<% 
          			for(int i=0;i<userRecords.size();i++)
		          	{          	
						UsersVO objUsersVO = (UsersVO)userRecords.get(i);
						objUsersVO.getUserId();
						String lName = String.valueOf(objUsersVO.getLastName());
						lName = lName.equals("null")?" ":lName;
						String parentName = objUsersVO.getFirstName()+" "+lName;
						%>
						<option value="<%=objUsersVO.getUserId()%>"><%=parentName%></option>
						<%
		           	}
          		%>
				</html:select>
			</td>
		  </tr>		  
		  <tr>
            <td width="150" class="fieldtitle">Role*</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><html:select property="role" >
								<html:optionsCollection name="roleRecords" label="groupName" value="groupId"/>
								</html:select></td>			
		  </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="btnbg"><html:submit property="update" styleClass="button" value="Update" onclick="updateUser('update')"></html:submit>
					<html:reset value="Reset" styleClass="button" onclick='resetBtn()'></html:reset>
                   <html:button property="Cancel" styleClass="button" value="Cancel" onclick="callCancel()"></html:button >
                   </td></tr>
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