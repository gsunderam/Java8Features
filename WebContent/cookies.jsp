<html>
<head>
	<title>Cookies</title>
	
<script>
	function displayCookies() {
		var cookies = document.cookie
		document.getElementById('cookies').innerHTML = cookies + '\n'
		var el = document.getElementById('cookieDetail')
		var detailStr = ""
		
		var cookiesMap = {}
		
		var cookiesArr = cookies.split(";")
		for (var i = 0; i < cookiesArr.length; i++) {
			var cookieVal = cookiesArr[i].split('=')
			cookiesMap[cookieVal[0]] = cookieVal[1]
		}
		
		for (name in cookiesMap) detailStr += "key : " + name + " value : " + cookiesMap[name] + '<br>'
		
		el.innerHTML += detailStr
	}
	
	function deleteCookie() {
		var name = "gstest"
		document.getElementById('delete').value = "true"
		document.forms[0].action = "<%= request.getContextPath() %>/cookie?cookiename=" + name
		document.forms[0].submit()
	}
</script>	
</head>
	
<body onload="displayCookies()">
	Cookies created : <div id='cookies'></div>
	<br>
	Displaying key value pairs: <div id = "cookieDetail"></div>
	 
	<form action="<%= request.getContextPath() %>/cookie" method="post">
		<input type="submit" value="Read Cookies">
		<input type="button" value="Delete Cookie" onclick="deleteCookie()">
		<input type="hidden" id="delete" name="delete" value="false">
	</form>
</body>	
</html>