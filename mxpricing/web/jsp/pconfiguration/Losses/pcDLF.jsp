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
<%@ page import="java.text.NumberFormat"%>
<%@ page import="com.savant.pricing.common.NumberUtil"%>
<%@ page import="com.savant.pricing.dao.DLFCodeDAO"%>
<%@ page import="com.savant.pricing.dao.DLFDAO"%>
<%@ page import="com.savant.pricing.dao.TDSPDAO"%>
<%@ page import="com.savant.pricing.valueobjects.TDSPVO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.DLFVO"%>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.pconfiguration.DLFForm" /> 
<jsp:setProperty name="frm" property="*" />

<%
	NumberFormat tnf = NumberUtil.tetraFraction();
	DLFCodeDAO objDLFCodeDAO = new DLFCodeDAO();
	DLFDAO objDLFDAO = new DLFDAO();
	
	TDSPDAO objTDSPDAO = new TDSPDAO();
	TDSPVO objTDSPVO = new TDSPVO();
	List lsAllDLFCodes = null; 
	List lsAllTDSP = null;
	try
	{
		lsAllDLFCodes = objDLFCodeDAO.getAllDLFCodes();
		pageContext.setAttribute("lsAllDLFCodes",lsAllDLFCodes);
		
		lsAllTDSP = objTDSPDAO.getAllTDSP();
		pageContext.setAttribute("lsAllTDSP",lsAllTDSP);
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

function calledit()
{
	document.getElementById('DLFmodify').style.display = 'block';
	document.getElementById('DLFvalue').style.display = 'none';
}

function callCancel()
{
	document.forms[0].action = '<%=request.getContextPath()%>/jsp/pconfiguration/Losses/pcDLF.jsp';
	document.forms[0].submit();
}

function checkEnter(e,strid)
{ 
      var characterCode;
      e = event
      characterCode = e.keyCode;
      var dot = "";
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

function callSubmit()
{
	var temp=document.forms[0];
	var obj = document.getElementById('dlfUpdate');
	var editstr ="";
	for(var i=1;i<obj.children[0].children.length;i++)
	{
		for(var j=1;j<obj.children[0].children[i].children.length;j++)
		{
			editstr += obj.children[0].children[i].children[j].children[0].name.split("_")[1]+",";
			
			if(obj.children[0].children[i].children[j].children[0].value.trim()=="" || obj.children[0].children[i].children[j].children[0].value.trim()=="0")
				editstr += "0.0"+",";
			else
				editstr += obj.children[0].children[i].children[j].children[0].value+",";
			
			editstr += obj.children[0].children[i].children[j].children[0].value+",";
			editstr += "substr";
		}
	}
   temp.dlfEdit.value = editstr;
   temp.formActions.value = 'edit';
   temp.submit();
}
   
</script>
<body> 
<html:form action="DLFList">
<html:hidden property="formActions"/>
<input type='hidden' name='dlfEdit'/>

<script language="JavaScript1.2"></script> 
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
   <td valign="top"> <jsp:include page="/jsp/menu.jsp"/>
   		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
        	<tr>
          		<td class="page_title">Distribution Loss Factor (DLF)</td>          
          	</tr>
          	<tr id='messageIdx'>
          		<td class="message"><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid"/></html:messages></td>          
          	</tr>
          	<tr id='errorId'>
          		<td class="error"><html:errors/></td>          
          	</tr>
         </table>        
		<table width="100%"  border="0" cellpadding="0" cellspacing="0" id ='DLFvalue' style='display:block'>
        <tr id="tblLossFactor" style="display:block">
          <td>
            <fieldset style='align:center; margin: 0px 500px 0px 10px'>
            <legend>Distribution Loss Factor</legend>
            <br>
            <table border="0" cellspacing="0" cellpadding="3" id="tblNoTxt">
            <tr>
            	<td width="150" class="tblheader" align='center'>TDSP / DLFCode</td>
              <%
              	for(int i=0;i<lsAllDLFCodes.size();i++)
					{
						String strDflName = (String)lsAllDLFCodes.get(i);
              %>
                <td width="50" class="tblheader"  align='center'><%=strDflName%></td>
              <%
              		}
              %>
			</tr>
			 <%
              	for(int i=0;i<lsAllTDSP.size();i++)
					{
						objTDSPVO = (TDSPVO)lsAllTDSP.get(i);
              %>
					<tr>
            		<td width="150" class="tblheader"><%=objTDSPVO.getTdspName()%></td>
            			<%
              				for(int j=0;j<lsAllDLFCodes.size();j++)
							{
								String strDflName = (String)lsAllDLFCodes.get(j);
								int dlfCodeId = DLFCodeDAO.getDLFCodeId(objTDSPVO,strDflName);
								DLFVO objDLFVO = null;
								if(dlfCodeId != 0)
									objDLFVO = objDLFDAO.getDLFVO(dlfCodeId);
								String chkForZero = "0.0000";
								if(objDLFVO != null)
								chkForZero = tnf.format(objDLFVO.getDlf()).equals("0.0000")?"0.0000":tnf.format(objDLFVO.getDlf());
								
								if(dlfCodeId>0)
								{
							%>
								<td width="50" class="tbldata" align='right'><%=chkForZero%></td>
							<%
								}
								else
								{
							%>
								<td width="50" class="tbldata" Style="color:#D0D0D0;" align='right'><%=chkForZero%></td>
						<%
								}
							}
							%>
					</tr>
					<%
              		}
              %>
            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="fieldata"><input name="edit" id="btnEdit" type="button" class="button" value="Edit" onclick='calledit()'></td>
              </tr>
            </table>
          </fieldset></td>
        </tr>
      </table>	
      <table width="100%"  border="0" cellpadding="0" cellspacing="0" style='display:none' id ='DLFmodify'>
        <tr id="tblLossFactor" style="display:block">
          <td>
            <fieldset style='align:center; margin: 0px 500px 0px 10px'>
            <legend>Distribution Loss Factor</legend>
            <br>
            <table border="0" cellspacing="0" cellpadding="3" id="dlfUpdate">
            <tr>
            	<td width="150" class="tblheader" align='center'>TDSP / DLFCode</td>
              <%
              	for(int i=0;i<lsAllDLFCodes.size();i++)
					{
						String strDflName = (String)lsAllDLFCodes.get(i);
              %>
                <td width="50" class="tblheader" align='center' ><%=strDflName%></td>
              <%
              		}
              %>
			</tr>
			 <%
              	for(int i=0;i<lsAllTDSP.size();i++)
					{
						objTDSPVO = (TDSPVO)lsAllTDSP.get(i);
              %>
					<tr>
            		<td width="150" class="tblheader"><%=objTDSPVO.getTdspName()%></td>
            			<%
              				tnf.setGroupingUsed(false);
              				for(int j=0;j<lsAllDLFCodes.size();j++)
							{
								String strDlfName = (String)lsAllDLFCodes.get(j);
								int dlfCodeId = DLFCodeDAO.getDLFCodeId(objTDSPVO,strDlfName);
								DLFVO objDLFVO = null;
								if(dlfCodeId != 0)
									objDLFVO = objDLFDAO.getDLFVO(dlfCodeId);
								String chkForZero = "0.0000";
								if(objDLFVO != null)
									chkForZero = tnf.format(objDLFVO.getDlf()).equals("0.0000")?"0.0000":tnf.format(objDLFVO.getDlf());
								
								if(dlfCodeId>0)
								{
							%>
								<td width="50" class="tbldata_mini" align='right'><input type='text' class='textbox' id='DLF_Update<%=dlfCodeId%>' Style="text-align:right" onkeypress="return checkEnter(event,'DLF_<%=dlfCodeId%>')" value='<%=chkForZero%>' name='DLF_<%=dlfCodeId%>' onPaste='return false' onDrop='return false' size='5' maxlength="20" /></td>
							<%
								}
								else
								{
							%>
								<td width="50" class="tbldata_mini" align='right'><input type='text' class='textbox' Style="color:#D0D0D0;text-align:right" id='DLF_Update<%=dlfCodeId%>' onkeypress="return checkEnter(event,'DLF_<%=dlfCodeId%>')" value='<%=chkForZero%>' name='DLF_<%=dlfCodeId%>' onPaste='return false' onDrop='return false' size='5' maxlength="20" readonly /></td>
						<%
								}
							}
							%>
					</tr>
					<%
              		}
              %>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="fieldata"><html:button  property="edit" styleClass="button" value="Update" onclick="callSubmit();"/></td>
				<td class="fieldata"><html:reset value="Reset" styleClass="button"></html:reset ></td>
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
