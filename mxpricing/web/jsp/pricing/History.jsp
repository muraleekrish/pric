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
<%@ page import="com.savant.pricing.dao.ContractsDAO"%>
<%@ page import="com.savant.pricing.dao.PreferenceProductsDAO"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.dao.PreferenceTermsDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%@ page import="com.savant.pricing.common.chart.HistoryChartCollection"%>
<%@ page import="com.savant.pricing.transferobjects.TermDetails"%>
<%@ page import = "java.io.PrintWriter" %>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="com.savant.pricing.common.NumberUtil"%>
<jsp:useBean id="frm" class="com.savant.pricing.summary.SummaryForm" />
<jsp:setProperty name="frm" property="*" />
<%@ page import="java.util.*"%>
<html:html>
<%
	NumberFormat sf = NumberFormat.getIntegerInstance();
	PreferenceProductsDAO objPreferenceProductsDAO = new PreferenceProductsDAO();
	ContractsDAO objContractsDAO = new ContractsDAO();
	PreferenceTermsDAO objPreferenceTermsDAO = new PreferenceTermsDAO();
	ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
	HistoryChartCollection objHistoryChartCollection = new HistoryChartCollection();
	java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MM-dd-yyyy");
	ProspectiveCustomerVO objProspectiveCustomerVO = null;
	List lstPriceResults = null;
	List lstTerms = null;
	boolean farProduct = false;
	String[] txt = new String[2];
	String maxdate = "";
	String mindate = "";
	String full = "";
	int pricerunid = 0;
	int noEsiid = 0;
	
	if(frm.getCustId().equalsIgnoreCase(""))
		frm.setCustId(request.getParameter("prsCustId"));
	int prsCustId = Integer.parseInt(frm.getCustId());
	
	HashMap hmResult = (HashMap)objPreferenceProductsDAO.getAllPreferenceProductsByCust(prsCustId);
	objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(prsCustId);
	lstTerms = (List)objPreferenceTermsDAO.getAllTermsByCust(prsCustId);
	Set setProducts = hmResult.keySet();
	int productId = ((Integer)setProducts.iterator().next()).intValue();
	int term = 0;
	full = objContractsDAO.getDates(prsCustId);
	maxdate = full.split(",")[0];
	mindate = full.split(",")[1];
	pricerunid = Integer.parseInt(objContractsDAO.getPriceRunId(prsCustId));
	txt = objContractsDAO.getesiids(pricerunid);
	noEsiid = txt[0].split(",").length;
	TermDetails objTermDetails = null;
	if(lstTerms.size()>0)
	{
		objTermDetails = (TermDetails)lstTerms.get(0);
    }
    term = objTermDetails.getTerm();
	if(frm.getProduct().equalsIgnoreCase(""))
		frm.setProduct(productId+"");
	if(frm.getTerm().equalsIgnoreCase(""))
		frm.setTerm(term+"");
	lstPriceResults = (List)objContractsDAO.getAllPriceByCustmerWise(prsCustId,Integer.parseInt(frm.getProduct()),frm.getRunType(),Integer.parseInt(frm.getTerm()),df.parse(frm.getFromDate()),df.parse(frm.getToDate()));
	int recordCount = lstPriceResults.size();
	java.text.NumberFormat nf = com.savant.pricing.common.NumberUtil.doubleFraction();
	
	pageContext.setAttribute("lstPriceResults",lstPriceResults);
	pageContext.setAttribute("lstResult",hmResult);
	pageContext.setAttribute("lstTerms",lstTerms);
	pageContext.setAttribute("objProspectiveCustomerVO",objProspectiveCustomerVO);
	
	String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";
	String menuFtr[]= menupath.split("For");
	menupath = menupath.replaceFirst(menuFtr[1].trim(),(objProspectiveCustomerVO.getCustomerId()==null)?"All Customers":objProspectiveCustomerVO.getCustomerId()+" - "+objProspectiveCustomerVO.getCustomerName());
	session.removeAttribute("home");
  	session.setAttribute("home",menupath);
  	
  	int browserHt = 0;
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 360;
	else
		browserHt = 340;
	%>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
<script type="text/javascript">
function submitAction()
{
var temp = document.forms[0];
temp.custId.value = '<%=frm.getCustId()%>';
temp.submit();
}
function displaydata(message)
{
 	if(message=='bar')
		 {
		    document.getElementById('barchart').style.display = 'block';
			document.getElementById('linechart').style.display = 'none';
		}
		else
		{
		   document.getElementById('barchart').style.display = 'none';
		   document.getElementById('linechart').style.display = 'block';
		}
}
function loadDefault()
{
var temp = document.forms[0];
}
</script>
</head>
<body onload='loadDefault()'> 
<html:form action="/viewhistory" > 
<html:hidden property="custId"/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="/jsp/menu.jsp"/> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="950" class="page_title">History</td> 
		  <td class="page_title"><a href="<%=request.getContextPath()%>/jsp/pricerun/proposal.jsp"><font size="2" color="blue">Back</font></a></td>
        </tr> 
      </table> 
      <fieldset style='margin: -5px 5px 0px 5px'> 
      <legend> Customer Info. </legend> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr > 
          <td width="111" class="fieldtitle" >Customer Name </td> 
          <td width="4" class="fieldtitle">:</td> 
          <td width="234" class="fielddata"><bean:write name="objProspectiveCustomerVO" property="customerName" /> </td> 
          <td width="128" class="fieldtitle"  >Customer&nbsp;Id</td> 
          <td width="4" class="fieldtitle">:</td> 
          <td width="128" class="fielddata" ><bean:write name="objProspectiveCustomerVO" property="customerId" ignore="true" /> </td> 
          <td width="202" class="fieldtitle" >First Run Date </td> 
          <td width="4" class="fieldtitle">:</td> 
          <td width="286" class="fielddata" ><%=mindate%></td> 
        </tr> 
        <tr > 
          <td class="fieldtitle" >No. of ESIID</td> 
          <td class="fieldtitle">:</td> 
          <td class="fielddata"><%=noEsiid%></td> 
          <td class="fieldtitle" >Annual kWh </td> 
          <td class="fieldtitle">:</td> 
          <td class="fielddata" ><%=sf.format(Double.parseDouble(txt[1]))%></td> 
          <td class="fieldtitle" >Most Recent Run Date </td> 
          <td class="fieldtitle">:</td> 
          <td class="fielddata" ><%=maxdate%></td> 
        </tr> 
      </table> 
      </fieldset> 
      <table width='100%' border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="90" class="search" >Product</td> 
          <td width="1" class="search">:</td> 
          <td width="163" class="search"><html:select property="product" onchange="submitAction()">
				                         <html:options collection="lstResult" property="key" labelProperty="value"/>
			                             </html:select></td> 
          <td width='90' class='search' >Terms</td> 
          <td width='1' class='search'>:</td> 
          <td width='90' class='search'><html:select property="term" onchange="submitAction()">
										<html:options collection="lstTerms" property="term" labelProperty="term"/>
			                             </html:select> </td> 
          <td width='86' class="search" >Run Type </td> 
          <td width='1' class="search">:</td> 
          <td width='90'class="search"><html:select property="runType" onchange="submitAction()">
                                         <html:option value="all">All</html:option>
										 <html:option value="M">Manual</html:option>
										 <html:option value="A">Auto Run</html:option>
			                             </html:select> </td> 
          <td width='70' class="search" >Period </td> 
          <td width='1' class="search">:</td> 
          <td class="search"> <html:text property="fromDate" styleClass="textbox" size="10" maxlength="10" readonly="true" /> <a href="#" onClick="showCalendarControl(document.getElementById('fromDate'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif"  width="16" height="16" border="0" align="absmiddle" id="todat"></a>
          To: <html:text property="toDate" styleClass="textbox" size="10" maxlength="15" readonly="true" />
			<a href="#" onClick="showCalendarControl(document.getElementById('toDate'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif"  width="16" height="16" border="0" align="absmiddle" id="fromdat"></a>
			<html:button property="button" value="Go!" styleClass="button_sub_internal" onclick="submitAction()" /></td> 
	</tr> 
      </table> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
      	<tr> 
	        <td width="30%" style='background-color:#EFEFEF'>&nbsp;</td>
	        <td width="600" style='background-color:#EFEFEF' align='right'>
            	<input name="radiobutton" type="radio" value="radiobutton" id='barchartRadio' onClick="displaydata('bar')" checked><label for='fwdBlkDet'><b>Bar Chart</b></label>&nbsp;&nbsp;
            	<input name="radiobutton" type="radio" value="radiobutton" id='linechartradio' onClick="displaydata('line')"><label for='fwdProfDet'><b>Line Chart<b/></label>
	        </td>
	    </tr>
        <tr> 
          <td valign='top' width="30%">
          <table border="0" width="100%" cellspacing="0" cellpadding="0">
              <tr height='20'><td>&nbsp;</td></tr>
           </table>
          <div style="overflow:auto; width:100%; height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')"> 
              <table border="0" width="100%" cellspacing="0" cellpadding="0">
                <tr class='staticHeader'>
                  <td width="16%" align="center"  class="tblheader">Sl. No </td> 
                  <td width="46%" align="center"  class="tblheader">Run Date</td> 
                  <td width="38%" align="center"  class="tblheader">Price($/MWh)</td> 
                </tr> 
				<logic:iterate id="priceDetails" name="lstPriceResults" indexId="i"> 
                <tr> 
                  <td class="tbldata" align="right"><%=i.intValue()+1%></td> 
                  <td class="tbldata" align="right"><bean:write name="priceDetails" property="runDateTime" format="MM-dd-yy hh:mm"/></td> 
                  <td class="tbldata" align="right">
					<%if(frm.getProduct().equalsIgnoreCase("5")){
					farProduct = true;
					%>
					<bean:define id="baserate" name="priceDetails" property="baseRate"/>
					<bean:define id="ff" name="priceDetails" property="fuelFactor" ></bean:define>
					<%=nf.format(((Float)baserate).doubleValue()+((Float)ff).doubleValue())%>
					<%}else{%>
					<bean:write name="priceDetails" property="fixedPrice" format="0.00"/>
					<%}
					%>
					</td> 
                </tr> 
                </logic:iterate>
				<%
				
				int emptySize = 0;
				if(lstPriceResults.size() <10)
				{
					emptySize = 10-lstPriceResults.size();
					for(int i=0;i<emptySize;i++)
					{
				%>
					<tr><td class="tbldata">&nbsp;</td><td class="tbldata">&nbsp;</td><td class="tbldata">&nbsp;</td></tr>
				<%
					}
				} 
				%>
              </table> 
            </div> 
            <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="sort"> 
              <tr> 
                <td class='fieldata' style="color:#0033CC;"> <b>Total Records: <%=recordCount%></b></td> 
              </tr> 
            </table></td> 
          <td width="50%">
          <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
             <tr>
              <td id='barchart'><%String barchart = objHistoryChartCollection.getBarChart(session,lstPriceResults,farProduct,"total",new PrintWriter(out));
              String barChartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + barchart;%>
		  <img src="<%= barChartURL%>" border=0 usemap="#<%=barchart%>"></td>
		    <td  style='display:none' id='linechart'><%String linechart = objHistoryChartCollection.getLineChart(session,lstPriceResults,farProduct,"total",new PrintWriter(out));
              String lineChartURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + linechart;%>
		  <img src="<%= lineChartURL%>" border=0 usemap="#<%=linechart%>"></td>
              </tr>
            </table>
          </td> 
        </tr> 
      </table></td> 
  </tr> 
  <tr> 
    <td height="20"><table width="100%"  border="0" cellpadding="0" cellspacing="0" > 
        <tr> 
          <td class="copyright_link"><%=menupath%></td> 
          <td width="250"   class="copyright" align='right'>Design Rights Savant Technologies </td> 
        </tr> 
      </table></td> 
  </tr> 
</table> 
</html:form> 
</body>
</html:html>
<%}%>
