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
<%@ page import="com.savant.pricing.calculation.valueobjects.ReportsTemplateHeaderVO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.ReportsParamVO"%>
<%@ page import="com.savant.pricing.calculation.dao.ReportsDAO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.HashMap"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.contracttemplate.ContractForm" />
<jsp:setProperty name="frm" property="*" />

<html:html>
<%
ReportsDAO objReportsDAO = new ReportsDAO();
HashMap hmreportTemplate = new HashMap();
HashMap hmParam = new HashMap();
List lstReportTemplate = objReportsDAO.getAllReportsTemplates();
Iterator iteReport = lstReportTemplate.iterator();
ReportsTemplateHeaderVO objReportsTemplateHeaderVO;
ReportsParamVO objReportsParamVO ;
while(iteReport.hasNext())
{
objReportsTemplateHeaderVO = (ReportsTemplateHeaderVO)iteReport.next();
hmreportTemplate.put(new Integer(objReportsTemplateHeaderVO.getReportIdentifier()),objReportsTemplateHeaderVO.getReportName());
}
if( frm.getReport()!=0)
{
 List lstParam = objReportsDAO.getAllReportParams(frm.getReport());
  Iterator it = lstParam.iterator();
 while(it.hasNext())
   {
     objReportsParamVO  = (ReportsParamVO)it.next();
     hmParam.put(new Integer(objReportsParamVO.getReportParamIdentifier()),objReportsParamVO.getReportParamName());
   }
}
pageContext.setAttribute("hmreportTemplate",hmreportTemplate);
pageContext.setAttribute("hmParam",hmParam);
%>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
</head>
<script>

function callLoadCombo(message)
{
	var temp = document.forms[0];
	var report = temp.report.value;
	var paramv = temp.reportParam.value;
	if(message=="param")
	if(parseInt(paramv,10)==0)
	{
		document.getElementById('paramValues').innerText = "";
		return true;
	}
	
	var url = '<%=request.getContextPath()%>/servlet/contractTemplate';
	var param = 'Report='+report+'&Message='+message+'&Param='+paramv;
	if (window.XMLHttpRequest) // Non-IE browsers
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: loadCombo});
			}
			else if (window.ActiveXObject) // IE
			{
				req = new ActiveXObject("Microsoft.XMLHTTP");
				if (req)
				{
					req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: loadCombo});
				}
			}
}
function loadCombo(req){
   var a = req.responseText.split("@@@");
 	if(a[0]=="report")
 	{
    	document.getElementById('reportParam').innerHTML = a[1];
    	 document.getElementById('paramValues').innerText = "";
    }
    else 
    {
         document.getElementById('paramValues').innerText = a[1];
    }
}
function viewparam()
{
	callLoadCombo("param");
}
function update()
{
	var temp = document.forms[0];
	temp.formAction.value ='update';
	temp.submit();
}
</script>
<body > 
<html:form action="/pcContract" >
<html:hidden property="formAction" />
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title" >Proposal&nbsp;Templates</td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
        <tr>
         <td class="message" colspan='6'>
		<html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages>
		</td>
		</tr>
		<tr>
		<td colspan='6' class='error'> <html:errors/></td>
		</tr>
      </table> 
      <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="tblSearch"> 
        <tr> 
          <td width="100" class="fieldtitle">Contract</td> 
          <td width="1" class="fieldtitle">:</td> 
          <td width="794" class="search"><html:select property="report" styleClass="Combo" onchange="callLoadCombo('report')"> 
			<html:option value="0">Select one</html:option>	 
		<html:options collection="hmreportTemplate" property="key" labelProperty="value"/> </html:select></td>
		<td width="1" class="search">&nbsp;</td> 
		</tr> 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
        <tr> 
          <td width="100" class="fieldtitle">Section</td> 
          <td width="1" class="fieldtitle">:</td> 
          <td width="200" class="search" id="reportParam" > <html:select property="reportParam" styleClass="Combo" onchange="viewparam()"> 
                 <html:option value="0" >Select one</html:option>
				<html:options collection="hmParam" property="key" labelProperty="value"/>
              </html:select></td>
           <td width="1" class="search">&nbsp</td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="100" class="fieldtitle" valign='top'>Content</td> 
          <td width="1" class="fieldtitle" valign='top'>:</td> 
          <td width="800" valign="middle"><html:textarea property="paramValue" cols="100" rows="10" styleId="paramValues"/></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td class="btnbg"><html:submit value="Update" styleClass="button" onclick="update()" />
			<input type='button' class='button' value = "Reset" onclick="viewparam()"></input>
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