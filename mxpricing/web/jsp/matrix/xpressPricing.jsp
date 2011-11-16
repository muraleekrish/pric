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
<%@ page import="com.savant.pricing.dao.CongestionZonesDAO"%>
<%@ page import="com.savant.pricing.securityadmin.dao.UserDAO"%>
<%@ page import="com.savant.pricing.matrixpricing.dao.MatrixRunResultDAO"%>
<%@ page import="com.savant.pricing.matrixpricing.dao.MMPriceRunStatusDAO"%>
<%@ page import="com.savant.pricing.dao.ForwardCurveBlockDAO"%>
<%@ page import="com.savant.pricing.dao.GasPriceDAO"%>
<%@ page import="com.savant.pricing.matrixpricing.valueobjects.MMPriceRunHeaderVO"%>
<%@ page import="com.savant.pricing.matrixpricing.dao.ComputeMMPrice"%>
<%@ page import="com.savant.pricing.valueobjects.CongestionZonesVO"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.List"%>
<%@ page import=" com.savant.pricing.common.NumberUtil"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.savant.pricing.common.SortString"%>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.matrix.XPressPricingViewResultForm" />
<jsp:setProperty name="frm" property="*" />

<%try{
     TDSPDAO objTDSPDAO = new TDSPDAO();
     CongestionZonesDAO objCongestionZonesDAO = new CongestionZonesDAO();
     MatrixRunResultDAO objMatrixRunResultDAO = new MatrixRunResultDAO();
     MMPriceRunStatusDAO objMMPriceRunStatusDAO = new MMPriceRunStatusDAO();
     UserDAO objUserDAO = new UserDAO();
     ForwardCurveBlockDAO objForwardCurveBlockDAO = new ForwardCurveBlockDAO();
	 GasPriceDAO objGasPriceDAO = new GasPriceDAO();
     SortString objSortString = new SortString();
     List lstCongestionZones = objCongestionZonesDAO.getAllCongestionZones();
     MMPriceRunHeaderVO objMMPriceRunHeaderVO = null;
     List lstTdsp = objTDSPDAO.getAllTDSP();
     java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("##,###.00");
     NumberFormat df = NumberUtil.doubleFraction();
     df.setGroupingUsed(false);
     NumberFormat tf = NumberUtil.tetraFraction();
     String strLoadFactor = "No Demand";
	 String refId = "";
	 String noTermsAvail = "No Records Found";
     SimpleDateFormat sdfFullTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
     
     Date date = objForwardCurveBlockDAO.fwdCurveLastImportedOn();
	 Date dategas = objGasPriceDAO.teeNaturalGasPriceLastImportedOn();
     
	 Calendar objCalendar = Calendar.getInstance();
     String refNo = "MM"+objCalendar.getTimeInMillis()+"";
	 
	 boolean cpeElgible = objUserDAO.isUserElgible((String)session.getAttribute("userName"),"Run");
	 
	 if(request.getParameter("priceRunId")!=null && !request.getParameter("priceRunId").equalsIgnoreCase(""))
	 {
	   refId = request.getParameter("priceRunId");
	   objMMPriceRunHeaderVO = objMMPriceRunStatusDAO.getRunresultDetails(refId);
	 }
	 else
	 {
		 objMMPriceRunHeaderVO = objMMPriceRunStatusDAO.getLatsetRun();
		 if(objMMPriceRunHeaderVO!=null)
		   refId = objMMPriceRunHeaderVO.getPriceRunRefNo();
     }
     CongestionZonesVO objCongestionZonesVO = new CongestionZonesVO();
     objCongestionZonesVO= objTDSPDAO.getTDSP(Integer.parseInt(frm.getComboTdsp())).getCongestionZone();
	 ComputeMMPrice objComputeMMPrice = new ComputeMMPrice();
	 double loadFactor = objComputeMMPrice.getLoadFactor(frm.getMonthlyEnergy(),frm.getMonthlyDemand(),Integer.parseInt(frm.getComboTdsp()));
	 int profileId = objComputeMMPrice.getLoadProfile(loadFactor);
	 if(loadFactor>0.0)
	    strLoadFactor = (int)(loadFactor*100)+"%";
	 double estimatedkWh = objComputeMMPrice.getEstimatedkWh(frm.getBillMonth(),frm.getMonthlyEnergy(),profileId,objCongestionZonesVO.getCongestionZoneId()+"",frm.getComboTdsp());
	 List lstResult = objMatrixRunResultDAO.getRunresultDetails(refId,Integer.parseInt(frm.getComboTdsp()),objCongestionZonesVO.getCongestionZoneId(),profileId,Integer.parseInt(frm.getComboTerm()));
     double depositAmout = objMatrixRunResultDAO.getDepositAmount(lstResult,estimatedkWh);
	 int browserHt = 0;
	 SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	
	 List lstAllUsers = null;
	 if(cpeElgible)
	 	lstAllUsers = objUserDAO.getAllUsers();
	 else
	 	lstAllUsers = objUserDAO.getChildPersons((String)session.getAttribute("userName"), true);
	 
	 double customerCharge = lstResult!=null?lstResult.size()>0?((com.savant.pricing.matrixpricing.valueobjects.MatrixRunResultVO)lstResult.get(0)).getCustCharge():0.0:0.0;
	
  	 if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 490;
	 else
		browserHt = 470;
	 pageContext.setAttribute("objMMPriceRunHeaderVO",objMMPriceRunHeaderVO);
	 pageContext.setAttribute("lstCongestionZones",lstCongestionZones);
	 pageContext.setAttribute("lstTdsp",lstTdsp);
	 pageContext.setAttribute("lstResult",lstResult);
	 pageContext.setAttribute("lstAllUsers",lstAllUsers);
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'> </script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/RunResultDAO.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ComputeMMPrice.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/TDSPDAO.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/UserDAO.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'> </script>
<script>

String.prototype.trim=function()
{
    return this.replace(/^\s*|\s*$/g,'');
}
var congestionZoneId = <%=objCongestionZonesVO.getCongestionZoneId()%>;
var profileId = <%=profileId%>;
var refId = '<%=refId%>';
var energy = 1;
var localEstimatedkwh = <%=estimatedkWh%>;
function callList()
{
	temp = document.forms[0];
	temp.action = '<%=request.getContextPath()%>/jsp/matrix/xpressPricingList.jsp';
	temp.submit();
}
function loadResult()
{
  getCongestionZone();
}

function getCongestionZone() {
  var name = dwr.util.getValue("comboTdsp");
  TDSPDAO.getCongestionZoneName(name,function(data)
   {
	dwr.util.setValue("CongestionZone", data);
   });
  TDSPDAO.getCongestionZoneId(name,function(data)
   {	
    congestionZoneId = data;
    getLoadFactor();
  });
}

function getLoadFactor()
{
    var monthlyEnergy = dwr.util.getValue("monthlyEnergy");
    var monthlyDemand = dwr.util.getValue("monthlyDemand");
    var tdsp = dwr.util.getValue("comboTdsp");
    ComputeMMPrice.getLoadFactor(monthlyEnergy,monthlyDemand,tdsp,function(data)
     {
        getProfileId(data);
        if(data>0.0)
          dwr.util.setValue("loadFactor",parseInt(parseFloat(data*100))+"%");
		else 
    	  dwr.util.setValue("loadFactor","No Demand");
     });
     
}
function getProfileId(loadFactor)
{
    ComputeMMPrice.getLoadProfile(loadFactor, function(data)
     {
		profileId = data;
	    getEstimatedkWh(data);
     });
}
function getEstimatedkWh(profileId)
{
   var billMonth = dwr.util.getValue("billMonth");
   var monthlyEnergy = dwr.util.getValue("monthlyEnergy");
   var tdsp = dwr.util.getValue("comboTdsp");
    
   ComputeMMPrice.getEstimatedkWh(billMonth, monthlyEnergy, profileId,congestionZoneId,tdsp, function(data)
     {
        dwr.util.setValue("estimatedkWh",fmtMoney(data, 2, '.', ',' ));   
        localEstimatedkwh = data;		
		gettable(data);
     });
  
}
fmtMoney = function(n, c, d, t){
    var m = (c = Math.abs(c) + 1 ? c : 2, d = d || ",", t = t || ".",
        /(\d+)(?:(\.\d+)|)/.exec(n + "")), x = m[1].length > 3 ? m[1].length % 3 : 0;
    return (x ? m[1].substr(0, x) + t : "") + m[1].substr(x).replace(/(\d{3})(?=\d)/g,
        "$1" + t) + (c ? d + (+m[2] || 0).toFixed(c).substr(2) : "");
};

function gettable(depositAmount)
{
   try{
   var tdsp = dwr.util.getValue("comboTdsp");
   var term = dwr.util.getValue("comboTerm");   
    RunResultDAO.constructTable(refId, tdsp, congestionZoneId,profileId,term,energy,depositAmount,function(data)
     {
     var result =data.split("#$@%");   
	 document.getElementById('bundleTable').innerHTML = result[0];	 
	 dwr.util.setValue("depositAmount",fmtMoney(result[1], 2, ".", "," )); 	 
	 dwr.util.setValue("customerCharge",result[2]);
     });
	}catch(err){
		
			alert(err.description);
		}
}
function computeSavings(term)
{
  var savingsId = 'savings'+term;
  var savingsPercent = 'percent'+term;
  var compriceprice = dwr.util.getValue('competitorPrice'+term);
  var tdspCharge = dwr.util.getValue('tdspCharge'+term);
  var energyCharge = dwr.util.getValue('energyCharge'+term);
  var customerCharge = dwr.util.getValue('customerCharge');
  ComputeMMPrice.get$Savings(parseFloat(tdspCharge), parseFloat(energyCharge), parseFloat(compriceprice),localEstimatedkwh,parseInt(term/12),parseFloat(customerCharge), function(data)
  {
        var result = data.split("$#%@");
        dwr.util.setValue(savingsId,result[0]);   
        dwr.util.setValue(savingsPercent,result[1]);	
  });
}
function loadTeam()
{
  var userId = dwr.util.getValue('cmbSalesRep');
  UserDAO.getTeamMembers(userId, function(data)
  {
        var result = data.split("@#$");
        dwr.util.setValue("salesManager",result[0]);   
        dwr.util.setValue("analyst",result[1]);	
  });
}

function refreshPage()
{
 temp = document.forms[0];
 temp.formAction.value = "refresh";
 temp.submit();
}
function loadDefault()
{
  temp = document.forms[0];
  temp.customerName.value = temp.customerName.value;
  temp.formAction.value = temp.formAction.value;
  temp.monthlyDemand.value = temp.monthlyDemand.value;
  //temp.cmbSalesRep.value = temp.cmbSalesRep.value;
  loadTeam();
  temp.salesManager.value = temp.salesManager.value;
  temp.analyst.value = temp.analyst.value;
  temp.referenceNo.value = temp.referenceNo.value;
  temp.customerCharge.value = '<%=decimalFormat.format(customerCharge)%>';
  temp.priceRunId.value= '<%=refId%>';
  temp.referenceNo.value= '<%=refNo%>';
  checkCenterpoint();
  callenergyOnly('<%=frm.getEnergyOnly()%>');
          
}
function checkEnter(e)
   { 
      var characterCode;
      e = event
      characterCode = e.keyCode ;
     if( (characterCode>=48) && (characterCode<=57))
     {
		  return true;
	  }
	  else if(characterCode == 13)
	   {
		  loadResult();
		  return false;
	   }
	  else
	   {
	      return false;
	   }
   }
function checkCenterpoint()
{
 temp = document.forms[0];
 if(temp.comboTdsp.value == '1')
 {
   document.getElementById('monthlukva').innerText = "Monthly kVA";
 }
 else 
 {
   document.getElementById('monthlukva').innerText = "Monthly kW";
 }
}
 function callenergyOnly(isenergy)
{
 if(isenergy != "bundle")
   energy = 0;
 else
   energy = 1;
}

function makepdf()
{	
	temp = document.forms[0];
	var objTerm; var priceIdVal;	
	
	objTerm = document.getElementById('idTermBundled');
		
	var strTerm ="";
	if(objTerm.children[0].children.length>1 && objTerm.children[0].children[1].children[0].innerText.trim() != "<%=noTermsAvail%>")
	for(var i=1;i<objTerm.children[0].children.length;i++)
    {	
		if(objTerm.children[0].children[i].children[0].innerText.trim()=="")
			strTerm += "0.00";
		else
			strTerm += objTerm.children[0].children[i].children[0].innerText.trim();		

		if(objTerm.children[0].children[i].children[1].innerText.trim()=="")
			strTerm += "@#$"+"0.00";
		else
			strTerm += "@#$"+objTerm.children[0].children[i].children[1].innerText.trim();		

		if(objTerm.children[0].children[i].children[2].innerText.trim()=="")
			strTerm += "@#$"+"0.00";
		else
			strTerm += "@#$"+objTerm.children[0].children[i].children[2].innerText.trim();		

		if(objTerm.children[0].children[i].children[3].children[0].value.trim()=="")
			strTerm += "@#$"+"0.00";
		else
			strTerm += "@#$"+objTerm.children[0].children[i].children[3].children[0].value.trim();		

		if(objTerm.children[0].children[i].children[4].innerText.trim()=="")
			strTerm += "@#$"+"0.00";
		else
			strTerm += "@#$"+objTerm.children[0].children[i].children[4].innerText.trim();		

		if(objTerm.children[0].children[i].children[5].innerText.trim()=="")
			strTerm += "@#$"+"0.00";
		else
			strTerm += "@#$"+objTerm.children[0].children[i].children[5].innerText;		
		strTerm += "&&&";		
	}	
	temp.termStr.value = strTerm;
	temp.monthlykva.value = document.getElementById('monthlukva').innerText;
	temp.strBillMnth.value = temp.billMonth.options[temp.billMonth.selectedIndex].text;
	temp.strTdsp.value = temp.comboTdsp.options[temp.comboTdsp.selectedIndex].text;
	temp.strTerms.value = temp.comboTerm.options[temp.comboTerm.selectedIndex].text;
	temp.formAction.value = 'makepdf';
	temp.submit();
}
</script>
</head>
<body onload='loadDefault()'> 
<html:form action="/frmXPressPricingView"> 
<html:hidden property="formAction"/>
<input type="hidden" name="priceRunId" />
<input type="hidden" name="termStr" />
<input type="hidden" name="strBillMnth" />
<input type="hidden" name="strTdsp" />
<input type="hidden" name="strTerms" />
<input type="hidden" name="monthlykva" />

<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> 
    <td  valign="top"><jsp:include page="../menu.jsp"/> 
      <validator:errorsExist>
		<span class='error'><html:errors/></span>
		</validator:errorsExist>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title">Fast Quote for <%=sdf.format(objMMPriceRunHeaderVO.getPriceRunTime())%></td> 
          <td class="page_title"><table border="0" align="right" cellpadding="0" cellspacing="0" class="topnav">
            <tr>
             <td><a href="#" onClick="makepdf()">Make PDF</a>
             | <a href="<%=request.getContextPath()%>/jsp/matrix/customerFiles.jsp">Customer Files</a>
             <%if(cpeElgible){%>
              | <a href="#" onClick="callList()">List</a>
			  | <a href="<%=request.getContextPath()%>/jsp/matrix/matrixPricingList.jsp">Price Matrix</a>
              <%}%>
              &nbsp;&nbsp;</td>
            </tr>
          </table></td>
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
        <tr height="20"> 
           <td width="220" class="search" colspan='3'><strong>&nbsp;Forward Curves Last Imported Dates:</strong></td> 
      	   <td width="100" class="search"><b>Energy Price<b></td>
      	   <td width="1" class="search"><b>:<b></td>
      	   <td width="150" class="search"><%=date==null?"":sdfFullTime.format(date)%></td>
	   	   <td width="150" class="search"><b>Natural Gas Price</b></td>
	   	   <td width="1" class="search"><b>:<b></td>
      	   <td class="search"><%=dategas==null?"":sdfFullTime.format(dategas)%></td>
    	</tr>
		<tr height="20"> 
           <td class="search" colspan='9' align='right'>12-Month Natural Gas Forward Price on <%=sdf.format(objMMPriceRunHeaderVO.getPriceRunTime())%> : <bean:write name="objMMPriceRunHeaderVO" format="##.00" property="gasValue"/> $/MMBtu&nbsp;&nbsp;</td>
    	</tr>
      </table>
	  <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr id="tblPremium">
            <td>
            <fieldset id="custInfo" style='align:center; margin: -10px 10px 0px 10px'>
            <legend>Customer Information</legend> 
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
                  <td width="150" class="fieldtitle">Customer Name </td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="460" class="fieldata" colspan='4'><html:text property="customerName" styleId="custName" styleClass="textbox" maxlength="80" style="background-color:#FDFDB3" size="71"/></td>				              	
				  <td width="119" class="fieldtitle">Reference Number</td>				              	
				  <td width="1" class="fieldtitle">:</td>
				  <td colspan="2" class="fieldata"><input type="text" name="referenceNo" class="tboxfullplain" readonly="true"/></td>
				</tr>
				<tr>
                  <td width="150" class="fieldtitle">Bill Month</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata">
                  <html:select property="billMonth" styleId="billMonth" onchange="loadResult()" >
				    <html:option value="0">January</html:option><html:option value="1">February</html:option>
					<html:option value="2">March</html:option><html:option value="3">April</html:option>
					<html:option value="4">May</html:option><html:option value="5">June</html:option>
					<html:option value="6">July</html:option><html:option value="7">August</html:option>
					<html:option value="8">September</html:option><html:option value="9">October</html:option>
					<html:option value="10">November</html:option><html:option value="11">December</html:option>
                  </html:select>
                  </td>
				  <td width="119" class="fieldtitle">Monthly kWh</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><html:text property="monthlyEnergy" styleId="monthlyEnergy" styleClass="textbox" style="background-color:#FDFDB3" onkeypress="return checkEnter(event)" onchange="loadResult()"/></td>
				  <td width="119" class="fieldtitle" id='monthlukva'>Monthly kVA</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td colspan="2" class="fieldata"><html:text property="monthlyDemand" styleClass="textbox" style="background-color:#FDFDB3" onkeypress="return checkEnter(event)" onchange="loadResult()"/></td> 	
                </tr>
				<tr>
                  <td width="150" class="fieldtitle">Estimated kWh<span style='color:#FF0000'>*</span></td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><input type="text" name="estimatedkWh" id="estimatedkWh" class="tboxfullplain" value="<%=decimalFormat.format(estimatedkWh)%>" readonly="true" /></td>				  
				  <td width="119" class="fieldtitle">Load Factor</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><input type="text" name="loadFactor" id="loadFactor" class="tboxfullplain" value="<%=strLoadFactor%>" readonly="true" /></td>
				  <td width="119" class="fieldtitle">Customer Charge</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td colspan="2" class="fieldata"><html:text property="customerCharge" styleId="customerCharge" styleClass="tboxfullplain" readonly="true"/></td>
                </tr>
				<tr>
                  <td width="150" class="fieldtitle">TDSP</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"> <html:select property="comboTdsp" styleId="comboTdsp" onchange="checkCenterpoint();loadResult();">
				    <html:options collection="lstTdsp" property="tdspIdentifier" labelProperty="tdspName"/>
                  </html:select></td>				  
				  <td width="119" class="fieldtitle">Congestion Zone</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><i><input type="text" name="cogZone" id="CongestionZone" class="tboxfullplain" value="<%=objCongestionZonesVO.getCongestionZone()%>" readonly="true" /></i></td>
				  <td width="119" class="fieldtitle">Terms</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td colspan="2" class="fieldata">
				  	<html:select property="comboTerm" styleId="comboTerm" onchange="loadResult()">
				  		<html:option value="0">All</html:option>
						<html:option value="12">12</html:option>
				  		<html:option value="24">24</html:option>
						<html:option value="36">36</html:option>
              		</html:select>
                </tr>
				<tr>
                  <td width="150" class="fieldtitle">Sales Rep</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata">
					<html:select property="cmbSalesRep" onchange="loadTeam()" styleId="cmbSalesRep">
						<html:options collection="lstAllUsers" property="userId" labelProperty="firstName" />
					</html:select></td>				  
				  <td width="119" class="fieldtitle">Sales Manager</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><html:text property="salesManager" styleId="salesManager" styleClass="tboxfullplain" readonly="true" /></td>
				  <td width="119" class="fieldtitle">Pricing Analyst</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td colspan="2" class="fieldata"><html:text property="analyst" styleId="analyst" styleClass="tboxfullplain" readonly="true" /></td>
                </tr>
				<tr>
                  <td width="441" class="fieldtitle" colspan="4">Does Customer have three months of good payment history?</td>
				  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><html:select property="paymenthistory">
				  <html:option value="0">No</html:option>
				  <html:option value="1">Yes</html:option>
              </html:select></td>
				  <td width="119" class="fieldtitle">Deposit Amount</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata"><input type="text" name="depositAmount" id="depositAmount" class="tboxfullplain" value="<%=decimalFormat.format((int)depositAmout)%>" readonly="true" /></td>
				  <td class="fieldata" align='right'>&nbsp;
				    
			      </td>
				</tr>
			</table>
			</fieldset>
			<br>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" style='margin: 0px 0px 0px 20px'> 
				<tr> 
				   <td width="150" class="fieldtitle">Prices:</td>
				   <td width="4" class="fieldtitle">:</td>
	               <td width="124" class="fieldata"><html:radio property="energyOnly" value="bundle" onclick="callenergyOnly('bundle');loadResult();" styleId="bundleTerm" /><label for='bundleTerm'>Bundled Price</label></td>
				   <td class="fieldata"><html:radio property="energyOnly" value="energy" onclick="callenergyOnly('energy');loadResult();" styleId="energyTerm" /><label for='energyTerm'>Energy Only Price</label></td>
				</tr>
		  	</table>
		  	<br>
		   <div id='bundleTable' style="overflow:auto;  height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')">
		   <table width="98%" border="0" cellspacing="0" cellpadding="0" style='margin: 0px 10px 0px 10px' id='idTermBundled'> 
	        <tr class='staticHeader'> 
	          <td width="50" class="tblheader" style="border-left:solid 1px #CCCCCC"><div align="center">Term</div></td> 
	          <td width="207" class="tblheader"><div align="center">Energy Price ($/kWh)<span style='color:#FF0000'>**</span></div></td> 
	          <td width="215" class="tblheader"><div align="center">TDSP Charges ($/kWh)</div></td> 
	          <td width="180" class="tblheader"><div align="center">Competitor Price ($/kWh)</div></td> 
	          <td width="190" class="tblheader"><div align="center">Savings ($)</div></td> 
	          <td width="120" class="tblheader"> <div align="center">% Savings</div></td> 
	          </tr>
	          
			  <%
			   if(lstResult.size()>0)
			   {
			   %>
	           <logic:iterate id="result" name="lstResult">
		        <tr> 
		          <td class="tbldata" style="border-left:dotted 1px #CCCCCC"><bean:write name="result" property="term" ignore="true"/></td> 
		          <td class="tbldata" id='energyCharge<bean:write name="result" property="term" ignore="true"/>'><bean:write name="result" property="energyOnlyPrice" ignore="true" format="#0.0000"/></td> 
		          <td class="tbldata" id='tdspCharge<bean:write name="result" property="term" ignore="true"/>'><bean:write name="result" property="tdspCharge" ignore="true" format="#0.0000"/></td> 
		          <td class="tbldata" style="background-color:#FDFDB3"><input name='competitorPrice' type='text' onchange='computeSavings("<bean:write name="result" property="term" ignore="true"/>")' id='competitorPrice<bean:write name="result" property="term" ignore="true"/>' size='30' style='background-color:#FDFDB3; border:0'></td> 
		         <td class="tbldata" id='savings<bean:write name="result" property="term" ignore="true"/>'>&nbsp;</td> 
		         <td class="tbldata" id='percent<bean:write name="result" property="term" ignore="true"/>'>&nbsp;</td> 
		        </tr> 
	        </logic:iterate>
			<%
		  	}
  		  	else
		  	{
	   		%>
		 		<tr><td class = 'tbldata' colspan = '9' align = 'center'><%=noTermsAvail%></td></tr>
			<%
		  	}
			%>  
			
            </table>
			</div>
          </td>
          </tr>
        </table>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table>
		<table width="98%" border="0" cellspacing="0" cellpadding="0" style='margin: 0px 10px 0px 10px;'> 
			<tr height="20" style='color:#FF0000'> 
			  <td><b>Disclaimer</b></td> 
			</tr> 
			<tr height="20"> 
			  <td><span style='color:#FF0000'>* </span>Estimated Annual kWh based on customer inputs and corresponding profiles. Consumption will vary for individual customers.</td>	         
			</tr>
			<tr height="20"> 
			  <td><span style='color:#FF0000'>** </span>Energy Price Including ERCOT and PUC charges excludes all TDSP delivery charges.</td>	         
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
</html:html>
<%}catch(Exception e){
e.printStackTrace();
}
}%>
