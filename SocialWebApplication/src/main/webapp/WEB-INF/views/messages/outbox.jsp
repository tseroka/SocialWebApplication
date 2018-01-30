<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Messages</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/table_style.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>

<body>
<div id="outboxContainer">

<h1>Outbox</h1>  

<c:if test="${not empty outboxMessages}">
<table class="table">  
<tr>
<th>Recipients</th><th>Subject</th> <th>Sent date</th> <th>Remove message</th>
</tr>  


<c:forEach var="message" items="${outboxMessages}">  
<tr>
<td>
<c:forEach var="recipient" items="${message.messageRecipients}"> 
<a href='/SocialWebApplication/profile/view/${recipient}' target="_blank"> ${recipient}</a> 
</c:forEach>
</td>  
<td> <a href='outbox/${message.messageId}'> ${message.messageSubject} </a></td>  
<td>${message.sentDate} </td>
<td><a href='/SocialWebApplication/profile/messages/remove/${message.messageId}'>Remove</a></td>  
</tr>
</c:forEach>

<c:forEach begin="1" end="${endpage}" var="page">
         <a href="/SocialWebApplication/profile/messages/outbox?page=${page}">${page}</a>
</c:forEach>

</table>
</c:if>
<c:if test="${empty outboxMessages}">
<h1>No messages</h1>
</c:if>

</div>

</body>

</html>