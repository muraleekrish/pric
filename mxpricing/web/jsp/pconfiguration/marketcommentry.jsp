<%
System.out.println("Start page :");
String userName  ="";
try
{
	String userType= "";
	if(session.getAttribute("userType")!=null)
	{
		userType = (String)session.getAttribute("userType");
	}
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
	 userName = (String)session.getAttribute("userName");
%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="com.savant.pricing.dao.MarketCommentryDAO" %>
<%@ page import="com.savant.pricing.calculation.valueobjects.MarketCommentryVO" %>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pconfiguration.MarketCommentryForm" />
<jsp:setProperty name="frm" property="*" />

<html:html>
<%
Properties props;
props = new Properties();
InputStream is = this.getClass().getClassLoader().getResourceAsStream("ApplicationResources.properties");
props.load(is);
String highlightColor = props.getProperty("list.highlight.color");
String padding = props.getProperty("list.highlight.padding");
int tot = 0;
String str="";
String id = "0";
MarketCommentryDAO objMarketCommentryDAO = new MarketCommentryDAO();
MarketCommentryVO objMarketCommentryVO = new MarketCommentryVO();
String formact = frm.getFormAction();
System.out.println("******** form action :"+ frm.getFormAction());

List myList = null;
myList = objMarketCommentryDAO.getAllMarketCommentries();

tot = myList.size();
pageContext.setAttribute("myList",myList);



%>
<head>
<title>Market Commentry</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
</head>
<%

%>

<script>

function callServlet()
	{
		var temp = document.forms[0];
		var mrktDate = temp.txtMrktDate.value;
		  param = 'marketDate='+mrktDate;
			var url = '<%=request.getContextPath()%>/servlet/MrktCmmntryServlet';
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
		var a =req.responseText;
		var temp = document.forms[0];
		if(a==0)
		{
				temp.createdBy.value ='<%=userName%>';
				temp.modifiedBy.value ='<%=userName%>';
				temp.formAction.value ='add';
				temp.submit();
		}
		else
		{
			alert('Commentary already enetered for the selected date');
		}
}	
function calladd()
{
	var temp = document.forms[0];
	if(temp.txtMrktCommentry.value=='')
	{
		alert('Enter Commentary');
		return false;
	}
	else if(temp.txtMrktDate.value=='')
	{
		alert('Select Market Date ');
		return false;
	}
	else
	{
		callServlet();
	}
}
function callmodify()
{

	var temp = document.forms[0];
	if(temp.txtMrktCommentry.value=='')
	{
		alert('Enter Commentary');
		return false;
	}
	else
	{
		temp.formAction.value ='modify';
		temp.submit();
	}
}
function deleteItem()
{
		 obj = document.getElementsByName('checkValue');
            var subQuery = '';
            var queryString = '<%=request.getContextPath()%>/mrktCom.do?formAction=delete';
            var CheckedValue = new Array();
            count = 0;
            for(i=0;i<obj.length;i++)
            {
                        if(obj[i].checked == true)
                        {
			            CheckedValue[count] = obj[i].value
                            count++;
                        }
            }
            if(CheckedValue.length == 0)
            {
                       alert('Select a Market Commentary');
                        return
	          }

		if(!confirm("Warning! \n\nThe chosen item/s will be Deleted !"))
			{	
				for(i=0;i<obj.length;i++)
          			{
		               	if(obj[i].checked == true)
		                	{
							    obj[i].checked = false;
                        	}
		    			return ;
					}
			}
					else 
					{
						for(var s=0;s<CheckedValue.length;s++)
			            {
			               subQuery = subQuery+'&mrktIds='+CheckedValue[s];
			            }
			            queryString = queryString + subQuery;
			            document.forms[0].action = queryString;
			            document.forms[0].submit();
					}
}
function callView(commentID)
{
	var temp = document.forms[0];
	temp.formAction.value ='get';
	document.getElementById('hiddenID').value = commentID;
	temp.submit();
}

/*function disableMouseRightClick()
{
	if (navigator.appName == 'Microsoft Internet Explorer' && (event.button == 2 || event.button == 3))
	alert()	
}
if (document.all&&!document.getElementById){
document.onmousedown=disableMouseRightClick;
}
document.oncontextmenu=new Function("return false");
*/

function calLoad()
{
	var temp = document.forms[0];
	if('<%=formact%>'!='get')
	{
		temp.txtMrktCommentry.value='';
	}
	else if('<%=formact%>'=='modify')
	{
		temp.txtMrktCommentry.value='';
		temp.txtMrktDate.value='';
	}
}
function resetItem()
{
	var temp = document.forms[0];
	temp.txtMrktCommentry.value='';
	temp.txtMrktDate.value='';
	temp.formAction.value ='reset';
	temp.submit();
}

function callValidate(value)
{
	var temp = document.forms[0];
	if(value.length>600)
	{
		alert('Market Commentry too lengthy Make it short');
		temp.txtMrktCommentry.value='';
		return false;
	}

}
</script>
<!-- Script by hscripts.com -->

</script>
<body onload="calLoad();"> 
<html:form action="/mrktCom" >
<html:hidden property="formAction" />
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" > 
  <tr> 
  <input type="text" name="hiddentxt" id="hiddenID" style="display:none" />
  <html:text property="commentid" styleClass="textbox" size="10" maxlength="15" readonly="true" style="display:none" />
<html:text property="createdDate" styleClass="textbox" size="10" maxlength="15" readonly="true" style="display:none" />
<html:text property="modifiedDate" styleClass="textbox" size="10" maxlength="15" readonly="true" style="display:none" />
<html:text property="modifiedBy" styleClass="textbox" size="10" maxlength="15" readonly="true" style="display:none" />
<html:text property="createdBy" styleClass="textbox" size="10" maxlength="15" readonly="true" style="display:none" />
    <td  valign="top"> <jsp:include page="../menu.jsp"/> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title" >Market Commentary</td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
        <tr>
         <td class="message" colspan='6'>
		<html:messages id="messageid" message="true">&nbsp;<bean:write name="messageid" /></html:messages>
		</td>
		</tr>
		<tr>
		<td colspan='6' class='error'> <html:errors/></td>
		</tr>
      </table> 
      <table width="100%"  border="0" cellpadding="0" cellspacing="0" id="tblSearch"> 
        <tr> 
		<td width="150" class="fieldtitle"> Market Date </td>
		<td width="1"  class="fieldtitle"> : </td>
		<td width="150" class="fieldata" > <html:text property="txtMrktDate" styleClass="textbox" size="10" maxlength="15" readonly="true"/>
		<%
			if(!formact.equalsIgnoreCase("get"))
		{
					
				if(!userType.equalsIgnoreCase("rep"))
				{
		
		%>
		 <a href="#"  onClick="showCalendarControl(document.getElementById('txtMrktDate'),'fully')">&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/calendar.gif"  width="16" height="16" border="0" align="absmiddle" id="fromdat">
		 <%}}%>
		 </td>
		
        </tr> 
        <tr> 
		<td class="fieldtitle" > Market Commentary </td>
		<td class="fieldtitle"> : </td>
		<%
		if(userType.equalsIgnoreCase("rep"))
		{
		%>
		<td class="fieldata"> <html:textarea property="txtMrktCommentry" styleClass="textbox" rows="4" cols="70" readonly='true'/> &nbsp;&nbsp;&nbsp;(Maximum of 500 characters) </td>
		<%
		}
		else
		{
		%>
		<td class="fieldata"> <html:textarea property="txtMrktCommentry" styleClass="textbox" rows="4" cols="70" onblur="callValidate(this.value);"/> &nbsp;&nbsp;&nbsp;(Maximum of 500 characters) </td>
		<%
		 }
		 %><td >&nbsp;</td>
	    </tr> 
		<%
		if(userType.equalsIgnoreCase("rep"))
		{
		%>
        <tr> 
        <td >&nbsp;</td>
         <td >&nbsp;</td>
         <td >&nbsp;</td>
         <td >&nbsp;</td>
		</tr> 
		<%
		}
		else
		{
		%>
		 <tr> 
				<%
			if(!formact.equalsIgnoreCase("get"))
				{
				%>
        <td id="add"> <input type="button" Class="button" value="Add"   onclick="calladd();"/>
		<input type="button" Class="button" value="Reset" onclick="resetItem();"/> </td>
				<%
					}
				else
				{
				%>

				<td id="modify"><input type="button" Class="button" value="Update"  onclick="callmodify();"/ > 
				<input type="button" Class="button" value="Reset" onclick="resetItem();"/></td>
				<%}%>
				<td colspan="3" align="right"> 
				<input type="button" Class="button" value="Delete" onclick="deleteItem();">
				</td>
				</tr> 
				 <%
				 }
				 %>
      </table> 
      <div style="overflow:auto;height:expression(330 + 'px')" id="divList"> 
        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr class='staticHeader'>
            <td width="12" class="cmbheader">&nbsp;</td>
            <td width="70" class="tblheader" align='center'>Market&nbsp;Date</td>
            <td width="420" class="tblheader" align='center'>Commentary</td>            
            <td width="80" class="tblheader" align='center'>Created&nbsp;By</td>
            <td width="100" class="tblheader" align='center'>Created&nbsp;Date</td>
            <td width="80" class="tblheader" align='center'>Modified&nbsp;By</td>
            <td class="tblheader" align='center'>Modified&nbsp;Date</td>
          </tr>
        <logic:iterate id="mrktCmmtry" name="myList" indexId="i">
<%
				int val = i.intValue();
				str=((MarketCommentryVO)myList.get(val)).getMarketComments();
				if(str.length()>85)
				{
					str = str.substring(0,83)+"...";
				}
%>
			<tr  onmouseover="this.style.cursor='hand'; this.style.backgroundColor='<%=highlightColor%>';this.style.padding='<%=padding%>'" onmouseout="this.style.backgroundColor=''">
			<td  class="tbldata"> <input name="checkValue" type="checkbox" id="checkValue" value='<bean:write name="mrktCmmtry" property="commentryId"/>'></td>
            <td  class="tbldata" align='right' title='<bean:write name="mrktCmmtry" property="marketDate"/>' onclick="callView('<bean:write name="mrktCmmtry" property="commentryId"/>')"> <bean:write name="mrktCmmtry" property="marketDate" ignore="true"/></td>
            <td  class="tbldata" align='left' title='<bean:write name="mrktCmmtry" property="marketComments"/>' onclick="callView('<bean:write name="mrktCmmtry" property="commentryId"/>')"><%=str%></td>            
<%
				
				str=((MarketCommentryVO)myList.get(val)).getCreatedBy();
				if(str.length()>10)
				{
					str = str.substring(0,8)+"...";
				}
%>
            <td  class="tbldata" align='left' title='<bean:write name="mrktCmmtry" property="createdBy"/>' onclick="callView('<bean:write name="mrktCmmtry" property="commentryId"/>')"> <%=str%></td>
            <td  class="tbldata" align='right' title='<bean:write name="mrktCmmtry" property="createdDate"/>' onclick="callView('<bean:write name="mrktCmmtry" property="commentryId"/>')"> <bean:write name="mrktCmmtry" property="createdDate" ignore="true"/></td>
            <%
				
				str=((MarketCommentryVO)myList.get(val)).getModifiedBy();
				if(str.length()>10)
				{
					str = str.substring(0,8)+"...";
				}
%>
            <td  class="tbldata" align='left' title='<bean:write name="mrktCmmtry" property="modifiedBy"/>' onclick="callView('<bean:write name="mrktCmmtry" property="commentryId"/>')"> <%=str%></td>
            <td  class="tbldata" align='right' title='<bean:write name="mrktCmmtry" property="modifiedDate"/>' onclick="callView('<bean:write name="mrktCmmtry" property="commentryId"/>')"> <bean:write name="mrktCmmtry" property="modifiedDate" ignore="true"/></td>
          </tr>
       </logic:iterate>
      </table>
      </div>
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
		 <tr> 
          <td class='fieldata' style="color:#0033CC;"><b>Total Records&nbsp;&nbsp;:&nbsp;&nbsp;<%=tot%></b></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0" > 
        <tr> 
          <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
        </tr> 
       
      </table> 
    </td> 
  </tr> 
  <tr> 
    <td height="20"><table width="100%"  border="0" cellpadding="0" cellspacing="0" > 
        <tr> 	<%String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";%>
          <td   class="copyright_link"><%=menupath%></td>
          <td width="250"   class="copyright" align='right'>Design Rights Savant Technologies </td> 
        </tr> 
      </table></td> 
  </tr> 
</table> 
</html:form>
</body>
</html:html>
<%}
System.out.println("End page :");
}
catch(Exception e)
{
e.printStackTrace();
}
%>