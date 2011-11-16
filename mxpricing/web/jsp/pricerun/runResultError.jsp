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
<%@ page import="com.savant.pricing.dao.PriceRunCustomerDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO"%>
<%
String priceRunId =null;
String priceRunRefNo = null;

if( request.getParameter("PriceRunRefNo")!=null)
{	
	priceRunRefNo = request.getParameter("PriceRunRefNo");
	session.setAttribute("PriceRunRefNo",priceRunRefNo);
}
else
{
	priceRunRefNo = (String)session.getAttribute("PriceRunRefNo");
}
if( request.getParameter("priceRunId")!=null)
{	
	priceRunId = request.getParameter("priceRunId");
	session.setAttribute("priceRunId",priceRunId);
}
else{
	priceRunId = (String)session.getAttribute("priceRunId");
}
PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
PriceRunCustomerVO objPriceRunCustomerVO = null;

if(priceRunId != null)
	objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(Integer.parseInt(priceRunId));
	pageContext.setAttribute("objPriceRunCustomerVO",objPriceRunCustomerVO);

%>
<html:html>
<head>
<script>
function callRunResult()
{
var temp = document.forms[0];
temp.formAction.value = "back";
temp.submit();
}
</script>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
</head>
<body > 
<html:form action="runresulterror"> 
<html:hidden property="formAction" />
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title" >Dashboard</td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
        <tr> 
          <td class="fieldata" colspan='6' class="message"><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages></td> 
        </tr> 
        <tr> 
          <td colspan='6'class='error'><html:errors/></td> 
        </tr> 
      </table> 
	<fieldset id="fsPriceInfo"> 
            <legend style="color:#0033CC">Pricing Info - Price RunId : <%=session.getAttribute("PriceRunRefNo")%></legend> 
      <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="tblSearch"> 
        <tr> 
          <td width="163" class="fieldtitle">Customer Name </td> 
          <td width="1" class="fieldtitle">:</td> 
          <bean:define id="custRunDetails" name="objPriceRunCustomerVO"/>
          <td width="752" ><bean:write name="custRunDetails" property="prospectiveCustomer.customerName" ignore="true"/></td> 
          <td width="48" >&nbsp</td> 
        </tr> 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
        <tr> 
          <td width="163" class="fieldtitle">RunStatus</td> 
          <td width="1" class="fieldtitle">:</td> 
          <td width="752" class="fielddata"><logic:equal value="false" property="runStatus" name="custRunDetails">Failure</logic:equal></td> 
          <td width="48" >&nbsp</td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="163" class="fieldtitle" valign='top'>Reason</td> 
          <td width="1" class="fieldtitle" valign='top'>:</td> 
          <td width="800" valign="middle"><bean:write name="custRunDetails" property="reason" ignore="true"/></td> 
        </tr> 
      </table> 
 </fieldset> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="btnbg"><input name="button" class='button' type="button" value="Back" onclick="callRunResult()">
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
<%}%>