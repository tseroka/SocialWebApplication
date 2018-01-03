   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
   <html>
   <head>
  
  </head>
  <body>
<h1>Outbox</h1>  

<c:if test="${not empty outboxMessages}">
<table border="2" width="70%" cellpadding="2">  
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
</table>
</c:if>
<c:if test="${empty outboxMessages}">
No messages
</c:if>
</body>

</html>