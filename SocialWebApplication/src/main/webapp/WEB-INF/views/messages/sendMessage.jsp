<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style><%@include file="/css/register_style.css"%></style> 
<title>Send Message</title>
</head>
<body>

      <div id="container">

<form:form id="sendMessageForm" modelAttribute="newMessage" action="/SocialWebApplication/profile/messages/sendProcessing" method="post">

		
<form:input path="messageRecipients" name="messageRecipients" id="recipients" cssClass="inputData" 
placeholder="Recipients" onfocus="this.placeholder=''"  onblur="this.placeholder='Recipients'"/>
			
			
			
<form:input path="messageSubject" name="messageSubject" id="subject"  cssClass="inputData" 
placeholder="Subject" onfocus="this.placeholder=''" onblur="this.placeholder='Subject'"/>
			
				
				
<form:input path="messageText" name="messageText" id="text" cssClass="inputData" 
placeholder="Text" onfocus="this.placeholder=''" onblur="this.placeholder='Text'" />
				
			
				
<form:button id="send" cssClass="inputSubmit" name="register">Send</form:button>
		
				<a href="home.jsp">Home</a>
		
</form:form>

	</div>
	
     <table align="center">
		<tr>
			<td style="font-style: italic; color: red;">${emptyRecipientsOrSubject}</td>
		</tr>
	</table>
	
</body>
</html>