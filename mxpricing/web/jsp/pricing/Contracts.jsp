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
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="com.savant.pricing.securityadmin.dao.UserDAO"%>
<%@ page import="com.savant.pricing.common.FilterHandler"%>
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.dao.ContractsTrackingDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ContractsTrackingVO"%>
<%@ page import="com.savant.pricing.dao.CustomerStatusDAO"%>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.ContractsListForm" />
<jsp:setProperty name="frm" property="*" />

<%
	Properties props;
	props = new Properties();
	InputStream is = this.getClass().getClassLoader().getResourceAsStream("ApplicationResources.properties");
	props.load(is);
	String highlightColor = props.getProperty("list.highlight.color");
	String padding = props.getProperty("list.highlight.padding");
	
	int browserHt = 0;	
	int totalCount=1;
	int pageCount = 1;
	int maxItems = 0;
	int totalPages = 0;
	String str="";
	boolean order = true;
	int contractStatId;
	LinkedHashMap hmResult = new LinkedHashMap();
	List lstAllContracts = null;
	FilterHandler objFilterHandler = new FilterHandler();
	ContractsTrackingDAO objContractsTrackingDAO = new ContractsTrackingDAO();
	ContractsTrackingVO objContractsTrackingVO = new ContractsTrackingVO();
	CustomerStatusDAO objCustomerStatusDAO = new CustomerStatusDAO();
	UserDAO objUserDAO = new UserDAO();
	boolean cpeElgible = objUserDAO.isUserElgible((String)session.getAttribute("userName"),"Run");
	
try{
		if(frm.getSortOrder().equalsIgnoreCase("ascending"))
		{
			order = true;
		}
		else
		{
			order = false;
		}
		
		Filter fil[] =null;
		Filter filtCustName  = null;
		Filter filtContractId  = null;
		if(request.getParameter("ftmsg")!=null&&request.getParameter("ftmsg").equalsIgnoreCase("footer"))
			{
				String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";
				String menuFtr[]= menupath.split("For");
				 if(menuFtr.length>1 && ! menuFtr[1].trim().equalsIgnoreCase("all Customers"))
				 {
				  String id = menuFtr[1].split("-")[0].trim();
				  frm.setTxtCMSId(id);
				 }
			}
  	    if((frm.getFormActions().equalsIgnoreCase("search"))||(frm.getFormActions().equalsIgnoreCase("navigation")))
  		{
  		   	Vector filter = new Vector();
  		   	maxItems = Integer.parseInt(frm.getMaxItems());
  		   	
			  if (!frm.getTxtCustName().trim().equalsIgnoreCase(""))
			  {
			  		filter = new Vector();
			  		filter = objFilterHandler.setFormDetails("",frm.getTxtCustName(),frm.getCmbCustName(),filter);
			  		fil = new Filter[filter.size()];
			 		filter.copyInto(fil);
			  		filtCustName = fil[0];
			  }
			   if (!frm.getTxtContractId().trim().equalsIgnoreCase(""))
			  {
					filter = new Vector();
			  		filter = objFilterHandler.setFormDetails("",frm.getTxtContractId(),frm.getCmbContractId(),filter);
			  		fil = new Filter[filter.size()];
			 		filter.copyInto(fil);
			  		filtContractId = fil[0];
			  }
		}
		contractStatId = Integer.parseInt(frm.getCmbContractStatus());
		maxItems = Integer.parseInt(frm.getMaxItems());
		pageCount = Integer.parseInt(frm.getPage());
		if(cpeElgible)
		 hmResult =  objContractsTrackingDAO.getAllContracts(filtCustName, frm.getTxtCMSId(), filtContractId, contractStatId, "contractTrackingIdentifier", order, ((pageCount-1)*maxItems), maxItems);
		else
		 hmResult =  objContractsTrackingDAO.getAllContractsByManagerandRep(filtCustName, frm.getTxtCMSId(), filtContractId,String.valueOf(session.getAttribute("userName")), contractStatId, "contractTrackingIdentifier", order, ((pageCount-1)*maxItems), maxItems);
		lstAllContracts = (List)hmResult.get("Records");
		totalCount = Integer.parseInt(String.valueOf(hmResult.get("TotalRecordCount")));
		pageContext.setAttribute("lstAllContracts",lstAllContracts);
		
		if(totalCount%maxItems == 0)
  		{
	  		totalPages = (totalCount/maxItems);
  		}
  		else
  		{
	  		totalPages = (totalCount/maxItems) + 1;
	  	}
	  	if(totalPages==0)
			totalPages = 1;
	    if(frm.getFormActions().equalsIgnoreCase("navigation"))
		{
			 pageCount = Integer.parseInt(frm.getPage());
		}
		else if(frm.getFormActions().equalsIgnoreCase("navigationDown"))
		{
			maxItems = Integer.parseInt(frm.getMaxItems());
		}
		
		List lstAllContractStatus = null;
		lstAllContractStatus = objCustomerStatusDAO.getAllCustomerStatus();
		pageContext.setAttribute("lstAllContractStatus",lstAllContractStatus);
		
		if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
			browserHt = 260;
		else
			browserHt = 215;
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/script/common.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
</head>
<script>
var managerName = "";
function callDashboard(runCustomerRefId,PriceRunRefNo)
{
temp=document.forms[0];
temp.action='<%=request.getContextPath()%>/dashBoard.do?priceRunId='+runCustomerRefId+'&PriceRunRefNo='+PriceRunRefNo;
temp.submit();
}
function callErrorPage(runCustomerRefId,PriceRunRefNo)
{
temp=document.forms[0];
temp.action='<%=request.getContextPath()%>/runresulterror.do?priceRunId='+runCustomerRefId+'&PriceRunRefNo='+PriceRunRefNo;
temp.submit();
}

function search()
{
	temp = document.forms[0];
	temp.formActions.value="search";
	temp.page.value = 1;
	temp.submit();
}

 function pageDecrement()
 {

 	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) - 1;
	temp.formActions.value="navigation";
	temp.page.value  = page;
	temp.submit();
}
function changePageGoto()
{
	temp = document.forms[0];
	temp.formActions.value="search";
	temp.submit();
}
function changePage()
{
	temp=document.forms[0];
	temp.formActions.value="navigation";
	temp.page.value  =0;
	temp.submit();
}
function pageIncrement()
 {
 	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) + 1;
	temp.formActions.value="navigation";
	temp.page.value  = page;
	temp.submit();
}

function callClearSearch()
{
try{
	temp=document.forms[0];
	temp.txtCustName.value="";
	temp.cmbCustName.selectedIndex="0";
	temp.txtContractId.value ="";
	temp.txtCMSId.value ="";
	temp.cmbContractId.selectedIndex ="0";
	temp.cmbContractStatus.selectedIndex ="0";
}catch(err)
	{
alert(err.description);
}
}
function callSort()
 {
 
	temp=document.forms[0];
	var odr = '<%=order%>';
	if(odr == 'true')
	 {
		temp.sortOrder.value="descending";
		temp.submit();
	}
	else
	{
		temp.sortOrder.value="ascending";
		temp.submit();
	}
 
 }

function loadDefault()
{
	temp = document.forms[0];
	var formAction = '<%=frm.getFormActions()%>';
	var maxItems = '<%=frm.getMaxItems()%>';
	var page = '<%=frm.getPage()%>';
	temp.page.value = page;
	if(maxItems=="15")
	{
		temp.maxItems[0].checked = true;
	}
	else if(maxItems=="25")
	{
		temp.maxItems[1].checked = true;
	}
	else if(maxItems=="50")
	{
		temp.maxItems[2].checked = true;
	}
	else if(maxItems=="100")
	{
		temp.maxItems[3].checked = true;
	}
	temp.txtCustName.value = temp.txtCustName.value;
	temp.txtContractId.value = temp.txtContractId.value;
	temp.txtCMSId.value = '<%=frm.getTxtCMSId().equalsIgnoreCase("0")?"":frm.getTxtCMSId()%>';
}

function checkEnter(e)
{ 
     var characterCode ;
     e = event
     characterCode = e.keyCode 
     if(characterCode == 13)
     { 
       search();
       return false;
     }
     else
     {
       return true; 
     }
}

function editContracts()
{
	try{
		temp=document.forms[0];
		var obj = document.getElementById('ContractsList');
		var contrId="";
		var count=0;
		for(var i=1;i<obj.children[0].children.length;i++)
			{
				if(obj.children[0].children[i].children[0].children[0].checked)
				{
					contrId += obj.children[0].children[i].children[0].children[0].value;
					count++;
				}		
			}
		if(count>0)
		{			
			var contractStatus = document.getElementById('idConractStstus_'+contrId).value;
			temp.action='<%=request.getContextPath()%>/ContractEdit.do?ContractId='+contrId+'&formActoin=edit&contractStatus='+contractStatus;
			temp.formActions.value="edit";
			temp.submit();
		}
		else
		{
			alert("Please select a Contract to Modify");
		}
	}catch(err)
	{
		alert(err.description);
	}
}

function deleteContracts()
{
	try{
		temp=document.forms[0];
		var obj = document.getElementById('ContractsList');
		var contrId="";
		var roleName = "";
		var count=0;
		for(var i=1;i<obj.children[0].children.length;i++)
		{
			if(i == 1)
			{
				if(obj.children[0].children[i].children[0].children[0].checked)
				{
					subQuery += obj.children[0].children[i].children[0].children[0].value+",";
					count++;
				}
			}
			else
			{
				if(obj.children[0].children[i].children[0].children[0].checked)
				{
					subQuery += obj.children[0].children[i].children[0].children[0].value+",";
					count++;
				}
			}
		}
		if(count>0)
		{
			if(!confirm("Warning!\nChosen Item will be deleted."))
			{
				return;
			}
			else
			{	
				temp.action='<%=request.getContextPath()%>/Contracts.do?ContractId='+contrId+'&formActoin=delete';
				temp.formActions.value="delete";
				temp.submit();
			}
		}
		else
		{
			alert("Please select atleast one Contracts to delete");
		}
	}catch(err)
	{
	alert(err.description);
	}
}

function callContractView(contractId)
{
temp=document.forms[0];
temp.action='<%=request.getContextPath()%>/jsp/pricing/Contractsview.jsp?contractId='+contractId;
temp.submit();
}

function callSort()
{
	temp=document.forms[0];
	var odr = 'true';	
	if(odr == '<%=order%>')
	{
		temp.sortOrder.value="descending";
		temp.submit();
	}
	else
	{
		temp.sortOrder.value="ascending";
		temp.submit();
	} 
}

function createContract(trackingId,contractId,productId,message,template)
{
	
	 if(productId!=1&&productId!=5&&productId!=2&&productId!=6&&productId!=7&&productId!=8)
	 {
		  alert("Contract template is not available.");
		  return false;
	 }
	 else
	 {
		window.parent.location = '<%=request.getContextPath()%>/servlet/ContractPDFServlet?ContractId='+contractId+'&message='+message+'&trackingId='+trackingId+'&template='+template+'&productId='+productId;
	 }
}

function checkNumber(e)
{ 
     var characterCode ;
     e = event
     characterCode = e.keyCode 
     if(characterCode == 13)
     { 
       search();
       return false;
     }
     else
     {
       if(!(event.keyCode>=48 && event.keyCode<=57))
     	{
     		return false
     	}
       return true; 
     }
}

function constructToolTip(custName, dba)
{
    	var table = "<span bgcolor='#E8E8E8'><table height=\'40\' border=\'0\' cellspacing=\'1\' cellpadding=\'0\'>"+
		     "<tr ><td><b> Name</b></td>"+
		     "<td width=\'1\'>:</td>"+
	    	 "<td> "+custName+"</td></tr>"+
		     "<tr><td><b>DBA</b></td>"+
	    	 "<td width=\'1\'>:</td>"+
	      	 "<td>"+dba+"</td></tr>"+
             "</table></span>";
        Tip(table);
}

</script>
<body onLoad="loadDefault();"> 
<script type="text/javascript" src="<%=request.getContextPath()%>/script/tooltip/wz_tooltip.js"></script>
<html:form action="contractsList" focus="txtCustName">
<html:hidden property="formActions"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>

<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/>
	<font class="message"><html:messages id="messageid" message="true"><bean:write name="messageid" /></html:messages></font>
	<font class='error'><html:errors/></font>
   <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td class="page_title">Contract Tracking</td>
          <td class="page_title"><table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
            <tr>
              <td><a href="#" onclick="editContracts()">Modify&nbsp;</a></td>
            </tr>
          </table></td>
        </tr> 
      </table> 
      <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
        <tr> 
          <td width="110" class="search">Customer Name</td> 
          <td width="1" class="search">:</td> 
          <td width="350" class="search">
				<html:text property="txtCustName" styleClass="textbox" size="15" maxlength="15" onkeypress="return checkEnter(event)" />
				<html:select property="cmbCustName" onchange='document.forms[0].go.focus();'>
		          <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
				</html:select> 
		  </td> 
          <td width="80" class="search">Customer&nbsp;Id</td> 
          <td width="1" class="search">:</td> 
          <td class="search">
				<html:text property="txtCMSId" styleClass="textbox" size="15" maxlength="20" onkeypress="return checkNumber(event)" />
		  </td> 
        </tr> 
        <tr> 
        <td width="80" class="search">Contract ID</td> 
          <td width="1" class="search">:</td> 
          <td class="search">
				<html:text property="txtContractId" styleClass="textbox" size="15" maxlength="25" onkeypress="return checkEnter(event)" />
	            <html:select property="cmbContractId" onchange='document.forms[0].go.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
	            </html:select>
		  </td> 
          <td width="110" class="search">Customer Status</td> 
          <td width="1" class="search">:</td> 
          <td class="search">
  			<html:select property="cmbContractStatus" onchange='document.forms[0].go.focus();'>
				<html:option value="0">Select one</html:option>
				<html:options collection="lstAllContractStatus" property="customerStatusId" labelProperty="customerStatus" />
			</html:select>

		      <input name="go" type="submit" class="button_sub_internal" value="Go!" onclick="search()">
              <input name="clear" type="button" class="button_sub_internal" value="Clear" onclick="callClearSearch();">
		  </td>
		</tr>
      </table> 
      <br>  
      <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="ContractsList"> 
        <tr class='staticHeader'> 
          <td width="22" class="cmbheader">&nbsp;</td>
          <%if(order)
            {%>
          <td width="16%" class="tblheader" id='img_sort' onClick="callSort();" style="cursor:hand" title='Sort by Contract ID in Ascending'>Contract ID <img src="<%=request.getContextPath()%>/images/sort.gif" width="7" height="8"></td>
          <%}else{%>
          <td width="16%" class="tblheader" id='img_sort' onClick="callSort();" style="cursor:hand" title='Sort by Contract ID in Decending'>Contract ID <img src="<%=request.getContextPath()%>/images/sort_up.gif" width="7" height="8"></td>
          <%}%>
          <td class="tblheader" >Customer Name</td>
          <td width="8%" class="tblheader" >Customer&nbsp;Id</td>
          <td width="18%" class="tblheader">Product(s)</td>
          <td width="6%" class="tblheader">Term</td>
          <td width="8%" class="tblheader">Start Date</td>
          <td width="8%" class="tblheader">End Date</td>
          <td width="13%" class="tblheader">Customer Status</td>
        </tr>
        <%
        	for(int i=0;i<lstAllContracts.size();i++)
        	{
        		objContractsTrackingVO = (ContractsTrackingVO)lstAllContracts.get(i);
        	}
		%>
		<% 
          if( lstAllContracts.size() > 0 )
          {
        %>
		<logic:iterate id="contTrackDet" name="lstAllContracts" indexId="s">
		<tr onmouseover="this.style.cursor='hand'; this.style.backgroundColor='<%=highlightColor%>';this.style.padding='<%=padding%>'" onmouseout="this.style.backgroundColor='';"> 
		<%
    		objContractsTrackingVO = (ContractsTrackingVO)lstAllContracts.get(s.intValue());
    		String strCusName = "";
			int val = s.intValue();
			str = objContractsTrackingVO.getContractRef().getPriceRunCustomerRef().getProspectiveCustomer().getCustomerName();
			String dba = ((ContractsTrackingVO)lstAllContracts.get(val)).getContractRef().getPriceRunCustomerRef().getProspectiveCustomer().getCustomerDBA()==null?"":((ContractsTrackingVO)lstAllContracts.get(val)).getContractRef().getPriceRunCustomerRef().getProspectiveCustomer().getCustomerDBA();
			strCusName = str;
			str = str.replaceAll("'","\\\\'");
            str = str.replaceAll("\"","");
            dba = dba.replaceAll("'","\\\\'");
            dba = dba.replaceAll("\"","");
		%>
		<bean:define id="contTrackId" name="contTrackDet" property="contractTrackingIdentifier"></bean:define>
		<td width="22" class="tbldata"><html:radio property="contTrackId" value='<%=String.valueOf(contTrackId)%>'/></td> 
		<%
			String template = ((ContractsTrackingVO)lstAllContracts.get(val)).getReportCode();
		if(template.equalsIgnoreCase("A"))
			  {
		%>
		<td class="tbldata" style="color:#0000FF" height="30" align='right'  onclick="callContractView('<bean:write name="contTrackDet" property="contractTrackingIdentifier"/>')">&nbsp;<bean:write name="contTrackDet" property="contractTrackingIdentifier"/></td> 
		<%
			  }
		else if(template.equalsIgnoreCase("B"))
			  {
		%>
		<td class="tbldata_auto" height="30" align='right'  onclick="callContractView('<bean:write name="contTrackDet" property="contractTrackingIdentifier"/>')">&nbsp;<bean:write name="contTrackDet" property="contractTrackingIdentifier"/></td> 
		<% 
			  }
		else
			  {
		%>
		<td class="tbldata" height="30" align='right'  onclick="callContractView('<bean:write name="contTrackDet" property="contractTrackingIdentifier"/>')">&nbsp;<bean:write name="contTrackDet" property="contractTrackingIdentifier"/></td> 
		<%
		  }
			if(strCusName.length()>32)
				{
					strCusName = strCusName.substring(0,30)+"...";
				%>
				<td class = 'tbldata' onmouseover="constructToolTip('<%=str%>', '<%=dba%>')" onclick="callContractView('<bean:write name="contTrackDet" property="contractTrackingIdentifier"/>')"><%=strCusName%>&nbsp;</td> 
			<% }
			else
			{ %>
				<td class = 'tbldata' onmouseover="constructToolTip('<%=str%>', '<%=dba%>')" onclick="callContractView('<bean:write name="contTrackDet" property="contractTrackingIdentifier"/>')"><bean:write name="contTrackDet" property="contractRef.priceRunCustomerRef.prospectiveCustomer.customerName"/>&nbsp;</td> 
			<%}%>
		<td  align='right' class="tbldata"><bean:write name="contTrackDet" property='contractRef.priceRunCustomerRef.prospectiveCustomer.customerId'/></td> 
    	<%	String strProducts = "";
			str = objContractsTrackingVO.getContractRef().getProduct().getProductName();
			strProducts = str;
			if(strProducts.length()>27)
				{
					strProducts = strProducts.substring(0,25)+"...";
		%>
			<td class="tbldata" title='<bean:write name="contTrackDet" property="contractRef.product.productName"/>' onclick="callContractView('<bean:write name="contTrackDet" property="contractTrackingIdentifier"/>')"><%=strProducts%>&nbsp;</td> 
		<% }
			else
			{ %>	
			<td class="tbldata"  onclick="callContractView('<bean:write name="contTrackDet" property="contractTrackingIdentifier"/>')"><bean:write name="contTrackDet" property="contractRef.product.productName"/>&nbsp;</td> 
		<%	}
		%>
		<td class="tbldata" align='right' onclick="callContractView('<bean:write name="contTrackDet" property="contractTrackingIdentifier"/>')"><bean:write name="contTrackDet" property="contractRef.term"/>&nbsp;</td> 
		<td class="tbldata" align='right' onclick="callContractView('<bean:write name="contTrackDet" property="contractTrackingIdentifier"/>')"><bean:write name="contTrackDet" property="contractStartDate" format="MM-dd-yy"/>&nbsp;</td> 
		<td class="tbldata" align='right' onclick="callContractView('<bean:write name="contTrackDet" property="contractTrackingIdentifier"/>')"><bean:write name="contTrackDet" property="contractEndDate"  format="MM-dd-yy"/>&nbsp;</td> 
		<% 
			String strCotractStat = objContractsTrackingVO.getCustomerStatus().getCustomerStatus();
			if(strCotractStat.trim().equalsIgnoreCase("contract signed")||strCotractStat.trim().equalsIgnoreCase("Entered into CMS"))
			{
			%>
			<td class="tbldata">
				<a href="#" onClick="createContract('<%=objContractsTrackingVO.getContractTrackingIdentifier()%>',<%=objContractsTrackingVO.getContractRef().getContractIdentifier()%>,'<%=objContractsTrackingVO.getContractRef().getProduct().getProductIdentifier()%>','tracking','<%=objContractsTrackingVO.getReportCode()%>')">
					<bean:write name="contTrackDet" property="customerStatus.customerStatus"/>&nbsp;
				</a>
			</td>
			<%
			}
			else
			{
		%>
			<td class="tbldata"><bean:write name="contTrackDet" property="customerStatus.customerStatus"/>&nbsp;</td>
		<% 	} %>
		<input type='hidden' id='idConractStstus_<bean:write name="contTrackDet" property="contractTrackingIdentifier"/>' value='<bean:write name="contTrackDet" property="customerStatus.customerStatusId"/>'> 
		</tr>
         </logic:iterate>
		<%
		  }
  		  else
		  {
	    %>
		 	<tr>
		 		<td colspan='10'>
				 	<jsp:include page="/jsp/noRecordsFound.jsp"/>
				</td>
			</tr>
		<%
		  }
		%>
      </table>
      </div>
       <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="sort">
          <tr>
            <td width="100">Page  <%=pageCount%> of <%=totalPages%></td>
            <td width="150">Items  <%=totalCount>0?(pageCount*maxItems)-maxItems+1:0%> - <%=((maxItems*pageCount)>totalCount)?totalCount:(maxItems*pageCount)%> of <%=totalCount%></td>
                <td>Show
	   				<html:radio property="maxItems" value="10" onclick="changePage()"/> 10
	   				<html:radio property="maxItems" value="20" onclick="changePage()"/> 20
	   				<html:radio property="maxItems" value="50" onclick="changePage()"/> 50
	   				<html:radio property="maxItems" value="100" onclick="changePage()"/> 100
				    Items/Page </td>

                <td width="180" class="nav_page_right">
               	<%
								if(Integer.parseInt(frm.getPage())>1)
								{
								%>
						     <a href="#" style="color:blue" onclick="pageDecrement()" ><img src='<%=request.getContextPath()%>/images/previous.gif' align="bottom" alt="Previous" border="0"> Previous</a>
						      <%
							    }
							    %>
                			Goto <html:select property="page" onchange="changePageGoto()">
						      <%
								for(int i=0;i<totalPages;i++)
								{
							 %>
						      <option value="<%=(i+1)%>"><%=(i+1)%></option>
						      <%
							    }
							%>
						      </html:select>
						      
								<%
							     if((Integer.parseInt(frm.getPage())>1) && (Integer.parseInt(frm.getPage())<totalPages))
							    {
							    %> 
						      <%
							   }
							    if(Integer.parseInt(frm.getPage())<totalPages)
							    {
						      %>
						       <a href="#" style="color:blue" onclick="pageIncrement()">Next <img src='<%=request.getContextPath()%>/images/next.gif' align="bottom" alt="Next" border="0"></a> 
						      <%
							    }
							   %>
			</td>
		</tr>
      </table>
	   <table>
	      <tr>
				<td><img border='0' width='11' height='11' src='<%=request.getContextPath()%>/images/manual_run.gif' alt='Price Run Id' /></td><td style='color:#333333'>&nbsp;Below 50 kW</td>
				<td>&nbsp;</td>
				<td><img border='0' width='11' height='11' src='<%=request.getContextPath()%>/images/automatic_run.gif' alt='Price Run Id' /></td><td style='color:#333333'>&nbsp;Above 50 kW</td>
		  </tr>
      </table>
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
<%}catch(Exception e)
{
e.printStackTrace();
}}%>
