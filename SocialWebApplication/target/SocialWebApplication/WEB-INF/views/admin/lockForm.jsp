<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Lock account</title>

<style><%@include file="/css/form_style.css"%></style> 

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


</head>

	<div id="activate-box">

	
    <form:form name='lockForm' modelAttribute="lockAccount" action="/SocialWebApplication/admin/lockProcessing" method="POST">

	Account id: <form:input type="text" path="id" name="id" class="inputData" 
	placeholder="Account Id" onfocus="this.placeholder=''" onblur="this.placeholder='Account Id'"/>
	
	<br>
	
	Lock time: <form:select path="lockTime" name="time" id="lockTime" cssClass="inputData">
	   <option value="0">Permanent</option>
       <option value="1">1 day</option>
       <option value="7">7 days</option>
       <option value="30">1 month</option>
       <option value="90">3 months</option>
       <option value="365">1 year</option>
	</form:select>
	
	<br>
	Lock reason: <form:select path="lockReason" name="reason" id="lockReason" class="inputData">
	   <option value="badLanguage">Bad language</option>
	</form:select>
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	
	<form:button id="lock" cssClass="inputSubmit" name="activate">Lock account</form:button>
			
    </form:form>
    
	</div>

</body>
</html>