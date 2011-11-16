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
<%@ page import="com.savant.pricing.transferobjects.HeatRateProduct"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.HeatRateForm" />
<jsp:setProperty name="frm" property="*" />

<%
try{
SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
SimpleDateFormat monthFormat = new SimpleDateFormat("MMM yyyy");
NumberFormat dnf = NumberUtil.doubleFraction(); 
NumberFormat tnf = NumberUtil.tetraFraction();
PricingDAO objPricingDAO = new PricingDAO();
String priceRunId =null;
GregorianCalendar gc = new GregorianCalendar();
gc.setTime(new Date());
String esiids = "";
gc.add(Calendar.DATE ,1);
Date expiryDate = gc.getTime();
String priceRunRefNo = null;

if( request.getParameter("PriceRunRefNo")!=null)
{	
	priceRunRefNo = request.getParameter("PriceRunRefNo");
}
if( request.getParameter("priceRunId")!=null)
{	
	priceRunId = request.getParameter("priceRunId");
	frm.setRunRefId(priceRunId);
	
}
if(request.getParameter("strESIID")!=null)
{
esiids = request.getParameter("strESIID");
}
if(request.getParameter("term")== null && request.getParameter("hidTerm")!=null)
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
HeatRateProduct objHeatRateProduct = objPricingDAO.getHeatRateProduct(Float.parseFloat(frm.getHeatRateInput()),Integer.parseInt(frm.getTerm()),Integer.parseInt(priceRunId),esiids);

Vector vecIndDetails = objHeatRateProduct.getVecIndividualHeatRateProduct();

pageContext.setAttribute("vecIndDetails",vecIndDetails);
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/common.js"></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
</head>
<script>
var localESIID = '';
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
		if(parseInt(vartxt.value,10) <= 1)
			vartxt.value=1;
		else
			vartxt.value = parseInt(vartxt.value,10) - 1 ;	
	}
}
function incmonth1(type,txt){
	var vartxt = document.getElementById(txt);

	if(type == "increment"){
		if(parseInt(vartxt.value,10) == 60){
			return;
		}
		else{
			vartxt.value = parseInt(vartxt.value,10) + 50 ;
		}
	}
	else{
		if(parseInt(vartxt.value,10) == 1)
			return;
		else
			vartxt.value = parseInt(vartxt.value,10) - 50 ;	
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
document.forms[0].priceRunId.value = '<%=request.getParameter("priceRunId")%>';
document.forms[0].PriceRunRefNo.value = '<%=request.getParameter("PriceRunRefNo")%>';

}
</script>
</head>
<body onload='loadDefault();'> 
<html:form action="heatrate" method="post">
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
            <td width="250" class="page_title">Heat&nbsp;Rate&nbsp;Product</td> 
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
            <td width="78" class="subtab_on" >Heat&nbsp;Rate</td> 
            <td width="100" class="subtab_off" ><a href="javascript:callpage('aggLoadProfile')">Aggregated&nbsp;LP</a></td> 
            <td >&nbsp;</td> 
          </tr> 
        </table> 
         <br><table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
         
          <tr> 
            <td width="193" class="fieldtitle " >Heat Rate, Btu/kWh
</td> 
            <td width="1" class="fieldtitle ">:</td> 
            <td  class="fieldata "> <%=frm.getHeatRateInput()%>
</td> 
          </tr> 
          <tr > 
            <td class="fieldtitle ">Retail Adder, $/MWH
 </td> 
            <td class="fieldtitle ">:</td> 
            <td class="fieldata "> <%=objHeatRateProduct.getRetailAdder() %>
</td> 
          </tr>
          
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr id="tblDashBrd"> 
            <td> <fieldset id="fsPriceInfo"> 
              <legend style="color:#0033CC">Pricing Info - Price Run ID: <%=priceRunRefNo%></legend> 
              <br> 
              <table width="100%" cellpadding="0" cellspacing="0"> 
                <tr> 
                  <td width="112" class="fieldtitle">Customer</td> 
                  <td width="4" class="fieldtitle">:</td> 
                  <td width="203" class="fieldata"><%=objHeatRateProduct.getCustomerName()%> </td> 
                  <td width="234" class="fieldtitle">Fixed Price: Energy Only Price, $/MWh</td> 
                  <td width="4" class="fieldtitle">:</td> 
                  <td width="127" class="fieldata"><%=dnf.format(objHeatRateProduct.getFixedPrice())%></td> 
                  <td width="123"  class="fieldtitle">Offer Date</td>
                  <td width="4" class="fieldtitle">:</td>
                  <td width="160" class="fieldata"><%=sdf.format(new Date())%></td>
                </tr> 
                <tr> 
                  <td class="fieldtitle">Total ESI ID </td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"> <%=objHeatRateProduct.getNoOfEsiIds()%></td> 
                  <td class="fieldtitle">Equivalent Wholesale Price, $/MWh</td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"><%=dnf.format(objHeatRateProduct.getEquivalentWholeSalePrice())%> </td> 
                  <td class="fieldtitle">Expiry Date</td>
                  <td class="fieldtitle">:</td>
                  <td class="fieldata"><%=sdf.format(expiryDate)%></td>
                </tr> 
                <tr> 
                  <td class="fieldtitle">Start Month</td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"><%=monthFormat.format(objHeatRateProduct.getContractStartDate())%></td> 
                  <td class="fieldtitle">Stop Month </td> 
                  <td class="fieldtitle">:</td> 
                  <td class="fieldata"><%=monthFormat.format(objHeatRateProduct.getContractEndDate())%></td> 
                  <td class="fieldata">&nbsp;</td>
                  <td class="fieldata">&nbsp;</td>
                  <td class="fieldata">&nbsp;</td>
                </tr> 
              </table> 
              </fieldset> 
              <table width="100%" cellpadding="0" cellspacing="0"> 
                <tr> 
                  <td><a href="javascript:showHideSpl1('fsPriceInfo','tblSearch','imgShowHide2','dvList')"><img src="<%=request.getContextPath()%>/images/hide.gif" name="imgShowHide" width="50" height="10" border="0" id="imgShowHide2"></a></td> 
                </tr> 
              </table> 
              <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="tblSearch"> 
                <tr> 
                  <td width="150" class="search">Heat Rate Data Source</td> 
                  <td width="10" class="search">:</td> 
                  <td width="271" class="search"><select name="select"> 
                      <option selected>Fixed Heat Rate Input</option> 
                    </select></td> 
                  <td width="226" class="search">Fixed Heat Rate Input (Btu/kWh)</td> 
                  <td width="9" class="search">:</td> 
                  <td width="327" class="search"><a href="javascript:incmonth1('decrement','heatRateInput')"><img src="<%=request.getContextPath()%>/images/reduce.gif" width="11" height="11" align="absmiddle" border="0"></a> 
                   <html:text property="heatRateInput" styleClass="textbox" size="10"/>
                    <a href="javascript:incmonth1('increment','heatRateInput')"><img src="<%=request.getContextPath()%>/images/increase.gif" width="11" height="11" border="0" align="absmiddle"></a></td> 
                </tr> 
                <tr> 
                  <td class="search">Gas Price Index</td> 
                  <td class="search">:</td> 
                  <td class="search">NYMEX Henry Hub </td> 
                  <td class="search">Term in Months:</td> 
                  <td class="search">:</td> 
                  <td class="search"><a href="javascript:incmonth('decrement','term')"><img src="<%=request.getContextPath()%>/images/reduce.gif" width="11" height="11" align="absmiddle" border="0"></a>
				          				<html:text property="term" styleClass="textbox" size="10" value="<%= frm.getTerm()%>"/>
                    <a href="javascript:incmonth('increment','term')"><img src="<%=request.getContextPath()%>/images/increase.gif" width="11" height="11" border="0" align="absmiddle"></a>
                    <input name="Submit4" type="submit" class="button_sub_internal" value="Go!"></td> 
               
                  
                </tr> 
                
              </table> 
              <table width="100%" cellpadding="0" cellspacing="0"> 
                <tr> 
                  <td><a href="javascript:showHideSpl1('tblSearch','fsPriceInfo','imgShowHide1','dvList')"><img src="<%=request.getContextPath()%>/images/hide.gif" name="imgShowHide" width="50" height="10" border="0" id="imgShowHide1"></a></td> 
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
                        <tr > 
                          <td class="cmbheader"  width="90">Contract&nbsp;Month </td> 
                          <td class="cmbheader"  width="70">Month/Year </td> 
                          <td class="cmbheader"  width="115">Index Price, $/MMBtu </td> 
                          <td  class="cmbheader" width="115">Heat Rate, Btu/kWh </td> 
                          <td class="cmbheader" width="130">Equiv Wholesale $/MWH </td> 
                          <td class="cmbheader"> Calendar Month kWh </td> 
                        </tr> 
                      </table> 
                    </div> 
                    <div style="overflow:auto; width:718; height:120;" id="dvList"> 
                      <table width="700" cellspacing="0" cellpadding="0"> 
                      <logic:iterate id="indDetails" name ="vecIndDetails" >
                       <tr> 
                          <td width="94" class="list_data" ><bean:write name="indDetails" property="contractMonth"/></td> 
                          <td width="77" class="list_data" ><bean:write name="indDetails" property="termMonth" format="MMM-yy"/>  </td> 
                          <td width="124" align="right" class="list_data" ><bean:write name="indDetails" property="indexPrice" format="##,000.00"/> </td> 
                          <td width="121" align="right" class="list_data" ><%= frm.getHeatRateInput()%></td> 
                          <td width="139" align="right" class="list_data" ><bean:write name="indDetails" property="equiWholeSale$PerMWH" format="##,000.00"/>  </td> 
                          <td width="143" align="right" class="list_data" ><bean:write name="indDetails" property="totKwh" format="##,000.00"/>  </td> 
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
			  <input name="Submit3" type="button" class="button" value="Cancel" onClick="window.open('<%=request.getContextPath()%>/jsp/pricerun/runresult.jsp','_self')"></td> 
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
</html:form> 
</body>
</html:html>
<%}catch(Exception e)
{
	e.printStackTrace();
}
}
%>