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
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.common.FilterHandler"%>
<%@ page import="com.savant.pricing.dao.CustomerStatusDAO"%>
<%@ page import="com.savant.pricing.dao.CDRStatusDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.ProspectiveCustomerListForm" /><jsp:setProperty name="frm" property="*" />
<%
		ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
		int totalCount=1;
		int pageCount = 1;
		int maxItems = 0;
		int totalPages = 0;
		String str = "";
		String address = "";
		boolean order = true;
		HashMap hmResult = new HashMap();
		List listProspectiveCustLst = null;
		List  listCDRStatus = null;
		FilterHandler objFilterHandler = new FilterHandler();
		CustomerStatusDAO objCustomerStatusDAO = new CustomerStatusDAO();
		List objCustomerStatusVO = objCustomerStatusDAO.getAllCustomerStatus();
		String sortFieldName = frm.getSortField();
		CDRStatusDAO objCDRStatusDAO = new CDRStatusDAO();
		listCDRStatus = objCDRStatusDAO.getAllCDRStatus();
		try
		{
			pageContext.setAttribute("objCustomerStatusVO",objCustomerStatusVO);
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
			Filter fil[] =null;
			Filter newfilter  = null;
			if(frm.getFormActions().equalsIgnoreCase("search")||(frm.getFormActions().equalsIgnoreCase("navigation")))
	  		{
	  		   	maxItems = Integer.parseInt(frm.getMaxItems());
	  		  	Vector filter = new Vector();
				  if (!frm.getTxtCustomerName().trim().equalsIgnoreCase(""))
				  {
				  		filter = objFilterHandler.setFormDetails("customerName",frm.getTxtCustomerName(),frm.getSearchCustomerName(),filter);
				  		fil = new Filter[filter.size()];
				 		filter.copyInto(fil);
				  		newfilter = fil[0];
				  }
			}	
			maxItems = Integer.parseInt(frm.getMaxItems());
			pageCount = Integer.parseInt(frm.getPage());
			hmResult = objProspectiveCustomerDAO.getAllProspectiveCustomer(newfilter, null, null, Integer.parseInt(frm.getTxtCustomerId()), 0, 1, "", "", "", "", "customerName", order, ((pageCount-1)*maxItems), maxItems, frm.getCmbAutoRun(), "0");
			totalCount = Integer.parseInt(String.valueOf(hmResult.get("TotalRecordCount")));
			listProspectiveCustLst = (List)hmResult.get("Records");
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
						
		pageContext.setAttribute("listProspectiveCustLst",listProspectiveCustLst);
		pageContext.setAttribute("listCDRStatus",listCDRStatus);
		
		int browserHt = 0;
		if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
			browserHt = 260;
		else
			browserHt = 240;
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/mm_menu.js"></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ProspectiveCustomerDAO.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
</head>
<script>

 function pageDecrement()
 {

 	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) - 1;
	temp.page.value  = page;
	temp.pageaction.value = "same";
	temp.submit();
	}
function changePage()
{
	temp=document.forms[0];
	temp.formActions.value="navigation";
	temp.page.value  =0;
	temp.pageaction.value = "same";
	temp.submit();
}
function pageIncrement()
 {
 	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) + 1;
	temp.page.value  = page;
	temp.pageaction.value ="same";
	temp.submit();
}
function search()
{
	temp=document.forms[0];
	temp.formActions.value="search";
	temp.page.value = 1;
	temp.pageaction.value = "same";
	temp.submit();
}
function clearSearch()
{
try{
	temp=document.forms[0];
	temp.txtCustomerName.value="";
	temp.txtCustomerId.value="";
	temp.searchCustomerName.selectedIndex =0;
}catch(err)
	{
alert(err.description);
}
}
		function onchck(chcked,value)
			{			
			var temp = document.forms[0];						
			  if(chcked)
				{
				  if( temp.pagecustIds.value.length>=1)
				  temp.pagecustIds.value += ","+value;
				  else
					  temp.pagecustIds.value += value;					  
				}
				else
				{
					var custIdValues = temp.pagecustIds.value.split(",");					
					var tempCustId = "";
					for(var i = 0;i<custIdValues.length;i++)
					{
						if(custIdValues[i]!=value)
						{
							if(tempCustId.length>0)
							tempCustId += ","+custIdValues[i];
							else
							 tempCustId += custIdValues[i];							
						}
					}
					 temp.pagecustIds.value = tempCustId;					
				}							
			}
function chkChildParent()
{
		var ischkAll = true;
		if(document.forms[0].checkValue.length!=undefined)
		{
			for(var i=0;i<document.forms[0].checkValue.length;i++)
			{
		
				if(!document.forms[0].checkValue[i].checked)
				{
					ischkAll = false;
					break;
				}
			}
		}
		else if(!document.forms[0].checkValue.checked)
		{
			ischkAll = false;
		}

		document.forms[0].checkboxs.checked = ischkAll;
}
function checkAll()
{
	var isCheck=document.forms[0].checkboxs.checked;
	var temp = document.forms[0];						
			  	
	if(document.forms[0].checkValue.length!=undefined)
	{
		for (var i=0;i<document.forms[0].checkValue.length;i++ )
		{
			if(!isCheck)
			{
				var custIdValues = temp.pagecustIds.value.split(",");
					var tempCustId = "";
					for(var j = 0;j<custIdValues.length;j++)
					{
						if(custIdValues[j]!=temp.checkValue[i].value)
						{
							if(tempCustId.length>0)
							tempCustId = custIdValues[j]+",";
						}
					}
					 temp.pagecustIds.value = tempCustId;
			}
			else
			{
				 temp.pagecustIds.value += temp.checkValue[i].value +",";		
			}
			document.forms[0].checkValue[i].checked=isCheck;
		}
	}else
	{
		document.forms[0].checkValue.checked=isCheck;
		if(isCheck)
		    temp.pagecustIds.value = temp.checkValue.value;
		else
			temp.pagecustIds.value = "";

		
	}
}


function selectedCust()
	{
	try{
		temp=document.forms[0];		
		var obj = document.getElementById('customerList');
		
		if(temp.pagecustIds.value.length>=1)
		{
			
		temp.action='<%=request.getContextPath()%>/runList.do';
		temp.custIds.value = temp.pagecustIds.value;
		temp.submit();
		}
		else
		{
		alert("Please select atleast one Customer to run Pricer");
		}
		
	}catch(err)
	{
		alert(err.description);
	}
}
function loadDefault()
{
	temp = document.forms[0];

	var formAction = '<%=frm.getFormActions()%>';
	var maxItems = '<%=frm.getMaxItems()%>';
	var page = '<%=frm.getPage()%>';

	temp.page.value = page;
	temp.txtCustomerId.value = '<%=frm.getTxtCustomerId().equalsIgnoreCase("0")?"":frm.getTxtCustomerId()%>';
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
	temp.custIds.value = '<%=request.getParameter("custIds")%>';	
	temp.pageaction.value = '<%=request.getParameter("pageaction")%>';
	if(temp.pageaction.value!=null && temp.pageaction.value == 'same')
	{
		temp.pagecustIds.value = '<%=request.getParameter("pagecustIds")%>';
	}	
	else if(temp.pageaction.value != 'same')
	{
		if(temp.custIds.value!=0)
		{
			temp.pagecustIds.value = temp.custIds.value;		
		}
	}
		var arr = temp.pagecustIds.value.split(",");
		if(document.forms[0].checkValue!=null)
		if(document.forms[0].checkValue.length!=undefined)
		{		
			for (var i=0;i<document.forms[0].checkValue.length;i++ )
			{
				for (var j=0;j<arr.length ;j++ )
				{
					if(document.forms[0].checkValue[i].value==arr[j])
					{
						document.forms[0].checkValue[i].checked = true;		
						chkChildParent();
						break;					
					}
				}
			}
		}else
			{
			for (var j=0;j<arr.length ;j++ )
				{
					if(document.forms[0].checkValue.value==arr[j])
					{
						document.forms[0].checkValue.checked = true;		
						chkChildParent();
						break;					
					}
				}		
				
			}
}
function changePageGoto()
{
	temp = document.forms[0];
	temp.formActions.value="search";
	temp.pageaction.value = "same";
	temp.submit();
}
function callSort()
 {
	temp=document.forms[0];
	var odr = '<%=order%>';
	temp.pageaction.value = "same";
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
function cancelSearch()
{
		var temp = document.forms[0];
		temp.action='<%=request.getContextPath()%>/runList.do';
		if(temp.custIds.value!=undefined)
		temp.custIds.value = temp.custIds.value;
		temp.submit();
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

function constructToolTip(custName, dba, id)
{
	//ProspectiveCustomerDAO.getValidandtotalESIIDCount(id, function(data)
   // {	
    	var table = "<span bgcolor='#E8E8E8'><table height=\'40\' border=\'0\' cellspacing=\'1\' cellpadding=\'0\'>"+
		     "<tr ><td><b> Name</b></td>"+
		     "<td width=\'1\'>:</td>"+
	    	 "<td> "+custName+"</td></tr>"+
		     "<tr><td><b>DBA</b></td>"+
	    	 "<td width=\'1\'>:</td>"+
	      	 "<td>"+dba+"</td></tr>"+
             "</table></span>";
        Tip(table);
	//});
}


</script>
<body onload ="loadDefault()">
<script type="text/javascript" src="<%=request.getContextPath()%>/script/tooltip/wz_tooltip.js"></script> 
<html:form action="runSearch" focus="txtCustomerName"> 
<html:hidden property="customerId"/> 
<html:hidden property="formActions"/> 
<input type="hidden" name="custIds"/>
<input type="hidden" name="pagecustIds"/>
<input type="hidden" name="pageaction"/>
<input type="hidden" name="pageCount" value="<%=pageCount%>"> 
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="100%"  valign="top" >
      <!-- Menu Start -->
      <jsp:include page="../menu.jsp"/>
  <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title"> Add Customer To Run </td> 
          <td class="page_title">&nbsp;</td>
        </tr> 
      </table> 
	<table width='100%' border="0" cellpadding="0" cellspacing="0"> 
        <tr> 
          <td width="150"  class="search">Prospective Customers</td> 
          <td width="1"    class="search">:</td> 
          <td width="200"  class="search"><html:text property="txtCustomerName" styleClass="textbox" size="15" maxlength="15" onkeypress="return checkEnter(event)" /> <html:select property="searchCustomerName"> <html:option value="0">Start With</html:option> <html:option value="1">End With</html:option> <html:option value="2">Exactly</html:option> <html:option value="3">AnyWhere</html:option> </html:select></td> 
          <td width="100"  class="search">Customer Id </td> 
          <td width="1"    class="search">:</td> 
          <td width = '50' class="search"><html:text property="txtCustomerId" styleClass="textbox" size="15" maxlength="15" onkeypress="return checkEnter(event)" /></td>
          <td width = '60' class = 'search'>Auto&nbsp;run</td> 
          <td width = '1'  class = 'search'>:</td> 
          <td class = 'search'>
			  <html:select property = 'cmbAutoRun'>
	            <html:option value = '0'>All</html:option>
				<html:option value = '1'>Yes</html:option>
				<html:option value = '2'>No</html:option>
			  </html:select>
              <html:button property="Button" value="Go!" styleClass="button_sub_internal" onclick="search()" /> <html:button property="Button" value="Clear" styleClass="button_sub_internal" onclick="clearSearch()"/>
		  </td>
        </tr> 
       <%-- <tr> 
          <td width="116" class="search">Customer Status </td> 
          <td width="9" class="search">:</td> 
          <td width="213" class="search"> <html:select property="cmbCustomerStatus"> <html:option value="0">--choose --</html:option> <html:options collection="objCustomerStatusVO" property="customerStatusId" labelProperty="customerStatus" /> </html:select> </td> 
          <td width="116" class="search">CDR&nbsp;Status</td> 
          <td width="10" class="search">:</td> 
          <td class="search" colspan="4"><html:select property="cmbCDRStatus"> <html:option value="0">--choose --</html:option> <html:options collection="listCDRStatus" property="cdrStateId" labelProperty="cdrState" /> </html:select> 
          </td> 
        </tr> --%>
      </table>
      <table> 
        <tr> 
          <td> <font size="1px" face="Verdana" style="color:green"><html:errors/></font></td> 
        </tr> 
      </table>
      <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
	<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="customerList"> 
        <tr class='staticHeader'> 
          <td width="10" class="cmbheader"><input type="checkbox" name="checkboxs" onClick="checkAll()"></td> 
          <td width="183" class="tblheader">Prospective Customers</td> 
          <td width="53" class="tblheader">Customer&nbsp;Id </td> 
          <td width="73" class="tblheader">CUD&nbsp;Imported </td> 
          <td width="107" class="tblheader">Business Type</td>
          <td width="115" class="tblheader">Point of Contact </td> 
          <td width="158" class="tblheader">Address</td> 
          <td width="133" class="tblheader"> Customer Status </td> 
          <td width="130" class="tblheader">Approval&nbsp;Status</td> 
        </tr> 
        <logic:iterate id="searchcustomer" name="listProspectiveCustLst" indexId="i"> 
        <tr> 
          <td class="tbldata"><input name='checkbox"<bean:write name="searchcustomer" property="prospectiveCustomerId" ignore="true"/>"' id="checkValue" type="checkbox" value='<bean:write name="searchcustomer" property="prospectiveCustomerId" ignore="true"/>' onClick="chkChildParent();onchck(this.checked,this.value)"></td> 
          <%
				int val = i.intValue();
				str     = ((ProspectiveCustomerVO)listProspectiveCustLst.get(val)).getCustomerName();
				String dba = ((ProspectiveCustomerVO)listProspectiveCustLst.get(val)).getCustomerDBA()==null?"":((ProspectiveCustomerVO)listProspectiveCustLst.get(val)).getCustomerDBA();
				String tootipCustName = str;
				tootipCustName = tootipCustName.replaceAll("'","\\\\'");
                tootipCustName = tootipCustName.replaceAll("\"","");
                dba = dba.replaceAll("'","\\\\'");
                dba = dba.replaceAll("\"","");
				if(str.length()>26)
				{
					str = str.substring(0,25)+"...";
				%>
					<td class="tbldata" onmouseover="constructToolTip('<%=tootipCustName%>', '<%=dba%>', <bean:write name = 'searchcustomer' property = 'prospectiveCustomerId'/>)"><%=str%></td> 
				<%
				}
				else
				{
			%> 
          			<td class="tbldata" onmouseover="constructToolTip('<%=tootipCustName%>', '<%=dba%>', <bean:write name = 'searchcustomer' property = 'prospectiveCustomerId'/>)"><bean:write name="searchcustomer" property="customerName" ignore="true"/></td> 
          	<% 	} %>
          <td class="tbldata" align='right'><bean:write name="searchcustomer" property="customerId" ignore="true"/>&nbsp;</td> 
          <logic:present name="searchcustomer" property="importedPICOn">
          <td class="tbldata" align='center' title='Imported on <bean:write name="searchcustomer" property="importedPICOn"/>'>Yes
          </logic:present >
		  <logic:notPresent name="searchcustomer" property="importedPICOn">
           <td class="tbldata" align='center'>No
          </logic:notPresent>
          </td> 
          <td class="tbldata"><bean:write name="searchcustomer" property="businessType" ignore="true" />&nbsp;</td>
          <td class="tbldata"><bean:write name="searchcustomer" property="pocFirstName" ignore="true" />&nbsp;</td> 
          <%
          	address = ((ProspectiveCustomerVO)listProspectiveCustLst.get(val)).getAddress();
				if(address.length()>21)
				{
					address = address.substring(0,21)+"...";
				%>	
					<td class="tbldata" title='<bean:write name="searchcustomer" property="address" ignore="true" />'><%=address%>&nbsp;</td> 
			<%
				}
				else
				{
          %>
          			<td class="tbldata"><bean:write name="searchcustomer" property="address" ignore="true" />&nbsp;</td> 
          <% 	} %>
          <td class="tbldata"><bean:write name="searchcustomer" property="customerStatus.customerStatus" ignore="true" />&nbsp;</td> 
          <td class="tbldata"><bean:write name="searchcustomer" property="cdrStatus.cdrState" ignore="true" />&nbsp;</td> 
        </tr> 
        </logic:iterate> 
      </table>
      </div>
	<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="sort"> 
        <tr> 
          <td width="100">Page <%=pageCount%> of <%=totalPages%></td> 
          <td width="150">Items <%=totalCount>0?(pageCount*maxItems)-maxItems+1:0%> - <%=((maxItems*pageCount)>totalCount)?totalCount:(maxItems*pageCount)%> of <%=totalCount%></td> 
          <td>Show <html:radio property="maxItems" value="10" onclick="changePage()"/> 10 <html:radio property="maxItems" value="20" onclick="changePage()"/> 20 <html:radio property="maxItems" value="50" onclick="changePage()"/> 50 <html:radio property="maxItems" value="100" onclick="changePage()"/> 100 Items/Page </td> 
          <td width="180" class="nav_page_right"> <%
								if(Integer.parseInt(frm.getPage())>1)
								{%> 
          <a href="#" style="color:blue" onclick="pageDecrement()" ><img src='<%=request.getContextPath()%>/images/previous.gif' align="bottom" alt="Previous" border="0"> Previous</a> 
            <%}%> 
            Goto <html:select property="page" onchange="changePageGoto()"> 
            <%for(int i=0;i<totalPages;i++){%> 
            <option value="<%=(i+1)%>"><%=(i+1)%></option> 
            <%}%> 
            </html:select> 
            <%if((Integer.parseInt(frm.getPage())>1) && (Integer.parseInt(frm.getPage())<totalPages))
							    {%> 
            <%}if(Integer.parseInt(frm.getPage())<totalPages)
							    {%> 
            <a href="#" style="color:blue" onclick="pageIncrement()">Next <img src='<%=request.getContextPath()%>/images/next.gif' align="bottom" alt="Next" border="0"></a> 
            <%}%> </td> 
        </tr> 
      </table>
  <tr class="page_title">
    <td  align="right" >
      <input type="button" name="add"  class="button" value="Add" onClick="selectedCust()">
      <input type="button" name="cancel" value="Cancel" class="button" onClick="cancelSearch()"></td>
  </tr>
  <tr><td>&nbsp;</td></tr>
  <tr>
    <td height="20" ><table width="100%"  border="0" cellpadding="0" cellspacing="0" > 
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
