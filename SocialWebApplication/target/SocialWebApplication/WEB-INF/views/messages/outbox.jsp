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
<div id="outboxContainer">

<h1>Outbox</h1>  

<c:if test="${not empty outboxMessages.content}">
<table class="table">  
<tr>
<th>Recipients</th><th>Subject</th> <th>Sent date</th> <th>Remove message</th>
</tr>  


<c:forEach var="message" items="${outboxMessages.content}">  
<tr>

<td>
<c:forEach var="recipient" items="${message.messageRecipients}"> 
<a href='/profile/view/${recipient}' target="_blank"> ${recipient}</a> 
</c:forEach>
</td>  

<td> <a href='outbox/${message.messageId}'> ${message.messageSubject} </a></td>  

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
         <a href="/profile/messages/outbox?page=${page}">${page}</a>
</c:forEach>

</c:if>
<c:if test="${empty outboxMessages.content}">
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