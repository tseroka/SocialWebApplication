<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="false" %>
<html>
<head>
<title>Activate account</title>
<style><%@include file="/css/login_style.css"%></style> 
</head>

	<div id="activate-box">

	<table align="center"><tr><td style="font-style: bold; color: red;">${message}</td></tr></table>	
	
    <form:form name='activationForm' modelAttribute="send-activation-code-again" action="/SocialWebApplication/sendActivationCodeAgainProcessing" method="POST">

	<form:input type="text" path="email" name="Email" class="inputData" 
	placeholder="Email" onfocus="this.placeholder=''" onblur="this.placeholder='Email'"/>
		
	<form:input type="text" path="username" name="Username" class="inputData" 
	placeholder="Username" onfocus="this.placeholder=''" onblur="this.placeholder='Username'"/>		
	
	<form:button id="sendActivationCode" cssClass="inputSubmit" name="sendActivationCode">Send activation code</form:button>
			
    </form:form>
    
	</div>

</body>
</html>