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
<%@ page import="com.savant.pricing.dao.CongestionZonesDAO"%>
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.dao.MeterCategoryDAO"%>
<%@ page import="com.savant.pricing.common.FilterHandler"%>
<%@ page import="com.savant.pricing.valueobjects.MeterCategoryVO" %>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.global.tdsp.MeterTypeForm" />
<jsp:setProperty name="frm" property="*" />

<%
	int browserHt = 0;	
	String userName = (String)session.getAttribute("userName");
 	if(userName==null)
		getServletConfig().getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(request, response);
	CongestionZonesDAO objCongestionZonesDAO = new CongestionZonesDAO();
	int totalRecords=1;
	int pageCount = 1;
	int maxItems = 0;
	int totalPages = 0;
	int startPosition = 0;
	HashMap hmResult = new HashMap();
	List lstMtrType = null;
	FilterHandler objFilterHandler = new FilterHandler();
	boolean order = true;
	try
	{
		MeterCategoryDAO objMeterCategoryDAO = new MeterCategoryDAO();
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
		if(frm.getFormActions().equals("update")||frm.getFormActions().equals("delete"))
			frm.setFormActions("");
			Filter fil[]=null;
		if(frm.getFormActions().equals(""))
		{
			fil = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		//else if(frm.getFormActions().equalsIgnoreCase("search"))
  		else if(frm.getFormActions().equalsIgnoreCase("search"))
  		{
    		Vector filter = new Vector();
  		  	if (!frm.getTxtMeterType().equals(""))
			{
	   			filter = objFilterHandler.setFormDetails("meterCategory",frm.getTxtMeterType(),frm.getSearchMeterType(),filter);
			}
		  fil = new Filter[filter.size()];
		  filter.copyInto(fil);
		}
		maxItems = Integer.parseInt(frm.getMaxItems());
    	pageCount = Integer.parseInt(frm.getPage());
		startPosition = Integer.parseInt(frm.getStartPosition());
		hmResult = objMeterCategoryDAO.getAllMeterCategory(fil,"meterCategory",order,((pageCount-1)*maxItems),maxItems);
		totalRecords = Integer.parseInt(String.valueOf(hmResult.get("TotalRecordCount")));
		if(hmResult!=null)
		{
			 lstMtrType =(List)hmResult.get("Records");
		}
		
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
		
		pageContext.setAttribute("lstMtrType",lstMtrType);
		
		if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
			browserHt = 214;
		else
			browserHt = 194;
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
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/mm_menu.js"></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
</head>
<script>
 function pageDecrement()
 {

 	temp = document.forms[0];
	if(parseInt(temp.page.value,10) > 0)
	 {
		page = parseInt(temp.page.value,10) - 1;
 		temp.page.value  = page;
	}
	temp.formActions.value="search";
 	temp.submit();
	}
function changePage()
{
try{
	temp=document.forms[0];
	temp.formActions.value="search";
	temp.page.value  =0;
	temp.submit();
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

function pageIncrement()
 {
 	temp = document.forms[0];

 if(parseInt(temp.page.value,10)+1 < parseInt(temp.page.options.length))
	 {
	page = parseInt(temp.page.value,10) + 1;
 	temp.page.value  = page;
	 }
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
	temp.txtMeterType.value = temp.txtMeterType.value;
}
function search()
{
	temp=document.forms[0];
	temp.formActions.value = "search";
	temp.page.value =1;
	temp.submit();
}
function fnClear()
{
	temp = document.forms[0];
	temp.txtMeterType.value = '';
	temp.searchMeterType.value = '0';
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
function changeStatus()
{
	try
	{
		var temp, flag, queryString;
		queryString = "";
		temp    = document.forms[0];
		var obj = document.getElementById( 'meterLst' );
		flag = 0;

		for( var i = 0; i < obj.children[0].children.length; i++ )
		{
			if( obj.children[0].children[i].children[0].children[0].checked )
			{
				queryString = obj.children[0].children[i].children[0].children[0].value;
				flag     = 1;
				break;
			}
		}
		if( flag  )
		{
			temp.formActions.value = 'edit';
			temp.meterId.value     = queryString;
			temp.submit();
		}
		else
		{
			alert( 'Please select a Meter Type' );
		}
	}
	catch( err )
	{
		alert( err.description );
	}
}

</script>
<body onload="loadDefault()"> 
<html:form action="meterTypeList" focus='txtMeterType' method="post">
<html:hidden property="formActions"/>
<html:hidden property="meterId"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
<script language="JavaScript1.2"></script> 
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
	<td  valign="top"> <jsp:include page="/jsp/menu.jsp"/>
		<table width = '100%' border = '0' cellspacing = '0' cellpadding = '0'> 
			<tr> 
	    		<td class = 'message'>
	    			<logic:messagesPresent message = 'true' >
						<html:messages id = 'messageid' message = 'true'><bean:write name = 'messageid'/></html:messages>
					</logic:messagesPresent>
		    	</td>
	    	</tr>
		</table>
   <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width='250' class="page_title">Meter Type</td>
          <%-- <td class="page_title">
          	<table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
            		<tr>
              			<td><a href="gServiceVoltageAdd.htm">Add</a> | <a href="gServiceVoltageEdit.htm">Modify</a> | <a href="#">Make Valid</a></td>
            		</tr>
          		</table>
          	</td> --%>
		  <td class = 'page_title'>
			<table border = '0' align = 'right' cellpadding ='0' cellspacing = '0' class = 'topnav'>
            	<tr>
		            <td><a href='#' onclick='changeStatus()'>Make Valid/InValid</a></td>
		        </tr>
	        </table>
	      </td>
          </tr><tr>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="100" class="search">Meter Type</td>
            <td width="12" class="search">:</td>
            <td class="search"><html:text property="txtMeterType" styleClass="textbox" size="15" onkeypress="return checkEnter(event)"/>
               <html:select property="searchMeterType" onchange='document.forms[0].btnSubmit.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              </html:select>       
            <html:button property="btnSubmit" value="Go!" onclick="search();" styleClass="button_sub_internal"/>
			<html:button property="btnclear" onclick = "fnClear();" value="Clear" styleClass="button_sub_internal"/></td></tr>
        </table>
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
        <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr class='staticHeader'>
            <td width="25" class="cmbheader">&nbsp;</td>
            <%if(order)
            {%>
            <td width="186" class="tblheader" id='img_sort' onClick="callSort();" style="cursor:hand" title='Sort by Meter Type in Ascending'>Meter Type <img src="<%=request.getContextPath()%>/images/sort.gif" width="7" height="8"></td>
            <%}else{%>
            <td width="186" class="tblheader" id='img_sort' onClick="callSort();" style="cursor:hand" title='Sort by Meter Type in Decending'>Meter Type <img src="<%=request.getContextPath()%>/images/sort_up.gif" width="7" height="8"></td>
            <%}%>
            <td class="tblheader">Description</td>
            <td width = '100' class = 'tblheader'>Is Valid</td>
          </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0" id = 'meterLst'>
        <% 
          if( lstMtrType.size() > 0 )
          {
        %>
		<logic:iterate id="mtrtype"  name="lstMtrType" indexId = 'i'>
		  <%
				int val           = i.intValue();
				boolean runStatus = false;
				runStatus = ((MeterCategoryVO)lstMtrType.get(val)).isValid();
		  %>
          <tr>
            <td width="19" class="tbldata">
			<input type="radio" name="checkValue" value='<bean:write name="mtrtype" property="meterCategoryIdentifier"/>'>
			</td>
            <td width="186" class="tbldata"><bean:write name="mtrtype" property="meterCategory" /></td>
            <td class="tbldata"><bean:write name="mtrtype" property="description" />&nbsp;</td> 
            <td width = '100' align='center' class = 'tbldata'>
	        <% 
	        	if(runStatus)
	        	{
	        %>	
   			        <img border='0' width='11' height='11' src='<%=request.getContextPath()%>/images/success.gif' alt = 'Valid' />
            <%
	            }
            	else
            	{
            %>
					<img border='0' width='11' height='11' src='<%=request.getContextPath()%>/images/failure.gif' alt = 'Invalid' />
    		<%
    			}
    		%>
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
