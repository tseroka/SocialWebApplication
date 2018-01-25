<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Profile</title>
</head>

<body>

<h1>Profiles List</h1>  
<table>  
<tr>

<th>Nickname</th><th>Sex</th>
<th>City</th>
</tr>  

   <c:forEach var="profile" items="${profiles}">   

    
   <tr>  
  
   <td>${profile.nickname}</td>  
  <td>${profile.sex}</td>  
  <td>${profile.city}</td>  

   </tr>  
   </c:forEach>  
   
</table>  
 
</body>
 
</html>