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

	<p class="ok">${registered}</p>
		
	<p class="error">${message}</p>
	
	<p class="ok">${ok}</p>
	
	
	
<form name='loginForm' action="<c:url value='/loginProcess' />" method='POST'>

	<input type='text' name='username' class="inputData" 
	placeholder="Username" onfocus="this.placeholder=''" onblur="this.placeholder='Username'"
	pattern="^[a-zA-Z0-9]{8,25}$" title="Invalid username or password" required>
			
	<input type='password' name='password' class="inputData" 
	placeholder="Password" onfocus="this.placeholder=''" onblur="this.placeholder='Password'"
	pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,40}$" title="Invalid username or password" required />
			
	<input name="submit" type="submit" value="Login" class="inputSubmit" />
			
    <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		
</form>

      <c:if test="${loginActionRedirect}">
      <a href='/exceptions/${linkToAction}' >${linkToAction}</a>
      </c:if>
      
      <p>Don't have the account? <a href='/register'>Create an account</a> </p>
      
	</div>

<a class="big" href="/about">About</a>
</body>

</html>