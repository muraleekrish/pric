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
<%@ page import="com.savant.pricing.dao.PremiumDAO"%>
<%@ page import="com.savant.pricing.valueobjects.PremiumVO"%>
<%@ page import="com.savant.pricing.dao.ShappingPremiumDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ShappingPremiumVO"%>
<%@ page import="java.util.HashMap"%>

<jsp:useBean id="frm" class="com.savant.pricing.presentation.pconfiguration.PremiumForm" />
<jsp:setProperty name="frm" property="*" />
<%
	PremiumDAO objPremiumDAO = new PremiumDAO();
	HashMap hmPremiumVo = (HashMap)objPremiumDAO.getPremiumVO();
	ShappingPremiumDAO objShappingPremiumDAO = new ShappingPremiumDAO();
	HashMap hmShappingPremiumVo = objShappingPremiumDAO.getShappingPremiumVO();
	
	boolean shappedPrice = ((ShappingPremiumVO)hmShappingPremiumVo.get("Use Fulcrum Shaped Price Where Available")).isApply();
	boolean zonalPrice = ((ShappingPremiumVO)hmShappingPremiumVo.get("Apply Price Scalars to Zonal Price Curve")).isApply();
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script>

function makePreEdit(){
	var prepercent = document.getElementById('txtPrePercent');
	var prevalue = document.getElementById('txtPreValue');
	var VIB = document.getElementById('txtVIB');
	var Vola = document.getElementById('txtVola');
	var Eff = document.getElementById('txtEff');
	var btn = document.getElementById('btnPreEdit'); 
	
	if(prepercent.className == 'tboxplain'){
		prepercent.className = 'textbox';
		prepercent.contentEditable = true;
		prevalue.className = 'textbox';
		prevalue.contentEditable = true;
		VIB.className = 'textbox';
		VIB.contentEditable = false;
		Vola.className = 'textbox';
		Vola.contentEditable = true;
		Eff.className = 'textbox';
		Eff.contentEditable = true;
		btn.style.display='none';
	}
}

function calledit()
{
	document.getElementById('tblPremEdit').style.display = 'block';
	document.getElementById('tblPremDisp').style.display = 'none';
	document.getElementById('messageId').style.display = 'none';
	document.getElementById('errorId').style.display = 'none';
	
	var temp=document.forms[0];
	temp.shapedPriceEdit.value = '<%=shappedPrice%>';
	temp.zonalPriceEdit.value = '<%=zonalPrice%>';
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

function callSubmit()
{
	var temp=document.forms[0];
	var obj1 = document.getElementById('tblPremium');
	var obj2 = document.getElementById('tblTrans');
	var obj3 = document.getElementById('tblCombo');
	var editstr ="";
	
	for(var i=1;i<obj1.children[0].children.length;i++)
    {
		editstr += obj1.children[0].children[i].children[2].children[0].name.split("+")[0]+",";
		editstr += obj1.children[0].children[i].children[2].children[0].name.split("+")[1]+",";
		editstr +=  obj1.children[0].children[i].children[2].children[0].value+",";
		editstr += "premiumstr";
    }
    
    for(var j=0;j<obj2.children[0].children.length;j++)
    {
		editstr += obj2.children[0].children[j].children[2].children[0].name.split("+")[0]+",";
		editstr += obj2.children[0].children[j].children[2].children[0].name.split("+")[1]+",";
		editstr +=  obj2.children[0].children[j].children[2].children[0].value+",";
		editstr += "premiumstr";
    }
    
    var shapestr = '';
    shapestr += <%=((ShappingPremiumVO)hmShappingPremiumVo.get("Use Fulcrum Shaped Price Where Available")).getShappingPremiumId()%>+','+'Use Fulcrum Shaped Price Where Available '+","+temp.shapedPriceEdit.value+"combo";
	shapestr += <%=((ShappingPremiumVO)hmShappingPremiumVo.get("Apply Price Scalars to Zonal Price Curve")).getShappingPremiumId()%>+','+'Apply Price Scalars to Zonal Price Curve'+","+temp.zonalPriceEdit.value+"combo";
	
	temp.premiumUpdate.value = editstr;
	temp.shapeUpdate.value = shapestr;
	temp.formActions.value = 'edit';
	temp.submit(); 
}

function callCancel()
{
	document.forms[0].action = '<%=request.getContextPath()%>/jsp/pconfiguration/charges/pcPremium.jsp';
	document.forms[0].submit();
}

function callAdd(val,volp,purp)
{
	var volPrem = 0;
	var purInPrem = 0;
	var tot = 0;
	if(volp.value!='')
		volPrem = volp.value;
	if(purp.value!='')
		purInPrem = purp.value;

	tot = parseFloat(volPrem)+parseFloat(purInPrem);
	val.value = Math.round(tot*100)/100;
}
</script>
</head>
<body> 
<html:form action="Premium" method="post">
<html:hidden property="formActions"/>
<input type='hidden' name='premiumUpdate'/>
<input type='hidden' name='shapeUpdate'/>
  <table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
    <tr> 
      <td  valign="top"> <jsp:include page="/jsp/menu.jsp"/>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="250" class="page_title">Premium</td> 
          </tr> 
          <tr id='messageId'>
          		<td class="message"><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages></td>          
          	</tr>
          	<tr id='errorId'>
          		<td class="error"><html:errors/></td>          
          	</tr>
        </table> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblPremDisp"> 
          <tr> 
            <td><table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
                <tr> 
                  <td colspan="2"><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
                </tr> 
                <tr> 
                  <td width="250" class="fieldtitle">VIB Premium %</td> 
                  <td width="1" class="fieldtitle">:</td> 
                  <td class="fieldata"><%=((PremiumVO)hmPremiumVo.get("VIB Premium")).getValue()%></td> 
                </tr> 
                <tr> 
                  <td width="250" class="fieldtitle">Volatility Premium %</td> 
                  <td width="1" class="fieldtitle">:</td> 
                  <td class="fieldata"><%=((PremiumVO)hmPremiumVo.get("Volatility Premium %")).getValue()%></td> 
                </tr> 
                <tr> 
                  <td width="250" class="fieldtitle">Purchasing InEfficiency Premium %</td> 
                  <td width="1" class="fieldtitle">:</td> 
                  <td class="fieldata"><%=((PremiumVO)hmPremiumVo.get("Purchasing Inefficiency Premium %")).getValue()%></td> 
                </tr> 
              </table> 
              <fieldset> 
              <legend>Transmission & Distribution Premiums</legend> 
              <br> 
              <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
                <tr> 
                  <td width="245" class="fieldtitle">Premium % </td> 
                  <td width="1" class="fieldtitle">:</td> 
                  <td class="fieldata"><%=((PremiumVO)hmPremiumVo.get("Premium %")).getValue()%></td> 
                </tr> 
                <tr> 
                  <td width="245" class="fieldtitle">Premiums $/MWh </td> 
                  <td width="1" class="fieldtitle">:</td> 
                  <td class="fieldata"><%=((PremiumVO)hmPremiumVo.get("Premium $/MWH")).getValue()%></td> 
                </tr> 
              </table> 
              </fieldset> 
              <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
                <tr>
	                <td width="251" class="fieldtitle">Use Shaped Price Where Available </td> 
	                <td width="1" class="fieldtitle">:</td> 
	                  <%
	                  		String fulShapPrice = "";
	                  		if(shappedPrice)
	                  			fulShapPrice = "Yes";
	                  		else
	                  			fulShapPrice = "No";
	                  %>
	                 <td class="fieldata"><%=fulShapPrice%></td> 
                </tr>
                <tr> 
                  <td width="251" class="fieldtitle">Apply Price Scalars to Zonal Price Curve </td> 
                  <td width="1" class="fieldtitle">:</td>
                  <%
                  		String scalZonalPrice = "";
                  		if(zonalPrice)
                  			scalZonalPrice = "Yes";
                  		else
                  			scalZonalPrice = "No";
                  %>
                  <td class="fieldata"><%=scalZonalPrice%></td> 
                </tr> 
              </table> 
              <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
                <tr> 
                  <td class="fieldata"><input name="btnPreEdit" type="button" class = 'button' id="btnPreEdit" value="Edit" onClick="calledit()"> 
                    (Maximum impact will be used : Applicable only to bundled pricing) </td> 
                </tr> 
              </table></td> 
          </tr> 
        </table> 
        
        <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblPremEdit" style="display:none"> 
          <tr> 
            <td><table border="0" cellspacing="0" cellpadding="0" id="tblPremium"> 
                <tr> 
                  <td colspan="3"><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
                </tr> 
                <tr> 
                  <td width="250" class="fieldtitle">VIB Premium %</td> 
                  <td width="1" class="fieldtitle">:</td> 
                  <td class="fieldata"><input name="<%=((PremiumVO)hmPremiumVo.get("VIB Premium")).getPremiumId()%>+VIB Premium" type="text" class="textbox" id="txt<%=((PremiumVO)hmPremiumVo.get("VIB Premium")).getPremiumId()%>" value='<%=((PremiumVO)hmPremiumVo.get("VIB Premium")).getValue()%>' size="8" readonly="true" maxlength="7" style="border:0px"></td> 
                </tr> 
                <tr> 
                  <td width="250" class="fieldtitle">Volatality Premium %</td> 
                  <td width="1" class="fieldtitle">:</td> 
                  <td class="fieldata"><input name="<%=((PremiumVO)hmPremiumVo.get("Volatility Premium %")).getPremiumId()%>+Volatility Premium %" type="text" class="textbox" id="txt<%=((PremiumVO)hmPremiumVo.get("Volatility Premium %")).getPremiumId()%>" value='<%=((PremiumVO)hmPremiumVo.get("Volatility Premium %")).getValue()%>' size="8" onkeypress="return checkFloatVal(event,'txt<%=((PremiumVO)hmPremiumVo.get("Volatility Premium %")).getPremiumId()%>')" maxlength="7" onblur="callAdd(txt<%=((PremiumVO)hmPremiumVo.get("VIB Premium")).getPremiumId()%>,txt<%=((PremiumVO)hmPremiumVo.get("Volatility Premium %")).getPremiumId()%>,txt<%=((PremiumVO)hmPremiumVo.get("Purchasing Inefficiency Premium %")).getPremiumId()%>);"></td> 
                </tr> 
                <tr> 
                  <td width="250" class="fieldtitle">Purchasing InEfficiency Premium %</td> 
                  <td width="1"class="fieldtitle">:</td> 
                  <td class="fieldata"><input name="<%=((PremiumVO)hmPremiumVo.get("Purchasing Inefficiency Premium %")).getPremiumId()%>+Purchasing Inefficiency Premium %" type="text" class="textbox" id="txt<%=((PremiumVO)hmPremiumVo.get("Purchasing Inefficiency Premium %")).getPremiumId()%>" value='<%=((PremiumVO)hmPremiumVo.get("Purchasing Inefficiency Premium %")).getValue()%>' size="8" onkeypress="return checkFloatVal(event,'txt<%=((PremiumVO)hmPremiumVo.get("Purchasing Inefficiency Premium %")).getPremiumId()%>')" maxlength="7" onblur="callAdd(txt<%=((PremiumVO)hmPremiumVo.get("VIB Premium")).getPremiumId()%>,txt<%=((PremiumVO)hmPremiumVo.get("Volatility Premium %")).getPremiumId()%>,txt<%=((PremiumVO)hmPremiumVo.get("Purchasing Inefficiency Premium %")).getPremiumId()%>);"></td> 
                </tr> 
              </table> 
              <fieldset> 
              <legend>Transmission & Distribution Premiums</legend> 
              <br> 
              <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblTrans"> 
                <tr> 
                  <td width="245" class="fieldtitle">Premium % </td> 
                  <td width="1" class="fieldtitle">:</td> 
                  <td class="fieldata"><input name="<%=((PremiumVO)hmPremiumVo.get("Premium %")).getPremiumId()%>+Premium %" type="text" class="textbox" id="txt<%=((PremiumVO)hmPremiumVo.get("Premium %")).getPremiumId()%>" value='<%=((PremiumVO)hmPremiumVo.get("Premium %")).getValue()%>' size="8" onkeypress="return checkFloatVal(event,'txt<%=((PremiumVO)hmPremiumVo.get("Premium %")).getPremiumId()%>')" maxlength="7"></td> 
                </tr> 
                <tr> 
                  <td width="245" class="fieldtitle">Premiums $/MWh </td> 
                  <td width="1" class="fieldtitle">:</td> 
                  <td class="fieldata"><input name="<%=((PremiumVO)hmPremiumVo.get("Premium $/MWH")).getPremiumId()%>+Premium $/MWH" type="text" class="textbox" id="txt<%=((PremiumVO)hmPremiumVo.get("Premium $/MWH")).getPremiumId()%>" value='<%=((PremiumVO)hmPremiumVo.get("Premium $/MWH")).getValue()%>' size="8" onkeypress="return checkFloatVal(event,'txt<%=((PremiumVO)hmPremiumVo.get("Premium $/MWH")).getPremiumId()%>')" maxlength="7"></td> 
                </tr> 
              </table> 
              </fieldset> 
              <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblCombo"> 
                <tr>
	                <td width="251" class="fieldtitle">Use Shaped Price Where Available </td> 
	                <td width="1" class="fieldtitle">:</td> 
	                <td class="fieldata">
	                	<select name="shapedPriceEdit"> 
	                      <option value='true'>Yes</option> 
	                      <option value='false'>No</option> 
	                    </select>
	                </td> 
                </tr>
				<tr> 
                  <td width="251" class="fieldtitle">Apply Price Scalars to Zonal Price Curve </td> 
                  <td width="1" class="fieldtitle">:</td> 
                  <td class="fieldata">
	                  <select name="zonalPriceEdit"> 
	                      <option value='true'>Yes</option> 
	                      <option value='false'>No</option> 
	                  </select>
	              </td> 
                </tr> 
              </table> 
              <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
                <tr> 
            <td><img src="../../images/spacer.gif" width="1" height="5"></td> 
          </tr>
                <tr> 
                  <td class="btnbg">
                  	<input name="Submit" type="submit" class="button" value="Update" onclick='callSubmit()'>
                  	<input name="Submit3" type="reset" class="button" value="Reset">
                  	<input name = 'Cancel' type = 'button' class = 'button' value= 'Cancel' onclick = 'callCancel()'>
                   </td> 
                </tr> 
              </table></td> 
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
