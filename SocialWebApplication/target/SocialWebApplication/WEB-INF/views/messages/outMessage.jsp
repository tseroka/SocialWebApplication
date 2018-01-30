<%@ page language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Message</title>
</head>
<body>

<table>

<tr>
<th>Recipients</th><th>Subject</th>
<th>Text</th> <th>Sent date</th> <th>Remove message</th>
</tr>  

<tr>
<td> 
<c:forEach var="recipient" items="${message.messageRecipients}"> 
<a href='/SocialWebApplication/profile/view/${recipient}' target="_blank"> ${recipient}</a> 
</c:forEach>
</td>  
<td>${message.messageSubject}</td>  
<td>${message.messageText}</td>  
<td>${message.sentDate} </td>
<td><a href='/SocialWebApplication/profile/messages/remove/${message.messageId}'>Remove</a></td>  
</tr>

</table>

</body>

</html>