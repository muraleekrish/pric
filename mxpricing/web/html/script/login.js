function chklogin() {
	if(document.forms[0].txtUserId.value != 'pricing' && document.forms[0].txtUserPwd.value != 'savant5707') {
		alert('Incorrect Login');
		document.forms[0].reset();
		document.forms[0].txtUserId.focus();
		return false;
	}
	if(document.forms[0].txtUserId.value != 'pricing') {
		alert('Incorrect User ID');
		document.forms[0].reset();
		document.forms[0].txtUserId.focus();
		return false;
	}
	else if(document.forms[0].txtUserPwd.value != 'savant5707') {
		alert('Incorrect Password');
		document.forms[0].txtUserPwd.value='';
		document.forms[0].txtUserPwd.focus();
		return false;
	}
	else
		return true;
}
