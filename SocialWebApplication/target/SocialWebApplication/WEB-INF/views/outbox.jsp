   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
   <html>
   <head>
  
  </head>
  <body>
<h1>Outbox</h1>  


<table border="2" width="70%" cellpadding="2">  
<tr>
<th>Recipients</th><th>Subject</th>
</tr>  

<c:forEach var="message" items="${outboxMessages}">  
<tr>
<td>
<c:forEach var="recipient" items="${message.messageRecipients}"> 
<a href='/SocialWebApplication/profile/view/${recipient}' target="_blank"> ${recipient}</a> 
</c:forEach>
</td>  
<td> <a href='outbox/${message.messageId}'> ${message.messageSubject} </a></td>  
</tr>
</c:forEach>
</body>

</html>