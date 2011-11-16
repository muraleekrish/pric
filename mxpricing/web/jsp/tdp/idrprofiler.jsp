<%
try
{
	String action ="";
	String errorMsg ="";
	System.out.println("Strat ************* :"+request.getAttribute("Error"));
	
	if(request.getAttribute("Error")!=null)
	{
		System.out.println("Inside");
		errorMsg = (String)session.getAttribute("ImportStatus");
		 
	}	
		
	System.out.println("ERROR ************* :"+errorMsg);
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

<html:html>
<head>
<title>TDP</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/utils.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/calendar-setup.js"></script>
<!-- Loading language definition file -->
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/calendar-en.js"></script>
<script src='<%=request.getContextPath()%>/scripts/prototype.js' language="javascript"></script>
<script>
function callSubmit()
{
	temp=document.forms[0];	
	if(	temp.file.value=="")
	{
		alert("Select file to upload.");
	}
	else
	{
		temp.formAction.value="Import";
		 
		temp.submit();
		startTimer();
	}
}
function startTimer ( )
{
    timeId = setInterval ( "checkStatus()", 1000 );
}
function checkStatus ( )
{    
	temp=document.forms[0];
	temp.txtload.value=" Loading.........!";
	setTimeout ( 'temp.txtload.value= ""', 500 );	
}
function init()
{
document.forms[0].rdoTdsp[1].checked=true;
}


</script>
<body onload="init();">    
<html:form method="post" action="frmIDRProfiler"  enctype="multipart/form-data"> 
<input type="hidden" name="formAction">
<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="Center"> 
  <tr>
    <td  valign="top"> 
	<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
      		<tr> 
        		<td width="800" class="page_title">Typical&nbsp;Day&nbsp;Profiler<%if(errorMsg!="")%> &nbsp;&nbsp;<font color="red"><%=errorMsg%></font>
				<html:text property="message" style="text-align:right;font-size:12px;font-weight:2px; border-style:none;border-color:#FFFFFF;cursor:default;text-align=right;color:green;font-family:'Verdana', Times, serif"/>
   					<table width="100%"  border="0" cellpadding="0" cellspacing="0">
     					<tr> 
          					<td class="message"><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages></td> 
        				</tr> 
        				<tr> 
          					<td class='error'><html:errors/></td> 
        				</tr> 
   					</table>
   				</td>              
    			<td align='right' class="page_title">&nbsp;

     			</td>
				</tr>

	<!--<tr> 
		<td width="100%" class="page_title">Typical Day Profiler - Import File&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="message" style="text-align:right;font-size:12px;font-weight:2px; border-style:none;border-color:#FFFFFF;cursor:default;text-align=right;color:green;font-family:'Verdana', Times, serif"/></td> 
	</tr> -->
	</table>
	<table width="48%"  border="0" align="center"> <br>
	<tr> 
	    <td  class="tblheader" colspan=2>TDSP Type</td> 
		
	    <td width="373">&nbsp;</td>
	</tr> 
	<tr> 
	    <td width="72"  class=""><html:radio property="rdoTdsp" value="CenterPoint" />CenterPoint</td> 
	    <td width="81"  class="">&nbsp;</td>
	    <td>&nbsp;</td>
	</tr> 
	<tr> 
	    <td width="72"  class=""><html:radio property="rdoTdsp" value="Oncor" />Oncor</td> 
	    <td width="81"  class="">&nbsp;</td>
	    <td>&nbsp;</td>
	</tr> 
	<tr> 
	    <td width="72"  class=""><html:radio property="rdoTdsp" value="AEP" />AEP</td> 
	    <td width="81"  class="">&nbsp;</td>
	    <td>&nbsp;</td>
	</tr>
	<tr></tr>
	<tr></tr>
	<tr> 
	    <td width="72"  class="">File Name: </td> 
	    <td width="81"  class=""><html:file property="file" size="80"/></td>
	    <td>&nbsp;</td>
	</tr> 
	</table>
	<br>
	<table align="center">
	<tr> 
	 <td align=left> <input type=text name="txtload" style="text-align:right;font-size:10px; border-style:none;border-color:#FFFFFF;cursor:default;text-align=right;color:red;font-family:'Verdana', Times, serif"></td>   <td align=left><input name="btnImport" type="button" value="Import" onclick="callSubmit()"></td>	
	</tr> 

	</table>	
</html:form> 
</body>
</html:html>
<%}
}
catch (Exception e) {
    e.printStackTrace();
} 

%>