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
<%@ page import="com.savant.pricing.common.FilterHandler"%>
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.dao.CDRStatusDAO"%>
<%@ page import="com.savant.pricing.dao.CustomerStatusDAO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.CustomerPreferencesVO "%>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.ProspectiveCustomerListForm" />
<jsp:setProperty name="frm" property="*" />
 
<%
	int browserHt = 0;
try{
	
	Properties props;
	props = new Properties();
	InputStream is = this.getClass().getClassLoader().getResourceAsStream("ApplicationResources.properties");
	props.load(is);
	String highlightColor = props.getProperty("list.highlight.color");
	String padding = props.getProperty("list.highlight.padding");
	
	ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
	int totalCount=1;
	int pageCount = 0;
	int maxItems = 0;
	int startPosition = 0;
	int totalPages = 0;
	String str = "";
	int columnNumber = 0;
	boolean autoRun = false;
	int cusId;
	HashMap hmResult = new HashMap();
	List  listProspectiveCustLst = null;
	List  listCDRStatus = null;
	FilterHandler objFilterHandler = new FilterHandler();
	CustomerStatusDAO objCustomerStatusDAO = new CustomerStatusDAO();
	CustomerPreferencesVO  objCustomerPreferencesVO = null;
	CDRStatusDAO objCDRStatusDAO = new CDRStatusDAO();
	listCDRStatus = objCDRStatusDAO.getAllCDRStatus();
	List objCustomerStatusVO = objCustomerStatusDAO.getAllCustomerStatus();
	String sortFieldName = frm.getSortField();
	if(request.getParameter("ftmsg")!=null&&request.getParameter("ftmsg").equalsIgnoreCase("footer"))
			{
			String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";
			String menuFtr[]= menupath.split("For");
			if(menuFtr.length>1 && ! menuFtr[1].trim().equalsIgnoreCase("all Customers"))
			 {
			  String id = menuFtr[1].split("-")[0].trim();
			  frm.setTxtCustomerId(id);
			 }
			}	
	if(frm.getTxtCustomerId().equals(""))
	{
			frm.setTxtCustomerId("0");
	}
	pageContext.setAttribute("objCustomerStatusVO",objCustomerStatusVO);
	Filter fil[] =null;
	Filter newFilter  = null;
	Filter Repfilter  = null;
	if ((frm.getFormActions().equalsIgnoreCase("add"))||(frm.getFormActions().equalsIgnoreCase("list")) ||(frm.getFormActions().equalsIgnoreCase("edit")) )
		{  
			fil = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		else if(frm.getFormActions().equalsIgnoreCase("search")||(frm.getFormActions().equalsIgnoreCase("navigation"))||(frm.getFormActions().equalsIgnoreCase("sorting")))
  		{
  		  	maxItems = Integer.parseInt(frm.getMaxItems());
  		  
  		  	Vector filter = new Vector();
  		  	Vector repFilter = new Vector();
			  if (!frm.getTxtCustomerName().trim().equalsIgnoreCase(""))
			  {
			  		filter = objFilterHandler.setFormDetails("customerName",frm.getTxtCustomerName(),frm.getSearchCustomerName(),filter);
			  		fil = new Filter[filter.size()];
			 		filter.copyInto(fil);
			  		newFilter = fil[0];
			  }
			   if (!frm.getTxtsalesRep().trim().equalsIgnoreCase(""))
			  {
					
			  		repFilter = objFilterHandler.setFormDetails("",frm.getTxtsalesRep(),frm.getSearchsalesRep(),repFilter);
			  		fil = new Filter[repFilter.size()];
			 		repFilter.copyInto(fil);
			  		Repfilter = fil[0];
			  }
	  }
	maxItems = Integer.parseInt(frm.getMaxItems());
	pageCount = Integer.parseInt(frm.getPage());
	startPosition = Integer.parseInt(frm.getStartPosition());
	startPosition = (maxItems * pageCount)+1;
	if(frm.getFormActions().equalsIgnoreCase("cancel"))
		frm.setCmbCustomerStatus("0");
	hmResult = objProspectiveCustomerDAO.getAllProspectiveCustomerBySalesManager(String.valueOf(session.getAttribute("userName")),Repfilter,newFilter,Integer.parseInt(frm.getTxtCustomerId()),Integer.parseInt(frm.getCmbCustomerStatus()),Integer.parseInt(frm.getCmbCDRStatus()),((pageCount-1)*maxItems),maxItems,frm.getCmbAutoRun());
	listProspectiveCustLst = (List)hmResult.get("Records");
	pageContext.setAttribute("listProspectiveCustLst",listProspectiveCustLst);
	pageContext.setAttribute("listCDRStatus",listCDRStatus);
	totalCount = Integer.parseInt(String.valueOf(hmResult.get("TotalRecordCount")));
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
	
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 240;
	else
		browserHt = 220;
%>

<html:html>

<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script src='<%=request.getContextPath()%>/script/commonSort.js'></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ProspectiveCustomerDAO.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
</head>
<script>

function changePage()
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
	temp.page.value  = page;
	temp.submit();
}

function pageIncrement()
{
	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) + 1;
	temp.page.value  = page;
	temp.submit();
}

function search()
{
	temp=document.forms[0];
	temp.formActions.value="search";
	temp.page.value = 1;
	temp.submit();
}
function callCustomerExcel()
{
	var timeanddate = new Date();
    window.parent.location = '<%=request.getContextPath()%>/servlet/CustomerExcelServlet?time='+timeanddate+urlString
}
function clearSearch()
{
	try
	{
		temp=document.forms[0];
		temp.txtCustomerName.value="";
		temp.txtCustomerId.value="";
		temp.txtsalesRep.value = "";
		temp.searchCustomerName.selectedIndex = 0;
		temp.cmbCustomerStatus.selectedIndex  = 0;
		temp.searchsalesRep.selectedIndex     = 0;
		temp.cmbCDRStatus.selectedIndex       = 0;
		temp.cmbAutoRun.selectedIndex       = 0;
	}
	catch(err)
	{
		alert(err.description);
	}
}

var pageCount = 1; 
var urlString = "";
function sortTables(fm, col, field)
{
	try
	{
		direction = 1;
		if(fm.sortOrder.value == 'ascending')
		   direction = -1;
		else
			direction = 1;
			
		header = col.parentNode;
		tab = header.parentNode;
		
		for(i=1;i<(header.children.length);i++)
		{	
			header.children[i].children[0].style.visibility ="hidden" ;
			header.children[i].title = "Sort by" + header.children[i].innerText +" in Ascending";
		}
	
		col.children[0].style.visibility="visible";
		
		if(direction==-1)
		{
			col.children[0].src = '<%=request.getContextPath()%>/images/sort.gif';
			col.title = "Sort by" + col.innerText + " in Ascending";
			fm.sortField.value = field;
			fm.sortOrder.value = 'desending';
		}
		else
		{
			col.children[0].src ='<%=request.getContextPath()%>/images/sort_up.gif';
			col.title = "Sort by" + col.innerText + " in Descending";
			fm.sortField.value = field;
			fm.sortOrder.value = 'ascending';
	
		}
		for(i=0;i<header.cells.length;i++)
		{
			if(header.cells[i]==col)
			{
				colNum = i;
				col.sortOrder=direction;
			}
			else
				header.cells[i].sortOrder=-1;
		}
		fm.formActions.value="sorting";
	}
	catch(err)
	{
		alert(err.description);
	}
	fm.submit();
}


function Sort(tabName,columnIndex,ascending)
{
	 if(pageCount==1)
	 {
	   sortByTable(tabName,columnIndex,ascending);
	 }
	 else
	 {
	   //set the current page number, number of records per page, sort column index and sort order as the request parameters
	  document.forms[0].submit();
	 }
}

function changePageGoto()
{
	temp = document.forms[0];
	temp.formActions.value="search";
	temp.submit();
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
	temp.txtCustomerName.value = temp.txtCustomerName.value;
	temp.txtsalesRep.value = temp.txtsalesRep.value;
	temp.txtCustomerId.value = '<%=frm.getTxtCustomerId().equalsIgnoreCase("0")?"":frm.getTxtCustomerId()%>';
	urlString = '&User=Manager&Cust='+temp.txtCustomerName.value+'&custSrch='+<%=frm.getSearchCustomerName()%>+'&cmsId='+temp.txtCustomerId.value+'&Rep='+temp.txtsalesRep.value+'&repSrch='+<%=frm.getSearchsalesRep()%>+'&status='+<%=frm.getCmbCustomerStatus()%>+'&sort='+<%=frm.getSortOrder().equals("ascending")?"0":"1"%>+'&CDRstatus='+<%=frm.getCmbCDRStatus()%>+'&autoRun='+<%=frm.getCmbAutoRun()%>;
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

function callView(prsCustId)
{
	temp=document.forms[0];
	temp.action='<%=request.getContextPath()%>/jsp/pricing/ProspectiveCustomersMngrEdit.jsp?prsCustId='+prsCustId;
	temp.submit();
}

function updateAutoRun(chk_box,custId)
{	
	var val = "no";
	if(chk_box.checked)
    	val = "yes";

	var url = "<%=request.getContextPath()%>/servlet/updateAutoRunServlet";
	var param = 'autoRun='+val+'&cusId='+custId+'&time='+new Date();

	if (window.XMLHttpRequest) // Non-IE browsers
	{
		req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: empty});
	}
	else if (window.ActiveXObject) // IE
	{
		req = new ActiveXObject("Microsoft.XMLHTTP");
		if (req)
		{
			req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: empty});
		}
	}
}
/* don't delete the method because this is response for updateAutoRun() method */
function empty(req)
{
}

function constructToolTip(custName,dba,id)
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

<body onload='loadDefault()'>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/tooltip/wz_tooltip.js"></script> 
<html:form action="frmProspectiveCustomerMngrList" focus="txtCustomerName">
<html:hidden property="customerId" />
<html:hidden property="formActions"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
<input type="hidden" name="pageCount" value="<%=pageCount%>">
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
			<tr> 
	         <td class="message">
	         	  <logic:messagesPresent message="true" >
					<html:messages id="messageid" message="true"><bean:write name="messageid" /></html:messages>
				  </logic:messagesPresent>
	         </td>
	         <td class='error'><html:errors /></td>
	        </tr>
	     </table>

      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td class="page_title">Prospects</td> 
          <td class="page_title"><table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
            <tr>
              <td><a href="<%=request.getContextPath()%>/prospectiveCustomersRepAdd.do?User=Manager">Add</a></td>
            </tr>
          </table></td>
        </tr> 
      </table>
  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="142" class="search">Customer Name</td>
            <td width="1" class="search">:</td>
            <td width="226" class="search"><html:text property="txtCustomerName" styleClass="textbox" size="15" maxlength="15" onkeypress="return checkEnter(event)" />
              <html:select property="searchCustomerName" onchange="document.forms[0].txtCustomerName.focus();">
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              </html:select></td>
            <td width="116" class="search">Customer&nbsp;Id</td>
            <td width="1" class="search">:</td>
            <td width="151" class="search"><html:text property="txtCustomerId" styleClass="textbox" size="15" maxlength="15" onkeypress="return checkNumber(event)" />
			<td width="116" class="search">Sales Rep</td>
            <td width="1" class="search">:</td>
            <td width="213" class="search"> 
				<html:text property="txtsalesRep" styleClass="textbox" size="15" maxlength="15" onkeypress="return checkEnter(event)" />
              	<html:select property="searchsalesRep" onchange="document.forms[0].txtsalesRep.focus();">
		            <html:option value="0">Start With</html:option>
					<html:option value="1">End With</html:option>
					<html:option value="2">Exactly</html:option>
					<html:option value="3">AnyWhere</html:option>
              	</html:select>
			</td>
			<td class="search">&nbsp;</td>
			</tr>
		    <tr>
		    <td width="140" class="search">Customer Status</td>
            <td width="1" class="search">:</td>
            <td width="209" class="search">
		  		<html:select property="cmbCustomerStatus">
			        <html:option value="0">Select one</html:option>
					<html:options collection="objCustomerStatusVO" property="customerStatusId" labelProperty="customerStatus" />
				</html:select>
			</td>
            <td width="116" class="search">Auto Run</td>
            <td width="1" class="search">:</td>
            <td class="search" >
				<html:select property="cmbAutoRun">
	                <html:option value="0">All</html:option>
					<html:option value="1">Yes</html:option>
					<html:option value="2">No</html:option>
				</html:select>
			</td>
			<td width="116" class="search">Approval Status</td>
            <td width="1" class="search">:</td>
            <td width="213" class="search"> 
				<html:select property="cmbCDRStatus">
		            <html:option value="0">Select one</html:option>
		            <html:options collection="listCDRStatus" property="cdrStateId" labelProperty="cdrState" />
				</html:select>	
											 
	           <html:button property="button" value="Go!" styleClass="button_sub_internal" onclick="search()" />
			   <html:button property="Clear" value="Clear" styleClass="button_sub_internal" onclick="clearSearch()"/></td>
		   <td align='right' class="search"><a href='javascript:callCustomerExcel()'><img border='0' align='right'src="<%=request.getContextPath()%>/images/excel_icon.gif" ></a></td>
		  </tr>
      </table>
      <table>
			<tr> 
				<td> <font size="1px" face="Verdana"><html:errors/></font></td>
			</tr>
		</table>
		<div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
		<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="prospectiveCustomerLst">
          <tr class='staticHeader'>
            <td width="187" class="tblheader" align='center'>Customer&nbsp;Name</td>
            <td width="80" class="tblheader" align='center'>Customer&nbsp;Id</td>
            <td class="tblheader" align='center'>Address</td>
            <td width="160" class="tblheader" align='center'>Sales&nbsp;Rep</td>
            <td width="150" class="tblheader" align='center'>Customer&nbsp;Status</td>
            <td width="100" class="tblheader" align='center'>Auto&nbsp;Run</td>
            <td width="100" class="tblheader" align='center'>Approval&nbsp;Status</td>
          </tr>
          <% 
             if( listProspectiveCustLst.size() > 0 )
             {
          %>
          <logic:iterate id="customerManager" name="listProspectiveCustLst" indexId="i">
  			<tr onmouseover="this.style.cursor='hand'; this.style.backgroundColor='<%=highlightColor%>';this.style.padding='<%=padding%>'" onmouseout="this.style.backgroundColor='';" > 
			<%
				int val = i.intValue();
				str=((ProspectiveCustomerVO)listProspectiveCustLst.get(val)).getCustomerName();
				String tootipCustName = str;
				String dba = ((ProspectiveCustomerVO)listProspectiveCustLst.get(val)).getCustomerDBA()==null?"":((ProspectiveCustomerVO)listProspectiveCustLst.get(val)).getCustomerDBA();
				tootipCustName = tootipCustName.replaceAll("'","\\\\'");
                tootipCustName = tootipCustName.replaceAll("\"","");
                dba = dba.replaceAll("'","\\\\'");
                dba = dba.replaceAll("\"","");
                
				if(str.length()>25)
					{
						str = str.substring(0,25)+"...";
				%>
				<td height='30' class="tbldata" onmouseover="constructToolTip('<%=tootipCustName%>','<%=dba%>',<bean:write name="customerManager" property="prospectiveCustomerId"/>)" onclick="callView('<bean:write name="customerManager" property="prospectiveCustomerId"/>')"><%=str%>&nbsp;</td>
				<%	}
					else
					{
 				%>
   						<td height='30' class="tbldata" onmouseover="constructToolTip('<%=tootipCustName%>','<%=dba%>',<bean:write name="customerManager" property="prospectiveCustomerId"/>)" onclick="callView('<bean:write name="customerManager" property="prospectiveCustomerId"/>')"><bean:write name="customerManager" property="customerName"/>&nbsp;</td>
   				<% } %>
			<td class="tbldata" align='right' onclick="callView('<bean:write name="customerManager" property="prospectiveCustomerId"/>')">
            <bean:write name="customerManager" property="customerId" ignore="true"/>
            &nbsp;</td>
			<%
				int valAddr = i.intValue();
				str=((ProspectiveCustomerVO)listProspectiveCustLst.get(valAddr)).getAddress();
				if(str.length()>25)								{
					str = str.substring(0,23)+"...";
				%>
				<td class="tbldata" onclick="callView('<bean:write name="customerManager" property="prospectiveCustomerId"/>')" title='<bean:write name="customerManager" property="address" ignore="true"/>'><%=str%></a>&nbsp;</td>
				<%	}
					else
					{
 				%>
   						<td class="tbldata" onclick="callView('<bean:write name="customerManager" property="prospectiveCustomerId"/>')"><bean:write name="customerManager" property="address" ignore="true"/>&nbsp;</td>
   				<% } %>
			<%
				int valSalesRep = i.intValue();
				String strFrstName = ""; String strLastName = "";
				strFrstName =((ProspectiveCustomerVO)listProspectiveCustLst.get(valSalesRep)).getSalesRep()==null?"&nbsp;":((ProspectiveCustomerVO)listProspectiveCustLst.get(valSalesRep)).getSalesRep().getFirstName();
				strLastName =((ProspectiveCustomerVO)listProspectiveCustLst.get(valSalesRep)).getSalesRep()==null?"":((ProspectiveCustomerVO)listProspectiveCustLst.get(valSalesRep)).getSalesRep().getLastName();
				str = strFrstName+strLastName;
				if(str.length()>22)								
				{
					str = str.substring(0,20)+"...";
				%>
			 <td class="tbldata" onclick="callView('<bean:write name="customerManager" property="prospectiveCustomerId"/>')" title='<bean:write name="customerManager" property="salesRep.firstName" ignore="true"/>&nbsp;<bean:write name="customerManager" property="salesRep.lastName" ignore="true"/>'><%=str%>&nbsp;</td> 
			<%	} 
				else
				{
				 %>
   				<td class="tbldata" onclick="callView('<bean:write name="customerManager" property="prospectiveCustomerId"/>')"><bean:write name="customerManager" property="salesRep.firstName" ignore="true"/>&nbsp;<bean:write name="customerManager" property="salesRep.lastName" ignore="true"/>&nbsp;</td>
			<% } %>
			
			<%
					str=((ProspectiveCustomerVO)listProspectiveCustLst.get(valAddr)).getCustomerStatus().getCustomerStatus();
					if(str.length()>20)
					{
						str = str.substring(0,18)+"...";
						%>
						<td class="tbldata" onclick="callView('<bean:write name="customerManager" property="prospectiveCustomerId"/>')" title='<bean:write name="customerManager" property="customerStatus.customerStatus" ignore="true"/>'><%=str%>&nbsp;</td>
						<%
					}
					else
					{
			%>
				<td class="tbldata" onclick="callView('<bean:write name="customerManager" property="prospectiveCustomerId"/>')"><bean:write name="customerManager" property="customerStatus.customerStatus" ignore="true"/>&nbsp;</td>
				 <% } %>

			<td class="tbldata" onclick="" align='center'>
			<%
			   cusId = ((ProspectiveCustomerVO)listProspectiveCustLst.get(i.intValue())).getProspectiveCustomerId();
			   objCustomerPreferencesVO = objProspectiveCustomerDAO.getProspectiveCustomerPreferences(cusId);
				if(objCustomerPreferencesVO!=null)
				  autoRun = objCustomerPreferencesVO.isAutoRun();
				if(autoRun)
				{
			%>	
				<input type="checkbox" checked name="autoRunCheck" id='checkValue' onclick='updateAutoRun(this,<%=cusId%>);'/>
			<%
				}
				else
				{
			%>
				<input type="checkbox" name="autoRunCheck" id='checkValue' onclick='updateAutoRun(this,<%=cusId%>);'/>
			<%
				}
			%>
			</td>
			
			<td class="tbldata" onclick="callView('<bean:write name="customerManager" property="prospectiveCustomerId"/>')"><bean:write name="customerManager" property="cdrStatus.cdrState" ignore="true"/>&nbsp;</td>
		 </tr>
		</logic:iterate>
		<%
		  }
  		  else
		  {
	    %>
<!-- 		 	<tr><td class = 'tbldata' colspan = '7' align = 'center'>--- No Results Found ---</td></tr> -->
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
        <!-- navigator Start-->
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
}
}%>
