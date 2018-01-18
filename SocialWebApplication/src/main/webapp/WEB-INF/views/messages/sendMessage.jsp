<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
     "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style><%@include file="/css/register_style.css"%></style> 
<title>Send Message</title>
</head>
<body>

      <div id="container">                                   

<form:form modelAttribute = "message" method="POST" action="/SocialWebApplication/profile/messages/sendProcessing?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
         

 <form:input  path="messageRecipients" name="messageRecipients" id="recipients" cssClass="inputData" 
placeholder="Recipients" onfocus="this.placeholder=''"  onblur="this.placeholder='Recipients'"/> </br>
									
 <form:input path="messageSubject" name="messageSubject" id="subject"  cssClass="inputData" 
placeholder="Subject" onfocus="this.placeholder=''" onblur="this.placeholder='Subject'"/> </br>	
				
<form:textarea rows="10" cols="30" path="messageText" name="messageText" id="text" cssClass="inputData" 
      placeholder="Text" onfocus="this.placeholder=''" onblur="this.placeholder='Text'" /> </br>

You can upload up to 5 files with 20 MB total size </br>
<form:input id="fileUpload" type="file" path="fileUpload" multiple="multiple" name="fileUpload" size="50" /> </br>	
	
<form:hidden path="fileUpload" name="${_csrf.parameterName}" value="${_csrf.token}" />
              
                <form:button>Send</form:button> 

        </form:form>

  
  <a href="home.jsp">Home</a>
	</div>
	
</body>
</html>