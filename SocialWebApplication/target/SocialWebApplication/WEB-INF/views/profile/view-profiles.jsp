<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Profile</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/table_style.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>

<body>

<h1>Profiles List</h1>  
<table class="table">  
<tr>

<th>Nickname</th><th>Sex</th>
<th>City</th>
</tr>  

   <c:forEach var="profile" items="${profiles}">   

    
   <tr>  
  
   <td>${profile.nickname}</td>  
  <td>${profile.sex}</td>  
  <td>${profile.city}</td>  

   </tr>  
   </c:forEach>  
   
</table>  
 
</body>
 
</html>