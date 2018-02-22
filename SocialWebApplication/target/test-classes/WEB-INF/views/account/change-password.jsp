<%@ page language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<!doctype html>
<html>
<head>
<meta charset="utf-8">

<title>Change password</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/form_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/index_style.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>
<body>

	
<div id="container">


<form:form modelAttribute="editAccount" method="POST" action="/user/edit/password/save">    

<p class="error">${invalidPasswordMessage}</p>	
<form:password path="currentPassword" name="Current password" id="currentPassword"  cssClass="inputData" 
placeholder="Current password" onfocus="this.placeholder=''" onblur="this.placeholder='Current password'"/>

<form:password path="newPassword" name="New password" id="newPassword"  cssClass="inputData" 
placeholder="New password" onfocus="this.placeholder=''" onblur="this.placeholder='New password'"
pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,40}$" title="Password must be 8-40 characters long, and contain at least one lowercase, uppercase letter and digit" required="true"/>
			
<form:password path="repeatPassword" name="Current password" id="repeatPassword"  cssClass="inputData" 
placeholder="Repeat password" onfocus="this.placeholder=''" onblur="this.placeholder='Repeat password'"/>	

<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

<form:button id="change" cssClass="inputSubmit" name="change">Change password</form:button>
			
</form:form>

</div>

<script> 
$(function () {
    $("#register").click(function () {
        var password = $("#newPassword").val();
        var confirmPassword = $("#repeatPassword").val();
        if (password != confirmPassword) {
            alert("Passwords do not match.");
            return false;
        }
        return true;
    });
});
</script>

</body>
</html>