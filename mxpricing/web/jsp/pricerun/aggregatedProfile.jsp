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
<%@ page import = "java.io.PrintWriter" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savant.pricing.dao.LoadProfileTypesDAO"%>
<%@ page import="com.savant.pricing.calculation.dao.PricingDAO"%>
<%@ page import="com.savant.pricing.transferobjects.PricingDashBoard"%>
<%@ page import="com.savant.pricing.common.chart.AggLoadProfileChart"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.aggregatorlp.AggLoadProfileForm" />
<jsp:setProperty name="frm" property="*" />
<html:html>
	<head>
	<%
	try
	{
	String priceRunId =null;
	String priceRunRefNo = null;
	HashMap hmTDSPs = new HashMap();
	HashMap hmCongestionZones = new HashMap();
	HashMap hmEsiids = new HashMap();

	if( request.getParameter("PriceRunRefNo")!=null)
	{	
		priceRunRefNo = request.getParameter("PriceRunRefNo");
	}
	
	if( request.getParameter("priceRunId")!=null)
	{	
		priceRunId = request.getParameter("priceRunId");
		frm.setRunRefId(priceRunId);
	}
	HashMap hmLoadProfileAll ;
	LoadProfileTypesDAO objLoadProfileTypesDAO = new LoadProfileTypesDAO();
	hmLoadProfileAll = (HashMap)objLoadProfileTypesDAO.getAggregatedLoadProfileDetails(Integer.parseInt(priceRunId),frm.getEsiids());
	HashMap hmWeekDay = (HashMap)hmLoadProfileAll.get(new Integer(2));
	HashMap hmWeekEnd = (HashMap)hmLoadProfileAll.get(new Integer(3));
	pageContext.setAttribute("hmWeekDay",hmWeekDay);
	pageContext.setAttribute("hmWeekEnd",hmWeekEnd);
	AggLoadProfileChart objAggLoadProfileChart = new AggLoadProfileChart();
	HashMap objcCartName = objAggLoadProfileChart.getchart(session,Integer.parseInt(priceRunId),frm.getEsiids(),new PrintWriter(out));
	String filenameDayChart = (String)objcCartName.get("weekday");
	String filenameEndChart = (String)objcCartName.get("weekend");
	String[] month = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	
	PricingDAO objPricingDAO = new PricingDAO();
	PricingDashBoard objPricingDashBoard = null;
	objPricingDashBoard = objPricingDAO.getDashBoardDetails(Integer.parseInt(priceRunId),12, frm.getEsiids());
	hmTDSPs = objPricingDAO.getAllTDSPs(Integer.parseInt(priceRunId));
	hmCongestionZones = objPricingDAO.getAllCongestionZones(Integer.parseInt(priceRunId));
	hmEsiids = objPricingDAO.getAllEsiIds(Integer.parseInt(priceRunId));
	int browserWd = Integer.parseInt(String.valueOf(request.getSession().getAttribute("browserWidth")));
	int imageWd = 1000*browserWd/1148;
		
	pageContext.setAttribute("hmTDSPs",hmTDSPs);
	pageContext.setAttribute("hmCongestionZones",hmCongestionZones);
	pageContext.setAttribute("hmEsiids",hmEsiids);
	%>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/common.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/slider.js"></script>
<script>
function displaydata(select)
{
    if(select=='weekday')
	 {
	    document.getElementById('tblweekday').style.display = 'block';
		document.getElementById('tblweekend').style.display = 'none';		
        document.getElementById('imgday').style.display = 'block';
		document.getElementById('imgend').style.display = 'none';
	}
	else
	{
	   document.getElementById('tblweekday').style.display = 'none';
		document.getElementById('tblweekend').style.display = 'block';		
        document.getElementById('imgday').style.display = 'none';
		document.getElementById('imgend').style.display = 'block';	
	}
	
}
function servletaccess(message)
{
		var temp = document.forms[0];
		var zone = temp.congestionZones.value;
		var tdsp = temp.tdsps.value;
		var timeanddate = new Date();
		var objSourceElement = temp.esiids;
		var esiId = new Array();
        var x = 0;
         for (var i = 0; i < objSourceElement.length; i++) 
             {
           		if (objSourceElement.options[i].selected) 
               		{
             			 if(objSourceElement.options[i].value != 0)
             			 {
             			 	esiId[x] = objSourceElement.options[i].value;
             			 	x++;
             			 }
               		}
	         }
	         var param = 'ESIID='+esiId+'&Zone='+zone+'&TDSP='+tdsp+'&Message='+message+'&date&time='+timeanddate+'&priceRunId='+<%=priceRunId%>;
	         
	         var url = '<%=request.getContextPath()%>/servlet/AggExcelServlet';
			 showload('yes');
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
}
function showResponse(req)
{
showload('no');
var a =req.responseText.split("***@@");
document.getElementById('tblweekday').innerHTML = a[0];
document.getElementById('tblweekend').innerHTML = a[1];
document.getElementById('imgday').innerHTML = a[2];
document.getElementById('imgend').innerHTML = a[3];
document.getElementById('esiidCount').innerText = a[4];
document.getElementById('comboEsiid').style.display = 'none';
var showImg = "<%=request.getContextPath()%>/images/show.gif";
document.getElementById('imgShowHide3').src = showImg;
document.getElementById('imgShowHide3').title = "Show"
}
function callLoadCombo()
{
	var temp = document.forms[0];
	var tdsp = temp.tdsps.value;
	var zone = temp.congestionZones.value;
	var timeanddate = new Date();
	if(parseInt(tdsp,10)<0)
	return false;
	
	var url = '<%=request.getContextPath()%>/servlet/dashBoardComboContent';
	var param = 'TDSP='+tdsp+'&Zone='+zone+'&timeanddate='+timeanddate+'&priceRunId='+<%=priceRunId%>;
	showload('yes');
	if (window.XMLHttpRequest) // Non-IE browsers
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: loadCombo});
			}
			else if (window.ActiveXObject) // IE
			{
				req = new ActiveXObject("Microsoft.XMLHTTP");
				if (req)
				{
					req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: loadCombo});
				}
			}
}
function loadCombo(req){
var a =req.responseText.split("@#@#");
document.getElementById('congestion').innerHTML = a[0];
document.getElementById('cmbesiid').innerHTML = a[1];
showload('no');
defaultload();
}
function callEsiIdCombo()
{
	var temp = document.forms[0];
	var Zones = temp.congestionZones.value;
	var tdsp = temp.tdsps.value;
	var timeanddate = new Date();
	if(parseInt(Zones,10)<0)
	return;
	var url = '<%=request.getContextPath()%>/servlet/dashBoardComboESIID';
	var param = 'Zone='+Zones+'&TDSP='+tdsp+'&timeanddate='+timeanddate+'&priceRunId='+<%=priceRunId%>;
	showload('yes');
	if (window.XMLHttpRequest) // Non-IE browsers
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: loadComboESIID});
			}
			else if (window.ActiveXObject) // IE
			{
				req = new ActiveXObject("Microsoft.XMLHTTP");
				if (req)
				{
					req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: loadComboESIID});
				}
			}
}
function loadComboESIID(req){
var a =req.responseText.split("@@@");
document.getElementById('cmbesiid').innerHTML = a[0];
defaultload();
showload('no');
}
function defaultload()
{
	var div = document.getElementById('comboEsiid');
	var curleft = curtop = 0;
    var obj = document.getElementById('imgShowHide3');
       if (obj.offsetParent)
          {
            curleft = obj.offsetLeft;
            curtop = obj.offsetTop;
            while (obj = obj.offsetParent)
            {
              curleft += obj.offsetLeft
              curtop += obj.offsetTop
            }
          }
          
    var pic = document.getElementById('imgShowHide3');
	var w = pic.offsetWidth;
	div.style.top = curtop + 12;	
	div.style.left = curleft - w;
}

function exporttoExcel()
{
	var temp = document.forms[0];
		var zone = temp.congestionZones.value;
		var tdsp = temp.tdsps.value;
		var timeanddate = new Date();
		var objSourceElement = temp.esiids;
		var esiId = new Array();
        var x = 0;
         for (var i = 0; i < objSourceElement.length; i++) 
             {
           		if (objSourceElement.options[i].selected) 
               		{
             			 if(objSourceElement.options[i].value != 0)
             			 {
             			 	esiId[x] = objSourceElement.options[i].value;
             			 	x++;
             			 }
               		}
	         }
    window.parent.location = '<%=request.getContextPath()%>/servlet/AggExcelServlet?ESIID='+esiId+'&Zone='+zone+'&TDSP='+tdsp+'&Message=Excel&date&time='+timeanddate+'&priceRunId='+<%=priceRunId%>;
}
function callpage(pagename)
{
	try{
	var temp = document.forms[0];
	temp.action='<%=request.getContextPath()%>/'+pagename+'.do?priceRunId=<%=frm.getRunRefId()%>&PriceRunRefNo=<%=priceRunRefNo%>';
	temp.strESIID.value = localESIID;
	temp.hidTerm.value = localTerm;
	temp.submit();
	}catch(err)
	{
	}
}
var localESIID = '';
var localTerm = '';
function loadDefault()
{
localESIID = '<%=request.getParameter("strESIID")%>';
localTerm = '<%=request.getParameter("hidTerm")%>';
defaultload();
}
function showload(mess)
 {
 	if(mess=='yes')
 		document.getElementById('loadimage').style.display = 'block';
	 else
 		document.getElementById('loadimage').style.display = 'none';
 }
</script>
</head>
<div id ="loadimage" style="overflow:auto; position:absolute; top:68px; left:781px; display:none; " >
	<img src="<%=request.getContextPath()%>/images/preloader_pricing.gif"   align="middle">
</div>
<body onload='loadDefault();'>
<html:form action="/aggLoadProfile" method="post">
<input type='hidden' name='strESIID'/>
<input type='hidden' name='hidTerm'/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top">
        <jsp:include page="/jsp/menu.jsp"/> 
        
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title"> Aggregated Load Profile</td> 
        </tr> 
     </table>
     <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
          </tr> 
        </table> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="83" class="subtab_off" id="set1"><a href="javascript:callpage('dashBoard')" >Dashboard</a></td> 
            <td width="100" class="subtab_on" >Aggregated&nbsp;LP</td>
            <td width="690" align='right'><a href='javascript:exporttoExcel()'><img border='0' align='right'src="<%=request.getContextPath()%>/images/excel_icon.gif" ></a></td>
          </tr> 
        </table> 
            <table width="100%" cellpadding="0" cellspacing="0"> 
              <tr> 
                <td width="117" class="search"><strong>Customer Name</strong></td> 
                <td width="4" class="search">:</td> 
                <td width="217" class="search"><%= objPricingDashBoard.getCustomerName()%>&nbsp;</td> 
                <td width="100" class="search"><strong>ESI ID</strong> </td> 
                <td width="1" class="search">:</td> 
                <td width="199" class="search" id='esiidCount'><%= objPricingDashBoard.getNoOfEsiIds()%> of <%=objPricingDAO.getAllEsiIds(Integer.parseInt(priceRunId)).size()%></td> 
              </tr>                            
            </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">        
      		<tr>
		      	<td width="46" class="search" colspan='3'>&nbsp;</td> 
		        <td width="80" class="search" colspan='3'><input name="radiobutton" type="radio" value="radiobutton" id='weekday' onClick="displaydata('weekday')" checked><label for='weekday'>Week Day</label></td> 
		        <td width="80" class="search" colspan='3'><input name="radiobutton" type="radio" value="radiobutton" id='weekend' onClick="displaydata('weekend')"><label for='weekend'>Week End</label></td> 
		      	</tr>
              <tr> 
                <td width="46" class="search">TDSP</td> 
                <td width="4" class="search">:</td> 
                <td width="113" class="search"> <html:select property="tdsps" styleClass="Combo" onchange="callLoadCombo();servletaccess('TDSP')"> <html:option value="0">Select one</html:option> <html:options collection="hmTDSPs" property="key" labelProperty="value"/> </html:select></td> 
                <td width="114" class="search">Congestion Zone </td> 
                <td width="4" class="search">:</td> 
                <td width="121" class="search" id="congestion"> <html:select property="congestionZones" styleClass="Combo" onchange="callEsiIdCombo();servletaccess('Zone')"> <html:option value="0">Select one</html:option> <html:options collection="hmCongestionZones" property="key" labelProperty="value"/> </html:select></td> 
                <td width="87" class="search">Select ESIIDs</td> 
                <td width="62" class="search">:<a href="javascript:showHideSpl('comboEsiid','imgShowHide3')"><img src="<%=request.getContextPath()%>/images/show.gif" name="imgShowHide3" width="50" height="10" border="0" id="imgShowHide2" align="middle"></a></td> 
                <td width="222"  class="search" id ="cmbesiid"> <div id ="comboEsiid"  class='search' style="overflow:auto; position:absolute; top:180px; left:710px; display:none;" ><html:select property="esiids" multiple="true" styleClass="Combo" size="10" onchange="servletaccess('false');"> <html:option value="0">All</html:option> <html:options collection="hmEsiids" property="key" labelProperty="value"/> </html:select> </div></td> 
              </tr> 
        </table> 
	 <table width="100%" border="0" cellspacing="0" cellpadding="0">	 	 
  <tr>
    <td width="40%" valign="center"><div style="overflow:auto; width:400; height:330;display:block" id='tblweekday'>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20%" class="tblheader" rowspan="2">Month</td>
    <td colspan="3" class="tblheader">On Peak(kW)</td>   
    <td colspan="3" class="tblheader">Off Peak(kW)</td>    
  </tr>
  <tr >
    <td width="12%" class="tblheader">Min</td>
    <td width="12%" class="tblheader">Max</td>
    <td width="12%" class="tblheader">Avg</td>
    <td width="12%" class="tblheader">Min</td>
    <td width="12%" class="tblheader">Max</td>
    <td width="12%" class="tblheader">Avg</td>
  </tr>
  <logic:iterate id="weekday" name="hmWeekDay" indexId="count" >
  <tr>
  <bean:define id="index" name="weekday" property="key"/>
    <td class="tbldata" align='left'><%=month[((Integer)index).intValue()-1]%></td>
    <td class="tbldata" align='right'><bean:write name="weekday" property="value.onPeakMin" format="#,###" ignore="true"/></td>
    <td class="tbldata" align='right'><bean:write name="weekday" property="value.onPeakMax" format="#,###" ignore="true"/></td>
    <td class="tbldata" align='right'><bean:write name="weekday" property="value.onPeakAvg" format="#,###" ignore="true"/></td>
    <td class="tbldata" align='right'><bean:write name="weekday" property="value.offPeakMin" format="#,###" ignore="true"/></td>
    <td class="tbldata" align='right'><bean:write name="weekday" property="value.offPeakMax" format="#,###" ignore="true"/></td>
    <td class="tbldata" align='right'><bean:write name="weekday" property="value.offPeakAvg" format="#,###" ignore="true"/></td>
  </tr>
  </logic:iterate>
</table>
</div>
<div style="overflow:auto; width:400; height:330;display:none" id='tblweekend'>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="20%" class="tblheader" rowspan="2">Month</td>
    <td colspan="3" class="tblheader">On Peak(kW)</td>   
    <td colspan="3" class="tblheader">Off Peak(kW)</td>    
  </tr>
  <tr >
    <td width="12%" class="tblheader">Min</td>
    <td width="12%" class="tblheader">Max</td>
    <td width="12%" class="tblheader">Avg</td>
    <td width="12%" class="tblheader">Min</td>
    <td width="12%" class="tblheader">Max</td>
    <td width="12%" class="tblheader">Avg</td>
  </tr>
  <tr>
     <logic:iterate id="weekend" name="hmWeekEnd" indexId="count" >
  <tr>
  <bean:define id="index" name="weekend" property="key"/>
    <td class="tbldata" align='left'><%=month[((Integer)index).intValue()-1]%></td>
    <td class="tbldata" align='right'><bean:write name="weekend" property="value.onPeakMin" format="#,###" ignore="true"/></td>
    <td class="tbldata" align='right'><bean:write name="weekend" property="value.onPeakMax" format="#,###" ignore="true"/></td>
    <td class="tbldata" align='right'><bean:write name="weekend" property="value.onPeakAvg" format="#,###" ignore="true"/></td>
    <td class="tbldata" align='right'><bean:write name="weekend" property="value.offPeakMin" format="#,###" ignore="true"/></td>
    <td class="tbldata" align='right'><bean:write name="weekend" property="value.offPeakMax" format="#,###" ignore="true"/></td>
    <td class="tbldata" align='right'><bean:write name="weekend" property="value.offPeakAvg" format="#,###" ignore="true"/></td>
  </tr>
  </logic:iterate>
  </tr>
</table>

</div>
</td>
    <td width="60%" id = 'imgday' style="display:block"><%
String dayChartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filenameDayChart; %>
      <img src="<%= dayChartURL%>"  border=0 usemap="#<%=filenameDayChart%>"></td>
<td width="60%" id = 'imgend' style="display:none"><%
String endChartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filenameEndChart; %>
      <img src="<%= endChartURL%>"  border=0 usemap="#<%=filenameEndChart%>"></td>
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
<%
}
catch(Exception e)
{
e.printStackTrace();
}

%>
</html:html>
<%}%>