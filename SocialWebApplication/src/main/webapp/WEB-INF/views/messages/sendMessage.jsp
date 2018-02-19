<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Send message</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/form_style.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>

<body>

	
      <div id="container">                                   

<p class="error">${sendingNotAllowed}</p>

<form:form modelAttribute = "message" method="POST" action="/profile/messages/sendProcessing?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
         

 <form:input  path="messageRecipients" name="messageRecipients" id="recipients" cssClass="inputData" 
placeholder="Recipients" onfocus="this.placeholder=''"  onblur="this.placeholder='Recipients'"/> <br>
									
 <form:input path="messageSubject" name="messageSubject" id="subject"  cssClass="inputData" 
placeholder="Subject" onfocus="this.placeholder=''" onblur="this.placeholder='Subject'"/> <br>	
				
<form:textarea rows="10" cols="30" path="messageText" name="messageText" id="text" cssClass="inputData" 
      placeholder="Text" onfocus="this.placeholder=''" onblur="this.placeholder='Text'" /> <br>

  <p> You can upload up to 5 files with 20 MB total size </p> 
  
<form:input id="fileUpload" type="file" path="fileUpload" multiple="multiple" name="fileUpload" size="50" /> <br>	
	
<form:hidden path="fileUpload" name="${_csrf.parameterName}" value="${_csrf.token}" />
              
                <form:button>Send</form:button> 

        </form:form>

  

	</div>
	
</body>

</html>