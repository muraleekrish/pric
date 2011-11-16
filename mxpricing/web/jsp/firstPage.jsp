<%
String pageForward="";
System.out.println("SESSION ATTRIBUTE:::::::"+session.getAttribute("firstPage"));
if(session.getAttribute("firstPage")!=null){
pageForward=((String)session.getAttribute("contextName")+(String)session.getAttribute("firstPage")).replaceAll("\\\\","/");
System.out.println("pageForward:"+pageForward);
}
else
pageForward=(String)session.getAttribute("contextName")+"/jsp/norole.jsp";

%>

<html>
<head>
<title>Customer Pricing</title>
</head>
<head><frameset rows="*" cols="*" framespacing="0" frameborder="no" border="0">
  <frame src='<%=pageForward%>' noresize="noresize" scrolling="no" >
</frameset>
<noframes></noframes>
<noframes></noframes>
</html>