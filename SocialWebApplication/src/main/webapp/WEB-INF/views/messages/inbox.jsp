<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Messages</title>
</head>

<body>

<div id="inboxContainer">

<h1>Inbox</h1>  
<a href="globalMessages" >Global messages</a>

<c:if test="${not empty inboxMessages}">
<table>  
<tr>
<th>Sender</th> <th>Subject</th> <th>Sent date</th> <th>Remove message</th>
</tr>  

<c:forEach var="message" items="${inboxMessages}">  
<tr>
<td><a href='/SocialWebApplication/profile/view/${message.messageSender}' target="_blank"> ${message.messageSender}</a> </td>  
<td><a href='inbox/${message.messageId}'> ${message.messageSubject} </a> </td>
<td>${message.sentDate} </td>
<td><a href='/SocialWebApplication/profile/messages/remove/${message.messageId}'>Remove</a></td>  
</tr>
</c:forEach>
</table>

<c:forEach begin="1" end="${endpage}" var="page">
         <a href="/SocialWebApplication/profile/messages/inbox?page=${page}">${page}</a>
</c:forEach>
</c:if>

<c:if test="${empty inboxMessages}">
No messages
</c:if>

</div>
</body>

</html>