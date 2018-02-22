<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Profile</title>
</head>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

<body>

  <h1>${profile.nickname} </h1> <br>


  <h2> City: ${profile.city} </h2> <br>
  <h2> Sex: ${profile.sex} </h2> <br>
  <h2> Interests: 
  
  <c:forEach items="${profile.interests}" var="interest">
    <tr>
        <td><c:out value="${interest}"/> ,</td>
    </tr>
</c:forEach> </h2> <br> 
  
  
     <c:if test="${profile.allowEveryoneToSendMessage}">
    <a href='/profile/messages/send/recipient=${profile.nickname}' >Send message</a> 
     </c:if>
     
     <c:if test="${!profile.allowEveryoneToSendMessage}">
        <c:if test="${isFriend}">
           <a href='/profile/messages/send/recipient=${profile.nickname}' >Send message</a> 
        </c:if>
     </c:if>
     <br>
     
     <c:if test = "${isFriend}">
     <h2> You are friends with ${profile.nickname}.</h2>
     </c:if>
     
     <c:if test = "${!isFriend}">
        <c:if test = "${!isInvited}">
            <a id="invite" href='/profile/friends/invite=${profile.nickname}'>Invite to friends</a>
        </c:if>
     </c:if>
 
     <c:if test = "${isInvited}">
     <h2> You have invited ${profile.nickname} to Your friends list. </h2>
     </c:if>
 
 
</body>
  
</html>