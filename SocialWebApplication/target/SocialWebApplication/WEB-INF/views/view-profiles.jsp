 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
   <html>
   <head>
  
  </head>
  <body>
<h1>Profiles List</h1>  
<table border="2" width="70%" cellpadding="2">  
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