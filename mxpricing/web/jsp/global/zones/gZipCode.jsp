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
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.common.FilterHandler"%>
<%@ page import="com.savant.pricing.dao.ZipCodesDAO"%>
<%@ page import="com.savant.pricing.dao.CongestionZonesDAO"%>
<%@ page import="com.savant.pricing.dao.WeatherZonesDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ZipCodeVO" %>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.global.ZipCodeListForm" />
<jsp:setProperty name="frm" property="*" />

<%
	int browserHt = 0;	
	int totalCount=1; 
	int pageCount = 1;
	int maxItems = 0;
	int totalPages = 0;
	int startPosition = 0;
	
	FilterHandler objFilterHandler = new FilterHandler();
	ZipCodesDAO objZipCodesDAO = new ZipCodesDAO();
	CongestionZonesDAO objCongestionZonesDAO = new CongestionZonesDAO();
	WeatherZonesDAO objWeatherZonesDAO = new WeatherZonesDAO();
	HashMap hmResult = new HashMap();
	HashMap hmwetherZone = new HashMap();
	List lsWeZone = null ;
	List lsConZone =  null ; 
	boolean order = true;
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
		Filter nwfil = null;
		if(frm.getFormActions().equals(""))
		{
			fil = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		else if(frm.getFormActions().equalsIgnoreCase("search")||(frm.getFormActions().equalsIgnoreCase("navigation"))||(frm.getFormActions().equalsIgnoreCase("sorting")))
  		{
    		Vector filter = new Vector(); 
  		  	if (!frm.getTxtZipCode().trim().equals("")) 
			{
	   			filter = objFilterHandler.setFormDetails("zipCode",frm.getTxtZipCode(),"",filter);
	   			fil = new Filter[filter.size()]; 
		  		filter.copyInto(fil); 
		  		nwfil = fil[0];
			}
		}
		maxItems = Integer.parseInt(frm.getMaxItems());
    	pageCount = Integer.parseInt(frm.getPage());
		startPosition = Integer.parseInt(frm.getStartPosition());
		startPosition = (maxItems * pageCount)+1;
	  	 hmResult = objZipCodesDAO.getAllZipCodes(nwfil,Integer.parseInt(frm.getCongestionZone()),Integer.parseInt(frm.getWeatherZone()),"zipCode",order,((pageCount-1)*maxItems),maxItems);
		lsConZone = objCongestionZonesDAO.getAllCongestionZones();
	
	lsWeZone = objWeatherZonesDAO.getAllWeatherZones();
	List lstzipCode=null;
	if(hmResult!=null)
		{
			 lstzipCode =(List)hmResult.get("Records");
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
			
    if(frm.getFormActions().equalsIgnoreCase("navigation"))
		{
			 pageCount = Integer.parseInt(frm.getPage());
		}
		else if(frm.getFormActions().equalsIgnoreCase("navigationDown"))
		{
			maxItems = Integer.parseInt(frm.getMaxItems());
		}
	pageContext.setAttribute("lstzipCode",lstzipCode);
	pageContext.setAttribute("lsConZone",lsConZone);
	pageContext.setAttribute("lsWeZone",lsWeZone);
	
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 213;
	else
		browserHt = 193;
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
	temp.formActions.value="search";
	temp.page.value  = page;
	temp.submit();
}
function search()
{
	temp=document.forms[0];
	temp.formActions.value="search";
	temp.page.value  = 1;
	temp.submit();
}
function changePageGoto()
{
	temp = document.forms[0];
	temp.formActions.value="search";
	temp.submit();
}
function clearSearch()
{
try{
	temp=document.forms[0];
	temp.txtZipCode.value="";
	temp.congestionZone.selectedIndex =0;
	temp.weatherZone.selectedIndex =0;
}catch(err)
	{
alert(err.description);
}
}

function checkEnter(e)
{ 
     if(e.keyCode == 13)
     { 
       navigation();
       return false;
     }
     else if(event.keyCode>=48 && event.keyCode<=57)
     {
       return true; 
     }
     else
     return false;
}
function loadDefault()
{
	temp = document.forms[0];
	var obj = document.getElementById('img_sort');
	
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
	temp.txtZipCode.value = temp.txtZipCode.value;
}

function callSort()
{
	temp=document.forms[0];
	var odr = 'true';	
	temp.formActions.value="search";
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
		var obj = document.getElementById( 'zipLst' );
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
		if( flag )
		{
			temp.formActions.value = 'edit';
			temp.zipId.value       =  queryString;
			temp.submit();
		}
		else
		{
			alert( 'Please select a Zip code' );
		}
	}
	catch( err )
	{
		alert( err.description );
	}
}

</script>
<body onLoad="loadDefault();"> 
<html:form action="zipcodeList" focus='txtZipCode'>
<html:hidden property="formActions"/>
<html:hidden property="zipId"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>

<table width="100%" height='100%' border="0" cellpadding="0" cellspacing="0"> 
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
          <td width="250" class="page_title">Zip Code</td>
          <td class="page_title"><table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
            <tr>
            <!--   <td><a href="gZipCodeAdd.htm">Add</a> | <a href="gZipCodeEdit.htm">Modify</a> | <a href="#">Make InValid</a> </td>         -->                  
            </tr>
          </table></td>
          <td class = 'page_title'>
			<table border = '0' align = 'right' cellpadding ='0' cellspacing = '0' class = 'topnav'>
            	<tr>
		            <td><a href = '<%=request.getContextPath()%>/jsp/global/zones/gZipAdd.jsp'>Add</a> | <a href='#' onclick='changeStatus()'>Make Valid/InValid</a></td>
		        </tr>
	        </table>
	      </td>
        </tr>
      </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="60" class="search" align='right'>Zip Code</td>
            <td width="1" class="search">:</td>
            <td width="150" class="search" align='left'><html:text property="txtZipCode" styleClass="textbox" size="15" maxlength="8" onkeypress="return checkEnter(event)" /></td>
            <td width="100" class="search" align='right'>Congestion Zone</td>
            <td width="1" class="search">:</td>
            <td width="200" class="search" align='left'>
             <html:select property="congestionZone" onchange='document.forms[0].Submit.focus();'>
                <html:option value="0">Select one</html:option>
				<html:options collection="lsConZone" property="congestionZoneId" labelProperty="congestionZone"/>
               </html:select>
             </td>          
            <td width="100" class="search" align='right'>Weather Zone</td>
            <td width="1" class="search">:</td>
			<td class="search" align='left'>
            <html:select property="weatherZone" onchange='document.forms[0].Submit.focus();'>
                <html:option value="0">Select one</html:option>
				<html:options collection="lsWeZone" property="weatherZoneId" labelProperty="weatherZone"/>
               </html:select>			   
            <html:button property="Submit" value="Go!" styleClass="button_sub_internal" onclick="search()" />
			<html:button property="Clear" value="Clear" styleClass="button_sub_internal" onclick="clearSearch()" />
			</td>
           </tr>
        </table>
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
        <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px');" id="divList"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr class='staticHeader'>
            <td width="25" class="cmbheader">&nbsp;</td>
            <%if(order)
            {%>
			<td width="160" class="tblheader" id='img_sort' onClick="callSort();" style="cursor:hand" title='Sort by Zip Code in Ascending'>Zip Code <img src="<%=request.getContextPath()%>/images/sort.gif" width="7" height="8"></td>
			<%}else{%>
            <td width="160" class="tblheader" id='img_sort' onClick="callSort();" style="cursor:hand" title='Sort by Zip Code in Decending'>Zip Code <img src="<%=request.getContextPath()%>/images/sort_up.gif" width="7" height="8"></td>
            <%}%>
            <td width="140" class="tblheader">Congestion Zone</td>
            <td class="tblheader">Weather Zone </td>
            <td width = '100' class = 'tblheader'>Is Valid</td>
          </tr>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id='zipLst'>
        <% 
          if( lstzipCode.size() > 0 )
          {
        %>
		<logic:iterate id="zipcode"  name="lstzipCode" indexId = 'i'>
		  <%
				int val           = i.intValue();
				boolean runStatus = false;
				runStatus = ((ZipCodeVO)lstzipCode.get(val)).isValid();
		  %>
		  <tr>
            <td width="26" class="tbldata_chk" height='30'>
				<input type="radio" name="checkValue" value='<bean:write name="zipcode" property="zipCode"/>'">
			</td>
             <td width="160" class="tbldata"><bean:write name="zipcode" property="zipCode"/></td>
             <td width="140" class="tbldata">
				<logic:present name="zipcode" property="congestionZone">
              		<bean:write name="zipcode" property="congestionZone.congestionZone" ignore="true" />
            	</logic:present>&nbsp;
			 </td>
	         <td class="tbldata">
				<logic:present name="zipcode" property="weatherZone">
					<bean:write name="zipcode" property="weatherZone.weatherZone" ignore="true"/>
				</logic:present>&nbsp;
			 </td>
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
       </logic:iterate >
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
            <td width="200">Items  <%=totalCount>0?(pageCount*maxItems)-maxItems+1:0%> - <%=((maxItems*pageCount)>totalCount)?totalCount:(maxItems*pageCount)%> of <%=totalCount%></td>
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
