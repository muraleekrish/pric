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
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.pconfiguration.CalendarForm"/>
<jsp:setProperty name="frm" property="*"/>

<script>

function roleUpdate()
{
   	document.forms[0].formActions.value = "update";
	document.forms[0].submit();
}

function callCancel()
{
	document.forms[0].action = '<%=request.getContextPath()%>/jsp/pconfiguration/calendar/pcCalendar.jsp';
	document.forms[0].submit();
}

function validateTextArea()
{
	var strReason = document.forms[0].txtReason.value
	if(strReason.length > 199)
	{ 
		alert("Not allowed to enter more than 200 Characters.") 
		document.forms[0].txtReason.value = strReason.substring(0,199);
	}
}

function textCounter(field, countfield, maxlimit) 
{
	if(field.value.length > maxlimit)
		field.value = field.value.substring(0, maxlimit);
	else 
		countfield.value = maxlimit - field.value.length;
}

function setRemainLen()
{
	document.forms[0].remLen.value = 200-document.forms[0].txtReason.value.length
}

</script>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/Styles.css">
<script src='<%=request.getContextPath()%>/script/CalendarControl.js' language="javascript"></script>
</head>
<body onload='setRemainLen()'>
<html:form action="holidaysEdit">
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
              	  <td class="form_data"><html:text property="txtDate" readonly="true" styleClass="textbox" onfocus="true" size="10" /></td>
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
                  	<input name="Submit" type="button" class="button" id="btnRole" onclick = "roleUpdate()" value="Update"> 
                    <input name="Submit2" type="button" class="button" value="Reset" onclick="document.forms[0].reset();document.forms[0].submit()"> 
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
