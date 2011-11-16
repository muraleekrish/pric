<%
	if( session.getAttribute("userName") == null )
	{
		if( session.getLastAccessedTime() > 0 )
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
<jsp:useBean id="frm" class="com.savant.pricing.presentation.global.ZipCodeAddForm"/>
<jsp:setProperty name="frm" property="*"/>
<%@ page import="java.util.List"%>
<%@ page import="com.savant.pricing.dao.ZipCodesDAO"%>
<%@ page import="com.savant.pricing.dao.CongestionZonesDAO"%>
<%@ page import="com.savant.pricing.dao.WeatherZonesDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ZipCodeVO" %>

<%
	int browserHt = 0;
	
	List lsWeZone  = null;
	List lsConZone = null;
	CongestionZonesDAO objCongestionZonesDAO = new CongestionZonesDAO();
	WeatherZonesDAO objWeatherZonesDAO       = new WeatherZonesDAO();
	
	lsConZone = objCongestionZonesDAO.getAllCongestionZones();
	lsWeZone  = objWeatherZonesDAO.getAllWeatherZones();
	
	pageContext.setAttribute( "lsConZone", lsConZone );
	pageContext.setAttribute( "lsWeZone", lsWeZone );
	
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 213;
	else
		browserHt = 193;
	
%>
<html:html>
<head>
	<title>Customer Pricing</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
	<script language="JavaScript" src="<%=request.getContextPath()%>/script/mm_menu.js"></script>
	<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
	<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
</head>
<script>
function checkEnter(e)
{ 
     if( event.keyCode > 47 && event.keyCode < 58 )
     {
       return true; 
     }
     else
     {
	     return false;
	 }
}

function addZipCode( submitAction )
{
	var temp = document.forms[0];
	temp.formAction.value = submitAction;
}

function callCancel()
{
	document.forms[0].action = '<%=request.getContextPath()%>/jsp/global/zones/gZipCode.jsp';
	document.forms[0].submit();
}

</script>
<body> 
<html:form action = 'zipAdd'>
<html:hidden property = 'formAction' />
<table width = '100%' height = '100%' border = '0' cellpadding = '0' cellspacing = '0'> 
	<tr> 
    	<td  valign = 'top'> <jsp:include page="../../menu.jsp"/><font size = '1px' face = 'Verdana' style = 'color:#FF0000'><html:errors/></font>
		    <table width = '100%'  border = '0' cellspacing = '0' cellpadding = '0'>
        		<tr>
		    	    <td width = '250' class = 'page_title'>Zip Code Add</td>
	        	</tr>
    	    </table>
        	<table width = '100%'  border = '0' cellpadding = '0' cellspacing = '0'>
          		<tr>
			        <td><img src = "<%=request.getContextPath()%>/images/spacer.gif" width = '1' height = '5'></td>
		        </tr>
        	</table>
	        <table width = '100%'  border = '0' cellspacing = '0' cellpadding = '0'>
				<tr>
		            <td width = '125' class = 'fieldtitle'>Zip Code*</td>
        		    <td width = '1' class = 'fieldtitle'>:</td>
		            <td class = 'fieldata'><html:text styleClass = 'textbox' property = 'zipCode' size = '15' maxlength = '8' onkeypress = 'return checkEnter(event)' /></td>
        		</tr>
	      	
		        <tr>
          			<td class = 'fieldtitle'>Congestion Zone*</td>
		            <td width = '1' class = 'fieldtitle'>:</td>
        		    <td class = 'fieldata'>
        		    	<html:select property = 'congestionName' >
								<html:options collection = 'lsConZone' property = 'congestionZoneId' labelProperty = 'congestionZone'/>
						</html:select>
					</td>
				</tr>
		  	    <tr>
			        <td width = '100' class = 'fieldtitle'>Weather Zone*</td>
            		<td width = '1' class = 'fieldtitle'>:</td>
		            <td class = 'fieldata'>
						<html:select property = 'weatherZoneName' >
								<html:options collection = 'lsWeZone' property = 'weatherZoneId' labelProperty = 'weatherZone'/>
						</html:select>
					</td>
				</tr>
			</table>
	        <table width = '100%'  border = '0' cellspacing = '0' cellpadding = '0'>
    	    	<tr>
        	    	<td class = 'btnbg'>
						<html:submit  property = 'btnSubmit' styleClass ='button' value = 'Add' onclick = "addZipCode( 'Add' )"></html:submit>
						<html:reset value = 'Reset' styleClass = 'button'></html:reset >
    	                <html:button property = 'Cancel' styleClass = 'button' value = 'Cancel' onclick = 'callCancel()'></html:button >
        	        </td>
				</tr>
			</table>
		</td> 
	</tr> 
    <tr> 
    	<td height = '20'>
    		<table width = '100%'  border = '0' cellpadding = '0' cellspacing = '0' > 
		        <tr> 
        			<%String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";%>
			        <td class = 'copyright_link'><%=menupath%></td>
			        <td width = '250' class = 'copyright' align = 'right'>Design Rights Savant Technologies </td> 
		        </tr> 
		    </table>
	    </td> 
	</tr> 
</table> 

</html:form>
</body>
</html:html>
<% } %>