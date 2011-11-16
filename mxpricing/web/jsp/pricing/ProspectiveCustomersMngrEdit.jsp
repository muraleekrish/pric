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
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%@ page import="com.savant.pricing.valueobjects.CustomerCommentsVO"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.ProspectiveCustomerAddForm" />
<jsp:setProperty name="frm" property="*" />

<%

try{
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	if(request.getParameter("prsCustId")!=null && !request.getParameter("prsCustId").equals(""))
	{
	frm.setProspectiveCustId(request.getParameter("prsCustId"));
	session.setAttribute("prsCustId",request.getParameter("prsCustId"));
	}
	else{
	frm.setProspectiveCustId((String)session.getAttribute("prsCustId"));
	}
	
	ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
	ProspectiveCustomerVO  objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(frm.getProspectiveCustId()));
	pageContext.setAttribute("objProspectiveCustomerVO",objProspectiveCustomerVO);  
	
	LinkedHashMap hmAddressType = objProspectiveCustomerDAO.getAllAddressTypesFromCMS();
	LinkedHashMap hmLocationType =  objProspectiveCustomerDAO.getAllLocationTypesFromCMS();
	pageContext.setAttribute("hmAddressType",hmAddressType);
	pageContext.setAttribute("hmLocationType",hmLocationType);
	
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
		
		String path = request.getContextPath()+"/jsp/pricing/ProspectiveCustomersMngr.jsp";
		
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>

<script>

function customerEdit()
{
	try{
	var temp = document.forms[0];
	temp.action='<%=request.getContextPath()%>/ProspectiveCustomerRepEdit.do?prsCustId='+<%=frm.getProspectiveCustId()%>+'&User=Manager';
	temp.formActions.value="edit";
	temp.submit();
	}catch(err)
	{
	}
}

function actioncontrol(formaction)
{
	var temp = document.forms[0];
	temp.formActions.value= formaction;
	temp.submit();
}
function callPopUpPage()
{
	window.open('<%=path%>','_self');
}
</script>
</head>
<body> 
<html:form action="pMngrEdit">  
<html:hidden property="formActions"/>
<html:hidden property="prospectiveCustId"/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/>
      <validator:errorsExist>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
			<tr> 
	         <td class="error">
				<html:errors/>
	         </td>
	        </tr>
	     </table>
		</validator:errorsExist>
		<logic:messagesPresent message="true" >
		<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
			<tr> 
	         <td class="message">
				<html:messages id="messageid" message="true"><bean:write name="messageid" /></html:messages>
	         </td>
	        </tr>
	     </table>
	  </logic:messagesPresent>
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
            <td width="113" class="subtab_on" id="set1">Customer&nbsp;Details </td>
            <td width="82" class="subtab_off" ><a href="<%=request.getContextPath()%>/jsp/pricing/MangrViewPic.jsp" >CUD&nbsp;Info</a></td>
            <td width="715"></td>
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
                  <td class="fieldata" colspan='7'><bean:write name="objProspectiveCustomerVO" property="customerId" ignore="true"/></td>				              	
				</tr>
				<tr>
                  <td width="150" class="fieldtitle">Name</td>
                  <td width="1" class="fieldtitle">:</td>
				 <%
                  		String strCustName = objProspectiveCustomerVO.getCustomerName()==null?"":objProspectiveCustomerVO.getCustomerName();
                  		String cpyStrCustName = strCustName;
                  		if(cpyStrCustName.length()>32)
							strCustName = strCustName.substring(0,30)+"...";
                  %> 
                  <td width="170" class="fieldata" <%if(cpyStrCustName.length()>32)out.write("title='"+cpyStrCustName+"'");%> ><%=strCustName%></td>
				  <td width="119" class="fieldtitle">Address Type</td>
                  <td width="1" class="fieldtitle">:</td>
				  <%
                  	String stradrType = "";
                  	int adrTypeId = objProspectiveCustomerVO.getAddressTypeId();
                  	stradrType = String.valueOf(hmAddressType.get(new Integer(adrTypeId))==null?"":hmAddressType.get(new Integer(adrTypeId)));
                  %>
                  <td width="170" class="fieldata"><%=stradrType%></td>

				  <td width="119" class="fieldtitle">Sales Rep</td>
                  <td width="1" class="fieldtitle">:</td>
				 <%
                  		String strSalesRepFrstName = objProspectiveCustomerVO.getSalesRep().getFirstName()==null?"":objProspectiveCustomerVO.getSalesRep().getFirstName();
                  		String strSalesRepLstName = objProspectiveCustomerVO.getSalesRep().getLastName()==null?"":objProspectiveCustomerVO.getSalesRep().getLastName();
                  		String strFullSPFullName = strSalesRepFrstName+" "+strSalesRepLstName;
                  		String cpyStrSalesRep = strFullSPFullName;
                  		if(strFullSPFullName.length()>37)
							cpyStrSalesRep = strFullSPFullName.substring(0,35)+"...";
                  %>
				<td <%if(strFullSPFullName.length()>32)out.write("title='"+strFullSPFullName+"'");%> class="fieldata"><%=cpyStrSalesRep%></td> 	
                </tr>
				<tr>
                  <td width="150" class="fieldtitle">DBA</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="customerDBA" ignore="true"/></td>				  
				  <td width="119" class="fieldtitle">Address</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="address" ignore="true"/></td>
				  <td width="119" class="fieldtitle">Aggregator</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata">&nbsp;</td>
                </tr>
				<tr>
                  <td width="150" class="fieldtitle">Status</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="customerStatus.customerStatus" ignore="true"/></td>				  
				  <td width="119" class="fieldtitle">State</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="state" ignore="true"/></td>
				  <td width="119" class="fieldtitle">Broker</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata">&nbsp;</td>
                </tr>
				<tr>
                  <td width="150" class="fieldtitle">Type</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata">&nbsp;</td>				  
				  <td width="119" class="fieldtitle">City</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="city" ignore="true"/></td>
				  <td width="119" class="fieldtitle">Consultant</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata">&nbsp;</td>
                </tr>
				<tr>
                  <td width="150" class="fieldtitle">Location Type</td>
                  <td width="1" class="fieldtitle">:</td>
				  <%
                  	String strlocType = "";
                  	int locTypeId = objProspectiveCustomerVO.getLocationTypeId();
                  	strlocType = String.valueOf(hmLocationType.get(new Integer(locTypeId))==null?"":hmLocationType.get(new Integer(locTypeId)));
                  %> 
                  <td width="170" class="fieldata"><%=strlocType%></td>				  
				  <td width="119" class="fieldtitle">Zip Code</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td width="170" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="zipCode" ignore="true"/></td>
				  <td width="119" class="fieldtitle">Approval Status</td>
                  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata"><bean:write name="objProspectiveCustomerVO" property="cdrStatus.cdrState" ignore="true"/></td>
                </tr>
			</table>
			</fieldset>
			<br>

			<fieldset id="pointofCont" style='align:center; margin: -9px 10px 0px 10px'>
            <legend>Point Of Contact</legend> 
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">			  		
				<tr>
				  <td width="150" class="fieldtitle">Name</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata">
					<bean:write name="objProspectiveCustomerVO" property="pocFirstName" ignore="true"/>&nbsp;<bean:write name="objProspectiveCustomerVO" property="pocLastName" ignore="true"/>
				  </td>				  
				  <td width="119" class="fieldtitle">Phone</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="phone" ignore="true"/></td>
				  <td width="119" class="fieldtitle">Fax</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata"><bean:write name="objProspectiveCustomerVO" property="fax" ignore="true"/></td>
				</tr>
				<tr>
				  <td width="150" class="fieldtitle">Title</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="title" ignore="true"/></td>
				  <td width="119" class="fieldtitle">Mobile</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="mobile" ignore="true"/></td>
				  <td width="119" class="fieldtitle">Email</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata"><bean:write name="objProspectiveCustomerVO" property="email" ignore="true"/></td>
				</tr>				
			</table>
			</fieldset>

			<fieldset id="businessInfo" style='align:center; margin: -5px 10px 0px 10px'>
            <legend>Business Info</legend> 
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td width="150" class="fieldtitle">Business Type</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="businessType" ignore="true"/></td>				  
				  <td width="119" class="fieldtitle">Competitor</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="competitor" ignore="true"/></td>
				  <td width="119" class="fieldtitle">Out Of Cycle Switch?</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata"><logic:equal value="true" property="outOfCycleSwitch" name="objProspectiveCustomerVO"> Yes </logic:equal> <logic:equal value="false" property="outOfCycleSwitch" name="objProspectiveCustomerVO"> No </logic:equal></td>
				</tr>
				<tr>
				  <td width="150" class="fieldtitle">Current Provider</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="currentProvider" ignore="true"/></td>				  
				  <td width="119" class="fieldtitle">Competitor Price</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td width="170" class="fieldata"><bean:write name="objProspectiveCustomerVO" property="competitorPrice" ignore="true"/></td>
				  <td width="119" class="fieldtitle">Tax Exempt</td>
				  <td width="1" class="fieldtitle">:</td>
				  <td class="fieldata"><logic:equal value="true" property="taxExempt" name="objProspectiveCustomerVO"> Yes </logic:equal> <logic:equal value="false" property="taxExempt" name="objProspectiveCustomerVO"> No </logic:equal></td>
				</tr>
				<tr>
                  <td width="150" class="fieldtitle">Number of ESIIDs</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td  class="fieldata" colspan='7'>&nbsp;</td>         	
				</tr>
			</table>
			</fieldset>
			
			<fieldset id="comments" style='align:center; margin: -5px 10px 0px 10px'>
              <legend>Comments-History</legend>
				<div style="overflow:auto;height:<%=((browserHt+120)-(browserHt))%>" id="divList"> 
				 <table width="100%" border="0" cellspacing="0" cellpadding="0">
				  <tr class='staticHeader'>
				    <td width="60%" class="tblheader" align="center">Comments</td>
				    <td width="20%" class="tblheader" align="center">Created By </td>
				    <td width="20%" class="tblheader" align="center">Created Date</td>
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
		<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
			<tr> 
				<td class="fieldata">
					<html:button  property="Edit" styleClass="button" value="Edit" onclick="customerEdit()"></html:button>
					 <html:button  property="Approve" styleClass="button" value="Approve" onclick="actioncontrol('approve')"></html:button>
					 <html:button  property="Reject" styleClass="button" value="Reject" onclick="actioncontrol('reject')"></html:button>
					 <html:button  property="Next" styleClass="button" value="Next" onclick="actioncontrol('next')"></html:button>
				 
					 	
					 
					 	
					 <html:button  property="Cancel" styleClass="button" value="Cancel" onclick="callPopUpPage()"></html:button>
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
</table>  </html:form>
</body>
</html:html>
<%}catch(Exception e){
e.printStackTrace();
}
}%>