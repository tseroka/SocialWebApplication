<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Invitations</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/table_style.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>
 
<body>
<c:if test="${not empty receivedInvitationsList}">
<h1>Received invitations </h1>  
<table class="table"> 

<tr>
<th>Nickname</th>
<th>Accept</th>
<th>Decline</th>
</tr>

<c:forEach var="nickname" items="${receivedInvitationsList}">
<tr>
<td><a href='/profile/view/${nickname}'>${nickname}</a></td>  
<td><a href='/profile/friends/invitation/accept=${nickname}'>Accept</a></td>  
<td><a href='/profile/friends/invitation/decline=${nickname}'>Decline</a></td>  
</tr>
</c:forEach>
</table>
</c:if>

<c:if test="${empty receivedInvitationsList}">
You have no active invitations to friends list.
</c:if>

</body> 

</html>