<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" session="false" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Reset password</title>

<style><%@include file="/css/form_style.css"%></style> 

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>

</head>

    <header>
	
		<h2 class="logo">Social Website</h2>

	</header>
	
	<div id="activate-box">

	<p class="error">${message}</p>

	
    <form:form name="resetPassword" modelAttribute="resetPassword" action="/exceptions/resetPasswordProcessing" method="POST">

	<form:input type="text" path="code" name="unlockCode" cssClass="inputData" 
	placeholder="Unlock code" onfocus="this.placeholder=''" onblur="this.placeholder='Unlock code'"/>
				
	<form:password path="newPassword" name="newPassword" id="newPassword"  cssClass="inputData" 
    placeholder="New Password" onfocus="this.placeholder=''" onblur="this.placeholder='New Password'"
    pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,40}$" title="Password must be 8-40 characters long, and contain at least one lowercase, uppercase letter and digit" required="true"/>

    <form:password path="newPasswordRepeat" name="newPasswordRepeat" id="newPasswordRepeat"  cssClass="inputData" 
    placeholder="Confirm" onfocus="this.placeholder=''" onblur="this.placeholder='Confirm Password'"/>
    
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    
	<form:button id="resetPassword" cssClass="inputSubmit" name="resetPassword">Reset password</form:button>
			
    </form:form>
    
    <a href="/exceptions/Reset password">Send reset password code again</a>
	</div>

</body>
</html>