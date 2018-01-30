<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Friends</title>
<style><%@include file="/css/index_style.css"%></style>
</head>
  
<body>

<c:if test="${not empty friendsList}">
<h1>Your Friends</h1>  
<table> 

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
</c:if>

<c:if test="${empty friendsList}">
You have no friends, try out <div class="link"> <a href="/SocialWebApplication/search">Search profiles</a> </div> feature to find someone You would like to know.
</c:if>

</body> 

</html>