<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" session="false" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Send unlock code</title>

<style><%@include file="/css/form_style.css"%></style> 

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>


</head>

    <header>
	
		<h2 class="logo">Social Website</h2>

	</header>
	
	<div id="activate-box">

	<p class="error">${message}</p>
	
	
    <form:form name='sendUnlockCodeForm' modelAttribute="sendUnlockCode" action="/exceptions/sendUnlockCodeProcessing" method="POST">

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