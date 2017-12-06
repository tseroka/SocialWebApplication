   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
   <html>
   <head>
  
  </head>
  <body>
<h1>Inbox</h1>  

<table border="2" width="70%" cellpadding="2">  
<tr>
<th>Sender</th><th>Subject</th>

</tr>  

<c:forEach var="message" items="${inboxMessages}">  
<tr>
<td><a href='/SocialWebApplication/profile/view/${message.messageSender}' target="_blank"> ${message.messageSender}</a> </td>  
<td><a href='inbox/${message.messageId}'> ${message.messageSubject} </a> </td>
</tr>
</c:forEach>
</body>

</html>