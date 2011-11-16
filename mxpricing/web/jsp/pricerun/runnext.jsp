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
<%@ page import="java.util.Date"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.dao.PreferenceTermsDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.CustomerPreferenceProductsVO"%>
<%@ page import="com.savant.pricing.dao.PICDAO"%>
<%@ page import="com.savant.pricing.calculation.valueobjects.CustomerPreferencesTermsVO"%>
<%@ page import="com.savant.pricing.dao.ForwardCurveBlockDAO"%>
<%@ page import="com.savant.pricing.dao.GasPriceDAO"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.RunListForm" />
<jsp:setProperty name="frm" property="*" />
<%
	CustomerPreferencesTermsVO objCustomerPreferencesTermsVO = new CustomerPreferencesTermsVO();
	
	String cusids = "";
    if(request.getParameter("custIds")!=null&&request.getParameter("custIds").length()>0)
    {
    cusids = request.getParameter("custIds");
    }
    else
    {
    cusids = frm.getPricerRunCustomerId();
    }
	ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
	PreferenceTermsDAO objPreferenceTermsDAO = new PreferenceTermsDAO();
	PICDAO objPICDAO = new PICDAO();
	List lstProspectiveCust =new java.util.LinkedList();
	java.text.SimpleDateFormat sdf = new 	java.text.SimpleDateFormat("MM/dd/yyyy hh:mm a");
	if(cusids!=null)
	{
		lstProspectiveCust = objProspectiveCustomerDAO.getProspectiveCustomers(cusids);
	}
	pageContext.setAttribute("lstProspectiveCust",lstProspectiveCust);
	
	ForwardCurveBlockDAO objForwardCurveBlockDAO = new ForwardCurveBlockDAO();
	Date date = objForwardCurveBlockDAO.fwdCurveLastImportedOn();
	GasPriceDAO objGasPriceDAO = new GasPriceDAO();
	Date dategas = objGasPriceDAO.teeNaturalGasPriceLastImportedOn();
	
	
	int browserHt = 0;
	if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 270;
	else
		browserHt = 250;
%>
<html:html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
<SCRIPT LANGUAGE="JavaScript">
function callExp()
{
	document.getElementById('CusExp1').style.display='block';
	document.getElementById('CusExp2').style.display='block';
	document.getElementById('img1').src = '<%=request.getContextPath()%>/images/open.gif';
	document.getElementById('img2').src = '<%=request.getContextPath()%>/images/open.gif';

}
function callCol()
{
	document.getElementById('CusExp1').style.display='none';
	document.getElementById('CusExp2').style.display='none';
	document.getElementById('img1').src = '<%=request.getContextPath()%>/images/close.gif';
	document.getElementById('img2').src = '<%=request.getContextPath()%>/images/close.gif';					
}										

function showHide(tbl,childtbl,img) {
	var table = document.getElementById(tbl);
	var chtable = document.getElementById(childtbl);
	var img = document.getElementById(img);
	
	if(table.style.display == 'none') {
		table.style.display = 'block';
		img.src = '<%=request.getContextPath()%>/images/open.gif';
		
	}
	else if(table.style.display == 'block') {
		table.style.display = 'none';
		img.src = '<%=request.getContextPath()%>/images/close.gif';
		
	}
}

function run()
{
	try{
    startclock();
	var temp = document.forms[0];
	temp.formActions.value="run";
	temp.submit();
	
 }catch(err)
	{
	alert(err);
	}
}
function showprogress(mess)
{
  if(mess=="yes")
	document.getElementById('progress').style.display='block';
	else
	document.getElementById('progress').style.display='none';
}
var nhours = 0;
var nmins = 0;
var nsecn = 0;
var currenthr = 0;
var currentmin = 0;
var currentsec = 0;
var defaulthr = 0;
var defmin = 0;
var defsec = 0;

function startclock()
{
	defsec = new Date().getSeconds()-1;
	var thetime=new Date();
	currentsec =thetime.getSeconds()-defsec;
	if(currentsec!=0)
		{
		nsecn += currentsec;
		}
	if(nsecn>=60)
		{
		nmins =nmins+1;
		nsecn = 0;
		}
	if(nmins>=60)
		{
		nhours +=1;
		nmins = 0;
		}
	if (nsecn<10)
	 	defsec="0"+nsecn;
	else
	 	defsec = nsecn;
	
	if (nmins<10)
	 	defmin="0"+nmins;
	 else
	 	defmin = nmins;
	
	document.getElementById('clockspot').value=nhours+":"+defmin+":"+defsec;
	setTimeout('startclock()',1000);

} 

function constructToolTip(custName, dba)
{
    	var table = "<span bgcolor='#E8E8E8'><table height=\'40\' border=\'0\' cellspacing=\'1\' cellpadding=\'0\'>"+
		     "<tr ><td><b> Name</b></td>"+
		     "<td width=\'1\'>:</td>"+
	    	 "<td> "+custName+"</td></tr>"+
		     "<tr><td><b>DBA</b></td>"+
	    	 "<td width=\'1\'>:</td>"+
	      	 "<td>"+dba+"</td></tr>"+
             "</table></span>";
        Tip(table);
}

</script>
<div id ="progress" style="overflow:auto; position:absolute; top:68px; left:891px; display:none; " >
	<img src="<%=request.getContextPath()%>/images/preloader_pricing.gif"  align="middle">
</div>
<body> 
<script type="text/javascript" src="<%=request.getContextPath()%>/script/tooltip/wz_tooltip.js"></script> 
<html:form action="runnext"> 
<html:hidden property="formActions"/>
<html:hidden property="pricerRunCustomerId" value="<%=cusids%>" /> 
<input type="hidden" name="custIds"/>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> 
    <td valign="top"> <jsp:include page="../menu.jsp"/>
  		<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
      		<tr> 
        		<td width="800" class="page_title"> Run 
   					<table width="100%"  border="0" cellpadding="0" cellspacing="0">
     					<tr> 
          					<td class="message"><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages></td> 
        				</tr> 
        				<tr> 
          					<td class='error'><html:errors/></td> 
        				</tr> 
   					</table>
   				</td>              
    			<td align='right' class="page_title">
     				<input type="text" id = 'clockspot'class="tboxfullplain" name="clockspot" size="10">
     			</td>
      		</tr> 
    	</table>
		<table border="0" cellpadding="0" cellspacing="0"> 
	      	<tr> 
		        <td width="125" class="fieldtitle">Rundate</td> 
		        <td width="1" class="fieldtitle">:</td> 
		        <td width="300" class="fieldata"><%=sdf.format(new java.util.Date())%> </td> 
		        <td width="151" class="fieldtitle">Pricing Analyst</td> 
		        <td width="1" class="fieldtitle">:</td> 
		        <td class="fieldata"><%=request.getSession().getAttribute("userName")%></td> 
	      	</tr> 
		  	<tr> 
	          <td colspan="6" class="fieldata"><strong>&nbsp;Forward Curves Last Imported Dates:</strong></td> 
	      	</tr> 
		  	<tr> 
          		<td class="fieldtitle">Electric Price </td> 
          		<td class="fieldtitle">:</td> 
          		<td class="fieldata"><%= date==null?"":sdf.format(date)%> </td> 
		   		<td class="fieldtitle">Natural Gas Price </td> 
           		<td class="fieldtitle">:</td> 
          		<td class="fieldata"><%= dategas==null?"":sdf.format(dategas)%></td>
	    	</tr> 
    	 </table>
    	 <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>
		  <div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')">
		  <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
    		<tr class='staticHeader'> 
		        <td width="16" class="cmbheader" align='center'>&nbsp;</td> 
		        <td width="80" class="tblheader" align='center'>Customer&nbsp;Id </td> 
		        <td width="200" class="tblheader" align='center'>Customer Name </td>
		        <td width="180" class="tblheader" align='center'>Product(s)</td>  
		        <td width="100" class="tblheader" align='center'>Terms</td> 
		        <td width="130" class="tblheader" align='center'>Point of Contact </td> 
		        <td width="96" class="tblheader" align='center'>No.of ESIID(s)</td> 
		        <td class="tblheader" align='center'>Sales Rep </td> 
      		</tr> 
        <logic:iterate id="prospectiveCust" name="lstProspectiveCust" indexId="s"> 
        <%
       	  String strproducts = "";
       	  String salesrep = "";
          int val = s.intValue();
		  
		  int prsCustId = ((ProspectiveCustomerVO)lstProspectiveCust.get(val)).getProspectiveCustomerId();
          List lstPreferenceTerms = objProspectiveCustomerDAO.getProspectiveCustomerPreferenceTerms(prsCustId);
          int esiIdCount = objPICDAO.getNoOfESIID(prsCustId);
          List ltspreferenceproducts = (List)objProspectiveCustomerDAO.getProspectiveCustomerPreferenceProducts(prsCustId);
          pageContext.setAttribute("ltspreferenceproducts",ltspreferenceproducts);
          pageContext.setAttribute("lstPreferenceTerms",lstPreferenceTerms);
          int i = 0;
        %>
        <tr> 
          <td class="tbldata" height='25'>&nbsp;</td> 
          <td class="tbldata" align='right'> 
          <bean:write name="prospectiveCust" property="customerId" ignore="true"/>
          &nbsp;</td> 
          <%
          		String str=((ProspectiveCustomerVO)lstProspectiveCust.get(val)).getCustomerName();
          		String dba = ((ProspectiveCustomerVO)lstProspectiveCust.get(val)).getCustomerDBA()==null?"":((ProspectiveCustomerVO)lstProspectiveCust.get(val)).getCustomerDBA();
          		String tootipCustName = str;
          		tootipCustName = tootipCustName.replaceAll("'","\\\\'");
                tootipCustName = tootipCustName.replaceAll("\"","");
		  		if(str.length()>30)
		  		{
					str = str.substring(0,28)+"...";
				%>
					<td class = 'tbldata' onmouseover="constructToolTip('<%=tootipCustName%>','<%=dba%>')"><%=str%></td> 
		  		<%
		  		}
		  		else
		  		{
          %>
					<td class = 'tbldata' onmouseover="constructToolTip('<%=tootipCustName%>','<%=dba%>')"><bean:write name="prospectiveCust" property="customerName" /></td> 
          <% 	} %>
          
          <logic:iterate id="products" name="ltspreferenceproducts" indexId="count">
		  <% 
 	 		if(count.intValue()==0)
 	 		{
 	 			strproducts = ((CustomerPreferenceProductsVO)ltspreferenceproducts.get(count.intValue())).getProduct().getProductName();
            }
            else
            {
            	strproducts += ", "+((CustomerPreferenceProductsVO)ltspreferenceproducts.get(count.intValue())).getProduct().getProductName();
            }
           %>
          </logic:iterate>
    		<%
    			str = strproducts;
    			if(str.length()>27)
		  		{
    				str = strproducts.substring(0,25)+"...";
    		%>
    		<td class="tbldata" title='<%=strproducts%>'><%=str%>&nbsp;</td>
    		<% 	}
    			else
    			{
    		 %>
    		<td class="tbldata"><%=strproducts%>&nbsp;</td>
    		<% 	} %>
         <% String strTerm = "";
          	String subStrTerm = "";
          	for(int k=0;k<lstPreferenceTerms.size();k++)
          	{
          		objCustomerPreferencesTermsVO = (CustomerPreferencesTermsVO)lstPreferenceTerms.get(k);
          		if(strTerm.equals(""))
          			strTerm = String.valueOf(objCustomerPreferencesTermsVO.getTerm()).trim();
          		else
          			strTerm = strTerm+", "+ String.valueOf(objCustomerPreferencesTermsVO.getTerm()).trim();
          	}
          	subStrTerm = strTerm;
			if(subStrTerm.length()>10)
				{
					subStrTerm = subStrTerm.substring(0,10)+"...";
				%>
				<td class="tbldata" align='right' title='<%=strTerm%>'><%=subStrTerm%>&nbsp;</td>
			<%
				}
				else
				{
			%>
				<td class="tbldata" align='right'><%=strTerm%>&nbsp;</td>
			<% } %>
          <td class="tbldata"><bean:write name="prospectiveCust" property="pocFirstName" />&nbsp;</td> 
          <td class="tbldata" align='right'><%=esiIdCount%>&nbsp;</td> 
          <% 
          if(((ProspectiveCustomerVO)lstProspectiveCust.get(val)).getSalesRep()!=null&&((ProspectiveCustomerVO)lstProspectiveCust.get(val)).getSalesRep().getFirstName()!=null)
          {
	          salesrep =((ProspectiveCustomerVO)lstProspectiveCust.get(val)).getSalesRep().getFirstName();
	          if(((ProspectiveCustomerVO)lstProspectiveCust.get(val)).getSalesRep()!=null&&((ProspectiveCustomerVO)lstProspectiveCust.get(val)).getSalesRep().getLastName()!=null)
	          salesrep = salesrep+" "+((ProspectiveCustomerVO)lstProspectiveCust.get(val)).getSalesRep().getLastName();
		 }
		 
		 String strSalesrep = "";
		 if(salesrep.length()>22)
		 {
			strSalesrep = salesrep.substring(0,20)+"...";
		 %>
          <td class="tbldata" title='<%=salesrep%>'><%=strSalesrep%>&nbsp;</td> 
      <% }
      	 else
      	 {
      	 %>
			<td class="tbldata"><%=salesrep%>&nbsp;</td> 
	  <% } %>
        </tr> 
        </logic:iterate>         
      </table>
	</div>
<table width="100%"  border="0" cellpadding="0" cellspacing="0" > 
          <tr> 
            <td colspan="3">&nbsp;</td> 
          </tr> 
          <tr> 
            <td colspan="3" align="right" ><input type="button" class="button" name="add" value=" Run " onClick="run();showprogress('yes');">
              <input type="button" name="cancel" value="Cancel" class="button" onClick="window.open('<%=request.getContextPath()%>/jsp/pricerun/run.jsp','_self')"></td> 
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
</html:html>
<%}%>