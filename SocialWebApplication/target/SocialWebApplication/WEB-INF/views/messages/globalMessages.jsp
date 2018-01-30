<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Admin messages</title>
</head>

<body>

<h1>Admin messages</h1>  

<c:if test="${not empty globalMessages}">
<table>  
<tr>
<th>Sender</th> <th>Subject</th> <th>Date</th>

</tr>  

<c:forEach var="message" items="${globalMessages}">  
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