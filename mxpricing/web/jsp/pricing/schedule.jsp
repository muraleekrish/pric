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
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.savant.pricing.dao.ScheduleDAO"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%@ page import="com.savant.pricing.dao.ForwardCurveBlockDAO"%>
<%@ page import="com.savant.pricing.dao.GasPriceDAO"%>
<%@ page import="com.savant.pricing.dao.CDRStatusDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.CustomerPreferenceProductsVO"%>
<%@ page import="com.savant.pricing.valueobjects.CustomerCommentsVO"%>
<%@ page import="com.savant.pricing.transferobjects.TeamDetails"%>	
<%@ page import="com.savant.pricing.common.NumberUtil"%>
<%@ page import="java.text.NumberFormat"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.ScheduleListForm" />
<jsp:setProperty name="frm" property="*" />
<%
	ScheduleDAO objScheduleDAO = new ScheduleDAO();
	ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
	ForwardCurveBlockDAO objForwardCurveBlockDAO = new ForwardCurveBlockDAO();
	GasPriceDAO objGasPriceDAO = new GasPriceDAO();
	CDRStatusDAO objCDRStatusDAO = new CDRStatusDAO();
	ProspectiveCustomerVO objProspectiveCustomerVO = new ProspectiveCustomerVO();	
	CustomerPreferenceProductsVO objCustomerPreferenceProductsVO = new CustomerPreferenceProductsVO();
	CustomerCommentsVO objCustomerCommentsVO = null;
	TeamDetails objTeamDetails = null;
	boolean autoRun = false;
	LinkedHashMap lhmTerms = new LinkedHashMap();
	List lst = new ArrayList();
	Set objSet = null;
	
	List lstProspectiveCust =new java.util.LinkedList();
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat mnthFormat = new SimpleDateFormat("MMM yyyy");
	NumberFormat tnf = NumberUtil.tetraFraction();
	
	Date date = objForwardCurveBlockDAO.fwdCurveLastImportedOn();
	Date dategas = objGasPriceDAO.teeNaturalGasPriceLastImportedOn();
	LinkedHashMap lhmProspects = null;
	
	List listCDRStatus = null;
	listCDRStatus = objCDRStatusDAO.getAllCDRStatus();
	
	// get Prospective Customers List
	List lstProspectCust = objScheduleDAO.getProspectivCust();
	
	// get Autorun Customers
	HashMap hmAutorunCust = objScheduleDAO.getAutoRunCust();
	
	// get Total ESIID'd by Customer
	HashMap hmTotEsiids = objScheduleDAO.getTotEsiidbyCust();
	
	// get Total ESIID'd by Customer
	HashMap hmTotValidEsiids = objScheduleDAO.getTotValidEsiidbyCust();
	
	int CMSId = 0;
	if(!frm.getTxtCustomerId().trim().equals(""))
		CMSId = Integer.parseInt(frm.getTxtCustomerId());
	
	lhmProspects = objScheduleDAO.getDealLeverDetails(CMSId, Integer.parseInt(frm.getCmbCDRStatus()), Integer.parseInt(frm.getCmbAutoRun()));
	int browserHt = 0;
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 225;
	else
		browserHt = 205;
		
	pageContext.setAttribute("listCDRStatus",listCDRStatus);
	pageContext.setAttribute("lstProspectCust",lstProspectCust);
	pageContext.setAttribute("hmAutorunCust",hmAutorunCust);
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>

<style type="text/css">
	#dhtmltooltip{
	position: absolute;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	/*filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);*/
	}
</style>

<SCRIPT LANGUAGE="JavaScript">

function checkNumber(e)
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
       if(!(event.keyCode>=48 && event.keyCode<=57))
     	{
     		return false
     	}
       return true; 
     }
}

function search()
{
	temp=document.forms[0];
	temp.formActions.value="search";
	temp.submit();
}

function clearSearch()
{
	try{
		temp=document.forms[0];
		temp.txtCustomerId.value="";
		temp.cmbCDRStatus.selectedIndex = 0;
		temp.cmbAutoRun.selectedIndex = 0;
	}catch(err)
		{
	alert(err.description);
	}
}

var urlString = "";
function loadDefault()
{
	var temp = document.forms[0];
	temp.cmbCDRStatusselectedIndex = '<%=frm.getCmbCDRStatus()%>';
	urlString = '&CMSId='+temp.txtCustomerId.value+'&CDRStatus='+<%=frm.getCmbCDRStatus()%>+
				'&AutoRun='+<%=frm.getCmbAutoRun()%>;
}

function callScheduleExcel()
{
    window.parent.location = '<%=request.getContextPath()%>/servlet/scheduleExcelServlet?'+urlString
}

</script>

<div id ="progress" style="overflow:auto; position:absolute; top:90px; left:891px; display:none; " >
<img src="<%=request.getContextPath()%>/images/preloader_pricing.gif"  align="middle">
</div>
<body style='overflow:hidden' onload="loadDefault();"> 

<div id="dhtmltooltip"></div>
<SCRIPT LANGUAGE="JavaScript">

/****
DHTML tooltip script
***/

	var offsetxpoint=-60 //Customize x offset of tooltip
	var offsetypoint=20 //Customize y offset of tooltip
	var ie=document.all
	var ns6=document.getElementById && !document.all
	var enabletip=false
	if (ie||ns6)
		var tipobj=document.all? document.all["dhtmltooltip"] : document.getElementById? document.getElementById("dhtmltooltip") : ""
	
	function ietruebody()
	{
	return (document.compatMode && document.compatMode!="BackCompat")? document.documentElement : document.body
	}

	function ddrivetip(thetext, thecolor, thewidth)
	{
		if (ns6||ie)
		{
			if (typeof thewidth!="undefined") tipobj.style.width=thewidth+"px"
			if (typeof thecolor!="undefined" && thecolor!="") tipobj.style.backgroundColor=thecolor
			var tempArr = new Array();
			tempArr = thetext.split('+');
			var divTbl= "<table width='100%' border='1' cellspacing='0' cellpadding='0'>";
			divTbl += "<tr><td class='fieldtitle'>Pic Imported On</td><td class='fieldata'>"+tempArr[0]+"&nbsp;</td></tr>";
			divTbl += "<tr><td class='fieldtitle'>Total ESIID's</td><td class='fieldata'>"+tempArr[1]+"&nbsp;</td></tr>";
			divTbl += "<tr><td class='fieldtitle'>Selected ESIID's</td><td class='fieldata'>"+tempArr[2]+"&nbsp;</td></tr>";
			divTbl += "<tr><td class='fieldtitle'>Sales Rep</td><td class='fieldata'>"+tempArr[3]+"&nbsp;</td></tr>";
			divTbl += "<tr><td class='fieldtitle'>Sales Manager</td><td class='fieldata'>"+tempArr[4]+"&nbsp;</td></tr>";
			divTbl += "<tr><td class='fieldtitle'>Contract Start Month</td><td class='fieldata'>"+tempArr[5]+"&nbsp;</td></tr>";
			divTbl += "</table>";

			tipobj.innerHTML=divTbl;
			
			enabletip=true
			return false
		}
	}
	
	function positiontip(e)
	{
		if (enabletip)
		{
			var curX=(ns6)?e.pageX : event.clientX+ietruebody().scrollLeft;
			var curY=(ns6)?e.pageY : event.clientY+ietruebody().scrollTop;
			//Find out how close the mouse is to the corner of the window
			var rightedge=ie&&!window.opera? ietruebody().clientWidth-event.clientX-offsetxpoint : window.innerWidth-e.clientX-offsetxpoint-20
			var bottomedge=ie&&!window.opera? ietruebody().clientHeight-event.clientY-offsetypoint : window.innerHeight-e.clientY-offsetypoint-20
			var leftedge=(offsetxpoint<0)? offsetxpoint*(-1) : -1000
			//if the horizontal distance isn't enough to accomodate the width of the context menu
			if (rightedge<tipobj.offsetWidth)
			//move the horizontal position of the menu to the left by it's width
			tipobj.style.left=ie? ietruebody().scrollLeft+event.clientX-tipobj.offsetWidth+"px" : window.pageXOffset+e.clientX-tipobj.offsetWidth+"px"
			else if (curX<leftedge)
			tipobj.style.left="66px"
			else
			//position the horizontal position of the menu where the mouse is positioned
			tipobj.style.left=(curX+60)+offsetxpoint+"px"
			
			//same concept with the vertical position
			if (bottomedge<tipobj.offsetHeight)
			tipobj.style.top=ie? ietruebody().scrollTop+event.clientY-tipobj.offsetHeight-offsetypoint+"px" : window.pageYOffset+e.clientY-tipobj.offsetHeight-offsetypoint+"px"
			else
			tipobj.style.top=(curY-65)+offsetypoint+"px"
			tipobj.style.visibility="visible"
		}
	}

function hideddrivetip()
{
	if (ns6||ie)
	{
		enabletip=false
		tipobj.style.visibility="hidden"
		tipobj.style.left="-1000px"
		tipobj.style.backgroundColor=''
		tipobj.style.width=''
	}
}

document.onmousemove=positiontip

</script>

<html:form action="schedule"> 
<html:hidden property="formActions"/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> 
    <td valign="top"> <jsp:include page="../menu.jsp"/>
  		<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
      		<tr> 
        		<td width="700" class="page_title">Schedule For <%=sdfDate.format(new Date())%></td>
        		<td align='right' class="page_title"><a href='javascript:callScheduleExcel()'><img border='0' align='right'src="<%=request.getContextPath()%>/images/excel_icon.gif" alt="Export to Excel"></a>&nbsp;&nbsp;&nbsp;</td>
    		</tr> 
    	</table>
    	<table width="100%"  border="0" cellpadding="0" cellspacing="0">
 			<tr> 
      			<td class="message"><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages></td> 
			</tr> 
			<tr> 
  				<td class='error'><html:errors/></td> 
        	</tr> 
   		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"> 
		  	<tr> 
	           <td width="289" class="fieldata" colspan='3'><strong>&nbsp;Forward Curves Last Imported Dates:&nbsp;&nbsp;&nbsp;</strong></td> 
          	   <td width="100" class="fieldata"><b>Energy Price<b></td>
          	   <td width="1" class="fieldata"><b>:<b></td>
          	   <td width="190" class="fieldata"><%=date==null?"":sdf.format(date)%></td>
		   	   <td width="130" class="fieldata"><b>Natural Gas Price</b></td>
		   	   <td width="1" class="fieldata"><b>:<b></td>
          	   <td class="fieldata"><%=dategas==null?"":sdf.format(dategas)%></td>
	    	</tr>
	    	<tr>
	    		<td width="108" class="search">Customer&nbsp;Id</td>
          		<td width="1" class="search">:</td>
          		<td width="180" class="search"><html:text property="txtCustomerId" styleClass="textbox" size="15" maxlength="15" onkeypress="return checkNumber(event)" /></td>
				
				<td width="100" class="search">Approval Status</td>
          		<td width="1" class="search">:</td>
          		<td width="190" class="search">
		          <html:select property="cmbCDRStatus">
		            <html:option value="0">Select one</html:option>
		            <html:options collection="listCDRStatus" property="cdrStateId" labelProperty="cdrState" />
				  </html:select>
		  		</td>
				
				<td width="130" class="search">Auto Run</td>
	            <td width="1" class="search">:</td>
	            <td class="search">
	          	  <html:select property="cmbAutoRun">
		            <html:option value="0">All</html:option>
					<html:option value="1">Yes</html:option>
					<html:option value="2">No</html:option>
				  </html:select>
				<html:button property="Submit2" value="Go!" onclick="search();" styleClass="button_sub_internal"/>
		  		<html:button property="Submit22" onclick = "clearSearch();" value="Clear" styleClass="button_sub_internal"/>
			    </td>
			</tr>
    	 </table>
    	 <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
		  <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')">
		  <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
    		<tr class='staticHeader'> 		       
		        <td width="60" class="tblheader1" align='center'>Customer&nbsp;Id</td> 		        
		        <td class="tblheader1" align='center'>Customer Name</td>
		        <td width="80" class="tblheader1" align='center'>Approval Status</td>
		        <td width="110" class="tblheader1" align='center'>Product(s)</td>  
		        <td width='600'>
		        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		        		<tr><td colspan='8' class="tblheader1">Deal Lever</td></tr>
		        		<tr>
		        			<td width="50" class="tblheader1" align='center'>Terms</td>
		        			<td width="71" class="tblheader1">CC</td>
		        			<td width="71" class="tblheader1">AVP</td>
		        			<td width="71" class="tblheader1">SAF</td>
		        			<td width="71" class="tblheader1">AF</td>
		        			<td width="71" class="tblheader1">BWC</td>
		        			<td width="71" class="tblheader1">OF</td>
		        			<td class="tblheader1">Margin</td>
		        		</tr>
		        	</table>
		        </td> 
      		</tr>
      		<%
      		if(lhmProspects.size()>0)
      		{
           	String strproducts = "";
			pageContext.setAttribute("lhmProspects", lhmProspects);
	        %>
			 <logic:iterate id="Prospects" name="lhmProspects" indexId="cntProspect" >
  				<bean:define id="indxProspect" name="Prospects" property="key"/>
				<tr>
				<%	
					LinkedHashMap lhmProducts = (LinkedHashMap)lhmProspects.get(indxProspect);
					pageContext.setAttribute("lhmProducts", lhmProducts);
					
					int prospectId = Integer.parseInt((String)indxProspect);
					Iterator itrProspectCust = lstProspectCust.iterator();
					while(itrProspectCust.hasNext())
					{
						objProspectiveCustomerVO = (ProspectiveCustomerVO)itrProspectCust.next();
						if(objProspectiveCustomerVO.getProspectiveCustomerId() == prospectId)
							break;
					}
					String cmsId = objProspectiveCustomerVO.getCustomerId()==null?"":objProspectiveCustomerVO.getCustomerId()+"";
					SimpleDateFormat sdfhrmin = new SimpleDateFormat("MM-dd-yyyy HH:mm");
					String importPicOn = "";
					if(objProspectiveCustomerVO.getImportedPICOn()!=null)
						importPicOn = mnthFormat.format(objProspectiveCustomerVO.getImportedPICOn());
					else
						importPicOn = "";
					String toolTip = "";
					objTeamDetails =  objProspectiveCustomerDAO.getTeam(prospectId);
					String salesRep = ""; String salesMgr = "";
					salesRep = objTeamDetails.getSalesRep().getFirstName()+" "+objTeamDetails.getSalesRep().getLastName();
					salesMgr = objTeamDetails.getSalesManager().getFirstName()+" "+objTeamDetails.getSalesManager().getLastName();
					String totEsiid = "";					
					if(hmTotEsiids.get(new Integer(prospectId))!=null)
						totEsiid = ""+hmTotEsiids.get(new Integer(prospectId));
					else
						totEsiid = "";
					String totValEsiid = "";
					if(hmTotValidEsiids.get(new Integer(prospectId))!=null)
						totValEsiid = ""+hmTotValidEsiids.get(new Integer(prospectId));
					else
						totValEsiid = "";
					
					toolTip = importPicOn+"+"+totEsiid+"+"+totValEsiid+"+"+salesRep+"+"+salesMgr+"+"+mnthFormat.format(objProspectiveCustomerVO.getContractStartDate());
		   			autoRun = false;
		   			autoRun = ((Boolean)hmAutorunCust.get(new Integer(prospectId))).booleanValue();
				    if(autoRun)
				    {
					%>
						<td width="60" rowspan='<%=(lhmProducts.size()+1)%>' class="tbldata1" align='right' valign='top' style='color:#00AA00;' onMouseover="ddrivetip('<%=toolTip%>','lightyellow','300')" onMouseout='hideddrivetip()'><%=cmsId%>&nbsp;</td>
					<%	
						}
						else
						{
					%>
						<td width="60" rowspan='<%=(lhmProducts.size()+1)%>' class="tbldata1" align='right' valign='top' onMouseover="ddrivetip('<%=toolTip%>','lightyellow','300')" onMouseout='hideddrivetip()'><%=cmsId%>&nbsp;</td>
					<%	
						}
					%>
				  <td rowspan='<%=(lhmProducts.size()+1)%>' class="tbldata1" valign='top'><%=objProspectiveCustomerVO.getCustomerName()%></td>
				  <td width="80" rowspan='<%=(lhmProducts.size()+1)%>' class="tbldata1" valign='top'><%=objProspectiveCustomerVO.getCdrStatus().getCdrState()%></td>
				  <logic:iterate id="Products" name="lhmProducts" indexId="cntProduct" >
  					<bean:define id="indxProduct" name="Products" property="key"/>
					<%	
						lhmTerms = (LinkedHashMap)lhmProducts.get(indxProduct);
						pageContext.setAttribute("lhmTerms", lhmTerms);
						String strProd = "";
						if(String.valueOf(indxProduct).length()>15)
						{
							strProd = String.valueOf(indxProduct).substring(0,13)+"...";
					%>
							<td width="110" class="tbldata1" valign='top' title='<%=String.valueOf(indxProduct)%>'><%=strProd%></td>
					<%
						}
						else
						{
					%>
							<td width="110" class="tbldata1" valign='top'><%=indxProduct%></td>
					<% } %>
					<td colspan='8' width='600'>
						<table width="100%"  border="0" cellspacing="0" cellpadding="0">
						<logic:iterate id="Terms" name="lhmTerms" indexId="cntTerm" >
  							<bean:define id="indxTerm" name="Terms" property="key"/>
							<%	
								HashMap hmDealLevers = (HashMap)lhmTerms.get(indxTerm);
								pageContext.setAttribute("hmDealLevers", hmDealLevers);
							%>
			        		<tr>
			        			<td width="50" class="tbldata1" align='right'><%=indxTerm%></td>
			        			<td width="70" class="tbldata1" align='right'><%=tnf.format(((Float)hmDealLevers.get(new Integer(1))).floatValue())%></td>
			        			<td width="70" class="tbldata1" align='right'><%=tnf.format(((Float)hmDealLevers.get(new Integer(7))).floatValue())%></td>
			        			<td width="70" class="tbldata1" align='right'><%=tnf.format(((Float)hmDealLevers.get(new Integer(4))).floatValue())%></td>
			        			<td width="70" class="tbldata1" align='right'><%=tnf.format(((Float)hmDealLevers.get(new Integer(5))).floatValue())%></td>
			        			<td width="70" class="tbldata1" align='right'><%=tnf.format(((Float)hmDealLevers.get(new Integer(6))).floatValue())%></td>
			        			<td width="70" class="tbldata1" align='right'><%=tnf.format(((Float)hmDealLevers.get(new Integer(2))).floatValue())%></td>
			        			<td class="tbldata1" align='right'><%=tnf.format(((Float)hmDealLevers.get(new Integer(3))).floatValue())%></td>
			        		</tr>
			        		</logic:iterate>
		        		</table>
					</td>
					</tr>
				</logic:iterate>
				<tr>
				<%
					objSet = objProspectiveCustomerVO.getCustomerComments();
				  	int size = objSet.size();
				    if(size>0)
				    {
					  	Iterator itr = objSet.iterator();
					  	if(itr.hasNext())
					  	{
					  		objCustomerCommentsVO = (CustomerCommentsVO)itr.next();
				  	
				%>
							<td class="tbldata1" style="background-color:lightyellow">Comments</td><td class="tbldata"  style="background-color:lightyellow" WIDTH="590"><%=objCustomerCommentsVO.getComments()%></td>
				<%
						}
					}
					else
					{
				%>
							<td class="tbldata1" style="background-color:lightyellow">Comments</td><td class="tbldata" style="background-color:lightyellow" align="center" >--- No Comments ---</td>
				<%
					}
				%>
				</tr>
      		</logic:iterate>
			<% }
			   else
			   { %>
				<tr>
		 		<td colspan='10'>
				 	<jsp:include page="/jsp/noRecordsFound.jsp"/>
				</td>
			</tr>
			<% }
			   %>
      </table>
	</div>
  </td> 
 </tr>
 <tr>
 	<td height="20" colspan='3' class="sort"><table width="100%" border="0" cellpadding="0" cellspacing="0" > 
		<tr>
			<td width='15'><img border='0' width='11' height='11' src='<%=request.getContextPath()%>/images/automatic_run.gif' alt='Autorun Enabled Customer' /></td>
			<td width='150' style='color:#333333'>&nbsp;Autorun Enabled Customer</td>
			<td style='font-weight:bold; color:blue'>&nbsp;Total Records : <%=lhmProspects.size()%></td>
  		</tr>
  	</table></td>
  </tr> 
  <tr> 
    <td height="20"><table width="100%"  border="0" cellpadding="0" cellspacing="0" > 
        <tr> 
        	<%String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";%>
          <td class="copyright_link"><%=menupath%></td>
          <td width="250"   class="copyright" align='right'>Design Rights Savant Technologies</td> 
        </tr> 
      </table></td> 
  </tr> 
</table> 
</html:form>
</html:html>
<%}%>