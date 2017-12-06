      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
   <html>
   
   <head>
  <title>Friends</title>
  </head>
  
  <body>

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
</body> 
</html>