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
<%@ page import="com.savant.pricing.calculation.dao.PricingDAO"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="com.savant.pricing.common.NumberUtil"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.savant.pricing.transferobjects.EPP"%>
<%@ page import="com.savant.pricing.dao.PriceRunCustomerDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO"%>
<%@ page import="com.savant.pricing.dao.PreferenceProductsDAO"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.EPPForm" />
<jsp:setProperty name="frm" property="*" />

<%
try{
SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
NumberFormat dnf = NumberUtil.doubleFraction(); 
NumberFormat tnf = NumberUtil.tetraFraction();
PreferenceProductsDAO objPreferenceProductsDAO = new PreferenceProductsDAO();
PricingDAO objPricingDAO = new PricingDAO();
String priceRunId =null;
GregorianCalendar gc = new GregorianCalendar();
gc.setTime(new Date());
gc.add(Calendar.DATE ,1);
String priceRunRefNo = null;
String esiids = "";

if( request.getParameter("PriceRunRefNo")!=null)
{	
	priceRunRefNo = request.getParameter("PriceRunRefNo");
}

if( request.getParameter("priceRunId")!=null)
{	
	priceRunId = request.getParameter("priceRunId");
	frm.setRunRefId(priceRunId);
}
if(request.getParameter("term")==null && request.getParameter("hidTerm")!=null)
{
	if(request.getParameter("hidTerm")!=null && !request.getParameter("hidTerm").equalsIgnoreCase(""))
	{
	frm.setTerm(request.getParameter("hidTerm"));
	}
	else
	{
	int termDetails[] = objPricingDAO.getPreferenceTerms(Integer.parseInt(priceRunId));
	frm.setTerm(termDetails[0]+"");
	}
}
else{
	frm.setTerm(request.getParameter("term"));
}
if(request.getParameter("strESIID")!=null)
{
esiids = request.getParameter("strESIID");
}
PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
PriceRunCustomerVO objPriceRunCustomerVO = null;
objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(Integer.parseInt(priceRunId));

EPP objEPP = objPricingDAO.getEPP(Float.parseFloat(frm.getBaseGasPrice()),Integer.parseInt(frm.getTerm()),Integer.parseInt(priceRunId),esiids);
Vector vecIndDetails = objEPP.getVecIndividualEPP();
pageContext.setAttribute("vecIndDetails",vecIndDetails);
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<%=request.getContextPath()%>/script/common.js"></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script>
function scrollDiv(){
	var a = document.getElementById('dvMultOffHead');
	var b = document.getElementById('dvMultOffVal');
	a.scrollRight = b.scrollRight;
	a.scrollLeft = b.scrollLeft;
}

function navtab(clicked, a, b){
	var dash = document.getElementById('tblDashBoard');
	var graph = document.getElementById('tblGraph');
	var multoff = document.getElementById('tblMultOff');
	
	clicked.className = 'subtab_on';
	a.className = 'subtab_off';
	b.className = 'subtab_off';
	if(clicked.id == 'set1'){
		dash.style.display = 'block';
		graph.style.display = 'none';
		multoff.style.display = 'none';
	}
	else if(clicked.id == 'set2'){
		dash.style.display = 'none';
		graph.style.display = 'block';
		multoff.style.display = 'none';
	}
	else if(clicked.id == 'set3'){
		dash.style.display = 'none';
		graph.style.display = 'none';
		multoff.style.display = 'block';
	}
}

function incmonth(type,txt){
	var vartxt = document.getElementById(txt);

	if(type == "increment"){
		if(parseInt(vartxt.value,10) == 60){
			return;
		}
		else{
			vartxt.value = parseInt(vartxt.value,10) + 1 ;
		}
	}
	else{
		if(parseInt(vartxt.value,10) == 1)
			return;
		else
			vartxt.value = parseInt(vartxt.value,10) - 1 ;	
	}
}
function incmonth1(type,txt)
{
	var vartxt = document.getElementById(txt);
	if(type == "increment")
	{
		vartxt.value = parseFloat(vartxt.value)+1.0/10 ;
	}
	else
	{
	vartxt.value = parseFloat(vartxt.value) - parseFloat(0.1) ;
	}
}
function chFixPrice(val){
	if(val == 0 || val == 1){
		document.getElementById('fxdPrice').className = 'tbldata';
		document.getElementById('summer').style.display = 'none';
		document.getElementById('winter').style.display = 'none';
	}
	else if(val == 2){
		document.getElementById('fxdPrice').className = 'tbltitlebold';
		document.getElementById('summer').style.display = 'block';
		document.getElementById('winter').style.display = 'block';
	}
}
function calldisplay()
{
	document.getElementById('contarctsuccess').style.display = 'none';
	document.getElementById('contractfail').style.display = 'none';
}
function callcpe()
{
	var temp = document.forms[0];
	temp.action = '<%=request.getContextPath()%>/jsp/pricerun/proposal.jsp';
	temp.priceRunId.value = '<%=priceRunId%>';
    temp.submit();
}
function showprogress(mess)
{
  if(mess=="yes")
	document.getElementById('progress').style.display='block';
	else
	document.getElementById('progress').style.display='none';
}
function callpage(pagename)
{
	try{
	var temp = document.forms[0];
	temp.action='<%=request.getContextPath()%>/'+pagename+'.do?priceRunId=<%=frm.getRunRefId()%>&PriceRunRefNo=<%=priceRunRefNo%>';
	temp.strESIID.value = localESIID;
	temp.hidTerm.value = localterm;
	temp.submit();
	}catch(err)
	{
	}
}
var localterm = '';
var localESIID = '';
function loadDefault()
{
localESIID = '<%=request.getParameter("strESIID")%>';
localterm = '<%=(request.getParameter("hidTerm"))%>';
document.forms[0].priceRunId.value = '<%=request.getParameter("priceRunId")%>';
document.forms[0].PriceRunRefNo.value = '<%=request.getParameter("PriceRunRefNo")%>';
}
function callsubmit()
{
var temp = document.forms[0];
temp.strESIID.value = localESIID;
temp.hidTerm.value = localterm;
temp.submit();
}

</script>
</head>
<div id ="progress" style="overflow:auto; position:absolute; top:89px; left:980px; display:none" >
	<img src="<%=request.getContextPath()%>/images/preloader_pricing.gif"  align="middle">
</div>
<body onload='loadDefault();'> 
<html:form action="epp" method="post">
<html:hidden property="formAction" />
<input type='hidden' name='priceRunId'/>
<input type='hidden' name='PriceRunRefNo'/>
<input type='hidden' name='strESIID'/>
<input type='hidden' name='hidTerm'/>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/><html:errors/>
	
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="250" class="page_title">Energy Partner Plan </td> 
          </tr> 
        </table> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
          </tr> 
        </table> 
        <table width="320"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="83" class="subtab_off" id="set1"><a href="javascript:callpage('dashBoard')">Dashboard</a></td> 
            <td width="42" class="subtab_on" >EPP</td> 
            <td width="78" class="subtab_off" ><a href="javascript:callpage('heatrate');">Heat&nbsp;Rate</a></td> 
            <td width="100" class="subtab_off" ><a href="javascript:callpage('aggLoadProfile')">Aggregated&nbsp;LP</a></td> 
          </tr> 
        </table> 
        
        <br> <table width="100%" align="center"  border="0" cellspacing="0" cellpadding="0"> 
          <tr align="center"> 
            <td colspan="4" class="list_tit_disp">ENERGY PARTNER PLAN</td> 
          </tr> 
          <tr> 
            <td width="346" class="list_data_disp " >Base Rate, $/kWh </td> 
            <td width="8" class="list_data_disp ">:</td> 
            <td width="268" class="list_data_disp "><%= tnf.format(objEPP.getBaseRate())%></td>
            <td width="370" class="list_data_disp ">&nbsp;</td> 
          </tr> 
          <tr > 
            <td class="list_data_disp ">Fuel Adjustment Rate, $/kWh </td> 
            <td class="list_data_disp ">:</td> 
            <td class="list_data_disp "><%= tnf.format(objEPP.getFuelAdjustmentRate())%></td>
            <td class="list_data_disp ">FAF Based on Multiplier of <%=tnf.format(objEPP.getFafMultiplier())%> and Base Gas Price of $<%= dnf.format(objEPP.getBaseGasPrice())%> per MMBtu </td> 
          </tr> 
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr > 
            <td> <fieldset id="fsPriceInfo" > 
              <legend style="color:#0033CC">Pricing Info - Price Run ID: <%=priceRunRefNo%></legend> 
              <table width="100%" cellpadding="0" cellspacing="0" > 
                <tr> 
                  <td width="81" class="fieldtitle">Customer</td> 
                  <td width="4" class="fieldtitle">:</td> 
                  <td width="256" class="fieldata"><%= objEPP.getCustomerName()%> </td> 
                  <td width="152" class="fieldtitle">Start Month</td> 
                  <td width="10" class="fieldtitle">:</td> 
                  <td width="128" class="fieldata"><%=sdf.format(objEPP.getContractStartMonth())%></td> 
                </tr> 
                <tr> 
                  <td class="fieldtitle">Total ESI ID </td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"><%= objEPP.getNoOfEsiIds()%></td> 
                  <td class="fieldtitle">Stop Month </td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"><%=sdf.format(objEPP.getContractEndMonth())%></td> 
                </tr> 
              </table> 
              </fieldset> 
              <table width="100%" cellpadding="0" cellspacing="0"> 
                <tr> 
                    <td><a href="javascript:showHideSpl1('fsPriceInfo','tblSearch','imgShowHide2','dvList')"><img src="<%=request.getContextPath()%>/images/hide.gif" name="imgShowHide" width="50" height="10" border="0" id="imgShowHide2"></a></td> 
                </tr> 
              </table> 
              <table width="406" border="0" cellspacing="0" cellpadding="0">
  				<tr>
   					 <td width="406" colspan="6" id='contarctsuccess' class="message" style="display:none">Proposal Details are exported to database.</td>
    			</tr>
  				<tr>
   					 <td id = 'contractfail' class='error' style="display:none">Error in exporting Proposal details.</td>
   				</tr>
				</table>
              <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="tblSearch"> 
                <tr> 
                  <td width="199" class="search">Gas Price Index</td> 
                  <td width="9" class="search">:</td> 
                  <td width="228" class="search">NYMEX Henry Hub</td> 
                  <td width="254" class="search">Fixed Price: Energy Only Price, $/MWh</td> 
                  <td width="1" class="search">:</td> 
                  <td width="292" class="search"> <%= dnf.format(objEPP.getFixedPrice())%></td> 
                </tr> 
                <tr> 
                  <td class="search">Weighted Average Gas Price</td> 
                  <td class="search">:</td> 
                  <td class="search"><%=tnf.format(objEPP.getWeightedAvgGasPrice())%></td> 
                  <td class="search">Term in Months:</td> 
                  <td class="search">:</td> 
                  <td class="search"><a href="javascript:incmonth('decrement','term')"><img src="<%=request.getContextPath()%>/images/reduce.gif" width="11" height="11" align="absmiddle" border="0"></a>
				          				<html:text property="term" styleClass="textbox" size="10" value="<%= frm.getTerm()%>"/>
                    <a href="javascript:incmonth('increment','term')"><img src="<%=request.getContextPath()%>/images/increase.gif" width="11" height="11" border="0" align="absmiddle"></a> </td> 
                </tr> 
                <tr> 
                  <td  class="search">Computed FAF Multiplier</td> 
                  <td class="search">:</td> 
                  <td  class="search"><%=tnf.format(objEPP.getFafMultiplier())%></td> 
                  <td class="search">Base Gas Price for Offer, $/MMBtu</td> 
                  <td class="search">:</td> 
                  <td class="search"> <a href="javascript:incmonth1('decrement','baseGasPrice')"><img src="<%=request.getContextPath()%>/images/reduce.gif" width="11" height="11" align="absmiddle" border="0"></a>
				          				<html:text property="baseGasPrice" styleClass="textbox" size="10"/>
                    <a href="javascript:incmonth1('increment','baseGasPrice')"><img src="<%=request.getContextPath()%>/images/increase.gif" width="11" height="11" border="0" align="absmiddle"></a> 
                    <input name="Submit" type="button" class="button_sub_internal" value="Go!" onclick="calldisplay();callsubmit()"></td> 
                </tr> 
              </table> 
              <table width="100%" cellpadding="0" cellspacing="0"> 
                <tr> 
                  <td><a href="javascript:showHideSpl1('tblSearch','fsPriceInfo','imgShowHide1','dvList');"><img src="<%=request.getContextPath()%>/images/hide.gif" name="imgShowHide" width="50" height="10" border="0" id="imgShowHide1"></a></td> 
                </tr> 
              </table> 
              <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblGraph" style="display:none;"> 
                <tr> 
                  <td class="pad"><img src="images/graph.gif" width="500" height="300"></td> 
                </tr> 
              </table> 
              <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblMultOff" style="display:block;"> 
                <tr> 
                  <td> <div style="overflow:hidden; width:700;" id="dvTitle"> 
                      <table width="700"  border="0" cellspacing="0" cellpadding="3"> 
                        <tr> 
                          <td width="90" class="cmbheader" >Contract Month </td> 
                          <td width="90" class="cmbheader">Month/Year </td> 
                          <td width="120" class="cmbheader">Index Price, $/MMBtu </td> 
                          <td width="120" class="cmbheader">FAF Multiplier </td> 
                          <td width="120" class="cmbheader">Equiv Wholesale $/MWh</td> 
                          <td class="cmbheader">Calendar Month kWh </td> 
                        </tr> 
                      </table> 
                    </div> 
                    <div style="overflow:auto; width:718; height:120;" id="dvList"> 
                      <table width="700" cellspacing="0" cellpadding="0"> 
                       <logic:iterate id="indDetails" name ="vecIndDetails" >
                       <tr> 
                          <td width="99" class="list_data" ><bean:write name="indDetails" property="contractMonth"/></td> 
                          <td width="96" class="list_data" ><bean:write name="indDetails" property="termMonth" format="MMM-yy"/>  </td> 
                          <td width="128" align="right" class="list_data" ><bean:write name="indDetails" property="indexPrice"/> </td> 
                          <td width="128" align="right" class="list_data" ><%=tnf.format(objEPP.getFafMultiplier())%> </td> 
                          <td width="127" align="right" class="list_data" ><bean:write name="indDetails" property="equiWholeSale$perMWH" format="#0.00"/>  </td> 
                          <td width="120" align="right" class="list_data" ><bean:write name="indDetails" property="totKwh" format="#0,000"/>  </td> 
                       </tr> 
                        </logic:iterate>
                      </table> 
                  </div></td> 
                </tr> 
              </table></td> 
          </tr> 
        </table> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td class="btnbg"><input name="Submit" type="button" class="button" value="Back" onClick="callpage('dashBoard')"> 
              <input name="createContract" type="button" class="button" value="Lock Deal" onClick="callContract('one')">
              <%if(objPreferenceProductsDAO.getProspectiveCustomerPreferenceByProduct(objPriceRunCustomerVO.getPriceRunCustomerRefId(),5)!=null)
              {%>
              <input name="createContractAll" type="button" class="button" value="Lock All Deal" onClick="callContract('All')">
              <%}%>
			  <input name="Submit3" type="button" class="button" value="Cancel" onClick="window.open('<%=request.getContextPath()%>/jsp/pricerun/runresult.jsp','_self')"> 
			  <input name="Submit2" type="button" class="button" value="Proposal" onClick="callcpe()"></td>
         </tr> 
        </table></td> 
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
<script>
function callContract(message)
{
   var term = document.forms[0].term.value;
   var temp= document.forms[0];
   var strEsiid = '';
   strEsiid = '<%=request.getParameter("strESIID")%>';
   if(message=='All')
   {
   var param = 'gasperMMBtu='+<%=frm.getBaseGasPrice()%>+'&message=allDealEPP&priceRunId='+<%=priceRunId%>+'&esiId='+strEsiid+'&message=allDealEPP';
   }
   else
   {
   var param = 'Term='+term+'&product=FAR'+'&gasperMMBtu='+<%=frm.getBaseGasPrice()%>+'&priceRunId='+<%=priceRunId%>+'&esiId='+strEsiid+'&message=deal';
   }
   var url = '<%=request.getContextPath()%>/servlet/createContact';
	showprogress('yes');
			if (window.XMLHttpRequest) // Non-IE browsers
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showContractResponse});
			}
			else if (window.ActiveXObject) // IE
			{
				req = new ActiveXObject("Microsoft.XMLHTTP");
				if (req)
				{
					req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showContractResponse});
				}
			}
}
function showContractResponse(req)
{
 var result = req.responseText;
	showprogress('no');
 if(result == 'Success')
 {
    document.getElementById('contarctsuccess').style.display = 'block';
    document.getElementById('contractfail').style.display = 'none';
  }
  else
  {
  document.getElementById('contractfail').style.display = 'block';
  document.getElementById('contarctsuccess').style.display = 'none';
  if(result != 'Failure')
      document.getElementById('contractfail').innerText = result;
  }
   
}

</script>
</html:form> 
</body>
</html:html>
<%}
catch(Exception e)
{
e.printStackTrace();
}
%>
<%}%>
