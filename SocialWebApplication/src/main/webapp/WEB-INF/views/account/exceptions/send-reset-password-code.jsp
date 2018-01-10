<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="false" %>
<html>
<head>
<title>Send reset password code</title>
<style><%@include file="/css/login_style.css"%></style> 
</head>

	<div id="activate-box">

	<table align="center"><tr><td style="font-style: bold; color: red;">${message}</td></tr></table>	
	
	Send reset password code
	
    <form:form name='sendResetPasswordCodeForm' modelAttribute="sendResetPasswordCode" action="/SocialWebApplication/exceptions/sendResetPasswordCodeProcessing" method="POST">

	<form:input type="text" path="email" name="Email" class="inputData" 
	placeholder="Email" onfocus="this.placeholder=''" onblur="this.placeholder='Email'"/>
		
	<form:input type="text" path="username" name="Username" class="inputData" 
	placeholder="Username" onfocus="this.placeholder=''" onblur="this.placeholder='Username'"/>		
	
	<form:button id="sendResetPasswordCode" cssClass="inputSubmit" name="sendResetPasswordCode">Send reset password code</form:button>
			
    </form:form>
    
	</div>

</body>
</html>