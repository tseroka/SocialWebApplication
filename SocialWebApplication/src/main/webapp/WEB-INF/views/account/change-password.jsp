<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style><%@include file="/css/register_style.css"%></style> 
<title>Edit account</title>
</head>
<body>
<div id="container">


<form:form modelAttribute="editAccount" method="POST" action="/SocialWebApplication/user/edit/password/save">    

<table align="center"><tr><td style="font-style: italic; color: red;">${invalidPasswordMessage}</td></tr></table>	
<form:password path="currentPassword" name="Current password" id="currentPassword"  cssClass="inputData" 
placeholder="Current password" onfocus="this.placeholder=''" onblur="this.placeholder='Current password'"/>

<form:password path="newPassword" name="New password" id="newPassword"  cssClass="inputData" 
placeholder="New password" onfocus="this.placeholder=''" onblur="this.placeholder='New password'"/>
			
<form:password path="repeatPassword" name="Current password" id="repeatPassword"  cssClass="inputData" 
placeholder="Repeat password" onfocus="this.placeholder=''" onblur="this.placeholder='Repeat password'"/>	

<form:button id="change" cssClass="inputSubmit" name="change">Change password</form:button>
			
</form:form>

</div>


</body>
</html>