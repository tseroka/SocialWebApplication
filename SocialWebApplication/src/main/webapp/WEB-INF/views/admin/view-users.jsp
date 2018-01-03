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
<th>Country</th>
<th>Creation date</th>
<th>Enabled</th>
<th>Not locked</th><th>Block account</th> 
</tr>  

   <c:forEach var="User" items="${listUser}">   

    
   <tr>  
   <td>${User.id}</td>  
   <td>${User.username}</td>  
   <td>${User.nickname}</td>  
   <td>${User.email}</td>  
   <td>${User.country}</td> 
   <td>${User.creationDate}</td> 
   <td>${User.enabled}</td> 
   <td>${User.notLocked}</td> 
   <td>
    <c:if test = "${User.notLocked}">
        <a href='lockUser/${User.id}'>Block Account</a>
    </c:if>
    <c:if test = "${!User.notLocked}">
        <a href='unlockUser/${User.id}'>Unlock Account</a>
    </c:if>
</td> 
   </tr>  
   </c:forEach>  
   
</table>  
 
 </body>
 </html>