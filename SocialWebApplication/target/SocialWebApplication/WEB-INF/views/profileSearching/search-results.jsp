<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Search results</title>
</head>

<body>
<h1>Search Results</h1>  
<table>  
<tr>

<th>Nickname</th>
</tr>  

   <c:forEach var="nickname" items="${findedProfiles}">   

    
   <tr>  
 
   <td><a href='/SocialWebApplication/profile/view/${nickname}'>${nickname}</a></td>  
  

   </tr>  
   </c:forEach>  
   
</table> 
 
</body>

</html>