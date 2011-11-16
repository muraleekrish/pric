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
<%@ page import="com.savant.pricing.dao.CustomerStatusDAO"%>
<%@ page import="com.savant.pricing.dao.CDRStatusDAO"%>
<%@ page import="com.savant.pricing.securityadmin.dao.UserDAO"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.ProspectiveCustomerAddForm" />
<jsp:setProperty name="frm" property="*" />

<%
	int browserHt = 0;
	CDRStatusDAO objCDRStatusDAO = new CDRStatusDAO();
	CustomerStatusDAO objCustomerStatusDAO = new CustomerStatusDAO();
	List objCustomerStatusVO = objCustomerStatusDAO.getAllCustomerStatus();
	List lstCDRstate = objCDRStatusDAO.getAllCDRStatus();
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy");
	UserDAO objUsersDAO = new UserDAO(); 
	List objUsersVO = null;
	ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
	LinkedHashMap hmLocationType =  objProspectiveCustomerDAO.getAllLocationTypesFromCMS();
	LinkedHashMap hmAddressType = objProspectiveCustomerDAO.getAllAddressTypesFromCMS();
	LinkedHashMap hmState = objProspectiveCustomerDAO.getAllStatesFromCMS();
	LinkedHashMap hmYear = new  LinkedHashMap();
	int year = Integer.parseInt(sdf.format(new java.util.Date()));
	for(int i=0;i<10;i++)
	{
		hmYear.put(new Integer(year+i),new Integer(year+i));
	}
	if(request.getParameter("User")!=null)
		frm.setPageUser(request.getParameter("User"));
	else
		frm.setPageUser((String)request.getSession().getAttribute("pageUser"));
	if(frm.getPageUser().equalsIgnoreCase("manager"))
	    objUsersVO = objUsersDAO.getChildPersons((String)session.getAttribute("userName"), true);
	else
	    objUsersVO = objUsersDAO.getAllUsers();
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
			browserHt = 140;
	   else
			browserHt = 120;
	pageContext.setAttribute("hmYear",hmYear);						
	pageContext.setAttribute("objUsersVOs",objUsersVO);
	request.getSession().setAttribute("pageUser",frm.getPageUser()+"");
	pageContext.setAttribute("objCustomerStatusVO",objCustomerStatusVO);
	pageContext.setAttribute("lstCDRstate",lstCDRstate);
	pageContext.setAttribute("hmLocationType",hmLocationType);
	pageContext.setAttribute("hmAddressType",hmAddressType);
	pageContext.setAttribute("hmState",hmState);
%><head>
<title>Customer Pricing</title>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script src='<%=request.getContextPath()%>/script/CalendarControl.js' language="javascript"></script>
</head>


<html:html>
<style type="text/css">
        input.prompt {border:1 solid transparent; background-color:#99ccff;width:50;font-family:arial;font-size:12;font-weight:bold color:black;} 
        td.titlebar { background-color:#2b765F; color:#0000D2; font-weight:bold;font-family:arial; font-size:12;} 
        table.promptbox {border:1 solid #ccccff; background-color:#FFFFE6; color:black;padding-left:2;padding-right:2;padding-bottom:2;font-family:arial; font-size:12;} 
        input.promptbox {border:1 solid #0000FF; background-color:white;width:100%;font-family:arial;font-size:12; color:black; }
 </style>
<script>

function addCustomer(submitAction)
{
	var temp = document.forms[0];
	temp.formActions.value = submitAction;
	temp.pageUser.value = '<%=frm.getPageUser()%>';
}

function callImport(id)
{
	var temp = document.forms[0];
	if(id=="")
	{
		alert("Enter Customer Id");
		temp.reset();
	}
	else
	{
	temp.formActions.value ="Import";
	temp.submit();
	}
}
function selectValue(comboObj,value)
{
   for(i=0;i<comboObj.options.length;i++)
   
    {	
       if(comboObj.options[i].value==value)
         {
			comboObj.options[i].selected=true;
		}
	}	
}
function callcancel()
{
	var temp = document.forms[0];
	temp.pageUser.value = '<%=frm.getPageUser()%>';
	temp.formActions.value = "cancel";
	temp.submit();
	
}

function checkNumber(e,id)
   { 
      var characterCode;
	  e = event
	  characterCode = e.keyCode ;
	 
	 if((characterCode>=48) && (characterCode<=57))
     {
		return true;
	 }
	 else if(id=="zip")
	 {
	  	if((characterCode==45) || (characterCode==32))
	  	   return true;
	  	else
	  	  return false;
	 }
	 else
	 {
	 	return false;
	 }
}
function checkFloatVal(e,val,prop)
{ 
      var characterCode;
      var tempVar;
      e = event
      characterCode = e.keyCode;
      var dot = "";
      if(val.charAt(0)=='.')
      {
      	tempVar = val.split(".")
      	val = '0.'+tempVar[1];
      	if(prop=='txtusage')
      		document.forms[0].txtusage.value = val;
      	else if(prop=='txtcommission')
      		document.forms[0].txtcommission.value = val;
      	else if(prop=='txtcommissionIncome')
      		document.forms[0].txtcommissionIncome.value = val;
      	else
      		document.forms[0].txtcompetitorPrice.value = val;
      }
      dot = val.split(".");
     if((characterCode>=48) && (characterCode<=57) || characterCode == 46 && (dot.length == 1 || dot.length == 0))
     {
		return true;
	 }
	 else
	 {
	  	return false;
	 }
}

function prompt2() 
	{ 	
		document.getElementById("comboEsiid").style.display='block';
		document.getElementById("promptbox").focus() 
	} 
function myfunction(value) { 
		if(value.length<=0)
			{
			return true;			
			}
		else
		   {
			document.forms[0].txtCustomerCMSId.value = value;
			callImport(value);
			return true;
		   } 
	}

function loaddefault()
	{<%if(frm.getCmbSalesRep()==null||frm.getCmbSalesRep().equals("null")){%>
	if(document.forms[0].cmbSalesRep!=null)
	{	
		var userName = '<%=(String)session.getAttribute("userName")%>';		
		document.forms[0].cmbSalesRep.value = userName;
	}
	<%}%>
	if('<%=frm.getPageUser().equalsIgnoreCase("Analyst")%>'=="false")
	{
		var temp = document.forms[0];
		temp.cmbCMSAddressTypeId.value = 5;
		temp.cmbCMSLocationId.value = 2;
		temp.cmbCustomerState.value = "Texas";
	}
}

function fnKeycodeValidate(val,str)
{
	if(val == 46)
	{
		if(str.length > 0)
		{
			for(var i = 0;i < str.length;i++)
			{
				var ch = str.substring(i,i+1);
				if(ch=='.')
				{
					trueFalse = 0;
					return trueFalse;
				}
				else
				{
					trueFalse = 1;
				}
			}
		}
		else
		{
			trueFalse = 1;
		}
	}
	else
	{
		trueFalse = 1;
	}
	return trueFalse;
}

function getIt(m){
	
n=m.name;


//p1=document.forms[0].elements[n]

p1=m

ValidatePhone()

}

function ValidatePhone()
{
	p=p1.value
	vName = n;
	if(p.length==3)
		{
           pp=p;
           d4=p.indexOf('(')
           d5=p.indexOf(')')
           if(d4==-1)
			   {
			        pp="("+pp;
	           }
	       if(d5==-1)
			   {
			        pp=pp+")";
	           }
		if(vName == "txtPhone1")
			{
			   document.forms[0].txtPhone1.value="";
			   document.forms[0].txtPhone1.value=pp;
			}
			else if(vName == "txtPhone2")
			{
				document.forms[0].txtPhone2.value="";
			   document.forms[0].txtPhone2.value=pp;
			}
			else if(vName == "txtFax1")
			{
				document.forms[0].txtFax1.value="";
			   document.forms[0].txtFax1.value=pp;
			}
			else if(vName == "txtFax2")
			{
				document.forms[0].txtFax2.value="";
			   document.forms[0].txtFax2.value=pp;
			}
		}

if(p.length>3)
	{
       d1=p.indexOf('(')
       d2=p.indexOf(')')
           if (d2==-1)
		   {
            l30=p.length;
            p30=p.substring(0,4);
            p30=p30+")"
            p31=p.substring(4,l30);
            pp=p30+p31;
	           if(vName == "txtPhone1")
				{
				   document.forms[0].txtPhone1.value="";
				   document.forms[0].txtPhone1.value=pp;
				}
				else if(vName == "txtPhone2")
				{
					document.forms[0].txtPhone2.value="";
				   document.forms[0].txtPhone2.value=pp;
				}
				else if(vName == "txtFax1")
				{
					document.forms[0].txtFax1.value="";
				   document.forms[0].txtFax1.value=pp;
				}
				else if(vName == "txtFax2")
				{
					document.forms[0].txtFax2.value="";
				   document.forms[0].txtFax2.value=pp;
				}
           }
     }

if(p.length>5)
	{
       p11=p.substring(d1+1,d2);
	   if(p11.length>3)
		   {
			   p12=p11;
			   l12=p12.length;
			   l15=p.length
			   p13=p11.substring(0,3);
			   p14=p11.substring(3,l12);
			   p15=p.substring(d2+1,l15);
			   document.forms[0].vName.value="";
			   pp="("+p13+")"+p14+p15;
			   document.forms[0].vName.value=pp;
           }
           l16=p.length;
           p16=p.substring(d2+1,l16);
           l17=p16.length;
           if(l17>3&&p16.indexOf('-')==-1)
			   {
					p17=p.substring(d2+1,d2+4);
					p18=p.substring(d2+4,l16);
   				    p19=p.substring(0,d2+1);
			        pp=p19+p17+"-"+p18;
				    if(vName == "txtPhone1")
					{
					   document.forms[0].txtPhone1.value="";
					   document.forms[0].txtPhone1.value=pp;
					}
					else if(vName == "txtPhone2")
					{
						document.forms[0].txtPhone2.value="";
					   document.forms[0].txtPhone2.value=pp;
					}
					else if(vName == "txtFax1")
					{
						document.forms[0].txtFax1.value="";
					   document.forms[0].txtFax1.value=pp;
					}
					else if(vName == "txtFax2")
					{
						document.forms[0].txtFax2.value="";
					   document.forms[0].txtFax2.value=pp;
					}
              }
		}

setTimeout(ValidatePhone,0);

}
</script>
<div id ="comboEsiid" style="overflow:auto; position:absolute; top:90px; left:360px; display:none;">
    <table cellspacing="0" cellpadding="0" border="0" width="150" class="promptbox">
	   <tr>
	     <td><b>Enter Customer ID</b></td>
	   </tr>
	   <tr>
	     <td><input type="text" value="" id="promptbox" class="promptbox" onpaste="return false;" ondrop="return false;" onblur="this.focus()" onkeypress='return checkNumber(event,"");'></td>
	   </tr>
	   <tr>
	     <td align="center"><br>
		    <input type="button" class="prompt" id='cmsIDOk' value="Ok" onMouseOver="this.style.border='1 outset #dddddd'" onMouseOut="this.style.border='1 solid '" onClick="myfunction(document.getElementById('promptbox').value);">
			<input type="button" class="prompt" id='cmsIDcancel' value="Cancel" onMouseOver="this.style.border='1 outset '" onMouseOut="this.style.border='1 solid '" onClick="myfunction(' ');">
		</td>
	  </tr>
	 </table>
</div>
<body onload='loaddefault();'> 
<html:form action="prospectiveCustomersRepAdd">  
<html:hidden property="formActions" />
<html:hidden property="pageUser"/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> 
    <td  valign="top">
<jsp:include page="../menu.jsp"/>
<div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')" id="divList"> 
  		<validator:errorsExist>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
				<tr> 
		         <td class="error">
					<html:errors/>
		         </td>
		        </tr>
		     </table>
		</validator:errorsExist>
  		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	        <tr> 
	          <td width="250" class="page_title"> Prospective Customers</td> 
			  <td class="page_title">&nbsp;</td>
	        </tr> 
      	</table>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	       <tr>
	          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="3"></td>
	       </tr>
	    </table>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="112" class="subtab_on" id="set1">Customer&nbsp;Details</td>
              <td width="76" class="subtab_off_hide">Preferences</td>
			<td width="76" class="subtab_off_hide">Usage&nbsp;Files</td>
			<td width="535">&nbsp;</td>
          </tr>
        </table>
        
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="subtabbase"><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="2"></td>
          </tr>
        </table>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
	         
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr id="tblPremium">
            <td>
            <fieldset id="custInfo" style='align:center; margin: -10px 10px 0px 10px'>
            <legend>Customer Info</legend> 
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
                  <td width="150" class="fieldtitle">Name*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata" >
                  <html:text property="txtCustomerName" styleClass="textbox" maxlength="150" style="background-color:#FDFDB3" />
                  </td>
				  
				  <td width="119" class="fieldtitle">Address Type*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata">
					<html:select property="cmbCMSAddressTypeId" style="background-color:#FDFDB3">
						<html:option value="0" >Select one</html:option>
						<html:options collection="hmAddressType" property="key" labelProperty="value" />
				    </html:select>
				  </td>

				  <td width="119" class="fieldtitle">Sales Rep</td>
                  <td width="1" class="fieldtitle">:</td>
				<%if(!frm.getPageUser().equalsIgnoreCase("rep")){%>
					  <td class="fieldata">
						  <html:select property="cmbSalesRep" >
							<html:options collection="objUsersVOs" property="userId" labelProperty="firstName" />
						  </html:select>
					  </td>
				<%}else
				{
				%>
					<td class="fieldata">
						<%=String.valueOf(session.getAttribute("firstName"))%>
					</td>
				<%}%>	
                </tr>
				<tr>
                  <td width="150" class="fieldtitle">DBA</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata">
					<html:text property="txtCustomerDBA" styleClass="textbox" maxlength="50" />
                  </td>
				  
				  <td width="119" class="fieldtitle">Address*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><html:text property="txtCustomerAddress" styleClass="textbox" maxlength="200" style="background-color:#FDFDB3"/></td>

				 <!--  <td width="119" class="fieldtitle">Aggregator</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata">
					  <html:select property="aggregator" disabled='true'>
						  <html:option value="0">Select one</html:option>
						</html:select> -->
				 <td width="119" class="fieldtitle">Status*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td class="fieldata">
					 <html:select property="cmbCustomerStatus" style="background-color:#FDFDB3">
						  <html:option value="0" >Select one</html:option>
						  <html:options collection="objCustomerStatusVO" property="customerStatusId" labelProperty="customerStatus" />
					  </html:select>
                  </td>
				  </td>
                </tr>
				<tr>
                 <!--  <td width="150" class="fieldtitle">Status*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata">
					 <html:select property="cmbCustomerStatus" style="background-color:#FDFDB3">
						  <html:option value="0" >Select one</html:option>
						  <html:options collection="objCustomerStatusVO" property="customerStatusId" labelProperty="customerStatus" />
					  </html:select>
                  </td> -->
				  
				  <td width="150" class="fieldtitle">State*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><html:select property="cmbCustomerState" style="background-color:#FDFDB3">
				  <html:option value="0" >Select one</html:option>
				  <html:options collection="hmState" property="value" labelProperty="value" />
				</html:select></td>

				 <td width="119" class="fieldtitle">City*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata">
					  <html:text property="txtCustomerCity" styleClass="textbox" maxlength="50" style="background-color:#FDFDB3" />
				  </td>

				   <td width="119" class="fieldtitle">Location Type*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td  class="fieldata">
					<html:select property="cmbCMSLocationId" style="background-color:#FDFDB3">
					  <html:option value="0" >Select one</html:option>
					  <html:options collection="hmLocationType" property="key" labelProperty="value" />
					 </html:select>
                  </td>

				 <!--  <td width="119" class="fieldtitle">Broker</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata">
					  <html:select property="broker" disabled='true'>
						  <html:option value="0" >Select one</html:option>
						</html:select>
				  </td> -->

                </tr>
				<!-- <tr>
                  <td width="150" class="fieldtitle">Type</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata">
					<html:select property="typeNewRenewal" disabled='true'>
						<html:option value="1">New</html:option>
						<html:option value="2">Renewal</html:option>
					</html:select>
                  </td> 
				

				  <td width="119" class="fieldtitle">Consultant</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata">
					 <html:select property="consultant" disabled='true'>
						<html:option value="0">Select one</html:option>
					 </html:select>
				  </td> 
                </tr>-->
				<tr>
               
				  
				  <td width="150" class="fieldtitle">Zip Code*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><html:text property="txtzipcode" styleClass="textbox" onkeypress="return checkNumber(event,'zip')" maxlength="12" style="background-color:#FDFDB3"/></td>

				  <td width="119" class="fieldtitle">Approval Status*</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata">Submitted</td>
				   <%if(frm.getPageUser().equalsIgnoreCase("Analyst")){%>
				  <td width="119" class="fieldtitle">Is MM Customer</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td  class="fieldata" >
                  <html:select property="isMMCust">
						<html:option value="0" >No</html:option>
						<html:option value="1">Yes</html:option>
				    </html:select>
                  </td>
				<%}else
				{
				%>
					<td colspan='6' class="fieldata">&nbsp;</td>                   	
				<%}%>    
				</td>    
                </tr>
			</table>
			</fieldset>
			<br>
			

			<fieldset id="pointofCont" style='align:center; margin: -9px 10px 0px 10px'>
            <legend>Point Of Contact</legend> 
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">			  		
				<tr>
				  <td width="150" class="fieldtitle">First name*</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata">
					<html:text property="pntofCntFirstname" styleClass="textbox" maxlength="50" style="background-color:#FDFDB3"/>
				  </td>
				  
				  <td width="119" class="fieldtitle">Phone</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata">
					<html:text property="txtPhone" styleClass="textbox" maxlength="20" onkeypress = "if((fnKeycodeValidate(event.keyCode,this.value)=='1')&&((event.keyCode>46 && event.keyCode<58)||(event.keyCode==8)||(event.keyCode==190) ||(event.keyCode==46) ||(event.keyCode==37) ||(event.keyCode==39) || (event.keyCode==9)|| (event.keyCode==45))) event.returnValue = true; else event.returnValue = false"  onclick="javascript:getIt(this)" onfocus="javascript:getIt(this)"/>
				  </td>
				  <td width="119" class="fieldtitle">Fax</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata"><html:text property="txtFax" styleClass="textbox" maxlength="20" onkeypress = "if((fnKeycodeValidate(event.keyCode,this.value)=='1')&&((event.keyCode>46 && event.keyCode<58)||(event.keyCode==8)||(event.keyCode==190) ||(event.keyCode==46) ||(event.keyCode==37) ||(event.keyCode==39) || (event.keyCode==9)|| (event.keyCode==45))) event.returnValue = true; else event.returnValue = false" onclick="javascript:getIt(this)" onfocus="javascript:getIt(this)"/></td>
				</tr>
				<tr>
				  <td width="150" class="fieldtitle">Last Name*</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata">
					<html:text property="pntofCntLastname" styleClass="textbox" maxlength="50" style="background-color:#FDFDB3"/>
				  </td>
				  
				  <td width="119" class="fieldtitle">Mobile</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata">
					<html:text property="txtMobile" styleClass="textbox" maxlength="20" onkeypress = "if((fnKeycodeValidate(event.keyCode,this.value)=='1')&&((event.keyCode>46 && event.keyCode<58)||(event.keyCode==8)||(event.keyCode==190) ||(event.keyCode==46) ||(event.keyCode==37) ||(event.keyCode==39) || (event.keyCode==9)|| (event.keyCode==45))) event.returnValue = true; else event.returnValue = false"/>
				  </td>
				  <td width="119" class="fieldtitle">Email</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata"><html:text property="txtEmail" styleClass="textbox" maxlength="50" /></td>
				</tr>
				<tr>
                  <td width="150" class="fieldtitle">Title</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td  class="fieldata" colspan='7'>
					<html:text property="txtCustomerTitle" styleClass="textbox" maxlength="50" />
				  </td>         	
			   </tr>
			</table>
			</fieldset>
			

			<fieldset id="businessInfo" style='align:center; margin: -5px 10px 0px 10px'>
            <legend>Business Info</legend> 
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td width="150" class="fieldtitle">Business Type</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata">
					<html:text property="txtBiztype" styleClass="textbox" maxlength="50" />
				  </td>
				  
				  <td width="119" class="fieldtitle">Competitor</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata">
					<html:text property="txtcompetitor" styleClass="textbox" maxlength="50"/>
				  </td>
				  <td width="119" class="fieldtitle">Out Of Cycle Switch?</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata">
					  <html:select property="cmbcycle">
						<html:option value="0" >No</html:option>
						<html:option value="1">Yes</html:option>
					  </html:select>
				  </td>
				</tr>
				<tr>
				  <td width="150" class="fieldtitle">Current Provider</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata">
					<html:text property="txtCurentProvider" styleClass="textbox" maxlength="50" />
				  </td>
				  
				  <td width="119" class="fieldtitle">Competitor Price</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata">
					<html:text property="txtcompetitorPrice" styleClass="textbox" onkeypress="return checkFloatVal(event,this.value,'txtcompetitorPrice')" />
				  </td>
				  <td width="119" class="fieldtitle">Tax Exempt</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata">
					<html:select property="cmbtaxExempt">
						<html:option value="0" >No</html:option>
						<html:option value="1">Yes</html:option>
					</html:select>
				  </td>
				</tr>
				<tr>
                  <td width="150" class="fieldtitle">Number of ESIIDs</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td  class="fieldata" colspan='7'>
					<html:text property="noofESIIDs" styleClass="textbox" maxlength="50" readonly='true'/>
				  </td>         	
				</tr>
			</table>
			</fieldset>

			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
                  <td width="627">
			<table width="100%"  border="0" cellspacing="0" cellpadding="0" style='margin: 5px 0px 0px 16px'>
				<tr>
                  <td width="150" class="fieldtitle" valign='top' style='color:0046D5'>Comments</td>
                  <td width="1" class="fieldtitle" valign='top'>:</td>
                  <td class="fieldata" colspan="7"><html:textarea property="txtcomments" styleClass="textbox" rows="3" cols="70" /></td>				  
				</tr>
			</table>
			</td>
				 <td valign='bottom' style='padding: 0px 0px 0px 0px'> <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="fieldata" align="right">
                  	<html:submit  property="submit1" styleClass="button" value="Submit" onclick="addCustomer('preference')"></html:submit>
					<html:reset value="Reset" styleClass="button"></html:reset >
                    <html:button property="Cancel" styleClass="button" value="Cancel" onclick="callcancel()"></html:button >
                   </td>
                </tr>
              </table></td>
						<td width="15">&nbsp;</td>
				</tr>
			</table>
           
            </td>
          </tr>
        </table>
        </div>
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