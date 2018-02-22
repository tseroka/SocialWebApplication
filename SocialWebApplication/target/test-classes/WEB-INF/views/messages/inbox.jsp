<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ page language="java"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Messages</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<style><%@include file="/css/index_style.css"%></style>
<style><%@include file="/css/navigation_bar.css"%></style>
<style><%@include file="/css/table_style.css"%></style>

<jsp:include page="/WEB-INF/views/static/navbar.jsp" />

</head>

<body>

<div id="inboxContainer">

<h1> Inbox</h1>  


<c:if test="${not empty inboxMessages.content}">
<table class="table">  
<tr>
<th>Sender</th> <th>Subject</th> <th>Sent date</th> <th>Remove message</th>
</tr>  

<c:forEach var="message" items="${inboxMessages.content}">  
<tr>
<td><a href='/profile/view/${message.messageSender}' target="_blank"> ${message.messageSender}</a> </td>  
<td><a href='inbox/${message.messageId}'> ${message.messageSubject} </a> </td>
<td>${message.sentDate} </td>
<td>
<c:url var="removeURL" value='/profile/messages/remove/${message.messageId}'/>
     <form action="${removeURL}" id="remove" method="post" class="confirm">
       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
       <input class="tableButton" type="submit" value="Remove" />
     </form>
</td>  
</tr>
</c:forEach>
</table>

<c:forEach begin="1" end="${endpage}" var="page">
         <a href="/profile/messages/inbox?page=${page}">${page}</a>
</c:forEach>
</c:if>

<c:if test="${empty inboxMessages.content}">
<h1>No messages</h1>
</c:if>

</div>
<script>  
    $(document).on('submit','form.confirm', function(){
    	return confirm('Are you sure?'); 
    	});
    </script>
</body>

</html>