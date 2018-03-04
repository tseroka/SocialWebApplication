<%@ page language="java" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page import="com.app.web.social.security.SessionListener" %>
<!doctype html>
<html>

<head>
<meta charset="utf-8">
  
<title>Social Web Application</title>
    
<meta name="description" content="Web application with Spring MVC, Hibernate, MySQL">
<meta name="author" content="Tymon Seroka"> 

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/index_style.css"%></style>

</head>
 
<body>
  
  
	<c:if test="${pageContext.request.userPrincipal.name != null}">
			
	<jsp:include page="/WEB-INF/views/static/navbar.jsp" />
	
    </c:if>   
    
   Users online: ${usersOnline}

	 <p>&copy; Tymon Seroka </p>



</body>

</html>