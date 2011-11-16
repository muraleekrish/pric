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
<%@ page import="java.util.List"%>
<%@ page import="com.savant.pricing.dao.TaxRatesDAO"%>
<%@ page import="com.savant.pricing.valueobjects.TaxRatesVO"%>
<%@ page import="com.savant.pricing.common.NumberUtil"%>
<%@ page import="java.text.NumberFormat"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pconfiguration.TaxRatesForm" />
<jsp:setProperty name="frm" property="*" />

<%
	TaxRatesDAO objTaxRatesDAO = new TaxRatesDAO();
	TaxRatesVO objTaxRatesVO = new TaxRatesVO();
	List lstTaxRatesDAO = null; 
	
	NumberFormat dnf = NumberUtil.doubleFraction();
	
	try
	{
		lstTaxRatesDAO = objTaxRatesDAO.getAllTaxRates();
		pageContext.setAttribute("lstTaxRatesDAO",lstTaxRatesDAO);
		
	}
	catch(Exception e)
	{	
		e.printStackTrace();
	}
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
</head>
<script>

function calledit()
{
	document.getElementById('tblTaxEdit').style.display = 'block';
	document.getElementById('tblTax').style.display = 'none';
	document.getElementById('messageId').style.display = 'none';
	document.getElementById('errorId').style.display = 'none';
}

function callSubmit()
{
	var temp=document.forms[0];
	var obj = document.getElementById('idTaxUpdate');
	var editstr ="";
	
	for(var i=0;i<obj.children[0].children.length;i++)
    {
		editstr += obj.children[0].children[i].children[2].children[0].name.split("+")[1]+",";
		editstr += obj.children[0].children[i].children[2].children[0].name.split("+")[2]+",";
		if(obj.children[0].children[i].children[2].children[0].value == "" || obj.children[0].children[i].children[2].children[0].value == ".")
			editstr +=  "0.00"+",";
		else
			editstr +=  obj.children[0].children[i].children[2].children[0].value+",";
		editstr += "taxstr";
    }	 	
	temp.taxEdit.value = editstr;
	temp.formActions.value = 'edit';
	temp.submit();
}

function checkFloatVal(e, strId)
{ 
      var characterCode;
      e = event
      characterCode = e.keyCode;
      var dot = "";
      dot = document.getElementById(strId).value.split(".");
     if( (characterCode>=48) && (characterCode<=57) || characterCode == 46 && (dot.length == 1 || dot.length == 0))
     {
		return true;
	 }
	 else
	 {
	  	return false;
	 }
}

function callCancel()
{
	document.forms[0].action = '<%=request.getContextPath()%>/jsp/pconfiguration/charges/pcTaxes.jsp';
	document.forms[0].submit();
}
   
</script>
<body> 
<html:form action="TaxRatesList">
<html:hidden property="formActions"/>
<input type='hidden' name='taxEdit'/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
   <td valign="top"> <jsp:include page="/jsp/menu.jsp"/>
   		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
        	<tr>
          		<td class="page_title">Taxes</td>          
          	</tr>
          	<tr id='messageId'>
          		<td class="message"><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages></td>          
          	</tr>
          	<tr id='errorId'>
          		<td class="error"><html:errors/></td>          
          	</tr>
         </table>        
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr id="tblTax" style="display:block">
          <td>
            <fieldset>
            <legend>Taxes</legend>
            <br>
            <table border="0" cellspacing="0" cellpadding="0">
		 		<%
		 			for(int i=0;i<lstTaxRatesDAO.size();i++)
					{
						objTaxRatesVO = (TaxRatesVO)lstTaxRatesDAO.get(i);
					%>	
					<tr>	
						<td width="120" class="fieldtitle"><%=objTaxRatesVO.getTaxType().getTaxType()%></td>	
						<td width="1" class="fieldtitle">:</td>
						<td width="100" class="fieldata"><%=dnf.format(objTaxRatesVO.getTaxRate())%>%</td>	
					</tr>	
					<%	
					}
		 		%>
            </table>
            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="fieldata">
                	<input name="edit" id="btnEdit" type="button" class = 'button' value="Edit" onclick='calledit()'>
                </td>
              </tr>
            </table>
          </fieldset></td>
        </tr>
        
        <tr id="tblTaxEdit" style="display:none">
          <td>
            <fieldset>
            <legend>Taxes</legend>
            <br>
            <table border="0" cellspacing="0" cellpadding="0" id='idTaxUpdate'>
		 		<%
		 			for(int i=0;i<lstTaxRatesDAO.size();i++)
					{
						objTaxRatesVO = (TaxRatesVO)lstTaxRatesDAO.get(i);
					%>	
					<tr>	
						<td width="120" class="fieldtitle"><%=objTaxRatesVO.getTaxType().getTaxType()%></td>	
						<td width="1" class="fieldtitle">:</td>
						<td width="100" class="fieldata"><input class='textbox' size='7' style='text-align=right' type='text' id='tax<%=objTaxRatesVO.getTaxType().getTaxTypeIdentifier()%><%=objTaxRatesVO.getTaxRateIdentifier()%><%=objTaxRatesVO.getTaxRate()%>' onkeypress="return checkFloatVal(event,'tax<%=objTaxRatesVO.getTaxType().getTaxTypeIdentifier()%><%=objTaxRatesVO.getTaxRateIdentifier()%><%=objTaxRatesVO.getTaxRate()%>')" value='<%=objTaxRatesVO.getTaxRate()%>' name='tax+<%=objTaxRatesVO.getTaxType().getTaxTypeIdentifier()%>+<%=objTaxRatesVO.getTaxRateIdentifier()%>' onPaste='return false' onDrop='return false' maxlength="7" />%</td>	
					</tr>	
					<%	
					}
		 		%>
            </table>
            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="fieldata">
                	<input name="submitbutton" type="button" class="button" value="Update" onclick='callSubmit()'>
                	<input name="resetbutton"  type="reset" class="button" value="Reset" >
                	<input name = 'Cancel' type = 'button' class = 'button' value= 'Cancel' onclick = 'callCancel()'>
                </td>
              </tr>
            </table>
          </fieldset></td>
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
<%}%>