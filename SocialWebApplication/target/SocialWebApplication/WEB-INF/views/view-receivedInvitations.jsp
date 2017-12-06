      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
   <html>
   
   <head>
  <title>Friends</title>
  </head>
  
  <body>

<h1>Friends invitations </h1>  
<table border="2" width="70%" cellpadding="2"> 

<tr>
<th>Nickname</th>
<th>Accept</th>
<th>Decline</th>
</tr>

<c:forEach var="nickname" items="${receivedInvitationsList}">
<tr>
<td><a href='/SocialWebApplication/profile/view/${nickname}'>${nickname}</a></td>  
<td><a href='/SocialWebApplication/profile/friends/invitation/accept=${nickname}'>Accept</a></td>  
<td><a href='/SocialWebApplication/profile/friends/invitation/decline=${nickname}'>Decline</a></td>  
</tr>
</c:forEach>
</table>
</body> 
</html>