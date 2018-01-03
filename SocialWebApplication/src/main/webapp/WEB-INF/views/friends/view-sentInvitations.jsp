      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
   <html>
   
   <head>
  <title>Friends</title>
  </head>
  
  <body>
<c:if test="${not empty sentInvitationsList}">
<h1>Sent invitations</h1>  
<table border="2" width="70%" cellpadding="2"> 

<tr>
<th>Nickname</th>
</tr>

<c:forEach var="nickname" items="${sentInvitationsList}">
<tr>
<td><a href='/SocialWebApplication/profile/view/${nickname}'>${nickname}</a></td>  
</tr>
</c:forEach>
</table>
</c:if>

<c:if test="${empty sentInvitationsList}">
You have no active sent invitations to friends list.
</c:if>
</body> 
</html>