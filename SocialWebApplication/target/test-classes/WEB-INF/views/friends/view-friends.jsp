<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Friends</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/table_style.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>
  
<body>

<c:if test="${not empty friendsList}">
<h1>Your Friends</h1>  
<table class="table"> 

<tr>
<th>Nickname</th>
<th>Remove</th>
</tr>

<c:forEach var="nickname" items="${friendsList}">
<tr>
<td><a href='/profile/view/${nickname}'>${nickname}</a> </td>  
<td><a href='/profile/friends/remove=${nickname}'>Remove ${nickname} from friends list</a></td>
</tr>
</c:forEach>
</table>
</c:if>

<c:if test="${empty friendsList}">
You have no friends, try out <div class="link"> <a href="/search">Search profiles</a> </div> feature to find someone You would like to know.
</c:if>

<c:if test="${not empty suggestedFriends}">

<c:if test="${suggest}">
<h1>Suggested friends</h1>  
<table class="table"> 

<tr>
<th>Nickname</th>
</tr>

<c:forEach var="nickname" items="${suggestedFriends}">
<tr>
<td><a href='/profile/view/${nickname}'>${nickname}</a> </td>  
</tr>
</c:forEach>

</table>
</c:if>
</c:if>
</body> 

</html>