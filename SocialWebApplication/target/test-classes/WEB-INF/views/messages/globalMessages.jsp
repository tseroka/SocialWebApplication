<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Admin messages</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/table_style.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>

<body>

<h1>Admin messages</h1>  

<c:if test="${not empty globalMessages}">
<table class="table">  
<tr>
<th>Sender</th> <th>Subject</th> <th>Date</th>

</tr>  

<c:forEach var="message" items="${globalMessages.content}">  
<tr>
<td> ${message.messageSender}</td>  
<td><a href='inbox/${message.messageId}'> ${message.messageSubject} </a> </td>
<td>${message.sentDate} </td>
</tr>
</c:forEach>
</table>
</c:if>

<c:if test="${empty globalMessages}">
No messages
</c:if>

</body>

</html>