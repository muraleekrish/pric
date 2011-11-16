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
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.dao.HolidayDAO"%>
<%@ page import="com.savant.pricing.common.FilterHandler"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pconfiguration.CalendarListForm" />
<jsp:setProperty name="frm" property="*" />

<%
	int browserHt = 0;
	Properties props;
	props = new Properties();
	InputStream is = this.getClass().getClassLoader().getResourceAsStream("ApplicationResources.properties");
	props.load(is);
	String highlightColor = props.getProperty("list.highlight.color");
	String padding = props.getProperty("list.highlight.padding");
	
	HolidayDAO objHolidayDAO = new HolidayDAO();
	int totalRecords=1;
	int pageCount = 1;
	int maxItems = 0;
	int startPosition = 0;
	int totalPages = 0;
	boolean order = true;

	HashMap hmResult = new HashMap();
	List lstHolidays = null;
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	FilterHandler objFilterHandler = new FilterHandler();
	try
	{
		if(frm.getSortOrder().equalsIgnoreCase("ascending"))
		{
			order = true;
		}
		else
		{
			order = false;
		}
		String sortfield = frm.getSortField();
		if(request.getParameter("formActions") != null)
		frm.setFormActions(request.getParameter("formActions"));
		Filter fil[]=null;
		Filter calendarfilter  = null;
		if(frm.getFormActions().equals(""))
		{
			fil = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		if(frm.getFormActions().equalsIgnoreCase("search")||(frm.getFormActions().equalsIgnoreCase("navigation"))||(frm.getFormActions().equalsIgnoreCase("sorting")))
  		{ 	
    		Vector filter = new Vector();
			if (!frm.getTxtReason().trim().equalsIgnoreCase(""))
			{
		  		filter = new Vector();
		  		filter = objFilterHandler.setFormDetails("reason",frm.getTxtReason(),frm.getSearchReason(),filter);
		  		fil = new Filter[filter.size()];
		 		filter.copyInto(fil);
		  		calendarfilter = fil[0];
			}
		}
		maxItems = Integer.parseInt(frm.getMaxItems());
    	pageCount = Integer.parseInt(frm.getPage());
		startPosition = Integer.parseInt(frm.getStartPosition());
		Date fromDate = null;
		Date toDate = null;
		if(!frm.getTxtStartDate().equals(""))
			fromDate = sdf.parse(frm.getTxtStartDate());
		if(!frm.getTxtEndDate().equals(""))
			toDate = sdf.parse(frm.getTxtEndDate());
			
		hmResult = objHolidayDAO.getAllHolidays(calendarfilter,fromDate,toDate,"date",order,((pageCount-1)*maxItems),maxItems);
		totalRecords = Integer.parseInt(String.valueOf(hmResult.get("TotalRecordCount")));
		if(hmResult!=null)
			 lstHolidays =(List)hmResult.get("Records");
		totalRecords = Integer.parseInt(hmResult.get("TotalRecordCount").toString());
		
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
	    if(frm.getFormActions().equalsIgnoreCase("navigation"))
		{
			 pageCount = Integer.parseInt(frm.getPage());
		}
		else if(frm.getFormActions().equalsIgnoreCase("navigationDown"))
		{
			maxItems = Integer.parseInt(frm.getMaxItems());
		}
		
		pageContext.setAttribute("lstHolidays",lstHolidays);
		
		if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
			browserHt = 215;
		else
			browserHt = 195;
	}
	catch(Exception e)
	{	
		e.printStackTrace();
	}
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" >
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/mm_menu.js"></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/script/common.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
</head>
<script>

function navigation()
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
	temp.formActions.value="navigation";
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
	temp.page.value  = page;
	temp.formActions.value="navigation";
	temp.submit();
}

function changePageGoto()
{
	temp = document.forms[0];	
	temp.formActions.value="search";
	temp.submit();
}

function editCalendar()
{
try{
	temp=document.forms[0];
	var obj = document.getElementById('holidayList');
	var hldDate="";
	var count=0;
	for(var i=0;i<obj.children[0].children.length;i++)
		{
				if(obj.children[0].children[i].children[0].children[0].checked)
				{
					hldDate = obj.children[0].children[i].children[0].children[0].value;
					count++;
				}
		}
	if(count>0)
	{
		temp.action='<%=request.getContextPath()%>/holidaysEdit.do?HolidayDate='+hldDate+'&formActoin=edit';
		temp.formActions.value="edit";
		temp.submit();
	}else
	{
		alert("Please select a Date to Modify");
	}
  }
  catch(err)
  {
	alert(err.description);
  }
}

function deleteCalendar()
{
try{
	temp=document.forms[0];
	var obj = document.getElementById('holidayList');
	var hldDate="";
	var count=0;
	for(var i=0;i<obj.children[0].children.length;i++)
		{
			if(obj.children[0].children[i].children[0].children[0].checked)
				{
					hldDate = obj.children[0].children[i].children[0].children[0].value;
					count++;
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
			temp.action='<%=request.getContextPath()%>/calendarList.do?HolidayDate='+hldDate+'&formActoin=delete';
			temp.formActions.value="delete";
			temp.submit();
		}
	}
	else
	{
		alert("Please select a Date to Delete");
	}
  }
  catch(err)
  {
	alert(err.description);
  }
}

function loadDefault()
{	
	temp=document.forms[0];
	var page = '<%=frm.getPage()%>';
	temp.page.value = page;	
	var maxItems = '<%=frm.getMaxItems()%>';
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
	temp.txtReason.value = temp.txtReason.value;
}

function search()
{
	temp=document.forms[0];
	temp.formActions.value = "search";
	temp.page.value=1;
	temp.submit();
}
function fnClear()
{
	temp = document.forms[0];
	temp.txtReason.value = '';
	temp.searchReason.value = '0';
	temp.txtStartDate.value = '';
	temp.txtEndDate.value = '';
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

function callView(hldDate)
{
	temp=document.forms[0];
	temp.action='<%=request.getContextPath()%>/jsp/pconfiguration/calendar/pccalendarview.jsp?hldDate='+hldDate;
	temp.submit();
}

</script>
<body onload="loadDefault();"> 
<html:form action="calendarList" focus='txtReason'>
<html:hidden property="formActions"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>

<script language="JavaScript1.2"></script> 
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
   <td  valign="top"> <jsp:include page="/jsp/menu.jsp"/>
	<table width="100%"  border="0" cellpadding="0" cellspacing="0">
      <tr>
		<td class="message">
		  <html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages>
		</td>
		</tr>
		<tr>
    	 <td class='error'><html:errors/></td>
		</tr>
     </table>
   		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	       <tr>
	          <td width="250" class="page_title">Holidays </td>
	          <td class="page_title"><table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
	            <tr>
	              <td><a href="<%=request.getContextPath()%>/jsp/pconfiguration/calendar/pccalendaradd.jsp">Add</a> | <a href="#" onclick="editCalendar()">Modify</a> | <a href="#" onclick="deleteCalendar()">Delete</a></td>
	            </tr>
	          </table></td>
	        </tr>
         </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="60" class="search">Reason</td>
            <td width="1" class="search">:</td>
            <td width="300" class="search"><html:text property="txtReason" styleClass="textbox" size="15" onfocus="true" onkeypress="return checkEnter(event)"/>
               <html:select property="searchReason">
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              </html:select> </td>
            <td width="50" class="search">Date</td>
            <td width="1" class="search">:</td>
            <td class="search"><html:text property="txtStartDate" styleClass="textbox"  size="10" maxlength="10" onkeypress="return false"/> 
            <a href="#" onClick="showCalendarControl(document.getElementById('txtStartDate'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> 
            - <html:text property="txtEndDate" styleClass="textbox"  size="10" maxlength="10" onkeypress="return false"/> 
            <a href="#" onClick="showCalendarControl(document.getElementById('txtEndDate'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> 
            
			<html:button property="btnSubmit" value="Go!" onclick="search();" styleClass="button_sub_internal"/>
			<html:button property="btnclear" onclick = "fnClear();" value="Clear" styleClass="button_sub_internal"/></td></tr>
        </table>
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
        <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class='staticHeader'>
            <td width="25" class="cmbheader">&nbsp;</td>
            <%if(order)
            {%>
            <td width="100" class="tblheader" id='img_sort' onClick="callSort();" align='center' style="cursor:hand" title='Sort by Date in Ascending'>Date <img src="<%=request.getContextPath()%>/images/sort.gif" width="7" height="8"></td>
            <%}else{%>
            <td width="100" class="tblheader" id='img_sort' onClick="callSort();" align='center' style="cursor:hand" title='Sort by Date in Decending'>Date <img src="<%=request.getContextPath()%>/images/sort_up.gif" width="7" height="8"></td>
            <%}%>
            <td class="tblheader">Reason</td>
          </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="holidayList">
        <% 
          if( lstHolidays.size() > 0 )
          {
        %>
		<logic:iterate id="holidays" name="lstHolidays">
          <tr onmouseover="this.style.cursor='hand'; this.style.backgroundColor='<%=highlightColor%>';this.style.padding='<%=padding%>'" onmouseout="this.style.backgroundColor='';"> 
          	<td width="19" class="tbldata"><input type="radio" name="checkbox" value='<bean:write name="holidays" property="date" format="MM-dd-yyyy" />'></td>
           	<td width="100" class="tbldata" align='right' onclick="callView('<bean:write name="holidays" property="date" format="MM-dd-yyyy" />')"><bean:write name="holidays" property="date" format="MM-dd-yyyy" /></td>
           	<td class="tbldata" onclick="callView('<bean:write name="holidays" property="date" format="MM-dd-yyyy" />')"><bean:write name="holidays" property="reason" />&nbsp;</td> 
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
            <td width="150">Items  <%=totalRecords>0?(pageCount*maxItems)-maxItems+1:0%> - <%=((maxItems*pageCount)>totalRecords)?totalRecords:(maxItems*pageCount)%> of <%=totalRecords%></td>
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
<%}%>
