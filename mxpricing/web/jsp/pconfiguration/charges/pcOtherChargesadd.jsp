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
<%@ page import="com.savant.pricing.dao.EnergyChargeRatesDAO"%>
<%@ page import="com.savant.pricing.dao.CongestionZonesDAO"%>
<%@ page import="java.util.*"%>
<%@ page import="com.savant.pricing.dao.UOMDAO"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pconfiguration.OtherChargesAddForm"/><jsp:setProperty name="frm" property="*"/>
<%
	EnergyChargeRatesDAO objEnergyChargeRatesDAO = new EnergyChargeRatesDAO();
	CongestionZonesDAO obCongestionZonesDAO = new CongestionZonesDAO();
	UOMDAO objUOMDAO = new UOMDAO();
	List lstUOM = objUOMDAO.getAllUOM();
	HashMap hmResult = new HashMap();
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy");
	hmResult = objEnergyChargeRatesDAO.getEnergyCharges(Integer.parseInt(frm.getCmbEnergeChrgeId()),-1,-1,Integer.parseInt(frm.getCmbCongestionId()));
	List lstEnergyChargesRates = null;
	List lstEnergyCharges = (List)objEnergyChargeRatesDAO.getAllEnergyChargeTypes();
	List lstCongestionChrges = obCongestionZonesDAO.getAllCongestionZones();
	lstEnergyChargesRates =(List)hmResult.get("Records");
	int recordCount = Integer.parseInt(hmResult.get("TotalRecordCount").toString());
	LinkedHashMap hmYear = new  LinkedHashMap();
	LinkedHashMap hmMonth = new  LinkedHashMap();
	hmMonth.put("0","January");
	hmMonth.put("1","February");
	hmMonth.put("2","March");
	hmMonth.put("3","April");
	hmMonth.put("4","May");
	hmMonth.put("5","June");
	hmMonth.put("6","July");
	hmMonth.put("7","August");
	hmMonth.put("8","September");
	hmMonth.put("9","October");
	hmMonth.put("10","November");
	hmMonth.put("11","December");
	int year = Integer.parseInt(sdf.format(new java.util.Date()));
	for(int i=0;i<10;i++)
	{
		hmYear.put(new Integer(year+i),new Integer(year+i));
	}
	pageContext.setAttribute("hmMonth",hmMonth);	
	pageContext.setAttribute("hmYear",hmYear);
	pageContext.setAttribute("lstCongestionChrges",lstCongestionChrges);
	pageContext.setAttribute("lstEnergyCharges",lstEnergyCharges);
	pageContext.setAttribute("lstEnergyChargesRates",lstEnergyChargesRates);
	pageContext.setAttribute("lstUOM",lstUOM);
	
	int browserHt = 0;
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 245;
	else
		browserHt = 225;
%>
<script>

function callCancel()
{
	document.forms[0].action = '<%=request.getContextPath()%>/jsp/pconfiguration/calendar/pcCalendar.jsp';
	document.forms[0].submit();
}
function diplaymonth()
{
	var objEle = document.getElementById('display');
	if(objEle.checked)
	{
		document.getElementById('enbtable').style.display = 'block';
		document.getElementById('distable').style.display = 'none';
	}
	else
	{
		document.getElementById('enbtable').style.display = "none";
		document.getElementById('distable').style.display = "block";
	}
}
function checkEnter(e)
   { 
      var characterCode;
      e = event
      characterCode = e.keyCode ;
      var dot = document.getElementById('value').value.split(".");
     if( (characterCode>=48) && (characterCode<=57) || characterCode == 46 && (dot.length == 1 || dot.length == 0))
     {
		  return true;
	  }
	  else
	  {
	  return false;
	  }
   }
function submitAction(value)
{
	var temp = document.forms[0];
	temp.formActions.value = value;
	temp.submit();
}
</script>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
</head>
<body> 
<html:form action="otherchargeAdd"> 
<html:hidden property="formActions"/> 
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="/jsp/menu.jsp"/> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title"> Other Charges Add/Update</td> 
        </tr> 
      </table> 
      <font class="message" ><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages></font>
    	<font class="error"><html:errors/></font>
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr > 
          <td width="147" class="fieldtitle" align="center">Energy Charge </td> 
          <td width="4" class="fieldtitle">:</td> 
          <td width="224" class="fieldtitle"><html:select property="cmbEnergeChrgeId" onchange="submitAction('view')"> <html:options collection="lstEnergyCharges" property="energyChargeIdentifier" labelProperty="chargeName"/> </html:select></td> 
          <td width="166" class="fieldtitle" align="center">Congestion Zone </td> 
          <td width="1" class="fieldtitle">:</td> 
          <td width="422" class="fieldtitle" ><html:select property="cmbCongestionId" onchange="submitAction('view')"> 
          <html:option value="0" >Select one</html:option>
			<html:options collection="lstCongestionChrges" property="congestionZoneId" labelProperty="congestionZone"/>
			 </html:select></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="50%"> <div style="overflow:auto; height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')"> 
              <table border="0" width="100%" cellspacing="0" cellpadding="0"> 
                <tr class='staticHeader'> 
                  <td width="30%" align="center"  class="tblheader">Month - Year</td> 
                  <td width="40%" align="center"  class="tblheader">Congestion Zone </td> 
                  <td align="center"  class="tblheader">Value</td> 
                </tr> 
                <logic:iterate id="charges"  name="lstEnergyChargesRates"> 
                <tr> 
                  <td class="tbldata" align="right">&nbsp;<bean:write name="charges" property="monthYear" format="MMM - yyyy"/></td> 
                  <td class="tbldata" align="left"> <logic:present name="charges" property="congestion"> <bean:write name="charges" property="congestion.congestionZone" ignore="true"/> </logic:present>&nbsp; </td> 
                  <td class="tbldata" align="right"><bean:write name="charges" property="charge" format="0.0000"/> (<bean:write name="charges" property="unit.unit"/>)</td> 
                </tr> 
                </logic:iterate > 
              </table> 
            </div></td> 
            <td width="1%">&nbsp;</td>
          <td valign='top'> 
          <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
        
          <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
              <tr style='height:25'> 
                <td width="122" class="fieldtitle">Value</td> 
                <td width="1" class="fieldtitle">:</td> 
                <td width="442"><html:text property="txtValue" size="15" maxlength="15" styleId="value" styleClass="textbox" onkeypress="return checkEnter(event)"/></td> 
              </tr> 
              <tr  style='height:25'> 
                <td class="fieldtitle">Year</td> 
                <td class="fieldtitle">:</td> 
                <td><html:select property="cmbYear" > <html:options collection="hmYear" property="key" labelProperty="value"/> </html:select></td> 
              </tr> 
              <tr style='height:25'> 
                <td class="fieldtitle">Unit</td> 
                <td class="fieldtitle">:</td> 
                <td><html:select property="cmbUnit"> <html:options collection="lstUOM" property="uomIdentifier" labelProperty="unit" /> </html:select></td> 
              </tr> 
              <tr style='height:25'> 
                <td class="fieldtitle" colspan="2"><input type="checkbox" id='display' name="checkbox1" value="checkbox" onclick='diplaymonth();'> 
                  Month Wise </td> 
                <td>&nbsp;</td> 
              </tr> 
              <tr> 
                <td colspan="3" id='distable' style='display:block'>
                <fieldset style='width:500 ; color:#993333'><legend>Months</legend>
                <table width="100%" border="0" cellspacing="0"  cellpadding="0" > 
                <logic:iterate id="months" name="hmMonth" indexId="count">
				<bean:define id="monthid" name="months" property="key"/>
                     <%if(count.intValue()%4==0){
                     %>
					<tr><%}%><td class="fieldata">
                      <html:multibox property="month" disabled="true" value='<%=String.valueOf(monthid)%>'/><bean:write name="months" property="value" /> </td>
                      <%if(count.intValue()%4==3){%>
					</tr><%}%>
                </logic:iterate>
                  </table> 
                  </fieldset>
                  </td>
                  </tr>
                  <tr>
                  <td id='enbtable' colspan="3" style='display:none'>
                  <fieldset style='width:500 ; color:#993333'><legend>Months</legend>
                  <table width="100%" border="0" cellspacing="0"  cellpadding="0" > 
                    <logic:iterate id="months" name="hmMonth" indexId="count">
				<bean:define id="monthid" name="months" property="key"/>
                     <%if(count.intValue()%4==0){
                     %>
					<tr><%}%><td class="fieldata">
                      <html:multibox property="month" value='<%=String.valueOf(monthid)%>'/><bean:write name="months" property="value" /> </td>
                      <%if(count.intValue()%4==3){%>
					</tr><%}%>
                </logic:iterate> 
                  </table></fieldset></td> 
              </tr> 
            </table>
            <table ><tr><td>
            </td></tr></table>
            <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
        
            <input name="Submit" type="button" class="button" value="Add/Update" onclick = "submitAction('apply')">
            </td> 
        </tr> 
        <tr><td><table width="100%"  border="0" cellpadding="0" cellspacing="0" class="sort">
          <tr>
            <td class='fieldata'  style="color:#0033CC;"> <b>Total Records: <%=recordCount%></b>
			</td>
		</tr>
      </table></td></tr>
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        <tr><td><input name="Submit" type="button" class="button" value="List" onclick = "submitAction('List')"></td></tr>
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
</html:html>
<%}%>
