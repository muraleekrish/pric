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
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="java.util.*"%>
<%@ page import="com.savant.pricing.dao.CDRStatusDAO"%>
<%@ page import="com.savant.pricing.securityadmin.dao.UserDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%@ page import="com.savant.pricing.valueobjects.CustomerCommentsVO"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.ProspectiveCustomerAddForm" />
<jsp:setProperty name="frm" property="*" />
<%
	CDRStatusDAO objCDRStatusDAO = new CDRStatusDAO();
	String custId = request.getParameter("prsCustId");
	if(custId==null)
	{
		custId = frm.getProspectiveCustId();
	}
	frm.setProspectiveCustId(custId);
	UserDAO objUsersDAO = new UserDAO(); 
	List objUsersVO = null;
	session.setAttribute("customerId",request.getParameter("prsCustId"));		
	if(request.getParameter("User")!=null)
		frm.setPageUser(request.getParameter("User"));
	else
		frm.setPageUser((String)request.getSession().getAttribute("pageUser"));
		
    if(frm.getPageUser().equalsIgnoreCase("manager"))
       objUsersVO = objUsersDAO.getChildPersons((String)session.getAttribute("userName"), true);
    else
       objUsersVO = objUsersDAO.getAllUsers();
	request.getSession().setAttribute("pageUser",frm.getPageUser()+"");
	java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("yyyy");
	CustomerStatusDAO objCustomerStatusDAO = new CustomerStatusDAO();
	List objCustomerStatusVO = objCustomerStatusDAO.getAllCustStatforProspect();
	ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
	LinkedHashMap hmLocationType =  objProspectiveCustomerDAO.getAllLocationTypesFromCMS();
	LinkedHashMap hmAddressType = objProspectiveCustomerDAO.getAllAddressTypesFromCMS();
	LinkedHashMap hmState = objProspectiveCustomerDAO.getAllStatesFromCMS();
	pageContext.setAttribute("objCustomerStatusVO",objCustomerStatusVO);
	List lstCDRstate = objCDRStatusDAO.getAllCDRStatus();
	pageContext.setAttribute("lstCDRstate",lstCDRstate);
	pageContext.setAttribute("objUsersVOs",objUsersVO);
	pageContext.setAttribute("hmLocationType",hmLocationType);
	pageContext.setAttribute("hmAddressType",hmAddressType);
	pageContext.setAttribute("hmState",hmState);
	
	LinkedHashMap hmYear = new  LinkedHashMap();
	int year = Integer.parseInt(sdf1.format(new java.util.Date()));
	for(int i=0;i<10;i++)
	{
		hmYear.put(new Integer(year+i),new Integer(year+i));
	}
	pageContext.setAttribute("hmYear",hmYear);						
	ProspectiveCustomerVO objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(custId));
	List lst = new ArrayList();
	
	Set objSet = null;
	objSet = objProspectiveCustomerVO.getCustomerComments();
	
  	int size = objSet.size();
  	CustomerCommentsVO objCustomerCommentsVO = null;
    while(size>0)
    {
	  	Iterator itr = objSet.iterator();
	  	while(itr.hasNext())
	  	{
	  		objCustomerCommentsVO = (CustomerCommentsVO)itr.next();
	  		if(objCustomerCommentsVO.getVersion() == size)
	  		{
	  			lst.add(objCustomerCommentsVO);
	  			break;
	  		}
	  	}
	  	size=size-1;
  	}
  	String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";
	String menuFtr[]= menupath.split("For");
	menupath = menupath.replaceFirst(menuFtr[1].trim(),(objProspectiveCustomerVO.getCustomerId()+"").equalsIgnoreCase("")||objProspectiveCustomerVO.getCustomerId()==null?"All Customers":objProspectiveCustomerVO.getCustomerId()+" - "+objProspectiveCustomerVO.getCustomerName());
	session.removeAttribute("home");
  	session.setAttribute("home",menupath);
	pageContext.setAttribute("setCustomerComments",lst);
	
	int browserHt = 0;
  	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 210;
	else
		browserHt = 190;
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script src='<%=request.getContextPath()%>/script/CalendarControl.js' language="javascript"></script>
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
</head>
<script>

String.prototype.trim=function()
{
    return this.replace(/^\s*|\s*$/g,'');
}
function checkEnter(e,id)
   { 
      var characterCode;
      e = event
      characterCode = e.keyCode ;
     if( (characterCode>=48) && (characterCode<=57)||(characterCode==45)||(characterCode<=57))
     {
		  return true;
	  }
	  else
	  {
	  return false;
	  }
   }
function addCustomer(submitAction)
{
	var temp = document.forms[0];
	var bool = echeck();
	if(bool)
	{
		temp.formActions.value =submitAction;
		temp.pageUser.value = '<%=frm.getPageUser()%>'
		temp.prospectiveCustId.value = '<%=custId%>';
		temp.createdDate.value = '<%=frm.getCreatedDate()%>'
		temp.createdBy.value = '<%=frm.getCreatedBy()%>';
		temp.submit();
	}
}
function callPreferences()
{
	var temp = document.forms[0];
	temp.action="<%=request.getContextPath()%>/frmPreferenceRepEdit.do?formActions=edit";
	temp.submit();
}
function callEsiid()
{
	var temp=document.forms[0];
	temp.action='<%=request.getContextPath()%>/jsp/pricing/Essiid.jsp';
	temp.pageAction.value = 'edit';
	temp.submit();
}
function callupload()
{
	var temp=document.forms[0];
	temp.action='<%=request.getContextPath()%>/jsp/pricing/ProspectiveCustomerUsageFile.jsp';
	temp.pageAction.value = 'edit';
	temp.submit();
}

function callcancel()
{
	var temp = document.forms[0];
	temp.pageUser.value = '<%=frm.getPageUser()%>';
	temp.cmbCustomerStatus.value = '0';
	temp.formActions.value = "cancel";
	temp.submit();
}

function echeck() {

		var temp = document.forms[0];
		var str = temp.txtEmail.value;
		var at="@"
		var dot="."
		var lat=str.indexOf(at)
		var lstr=str.length
		var ldot=str.indexOf(dot)
		
		if(str.trim().length <= 0)
			return true;
		else
		{
			if (str.indexOf(at)==-1){
			   alert("Invalid E-mail ID")
			   return false
			}
	
			if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
			   alert("Invalid E-mail ID")
			   return false
			}
	
			else if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
			    alert("Invalid E-mail ID")
			    return false
			}
	
			else if (str.indexOf(at,(lat+1))!=-1){
			    alert("Invalid E-mail ID")
			    return false
			 }
	
			else if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
			    alert("Invalid E-mail ID")
			    return false
			 }
	
			else if (str.indexOf(dot,(lat+2))==-1){
			    alert("Invalid E-mail ID")
			    return false
			 }
			
			 else if (str.indexOf(" ")!=-1){
			    alert("Invalid E-mail ID")
			    return false
			 }
			 else
			 	return true;
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
<body > 
<html:form action="ProspectiveCustomerRepEdit">  
<html:hidden property="formActions"/>
<html:hidden property="prospectiveCustId"/>
<html:hidden property="pageUser" />
<html:hidden property="createdDate" />
<html:hidden property="createdBy" />
<input type='hidden' name='pageAction'/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> 
    <td  valign="top"><jsp:include page="../menu.jsp"/>
      <validator:errorsExist>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
			<tr> 
	         <td class="error">
				<html:errors/>
	         </td>
	        </tr>
	     </table>
	  </validator:errorsExist>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title">Prospects</td> 
		  <td class="page_title">&nbsp;</td>
        </tr> 
      </table>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
      </table>
  <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr>
            <td width="112" class="subtab_on" id="set1">Customer&nbsp;Details</td>
           <!--  <td width="76" class="subtab_off" ><a href="#" onclick="callEsiid()"  >ESIID</a></td>  -->
            <td width="76" class="subtab_off"><a href="#" onclick="callPreferences()"  >Preferences</a></td>
            <td width="76" class="subtab_off"><a href = "#" onclick="callupload()">Usage&nbsp;Files</a></td>
			<td width="687">&nbsp;</td>
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
	  	
	  	<div style="border:solid 1px silver;overflow:auto; height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')">
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr id="tblPremium">
            <td>
            <fieldset id="custInfo" style='align:center; margin: -10px 10px 0px 10px'>
            <legend>Customer Info</legend> 
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
                  <td width="150" class="fieldtitle">Customer&nbsp;Id</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td  width="170" class="fieldata" colspan='4'><%=custId%>
				  <td colspan='6' class="fieldata">&nbsp;</td>  
				               	
				</tr>
				<tr>
                  <td width="150" class="fieldtitle">Name*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata" >
                  <html:text property="txtCustomerName" styleClass="textbox" maxlength="150" style="background-color:#FDFDB3"/>
                  </td>
				  
				  <td width="119" class="fieldtitle">Address Type*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata">
					<html:select property="cmbCMSAddressTypeId">
						<html:option value="0" >Select one</html:option>
						<html:options collection="hmAddressType" property="key" labelProperty="value" style="background-color:#FDFDB3"/>
				    </html:select>
				  </td>

				  <td width="119" class="fieldtitle">Sales Rep</td>
                  <td width="1" class="fieldtitle">:</td>
				<%if(!frm.getPageUser().equalsIgnoreCase("Rep")){%>
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

				<!--   <td width="119" class="fieldtitle">Aggregator</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata">
					  <html:select property="aggregator" disabled='true'>
						  <html:option value="0">Select one</html:option>
						</html:select>
				  </td> -->
				   <td width="119" class="fieldtitle">Status*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td  class="fieldata">
					 <html:select property="cmbCustomerStatus" style="background-color:#FDFDB3">
						  <html:option value="0" >Select one</html:option>
						  <html:options collection="objCustomerStatusVO" property="customerStatusId" labelProperty="customerStatus" />
					  </html:select>
                  </td>
                </tr>
				<tr>
                 
				  
				  <td width="150" class="fieldtitle">State*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><html:select property="cmbCustomerState" style="background-color:#FDFDB3">
				  <html:option value="0" >Select one</html:option>
				  <html:options collection="hmState" property="value" labelProperty="value" />
				</html:select></td>

				<!--   <td width="119" class="fieldtitle">Type</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata">
					<html:select property="typeNewRenewal" disabled='true'>
						<html:option value="1">New</html:option>
						<html:option value="2">Renewal</html:option>
					</html:select>
                  </td> -->
				    <td width="170" width="119" class="fieldtitle">City*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td  class="fieldata">
					  <html:text property="txtCustomerCity" styleClass="textbox" maxlength="50" style="background-color:#FDFDB3"/>
				  </td>
				 <td width="119" class="fieldtitle">Location Type*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td   class="fieldata">
					<html:select property="cmbCMSLocationId" style="background-color:#FDFDB3">
					  <html:option value="0" >Select one</html:option>
					  <html:options collection="hmLocationType" property="key" labelProperty="value" />
					 </html:select>
                  </td>

				<!--   <td width="119" class="fieldtitle">Broker</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata">
					  <html:select property="broker" disabled='true'>
						  <html:option value="0" >Select one</html:option>
						</html:select>
				  </td> -->

                </tr>
				<tr>
                
				 <!--  <td width="119" class="fieldtitle">Consultant</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata">
					 <html:select property="consultant" disabled='true'>
						<html:option value="0">Select one</html:option>
					 </html:select>
				  </td> -->

				
              			  
				  <td width="150" class="fieldtitle">Zip Code*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><html:text property="txtzipcode" styleClass="textbox" onkeypress="return checkNumber(event,'zip')" maxlength="12" style="background-color:#FDFDB3"/></td>

				  <td width="119" class="fieldtitle">Approval Status*</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata">Submitted</td>

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
				  <td class="fieldata"><html:text property="txtFax" styleClass="textbox" maxlength="20" onkeypress = "if((fnKeycodeValidate(event.keyCode,this.value)=='1')&&((event.keyCode>46 && event.keyCode<58)||(event.keyCode==8)||(event.keyCode==190) ||(event.keyCode==46) ||(event.keyCode==37) ||(event.keyCode==39) || (event.keyCode==9)|| (event.keyCode==45))) event.returnValue = true; else event.returnValue = false"  onclick="javascript:getIt(this)" onfocus="javascript:getIt(this)"/></td>
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
					<html:text property="txtMobile" styleClass="textbox" maxlength="20" onkeypress = "if((fnKeycodeValidate(event.keyCode,this.value)=='1')&&((event.keyCode>46 && event.keyCode<58)||(event.keyCode==8)||(event.keyCode==190) ||(event.keyCode==46) ||(event.keyCode==37) ||(event.keyCode==39) || (event.keyCode==9)|| (event.keyCode==45))) event.returnValue = true; else event.returnValue = false"  onclick="javascript:getIt(this)" onfocus="javascript:getIt(this)"/>
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

			<table width="100%"  border="0" cellspacing="0" cellpadding="0" style='margin: 5px 0px 0px 16px'>
				<tr>
                  <td width="150" class="fieldtitle" valign='top' style='color:0046D5'>Comments</td>
                  <td width="1" class="fieldtitle" valign='top'>:</td>
                  <td class="fieldata" colspan="7"><html:textarea property="txtcomments" styleClass="textbox" rows="3" cols="70" /></td>				  
				</tr>
			</table>

			<fieldset id="comments" style='align:center; margin: -5px 10px 0px 10px'>
            <legend>Comments-History</legend>
				<div style="overflow:auto;height:<%=((browserHt+100)-(browserHt))%>" id="divList"> 
				 <table width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr class='staticHeader'>
				    <td width="60%" class="tblheader" align="center">Comments</td>
				    <td width="20%" class="tblheader" align="center">Created By </td>
				    <td width="20%" class="tblheader" align="center">Created Date </td>
				  </tr>
				  <% if(((List)pageContext.getAttribute("setCustomerComments")).size()>0)
					{%>
				  <logic:iterate id="custComment" name="setCustomerComments">
				  <tr>
				    <td class="tbldata">&nbsp;<bean:write name="custComment" property="comments" ignore="true"/></td>
				    <td class="tbldata"><bean:write name="custComment" property="createdBy" ignore="true"/>&nbsp;</td>
				    <td class="tbldata"><bean:write name="custComment" property="createdDate" ignore="true" format="MM-dd-yyyy hh:mm a"/>&nbsp;</td>
				  </tr>
				  </logic:iterate>
 				<% } 
			        else
					{
					%>
						<tr><td class="tbldata" align='center' colspan='3' style='border-left:dotted 1px #CCCCCC;'>--- No Data ---</td></tr>
				 <% } %>
				</table>
				</div>
				</fieldset>
             </td>
            </tr>
			</table>
        </div>
        <br>
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td class="fieldata">
              	<html:button  property="Update" styleClass="button" value="Submit" onclick="addCustomer('update');"/>
				<html:reset value="Reset" styleClass="button"></html:reset >
               <html:button property="Cancel" styleClass="button" value="Cancel" onclick="callcancel()"></html:button >
            </td>
          </tr>
        </table>
    </td> 
  </tr> 
  <tr> 
    <td height="20"><table width="100%"  border="0" cellpadding="0" cellspacing="0" > 
        <tr> 
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