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
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%@ page import="com.savant.pricing.dao.PICDAO"%>
<%@ page import="com.savant.pricing.calculation.dao.DealLeversDAO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="com.savant.pricing.securityadmin.dao.UserDAO"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.DealLeversAnalyForm" />
<jsp:setProperty name="frm" property="*" />

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
	UserDAO objUserDAO = new UserDAO();
	boolean runElgible = objUserDAO.isUserElgible((String)session.getAttribute("userName"),"Run");
	ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
	ProspectiveCustomerVO  objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(frm.getPrsCustId()));
	List vecValidESIID = new ArrayList();
	PICDAO objPICDAO = new PICDAO();
	vecValidESIID =  objPICDAO.getAllValidESIID(Integer.parseInt(frm.getPrsCustId()));
	LinkedHashMap hmDealLever = new DealLeversDAO().getDealLeversTermDetails(Integer.parseInt(frm.getPrsCustId()));
	pageContext.setAttribute("hmDealLever",hmDealLever);

%>
<html:html>
<html>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
<script>
function callServlet(message,term)
	{
		var temp = document.forms[0];
		var param ;
		var custId = <%=frm.getPrsCustId()%>
		if((message=='Update')||((message=='add'))||(message=='ApplyToAll'))
		{
	    	var cust = temp.txtCust.value;
			var addl = temp.txtAddl.value;
			var Agnt = temp.txtAgnt.value
			var agg = temp.txtAgg.value;
			var bW = temp.txtBW.value;
			var other = temp.txtOther.value;
			var margin = temp.txtMargin.value;
			var longtrm = temp.txtLong.value;
            param = 'Term='+term+'&Message='+message+'&CustId='+custId+'&Cust='+cust+'&addl='+addl+'&Agnt='+Agnt+'&agg='+agg+'&bW='+bW+'&other='+other+'&margin='+margin+'&longterm='+longtrm;
		}
		else
		{
		  param = 'Term='+term+'&Message='+message+'&CustId='+custId;
		}

			var url = '<%=request.getContextPath()%>/servlet/dealLeverAnalyst';
			if (window.XMLHttpRequest) // Non-IE browsers
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse});
			}
			else if (window.ActiveXObject) // IE
			{
				req = new ActiveXObject("Microsoft.XMLHTTP");
				if (req)
				{
					req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse});
				}
			}
	}

	function showResponse(req)
	{
	try{
		var a =req.responseText.split("!@#");
		if(a.length==3)
		{
    		if(a[0]=='success')
    		{
	        	document.getElementById('returnmessage').innerText=a[1]; 
	        	document.getElementById('termtabledetails').innerHTML=a[2]; 
	        	document.getElementById('returnmessage').style.display='block';
	        	document.getElementById('addmodifyblock').innerHTML = "";
	        	document.getElementById('error').innerText = "";
        		document.getElementById('error').style.display='none';
        		
	        }
    		else
    		{
    		    document.getElementById('returnmessage').innerText=""; 
    		    document.getElementById('returnmessage').style.display='none';
        		document.getElementById('error').innerText = a[1];
        		document.getElementById('error').style.display='block';
        	}
		}
		else if(a.length==1)
		{
				document.getElementById('error').innerText = "";
        		document.getElementById('error').style.display='none';
				document.getElementById('addmodifyblock').innerHTML = a;
				document.getElementById('returnmessage').innerText="";        		
	        	document.getElementById('returnmessage').style.display='none';
	        	
		}
	}
		catch(err){
			alert(err.description);
		}
}
function calladd()
{
var term = document.forms[0].txtterm.value;
this.callServlet('add',term);
}
	
function viewDealLevers(term)
{
this.callServlet('view',term);
}
function checkentry()
{
      var characterCode ;
          characterCode = event.keyCode ;
     if((characterCode >=48 )&& (characterCode <=57 )||(characterCode ==46 ))
             return true;
       else
             return false; 
}
function callCancel()
{
document.getElementById('addmodifyblock').innerHTML = "";
}
function callModify(message)
{
       var temp = document.forms[0];
	   var term ;
	try
	{	
		
   if (temp.buttonGroup[0]) 
   		{
          for (var i=0; i<temp.buttonGroup.length; i++) 
              {
                if (temp.buttonGroup[i].checked)
                 {
                    term = temp.buttonGroup[i].value;
                 }
              }
        } 
        else {
                if (temp.buttonGroup.checked) 
                {
                 term = temp.buttonGroup.value; 
                } 
            }
     if((term==undefined)||(term==''))
     {
        if(message == 'modify')
        	alert('Please select any term Deal Adjustment to modify');
        else if(message == 'delete')
        	alert('Please select any term Deal Adjustment to delete');
     }
     else
        callServlet(message,term);
    }
    catch(err)
    {
      alert("There is no term Deal Adjustment");
    }
}
function callRun()
{
var temp= document.forms[0];
<%if(objProspectiveCustomerVO.getCdrStatus().getCdrState().equalsIgnoreCase("Approved")){%>
temp.action ='<%=request.getContextPath()%>/runList.do';
temp.custIds.value = '<%=frm.getPrsCustId()%>';
temp.submit();
<%}else{%>
alert("Customer is not Approved");
<%}%>
}
	
</script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
</head>
<body> 
<html:form action="/DealLeverAnalyst">  
<html:hidden property="formAction"/>
<html:hidden property="prsCustId"/>
<input type='hidden' name='custIds'/>
  <table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
    <tr> 
      <td  valign="top">  <jsp:include page="/jsp/menu.jsp"/> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="250" class="page_title"> Prospective Customers</td> 
            <td class="page_title">&nbsp;</td>
             <font class="message"><html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages></font>
            <font class="error"><html:errors/></font>
          </tr> 
        </table> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
          </tr> 
        </table> 
          
        <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td width="112" class="subtab_off" id="set1"><a href="<%=request.getContextPath()%>/jsp/pricing/ProspectiveCustomersAnalystEdit.jsp?prsCustId=<%=frm.getPrsCustId()+""%>" >Customer&nbsp;Details</a></td> 
            <td width="78" class="subtab_off" ><a href="<%=request.getContextPath()%>/FileUpload.do?prsCustId=<%=frm.getPrsCustId()%>">Import&nbsp;CUD</a></td> 
            <td width="82" class="subtab_off" ><a href="<%=request.getContextPath()%>/PreferenceAnalyst.do?prsCustId=<%=frm.getPrsCustId()%>" >&nbsp;Select ESIIDs</a></td> 
            <%if(!objProspectiveCustomerVO.isMmCust())
            {%>
			  <td width="82" class="subtab_off" ><a href="<%=request.getContextPath()%>/Ecomp.do?prsCustId=<%=frm.getPrsCustId()%>" >&nbsp;Select&nbsp;Components</a></td>
		  <%}%>
            <td width="80" class="subtab_on">Deal&nbsp;Adjustment</td> 
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
            <td width="125" class="fieldtitle">Customer Name</td> 
            <td width="1" class="fieldtitle">:</td> 
            <td width="187" class="fielddata">&nbsp;<%=objProspectiveCustomerVO.getCustomerName()%></td> 
            <td width="125" class="fieldtitle">Customer DBA</td> 
            <td width="1" class="fieldtitle">:</td> 
            <td class="fielddata">&nbsp;<%=objProspectiveCustomerVO.getCustomerDBA()==null?"":objProspectiveCustomerVO.getCustomerDBA()%></td> 
          </tr> 
          <tr>
            <td width="125" class="fieldtitle">Customer ID</td> 
            <td width="1" class="fieldtitle">:</td> 
            <td width="187" class="fielddata">&nbsp;<%=objProspectiveCustomerVO.getCustomerId()==null?"":(objProspectiveCustomerVO.getCustomerId()+"")%></td> 
            <td width="125" class="fieldtitle">No. of ESIIDs</td> 
            <td width="1" class="fieldtitle">:</td> 
            <td class="fielddata">&nbsp;<%=vecValidESIID.size()%></td> 
          </tr> 
        </table>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
          <tr> 
            <td class="fieldata"><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
          </tr> 
          <tr> 
          <td width='10'></td>
            <td id = 'error' style='display:none;' class="error"></td> 
          </tr> 
          <tr> 
           <td width='10'></td>
            <td id = 'returnmessage' style='display:none;'class='message'></td> 
          </tr> 
        </table> 
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="300" class="fieldtitle" height="23" align="center">Deal Adjustment Details</td>
           </tr>
        </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
        </table>

        <table width="991" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="390"><table width="274" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="30"  class="tblheader">&nbsp;</td>
                  <td width="110" class="tblheader">Term</td>
                  <td width="134" class="tblheader">Add/Modified On </td>
                </tr>
              </table>
                <div style="width:290px; height:240px ;overflow:auto" id='termtabledetails'>
                  <table width="272" border="0" cellspacing="0" cellpadding="0">
                  <logic:present name="hmDealLever">
                    <logic:iterate id="deallever" name="hmDealLever">
                    <tr>
                      <td width="27" class="tbldata"><input name='buttonGroup' type="radio" value='<bean:write name="deallever" property="key"/>'>
                      </td>
                      <td width="108" class="tbldata"><bean:write name="deallever" property="key"/></td>
                      <td width="137" class="tbldata" align="right"><a href="#" onClick="viewDealLevers('<bean:write name="deallever" property="key"/>');"><bean:write name="deallever" property="value" ignore="true" format="MM-dd-yyyy"/></a></td>
                    </tr>
                     </logic:iterate>
                  </logic:present>
                  </table>
                </div>
           </td>
            <td width="10">&nbsp;</td>
            <td width="584" valign="top" id='addmodifyblock'></td>
          </tr>
        </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0" id='tableaddbutton'> 
          <tr> 
            <td> <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
                <tr>
                  <td class="fieldata"><input name="Button" type="button" class="button" id="addbut" value="Add" onClick="callServlet('addBlock',0);">
                  <input name="Button" type="button" class="button" id="modify" value="Modify" onClick="callModify('modify');">
                  <input name="Button" type="button" class="button" id="delete" value="Delete" onClick="callModify('delete');">
                  <%if(runElgible&&!objProspectiveCustomerVO.isMmCust())
                  {%>
                  <input name="Button" type="button" class="button" id="run" value="Add to Run" onClick="callRun();">
                  <%}%>
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
}
catch(Exception e)
{
e.printStackTrace();
}
}
%>
