   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
 <html>
   <head>
  
  </head>
  <body>
  <h1>${profile.nickname} profile</h1> </br>
  Sex: ${profile.sex} </br>
  City: ${profile.city} </br>
  Interests:
 <c:forEach items="${profile.interests}" var="interest">
    <tr>
        <td><c:out value="${interest}"/> ,</td>
    </tr>
</c:forEach>
</br>
 <c:if test="${pageContext.request.userPrincipal.name != null}">
     <a href='/SocialWebApplication/profile/messages/send/recipient=${profile.nickname}' >Send message</a> </br>
     
     <a href='/SocialWebApplication/profile/friends/invite=${profile.nickname}'>Invite to friends</a>
     
 </c:if>  
 
 
  </body>
  </html>