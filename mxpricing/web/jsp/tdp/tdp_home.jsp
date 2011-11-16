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

<%@page import="java.sql.ResultSet,java.io.InputStream,java.util.Properties,java.util.Hashtable,java.sql.SQLException,com.savant.pricing.common.BuildConfig,java.text.SimpleDateFormat,java.io.File,java.sql.CallableStatement,java.sql.ResultSet,org.hibernate.Query,org.hibernate.Session,com.savant.pricing.common.HibernateUtil;"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"><head>
<title>TDP</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<meta http-equiv="language" content="en-us" />

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
<%! String profileType="";
	String sname=null;
	String svalue=null;
    String path;
	String qry;
	String CustCount=null;
%>
<%
		System.out.println("*********** :"+session.getAttribute("userName"));
		session.setAttribute("ImportStatus","");
		if (request.getParameter("profileType")==null)
		{
		  profileType="IDR";
		}
		else
		{
		  profileType=request.getParameter("profileType");
		}
		if (request.getParameter("searchvalue")==null)
		{
		  svalue="x";
		}
		else
		{
		  svalue=request.getParameter("searchvalue");
		}
		if (request.getParameter("searchname")==null)
		{
		  sname="x";
		}
		else
		{
		  sname=request.getParameter("searchname");
		}
%>
</head>
<link href="<%=request.getContextPath()%>/styles/common.css" type="text/css" rel="stylesheet">
<script> 
function callHig(clas)
{
alert('');
}
function callReportType(val)
{	
val = document.getElementById('reportType').value;
	if(val==4)
	{
		document.getElementById('tdNoDays').style.display='block'
	}
	else
	{
	document.getElementById('tdNoDays').style.display='none'
	}
}


function callImportFile()
{	
	var features = "toolbars=no,status=no,resizable=no,top=40px,left=350px,height=300px,width=400";
//	window.showModalDialog('<%=request.getContextPath()%>/tdp/idrdata.jsp','','dialogHeight:300px;dialogWidth:400px');
	window.open('<%=request.getContextPath()%>/jsp/tdp/idrdata.jsp','',features);
}
function CallGo(x)
{
  var temp=document.forms[0];  
//  temp.action='<%=request.getContextPath()%>/ContractEdit.do?ContractId='+contrId+'&formActoin=edit&contractStatus='+contractStatus;
  temp.action ="<%=request.getContextPath()%>/jsp/tdp/tdp_home.jsp?profileType="+x;
  temp.submit();
}
function CallGo2()
{
  var temp=document.forms[0];  
  var sname=temp.searchname.value;
  var svalue=temp.searchvalue.value;
  if ((svalue=="") || (svalue==null))
	  svalue="x";
  var ptype;
  if (temp.profileType[0].checked)
	 ptype="IDR"
  else if (temp.profileType[1].checked)
	 ptype="NIDR"
  else if (temp.profileType[2].checked)
	 ptype="unknown"

  if ((sname!="x") && (svalue=="x"))
	{	
	  alert("Enter Search Value");
	  return;
	}
  else
	{
	  
   	  temp.action ="<%=request.getContextPath()%>/jsp/tdp/tdp_home.jsp?profileType="+ptype+"&sname="+sname+"&svalue="+svalue;
	  temp.submit();
	}
}
function onInit()
{
 var temp=document.forms[0];

 var pType='<%=profileType%>';
 if (pType=="IDR")
	{
	  temp.profileType[0].checked=true;
	}
else if (pType=="NIDR")
	{
	  temp.profileType[1].checked=true;
	}
else if (pType=="unknown")
	{
	  temp.profileType[2].checked=true;
	}
}

function setAll()
{
var temp=document.forms[0];
temp.searchname.value='<%=sname%>';
var svalue='<%=svalue%>';
if(svalue=="x")
temp.searchvalue.value="";
else
temp.searchvalue.value=svalue;
}
</script>

<body onload="onInit();setAll();">
<script type="text/javascript" src="<%=request.getContextPath()%>/script/tooltip/wz_tooltip.js"></script> 
<form>
  <table width="100%"  border="0" cellspacing="0" cellpadding="0" >
    <tr>
    <td  valign="top"> <jsp:include page="../menu.jsp"/> 
	<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
      		<tr> 
        		<td width="600" class="page_title">Typical&nbsp;Day&nbsp;Profiler&nbsp;&nbsp;&nbsp;
				<html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages>
        				<html:errors/>
   				</td>              
    			<td align='right' class="page_title">&nbsp;

     			</td>
<!--       <td width="357" class="page_title">Typical&nbsp;Day&nbsp;Profiler</td>
      <td  class="page_title">&nbsp;</td> -->
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="filter">
    <tr>
      <td width="129">Type of Customers </td>
      <td width="86"><input name="profileType" id="profileType" type="radio" value="IDR"  onclick="CallGo(this.value)">
      IDR :</td>
      <td width="86">      
      <input name="profileType" type="radio" id="profileType"  value="NIDR" onclick="CallGo(this.value)">        
      Non-IDR        </td>
	        <td width="86">      
    &nbsp;</td>
	   <td width="54">Search By </td>
      <td width="1">:</td>
      <td   ><select name="searchname" id="searchname">
             <option value="x" selected="selected">- Choose -</option>
          <option value="CUSTOMERID">Customer ID</option>
          <option value="ESIIDNUMBER">ESI-ID Number</option>
          <option value="CUSTOMERNAME">Customer Name</option>   
        </select>        
        <input name="searchvalue" type="text" id="searchvalue" size="25" />&nbsp;&nbsp;&nbsp;<input name="Submit" type="submit" class="button_sub_internal" id="Submit" value="Go!" onClick="CallGo2();">
    </tr>
  </table><br>
  <% if (profileType.equals("IDR"))
  {%>
  <legend><b>List of IDR Customers </b></legend>
  <%}
  else if (profileType.equals("NIDR"))
  {%>
  <legend><b>List of Non-IDR Customers </b></legend>
  <%}
   else if (profileType.equals("unknown"))
  {%>
  <legend><b>List of UnKnown Customers </b></legend>
  <%}%>

<div id="tableContainer" class="tableContainer" >
<table border="0" cellpadding="0" cellspacing="0"  align="center" class="">

<thead class="">
          <tr>
		    <td class = 'tblheader' align=left>SNo.</td>
            <td class = 'tblheader'>ESI-ID</td>
            <td class = 'tblheader'>Customer&nbsp;ID</td>
            <td class = 'tblheader'>Customer&nbsp;Name</td>
            <td class = 'tblheader'>Profile&nbsp;Name</td>
            <td class = 'tblheader'>Zip&nbsp;Code</td>
		    <td class = 'tblheader'>Service&nbsp;Address</td>        
          </tr>
 </thead>

<% 

	response.setHeader("Cache-Control", "no-cache");
	Session objSession = null;
    CallableStatement cstmnt = null;
	ResultSet rs = null;
	int i=0;
  // 	DBConnection objDBConnection = null;
try
{
  	
	//objDBConnection = DBConnectionFactory.getConnection();
	//Hashtable htable=new Hashtable ();	
	System.out.println(profileType);
	if(sname.equalsIgnoreCase("x"))
	{
		sname = "0";
		svalue = "0";
	}
	System.out.println("******** sname *******:"+sname + " " + svalue);
	 objSession = HibernateUtil.getSession();
     objSession.beginTransaction();
     cstmnt = objSession.connection().prepareCall("{call dbo.Sp_Mxep_Custdetails(?,?,?)}");
     cstmnt.setString(1,profileType);
     cstmnt.setString(2,sname);
     cstmnt.setString(3,svalue);
     rs = cstmnt.executeQuery();
     System.out.println("******** rs *******:"+rs );
//	rs=objDBConnection.executeStatement("exec dbo.Sp_Mxep_Custdetails'"+profileType+"','"+sname+"','"+svalue+"'",htable);
	String esiidno=null;
	String profileName=null;
	String datasource=null;
	CustCount="0";
	
	while(rs.next())
	{
	i++;
	//CustCount++;
	 esiidno=rs.getString("esiid");
 	 profileName=rs.getString("load_profile");
	
%>
     <tr>
	   <td class="tbldata">&nbsp;<%=i%><br></td>
	   <%
			if(profileType.equalsIgnoreCase("IDR"))
			{
			
		   %>
			    <td class="tbldata"><a href="<%=request.getContextPath()%>/jsp/tdp/tdp.jsp?popwindow=no&esiidno=<%=esiidno%>" target="_self" >&nbsp;<%=esiidno%></a><br></td>

		  <%}
			
			else if(profileType.equalsIgnoreCase("NIDR")){%>
				  <td class="tbldata"><a href="<%=request.getContextPath()%>/jsp/tdp/tdp.jsp?popwindow=cancel&esiidno=<%=(esiidno==null?"":esiidno)%>&profileName=<%=(profileName==null?"":profileName.substring(0,profileName.indexOf('_',profileName.indexOf('_')+1)))%>" target="_self" >&nbsp;<%=esiidno%></a><br></td>			
			<%}
		  %>
	   
	   <td class="tbldata">&nbsp;<%=rs.getString("cust_id")%><br></td>
       <td class="tbldata">&nbsp;<%=rs.getString("cust_name")%><br></td>
	   <td class="tbldata">&nbsp;<%=profileName%><br></td>
       
      <td class="tbldata">&nbsp;<%=rs.getString("zip_code")%><br></td>
	    <td class="tbldata">&nbsp;<%=rs.getString("adress_1")%></td>
    </tr>
         
<%
	}
//CustCount--;
}

  finally
        {
	 
            try
            {
           	 	objSession.getTransaction().commit();
                cstmnt.close();
                rs.close();
            }
            catch (SQLException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            objSession.close();
        }
%>

    </table>


</div>

<tr><td ><br><font color="blue"><B>Total&nbsp;No.&nbsp;of&nbsp;Customers&nbsp;:&nbsp;<%=i%></B></font></td></tr>
 <tr> 
    <td height="20"><table width="100%"  border="0" cellpadding="0" cellspacing="0" > 
        <tr> 
        	<%String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";%>
          <td   class="copyright_link"><%=menupath%></td>
          <td width="250"   class="copyright" align='right'>Design Rights Savant Technologies </td> 
        </tr> 
</table>

<div>
</form>
</body></html>
<%}%>