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
<%@ page import="com.savant.pricing.dao.LoadProfileTypesDAO"%>
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.common.FilterHandler"%>
<%@ page import="com.savant.pricing.valueobjects.LoadProfileTypesVO" %>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.InputStream"%>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.global.LoadProfileForm" />
<jsp:setProperty name="frm" property="*" />

<%!
FilterHandler objFilterHandler = new FilterHandler();
public String search() {
    return null;
}
%>
<%
	Properties props;
	props = new Properties();
	InputStream is = this.getClass().getClassLoader().getResourceAsStream("ApplicationResources.properties");
	props.load(is);
	String highlightColor = props.getProperty("list.highlight.color");
	
	LoadProfileTypesDAO objLoadProfileTypesDAO = new LoadProfileTypesDAO();
	
	int totalCount=1;
	int pageCount = 1;
	int maxItems = 0;
	int totalPages = 0;
	int startPosition = 0;
	FilterHandler objFilterHandler = new FilterHandler();
	
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
		if(frm.getFormActions().equals(""))
		{
			fil = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		else if(frm.getFormActions().equalsIgnoreCase("search"))
  		{
    		Vector filter = new Vector();
  		  	
  		  	
  		  	if (!frm.getTxtName().equals(""))
			{
	   			filter = objFilterHandler.setFormDetails("profileType",frm.getTxtName(),frm.getSearchName(),filter);

			}
		  fil = new Filter[filter.size()];
		  filter.copyInto(fil);
		}
		maxItems = Integer.parseInt(frm.getMaxItems());
    	pageCount = Integer.parseInt(frm.getPage());
		startPosition = Integer.parseInt(frm.getStartPosition());
		
	HashMap hmResult =objLoadProfileTypesDAO.getAllProfileTypes(fil,"profileType",order,((pageCount-1)*maxItems),maxItems);
	List lstLoadProfile=null;
	if(hmResult!=null)
		{
			 lstLoadProfile =(List)hmResult.get("Records");
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
	pageContext.setAttribute("lstLoadProfile",lstLoadProfile);
	
	int browserHt = 0;
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 214;
	else
		browserHt = 194;
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
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
	var page = 1;
	page = parseInt(temp.page.value,10) + 1;
	temp.formActions.value="search";
	temp.page.value  = page;
	temp.submit();
}

function loadDefault()
{
	temp=document.forms[0];
	temp.page.options.value ='<%=frm.getPage()%>';
}
function clearSearch()
{
try{
	temp=document.forms[0];
	temp.txtName.value="";
	temp.searchName.selectedIndex =0;
}catch(err)
	{
alert(err.description);
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

function loadDefault()
{
	temp=document.forms[0];
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
	temp.txtName.value = temp.txtName.value;
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
		var obj = document.getElementById( 'loadLst' );
		flag = 0;

		for( var i = 1; i < obj.children[0].children.length; i++ )
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
			temp.loadId.value      = queryString;
			temp.submit();
		}
		else
		{
			alert( 'Please select a Load List' );
		}
	}
	catch( err )
	{
		alert( err.description );
	}
}
</script>
<body onload="loadDefault()"> 
<html:form action="LoadProfileList" focus='txtName'>
<html:hidden property="formActions"/>
<html:hidden property="loadId"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>

<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/>
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
          <td width='250' class="page_title">Load Profiles</td>
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
         </tr>
         <tr>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="80" class="search">Name</td>
            <td width="1" class="search">:</td>
            <td class="search"><html:text property="txtName" styleClass="textbox" size="15" onkeypress="return checkEnter(event)"/>
               <html:select property="searchName" onchange='document.forms[0].Submit.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              </html:select>         
            <html:button property="Submit" value="Go!" onclick="search();" styleClass="button_sub_internal"/>
			<html:button property="Clear" value="Clear" styleClass="button_sub_internal" onclick="clearSearch()"/></td></tr>
        </table>
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
       <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0" id='loadLst'>
           <tr class='staticHeader'>
            <td width="24" class="cmbheader">&nbsp;</td>
            <%if(order)
            {%>
            <td width="180" class="tblheader" id='img_sort' onclick='callSort()' style="cursor:hand" title='Sort by Name in Ascending'>Name <img src="<%=request.getContextPath()%>/images/sort.gif" width="7" height="8"></td>
            <%}else{%>
            <td width="180" class="tblheader" id='img_sort' onclick='callSort()' style="cursor:hand" title='Sort by Name in Decending'>Name <img src="<%=request.getContextPath()%>/images/sort_up.gif" width="7" height="8"></td>
            <%}%>
            <td class="tblheader">Description</td>
            <td width = '100' class = 'tblheader'>Is Valid</td>
          </tr>
       <% 
          if( lstLoadProfile.size() > 0 )
          {
        %>
		<logic:iterate id="loadProfile"  name="lstLoadProfile" indexId = 'i'>
		  <%
				int val           = i.intValue();
				boolean runStatus = false;
				runStatus = ((LoadProfileTypesVO)lstLoadProfile.get(val)).isValid();
		  %>
          <tr> 	
            <td width="19" class="tbldata">
			<input type="radio" name="checkbox" value='<bean:write name="loadProfile" property="profileIdentifier"/>'>
			</td>
             <td width="180" class="tbldata"><bean:write name="loadProfile" property="profileType" /></td>
           <td class="tbldata"><bean:write name="loadProfile" property="description" />&nbsp;</td> 
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
