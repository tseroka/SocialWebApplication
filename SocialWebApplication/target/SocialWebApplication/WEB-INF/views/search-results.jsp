 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
   <html>
   <head>
  
  </head>
  <body>
<h1>Search Results</h1>  
<table border="2" width="70%" cellpadding="2">  
<tr>

<th>Nickname</th>
</tr>  

   <c:forEach var="profile" items="${findedProfiles}">   

    
   <tr>  
 
   <td><a href='/SocialWebApplication/profile/view/${profile.nickname}'>${profile.nickname}</a></td>  
  

   </tr>  
   </c:forEach>  
   
</table>  
 
 </body>
 </html>