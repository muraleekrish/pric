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
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="com.savant.pricing.common.FilterHandler"%>
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.dao.CostOfCapitalDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.CostOfCapitalVO"%>
<jsp:useBean id="frm" class="com.savant.pricing.pconfig.deallevers.CostofCapitalForm" />
<jsp:setProperty name="frm" property="*" />

<%
	int browserHt = 0;
	Properties props;
	props = new Properties();
	InputStream is = this.getClass().getClassLoader().getResourceAsStream("ApplicationResources.properties");
	props.load(is);
	String highlightColor = props.getProperty("list.highlight.color");
	String padding = props.getProperty("list.highlight.padding");
	
	int totalCount=1;
	int pageCount = 1;
	int maxItems = 0;
	int totalPages = 0;
	int startPosition = 0;
	boolean order = true;
	boolean overRide = false;
	HashMap hmResult = new HashMap();
	List listCostCap = null;
	CostOfCapitalDAO objCostOfCapitalDAO = new CostOfCapitalDAO();
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
		if(request.getParameter("formAction") != null)
		{
 			frm.setFormAction(request.getParameter("formAction"));
		}
		Filter costCapfilter  = null;
		Filter fil[]=null;
		if(frm.getFormAction().equals("update")||frm.getFormAction().equals("delete")||frm.getFormAction().equals("List"))
		{
			fil = new Filter[0];
			session.setAttribute("objFilter",fil);
		}
		else if(frm.getFormAction().equalsIgnoreCase("search")||(frm.getFormAction().equalsIgnoreCase("navigation"))||(frm.getFormAction().equalsIgnoreCase("sorting")))
		{
			Vector filter = new Vector();
			if (!frm.getTxtCocs().equals(""))
			{
				filter = objFilterHandler.setFormDetails("cocName",frm.getTxtCocs(),frm.getCmbCocs(),filter);
				fil = new Filter[filter.size()];
			  	filter.copyInto(fil);
			  	costCapfilter = fil[0];
			}
		}
		maxItems = Integer.parseInt(frm.getMaxItems());
		pageCount = Integer.parseInt(frm.getPage());
		hmResult = objCostOfCapitalDAO.getAllCoc(costCapfilter,"cocName",order,((pageCount-1)*maxItems),maxItems);
		totalCount = Integer.parseInt(String.valueOf(hmResult.get("TotalRecordCount")));
		startPosition = Integer.parseInt(frm.getStartPosition());
		startPosition = (maxItems * pageCount)+1;
		listCostCap = (List)hmResult.get("Records");
		java.util.Iterator itr = listCostCap.iterator();
		if(itr.hasNext())
		{
			CostOfCapitalVO objCostOfCapitalVO = (CostOfCapitalVO)itr.next();
			
		}
		pageContext.setAttribute("listCostCap",listCostCap);
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
		
		if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
			browserHt = 208;
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
<script src="<%=request.getContextPath()%>/script/common.js"></script>
<script src="<%=request.getContextPath()%>/script/prototype.js"></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script>
function changePageGoto()
{
	temp = document.forms[0];
	temp.formActions.value="search";
	temp.submit();
}
function modify(message)
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
					subQuery = obj.children[0].children[i].children[0].children[0].value;
					count++;
				}
			}
		}	 
		if(count>1)
		{
		alert("Please select any one Cost of Capital");
		}
		else if(count==1)
		{	
		  temp.action='<%=request.getContextPath()%>/costCapEdit.do?cocId='+subQuery;
	      temp.formAction.value=message;
	      temp.submit();
	    }else{
	alert("Please Select a  Cost of Capital");
	}

}catch(err)
	{
alert(err.description);
}

}
function callmodify(id)
{
	 temp=document.forms[0];
	 temp.action='<%=request.getContextPath()%>/costCapEdit.do?cocId='+id;
     temp.formAction.value='edit';
	 temp.submit();
}

function override(message)
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
				  if(count>0)
					 subQuery +=",";
					subQuery += obj.children[0].children[i].children[0].children[0].value;
					count++;
				}
			}
		}	 
		alert(subQuery);
		if(count<1)
		{
		alert("please Select Deal Adjustments");
		}
		else 
		{	
	      temp.action='<%=request.getContextPath()%>/pcDealLeverEdit.do';
          temp.dealLeverId.value = subQuery;
	      temp.formAction.value=message;
	      temp.submit();
	    }

}catch(err)
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
function search()
{
	temp = document.forms[0];
	temp.formAction.value="search";
	temp.page.value = 1;
	temp.submit();
}

 function pageDecrement()
 {

 	temp = document.forms[0];
	if(parseInt(temp.page.value,10) > 0)
	 {
		page = parseInt(temp.page.value,10) - 1;
 		temp.page.value  = page;
	}
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

 if(parseInt(temp.page.value,10)+1 < parseInt(temp.pageTop.options.length))
	 {
	page = parseInt(temp.page.value,10) + 1;

 	temp.page.value  = page;
	 }
	 temp.formAction.value="navigation";
 	temp.submit();
}
function loadDefault()
{
	
	temp=document.forms[0];
	temp.pageTop.options.value ='<%=frm.getPage()%>';
	temp.txtCocs.value = temp.txtCocs.value;

}
function clearSearch()
{
try{
	temp=document.forms[0];
	temp.txtCocs.value="";
	temp.cmbCocs.selectedIndex = 0;
	
}catch(err)
	{
alert(err.description);
}
}

function overrideChk(chk)
{	
	var ele = chk.checked;
    var url = "<%=request.getContextPath()%>/servlet/updateOverride";
    var param = "override="+ele;
    var req = new ActiveXObject("Microsoft.XMLHTTP");
    if (req)
    {
        req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: empty});
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

/* don't delete the method because this is response for overrideChk() method */
function empty(req)
{
   //alert("req:"+req.responseText);
}

function loadOverRide()
{
	var temp = document.forms[0];
	//temp.overrideCheck.checked = <%=overRide%>;
}


</script>
</head>
<body onload='loadOverRide();'> 
<html:form action="costCap" focus='txtCocs'>
<html:hidden property="formAction"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
<input type="hidden" name="cocId"/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="/jsp/menu.jsp"/>
		<font class="message" ><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages></font>
    	<font class="error"><html:errors/></font>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="250" class="page_title">Cost&nbsp;of&nbsp;Capitals</td> 
            <td class="page_title"><table  border="0" align="right" cellpadding="0" cellspacing="0"> 
               
              </table></td> 
          </tr> 
      </table> 
	   <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="65" class="search">Cost&nbsp;of&nbsp;Capitals</td>
            <td width="1" class="search">:</td>
            <td width="400" class="search"><html:text property="txtCocs" styleClass="TextBox" onkeypress="return checkEnter(event)"/> 
			<html:select property="cmbCocs" styleClass="Combo">
			<html:option value="0">Starts With</html:option> 
			<html:option value="1">Ends With</html:option>
			<html:option value="2">Exactly</html:option>
			<html:option value="3">Anywhere</html:option> </html:select>
            <html:button property="go" styleClass="button_sub_internal" onclick="search()" value="Go"/>
            <html:button property="Button" styleClass="button_sub_internal" value="Clear"  onclick="clearSearch()"/></td>
           <!-- <td width="50" class="search">IsValid </td>
            <td width="1" class="search">:</td>            
            <td class="search"><input type="checkbox" name="overrideCheck" id='overrideId' onClick ='overrideChk(this);'></td>-->
          </tr>
        </table>
     <table width="100%"  border="0" cellpadding="0" cellspacing="0">
     	<tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
     </table>
         <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
          <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="userLst" > 
            <tr class='staticHeader'> 
             
              <%if(order)
            {%>
			<td class="tblheader" align='center' onClick="callSort();" style="cursor:hand" title='Sort by Cost of Capital in Ascending'>Cost&nbsp;of&nbsp;Capitals<img src='<%=request.getContextPath()%>/images/sort.gif' width="7" height="8"></td>
            <%}else{%>
			<td class="tblheader" align='center' onClick="callSort();" style="cursor:hand" title='Sort by Cost of Capital in Decending'>Cost&nbsp;of&nbsp;Capitals<img src='<%=request.getContextPath()%>/images/sort_up.gif' width="7" height="8"></td>
			<%}%>
              <td class="tblheader" align='center'>Value</td> 
              <td width="594" class="tblheader" align='center'>Unit of Measure </td> 
            </tr>
        <% 
          if( listCostCap.size() > 0 )
          {
        %>
         <logic:iterate id="coc" name="listCostCap">
			<tr onmouseover="this.style.cursor='hand'; this.style.backgroundColor='<%=highlightColor%>';this.style.padding='<%=padding%>'" onmouseout="this.style.backgroundColor='';"> 
            
              <td width="250" class="tbldata" onclick="callmodify('<bean:write name="coc" property="cocId"/>')"><bean:write name="coc" property="cocName" /></td> 
              <td width="192" class="tbldata" align='right' onclick="callmodify('<bean:write name="coc" property="cocId"/>')"><bean:write name="coc" property="cocValue" /></td> 
              <td width="594" class="tbldata" onclick="callmodify('<bean:write name="coc" property="cocId"/>')"><bean:write name="coc" property="unit.unit" /></td> 
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
          <td width="250"   class="copyright" align='right'>Design Rights Savant Technologies</td> 
        </tr> 
      </table></td> 
  </tr> 
</table> 
</html:form>
</body>
</html:html>
<%}%>