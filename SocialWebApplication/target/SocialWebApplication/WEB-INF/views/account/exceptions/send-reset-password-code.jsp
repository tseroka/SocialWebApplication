<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" session="false" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Send reset password code</title>

<style><%@include file="/css/form_style.css"%></style> 

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>


</head>

	<div id="activate-box">

	<p class="error">${message}</p>	
	
	Send reset password code
	
    <form:form name='sendResetPasswordCodeForm' modelAttribute="sendResetPasswordCode" action="/exceptions/sendResetPasswordCodeProcessing" method="POST">

	<form:input type="text" path="email" name="Email" cssClass="inputData" 
	placeholder="Email" onfocus="this.placeholder=''" onblur="this.placeholder='Email'"/>
		
	<form:input type="text" path="username" name="Username" cssClass="inputData" 
	placeholder="Username" onfocus="this.placeholder=''" onblur="this.placeholder='Username'"/>		
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	
	<form:button id="sendResetPasswordCode" cssClass="inputSubmit" name="sendResetPasswordCode">Send reset password code</form:button>
			
    </form:form>
    
	</div>

</body>
</html>