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
<jsp:useBean id="frm" class="com.savant.pricing.presentation.tdp.DailyProfileForm" /><jsp:setProperty name="frm" property="*" />
<%@ page import="com.savant.pricing.tdp.dao.IDRProfilerManger"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Enumeration"%>
<%! 
	
	String esiidnoold=null;
	String esiidnonew=null;
	String winObj=null;
	String winstatus=null;
	String profileName=null;
	GregorianCalendar gc = new  GregorianCalendar();
	SimpleDateFormat sdfUI = new SimpleDateFormat("MMM-dd-yyyy");
	boolean flagsubmit=false;
%>
<%
	if(request.getParameter("esiidno")!=null)
	{
	  esiidnoold=request.getParameter("esiidno");
	}		
	if(request.getParameter("esiid")!=null)
	{
	  esiidnonew=request.getParameter("esiid");
	}	
	session.setAttribute("old",esiidnoold);
	session.setAttribute("new",esiidnonew);
	Vector vecIntervalProfileSummary  = new Vector();
	Vector vecDayProfileSummary = new Vector();
	Vector vecUsageDetails = new Vector();
	Vector vecWeeklyProfile = new Vector();
	Vector vecperiod = new Vector();
	Vector vecKwh = new Vector();
	Hashtable flowDate = new Hashtable();

	String graphURLWeekDays = "";
	String graphURLWeekEnds = "";
	String graphURLInterval = "";
	String graphURLDay = "";
	String graphURLMonth = "";
	String maxflowDate = "";
	String minflowDate = "";
	String fileName = "";
	IDRProfilerManger objManager=null;
	if(!frm.getFormAction().equals(""))
	{

		 Enumeration enu=request.getParameterNames();
		 String name;
		 while(enu.hasMoreElements())
		{
			 name=(String)enu.nextElement();
		}
		 winstatus=request.getParameter("txtpop");
		 profileName=request.getParameter("txtprofile");

		objManager = new IDRProfilerManger();
		if(!winstatus.equalsIgnoreCase("cancel"))
		{
			flowDate = objManager.getFlowDate(frm.getTxtESIID());
		}
		else
		{
			flowDate = objManager.getFlowDate(profileName);
		}
		minflowDate = (String)flowDate.get("minflowDate");
		maxflowDate = (String)flowDate.get("maxflowDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try	{
			gc.setTime(new Date(maxflowDate));
			gc.add(Calendar.DATE, -6);
			Date date = gc.getTime();

			if((frm.getTxtHourlyfrmDate().trim().equals("")) || (frm.getTxtHourlytoDate().trim().equals("")))
			{
				frm.setTxtHourlytoDate(sdfUI.format(new Date(maxflowDate)));
				frm.setTxtHourlyfrmDate(sdfUI.format(date));
			}

			if((frm.getTxtDailyfrmDate().trim().equals("")) || (frm.getTxtDailytoDate().trim().equals("")))
			{
				frm.setTxtDailytoDate(sdfUI.format(new Date(maxflowDate)));
				frm.setTxtDailyfrmDate(sdfUI.format(date));				
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		String fname1=null,fname2=null,fname3=null;
		String WeeklyProfileChart[]=null;
		Hashtable htProfileByMonth=new Hashtable();
		
		if(!winstatus.equalsIgnoreCase("cancel"))
		{
		
		if(request.getParameter("slcProfile").equalsIgnoreCase("usage Details"))
		{
			vecUsageDetails = objManager.getTypicalDayProfileDataByESIID(frm.getTxtESIID());
		}
		else if(request.getParameter("slcProfile").equalsIgnoreCase("Hourly Profile"))
		{
			vecIntervalProfileSummary = objManager.getDayProfileByIntervalSummary("Interval",frm.getTxtESIID(),frm.getTxtHourlyfrmDate(),frm.getTxtHourlytoDate());
			fname1 = objManager.getChartForDayProfile(vecIntervalProfileSummary, frm.getTxtESIID(),frm.getTxtHourlyfrmDate(),frm.getTxtHourlytoDate(),session,"Hourly Profile Graph for ESI-ID : ");
			graphURLInterval = request.getContextPath() + "/servlet/DisplayChart?filename=" + fname1;
		}
		else if(request.getParameter("slcProfile").equalsIgnoreCase("Daily Profile"))
		{

			vecDayProfileSummary = objManager.getDayProfileByIntervalSummary("Date",frm.getTxtESIID(),frm.getTxtDailyfrmDate(),frm.getTxtDailytoDate());
			fname2 = objManager.getBarChartForProfileByDate(vecDayProfileSummary, frm.getTxtESIID(),frm.getTxtDailyfrmDate(),frm.getTxtDailytoDate(),session,"Daily Profile Graph for ESI-ID : ");
			graphURLDay = request.getContextPath() + "/servlet/DisplayChart?filename=" + fname2;
		}
		else if(request.getParameter("slcProfile").equalsIgnoreCase("Monthly Profile"))
		{
			htProfileByMonth = objManager.getDayProfileForAnYear(frm.getTxtESIID());
			vecperiod = (Vector)htProfileByMonth.get("timeStamp");
			vecKwh = (Vector)htProfileByMonth.get("kwh");

			fname3 = objManager.getBarChartForProfileByYear(htProfileByMonth, frm.getTxtESIID(), session,"Monthly Profile for ESI-ID : ");
			graphURLMonth = request.getContextPath() + "/servlet/DisplayChart?filename=" + fname3;
		}
		else if(request.getParameter("slcProfile").equalsIgnoreCase("Weekly Profile"))
		{
			vecWeeklyProfile = objManager.getTypicalDayHourlyProfileDataByESIID(frm.getTxtESIID(),frm.getSlcWeeklyProfMonth());

			WeeklyProfileChart = objManager.getChartForTypicalDayHourlyProfileDataByESIID(vecWeeklyProfile, frm.getTxtESIID(),session);	
			graphURLWeekDays = request.getContextPath() + "/servlet/DisplayChart?filename=" + WeeklyProfileChart[0];
			graphURLWeekEnds = request.getContextPath() + "/servlet/DisplayChart?filename=" + WeeklyProfileChart[1];
		}

		flagsubmit=true;
		}
		else
		{	
		if(request.getParameter("slcProfile").equalsIgnoreCase("Hourly Profile"))
		{
		vecIntervalProfileSummary = objManager.getDayProfileByIntervalSummary("Interval",profileName,frm.getTxtHourlyfrmDate(),frm.getTxtHourlytoDate());
		fname1 = objManager.getChartForDayProfile(vecIntervalProfileSummary, profileName,frm.getTxtHourlyfrmDate(),frm.getTxtHourlytoDate(),session,"Hourly Profile Graph for : ");
		graphURLInterval = request.getContextPath() + "/servlet/DisplayChart?filename=" + fname1;
		}
		else if(request.getParameter("slcProfile").equalsIgnoreCase("Daily Profile"))
		{
		vecDayProfileSummary = objManager.getDayProfileByIntervalSummary("Date",profileName,frm.getTxtDailyfrmDate(),frm.getTxtDailytoDate());
		fname2 = objManager.getBarChartForProfileByDate(vecDayProfileSummary, profileName,frm.getTxtDailyfrmDate(),frm.getTxtDailytoDate(),session,"Daily Profile Graph for : ");
		graphURLDay = request.getContextPath() + "/servlet/DisplayChart?filename=" + fname2;
		}
		else if(request.getParameter("slcProfile").equalsIgnoreCase("Monthly Profile"))
		{
		htProfileByMonth = objManager.getDayProfileForAnYear(profileName);
		vecperiod = (Vector)htProfileByMonth.get("timeStamp");
		vecKwh = (Vector)htProfileByMonth.get("kwh");

		fname3 = objManager.getBarChartForProfileByYear(htProfileByMonth, profileName, session,"Monthly Profile for : ");
		graphURLMonth = request.getContextPath() + "/servlet/DisplayChart?filename=" + fname3;
		}
		else if(request.getParameter("slcProfile").equalsIgnoreCase("Weekly Profile"))
		{
		vecWeeklyProfile = objManager.getTypicalDayHourlyProfileDataByESIID(profileName,frm.getSlcWeeklyProfMonth());
		WeeklyProfileChart = objManager.getChartForTypicalDayHourlyProfileDataByESIID(vecWeeklyProfile,profileName,session);		
		graphURLWeekDays = request.getContextPath() + "/servlet/DisplayChart?filename=" + WeeklyProfileChart[0];
		graphURLWeekEnds = request.getContextPath() + "/servlet/DisplayChart?filename=" + WeeklyProfileChart[1];
		}
		else if(request.getParameter("slcProfile").equalsIgnoreCase("Usage Details"))
		{
		vecUsageDetails = objManager.getTypicalDayProfileDataByESIID(profileName);
		}
	  }		
		
	}
	pageContext.setAttribute("vecIntervalProfileSummary",vecIntervalProfileSummary);
	pageContext.setAttribute("vecDayProfileSummary",vecDayProfileSummary);
	pageContext.setAttribute("vecUsageDetails",vecUsageDetails);
	pageContext.setAttribute("vecWeeklyProfile",vecWeeklyProfile);
	//pageContext.setAttribute("vecDayProfileSummary",vecDayProfileSummary);
%>
<html:html>
<head>
<title>TDP</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/tdp/winter.css" />
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src='<%=request.getContextPath()%>/script/common.js'></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/utils.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/calendar-en.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/calendar-setup.js"></script>
<script src='<%=request.getContextPath()%>/scripts/prototype.js' language="javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">


<script>
var win;
</script>
<style type="text/css">

.tblFooter {
	font:Arial, Helvetica, sans-serif;
	size:11px;
	background-color:#F5F5F5;
	padding:2px;}
	.tbldata {
	border-right:dotted 1px #CCCCCC;
	border-bottom:dotted 1px #CCCCCC;
	color:#000000;
	font-family:Verdana, Arial, Helvetica, sans-serif;
	font-size:10px;
	height:20px;
}
tr:hover {
background-color: #e1eafe;
}
div.tableContainer {
	height: 420px;
	overflow: auto;
	width: 1000px;

}


div.tableContainer table {
	float: left;
	width: 1122px
}

thead.fixedHeader tr {
	position: relative
}



thead.fixedHeader th {
	background: #fff;


}
</style>
</head>
<script>

function wait(delay){
string="pauseforalert("+delay+");";
setTimeout(string,delay);
}
function pauseforalert(delay){
var	temp = document.forms[0];
temp.txtloadtdp.value='<%=session.getAttribute("ImportStatus")%>';
}


function loadCalendar(img,txt)
{	
	var temp = document.forms[0];
		
		if (document.getElementsByTagName('img').length != 'undefined')
		{
			for (var i = 0; i < document.getElementsByTagName('img').length; i++)
			{
				var obj = document.getElementsByTagName('img')[i];
				if (obj.name == img)
					imgStObj = obj;
			}
		}

		if (document.getElementsByTagName('input').length != 'undefined')
		{
			for (var i = 0; i < document.getElementsByTagName('input').length; i++)
			{
				var obj = document.getElementsByTagName('input')[i];
				if (obj.name == txt)
					txtStObj = obj;
			}
		}
		getCalendarDate(txtStObj,imgStObj);
}

function getCalendarDate(textObj,imgObj)
{
	Mira.Calendar.setup({
		firstDay     : 0,
		weekNumbers  : false,
		showOthers   : false,
		showsTime    : false,
		timeFormat   : "24",
		step         : 1,
		range        : [1900.01, 2999.12],
		electric     : false,
		singleClick  : true,
		inputField   : textObj.name,
		button       : imgObj.name,
		ifFormat     : "%b-%d-%Y",
		daFormat     : "%m-%d-%Y",
		align        : "Br"
		});
}
function CallProfileChange()
{
	temp = document.forms[0];
	var btnStatus='<%=winstatus%>';
	if(temp.slcProfile.value == 'Hourly Profile')
	{
		document.getElementById('tblHourly').style.display = 'block';
		document.getElementById('trHourly').style.display = 'block';
		document.getElementById('tblDaily').style.display = 'none';
		document.getElementById('trDaily').style.display = 'none';
		document.getElementById('tblWeekly').style.display = 'none';
		document.getElementById('trWeekly').style.display = 'none';
		document.getElementById('tblMonthly').style.display = 'none';
		document.getElementById('tblDetails').style.display = 'none';
		if(btnStatus=="cancel")
		{
			temp.btnImport.style.display="none";
		}
	
	}
	else if(temp.slcProfile.value == 'Daily Profile')
	{
		document.getElementById('tblHourly').style.display = 'none';
		document.getElementById('trHourly').style.display = 'none';
		document.getElementById('tblDaily').style.display = 'block';
		document.getElementById('trDaily').style.display = 'block';
		document.getElementById('tblWeekly').style.display = 'none';
		document.getElementById('trWeekly').style.display = 'none';
		document.getElementById('tblMonthly').style.display = 'none';
		document.getElementById('tblDetails').style.display = 'none';
		if(btnStatus=="cancel")
		{
			temp.btnImport.style.display="none";
		}
	}
	else if(temp.slcProfile.value == 'Weekly Profile')
	{
	
		document.getElementById('tblHourly').style.display = 'none';
		document.getElementById('trHourly').style.display = 'none';
		document.getElementById('tblDaily').style.display = 'none';
		document.getElementById('trDaily').style.display = 'none';
		document.getElementById('tblWeekly').style.display = 'block';
		document.getElementById('trWeekly').style.display = 'none';
		document.getElementById('tblMonthly').style.display = 'none';
		document.getElementById('tblDetails').style.display = 'none';
		if(btnStatus=="cancel")
		{
			temp.btnImport.style.display="none";
		}	}
	else if(temp.slcProfile.value == 'Monthly Profile')
	{
		document.getElementById('tblHourly').style.display = 'none';
		document.getElementById('trHourly').style.display = 'none';
		document.getElementById('tblDaily').style.display = 'none';
		document.getElementById('trDaily').style.display = 'none';
		document.getElementById('tblWeekly').style.display = 'none';
		document.getElementById('trWeekly').style.display = 'none';
		document.getElementById('tblMonthly').style.display = 'block';
		document.getElementById('tblDetails').style.display = 'none';
		if(btnStatus=="cancel")
		{
			temp.btnImport.style.display="none";
		}
	}
	else
	{
		document.getElementById('tblHourly').style.display = 'none';
		document.getElementById('trHourly').style.display = 'none';
		document.getElementById('tblDaily').style.display = 'none';
		document.getElementById('trDaily').style.display = 'none';
		document.getElementById('tblWeekly').style.display = 'none';
		document.getElementById('trWeekly').style.display = 'none';
		document.getElementById('tblMonthly').style.display = 'none';
		document.getElementById('tblDetails').style.display = 'block';
		if(btnStatus=="cancel")
		{
			temp.btnImport.style.display="none";
		}
		
	}
		temp.formAction.value="submit";
		temp.submit();
		startTimer();
	
}
function getDateDiff(fDate, tDate)
{
		var arr = new Array('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
		var frmMonth = 0;
		var toMonth = 0;
		var frmDate = (fDate).split('-');
		var tooDate = (tDate).split('-');
		for(i=0;i<arr.length;i++)
		{
			if(arr[i] == frmDate[0])
			{

				frmMonth = i;
				break;
			}
		}
		for(i=0;i<arr.length;i++)
		{
			if(arr[i] == tooDate[0])
			{
				toMonth = i;
				break;
			}
		}
		var fromDate = new Date(frmDate[2], frmMonth, frmDate[1]);
		var toDate = new Date(tooDate[2], toMonth, tooDate[1]);
		var one_day=1000*60*60*24;
	    var diff = Math.ceil((toDate.getTime() - fromDate.getTime())/(one_day))
		return diff
}
function CallGo()
{

	temp = document.forms[0];
	btn = temp.slcProfile.value;
	if(temp.txtESIID.value == '')
	{
		alert('Please enter ESIID Number');
		return;
	}
	if(isNaN(temp.txtESIID.value))
	{
		alert('Please enter valid ESIID Number');
		return;
	}
	var hourlyDiff = getDateDiff(temp.txtHourlyfrmDate.value, temp.txtHourlytoDate.value);

	if(hourlyDiff > 7)
	{
		alert("Profile can be viewed only for 7 days.  Adjust the date range accordingly.");		
		return;
	}
	else if(hourlyDiff < 0)
	{
		alert(' To-Date should be greater than From-Date');
		return;
	}

	var dailyDiff = getDateDiff(temp.txtDailyfrmDate.value, temp.txtDailytoDate.value);
	if(dailyDiff > 7)
	{
		alert(" Profile can be viewed only for 7 days.  Adjust the date range accordingly.");
		return;
	}
	else if(dailyDiff < 0)
	{
		alert(' To-Date should be greater than From-Date');
		return;
	}
	temp.formAction.value="submit";
	temp.submit();
}
function CallClear(btn)
{
	temp = document.forms[0];
	temp.txtHourlyfrmDate.value = '';
	temp.txtHourlytoDate.value = '';
	temp.txtDailyfrmDate.value = '';
	temp.txtDailytoDate.value = '';
	temp.slcProfile.value = 'Usage Details';
	temp.slcWeeklyProfMonth.value = 'All';
}
function chktabbed(clicked, a, tb1, tb2)  
{
	document.getElementById(clicked).className= 'tabon';
	document.getElementById(a).className= 'taboff';

	document.getElementById(tb1).style.display = 'block';
	document.getElementById(tb2).style.display = 'none';
}

function callImportFile()
{	
	temp = document.forms[0];
	//temp.txtESIID.value = '';
	temp.txtHourlyfrmDate.value = '';
	temp.txtHourlytoDate.value = '';
	temp.txtDailyfrmDate.value = '';
	temp.txtDailytoDate.value = '';
	temp.slcProfile.value = 'Usage Details';
	temp.slcWeeklyProfMonth.value = 'All';
	var features = "toolbars=no,status=no,resizable=no,top=350px,left=350px,height=220px,width=600";
	
	win=window.open('<%=request.getContextPath()%>/jsp/tdp/idrprofiler.jsp','popup',features);
	win.focus();
}

function CallSettings()
{
	temp = document.forms[0];
	temp.txtESIID.value = '<%=frm.getTxtESIID()%>';
	temp.txtHourlyfrmDate.value = '<%=frm.getTxtHourlyfrmDate()%>';
	temp.txtHourlytoDate.value = '<%=frm.getTxtHourlytoDate()%>';
	temp.txtDailyfrmDate.value = '<%=frm.getTxtDailyfrmDate()%>';
	temp.txtDailytoDate.value = '<%=frm.getTxtDailytoDate()%>';
	temp.slcProfile.value = '<%=frm.getSlcProfile()%>';
	temp.slcWeeklyProfMonth.value = '<%=frm.getSlcWeeklyProfMonth()%>';

}

function onload()
{

var temp=document.forms[0];
var importStatus='<%=session.getAttribute("ImportStatus")%>';
if (importStatus.length>0) 
	{
	temp.txtloadtdp.value='<%=session.getAttribute("ImportStatus")%>';
	wait(3000);
	}

var esiidno='<%=request.getParameter("esiidno")%>';
var popwin='<%=request.getParameter("popwindow")%>';
temp.txtESIID.value='<%=esiidnoold%>';
if(temp.txtESIID.value=="null")
{
temp.txtESIID.value='';
}
var submitstatus='<%=request.getParameter("submitstatus")%>';
if ((!((esiidno=="") || (esiidno=="null"))) && (popwin=="no"))
	{
	temp.formAction.value="submit";

	temp.submit();
	startTimer();
	}
else if (popwin=="yes")
	{
	callImportFile();
	}
else if (popwin=="cancel")
	{

		var profileName='<%=request.getParameter("profileName")%>';
		if((profileName.length!="") || (profileName!=null))
		{	
		temp.btnImport.style.display="none";
		temp.txtpop.value="cancel";
		temp.txtprofile.value=profileName;
		//alert("Non-IDR Customer " +profileName);
		temp.formAction.value="submit";
		temp.submit();
		startTimer();
		}
	}
else if(submitstatus!="null")
	{
	
		var flagstatus=('<%=flagsubmit%>');
		//alert(flagstatus);
	//	alert(submitstatus);
		
		{
		if ((flagstatus=="true") || (submitstatus=="yes") )
		{
	//		alert(submitstatus);
			temp.formAction.value="submit";
			temp.submit();
			startTimer();
		}
		}
	}
var esiidnoold='<%=session.getAttribute("old")%>';
var esiidnonew='<%=request.getParameter("esiid")%>';
//popwin=null;
var btnStatus='<%=winstatus%>'
		if(btnStatus=="cancel")
		{
			temp.btnImport.style.display="none";
			temp.txtpop.value=btnStatus;
			temp.txtprofile.value='<%=profileName%>'
		}
}


function startTimer ( )
{
    timeId = setInterval ( "checkStatus()", 1000 );
}
function checkStatus ( )
{    
	temp=document.forms[0];
	temp.txtload.value=" Loading.........!";
	setTimeout ( 'temp.txtload.value= ""', 500 );	
}

</script>
<%
session.setAttribute("ImportStatus","");
%>
<body onLoad="loadCalendar('imgHourlyfrmDate','txtHourlyfrmDate');loadCalendar('imgHourlytoDate','txtHourlytoDate');loadCalendar('imgDailyfrmDate','txtDailyfrmDate');loadCalendar('imgDailytoDate','txtDailytoDate');CallSettings();onload();" > 
<html:form action="/frmDailyProfile"> <html:hidden property ="formAction"/> 
<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/> </td> 
  </tr> 
</table>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="800" class="page_title">Typical&nbsp;Day&nbsp;Profiler <html:text property="txtloadtdp" style="border:0px;color:green" size="25" /></td>
    <td  class="page_title" align=right> <a href="<%=request.getContextPath()%>/jsp/tdp/tdp_home.jsp"> <font size=2 color="blue">List of Customers</font></a></td>
    <td align='right' class="page_title">&nbsp; </td>
  </tr>
</table>
<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="filter"> 
  <tr> 
    <td width="30">&nbsp;</td> 
    <td width="80">Profile </td> 
    <td width="1">:</td> 
    <td width="150"> <html:select property="slcProfile" onchange="CallProfileChange()"> <html:option value="Usage Details">Usage Detail</html:option> <html:option value="Hourly Profile">Hourly Profile</html:option> <html:option value="Daily Profile">Daily Profile</html:option> <html:option value="Weekly Profile">Weekly Profile</html:option> <html:option value="Monthly Profile">Monthly Profile</html:option> </html:select> </td> 
    <td width="80">ESI-ID Number </td> 
    <td width="1">:</td> 
    <td width="120"><html:text property="txtESIID" size="25" onchange="CallClear('NotAll')" readonly="true"  /> 
      <input type=hidden name="txtpop"> 
      <input type=hidden name="txtprofile"></td> 
    <td align=right colspan=2> <input name="btnImport" type="button" class="button_sub_internal" style="display:block" value="Import Data from File" onclick="callImportFile();"> </td> 
  </tr> 
  <%
		String Hourly = "none";
		String Daily = "none";
		String Weekly = "none";
		String Monthly = "none";
		String UsgDetails = "none";

		if(frm.getSlcProfile().equalsIgnoreCase("Usage Details"))
			UsgDetails = "block";
		else if(frm.getSlcProfile().equalsIgnoreCase("Hourly Profile"))
			Hourly = "block";
		else if(frm.getSlcProfile().equalsIgnoreCase("Daily Profile"))
			Daily = "block";
		else if(frm.getSlcProfile().equalsIgnoreCase("Weekly Profile"))
			Weekly = "block";
		else if(frm.getSlcProfile().equalsIgnoreCase("Monthly Profile"))
			Monthly = "block";
	%> 
  <tr id="trHourly" style="display:<%=Hourly%>"> 
    <td width="30">&nbsp;</td> 
    <td width="80">From Date</td> 
    <td width="1">:</td> 
    <td width="120"> <html:text property="txtHourlyfrmDate" size="10" readonly="true" onchange="document.forms[0].txtDailyfrmDate.value=this.value"/> <img src="<%=request.getContextPath()%>/images/calendar.gif" border="0" width="16" height="16" name='imgHourlyfrmDate' align='absmiddle' onMouseOver="this.style.cursor='hand'" onclick="getCalendarDate(document.forms[0].txtHourlyfrmDate,this);" >&nbsp; </td> 
    <td width="80">To Date</td> 
    <td width="1">:</td> 
    <td width="120"> <html:text property="txtHourlytoDate" size="10" readonly="true" onchange="document.forms[0].txtDailytoDate.value=this.value" /> <img src="<%=request.getContextPath()%>/images/calendar.gif" border="0" width="16" height="16" name='imgHourlytoDate' align='absmiddle' onMouseOver="this.style.cursor='hand'" onclick="getCalendarDate(document.forms[0].txtHourlytoDate,this);"> </td> 
    <%
			 if((minflowDate==null) || (minflowDate.length()<=0))
			   minflowDate="&nbsp;&nbsp;  - &nbsp;&nbsp;   ";
			    if((maxflowDate==null) || (maxflowDate.length()<=0))
			   maxflowDate="&nbsp;&nbsp;  - &nbsp;&nbsp;   ";
			  %> 
    <td>Data Avaliable From <%=minflowDate%> To <%=maxflowDate%></td> 
    <td align=right> <input name="btnGo" type="button" class="button_sub_internal" value="Go" onclick="CallGo()"> </td> 
  </tr> 
  <tr id="trDaily" style="display:<%=Daily%>"> 
    <td width="30">&nbsp;</td> 
    <td width="80">From Date</td> 
    <td width="1">:</td> 
    <td width="120"> <html:text property="txtDailyfrmDate" size="10" readonly="true" onchange="document.forms[0].txtHourlyfrmDate.value=this.value"/> <img src='<%=request.getContextPath()%>/images/calendar.gif' border="0" width="16" height="16" name='imgDailyfrmDate' align='absmiddle' onMouseOver="this.style.cursor='hand'" onclick="getCalendarDate(document.forms[0].txtDailyfrmDate,this);">&nbsp; </td> 
    <td width="80">To Date</td> 
    <td width="1">:</td> 
    <td width="120"> <html:text property="txtDailytoDate" size="10" readonly="true" onchange="document.forms[0].txtHourlytoDate.value=this.value"/> <img src='<%=request.getContextPath()%>/images/calendar.gif' border="0" width="16" height="16" name='imgDailytoDate' align='absmiddle' onMouseOver="this.style.cursor='hand'" onclick="getCalendarDate(document.forms[0].txtDailytoDate,this);"> </td> 
    <%
				if((minflowDate==null) || (minflowDate.length()<=0))
				minflowDate="&nbsp;&nbsp;  - &nbsp;&nbsp;   ";
			    if((maxflowDate==null) || (maxflowDate.length()<=0))
				maxflowDate="&nbsp;&nbsp;  - &nbsp;&nbsp;   ";
		%> 
    <td>Data Avaliable From <%=minflowDate%> To <%=maxflowDate%></td> 
    <td align=right> <input name="btnGo" type="button" class="button_sub_internal" value="Go" onclick="CallGo()"> </td> 
  </tr> 
  <tr id="trWeekly" style="display:none"> 
    <td width="30">&nbsp;</td> 
    <td width="80">Month</td> 
    <td width="1">:</td> 
    <td width="120"> <html:select property="slcWeeklyProfMonth" onchange="CallGo();"> <html:option value="All">All</html:option> <html:option value="Jan">Jan</html:option> <html:option value="Feb">Feb</html:option> <html:option value="Mar">Mar</html:option> <html:option value="Apr">Apr</html:option> <html:option value="May">May</html:option> <html:option value="Jun">Jun</html:option> <html:option value="Jul">Jul</html:option> <html:option value="Aug">Aug</html:option> <html:option value="Sep">Sep</html:option> <html:option value="Oct">Oct</html:option> <html:option value="Nov">Nov</html:option> <html:option value="Dec">Dec</html:option> </html:select> </td> 
    <td width="80" colspan=5>&nbsp;</td> 
  </tr> 
</table> 
<% //out.println(	esiidno);
	if(esiidnoold!=null) {%> 
<jsp:include page="/customerdetail/IncludeCustDetail.jsp" flush="true" >
	<jsp:param name="ColName" value='ESIIDNumber'/>
	<jsp:param name="ColValue" value='<%=esiidnoold%>' />
	 
</jsp:include> 
<%}%> 
<input type=text name="txtload" style="text-align:right;font-size:10px; border-style:none;border-color:#FFFFFF;cursor:default;text-align=right;color:red;font-family:'Verdana', Times, serif" > 
 <div style="overflow:auto; width:'100%'; height:'413px';display:<%=Hourly%>;"> 
<table width="100%" border="0" id="tblHourly" style="border-bottom:solid 1px #DDD;display:<%=Hourly%>;"> 
  <tr> 
    <td width="20%" style="border-right:solid 1px #DDD;"> <table border="0" width="100%" cellpadding="0" cellspacing="0"> 
        <tr align="center"> 
          <td width="80" class="tblheader">&nbsp;Date</td> 
          <td width="50" class="tblheader">&nbsp;Hour</td> 
          <td class="tblheader">&nbsp;kWh</td> 
        </tr> 
      </table> 
      <div style="overflow:auto; width:'105%'; height:'410px';"> 
        <table border="0" width="100%"> 
          <logic:iterate id="IntervalProfileSummary" name="vecIntervalProfileSummary" indexId="count"> 
          <tr> 
            <td width="80" align="left" class="tbldata"><bean:write name="IntervalProfileSummary" property="mrktDate"/></td> 
            <td width="50" align="center" class="tbldata"><bean:write name="IntervalProfileSummary" property="hour"/></td> 
            <td	align="right" class="tbldata"><bean:write name="IntervalProfileSummary" property="kwh"/></td> 
          </tr> 
          </logic:iterate> 
        </table> 
      </div></td> 
    <td width="70%"> <table border="0" width="100%"> 
        <tr> 
          <td align="center"> <img src="<%=graphURLInterval%>" width="700" height="400"> </td> 
        </tr> 
      </table></td> 
  </tr> 
</table> 
</div>
 <div style="overflow:auto; width:'100%'; height:'413px';display:<%=Daily%>;"> 
<table width="100%" border="0" id="tblDaily" style="border-bottom:solid 1px #DDD;display:<%=Daily%>;"> 
  <tr> 
    <td width="20%" style="border-right:solid 1px #DDD;"> <table border="0" width="100%" cellpadding="0" cellspacing="0"> 
        <tr align="center"> 
          <td width="50%" class="tblheader">&nbsp;Date</td> 
          <td class="tblheader">&nbsp;kWh</td> 
        </tr> 
      </table> 
      <div style="overflow:auto; width:'105%'; height:'400px';"> 
        <table border="0" width="100%"> 
          <logic:iterate id="dayProfileSummary" name="vecDayProfileSummary" indexId="count"> 
          <tr> 
            <td width="50%" align="left" class="tbldata"><bean:write name="dayProfileSummary" property="mrktDate"/></td> 
            <td align="right" class="tbldata"><bean:write name="dayProfileSummary" property="kwh"/></td> 
          </tr> 
          </logic:iterate> 
        </table> 
      </div></td> 
    <td width="70%"> <table border="0" width="100%"> 
        <tr> 
          <td align="center"> <img src="<%=graphURLDay%>" width="700" height="400"> </td> 
        </tr> 
      </table></td> 
  </tr> 
</table> 
</div>
<div style="overflow:auto; width:'100%'; height:'440px';display:<%=Weekly%>;"> 
<table width="100%" border="0" id="tblWeekly" style="border-bottom:solid 1px #DDD;display:<%=Weekly%>;"> 
  <tr> 
    <td> <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="105" class="tabon" id="t1"> <a href="javascript:chktabbed('t1', 't2', 'tblWeekDays', 'tblWeekEnds' )">Week Days</a></td> 
          <td width="105" class="taboff" id="t2"><a href="javascript:chktabbed('t2', 't1', 'tblWeekEnds', 'tblWeekDays')">Week Ends</a></td> 
          <td>&nbsp;</td> 
        </tr> 
      </table> 
      <table width="100%" border="0" cellpadding="0" cellspacing="0" id="tblWeekDays" style="display:block"> 
        <tr> 
          <td width="20%" style="border-right:solid 1px #DDD;"> <table width="100%" border="0" cellpadding="0" cellspacing="0"> 
              <tr> 
                <td width="50%" class="tblheader" align=center>Month</td> 
               <!--  <td width="50" class="tblheader" align=center>Interval</td>  -->
                <td class="tblheader" align=center>kWh</td> 
              </tr> 
            </table> 
            <div style="overflow:auto; width:'105%'; height:'400px';"> 
              <table width="100%" border="0"> 
                <logic:iterate id="WeeklyProfile" name="vecWeeklyProfile" indexId="count"> 
                <tr> 
                  <td width="50%" class="tbldata"><bean:write name="WeeklyProfile" property="month"/></td> 
                  <!-- <td width="50" class="tbldata"><bean:write name="WeeklyProfile" property="interval"/></td>  -->
                  <td class="tbldata" align="right"><bean:write name="WeeklyProfile" property="weekDaykWh"/></td> 
                </tr> 
                </logic:iterate> 
              </table> 
            </div></td> 
          <td width="70%"> <table border="0" width="100%"> 
              <tr> 
                <td align="center"> <img src="<%=graphURLWeekDays%>" width="700" height="400"> </td> 
              </tr> 
            </table></td> 
        </tr> 
      </table> 
	 
	 
      <table width="100%" border="0" cellpadding="0" cellspacing="0" id="tblWeekEnds" style="display:none"> 
        <tr> 
          <td width="20%" style="border-right:solid 1px #DDD;"> <table width="100%" border="0" cellpadding="0" cellspacing="0"> 
              <tr> 
                <td width="50%" class="tblheader">&nbsp;Month&nbsp;</td> 
                <!-- <td width="50" class="tblheader">&nbsp;Interval&nbsp;</td>  -->
                <td class="tblheader" align=center>&nbsp;kWh&nbsp;</td> 
              </tr> 
            </table> 
            <div style="overflow:auto; width:'100%'; height:'400px';"> 
              <table width="100%" border="0"> 
                <logic:iterate id="WeeklyProfile" name="vecWeeklyProfile" indexId="count"> 
                <tr> 
                  <td width="50%" class="tbldata"><bean:write name="WeeklyProfile" property="month"/></td> 
                  <!-- <td width="50" class="tbldata"><bean:write name="WeeklyProfile" property="interval"/></td>  -->
                  <td class="tbldata" align="right"><bean:write name="WeeklyProfile" property="weekEndkWh"/></td> 
                </tr> 
                </logic:iterate> 
              </table> 
            </div></td> 
          <td width="70%"> <table border="0" width="100%"> 
              <tr> 
                <td align="center"> <img src="<%=graphURLWeekEnds%>" width="700" height="400"> </td> 
              </tr> 
            </table></td> 
        </tr> 
      </table>
	 
	  </td> 
  </tr> 
</table> 
 </div>
 <div style="overflow:auto; width:'100%'; height:'438px';display:<%=Monthly%>;"> 

<table width="100%" border="0" id="tblMonthly" style="border-bottom:solid 1px #DDD;display:<%=Monthly%>;"> 
  <tr> 
    <td width="20%" style="border-right:solid 1px #DDD;"> <table border="0" width="100%" cellpadding="0" cellspacing="0"> 
        <tr align="center"> 
          <td width="50%" class="tblheader">&nbsp;Month</td> 
          <td class="tblheader">&nbsp;kWh</td> 
        </tr> 
      </table> 
      <div style="overflow:auto; width:'105%'; height:'410px';"> 
        <table border="0" width="100%"> 
          <%
			for(int i =0; i<vecperiod.size();i++)
			{
		%> 
          <tr> 
            <td width="50%" class="tbldata"><%=(String) vecperiod.get(i)%></td> 
            <td align="right" class="tbldata"><%=(String) vecKwh.get(i)%></td> 
          </tr> 
          <%
			}			
		%> 
        </table> 
      </div></td> 
    <td width="70%"> <table border="0" width="100%"> 
        <tr> 
          <td align="center"> <img src="<%=graphURLMonth%>" width="700" height="400"> </td> 
        </tr> 
      </table></td> 
  </tr> 
</table> 
</div>
<div style="overflow:auto; width:100%; height:438px;display:<%=UsgDetails%>;"> 
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="" id="tblDetails" style="display:<%=UsgDetails%>;" > 
  <tr> 
    <td class="tblheader" width="60">Month</td> 
    <td class="tblheader" width="75">Year</td> 
    <td class="tblheader" width="100">On-Peak</td> 
    <td class="tblheader" width="90">Off-Peak</td> 
    <td class="tblheader" width="90">Total kWh </td> 
    <td class="tblheader" width="100">Min Hourly</td> 
    <td class="tblheader" width="100">Max Hourly</td> 
    <td class="tblheader" width="100">Min 15 Minutes</td> 
    <td class="tblheader" width="100">Max 15 Minutes</td> 
    <td class="tblheader" width="100">No. of Week Days</td> 
    <td class="tblheader" width="100">No. of Week Ends</td> 
    <td class="tblheader" width="100">Total No. of Days</td> 
    <td class="tblheader">Load Factor</td> 
  </tr> 
<logic:iterate id="UsageDetails" name="vecUsageDetails" indexId="count"> 
<tr> 
  <td class="tbldata"><bean:write name="UsageDetails" property="month"/></td> 
    <td class="tbldata" align="right"><bean:write name="UsageDetails" property="year"/></td> 
  <td class="tbldata" align="right"><bean:write name="UsageDetails" property="totalOnPeakkWh"/></td> 
  <td class="tbldata" align="right"><bean:write name="UsageDetails" property="totalOffPeakkWh"/></td> 
  <td class="tbldata" align="right"><bean:write name="UsageDetails" property="totalkWh"/></td> 
  <td class="tbldata" align="right"><bean:write name="UsageDetails" property="minHourlykWh"/></td> 
  <td class="tbldata" align="right"><bean:write name="UsageDetails" property="maxHourlykWh"/></td> 
  <td class="tbldata" align="right"><bean:write name="UsageDetails" property="min15MinIntkWh"/></td> 
  <td class="tbldata" align="right"><bean:write name="UsageDetails" property="max15MinIntkWh"/></td> 
  <td class="tbldata" align="right"><bean:write name="UsageDetails" property="noOfWeekDay"/></td> 
  <td class="tbldata" align="right"><bean:write name="UsageDetails" property="noOfWeekEnd"/></td> 
  <td class="tbldata" align="right"><bean:write name="UsageDetails" property="noOfDays"/></td> 
  <td class="tbldata" align="right"><bean:write name="UsageDetails" property="loadFactor"/></td> 
</tr> 
</logic:iterate> 
</table>
</div>
  <table width="100%"  border="0" cellpadding="0" cellspacing="0" > 
    <tr> 
      <%String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";%> 
      <td   class="copyright_link"><%=menupath%></td> 
      <td   class="copyright" align='right'>Design Rights Savant Technologies </td> 
    </tr> 
  </table> 
</html:form> 
</body>
</html:html>
<%}%>
