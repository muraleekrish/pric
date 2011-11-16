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
<%String greetings = new com.savant.pricing.securityadmin.dao.UserDAO().defineCurrentGreeting();%>
<script src="<%=request.getContextPath()%>/script/drop_down_menus.js"></script>
<script>
 
 function EditSettings()
 {
	var temp = document.forms[0];
	var settigsUsrName = '<%=String.valueOf(session.getAttribute("userName"))%>';
	temp.action='<%=request.getContextPath()%>/settingsEdit.do?userid='+settigsUsrName+'&formAction=edit';
	temp.submit();
 }
 
 </script>
<%@ page import="com.savant.pricing.common.BuildConfig"%>
 <html:hidden property="formAction" value="" /> 
<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
  <tr> 
    <td bgcolor="#666666"><img src="spacer.gif" width="1" height="8" ></td> 
    <td colspan="3" bgcolor="#ff0000"><img src="spacer.gif" width="1" height="8" ></td> 
  </tr> 
  <tr> 
    <td width="150"><img src="<%=request.getContextPath()%>/images/logo_small.gif" width="149" height="50"></td> 
    <td  class="menubg" align="left" valign="bottom" style="padding:0px 0px 0px 30px "><%=session.getAttribute("constructedMenu")%></td>     <%
			
          	String strFrstName = String.valueOf(session.getAttribute("firstName"));
          	String strSubStrFrstName = strFrstName;
          	if(strFrstName.length()>17)
          		strSubStrFrstName = strFrstName.substring(0,15)+"...";
          %> 
    <td  width="250" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="loginfo"><%=greetings%>,
      <%
          	if(strFrstName.length()>17)
          		out.write("<span title='"+strFrstName+"'>");
          %> 
      <%=strSubStrFrstName%> 
      <%
          	if(strFrstName.length()>17)
          		out.write("</span>");
          %> 
&nbsp;<a href="#" onclick="javascript:EditSettings()"><img src="<%=request.getContextPath()%>/images/icon_settings.gif" width="16" height="16" align="absmiddle" alt='Settings' style='border:0'></a> <a href="<%=request.getContextPath()%>/jsp/logoff.jsp"><img src="<%=request.getContextPath()%>/images/icon_logoff.gif" width="16" height="16" border="0" align="absmiddle" alt='Sign out'></a></td>
  </tr>
  <tr>
    <td class="menubg" align='right' style='color:#666666'><strong>Environment : <%=BuildConfig.Env_Variable%>&nbsp;&nbsp;</strong></td>
  </tr>
</table></td> 
  </tr> 
</table> 
<table width="100%"  border="0" cellspacing="0" cellpadding="0" bgcolor="#666666"> 
  <tr> 
    <td  class="menubg" ><img width="1" height="5" src="Spacer.gif"></td> 
   
  </tr> 
</table> 
<%}%> 
