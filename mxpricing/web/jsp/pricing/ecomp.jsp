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
<%@ page import="com.savant.pricing.dao.CustEnergyComponentsDAO"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.EnergyComponentsVO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.CustEnergyComponentsVO"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.EcompoForm" />
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
		CustEnergyComponentsDAO objCustEnergyComponentsDAO = new CustEnergyComponentsDAO();
		ProspectiveCustomerVO  objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(frm.getPrsCustId()));
		HashMap hmRes = new HashMap();
		HashMap hmValid = new HashMap();
		HashMap hmNotValid = new HashMap();
        hmRes = objCustEnergyComponentsDAO.getEngCompo(Integer.parseInt(frm.getPrsCustId()));
		hmValid = (HashMap)hmRes.get("valid");
		System.out.println(" valid size " + hmValid.size());
		hmNotValid = (HashMap)hmRes.get("notvalid");
		System.out.println("In valid size " + hmNotValid.size());
		pageContext.setAttribute("objProspectiveCustomerVO",objProspectiveCustomerVO);
		pageContext.setAttribute("hmValid",hmValid);
		pageContext.setAttribute("hmNotValid",hmNotValid);
%>

<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/submenu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/mm_menu.js"></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
<script>

function callEsiid()
{
	try{
	var temp = document.forms[0];
	temp.action='<%=request.getContextPath()%>/PreferenceAnalyst.do?prsCustId=<%=frm.getPrsCustId()%>';
	temp.formActions.value="next";
	temp.submit();
	}catch(err)
	{
	}
}
function callDealLever()
{
	try{
	var temp = document.forms[0];
	temp.action='<%=request.getContextPath()%>/DealLeverAnalyst.do?prsCustId=<%=frm.getPrsCustId()%>';
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
	if (tmp.selectedEngComp.selectedIndex == 0)
	{
	}
	selectCurrentGroups();
	var str_key="";
	for(var i =0;i<tmp.selectedEngComp.options.length;i++)
	{
		if(i==0)
		{
			str_key+=tmp.selectedEngComp.options[i].value;
		}
		else
		{
			str_key+=","+tmp.selectedEngComp.options[i].value;
		}
	}

	selectUnCurrentGroups();
	var str_Unselkey="";
	for(var i =0;i<tmp.possibleEngComp.options.length;i++)
	{
		if(i==0)
		{
			str_Unselkey+=tmp.possibleEngComp.options[i].value;
		}
		else
		{
			str_Unselkey+=","+tmp.possibleEngComp.options[i].value;
		}
	}
	tmp.unselenergyComponents.value= str_Unselkey;
	tmp.energyComponents.value= str_key;
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
	for(var i =0;i<temp.selectedEngComp.options.length;i++)
	{
		temp.selectedEngComp.options[i].selected = true;
	}
}

function selectUnCurrentGroups()
{
	var temp = document.forms[0];
	for(var i =0;i<temp.possibleEngComp.options.length;i++)
	{
		temp.possibleEngComp.options[i].selected = true;
	}
}
</script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
</head>
<body> 
<html:form action="/Ecomp">
<html:hidden property="prsCustId" />
<html:hidden property="formActions"/>
<html:hidden property="energyComponents" />
<html:hidden property="unselenergyComponents" />
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
			 <td width="82" class="subtab_off" ><a href="<%=request.getContextPath()%>/PreferenceAnalyst.do?prsCustId=<%=frm.getPrsCustId()%>" >&nbsp;Select ESIIDs</a></td>
            <td width="82" class="subtab_on" >&nbsp;Select&nbsp;Components</td> 
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
            <td width="149" class="fieldtitle">No. of Selected Energy Components</td> 
            <td width="1" class="fieldtitle">:</td> 
            <td class="fieldata"><%=hmValid.size()%></td> 
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
                  <td width="149" class="fieldtitle">Energy Components</td> 
                  <td width="1" class="fieldtitle">:</td> 
                  <td class="fieldata" width="144">
                  
                  <html:select property="possibleEngComp" multiple="true" size="10" style="width:170">
				 	<%
				for (Iterator it1=hmNotValid.entrySet().iterator(); it1.hasNext(); ) 
                {
                      Map.Entry entry1 = (Map.Entry) it1.next();
                      String key = entry1.getKey().toString();
                      String value = entry1.getValue().toString();
         		%>
 				<option value='<%=key%>'><%=value%></option>
           		<%  } %> 
             
				  </html:select>
				</td> 
                  <td width="33" class="fielddata"><a href="#" onClick='ListBoxOptionMove(possibleEngComp,selectedEngComp)'>&nbsp;<img src="images/add.gif" width="20" height="20" border="0"></a>&nbsp;<br> 
                  <a href="#" onClick='ListBoxOptionMove(selectedEngComp,possibleEngComp)'>&nbsp;<img src="images/remove.gif" width="20" height="20" border="0"></a> &nbsp;</td> 
                  <td class="fielddata">
                  
				<html:select property="selectedEngComp" multiple="true" size="10" style="width:170">
				<%
				for (Iterator it1=hmValid.entrySet().iterator(); it1.hasNext(); ) 
                {
                      Map.Entry entry1 = (Map.Entry) it1.next();
                      String key = entry1.getKey().toString();
                      String value = entry1.getValue().toString();
         		%>
 				<option value='<%=key%>'><%=value%></option>
           		<%  } %> 
              </html:select> 

                 </td>
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
                   <input name="next" type="button" class="button" value="Next" onClick="callDealLever()"> 
                   <input name="Cancel" type="button" class="button" id="Cancel" value="Cancel" onClick="callEsiid()"> 
                   
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