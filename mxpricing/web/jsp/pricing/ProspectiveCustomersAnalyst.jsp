<%
try{
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
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.ProspectiveCustomerListForm" />
<jsp:setProperty name="frm" property="*" />
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.dao.CDRStatusDAO"%>
<%@ page import="com.savant.pricing.dao.CustomerStatusDAO"%>
<%@ page import="com.savant.pricing.transferobjects.TeamDetails"%>	
<%@ page import="com.savant.pricing.calculation.valueobjects.CustomerPreferencesVO "%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>

<%

	int browserHt = 0;
	Properties props;
	props = new Properties();
	InputStream is = this.getClass().getClassLoader().getResourceAsStream("ApplicationResources.properties");
	props.load(is);
	String highlightColor = props.getProperty("list.highlight.color");
	String padding = props.getProperty("list.highlight.padding");
	TeamDetails objTeamDetails = null;
	ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
	int totalCount=1;
	int pageCount = 0;
	int maxItems = 0;
	int totalPages = 0;
	String str="";
	boolean order = true;
	boolean autoRun = false;
	int cusId;
	HashMap hmResult = new HashMap();
	List listProspectiveCustLst =null;
	List  listCDRStatus = null;
	FilterHandler objFilterHandler = new FilterHandler();
	CustomerStatusDAO objCustomerStatusDAO = new CustomerStatusDAO();
	CustomerPreferencesVO  objCustomerPreferencesVO = null;

	List objCustomerStatusVO = objCustomerStatusDAO.getAllCustomerStatus();
	CDRStatusDAO objCDRStatusDAO = new CDRStatusDAO();
	listCDRStatus = objCDRStatusDAO.getAllCDRStatus();
	if(frm.getSortOrder().equalsIgnoreCase("ascending"))
	{
		order = true;
	}
	else
	{
		order = false;
	}
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
		Filter newfilter  = null;
		Filter Repfilter  = null;
		Filter Mngrfilter  = null;
		if ((frm.getFormActions().equalsIgnoreCase("add"))||(frm.getFormActions().equalsIgnoreCase("list")) ||(frm.getFormActions().equalsIgnoreCase("edit")) )
		{  
			fil = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		else if(frm.getFormActions().equalsIgnoreCase("search")||(frm.getFormActions().equalsIgnoreCase("navigation"))||(frm.getFormActions().equalsIgnoreCase("sorting")))
  		{
  		   maxItems = Integer.parseInt(frm.getMaxItems());
  		  
	       maxItems = Integer.parseInt(frm.getMaxItems());
  		  
  		  	Vector filter = new Vector();
			  if (!frm.getTxtCustomerName().trim().equalsIgnoreCase(""))
			  {
			  		filter = new Vector();
			  		filter = objFilterHandler.setFormDetails("customerName",frm.getTxtCustomerName(),frm.getSearchCustomerName(),filter);
			  		fil = new Filter[filter.size()];
			 		filter.copyInto(fil);
			  		newfilter = fil[0];
			  }
			   if (!frm.getTxtsalesRep().trim().equalsIgnoreCase(""))
			  {
					filter = new Vector();
			  		filter = objFilterHandler.setFormDetails("",frm.getTxtsalesRep(),frm.getSearchsalesRep(),filter);
			  		fil = new Filter[filter.size()];
			 		filter.copyInto(fil);
			  		Repfilter = fil[0];
			  }
			   if (!frm.getTxtmanagerName().trim().equalsIgnoreCase(""))
			  {
			  		filter = new Vector();
			  		filter = objFilterHandler.setFormDetails("",frm.getTxtmanagerName(),frm.getSearchSalesManager(),filter);
			  		fil = new Filter[filter.size()];
			 		filter.copyInto(fil);
			  		Mngrfilter = fil[0];
			  }
	  }
	maxItems = Integer.parseInt(frm.getMaxItems());
	pageCount = Integer.parseInt(frm.getPage());
	if(frm.getFormActions().equalsIgnoreCase("cancel"))
		frm.setCmbCustomerStatus("0");
	hmResult = 	objProspectiveCustomerDAO.getAllProspectiveCustomer(newfilter,Repfilter,Mngrfilter,Integer.parseInt(frm.getTxtCustomerId()),Integer.parseInt(frm.getCmbCustomerStatus()),Integer.parseInt(frm.getCmbCDRStatus()),frm.getCreatedFromDate(), frm.getCreatedToDate(), frm.getModifiedFromDate(), frm.getModifiedToDate(), "customerName",order,((pageCount-1)*maxItems),maxItems,frm.getCmbAutoRun(),frm.getIsMMCust());
     
	totalCount = Integer.parseInt(String.valueOf(hmResult.get("TotalRecordCount")));
	listProspectiveCustLst = (List)hmResult.get("Records");
	pageContext.setAttribute("listProspectiveCustLst",listProspectiveCustLst);
	pageContext.setAttribute("listCDRStatus",listCDRStatus);
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
		browserHt = 265;
   else
		browserHt = 243;
%>
<html:html>
<html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script src='<%=request.getContextPath()%>/script/commonSort.js'></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ProspectiveCustomerDAO.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
</head>
<script>
 function pageDecrement()
{
	temp = document.forms[0];
	var page = 1;
	temp.formActions.value="search";
	page = parseInt(temp.page.value,10) - 1;
	temp.page.value  = page;
	temp.submit();
}
function pageIncrement()
{
	temp = document.forms[0];
	var page = 1;
	temp.formActions.value="search";
	page = parseInt(temp.page.value,10) + 1;
	temp.page.value  = page;
	temp.submit();
}
function changePage()
{
	temp = document.forms[0];
	temp.formActions.value="search";
	temp.page.value = 1;
	temp.submit();
}
var pageCount = 1; 
var urlString = "";

function sortTables(fm, col, field)
	{
	
		try{
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
		}catch(err){
		alert(err.description);
		}
		fm.submit();
	}


function Sort(tabName,columnIndex,ascending)
{try{
	 if(pageCount==1)
	 {
	   sortByTable(tabName,columnIndex,ascending);
	 }
	 else
	 {
	   //set the current page number, number of records per page, sort column index and sort order as the request parameters
	  document.forms[0].submit();
	 }
	 }catch(err)
		{
		alert(err.description);
		}
 }
 function search()
{
	var result = true;
	if(((temp.createdFromDate.value!="")&&(temp.createdToDate.value!=""))||((temp.createdFromDate.value!="")||(temp.createdToDate.value!="")))
	{
		result = callIntervalExcelReport(temp.createdFromDate.value,temp.createdToDate.value);
	}
	else if(((temp.modifiedFromDate.value!="")&&(temp.modifiedToDate.value!=""))||(temp.modifiedFromDate.value!="")||(temp.modifiedToDate.value!=""))
	{
		result = callIntervalExcelReport(temp.modifiedFromDate.value,temp.modifiedToDate.value);
	}
	if(result)
	{
		temp=document.forms[0];
		temp.formActions.value="search";
		temp.page.value = 1;
		temp.submit();
	}
	else
	return;
}


function callIntervalExcelReport(startDate,endDate)

{
      var flag = true;
      var temp = document.forms[0];
      var sdate = "";
      var edate="";
      if(startDate=="")
      {
            alert("Enter Start Date");
            flag = false;
      }
      else if(endDate=="")
      {
            alert("Enter End Date");
            flag = false;
      }
      else
      {
            sdate = startDate;
            edate = endDate;
            
      		var arMon = new Array("01","02","03","04","05","06","07","08","09","10","11","12");
      		if((startDate != "") && (endDate != ""))
      		{
            	var sDate = (startDate).split("-");
            	var eDate = (endDate).split("-");
            	var sMon = 0;
            	var eMon = 0;
            	for(var i = 0; i < arMon.length; i++)
            	{
                  	if (sDate[0] == arMon[i])
                        sMon = i;
            	}
            	var stDate = new Date(sDate[2],sMon,sDate[1]);
            	for(var j = 0; j < arMon.length; j++)
            	{
                  	if (eDate[0] == arMon[j])
                        eMon = j;
            	}
            	var edDate = new Date(eDate[2],eMon,eDate[1]);
            	if (stDate > edDate)
            	{
                  	flag = false;
                  	alert("Start Date cannot be greater than End Date - Can't Export");
                  	//return ;
            	}
      		}
            
		}
		return flag;
}


function clearSearch()
{
try{
	temp=document.forms[0];
	temp.txtCustomerName.value="";
	temp.txtCustomerId.value="";
	temp.txtmanagerName.value="";
	temp.txtsalesRep.value="";
	temp.searchCustomerName.selectedIndex = 0;
	temp.searchSalesManager.selectedIndex = 0;
	temp.searchsalesRep.selectedIndex     = 0;
	temp.cmbCustomerStatus.selectedIndex  = 0;
	temp.cmbCDRStatus.selectedIndex       = 0;
	temp.cmbAutoRun.selectedIndex         = 0;
	temp.createdFromDate.value="";
	temp.createdToDate.value="";
	temp.modifiedFromDate.value="";
	temp.modifiedToDate.value="";
}catch(err)
	{
alert(err.description);
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
	temp.txtmanagerName.value = temp.txtmanagerName.value;
	temp.txtsalesRep.value = temp.txtsalesRep.value;
	temp.txtCustomerId.value = '<%=frm.getTxtCustomerId().equalsIgnoreCase("0")?"":frm.getTxtCustomerId()%>';
	urlString = '&User=Analyst&Cust='+temp.txtCustomerName.value+'&custSrch='+<%=frm.getSearchCustomerName()%>+
				'&cmsId='+temp.txtCustomerId.value+'&mang='+temp.txtmanagerName.value+'&mangSrch='+<%=frm.getSearchSalesManager()%>+
				'&Rep='+temp.txtsalesRep.value+'&repSrch='+<%=frm.getSearchsalesRep()%>+'&status='+<%=frm.getCmbCustomerStatus()%>+
				'&sort='+<%=frm.getSortOrder().equals("ascending")?"0":"1"%>+'&CDRstatus='+<%=frm.getCmbCDRStatus()%>+
				'&crFromDt='+temp.createdFromDate.value+'&crToDt='+temp.createdToDate.value+'&mdFromDt='+temp.modifiedFromDate.value+'&mdToDt='+temp.modifiedToDate.value+'&autoRun='+<%=frm.getCmbAutoRun()%>+'&MMCust='+<%=frm.getIsMMCust()%>;
}
function callCustomerExcel()
{
	var timeanddate = new Date();
    window.parent.location = '<%=request.getContextPath()%>/servlet/CustomerExcelServlet?time='+timeanddate+urlString
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
	temp.action='<%=request.getContextPath()%>/jsp/pricing/ProspectiveCustomersAnalystEdit.jsp?prsCustId='+prsCustId;
	temp.submit();
}
function writetoCMS()
{
  var temp = document.forms[0];
  var custID;
  if (temp.buttonGroup[0]) 
  {
  	for (var i=0; i<temp.buttonGroup.length; i++) 
    {
    	if(temp.buttonGroup[i].checked)
        	custID = temp.buttonGroup[i].value;
    }
  } 
  else 
  {
  	if(temp.buttonGroup.checked) 
    	custID = temp.buttonGroup.value; 
  }
  if((custID==undefined)||(custID==''))
  {
   	alert('Select customer to write details into CMS.');
  }
  else
  {
  	if(!confirm("Warning!\nChosen item Write Into CMS."))
	{
		return;
	}
	else
	{	
		temp.customerId.value = custID;
	    temp.formActions.value = "writeIntoCMS";
	    temp.submit();
	}
  }
}
function deleteCust()
{
 var temp = document.forms[0];
  var custID;
  if (temp.buttonGroup[0]) 
   		{
          for (var i=0; i<temp.buttonGroup.length; i++) 
              {
                if(temp.buttonGroup[i].checked)
                    custID = temp.buttonGroup[i].value;
              }
        } 
        else {
                if(temp.buttonGroup.checked) 
                    custID = temp.buttonGroup.value; 
            }
  if((custID==undefined)||(custID==''))
     {
        	alert('Select customer to delete from list.');
     }
     else
     {
	    temp.customerId.value = custID;
        temp.formActions.value = "delete";
        temp.submit();
     }
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
function constructToolTip(custName,dba)
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

function cal(e)
{
	
}

function checkKey() {  //Function to disable "backspace Key"
	var key = event.keyCode; 
	var temp = document.forms[0];
	var txt = temp.txtCustomerName.value;
	if(txt.length<4)
	{
		if (key == 8) 
		{ 
			event.keyCode=9; 
			return (event.keyCode);
		}
	} 
} 


</script>

<body onload="loadDefault();"> 
<script type="text/javascript" src="<%=request.getContextPath()%>/script/tooltip/wz_tooltip.js"></script> 
<html:form action="frmProspectiveCustomerAnalystList" focus="txtCustomerName">
<html:hidden property="customerId" />
<html:hidden property="formActions"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
<html:hidden property="user"/>
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
	         </tr>
	         <tr> 
	         <td class="error">
				<html:errors/>
	         </td>
	        </tr>
	     </table>
		
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title">Prospects</td> 
		  <td class="page_title"><table align='right' border="0" cellpadding="0" cellspacing="0" class="topnav">
            <tr>
              <td><a href="<%=request.getContextPath()%>/prospectiveCustomersRepAdd.do?User=Analyst">Add</a></td>
              <td>&nbsp;|&nbsp;</td>
              <td><a href="javascript:deleteCust()">Delete</a></td>
            </tr>
          </table></td>
        </tr> 
      </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="100" class="search">Customer Name</td>
          <td width="1" class="search">:</td>
          <td width="190" class="search"><html:text property="txtCustomerName" styleClass="textbox" size="15" maxlength="150" onkeypress="return checkEnter(event)" />
              <html:select property="searchCustomerName" onchange="document.forms[0].txtCustomerName.focus();">
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              </html:select></td>
          <td width="108" class="search">Customer&nbsp;Id</td>
          <td width="1" class="search">:</td>
          <td width="180" class="search"><html:text property="txtCustomerId" styleClass="textbox" size="15" maxlength="15" onkeypress="return checkNumber(event)" />
          </td>
          <td width="90" class="search">Sales Rep</td>
          <td width="1" class="search">:</td>
          <td colspan='2' class="search">
			  <html:text property="txtsalesRep" styleClass="textbox" size="15" maxlength="150" onkeypress="return checkEnter(event)" />
              <html:select property="searchsalesRep" onchange="document.forms[0].txtsalesRep.focus();">
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              </html:select>
          </td>
        </tr>
        <tr>
          <td width="100" class="search">Sales Manager</td>
          <td width="1" class="search">:</td>
          <td width="190" class="search">
			  <html:text property="txtmanagerName" styleClass="textbox" size="15" maxlength="150" onkeypress="return checkEnter(event)" />
              <html:select property="searchSalesManager" onchange="document.forms[0].txtmanagerName.focus();">
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              </html:select>			  
		  </td>
          <td width="108" class="search">Customer Status</td>
          <td width="1" class="search">:</td>
          <td width="180" class="search">
			  <html:select property="cmbCustomerStatus">
                <html:option value="0">Select one</html:option>
				<html:options collection="objCustomerStatusVO" property="customerStatusId" labelProperty="customerStatus" />
			  </html:select>
		  </td>	
		  <td width="100" class="search">Approval Status</td>
        <td width="1" class="search">:</td>
        <td width="190" class="search">
        	<html:select property="cmbCDRStatus">
                <html:option value="0">Select one</html:option>
                <html:options collection="listCDRStatus" property="cdrStateId" labelProperty="cdrState" />
			</html:select>
		</td>
		 <td class="search" align='right'>
			<a href='javascript:callCustomerExcel()'><img border='0'  src="<%=request.getContextPath()%>/images/excel_icon.gif" alt='Export to Excel'></a>&nbsp;&nbsp;&nbsp;
		 </td>
	  </tr>
	  <tr>
		  <td width="90" class="search">MM Customer</td>
          <td width="1" class="search">:</td>
          <td width='190' class="search">
          	  <html:select property="isMMCust">
	            <html:option value="0">No</html:option>
				<html:option value="1">Yes</html:option>
			  </html:select>&nbsp;&nbsp;&nbsp;Auto&nbsp;run:&nbsp;
			  <html:select property="cmbAutoRun">
	            <html:option value="0">All</html:option>
				<html:option value="1">Yes</html:option>
				<html:option value="2">No</html:option>
			  </html:select>
			
		 </td>
		<td width="100" class="search">Created&nbsp;Date</td>
        <td width="1" class="search">:</td>
        <td class="search" colspan='5'> 
			<html:text property="createdFromDate" styleClass="textbox"  size="10" styleId="acreatedFromDate" maxlength="10" onkeypress="return false"/> 
			<a href="#" onClick="showCalendarControl(document.getElementById('acreatedFromDate'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> - 
			<html:text property="createdToDate" styleClass="textbox"  size="10" maxlength="10" onkeypress="return false"/> 
			<a href="#" onClick="showCalendarControl(document.getElementById('createdToDate'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> 
			&nbsp;&nbsp;Modified&nbsp;Date: 
			<html:text property="modifiedFromDate" styleClass="textbox"  size="10" maxlength="10" onkeypress="return false"/> 
			<a href="#" onClick="showCalendarControl(document.getElementById('modifiedFromDate'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> - 
			<html:text property="modifiedToDate" styleClass="textbox"  size="10" maxlength="10" onchange="" onkeypress="return false" /> 
			<a href="#" onClick="showCalendarControl(document.getElementById('modifiedToDate'),'fully');" ><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle" ></a>			
			
			<html:button property="Submit2" value="Go!" onclick="search()" styleClass="button_sub_internal"/>
		  	<html:button property="Submit22" onclick = "clearSearch();" value="Clear" styleClass="button_sub_internal"/>
		</td>
	  </tr>
      </table>
      <table  width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr> 
			<!-- 	<td> <font size="1px" face="Verdana"><html:errors/></font></td> -->
			</tr>
		</table>
	  <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr class='staticHeader'>
            <td width="22" class="cmbheader">&nbsp;</td>
            <%if(order)
            {%>
			<td width="175" class="tblheader" onClick="callSort();" align='center' style="cursor:hand" title='Sort by CustomerName in Ascending'>Customer Name <img src='<%=request.getContextPath()%>/images/sort.gif' width="7" height="8"></td>
            <%}else{%>
			<td width="175" class="tblheader" onClick="callSort();" align='center' style="cursor:hand" title='Sort by CustomerName in Decending'>Customer Name <img src='<%=request.getContextPath()%>/images/sort_up.gif' width="7" height="8"></td>
			<%}%>
            <td width="70" class="tblheader" align='center'>Customer&nbsp;Id</td>
            <td width="100" class="tblheader" align='center'>CUD&nbsp;Imported&nbsp;on</td>            
            <td width="120" class="tblheader" align='center'>Sales&nbsp;Rep</td>
            <td width="100" class="tblheader" align='center'>Sales&nbsp;Manager</td>
            <td class="tblheader" align='center'>Customer&nbsp;Status</td>
            <td width="80" class="tblheader" align='center'>Auto&nbsp;Run</td>
            <td width="100" class="tblheader" align='center'>Approval&nbsp;Status</td>
          </tr>
          <% 
             if( listProspectiveCustLst.size() > 0 )
             {
          %>
          <logic:iterate id="customerAnalyst" name="listProspectiveCustLst" indexId="i">
             <tr onmouseover="this.style.cursor='hand'; this.style.backgroundColor='<%=highlightColor%>';this.style.padding='<%=padding%>'" onmouseout="this.style.backgroundColor='';"> 
             <td width="22" class="tbldata"><input name='buttonGroup' type="radio" value='<bean:write name="customerAnalyst" property="prospectiveCustomerId"/>'></td>
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
			<td height='30' class="tbldata" onmouseover="constructToolTip('<%=tootipCustName%>','<%=dba%>')" onclick="callView('<bean:write name="customerAnalyst" property="prospectiveCustomerId"/>')"><%=str%>&nbsp;</td>
			<%	} 
				else
				{
				  %>
   				<td height='30' class="tbldata" onmouseover="constructToolTip('<%=tootipCustName%>','<%=dba%>')" onclick="callView('<bean:write name="customerAnalyst" property="prospectiveCustomerId"/>')"><bean:write name="customerAnalyst" property="customerName" ignore="true"/>&nbsp;</td>
			<% 	} %>
		   	<td class="tbldata" align="right" onclick="callView('<bean:write name="customerAnalyst" property="prospectiveCustomerId"/>')">
            <bean:write name="customerAnalyst" property="customerId" ignore="true"/>
             &nbsp;</td>
			<td class="tbldata" align="right" onclick="callView('<bean:write name="customerAnalyst" property="prospectiveCustomerId"/>')">&nbsp;<bean:write name="customerAnalyst" property="importedPICOn" ignore="true" format="MM-dd-yyyy HH:mm"/></td>
						
			<%
				int valSalesRep = i.intValue();
				objTeamDetails =  objProspectiveCustomerDAO.getTeam(((ProspectiveCustomerVO)listProspectiveCustLst.get(valSalesRep)).getProspectiveCustomerId());
				String strFrstName = ""; String strLastName = "";
				strFrstName = objTeamDetails.getSalesRep().getFirstName();
				strLastName =objTeamDetails.getSalesRep().getLastName();
				str = strFrstName+" "+strLastName;
				if(str.length()>20)								
				{
					str = str.substring(0,18)+"...";
				%>
			 <td class="tbldata" onclick="callView('<bean:write name="customerAnalyst" property="prospectiveCustomerId"/>')" title='<%=strFrstName%>&nbsp;<%=strLastName%>'><%=str%>&nbsp;</td> 
			<%	} 
				else
				{
				 %>
   				<td class="tbldata" onclick="callView('<bean:write name="customerAnalyst" property="prospectiveCustomerId"/>')"><%=strFrstName%>&nbsp;<%=strLastName%></td>
			<% } %>
			
			<%
						strFrstName = objTeamDetails.getSalesManager().getFirstName();
						strLastName =  objTeamDetails.getSalesManager().getLastName();
						str = strFrstName+" "+strLastName;
						if(str.length()>15)
						{
							str = str.substring(0,13)+"...";
							%>
							<td class="tbldata" title="<%=strFrstName%>&nbsp;<%=strLastName%>"><%=str%>&nbsp;</td>
							<%
						}
						else
						{
			%>
			<td class="tbldata" onclick="callView('<bean:write name="customerAnalyst" property="prospectiveCustomerId"/>')">
			<logic:present name="customerAnalyst" property="salesRep.parentUser">
              <%=strFrstName%>&nbsp;<%=strLastName%>
            </logic:present>
			&nbsp;</td>
					 <% } %>

			<%
						str=((ProspectiveCustomerVO)listProspectiveCustLst.get(valSalesRep)).getCustomerStatus().getCustomerStatus();
						if(str.length()>17)
						{
							str = str.substring(0,15)+"...";
							%>
							<td class="tbldata" title='<bean:write name="customerAnalyst" property="customerStatus.customerStatus" ignore="true"/>'><%=str%>&nbsp;</td>
							<%
						}
						else
						{
			%>
				<td class="tbldata" onclick="callView('<bean:write name="customerAnalyst" property="prospectiveCustomerId"/>')"><bean:write name="customerAnalyst" property="customerStatus.customerStatus" ignore="true"/>&nbsp;</td>
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
				<input type='checkbox' checked name='autoRunCheck' id='checkValue' onclick='updateAutoRun(this,<%=cusId%>);'/>
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
			<td class="tbldata" onclick="callView('<bean:write name="customerAnalyst" property="prospectiveCustomerId"/>')"><bean:write name="customerAnalyst" property="cdrStatus.cdrState" ignore="true"/>&nbsp;</td>
          </tr>
       </logic:iterate>
		<%
		  }
  		  else
		  {
	    %>
		 	<!--<tr><td class = 'tbldata' colspan = '9' align = 'center'>--- No Results Found ---</td></tr>-->
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
<%
}
}
catch(Exception e)
{
e.printStackTrace();
}

%>