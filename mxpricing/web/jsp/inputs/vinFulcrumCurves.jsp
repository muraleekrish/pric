<%
try{
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
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.savant.pricing.dao.ForwardCurveBlockDAO"%>
<%@ page import="com.savant.pricing.dao.ForwardCurveProfileDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.ForwardCurveBlockVO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.ForwardCurveProfileVO"%>
<%@ page import="com.savant.pricing.dao.CongestionZonesDAO"%>
<%@ page import="com.savant.pricing.valueobjects.CongestionZonesVO"%>
<%@ page import="com.savant.pricing.common.chart.FulcrumCurve"%>
<%@ page import="com.savant.pricing.dao.PriceBlockDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.PriceBlockHeaderVO"%>
<%@ page import="com.savant.pricing.dao.WeatherZonesDAO"%>
<%@ page import="com.savant.pricing.valueobjects.WeatherZonesVO"%>
<%@ page import="com.savant.pricing.dao.LoadProfileTypesDAO"%>
<%@ page import="com.savant.pricing.valueobjects.LoadProfileTypesVO"%>
<html:html>
	<head>
	<%
	try
	{
	ForwardCurveBlockDAO objForwardCurveBlockDAO = new ForwardCurveBlockDAO();
	HashMap hmBlckdetails = objForwardCurveBlockDAO.getAllForwardCurveBlocks(1,1);
	List lstBlckdetails =new java.util.ArrayList() ;
	Date fwdCurveLastImported = objForwardCurveBlockDAO.fwdCurveLastImportedOn();
	System.out.println(" fwdCurveLastImported :"+fwdCurveLastImported);
	ForwardCurveProfileDAO objForwardCurveProfileDAO = new ForwardCurveProfileDAO();
	List lstprofiledetails = (List)objForwardCurveProfileDAO.getAllForwardCurveProfiles(1,1,1);
	
	CongestionZonesDAO objCongestionZonesDAO = new CongestionZonesDAO();
	HashMap hmCongestionZones = new HashMap();
	HashMap hmWeatherZones = new HashMap();
	HashMap hmProfile = new HashMap();
	ForwardCurveBlockVO blckvo = new ForwardCurveBlockVO();
	List lstcongestionZone = objCongestionZonesDAO.getAllCongestionZones();
	SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
	Iterator iteCongestionZone = lstcongestionZone.iterator();
	while(iteCongestionZone.hasNext())
	{
	CongestionZonesVO objCongestionZonesVO = (CongestionZonesVO)iteCongestionZone.next();
	hmCongestionZones.put(new Integer(objCongestionZonesVO.getCongestionZoneId()),new String(objCongestionZonesVO.getCongestionZone()));
	}
	
	List lst =new PriceBlockDAO().getAllPriceBlock();
	Iterator blckIte = lst.iterator();
	while(blckIte.hasNext())
	{
	 PriceBlockHeaderVO objPriceBlockVO = (PriceBlockHeaderVO)blckIte.next();
	 int prcId = objPriceBlockVO.getPriceBlockIdentifier();
	 List lstprice = (List)hmBlckdetails.get(new Integer(prcId));
	 if(null != lstprice)
	    lstBlckdetails.addAll(lstprice);
  	}
	if(lstBlckdetails.size()>0)
	{
	blckvo = ((ForwardCurveBlockVO)lstBlckdetails.get(0));
	}
	
	List lstWeatherZone = new WeatherZonesDAO().getAllWeatherZones();
	Iterator iteWeatherZone = lstWeatherZone.iterator();
	while(iteWeatherZone.hasNext())
	{
	WeatherZonesVO objWeatherZonesVO = (WeatherZonesVO)iteWeatherZone.next();
	hmWeatherZones.put(new Integer(objWeatherZonesVO.getWeatherZoneId()),new String(objWeatherZonesVO.getWeatherZone()));
	}
	int browserWd = Integer.parseInt(String.valueOf(request.getSession().getAttribute("browserWidth")));
	int imageWd = 1000*browserWd/1148;
	
	
	List lstProfile = new LoadProfileTypesDAO().getAllProfileTypes();
	Iterator iteProfile = lstProfile.iterator();
	while(iteProfile.hasNext())
	{
	LoadProfileTypesVO objLoadProfileTypesVO = (LoadProfileTypesVO)iteProfile.next();
	hmProfile.put(new Integer(objLoadProfileTypesVO.getProfileIdentifier()),new String(objLoadProfileTypesVO.getProfileType()));
	}
	System.out.println(" list size 3:"+lstBlckdetails.size());
	pageContext.setAttribute("hmProfileZones",hmProfile);
	pageContext.setAttribute("hmWeatherZones",hmWeatherZones);
	pageContext.setAttribute("lstBlckdetails",lstBlckdetails);
	pageContext.setAttribute("profiledetails",lstprofiledetails);
	pageContext.setAttribute("hmCongestionZones",hmCongestionZones);
	pageContext.setAttribute("blckvo",blckvo);
	%>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
<script>
function displaydata(select)
{
    if(select=='block')
	 {
	    document.getElementById('hdblock').style.display = 'block';
		document.getElementById('datablock').style.display = 'block';
		document.getElementById('dataprofile').style.display = 'none';
		document.getElementById('hdprofile').style.display = 'none';
        document.getElementById('profilechart').style.display = 'none';
        document.getElementById('blckchart').style.display = 'block';
        document.getElementById('profilesearch').style.display = 'none';
        document.getElementById('blocksearch').style.display = 'block';
         document.getElementById('srcblck').style.display = 'block';
		document.getElementById('srcprofile').style.display = 'none';
	}
	else
	{
	   document.getElementById('hdblock').style.display = 'none';
	   document.getElementById('datablock').style.display = 'none';
	   document.getElementById('dataprofile').style.display = 'block';
	   document.getElementById('hdprofile').style.display = 'block';
       document.getElementById('profilechart').style.display = 'block';
       document.getElementById('blckchart').style.display = 'none';
       document.getElementById('blocksearch').style.display = 'none';
       document.getElementById('profilesearch').style.display = 'block';
       document.getElementById('srcblck').style.display = 'none';
	   document.getElementById('srcprofile').style.display = 'block';
	}
	
}
function displayCombo()
{
   var temp = document.forms[0];
   temp.congestionZones.value = '1';
   temp.weatherzone.value = '1';
   temp.profile.value = '1';
 
}
function displayprofilechart()
{
		try
		{
       	var temp = document.forms[0];
       	var weatherId = temp.weatherzone.value;
   		var profileId = temp.profile.value;
   		var timeanddate = new Date();
		var param = 'weatherZoneId='+weatherId+'&profileId='+profileId+'&timeanddate='+timeanddate;
		var url = '<%=request.getContextPath()%>/servlet/inputEnergyProfile';
		showload('yes');
		if (window.XMLHttpRequest) // Non-IE browsers
		{
			req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponseProfile});
		}
		else if (window.ActiveXObject) // IE
		{
			req = new ActiveXObject("Microsoft.XMLHTTP");
			if (req)
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponseProfile});
			}
		}
		}
		catch(err)
		{
		alert(err.description);
		}
}

	function showResponseProfile(req)
	{
			try
			{
		var a =req.responseText.split("###");
    	document.getElementById('dataprofile').innerHTML = a[0];
		document.getElementById('profilechart').innerHTML = a[1];
		showload('no');
		}
			catch(err)
			{
			alert(err.description);
		}
	}

function displaychart(value)
	{
	
	try{
       	var temp = document.forms[0];
       	var timeanddate = new Date();
		var param = 'ZoneId='+value+'&timeanddate='+timeanddate;
		var url = '<%=request.getContextPath()%>/servlet/inputEnergy';
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
		}catch(err)
		{
		alert(err.description);
		}
}
	function showResponse(req)
	{
	try{
    	var a =req.responseText.split("####");
    	document.getElementById('datablock').innerHTML = a[0];
		document.getElementById('blckchart').innerHTML = a[1];
		showload('no');
		}
		catch(err){
		
			alert(err.description);
		}
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
	<div id ="loadimage" style="overflow:auto; position:absolute; top:68px; left:891px; display:none; " >
	<img src="<%=request.getContextPath()%>/images/preloader_pricing.gif"   align="middle">
</div>
<body onload="displayCombo();displaychart(1)">
<html:form action="/viewenergyprice" >
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top">
        <jsp:include page="/jsp/menu.jsp"/> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title">Electric&nbsp;Price</td> 
          <div id ="loadimage" style="overflow:auto; position:absolute; top:234px; left:891px; display:none; " >
		<img src="<%=request.getContextPath()%>/images/preloader_pricing.gif"  align="middle">
		  </div>
        </tr> 
        
     </table>
	 <fieldset><legend>
		Electric&nbsp;Price</legend>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="5%" class="fieldtitle">&nbsp;</td>
          <td width="10%" class="fieldtitle">&nbsp;Market Date:</td>
	          <td width="30%" class="fieldtitle"><%=fwdCurveLastImported==null?"":df.format(fwdCurveLastImported)%>&nbsp;</td>
          <td width="10%" class="fieldtitle">&nbsp;Data Source:</td>
          <td class="fieldtitle" id='srcblck' style="display:block">&nbsp;</td><td width="422" class="fieldtitle" id='srcprofile' style="display:none" align='left'>&nbsp;</td>
        </tr>
      	<tr>
        	<td width="5%" class="fieldtitle">&nbsp;</td>
        	<td class="fieldtitle" colspan="2">
            	<input name="radiobutton" type="radio" value="radiobutton" id='fwdBlkDet' onClick="displaydata('block')" checked><label for='fwdBlkDet'>Electric Price Block Details</label>
        	</td>
        	<td class="fieldtitle" colspan="2">
            <!-- 	<input name="radiobutton" type="radio" value="radiobutton" id='fwdProfDet' onClick="displaydata('profile')"><label for='fwdProfDet'>Energy Price Profile Details</label> -->
        	</td>
      	</tr>
      </table>
	 </fieldset>
	 <table width="100%" border="0" cellspacing="0" cellpadding="0">
	 <tr><td colspan="3"><table width="100%" border="0" cellspacing="0" cellpadding="0" id = 'congestionzone'style="display:block">
      <tr id = 'blocksearch' style="display:block" >
                <td width="127"  class="search">Congestion Zone </td> 
                <td width = "4" class="search">:</td> 
                <td colspan="4" class="search" id="congestion"> <html:select property="congestionZones" styleClass="Combo" onchange="displaychart(this.value)"><html:options collection="hmCongestionZones" property="key" labelProperty="value"/> </html:select></td> 
      </tr>
      <tr id='profilesearch' style="display:none" >
                <td  class="search">Weather Zone</td> 
                <td  class="search">:</td> 
                <td width="284"  class="search" id="congestion"><html:select property="weatherzone" styleClass="Combo" onchange="displayprofilechart()"><html:options collection="hmWeatherZones" property="key" labelProperty="value"/> </html:select></td> 
                <td width="145"  class="search">Profile</td> 
                <td width="1"  class="search">:</td> 
                <td width="409"  class="search" id="congestion"><html:select property="profile" styleClass="Combo" onchange="displayprofilechart()"><html:options collection="hmProfileZones" property="key" labelProperty="value"/> </html:select></td> 
      </tr>
 
</table>
</td></tr>
	 <tr><td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0" id = 'weather' style="display:none">
  <tr>
    <td class="search">&nbsp;</td>
  </tr>
</table></td></tr>
  <tr>
    <td width="42%">
		<table width="98%" border="0" cellspacing="0" cellpadding="0" id='hdprofile'style="display:none" >
		  <tr>
		    <td width="104"  class="tblheader" align="center">Weather Zone</td>
		    <td width="107" class="tblheader" align="center">Profile</td>
		    <td  width="85" class="tblheader" align="center">Month-Year</td>
		    <td width="100" class="tblheader" align="center">Price($/MWh)</td>
		  </tr>
		</table>
		<table width="98%" border="0" cellspacing="0" cellpadding="0" id='hdblock' style="display:block">
		  <tr>
		    <td width="112" class="tblheader" align="center">Congestion&nbsp;Zone</td>
		    <td width="89" class="tblheader" align="center">Price&nbsp;Block</td>
		    <td width="89" class="tblheader" align="center">Month - Year</td>
		    <td width="100" class="tblheader" align="center">Price($/MWh)</td>
		  </tr>
		</table>
		 <div style="overflow:auto; width:100%; height:280;display:none" id='dataprofile'> 
		  <table width="100%" border="0" cellspacing="0" cellpadding="0" >
		  <logic:present name="profiledetails">
		   <logic:iterate id="profile" name="profiledetails">
		     <tr>
		       <td width="93" class="tbldata" align="left"><bean:write name="profile" property="weatherZone.weatherZone"/></td> 
		       <td width="93" class="tbldata" align="left"><bean:write name="profile" property="loadProfile.profileType"/></td>
		       <td  width="75" class="tbldata" align="right"><bean:write name="profile" property="monthYear" format="MMM yyyy"/></td>
		       <td width="83" class="tbldata" align="right"><bean:write name="profile" property="price" format="0.00"/></td>
		     </tr>
		   </logic:iterate>
		</logic:present>
		 </table>
		 </div>
		<div style="overflow:auto; width:100%; height:280;display:block" id='datablock'> 
		  <table width="100%" border="0" cellspacing="0" cellpadding="0"  style="display:block">
		  <logic:present name="lstBlckdetails">
		   <logic:iterate id="blck" name="lstBlckdetails">
		    <tr>
		      <td width="92" class="tbldata" align="left"><bean:write name="blck" property="congestionZone.congestionZone"/></td> 
		      <td width="73" class="tbldata" align="left"><bean:write name="blck" property="priceBlock.priceBlock"/></td>
		      <td width="73" class="tbldata" align="right"><bean:write name="blck" property="monthYear" format="MMM yyyy"/></td>
		      <td width="75" class="tbldata" align="right"><bean:write name="blck" property="price" format="0.00"/></td>
		    </tr>
		   </logic:iterate>
		</logic:present>
		  </table>
		</div>
		</td>
    <td width="59%" id = 'blckchart' style="display:block">
<%String filenameblckChart = new FulcrumCurve().getchart(session,1,"Sellers Choice", new PrintWriter(out));
String blckchartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filenameblckChart; %>
      <img src="<%= blckchartURL%>"  border=0 usemap="#<%=filenameblckChart%>"></td>
<td width="60%" id = 'profilechart' style="display:none">
<%String filenameProfileChart = new FulcrumCurve().getProfileChart(session,1,1,new PrintWriter(out));
String profilechartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filenameProfileChart; %> 
<img src="<%= profilechartURL%>"  border=0 usemap="#<%=filenameProfileChart%>"></td>
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
<%}
}catch(Exception e)
{
	e.printStackTrace();
}%>
