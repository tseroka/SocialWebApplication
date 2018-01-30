<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Invitations</title>
</head>
 
<body>
<c:if test="${not empty receivedInvitationsList}">
<h1>Received invitations </h1>  
<table> 

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
</c:if>

<c:if test="${empty receivedInvitationsList}">
You have no active invitations to friends list.
</c:if>

</body> 

</html>