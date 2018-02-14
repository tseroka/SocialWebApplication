<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" session="false" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Activate account</title>

<style><%@include file="/css/form_style.css"%></style> 

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>


</head>

    <header>
	
		<h2 class="logo">Social Website</h2>

	</header>
	
	<div id="activate-box">

	<p class="error">${message}</p>
	<p class="ok">${registered}</p>
	
    <form:form name='activationForm' modelAttribute="activate" action="/activateProcessing" method="POST">

	<form:input type="text" path="code" name="activationCode" cssClass="inputData" 
	placeholder="Activation code" onfocus="this.placeholder=''" onblur="this.placeholder='Activation code'"/>
				
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				
	<form:button id="activate" cssClass="inputSubmit" name="activate">Activate account</form:button>
			
    </form:form>
    
    <a href="/sendActivationCodeAgain">Send activation code again</a>
	</div>

</body>
</html>