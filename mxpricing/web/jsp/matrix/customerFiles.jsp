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
<%@ page import="com.savant.pricing.securityadmin.dao.UserDAO"%>
<%@ page import="com.savant.pricing.securityadmin.valueobject.UsersVO"%>
<%@ page import="com.savant.pricing.common.FilterHandler"%>
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="java.util.List "%>
<%@ page import="java.util.Vector "%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.savant.pricing.matrixpricing.dao.MMCustomersPDFDAO"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.matrix.PriceMatrixCustomerForm" /><jsp:setProperty name="frm" property="*" />
<%
try{
    UserDAO objUserDAO = new UserDAO();
    List lstusers = objUserDAO.getAllUsers();
    List lstMgr = lstusers;
    List lstRep = lstusers;
    FilterHandler objFilterHandler = new FilterHandler();
    MMCustomersPDFDAO objMMCustomersPDFDAO = new MMCustomersPDFDAO();
   	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yyyy");
   	int totalCount=1;
	int pageCount = 0;
	int maxItems = 0;
	java.util.Date fromDate = null;
	java.util.Date toDate = null;
	int totalPages = 0;
    Filter fil[] =null;
	Filter custfilter  = null;
	Filter runIdfilter  = null;
    Vector filter = new Vector();
    HashMap hmResult = new HashMap();
    List lstCust = new ArrayList();
    
    String usrName = (String)session.getAttribute("userName");
    boolean cpeElgible = objUserDAO.isUserElgible(usrName,"Run");
    List lstChildPersons = objUserDAO.getChildPersons(usrName, false);
    if(!cpeElgible)
    {
    	if(lstChildPersons.size()>0)
    	{
    		UsersVO objUsersVO = new UsersVO();
    		objUsersVO = objUserDAO.getUsers(usrName);
    		lstChildPersons.remove(objUsersVO);
    		lstMgr = new ArrayList();
    		lstMgr.add(objUsersVO);
    		lstRep = lstChildPersons;
    	}
    	else
    	{
    		UsersVO objUsersVO = new UsersVO();
    		lstMgr = new ArrayList();
    		lstRep = objUserDAO.getChildPersons(usrName, true);
    	}
    }
    
    if (!frm.getCustName().trim().equalsIgnoreCase(""))
	 {
        filter = new Vector();
  		filter = objFilterHandler.setFormDetails("customerName",frm.getCustName(),frm.getSearchCustName(),filter);
  		fil = new Filter[filter.size()];
 		filter.copyInto(fil);
  		custfilter = fil[0];
	  }
	   if (!frm.getRefNo().trim().equalsIgnoreCase(""))
	  {
		filter = new Vector();
  		filter = objFilterHandler.setFormDetails("",frm.getRefNo(),frm.getSearchrefNo(),filter);
  		fil = new Filter[filter.size()];
 		filter.copyInto(fil);
  		runIdfilter = fil[0];
	  }  
	 maxItems = Integer.parseInt(frm.getMaxItems());
	 pageCount = Integer.parseInt(frm.getPage());
	 
	 if(!frm.getFromDate().equalsIgnoreCase(""))
	 {
		   fromDate = sdf.parse(frm.getFromDate());
		 if(!frm.getToDate().equalsIgnoreCase(""))
		   toDate = sdf.parse(frm.getToDate());
	 }
	 hmResult = objMMCustomersPDFDAO.getCutomers(cpeElgible, (String)session.getAttribute("userName"), custfilter,runIdfilter,frm.getRep(),frm.getManager(), fromDate, toDate,frm.getSearchrunDate(),((pageCount-1)*maxItems),maxItems);
	 lstCust = (List)hmResult.get("Records");
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
		
	int browserHt = 0;
	 if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 235;
	else
		browserHt = 215;
	
	pageContext.setAttribute("lstCust",lstCust);
	pageContext.setAttribute("lstRep",lstRep);
	pageContext.setAttribute("lstMgr",lstMgr);
%>
<html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script src='<%=request.getContextPath()%>/script/commonSort.js'></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
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
	temp.formActions.value="search";
	temp.submit();
}

function pageIncrement()
{
	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) + 1;
	temp.page.value  = page;
	temp.formActions.value="search";
	temp.submit();
}

function search()
{
	temp=document.forms[0];
	temp.formActions.value="search";
	temp.page.value = 1;
	temp.submit();
}

function clearSearch()
{
	try
	{
		temp=document.forms[0];
		temp.formActions.value="";
		temp.custName.value="";
		temp.refNo.value="";
		temp.fromDate.value="";
		temp.toDate.value="";
		temp.searchCustName.selectedIndex = 0;
		temp.searchrefNo.selectedIndex = 0;
		temp.rep.selectedIndex = 0;
		temp.manager.selectedIndex = 0;
		temp.searchrunDate.selectedIndex = 0;
	}
	catch(err)
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
	callBetween();
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

function callView(prsCustId)
{
	temp=document.forms[0];
	temp.action='<%=request.getContextPath()%>/jsp/pricing/ProspectiveCustomersMngrEdit.jsp?prsCustId='+prsCustId;
	temp.submit();
}

function xpressPricingPdf(refNo)
{
	if(refNo != "")
	{
	    window.parent.location = '<%=request.getContextPath()%>/servlet/XpressPricingPdf?referNo='+refNo;
	}
}

function deleteCustFile()
{
  try
	{
	  var temp = document.forms[0];
	  var refId;
	  var radioObj = document.getElementsByName("radioGroup");
	  if (radioObj.length>0) 	  
	  {
		  for (var i=0; i<radioObj.length; i++) 
		  {
		  	if(radioObj[i].checked)
		    	refId = radioObj[i].value;
		  }
		  
		  if((refId==undefined)||(refId==''))
		  {
			  alert('Select Customer File to delete from list.');
		  }
		  else
		  {
		    if(!confirm("Warning!\nChosen item(s) will be deleted."))
			{
				return;
			}
			else
			{	
			    temp.referNum.value = refId;
			    temp.formActions.value = "delete";
			    temp.submit();
			}
		  }
	   } 
	   else
	   {
	   	alert('No Customer File in list.');
	   }
	}
	catch(err)
	{
		alert(err.description);
	}
}

function callXpressPricingList()
{
	temp = document.forms[0];
	temp.action = '<%=request.getContextPath()%>/jsp/matrix/xpressPricingList.jsp';
	temp.submit();
}

function callXpressPricing()
{
	temp = document.forms[0];
	temp.action = '<%=request.getContextPath()%>/jsp/matrix/xpressPricing.jsp';
	temp.submit();
}

function callBetween()
{
	temp = document.forms[0];
	var searchBtwn = temp.searchrunDate.value;
	if(searchBtwn == "between")
	{
		document.getElementById('toRunDate').style.display = 'block';
		document.getElementById('Dummy').style.display = 'none';
	} 
	else 
	{ 
		document.getElementById('toRunDate').style.display = 'none';
		document.getElementById('Dummy').style.display = 'block';
	}
}

</script>
<body onload='loadDefault()'> 
<html:form action="/frmMatrixCustomers">
<html:hidden property="formActions"/>
<html:hidden property="referNum" />
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/> 
      <!-- Menu End --> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td class="message">
			<logic:messagesPresent message="true" >
				<html:messages id="messageid" message="true">
					<bean:write name="messageid" />
				</html:messages>
			</logic:messagesPresent> </td> 
          <td class='error'><html:errors/></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title">MM Pricing - Customer Files</td> 
          <td class="page_title"><table border="0" align="right" cellpadding="0" cellspacing="0" class="topnav"> 
              <tr>
                <td><a href="javascript:deleteCustFile()">Delete</a> | <a href="#" onClick="callXpressPricing()">XPress Pricing</a>
                <%if(cpeElgible){%>
                 | <a href="#" onClick="callXpressPricingList()">List</a>
                 <%}%>
                 &nbsp;&nbsp;</td> 
              </tr> 
            </table></td> 
        </tr> 
      </table> 
      <table width="100%" border="0" cellpadding="0" cellspacing="0"> 
        <tr> 
          <td width="100" class="search">Customer Name</td> 
          <td width="1" class="search">:</td> 
          <td width="280" class="search" colspan="2"><html:text property="custName" size="15" styleClass="textbox" onkeypress="return checkEnter(event)" /> <html:select property="searchCustName" onchange="document.forms[0].custName.focus();"> <html:option value="0">Start With</html:option> <html:option value="1">End With</html:option> <html:option value="2">Exactly</html:option> <html:option value="3">AnyWhere</html:option> </html:select></td> 
          <td width="110" class="search">Reference Number</td> 
          <td width="1" class="search">:</td> 
          <td width="200" class="search" ><html:text property="refNo" size="15" styleClass="textbox" onkeypress="return checkEnter(event)" /> <html:select property="searchrefNo" onchange="document.forms[0].custName.focus();"> <html:option value="0">Start With</html:option> <html:option value="1">End With</html:option> <html:option value="2">Exactly</html:option> <html:option value="3">AnyWhere</html:option> </html:select></td> 
          <td width="65" class="search" >Sales Rep </td> 
          <td width="1" class="search" >:</td> 
          <td class="search">
			<html:select property="rep" onchange="document.forms[0].custName.focus();">
				<html:option value="0">Select one</html:option>
				<html:options collection="lstRep" property="userId" labelProperty="firstName"/>
			</html:select></td> 
        </tr>
        <tr> 
          <td width="100" class="search">Run Date </td> 
          <td width="1" class="search">:</td> 
          <td width="170" class="search">
				<html:select property="searchrunDate" onchange="callBetween();">
					<html:option value="on">On</html:option>
					<html:option value="before">Before</html:option>
					<html:option value="after">After</html:option>
					<html:option value="between">Between</html:option>
				</html:select>
				<html:text property="fromDate" styleClass="textbox" styleId="txtDateFrom" size="10" maxlength="10" onkeypress="return false" /> <a href="#" onClick="showCalendarControl(document.getElementById('txtDateFrom'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" name="imgDateFrom" width="16" height="16" border="0" align="absmiddle" id="imgDateFrom"></a>
		  </td>
		  <td width="110" class="search" id='Dummy' style="display:block">&nbsp;</td> 
          <td width="110" class="search" id='toRunDate' style="display:none">
          	<table border="0" cellpadding="0" cellspacing="0"><tr><td> - <html:text property="toDate" styleClass="textbox" styleId="txtDateTo" size="10" maxlength="10" onkeypress="return false" /> <a href="#" onClick="showCalendarControl(document.getElementById('txtDateTo'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" name="imgDateTo" width="16" height="16" border="0" align="absmiddle" id="imgDateTo"></a></td></tr></table></td> 
          <td width="110" class="search">Sales Manager</td> 
          <td width="1" class="search">:</td> 
          <td width="200" class="search">
			<html:select property="manager" onchange="document.forms[0].custName.focus();">
				<html:option value="0">Select one</html:option>
				<html:options collection="lstMgr" property="userId" labelProperty="firstName"/>
			</html:select></td> 
          <td class="search" colspan="3"> <html:button property="Button" value="Go!" styleClass="button_sub_internal" onclick="search()" /> <html:button property="Button" value="Clear" styleClass="button_sub_internal" onclick="clearSearch()" /></td> 
        </tr> 
      </table> 
      <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="prospectiveCustomerLst"> 
          <tr class='staticHeader'> 
            <td width="23" class="cmbheader">&nbsp;</td> 
            <td width="210" class="tblheader" align='center'>Customer Name</td> 
            <td width="152" class="tblheader" align='center'>Run Date</td> 
            <td width="211" class="tblheader" align='center'>Reference Number</td> 
            <td width="184" align='center' class="tblheader">Created by</td> 
            <td width="155"  align='center' class="tblheader">Sales Rep</td> 
            <td width="166"  align='center' class="tblheader">Sales Manager</td> 
          </tr>
          <% 
             if( lstCust.size() > 0 )
             {
          %> 
          <logic:iterate id="result" name="lstCust" > 
          <tr> 
            <td class="tbldata"><input name="radioGroup" type="radio" value="<bean:write name="result" property="refNo" ignore="true"/>"></td> 
            <td class="tbldata"><bean:write name="result" property="custName" ignore="true"/>&nbsp;</td> 
            <td class="tbldata"><bean:write name="result" property="priceRunRefNo.priceRunTime" ignore="true" format="MM-dd-yyyy hh:mm"/>&nbsp;</td> 
            <td class="tbldata"><a href="#" onClick="xpressPricingPdf('<bean:write name="result" property="refNo" ignore="true"/>')"><bean:write name="result" property="refNo" ignore="true"/></a>&nbsp;</td> 
            <td class="tbldata" title='<bean:write name="result" property="createdDate" ignore="true" format="MM-dd-yy hh:mm a"/>'><bean:write name="result" property="createdBy" ignore="true"/>&nbsp;</td> 
            <td class="tbldata"><bean:write name="result" property="salesRep.userId" ignore="true"/>&nbsp;</td> 
            <td class="tbldata"><bean:write name="result" property="salesManager.userId" ignore="true"/>&nbsp;</td> 
          </tr> 
          </logic:iterate>
		  <%
		  }
  		  else
		  {
	    %>
		 	<tr><td class = 'tbldata' colspan = '7' align = 'center'>--- No Results Found ---</td></tr>
		<%
		  }
		%>  
        </table>
      </div> 
      <!-- navigator Start--> 
      <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="sort"> 
        <tr> 
          <td width="100">Page <%=pageCount%> of <%=totalPages%></td> 
          <td width="150">Items <%=totalCount>0?(pageCount*maxItems)-maxItems+1:0%> - <%=((maxItems*pageCount)>totalCount)?totalCount:(maxItems*pageCount)%> of <%=totalCount%></td> 
          <td>Show <html:radio property="maxItems" value="10" onclick="changePage()"/> 10 <html:radio property="maxItems" value="20" onclick="changePage()"/> 20 <html:radio property="maxItems" value="50" onclick="changePage()"/> 50 <html:radio property="maxItems" value="100" onclick="changePage()"/> 100 Items/Page </td> 
          <td width="180" class="nav_page_right"> <%
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
							   %> </td> 
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
</html>
<%}
catch(Exception e)
{
e.printStackTrace();
}
}
%>
