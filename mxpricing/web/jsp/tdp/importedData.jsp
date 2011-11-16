<html>
<head>
<title>TDP</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/common.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/winter.css"/>
<script src='<%=request.getContextPath()%>/script/prototype.js' language="javascript"></script>
<script>
function showResponse1()
{
	window.parent.close();
var status='<%=session.getAttribute("ImportStatus")%>';

if(status == "Import successfully completed.")
	status="yes";
else
	status="no";
window.opener.location.href="<%=request.getContextPath()%>/jsp/tdp/tdp.jsp?submitstatus="+status;

window.close();
}
</script>
</head>
<body onload="showResponse1();"> 
</body>
</html>
