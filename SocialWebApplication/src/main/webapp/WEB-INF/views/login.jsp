<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" session="false"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Login</title>

<style><%@include file="/css/form_style.css"%></style> 

<style><%@include file="/css/index_style.css"%></style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


</head>

<body onload='document.loginForm.username.focus();'>

     <header>
	
		<h2 class="logo">Social Website</h2>

	</header>
	
	<div id="login-box">

	<table><tr><td style="font-style: bold; color: green;">${registered}</td></tr></table>
		
	<p class="error">${message}</p>
	
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
      
      <p>Don't have account? <a href='/SocialWebApplication/register'>Create account</a> </p>
      
	</div>

</body>

</html>