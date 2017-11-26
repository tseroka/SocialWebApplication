 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
   <html>
   <head>
  
  </head>
  <body>
<h1>Users List</h1>  
<table border="2" width="70%" cellpadding="2">  
<tr>
<th>Id</th><th>Username</th>
<th>Nickname</th><th>Email</th>
<th>Country</th><th>Role</th>
<th>Enabled</th><th>Disable</th> 
</tr>  

   <c:forEach var="aUser" items="${listUser}">   

    
   <tr>  
   <td>${aUser.id}</td>  
   <td>${aUser.username}</td>  
   <td>${aUser.nickname}</td>  
   <td>${aUser.email}</td>  
   <td>${aUser.country}</td> 
   <td>${aUser.role}</td> 
   <td>${aUser.enabled}</td> 
   <td>
    <c:if test = "${aUser.enabled}">
        <a href='disableUser/${aUser.id}'>Disable Account</a>
    </c:if>
    <c:if test = "${!aUser.enabled}">
        <a href='enableUser/${aUser.id}'>Enable Account</a>
    </c:if>
</td> 
   </tr>  
   </c:forEach>  
   
</table>  
 
 </body>
 </html>