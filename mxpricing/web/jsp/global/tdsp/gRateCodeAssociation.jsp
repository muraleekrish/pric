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
<%@ page import="com.savant.pricing.transferobjects.TDSPRateCodesDetails"%>
<%@ page import="com.savant.pricing.dao.TDSPDAO"%>
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.common.FilterHandler"%>
 
<jsp:useBean id="frm" class="com.savant.pricing.presentation.global.tdsp.RateCodeAssociationForm" />
<jsp:setProperty name="frm" property="*" />

<%
	int browserHt = 0;	
	String userName = (String)session.getAttribute("userName");
 	if(userName==null)
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
	RateCodesDAO objRateCodesDAO = new RateCodesDAO();
	TDSPRateCodesDetails objTDSPRateCodesDetails = new TDSPRateCodesDetails();
	TDSPDAO objTDSPDAO = new TDSPDAO();
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
			if (!frm.getTxtRateClass().trim().equalsIgnoreCase(""))
			{
		  		filter = new Vector();
		  		filter = objFilterHandler.setFormDetails("rateClass",frm.getTxtRateClass().trim(),frm.getSearchRateClass(),filter);
		  		fil = new Filter[filter.size()];
		 		filter.copyInto(fil);
		  		rateClassfilter = fil[0];
			}
		}
		maxItems = Integer.parseInt(frm.getMaxItems());
    	pageCount = Integer.parseInt(frm.getPage());
		startPosition = Integer.parseInt(frm.getStartPosition());
		hmResult = objRateCodesDAO.getAllRateCodes(rateCodefilter,rateClassfilter,Integer.parseInt(frm.getSearchTDSP()),"rateCode",order,((pageCount-1)*maxItems),maxItems);
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
		List selAllTDSP = objTDSPDAO.getAllTDSP();
		pageContext.setAttribute("selAllTDSP",selAllTDSP);
		
		if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
			browserHt = 213;
		else
			browserHt = 193;
	}
	catch(Exception e)
	{	
		e.printStackTrace();
	}
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
	temp.txtRateClass.value = temp.txtRateClass.value;
	temp.txtRateCode.value = temp.txtRateCode.value;
}
function fnClear()
{
	temp = document.forms[0];
	temp.txtRateCode.value = '';
	temp.searchRateCode.value = '0';
	temp.txtRateClass.value = '';
	temp.searchRateClass.value = '0';
	temp.searchTDSP.value = '0';
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
<html:form action="rateCodeAssociationList" focus='txtRateCode' method="post">
<html:hidden property="formActions"/>
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>

<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="../../menu.jsp"/>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="100%" class="page_title">Rate Codes Association</td> 
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
            <td width="200" class="search">
				<html:text property="txtRateCode" styleClass="textbox" size="15" onkeypress="return checkEnter(event)"/>
            	<html:select property="searchRateCode" onchange='document.forms[0].Submit.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              	</html:select></td>
            <td width="100" class="search">Rate Class Name</td>
            <td width="12" class="search">:</td>
            <td width="200" class="search">
				<html:text property="txtRateClass" styleClass="textbox" size="15" onkeypress="return checkEnter(event)"/>
               	<html:select property="searchRateClass" onchange='document.forms[0].Submit.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
               	</html:select></td>
            <td width="60" class="search">TDSP</td>
            <td width="1" class="search">:</td>
            <td class="search">
				<html:select property="searchTDSP" onchange='document.forms[0].Submit.focus();'>
					<html:option value="0">Select one</html:option>
					<html:optionsCollection name="selAllTDSP" label="tdspName" value='tdspIdentifier' />
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
              <td width="80" class="tblheader" id='img_sort' onClick="callSort();" style="cursor:hand" title='Sort by Rate Code in Ascending'>Rate Code <img src="<%=request.getContextPath()%>/images/sort.gif" width="7" height="8"></td> 
              <%}else{%>
              <td width="80" class="tblheader" id='img_sort' onClick="callSort();" style="cursor:hand" title='Sort by Rate Code in Decending'>Rate Code <img src="<%=request.getContextPath()%>/images/sort.gif" width="7" height="8"></td> 
              <%}%>
              <td width="120" class="tblheader">Rate Class Name </td> 
              <td class="tblheader">Description</td> 
              <td width="80" class="tblheader">Category</td>
              <td width="110" class="tblheader">Service Voltage</td>
              <td width="70" class="tblheader">SCUD %</td>
            </tr>
			<% 
          		if( lstRateCodes.size() > 0 )
		        {
        	%> 
			<logic:iterate id="rateCodes"  name="lstRateCodes" indexId="i">
          <tr>
            <td width="22" class="tbldata">
				<input type="radio" name="checkbox" value='<bean:write name="rateCodes" property="rateCode"/>'>
			</td>
			<%
				int val = i.intValue();
				str=((TDSPRateCodesDetails)lstRateCodes.get(val)).getRateCode();
				if(str.length()>8)
				{
					str = str.substring(0,7)+"...";
				%>
					<td class="tbldata" title='<bean:write name="rateCodes" property="rateCode" />'><%=str%>&nbsp;</td>
				<%
				}
				else
				{
			%>
             		<td class="tbldata"><bean:write name="rateCodes" property="rateCode" />&nbsp;</td>
             <% } 
             
				str=((TDSPRateCodesDetails)lstRateCodes.get(val)).getRateClass();
				if(str.length()>30)
				{
					str = str.substring(0,28)+"...";
				%>
             		<td class="tbldata" title='<bean:write name="rateCodes" property="rateClass" />' nowrap><%=str%>&nbsp;</td>
             	<%
				}
				else
				{
			%>	
					<td class="tbldata"><bean:write name="rateCodes" property="rateClass" />&nbsp;</td>
           	 <%	}
				str=((TDSPRateCodesDetails)lstRateCodes.get(val)).getDescription()==null?"":((TDSPRateCodesDetails)lstRateCodes.get(val)).getDescription();
				if(str.length()>60)
				{
					str = str.substring(0,58)+"...";
				%>
					<td class="tbldata" title='<bean:write name="rateCodes" property="description" />'><%=str%>&nbsp;</td> 
				<%
				}
				else
				{
			%>
           	 <td class="tbldata"><bean:write name="rateCodes" property="description" />&nbsp;</td> 
           	 <% } 
           	 %>
             <td class="tbldata">
				<logic:present name="rateCodes" property="meterCategory">
              		<bean:write name="rateCodes" property="meterCategory.meterCategory" ignore="true" />
            	</logic:present>&nbsp;
				<%--=((TDSPRateCodesDetails)lstRateCodes.get(val)).getMeterCategory().getMeterCategory()--%>
			 </td>
             <td class="tbldata">
				<logic:present name="rateCodes" property="serviceVoltage">
              		<bean:write name="rateCodes" property="serviceVoltage.serviceVoltageType" ignore="true" />
            	</logic:present>&nbsp;
				<%---=((TDSPRateCodesDetails)lstRateCodes.get(val)).getServiceVoltage().getServiceVoltageType()--%>
			 </td>
             <td class="tbldata" align='right'>
             	<logic:present name="rateCodes" property="scudPercentage">
              		<bean:write name="rateCodes" property="scudPercentage" ignore="true" />
            	</logic:present>&nbsp;
				<%--=((TDSPRateCodesDetails)lstRateCodes.get(val)).getScudPercentage()--%>
			 </td>
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
