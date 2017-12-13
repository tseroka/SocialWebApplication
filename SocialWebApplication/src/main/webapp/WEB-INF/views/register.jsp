<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="false" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style><%@include file="/css/register_style.css"%></style> 



<title>Registration</title>
</head>
<body>

      <div id="container">

<form:form id="regForm" modelAttribute="user" action="registerProcess" method="post" > 

		
<form:input path="username" name="username" id="username" cssClass="inputData" 
placeholder="Username" onfocus="this.placeholder=''"  onblur="this.placeholder='Username'"/>
<table align="center"><tr><td style="font-style: italic; color: red;">${usernameExistsMessage}</td></tr></table>
						
						
							
<form:password path="password" name="password" id="password"  cssClass="inputData" 
placeholder="Password" onfocus="this.placeholder=''" onblur="this.placeholder='Password'"/>



<form:input path="email" name="email" id="email" cssClass="inputData" 
placeholder="Email" onfocus="this.placeholder=''" onblur="this.placeholder='Email'" />
<table align="center"><tr><td style="font-style: italic; color: red;">${emailExistsMessage}</td></tr></table>
		
		
		
<form:input path="nickname" name="nickname" id="nickname" cssClass="inputData" 
placeholder="Nickname" onfocus="this.placeholder=''" onblur="this.placeholder='Nickname'" />
<table align="center"><tr><td style="font-style: italic; color: red;">${nicknameExistsMessage}</td></tr></table>



<form:input path="country" name="country" id="country" cssClass="inputData" 
placeholder="Country" onfocus="this.placeholder=''" onblur="this.placeholder='Country'" />
				
<form:button id="register" cssClass="inputSubmit" name="register">Register</form:button>
		
				<a href="home.jsp">Home</a>
		
</form:form>

	</div>
	
     <table align="center"><tr><td style="font-style: italic; color: red;">${sameInputsMessage}</td></tr></table>
	
</body>
</html>