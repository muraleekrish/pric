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
<%@ page import="com.savant.pricing.valueobjects.ContractsTrackingVO"%>
<%@ page import="com.savant.pricing.dao.ContractsTrackingDAO"%>
<%@ page import="com.savant.pricing.valueobjects.CustomerCommentsVO"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="com.savant.pricing.common.NumberUtil"%>

<%	
	int browserHt = Integer.parseInt(String.valueOf(request.getSession().getAttribute("browserHeight")));
	ContractsTrackingDAO objContractsTrackingDAO = new ContractsTrackingDAO();
	ContractsTrackingVO objContractsTrackingVO = null;
	NumberFormat tnf = NumberUtil.tetraFraction();
	objContractsTrackingVO = objContractsTrackingDAO.getContracts(request.getParameter("contractId"));
	
	ProspectiveCustomerVO objProspectiveCustomerVO = new ProspectiveCustomerVO();
	if(objContractsTrackingVO.getContractRef().getPriceRunCustomerRef().getProspectiveCustomer()!=null)
		objProspectiveCustomerVO = objContractsTrackingVO.getContractRef().getPriceRunCustomerRef().getProspectiveCustomer();
		
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
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/Styles.css">
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>

<script>
	function listUser()
	{
		temp = document.forms[0];
		temp.action = '<%=request.getContextPath()%>/jsp/pricing/Contracts.jsp';
		temp.submit();
	}
</script>
</head>
<body>
<html:form action="frmUserview">
<input type="hidden" name="viewAll"/>
<jsp:include page="/menu.jsp"/>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top"> <jsp:include page="../menu.jsp"/>
		  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	       <tr>
	         <td width="200" class="page_title">Contract Tracking</td>
	       </tr>
	       <tr>
	         <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
	       </tr>
	      </table>
    		<table width="100%" cellspacing="0" cellpadding="0" border ="0">
				<tr> 
					<td width="110" class="fieldtitle">Contract Id</td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%=objContractsTrackingVO.getContractTrackingIdentifier()%></td>
				</tr>
				<tr> 
					<td width="110" class="fieldtitle">Customer Name</td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%=objContractsTrackingVO.getContractRef().getPriceRunCustomerRef().getProspectiveCustomer().getCustomerName()==null?"":objContractsTrackingVO.getContractRef().getPriceRunCustomerRef().getProspectiveCustomer().getCustomerName()%></td>
				</tr>
				<tr> 
					<td width="110" class=" fieldtitle">Product</td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%=objContractsTrackingVO.getContractRef().getProduct().getProductName()==null?"":objContractsTrackingVO.getContractRef().getProduct().getProductName()%></td>
				</tr>
				<tr> 
					<td width="110" class="fieldtitle">Term</td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%=objContractsTrackingVO.getContractRef().getTerm()%></td>
				</tr>
				
				<%if(objContractsTrackingVO.getContractRef().getProduct().getProductIdentifier()!=5)
					{%>
				<tr> 
					<td width="110" class="fieldtitle">Price $/kWh</td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%=tnf.format(objContractsTrackingVO.getContractRef().getFixedPrice$PerMWh()/1000)%>
				</tr>
				<tr>
					<td width="110" class="fieldtitle">MCPE Adder</td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%=tnf.format(objContractsTrackingVO.getContractRef().getMcpeAdder())%>
					</td>
				</tr>
				<%}else{%>
				<tr>
					<td width="110" class="fieldtitle">Base Rate $/kWh</td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%=tnf.format(objContractsTrackingVO.getContractRef().getBaseRate$PerMWh()/1000)%>
					</td>
				</tr>
				<tr>
					<td width="110" class="fieldtitle">Fuel Adj. Rate</td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%=tnf.format(objContractsTrackingVO.getContractRef().getFuelFactor())%>
					</td>
				</tr>
				<tr>
					<td width="110" class="fieldtitle">Fuel Factor</td>
					<td width="1" class="fieldtitle">:</td>
					<td class="form_data"><%=tnf.format(objContractsTrackingVO.getContractRef().getComputedFAF())%>
					</td>
				</tr>
				<%}%>
				<tr> 
						<td width="110" class="fieldtitle">Status</td>
						<td width="1" class="fieldtitle">:</td>
						<td class="form_data"><%=objContractsTrackingVO.getCustomerStatus().getCustomerStatus()==null?"":objContractsTrackingVO.getCustomerStatus().getCustomerStatus()%></td>
				</tr>
				</table>
				<div style="overflow:auto;height:<%=((browserHt+220)-(browserHt))%>" id="divList"> 
				            <table width="95%" border="0" cellspacing="0" cellpadding="0">
				  <tr class='staticHeader'>
				    <td width="60%" class="tblheader" align="center">Comments</td>
				    <td width="18%" class="tblheader" align="center">Created By </td>
				    <td width="17%" class="tblheader" align="center">Created Date</td>
				  </tr>
				  <logic:iterate id="custComment" name="setCustomerComments">
				  <tr>
				    <td class="tbldata">&nbsp;<bean:write name="custComment" property="comments" ignore="true"/></td>
				    <td class="tbldata"><bean:write name="custComment" property="createdBy" ignore="true"/></td>
				    <td class="tbldata"><bean:write name="custComment" property="createdDate" ignore="true" format="MM-dd-yyyy hh:mm a"/></td>
				  </tr>
				  </logic:iterate>
				</table>
				</div>
				<%-- <tr>
			            <td width="110" class="fieldtitle" valign='top'>General Notes</td>
			            <td width="1" class="fieldtitle" valign='top'>:</td>
			            <td colspan='4' class="form_data">
                 			<textarea name="comments" rows="3" cols="94" readonly><%=objContractsTrackingVO.getContractRef().getPriceRunCustomerRef().getProspectiveCustomer().getComments()==null?"":objContractsTrackingVO.getContractRef().getPriceRunCustomerRef().getProspectiveCustomer().getComments()%></textarea>
			 			</td> 
		  		</tr>	--%>			
			</table>
			<br>
	<table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
	<tr>
		<td><html:button property="return" styleClass="Button" value="List" onclick="listUser();"/>
	</tr>
	</table></td>
</tr>
</table>
</html:form>
</body>
</html:html>
<%}%>