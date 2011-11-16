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

<%@ page import="com.savant.pricing.dao.TDSPDAO"%>
<%@ page import="com.savant.pricing.valueobjects.TDSPVO"%>
<%@ page import="com.savant.pricing.valueobjects.RateCodesVO"%>
<%@ page import="com.savant.pricing.dao.RateCodesDAO"%>
	
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pconfiguration.TDSPChargesListForm" />
<jsp:setProperty name="frm" property="*" />

<%
	int browserHt = 0;
	
	TDSPDAO objTDSPDAO = new TDSPDAO();
	TDSPVO objTDSPVO = null;
	List selAllTDSP = null;
	List hmRateCodes = null;
	
	selAllTDSP = objTDSPDAO.getAllTDSP();
	pageContext.setAttribute("selAllTDSP",selAllTDSP);
	objTDSPVO = (TDSPVO)selAllTDSP.get(0);
 	int frstTDSPId = objTDSPVO.getTdspIdentifier();
 	frm.setSearchTDSP(String.valueOf(frstTDSPId));
 	
 	RateCodesDAO objRateCodesDAO = new RateCodesDAO();
 	RateCodesVO objRateCodesVO = null;
	objTDSPVO = objTDSPDAO.getTDSP(frstTDSPId);
   	hmRateCodes = (List)objRateCodesDAO.getAllRateCodes(objTDSPVO.getTdspIdentifier());
	pageContext.setAttribute("hmRateCodes",hmRateCodes);
	
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 195;
	else
		browserHt = 175;
		
	/* if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt -= 341;
	else
		browserHt -= 321; */
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
<script type="text/javascript">

function CallLoadRateCode(message)
{
	var temp = document.forms[0];
	var tdspId = temp.searchTDSP.value;
	var url = '<%=request.getContextPath()%>/servlet/rateCode';
	var rateCodes = temp.searchRateCode.value;
	var param = 'tdspId='+tdspId+'&Message='+message+'&rateCodes='+rateCodes;
	if (window.XMLHttpRequest) // Non-IE browsers
	{
		req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: loadRateCode});
	}
	else if (window.ActiveXObject) // IE
	{
		req = new ActiveXObject("Microsoft.XMLHTTP");
		if (req)
		{
			req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: loadRateCode});
		}
	}
}

function loadRateCode(req)
{
    var a = req.responseText.split("@#$");
    if(a[0]=="ratecode")
    {
    	var resRateCode = document.getElementById("idRateCode");
    	resRateCode.innerHTML = a[1];
    	CallLoadRateCode('value');
    }
    else
    {
    	var resTrTable = document.getElementById("trtable");
    	resTrTable.innerHTML = a[1];
    }
}

function onLoadRateCode(message)
{
	var temp = document.forms[0];
	var tdspId = <%=frm.getSearchTDSP()%>
	var url = '<%=request.getContextPath()%>/servlet/rateCode';
	var rateCodes = <%=frm.getSearchRateCode()%>
	var param = 'tdspId='+tdspId+'&Message='+message+'&rateCodes='+rateCodes;
	if (window.XMLHttpRequest) // Non-IE browsers
	{
		req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: onloadFunct});
	}
	else if (window.ActiveXObject) // IE
	{
		req = new ActiveXObject("Microsoft.XMLHTTP");
		if (req)
		{
			req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: onloadFunct});
		}
	}
}

function onloadFunct(req)
{
    var a = req.responseText.split("@#$");
    if(a[0]=="ratecode")
    {
    	var resRateCode = document.getElementById("idRateCode");
    	resRateCode.innerHTML = a[1];
    	onLoadRateCode('value');
    }
    else
    {
    	var resTrTable = document.getElementById("trtable");
    	resTrTable.innerHTML = a[1];
    }
    var temp = document.forms[0];
    temp.searchRateCode.value = <%=frm.getSearchRateCode()%>;
}

</script>
<body onload="onLoadRateCode('ratecode');">
<html:form action="TDSPChargesList" >
<html:hidden property="formActions"/>

<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="../../menu.jsp"/>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        	<tr>
          		<td class="page_title">TDSP Charges</td>          
          	</tr>
         </table> 
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="50" class="search" align='right'>TDSP</td>
            <td width="1" class="search">:</td>
            <td width="220" class="search">
			 <html:select property="searchTDSP" onchange="CallLoadRateCode('ratecode');">
					<html:option value="0">Select one</html:option>
					<html:optionsCollection name="selAllTDSP" label="tdspName" value='tdspIdentifier' />
				</html:select> 
			</td>
            <td width="100" class="search" align='right'>Rate Code</td>
            <td width="1" class="search">:</td>
            <td width="220" class="search" id='idRateCode'>
				 <html:select property="searchRateCode" onchange="CallLoadRateCode('value')">
					<html:option value="-1">Select one</html:option>
					<html:optionsCollection name="hmRateCodes" label="rateCode" value='rateCode' /> 
				</html:select> 
			</td>
			</tr>
        </table>
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
        <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
        <table width='100%' border="0" cellspacing="0" cellpadding="0">		
			<tr><td id='trtable'>&nbsp;</td></tr>
        </table>
        </div>
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
