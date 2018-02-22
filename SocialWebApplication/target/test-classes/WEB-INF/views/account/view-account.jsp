<%@ page language="java" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>View account</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<script src="/scripts/navigation_bar.js"></script>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>
 
<body>

	
	<p class="ok">${message}</p>
	
  <h2> Username : ${user.username}  </h2>  <br>
  <h2> Nickname: ${user.nickname}</h2>  <br>
  <h2> Email: ${user.email} </h2> <br>
  <h2> Country: ${user.country} </h2> <br> 



<h2><a href="/user/edit" >Edit account</a></h2> <br>
<h2><a href="/user/edit/password" >Change password</a></h2>


</body>

</html>