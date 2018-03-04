<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Manage users</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/table_style.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>

<body>

	
<h1>Manage users</h1>  

<table class="table">  
<tr>
<th>Id</th><th>Username</th>
<th>Nickname</th><th>Email</th>
<th>Country</th>
<th>Creation date</th>
<th>Enabled</th>
<th>Locking account</th> 
<th>Delete account</th>
</tr>  

   <c:forEach var="User" items="${listUser.content}">   

    
   <tr>  
   <td>${User.id}</td>  
   <td>${User.username}</td>  
   <td>${User.nickname}</td>  
   <td>${User.email}</td>  
   <td>${User.country}</td> 
   <td>${User.creationDate}</td> 
   <td>${User.enabled}</td> 
   
   <td>
    
   <c:if test = "${User.notLocked}">   
       <c:url var="lockURL" value='lockUser/${User.id}'/>
       <form action="${lockURL}" method="get">
       <input class="tableButton" type="submit" value="Lock account" />
       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
       </form>
   </c:if>
    
   <c:if test = "${!User.notLocked}">   
       <c:url var="unlockURL" value='unlockUser/${User.id}'/>
       <form action="${unlockURL}" method="post" class="confirm">
       <input class="tableButton" type="submit" value="Unlock account" />
       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
       </form>
   </c:if>
    
   </td> 
   
   <td>
   <c:url var="deleteURL" value='deleteUser/${User.id}'/>
   <form action="${deleteURL}" method="post" class="confirm">
   <input class="tableButton" type="submit" value="Delete account" />
   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
   </form> 
   </td>
   </tr>  
   </c:forEach>  
   
</table>  
 
    <c:forEach begin="1" end="${endpage}" var="page">
        <a class="page" href="/admin/view-users?page=${page}">${page}</a> 
    </c:forEach>
    
    <script>  
    $(document).on('submit','form.confirm', function(){
    	return confirm('Are you sure?'); 
    	});
    </script>
 </body>
 </html>