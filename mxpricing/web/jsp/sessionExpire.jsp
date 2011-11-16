<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html:html>
<body>
<table>
<td align="center">
<bean:message key="common.error.warning"/>
<bean:message key="common.error.nosession"/>
<td>
</body>
<script>
setTimeout("delay()",2000);
function delay()
{
	 window.open("<%=request.getContextPath()%>/jsp/index.jsp","_top")
}
</script>
</html:html>
