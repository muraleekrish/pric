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
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savant.pricing.dao.RateCodesDAO"%>
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.common.FilterHandler"%>
<%@ page import="com.savant.pricing.transferobjects.TDSPRateCodesDetails"%>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.global.tdsp.RateCodeForm" />
<jsp:setProperty name="frm" property="*" />

<%
	int browserHt = 0;	
	RateCodesDAO objRateCodesDAO = new RateCodesDAO();
	TDSPRateCodesDetails objTDSPRateCodesDetails = new TDSPRateCodesDetails();
	int totalRecords=1;
	int pageCount = 1;
	int maxItems = 0;
	int startPosition = 0;
	int totalPages = 0;
	boolean order = true;
	String str="";
	
	HashMap hmResult = new HashMap();
	List lstRateCodes = null;
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
		if(request.getParameter("formActions") != null)
		frm.setFormActions(request.getParameter("formActions"));
		Filter fil[]=null;
		Filter rateCodefilter  = null;
		Filter rateClassfilter  = null;
		if(frm.getFormActions().equals(""))
		{
			fil = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		if(frm.getFormActions().equalsIgnoreCase("search")||(frm.getFormActions().equalsIgnoreCase("navigation"))||(frm.getFormActions().equalsIgnoreCase("sorting")))
  		{ 	
    		Vector filter = new Vector();
			if (!frm.getTxtRateCode().trim().equalsIgnoreCase(""))
			{
		  		filter = new Vector();
		  		filter = objFilterHandler.setFormDetails("rateCode",frm.getTxtRateCode().trim(),frm.getSearchRateCode(),filter);
		  		fil = new Filter[filter.size()];
		 		filter.copyInto(fil);
		  		rateCodefilter = fil[0];
			}
			if (!frm.getTxtRateClassName().trim().equalsIgnoreCase(""))
			{
		  		filter = new Vector();
		  		filter = objFilterHandler.setFormDetails("rateClass",frm.getTxtRateClassName().trim(),frm.getSearchRateClsName(),filter);
		  		fil = new Filter[filter.size()];
		 		filter.copyInto(fil);
		  		rateClassfilter = fil[0];
			}
		}
		maxItems = Integer.parseInt(frm.getMaxItems());
    	pageCount = Integer.parseInt(frm.getPage());
		startPosition = Integer.parseInt(frm.getStartPosition());
		hmResult = objRateCodesDAO.getAllRateCodes(rateCodefilter,rateClassfilter,0,"rateCode",order,((pageCount-1)*maxItems),maxItems);
		totalRecords = Integer.parseInt(String.valueOf(hmResult.get("TotalRecordCount")));
		if(hmResult!=null)
			 lstRateCodes =(List)hmResult.get("Records");
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
		
		pageContext.setAttribute("lstRateCodes",lstRateCodes);
	}
	catch(Exception e)
	{	
		e.printStackTrace();
	}
	String userName = (String)session.getAttribute("userName");
	if(userName==null)
	getServletConfig().getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
	
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 213;
	else
		browserHt = 193;
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/common.js"></script>
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
	temp.page.value  = page;
	temp.formActions.value="search";
	temp.submit();
}

function changePageGoto()
{
	temp = document.forms[0];	
	temp.formActions.value="search";
	temp.submit();
}


function loadDefault()
{	
	temp=document.forms[0];
	var page = '<%=request.getParameter("page")==null?frm.getPage():request.getParameter("page")%>';
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
	temp.txtRateCode.value = temp.txtRateCode.value;
	temp.txtRateClassName.value = temp.txtRateClassName.value;
}
function search()
{
	temp=document.forms[0];
	temp.formActions.value = "search";
	temp.page.value = 1;
	temp.submit();
}
function fnClear()
{
	temp = document.forms[0];
	temp.txtRateCode.value = '';
	temp.searchRateCode.value = '0';
	temp.txtRateClassName.value = '';
	temp.searchRateClsName.value = '0';
}

function callSort()
{
	temp=document.forms[0];
	temp.formActions.value="search";
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
     if(e.keyCode == 13)
     { 
       search();
       return false;
     }
     else
     {
       return true; 
     }
}
</script>
<body onload="loadDefault()"> 
<html:form action="rateCodeList" focus='txtRateCode'>
<html:hidden property="formActions"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>

<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top">
     <jsp:include page="../../menu.jsp"/>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="100%" width="250" class="page_title">Rate Codes</td> 
            <%-- <td class="page_title">
            	<table  border="0" align="right" cellpadding="0" cellspacing="0"> 
                	<tr> 
                  	<td class="topnav"><a href="gRateCodeAdd.htm">Add</a> | <a href="gRateCodeEdit.htm">Modify</a> | <a href="#">Make InValid</a> </td> 
                	</tr> 
              	</table>
            </td> --%>
          </tr> 
      </table> 
        <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="tblSearch"> 
          <tr> 
            <td width="69" class="search">Rate Code</td> 
            <td width="9" class="search">:</td> 
            <td width="300" class="search">
				<html:text property="txtRateCode" styleClass="textbox" size="15" onkeypress="return checkEnter(event)"/>
            	<html:select property="searchRateCode" onchange='document.forms[0].Submit.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              	</html:select></td>
            <td width="100" class="search">Rate Class Name</td>
            <td width="12" class="search">:</td>
            <td class="search">
				<html:text property="txtRateClassName" styleClass="textbox" size="15" onkeypress="return checkEnter(event)"/>
               	<html:select property="searchRateClsName" onchange='document.forms[0].Submit.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
               	</html:select>            
	            <html:button property="Submit" value="Go!" styleClass="button_sub_internal" onclick="search();" />
				<html:button property="Clear" value="Clear" styleClass="button_sub_internal" onclick = "fnClear();" />
			</td>
		  </tr>
        </table> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
          </tr> 
        </table> 
        <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="tblTdspList"> 
          <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblTdspList"> 
            <tr class='staticHeader'> 
              <td width="22" class="cmbheader">&nbsp;</td>
              <%if(order)
              {%>
              <td width="100" class="tblheader" id='img_sort' onClick="callSort();" style="cursor:hand" title='Sort by Rate Code in Ascending'>Rate Code <img src="<%=request.getContextPath()%>/images/sort.gif" width="7" height="8"></td> 
              <%}else{%>
              <td width="100" class="tblheader" id='img_sort' onClick="callSort();" style="cursor:hand" title='Sort by Rate Code in Decending'>Rate Code <img src="<%=request.getContextPath()%>/images/sort_up.gif" width="7" height="8"></td> 
              <%}%>
              <td class="tblheader">Rate Class Name </td> 
              <td width="594" class="tblheader">Description</td> 
            </tr>
            <% 
          		if( lstRateCodes.size() > 0 )
		        {
        	%>
			<logic:iterate id="rateCodes"  name="lstRateCodes" indexId="i">
          <tr>
            <td width="21" class="tbldata">
			<input type="radio" name="checkbox" value='<bean:write name="rateCodes" property="rateCode"/>'>
			</td>
             <td class="tbldata"><bean:write name="rateCodes" property="rateCode" /></td>
             <%
				int val = i.intValue();
				str=((TDSPRateCodesDetails)lstRateCodes.get(val)).getRateClass();
				if(str.length()>35)
				{
					str = str.substring(0,33)+"...";
					%>
					<td class="tbldata" title='<bean:write name="rateCodes" property="rateClass" />'><%=str%>&nbsp;</td>
				<%
				}
				else
				{
			%>
             <td class="tbldata"><bean:write name="rateCodes" property="rateClass" />&nbsp;</td>
             <%
				}
				
				str=((TDSPRateCodesDetails)lstRateCodes.get(val)).getDescription()==null?"":((TDSPRateCodesDetails)lstRateCodes.get(val)).getDescription();
				if(str.length()>80)
				{
					str = str.substring(0,78)+"...";
					%>
					<td width="594" class="tbldata" title='<bean:write name="rateCodes" property="description" />'><%=str%>&nbsp;</td> 
				<%
				}
				else
				{
			%>
           <td width="594" class="tbldata"><bean:write name="rateCodes" property="description" />&nbsp;</td> 
           <%
           		}
           	%>
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
							    	if(totalRecords%10!=0)
							      	{
						      %>
						      <a href="#" style="color:blue" onclick="pageIncrement()">Next <img src='<%=request.getContextPath()%>/images/next.gif' align="bottom" alt="Next" border="0"></a> 
						      <%
							    	}
							   }
							   %>
			</td>
		</tr>
      </table>     
      <!-- navigator End-->
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
