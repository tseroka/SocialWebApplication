   <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
 <html>
   <head>
  
  </head>
  <body>


  <h1>${user.username} account</h1> </br>
  Nickname: ${user.nickname} </br>
  Email: ${user.email} </br>
  Country: ${user.country} </br> 


  </body>
  </html>