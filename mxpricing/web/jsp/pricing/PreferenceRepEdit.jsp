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
<%@ page import="com.savant.pricing.dao.ProductsDAO"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.PreferenceRepForm" />
<jsp:setProperty name="frm" property="*" />

<%
	int browserHt = 0;	
	try{
	if(request.getParameter("User")!=null)
	{
	frm.setPageUser(request.getParameter("User"));
	}
	else
	{
	frm.setPageUser((String)request.getSession().getAttribute("pageUser"));
	}
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy");
	java.text.SimpleDateFormat sdfMonth = new java.text.SimpleDateFormat("MM");
	String customerId = (String)session.getAttribute("customerId");
	String pageUser = (String)request.getSession().getAttribute("pageUser");	
	ProductsDAO objProductsDAO = new ProductsDAO();
	
	LinkedHashMap hmYear = new  LinkedHashMap();
	int year = Integer.parseInt(sdf.format(new java.util.Date()));
	for(int i=0;i<10;i++)
	{
		hmYear.put(new Integer(year+i),new Integer(year+i));
	}
	pageContext.setAttribute("hmYear",hmYear);						
	
	HashMap hm = objProductsDAO.getAllProducts(null,"productName",true,0,100,true);
	List lstResult = (List)hm.get("Records");
	pageContext.setAttribute("lstResult",lstResult);
	ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
	ProspectiveCustomerVO  objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(customerId));
	
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 260;
	else
		browserHt = 215;
%><head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script src='<%=request.getContextPath()%>/script/commonSort.js'></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">

<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
</head>
<script>
function setFormAction()
{
	document.forms[0].formActions.value="Modify";
	document.forms[0].pageUser.value='<%=pageUser%>';
	document.forms[0].submit();
}
function callOverrides()
{
 window.open('Overrides.htm','_blank','width=670,height=470') 
}
function checkEnter(e)
   { 
      var characterCode;
      e = event
      characterCode = e.keyCode ;
     if( (characterCode>=48) && (characterCode<=57)||characterCode==32||characterCode==44)
     {
		  return true;
	  }
	  else
	  {
	  return false;
	  }
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

function customerEdit()
{
	try{
	var temp = document.forms[0];	
	temp.action='<%=request.getContextPath()%>/ProspectiveCustomerRepEdit.do?prsCustId='+<%=customerId%>+'&User=<%=pageUser%>';
	temp.formActions.value = "edit";	
	temp.submit();
	}catch(err)
	{
	}
}
function callDes(id)
{
	for(var i=0;;i++)
	{
		var desid = "description"+i;
		if(document.getElementById(desid)==null)
		{
		  break;
		}
		else
		{
		  document.getElementById(desid).style.display='none';
		}
	}
	document.getElementById(id).style.display='block';
}
</script>

<html:html>

<link href="styles/Styles.css" rel="stylesheet" type="text/css">
</head>
<body onload="callDes('description0')"> 
<html:form action="frmPreferenceRepEdit">
<html:hidden property="formActions"/>
<html:hidden property="pageUser" />
<input type='hidden' name='pageAction'/>
  <table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
    <tr> 
      <td valign="top">
		<jsp:include page="../menu.jsp"/>
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
            <td width="250" class="page_title"> Prospective Customers</td> 
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
            <td width="122" class="subtab_off" id="set1"><a href="#"  onclick="customerEdit()" >Customer&nbsp;Details </a></td> 
			  <td width="76" class="subtab_on" >Preferences</td> 
           <td width="76" class="subtab_off"><a href = "#" onclick='callupload();'>Usage&nbsp;Files</a></td>
            <td width="687">&nbsp;</td> 
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
            <td width="210" class="fieldtitle">Customer Name </td> 
            <td width="1" class="fieldtitle">:</td> 
            <td class="fieldata" colspan="4"><%=objProspectiveCustomerVO.getCustomerName()%></td>
          </tr> 
          <tr> 
            <td width="210" class="fieldtitle">Customer DBA</td> 
            <td width="1" class="fieldtitle">:</td> 
            <td class="fieldata" colspan="4"><%=objProspectiveCustomerVO.getCustomerDBA()==null?"":objProspectiveCustomerVO.getCustomerDBA()+""%></td> 
          </tr> 
          <tr> 
            <td width="210" class="fieldtitle">Customer ID</td> 
            <td width="1" class="fieldtitle">:</td> 
            <td class="fieldata" colspan="4"><%=objProspectiveCustomerVO.getCustomerId()==null?"":objProspectiveCustomerVO.getCustomerId()+""%></td> 
          </tr> 
        </table> 
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr id="tblPremium">
            <td>
              <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="220">
                    <table width="226"  border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="226" class="fieldtitle">Products*</td>
                      </tr>
                    </table>
                    <div style="width:244px; height:175px; overflow:auto" id="productList">
                      <table width="226"  border="0" cellspacing="0" cellpadding="0">
                       <logic:iterate id="products" name="lstResult" indexId="count">
                        <tr>
                        <bean:define id="productid" name="products" property="productIdentifier"></bean:define>
                          <td class="tbldata"><html:multibox property="productIds"  value='<%=String.valueOf(productid)%>'/>
                              <a href="#" onclick="callDes('description<%=count.intValue()%>')"><bean:write name="products" property="productName"/></a></td>
                         </tr>
                       </logic:iterate>
                      </table>
                  </div></td>
                  <td width="772" valign="top" > <br>
                      <br>
                      <br>
                      <br>
                      <table width="80%"  border="0" cellspacing="0" cellpadding="0">
                        <tr>
                        <tr> 
                           <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
                        </tr> 
                        <tr> 
                            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
                        </tr> 
                          <td>
                          <logic:iterate id="products" name="lstResult" indexId="count">
                          <fieldset id="description<%=count.intValue()%>" style="display:none">
                            <legend><bean:write name="products" property="productName" /></legend>
                            <br>
                            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                              <tr>
                                <td width="120" class="fieldtitle">Product Name</td>
                                <td width="1" class="fieldtitle">:</td>
                                <td class="fieldata">&nbsp;<bean:write name="products" property="productName" /></td>
                              </tr>
                              <tr>
                                <td class="fieldtitle">Description</td>
                                <td class="fieldtitle">:</td>
                                <td class="fieldata">&nbsp;<bean:write name="products" property="description"/></td>
                              </tr>
                            </table>
                            </fieldset>
                            </logic:iterate>
                             </td>
                        </tr>
                    </table></td>
                </tr>
				
              </table><br>
              <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="210" class="fieldtitle">Contract Start Month*</td>
                  <td width="1" class="fieldtitle">:</td>
                  <td class="fieldata"><html:select property="stMonth" >
												  <html:option value="1">Jan</html:option>
  												  <html:option value="2">Feb</html:option>
												  <html:option value="3">Mar</html:option>
												  <html:option value="4">Apr</html:option>
												  <html:option value="5">May</html:option>
												  <html:option value="6">Jun</html:option>
												  <html:option value="7">Jul</html:option>
												  <html:option value="8">Aug</html:option>
												  <html:option value="9">Sep</html:option>
												  <html:option value="10">Oct</html:option>
												  <html:option value="11">Nov</html:option>
												  <html:option value="12">Dec</html:option>
												   </html:select>&nbsp;
						<html:select property="stYear" >
						<html:options collection="hmYear" property="key" labelProperty="value"/>
						</html:select>
					</td></tr>
                <tr>
                  <td class="fieldtitle">Terms*</td>
                  <td class="fieldtitle">:</td>
                  <td class="fieldata"><html:text property="terms"  size="10" onkeypress="return checkEnter(event)"/>   Eg.:12, 24, 36</td>
                 
                </tr>
                <tr>
                  <td class="fieldtitle" >Auto Run</td>
                  <td class="fieldtitle">:</td>
                  <td class="fieldata"><html:checkbox property="autoRun"/></td>
                </tr>
                <%if(frm.getPageUser().equalsIgnoreCase("Analyst"))
                {%>
                <tr>
                  <td class="fieldtitle">Pricing Scalar - Unitary</td>
                  <td class="fieldtitle">:</td>
                  <td class="fieldata"><html:checkbox property="unitary"/></td>
                </tr>
                <%}%>
              </table>
              <br>
              
              <table width="100%"  border="0" cellspacing="0" cellpadding="0" id="btnSubmit">
                <tr>
                  <td class="fieldata"><input name="Submit" type="button" class="button" id="Submit" value="Submit" onclick="setFormAction()">
                      <input name="Reset" type="reset" class="button" id="Reset" value="Reset">
                  </td>
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
}catch(Exception e)
{
e.printStackTrace();
}
}
%>