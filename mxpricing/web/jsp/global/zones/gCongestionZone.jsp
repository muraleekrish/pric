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
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.dao.CongestionZonesDAO"%>
<%@ page import="com.savant.pricing.common.FilterHandler"%>
<%@ page import="com.savant.pricing.valueobjects.CongestionZonesVO" %>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.global.CongestionZoneListForm" />
<jsp:setProperty name="frm" property="*" />

<%
	int browserHt = 0;
	int totalCount=1;
	int pageCount = 1;
	int maxItems = 0;
	int totalPages = 0;
	List listCongZone = null;
	boolean order = true;
	HashMap hmResult = new HashMap();
	
	try
	{
	 	CongestionZonesDAO objCongestionZonesDAO=new CongestionZonesDAO();
		FilterHandler objFilterHandler = new FilterHandler();
	
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
	
	if(frm.getFormActions().equals("update")||frm.getFormActions().equals("delete"))

		frm.setFormActions("");
		Filter fil[]=null;
		if(frm.getFormActions().equals(""))
		{
			fil = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		else if(frm.getFormActions().equalsIgnoreCase("search"))
  		{
    		Vector filter = new Vector();
  		  	
  		  	if (!frm.getCongestionZone().equals(""))
			{
	   			filter = objFilterHandler.setFormDetails("congestionZone",frm.getCongestionZone(),frm.getSearchZone(),filter);
			}
		  fil = new Filter[filter.size()];
		  filter.copyInto(fil);
		}
		maxItems = Integer.parseInt(frm.getMaxItems());
    	pageCount = Integer.parseInt(frm.getPage());
		hmResult = objCongestionZonesDAO.getAllCongestionZones(fil,"congestionZone",order,((pageCount-1)*maxItems),maxItems);
		totalCount = Integer.parseInt(String.valueOf(hmResult.get("TotalRecordCount")));
		listCongZone = (List)hmResult.get("Records");
	
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
		pageContext.setAttribute("listCongZone",listCongZone);
		
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
<script>

function clearAll()
{
	var temp=document.forms[0];
	temp.searchZone.value=0;
	temp.congestionZone.value="";
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
	temp = document.forms[0];
	temp.formActions.value="search";
	temp.page.value = 1;
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
	temp.formActions.value="search";
	temp.page.value  = page;
	temp.submit();
}

function pageIncrement()
{
	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) + 1;
	temp.formActions.value="search";
	temp.page.value  = page;
	temp.submit();
}
function loadDefault()
{
	var temp = document.forms[0];
	var obj = document.getElementById('img_sort');
	if(temp.sortOrder.value == "ascending")
	{		
		obj.children[0].src = '<%=request.getContextPath()%>/images/sort.gif';		
	}
	else
	{		
		obj.children[0].src = '<%=request.getContextPath()%>/images/sort_up.gif';		
	}
	
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
	temp.congestionZone.value = temp.congestionZone.value;
}
function clearSearch()
{
try{
	temp=document.forms[0];
	temp.congestionZone.value="";
	temp.searchZone.selectedIndex = 0;
}catch(err)
	{
alert(err.description);
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
function changeStatus()
{
	try
	{
		var temp, flag, queryString;
		queryString  = "";
		flag = 0;
		temp    = document.forms[0];
		var obj = document.getElementById( 'zoneLst' );

		for( var i = 0; i < obj.children[0].children.length; i++ )
		{
			if( obj.children[0].children[i].children[0].children[0].checked )
			{
				queryString = obj.children[0].children[i].children[0].children[0].value;
				flag        = 1;
				break;
			}
		}
		if( flag  )
		{
			temp.formActions.value = 'edit';
			temp.congId.value      = queryString;
			temp.submit();
		}
		else
		{
			alert( 'Please select a Congestion Zone' );
		}
	}
	catch( err )
	{
		alert( err.description );
	}
}
</script>
</head>
<body onLoad="loadDefault();"> 
<html:form action="gCongestionZone" focus="congestionZone">
<html:hidden property="formActions"/>
<html:hidden property="congId" />
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>

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
	      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
    	    <tr>
        	  <td width="250" class="page_title">Congestion Zone </td>
	          <td class="page_title"><table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
            <tr>
             <!-- <td><a href="gCongestionZoneAdd.htm">Add</a> | <a href="gCongestionZoneEdit.htm">Modify</a> | <a href="#">Make InValid</a></td>              -->
            </tr>
          </table>
          </td>
          <td class = 'page_title'>
			<table border = '0' align = 'right' cellpadding ='0' cellspacing = '0' class = 'topnav'>
            	<tr>
		            <td><a href='#' onclick='changeStatus()'>Make Valid/InValid</a></td>
		        </tr>
	        </table>
	      </td>
        </tr>
      </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="65" class="search">Zone Name</td>
            <td width="1" class="search">:</td>
            <td class="search">
			 <html:text property="congestionZone" styleClass="TextBox" styleId="txtName" onkeypress="return checkEnter(event)"/> 
			 <html:select property="searchZone" styleClass="Combo" onchange='document.forms[0].go.focus();'> 
				<html:option value="0">Starts With</html:option> 
				<html:option value="1">Ends With</html:option> 
				<html:option value="2">Exactly</html:option> 
				<html:option value="3">Anywhere</html:option> 
			 </html:select>
             <html:button property="go" styleClass="button_sub_internal" onclick="search()" value="Go!"/>
			 <html:button property="Clear" value="Clear" styleClass="button_sub_internal" onclick="clearSearch()"/>
			 </td>
          </tr>
        </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
        <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class='staticHeader'>
            <td width="26" class="cmbheader">&nbsp;</td>
            <%if(order)
            {%>
			<td width="186" class="tblheader" id='img_sort' onClick="callSort();" style="cursor:hand" title='Sort by Zone Name in Ascending'>Zone Name <img src="<%=request.getContextPath()%>/images/sort.gif" width="7" height="8"></td>
			<%}else{%>
            <td width="186" class="tblheader" id='img_sort' onClick="callSort();" style="cursor:hand" title='Sort by Zone Name in Decending'>Zone Name <img src="<%=request.getContextPath()%>/images/sort_up.gif" width="7" height="8"></td>
            <%}%>
            <td class = 'tblheader'>Description</td>
            <td width = '100' class = 'tblheader'>Is Valid</td>
          </tr>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id='zoneLst'>
        <% 
          if( listCongZone.size() > 0 )
          {
        %>
		<logic:iterate id="zone" name="listCongZone" indexId = 'i'>
		  <%
				int val           = i.intValue();
				boolean runStatus = false;
				runStatus = ((CongestionZonesVO)listCongZone.get(val)).isValid();
		  %>
          <tr>
            <td width="27" class="tbldata_chk" height='30'>
				<input type="radio" name="checkValue" value='<bean:write name="zone" property="congestionZoneId"/>'>
			</td>
            <td width="186" class="tbldata"><bean:write name="zone" property="congestionZone" /></td>
	        <td class = 'tbldata'><bean:write name="zone" property="description" />&nbsp;</td> 
	        <td width = '100' align='center' class = 'tbldata'>
	        <% 
	        	if(runStatus)
	        	{
	        %>	
   			        <img border='0' width='11' height='11' src='<%=request.getContextPath()%>/images/success.gif' alt = 'Valid'/>
            <%
	            }
            	else
            	{
            %>
					<img border='0' width='11' height='11' src='<%=request.getContextPath()%>/images/failure.gif' alt = 'Invalid'/>
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
</html:html>
<%}%>
