   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
   <html>
   <head>
  
  </head>
  <body>
<h1>Global Messages</h1>  

<c:if test="${not empty globalMessages}">
<table border="2" width="70%" cellpadding="2">  
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