      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
   <html>
   
   <head>
  <title>Friends</title>
  </head>
  
  <body>

<h1>Your Friends</h1>  
<table border="2" width="70%" cellpadding="2"> 

<tr>
<th>Nickname</th>
<th>Remove</th>
</tr>

<c:forEach var="nickname" items="${friendsList}">
<tr>
<td><a href='/SocialWebApplication/profile/view/${nickname}'>${nickname}</a> </td>  
<td><a href='/SocialWebApplication/profile/friends/remove=${nickname}'>Remove ${nickname} from friends list</a></td>
</tr>
</c:forEach>
</table>
</body> 
</html>