<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="false" %>
<html>
<head>
<title>Unlock account</title>
<style><%@include file="/css/login_style.css"%></style> 
</head>

	<div id="activate-box">

	<table align="center"><tr><td style="font-style: bold; color: red;">${message}</td></tr></table>	
    
	Unlock account 
	                                                                                                           
    <form:form name='unlockAccountForm' modelAttribute="unlockAccount" action="/SocialWebApplication/exceptions/unlockProcessing" method="POST">

	<form:input type="text" path="code" name="unlockCode" cssClass="inputData" 
	placeholder="Unlock code" onfocus="this.placeholder=''" onblur="this.placeholder='Unlock code'"/>
				
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				
	<form:button id="unlock" cssClass="inputSubmit" name="unlock">Unlock account</form:button>
			
    </form:form>
    
    <a href="/SocialWebApplication/exceptions/Unlock account">Send unlock code again</a>
	</div>

</body>
</html>