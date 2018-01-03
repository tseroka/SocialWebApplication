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

<h1>Send global message</h1>
      <div id="container">
 
<form:form id="sendGlobalMessageForm" modelAttribute="message" action="/SocialWebApplication/admin/sendProcessing" method="POST">
			
<form:input path="messageSubject" name="messageSubject" id="subject"  cssClass="inputData" 
placeholder="Subject" onfocus="this.placeholder=''" onblur="this.placeholder='Subject'"/>
	
				
				
<form:input path="messageText" name="messageText" id="text" cssClass="inputData" 
placeholder="Text" onfocus="this.placeholder=''" onblur="this.placeholder='Text'" />
				
<form:button>Send</form:button>
		
</form:form>