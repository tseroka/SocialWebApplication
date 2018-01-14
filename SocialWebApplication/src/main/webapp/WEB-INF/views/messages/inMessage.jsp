<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Message</title>
</head>
<body>

<table  border="2" width="70%" cellpadding="2">
<tr>
<th>Sender</th><th>Subject</th>
<th>Text</th> <th>Sent date</th> <th>Remove message</th>
</tr>  
<tr>
<td><a href='/SocialWebApplication/profile/view/${message.messageSender}' target="_blank"> ${message.messageSender} </a> </td>  
<td>${message.messageSubject}</td>  
<td>${message.messageText}</a></td>  
<td>${message.sentDate} </td>
<td><a href='/SocialWebApplication/profile/messages/remove/${message.messageId}'>Remove</a></td>  
</tr>
</table>

<c:if test="${message.anyAttachment}">
<table  border="2" width="70%" cellpadding="2">
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