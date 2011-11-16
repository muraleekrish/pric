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
<%@ page import="java.util.Set"%>
<%@ page import="com.savant.pricing.dao.TDSPDAO"%>
<%@ page import="com.savant.pricing.dao.MeterReadCyclesDAO"%>
<%@ page import = "java.util.Iterator"%>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.pconfiguration.MeterReadRates" />
<jsp:setProperty name="frm" property="*" />
<%

	int browserHt = 0;
try{
	int totalCount=1; 
	int pageCount = 1;
	int maxItems = 0;
	int totalPages = 0;
	HashMap hmResult = new HashMap();
	HashMap hmCycle = new HashMap();
	HashMap hmRecords = new HashMap();
	List lstYear = null ;
	List tdsp = null;
	TDSPDAO objTDSPDAO = new TDSPDAO();
	MeterReadCyclesDAO objMeterReadCyclesDAO = new MeterReadCyclesDAO();
	
	try
	{
	if(request.getParameter("formAction") != null)
	frm.setFormActions(request.getParameter("formAction"));
	
		maxItems = Integer.parseInt(frm.getMaxItems());
    	pageCount = Integer.parseInt(frm.getPage());
		lstYear = objMeterReadCyclesDAO.getAllMeterReadYears();
    	tdsp = objTDSPDAO.getAllTDSP();
		hmCycle = objMeterReadCyclesDAO.getAllReadCycles(Integer.parseInt(frm.getCmbTDSP()));
		
		if(frm.getCmbMeterCycle().equalsIgnoreCase("0"))
		{
			frm.setCmbMeterCycle("");
		}  
		if(frm.getCmbTDSP().equalsIgnoreCase("")||frm.getCmbTDSP().equalsIgnoreCase("0"))
		{
			frm.setCmbTDSP("1");
		}
		if(frm.getSlctYear().equalsIgnoreCase(""))
		{
			frm.setSlctYear(lstYear.get(0).toString());
		}
		hmResult = objMeterReadCyclesDAO.getAllMeterReadDates(Integer.parseInt(frm.getSlctYear()),Integer.parseInt(frm.getCmbTDSP()),frm.getCmbMeterCycle(),((pageCount-1)*maxItems),maxItems) ;
	      if(hmResult!=null)
				{
					hmRecords =(HashMap)hmResult.get("Records");
				}
			totalCount = Integer.parseInt(hmResult.get("TotalRecordCount").toString())/12;
			
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
		pageContext.setAttribute("tdsp",tdsp);
		pageContext.setAttribute("hmCycle",hmCycle);

		if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
			browserHt = 212;
		else
			browserHt = 192;
			
		/* if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
			browserHt -= 400;
		else
			browserHt -= 380; */
	
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
<script language="JavaScript" src="<%=request.getContextPath()%>/script/common.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">


</head>
<script>
	function prevYear(obj)
	{
		var crntYear = <%=frm.getSlctYear()%>;
		prevYearValue(crntYear-1);
	}
	function nextYear(obj)
	{
		var crntYear = <%=frm.getSlctYear()%>;
		nextYearValue(crntYear+1);
	}
	function prevYearValue(year)
	{
		temp = document.forms[0];
		temp.slctYear.value = year;
		temp.submit();
	}
	function nextYearValue(year)
	{
		temp = document.forms[0];
		temp.slctYear.value = year;
		temp.submit();
	}

	function navigation()
	{
		temp = document.forms[0];
		temp.formAction.value="search";
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
	temp.page.value  =1;
	temp.submit();
}
function pageIncrement()
 {
 	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) + 1;
	temp.formAction.value="navigation";
	temp.page.value  = page;
	temp.submit();
}
function loadDefault()
{
	temp=document.forms[0];
	temp.pageTop.options.value ='<%=frm.getPage()%>';
}
function search()
{
	temp=document.forms[0];
	temp.formAction.value="search";
	temp.page.value=1;
	temp.submit();
}

function clearSearch()
{
	try{
		temp=document.forms[0];
		temp.cmbTDSP.value="1";
		temp.cmbMeterCycle.value ="0";
        temp.slctYear.value = '<%=lstYear.get(0)%>';
        temp.page.value = "1";
        search();
	}catch(err)
	{
		alert(err.description);
	}
}
function callCycle()
{
		temp=document.forms[0];
		temp.cmbMeterCycle.value ="0";
        temp.slctYear.value = '<%=lstYear.get(0)%>';
        temp.page.value = "1";
		temp.submit();
}
function selectValue(comboObj,value)
{
   for(i=0;i<comboObj.options.length;i++)
   
    {	
       if(comboObj.options[i].value==value)
         {
			comboObj.options[i].selected=true;
		}
	}	
}
function loadDefault()
{
	temp = document.forms[0];

	var formAction = '<%=frm.getFormActions()%>';
	var maxItems = '<%=frm.getMaxItems()%>';
	var page = '<%=frm.getPage()%>';
	temp.page.value = page;
    temp.slctYear.value = '<%=frm.getSlctYear()%>';
}

function changePageGoto()
{
	temp = document.forms[0];	
	temp.formAction.value="search";
	temp.submit();
}

</script>
<body onload ='loadDefault()'> 
<html:form action="pcMeterReadsList">
<html:hidden property="formAction"/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
   <td  valign="top">  <jsp:include page="../../menu.jsp"/>
   <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
         <td width="250" class="page_title">Meter Read Dates </td>
		 <%-- <td class="page_title">
		 		<table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
	              <tr>
	                <td><a href="#">Add</a> | <a href="#">Modify</a> | <a href="#">Make InValid</a> </td>               -->
	              </tr>        	
      			</table>
      		  </td> --%>
			<td class="page_title">
				<table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
            		<tr>
		               <td><a href="<%=request.getContextPath()%>/MeterReadsAdd.do">Add/Modify</a> </td>
        </tr>
      </table>
	        </td>
        </tr>
      </table>
       <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblSearch">
          <tr>
            <td width="35" height="24" class="search">TDSP</td>
            <td width="1" class="search">:</td>
            <td width="150" class="search">
             <html:select property="cmbTDSP" onchange="callCycle();">
              <html:options collection="tdsp" property="tdspIdentifier" labelProperty="tdspName"/>
              </html:select></td>
            <td width="100" class="search">Meter Read Cycle</td>
            <td width="1" class="search">:</td>
            <td class="search">
              <html:select property="cmbMeterCycle" onchange='search()'>
			    <html:option value="0">Select one</html:option>
				<html:options collection="hmCycle" property="key" labelProperty="value"/>
              </html:select>
			  <html:button property="Clear" value="Clear" styleClass="button_sub_internal" onclick="clearSearch()"/>
			</td>
		  </tr>
        </table>
      
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
          </tr> 
        </table> 
         
	    <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="dvList"> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
           <tr class='staticHeader'>
                <td colspan="13" class="tblhead"><a href="#"><img onclick="prevYear(this)" src="<%=request.getContextPath()%>/images/previous.gif" alt="Previous Year" width="4" height="7" border="0"></a>&nbsp;<%=frm.getSlctYear()%>&nbsp;<a href="#"><img src="<%=request.getContextPath()%>/images/next.gif" onclick="nextYear(this)" alt="Next Year" width="4" height="7" border="0"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Goto
                  <html:select style="background-color:#F6F6F6 " property="slctYear" onchange="search()">
                  <%Iterator iteYear = lstYear.iterator();
                  while(iteYear.hasNext())
                  {
                  int year = ((Integer)iteYear.next()).intValue();
                  %>
                   <option value='<%=year%>'><%=year%></option>
                   <%}%>

                  </html:select>
				</td>
          </tr>
          <tr class='staticHeader'>
            <td width="80" class="tblhead">Cycle</td>
            <td width="80" class="tblhead">Jan</td>
            <td width="80" class="tblhead">Feb</td>
            <td width="80" class="tblhead">Mar</td>
            <td width="80" class="tblhead">Apr</td>
            <td width="80" class="tblhead">May</td>
            <td width="80" class="tblhead">Jun</td>
            <td width="80" class="tblhead">Jul</td>
            <td width="80" class="tblhead">Aug</td>
            <td width="80" class="tblhead">Sep</td>
            <td width="80" class="tblhead">Oct</td>
            <td width="80" class="tblhead">Nov</td>
            <td width="80" class="tblhead">Dec</td>
          </tr>
			<%
           	Set obj = hmRecords.keySet();
           	if( obj.size() > 0 )
	        {
			Iterator itr1 = obj.iterator();
			while(itr1.hasNext())
			{
				String key = String.valueOf(itr1.next());
				String[] arr = key.split(":");
				List objList = (List)hmRecords.get(key);
				pageContext.setAttribute("objList",objList);
	          %>
      <tr>
            <td  class="tbldata" align='right'><%=arr[3]%></td>
            <td  class="tblflydatadate" align='right'><%=objList.size()<1?" ":objList.get(0)==null?" ":objList.get(0)%></td>
            <td  class="tblflydatadate" align='right'><%=objList.size()<2?" ":objList.get(1)==null?" ":objList.get(1)%></td>
            <td class="tblflydatadate" align='right'><%=objList.size()<3?" ":objList.get(2)==null?" ":objList.get(2)%></td>
            <td  class="tblflydatadate" align='right'><%=objList.size()<4?" ":objList.get(3)==null?" ":objList.get(3)%></td>
            <td  class="tblflydatadate" align='right'><%=objList.size()<5?" ":objList.get(4)==null?" ":objList.get(4)%></td>
            <td  class="tblflydatadate" align='right'><%=objList.size()<6?"":objList.get(5)==null?" ":objList.get(5)%></td>
            <td class="tblflydatadate" align='right'><%=objList.size()<7?"":objList.get(6)==null?" ":objList.get(6)%></td>
            <td  class="tblflydatadate" align='right'><%=objList.size()<8?"":objList.get(7)==null?" ":objList.get(7)%></td>
            <td  class="tblflydatadate" align='right'><%=objList.size()<9?"":objList.get(8)==null?" ":objList.get(8)%></td>
            <td  class="tblflydatadate" align='right'><%=objList.size()<10?"":objList.get(9)==null?" ":objList.get(9)%></td>
            <td  class="tblflydatadate" align='right'><%=objList.size()<11?"":objList.get(10)==null?" ":objList.get(10)%></td>
            <td class="tblflydatadate" align='right'><%=objList.size()<12?"":objList.get(11)==null?" ":objList.get(11)%></td>
          </tr>
          <%
      		   }
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
          <td width="100">Page   <%=pageCount%> of <%=totalPages%></td>
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
<%}
catch (Exception e) {
    e.printStackTrace();
}
}
%>