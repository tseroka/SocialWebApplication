<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" session="false" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Activate account</title>
<style><%@include file="/css/login_style.css"%></style> 
</head>

	<div id="activate-box">

	<table><tr><td style="font-style: bold; color: red;">${message}</td></tr></table>	
	<table><tr><td style="font-style: bold; color: green;">${registered}</td></tr></table>	
	
    <form:form name='activationForm' modelAttribute="activate" action="/SocialWebApplication/activateProcessing" method="POST">

	<form:input type="text" path="code" name="activationCode" cssClass="inputData" 
	placeholder="Activation code" onfocus="this.placeholder=''" onblur="this.placeholder='Activation code'"/>
				
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				
	<form:button id="activate" cssClass="inputSubmit" name="activate">Activate account</form:button>
			
    </form:form>
    
    <a href="/SocialWebApplication/sendActivationCodeAgain">Send activation code again</a>
	</div>

</body>
</html>