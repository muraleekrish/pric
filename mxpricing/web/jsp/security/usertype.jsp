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
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="com.savant.pricing.securityadmin.dao.UserTypeDAO"%>
<%@ page import="com.savant.pricing.common.FilterHandler"%>
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.common.BuildConfig"%>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.security.UserTypeListForm" />
<jsp:setProperty name="frm" property="*" />

<%
	Properties props;
	props = new Properties();
	InputStream is = this.getClass().getClassLoader().getResourceAsStream("ApplicationResources.properties");
	props.load(is);
	String highlightColor = props.getProperty("list.highlight.color");
	String padding = props.getProperty("list.highlight.padding");
	
	int browserHt = 0;
	UserTypeDAO objUserTypeDAO = new UserTypeDAO();
	
	int totalRecords=1;
	int pageCount = 1;
	int maxItems = 0;
	int startPosition = 0;
	int totalPages = 0;
	boolean order = true;
	HashMap hmResult = new HashMap();
	List listUserType = null;
	FilterHandler objFilterHandler = new FilterHandler();
	String sortFieldName = frm.getSortField();	
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
		
		Filter fil[] = null;
		Filter userTypefilter  = null;
		Filter parentTypefilter  = null;
		if ((frm.getFormAction().equalsIgnoreCase("add"))||(frm.getFormAction().equalsIgnoreCase("list")) ||(frm.getFormAction().equalsIgnoreCase("edit")) )
		{  
			fil = new Filter[0];
	 		session.setAttribute("objFilter",fil);
  		}
  		else if(frm.getFormAction().equalsIgnoreCase("search")||(frm.getFormAction().equalsIgnoreCase("navigation"))||(frm.getFormAction().equalsIgnoreCase("sorting")))
  		{  		   	
  		   	if(BuildConfig.DMODE)
			{
				System.out.println("frm.getTxtUserType()"+frm.getTxtUserType());
  		   		System.out.println("frm.getTxtParentType()"+frm.getTxtParentType()); 
  		   	}
  		   	maxItems = Integer.parseInt(frm.getMaxItems());  		  	
  		  	Vector filter = new Vector();
			if (!frm.getTxtUserType().trim().equalsIgnoreCase(""))
			{
		  		filter = new Vector();
		  		filter = objFilterHandler.setFormDetails("userType",frm.getTxtUserType(),frm.getSearchUserType(),filter);
		  		fil = new Filter[filter.size()];
		 		filter.copyInto(fil);
		  		userTypefilter = fil[0];
			}
			if (!frm.getTxtParentType().trim().equalsIgnoreCase(""))
			{
			  	filter = new Vector();
			  	filter = objFilterHandler.setFormDetails("userType",frm.getTxtParentType(),frm.getSearchParentType(),filter);
			  	fil = new Filter[filter.size()];
			 	filter.copyInto(fil);
			  	parentTypefilter = fil[0];
			}		
	  	}	
		maxItems = Integer.parseInt(frm.getMaxItems());
		pageCount = Integer.parseInt(frm.getPage());		
		hmResult = objUserTypeDAO.getAllUserType(userTypefilter,parentTypefilter,"userType",order,((pageCount-1)*maxItems),maxItems);
		totalRecords = Integer.parseInt(String.valueOf(hmResult.get("TotalRecordCount")));
		listUserType = (List)hmResult.get("Records");
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
					
	    if(frm.getFormAction().equalsIgnoreCase("navigation"))
		{
			 pageCount = Integer.parseInt(frm.getPage());
		}
		else if(frm.getFormAction().equalsIgnoreCase("navigationDown"))
		{
			maxItems = Integer.parseInt(frm.getMaxItems());
		}
		pageContext.setAttribute("listUserType",listUserType);
		
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
	temp.formAction.value="navigation";
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
	temp.formAction.value="navigation";
	temp.submit();
}

function changePageGoto()
{
	temp = document.forms[0];
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
	try{
		temp=document.forms[0];
		temp.txtUserType.value="";
		temp.txtParentType.value="";		
		temp.searchUserType.selectedIndex =0;
		temp.searchParentType.selectedIndex =0;		
		temp.formAction.value ="";
	}catch(err)
	{
		alert(err.description);
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

function editUserType()
{
try{
	temp=document.forms[0];

	var obj = document.getElementById('userLst');
	
	var subQuery="";
	var count=0;
	for(var i=1;i<obj.children[0].children.length;i++)
		{

			if(i == 1)
			{
				if(obj.children[0].children[i].children[0].children[0].checked)
				{
					subQuery = obj.children[0].children[i].children[0].children[0].value;
					count++;
				}
			}
			else
			{
				if(obj.children[0].children[i].children[0].children[0].checked)
				{
					subQuery += obj.children[0].children[i].children[0].children[0].value;
					count++;
				}
			}
		}
	if(count>0)
	{
		temp.action='<%=request.getContextPath()%>/userTypeEdit.do?userTypeid='+subQuery+'&formActoin=edit';
		temp.formAction.value="edit";
		temp.submit();
	}else
	{
		alert("Please select a User Type to Modify");
	}
  }
  catch(err)
  {
	alert(err.description);
  }
}

function deleteUserType()
{
try{
	temp=document.forms[0];

	var obj = document.getElementById('userLst');
	
	var subQuery="";
	var count=0;
	for(var i=1;i<obj.children[0].children.length;i++)
		{

			if(i == 1)
			{
				if(obj.children[0].children[i].children[0].children[0].checked)
				{
					subQuery += obj.children[0].children[i].children[0].children[0].value;
				count++;
				}
			}
			else
			{
				if(obj.children[0].children[i].children[0].children[0].checked)
				{
					subQuery = obj.children[0].children[i].children[0].children[0].value;
					count++;
				}
			}
		}	 
		//subQuery = subQuery.substring(0,subQuery.length-1);
	temp.action='<%=request.getContextPath()%>/usertype.do?userTypeid='+subQuery+'&formActoin=delete';
	if(count>0)
	{
		if(!confirm("Warning!\nChosen Item will be deleted."))
		{
			return;
		}
		else
		{		
			temp.formAction.value="delete";
			temp.submit();
		}
	}
	else
	{
		alert("Please select a UserType to Delete");
	}
  }
  catch(err)
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

function loadDefaults()
{
	temp=document.forms[0];
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
	temp.txtUserType.value = temp.txtUserType.value;
	temp.txtParentType.value = temp.txtParentType.value;
}

function callView(userId)
{
	temp=document.forms[0];
	temp.action='<%=request.getContextPath()%>/jsp/security/userTypeView.jsp?userId='+userId;
	temp.submit();
}

</script>
<body onLoad='loadDefaults();'> 
<html:form action="usertype" focus="txtUserType">
<html:hidden property="formAction" />
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>

<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td valign="top"> <jsp:include page="../menu.jsp"/>
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
          <td width="250" class="page_title">User Type </td>
          <td class="page_title"><table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
            <tr>
              <td><a href="<%=request.getContextPath()%>/jsp/security/usertypeadd.jsp">Add</a> | <a href="#" onclick="editUserType()">Modify</a> | <a href="#" onclick="deleteUserType()">Delete</a></td>
              
            </tr>
          </table></td>
        </tr>
      </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="65" class="search">User Type</td>
            <td width="9" class="search">:</td>
            <td width="330" class="search">
            	<html:text property="txtUserType" styleClass="textbox" size="15" maxlength="15" onkeypress="return checkEnter(event)" />
              	<html:select property="searchUserType" onchange='txtUserType.focus();'>
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              	</html:select></td>            
			<td width="99" class="search">Parent Type</td>
            <td width="10" class="search">:</td>
            <td width="479" class="search">
				<html:text property="txtParentType" styleClass="textbox" size="15" maxlength="15" onkeypress="return checkEnter(event)" />
              	<html:select property="searchParentType" onchange='txtParentType.focus();'> 
	              <html:option value="0">Start With</html:option>
				  <html:option value="1">End With</html:option>
				  <html:option value="2">Exactly</html:option>
				  <html:option value="3">AnyWhere</html:option>
              	</html:select>
				<html:button property="Button" value="Go!" styleClass="button_sub_internal" onclick="search()" />
				<html:button property="Button" value="Clear" styleClass="button_sub_internal" onclick="clearSearch()"/>
                </td>
          </tr>
        </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
       <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="userLst">
		 <tr class='staticHeader'>
            <td width="22" class="cmbheader">&nbsp;</td>
            <%if(order)
            {%>
            <td width="187" class="tblheader" onClick="callSort();" id='img_sort' align='center' style="cursor:hand" title='Sort by User Type in Ascending'>User Type <img src="<%=request.getContextPath()%>/images/sort.gif" width="7" height="8"></td>
            <%}else{%>
            <td width="187" class="tblheader" onClick="callSort();" id='img_sort' align='center' style="cursor:hand" title='Sort by User Type in Decending'>User Type <img src="<%=request.getContextPath()%>/images/sort_up.gif" width="7" height="8"></td>
            <%}%>
            <td width="191" class="tblheader" align='center'>Parent Type </td>
            <td width="592" class="tblheader" align='center'>Description</td>
          </tr>
          <% 
          if( listUserType.size() > 0 )
          {
          %>
          <logic:iterate id="usertype" name="listUserType">
			<tr onmouseover="this.style.cursor='hand'; this.style.backgroundColor='<%=highlightColor%>';this.style.padding='<%=padding%>'" onmouseout="this.style.backgroundColor='';"> 
			<td class="tbldata_chk" height='30'><input type="radio" name="checkbox2" value='<bean:write name="usertype" property="userTypeId"/>'></td>
			<td  class="tbldata" onclick="callView('<bean:write name="usertype" property="userTypeId"/>')"><bean:write name="usertype" property="userType"/>&nbsp;</td>
			<td class="tbldata" onclick="callView('<bean:write name="usertype" property="userTypeId"/>')">
			<logic:notEmpty  name="usertype" property="parentUserType">
			<bean:define type="com.savant.pricing.securityadmin.valueobject.UserTypesVO" id="sample" name="usertype" property="parentUserType"/>
			<bean:write name="sample" property="userType"/>&nbsp;
			</logic:notEmpty>
			<logic:empty  name="usertype" property="parentUserType">
			&nbsp;
			</logic:empty></td>
			<td class="tbldata" onclick="callView('<bean:write name="usertype" property="userTypeId"/>')"><bean:write name="usertype" property="description"/>&nbsp;</td>
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