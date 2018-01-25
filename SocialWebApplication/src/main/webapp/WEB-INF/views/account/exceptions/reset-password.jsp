<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" session="false" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Reset password</title>
<style><%@include file="/css/login_style.css"%></style> 
</head>

	<div id="activate-box">

	<table><tr><td style="font-style: bold; color: red;">${message}</td></tr></table>	
	
	Reset password
	
    <form:form name="resetPassword" modelAttribute="resetPassword" action="/SocialWebApplication/exceptions/resetPasswordProcessing" method="POST">

	<form:input type="text" path="code" name="unlockCode" cssClass="inputData" 
	placeholder="Unlock code" onfocus="this.placeholder=''" onblur="this.placeholder='Unlock code'"/>
				
	<form:password path="newPassword" name="newPassword" id="newPassword"  cssClass="inputData" 
    placeholder="New Password" onfocus="this.placeholder=''" onblur="this.placeholder='New Password'"/>

    <form:password path="newPasswordRepeat" name="newPasswordRepeat" id="newPasswordRepeat"  cssClass="inputData" 
    placeholder="Confirm" onfocus="this.placeholder=''" onblur="this.placeholder='Confirm Password'"/>
    
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    
	<form:button id="resetPassword" cssClass="inputSubmit" name="resetPassword">Reset password</form:button>
			
    </form:form>
    
    <a href="/SocialWebApplication/exceptions/Reset password">Send reset password code again</a>
	</div>

</body>
</html>