<%@ page language="java"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<style><%@include file="/css/register_style.css"%></style> 
<title>Edit account</title>
</head>
<body>
<div id="container">

<table><tr><td style="font-style: italic; color: red;">${sameInputsMessage}</td></tr></table>
<table><tr><td style="font-style: italic; color: red;">${noChanges}</td></tr></table>	

<form:form modelAttribute="editAccount" method="POST" action="/SocialWebApplication/user/edit/save">    

<table><tr><td style="font-style: italic; color: red;">${usernameExistsMessage}</td></tr></table>
<form:input path="username" name="username" id="username" cssClass="inputData" 
placeholder="Username" onfocus="this.placeholder=''"  onblur="this.placeholder='Username'"
pattern="^[a-zA-Z0-9]{8,25}$" title="Username must be 8-25 alphanumeric characters long" required="true"/>

						
<table><tr><td style="font-style: italic; color: red;">${emailExistsMessage}</td></tr></table>
<form:input path="email" name="email" id="email" cssClass="inputData" 
placeholder="Email" onfocus="this.placeholder=''" onblur="this.placeholder='Email'"
pattern="^[\\w!#$%&’*+/=\\-?^_`{|}~]+(\\.[\\w!#$%&’*+/=\\-?^_`{|}~]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$" title="Invalid email format" required="true" />
				

<form:input path="country" name="country" id="country" cssClass="inputData" 
placeholder="Country" onfocus="this.placeholder=''" onblur="this.placeholder='Country'"/>
	
<table><tr><td style="font-style: italic; color: red;">${invalidPasswordMessage}</td></tr></table>	
<form:password path="currentPassword" name="Current password" id="currentPassword"  cssClass="inputData" 
placeholder="Current password" onfocus="this.placeholder=''" onblur="this.placeholder='Current password'"/>

<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				
<form:button id="save" cssClass="inputSubmit" name="save">Save</form:button>
		
				<a href="/SocialWebApplication/">Home</a>
		
</form:form>



	</div>


</body>
</html>