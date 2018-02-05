<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Search results</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/table_style.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>

<body>
<h1>Search Results</h1>  
<table>  
<tr>

<th>Nickname</th>
</tr>  

   <c:forEach var="nickname" items="${findedProfiles}">   

    
   <tr>  
 
   <td><a href='/SocialWebApplication/profile/view/${nickname}'>${nickname}</a></td>  
  

   </tr>  
   </c:forEach>  
   
</table> 
 
</body>

</html>