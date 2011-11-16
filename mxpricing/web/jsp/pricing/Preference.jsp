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
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.savant.pricing.dao.PICDAO"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.PreferenceAnalystForm" />
<%
	try
	{
		if(request.getParameter("prsCustId")!= null && !request.getParameter("prsCustId").equals("") )
		  {
			frm.setPrsCustId(request.getParameter("prsCustId"));
		  }
		else
		  {
			frm.setPrsCustId((String)request.getAttribute("prsCustId"));
		  }

		ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
		ProspectiveCustomerVO  objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(frm.getPrsCustId()));
		List vecValidESIID = new ArrayList();
		List vecInValidESIID = new ArrayList();
		PICDAO objPICDAO = new PICDAO();
		vecValidESIID =  objPICDAO.getAllValidESIID(Integer.parseInt(frm.getPrsCustId()));
		vecInValidESIID =  objPICDAO.getAllInValidESIID(Integer.parseInt(frm.getPrsCustId()));
		pageContext.setAttribute("objProspectiveCustomerVO",objProspectiveCustomerVO);
		pageContext.setAttribute("vecValidESIID",vecValidESIID);
		pageContext.setAttribute("vecInValidESIID",vecInValidESIID);
%>

<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/mm_menu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
<script>

function importPic()
{
	try{
	var temp = document.forms[0];
	temp.action='<%=request.getContextPath()%>/FileUpload.do?prsCustId=<%=frm.getPrsCustId()%>';
	temp.formActions.value="next";
	temp.submit();
	}catch(err)
	{
	}
}
function callEcomp()
{
	try{
	var temp = document.forms[0];
	if(<%=!objProspectiveCustomerVO.isMmCust()%>)
	{
		temp.action='<%=request.getContextPath()%>/Ecomp.do?prsCustId=<%=frm.getPrsCustId()%>';
	}
	else	
	{
		temp.action='<%=request.getContextPath()%>/DealLeverAnalyst.do?prsCustId=<%=frm.getPrsCustId()%>';
	}
	temp.formActions.value="next";
	temp.submit();
	}catch(err)
	{
	}
}
function ListBoxOptionMove(objSourceElement, objTargetElement)
{
   var aryTempSourceOptions = new Array();
   var aryTempTargetOptions = new Array();
   var x = 0;
   for (var i = 0; i < objSourceElement.length; i++) 
   {
		if (objSourceElement.options[i].selected) 
		{
			var intTargetLen = objTargetElement.length++;
			objTargetElement.options[intTargetLen].text = objSourceElement.options[i].text;
			objTargetElement.options[intTargetLen].value = objSourceElement.options[i].value;
		}
		else
		{
			 var objTempValues = new Object();
			 objTempValues.text = objSourceElement.options[i].text;
			 objTempValues.value = objSourceElement.options[i].value;
			 aryTempSourceOptions[x] = objTempValues;
			 x++;
		}
	}
	for (var i = 0; i < objTargetElement.length; i++)
	{
		var objTempValues = new Object();
		objTempValues.text = objTargetElement.options[i].text;
		objTempValues.value = objTargetElement.options[i].value;
		aryTempTargetOptions[i] = objTempValues;
	}
//	jSort(aryTempTargetOptions);
//	aryTempTargetOptions.sort(sortByText);
	for (var i = 0; i < objTargetElement.length; i++)
	{
		objTargetElement.options[i].text = aryTempTargetOptions[i].text;
		objTargetElement.options[i].value = aryTempTargetOptions[i].value;
		objTargetElement.options[i].selected = false;
	}
	objSourceElement.length = aryTempSourceOptions.length;
	for (var i = 0; i < aryTempSourceOptions.length; i++)
	{
		objSourceElement.options[i].text = aryTempSourceOptions[i].text;
		objSourceElement.options[i].value = aryTempSourceOptions[i].value;
		objSourceElement.options[i].selected = false;
	}
}
function callSubmit()
{
	var tmp = document.forms[0];
	if (tmp.selectedGroups.selectedIndex == 0)
	{
	}
	selectCurrentGroups();
	var str_key="";
	for(var i =0;i<tmp.selectedGroups.options.length;i++)
	{
		if(i==0)
		{
			str_key+=tmp.selectedGroups.options[i].text;
		}
		else
		{
			str_key+=","+tmp.selectedGroups.options[i].text;
		}
	}
	tmp.esiids.value= str_key;
	tmp.formActions.value="update";
	tmp.submit();
}
function importPic()
{
	try{
	var temp = document.forms[0];
	temp.action='<%=request.getContextPath()%>/FileUpload.do?prsCustId=<%=frm.getPrsCustId()%>';
	temp.formActions.value="next";
	temp.submit();
	}catch(err)
	{
	}
}
function selectCurrentGroups()
{
	var temp = document.forms[0];
	for(var i =0;i<temp.selectedGroups.options.length;i++)
	{
		temp.selectedGroups.options[i].selected = true;
	}
}
</script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
</head>
<body> 
<html:form action="/PreferenceAnalyst">
<html:hidden property="prsCustId" />
<html:hidden property="formActions"/>
<html:hidden property="esiids" />
  <table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
    <tr> 
      <td  valign="top"> <jsp:include page="/jsp/menu.jsp"/> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="250" class="page_title"> Prospective Customers</td> 
            <td class="page_title">&nbsp;</td> 
            <font class="message" ><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages></font> 
            <font class="error" ><html:errors/></font>
          </tr> 
        </table> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
          </tr> 
        </table> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="112" class="subtab_off" id="set1"><a href="<%=request.getContextPath()%>/jsp/pricing/ProspectiveCustomersAnalystEdit.jsp?prospectiveCustId='<%=frm.getPrsCustId()%>'" >Customer&nbsp;Details </a></td> 
            <td width="78" class="subtab_off" ><a href="#" onclick="importPic();" >Import&nbsp;CUD</a></td> 
            <td width="82" class="subtab_on" >&nbsp;Select ESIIDs</td> 
            <%if(!objProspectiveCustomerVO.isMmCust())
            {%>
			 <td width="82" class="subtab_off" ><a href="<%=request.getContextPath()%>/Ecomp.do?prsCustId=<%=frm.getPrsCustId()%>" >&nbsp;Select&nbsp;Components</a></td>
			 <%}%>
            <td width="80" class="subtab_off"><a href="<%=request.getContextPath()%>/DealLeverAnalyst.do?prsCustId=<%=frm.getPrsCustId()%>">Deal&nbsp;Adjustment</a></td> 
            <td>&nbsp;</td> 
          </tr> 
        </table> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td class="subtabbase"><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
          </tr> 
        </table> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
          </tr> 
        </table> 
        <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
          <tr> 
            <td width="149" class="fieldtitle">Customer Name</td> 
            <td width="1" class="fieldtitle">:</td> 
            <td width="200" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="customerName" ignore="true" /></td> 
            <td width="149" class="fieldtitle">Customer DBA</td> 
            <td width="1" class="fieldtitle">:</td> 
            <td class="fieldata"><bean:write name="objProspectiveCustomerVO" property="customerDBA" ignore="true" /></td> 
          </tr> 
          <tr> 
            <td width="149" class="fieldtitle">Customer ID</td> 
            <td width="1" class="fieldtitle">:</td> 
            <td width="200" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="customerId" ignore="true"/></td> 
            <td width="100" class="fieldtitle">No. of ESIIDs </td> 
            <td width="1" class="fieldtitle">:</td> 
            <td class="fieldata"><%=vecValidESIID.size()%></td> 
          </tr> 
        </table> 
        <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
          <tr id="tblPremium"> 
            <td> <br> 
              <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
              <tr>
              	  <td width="149" class="fielddata">&nbsp;</td> 
                  <td width="1" class="fielddata">&nbsp;</td> 
                  <td class="fieldtitle" width="144" align='center'>Excluded</td>
                  <td width="33" class="fielddata">&nbsp;</td> 
                  <td width="168"  class="fieldtitle" align='center'>Included</td>
                  <td width="483"  valign="bottom" class="fieldata">&nbsp;</td>
              </tr>
                <tr> 
                  <td width="149" class="fieldtitle">ESIIDs</td> 
                  <td width="1" class="fieldtitle">:</td> 
                  <td class="fieldata" width="144">
                  <html:select property="possibleGroups" multiple="true" size="10" style="width:170">
				  <logic:present name="vecInValidESIID">
                  <html:options collection="vecInValidESIID" property="esiId" labelProperty="esiId"/>
				  </logic:present>
				  </html:select>
				</td> 
                  <td width="33" class="fielddata"><a href="#" onClick='ListBoxOptionMove(possibleGroups,selectedGroups)'>&nbsp;<img src="images/add.gif" width="20" height="20" border="0"></a>&nbsp;<br> 
                  <a href="#" onClick='ListBoxOptionMove(selectedGroups,possibleGroups)'>&nbsp;<img src="images/remove.gif" width="20" height="20" border="0"></a> &nbsp;</td> 
                  <td class="fielddata"><html:select property="selectedGroups" multiple="true" size="10" style="width:170">
                <logic:present name="vecValidESIID">
                  <html:options collection="vecValidESIID" property="esiId" labelProperty="esiId"/>
				</logic:present>
              </html:select></td>
                </tr> 
              </table> 
			  <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblOverri">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>

              <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="btnSubmit"> 
                <tr> 
                  <td class="fieldata">
                   <input name="Submit" type="button" class="button" value="Submit" onclick="callSubmit()"> 
                   <input name="next" type="button" class="button" value="Next" onClick="callEcomp()"> 
                   <input name="Cancel" type="button" class="button" id="Cancel" value="Cancel" onClick="importPic()"> 
                   
                </tr> 
            </table></td> 
          </tr> 
        </table> 
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
<%
}
catch(Exception e)
{
e.printStackTrace();
}
}
%>