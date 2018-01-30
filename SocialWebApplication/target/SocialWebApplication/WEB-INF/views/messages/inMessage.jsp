<%@ page language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Private message</title>
</head>

<body>

<table>
<tr>
<th>Sender</th><th>Subject</th>
<th>Text</th> <th>Sent date</th> <th>Remove message</th>
</tr>  
<tr>
<td><a href='/SocialWebApplication/profile/view/${message.messageSender}' target="_blank"> ${message.messageSender} </a> </td>  
<td>${message.messageSubject}</td>  
<td>${message.messageText}</td>  
<td>${message.sentDate} </td>
<td><a href='/SocialWebApplication/profile/messages/remove/${message.messageId}'>Remove</a></td>  
</tr>
</table>

<c:if test="${message.anyAttachment}">
<table>
<tr>
<th>Attachment name</th>
<th>Download</th>
</tr>  
<c:forEach var="attachment" items="${message.attachments}">  
<tr>
<td>${attachment.fileName}</td>
<td> <a href='/SocialWebApplication/profile/messages/download?msg=${message.messageId}&att=${attachment.attachmentId}' >Download</a> </td>
</tr>
</c:forEach>
</table>
</c:if>

</body>

</html>