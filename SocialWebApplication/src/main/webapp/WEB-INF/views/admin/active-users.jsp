<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Active users</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/table_style.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>

<body>

	
<h1>Active users</h1>  

<table class="table">  
<tr>
<th>Username</th>
</tr>  

  <c:forEach var="User" items="${activeUsers}">   
  
  <tr>  
  <td>${User}</td>  
  </tr>
  
  </c:forEach>
   
</table>  
 
  
 </body>
 </html>