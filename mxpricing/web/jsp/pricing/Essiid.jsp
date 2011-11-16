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
<%@ page import="com.savant.pricing.valueobjects.ESIIDDetailsVO"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.EssiidForm" />
<jsp:setProperty name="frm" property="*" />
<%
		int browserHt = 0;
		int totalCount=1;
		int pageCount = 1;
		int maxItems = 0;
		int totalPages = 0;
		String str = "";
		boolean order = true;
		List lsResult = null;
		String pageAction = request.getParameter("pageAction");
	 	if(pageAction==null)
	  	  pageAction = (String)session.getAttribute("pageAction");
	  	else
		 session.setAttribute("pageAction",pageAction);
		String customerId = (String)request.getSession().getAttribute("customerId");
		ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
		String pageUser = (String)request.getSession().getAttribute("pageUser");
		try
		{
			maxItems = Integer.parseInt(frm.getMaxItems());
			pageCount = Integer.parseInt(frm.getPage());
		    lsResult = objProspectiveCustomerDAO.getAllESIIDDetails(Integer.parseInt(customerId));
			totalCount = lsResult.size();
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
			pageContext.setAttribute("lsResult",lsResult);
			
			if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
				browserHt = 235;
			else
				browserHt = 220;
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
%>
<html:html>
<head>
<title>Customer Pricing</title>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
</head>
<script>
function navigation()
{
	temp = document.forms[0];
	temp.formActions.value="search";
	temp.page.value = 1;
	temp.submit();
}
function changePageGoto()
{
	temp = document.forms[0];
	temp.formActions.value="search";
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
	temp=document.forms[0];
	temp.formActions.value="navigation";
	temp.page.value  =0;
	temp.submit();
}
function callupload()
{
	var temp=document.forms[0];
	temp.action='<%=request.getContextPath()%>/jsp/pricing/ProspectiveCustomerUsageFile.jsp';
	temp.pageAction.value = '<%=pageAction%>';
	temp.submit();
}
function pageIncrement()
 {
 	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) + 1;
	temp.page.value  = page;
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
	temp.pageAction.value = '<%=request.getParameter("pageAction")%>'

}
function search()
{
	temp=document.forms[0];
	temp.formActions.value="search";
	temp.submit();
}
function clearSearch()
{
	try
		{
			temp=document.forms[0];
			temp.txtCustomerName.value="";
			temp.txtCustomerId.value="";
			
			temp.searchCustomerName.selectedIndex =0;
			temp.cmbCDRStatus.selectedIndex =0;
			temp.cmbCustomerStatus.selectedIndex =0;
			temp.submit();
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
function callReset()
{
var temp = document.forms[0];
temp.txtEssiid.value = "";
}
function callAdd()
{
	var temp = document.forms[0];
	if(temp.txtEssiid.value!="")
	{
		temp.formActions.value='add';
		temp.submit();
	}
	else
	{
		alert("Enter a ESIID");
		return false;
	}
}
function checkEnter()
{
      var characterCode ;
          characterCode = event.keyCode ;
     if((characterCode >=48 )&& (characterCode <=57 ))
             return true;
       else
             return false; 
}
function customerEdit()
{
	try{
	var temp = document.forms[0];
	temp.action='<%=request.getContextPath()%>/ProspectiveCustomerRepEdit.do?prsCustId='+<%=customerId%>+'&User=<%=pageUser%>';
	temp.formActions.value="edit";
	temp.submit();
	}catch(err)
	{
	}
}
function preference(){
var temp = document.forms[0];
temp.action="<%=request.getContextPath()%>/frmPreferenceRepEdit.do?formActions=edit";
temp.submit();
}

</script>
<body onload ="loadDefault()"> 
<html:form action="essiidlist">  
<html:hidden property="formActions" />
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
<html:hidden property="page"/>
<input type='hidden' name='pageAction'/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/>
 <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title">ESIID</td> 
		  <td class="page_title">&nbsp;</td>
        </tr> 
      </table>
      <font class='error'>
      <html:errors /></font>
      <font class='message'><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages></font>
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
      </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <%if(pageAction!=null && pageAction.equalsIgnoreCase("edit")){%>
            <td width="112" class="subtab_off" id="set1"><a href="#" onclick="customerEdit()">Customer&nbsp;Details</a></td>
             <td width="76" class="subtab_on" >ESIID</td> 
            <td width="76" class="subtab_off"><a href="#" onclick="preference()">Preferences</a></td>
            <%}
            else
            {%>
			<td width="112" class="subtab_off" id="set1">Customer&nbsp;Details</td>
            <td width="76" class="subtab_on" >ESIID</td> 
            <td width="76" class="subtab_off"><a href="<%=request.getContextPath()%>/jsp/pricing/PreferenceRepAdd.jsp">Preferences</a></td>
          <%}%>
            <td width="76" class="subtab_off"><a href = "#" onclick='callupload()'>Usage&nbsp;Files</a></td>
			<td width="611">&nbsp;</td>
          </tr>
        </table>
        
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="subtabbase"><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
	  
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr id="tblPremium">
            <td>
              <table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
                  <td width="188" class="fieldtitle">ESIID</td>
                  <td width="10" class="fieldtitle">:</td>
                  <td width="400" class="fieldtitle">
				<html:text property="txtEssiid" maxlength="50" size="40" styleClass="textbox" onkeypress="return checkEnter(event)"/>
				<html:button property="Button" value="Add"  onclick="callAdd();" />
				  <td  class="fieldtitle"><table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
            <tr>
               <td><a href="#" onclick ="callReset();">Reset</a> | <a href="#" onclick="customerEdit()">Cancel</a></td>
			  </tr></table></td>
                  
				</tr>
              </table>
              <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
              <table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr class='staticHeader'>
			       <td width="100" class="tblheader">ESIID(s)</td>
				   <td width="150" class="tblheader">Service Address</td> 
				   <td width="100" class="tblheader">City </td>
				   <td width="100" class="tblheader">State </td>
				   <td width="100" class="tblheader">Zip</td> 
				   <td width="100" class="tblheader">County </td>
				   <td width="100" class="tblheader">TDSP </td>
				  </tr>
				  <logic:iterate id="esiid" name="lsResult" indexId="i">
		  <tr>
				   	<%
				int val = i.intValue();
				str=((ESIIDDetailsVO)lsResult.get(val)).getEsiId();
				if(str.length()>35)
				{
					str = str.substring(0,35)+"...";
				}
			%>
		   	<td  width="100" class="tbldata" title='<bean:write name="esiid" property="esiId"/>'>
								<%=str%>&nbsp;</td>
			<td  width="100" class="tbldata"><bean:write name="esiid" property="serviceAddress1" ignore="true"/> <bean:write name="esiid" property="serviceAddress2" ignore="true"/></td>
			<td width="100" class="tbldata"><bean:write name="esiid" property="city" ignore="true"/>&nbsp;</td>
			<td width="100" class="tbldata"><bean:write name="esiid" property="state" ignore="true"/>&nbsp;</td>
			<td width="100" class="tbldata"><bean:write name="esiid" property="zip" ignore="true"/>&nbsp;</td>
			<td width="100" class="tbldata">&nbsp;</td>
			<td width="100" class="tbldata"><bean:write name="esiid" property="tdsp" ignore="true"/>&nbsp;</td>
		 </tr>
         </logic:iterate>
			  </table>
            </td>
          </tr>
        </table>
        </div>
	 <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="sort"> 
        <tr> 
          <td width="100">Page <%=pageCount%> of <%=totalPages%></td> 
          <td width="150">Items <%=totalCount>0?(pageCount*maxItems)-maxItems+1:0%> - <%=((maxItems*pageCount)>totalCount)?totalCount:(maxItems*pageCount)%> of <%=totalCount%></td> 
          <td>Show 
		<html:radio property="maxItems" value="10" onclick="changePage()"/> 10 
		<html:radio property="maxItems" value="20" onclick="changePage()"/> 20 
		<html:radio property="maxItems" value="50" onclick="changePage()"/> 50 
		<html:radio property="maxItems" value="100" onclick="changePage()"/> 100 Items/Page </td> 
          <td width="180" class="nav_page_right">
           <%
					if(Integer.parseInt(frm.getPage())>1)
	    	{%> 
          <a href="#" style="color:blue" onclick="pageDecrement()" ><img src='<%=request.getContextPath()%>/images/previous.gif' align="bottom" alt="Previous" border="0"> Previous</a> 
            <%
            }
            %> 
            Goto <html:select property="page" onchange="changePageGoto()"> 
            <%for(int i=0;i<totalPages;i++)
            {
            %> 
            <option value="<%=(i+1)%>"><%=(i+1)%></option> 
            <%}%> 
            </html:select> 
            <%if((Integer.parseInt(frm.getPage())>1) && (Integer.parseInt(frm.getPage())<totalPages))
			 {
            }if(Integer.parseInt(frm.getPage())<totalPages)
							    {%> 
            <a href="#" style="color:blue" onclick="pageIncrement()">Next <img src='<%=request.getContextPath()%>/images/next.gif' align="bottom" alt="Next" border="0"></a> 
            <%}%> </td> 
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