<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Send global message</title>
<style><%@include file="/css/register_style.css"%></style> 
</head>

<body>

<h1>Send global message</h1>
      <div id="container">
 
<form:form id="sendGlobalMessageForm" modelAttribute="message" action="/SocialWebApplication/admin/sendProcessing" method="POST">
			
<form:input path="messageSubject" name="messageSubject" id="subject"  cssClass="inputData" 
placeholder="Subject" onfocus="this.placeholder=''" onblur="this.placeholder='Subject'"/>
	
				
<form:textarea rows="10" cols="30" path="messageText" name="messageText" id="text" cssClass="inputData" 
placeholder="Text" onfocus="this.placeholder=''" onblur="this.placeholder='Text'" />

<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				
<form:button>Send</form:button>
		
</form:form>

</div>

</body>

</html>