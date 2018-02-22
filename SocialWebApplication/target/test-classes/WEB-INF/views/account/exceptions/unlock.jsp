<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java"  session="false" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Unlock account</title>

<style><%@include file="/css/form_style.css"%></style> 

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>

</head>

    <header>
	
		<h2 class="logo">Social Website</h2>

	</header>
	
	<div id="activate-box">

	<p class="error">${message}</p>
     
	                                                                                                           
    <form:form name='unlockAccountForm' modelAttribute="unlockAccount" action="/exceptions/unlockProcessing" method="POST">

	<form:input type="text" path="code" name="unlockCode" cssClass="inputData" 
	placeholder="Unlock code" onfocus="this.placeholder=''" onblur="this.placeholder='Unlock code'"/>
				
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				
	<form:button id="unlock" cssClass="inputSubmit" name="unlock">Unlock account</form:button>
			
    </form:form>
    
    <a href="/exceptions/Unlock account">Send unlock code again</a>
    
	</div>

</body>
</html>