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
<%@ page import="com.savant.pricing.dao.TLFDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.TLFVO"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="com.savant.pricing.common.NumberUtil"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pconfiguration.TLFForm"/>
<jsp:setProperty name="frm" property="*"/>
<%
	TLFDAO objTLFDAO = new TLFDAO();
	List lsTLF = null; 
	try
	{
		lsTLF = objTLFDAO.getAllTlf();
		pageContext.setAttribute("lsTLF",lsTLF);
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
<script src="<%=request.getContextPath()%>/scripts/prototype.js"></script>
</head>
<script>

String.prototype.trim=function()
{
    return this.replace(/^\s*|\s*$/g,'');
}

function callSubmit()
{
	var temp=document.forms[0];
	var obj = document.getElementById('tblSeasonValues');
	var editstr ="";
	
	for(var i=1;i<obj.children[0].children.length;i++)
	   {
		editstr += obj.children[0].children[i].children[1].children[0].name.split("+")[1]+",";
		
		if(obj.children[0].children[i].children[1].children[0].value.trim()=="" || obj.children[0].children[i].children[1].children[0].value.trim()==".")
			editstr += "0.0"+",";
		else
			editstr += obj.children[0].children[i].children[1].children[0].value+",";
		
		if(obj.children[0].children[i].children[2].children[0].value.trim()==""||obj.children[0].children[i].children[2].children[0].value.trim()==".")
			editstr += "0.0"+",";
		else
			editstr += obj.children[0].children[i].children[2].children[0].value+",";
		editstr += "substr";
	   }	 	
	   temp.seasonedit.value = editstr;
	   temp.formActions.value = 'edit';
	   temp.submit();
}

function calledit()
{
	document.getElementById('tblLossFactorEdit').style.display = 'block';
	document.getElementById('tblLossFactor').style.display = 'none';
	document.getElementById('messageIdx').style.display = 'none';
	document.getElementById('errorId').style.display = 'none';
}

function callCancel()
{
	document.forms[0].action = '<%=request.getContextPath()%>/jsp/pconfiguration/Losses/pcTLF.jsp';
	document.forms[0].submit();
}

function checkEnter(e,str,strid)
   { 
      var characterCode;
      e = event
      characterCode = e.keyCode;
      var dot = "";
      if(str=='onpeak')
      	 dot = document.getElementById(strid).value.split(".");
      else
         dot = document.getElementById(strid).value.split(".");
    
     if( (characterCode>=48) && (characterCode<=57) || characterCode == 46 && (dot.length == 1 || dot.length == 0))
     {
		  return true;
	  }
	  else
	  {
	  return false;
	  }
   }
   
</script>
<body> 
<html:form action="TLFList">
<html:hidden property="formActions"/>
<input type='hidden' name='seasonedit'/>
<script language="JavaScript1.2"></script> 
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
   <td valign="top"> <jsp:include page="/jsp/menu.jsp"/>
   		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
        	<tr>
          		<td class="page_title">Transmission Loss Factor (TLF)</td>          
          	</tr>
          	<tr id='messageIdx'>
          		<td class="message"><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid"/></html:messages></td>          
          	</tr>
          	<tr id='errorId'>
          		<td class="error"><html:errors/></td>          
          	</tr>
         </table>        
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr id="tblLossFactor" style="display:block">
          <td>
            <fieldset style='align:center; margin: 0px 750px 0px 10px'>
            <legend>Transmission Loss Factor</legend>
            <br>
            <table border="0" cellspacing="0"  cellpadding="0" id="tblNoTxt">
              <tr>
                <td class="tblheader" align='center'>Seasons</td>
                <td class="tblheader" align='center'>On-Peak</td>
                <td class="tblheader" align='center'>Off-Peak</td>
              </tr>
           <logic:iterate id="TLF" name="lsTLF">
	          <tr>
	            <td class="tbldata"><bean:write name="TLF" property="season.seasonName"/>&nbsp;</td>
	            <td class="tbldata" align='right'><bean:write name="TLF" property="onPeakLoss" format="0.0000" />&nbsp;</td>
	            <td class="tbldata" align='right'><bean:write name="TLF" property="offPeakLoss" format="0.0000" />&nbsp;</td> 
	          </tr>
           </logic:iterate> 
         </table>        
            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="fieldata"><input name="edit" id="btnEdit" type="button" class="button" value="Edit" onclick='calledit()'></td>
              </tr>
            </table>
          </fieldset></td>
        </tr>
        
         <tr id="tblLossFactorEdit" style="display:none">
          <td>
            <fieldset style='align:center; margin: 0px 750px 0px 10px'>
            <legend>Transmission Loss Factor</legend>
            <br>
            <table border="0" cellspacing="0" cellpadding="0" id="tblSeasonValues">
              <tr>
                <td class="tblheader" align='center'>Seasons</td>
                <td class="tblheader" align='center'>On-Peak</td>
                <td class="tblheader" align='center'>Off-Peak</td>
              </tr>
           <logic:iterate id="TLF"  name="lsTLF" indexId="count" indexId="indx">
	          <tr>
	          <%
	          		TLFVO objTLFVO = new TLFVO();
	          		int val = indx.intValue();
	          		objTLFVO = (TLFVO)lsTLF.get(val);
	          		NumberFormat tnf = NumberUtil.tetraFraction();
	          		tnf.setGroupingUsed(false);
	          		String strOnPeakLoss = tnf.format(objTLFVO.getOnPeakLoss());
	          		String strOffPeakLoss = tnf.format(objTLFVO.getOffPeakLoss());
	          	%>
	            <td class="tbldata"><bean:write name="TLF" property="season.seasonName"/>&nbsp;</td>
	            <td class="tbldata_mini" align='center'><input type='text' class='textbox' id='TLFOnPeakLoss<bean:write name="TLF" property="season.seasonId"/><bean:write name="TLF" property="onPeakLoss" />' onkeypress="return checkEnter(event,'onpeak','TLFOnPeakLoss<bean:write name="TLF" property="season.seasonId"/><bean:write name="TLF" property="onPeakLoss" />')" value='<%=strOnPeakLoss%>' name='TLF+<bean:write name="TLF" property="season.seasonId"/>' onPaste='return false' onDrop='return false' size='7' maxlength="20" /></td>
	           <td class="tbldata_mini" align='center'><input type='text' class='textbox' id='TLFOffPeakLoss<bean:write name="TLF" property="season.seasonId"/><bean:write name="TLF" property="offPeakLoss" />' onkeypress="return checkEnter(event,'offpeak','TLFOffPeakLoss<bean:write name="TLF" property="season.seasonId"/><bean:write name="TLF" property="offPeakLoss" />')" value='<%=strOffPeakLoss%>' name='TLF+<bean:write name="TLF" property="season.seasonId"/>' onDrop='return false' onPaste='return false' size='7' maxlength="20" /></td> 
	          </tr>
           </logic:iterate> 
         </table>        
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
              	<td class="fieldata" ><html:button  property="submitbutton" styleClass="button" value="Update" onclick="callSubmit();"/></td>
				<td class="fieldata" ><html:reset value="Reset" styleClass="button"></html:reset ></td>
				<td class="fieldata"><html:button property="Cancel" styleClass="button" value="Cancel" onclick="callCancel()"></html:button ></td>
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