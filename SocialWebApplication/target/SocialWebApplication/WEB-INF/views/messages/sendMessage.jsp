<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Send message</title>
<style><%@include file="/css/register_style.css"%></style> 
</head>

<body>

      <div id="container">                                   

<form:form modelAttribute = "message" method="POST" action="/SocialWebApplication/profile/messages/sendProcessing?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
         

 <form:input  path="messageRecipients" name="messageRecipients" id="recipients" cssClass="inputData" 
placeholder="Recipients" onfocus="this.placeholder=''"  onblur="this.placeholder='Recipients'"/> <br>
									
 <form:input path="messageSubject" name="messageSubject" id="subject"  cssClass="inputData" 
placeholder="Subject" onfocus="this.placeholder=''" onblur="this.placeholder='Subject'"/> <br>	
				
<form:textarea rows="10" cols="30" path="messageText" name="messageText" id="text" cssClass="inputData" 
      placeholder="Text" onfocus="this.placeholder=''" onblur="this.placeholder='Text'" /> <br>

You can upload up to 5 files with 20 MB total size <br>
<form:input id="fileUpload" type="file" path="fileUpload" multiple="multiple" name="fileUpload" size="50" /> <br>	
	
<form:hidden path="fileUpload" name="${_csrf.parameterName}" value="${_csrf.token}" />
              
                <form:button>Send</form:button> 

        </form:form>

  
  <a href="home.jsp">Home</a>
	</div>
	
</body>

</html>