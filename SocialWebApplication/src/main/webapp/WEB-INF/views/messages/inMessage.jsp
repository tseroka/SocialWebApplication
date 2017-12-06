<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<th>Text</th><th>Remove message</th>
</tr>  

<td><a href='/SocialWebApplication/profile/view/${message.messageSender}' target="_blank"> ${message.messageSender} </a> </td>  
<td>${message.messageSubject}</td>  
<td>${message.messageText}</a></td>  
<td><a href='/SocialWebApplication/profile/messages/remove/${message.messageId}'>Remove</a></td>  

</table>

</body>

</html>