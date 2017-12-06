<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<form:errors path="username" cssStyle="color: #ff0000;" />
			
			
<form:password path="password" name="password" id="password"  cssClass="inputData" 
placeholder="Password" onfocus="this.placeholder=''" onblur="this.placeholder='Password'"/>
<form:errors path="password" cssStyle="color: #ff0000;" />
				
<form:input path="email" name="email" id="email" cssClass="inputData" 
placeholder="Email" onfocus="this.placeholder=''" onblur="this.placeholder='Email'" />
<form:errors path="email" cssStyle="color: #ff0000;" />
		
<form:input path="nickname" name="nickname" id="nickname" cssClass="inputData" 
placeholder="Nickname" onfocus="this.placeholder=''" onblur="this.placeholder='Nickname'" />
<form:errors path="nickname" cssStyle="color: #ff0000;" />
			
<form:input path="country" name="country" id="country" cssClass="inputData" 
placeholder="Country" onfocus="this.placeholder=''" onblur="this.placeholder='Country'" />
				
<form:button id="register" cssClass="inputSubmit" name="register">Register</form:button>
		
				<a href="home.jsp">Home</a>
		
</form:form>

	</div>
	
     <table align="center">
		<tr>
			<td style="font-style: italic; color: red;">${message}</td>
		</tr>
	</table>
	
</body>
</html>