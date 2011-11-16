<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html:html>
<head>
<title>Customer Pricing </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=request.getContextPath()%>/script/login.js"></script>
<style>

.login_palette {
	background-image:url(images/Login-Palette.gif);
	background-repeat:no-repeat;
	width:400px;
	height:200px;
	padding:20px 20px 10px 20px;
	vertical-align:middle;
}

.data {
	color:#FFFFFF;
	font:"trebuchet MS";
	font-size:10px;
}

.form_login_link {
	color:#0000f;
	text-align:right;
}

.form_login_link a:link { color:#0000ff; text-decoration:none; }
.form_login_link a:visited { color:#0000ff; text-decoration:none; } 
.form_login_link a:hover { color:red; text-decoration:none; }

.form_label {
	font-family:"trebuchet MS";
	font-size:11px;
	font-weight:bold;
	text-align:right;
	padding:3px 5px 3px 2px;
}

input {
	font-family:"trebuchet MS";
	font-size:11px;
}

.capslock {
	background-color:#FFFCCC;
	font-size:11px;
	cursor:default;
}

</style>
<script>
function callLogin()
{
	temp = document.forms[0];
	temp.formAction.value = "login";
}
function click() {
	if ((event.button==2) || (event.button==3)) {
		alert("No way");
	}
}
//document.onmousedown=click;
function chkClick() {
	alert();
}
function antiLock(e) {
	var whichKey=0;
	var gotShift=false;
	var myMsg='Caps Lock is On.\n\nHaving Caps Lock on may cause you to enter your password incorrectly.\n\nYou should press Caps Lock to turn it off before entering your password.';
	if(document.all) {
		whichKey=e.keyCode;
		gotShift=e.shiftKey;
	}
	else if(document.layers) {
		whichKey=e.which;
		gotShift=(whichKey == 16) ? true : false;
	}
	else if (document.getElementById) {
		whichKey=e.which;
		gotShift=(whichKey == 16) ? true : false;
	}
	// Upper case letters are seen without depressing the Shift key, therefore Caps Lock is on
	if((whichKey >= 65 && whichKey <= 90) && !gotShift) {
		document.getElementById('dvCapsMsg').style.display = 'block';
		// Lower case letters are seen while depressing the Shift key, therefore Caps Lock is on
	} 
	else if ((whichKey >= 97 && whichKey <= 122) && gotShift) {
		document.getElementById('dvCapsMsg').style.display = 'block';
	}
	setTimeout('document.getElementById("dvCapsMsg").style.display = "none"',5000);
}

function chkkey() {
	alert(event.keyCode);
}

function getBrowserHeight()
{
	temp = document.forms[0];
	temp.browserHeight.value = document.body.clientHeight;
	temp.browserWidth.value = document.body.clientWidth;
	//temp.browserHeight.value = screen.availHeight;
	//temp.browserWidth.value = screen.availWidth;
}
function sendPassword()
{
	var temp = document.forms[0];
	temp.formAction.value = "sendmail";
	temp.submit();
}

</script>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->
</script>
</head>
<body onload='getBrowserHeight();'> 
<html:form action="frmSeurityLogin" focus="userId">
<html:hidden property="formAction" />
<input type='hidden' name='browserHeight' />
<input type='hidden' name='browserWidth' />

<div id="dvCapsMsg" style="position:absolute;left:650px; top:230px; width:269px; height:106px; z-index:1; display:none" onClick="this.style.display='none'"> 
  <table width="100%"  border="0" cellspacing="0" cellpadding="0"> 
    <tr> 
      <td width="23"><img src="<%=request.getContextPath()%>/images/caps_topleft.gif" width="23" height="23"></td> 
      <td background="<%=request.getContextPath()%>/images/caps_topborder.gif"><img src="<%=request.getContextPath()%>/images/caps_pointer.gif" width="45" height="23"></td> 
      <td width="23"><img src="<%=request.getContextPath()%>/images/caps_topright.gif" width="23" height="23"></td> 
    </tr> 
    <tr> 
      <td background="<%=request.getContextPath()%>/images/caps_leftborder.gif"><img src="<%=request.getContextPath()%>/images/caps_leftborder.gif" width="23" height="57"></td> 
      <td class="capslock"><b><img src="<%=request.getContextPath()%>/images/info_icon.gif" align="absmiddle"> Caps Lock is On</b><br> 
        <br> 
        Having Caps Lock on may cause you to enter your password Incorrectly.<br> 
        <br> 
        You should press Caps Lock to turn it off before entering your password. </td> 
      <td background="<%=request.getContextPath()%>/images/caps_rightborder.gif"><img src="<%=request.getContextPath()%>/images/caps_rightborder.gif" width="23" height="57"></td> 
    </tr> 
    <tr> 
      <td><img src="<%=request.getContextPath()%>/images/caps_botleft.gif" width="23" height="20"></td> 
      <td background="<%=request.getContextPath()%>/images/caps_botborder.gif">&nbsp;</td> 
      <td><img src="<%=request.getContextPath()%>/images/caps_botright.gif" width="23" height="20"></td> 
    </tr> 
  </table> 
</div> 
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0"> 
  <tr> 
    <td align="center"> <table border="0" cellpadding="0" cellspacing="0" class="login_panel"> 
        <tr> 
          <td width="150" align="center" valign='bottom'>&nbsp;</td> 
          <td class="login_form_panel"> <table width="100%"  border="0" cellpadding="0" cellspacing="0"> 
              <tr> 
                <td width="75" class="form_login_title">User ID</td> 
                <td width="1" class="form_login_title">:</td> 
                <td class="form_login_field"><html:text property="userId" styleClass="login_tbox" onfocus="true" size="30"/></td> 
              </tr> 
              <tr> 
                <td class="form_login_title">Password</td> 
                <td class="form_login_title">:</td> 
                <td class="form_login_field"><html:password property="password" styleClass="login_tbox" size="30" redisplay="false" onkeypress="antiLock(event)"/></td> 
              </tr> 
              <tr> 
                <td colspan="2" class="logfields">&nbsp;</td>
                <td style="padding:1px 3px 1px 1px;"><html:submit styleClass="login_button" value="Submit" onclick="callLogin()"/> 
               	</td>
              </tr> 
            </table></td> 
        </tr> 
		<tr style="padding:0px 5px">
		  <td  colspan="2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  <tr>
			    <td class="form_login_link"><a href='javascript:sendPassword()'>Forgot Password?</a></td> 
			 </tr> 
            </table>
	      </td>
	   </tr>
      </table>
     
	<table width="100%" height='50' border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="<%=request.getContextPath()%>/images/spacer.gif" width="1" height="5"></td>
          </tr>
     </table>
	<table><tr><td>
		<span class='error'><html:errors/></span>
		<span class='message'><html:messages id="messageid" message="true"><bean:write name="messageid" /></html:messages></span>
	</td></tr></table>
	</td>
  </tr> 
</table> 
</html:form>
</body>
</html:html>
	