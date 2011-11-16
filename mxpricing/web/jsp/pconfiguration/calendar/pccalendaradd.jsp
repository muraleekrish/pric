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
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pconfiguration.CalendarForm"/>
<jsp:setProperty name="frm" property="*"/>

<script>

function callCancel()
{
	document.forms[0].action = '<%=request.getContextPath()%>/jsp/pconfiguration/calendar/pcCalendar.jsp';
	document.forms[0].submit();
}
function holidaysAdd()
{	
	document.forms[0].formActions.value = "add";
	document.forms[0].submit();
}
function textCounter(field, countfield, maxlimit) 
{
	if(field.value.length > maxlimit)
		field.value = field.value.substring(0, maxlimit);
	else 
		countfield.value = maxlimit - field.value.length;
}
</script>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/Styles.css">
<script src='<%=request.getContextPath()%>/script/CalendarControl.js' language="javascript"></script>
</head>
<body>
<html:form action="holidaysAdd">
<html:hidden property="formActions"/>

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td valign="top"><jsp:include page="../../menu.jsp"/>
  		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
        	<tr><td colspan='3' class='error'><html:errors/></td></tr>
        	<tr>
          		<td width="250" class="page_title">Holidays</td>
          	</tr>
  		</table>
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
      		<tr>
        		<td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          		</tr>
       	</table>
	 	<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
    	      <tr> 
        	      <td width="100" class="fieldtitle">Date*</td> 
            	  <td width="1" class="fieldtitle">:</td> 
              	  <td class="form_data"><html:text property="txtDate" readonly="true" styleClass="textbox" onfocus="true" size="10" onkeydown="if((event.keyCode>47 && event.keyCode<58) || (event.keyCode>95 && event.keyCode<106) ||(event.keyCode==110)||(event.keyCode==8) ||(event.keyCode==46) ||(event.keyCode==37) ||(event.keyCode==39) || (event.keyCode==9) ||(event.keyCode==189))event.returnValue = true; else event.returnValue = false"/> <a href ="#" onClick="showCalendarControl(document.getElementById('txtDate'),'fully')"><img src='<%=request.getContextPath()%>/images/calendar.gif' name="imgDateFrom" width="16" height="16" border="0" align="absmiddle" id="imgDateFrom"></a></td>
              </tr> 
	          <tr> 
                 <td width="100" valign="top" class="fieldtitle">Reason</td> 
                 <td width="1" valign="top" class="fieldtitle">:</td> 
                 <td class="form_data" colspan = "2"><html:textarea property="txtReason" cols="40" rows="4" onkeydown="textCounter(document.forms[0].txtReason,document.forms[0].remLen,199)" onkeyup="textCounter(document.forms[0].txtReason,document.forms[0].remLen,199)" onmouseover="textCounter(document.forms[0].txtReason,document.forms[0].remLen,199)"/>&nbsp;( You may enter up to 200 characters. )&nbsp;<input readonly type=text name=remLen size=1 maxlength=3 value=""> characters left</td> 
	           </tr> 
		  </table>
          <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="form_btn_table"> 
            <tr> 
              <td>
              	<input name="Submit" type="button" class="button" id="btnRole" onclick = "holidaysAdd()" value="Add"> 
			    <html:button property="resetBtn" value="Reset" styleClass="button" onclick="document.forms[0].reset();document.forms[0].submit()"></html:button>
                <input name="Submit3" type="submit" class="button" value="Cancel" onclick = "callCancel()"></td> 
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
