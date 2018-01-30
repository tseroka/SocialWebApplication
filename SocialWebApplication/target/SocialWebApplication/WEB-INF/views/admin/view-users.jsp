<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Manage users</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>

<body>
<h1>Users List</h1>  
<table>  
<tr>
<th>Id</th><th>Username</th>
<th>Nickname</th><th>Email</th>
<th>Country</th>
<th>Creation date</th>
<th>Enabled</th>
<th>Not locked</th><th>Lock account</th> 
<th>Delete account</th>
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
        <a href='lockUser/${User.id}'>Lock Account</a>
    </c:if>
    
   <c:if test = "${!User.notLocked}">   
     
       <c:url var="unlockURL" value='unlockUser/${User.id}'/>
       <form action="${unlockURL}" method="post" class="confirm">
       <input type="submit" value="Unlock account" />
       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
       </form>
   
   </c:if>
    
   </td> 
   
   <td> <c:url var="deleteURL" value='deleteUser/${User.id}'/>
   <form action="${deleteURL}" method="post" class="confirm">
   <input type="submit" value="Delete account" />
   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
   </form> </td>
   </tr>  
   </c:forEach>  
   
</table>  
 
    <c:forEach begin="1" end="${endpage}" var="page">
         <a href="/SocialWebApplication/admin/view-users?page=${page}">${page}</a>
    </c:forEach>
    
    <script>  
    $(document).on('submit','form.confirm', function(){
    	return confirm('Are you sure?'); 
    	});
    </script>
 </body>
 </html>