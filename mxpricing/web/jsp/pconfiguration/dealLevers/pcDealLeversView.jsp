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
<jsp:useBean id="frm" class="com.savant.pricing.presentation.security.NewRoleAddForm"/>
<jsp:setProperty name="frm" property="*" />
<%@ page import="com.savant.pricing.calculation.valueobjects.DealLeversVO" %>
<%@ page import="com.savant.pricing.calculation.valueobjects.UOMVO" %>
<%@ page import="com.savant.pricing.calculation.dao.DealLeversDAO"%>
<%
	
    DealLeversVO objDealLeversVO = new DealLeversVO();
    DealLeversDAO objDealLeversDAO = new DealLeversDAO();
    UOMVO objUOMVO = new UOMVO();
	String id = request.getParameter("pcdealLeversId");
	
	try
	{
		objDealLeversVO = objDealLeversDAO.getDealLever(Integer.parseInt(id));
		objUOMVO = objDealLeversVO.getUnit();
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
</head>
<body> 
<html:form action="/pcDealLeverView"> 
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="../../menu.jsp"/> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="250" class="page_title">Deal&nbsp;Adjustments</td>
          </tr>
      </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="fieldtitle">Deal&nbsp;Adjustments</td>
            <td class="fieldtitle">:</td>
            <td class="fieldata"><%=objDealLeversVO.getDealLever()%></td>
          </tr>
          <tr>
            <td width="120" class="fieldtitle">Value</td>
            <td width="1" class="fieldtitle">:</td>
            <td class="fieldata"><%=objDealLeversVO.getValue()%></td>
          </tr>
          <tr>
            <td class="fieldtitle">Unit of Measure </td>
            <td class="fieldtitle">:</td>
            <td class="fieldata"><%=objUOMVO.getUnit()%></td>
          </tr>
        </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="btnbg"><input name="Submit" type="button" class="button" value="List" onClick="window.open('<%=request.getContextPath()%>/jsp/pconfiguration/dealLevers/pcDealLevers.jsp','_self')">
            </td>
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