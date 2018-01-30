<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Profile</title>
</head>

<body>

  <h1>${profile.nickname} profile</h1> <br>
  Sex: ${profile.sex} <br>
  City: ${profile.city} <br>
  Interests:
 <c:forEach items="${profile.interests}" var="interest">
    <tr>
        <td><c:out value="${interest}"/> ,</td>
    </tr>
</c:forEach>
<br>
     
     <c:if test="${profile.allowEveryoneToSendMessage}">
     <a href='/SocialWebApplication/profile/messages/send/recipient=${profile.nickname}' >Send message</a> 
     </c:if>
     
     <c:if test="${!profile.allowEveryoneToSendMessage}">
        <c:if test="${isFriend}">
           <a href='/SocialWebApplication/profile/messages/send/recipient=${profile.nickname}' >Send message</a> 
        </c:if>
     </c:if>
     <br>
     
     <c:if test = "${isFriend}">
     You are friends with ${profile.nickname}.
     </c:if>
     
     <c:if test = "${!isFriend}">
        <c:if test = "${!isInvited}">
            <a href='/SocialWebApplication/profile/friends/invite=${profile.nickname}'>Invite to friends</a>
        </c:if>
     </c:if>
 
     <c:if test = "${isInvited}">
     You have invited ${profile.nickname} to Your friends list.
     </c:if>
 
 
</body>
  
</html>