<%@ page language="java" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>View account</title>
</head>

<body>


  <h1>${user.username} account</h1> <br>
  Nickname: ${user.nickname} <br>
  Email: ${user.email} <br>
  Country: ${user.country} <br> 

<table><tr><td style="font-style: italic; color: green;">${message}</td></tr></table>	
<a href="/SocialWebApplication/user/edit" >Edit account</a> <br>
<a href="/SocialWebApplication/user/edit/password/" >Change password</a>

</body>

</html>