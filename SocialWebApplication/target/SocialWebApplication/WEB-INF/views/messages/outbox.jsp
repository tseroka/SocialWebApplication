<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Messages</title>
</head>

<body>
<div id="outboxContainer">

<h1>Outbox</h1>  

<c:if test="${not empty outboxMessages}">
<table>  
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
No messages
</c:if>

</div>

</body>

</html>