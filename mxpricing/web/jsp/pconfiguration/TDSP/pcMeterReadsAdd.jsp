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
<%@ page import="com.savant.pricing.dao.TDSPDAO"%>
<%@ page import="com.savant.pricing.dao.MeterReadCyclesDAO"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pconfiguration.MeterReadsAddForm"/><jsp:setProperty name="frm" property="*"/>
<%
	HashMap hmResult = new HashMap();
	HashMap hmCycle = new HashMap();
	List lstRecords = new ArrayList();
	LinkedHashMap hmYear = new  LinkedHashMap();
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy");
	
	List lstYear = null ;
	List TDSP;
	
	TDSPDAO objTDSPDAO = new TDSPDAO();
	MeterReadCyclesDAO objMeterReadCyclesDAO = new MeterReadCyclesDAO();
	
	if(request.getParameter("formAction") != null)
		frm.setFormActions(request.getParameter("formAction"));
	
	lstYear = objMeterReadCyclesDAO.getAllMeterReadYears();
   	TDSP    = objTDSPDAO.getAllTDSP();
   	int year = Integer.parseInt(sdf.format( new java.util.Date()) );
	if( frm.getTdsp().equalsIgnoreCase( "" ) || frm.getTdsp().equalsIgnoreCase( "0" ) ) 
	{
		frm.setTdsp( "1" );
	}

	if( frm.getMeterReadCycle().equalsIgnoreCase( "" ) || frm.getMeterReadCycle().equalsIgnoreCase( "0" ) ) 
	{
		frm.setMeterReadCycle("all");
	}
	
	hmCycle = objMeterReadCyclesDAO.getAllReadCycles( Integer.parseInt( frm.getTdsp() ) );

	if( frm.getYear().equalsIgnoreCase( "" ) )
	{
		frm.setYear(year+"");
	}
	
	hmResult = objMeterReadCyclesDAO.getMeterReadDates(Integer.parseInt(frm.getTdsp()), frm.getMeterReadCycle(), Integer.parseInt(frm.getYear())) ;
    
    if(hmResult!=null)
	{
		lstRecords =(List)hmResult.get("Records");
	}

	int recordCount = Integer.parseInt(hmResult.get("TotalRecordCount").toString());
	for(int i=0;i<10;i++)
	{
		hmYear.put(new Integer(year+i), new Integer(year+i));
	}
	pageContext.setAttribute( "hmMeterReadDates", lstRecords );
	pageContext.setAttribute( "TDSP", TDSP );
	pageContext.setAttribute( "hmCycle", hmCycle );
	pageContext.setAttribute( "hmYear", hmYear );
	
	int browserHt = 0;
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 245;
	else
		browserHt = 225;
%>
<script>

function submitAction(value)
{
	var temp = document.forms[0];
	temp.formActions.value = value;
	if(temp.meterReadCycleTxt.style.display == 'block')
	{
		temp.meterReadCycle.options[temp.meterReadCycle.options.selectedIndex].value = temp.meterReadCycleTxt.value;
	}
	temp.submit();
}
function loadDefault()
{
	var temp = document.forms[0];
	<%if(!frm.getFormActions().equalsIgnoreCase("apply"))	{
        String[] mnth = new String[12];
        for(int i = 0;i < 12;i++)
         {
           if(i < 9)
             {
               mnth[i] = "0" + (i+1) + "-01-"+frm.getYear();
             }
             else
             {
              mnth[i] = (i+1) + "-01-"+frm.getYear();
             }
         }
      frm.setMnths(mnth);
      }
	for(int i=0;i<12;i++){%>
	temp.mnths[<%=i%>].value = '<%=frm.getMnths()[i]%>';
<%}%>

}
function addCycle()
{
	var temp = document.forms[0];

	if(temp.meterReadCycleTxt.style.display == "none")
	{
		temp.meterReadCycle.style.display    = "none";
		temp.meterReadCycleTxt.style.display = "block";
		temp.meterReadCycleTxt.value         = "";
		document.getElementById('linkId').innerHTML = 'Cancel';
	}
	else
	{
		temp.meterReadCycleTxt.style.display = "none";
		temp.meterReadCycle.style.display    = "block";
		document.getElementById('linkId').innerHTML = 'Add Cycle';
	}
}
function clearDate(obj)
{
	obj.value = "";
}
</script>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
</head>
<body onload='loadDefault()'> 
<html:form action="MeterReadsAdd"> <html:hidden property="formActions"/> 
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="/jsp/menu.jsp"/> 
    <font class="message" ><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages></font> <font class="error"><html:errors/></font> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title">Meter Read Dates Add/Update</td> 
        </tr> 
      </table> 

      <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
      <table width='100%' border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="70" class="fieldtitle" align="center">TDSP </td> 
          <td width="1" class="fieldtitle">:</td> 
          <td width="150" class="fieldtitle"> <html:select property="tdsp" onchange="submitAction('')"> <html:options collection="TDSP" property="tdspIdentifier" labelProperty="tdspName"/> </html:select> </td> 

          <td width='80' class='fieldtitle' align='center'>Year</td> 
          <td width='1' class='fieldtitle'>:</td> 
          <td width='50' class='fieldtitle'> 
			<html:select property="year" onchange="submitAction('')"> 
				<html:options collection="hmYear" property="key" labelProperty="value"/> 
			</html:select> 
		  </td> 

          <td width='120' class="fieldtitle" align="center">Meter Read Cycle</td> 
          <td width='1' class="fieldtitle">:</td> 
          <td width='50'class="fieldtitle">
			<html:text property="meterReadCycleTxt" styleClass="textbox" size="12" maxlength="10" style="display:none"/> 
			<html:select property="meterReadCycle" onchange="submitAction('')" styleClass="textbox">
				<html:option value="All">Select one</html:option>
				<html:options collection="hmCycle" property="key" labelProperty="value"/>&nbsp;
            </html:select>
		  </td> 
		  <td class='fieldtitle_link'><a href = 'javascript:addCycle()' id='linkId'>Add Cycle</a></td>          
        </tr> 
      </table> 
      <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
      <table width = '100%' border='0' cellspacing='0' cellpadding='0'> 
        <tr> 
          <td width='60%'> <div style="overflow:auto; height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')"> 
              <table border="0" width="100%" cellspacing="0" cellpadding="0"> 
                <tr class='staticHeader'> 
                  <td width = "130" class="tblheader">TDSP</td> 
                  <td width = "150" class="tblheader">Meter Read Cycle</td> 
                  <td width = "100" class="tblheader">Month - Year</td> 
                  <td class="tblheader">Read Date</td> 
                </tr> 
                <logic:iterate id="meterread" name="hmMeterReadDates"> 
                <tr> 
                  <td class="tbldata" align='center'><bean:write name="meterread" property="tdsp.tdspName" /></td> 
                  <td class="tbldata" align='center'><bean:write name="meterread" property="cycle" /></td> 
                  <td class="tbldata" align='center'><bean:write name="meterread" property="monthYear" format="MMM - yyyy" /></td> 
                  <td class="tbldata" align='center'><bean:write name="meterread" property="readDate"  format="MM-dd-yyyy" /></td> 
                </tr> 
                </logic:iterate> 
              </table> 
            </div></td> 
          <td width="3%">&nbsp;</td> 
          <td> <div style="overflow:auto; height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')"> 
              <table border = '0' cellspacing = '0' cellpadding = '0'> 
                <tr> 
                  <td width = '90' class="fieldtitle">January</td> 
                  <td width = '1' class="fieldtitle">:</td> 
                  <td width = '100' class="fieldata"> <html:text property="mnths" styleClass="textbox" styleId="jan" size="10" maxlength="10" onkeypress="return false" onfocus="clearDate(this)"/> <a href="#" onClick="showCalendarControl(document.getElementById('jan'), 'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> </td> 
                </tr> 
                <tr> 
                  <td class='fieldtitle'>February</td> 
                  <td class='fieldtitle'>:</td> 
                  <td class='fieldata'> <html:text property="mnths" styleClass="textbox" styleId="feb" size="10" maxlength="10" onkeypress="return false" onfocus="clearDate(this)" /> <a href="#" onClick="showCalendarControl(document.getElementById('feb'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> </td> 
                </tr> 
                <tr> 
                  <td class="fieldtitle">March</td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"> <html:text property="mnths" styleClass="textbox" styleId="mar" size="10" maxlength="10" onkeypress="return false" onfocus="clearDate(this)"/> <a href="#" onClick="showCalendarControl(document.getElementById('mar'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> </td> 
                </tr> 
                <tr> 
                  <td class="fieldtitle">April</td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"> <html:text property="mnths" styleClass="textbox" styleId="apr"  size="10" maxlength="10" onkeypress="return false" onfocus="clearDate(this)"/> <a href="#" onClick="showCalendarControl(document.getElementById('apr'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> </td> 
                </tr> 
                <tr> 
                  <td class="fieldtitle">May</td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"> <html:text property="mnths" styleClass="textbox" styleId="may"  size="10" maxlength="10" onkeypress="return false" onfocus="clearDate(this)"/> <a href="#" onClick="showCalendarControl(document.getElementById('may'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> </td> 
                </tr> 
                <tr> 
                  <td class="fieldtitle">June</td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"> <html:text property="mnths" styleClass="textbox" styleId="jun"  size="10" maxlength="10" onkeypress="return false" onfocus="clearDate(this)"/> <a href="#" onClick="showCalendarControl(document.getElementById('jun'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> </td> 
                </tr> 
                <tr> 
                  <td class="fieldtitle">July</td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"> <html:text property="mnths" styleClass="textbox" styleId="jul"  size="10" maxlength="10" onkeypress="return false" onfocus="clearDate(this)"/> <a href="#" onClick="showCalendarControl(document.getElementById('jul'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> </td> 
                </tr> 
                <tr> 
                  <td class="fieldtitle">August</td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"> <html:text property="mnths" styleClass="textbox" styleId="aug"  size="10" maxlength="10" onkeypress="return false" onfocus="clearDate(this)"/> <a href="#" onClick="showCalendarControl(document.getElementById('aug'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> </td> 
                </tr> 
                <tr> 
                  <td class="fieldtitle">September</td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"> <html:text property="mnths" styleClass="textbox" styleId="sep"  size="10" maxlength="10" onkeypress="return false" onfocus="clearDate(this)"/> <a href="#" onClick="showCalendarControl(document.getElementById('sep'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> </td> 
                </tr> 
                <tr> 
                  <td class="fieldtitle">October</td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"> <html:text property="mnths" styleClass="textbox" styleId="oct"  size="10" maxlength="10" onkeypress="return false" onfocus="clearDate(this)"/> <a href="#" onClick="showCalendarControl(document.getElementById('oct'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> </td> 
                </tr> 
                <tr> 
                  <td class="fieldtitle">November</td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"> <html:text property="mnths" styleClass="textbox" size="10" styleId="nov"  maxlength="10" onkeypress="return false" onfocus="clearDate(this)"/> <a href="#" onClick="showCalendarControl(document.getElementById('nov'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> </td> 
                </tr> 
                <tr> 
                  <td class="fieldtitle">December</td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"> <html:text property="mnths" styleClass="textbox" size="10" styleId="dec"  maxlength="10" onkeypress="return false" onfocus="clearDate(this)"/> <a href="#" onClick="showCalendarControl(document.getElementById('dec'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" width="16" height="16" border="0" align="absmiddle"></a> </td> 
                </tr> 
                <tr> 
                  <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
                </tr> 
                <tr> 
                  <td colspan = '3' align= 'center'> <input name="Submit" type="button" class="button" value="Add/Update" onclick = "submitAction('apply')"> </td> 
                </tr> 
              </table> 
            </div></td> 
        </tr> 
        <tr> 
          <td> <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="sort"> 
              <tr> 
                <td class='fieldata'  style="color:#0033CC;"> <b>Total Records: <%=recordCount%></b></td> 
              </tr> 
            </table></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table></td> 
  </tr> 
  <tr> 
    <td> <input name="Submit" type="button" class="button" value="List" onclick = "submitAction('List')"> </td> 
  </tr> 
  <tr> 
    <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
  </tr> 
  <tr> 
    <td height="20"> <table width="100%"  border="0" cellpadding="0" cellspacing="0" > 
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
