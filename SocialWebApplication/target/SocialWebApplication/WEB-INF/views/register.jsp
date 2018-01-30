<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" session="false" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Registration</title>

<style><%@include file="/css/register_style.css"%></style> 

<script>
var forms = document.querySelectorAll('registerForm');
for (var i = 0; i < forms.length; i++) 
{
    forms[i].setAttribute('novalidate', true);
}

</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script> 
$(function () {
    $("#register").click(function () {
        var password = $("#password").val();
        var confirmPassword = $("#repeatPassword").val();
        if (password != confirmPassword) {
            alert("Passwords do not match.");
            return false;
        }
        return true;
    });
});
</script>

</head>

<body>

      <div id="container">
      
<table><tr><td style="font-style: italic; color: red;">${sameInputsMessage}</td></tr></table>
<form:form id="registerForm" modelAttribute="user" action="registerProcess" method="post"> 


<table><tr><td style="font-style: italic; color: red;">${usernameExistsMessage}</td></tr></table>		
<form:input path="username" name="username" id="username" cssClass="inputData" 
placeholder="Username" onfocus="this.placeholder=''"  onblur="this.placeholder='Username'"
pattern="^[a-zA-Z0-9]{8,25}$" title="Username must be 8-25 alphanumeric characters long" required="true" />

						
<form:password path="password" name="password" id="password"  cssClass="inputData" 
placeholder="Password" onfocus="this.placeholder=''" onblur="this.placeholder='Password'"
pattern="((?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{8,40})" title="Passowrd must be 8-40 characters long, and contain at least one lowercase, uppercase letter, digit and special character" required="true" />

<form:password path="repeatPassword" name="Repeat password" id="repeatPassword"  cssClass="inputData" 
placeholder="Repeat password" onfocus="this.placeholder=''" onblur="this.placeholder='Repeat password'" required="true"/>

<table><tr><td style="font-style: italic; color: red;">${emailExistsMessage}</td></tr></table>
<form:input path="email" name="email" id="email" cssClass="inputData" 
placeholder="Email" onfocus="this.placeholder=''" onblur="this.placeholder='Email'"
 pattern="^[\\w!#$%&’*+/=\\-?^_`{|}~]+(\\.[\\w!#$%&’*+/=\\-?^_`{|}~]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$" title="Invalid email format" required="true" />
		
		
<table><tr><td style="font-style: italic; color: red;">${nicknameExistsMessage}</td></tr></table>
<form:input path="nickname" name="nickname" id="nickname" cssClass="inputData" 
placeholder="Nickname" onfocus="this.placeholder=''" onblur="this.placeholder='Nickname'" 
pattern="^[a-zA-Z]{4,25}$" title="Nickname must be 4-25 characters long and contain only letters" required="true"  />

<form:input path="country" name="country" id="country" cssClass="inputData" 
placeholder="Country" onfocus="this.placeholder=''" onblur="this.placeholder='Country'" />
	
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				
<form:button id="register" cssClass="inputSubmit" name="register">Register</form:button>
		
				<a href="home.jsp">Home</a>
		
</form:form>

	</div>

	
</body>

</html>