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

<table align="center"><tr><td style="font-style: italic; color: red;">${sameInputsMessage}</td></tr></table>
<table align="center"><tr><td style="font-style: italic; color: red;">${noChanges}</td></tr></table>	

<form:form modelAttribute="editAccount" method="POST" action="/SocialWebApplication/user/edit/save">    

<table align="center"><tr><td style="font-style: italic; color: red;">${usernameExistsMessage}</td></tr></table>
<form:input path="username" name="username" id="username" cssClass="inputData" 
placeholder="Username" onfocus="this.placeholder=''"  onblur="this.placeholder='Username'"/>

						
<table align="center"><tr><td style="font-style: italic; color: red;">${emailExistsMessage}</td></tr></table>
<form:input path="email" name="email" id="email" cssClass="inputData" 
placeholder="Email" onfocus="this.placeholder=''" onblur="this.placeholder='Email'" />
				

<form:input path="country" name="country" id="country" cssClass="inputData" 
placeholder="Country" onfocus="this.placeholder=''" onblur="this.placeholder='Country'" />
	
<table align="center"><tr><td style="font-style: italic; color: red;">${invalidPasswordMessage}</td></tr></table>	
<form:password path="currentPassword" name="Current password" id="currentPassword"  cssClass="inputData" 
placeholder="Current password" onfocus="this.placeholder=''" onblur="this.placeholder='Current password'"/>

				
<form:button id="save" cssClass="inputSubmit" name="save">Save</form:button>
		
				<a href="/SocialWebApplication/">Home</a>
		
</form:form>



	</div>


</body>
</html>