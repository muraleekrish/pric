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
<%@ page import="com.savant.pricing.calculation.dao.PricingDAO"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.util.List"%>
<%@ page import="com.savant.pricing.common.FilterHandler"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.dao.PreferenceTermsDAO"%>
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO"%>
<%@ page import="com.savant.pricing.dao.PreferenceProductsDAO"%>
<%@ page import="com.savant.pricing.securityadmin.dao.UserDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.PriceRunCustomerTermsVO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.PriceRunCustomerProductsVO"%>
<%@ page import="com.savant.pricing.transferobjects.TeamDetails"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.RunListForm" />
<jsp:setProperty name="frm" property="*" />

<%
	int browserHt = 0;
	PreferenceProductsDAO objPreferenceProductsDAO = new PreferenceProductsDAO();
	PriceRunCustomerTermsVO objPriceRunCustomerTermsVO = new PriceRunCustomerTermsVO();
	PriceRunCustomerProductsVO objPriceRunCustomerProductsVO = new PriceRunCustomerProductsVO();
	TeamDetails objTeamDetails = null;
	ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
	
	UserDAO objUserDAO = new UserDAO();
	boolean runElgible = objUserDAO.isUserElgible((String)session.getAttribute("userName"),"Run");
	
	Properties props;
	props = new Properties();
	InputStream is = this.getClass().getClassLoader().getResourceAsStream("ApplicationResources.properties");
	props.load(is);
	String highlightColor = props.getProperty("list.highlight.color");
	String padding = props.getProperty("list.highlight.padding");
	
	int totalCount=1;  
	int pageCount = 1;
	int maxItems = 0;
	int totalPages = 0;
	String str="";
	boolean order = true;
	HashMap hmResult = new HashMap();
	List prLst = null;
	FilterHandler objFilterHandler = new FilterHandler();
	PricingDAO pricingDAO  =  new PricingDAO();
	PreferenceTermsDAO objPreferenceTermsDAO = new PreferenceTermsDAO();
	java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MM-dd-yyyy");

try{
		Filter fil[] =null;
		Filter filtPriceRunId  = null;
		Filter filtCust  = null;
		Filter filtSalesRep  = null;
		Filter filtSalesManager  = null;
		Filter filtEsiid  = null;
		if(request.getParameter("ftmsg")!=null&&request.getParameter("ftmsg").equalsIgnoreCase("footer"))
			{
			String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";
			String menuFtr[]= menupath.split("For");
			if(menuFtr.length>1 && ! menuFtr[1].trim().equalsIgnoreCase("all Customers"))
			 {
			 	String id = menuFtr[1].split("-")[0].trim();
				frm.setTxtEsiid(id);
				frm.setFormActions("search");
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
			  		filtCust = fil[0];
			  }
			   if (!frm.getTxtPrcRunId().trim().equalsIgnoreCase(""))
			  {
					filter = new Vector();
			  		filter = objFilterHandler.setFormDetails("",frm.getTxtPrcRunId(),frm.getCmbPriceRunId(),filter);
			  		fil = new Filter[filter.size()];
			 		filter.copyInto(fil);
			  		filtPriceRunId = fil[0];
			  }
			   if (!frm.getTxtSalesRep().trim().equalsIgnoreCase(""))
			  {
			  		filter = new Vector();
			  		filter = objFilterHandler.setFormDetails("",frm.getTxtSalesRep(),frm.getCmbSalesRep(),filter);
			  		fil = new Filter[filter.size()];
			 		filter.copyInto(fil);
			  		filtSalesRep = fil[0];
			  }
			   if (!frm.getTxtSalesManager().trim().equalsIgnoreCase(""))
			  {
			  		filter = new Vector();
			  		filter = objFilterHandler.setFormDetails("",frm.getTxtSalesManager(),frm.getCmbSalesManager(),filter);
			  		fil = new Filter[filter.size()];
			 		filter.copyInto(fil);
			  		filtSalesManager = fil[0];
			  }
			   if (!frm.getTxtEsiid().trim().equalsIgnoreCase(""))
			  {
			  		filter = new Vector();
			  		filter = objFilterHandler.setFormDetails("",frm.getTxtEsiid(),frm.getCmbEsiId(),filter);
			  		fil = new Filter[filter.size()];
			 		filter.copyInto(fil);
			  		filtEsiid = fil[0];
			  }
		}
		maxItems = Integer.parseInt(frm.getMaxItems());
		pageCount = Integer.parseInt(frm.getPage());
		 hmResult =  pricingDAO.getAllPriceRunResults(df.parse(frm.getTxtStartDate()),df.parse(frm.getTxtEndDate()),filtCust, filtPriceRunId,filtSalesRep,filtSalesManager, filtEsiid,((pageCount-1)*maxItems),maxItems);
		 prLst = (List)hmResult.get("Records");
		 totalCount = Integer.parseInt(String.valueOf(hmResult.get("TotalRecordCount")));
		pageContext.setAttribute("prLst",prLst);
		
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
			browserHt = 257;
		else
			browserHt = 237;
			
		/* if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
			browserHt -= 400;
		else
			browserHt -= 380; */
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/script/common.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ProspectiveCustomerDAO.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
</head>
<script>

var managerName = "";

function callDecide(runStatus, custRefId, priceRunRefNo)
{
	if(runStatus)
		callDashboard(custRefId, priceRunRefNo);
	else
		callErrorPage(custRefId,priceRunRefNo);
}

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

function callSearch()
{
	temp=document.forms[0];
	temp.formActions.value='search';
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
	temp.page.value  = page;
	temp.formActions.value='search';
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
	temp.formActions.value='search';
	temp.page.value  = page;
	temp.submit();
}

function callClearSearch()
{
try{
	temp=document.forms[0];
	temp.txtCustName.value="";
	temp.txtEsiid.value="";
	temp.txtSalesRep.value     = "";
	temp.txtSalesManager.value = "";
	temp.txtPrcRunId.value="";
	temp.cmbSalesRep.selectedIndex =0;
	temp.cmbPriceRunId.selectedIndex =0;
	temp.cmbSalesManager.selectedIndex =0;
	temp.cmbCustName.selectedIndex = 0;
	temp.cmbEsiId.selectedIndex    = 0;
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
	temp.txtPrcRunId.value = temp.txtPrcRunId.value;
	temp.txtCustName.value = temp.txtCustName.value;
	temp.txtEsiid.value =  '<%=frm.getTxtEsiid()%>';;
	temp.txtSalesRep.value = temp.txtSalesRep.value;
	temp.txtSalesManager.value = temp.txtSalesManager.value;
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

function deleteRunResult()
{
		temp=document.forms[0];
		var obj = document.getElementById('idRunRsltData');
		var priceRunIds="";
		var roleName = "";
		var count=0;
		for(var i=1;i<obj.children[0].children.length;i++)
		{
			if(i == 1)
			{
				if(obj.children[0].children[i].children[0].children[0].checked)
				{
					priceRunIds += obj.children[0].children[i].children[0].children[0].value+",";
					count++;
				}
			}
			else
			{
				if(obj.children[0].children[i].children[0].children[0].checked)
				{
					priceRunIds += obj.children[0].children[i].children[0].children[0].value+",";
					count++;
				}
			}
		}
		priceRunIds = priceRunIds.substring(0,priceRunIds.length-1);
		if(count>0)
		{
			if(!confirm("Warning!\nChosen item(s) will be deleted."))
			{
				return;
			}
			else
			{	
				temp.action='<%=request.getContextPath()%>/runresult.do?priceRunId='+priceRunIds+'&formActoin=delete';
				temp.formActions.value="delete";
				temp.submit();
			}
		}
		else
		{
			alert("Please select atleast one Price Run");
		}
}

function checkAll(objChkAll,chkobj)
{
	var objChk = document.getElementsByName(chkobj);
	if(objChkAll.checked == true)
	{
		if(objChk.length==undefined)
		{
			objChk.checked =true;
		}
		else
		{
			for(i=0;i<objChk.length;i++)
			{
				objChk[i].checked=true;
			}
		}
	}
	else
	{
		if(objChk.length==undefined)
		{
			objChk.checked =false;
		}
		else
		{
			for(i=0;i<objChk.length;i++)
			{
				objChk[i].checked=false;
			}
		}
	}
}

function unCheckAll(chkCpe, chkAllCpe)
{
	var count =0;	
	var objCheckAll = document.getElementsByName(chkCpe);	
	if(!(objCheckAll == undefined))
	{
		for(i=0;i<objCheckAll.length;i++)
		{			
			if(objCheckAll[i].checked == false)
				count++;	
		}		
		
		if(count > 0)
			document.getElementsByName(chkAllCpe)[0].checked = false;	
		else
			document.getElementsByName(chkAllCpe)[0].checked = true;	
	}	
}

function constructToolTip(custName, dba, id)
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
<html:form action="runresult" focus="txtCustName">
<html:hidden property="formActions"/>
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
         	<td class='error'><html:errors/></td>
        </tr>
	  </table>
   	  <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title">Run Results</td>
          <td class="page_title"><table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
            <tr>
            <%if(runElgible){%>
              <td><a href="<%=request.getContextPath()%>/jsp/pricerun/run.jsp">Run</a> | <a href="#" onclick="deleteRunResult()">Delete&nbsp;&nbsp;</a></td>
              <%}
              else{%>
			  <td> <a href="#" onclick="deleteRunResult()">Delete&nbsp;&nbsp;</a></td>
			  <%}%>
            </tr>
          </table></td>
        </tr> 
      </table> 
      <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
        <tr> 
          <td width="130" class="search">Customer Name</td> 
          <td width="1" class="search">:</td> 
          <td width="270" class="search">
			 <html:text property="txtCustName" styleClass="textbox" size="15" maxlength="30" onkeypress="return checkEnter(event)" />
             <html:select property="cmbCustName" onchange='document.forms[0].Submit2.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              </html:select>
		  </td> 
		  <td width="130" class="search">Customer&nbsp;Id&nbsp;/&nbsp;ESIID</td> 
          <td width="1" class="search">:</td> 
          <td width="280" class="search">
			 <html:text property="txtEsiid"  styleClass="textbox" size="15" onkeypress="return checkEnter(event)" />
	  		 <html:select property="cmbEsiId" onchange='document.forms[0].Submit2.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
	         </html:select>
		  </td> 
          <td width="100" class="search">Sales Rep</td> 
          <td width="1" class="search">:</td> 
          <td width="290" class="search"><html:text property="txtSalesRep" styleClass="textbox" size="15" maxlength="20" onkeypress="return checkEnter(event)" />
             <html:select property="cmbSalesRep" onchange='document.forms[0].Submit2.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              </html:select>   </td> 
        </tr> 
        <tr> 
          <td width="130" class="search">Sales Manager </td> 
          <td width="1" class="search">:</td> 
          <td width="270" class="search"><html:text property="txtSalesManager" styleClass="textbox" size="15" maxlength="20" />
             <html:select property="cmbSalesManager" onchange='document.forms[0].Submit2.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              </html:select></td> 
          <td width="100" class="search">Price Run ID</td> 
          <td width="1" class="search">:</td> 
          <td width="280" class="search">
			  <html:text property="txtPrcRunId" styleClass="textbox" size="15" maxlength="20" onkeypress="return checkEnter(event)" />
			  <html:select property="cmbPriceRunId" onchange='document.forms[0].Submit2.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
	          </html:select> 
	          </td>
          <td width="100" class="search"> Run Date</td> 
          <td width="1" class="search">:</td>  
          <td width="400" class="search">
			<html:text property="txtStartDate" styleClass="textbox" readonly="true" size="10" maxlength="10" onkeypress="return checkEnter(event)" /> 
            <a href="#" onClick="showCalendarControl(document.getElementById('txtStartDate'),'fully')" onblur='document.forms[0].Submit2.focus();'><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> 
            - <html:text property="txtEndDate" styleClass="textbox" readonly="true" size="10" maxlength="10" /> 
            <a href="#" onClick="showCalendarControl(document.getElementById('txtEndDate'),'fully')" onblur='document.forms[0].Submit2.focus();'><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> 
          
            <input name="Submit2" type="submit" class="button_sub_internal" value="Go!" onclick="callSearch();">
            <input name="Submit22" type="button" class="button_sub_internal" value="Clear" onclick="callClearSearch();"> </td> 
      </table> 
      <br>
      <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" id='idRunRsltData'> 
        <tr class='staticHeader'> 
          <td width="3%" class="cmbheader" align='center'><input type='checkbox' name='chkAll' value='functionChkAll' onclick="checkAll(this,'chkData')"/></td> 
          <td width="14%" class="tblheader" align='center'>Price Run ID</td> 
          <td width="7%" class="tblheader" align='center'>Customer&nbsp;Id</td>          
          <td width="17%" class="tblheader" align='center'>Customer Name</td> 
          <td width="13%" class="tblheader" align='center'>Product(s)</td> 
          <td width="7%" class="tblheader" align='center'>Term</td> 
          <td width=8%" class="tblheader" align='center'>Run Status</td>
          <td width="8%" class="tblheader" align='center'>Run Date </td> 
          <td class="tblheader" align='center'>Sales Rep</td>
          <td width="11%" class="tblheader" align='center'>Sales Manager</td>
        </tr>
        <% 
          if( prLst.size() > 0 )
          {
        %>
        <logic:iterate id="custdetails" name="prLst" indexId="s">
        <%
			String strCusName = "";
			int val = s.intValue();
			str = ((PriceRunCustomerVO)prLst.get(val)).getProspectiveCustomer().getCustomerName();
			String dba = ((PriceRunCustomerVO)prLst.get(val)).getProspectiveCustomer().getCustomerDBA()==null?"":((PriceRunCustomerVO)prLst.get(val)).getProspectiveCustomer().getCustomerDBA();
			int prsCustId = ((PriceRunCustomerVO)prLst.get(val)).getPriceRunCustomerRefId();
          	List lstPreferenceTerms = (List)objPreferenceTermsDAO.getAllPreferenceTerms(prsCustId);
          	List ltspreferenceproducts = (List)objPreferenceProductsDAO.getAllPreferenceProducts(prsCustId);
          	pageContext.setAttribute("ltspreferenceproducts", ltspreferenceproducts);
          	pageContext.setAttribute("lstPreferenceTerms", lstPreferenceTerms);
        	strCusName = str;
        	str = str.replaceAll("'","\\\\'");
            str = str.replaceAll("\"","");
            dba = dba.replaceAll("'","\\\\'");
            dba = dba.replaceAll("\"","");

        	if(strCusName.length()>22)
			{
				strCusName = strCusName.substring(0,20)+"...";
			}
			boolean runStat = ((PriceRunCustomerVO)prLst.get(val)).isRunStatus();
		%>
		<tr onmouseover="this.style.cursor='hand'; this.style.backgroundColor='<%=highlightColor%>';this.style.padding='<%=padding%>'" onmouseout="this.style.backgroundColor='';"> 
       	<td class="tbldata_chk" align='center'><input type='checkbox' name='chkData' value='<bean:write name="custdetails" property="priceRunCustomerRefId" />' onclick="unCheckAll('chkData','chkAll')" /></td> 
       	<%  if(((PriceRunCustomerVO)prLst.get(val)).getPriceRunRef().getRunType().equalsIgnoreCase("m"))
       		{
       	%>
		<td class="tbldata" style="color:#0000FF" height="30" align='right' onclick="callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')">&nbsp;<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" /></td> 
		<%
    		}
    		else
          	{          	
	   	%>
    	<td class="tbldata_auto" height="30" align='right' onclick="callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')">&nbsp;<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" /></td> 
       	<%
       		}
       	%>
		<td class="tbldata" align='right' onclick="callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')">&nbsp;<bean:write name="custdetails" property="prospectiveCustomer.customerId" ignore="true"/></td>
      	<%
       		if(str.length()>25)
			{
       	%>
    	<td class = 'tbldata' onmouseover="constructToolTip('<%=str%>', '<%=dba%>',<bean:write name="custdetails" property="prospectiveCustomer.prospectiveCustomerId"/>)" onclick="callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')"><%=strCusName%>&nbsp;</td>
       	<%
       		}
       		else
       		{
      	%>
		<td class = 'tbldata' onmouseover="constructToolTip('<%=str%>', '<%=dba%>', <bean:write name = 'custdetails' property = 'prospectiveCustomer.prospectiveCustomerId'/>)" onclick = "callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')"><bean:write name = 'custdetails' property = 'prospectiveCustomer.customerName'/>&nbsp;</td>
		<%
			}
    		String strProd = "";
        	String subStrProd = "";
        	for(int k=0;k<ltspreferenceproducts.size();k++)
        	{
        		objPriceRunCustomerProductsVO = (PriceRunCustomerProductsVO)ltspreferenceproducts.get(k);
          		if(strProd.equals(""))
          			strProd = String.valueOf(objPriceRunCustomerProductsVO.getProduct().getProductName()).trim();
          		else
          			strProd = strProd+", "+ String.valueOf(objPriceRunCustomerProductsVO.getProduct().getProductName()).trim();
          	}
          	subStrProd = strProd;
          	if(subStrProd.length()>17)
			{
				subStrProd = subStrProd.substring(0,15)+"...";
       	%>
		<td class="tbldata" title='<%=strProd%>' onclick="callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')"><%=subStrProd%>&nbsp;</td>
	 	<%
	  		}
	  		else
	  		{
	  	%>
		<td class="tbldata" onclick="callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')"><%=strProd%>&nbsp;</td>
		<%
			}
			
	          	String strTerm    = "";
	          	String subStrTerm = "";
	          	for(int k=0;k<lstPreferenceTerms.size();k++)
	          	{
	          		objPriceRunCustomerTermsVO = (PriceRunCustomerTermsVO)lstPreferenceTerms.get(k);
	          		if(strTerm.equals(""))
	          			strTerm = String.valueOf(objPriceRunCustomerTermsVO.getTerm()).trim();
	          		else
	          			strTerm = strTerm+", "+ String.valueOf(objPriceRunCustomerTermsVO.getTerm()).trim();
	          	}
          	
	          	subStrTerm = strTerm;
				if(subStrTerm.length()>8)
				{
					subStrTerm = subStrTerm.substring(0,6)+"...";
          	%>
				<td class="tbldata" title='<%=strTerm%>' align='right' onclick="callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')"><%=subStrTerm%>&nbsp;</td>
		 	<%
		  		}
		  		else
		  		{
		  	%>
				<td class="tbldata" align='right' onclick="callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')"><%=strTerm%>&nbsp;</td>
			<%
				}
			%>

			<td class="tbldata" align="center" onclick="callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')">
			<% 
				if(((PriceRunCustomerVO)prLst.get(val)).isRunStatus()) 
            	{
            %>
                <img border='0' width='11' height='11' src='<%=request.getContextPath()%>/images/success.gif' alt='Success' />
            <%
            	}
            	else
            	{
            %>                            
                <img border='0' width='11' height='11' src='<%=request.getContextPath()%>/images/failure.gif' alt='<%=((PriceRunCustomerVO)prLst.get(val)).getReason()%>' />
            <%
            	}
			%>
			</td>

          	<%-- <td class="tbldata"><logic:equal value="true" name="custdetails" property="runStatus">Success</logic:equal><logic:equal value="false" name="custdetails" property="runStatus">Failure</logic:equal>&nbsp;</td>  --%>
          	<td class="tbldata" align='right' onclick="callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')">&nbsp;<bean:write name="custdetails" property="priceRunRef.priceRunTime" format="MM-dd-yyyy" ignore="true"/></td>
          	<%
          			int valsaleRep = s.intValue();
		     		objTeamDetails = objProspectiveCustomerDAO.getTeam(((PriceRunCustomerVO)prLst.get(valsaleRep)).getProspectiveCustomer().getProspectiveCustomerId());
					str=(objTeamDetails.getSalesRep().getFirstName()==null?"":objTeamDetails.getSalesRep().getFirstName()+" ");
					String  strLastName = "";
					strLastName = (objTeamDetails.getSalesRep().getLastName()==null?"":objTeamDetails.getSalesRep().getLastName());
					str=str + strLastName;
				if(str.length()>17)
				{
				%>
					<td class="tbldata" title='<%=str%>' onclick="callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')">&nbsp;<%=str.substring(0,15)+"..."%></td>
				<%
				}
				else
				{
			%>
          			<td class="tbldata" onclick="callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')">&nbsp;<%=str%></td>
          	<%
				}
						String strFrstName = objTeamDetails.getSalesManager().getFirstName();
						strLastName =  objTeamDetails.getSalesManager().getLastName();
						str = strFrstName+strLastName;
						if(str.length()>15)
						{
							str = str.substring(0,13)+"...";
							%>
							<td class="tbldata" id='manager<%=s.intValue()%>' onclick="callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')" title="<%=strFrstName%>&nbsp;<%=strLastName%>"><%=str%>&nbsp;</td>
							<%
						}
						else
						{
			%>
			<td class="tbldata" id='manager<%=s.intValue()%>' onclick="callDecide(<%=runStat%>,'<bean:write name="custdetails" property="priceRunCustomerRefId" />','<bean:write name="custdetails" property="priceRunRef.priceRunRefNo" />')">&nbsp;
              <%=strFrstName%>&nbsp;<%=strLastName%>
			</td>
					 <% } %>

        </tr>
         </logic:iterate>
		<%
		  }
  		  else
		  {
	    %>
		 	<tr>
		 		<td colspan='10'>
				 	<jsp:include page="../noRecordsFound.jsp"/>
				</td>
			</tr>
		<%
		  }
		%>
      </table>
      </div>
       <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="sort">
          <tr>
            <td width="120">Page  <%=pageCount%> of <%=totalPages%></td>
            <td width="220">Items  <%=totalCount>0?(pageCount*maxItems)-maxItems+1:0%> - <%=((maxItems*pageCount)>totalCount)?totalCount:(maxItems*pageCount)%> of <%=totalCount%></td>
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
				<td><img border='0' width='11' height='11' src='<%=request.getContextPath()%>/images/manual_run.gif' alt='Price Run Id' /></td><td style='color:#333333'>&nbsp;Manual Run</td>
				<td>&nbsp;</td>
				<td><img border='0' width='11' height='11' src='<%=request.getContextPath()%>/images/automatic_run.gif' alt='Price Run Id' /></td><td style='color:#333333'>&nbsp;Automatic Run</td>
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
