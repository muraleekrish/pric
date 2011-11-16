<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>Customer Pricing</title>
</head>
<script>
function dologin()
{
	window.open("<%=request.getContextPath()%>/jsp/index.jsp","_top");	}
</script>
<%

	session.invalidate();
	
	request.getSession(true);
%>
<body onload="dologin();">
</body>
</html>
