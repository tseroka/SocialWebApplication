<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" session="false" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Send unlock code</title>
<style><%@include file="/css/login_style.css"%></style> 
</head>

	<div id="activate-box">

	<table><tr><td style="font-style: bold; color: red;">${message}</td></tr></table>	
	
	Send account unlock code
	
    <form:form name='sendUnlockCodeForm' modelAttribute="sendUnlockCode" action="/SocialWebApplication/exceptions/sendUnlockCodeProcessing" method="POST">

	<form:input type="text" path="email" name="Email" cssClass="inputData" 
	placeholder="Email" onfocus="this.placeholder=''" onblur="this.placeholder='Email'"/>
		
	<form:input type="text" path="username" name="Username" cssClass="inputData" 
	placeholder="Username" onfocus="this.placeholder=''" onblur="this.placeholder='Username'"/>		
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	
	<form:button id="sendUlockCode" cssClass="inputSubmit" name="sendUnlockCode">Send unlock code</form:button>
			
    </form:form>
    
	</div>

</body>
</html>