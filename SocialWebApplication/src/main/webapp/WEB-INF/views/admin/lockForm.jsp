<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="false" %>
<html>
<head>
<title>Lock account</title>
<style><%@include file="/css/login_style.css"%></style> 
</head>

	<div id="activate-box">

	
    <form:form name='lockForm' modelAttribute="lockAccount" action="/SocialWebApplication/admin/lockProcessing" method="POST">

	Account id: <form:input type="text" path="id" name="id" class="inputData" 
	placeholder="Account Id" onfocus="this.placeholder=''" onblur="this.placeholder='Account Id'"/>
	
	</br>
	Lock time in days (0 is permanent): <form:input type="text" path="lockTime" name="lockTime" class="inputData" 
	placeholder="Lock time" onfocus="this.placeholder=''" onblur="this.placeholder='Lock time'"/>
	
	</br>
	Lock reason: <form:select path="lockReason" name="reason" id="lockReason" class="inputData">
	   <option value="badLanguage-perm">Bad language, permanent</option>
       <option value="badLanguage-time">Bad language, temporary </option>
	</form:select>
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	
	<form:button id="lock" cssClass="inputSubmit" name="activate">Lock account</form:button>
			
    </form:form>
    
	</div>

</body>
</html>