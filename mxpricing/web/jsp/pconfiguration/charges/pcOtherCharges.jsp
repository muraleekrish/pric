<%try{
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
<%@ page import="java.util.List"%>
<%@ page import="com.savant.pricing.dao.EnergyChargeRatesDAO"%>
<%@ page import="com.savant.pricing.dao.CongestionZonesDAO"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pconfiguration.OtherChargesListForm" />
<jsp:setProperty name="frm" property="*" />

<%
	String energy = "0";
	String zone = "0";
	int maxItem = 10;
	int pageCnt = 1;
	if(request.getAttribute("EnergyCharge")!=null)
	{
		energy = (String)request.getAttribute("EnergyCharge");
	}
	if(request.getAttribute("CongestionZone")!=null)
	{
		zone = (String)request.getAttribute("CongestionZone");
	}
	if(request.getAttribute("MaxItem")!=null)
	{
		String max = (String)request.getAttribute("MaxItem");
		maxItem = Integer.parseInt(max);
	}
	if(request.getAttribute("Page")!=null)
	{
		String pageCt = (String)request.getAttribute("Page");
		pageCnt = Integer.parseInt(pageCt);
	}
	int browserHt = 0;	
	EnergyChargeRatesDAO objEnergyChargeRatesDAO = new EnergyChargeRatesDAO();
	CongestionZonesDAO obCongestionZonesDAO = new CongestionZonesDAO();
	int totalCount=1; 
	int pageCount = 1;
	int maxItems = 0;
	int totalPages = 0;
	HashMap hmResult = new HashMap();
	if(request.getParameter("formActions") != null)
	     frm.setFormAction(request.getParameter("formActions"));
	
	if(frm.getFormAction().equalsIgnoreCase("import"))
		frm.setFormAction("List");
	  
	if(frm.getFormAction().equalsIgnoreCase("List")||frm.getFormAction().equalsIgnoreCase("search"))
	{
		maxItems = Integer.parseInt(frm.getMaxItems());
    	pageCount = Integer.parseInt(frm.getPage());
	  	hmResult = objEnergyChargeRatesDAO.getEnergyCharges(Integer.parseInt(energy),((pageCnt-1)*maxItem),maxItem,Integer.parseInt(zone));
		maxItems = maxItem;
		pageCount = pageCnt;
	 }
	List lstEnergyCharges = (List)objEnergyChargeRatesDAO.getAllEnergyChargeTypes();
	List lstEnergyChargesRates = null;
	List lstCongestionChrges = obCongestionZonesDAO.getAllCongestionZones();
	if(hmResult!=null)
		{
			 lstEnergyChargesRates =(List)hmResult.get("Records");
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
	
	pageContext.setAttribute("lstCongestionChrges",lstCongestionChrges);
	pageContext.setAttribute("lstEnergyCharges",lstEnergyCharges);
	pageContext.setAttribute("lstEnergyChargesRates",lstEnergyChargesRates);
	
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 210;
	else
		browserHt = 190;
		
	/* if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt -= 358;
	else
		browserHt -= 338; */
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
	temp.page.value = 1;
	temp.submit();
}

 function pageDecrement()
 {
 	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) - 1;
	temp.formAction.value="search";
	temp.page.value  = page;
	temp.submit();
	}
function changePage()
{
	temp=document.forms[0];
	temp.formAction.value="search";
	temp.page.value  =0;
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
function search()
{
	temp=document.forms[0];
	temp.formAction.value="search";
	temp.page.value  = 1;
	temp.submit();
}
function changePageGoto()
{
	temp = document.forms[0];
	temp.formAction.value="search";
	temp.submit();
}
function clearSearch()
{
try{
	temp=document.forms[0];
	temp.cmbEnergyCharge.value = "0";
}catch(err)
	{
alert(err.description);
}
}
function loadDefault()
{
	temp = document.forms[0];
	//maxItems = maxItem;
    //pageCount = pageCnt;
	var obj = document.getElementById('img_sort');
	var formAction = '<%=frm.getFormAction()%>';
	var maxItems = '<%=maxItem%>';
	var page = '<%=pageCnt%>';
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
}

function callImport()
{
	temp = document.forms[0];
	temp.formAction.value="import";
	temp.submit();
}

</script>
<body onLoad="loadDefault();"> 
<html:form action="pcOtherCharges" method="post" enctype="multipart/form-data">
<html:hidden property="formAction"/>

<table width="100%" height='100%' border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top">  <jsp:include page="../../menu.jsp"/>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="350" class="page_title">Other Charges&nbsp;&nbsp;&nbsp;&nbsp;<font class="message" ><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages></font>
    	<font class="error"><html:errors/></font></td>
          <td class="page_title"><table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
            <tr>
               <td> 
			   <a href="<%=request.getContextPath()%>/otherchargeAdd.do">Add/Modify</a> </td>
            </tr>
          </table></td>
        </tr>
      </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
		 <tr>
		    <td width="100" class="search">Import File</td>
            <td width="1" class="search">:</td>
            <td width="250" class="search" align='left'>
            <html:file property ="othrBrowse" styleClass="botton"  size="50"  onkeypress="return false" />
             </td>
			    <td width="100" align='left' class="search">
				<input type="button" name="import" class="button" value="Import" onclick="callImport();" /></td>
            <td width="1" class="search">&nbsp;</td>
             <td class="search" align='left'>
            &nbsp;
             </td>
		   </tr>
          <tr>
            <td width="100" class="search">Charge Name</td>
            <td width="1" class="search">:</td>
            <td width="150" class="search" align='left'>
             <html:select property="cmbEnergyCharge" onchange='search()'>
                <html:option value="0">Select one</html:option>
				<html:options collection="lstEnergyCharges" property="energyChargeIdentifier" labelProperty="chargeName"/>
               </html:select>
             </td>
             <td width="100" align='left' class="search">Congestion Zone</td>
            <td width="1" class="search">:</td>
             <td class="search" align='left'>
             <html:select property="cmbCongestionZone" onchange='search()'>
                <html:option value="0">Select one</html:option>
				<html:options collection="lstCongestionChrges" property="congestionZoneId" labelProperty="congestionZone"/>
               </html:select>
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
            <td width="26" class="cmbheader">&nbsp;</td>
            <td width="350" class="tblheader" >Energy Charge Name </td>
            <td width="170" class="tblheader">Month - Year</td>
            <td width="170" class="tblheader">Congestion Zone</td>
            <td  width="120" class="tblheader">Value</td>
            <td class="tblheader">Unit</td>
          </tr>
          <% 
            if( lstEnergyChargesRates.size() > 0 )
            {
         %>
		<logic:iterate id="charges"  name="lstEnergyChargesRates">
		  <tr>
            <td class="tbldata">&nbsp;</td>
             <td class="tbldata"><bean:write name="charges" property="energyChargeName.chargeName"/></td>
             <td class="tbldata"><bean:write name="charges" property="monthYear" format="MMM - yyyy"/></td>
             <td class="tbldata">
             <logic:present name="charges" property="congestion">
             <bean:write name="charges" property="congestion.congestionZone" ignore="true"/>
             </logic:present>&nbsp;
			</td>
	         <td class="tbldata"><bean:write name="charges" property="charge" format="0.0000"/></td> 
   			 <td class="tbldata"><bean:write name="charges" property="unit.unit"/></td>
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
	   <%
		pageContext.removeAttribute("lstCongestionChrges");
		pageContext.removeAttribute("lstEnergyCharges");
		pageContext.removeAttribute("lstEnergyChargesRates");%>
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
								if(pageCnt>1)
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
							     if((pageCnt>1) && (pageCnt<totalPages))
							    {
							    %> 
						      <%
							   }
							    if(pageCnt<totalPages)
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
<%}}catch(Exception e)
{
	e.printStackTrace();
}
%>
