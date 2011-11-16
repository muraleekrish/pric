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
<jsp:useBean id="frm" class="com.savant.pricing.pconfig.deallevers.CostofCapitalEditForm" />

<%@ page import="com.savant.pricing.dao.UOMDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.UOMVO"%>
<%@ page import="com.savant.pricing.dao.CostOfCapitalDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.CostOfCapitalVO"%>
<%@ page import="java.util.List"%>
<jsp:setProperty name="frm" property="*" />

<html:html>
<head>
<%
UOMDAO objUOMDAO = new UOMDAO();
List lstUOM = objUOMDAO.getAllUOM();
pageContext.setAttribute("lstUOM",lstUOM);
String id = request.getParameter("cocId");
UOMVO objUOMVO = new UOMVO();


CostOfCapitalDAO objCostOfCapitalDAO = new CostOfCapitalDAO();
CostOfCapitalVO objCostOfCapitalVO = new CostOfCapitalVO();
try
	{
		objCostOfCapitalVO = objCostOfCapitalDAO.getCoc(Integer.parseInt(id));
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}

%>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
</head>
<script>
function update()
{

	var temp = document.forms[0];
	if(temp.value.value==".")
		temp.value.value = 0.0;
	
	temp.formAction.value ="Modify";
	temp.submit();
}
function checkEnter(e)
   { 
      var characterCode;
      e = event
      characterCode = e.keyCode ;
      var dot = document.getElementById('value').value.split(".");
     if( (characterCode>=48) && (characterCode<=57) || characterCode == 46 && (dot.length == 1 || dot.length == 0))
     {
		  return true;
	  }
	  else
	  {
	  return false;
	  }
   }
   function loaddefault()
   {
   var temp = document.forms[0];
	switch (parseInt(temp.cocId.value)){	
	case 1: 
	 document.getElementById('maxvalue').innerText = 'Max. value: 20.0';
	break;
	case 2:
		document.getElementById('maxvalue').innerText = 'Max. value: 0.009';
    break;
	case 3:
		document.getElementById('maxvalue').innerText = 'Max. value: 0.01';
	break;
	case 4:
		document.getElementById('maxvalue').innerText = 'Max. value: 0.005';
	break;
	case 5:
		document.getElementById('maxvalue').innerText = 'Max. value: 0.05';
	break;
	case 6:
		document.getElementById('maxvalue').innerText = 'Max. value: 0.009';
	break;
	case 7:
		document.getElementById('maxvalue').innerText = 'Max. value: 5';
	break;
	default:
	}
   }

   function callCancel()
	{	
		var temp = document.forms[0];
		temp.action = '<%=request.getContextPath()%>/jsp/pconfiguration/dealLevers/costCapital.jsp';
		temp.submit();
	}
   
</script>
<body onload=''> 
<html:form action="/costCapEdit">
<html:hidden property="formAction"/>
<html:hidden property="cocId"/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="../../menu.jsp"/>
		<font class="error"><html:errors/></font>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="250" class="page_title">Cost&nbsp;of&nbsp;Capitals</td>
          </tr>
      </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="fieldtitle">Cost&nbsp;of&nbsp;Capital</td>
            <td class="fieldtitle">:</td>
            <td class="fieldata"><html:text property="cocName" styleClass="textbox" readonly="true" size="30" style="border:0px"/></td>
          </tr>
          <tr>
            <td width="120" class="fieldtitle">Value</td>
            <td width="1" class="fieldtitle">:</td>
            <td width="50" class="fieldata"><html:text property="value" styleClass="textbox" styleId="value" onkeypress="return checkEnter(event)" maxlength="20" />&nbsp;</td>
          </tr>
          <tr>
            <td class="fieldtitle">Unit of Measure </td>
            <td class="fieldtitle">:</td>
            <td class="fieldata"> <%=objCostOfCapitalVO.getUnit().getUnit()%>
			<html:select property="cmboUnit" style="display:none">
					 <html:option value="0" >Select one</html:option>
					 <html:options collection="lstUOM" property="uomIdentifier" labelProperty="unit" />
			</html:select>
			</td>
          </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="btnbg"><input name="Submit" type="button" class="button" value="Update" onclick = "update()">
            <input name="Submit3" type="reset" class="button" value="Reset" >
            <input name="Submit2" type="button" class="button" value="Cancel" onClick="callCancel();">
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
