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
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="com.savant.pricing.matrixpricing.dao.MMPriceRunStatusDAO"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.matrix.XpressPricingListForm" /><jsp:setProperty name="frm" property="*" />
<%	
	Properties props;
	props = new Properties();
	InputStream is = this.getClass().getClassLoader().getResourceAsStream("ApplicationResources.properties");
	props.load(is);
	String highlightColor = props.getProperty("list.highlight.color");
	String padding = props.getProperty("list.highlight.padding");
	MMPriceRunStatusDAO objMMPriceRunStatusDAO = new MMPriceRunStatusDAO();
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd-yyyy");

	int totalRecords=1;
	int pageCount = 0;
	int maxItems = 0;
	int startPosition = 0;
	int totalPages = 0;
	boolean order = true;
	HashMap hmResult = new HashMap();
	List listResult = null;
	try
	{
		Date fromDate = null;
		Date toDate = null;
		Date offerDate = null;
		Date expDate = null;
    	maxItems = Integer.parseInt(frm.getMaxItems());
  	  if(frm.getFormAction().equalsIgnoreCase("search")||(frm.getFormAction().equalsIgnoreCase("navigation"))||(frm.getFormAction().equalsIgnoreCase("sorting")))
  		{  		   	
  		   	if(frm.getFromDate().length()>1)
    		   	fromDate = sdf.parse(frm.getFromDate());
		   	if(frm.getToDate().length()>1)
    		   	toDate = sdf.parse(frm.getToDate());
		   	if(frm.getExpDate().length()>1)
    		   	expDate = sdf.parse(frm.getExpDate());
		   	if(frm.getOfferDate().length()>1)
    		   	offerDate = sdf.parse(frm.getOfferDate());
	  	}
	  	maxItems = Integer.parseInt(frm.getMaxItems());
		pageCount = Integer.parseInt(frm.getPage());
	    hmResult = objMMPriceRunStatusDAO.getAllRunResults(fromDate,frm.getSearchRunDate(),toDate,frm.getSearchStatus(),expDate,offerDate,((pageCount-1)*maxItems),maxItems);
		totalRecords = Integer.parseInt(String.valueOf(hmResult.get("TotalRecordCount")));
		listResult = (List)hmResult.get("Records");		
		
		if(totalRecords%maxItems == 0)
		{
			totalPages = (totalRecords/maxItems);
		}
		else
		{
			totalPages = (totalRecords/maxItems) + 1;
		}
			  	
	  	if(totalPages==0)
			totalPages = 1;

		pageContext.setAttribute("listResult",listResult);
	}
	catch(Exception e)	
	{
		e.printStackTrace();
	}	
	
	int browserHt = 0;
	 if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 235;
	else
		browserHt = 215;
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

function changePageGoto()
{
	temp = document.forms[0];
	temp.formAction.value="search";
	temp.submit();
}
function pageDecrement()
{
 	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) - 1;
	temp.page.value  = page;
	temp.formAction.value="search";
	temp.submit();
}

function changePage()
{
	temp=document.forms[0];
	temp.formAction.value="navigation";
	temp.page.value  =0;
	temp.submit();
}

function pageIncrement()
{
 	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) + 1;
	temp.page.value  = page;
	temp.formAction.value="search";
	temp.submit();
}

function search()
{
	temp=document.forms[0];
	temp.formAction.value="search";
	temp.page.value = 1;
	temp.submit();
}

function clearSearch()
{
	try
	{
		temp=document.forms[0];
		temp.searchRunDate.selectedIndex = "";
		temp.fromDate.value="";
		temp.toDate.value="";
		temp.searchStatus.selectedIndex = "";
		temp.offerDate.value="";
		temp.expDate.value="";
	}
	catch(err)
	{
		alert(err.description);
	}
}
function callView(runId)
{
	temp = document.forms[0];
	temp.action = "<%=request.getContextPath()%>/frmXPressPricingView.do";
	temp.priceRunId.value = runId;
	temp.submit();

}
function runCust()
{
    temp=document.forms[0];
	temp.formAction.value="run";
	temp.submit();
}

function deleteMMRunResult()
{
  try
  {
	  var temp = document.forms[0];
	  var refId;
	  var radioObj = document.getElementsByName("radioGroup");
	  if(radioObj.length>0) 
	  {
		  for (var i=0; i<radioObj.length; i++) 
		  {
		  	if(radioObj[i].checked)
		    	refId = radioObj[i].value;
		  }
		  
		  if((refId==undefined)||(refId==''))
		  {
			  alert('Select Price Run to delete from list.');
		  }
		  else
		  {
		    if(!confirm("Warning!\nChosen item(s) will be deleted."))
			{
				return;
			}
			else
			{	
			    temp.priceRunId.value = refId;
			    temp.formAction.value = "delete";
			    temp.submit();
			}
		  }
	  } 
	  else
	  {
	   	alert('No Price Run in list.');
	  }
	}
	catch(err)
	{
		alert(err.description);
	}
}

function loadDefault()
{
	temp = document.forms[0];
	var formAction = '<%=frm.getFormAction()%>';
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

function callBetween()
{
	temp = document.forms[0];
	var searchBtwn = temp.searchRunDate.value;
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

function showload(mess)
 {
 	if(mess=='yes')
 		document.getElementById('loadimage').style.display = 'block';
	 else
 		document.getElementById('loadimage').style.display = 'none';
}

</script>
<div id ="loadimage" style="overflow:auto; position:absolute; top:88px; left:700px; display:none; " >
<img src="<%=request.getContextPath()%>/images/preloader_pricing.gif"  align="middle">
</div>
<body onload='loadDefault()'> 
<html:form action="/frmXpressPricingList">
<html:hidden property="formAction"/> 
<input type="hidden" name="priceRunId"/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> <td  valign="top"> 
    <jsp:include page="../menu.jsp"/> 
    <!-- Menu End --> 
    <validator:errorsExist>
		<span class='error'><html:errors/></span>
	</validator:errorsExist>
	<logic:messagesPresent message="true" >
		<span class='message'>
			<html:messages id="messageid" message="true">
				<bean:write name="messageid" />
			</html:messages>
		</span>
	</logic:messagesPresent>
    <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
      <tr> 
        <td width="250" class="page_title">Xpress Pricing</td> 
        <td class="page_title"><table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav"> 
            <tr> 
              <td><a href="#" onclick="runCust();showload('yes');">Run</a> | <a href="#" onclick="deleteMMRunResult()">Delete</a> | <a href="<%=request.getContextPath()%>/jsp/matrix/customerFiles.jsp">Customer Files</a>&nbsp;&nbsp;</td> 
            </tr> 
          </table></td> 
      </tr> 
    </table> 
    <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
      <tr> 
        <td width="99" class="search">Run Date</td> 
        <td width="4" class="search">:</td> 
        <td width="170" class="search"> <html:select property="searchRunDate" onchange="callBetween();">
                                        <html:option value="on">On</html:option>
                                        <html:option value="before">Before</html:option>
                                        <html:option value="after">After</html:option>
                                        <html:option value="between">Between</html:option> </html:select>
        <html:text property="fromDate" styleClass="textbox" styleId="txtDateFrom1" size="10" maxlength="10" onkeypress="return false" /> <a href="#" onClick="showCalendarControl(document.getElementById('txtDateFrom1'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" name="imgDateFrom1" width="16" height="16" border="0" align="absmiddle" id="imgDateFrom1"></a>
        </a></td> 
		<td width="115" class="search" id='Dummy' style="display:block">&nbsp;</td>
       <td width="115" class="search" id='toRunDate' style="display:none">
       		<table border="0" cellpadding="0" cellspacing="0" ><tr><td> - <html:text property="toDate" styleClass="textbox" styleId="txtDateTo" size="10" maxlength="10" onkeypress="return false" /> <a href="#" onClick="showCalendarControl(document.getElementById('txtDateTo'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" name="imgDateTo" width="16" height="16" border="0" align="absmiddle" id="imgDateTo"></a></td></tr></table>
       </td> 
       <td width="109" class="search">Run Status</td> 
        <td width="9" class="search">:</td> 
        <td class="search"> <html:select property="searchStatus" onchange=""> <html:option value="">Select one</html:option> <html:option value="true">Success</html:option> <html:option value="false">Failure</html:option> </html:select></td> 
      <tr> 
        <td class="search">Offer Date</td> 
        <td class="search">:</td> 
        <td class="search" colspan="2"><html:text property="offerDate" styleClass="textbox" readonly="true" styleId="txtDateFrom2" size="10" maxlength="10" onkeypress="return false;" /> <a href="#" onClick="showCalendarControl(document.getElementById('txtDateFrom2'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" name="imgDateFrom2" width="16" height="16" border="0" align="absmiddle" id="imgDateFrom2"></a></td> 
        <td class="search">Expiry Date</td> 
        <td class="search">:</td> 
        <td class="search"> <html:text property="expDate" styleClass="textbox" readonly="true" styleId="txtDateFrom3" size="10" maxlength="10" onkeypress="return false;" /> <a href="#" onClick="showCalendarControl(document.getElementById('txtDateFrom3'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" name="imgDateFrom3" width="16" height="16" border="0" align="absmiddle" id="imgDateFrom3"></a> &nbsp;&nbsp;<html:button property="Button" value="Go!" styleClass="button_sub_internal" onclick="search()" /> <html:button property="Button" value="Clear" styleClass="button_sub_internal" onclick="clearSearch()"/></td> 
      </tr> 
    </table> 
    <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="prospectiveCustomerLst"> 
        <tr class='staticHeader'> 
          <td width="22" class="cmbheader">&nbsp;</td> 
          <td width="250" class="tblheader" align='center'>Run Date</td> 
          <td width="200" class="tblheader" align='center'>Run Status</td> 
          <td width="200" class="tblheader" align='center'>Offer Date</td> 
          <td class="tblheader" align='center'>Expiry Date</td> 
        </tr> 
        <% 
           if( listResult.size() > 0 )
           {
          %> 
		<logic:iterate id="result" name="listResult" > 
        <tr onmouseover="this.style.cursor='hand'; this.style.backgroundColor='<%=highlightColor%>';this.style.padding='<%=padding%>'" onmouseout="this.style.backgroundColor='';"> 
          <td width="22" class="tbldata"><input name="radioGroup" type="radio" value="<bean:write name="result" property="priceRunRefNo" ignore="true"/>"></td> 
          <td width="175" class="tbldata" onclick="callView('<bean:write name="result" property="priceRunRefNo" ignore="true"/>')"><bean:write name="result" property="priceRunRefNo" ignore="true"/></td> 
          <td width="125" class="tbldata" onclick="callView('<bean:write name="result" property="priceRunRefNo" ignore="true"/>')"><logic:equal value="true" name="result" property="status" >Success</logic:equal> <logic:notEqual value="true" name="result" property="status" >Failure</logic:notEqual></td> 
          <td width="150" class="tbldata" onclick="callView('<bean:write name="result" property="priceRunRefNo" ignore="true"/>')"><bean:write name="result" property="offerDate" ignore="true" format="MM-dd-yyyy hh:mm a"/></td> 
          <td class="tbldata" onclick="callView('<bean:write name="result" property="priceRunRefNo" ignore="true"/>')"><bean:write name="result" property="expiredate" ignore="true" format="MM-dd-yyyy hh:mm a"/></td> 
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
    <!-- navigator Start--> 
    <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="sort"> 
      <tr> 
        <td width="100">Page <%=pageCount%> of <%=totalPages%></td> 
        <td width="150">Items <%=totalRecords>0?(pageCount*maxItems)-maxItems+1:0%> - <%=((maxItems*pageCount)>totalRecords)?totalRecords:(maxItems*pageCount)%> of <%=totalRecords%></td> 
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
</html>
<%}%>
