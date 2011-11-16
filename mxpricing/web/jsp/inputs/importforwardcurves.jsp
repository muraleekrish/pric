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

<jsp:useBean id="frm" class="com.savant.pricing.presentation.fulcruminput.ImportInputEnergyForm" />
<%@ page import="com.savant.pricing.dao.ForwardCurveBlockDAO"%>
<%@ page import="com.savant.pricing.dao.GasPriceDAO"%>

<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<jsp:setProperty name="frm" property="*" />
<html:html>
<%
SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
	ForwardCurveBlockDAO objForwardCurveBlockDAO = new ForwardCurveBlockDAO();
	Date fwdCurveLastImported = objForwardCurveBlockDAO.fwdCurveLastImportedOn();
	GasPriceDAO objGasPriceDAO = new GasPriceDAO();
	Date gasPriceLastImported = objGasPriceDAO.teeNaturalGasPriceLastImportedOn();

%>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/mm_menu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script>
function displaydate(select)
{
    if(select=='Energy')
	 {
	    document.getElementById('engimportdate').style.display = 'block';
		document.getElementById('gasimportdate').style.display = 'none';
	}
	else
	{
	   document.getElementById('engimportdate').style.display = 'none';
	   document.getElementById('gasimportdate').style.display = 'block';
	}
}
function callsubmit()
{
var temp = document.forms[0];
temp.formActions.value = 'Import';
temp.submit();
}
</script>
</head>
<body > 
<html:form action="/inputXls" method="post" enctype="multipart/form-data">
<html:hidden property="formActions" />
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="/jsp/menu.jsp"/> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="250" class="page_title">Import&nbsp;Forwards </td>
          </tr>
      </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
          <tr>
		<td colspan='6' class="message">
		<html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /><br></html:messages>
		</td>
		</tr><tr>
			<td colspan='6' class='error'><html:errors/></td>
		</tr>
        </table>
		<fieldset><br> <legend>
		Import&nbsp;Forward&nbsp;Curves (Natural Gas and Electric Price)</legend>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
         <!--  <tr>
            <td width="692">&nbsp;</td>
			<td width="95" align='right'>Last&nbsp;Imported&nbsp;Date</td>
            <td width="1" valign="middle">:</td>
            <td id='engimportdate' style="display:block">&nbsp;<%=fwdCurveLastImported==null?"":df.format(fwdCurveLastImported)%></td><td id='gasimportdate' style="display:none">&nbsp;<%=gasPriceLastImported==null?"":df.format(gasPriceLastImported)%></td>
          </tr> -->
		  <!-- <tr>
           <td class="fieldtitle" width="14%" >Market Date </td>
            <td class="fieldtitle" width="1%">:</td>
            <td class="fieldtitle" width="12%"><html:text property="txtforwarddate" styleClass="textbox" styleId="txtforwarddateFrom" size="10" readonly="true"/><a href="#" onClick="showCalendarControl(document.getElementById('txtforwarddateFrom'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" name="imgDateFrom" width="16" height="16" border="0" align="absmiddle" id="imgDateFrom"></a></td>
			<td class="fieldtitle" width="69%"><html:file property ="forwardBrowse" styleClass="botton"  size="50"   onkeypress="return false" /></td>
			</tr> -->
			<tr>
			<td class="" >&nbsp;</td>
			<td class="">&nbsp;</td>
		    <td class="">&nbsp;</td>
		    <td class="">&nbsp;</td>
			</tr>

			<tr>
   	        <td class="fieldtitle" width="14%" >Market Date </td>
            <td class="fieldtitle" width="1%">:</td>
            <td class="fieldtitle" width="12%"><html:text property="txtgasdate" styleClass="textbox" styleId="txtgasdateFrom" size="10" readonly="true"/><a href="#" onClick="showCalendarControl(document.getElementById('txtgasdateFrom'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" name="imgDateFrom" width="16" height="16" border="0" align="absmiddle" id="imgDateFrom"></a></td>
			<td class="fieldtitle" width="69%"><html:file property ="gasBrowse" styleClass="botton"  size="50"   onkeypress="return false" /></td>
		  </tr>
		  <tr>
			    <td class="" colspan="10"><input name="Submit" type="button" class="button" value="Import" onclick='callsubmit()'></td>
              </tr>
            </table>
			
		</fieldset><br>
		</td>
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