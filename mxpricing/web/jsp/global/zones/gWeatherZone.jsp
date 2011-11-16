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
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.dao.WeatherZonesDAO"%>
<%@ page import="com.savant.pricing.dao.CongestionZonesDAO"%>
<%@ page import="com.savant.pricing.valueobjects.WeatherZonesVO" %>
<%@ page import="com.savant.pricing.common.FilterHandler"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.global.WeatherZoneListform" />
<jsp:setProperty name="frm" property="*" />
<%
	int browserHt = 0;	
	int totalCount=1; 
	int pageCount = 1;
	int maxItems = 0;
	int totalPages = 0;
	int startPosition = 0;
	int congZone = 0;
	FilterHandler objFilterHandler = new FilterHandler();
	WeatherZonesDAO objWeatherZoneDAO = new WeatherZonesDAO(); 
	CongestionZonesDAO objCongestionZonesDAO = new CongestionZonesDAO();
	boolean order = true;
	
	List arrWeatherZoneLst=null;
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
			if(request.getParameter("formAction") != null)
				{
		 			frm.setFormAction(request.getParameter("formAction"));
				}
			
			if(frm.getFormAction().equals("update")||frm.getFormAction().equals("delete"))
				frm.setFormAction("List");
				Filter fil[]=null;
				if(frm.getFormAction().equals("List"))
				{
					fil = new Filter[0];
					session.setAttribute("objFilter",fil);
				}
				else if(frm.getFormAction().equalsIgnoreCase("search"))
				{
					Vector filter = new Vector();
					congZone = Integer.parseInt(frm.getCongestionZone());
					if (!frm.getTxtCode().trim().equals(""))
					{
						filter = objFilterHandler.setFormDetails("weatherZoneCode",frm.getTxtCode(),frm.getSearchCode(),filter);
					}
					if (!frm.getTxtName().trim().equals(""))
					{
						filter = objFilterHandler.setFormDetails("weatherZone",frm.getTxtName(),frm.getSearchCode(),filter);

					}
				  fil = new Filter[filter.size()];
				  filter.copyInto(fil);
				}
				maxItems = Integer.parseInt(frm.getMaxItems());
				pageCount = Integer.parseInt(frm.getPage());
				startPosition = Integer.parseInt(frm.getStartPosition());
				startPosition = (maxItems * pageCount)+1;
				
				HashMap hmResult =objWeatherZoneDAO.getAllWeatherZones(fil,congZone,"weatherZone",order,((pageCount-1)*maxItems),maxItems);
					
				 List lsCongZone = objCongestionZonesDAO.getAllCongestionZones();
				 pageContext.setAttribute("lsCongZone",lsCongZone);
				
				if(hmResult!=null)
				{
					 arrWeatherZoneLst =(List)hmResult.get("Records");
				}
				totalCount = Integer.parseInt(hmResult.get("TotalRecordCount").toString());
			
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
			
				if(frm.getFormAction().equalsIgnoreCase("navigation"))
				{
					 pageCount = Integer.parseInt(frm.getPage());
				}
				else if(frm.getFormAction().equalsIgnoreCase("navigationDown"))
				{
					maxItems = Integer.parseInt(frm.getMaxItems());
				}
				
				pageContext.setAttribute("arrWeatherZoneLst",arrWeatherZoneLst);
			
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
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/mm_menu.js"></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
</head>
<script>

function navigation()
{
		temp = document.forms[0];
		temp.formAction.value="search";
		temp.page.value = temp.pageTop.options[temp.pageTop.selectedIndex].value;
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
	temp.formAction.value="search";
	temp.page.value=0;
	temp.submit();
}
function changePageGoto()
{
	temp = document.forms[0];
	temp.formActions.value="search";
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
try{
	temp=document.forms[0];
	temp.txtName.value="";
	temp.txtCode.value="";
	temp.congestionZone.selectedIndex = 0;
	temp.searchName.selectedIndex     = 0;
	temp.searchCode.selectedIndex     = 0;
}catch(err)
	{
alert(err.description);
}
}
function pageDecrement()
 {

 	temp = document.forms[0];
	var page = 1;
	temp.formAction.value="search";
	page = parseInt(temp.page.value,10) - 1;
	temp.page.value  = page;
	temp.submit();
}
function pageIncrement()
 {
 	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) + 1;
	temp.formAction.value="search";
	temp.page.value  = page;
	temp.submit();
}

function loadDefault()
{	
	temp=document.forms[0];
	
	var obj = document.getElementById('img_sort');
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
	temp.txtName.value = temp.txtName.value;
	temp.txtCode.value = temp.txtCode.value;
	
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
		queryString = "";
		temp    = document.forms[0];
		var obj = document.getElementById( 'weatherLst' );
		flag = 0;

		for(var i = 1; i < obj.children[0].children.length; i++)
		{
			if( obj.children[0].children[i].children[0].children[0].checked )
			{
				queryString = obj.children[0].children[i].children[0].children[0].value;
				flag        = 1;
				break;
			}
		}
		if( flag )
		{
			temp.formAction.value    = 'edit';
			temp.zoneId.value        = queryString;
			temp.submit();
		}
		else
		{
			alert( 'Please select a Weather Zone' );
		}
	}
	catch( err )
	{
		alert( err.description );
	}
}
</script>
<body onload="loadDefault();"> 
<html:form action="weatherZoneList" focus='txtName'>
<html:hidden property="formAction"/>
<html:hidden property="zoneId"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top">  <jsp:include page="../../menu.jsp"/>
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
          <td width="250" class="page_title">Weather Zone </td>
        <td class="page_title"><table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
              <tr>
                <!-- <td><a href="gWeatherZoneAdd.htm">Add</a> | <a href="gWeatherZoneEdit.htm">Modify</a> | <a href="#">Make InValid</a>  --></td>               
              </tr>
          </table></td>
          <td class="page_title">
			<table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
            	<tr>
		            <td><a href='#' onclick='changeStatus()'>Make Valid/InValid</a></td>
		        </tr>
	        </table>
	      </td>
        </tr>
      </table>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="35" height="24" class="search">Name</td>
            <td width="1" class="search">:</td>
            <td width="220" class="search"><html:text property="txtName" styleClass="textbox" size="15" onkeypress="return checkEnter(event)"/>
             <html:select property="searchName" onchange='document.forms[0].Submit.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              </html:select></td>
            <td width="30" class="search">Code</td>
            <td width="1" class="search">:</td>
            <td class="search"><html:text property="txtCode" styleClass="textbox" size="15" onkeypress="return checkEnter(event)"/>
              <html:select property="searchCode" onchange='document.forms[0].Submit.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              </html:select>
			</td>
			<td width="95" class="search">Congestion Zone</td>
            <td width="1" class="search">:</td>
            <td class="search"> <html:select property="congestionZone" onchange='document.forms[0].Submit.focus();'>
               <html:option value="0">Select one</html:option>
               <html:options collection="lsCongZone" property="congestionZoneId" labelProperty="congestionZone"/>
            </html:select>
            <html:button property="Submit" value="Go!" styleClass="button_sub_internal" onclick="search()" />
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
        <table width="100%"  border="0" cellspacing="0" cellpadding="0" id = 'weatherLst'>
          <tr class='staticHeader'>
            <td width="22" class="cmbheader">&nbsp;</td>
            <%if(order)
            {%>
            <td width="200" class="tblheader" id='img_sort' onClick="callSort();" style="cursor:hand" title='Sort by Zone Name in Ascending'>Zone Name <img src="<%=request.getContextPath()%>/images/sort.gif" width="7" height="8"></td>
            <%}else{%>
            <td width="200" class="tblheader" id='img_sort' onClick="callSort();" style="cursor:hand" title='Sort by Zone Name in Decending'>Zone Name <img src="<%=request.getContextPath()%>/images/sort_up.gif" width="7" height="8"></td>
            <%}%>
            <td width="180" class="tblheader">Code</td>
            <td width="180" class="tblheader">Congestion Zone</td>
            <td class="tblheader">Description</td>
            <td width = '100' class = 'tblheader'>Is Valid</td>
          </tr>
          <% 
            if( arrWeatherZoneLst.size() > 0 )
            {
          %>
          <logic:iterate id="zone"  name="arrWeatherZoneLst" indexId='i'>
		  <%
				int val           = i.intValue();
				boolean runStatus = false;
				runStatus = ((WeatherZonesVO) arrWeatherZoneLst.get(val)).isValid();
		  %>
          <tr>
             <td width="22" class="tbldata_chk" height='30'>
				<input type="radio" name="checkValue" value='<bean:write name="zone" property="weatherZoneId"/>'>
			 </td>
             <td width="200" class="tbldata"><bean:write name="zone" property="weatherZone" /></td>
             <td width="180" class="tbldata"><bean:write name="zone" property="weatherZoneCode" /></td>
             <td width="180" class="tbldata"><bean:write name="zone" property="congestionZone.congestionZone" /></td>
           	 <td class="tbldata"><bean:write name="zone" property="description" />&nbsp;</td> 
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