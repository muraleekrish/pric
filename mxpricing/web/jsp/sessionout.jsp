<html>
  <head>
    <title>Session Expired</title>
    <link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
    <link href="<%=request.getContextPath()%>/styles/Styles.css" rel="stylesheet" type="text/css">
  </head>
  <body>
  <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
	  <tr>
		  <td valign='top'>
		    <table border="0" cellpadding="0" cellspacing="0" width=50% align="center" style='margin:50px;'>
		        <tr>
		            <td background="<%=request.getContextPath()%>/images/banner_bg.gif" style="list-style-type:circle; border: solid 1px #cccccc; text-align:center; font-size:18px; font-weight:bold; color:#F95D00;" >Session Expired!</td>
		        </tr>
		        <tr>
		            <td class='session_timeout' style='padding-top:10px; font-size:12px; text-align:left;'>Sorry! your session time has expired. Please re-login.</td>
		        </tr>
		        <tr>
		            <td class='session_timeout_link'><a href='<%=request.getContextPath()%>' >Login to Pricing</a></td>
		        </tr>
		        <tr>
		            <td class='session_timeout' style='padding-top:10px; font-size:10px;'>Note: If the problem persists, please clear the browser cache and reload the page.</td>
		        </tr>
		    </table>
		  </td>
	  </tr>	  
  </table>
  </body>
</html>
