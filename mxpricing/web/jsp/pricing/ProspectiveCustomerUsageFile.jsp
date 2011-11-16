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
<%@ page import="com.savant.pricing.dao.CustomerFileDAO"%>
<%@ page import="com.savant.pricing.dao.ProspectiveCustomerDAO"%>
<%@ page import="com.savant.pricing.valueobjects.ProspectiveCustomerVO"%>
<%@ page import="com.savant.pricing.dao.CustomerFileTypeDAO"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.pricing.ProspectiveCustomerFileForm" /><jsp:setProperty name="frm" property="*" />
<%
	 java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MMM-yyyy");
	 String customerId = (String)session.getAttribute("customerId");
	 frm.setCustId(customerId);
	 Hashtable htCustomerFileType = new Hashtable();
	 String pageAction = request.getParameter("pageAction");
	 if(pageAction==null)
	  pageAction = (String)session.getAttribute("pageAction");
	 else
	 session.setAttribute("pageAction",pageAction);
	 CustomerFileDAO objCustomerFileDAO = new CustomerFileDAO();
	 CustomerFileTypeDAO objCustomerFileTypeDAO = new CustomerFileTypeDAO();
	 Collection colCustomerFileTypes = objCustomerFileTypeDAO.getAllFileTypes();
     LinkedHashMap objLinkedHashMap = new LinkedHashMap();
     objLinkedHashMap = objCustomerFileDAO.getAllFilesbyCustomer(Integer.parseInt(customerId));
     Iterator iter = colCustomerFileTypes.iterator();
     String pageUser = (String)request.getSession().getAttribute("pageUser");
     ProspectiveCustomerDAO objProspectiveCustomerDAO = new ProspectiveCustomerDAO();
     ProspectiveCustomerVO  objProspectiveCustomerVO = objProspectiveCustomerDAO.getProspectiveCustomer(Integer.parseInt(customerId));
	 pageContext.setAttribute("fileTypes",colCustomerFileTypes);
	 
	 int browserHt = 0;
	 if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
		browserHt = 400;
	else
		browserHt = 380;
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

function fnAdd()
{
	var temp = document.forms[0];
	var fileTypeId = temp.cmbFileTypes.value;
	if(fileTypeId == "0")
	{
		alert("Select a filetype");
		return;
	}
	else
	{
		temp.fileTypeId.value = fileTypeId;
		temp.custId.value = '<%=frm.getCustId()%>';
		temp.formActions.value = "addProspectiveCustomerFile";
		temp.submit();
	}
}

function fnCheck1(val)
{
	var fileId = val;
	var fileName = document.getElementById('td'+val).innerHTML;
	window.parent.location = '<%=request.getContextPath()%>/servlet/ProspectiveCustomerFileServlet?fileId='+<%=frm.getCustId()%>+'&fileName='+fileName;
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

function preference()
{
	var temp = document.forms[0];
	temp.action="<%=request.getContextPath()%>/frmPreferenceRepEdit.do?formActions=edit";
	temp.submit();
}

function callEsiid(message)
{
	var temp=document.forms[0];
	temp.action='<%=request.getContextPath()%>/jsp/pricing/Essiid.jsp?pageAction='+message;
	temp.submit();
}

function fnDelete(val1,val2)
{
	var fileName = val1;
	if(!confirm("Warning \n\n File '"+val1+"' will be deleted"))
	{
		return;
	}
	else
	{
		var fileTypeId = val2;
		var temp = document.forms[0];
		temp.custId.value = '<%=frm.getCustId()%>';
		temp.fileTypeId.value = fileTypeId;
		temp.fileName.value = val1;
		temp.formActions.value = "deleteProspectiveCustomerFile";
		temp.submit();
	}
}

</script>
<body > 
<html:form action="/prospectiveCustomersFile" method="post" enctype="multipart/form-data"> 
<html:hidden property="formActions" /> <html:hidden property="fileTypeId"/> 
<html:hidden property="custId"/>
<html:hidden property="fileName" /> 
<input type='hidden' name='pageAction'/> 
<table width="100%"  height="100%" border="0" cellpadding="0" cellspacing="0" > <tr> 
   <td  valign="top"> 
    <jsp:include page="../menu.jsp"/> 
  <validator:errorsExist> 
  <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
    <tr> 
      <td class="error"> <html:errors/> </td> 
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
      <%if(pageAction!=null && pageAction.equalsIgnoreCase("edit")){%> 
      <td width="112" class="subtab_off" id="set1"><a href="#" onclick="customerEdit()">Customer&nbsp;Details</a></td> 
      <td width="76" class="subtab_off"><a href="#" onclick="preference()">Preferences</a></td> 
      <%}
            else
            {%> 
      <td width="112" class="subtab_off" id="set1">Customer&nbsp;Details</td> 
      <td width="76" class="subtab_off"><a href="<%=request.getContextPath()%>/jsp/pricing/PreferenceRepAdd.jsp">Preferences</a></td> 
      <%}%> 
      <td width="76" class="subtab_on"><a href = "<%=request.getContextPath()%>/jsp/pricing/ProspectiveCustomerUsageFile.jsp">Usage&nbsp;Files</a></td> 
      <td width="535">&nbsp;</td> 
    </tr> 
  </table> 
  <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
    <tr> 
      <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td> 
    </tr> 
  </table>   
  <table width="97%" border="0" cellpadding="0" style = "BORDER:1PX solid #386383;" cellspacing="0" align = "center"> <tr id="tblPremium"> 
     <td> 
      <table width="100%" border="0" style = 'padding:1px;BORDER:1PX solid #386383' cellspacing="0" cellpadding="15" align = "center"> 
      <tr> 
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0"> 
            <tr> 
              <td width="13%" class="fieldtitle">Customer&nbsp;Name</td> 
              <td width="1" class="fieldtitle">:</td> 
              <td width="20%" class="fieldata"><%=objProspectiveCustomerVO.getCustomerName()%></td> 
              <td width="10%" class="fieldtitle">Customer&nbsp;DBA</td> 
              <td width="1" class="fieldtitle">:</td> 
              <td width="20%" class="fieldata"><%=objProspectiveCustomerVO.getCustomerDBA()==null?"":objProspectiveCustomerVO.getCustomerDBA()+""%></td> 
              <td width="10%" class="fieldtitle">Customer&nbsp;ID</td> 
              <td width="1" class="fieldtitle">:</td> 
              <td class="fieldata"><%=objProspectiveCustomerVO.getCustomerId()==null?"":objProspectiveCustomerVO.getCustomerId().intValue()+""%></td> 
            </tr> 
              <tr> 
              <td class="fieldtitle">FileType</td> 
              <td class="fieldtitle">:</td> 
              <td class="fieldata"><html:select property="cmbFileTypes"><html:options collection="fileTypes" property="fileTypeIdentifier" labelProperty="fileType"/> </html:select></td> 
              <td class="fieldtitle">Filepath</td> 
              <td class="fieldtitle">:</td> 
              <td class="fieldata"><html:file maxlength="1000" size="30" styleClass="botton"  property="theFile" onkeypress="return false"/></td> 
              <td align="right" class="fieldata" colspan="3">
			  <html:button property="add" styleClass="button" value="Add" onclick="javascript:fnAdd()"/></td> 
            </tr> 
			<tr>
              <td class="fieldtitle" valign='top'style='color:0046D5'>Comments</td> 
              <td class="fieldtitle" valign='top'>:</td> 
              <td class="fieldata" colspan="7"><html:textarea property="desc" styleClass="textbox" rows="3" cols="70" /></td> 
			  </tr>
          </table></td> 
        </tr> 
      <tr> 
       <td align="center"> 
        <table width="80%"  border="0"  style='padding:3px;' cellspacing="0" cellpadding="0" align="center"> 
          <logic:iterate id="fletype" name="fileTypes" indexId="j"> <bean:define id="filetypeID" name="fletype" property="fileTypeIdentifier"/> 
          <% 
			List objList  = (List)objLinkedHashMap.get(filetypeID);
			if(objList==null)
				pageContext.removeAttribute("objList");
			else
				pageContext.setAttribute("objList",objList);
			%> 
          <tr> 
            <td align='center' class='search1'><bean:write name="fletype" property="fileType" /></td> 
          </tr> 
          <tr> 
            <td style="padding:10px 0px">
				<div style="overflow:auto;height:expression((document.body.clientHeight - <%=browserHt%>) + 'px')"> 
                <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center"> 
                <tr class='staticHeader'> 
                    <td width="2%" class="tblheader1">S.&nbsp;No</td> 
                    <td width="40%" class="tblheader1">File&nbsp;Name</td> 
                    <td width="10%" class="tblheader1">Created&nbsp;On</td> 
					<td width="40%" class="tblheader1">Comments</td> 
                    <td style="background:#FFFFFF">&nbsp;</td> 
                  </tr> 
                <logic:present name="objList"> <logic:iterate id="filedetails" name="objList" indexId="i"> 
                <tr> 
                  <td class="tbldata1"><%=i.intValue()+1%></td> 
                  <td class="tbldata1" ><a id = "td<%=j.intValue()+1%><%=i.intValue()+1%>" href = "javascript:fnCheck1('<%=j.intValue()+1%><%=i.intValue()+1%>');"><bean:write name="filedetails" property="fileName" ignore="true"/></a></td> 
                  <td class="tbldata1"><bean:write name="filedetails" property="createdDate" ignore="true" format="MMM dd, yy"/></td> 
				  <td class="tbldata1"><bean:write name="filedetails" property="description" ignore="true"/>&nbsp;</td> 
                  <td class="fieldata"> &nbsp;&nbsp;<a id = "del<%=j.intValue()+1%><%=i.intValue()+1%>" href = "javascript:fnDelete('<bean:write name="filedetails" property="fileName" ignore="true"/>','<bean:write name="fletype" property="fileTypeIdentifier"/>');"> Delete</a></td> 
                </tr> 
                </logic:iterate> </logic:present> <logic:notPresent name="objList"> 
                  <tr> 
                  <td width="2%" class="tbldata1">&nbsp;</td> 
                  <td width="40%" class="tbldata1" align='center'> No files Available.</td> 
                  <td width="10%" class="tbldata1">&nbsp;</td> 
                  <td width="40%" class="tbldata1">&nbsp;</td> 
                  <td class="">&nbsp;</td> 
                </tr> 
                  </logic:notPresent> 
              </table> 
              </div> 
          </logic:iterate> 
          </td> 
           </tr> 
            </table> 
      </td> 
       </tr> 
        </table> 
    </td> 
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
