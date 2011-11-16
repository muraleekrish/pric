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
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.savant.pricing.dao.IDRDAO"%>
<%@ page import="com.savant.pricing.dao.NIDRDAO"%>
<%@ page import="com.savant.pricing.common.chart.ProfileChart"%>
<html:html>
	<head>
	<%
	try
	{
	IDRDAO objIDRDAO = new IDRDAO();
	NIDRDAO objNIDRDAO = new NIDRDAO();
	int browserWd = Integer.parseInt(String.valueOf(request.getSession().getAttribute("browserWidth")));
	int imageWd = 1000*browserWd/1148;
	
	List lstLoadprofiles = (List)objNIDRDAO.getAllLoadProfiles();
	List lstEsiids = (List)objIDRDAO.getAllEsiids();
	HashMap hmProfileDetails = null;
	HashMap hmEsiidDetails = null;
	String[] month = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	if(lstLoadprofiles!=null && lstLoadprofiles.size()>0)
	{
		hmProfileDetails = objNIDRDAO.getNIDRProfileDetails((String)lstLoadprofiles.get(0));
		HashMap weekDayProfile = (HashMap)hmProfileDetails.get(new Integer(2));
		HashMap weekEndProfile = (HashMap)hmProfileDetails.get(new Integer(3));
		pageContext.setAttribute("weekDayProfile",weekDayProfile);
		pageContext.setAttribute("weekEndProfile",weekEndProfile);
	}
	if(lstEsiids!=null && lstEsiids.size()>0)
	{
		hmEsiidDetails = objIDRDAO.getIDRProfileDetails((String)lstEsiids.get(0));
		HashMap weekDayEsiid = (HashMap)hmEsiidDetails.get(new Integer(2));
		HashMap weekEndEsiid = (HashMap)hmEsiidDetails.get(new Integer(3));
		pageContext.setAttribute("weekDayEsiid",weekDayEsiid);
		pageContext.setAttribute("weekEndEsiid",weekEndEsiid);
	}
	
	ProfileChart objProfileChart = new ProfileChart();
	
	    HashMap profileChart = objProfileChart.getchart(session,hmProfileDetails,new PrintWriter(out));
	    HashMap esiidChart = objProfileChart.getchart(session,hmEsiidDetails,new PrintWriter(out));
	
	%>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
<script>
function displayweekday()
{
var profile = document.getElementById('nidr').checked;
var weekday = document.getElementById('weekDay').checked;
	if(profile)
	{			
		document.getElementById('legendName').innerText = 'NIDR Profile Details';		
		document.getElementById('esiid').style.display = 'none';
		document.getElementById('endEsiid').style.display = 'none';
		document.getElementById('dayEsiid').style.display = 'none';
    	document.getElementById('esiiddayChart').style.display = 'none';
	    document.getElementById('esiidendChart').style.display = 'none';
	    if(weekday)
		 {   		    
   		    document.getElementById('endprofile').style.display = 'none';   		    
   		    document.getElementById('profileendChart').style.display = 'none';
			document.getElementById('dayprofile').style.display = 'block';
			document.getElementById('profiledayChart').style.display = 'block';
			document.getElementById('profile').style.display = 'block';
		 }
		else
		{
			document.getElementById('dayprofile').style.display = 'none';
			document.getElementById('profiledayChart').style.display = 'none';
   		    document.getElementById('endprofile').style.display = 'block';   		    
   		    document.getElementById('profileendChart').style.display = 'block';
			document.getElementById('profile').style.display = 'block';
		}
		
			   
	}
	else
	{	 
		document.getElementById('profile').style.display = 'none';		
		document.getElementById('dayprofile').style.display = 'none';
	    document.getElementById('endprofile').style.display = 'none';
        document.getElementById('profiledayChart').style.display = 'none';
	    document.getElementById('profileendChart').style.display = 'none';		
		document.getElementById('legendName').innerText = 'IDR Profile Details';
	 if(weekday)
		 {          
          document.getElementById('endEsiid').style.display = 'none';          
	      document.getElementById('esiidendChart').style.display = 'none';
		  document.getElementById('dayEsiid').style.display = 'block';
		  document.getElementById('esiiddayChart').style.display = 'block';
		  document.getElementById('esiid').style.display = 'block';
		 }
		else
		{
          document.getElementById('dayEsiid').style.display = 'none';
		  document.getElementById('esiiddayChart').style.display = 'none';
		  document.getElementById('endEsiid').style.display = 'block';          
	      document.getElementById('esiidendChart').style.display = 'block';
		  document.getElementById('esiid').style.display = 'block';
		}		
		
	}
		
}
function displayNIDR()
{
	try{
       	var temp = document.forms[0];
       	var profile = temp.profile.value;
   		var timeanddate = new Date();
		var param = 'profile='+profile+'&timeanddate='+timeanddate;
		var url = '<%=request.getContextPath()%>/servlet/callNIDRView';
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
		}catch(err)
		{
		alert(err.description);
		}
}

	function showResponseProfile(req)
	{
	try{
		var a =req.responseText.split("@@@");
    	document.getElementById('dayprofile').innerHTML = a[0];
		document.getElementById('endprofile').innerHTML = a[1];
		document.getElementById('profiledayChart').innerHTML = a[2];
		document.getElementById('profileendChart').innerHTML = a[3];
		showload('no');
		}
		catch(err){
		
			alert(err.description);
		}
	}

function displayIDR()
{
	try{
       	var temp = document.forms[0];
	    var esiid = temp.esiid.value;
   		var timeanddate = new Date();
		var param = 'ESIID='+esiid+'&timeanddate='+timeanddate;
		var url = '<%=request.getContextPath()%>/servlet/callIDRView';
		showload('yes');
		if (window.XMLHttpRequest) // Non-IE browsers
		{
			req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponseESIID});
		}
		else if (window.ActiveXObject) // IE
		{
			req = new ActiveXObject("Microsoft.XMLHTTP");
			if (req)
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponseESIID});
			}
		}
		}catch(err)
		{
		alert(err.description);
		}
}

	function showResponseESIID(req)
	{
	try{
		var a =req.responseText.split("@@@");
    	document.getElementById('dayEsiid').innerHTML = a[0];
		document.getElementById('endEsiid').innerHTML = a[1];
		document.getElementById('esiiddayChart').innerHTML = a[2];
		document.getElementById('esiidendChart').innerHTML = a[3];
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
<img src="<%=request.getContextPath()%>/images/preloader_pricing.gif"  align="middle">
</div>
<body>
<html:form action="/viewProfilePrice" >
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top">
        <jsp:include page="/jsp/menu.jsp"/> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr > 
          <td width="150" class="page_title" >IDR & NIDR Details</td>
           <td class="page_title">
           <table border="0" cellpadding="0" cellspacing="0" class="topnav">
            <tr>
              <td width="187" ><b><input name="radiobutton1" type="radio" value="radiobutton" id='idr' onClick="displayweekday()" checked><label for='idr'>IDR Profile Details</label></b></td>
              <td width="213"><b><input name="radiobutton1" type="radio" value="radiobutton" id='nidr' onClick="displayweekday()"><label for='nidr'>NIDR Profile Details</label></b></td>
            </tr>
          </table></td>        
        </tr>         
     </table>
	 <fieldset><legend id='legendName'>
		IDR Profile Details</legend>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
      	<tr><td width="271" class="fieldtitle" >
      	<table border="0" cellspacing="0" cellpadding="0">
      <tr id = 'profile' style="display:none" >
                <td width="106"   class="fieldtitle">Load Profile</td> 
                <td width="1"  class="fieldtitle">:</td> 
                <td width="98"  class="fieldtitle" id="congestion">
                <html:select property="profile" onchange="displayNIDR()">
                  <%Iterator iteProfile = lstLoadprofiles.iterator();
                  while(iteProfile.hasNext())
                  {
                  String profile = ((String)iteProfile.next());
                  %>
                   <option value='<%=profile%>'><%=profile%></option>
                   <%}%>
                  </html:select></td> 
      </tr>
      <tr style="display:block"  id='esiid'>
                <td  class="fieldtitle">ESIID</td> 
                <td  class="fieldtitle">:</td> 
                <td   class="fieldtitle"><html:select property="esiid" styleClass="Combo" onchange="displayIDR()">
 				<%Iterator iteEsiid = lstEsiids.iterator();
                  while(iteEsiid.hasNext())
                  {
                  String esiid = ((String)iteEsiid.next());
                  %>
                   <option value='<%=esiid%>'><%=esiid%></option>
                   <%}%> </html:select></td> 
      </tr>
 
		</table></td>
        	<td class="fieldtitle" width="80">
            	<input name="radiobutton" type="radio" value="radiobutton" id='weekDay' onClick="displayweekday()" checked><label for='weekDay'>WeekDay</label>
        	</td>
        	<td width="750" class="fieldtitle">
            	<input name="radiobutton" type="radio" value="radiobutton" id='weekEnd' onClick="displayweekday()"><label for='weekEnd'>WeekEnd</label>
        	</td>
      	</tr>
      </table>
      
	 </fieldset>
	 <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="40%"> 
 <div style="overflow:auto; width:100%; height:280;display:none" id='dayprofile'> 
  <table width="100%" border="0" cellspacing="0" cellpadding="0"  >
  <tr class='staticHeader'>
    <td width="50"  class="tblheader" align="center">Month</td>
    <td width="50" class="tblheader" align="center">Hour</td>
    <td  width="85" class="tblheader" align="center">Value(kWh)</td>
  </tr>
  <logic:present name="weekDayProfile">
   <logic:iterate id="weekDayProf" name="weekDayProfile">
	<bean:define id="index" name="weekDayProf" property="key"/>
     <logic:iterate id="weekDayProfdel" name="weekDayProf" property="value">
     <tr>
       <td  class="tbldata" align="left"><%=month[((Integer)index).intValue()-1]%></td>
       <td   class="tbldata" align="right"><bean:write name="weekDayProfdel" property="hour"/></td>
       <td  class="tbldata" align="right"><bean:write name="weekDayProfdel" property="value" format="0.0000"/></td>
     </tr>
     </logic:iterate>
   </logic:iterate>
</logic:present>
 </table>
 </div>
 <div style="overflow:auto;height:280;display:none" id='endprofile'> 
 <table width="100%" border="0" cellspacing="0" cellpadding="0"  >
  <tr class='staticHeader'>
    <td width="50"  class="tblheader" align="center">Month</td>
    <td width="50" class="tblheader" align="center">Hour</td>
    <td  width="85" class="tblheader" align="center">Value(kWh)</td>
  </tr>
  <logic:present name="weekEndProfile">
   <logic:iterate id="weekEndProf" name="weekEndProfile">
	<bean:define id="index" name="weekEndProf" property="key"/>
     <logic:iterate id="weekEndProfdel" name="weekEndProf" property="value">
     <tr>
       <td  class="tbldata" align="left"><%=month[((Integer)index).intValue()-1]%></td>
       <td   class="tbldata" align="right"><bean:write name="weekEndProfdel" property="hour"/></td>
       <td  class="tbldata" align="right"><bean:write name="weekEndProfdel" property="value" format="0.0000"/></td>
     </tr>
     </logic:iterate>
   </logic:iterate>
</logic:present>
 </table>
 </div>
 <div style="overflow:auto;height:280;display:block" id='dayEsiid'> 
 <table width="100%" border="0" cellspacing="0" cellpadding="0"  >
  <tr class='staticHeader'>
    <td width="50"  class="tblheader" align="center">Month</td>
    <td width="50" class="tblheader" align="center">Hour</td>
    <td  width="85" class="tblheader" align="center">Value(kWh)</td>
  </tr>
  <logic:present name="weekDayEsiid">
   <logic:iterate id="DayEsiid" name="weekDayEsiid">
	<bean:define id="index" name="DayEsiid" property="key"/>
     <logic:iterate id="weekDayEsiiddel" name="DayEsiid" property="value">
     <tr>
       <td  class="tbldata" align="left"><%=month[((Integer)index).intValue()-1]%></td>
       <td   class="tbldata" align="right"><bean:write name="weekDayEsiiddel" property="hour"/></td>
       <td  class="tbldata" align="right"><bean:write name="weekDayEsiiddel" property="value" format="0.0000"/></td>
     </tr>
     </logic:iterate>
   </logic:iterate>
</logic:present>
 </table>
 </div>
  <div style="overflow:auto; height:280;display:none" id='endEsiid'> 
 <table width="100%" border="0" cellspacing="0" cellpadding="0"  >
  <tr class='staticHeader'>
    <td width="50" class="tblheader" align="center">Month</td>
    <td width="50" class="tblheader" align="center">Hour</td>
    <td  width="85" class="tblheader" align="center">Value(kWh)</td>
  </tr>
  <logic:present name="weekEndEsiid">
   <logic:iterate id="endEsiid" name="weekEndEsiid">
	<bean:define id="index" name="endEsiid" property="key"/>
     <logic:iterate id="weekEnd" name="endEsiid" property="value">
     <tr>
       <td  class="tbldata" align="left"><%=month[((Integer)index).intValue()-1]%></td>
       <td  class="tbldata" align="right"><bean:write name="weekEnd" property="hour"/></td>
       <td  class="tbldata" align="right"><bean:write name="weekEnd" property="value" format="0.0000"/></td>
     </tr>
     </logic:iterate>
   </logic:iterate>
</logic:present>
 </table>
 </div>
</td>
<td width="59%" id = 'profiledayChart' style="display:none"><%String dayProfilechartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + (String)profileChart.get("weekday"); %>
      <img src="<%= dayProfilechartURL%>"  border=0 usemap="#<%=(String)profileChart.get("weekday")%>"></td> 
  <td  id = 'profileendChart' style="display:none"><%String endprofileChartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + (String)profileChart.get("weekend"); %>
      <img src="<%= endprofileChartURL%>"  border=0 usemap="#<%=(String)profileChart.get("weekend")%>"></td>
  <td  id = 'esiiddayChart' style="display:block"><%String dayEsiidchartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + (String)esiidChart.get("weekday");%>
      <img src="<%= dayEsiidchartURL%>"  border=0 usemap="#<%=(String)esiidChart.get("weekday")%>"></td>
  <td  id = 'esiidendChart' style="display:none"><%String endEsiidchartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" +  (String)esiidChart.get("weekend"); %>
      <img src="<%= endEsiidchartURL%>"  border=0 usemap="#<%=(String)esiidChart.get("weekend")%>"></td>
  
  
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
