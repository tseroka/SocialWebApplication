<%@ page language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Private message</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/table_style.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>

<body>

<table class="table">
<tr>
<th>Sender</th><th>Subject</th>
<th>Sent date</th> <th>Remove message</th>
</tr>  
<tr>
<td><a href='/profile/view/${message.messageSender}' target="_blank"> ${message.messageSender} </a> </td>  
<td>${message.messageSubject}</td>   
<td>${message.sentDate} </td>
<td>
<c:url var="removeURL" value='/profile/messages/remove/${message.messageId}'/>
     <form action="${removeURL}" id="remove" method="post" class="confirm">
       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
     </form>
     <a href="#" onclick='$("#remove").submit();'>Remove</a>
</td>  
</tr>
</table>

<div class="messageText">
 ${message.messageText}
</div>

<c:if test="${message.anyAttachment}">
<table class="table">
<tr>
<th>Attachment name</th>
<th>Download</th>
</tr>  
<c:forEach var="attachment" items="${message.attachments}">  
<tr>
<td>${attachment.fileName}</td>
<td> <a href='/profile/messages/download?msg=${message.messageId}&att=${attachment.attachmentId}' >Download</a> </td>
</tr>
</c:forEach>
</table>
</c:if>

</body>

</html>