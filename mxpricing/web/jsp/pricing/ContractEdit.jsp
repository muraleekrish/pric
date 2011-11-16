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
<%@ page import="com.savant.pricing.dao.ContractsTrackingDAO"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.dao.CustomerStatusDAO"%>
<%@ page import="com.savant.pricing.dao.ContractsDAO"%>
<%@ page import="com.savant.pricing.valueobjects.CustomerCommentsVO"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.ContractsTrackingForm"/><jsp:setProperty name="frm" property="*"/>
<%
try
{
	int browserHt = Integer.parseInt(String.valueOf(request.getSession().getAttribute("browserHeight")));
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	ContractsTrackingDAO objContractsTrackingDAO = new ContractsTrackingDAO();
	CustomerStatusDAO objCustomerStatusDAO = new CustomerStatusDAO();
	ContractsDAO objContractsDAO = new ContractsDAO();
	LinkedHashMap contractType = null;
	LinkedHashMap contractCMSstatus = null;
	LinkedHashMap RateClassFromCMS = null;
	contractType = objContractsDAO.getAllContractTypesFromCMS();
	contractCMSstatus = objContractsDAO.getAllContractStatusFromCMS();
	RateClassFromCMS = objContractsDAO.getAllMXEnergyRateClassFromCMS();
	List lstAllContractStatus = null;
	lstAllContractStatus = objCustomerStatusDAO.getAllCustomerStatus();
	
	ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
	String strCustomerId = String.valueOf(request.getAttribute("CustomerId")==null?frm.getCustomerId()+"":String.valueOf(request.getAttribute("CustomerId")));
	ProspectiveCustomerVO objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(strCustomerId));
	
	List lst = new ArrayList();
	
	Set objSet = null;
	objSet = objProspectiveCustomerVO.getCustomerComments();
	
  	int size = objSet.size();
  	CustomerCommentsVO objCustomerCommentsVO = null;
    while(size>0)
    {
	  	Iterator itr = objSet.iterator();
	  	while(itr.hasNext())
	  	{
	  		objCustomerCommentsVO = (CustomerCommentsVO)itr.next();
	  		if(objCustomerCommentsVO.getVersion() == size)
	  		{
	  			lst.add(objCustomerCommentsVO);
	  			break;
	  		}
	  	}
	  	size=size-1;
  	}
  	String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";
	String menuFtr[]= menupath.split("For");
	menupath = menupath.replaceFirst(menuFtr[1].trim(),(objProspectiveCustomerVO.getCustomerId()+"").equalsIgnoreCase("")||objProspectiveCustomerVO.getCustomerId()==null?"All Customers":objProspectiveCustomerVO.getCustomerId()+" - "+objProspectiveCustomerVO.getCustomerName());
	session.removeAttribute("home");
  	session.setAttribute("home",menupath);
	pageContext.setAttribute("setCustomerComments",lst);
	pageContext.setAttribute("lstAllContractStatus",lstAllContractStatus);
	pageContext.setAttribute("contractType",contractType);
	pageContext.setAttribute("contractCMSstatus",contractCMSstatus);
	pageContext.setAttribute("RateClassFromCMS",RateClassFromCMS);
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
</head>
<script>

function updateContracts(submitAction)
{
	var temp = document.forms[0];
	temp.formActions.value =submitAction;
	if(null != temp.txtcommentsGeneral  )
		temp.txtcomments.value = temp.txtcommentsGeneral.value;
		else
		temp.txtcomments.value = document.getElementById('commentsId').value;
		temp.customerId.value = <%=strCustomerId%>
		//temp.submit();
}

function callstatus()
{
  if(document.forms[0].contractStatus.value==2||document.forms[0].contractStatus.value==11)
  {
	document.getElementById('contractType').style.display = 'none';
	document.getElementById('cmsstatus').style.display = 'none';
	document.getElementById('rateClass').style.display = 'none';
	document.getElementById('startDate').style.display = 'none';
  }
  else
  {
  	document.getElementById('contractType').style.display = 'none';
	document.getElementById('cmsstatus').style.display = 'none';
  	document.getElementById('rateClass').style.display = 'none';
	document.getElementById('startDate').style.display = 'none';
  }
}

function callcancel()
{
	var temp = document.forms[0];
	temp.action = '<%=request.getContextPath()%>/jsp/pricing/Contracts.jsp';
	temp.formActions.value = "cancel";
	temp.submit();
}

</script>
<body onload='callstatus();'> 
<html:form action="ContractEdit" > <html:hidden property="formActions"/>
 <html:hidden property="customerId"/> 
<html:hidden property="txtcomments" /> 
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/> 
      <font size="1px" face="Verdana" style="color:#FF0000"><html:errors/></font> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title">Contract Tracking </td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="203" class="fieldtitle">Contract Id</td> 
          <td width="4" class="fieldtitle">:</td> 
          <td width="894" class="fieldata"><html:text styleClass="textbox" property="contractTrackingIdentifier" size="50" readonly="true" style='border:0' /></td> 
        </tr> 
        <tr> 
          <td width="203" class="fieldtitle">Customer Name</td> 
          <td width="4" class="fieldtitle">:</td> 
          <td class="fieldata"><html:text styleClass="textbox" property="customerName" size="50" readonly="true" style='border:0' /></td> 
        </tr> 
        <tr> 
          <td width="203" class="fieldtitle">Product</td> 
          <td width="4" class="fieldtitle">:</td> 
          <td class="fieldata"><html:text styleClass="textbox" property="productName" size="50" readonly="true" style='border:0' /></td> 
        </tr> 
        <tr> 
          <td width="203" class="fieldtitle">Term</td> 
          <td width="4" class="fieldtitle">:</td> 
          <td class="fieldata"><html:text styleClass="textbox" property="term" size="50" readonly="true" style='border:0' /></td> 
        </tr> 
        <tr> 
          <td width="203" class="fieldtitle">Price $/kWh</td> 
          <td width="4" class="fieldtitle">:</td> 
          <td class="fieldata"><html:text styleClass="textbox" property="price" size="50" readonly="true" style='border:0' /></td> 
        </tr> 
        <tr> 
          <td width="203" valign="top" class="fieldtitle">Status*</td> 
          <td width="4" valign="top" class="fieldtitle">:</td> 
          <td class="fieldata"> <html:select property="contractStatus" onchange="callstatus()" > 
            <option value="0">Select one</option> 
            <html:optionsCollection name="lstAllContractStatus" label="customerStatus" value="customerStatusId"/> </html:select> </td> 
        </tr> 
        <tr id='contractType' style='display:none'> 
          <td width="203" valign="top" class="fieldtitle">Contract Type*</td> 
          <td width="4" valign="top" class="fieldtitle">:</td> 
          <td class="fieldata"><html:select property="contractType"> 
            <option value="0">Select one</option> 
            <html:optionsCollection name="contractType" label="value" value="key"/> </html:select> </td> 
        </tr> 
        <tr id='cmsstatus' style='display:none'> 
          <td width="203" valign="top" class="fieldtitle">Contract Status*</td> 
          <td width="4" valign="top" class="fieldtitle">:</td> 
          <td class="fieldata"><html:select property="contractCMSStatus"> 
            <option value="0">Select one</option> 
            <html:optionsCollection name="contractCMSstatus" label="value" value="key"/> </html:select> </td> 
        </tr> 
        <tr id='rateClass' style='display:none'> 
          <td width="203" valign="top" class="fieldtitle">Rate Class*</td> 
          <td width="4" valign="top" class="fieldtitle">:</td> 
          <td class="fieldata"> <html:select property="rateClass"> 
            <option value="0">Select one</option> 
            <html:optionsCollection name="RateClassFromCMS" label="value" value="key"/> </html:select> </td> 
        </tr> 
        <tr id='startDate' style='display:none'> 
          <td width="203" valign="top" class="fieldtitle">Contract Start Date</td> 
          <td width="4" valign="top" class="fieldtitle">:</td> 
          <td class="fieldata"><html:text property="contractStartDate" size="10" readonly="true"/> <a href="#" onClick="showCalendarControl(document.getElementById('contractStartDate'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" name="imgDateFrom" width="16" height="16" border="0" align="absmiddle" id="imgDateFrom"></a> </td> 
        </tr> 
        <tr> 
          <td width="188" class="fieldtitle" valign='top'>General Notes</td> 
          <td width="10" class="fieldtitle" valign='top'>:</td> 
          <td class="fieldata" width="258"  colspan="4"><html:textarea property="txtcommentsGeneral" styleClass="textbox" rows="3" cols="94"/></td> 
        </tr> 
       <tr>
            <td colspan="6">
			<fieldset style='align:center; margin: -10px 10px 5px 10px'>
            		<legend>Comments</legend>
				<div style="overflow:auto;height:<%=((browserHt+120)-(browserHt))%>" id="divList"> 
				            <table width="95%" border="0" cellspacing="0" cellpadding="0">
				  <tr class='staticHeader'>
				    <td width="60%" class="tblheader" align="center">Comments</td>
				    <td width="18%" class="tblheader" align="center">Created By </td>
				    <td width="17%" class="tblheader" align="center">Created Date</td>
				  </tr>
				  <logic:iterate id="custComment" name="setCustomerComments">
				  <tr>
				    <td class="tbldata"><bean:write name="custComment" property="comments" ignore="true"/>&nbsp;</td>
				    <td class="tbldata"><bean:write name="custComment" property="createdBy" ignore="true"/>&nbsp;</td>
				    <td class="tbldata"><bean:write name="custComment" property="createdDate" ignore="true" format="MM-dd-yyyy hh:mm a"/>&nbsp;</td>
				  </tr>
				  </logic:iterate>
				</table>
				</div>
				</fieldset>
             </td>
            </tr>
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td class="btnbg">
			<html:submit property="update" styleClass="button" value="Update" onclick="updateContracts('update')"></html:submit>
			<html:reset value="Reset" styleClass="button"></html:reset>
			<html:button property="Cancel" styleClass="button" value="Cancel" onclick="callcancel()"></html:button >
		  </td> 
        </tr> 
      </table></td> 
  </tr> 
  <tr> 
    <td height="20"><table width="100%"  border="0" cellpadding="0" cellspacing="0" > 
        <tr> 
          <td   class="copyright_link"><%=menupath%></td>
          <td width="250"   class="copyright" align='right'>Design Rights Savant Technologies </td> 
        </tr> 
      </table></td> 
  </tr> 
</table> 
</html:form> 
</body>
</html:html>
<%
}
catch(Exception e)
{
e.printStackTrace();
}
}%>
