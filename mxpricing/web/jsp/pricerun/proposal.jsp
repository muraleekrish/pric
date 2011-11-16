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
<%@ page import="com.savant.pricing.calculation.dao.PricingDAO"%>
<%@ page import="com.savant.pricing.transferobjects.PricingDashBoard"%>
<%@ page import="com.savant.pricing.dao.ContractsDAO"%>
<%@ page import="com.savant.pricing.common.Filter"%>
<%@ page import="com.savant.pricing.valueobjects.ContractsVO"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.savant.pricing.common.NumberUtil"%>
<%@ page import="java.util.Set"%>
<%@ page import="com.savant.pricing.securityadmin.dao.UserDAO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="com.savant.pricing.dao.PriceRunCustomerDAO" %>
<%@ page import="com.savant.pricing.calculation.valueobjects.PriceRunCustomerVO" %>
<jsp:useBean id="frm" class="com.savant.pricing.presentation.contract.ContactListForm" />
<jsp:setProperty name="frm" property="*" />
<html:html>
<%
try
{
	int browserHt = 0;
	boolean order = true;
	SimpleDateFormat dfTime = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
	
	ContractsDAO  objContractsDAO = new ContractsDAO();
	PricingDashBoard objPricingDashBoard = null;
	java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MM-dd-yyyy");
	int totalCount=1;
	int pageCount = 1;
	int maxItems = 0;
	int term = 0;
	String esiids = "";
	int priceRunId = 0;
	float diff = 0;
	NumberFormat tnf = NumberUtil.tetraFraction();
	NumberFormat dnf = NumberUtil.doubleFraction();
	NumberFormat nf = NumberFormat.getIntegerInstance();
	HashMap hmCustomer = new HashMap();
	UserDAO objUserDAO = new UserDAO();
	boolean cpeElgible = objUserDAO.isUserElgible((String)session.getAttribute("userName"),"Run");
	PriceRunCustomerDAO objPriceRunCustomerDAO = new PriceRunCustomerDAO();
	PricingDAO objPricingDAO = new PricingDAO();
    PriceRunCustomerVO objPriceRunCustomerVO = null;
    int priceRunRefId = 0;
    int totalPages = 0;
    if(frm.getSortOrder().equalsIgnoreCase("ascending"))
	{
		order = true;
	}
	else
	{
		order = false;
	}
    if(request.getParameter("priceRunId")!=null)
    {
    	priceRunId = Integer.parseInt(request.getParameter("priceRunId"));
    }
	Filter fil =null;
	Filter filSales=null;
			if(request.getParameter("ftmsg")!=null&&request.getParameter("ftmsg").equalsIgnoreCase("footer"))
			{
			String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";
			String menuFtr[]= menupath.split("For");
			 if(menuFtr.length>1 && ! menuFtr[1].trim().equalsIgnoreCase("all Customers"))
				 {
				  String id = menuFtr[1].split("-")[0].trim();
				  frm.setTxtCMSId(id);
				 }
			}
    	 int cmsId = frm.getTxtCMSId().trim().equals("")?0:Integer.parseInt(frm.getTxtCMSId());
    	 maxItems = Integer.parseInt(frm.getMaxItems());
		 pageCount = Integer.parseInt(frm.getPage());
		 if(frm.getFormActions().equalsIgnoreCase("search"))
  		    {
  		    	maxItems = Integer.parseInt(frm.getMaxItems());
	  		  	if(!frm.getTxtCustomerName().equalsIgnoreCase(""))
				{
					fil = new Filter();
		   			fil.setFieldName("searchCustomerName");
		   			fil.setFieldValue(frm.getTxtCustomerName());
		   			fil.setSpecialFunction(frm.getSearchCustomerName());
				}
				if(!frm.getTxtSalesName().equalsIgnoreCase(""))
				{
			        filSales = new Filter();
		   			filSales.setFieldName("searchSalesName");
		   			filSales.setFieldValue(frm.getTxtSalesName());
		   			filSales.setSpecialFunction(frm.getSearchSalesName());
				}
				/*
				if(cpeElgible)
				hmCustomer = objContractsDAO.getAllCPE(df.parse(frm.getTxtDateFrom()),df.parse(frm.getTxtDateTo()),fil,filSales,priceRunId, cmsId, order, frm.getSortField());
				else
				hmCustomer = objContractsDAO.getAllCPEByManager(String.valueOf(session.getAttribute("userName")),df.parse(frm.getTxtDateFrom()),df.parse(frm.getTxtDateTo()),fil,filSales,priceRunId, cmsId, order, frm.getSortField()); 
				*/
				hmCustomer = objPriceRunCustomerDAO.getAllCPE(String.valueOf(session.getAttribute("userName")),cpeElgible,df.parse(frm.getTxtDateFrom()),df.parse(frm.getTxtDateTo()),fil,filSales,priceRunId, cmsId, order, frm.getSortField(),((pageCount-1)*maxItems), maxItems);
		  }
		  else
		  {
               SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                Date objdate = new Date();
                frm.setTxtDateFrom(sdf.format(new Date(objdate.getYear(),objdate.getMonth(),objdate.getDate()-2,0,0,0)));
                frm.setTxtDateTo(sdf.format(new Date(objdate.getYear(),objdate.getMonth(),objdate.getDate(),0,0,0)));
				/*
                if(cpeElgible)
                {
                hmCustomer = objContractsDAO.getAllCPE(df.parse(frm.getTxtDateFrom()),df.parse(frm.getTxtDateTo()),null,null,priceRunId,cmsId, order, frm.getSortField());
                }
                else
                hmCustomer = objContractsDAO.getAllCPEByManager(String.valueOf(session.getAttribute("userName")),df.parse(frm.getTxtDateFrom()),df.parse(frm.getTxtDateTo()),null,null,priceRunId, cmsId, order, frm.getSortField());  
                */
                hmCustomer = objPriceRunCustomerDAO.getAllCPE(String.valueOf(session.getAttribute("userName")),cpeElgible,df.parse(frm.getTxtDateFrom()),df.parse(frm.getTxtDateTo()),null,null,priceRunId,cmsId, order, frm.getSortField(),((pageCount-1)*maxItems), maxItems);
		  }
		Set setCust = hmCustomer.keySet();
		List lstCustname = new java.util.ArrayList();
		//lstCustname.addAll(setCust);
		lstCustname = (List)hmCustomer.get("Records");
		totalCount = Integer.parseInt(String.valueOf(hmCustomer.get("TotalRecordCount")));
		if(totalCount%maxItems == 0)
	  	{
		  	totalPages = (totalCount/maxItems);
	  	}
	  	else
	 	{
	  		totalPages = (totalCount/maxItems) + 1;
	  	}
	  	if(totalPages==0)
			totalPages = 1;
	    if(frm.getFormActions().equalsIgnoreCase("navigation"))
		{
			 pageCount = Integer.parseInt(frm.getPage());
		}
		else if(frm.getFormActions().equalsIgnoreCase("navigationDown"))
		{
			maxItems = Integer.parseInt(frm.getMaxItems());
	
		}
		pageContext.setAttribute("lstCustname",lstCustname);
		pageContext.setAttribute("hmCust",hmCustomer);
		
		if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
			browserHt = 260;
		else
			browserHt = 240;
		/* 
		if(request.getAttribute("message")!=null && (request.getAttribute("message").equals("message") || request.getAttribute("message").equals("error")))
			browserHt -= 405;
		else
			browserHt -= 385; */
%>
<head>
<title>Customer Pricing</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="<%=request.getContextPath()%>/script/CalendarControl.js"></script>
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script src='<%=request.getContextPath()%>/script/prototype.js'></script>
</head>
<script type="text/javascript">


function showHide(tbl,childtbl,img,CMSid) {
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
function clearsearch()
{
	temp=document.forms[0];
	temp.txtCustomerName.value="";
	temp.txtCMSId.value="";	
	temp.txtSalesName.value="";
	temp.searchCustomerName.selectedIndex =0;
	temp.formActions.value="clear";
	temp.searchSalesName.selectedIndex = 0;
}

function changePageGoto()
{
	temp = document.forms[0];
	temp.formActions.value="search";
	temp.submit();
}

function changePage()
{
	temp=document.forms[0];
	temp.formActions.value="search";
	temp.page.value  =0;
	temp.submit();
}
function pageIncrement()
 {
 	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) + 1;
	temp.page.value  = page;
	temp.submit();
}
function pageDecrement()
{

 	temp = document.forms[0];
	var page = 1;
	page = parseInt(temp.page.value,10) - 1;
	temp.page.value  = page;
	temp.submit();
}
function search()
{
    temp = document.forms[0];
	temp.formActions.value="search";
	temp.submit();
}

function pdf(term,custIdvalue,productId,message)
{
    var temp = document.forms[0];
    window.parent.location = '<%=request.getContextPath()%>/servlet/CPEPDFServlet?Term='+term+'&custIdvalue='+custIdvalue+'&ProductId='+productId+'&Message='+message;
}

function showContractResponse(message)
{
var term="";
var custId = 0;
var productId=0;
var Esiid = 0;
var flag = true;
try{
	var objcpe = document.getElementsByName('checkValue');
   	if(objcpe.length!=undefined)
		{
			for(var i=0;i<objcpe.length;i++)
			{
				if(objcpe[i].checked)
				{
					var str = objcpe[i].value;					
					arraystr = str.split(":");
					term += arraystr[0]+",";
					if((custId != 0)&&(custId != arraystr[1]))
					{
					  alert("Select term details within customer run date.");
					  flag = false;
					  break;
					}else if((productId != 0)&&(productId != arraystr[2]))
					{
					  alert("Select terms within a Product.");
					  flag = false;
					  break;
					}else if(Esiid != 0)
					{
						if(Esiid.length==arraystr[3].length)
					    {							
						   var localesiid = Esiid.split(",");
						   for(var esiidCount =0;esiidCount<localesiid.length;esiidCount++ )
							{							   
							   var varEsiid = arraystr[3].split(",");							   
							   var flag = true;
							   for(var j = 0;j<varEsiid.lenth;j++)
								{								  
								   if(localesiid[esiidCount]==varEsiid[j])
									{
									   flag = false;
									}
								}
								if(!flag)
								{
									alert("Select term details for same ESIID selection.");
						            flag = false;
								    break;
								}								
							}							
						 }
						 else
						{
							 alert("Select term details for same ESIID selection.");
						     flag = false;
							 break;
						}
					  
					}
					custId = arraystr[1];
					productId = arraystr[2];
					Esiid = arraystr[3];
				}
			}
		}
		else if(document.getElementById('checkValue').checked)
		{
		            var str = objcpe.value;
					arraystr = str.split(":");
					term = arraystr[0];
					custId = arraystr[1];
					productId = arraystr[2];
		}
		if(term.length<1||custId==0||productId==0)
		{
			alert("Select contract details of atleast one term.");
		}
		else
		{
		if(flag)
		{
			pdf(term,custId,productId,message);
		}
	}
	}catch(err)
	{
		var pos = err.description.indexOf("checkValue.length' is null")
		if(pos > 0)
			alert("No data in this List");
	}
}

var localproductId = "";
var localcontractId = "";

function createContract(contractId,productId,CMSID,name,template,prc)
{
var price = 0;
if(prc!=undefined)
	price = prc;

		<%
		String ftrStr = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";
		String[] menustr = ftrStr.split("For"); %>
		var ftrstr1 = "<%=menustr[0]%>";
		var ftrstr2 = "<%=menustr[1].replaceAll("\"","")%>";
		if(CMSID=="")
			ftrstr2 = ftrstr2.replace(ftrstr2,"All Customers");
		else
			ftrstr2 = ftrstr2.replace(ftrstr2,CMSID+' - '+name);
		document.getElementById('foterId').innerHTML = ftrstr1+' for '+ftrstr2;
 if(productId!=1&&productId!=5&&productId!=2&&productId!=6&&productId!=7&&productId!=8)
					{
					  alert("Contract template is not available.");
					  return false;
					}
else
{
			    	window.parent.location = '<%=request.getContextPath()%>/servlet/ContractPDFServlet?ContractId='+contractId+'&time='+new Date()+'&template='+template+'&productId='+productId+'&price='+price	;
}
}
function loadDefault()
{
var temp = document.forms[0];
var formAction = '<%=frm.getFormActions()%>';
var maxItems = '<%=frm.getMaxItems()%>';
var page = '<%=frm.getPage()%>';
temp.page.value = page;
temp.txtCustomerName.value = temp.txtCustomerName.value;
temp.txtSalesName.value = temp.txtSalesName.value;
temp.txtCMSId.value = '<%=frm.getTxtCMSId().equalsIgnoreCase("0")?"":frm.getTxtCMSId()%>';
}
function checkEntertime(e)
{
 	var characterCode ;
     e = event
     characterCode = e.keyCode 
     if(characterCode >=48 && characterCode<=58)
     { 
     if(characterCode==58)
     {
     	var ExpTimearr  = document.getElementById('CPEExpiretime').value;
    	var dot = ExpTimearr.split(":");
     	if(dot.length>1)
		 {
		   return false;
		 }
		 else
		 {
		   	return true;
		 }
		 }
     }
     else
     {
       return false; 
     }
}
function createBlankPdf(custRunId,productId)
{
	if(productId!=1&&productId!=5&&productId!=2&&productId!=6&&productId!=7&&productId!=8)
					{
					  alert("Contract template is not available.");
					  return false;
					}
			else
			{
			    	window.parent.location = '<%=request.getContextPath()%>/servlet/ContractBlankPDFServlet?pricerunCustId='+custRunId+'&productId='+productId;
			}

}
function checkEnter(e)
{ 
     var characterCode ;
     e = event
     characterCode = e.keyCode 
     if(characterCode==13)
     { 
     	search();
       return false;
     }
     else
     {
       return true; 
     }
}
function checkCpe2()
{
var flag=false;
var productId=0;

var objcpe2 = document.getElementsByName('checkValue');
if(objcpe2.length!=undefined)
		{
	for(var i=0;i<objcpe2.length;i++)
			{
				if(objcpe2[i].checked)
				{
					var str = objcpe2[i].value;
					arraystrp = str.split(":");
					productId = arraystrp[2];
					if((productId == 5))
					{					
					  flag = true;
					  break;
					}
				}
			}
		}		
		if(flag)
		{
			document.getElementById('cpe2').style.display = "block";
			document.getElementById('dummy').style.display = "none";
		}
		else
		{
			document.getElementById('cpe2').style.display = "none";
			document.getElementById('dummy').style.display = "block";
		}
}

function checkAll(objChkAll,chkobj)
{
	var objChk = document.getElementsByName(chkobj);
	if(objChkAll.checked == true)
	{
		if(objChk.length==undefined)
		{
			objChk.checked =true;
		}
		else
		{
			for(i=0;i<objChk.length;i++)
			{
				objChk[i].checked=true;
			}
		}
	}
	else
	{
		if(objChk.length==undefined)
		{
			objChk.checked =false;
		}
		else
		{
			for(i=0;i<objChk.length;i++)
			{
				objChk[i].checked=false;
			}
		}
	}
	checkCpe2();
}

function unCheckAll(chkCpe, chkAllCpe)
{
	var count =0;	
	var objCheckAll = document.getElementsByName(chkCpe);	
	if(!(objCheckAll == undefined))
	{
		for(i=0;i<objCheckAll.length;i++)
		{			
			if(objCheckAll[i].checked == false)
				count++;	
		}		
		
		if(count > 0)
			document.getElementsByName(chkAllCpe)[0].checked = false;	
		else
			document.getElementsByName(chkAllCpe)[0].checked = true;	
	}	
	checkCpe2();
}
var localId = "";
function callExpireDateServlet(pricerunId)
{
		var temp = document.forms[0];
		var timeanddate = new Date();
		var CPEExpDate =  document.getElementById('CPEExpDate').value;
		var CPEStartDate = document.getElementById('CPEStartDate').value;
		var CPEExpDate =  document.getElementById('CPEExpDate').value;
		var ExpTime = document.getElementById('CPEExpiretime').value;
	    var param = 'time='+timeanddate+'&pricerunId='+pricerunId+'&StartDate='+CPEStartDate+'&ExpDate='+CPEExpDate+'&ExpTime='+ExpTime+'&foramt='+document.forms[0].searchTimeZone.value;
	    localId = 'Exp_'+pricerunId; 
	    var url = '<%=request.getContextPath()%>/servlet/contractExpServlet';
		if (window.XMLHttpRequest) // Non-IE browsers
		{
		showload('yes');
			req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse});
		}
		else if (window.ActiveXObject) // IE
		{
			showload('yes');
			req = new ActiveXObject("Microsoft.XMLHTTP");
			if (req)
			{
				req = new Ajax.Request(url,{method: 'get', parameters: param, onComplete: showResponse});
			}
		}
}
function showload(mess)
 {
 	if(mess=='yes')
 		document.getElementById('loadimage').style.display = 'block';
	 else
 		document.getElementById('loadimage').style.display = 'none';
 }
function showResponse(req)
{
	showload('no');
	for(var i = 0;;i++)
	{
		var id = localId+'c'+i;
		var obj = document.getElementById(id);
		if(obj == null)
			break;
		else
		  obj.title = req.responseText 	 
	}
}

function checkNumber(e)
{ 
     var characterCode ;
     e = event
     characterCode = e.keyCode 
     if(characterCode == 13)
     { 
       search();
       return false;
     }
     else
     {
       if(!(event.keyCode>=48 && event.keyCode<=57))
     	{
     		return false
     	}
       return true; 
     }
}

function callSort()
 {
 
	temp=document.forms[0];
	var odr = '<%=order%>';
	if(odr == 'true')
	 {
		temp.sortOrder.value="descending";
		temp.submit();
	}
	else
	{
		temp.sortOrder.value="ascending";
		temp.submit();
	}
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

var oldHlObj="0"; var oldChkObj = "";
function highlight(isChkObj, higlightObj)
{	
	var objNew = ""; var objOld = "";	
	objNew = document.getElementById(higlightObj);	
	var chkObjSize = document.getElementsByName(isChkObj.name);	
	var chkObjSizeCount = "0";
	for(var i=0;i<chkObjSize.length;i++)
	{
		if(chkObjSize[i].checked)
			chkObjSizeCount++;		
	}	
	if(chkObjSizeCount>0)
	{			
		objNew.style.backgroundColor = "#ffffc9";
		if(oldHlObj!="0" && oldHlObj!=higlightObj)
		{			
			objOld = document.getElementById(oldHlObj);
			objOld.style.backgroundColor = "";
			
			var splitChkOldObj = oldChkObj.split("pd_");
			if(splitChkOldObj.length>1)
			{
				var chkAllOldObjSize = document.getElementsByName(oldChkObj);
				for(var i=0;i<chkAllOldObjSize.length;i++)
					chkAllOldObjSize[i].checked = false;

				var chkOldObjSize = document.getElementsByName(splitChkOldObj[1]);
				for(var i=0;i<chkOldObjSize.length;i++)
					chkOldObjSize[i].checked = false;
			}
			else
			{
				var chkOldObjSize = document.getElementsByName(splitChkOldObj[0]);
				for(var i=0;i<chkOldObjSize.length;i++)
					chkOldObjSize[i].checked = false;
				
				var chkAllOldObjSize = document.getElementsByName('pd_'+splitChkOldObj[0]);
				for(var i=0;i<chkAllOldObjSize.length;i++)
					chkAllOldObjSize[i].checked = false;				
			}			
		}	
		oldHlObj = higlightObj;
	}
	else
	{
		objNew.style.backgroundColor = "";		
	}
	oldChkObj = isChkObj.name;
}

</script>
<div id ="loadimage" style="overflow:auto; position:absolute; top:68px; left:891px; display:none; " >
<img src="<%=request.getContextPath()%>/images/preloader_pricing.gif"  align="middle">
</div>
<body onload='loadDefault()'>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/tooltip/wz_tooltip.js"></script>
<html:form action="/frmproposal" focus="txtCustomerName" method="post">
<html:hidden property="formActions" />
<html:hidden property="sortField"  value="<%= frm.getSortField()%>"/>
<html:hidden property="sortOrder" value="<%= frm.getSortOrder()%>"/>
<input type="hidden" name="term"/>
<input type="hidden" name="custId"/>
<input type="hidden" name="price"/>
<input type="hidden" name="cntrStartdate"/>
<input type="hidden" name="expDate"/>
<input type="hidden" name="exptime"/>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td  valign="top"> <jsp:include page="../menu.jsp"/> 
      <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
        <tr> 
          <td width="250" class="page_title">Proposal & Contract</td> 
          <td class="page_title"><table  border="0" align="right" cellpadding="0" cellspacing="0" class="topnav"> </table></td> 
        </tr> 
      </table> 
      <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
        <tr> 
          <td width="100" height="29" class="search">Customer Name</td>                     
          <td width="9" class="search">:</td> 
         <td width="250" class="search">
			  <html:text property="txtCustomerName" styleClass="textbox" size="15" maxlength="150" onkeypress="return checkEnter(event)"  />
              <html:select property="searchCustomerName">
	              <html:option value="startswith">Start With</html:option>
				  <html:option value="endswith">End With</html:option>
				  <html:option value="exactly">Exactly</html:option>
				  <html:option value="anywhere">AnyWhere</html:option>
              </html:select></td> 
          <td width="47" height="29" class="search">Customer&nbsp;Id</td>                     
          <td width="1" class="search">:</td>           
          <td class="search">
			  <html:text property="txtCMSId" styleClass="textbox" size="15" maxlength="10" onkeypress="return checkNumber(event)" /></td> 
        </tr> 
        <tr>
          <td width="100" class="search">Sales Rep</td> 
          <td width="1" class="search">:</td>           
          <td width="200" class="search">
			  <html:text property="txtSalesName" styleClass="textbox" size="15" maxlength="150" onkeypress="return checkEnter(event)"  />
              <html:select property="searchSalesName">
	              <html:option value="startswith">Start With</html:option>
				  <html:option value="endswith">End With</html:option>
				  <html:option value="exactly">Exactly</html:option>
				  <html:option value="anywhere">AnyWhere</html:option>
              </html:select></td> 
          <td height="29" class="search">Run Date </td> 
          <td class="search">:</td> 
          <td colspan="4" class="search"> <html:text property="txtDateFrom" styleClass="textbox" styleId="txtDateFrom" size="10" maxlength="15" readonly="true" /> <a href="#" onClick="showCalendarControl(document.getElementById('txtDateFrom'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" name="imgDateFrom" width="16" height="16" border="0" align="absmiddle" id="imgDateFrom"></a> - <html:text property="txtDateTo" styleClass="textbox" styleId="txtDateTo" size="10" maxlength="15" readonly="true"/> <a href="#" onClick="showCalendarControl(document.getElementById('txtDateTo'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif" name="imgDateTo" width="16" height="16" border="0" align="absmiddle" id="imgDateTo"></a> <html:button property="button" value="Go!" styleClass="button_sub_internal" onclick="search()" /> <html:button property="Clear" value="Clear" styleClass="button_sub_internal" onclick="clearsearch()"/></td> 
        </tr> 
		 <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
      </table> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tr><td class="search">&nbsp;</td><%if(cpeElgible){%><td class="search" width="200" align="right"><strong>Proposal</strong> :</td>
          <td class="search" width="200"> Offer Date :<html:text property="txtCPEStratDate" styleClass="textbox" styleId="CPEStartDate" size="10" maxlength="10" readonly="true" /> <a href="#" onClick="showCalendarControl(document.getElementById('CPEStartDate'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif"  width="16" height="16" border="0" align="absmiddle" id="contStartDate"></a> </td> 
          <td width="350" class="search"> Expire Date : 
				<html:text property="txtCPEExpireDate" styleClass="textbox" styleId="CPEExpDate" size="10" maxlength="15" readonly="true" />
			<a href="#" onClick="showCalendarControl(document.getElementById('CPEExpDate'),'fully')"><img src="<%=request.getContextPath()%>/images/calendar.gif"  width="16" height="16" border="0" align="absmiddle" id="contExpDate"></a>
				<html:text property="txtExpireTime" styleClass="textbox" styleId="CPEExpiretime" size="5" maxlength="5" onkeypress="return checkEntertime(event)" /> Eg. (03:30)
											  <html:select property="searchTimeZone">
                                              <html:option value="0">A.M</html:option>
											  <html:option value="1">P.M</html:option>
                                                </html:select></td> <%}%>			
          <td class="search" width="60"><a href="#" style="color:#0033FF" onClick="showContractResponse('CPE')">Price&nbsp;Quote</a></td>
		   <td class="search" width="60" id="dummy" style='display:block'>&nbsp;</td>
		  <td class="search" width="60" id='cpe2' style='display:none'><a href="#" style="color:#0033FF"onClick="showContractResponse('CPE2')"> Proposal1</a>&nbsp;</td>
        </tr> 
		 
      </table> 
      <div style="height:expression((document.body.clientHeight - <%=browserHt%>) + 'px'); width:100%; overflow:auto;"> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
          <tr class='staticheader'> 
            <td width="3%" height="20" class="cmbheader">&nbsp;</td> 
            <%if(order)
            {%>
			<td width="35%" class="tblheader" align="center" title='Sort by CustomerName in Ascending' style="cursor:hand" onClick="callSort();">Customer&nbsp;Name <img src='<%=request.getContextPath()%>/images/sort.gif' width="7" height="8"></td>
            <%}else{%>
			<td width="35%" class="tblheader" align="center" title='Sort by CustomerName in Decending' style="cursor:hand" onClick="callSort();">Customer&nbsp;Name <img src='<%=request.getContextPath()%>/images/sort_up.gif' width="7" height="8"></td>
			<%}%>
			<td width="12%" class="tblheader" align="center">Customer&nbsp;Id</td> 
            <td width="33%" class="tblheader" align="center">Sales&nbsp;Rep</td> 
            <td width="20%" class="tblheader" align='center'>Run Date</td>
            <%if(cpeElgible){%>
			<td class="tblheader" align='left' title="Apply Expire Date">AED</td>
			<%}%>
          </tr> 
          <%
             int i = 0;
          %> 
          <logic:iterate id="Custname" name="lstCustname"> 
          <%
          // int custRefId =  ((Integer)lstCustname.get(i)).intValue();
            int custRefId = ((PriceRunCustomerVO)lstCustname.get(i)).getPriceRunCustomerRefId();
           objPriceRunCustomerVO = objPriceRunCustomerDAO.getPriceRunCustomer(custRefId);
           HashMap hmProduct = null;
           //(HashMap)hmCustomer.get(lstCustname.get(i));
           hmProduct = objContractsDAO.getContractsByProduct(((PriceRunCustomerVO)lstCustname.get(i)).getCustomerContracts());
           List lstCust = new java.util.ArrayList();
           lstCust = (List)hmProduct.get(new Integer(1));
           List lstFAR = null;
           lstFAR = (List)hmProduct.get(new Integer(5));
           List lstbundle = null;
           List lstunbundle = null;
           List lstblock = null;
           List lstblockMCPE = null;
           List lstmcpe = null;
           List lstres = null;
           lstbundle = (List)hmProduct.get(new Integer(7));
           lstunbundle = (List)hmProduct.get(new Integer(8));
           lstblock = (List)hmProduct.get(new Integer(3));
           lstblockMCPE = (List)hmProduct.get(new Integer(4));
           lstmcpe = (List)hmProduct.get(new Integer(2));
           lstres = (List)hmProduct.get(new Integer(6));
        /*   System.out.println("lstFAR :" +lstFAR );
           System.out.println("lstCust :" +lstCust );
           System.out.println("lstbundle :" +lstbundle );
           System.out.println("lstunbundle :" +lstunbundle );
           System.out.println("lstblock :" +lstblock );
           System.out.println("lstblockMCPE :" +lstblockMCPE );
           System.out.println("lstmcpe :" +lstmcpe );
           System.out.println("lstres :" +lstres ); */
           pageContext.setAttribute("Custname",objPriceRunCustomerVO);
           String prosCustName = objPriceRunCustomerVO.getProspectiveCustomer().getCustomerName();
           String dba = objPriceRunCustomerVO.getProspectiveCustomer().getCustomerDBA()==null?"":objPriceRunCustomerVO.getProspectiveCustomer().getCustomerDBA();
           prosCustName = prosCustName.replaceAll("'","\\\\'");
           prosCustName = prosCustName.replaceAll("\"","");
           dba = dba.replaceAll("'","\\\\'");
           dba = dba.replaceAll("\"","");
           String tootipCustName = prosCustName;
        %> 
          <tr id='hl_<bean:write name="Custname" property="priceRunCustomerRefId" ignore="true"/>'> 
            <td width="3%" align="center" valign="middle" class="tbldata"> <a href="#"onClick="javascript:showHide('CusExp+<%=i%>','CusExp+<%=i%>','img+<%=i%>','<%=objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId().intValue()%>')"> <img src="<%=request.getContextPath()%>/images/close.gif" width="11" height="11" border="0"  id="img+<%=i%>"> </a> </td> 
            <%
            	if(prosCustName.length()>50)
            	{
           			prosCustName = prosCustName.substring(0,48)+"...";
            %>
            		<td width="35%" class="tbldata" onmouseover="constructToolTip('<%=tootipCustName%>','<%=dba%>')"><a href="<%=request.getContextPath()%>/jsp/pricing/History.jsp?custId=<%=objPriceRunCustomerVO.getProspectiveCustomer().getProspectiveCustomerId()%>"><%=prosCustName%>&nbsp;</a></td> 
            <%
            	}
            	else
            	{
            %>
					<td width="35%" class="tbldata" onmouseover="constructToolTip('<%=tootipCustName%>','<%=dba%>')"><a href="<%=request.getContextPath()%>/jsp/pricing/History.jsp?custId=<%=objPriceRunCustomerVO.getProspectiveCustomer().getProspectiveCustomerId()%>"><bean:write name="Custname" property="prospectiveCustomer.customerName" ignore="true"/></a>&nbsp;</td> 
			<%
            	}
            %>
			<td width="12%" align = 'right' class="tbldata"><bean:write name="Custname" property="prospectiveCustomer.customerId" ignore="true"/>&nbsp;</td> 
        <td width="33%" class="tbldata"><bean:write name="Custname" property="prospectiveCustomer.salesRep.firstName" ignore="true"/>&nbsp;</td> 
            <td width="20%" class="tbldata" align='right'><bean:write name="Custname" property="priceRunRef.priceRunTime" ignore="true" format="MM-dd-yyyy HH:mm:ss"/></td> 
            <%if(cpeElgible){%>
            <td class="tbldata" align='left'><a title='Apply Expire Date' href="javaScript:callExpireDateServlet('<bean:write name="Custname" property="priceRunCustomerRefId" ignore="true"/>')">AED</a></td>
            <%}%>
          </tr> 
          <tr id="CusExp+<%=i%>" style="display:none" > 
            <td  class="tbldata" colspan="6"><table width="100%" border="0" cellpadding="0" cellspacing="0"> 
                <tr> 
                  <td width="30">&nbsp;</td> 
                  <td valign="top" > <table width="100%" border="0" cellpadding="0" cellspacing="0"> 
                      
                      <%if(lstCust.size()>0){%> 
                       <tr> 
                        <td height="106"> <fieldset style="color:#993333  "> 
                        <%if((((ContractsVO)lstCust.get(0)).getExpDate().compareTo(new Date())==1)||cpeElgible){%>
                          <legend>Proposal Details - <%=((ContractsVO)lstCust.get(0)).getProduct().getProductName()%></legend> 
                          <%}else {
                          %>
                          <legend>Proposal Details - <%=((ContractsVO)lstCust.get(0)).getProduct().getProductName()%></legend> 
							<%}%> 
                          <table width="100%" border="0" cellpadding="0" cellspacing="0"> 
                            <tr class="tblheader"> 
                              <td width="20" height="20" class="tbldata"><input type="checkbox" name="pd_<%=((ContractsVO)lstCust.get(0)).getPriceRunCustomerRef().getPriceRunCustomerRefId()%>_<%=((ContractsVO)lstCust.get(0)).getProduct().getProductName()%>" onclick="checkAll(this,'<%=((ContractsVO)lstCust.get(0)).getPriceRunCustomerRef().getPriceRunCustomerRefId()%>_<%=((ContractsVO)lstCust.get(0)).getProduct().getProductName()%>')"></td> 
                              <td width="45" class="tbldata">Term</td> 
                              <td width="95" class="tbldata">Contract&nbsp;kWh </td> 
                              <td width="80" class="tbldata">Price&nbsp;$/kWh </td> 
                              <td width="80" class="tbldata">Sales&nbsp;Comm.</td> 
                              <td width="70" class="tbldata">Agg.&nbsp;Fee </td> 
                              <td width="127" class="tbldata">Comp.&nbsp;Price&nbsp;$/kWh</td> 
                              <td width="58" class="tbldata">Savings</td> 
                              <td width="76" class="tbldata"> %&nbsp;Savings </td> 
                              <td class="tbldata"> Contracts</td> 
                            </tr> 
                            <%for(int count = 0;count<lstCust.size();count++)
                            {
							
                            %> 
                            <tr id="Exp_<%=((ContractsVO)lstCust.get(count)).getPriceRunCustomerRef().getPriceRunCustomerRefId()%>c<%=count%>" title="<%="Expire Date :"+dfTime.format(((ContractsVO)lstCust.get(count)).getExpDate())%>"> 
                              <td class="tbldata"><input type="checkbox" id ='checkValue' name="<%=((ContractsVO)lstCust.get(count)).getPriceRunCustomerRef().getPriceRunCustomerRefId()%>_<%=((ContractsVO)lstCust.get(0)).getProduct().getProductName()%>" value='<%=((ContractsVO)lstCust.get(count)).getTerm()%>:<%=custRefId%>:<%=((ContractsVO)lstCust.get(count)).getProduct().getProductIdentifier()%>:<%=((ContractsVO)lstCust.get(count)).getEsiIds()%>' onclick="unCheckAll('<%=((ContractsVO)lstCust.get(count)).getPriceRunCustomerRef().getPriceRunCustomerRefId()%>_<%=((ContractsVO)lstCust.get(0)).getProduct().getProductName()%>','pd_<%=((ContractsVO)lstCust.get(count)).getPriceRunCustomerRef().getPriceRunCustomerRefId()%>_<%=((ContractsVO)lstCust.get(0)).getProduct().getProductName()%>')"></td> 
                              <td class="tbldata" align='right'><%=((ContractsVO)lstCust.get(count)).getTerm()%></td> 
                              <td class="tbldata" align='right'><%=nf.format(((ContractsVO)lstCust.get(count)).getContractkWh())%></td> 
                              <td class="tbldata" align='right'><%=tnf.format(((ContractsVO)lstCust.get(count)).getFixedPrice$PerMWh()/1000)%></td> 
                              <td class="tbldata" align='right'><%=dnf.format((((ContractsVO)lstCust.get(count)).getSalesCommision()))%></td> 
                              <td class="tbldata" align='right'><%=dnf.format((((ContractsVO)lstCust.get(count)).getAggregatorFee()))%></td> 
                              <td class="tbldata" align='right'><%=objPriceRunCustomerVO.getProspectiveCustomer().getCompetitorPrice()%></td> 
                              <%
                              float fixedprice = ((ContractsVO)lstCust.get(count)).getFixedPrice$PerMWh()/1000;
                              float competitorprice = objPriceRunCustomerVO.getProspectiveCustomer().getCompetitorPrice();
                              float contractkwh = ((ContractsVO)lstCust.get(count)).getContractkWh();
                              %> 
                              <td class="tbldata" align='right'><%=(competitorprice!=0?dnf.format((competitorprice-fixedprice)*contractkwh):"-")%></td> 
                              <td class="tbldata" align='right'><%=(competitorprice!=0?dnf.format(((competitorprice-fixedprice)*100)/competitorprice):"-")%></td> 
                              <%if((((ContractsVO)lstCust.get(0)).getExpDate().compareTo(new Date())==1)||cpeElgible){%>
	                          <td class="tbldata" align='left'><a href="#" onClick="createContract(<%=((ContractsVO)lstCust.get(count)).getContractIdentifier()%>,'<%=((ContractsVO)lstCust.get(0)).getProduct().getProductIdentifier()%>','<%=objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId()==null?0:objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId().intValue()%>','<%=prosCustName%>','A')">Contract <50 kW </a> / <a href="#" onClick="createContract(<%=((ContractsVO)lstCust.get(count)).getContractIdentifier()%>,'<%=((ContractsVO)lstCust.get(0)).getProduct().getProductIdentifier()%>','<%=objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId()==null?0:objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId().intValue()%>','<%=prosCustName%>','B')">Contract >50 kW</a></td> 
	                         <%}else {
                         	 %>
							<td class="tbldata" align='left'>Contract Expired</td> 
							<%}%> 
                              
                            </tr> 
                            <%}
                           pageContext.removeAttribute("Custname");
                           %> 
                          </table> 
                          </fieldset></td> 
                      </tr> 
                      <%}%> 
                     
                      <%if(lstmcpe.size()>0){%> 
                      <tr> 
                        <td height="106"> <fieldset style="color:#993333  "> 
                        <%if((((ContractsVO)lstmcpe.get(0)).getExpDate().compareTo(new Date())==1)||cpeElgible){%>
                          	<legend>Proposal Details - <%=((ContractsVO)lstmcpe.get(0)).getProduct().getProductName()%></legend> 
                          	<%}else {
                          	%>
                          <legend>Proposal Details - <%=((ContractsVO)lstmcpe.get(0)).getProduct().getProductName()%></legend> 
						 <%}%> 
                          
                          <table width="100%" border="0" cellpadding="0" cellspacing="0"> 
                            <tr class="tblheader"> 
                              <td width="20" height="20" class="tbldata"><input name="pd_<%=((ContractsVO)lstmcpe.get(0)).getPriceRunCustomerRef().getPriceRunCustomerRefId()%>_<%=((ContractsVO)lstmcpe.get(0)).getProduct().getProductName()%>" type="checkbox" onclick="checkAll(this,'<%=((ContractsVO)lstmcpe.get(0)).getPriceRunCustomerRef().getPriceRunCustomerRefId()%>_<%=((ContractsVO)lstmcpe.get(0)).getProduct().getProductName()%>');"></td>
                              <td width="45" class="tbldata">Term</td> 
                              <td width="95" class="tbldata">Contract&nbsp;kWh </td> 
                              <td width="80" class="tbldata">Price&nbsp;$/kWh </td> 
                              <td width="80" class="tbldata">Sales&nbsp;Comm.</td> 
                              <td width="70" class="tbldata">Agg.&nbsp;Fee </td> 
                              <td width="127" class="tbldata">Comp.&nbsp;Price&nbsp;$/kWh</td> 
                              <td width="58" class="tbldata">Savings</td> 
                              <td width="80" class="tbldata"> %&nbsp;Savings </td> 
                              <td class="tbldata"> Contracts</td> 
                            </tr> 
                            <%for(int count = 0;count<lstmcpe.size();count++)
                            {
                             priceRunRefId = ((ContractsVO)lstmcpe.get(count)).getPriceRunCustomerRef().getPriceRunCustomerRefId();
                             esiids = ((ContractsVO)lstmcpe.get(count)).getEsiIds();
                             term = ((ContractsVO)lstmcpe.get(count)).getTerm();
  						     objPricingDashBoard = objPricingDAO.getDashBoardDetails(priceRunRefId,term,esiids);
							 diff = objPricingDashBoard.getEnergyDiff()/objPricingDashBoard.getContractkWh();
							 // (objPricingDashBoard.getEnergyCharge()-objPricingDashBoard.getEnergyChargeWithOutLoss())/objPricingDashBoard.getContractkWh();
                            %> 
                            <tr id="Exp_<%=((ContractsVO)lstmcpe.get(count)).getPriceRunCustomerRef().getPriceRunCustomerRefId()%>c<%=count%>" title='<%="Expire Date :"+dfTime.format(((ContractsVO)lstmcpe.get(count)).getExpDate())%>'> 
                              <td class="tbldata"><input id ='checkValue' name="<%=((ContractsVO)lstmcpe.get(count)).getPriceRunCustomerRef().getPriceRunCustomerRefId()%>_<%=((ContractsVO)lstmcpe.get(0)).getProduct().getProductName()%>" type="checkbox" value='<%=((ContractsVO)lstmcpe.get(count)).getTerm()%>:<%=custRefId%>:<%=((ContractsVO)lstmcpe.get(count)).getProduct().getProductIdentifier()%>:<%=((ContractsVO)lstmcpe.get(count)).getEsiIds()%>' onclick="unCheckAll('<%=((ContractsVO)lstmcpe.get(count)).getPriceRunCustomerRef().getPriceRunCustomerRefId()%>_<%=((ContractsVO)lstmcpe.get(0)).getProduct().getProductName()%>','pd_<%=((ContractsVO)lstmcpe.get(count)).getPriceRunCustomerRef().getPriceRunCustomerRefId()%>_<%=((ContractsVO)lstmcpe.get(0)).getProduct().getProductName()%>')"></td> 
                              <td class="tbldata" align='right'><%=((ContractsVO)lstmcpe.get(count)).getTerm()%></td> 
                              <td class="tbldata" align='right'><%=nf.format(((ContractsVO)lstmcpe.get(count)).getContractkWh())%></td> 
                              <td class="tbldata" align='right'><%=tnf.format(diff+((ContractsVO)lstmcpe.get(count)).getMcpeAdder())%></td> 
                              <td class="tbldata" align='right'><%=dnf.format((((ContractsVO)lstmcpe.get(count)).getSalesCommision()))%></td> 
                              <td class="tbldata" align='right'><%=dnf.format((((ContractsVO)lstmcpe.get(count)).getAggregatorFee()))%></td> 
                              <td class="tbldata" align='right'><%=objPriceRunCustomerVO.getProspectiveCustomer().getCompetitorPrice()%></td> 
                              <%
                              float fixedprice = ((ContractsVO)lstmcpe.get(count)).getMcpeAdder()+diff;
                              float competitorprice = objPriceRunCustomerVO.getProspectiveCustomer().getCompetitorPrice();
                              float contractkwh = ((ContractsVO)lstmcpe.get(count)).getContractkWh();
                              %> 
                              <td class="tbldata" align='right'><%=(competitorprice!=0?dnf.format((competitorprice-fixedprice)*contractkwh):"-")%></td> 
                              <td class="tbldata" align='right'><%=(competitorprice!=0?dnf.format(((competitorprice-fixedprice)*100)/competitorprice):"-")%></td> 
                              <%if((((ContractsVO)lstmcpe.get(0)).getExpDate().compareTo(new Date())==1)||cpeElgible){%>
                          		<td class="tbldata" align='left'><a href="#" onClick="createContract(<%=((ContractsVO)lstmcpe.get(count)).getContractIdentifier()%>,'<%=((ContractsVO)lstmcpe.get(0)).getProduct().getProductIdentifier()%>','<%=objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId()==null?0:objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId().intValue()%>','<%=prosCustName%>','A','<%=tnf.format(diff+((ContractsVO)lstmcpe.get(count)).getMcpeAdder())%>')">Contract <50 kW </a> / <a href="#" onClick="createContract(<%=((ContractsVO)lstmcpe.get(count)).getContractIdentifier()%>,'<%=((ContractsVO)lstmcpe.get(0)).getProduct().getProductIdentifier()%>','<%=objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId()==null?0:objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId().intValue()%>','<%=prosCustName%>','B','<%=tnf.format(diff+((ContractsVO)lstmcpe.get(count)).getMcpeAdder())%>')">Contract >50 kW</a></td> 
                          	   <%}else {
                          	   %>
        						<td class="tbldata" align='left'>Contract Expired</td> 
						     <%}%> 
                              
                            </tr> 
                            <%}
                           pageContext.removeAttribute("Custname");
                           %> 
                          </table> 
                          </fieldset></td> 
                      </tr> 
                      <%}%> 
                      <%if(lstres.size()>0){%> 
                      <tr> 
                        <td height="106"> <fieldset style="color:#993333  "> 
                        <%if((((ContractsVO)lstres.get(0)).getExpDate().compareTo(new Date())==1)||cpeElgible){%>
                          		<legend>Proposal Details - <%=((ContractsVO)lstres.get(0)).getProduct().getProductName()%></legend> 
                          	   <%}else {
                          	   %>
                          <legend>Proposal Details - <%=((ContractsVO)lstres.get(0)).getProduct().getProductName()%></legend> 
						     <%}%> 
                          <table width="100%" border="0" cellpadding="0" cellspacing="0"> 
                            <tr class="tblheader"> 
                              <td width="20" height="20" class="tbldata"><input type="checkbox" disabled /></td> 
                              <td width="45" class="tbldata">Term</td> 
                              <td width="95" class="tbldata">Contract&nbsp;kWh </td> 
                              <td width="80" class="tbldata">Price&nbsp;$/kWh </td> 
                              <td width="80" class="tbldata">Sales&nbsp;Comm.</td> 
                              <td width="70" class="tbldata">Agg.&nbsp;Fee </td> 
                              <td width="127" class="tbldata">Comp.&nbsp;Price&nbsp;$/kWh</td> 
                              <td width="58" class="tbldata">Savings</td> 
                              <td width="80" class="tbldata"> %&nbsp;Savings </td> 
                              <td class="tbldata"> Contracts</td> 
                            </tr> 
                            <%for(int count = 0;count<lstres.size();count++)
                            {
                            %> 
                            <tr id="Exp_<%=((ContractsVO)lstres.get(count)).getPriceRunCustomerRef().getPriceRunCustomerRefId()%>c<%=count%>" title='<%="Expire Date :"+dfTime.format(((ContractsVO)lstres.get(count)).getExpDate())%>'> 
                              <td class="tbldata"><input type="checkbox" disabled /></td> 
                              <td class="tbldata" align='right'><%=((ContractsVO)lstres.get(count)).getTerm()%></td> 
                              <td class="tbldata" align='right'><%=nf.format(((ContractsVO)lstres.get(count)).getContractkWh())%></td> 
                              <td class="tbldata" align='right'><%=tnf.format(((ContractsVO)lstres.get(count)).getFixedPrice$PerMWh()/1000)%></td> 
                              <td class="tbldata" align='right'><%=dnf.format((((ContractsVO)lstres.get(count)).getSalesCommision()))%></td> 
                              <td class="tbldata" align='right'><%=dnf.format((((ContractsVO)lstres.get(count)).getAggregatorFee()))%></td> 
                              <td class="tbldata" align='right'><%=objPriceRunCustomerVO.getProspectiveCustomer().getCompetitorPrice()%></td> 
                              <%
                              float fixedprice = ((ContractsVO)lstres.get(count)).getFixedPrice$PerMWh()/1000;
                              float competitorprice = objPriceRunCustomerVO.getProspectiveCustomer().getCompetitorPrice();
                              float contractkwh = ((ContractsVO)lstres.get(count)).getContractkWh();
                              %> 
                              <td class="tbldata" align='right'><%=(competitorprice!=0?dnf.format((competitorprice-fixedprice)*contractkwh):"-")%></td> 
                              <td class="tbldata" align='right'><%=(competitorprice!=0?dnf.format(((competitorprice-fixedprice)*100)/competitorprice):"-")%></td> 
                              <%if((((ContractsVO)lstres.get(0)).getExpDate().compareTo(new Date())==1)||cpeElgible){%>
                          		<td class="tbldata" align='left'><a href="#" onClick="createContract(<%=((ContractsVO)lstres.get(count)).getContractIdentifier()%>,'<%=((ContractsVO)lstres.get(0)).getProduct().getProductIdentifier()%>','<%=objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId()==null?0:objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId().intValue()%>','<%=prosCustName%>','A')">Contract <50 kW </a> / <a href="#" onClick="createContract(<%=((ContractsVO)lstres.get(count)).getContractIdentifier()%>,'<%=((ContractsVO)lstres.get(0)).getProduct().getProductIdentifier()%>','<%=objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId()==null?0:objPriceRunCustomerVO.getProspectiveCustomer().getCustomerId().intValue()%>','<%=prosCustName%>','B')">ContracBt</a></td> 
                          	   <%}else {
                          	   %>
        						<td class="tbldata" align='left'>Contract Expired</td> 
						     <%}%> 
                              
                            </tr> 
                            <%}
                           pageContext.removeAttribute("Custname");
                           %> 
                          </table> 
                          </fieldset></td> 
                      </tr> 
                      <%}%> 
                    </table></td>
                </tr> 
              </table></td> 
          </tr> 
          <%
           i++;
           %> 
          </logic:iterate> 
		<%
			if(lstCustname.size() == 0 )
			{
		%> 
          <tr>
		 		<td colspan='10'>
				 	<jsp:include page="/jsp/noRecordsFound.jsp"/>
				</td>
			</tr>
		<%
		  }
		%>   
		
        </table> 
      </div>
       <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="sort"> 
        <tr> 
          <td width="100">Page <%=pageCount%> of <%=totalPages%></td> 
          <td width="150">Items <%=totalCount>0?(pageCount*maxItems)-maxItems+1:0%> - <%=((maxItems*pageCount)>totalCount)?totalCount:(maxItems*pageCount)%> of <%=totalCount%></td> 
          <td>Show <html:radio property="maxItems" value="10" onclick="changePage()"/> 10 <html:radio property="maxItems" value="20" onclick="changePage()"/> 20 <html:radio property="maxItems" value="50" onclick="changePage()"/> 50 <html:radio property="maxItems" value="100" onclick="changePage()"/> 100 Items/Page </td> 
          <td width="180" class="nav_page_right"> <%
		    if(Integer.parseInt(frm.getPage())>1)
			{%> 
            <a href="#" style="color:blue" onclick="pageDecrement()" ><img src='<%=request.getContextPath()%>/images/previous.gif' align="bottom" alt="Previous" border="0"> Previous</a> 
            <%}%> 
            Goto <html:select property="page" onchange="changePageGoto()"> 
            <%for(int s=0;s<totalPages;s++){%> 
            <option value="<%=(s+1)%>"><%=(s+1)%></option> 
            <%}%> 
            </html:select> 
            <%if((Integer.parseInt(frm.getPage())>1) && (Integer.parseInt(frm.getPage())<totalPages))
							    {%> 
            <%}if(Integer.parseInt(frm.getPage())<totalPages)
							    {%> 
            <a href="#" style="color:blue" onclick="pageIncrement()">Next <img src='<%=request.getContextPath()%>/images/next.gif' align="bottom" alt="Next" border="0"></a> 
            <%}%> </td> 
        </tr> 
      </table>
      </td> 
  </tr> 
  <tr> 
    <td height="20" valign="bottom"><table width="100%"  border="0" cellpadding="0" cellspacing="0" > 
        <tr> 
        	<%String menupath = session.getAttribute("home")!=null?(String)session.getAttribute("home"):" ";%>
          <td class="copyright_link" id='foterId'><%=menupath%></td>
          <td width="250"   class="copyright" align='right'>Design Rights Savant Technologies </td> 
        </tr> 
      </table></td> 
  </tr> 
</table> 
</html:form> 
</body>
<%
}
catch (Exception e) {
e.printStackTrace();
}
%>
</html:html>
<%}%>