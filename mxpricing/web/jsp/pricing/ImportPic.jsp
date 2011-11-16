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
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.savant.pricing.common.NumberUtil"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.FileUploadForm" />
<%@ page import="com.savant.pricing.calculation.dao.LoadExtrapolationDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.PICVO"%>
<%@ page import="com.savant.pricing.common.chart.PICChart"%>
<%@ page import="com.savant.pricing.transferobjects.AnnualkWhDetails"%>
<%@ page import="com.savant.pricing.transferobjects.MonthDetails"%>
<jsp:setProperty name="frm" property="*" />
<%
try
{
if(request.getParameter("prsCustId")!= null && !request.getParameter("prsCustId").equals("") )
{
	frm.setPrsCustId(request.getParameter("prsCustId"));
}
else
{
	frm.setPrsCustId((String)request.getAttribute("prsCustId"));
}
NumberFormat num = NumberFormat.getIntegerInstance();
NumberFormat df = NumberUtil.doubleFraction();
NumberFormat nf = new DecimalFormat("0.0");
ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
ProspectiveCustomerVO  objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(frm.getPrsCustId()));
LoadExtrapolationDAO objLoadExtrapolationDAO = new LoadExtrapolationDAO();
AnnualkWhDetails objAnnualkWhDetails = null;
PICVO objPICVO=new PICVO();
List custDetails = null;
PICChart objchart = new PICChart();
List picUsage= new ArrayList();
List noOfDays = new ArrayList();
List readDayOfMonth=new ArrayList();
List kW = new ArrayList();
List kWh = new ArrayList();
List kVA = new ArrayList();
double loadFactorTotal = 0.0;
int count =1;
        try
        {
        custDetails = objLoadExtrapolationDAO.getAllESIID(Integer.parseInt(frm.getPrsCustId()));
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
        pageContext.setAttribute("essiids",custDetails);
         if(custDetails.size()>0)
         {
        objPICVO =(PICVO) custDetails.get(0)==null?null:(PICVO) custDetails.get(0);
       	loadFactorTotal = objLoadExtrapolationDAO.getLoadFactor(Integer.parseInt(frm.getPrsCustId()));
        picUsage = objLoadExtrapolationDAO.getMonthDetailsByESIID(objPICVO.getEsiId(),Integer.parseInt(frm.getPrsCustId()))==null?null:objLoadExtrapolationDAO.getMonthDetailsByESIID(objPICVO.getEsiId(),Integer.parseInt(frm.getPrsCustId()));
        List lstannual = (List)new com.savant.pricing.dao.PICDAO().getTotalkWh(Integer.parseInt(frm.getPrsCustId()),objPICVO.getEsiId());
        objAnnualkWhDetails = (AnnualkWhDetails)lstannual.get(0);
        
        }
	        
        pageContext.setAttribute("picUsage",picUsage);
        pageContext.setAttribute("objPICVO",objPICVO);
         
        int browserHt = 0;
	  	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
			browserHt = 210;
		else
			browserHt = 190;
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/mm_menu.js"></script>
<script src='<%=request.getContextPath()%>/script/commonSort.js'></script>
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script>
	function prevYear(obj)
	{
		var crntYear = parseInt(obj.parentNode.children(1).innerText,10);
		obj.parentNode.children(1).innerHTML = "&nbsp;"+(crntYear - 1)+"&nbsp;";
	}
	function nextYear(obj)
	{
		var crntYear = parseInt(obj.parentNode.children(1).innerText,10);
		obj.parentNode.children(1).innerHTML = "&nbsp;"+(crntYear + 1)+"&nbsp;";
	}
	
	function callRep()
{
	document.getElementById('esiiddetails').style.display='block';
	document.getElementById('tblChart').style.display='none';
	document.getElementById('Chart').className='subtab_off';
	document.getElementById('Report').className='subtab_on';
}
function callpreference()
{
	try{
	var temp = document.forms[0];
	temp.action='<%=request.getContextPath()%>/PreferenceAnalyst.do?prsCustId=<%=frm.getPrsCustId()%>';
	temp.formActions.value="next";
	temp.submit();
	}catch(err)
	{
	}
}
function callCht()
{
	document.getElementById('tblChart').style.display='block';
	document.getElementById('esiiddetails').style.display='none';
	document.getElementById('Chart').className='subtab_on';
	document.getElementById('Report').className='subtab_off';
}
function submits(formaction)
{
	var temp = document.forms[0];
	temp.formActions.value= 'upload';
	temp.prsCustId.value = '<%=frm.getPrsCustId()%>'
	temp.submit();
}

function servletaccess(message)
	{
	try{
       	var temp = document.forms[0];
       	var esiid;
       	if(message == 'combo')
		 esiid = temp.esiids.value;
		else
		 esiid = document.getElementById('txtesiid').innerText
        if(message!="combo")
        	esiid = esiid.split(" ESIID : ")[1];
		var prsCustId = '<%=frm.getPrsCustId()%>'
		var param = 'Esiid='+esiid+'&prsCustId='+prsCustId+'&message='+message+'&time='+new Date();
		var url = '<%=request.getContextPath()%>/servlet/picContent';
		if (window.XMLHttpRequest) // Non-IE browsers
		{
		
			req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse});
		}
		else if (window.ActiveXObject) // IE
		{
			req = new ActiveXObject("Microsoft.XMLHTTP");
			if (req)
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse});
			}
		}
		}catch(err)
		{
		alert(err.description);
		}
}

	function showResponse(req)
	{
	try{
		var temp = document.forms[0];
		var a =req.responseText.split("@@@");
		document.getElementById('esiiddetails').innerHTML = a[0];
		document.getElementById('tblChart').innerHTML = a[1];
		document.getElementById('txtesiid').innerText = a[2];
		document.getElementById('idcount').innerText = a[3];
		document.getElementById('custdetails').innerHTML = a[4];
    	document.getElementById('decimage').innerHTML = a[5];
    	document.getElementById('incimage').innerHTML = a[6];
    	document.getElementById('annual').innerHTML = a[7];
    	if(a[2]== "All ESIID")
    	   temp.esiids.value = 0;
    	else
    	  temp.esiids.value = a[2].split(" ESIID : ")[1];
		}
		catch(err){
		
			alert(err.description);
		}
	}
	function importToCMS()
	{
	  var pageAction = document.forms[0];
	  if(!confirm("Warning!\nExport to CMS?"))
	  {
		return;
	  }
	  else
	  {	
	  	pageAction.formActions.value = 'exportToCMS';
	  	pageAction.submit();
	  }	
	}
	
	function deleteESIID()
	{
	  var pageAction = document.forms[0];
	  if(!confirm("Warning!\nDelete the selected ESIID?"))
	  {
		return;
	  }
	  else
	  {	
	  	pageAction.totEsiids.value = '<%=custDetails.size()%>';
	  	pageAction.formActions.value = 'deleteESIID';
	  	pageAction.submit();
	  }	
	}
	
	function loadDefault()
	{
	<% 
		if(custDetails.size()>1)
        {
    %> 
		    var temp= document.forms[0];
		    temp.esiids.value = 0;
		    servletaccess('combo');
	<%   
		}
	%>
	}
</script>
<style type="text/css">
<!--
.style2 {color: #000000}
-->
</style>
</head>
<body onload="loadDefault()"> 
<html:form action="/FileUpload" method="post" enctype="multipart/form-data">
<html:hidden property="prsCustId" />
<html:hidden property="formActions"/>
<html:hidden property="totEsiids"/>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
    <td  valign="top"> <jsp:include page="/jsp/menu.jsp"/> 
	  <validator:errorsExist>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
			<tr> 
	         <td class="error">
				<html:errors/>
	         </td>
	        </tr>
	     </table>
	  </validator:errorsExist>
	  <logic:messagesPresent message="true" >
		<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
			<tr> 
	         <td class="message">
				<html:messages id="messageid" message="true"><bean:write name="messageid" /></html:messages>
	         </td>
	        </tr>
	    </table>
	  </logic:messagesPresent>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title"> Prospective Customers</td> 
          <td class="page_title">&nbsp;</td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="112" class="subtab_off" id="set1"><a href="<%=request.getContextPath()%>/jsp/pricing/ProspectiveCustomersAnalystEdit.jsp?prsCustId=<%=frm.getPrsCustId()+""%>" >Customer&nbsp;Details </a></td> 
          <td width="78" class="subtab_on" >Import&nbsp;CUD</td> 
          <td width="82" class="subtab_off" ><a href="<%=request.getContextPath()%>/PreferenceAnalyst.do?prsCustId=<%=frm.getPrsCustId()%>" >&nbsp;Select ESIIDs</a></td>
          <%if(!objProspectiveCustomerVO.isMmCust()) 
          {
          %>
		    <td width="82" class="subtab_off" ><a href="<%=request.getContextPath()%>/Ecomp.do?prsCustId=<%=frm.getPrsCustId()%>" >&nbsp;Select&nbsp;Components</a></td> 
	    <%}%>
          <td width="80" class="subtab_off"><a href="<%=request.getContextPath()%>/DealLeverAnalyst.do?prsCustId=<%=frm.getPrsCustId()%>">Deal&nbsp;Adjustment</a></td> 
          <td>&nbsp;</td> 
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
      <div style="border:solid 1px silver;overflow:auto; height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
        <tr> 
          <td width="120" class="fieldtitle">Customer Name</td> 
          <td width="1" class="fieldtitle">:</td> 
          <td width="200" class="fieldata"><%=objProspectiveCustomerVO.getCustomerName()==null?"":objProspectiveCustomerVO.getCustomerName()%></td> 
          <td width="120" class="fieldtitle">Customer DBA</td> 
          <td width="1" class="fieldtitle">:</td> 
          <td width="200"  class="fieldata"><%=objProspectiveCustomerVO.getCustomerDBA()==null?"":objProspectiveCustomerVO.getCustomerDBA()%></td> 
          <td width="117" class="fieldtitle">Customer ID</td> 
          <td width="1" class="fieldtitle">:</td> 
          <td width="90"  class="fieldata"><%=objProspectiveCustomerVO.getCustomerId()==null?"":objProspectiveCustomerVO.getCustomerId()+""%></td> 
          <td width="130" bgcolor='#FFFFBE'><strong>Overall Load Factor</strong></td> 
          <td width="1" bgcolor='#FFFFBE'>:</td> 
          <td bgcolor='#FFFFBE' class="fieldata"><%=df.format(loadFactorTotal)%></td> 
        </tr> 
      </table> 
      <br> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="117" class="fieldtitle">CUD File Path </td> 
          <td width="10" class="fieldtitle">:</td> 
          <td class="fieldtitle" width="865"> <html:file maxlength="1000" size="70" styleClass="botton"  property="theFile" onkeypress="return false"/> <span class="fieldata"> 
            <input name="ImportFile2" type="button" class="button" id="ImportFile" onclick="submits('upload')" value="Import File"> 
           &nbsp;&nbsp;
           <%if(custDetails.size()>0)
              {
              %>
           <!--  <a href='javaScript:importToCMS()'>Export to CMS</a>
             | --> <a href='javaScript:deleteESIID()'>Delete</a>
             <%
              }
              %>
             </span></td> 
        </tr> 
      </table> 
      <logic:notEmpty name="objPICVO" property="esiId"> 
      <fieldset> 
      <table width="100%" border="0" cellpadding="0" cellspacing="0"> 
        <tr class="tblhead"> 
          <td width="10%" align="right" id='decimage' ><%if(count>1&&custDetails.size()>count)
              {%> 
            <a href="#" onclick = "servletaccess('dec')" > <img  src="<%=request.getContextPath()%>/images/prev.gif" alt="Previous" width="6" height="12" border="0"> </a> 
            <%} %> 
		  </td> 
          <td width="25%" align="center" id="txtesiid" >&nbsp;ESIID :&nbsp;<%=objPICVO.getEsiId()%></td> 
          <td width="3%" align="left" id='incimage'> <%if(custDetails.size()>count)
                {%> 
 		<a href="#" onclick = "servletaccess('inc')" > <img src="<%=request.getContextPath()%>/images/nxt.gif" alt="Next" width="6" height="12" border="0"> </a>&nbsp;&nbsp; 
            <%
                   }
                   %> </td> 
          <td width="13%" > 
			<html:select property="esiids" styleClass="Combo" onchange="servletaccess('combo')"> 
			<html:options collection="essiids" property="esiId" labelProperty="esiId"/> 
			<%if(custDetails.size()>1){%>
				<html:option value="0">All ESIID</html:option> 
				<%}%>
            </html:select>&nbsp;&nbsp;&nbsp;</td> 
          <td width="15%" id="idcount" align="left"> ESIID <%=count%> of <%=custDetails.size()%></td> 
          <td id="annual" align="left" style="color:#0033CC"> Annual kWh: <%=num.format(objAnnualkWhDetails.getKWh())%> (<%= df.format(objAnnualkWhDetails.getKWhPercentage())+" %"%>)</td> 
        </tr> 
      </table> 
      <br> 
      <div style="width:100%; overflow:auto; display:block" id="custdetails"> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="189" class="fieldtitle">Customer </td> 
            <td width="10" class="fieldtitle">:</td> 
            <td class="fieldata" width="245" id="customername"><%=objPICVO.getCustomerName()==null?"":objPICVO.getCustomerName()%></td> 
            <td width="191" class="fieldtitle">Service Address</td> 
            <td width="10" class="fieldtitle">:</td> 
            <td width="347" class="fieldata"><%=objPICVO.getAddress1()==null?"":objPICVO.getAddress1()%></td> 
          </tr> 
          <tr> 
            <td width="189" class="fieldtitle">Rate Class </td> 
            <td width="10" class="fieldtitle">:</td> 
            <td class="fieldata" width="245"><%=objPICVO.getRateCode().getRateCode()%></td> 
            <td width="191" class="fieldtitle">Read Cycle </td> 
            <td width="10" class="fieldtitle">:</td> 
            <td width="347" class="fieldata"><%=objPICVO.getMeterReadCycle()%></td> 
          </tr> 
          <tr> 
            <td width="189" class="fieldtitle">Service Zip </td> 
            <td width="10" class="fieldtitle">:</td> 
            <td class="fieldata" width="245"><%=objPICVO.getZipCode().getZipCode()%></td> 
            <td width="191" class="fieldtitle">Load Profile </td> 
            <td width="10" class="fieldtitle">:</td> 
            <td width="347" class="fieldata"><%=objPICVO.getProfileDetails().getLoadProfile()%></td> 
          </tr> 
        </table> 
      </div> 
      <br> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="83" class="subtab_off" id="Report"><a href="#" onClick="callRep()" >Report</a></td> 
          <td width="52" class="subtab_on" id="Chart" ><a href="#" onClick="callCht()" >Chart</a></td> 
          <td >&nbsp;</td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td class="subtabbase"><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
      <br> 
      <div style="width:100%; overflow:auto; display:none" id="esiiddetails"> 
        <table width="100%" height="136"  border="0" cellpadding="0" cellspacing="0"> 
          <tr class="tblheader"> 
            <td width="107"> &nbsp; Month </td> 
            <td width="72" align="center">Jan</td> 
            <td width="67" align="center">Feb</td> 
            <td width="71" align="center">Mar</td> 
            <td width="67" align="center">Apr</td> 
            <td width="77" align="center">May</td> 
            <td width="70" align="center">Jun</td> 
            <td width="80" align="center">Jul</td> 
            <td width="72" align="center">Aug</td> 
            <td width="72" align="center">Sep</td> 
            <td width="72" align="center">Oct</td> 
            <td width="73" align="center">Nov</td> 
            <td width="73" align="center">Dec</td> 
          </tr> 
          <%
      List picusagevalue =((List)pageContext.getAttribute("picUsage"));
      List loadFactor = new ArrayList();
       float loadfactor=0;
           for(int i=0;i< picusagevalue.size();i++)
          { 
           noOfDays.add(new Integer(((MonthDetails)picusagevalue.get(i)).getNoOfDays()));
           readDayOfMonth.add(new Date(((MonthDetails)picusagevalue.get(i)).getMeterReadDate()));
           kW.add(new Float(((MonthDetails)picusagevalue.get(i)).getHistoricalDemand()));
           kVA.add(new Float(((MonthDetails)picusagevalue.get(i)).getHistoricalApparentPower()));
           kWh.add(new Float((((MonthDetails)picusagevalue.get(i)).getPicUsage())));
           
           
           if((((MonthDetails)picusagevalue.get(i)).getPicUsage()) == 0.0 ||((MonthDetails)picusagevalue.get(i)).getHistoricalDemand() == 0.0 || ((MonthDetails)picusagevalue.get(i)).getNoOfDays() == 0 )
               loadFactor.add(i,new Float(0.0)) ;
           else
           {
              loadfactor = (((((MonthDetails)picusagevalue.get(i)).getPicUsage())*100)/(((MonthDetails)picusagevalue.get(i)).getHistoricalDemand()*((MonthDetails)picusagevalue.get(i)).getNoOfDays()*24));
              loadFactor.add(i,new Float(loadfactor)) ;
           }
         }
      %> 
          <tr > 
            <td class="list_data"><strong>Read Date </strong></td> 
            <logic:iterate id="picusage" name="picUsage"> 
            <td width="72" class="list_data" align="right"><bean:write name="picusage" property="meterReadDate"/></td> 
            </logic:iterate> </tr> 
          <tr > 
            <td class="list_data"><strong>No of Days </strong></td> 
            <logic:iterate id="picusage" name="picUsage"> 
            <td width="72" class="list_data" align='right'><bean:write name="picusage" property="noOfDays"/></td> 
            </logic:iterate> </tr> 
          <tr > 
            <td class="list_data"><strong>kWh</strong></td> 
            <logic:iterate id="picusage" name="picUsage"> 
            <td width="72" class="list_data" align='right'><bean:write name="picusage" property="picUsage" format="#,###"/></td> 
            </logic:iterate> </tr> 
          <tr > 
            <td class="list_data"><strong>kVA</strong></td> 
            <logic:iterate id="picusage" name="picUsage"> 
            <td width="72" class="list_data" align='right'><bean:write name="picusage" property="historicalApparentPower" format="0.00##"/></td> 
            </logic:iterate> </tr> 
          <tr > 
            <td class="list_data"><strong>kW</strong></td> 
            <logic:iterate id="picusage" name="picUsage"> 
            <td width="72" class="list_data" align='right'><bean:write name="picusage" property="historicalDemand" format="0.00##"/></td> 
            </logic:iterate> </tr> 
          <tr > 
            <td class="list_data"><strong>Load Factor</strong></td> 
            <%for(int i=0;i<loadFactor.size();i++)
          {
          %> 
            <td width="72" class="list_data" align='right'><%=nf.format(loadFactor.get(i))%></td> 
            <%}
         %> 
          </tr> 
        </table> 
      </div> 
      <div style="width:100%; overflow:auto; display:block" id="tblChart"> <br> 
        <table width="100%"  height="" border="0" cellspacing="0" cellpadding="0"> 
          <tr > 
            <%
         HashMap hmfilenameChart = objchart.chart(session,kW,kWh,kVA,noOfDays,readDayOfMonth,new java.io.PrintWriter(out));
         String chartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + (String)hmfilenameChart.get("chartdays");  
         String kWkVAchartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + (String)hmfilenameChart.get("chartkW");  
         String kwhchartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + (String)hmfilenameChart.get("chartkWh");  
         %> 
            <td width="10">&nbsp;</td> 
            <td width="309"><img src="<%= chartURL%>"  border=0 usemap="#<%=(String)hmfilenameChart.get("chartdays")%>"></td> 
            <td width="288"><img src="<%=kWkVAchartURL%>"  border=0 usemap="#<%=(String)hmfilenameChart.get("chartkW")%>"></td> 
            <td width="288"><img src="<%= kwhchartURL%>"  border=0 usemap="#<%=(String)hmfilenameChart.get("chartkWh")%>"></td> 
            <td width="13">&nbsp;</td> 
          </tr> 
        </table> 
      </div> 
      </fieldset> 
      </logic:notEmpty> 
      </div>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="1%" class="fieldata">&nbsp;</td> 
          <td width="99%" class="fieldata"><input name="Import File" type="submit" class="button" id="Import File" value="Next" onClick="callpreference()"> </td> 
        </tr> 
      </table> 
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
catch(Exception e)
{
e.printStackTrace();
}
}
%>
