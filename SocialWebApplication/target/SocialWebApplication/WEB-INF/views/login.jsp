<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" session="false"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Login</title>

<style><%@include file="/css/login_style.css"%></style> 

</head>

<body onload='document.loginForm.username.focus();'>

	<div id="login-box">

	<table><tr><td style="font-style: bold; color: green;">${registered}</td></tr></table>
		
	<table><tr><td style="font-style: bold; color: red;">${message}</td></tr></table>	
	
	<table><tr><td style="font-style: bold; color: green;">${ok}</td></tr></table>	
	
	
<form name='loginForm' action="<c:url value='/loginProcess' />" method='POST'>

	<input type='text' name='username' class="inputData" 
	placeholder="Username" onfocus="this.placeholder=''" onblur="this.placeholder='Username'">
			
	<input type='password' name='password' class="inputData" 
	placeholder="Password" onfocus="this.placeholder=''" onblur="this.placeholder='Password'" />
			
	<input name="submit" type="submit" value="Login" class="inputSubmit" />
			
    <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		
</form>

      <c:if test="${loginActionRedirect}">
      <a href='/SocialWebApplication/exceptions/${linkToAction}' >${linkToAction}</a>
      </c:if>
      
	</div>

</body>

</html>