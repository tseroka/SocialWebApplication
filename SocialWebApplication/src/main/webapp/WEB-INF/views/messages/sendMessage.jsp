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
<form:form method="POST" action="/SocialWebApplication/profile/messages/sendProcessing" enctype="multipart/form-data">
            <table border="0">
                <tr>
                   
                    <td><form:input id="fileUpload" type="file" path="fileUpload"  name="fileUpload" size="50" /></td>
                </tr>
         
                <tr>
                    <td colspan="2" align="center"><input type="submit" value="Upload" /></td>
                </tr>
            </table>
        </form:form>



  
  <a href="home.jsp">Home</a>
	</div>
	
</body>
</html>